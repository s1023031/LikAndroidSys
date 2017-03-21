package com.lik.android.om;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;

public class Phrase extends BasePhrase implements ProcessDownloadInterface {

	private static final long serialVersionUID = -122075511238904724L;

	@Override
	public Phrase doInsert(LikDBAdapter adapter) {
		  InsertHelper ih = adapter.getInsertHelper(Phrase.TABLE_NAME);
		  ih.prepareForInsert();
        ih.bind(2, getPhkindNO());
        ih.bind(3, getPhraseNO());
        ih.bind(4, getPhraseDESC());
        ih.bind(5, getVersionNo());
  	    long rid = ih.execute();
        if(rid != -1) setRid(0);
		return this;
	}

//	@Override
//	public Phrase doInsert(LikDBAdapter adapter) {
//		SQLiteDatabase db = getdb(adapter);
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(COLUMN_NAME_PHKINDNO, getPhkindNO());
//        initialValues.put(COLUMN_NAME_PHRASENO, getPhraseNO());
//        initialValues.put(COLUMN_NAME_PHRASEDESC, getPhraseDESC());
//        initialValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
//        long rid = db.insert(TABLE_NAME, null, initialValues);
//        setRid(rid);
////        db.close();
//        closedb(adapter);
//		return this;
//	}

	@Override
	public Phrase doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();

        updateValues.put(COLUMN_NAME_PHRASEDESC, getPhraseDESC());
        updateValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(TABLE_NAME, updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
//        db.close();
        closedb(adapter);
		return this;		
	}

	@Override
	public Phrase doDelete(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SERIALID+"="+getSerialID();
		if(isDebug) Log.d(TAG,whereClause);
		//String[] whereArgs = {String.valueOf(getSerialID())};
		int rid = db.delete(TABLE_NAME, whereClause, null);
        setRid(rid);
		if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
//		db.close();
        closedb(adapter);
		return this;
	}

	@Override
	public Phrase findByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_PHKINDNO+"="+getPhkindNO());
		qb.appendWhere(" and "+COLUMN_NAME_PHRASENO+"='"+getPhraseNO()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PHRASE_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
		if(c == null) {
			setRid(-1);
			return this;
		} 	
        boolean notEmpty = c.moveToFirst();
        if(notEmpty) {
        	setSerialID(c.getInt(READ_PHRASE_SERIALID_INDEX));
        	setPhraseDESC(c.getString(READ_PHRASE_PHRASEDESC_INDEX));
            setVersionNo(c.getString(READ_PHRASE_VERSIONNO_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public Phrase queryBySerialID(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_SERIALID+"="+getSerialID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PHRASE_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
		if(c == null) {
			setRid(-1);
			return this;
		} 	
        boolean notEmpty = c.moveToFirst();
        if(notEmpty) {
        	setSerialID(c.getInt(READ_PHRASE_SERIALID_INDEX));
    		setPhkindNO(c.getInt(READ_PHRASE_PHKINDNO_INDEX));
    		setPhraseNO(c.getString(READ_PHRASE_PHRASENO_INDEX));
        	setPhraseDESC(c.getString(READ_PHRASE_PHRASEDESC_INDEX));
            setVersionNo(c.getString(READ_PHRASE_VERSIONNO_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public boolean processDownload(LikDBAdapter adapter,Map<String, String> detail, boolean isOnlyInsert) {
		boolean result = true;
		String flag = detail.get(FLAG_KEY);
		setPhkindNO(Integer.parseInt(detail.get(COLUMN_NAME_PHKINDNO)));
		setPhraseNO(detail.get(COLUMN_NAME_PHRASENO));
		if(!isOnlyInsert) findByKey(adapter);
		setPhraseDESC(detail.get(COLUMN_NAME_PHRASEDESC));
		setVersionNo(detail.get(COLUMN_NAME_VERSIONNO));
		if(isOnlyInsert) doInsert(adapter);
		else {
			if(getRid()<0) doInsert(adapter);
			else {
				if(flag.equals(FLAG_DELETE)) doDelete(adapter);
				else doUpdate(adapter);
			}
		}
		if(getRid()<0) result = false;
		return result;
	}
	
	public List<Phrase> getAllPhrase(LikDBAdapter adapter) {
		List<Phrase> result = new ArrayList<Phrase>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PHRASE_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	Phrase om = new Phrase();
            	om.setSerialID(c.getInt(READ_PHRASE_SERIALID_INDEX));

            	om.setPhkindNO(c.getInt(READ_PHRASE_PHKINDNO_INDEX));
            	om.setPhraseNO(c.getString(READ_PHRASE_PHRASENO_INDEX));
                om.setPhraseDESC(c.getString(READ_PHRASE_PHRASEDESC_INDEX));
                om.setVersionNo(c.getString(READ_PHRASE_VERSIONNO_INDEX));
                om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
        }
//        db.close();		
        c.close();
        closedb(adapter);
		return result;
	}

	public List<Phrase> getPhraseByPhkindNO(LikDBAdapter adapter) {
		List<Phrase> result = new ArrayList<Phrase>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_PHKINDNO+"="+getPhkindNO());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PHRASE_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	Phrase om = new Phrase();
            	om.setSerialID(c.getInt(READ_PHRASE_SERIALID_INDEX));

            	om.setPhkindNO(c.getInt(READ_PHRASE_PHKINDNO_INDEX));
            	om.setPhraseNO(c.getString(READ_PHRASE_PHRASENO_INDEX));
                om.setPhraseDESC(c.getString(READ_PHRASE_PHRASEDESC_INDEX));
                om.setVersionNo(c.getString(READ_PHRASE_VERSIONNO_INDEX));
                om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
        }
//        db.close();		
        c.close();
        closedb(adapter);
		return result;
	}

	public Phrase findByDesc(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_PHKINDNO+"="+getPhkindNO());
		qb.appendWhere(" and "+COLUMN_NAME_PHRASEDESC+"='"+getPhraseDESC()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PHRASE_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
		if(c == null) {
			setRid(-1);
			return this;
		} 	
        boolean notEmpty = c.moveToFirst();
        if(notEmpty) {
        	setSerialID(c.getInt(READ_PHRASE_SERIALID_INDEX));
        	setPhraseDESC(c.getString(READ_PHRASE_PHRASEDESC_INDEX));
            setVersionNo(c.getString(READ_PHRASE_VERSIONNO_INDEX));
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}


}
