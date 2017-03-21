package com.lik.android.om;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;

public class PhotoProjectState extends BasePhotoProjectState implements ProcessDownloadInterface{

	private static final long serialVersionUID = 4782007315882885398L;

	@Override
	public boolean processDownload(LikDBAdapter adapter,Map<String, String> detail, boolean isOnlyInsert) {
		Log.d(TAG,"processDownload Begin");
		boolean result = true;
		String flag = detail.get(FLAG_KEY);
		setCompanyID(Integer.parseInt(detail.get(COLUMN_NAME_COMPANYID)));
		setUserNO(detail.get(COLUMN_NAME_USERNO));
		setProjectID(Integer.parseInt(detail.get(COLUMN_NAME_PROJECTID)));
		
		if(!isOnlyInsert)
			findByKey(adapter);

		setViewOrder(Integer.parseInt(detail.get(COLUMN_NAME_VIEWORDER)));
		setStateID(Integer.parseInt(detail.get(COLUMN_NAME_STATEID)));
		setStateNO(Integer.parseInt(detail.get(COLUMN_NAME_STATENO)));
		setStateName(detail.get(COLUMN_NAME_STATENAME));
		setCustomerID(Integer.parseInt(detail.get(COLUMN_NAME_CUSTOMERID)));
		setKind(Integer.parseInt(detail.get(COLUMN_NAME_KIND)));
		
		try {
			if(detail.get(COLUMN_NAME_MAXDATE) != null) 
				setMaxDate(sdf2.parse(detail.get(COLUMN_NAME_MAXDATE)));
		} catch (ParseException e) {
			Log.e(TAG,e.getMessage());
		}

		if(isOnlyInsert) 
			doInsert(adapter);
		else 
		{
			if(getRid()<0) 
				doInsert(adapter);
			else 
			{
				if(flag.equals(FLAG_DELETE)) 
					doDelete(adapter);
				else 
					doUpdate(adapter);
			}
		}
		if(getRid()<0) result = false;
		Log.d(TAG,"processDownload End");
		return result;
	}


	@Override
	public PhotoProjectState doInsert(LikDBAdapter adapter) {
	      getdb(adapter);
	      Log.d(TAG,"doInsert Begin="+getTableName());
		  InsertHelper ih = adapter.getInsertHelper(getTableName());
		   Log.d(TAG,"test123");
		  ih.prepareForInsert();
	      ih.bind(2, getUserNO());
		  ih.bind(3, getCompanyID());
	      ih.bind(4, getProjectID());
	      ih.bind(5, getViewOrder());
	      ih.bind(6, getStateID());
	      ih.bind(7, getStateNO());
	      ih.bind(8 ,getStateName());
	      ih.bind(9, getCustomerID());
	      ih.bind(10, getMaxDate()==null?null:sdf2.format(getMaxDate()));
	      ih.bind(11, getKind());
	      Log.d(TAG,"abc="+ih.toString());
	      long rid = ih.execute();
	      Log.d(TAG,"rid="+rid);
	      if(rid != -1) setRid(0);
	      Log.d(TAG,"doInsert End");
		 return this;
	}

	@Override
	public PhotoProjectState doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_COMPANYID, getCompanyID());
        updateValues.put(COLUMN_NAME_USERNO, getUserNO());
        updateValues.put(COLUMN_NAME_PROJECTID, getProjectID());
        updateValues.put(COLUMN_NAME_VIEWORDER, getViewOrder());
        updateValues.put(COLUMN_NAME_STATEID, getStateID());
        updateValues.put(COLUMN_NAME_STATENO, getStateNO());
        updateValues.put(COLUMN_NAME_MAXDATE, getMaxDate()==null?null:sdf.format(getMaxDate()));
        updateValues.put(COLUMN_NAME_STATENAME, getStateName());
        updateValues.put(COLUMN_NAME_CUSTOMERID, getCustomerID());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(getTableName(), updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update時，回覆若為0表示沒有刪除一筆資料，因此設-1表示失敗
        closedb(adapter);
		return this;		
	}

	@Override
	public PhotoProjectState doDelete(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SERIALID+"="+getSerialID();
		if(isDebug) Log.d(TAG,whereClause);
		//String[] whereArgs = {String.valueOf(getSerialID())};
		int rid = db.delete(getTableName(), whereClause, null);
        setRid(rid);
		if(rid==0) setRid(-1);// delete時，回覆若為0表示沒有刪除一筆資料，因此設-1表示失敗
        closedb(adapter);
		return this;
	}

	@Override
	public PhotoProjectState findByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_USERNO+"='"+getUserNO()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_CUSTOMERID+"='"+getCustomerID()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_PROJECTID+"='"+getProjectID()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PHOTOPROJECTSTATE_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_PHOTOPROJECT_SERIALID_INDEX));
        	setCompanyID(c.getInt(READ_PHOTOPROJECT_COMPANYID_INDEX));
            setUserNO(c.getString(READ_PHOTOPROJECT_USERNO_INDEX));
            setProjectID(c.getInt(READ_PHOTOPROJECT_PROJECTID_INDEX));
            setViewOrder(c.getInt(READ_PHOTOPROJECT_VIEWORDER_INDEX));
            setStateID(c.getInt(READ_PHOTOPROJECT_STATEID_INDEX));
            setStateName(c.getString(READ_PHOTOPROJECT_STATENAME_INDEX));
            if(c.getString(READ_PHOTOPROJECT_MAXDATE_INDEX)!=null) {
	            try {
	            	Date date = sdf.parse(c.getString(READ_PHOTOPROJECT_MAXDATE_INDEX));
	            	setMaxDate(date);
	            } catch(ParseException pe) {
	            	setMaxDate(null);
	            }
            }
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public PhotoProjectState queryBySerialID(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_SERIALID+"="+getSerialID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PHOTOPROJECTSTATE_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_PHOTOPROJECT_SERIALID_INDEX));
        	setCompanyID(c.getInt(READ_PHOTOPROJECT_COMPANYID_INDEX));
            setUserNO(c.getString(READ_PHOTOPROJECT_USERNO_INDEX));
            setProjectID(c.getInt(READ_PHOTOPROJECT_PROJECTID_INDEX));
            setViewOrder(c.getInt(READ_PHOTOPROJECT_VIEWORDER_INDEX));
            setStateID(c.getInt(READ_PHOTOPROJECT_STATEID_INDEX));
            setStateName(c.getString(READ_PHOTOPROJECT_STATENAME_INDEX));
            setKind(c.getInt(READ_PHOTOPROJECT_KIND_INDEX));
            if(c.getString(READ_PHOTOPROJECT_MAXDATE_INDEX)!=null) {
	            try {
	            	Date date = sdf.parse(c.getString(READ_PHOTOPROJECT_MAXDATE_INDEX));
	            	setMaxDate(date);
	            } catch(ParseException pe) {
	            	setMaxDate(null);
	            }
            }
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}

}
