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

public class TemporaryCredit extends BaseTemporaryCredit implements ProcessDownloadInterface {


	private static final long serialVersionUID = 4999027760957530525L;

	@Override
	public TemporaryCredit doInsert(LikDBAdapter adapter) {
		getdb(adapter);
		InsertHelper ih = adapter.getInsertHelper(getTableName());
		ih.prepareForInsert();
		ih.bind(2, getCompanyID());
		ih.bind(3, getCustomerID());
		ih.bind(4, getUserNo());
		ih.bind(5, getAccountName());
		ih.bind(6, getAccountRemark());
		ih.bind(7, getAccountAmount());
	    long rid = ih.execute();
	    if(rid != -1) setRid(0);
		return this;
	}

	@Override
	public TemporaryCredit doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_COMPANYID, getCompanyID());
        updateValues.put(COLUMN_NAME_CUSTOMERID, getCustomerID());
        updateValues.put(COLUMN_NAME_USERNO, getUserNo());
        updateValues.put(COLUMN_NAME_ACCOUNTNAME, getAccountName());
        updateValues.put(COLUMN_NAME_ACCOUNTREMARK, getAccountRemark());
        updateValues.put(COLUMN_NAME_ACCOUNTAMOUNT, getAccountAmount());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(getTableName(), updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
        closedb(adapter);
		return this;		
	}

	@Override
	public TemporaryCredit doDelete(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SERIALID+"="+getSerialID();
		if(isDebug) Log.d(TAG,whereClause);
		//String[] whereArgs = {String.valueOf(getSerialID())};
		int rid = db.delete(getTableName(), whereClause, null);
        setRid(rid);
		if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
        closedb(adapter);
		return this;
	}

	@Override
	public TemporaryCredit findByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_USERNO+"='"+getUserNo()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_CUSTOMERID+"="+getCustomerID());
		qb.appendWhere(" and "+COLUMN_NAME_ACCOUNTNAME+"='"+getAccountName()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_TC_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_TC_SERIALID_INDEX));
        	setCompanyID(c.getInt(READ_TC_COMPANYID_INDEX));
        	setCustomerID(c.getInt(READ_TC_CUSTOMERID_INDEX));
        	setUserNo(c.getString(READ_TC_USERNO_INDEX));
        	setAccountName(c.getString(READ_TC_ACCOUNTNAME_INDEX));
        	setAccountRemark(c.getString(READ_TC_ACCOUNTREMARK_INDEX));
        	setAccountAmount(c.getDouble(READ_TC_ACCOUNTAMOUNT_INDEX));
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public TemporaryCredit queryBySerialID(LikDBAdapter adapter) {
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
				READ_TC_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_TC_SERIALID_INDEX));
        	setCompanyID(c.getInt(READ_TC_COMPANYID_INDEX));
        	setCustomerID(c.getInt(READ_TC_CUSTOMERID_INDEX));
        	setUserNo(c.getString(READ_TC_USERNO_INDEX));
        	setAccountName(c.getString(READ_TC_ACCOUNTNAME_INDEX));
        	setAccountRemark(c.getString(READ_TC_ACCOUNTREMARK_INDEX));
        	setAccountAmount(c.getDouble(READ_TC_ACCOUNTAMOUNT_INDEX));
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public boolean processDownload(LikDBAdapter adapter,
			Map<String, String> detail, boolean isOnlyInsert) {
		boolean result = true;
		String flag = detail.get(FLAG_KEY)==null?"N":detail.get(FLAG_KEY);
		setCompanyID(Integer.parseInt(detail.get(COLUMN_NAME_COMPANYID)));
		setCustomerID(Integer.parseInt(detail.get(COLUMN_NAME_CUSTOMERID)));
		setUserNo(detail.get(COLUMN_NAME_USERNO));
		setAccountName(detail.get(COLUMN_NAME_ACCOUNTNAME));
		if(!isOnlyInsert) findByKey(adapter);
		setAccountRemark(detail.get(COLUMN_NAME_ACCOUNTREMARK));
		setAccountAmount(Double.parseDouble(detail.get(COLUMN_NAME_ACCOUNTAMOUNT)));
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

	public List<TemporaryCredit> findByCustomerID(LikDBAdapter adapter) {
		List<TemporaryCredit> result = new ArrayList<TemporaryCredit>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_USERNO+"='"+getUserNo()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_CUSTOMERID+"="+getCustomerID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_TC_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if(c != null && c.moveToFirst()) {
        	do{
        		TemporaryCredit om = new TemporaryCredit();
        		om.setSerialID(c.getInt(READ_TC_SERIALID_INDEX));
        		om.setCompanyID(c.getInt(READ_TC_COMPANYID_INDEX));
        		om.setCustomerID(c.getInt(READ_TC_CUSTOMERID_INDEX));
        		om.setUserNo(c.getString(READ_TC_USERNO_INDEX));
        		om.setAccountName(c.getString(READ_TC_ACCOUNTNAME_INDEX));
        		om.setAccountRemark(c.getString(READ_TC_ACCOUNTREMARK_INDEX));
        		om.setAccountAmount(c.getDouble(READ_TC_ACCOUNTAMOUNT_INDEX));
        		om.setRid(0);
        		result.add(om);
        	}while(c.moveToNext());
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return result;
	}


}
