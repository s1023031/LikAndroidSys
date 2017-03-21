package com.lik.android.om;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;

public class PriorityList extends BasePriorityList {

	private static final long serialVersionUID = 1172541409375226068L;

	public PriorityList getPriorityListByPrimaryKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_SITENAME+"='"+getSiteName()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_PRIORITY+"="+getPriority());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PROIRITYLIST_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null) {
        	boolean notEmpty = c.moveToFirst();
        	if(notEmpty) {
        		setSerialID(c.getInt(READ_PROIRITYLIST_SERIALID_INDEX));
        		setSiteName(c.getString(READ_PROIRITYLIST_SITENAME_INDEX));
        		setPriority(c.getInt(READ_PROIRITYLIST_PRIORITY_INDEX));
        		setRid(0);
        	} else setRid(-1);
        }
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}
	
	public PriorityList insertPriorityList(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_NAME_SITENAME, getSiteName());
        initialValues.put(COLUMN_NAME_PRIORITY, getPriority());
        long rid = db.insert(TABLE_NAME, null, initialValues);
        setRid(rid);
//        db.close();
        closedb(adapter);
		return this;
	}

	public PriorityList updatePriorityList(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_SITENAME, getSiteName());
        updateValues.put(COLUMN_NAME_PRIORITY,getPriority());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(TABLE_NAME, updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
//        db.close();
        closedb(adapter);
		return this;		
	}

	public PriorityList deletePriorityList(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SERIALID+"="+getSerialID();
		if(isDebug) Log.d(TAG,whereClause);
		int rid = db.delete(TABLE_NAME, whereClause, null);
		setRid(rid);
		if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
//		db.close();
        closedb(adapter);
		return this;
	}

	@Override
	public PriorityList doInsert(LikDBAdapter adapter) {
		return insertPriorityList(adapter);
	}

	@Override
	public PriorityList doUpdate(LikDBAdapter adapter) {
		return updatePriorityList(adapter);
	}

	@Override
	public PriorityList doDelete(LikDBAdapter adapter) {
		return deletePriorityList(adapter);
	}

	@Override
	public PriorityList findByKey(LikDBAdapter adapter) {
		return getPriorityListByPrimaryKey(adapter);
	}

	@Override
	public PriorityList queryBySerialID(LikDBAdapter adapter) {
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
				READ_PROIRITYLIST_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null) {
        	boolean notEmpty = c.moveToFirst();
        	if(notEmpty) {
        		setSerialID(c.getInt(READ_PROIRITYLIST_SERIALID_INDEX));
        		setSiteName(c.getString(READ_PROIRITYLIST_SITENAME_INDEX));
        		setPriority(c.getInt(READ_PROIRITYLIST_PRIORITY_INDEX));
        		setRid(0);
        	} else setRid(-1);
        }
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

}
