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

public class Customers extends BaseCustomers implements ProcessDownloadInterface {

	private static final long serialVersionUID = -4549552728066389611L;

	public Customers getCustomersByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_USERNO+"='"+getUserNO()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_CUSTOMERID+"="+getCustomerID());

		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_CUSTOMERS_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_CUSTOMERS_SERIALID_INDEX));
        	setCustomerNO(c.getString(READ_CUSTOMERS_CUSTOMERNO_INDEX));
        	setShortName(c.getString(READ_CUSTOMERS_SHORTNAME_INDEX));
        	setFullName(c.getString(READ_CUSTOMERS_FULLNAME_INDEX));
        	setZipNo(c.getString(READ_CUSTOMERS_ZIPNO_INDEX));
        	setAddress(c.getString(READ_CUSTOMERS_ADDRESS_INDEX));
        	setTel1(c.getString(READ_CUSTOMERS_TEL1_INDEX));
        	setTel2(c.getString(READ_CUSTOMERS_TEL2_INDEX));
        	setActTel(c.getString(READ_CUSTOMERS_ACTTEL_INDEX));
        	setSalesID(c.getInt(READ_CUSTOMERS_SALESID_INDEX));
        	setBeVisit(c.getString(READ_CUSTOMERS_BEVISIT_INDEX));
        	setSalesName(c.getString(READ_CUSTOMERS_SALESNAME_INDEX));
        	setSalesNO(c.getString(READ_CUSTOMERS_SALESNO_INDEX));
            setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

	public Customers insertCustomers(LikDBAdapter adapter) {
		Log.d(TAG,"insertCustomers="+getTableName());
		getdb(adapter);
		InsertHelper ih = adapter.getInsertHelper(getTableName());
		ih.prepareForInsert();
		
		
		
		try{
			ih.bind(2, getCompanyID());
			Log.d(TAG,"getCompanyID="+getCompanyID());
			ih.bind(3, getUserNO());
			Log.d(TAG,"getUserNO="+getUserNO());
			ih.bind(4, getCustomerID());
			Log.d(TAG,"getCustomerID="+getCustomerID());
			ih.bind(5, getCustomerNO());
			Log.d(TAG,"getCustomerNO="+getCustomerNO());
	        ih.bind(6, getShortName());
	        Log.d(TAG,"getShortName="+getShortName());
	        ih.bind(7, getFullName());
	        Log.d(TAG,"getFullName="+getFullName());
	        ih.bind(8, getZipNo());
	        Log.d(TAG,"getZipNo="+getZipNo());
	        ih.bind(9, getAddress());
	        Log.d(TAG,"getAddress="+getAddress());
	        ih.bind(10, getTel1());
	        Log.d(TAG,"getTel1="+getTel1());
	        ih.bind(11, getTel2());
	        Log.d(TAG,"getTel2="+getTel2());
	        ih.bind(12, getActTel());
	        Log.d(TAG,"getActTel="+getActTel());
	        ih.bind(13, getSalesID());
	        Log.d(TAG,"getSalesID="+getSalesID());
	        ih.bind(14, getBeVisit());
	        Log.d(TAG,"getBeVisit="+getBeVisit());
	        ih.bind(15, getSalesName());
	        Log.d(TAG,"getSalesName="+getSalesName());
	        ih.bind(16, getSalesNO());
	        Log.d(TAG,"getSalesNO="+getSalesNO());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
        
  	    long rid = ih.execute();
        if(rid != -1) setRid(0);
    	Log.d(TAG,"insertCustomaaers="+getRid());
		return this;
	}

//	public Customers insertCustomers(LikDBAdapter adapter) {
//		SQLiteDatabase db = getdb(adapter);
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(COLUMN_NAME_COMPANYID, getCompanyID());
//        initialValues.put(COLUMN_NAME_USERNO, getUserNO());
//        initialValues.put(COLUMN_NAME_CUSTOMERID, getCustomerID());
//        initialValues.put(COLUMN_NAME_CUSTOMERNO, getCustomerNO());
//        initialValues.put(COLUMN_NAME_SHORTNAME, getShortName());
//        initialValues.put(COLUMN_NAME_FULLNAME, getFullName());
//        initialValues.put(COLUMN_NAME_ZIPNO, getZipNo());
//        initialValues.put(COLUMN_NAME_ADDRESS, getAddress());
//        initialValues.put(COLUMN_NAME_TEL1, getTel1());
//        initialValues.put(COLUMN_NAME_TEL2, getTel2());
//        initialValues.put(COLUMN_NAME_PAYTYPE, getPayType());
//        initialValues.put(COLUMN_NAME_SALESID, getSalesID());
//        initialValues.put(COLUMN_NAME_PRICEGRADE, getPriceGrade());
//        initialValues.put(COLUMN_NAME_PROMOTEGROUPID, getPromoteGroupID());
//        initialValues.put(COLUMN_NAME_BEVISIT, getBeVisit());
//        initialValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
//        long rid = db.insert(TABLE_NAME, null, initialValues);
//        setRid(rid);
////        db.close();
//        closedb(adapter);
//		return this;
//	}

	public Customers updateCustomers(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();

        updateValues.put(COLUMN_NAME_CUSTOMERNO, getCustomerNO());
        updateValues.put(COLUMN_NAME_SHORTNAME, getShortName());
        updateValues.put(COLUMN_NAME_FULLNAME, getFullName());
        updateValues.put(COLUMN_NAME_ZIPNO, getZipNo());
        updateValues.put(COLUMN_NAME_ADDRESS, getAddress());
        updateValues.put(COLUMN_NAME_TEL1, getTel1());
        updateValues.put(COLUMN_NAME_TEL2, getTel2());
        updateValues.put(COLUMN_NAME_ACTTEL, getActTel());
        updateValues.put(COLUMN_NAME_SALESID, getSalesID());
        updateValues.put(COLUMN_NAME_BEVISIT, getBeVisit());
        updateValues.put(COLUMN_NAME_SALESNAME, getSalesName());
        updateValues.put(COLUMN_NAME_SALESNO, getSalesNO());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(getTableName(), updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
//        db.close();
        closedb(adapter);
		return this;		
	}
	
	public Customers deleteCustomers(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SERIALID+"="+getSerialID();
		if(isDebug) Log.d(TAG,whereClause);
		//String[] whereArgs = {String.valueOf(getSerialID())};
		int rid = db.delete(getTableName(), whereClause, null);
        setRid(rid);
		if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
//		db.close();
        closedb(adapter);
		return this;
	}

	@Override
	public boolean processDownload(LikDBAdapter adapter,Map<String, String> detail, boolean isOnlyInsert) {
		boolean result = true;
		Log.d(TAG,"seq="+detail.get("DownloadSeq"));
		String flag = detail.get(FLAG_KEY);
		setCompanyID(Integer.parseInt(detail.get(COLUMN_NAME_COMPANYID)));
		setUserNO(detail.get(COLUMN_NAME_USERNO));
		setCustomerID(Integer.parseInt(detail.get(COLUMN_NAME_CUSTOMERID)));
		if(!isOnlyInsert) getCustomersByKey(adapter);
		setCustomerNO(detail.get(COLUMN_NAME_CUSTOMERNO));
		setShortName(detail.get(COLUMN_NAME_SHORTNAME));
		setFullName(detail.get(COLUMN_NAME_FULLNAME));
		setZipNo(detail.get(COLUMN_NAME_ZIPNO));
		setAddress(detail.get(COLUMN_NAME_ADDRESS));
		setTel1(detail.get(COLUMN_NAME_TEL1));
		setTel2(detail.get(COLUMN_NAME_TEL2));
		setActTel(detail.get(COLUMN_NAME_ACTTEL));
		setSalesID(Integer.parseInt(detail.get(COLUMN_NAME_SALESID)));
		setBeVisit(detail.get(COLUMN_NAME_BEVISIT));
		setSalesName(detail.get(COLUMN_NAME_SALESNAME));
		setSalesNO(detail.get(COLUMN_NAME_SALESNO));
		Log.d(TAG,"setSalesNO="+detail.get(COLUMN_NAME_SALESNO));
		if(isOnlyInsert) 
			insertCustomers(adapter);
		else {
			if(getRid()<0) 
				insertCustomers(adapter);
			else {
				if(flag.equals(FLAG_DELETE)) doDelete(adapter);
				else updateCustomers(adapter);
			}
		}
		if(getRid()<0) 
			result = false;
		Log.d(TAG,"Customer insert result="+result);
		return result;
	}

	@Override
	public Customers doInsert(LikDBAdapter adapter) {
		return insertCustomers(adapter);
	}

	@Override
	public Customers doUpdate(LikDBAdapter adapter) {
		return updateCustomers(adapter);
	}

	@Override
	public Customers doDelete(LikDBAdapter adapter) {
		return deleteCustomers(adapter);
	}

	@Override
	public Customers findByKey(LikDBAdapter adapter) {
		return getCustomersByKey(adapter);
	}

	@Override
	public Customers queryBySerialID(LikDBAdapter adapter) {
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
				READ_CUSTOMERS_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_CUSTOMERS_SERIALID_INDEX));
    		setCompanyID(c.getInt(READ_CUSTOMERS_COMPANYID_INDEX));
    		setUserNO(c.getString(READ_CUSTOMERS_USERNO_INDEX));
    		setCustomerID(c.getInt(READ_CUSTOMERS_CUSTOMERID_INDEX));
        	setCustomerNO(c.getString(READ_CUSTOMERS_CUSTOMERNO_INDEX));
        	setShortName(c.getString(READ_CUSTOMERS_SHORTNAME_INDEX));
        	setFullName(c.getString(READ_CUSTOMERS_FULLNAME_INDEX));
        	setZipNo(c.getString(READ_CUSTOMERS_ZIPNO_INDEX));
        	setAddress(c.getString(READ_CUSTOMERS_ADDRESS_INDEX));
        	setTel1(c.getString(READ_CUSTOMERS_TEL1_INDEX));
        	setTel2(c.getString(READ_CUSTOMERS_TEL2_INDEX));
        	setActTel(c.getString(READ_CUSTOMERS_ACTTEL_INDEX));
        	setSalesID(c.getInt(READ_CUSTOMERS_SALESID_INDEX));
        	setBeVisit(c.getString(READ_CUSTOMERS_BEVISIT_INDEX));
        	setSalesName(c.getString(READ_CUSTOMERS_SALESNAME_INDEX));
        	setSalesNO(c.getString(READ_CUSTOMERS_SALESNO_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}
	
	/**
	 * ����: UserNo,CompanyID
	 * BeVisit = 'Y' or 'N'
	 * CustType = 1 or 2
	 * @param adapter
	 * @return
	 */
	public List<Customers> getCustomersByUserNoCompanyID(LikDBAdapter adapter) {
		List<Customers> result = new ArrayList<Customers>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		Log.d(TAG,"123="+getTableName());
		
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_USERNO+"='"+getUserNO()+"'");
		if(getBeVisit() != null) qb.appendWhere(" and "+COLUMN_NAME_BEVISIT+"='"+getBeVisit()+"'");

		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_CUSTOMERS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
		if(c != null && c.getCount()>0) {
			c.moveToFirst();
            do {
            	Customers om = new Customers();
            	om.setSerialID(c.getInt(READ_CUSTOMERS_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_CUSTOMERS_COMPANYID_INDEX));
            	om.setUserNO(c.getString(READ_CUSTOMERS_USERNO_INDEX));
            	om.setCustomerID(c.getInt(READ_CUSTOMERS_CUSTOMERID_INDEX));
            	om.setCustomerNO(c.getString(READ_CUSTOMERS_CUSTOMERNO_INDEX));
            	om.setShortName(c.getString(READ_CUSTOMERS_SHORTNAME_INDEX));
            	om.setFullName(c.getString(READ_CUSTOMERS_FULLNAME_INDEX));
            	om.setZipNo(c.getString(READ_CUSTOMERS_ZIPNO_INDEX));
            	om.setAddress(c.getString(READ_CUSTOMERS_ADDRESS_INDEX));
            	om.setTel1(c.getString(READ_CUSTOMERS_TEL1_INDEX));
            	om.setTel2(c.getString(READ_CUSTOMERS_TEL2_INDEX));
            	om.setActTel(c.getString(READ_CUSTOMERS_ACTTEL_INDEX));
            	om.setSalesID(c.getInt(READ_CUSTOMERS_SALESID_INDEX));
            	om.setBeVisit(c.getString(READ_CUSTOMERS_BEVISIT_INDEX));
            	om.setSalesName(c.getString(READ_CUSTOMERS_SALESNAME_INDEX));
            	om.setSalesNO(c.getString(READ_CUSTOMERS_SALESNO_INDEX));
            
            	om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
    		c.close();
		} 		
        closedb(adapter);
		return result;
	}

	/**
	 * �Y������줣��null�A�h�θ�����search
	 * @param adapter
	 * @return
	 */
	public List<Customers> searchByKeyWord(LikDBAdapter adapter) {
		List<Customers> result = new ArrayList<Customers>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		if(getShortName() != null) {
			qb.appendWhere(COLUMN_NAME_SHORTNAME+" like '%"+getShortName()+"%'");			
		} else if(getCustomerNO() != null) {
			qb.appendWhere(COLUMN_NAME_CUSTOMERNO+" like '"+getCustomerNO()+"%'");						
		}
		qb.appendWhere(" and "+COLUMN_NAME_USERNO+"='"+getUserNO()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_CUSTOMERS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
		if(c != null && c.getCount()>0) {
			c.moveToFirst();
            do {
            	Customers om = new Customers();
            	om.setSerialID(c.getInt(READ_CUSTOMERS_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_CUSTOMERS_COMPANYID_INDEX));
            	om.setUserNO(c.getString(READ_CUSTOMERS_USERNO_INDEX));
            	om.setCustomerID(c.getInt(READ_CUSTOMERS_CUSTOMERID_INDEX));
            	om.setCustomerNO(c.getString(READ_CUSTOMERS_CUSTOMERNO_INDEX));
            	om.setShortName(c.getString(READ_CUSTOMERS_SHORTNAME_INDEX));
            	om.setFullName(c.getString(READ_CUSTOMERS_FULLNAME_INDEX));
            	om.setZipNo(c.getString(READ_CUSTOMERS_ZIPNO_INDEX));
            	om.setAddress(c.getString(READ_CUSTOMERS_ADDRESS_INDEX));
            	om.setTel1(c.getString(READ_CUSTOMERS_TEL1_INDEX));
            	om.setTel2(c.getString(READ_CUSTOMERS_TEL2_INDEX));
            	om.setActTel(c.getString(READ_CUSTOMERS_ACTTEL_INDEX));
            	om.setSalesID(c.getInt(READ_CUSTOMERS_SALESID_INDEX));
            	om.setBeVisit(c.getString(READ_CUSTOMERS_BEVISIT_INDEX));
            	om.setSalesName(c.getString(READ_CUSTOMERS_SALESNAME_INDEX));
            	om.setSalesNO(c.getString(READ_CUSTOMERS_SALESNO_INDEX));

            	om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
    		c.close();
		} 		
        closedb(adapter);
		return result;
	}

	public List<Customers> getAllCustomers(LikDBAdapter adapter) {
		List<Customers> result = new ArrayList<Customers>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_CUSTOMERS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
		if(c != null && c.moveToFirst()) {
            do {
            	Customers om = new Customers();
            	om.setSerialID(c.getInt(READ_CUSTOMERS_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_CUSTOMERS_COMPANYID_INDEX));
            	om.setUserNO(c.getString(READ_CUSTOMERS_USERNO_INDEX));
            	om.setCustomerID(c.getInt(READ_CUSTOMERS_CUSTOMERID_INDEX));
            	om.setCustomerNO(c.getString(READ_CUSTOMERS_CUSTOMERNO_INDEX));
            	om.setShortName(c.getString(READ_CUSTOMERS_SHORTNAME_INDEX));
            	om.setFullName(c.getString(READ_CUSTOMERS_FULLNAME_INDEX));
            	om.setZipNo(c.getString(READ_CUSTOMERS_ZIPNO_INDEX));
            	om.setAddress(c.getString(READ_CUSTOMERS_ADDRESS_INDEX));
            	om.setTel1(c.getString(READ_CUSTOMERS_TEL1_INDEX));
            	om.setTel2(c.getString(READ_CUSTOMERS_TEL2_INDEX));
            	om.setActTel(c.getString(READ_CUSTOMERS_ACTTEL_INDEX));
            	om.setSalesID(c.getInt(READ_CUSTOMERS_SALESID_INDEX));
            	om.setBeVisit(c.getString(READ_CUSTOMERS_BEVISIT_INDEX));
            	om.setSalesName(c.getString(READ_CUSTOMERS_SALESNAME_INDEX));
            	om.setSalesNO(c.getString(READ_CUSTOMERS_SALESNO_INDEX));
            	om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
    		c.close();
		} 		
        closedb(adapter);
		return result;
	}
	
	public List<Customers> getCustomersByShortName(LikDBAdapter adapter) {
		List<Customers> result = new ArrayList<Customers>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_USERNO+"='"+getUserNO()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_SHORTNAME+"='"+getShortName()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_CUSTOMERS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
		if(c != null && c.moveToFirst()) {
			do {
				Customers om = new Customers();
				om.setSerialID(c.getInt(READ_CUSTOMERS_SERIALID_INDEX));
				om.setCompanyID(c.getInt(READ_CUSTOMERS_COMPANYID_INDEX));
				om.setCustomerID(c.getInt(READ_CUSTOMERS_CUSTOMERID_INDEX));
				om.setCustomerNO(c.getString(READ_CUSTOMERS_CUSTOMERNO_INDEX));
				om.setUserNO(c.getString(READ_CUSTOMERS_USERNO_INDEX));
				om.setShortName(c.getString(READ_CUSTOMERS_SHORTNAME_INDEX));
				om.setFullName(c.getString(READ_CUSTOMERS_FULLNAME_INDEX));
				om.setZipNo(c.getString(READ_CUSTOMERS_ZIPNO_INDEX));
				om.setAddress(c.getString(READ_CUSTOMERS_ADDRESS_INDEX));
				om.setTel1(c.getString(READ_CUSTOMERS_TEL1_INDEX));
				om.setTel2(c.getString(READ_CUSTOMERS_TEL2_INDEX));
				om.setActTel(c.getString(READ_CUSTOMERS_ACTTEL_INDEX));
				om.setSalesID(c.getInt(READ_CUSTOMERS_SALESID_INDEX));
				om.setBeVisit(c.getString(READ_CUSTOMERS_BEVISIT_INDEX));
				om.setSalesName(c.getString(READ_CUSTOMERS_SALESNAME_INDEX));
				om.setSalesNO(c.getString(READ_CUSTOMERS_SALESNO_INDEX));
	        
				om.setRid(0);
				result.add(om);
            } while(c.moveToNext());
			c.close();
        } else setRid(-1);	
        closedb(adapter);
		return result;
	}

	public Customers getCustomersByNo(LikDBAdapter adapter) {
		Log.d(TAG,"getCustomersByNo Start TableName="+getTableName());
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_USERNO+"='"+getUserNO()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_CUSTOMERNO+"='"+getCustomerNO()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_CUSTOMERS_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_CUSTOMERS_SERIALID_INDEX));
        	setCustomerID(c.getInt(READ_CUSTOMERS_CUSTOMERID_INDEX));
        	setCustomerNO(c.getString(READ_CUSTOMERS_CUSTOMERNO_INDEX));
        	setShortName(c.getString(READ_CUSTOMERS_SHORTNAME_INDEX));
        	setFullName(c.getString(READ_CUSTOMERS_FULLNAME_INDEX));
        	setZipNo(c.getString(READ_CUSTOMERS_ZIPNO_INDEX));
        	setAddress(c.getString(READ_CUSTOMERS_ADDRESS_INDEX));
        	setTel1(c.getString(READ_CUSTOMERS_TEL1_INDEX));
        	setTel2(c.getString(READ_CUSTOMERS_TEL2_INDEX));
        	setActTel(c.getString(READ_CUSTOMERS_ACTTEL_INDEX));
        	setSalesID(c.getInt(READ_CUSTOMERS_SALESID_INDEX));
        	setBeVisit(c.getString(READ_CUSTOMERS_BEVISIT_INDEX));
        	setSalesName(c.getString(READ_CUSTOMERS_SALESNAME_INDEX));
        	setSalesNO(c.getString(READ_CUSTOMERS_SALESNO_INDEX));
   
        	setRid(0);
        } else setRid(-1);

        c.close();
        closedb(adapter);
        Log.d(TAG,"getCustomersByNo End ");
		return this;
	}

}
