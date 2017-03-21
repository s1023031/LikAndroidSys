package com.lik.android.om;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.lik.android.main.LikDBAdapter;

public class ConfigControl extends BaseConfigControl{

	private static final long serialVersionUID = -1270236431914542383L;
	
	/**
	 * 
	 * @param adapter
	 * @return
	 */
	public ArrayList<ConfigControl> getConfigControl(LikDBAdapter adapter) {
		
		ArrayList<ConfigControl> al = new ArrayList<ConfigControl>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);

		Cursor c = qb.query(
				db,            // The database to query
				READ_CONFIGCONTROL_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	ConfigControl om = new ConfigControl();
            	om.setSerialID(c.getInt(READ_CONFIGCONTROL_SERIALID_INDEX));
            	om.setLoginFlag(c.getString(READ_CONFIGCONTROL_COMPANYID_INDEX));
                al.add(om);
            } while(c.moveToNext());
        }
        c.close();
        closedb(adapter);
		return al;
	}
	
	public ConfigControl getConfigControlByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);

		Cursor c = qb.query(
				db,            // The database to query
				READ_CONFIGCONTROL_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_CONFIGCONTROL_SERIALID_INDEX));
        	setLoginFlag(c.getString(READ_CONFIGCONTROL_COMPANYID_INDEX));
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}

	public ConfigControl insertConfigControl(LikDBAdapter adapter) {
		InsertHelper ih = adapter.getInsertHelper(ConfigControl.TABLE_NAME);
	    ih.prepareForInsert();
		ih.bind(2, getLoginFlag());
  	    long rid = ih.execute();
        if(rid != -1) setRid(0);
		return this;
	}
	
	public ConfigControl updateConfigControl(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();

        updateValues.put(COLUMN_NAME_SERIALID, getSerialID());
        updateValues.put(COLUMN_NAME_LOGINFLAG, getLoginFlag());
   
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(TABLE_NAME, updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); 
        closedb(adapter);
		return this;		
	}
	
	
	@Override
	public ConfigControl doInsert(LikDBAdapter adapter) {
		return insertConfigControl(adapter);
	}

	@Override
	public ConfigControl doUpdate(LikDBAdapter adapter) {
		return updateConfigControl(adapter);
	}

	@Override
	public ConfigControl doDelete(LikDBAdapter adapter) {
		return null;
	}

	@Override
	public ConfigControl findByKey(LikDBAdapter adapter) {
		return getConfigControlByKey(adapter);
	}

	@Override
	public ConfigControl queryBySerialID(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_SERIALID+"="+getSerialID());

		Cursor c = qb.query(
				db,            // The database to query
				READ_CONFIGCONTROL_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_CONFIGCONTROL_SERIALID_INDEX));
        	setLoginFlag(c.getString(READ_CONFIGCONTROL_COMPANYID_INDEX));
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}

}
