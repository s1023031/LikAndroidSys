package com.lik.android.om;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import com.lik.android.main.LikDBAdapter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class SysProfile extends BaseSysProfile {

	private static final long serialVersionUID = -7737102102580776294L;

	/**
	 * 
	 * @param adapter
	 * @return
	 */
	public ArrayList<SysProfile> getAllSysProfile(LikDBAdapter adapter) {
		
		ArrayList<SysProfile> al = new ArrayList<SysProfile>();
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
				READ_SYSPROFILE_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	SysProfile om = new SysProfile();
            	om.setSerialID(c.getInt(READ_SYSPROFILE_SERIALID_INDEX));
                om.setCompanyNo(c.getString(READ_SYSPROFILE_COMPANYNO_INDEX));
                om.setSystemNo(c.getString(READ_SYSPROFILE_SYSTEMNO_INDEX));
                om.setPdaId(c.getInt(READ_SYSPROFILE_PDAID_INDEX));
                om.setVersionInfo(c.getString(READ_SYSPROFILE_VERSIONINFO_INDEX));
                om.setStockInfo(c.getString(READ_SYSPROFILE_STOCKINFO_INDEX));
                om.setCameraInfo(c.getString(READ_SYSPROFILE_CAMERAINFO_INDEX));
                om.setTelephoneInfo(c.getString(READ_SYSPROFILE_TELEPHONEINFO_INDEX));
                om.setMapInfo(c.getString(READ_SYSPROFILE_MAPINFO_INDEX));
                om.setMapTrackerInfo(c.getString(READ_SYSPROFILE_MAPTRACKERINFO_INDEX));
                om.setHistoryPeriod(c.getInt(READ_SYSPROFILE_HISTORYPERIOD_INDEX));
                om.setButtonAlign(c.getString(READ_SYSPROFILE_BUTTONALIGN_INDEX));
                om.setDownLoadMinute(c.getInt(READ_SYSPROFILE_DOWNLOADMINUTE_INDEX));
                om.setInstantMessengerInfo(c.getString(READ_SYSPROFILE_INSTANTMESSENGERINFO_INDEX));
                try {
                	Date lastModifiedDate = sdf.parse(c.getString(READ_SYSPROFILE_LASTMODIFIEDDATE_INDEX));
                	om.setLastModifiedDate(lastModifiedDate);
                } catch(ParseException pe) {
                	om.setLastModifiedDate(null);
                }
                om.setWebIsSecure(c.getString(READ_SYSPROFILE_WEBISSECURE_INDEX));
                om.setWebURL(c.getString(READ_SYSPROFILE_WEBURL_INDEX));
                om.setQueuePort(c.getInt(READ_SYSPROFILE_QUEUEPORT_INDEX));
                om.setCompanyName(c.getString(READ_SYSPROFILE_COMPANYNAME_INDEX));
            	al.add(om);
            } while(c.moveToNext());
        }
//        db.close();
        c.close();
        closedb(adapter);
		return al;
	}
	
	/**
	 * key = CompanyID & SystemNo
	 * @param adapter
	 * @return
	 */
	public SysProfile getSysProfileByPrimaryKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYNO+"='"+getCompanyNo()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_SYSTEMNO+"='"+getSystemNo()+"'");
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
        	setSerialID(c.getInt(READ_SYSPROFILE_SERIALID_INDEX));
        	setCompanyNo(c.getString(READ_SYSPROFILE_COMPANYNO_INDEX));
        	setSystemNo(c.getString(READ_SYSPROFILE_SYSTEMNO_INDEX));
        	setPdaId(c.getInt(READ_SYSPROFILE_PDAID_INDEX));
            setVersionInfo(c.getString(READ_SYSPROFILE_VERSIONINFO_INDEX));
            setStockInfo(c.getString(READ_SYSPROFILE_STOCKINFO_INDEX));
            setCameraInfo(c.getString(READ_SYSPROFILE_CAMERAINFO_INDEX));
            setTelephoneInfo(c.getString(READ_SYSPROFILE_TELEPHONEINFO_INDEX));
            setMapInfo(c.getString(READ_SYSPROFILE_MAPINFO_INDEX));
            setMapTrackerInfo(c.getString(READ_SYSPROFILE_MAPTRACKERINFO_INDEX));
            setHistoryPeriod(c.getInt(READ_SYSPROFILE_HISTORYPERIOD_INDEX));
            setButtonAlign(c.getString(READ_SYSPROFILE_BUTTONALIGN_INDEX));
            setDownLoadMinute(c.getInt(READ_SYSPROFILE_DOWNLOADMINUTE_INDEX));
            setInstantMessengerInfo(c.getString(READ_SYSPROFILE_INSTANTMESSENGERINFO_INDEX));
            try {
            	Date lastModifiedDate = sdf.parse(c.getString(READ_SYSPROFILE_LASTMODIFIEDDATE_INDEX));
            	setLastModifiedDate(lastModifiedDate);
            } catch(ParseException pe) {
            	setLastModifiedDate(null);
            }
            setWebIsSecure(c.getString(READ_SYSPROFILE_WEBISSECURE_INDEX));
            setWebURL(c.getString(READ_SYSPROFILE_WEBURL_INDEX));
            setQueuePort(c.getInt(READ_SYSPROFILE_QUEUEPORT_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

	public SysProfile insertSysProfile(LikDBAdapter adapter) {
		Log.d(TAG,"insertSysProfile start");
		SQLiteDatabase db = getdb(adapter);
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_NAME_COMPANYNO, getCompanyNo());
        initialValues.put(COLUMN_NAME_SYSTEMNO, getSystemNo());
        initialValues.put(COLUMN_NAME_PDAID, getPdaId());
        initialValues.put(COLUMN_NAME_VERSIONINFO, getVersionInfo());
        initialValues.put(COLUMN_NAME_STOCKINFO, getStockInfo());
        initialValues.put(COLUMN_NAME_CAMERAINFO, getCameraInfo());
        initialValues.put(COLUMN_NAME_TELEPHONEINFO, getTelephoneInfo());
        initialValues.put(COLUMN_NAME_MAPINFO, getMapInfo());
        initialValues.put(COLUMN_NAME_MAPTRACKERINFO, getMapTrackerInfo());
        initialValues.put(COLUMN_NAME_HISTORYPERIOD, getHistoryPeriod());
        initialValues.put(COLUMN_NAME_BUTTONALIGN, getButtonAlign());
        initialValues.put(COLUMN_NAME_DOWNLOADMINUTE, getDownLoadMinute());
        initialValues.put(COLUMN_NAME_INSTANTMESSENGERINFO, getInstantMessengerInfo());
        initialValues.put(COLUMN_NAME_LASTMODIFIEDDATE, sdf.format(getLastModifiedDate()));
        initialValues.put(COLUMN_NAME_WEBISSECURE, getWebIsSecure());
        initialValues.put(COLUMN_NAME_WEBURL, getWebURL());
        initialValues.put(COLUMN_NAME_QUEUEPORT, getQueuePort());
        Log.d(TAG,"getCompanyName="+getCompanyName());
        initialValues.put(COLUMN_NAME_COMPANYNAME, getCompanyName());
        long rid = db.insert(TABLE_NAME, null, initialValues);
        setRid(rid);
//        db.close();
        closedb(adapter);
		return this;
	}

	public SysProfile updateSysProfile(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_PDAID, getPdaId());
        updateValues.put(COLUMN_NAME_VERSIONINFO, getVersionInfo());
        updateValues.put(COLUMN_NAME_STOCKINFO, getStockInfo());
        updateValues.put(COLUMN_NAME_CAMERAINFO, getCameraInfo());
        updateValues.put(COLUMN_NAME_TELEPHONEINFO, getTelephoneInfo());
        updateValues.put(COLUMN_NAME_MAPINFO, getMapInfo());
        updateValues.put(COLUMN_NAME_MAPTRACKERINFO, getMapTrackerInfo());
        updateValues.put(COLUMN_NAME_HISTORYPERIOD, getHistoryPeriod());
        updateValues.put(COLUMN_NAME_BUTTONALIGN, getButtonAlign());
        updateValues.put(COLUMN_NAME_DOWNLOADMINUTE, getDownLoadMinute());
        updateValues.put(COLUMN_NAME_INSTANTMESSENGERINFO, getInstantMessengerInfo());
        updateValues.put(COLUMN_NAME_LASTMODIFIEDDATE, sdf.format(getLastModifiedDate()));
        updateValues.put(COLUMN_NAME_WEBISSECURE, getWebIsSecure());
        updateValues.put(COLUMN_NAME_WEBURL, getWebURL());
        updateValues.put(COLUMN_NAME_QUEUEPORT, getQueuePort());
        updateValues.put(COLUMN_NAME_COMPANYNAME, getCompanyName());
        long rid = db.update(
        		TABLE_NAME, 
        		updateValues, 
        		COLUMN_NAME_COMPANYNO+"='"+getCompanyNo()+"' and "+COLUMN_NAME_SYSTEMNO+"='"+getSystemNo()+"'", 
        		null);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
//        db.close();
        closedb(adapter);
		return this;		
	}
	
	public SysProfile deleteSysProfile(LikDBAdapter adapter) {
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
	public SysProfile doInsert(LikDBAdapter adapter) {
		return insertSysProfile(adapter);
	}

	@Override
	public SysProfile doUpdate(LikDBAdapter adapter) {
		return updateSysProfile(adapter);
	}

	@Override
	public SysProfile doDelete(LikDBAdapter adapter) {
		return deleteSysProfile(adapter);
	}

	@Override
	public SysProfile findByKey(LikDBAdapter adapter) {
		return getSysProfileByPrimaryKey(adapter);
	}

	@Override
	public SysProfile queryBySerialID(LikDBAdapter adapter) {
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
        	setSerialID(c.getInt(READ_SYSPROFILE_SERIALID_INDEX));
        	setCompanyNo(c.getString(READ_SYSPROFILE_COMPANYNO_INDEX));
        	setSystemNo(c.getString(READ_SYSPROFILE_SYSTEMNO_INDEX));
        	setPdaId(c.getInt(READ_SYSPROFILE_PDAID_INDEX));
            setVersionInfo(c.getString(READ_SYSPROFILE_VERSIONINFO_INDEX));
            setStockInfo(c.getString(READ_SYSPROFILE_STOCKINFO_INDEX));
            setCameraInfo(c.getString(READ_SYSPROFILE_CAMERAINFO_INDEX));
            setTelephoneInfo(c.getString(READ_SYSPROFILE_TELEPHONEINFO_INDEX));
            setMapInfo(c.getString(READ_SYSPROFILE_MAPINFO_INDEX));
            setMapTrackerInfo(c.getString(READ_SYSPROFILE_MAPTRACKERINFO_INDEX));
            setHistoryPeriod(c.getInt(READ_SYSPROFILE_HISTORYPERIOD_INDEX));
            setButtonAlign(c.getString(READ_SYSPROFILE_BUTTONALIGN_INDEX));
            setDownLoadMinute(c.getInt(READ_SYSPROFILE_DOWNLOADMINUTE_INDEX));
            setInstantMessengerInfo(c.getString(READ_SYSPROFILE_INSTANTMESSENGERINFO_INDEX));
            try {
            	Date lastModifiedDate = sdf.parse(c.getString(READ_SYSPROFILE_LASTMODIFIEDDATE_INDEX));
            	setLastModifiedDate(lastModifiedDate);
            } catch(ParseException pe) {
            	setLastModifiedDate(null);
            }
            setWebIsSecure(c.getString(READ_SYSPROFILE_WEBISSECURE_INDEX));
            setWebURL(c.getString(READ_SYSPROFILE_WEBURL_INDEX));
            setQueuePort(c.getInt(READ_SYSPROFILE_QUEUEPORT_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}
	
	public boolean isCloud() {
		boolean result = false;
		if(getWebIsSecure()==null) 
			return result;
		if(getWebIsSecure().equals(WEBISSECURE_Y)) 
			result = true;
		return result;
	}
	
	public String getProtocolURL() {
		StringBuffer result = new StringBuffer();
		if(isCloud()) result.append("https://");
		else result.append("http://");
		result.append(getWebURL());
		return result.toString();
	}
	
	public int getWebPort() {
		int port = 80; // default
//		if(isCloud()) {
//			port = 443;
//		}
//		if(getWebURL()==null) return port;
//		// find port
//		int istart,iend;
//		istart = getWebURL().lastIndexOf(":");
//		if(istart!=-1) {
//			istart++;
//			iend = getWebURL().substring(istart).indexOf("/");
//			if(iend!=-1) {
//				try {
//					iend += istart;
//					String s = getWebURL().substring(istart, iend);
//					Log.d(TAG,"istart="+istart+",iend="+iend+",s="+s);
//					port = Integer.parseInt(s);
//				} catch(NumberFormatException e) {
//					// do nothing
//				}
//			}
//		}
		try {
			URL url = new URL(getProtocolURL());
			port = url.getPort();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Log.d(TAG,"port="+port);
		return port;
	}
	
	public String getWebIp() {
		String result = null;
		try {
			URL url = new URL(getProtocolURL());
			result = url.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
