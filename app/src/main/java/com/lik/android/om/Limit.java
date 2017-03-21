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
import com.lik.android.view.SubAddProductsView;

public class Limit extends BaseLimit implements ProcessDownloadInterface {


	private static final long serialVersionUID = 4999027760957530525L;

	@Override
	public Limit doInsert(LikDBAdapter adapter) {
		getdb(adapter);
		InsertHelper ih = adapter.getInsertHelper(getTableName());
		ih.prepareForInsert();
		ih.bind(2, getCompanyID());
		ih.bind(3, getItemID());
		ih.bind(4, getCustomerID());
		ih.bind(5, getUserNo());
		ih.bind(6, getStradeDate()==null?null:sdf.format(getStradeDate()));
	    long rid = ih.execute();
	    if(rid != -1) setRid(0);
		return this;
	}

	@Override
	public Limit doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_COMPANYID, getCompanyID());
        updateValues.put(COLUMN_NAME_ITEMID, getItemID());
        updateValues.put(COLUMN_NAME_CUSTOMERID, getCustomerID());
        updateValues.put(COLUMN_NAME_USERNO, getUserNo());
        updateValues.put(COLUMN_NAME_STRADEDATE, sdf.format(getStradeDate()));
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(getTableName(), updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
        closedb(adapter);
		return this;		
	}

	@Override
	public Limit doDelete(LikDBAdapter adapter) {
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
	public Limit findByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_USERNO+"='"+getUserNo()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_ITEMID+"="+getItemID());
		qb.appendWhere(" and "+COLUMN_NAME_CUSTOMERID+"="+getCustomerID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_LIMIT_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_LIMIT_SERIALID_INDEX));
        	setCompanyID(c.getInt(READ_LIMIT_COMPANYID_INDEX));
        	setItemID(c.getInt(READ_LIMIT_ITEMID_INDEX));
        	setCustomerID(c.getInt(READ_LIMIT_CUSTOMERID_INDEX));
        	setUserNo(c.getString(READ_LIMIT_USERNO_INDEX));
        	if(c.getString(READ_LIMIT_STRADEDATE_INDEX)!=null) {
	            try {
	            	Date stradeDate = sdf.parse(c.getString(READ_LIMIT_STRADEDATE_INDEX));
	            	setStradeDate(stradeDate);
	            } catch(ParseException pe) {
	            	setStradeDate(null);
	            }
        	}
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public Limit queryBySerialID(LikDBAdapter adapter) {
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
				READ_LIMIT_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_LIMIT_SERIALID_INDEX));
        	setCompanyID(c.getInt(READ_LIMIT_COMPANYID_INDEX));
        	setItemID(c.getInt(READ_LIMIT_ITEMID_INDEX));
        	setCustomerID(c.getInt(READ_LIMIT_CUSTOMERID_INDEX));
        	setUserNo(c.getString(READ_LIMIT_USERNO_INDEX));
        	if(c.getString(READ_LIMIT_STRADEDATE_INDEX)!=null) {
	            try {
	            	Date stradeDate = sdf.parse(c.getString(READ_LIMIT_STRADEDATE_INDEX));
	            	setStradeDate(stradeDate);
	            } catch(ParseException pe) {
	            	setStradeDate(null);
	            }
        	}
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
//		setCompanyID(Integer.parseInt(detail.get(COLUMN_NAME_COMPANYID)));
		setCompanyID(adapter.getCompanyID());
		setItemID(Integer.parseInt(detail.get(COLUMN_NAME_ITEMID)));
		setCustomerID(Integer.parseInt(detail.get(COLUMN_NAME_CUSTOMERID)));
		if(!isOnlyInsert) findByKey(adapter);
		setUserNo(detail.get(COLUMN_NAME_USERNO));
		try {
			if(detail.get(COLUMN_NAME_STRADEDATE) != null) setStradeDate(sdf2.parse(detail.get(COLUMN_NAME_STRADEDATE)));
			else setStradeDate(null);
		} catch (ParseException e) {
			Log.e(TAG,e.getMessage());
		}
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
	
}
