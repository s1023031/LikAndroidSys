package com.lik.android.om;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;

public class Settings extends BaseSettings {

	private static final long serialVersionUID = -8868886971488834222L;

	@Override
	public Settings doInsert(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_NAME_TAG, getTag());
        initialValues.put(COLUMN_NAME_COLUMNNO, getColumnNO());
        initialValues.put(COLUMN_NAME_WIDTHWALUE, getWidthValue());
        initialValues.put(COLUMN_NAME_SEQUENCE, getSequence());
        long rid = db.insert(TABLE_NAME, null, initialValues);
        setRid(rid);
//        db.close();
        closedb(adapter);
		return this;
	}

	@Override
	public Settings doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_WIDTHWALUE, getWidthValue());
        updateValues.put(COLUMN_NAME_SEQUENCE, getSequence());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(TABLE_NAME, updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
//        db.close();
        closedb(adapter);
		return this;		
	}

	@Override
	public Settings doDelete(LikDBAdapter adapter) {
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
	public Settings findByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_TAG+"='"+getTag()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_COLUMNNO+"="+getColumnNO());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SETTINGS_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_SETTINGS_SERIALID_INDEX));
        	setTag(c.getString(READ_SETTINGS_TAG_INDEX));
        	setColumnNO(c.getInt(READ_SETTINGS_COLUMNNO_INDEX));
        	setWidthValue(c.getInt(READ_SETTINGS_WIDTHWALUE_INDEX));
        	setSequence(c.getInt(READ_SETTINGS_SEQUENCE_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public Settings queryBySerialID(LikDBAdapter adapter) {
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
				READ_SETTINGS_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_SETTINGS_SERIALID_INDEX));
        	setTag(c.getString(READ_SETTINGS_TAG_INDEX));
        	setColumnNO(c.getInt(READ_SETTINGS_COLUMNNO_INDEX));
        	setWidthValue(c.getInt(READ_SETTINGS_WIDTHWALUE_INDEX));
        	setSequence(c.getInt(READ_SETTINGS_SEQUENCE_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}
	
	public List<Settings> getSettingsByTag(LikDBAdapter adapter) {
		List<Settings> result = new ArrayList<Settings>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_TAG+"='"+getTag()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SETTINGS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				COLUMN_NAME_COLUMNNO+" asc"        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	Settings om = new Settings();
            	om.setSerialID(c.getInt(READ_SETTINGS_SERIALID_INDEX));
            	om.setTag(c.getString(READ_SETTINGS_TAG_INDEX));
            	om.setColumnNO(c.getInt(READ_SETTINGS_COLUMNNO_INDEX));
                om.setWidthValue(c.getInt(READ_SETTINGS_WIDTHWALUE_INDEX));
            	om.setSequence(c.getInt(READ_SETTINGS_SEQUENCE_INDEX));
                om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
        }
//        db.close();
        c.close();
        closedb(adapter);
		return result;		
	}
	
	public Settings doDeleteByTag(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_TAG+"='"+getTag()+"'";
		if(isDebug) Log.d(TAG,whereClause);
		//String[] whereArgs = {String.valueOf(getSerialID())};
		int rid = db.delete(TABLE_NAME, whereClause, null);
        setRid(rid);
		if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
        closedb(adapter);
		return this;
	}


}
