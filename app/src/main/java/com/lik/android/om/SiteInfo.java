package com.lik.android.om;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;

public class SiteInfo extends BaseSiteInfo {

	private static final long serialVersionUID = 7537603049020195366L;
	
	public SiteInfo getSiteInfoBySiteName(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_SITENAME+"='"+getSiteName()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SYSPROFILE_PROJECTION,    // The columns to return from the query
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
        	setSiteName(c.getString(READ_SITEINFO_SITENAME_INDEX));
        	setParent(c.getString(READ_SITEINFO_COMPANYID_INDEX));
        	setType(c.getString(READ_SITEINFO_SYSTEMNO_INDEX));
        	setRid(0);
        } else setRid(-1);

        c.close();
        closedb(adapter);
		return this;
	}
	
	public List<SiteInfo> getAllSiteInfo(LikDBAdapter adapter) {
		
		List<SiteInfo> result = new ArrayList<SiteInfo>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		//qb.appendWhere(COLUMN_NAME_SITENAME+"='"+getSiteName()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SYSPROFILE_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);

        boolean notEmpty = c.moveToFirst();
        if(c != null && c.getCount()>0) {
			c.moveToFirst();
            do {
            	SiteInfo om = new SiteInfo();
            	om.setSiteName(c.getString(READ_SITEINFO_SITENAME_INDEX));
            	om.setParent(c.getString(READ_SITEINFO_COMPANYID_INDEX));
            	om.setType(c.getString(READ_SITEINFO_SYSTEMNO_INDEX));
            	om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
    		c.close();
		} 		

        closedb(adapter);
		return result;
	}
	
	public SiteInfo insertSiteInfo(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_NAME_SITENAME, getSiteName());
        initialValues.put(COLUMN_NAME_PARENT, getParent());
        initialValues.put(COLUMN_NAME_TYPE, getType());
        long rid = db.insert(TABLE_NAME, null, initialValues);
        setRid(rid);
//        db.close();
        closedb(adapter);
		return this;
	}

	public SiteInfo updateSiteInfo(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_PARENT, getParent());
        updateValues.put(COLUMN_NAME_TYPE, getType());
        long rid = db.update(
        		TABLE_NAME, 
        		updateValues, 
        		COLUMN_NAME_SITENAME+"='"+getSiteName()+"'", 
        		null);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
//        db.close();
        closedb(adapter);
		return this;		
	}
	
	public SiteInfo deleteSiteInfo(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SITENAME+"='"+getSiteName()+"'";
		if(isDebug) Log.d(TAG,whereClause);
		//String[] whereArgs = {String.valueOf(getSerialID())};
		int rid = db.delete(TABLE_NAME, null, null);
		setRid(rid);
		if(rid==0) setRid(-1); 
//		db.close();
        closedb(adapter);
		return this;
	}

	@Override
	public SiteInfo doInsert(LikDBAdapter adapter) {
		return insertSiteInfo(adapter);
	}

	@Override
	public SiteInfo doUpdate(LikDBAdapter adapter) {
		return updateSiteInfo(adapter);
	}

	@Override
	public SiteInfo doDelete(LikDBAdapter adapter) {
		return deleteSiteInfo(adapter);
	}

	@Override
	public SiteInfo findByKey(LikDBAdapter adapter) {
		return getSiteInfoBySiteName(adapter);
	}

	@Override
	public SiteInfo queryBySerialID(LikDBAdapter adapter) {
		return getSiteInfoBySiteName(adapter);
	}

}
