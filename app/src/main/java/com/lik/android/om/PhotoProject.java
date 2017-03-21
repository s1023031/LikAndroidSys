package com.lik.android.om;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;

public class PhotoProject extends BasePhotoProject implements ProcessDownloadInterface {

	private static final long serialVersionUID = -4039225768734276931L;

	@Override
	public boolean processDownload(LikDBAdapter adapter, Map<String, String> detail, boolean isOnlyInsert) {
		boolean result = true;
		String flag = detail.get(FLAG_KEY);
		setCompanyID(Integer.parseInt(detail.get(COLUMN_NAME_COMPANYID)));
		setUserNO(detail.get(COLUMN_NAME_USERNO));
		setYearMonth(detail.get(COLUMN_NAME_YEARMONTH));
		setName(detail.get(COLUMN_NAME_NAME).trim()); 
		if(!isOnlyInsert) 
			findByKey(adapter);
		setSupplierNO(detail.get(COLUMN_NAME_SUPPLIERNO));
		setSupplierNM(detail.get(COLUMN_NAME_SUPPLIERNM));
		setRemark(detail.get(COLUMN_NAME_REMARK));
		setSalesNo(detail.get(COLUMN_NAME_SALESNO));
		setSalesName(detail.get(COLUMN_NAME_SALESNAME));
		setCustomerID(Integer.parseInt(detail.get(COLUMN_NAME_CUSTOMERID)));
		setProjectNO(detail.get(COLUMN_NAME_PROJECTNO));
		if(detail.get(COLUMN_NAME_COUNT)!=null) 
			setCount(Integer.parseInt(detail.get(COLUMN_NAME_COUNT)));
		try {
			if(detail.get(COLUMN_NAME_FINISHDATE) != null) 
				setFinishDate(sdf2.parse(detail.get(COLUMN_NAME_FINISHDATE)));
		} catch (ParseException e) {
			Log.e(TAG,e.getMessage());
		}
		if(detail.get(COLUMN_NAME_TAKEPHOTOID)!=null) 
			setTakePhotoID(Integer.parseInt(detail.get(COLUMN_NAME_TAKEPHOTOID)));
		setPhotoDisplay1(detail.get(COLUMN_NAME_PHOTODISPLAY1));
		setPhotoDisplay2(detail.get(COLUMN_NAME_PHOTODISPLAY2));
		setPhotoDisplay3(detail.get(COLUMN_NAME_PHOTODISPLAY3));
		setPhotoDisplay4(detail.get(COLUMN_NAME_PHOTODISPLAY4));
		setPhotoDisplay5(detail.get(COLUMN_NAME_PHOTODISPLAY5));
		setPhotoDisplay6(detail.get(COLUMN_NAME_PHOTODISPLAY6));
		setPhotoDisplay7(detail.get(COLUMN_NAME_PHOTODISPLAY7));
		setPhotoDisplay8(detail.get(COLUMN_NAME_PHOTODISPLAY8));
		
		if(isOnlyInsert) 
			doInsert(adapter);
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

	@Override
	public PhotoProject doInsert(LikDBAdapter adapter) {
		getdb(adapter);
		Log.d(TAG,"doInsert PhotoProject="+getTableName());
		  InsertHelper ih = adapter.getInsertHelper(getTableName());
		  ih.prepareForInsert();
	      ih.bind(2, getCompanyID());
	      ih.bind(3, getUserNO());
	      ih.bind(4, getYearMonth());
	      ih.bind(5, getName());
	      ih.bind(6, getSupplierNO());
	      ih.bind(7, getSupplierNM());
	      ih.bind(8, getRemark());
	      ih.bind(9, getSalesNo());
	      ih.bind(10, getSalesName());
	      ih.bind(11, getCount());
	      ih.bind(12, getFinishDate()==null?null:sdf.format(getFinishDate()));
	      ih.bind(13, getTakePhotoID());
	      ih.bind(14, getCustomerID());
	      ih.bind(15, getProjectNO());
	      ih.bind(16, getPhotoDisplay1());
	      ih.bind(17, getPhotoDisplay2());
	      ih.bind(18, getPhotoDisplay3());
	      ih.bind(19, getPhotoDisplay4());
	      ih.bind(20, getPhotoDisplay5());
	      ih.bind(21, getPhotoDisplay6());
	      ih.bind(22, getPhotoDisplay7());
	      ih.bind(23, getPhotoDisplay8());
	    long rid = ih.execute();
      if(rid != -1) setRid(0);
      Log.d(TAG,"doInsert End");
		return this;
	}

	@Override
	public PhotoProject doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_SUPPLIERNO, getSupplierNO());
        updateValues.put(COLUMN_NAME_SUPPLIERNM, getSupplierNM());
        updateValues.put(COLUMN_NAME_REMARK, getRemark());
        updateValues.put(COLUMN_NAME_SALESNO, getSalesNo());
        updateValues.put(COLUMN_NAME_SALESNAME, getSalesName());
        updateValues.put(COLUMN_NAME_COUNT, getCount());
        updateValues.put(COLUMN_NAME_FINISHDATE, getFinishDate()==null?null:sdf.format(getFinishDate()));
        updateValues.put(COLUMN_NAME_TAKEPHOTOID, getTakePhotoID());
        updateValues.put(COLUMN_NAME_CUSTOMERID, getCustomerID());
        updateValues.put(COLUMN_NAME_PROJECTNO, getProjectNO());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(getTableName(), updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update時，回覆若為0表示沒有刪除一筆資料，因此設-1表示失敗
        closedb(adapter);
		return this;		
	}

	@Override
	public PhotoProject doDelete(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SERIALID+"="+getSerialID();
		if(isDebug) Log.d(TAG,whereClause);
		//String[] whereArgs = {String.valueOf(getSerialID())};
		int rid = db.delete(getTableName(), whereClause, null);
        setRid(rid);
		if(rid==0) setRid(-1); // delete時，回覆若為0表示沒有刪除一筆資料，因此設-1表示失敗
        closedb(adapter);
		return this;
	}

	@Override
	public PhotoProject findByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_USERNO+"='"+getUserNO()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_YEARMONTH+"='"+getYearMonth()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_NAME+"='"+getName()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_SUPPLIERNO+"='"+getSupplierNO()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PHOTOPROJECT_PROJECTION,    // The columns to return from the query
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
        	setSupplierNO(c.getString(READ_PHOTOPROJECT_SUPPLIERNO_INDEX));
            setSupplierNM(c.getString(READ_PHOTOPROJECT_SUPPLIERNM_INDEX));
            setRemark(c.getString(READ_PHOTOPROJECT_REMARK_INDEX));
            setSalesNo(c.getString(READ_PHOTOPROJECT_SALESNO_INDEX));
            setSalesName(c.getString(READ_PHOTOPROJECT_SALESNAME_INDEX));
            setCount(c.getInt(READ_PHOTOPROJECT_COUNT_INDEX));
            if(c.getString(READ_PHOTOPROJECT_FINISHDATE_INDEX)!=null) {
	            try {
	            	Date date = sdf.parse(c.getString(READ_PHOTOPROJECT_FINISHDATE_INDEX));
	            	setFinishDate(date);
	            } catch(ParseException pe) {
	            	setFinishDate(null);
	            }
            }
            setTakePhotoID(c.getInt(READ_PHOTOPROJECT_TAKEPHOTOID_INDEX));
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public PhotoProject queryBySerialID(LikDBAdapter adapter) {
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
				READ_PHOTOPROJECT_PROJECTION,    // The columns to return from the query
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
          	setYearMonth(c.getString(READ_PHOTOPROJECT_YEARMONTH_INDEX));
        	setName(c.getString(READ_PHOTOPROJECT_NAME_INDEX));
        	setSupplierNO(c.getString(READ_PHOTOPROJECT_SUPPLIERNO_INDEX));
            setSupplierNM(c.getString(READ_PHOTOPROJECT_SUPPLIERNM_INDEX));
            setRemark(c.getString(READ_PHOTOPROJECT_REMARK_INDEX));
            setSalesNo(c.getString(READ_PHOTOPROJECT_SALESNO_INDEX));
            setSalesName(c.getString(READ_PHOTOPROJECT_SALESNAME_INDEX));
            setCount(c.getInt(READ_PHOTOPROJECT_COUNT_INDEX));
            if(c.getString(READ_PHOTOPROJECT_FINISHDATE_INDEX)!=null) {
	            try {
	            	Date date = sdf.parse(c.getString(READ_PHOTOPROJECT_FINISHDATE_INDEX));
	            	setFinishDate(date);
	            } catch(ParseException pe) {
	            	setFinishDate(null);
	            }
            }
            setTakePhotoID(c.getInt(READ_PHOTOPROJECT_TAKEPHOTOID_INDEX));
            setPhotoDisplay1(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY1_INDEX));
            setPhotoDisplay2(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY2_INDEX));
            setPhotoDisplay3(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY3_INDEX));
            setPhotoDisplay4(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY4_INDEX));
            setPhotoDisplay5(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY5_INDEX));
            setPhotoDisplay6(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY6_INDEX));
            setPhotoDisplay7(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY7_INDEX));
            setPhotoDisplay8(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY8_INDEX));
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}
	
	public PhotoProject getPhotoProjectDisplay(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_USERNO+"='"+getUserNO()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_PROJECTNO+"='"+getProjectNO()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PHOTOPROJECT_PROJECTION,    // The columns to return from the query
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
          	setYearMonth(c.getString(READ_PHOTOPROJECT_YEARMONTH_INDEX));
        	setName(c.getString(READ_PHOTOPROJECT_NAME_INDEX));
        	setSupplierNO(c.getString(READ_PHOTOPROJECT_SUPPLIERNO_INDEX));
            setSupplierNM(c.getString(READ_PHOTOPROJECT_SUPPLIERNM_INDEX));
            setRemark(c.getString(READ_PHOTOPROJECT_REMARK_INDEX));
            setSalesNo(c.getString(READ_PHOTOPROJECT_SALESNO_INDEX));
            setSalesName(c.getString(READ_PHOTOPROJECT_SALESNAME_INDEX));
            setCount(c.getInt(READ_PHOTOPROJECT_COUNT_INDEX));
            if(c.getString(READ_PHOTOPROJECT_FINISHDATE_INDEX)!=null) {
	            try {
	            	Date date = sdf.parse(c.getString(READ_PHOTOPROJECT_FINISHDATE_INDEX));
	            	setFinishDate(date);
	            } catch(ParseException pe) {
	            	setFinishDate(null);
	            }
            }
            setProjectNO(c.getString(READ_PHOTOPROJECT_PROJECTNO_INDEX));
            setTakePhotoID(c.getInt(READ_PHOTOPROJECT_TAKEPHOTOID_INDEX));
            setPhotoDisplay1(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY1_INDEX));
            setPhotoDisplay2(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY2_INDEX));
            setPhotoDisplay3(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY3_INDEX));
            setPhotoDisplay4(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY4_INDEX));
            setPhotoDisplay5(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY5_INDEX));
            setPhotoDisplay6(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY6_INDEX));
            setPhotoDisplay7(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY7_INDEX));
            setPhotoDisplay8(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY8_INDEX));
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}

	public List<PhotoProject> getProjects(LikDBAdapter adapter) {
		List<PhotoProject> result = new ArrayList<PhotoProject>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_USERNO+"='"+getUserNO()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		
		Cursor c = qb.query(
				db,            // The database to query
				READ_PHOTOPROJECT_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				"projectNo desc"        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	PhotoProject om = new PhotoProject();
            	om.setSerialID(c.getInt(READ_PHOTOPROJECT_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_PHOTOPROJECT_COMPANYID_INDEX));
            	om.setUserNO(c.getString(READ_PHOTOPROJECT_USERNO_INDEX));
            	om.setYearMonth(c.getString(READ_PHOTOPROJECT_YEARMONTH_INDEX));
            	om.setName(c.getString(READ_PHOTOPROJECT_NAME_INDEX));
	        	om.setSupplierNO(c.getString(READ_PHOTOPROJECT_SUPPLIERNO_INDEX));
	        	om.setSupplierNM(c.getString(READ_PHOTOPROJECT_SUPPLIERNM_INDEX));
	            om.setRemark(c.getString(READ_PHOTOPROJECT_REMARK_INDEX));
	            om.setSalesNo(c.getString(READ_PHOTOPROJECT_SALESNO_INDEX));
	            om.setSalesName(c.getString(READ_PHOTOPROJECT_SALESNAME_INDEX));
	            om.setCount(c.getInt(READ_PHOTOPROJECT_COUNT_INDEX));
	            if(c.getString(READ_PHOTOPROJECT_FINISHDATE_INDEX)!=null) {
		            try {
		            	Date date = sdf.parse(c.getString(READ_PHOTOPROJECT_FINISHDATE_INDEX));
		            	om.setFinishDate(date);
		            } catch(ParseException pe) {
		            	om.setFinishDate(null);
		            }
	            }
	            om.setProjectNO(c.getString(READ_PHOTOPROJECT_PROJECTNO_INDEX));
	            om.setTakePhotoID(c.getInt(READ_PHOTOPROJECT_TAKEPHOTOID_INDEX));
	            om.setPhotoDisplay1(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY1_INDEX));
	            om.setPhotoDisplay2(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY2_INDEX));
	            om.setPhotoDisplay3(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY3_INDEX));
	            om.setPhotoDisplay4(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY4_INDEX));
	            om.setPhotoDisplay5(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY5_INDEX));
	            om.setPhotoDisplay6(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY6_INDEX));
	            om.setPhotoDisplay7(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY7_INDEX));
	            om.setPhotoDisplay8(c.getString(READ_PHOTOPROJECT_PHOTODISPLAY8_INDEX));
	            om.setRid(0);
	        	result.add(om);
            } while(c.moveToNext());
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return result;
	}
}
