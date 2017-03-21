package com.lik.android.om;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;

public class SiteIPList extends BaseSiteIPList {

	private static final long serialVersionUID = -300699138486674182L;

	public ArrayList<SiteIPList> getSiteIPListBySiteName(LikDBAdapter adapter) {
        ArrayList<SiteIPList> al = new ArrayList<SiteIPList>();
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
				READ_SITEIPLIST_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	SiteIPList om = new SiteIPList();
            	om.setSerialID(c.getInt(READ_SITEIPLIST_SERIALID_INDEX));
                om.setIp(c.getString(READ_SITEIPLIST_IP_INDEX));
                om.setSiteName(c.getString(READ_SITEIPLIST_SITENAME_INDEX));
                om.setType(c.getString(READ_SITEIPLIST_TYPE_INDEX));
                om.setWebPort(c.getInt(READ_SITEIPLIST_WEBPORT_INDEX));
                om.setQueuePort(c.getInt(READ_SITEIPLIST_UEUEPORT_INDEX));
            	al.add(om);
            } while(c.moveToNext());
        }
//        db.close();
        c.close();
        closedb(adapter);
		return al;
	}

	public ArrayList<SiteIPList> getSiteIPListBySiteNameAndType(LikDBAdapter adapter) {
        ArrayList<SiteIPList> al = new ArrayList<SiteIPList>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_SITENAME+"='"+getSiteName()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_TYPE+"='"+getType()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_COMPANYPARENT+"='"+getCompanyParent()+"'");
		
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SITEIPLIST_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	SiteIPList om = new SiteIPList();
            	om.setSerialID(c.getInt(READ_SITEIPLIST_SERIALID_INDEX));
                om.setIp(c.getString(READ_SITEIPLIST_IP_INDEX));
                om.setSiteName(c.getString(READ_SITEIPLIST_SITENAME_INDEX));
                om.setType(c.getString(READ_SITEIPLIST_TYPE_INDEX));
                om.setWebPort(c.getInt(READ_SITEIPLIST_WEBPORT_INDEX));
                om.setQueuePort(c.getInt(READ_SITEIPLIST_UEUEPORT_INDEX));
                om.setCompanyParent(c.getString(READ_SITEIPLIST_COMPANYPARENT_INDEX));
            	al.add(om);
            } while(c.moveToNext());
        }
        c.close();
        closedb(adapter);
		return al;
	}

	public SiteIPList getSiteIPListBySiteNameAndIPAndType(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_SITENAME+"='"+getSiteName()+"'");
		//qb.appendWhere(" and "+COLUMN_NAME_IP+"='"+getIp()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_TYPE+"='"+getType()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_COMPANYPARENT+"='"+getCompanyParent()+"'");
		
	
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SITEIPLIST_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
        	boolean notEmpty = c.moveToFirst();
            if(notEmpty) {
            	setSerialID(c.getInt(READ_SITEIPLIST_SERIALID_INDEX));
                setIp(c.getString(READ_SITEIPLIST_IP_INDEX));
                setSiteName(c.getString(READ_SITEIPLIST_SITENAME_INDEX));
                setType(c.getString(READ_SITEIPLIST_TYPE_INDEX));
                setWebPort(c.getInt(READ_SITEIPLIST_WEBPORT_INDEX));
                setQueuePort(c.getInt(READ_SITEIPLIST_UEUEPORT_INDEX));
                setCompanyParent(c.getString(READ_SITEIPLIST_COMPANYPARENT_INDEX));
                setRid(0);
            } else setRid(-1);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}

	public SiteIPList insertSiteIP(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_NAME_IP, getIp());
        initialValues.put(COLUMN_NAME_SITENAME, getSiteName());
        initialValues.put(COLUMN_NAME_TYPE, getType());
        initialValues.put(COLUMN_NAME_WEBPORT, getWebPort());
        initialValues.put(COLUMN_NAME_QUEUEPORT, getQueuePort());
        initialValues.put(COLUMN_NAME_COMPANYPARENT, getCompanyParent());
        long rid = db.insert(TABLE_NAME, null, initialValues);
        setRid(rid);
        closedb(adapter);
		return this;
	}

	public SiteIPList updateSiteIP(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        String whereStatement = COLUMN_NAME_COMPANYPARENT+"='"+getCompanyParent()+
        										 "' and "+COLUMN_NAME_TYPE+"='"+getType()+"'";
        updateValues.put(COLUMN_NAME_IP, getIp());
      //  updateValues.put(COLUMN_NAME_SITENAME, getSiteName());
       // updateValues.put(COLUMN_NAME_TYPE, getType());
        updateValues.put(COLUMN_NAME_WEBPORT, getWebPort());
        updateValues.put(COLUMN_NAME_QUEUEPORT, getQueuePort());
        long rid = db.update(TABLE_NAME, updateValues, whereStatement, null);
        setRid(rid);
        if(rid==0) setRid(-1); 
        closedb(adapter);
		return this;		
	}
	
	public SiteIPList deleteSiteInfo(LikDBAdapter adapter) {
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
	public SiteIPList doInsert(LikDBAdapter adapter) {
		return insertSiteIP(adapter);
	}

	@Override
	public SiteIPList doUpdate(LikDBAdapter adapter) {
		return updateSiteIP(adapter);
	}

	@Override
	public SiteIPList doDelete(LikDBAdapter adapter) {
		return deleteSiteInfo(adapter);
	}

	@Override
	public SiteIPList findByKey(LikDBAdapter adapter) {
		return getSiteIPListBySiteNameAndIPAndType(adapter);
	}

	@Override
	public SiteIPList queryBySerialID(LikDBAdapter adapter) {
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
				READ_SITEIPLIST_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
        	boolean notEmpty = c.moveToFirst();
            if(notEmpty) {
            	setSerialID(c.getInt(READ_SITEIPLIST_SERIALID_INDEX));
                setIp(c.getString(READ_SITEIPLIST_IP_INDEX));
                setSiteName(c.getString(READ_SITEIPLIST_SITENAME_INDEX));
                setType(c.getString(READ_SITEIPLIST_TYPE_INDEX));
                setWebPort(c.getInt(READ_SITEIPLIST_WEBPORT_INDEX));
                setQueuePort(c.getInt(READ_SITEIPLIST_UEUEPORT_INDEX));
                setRid(0);
            } else setRid(-1);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

}
