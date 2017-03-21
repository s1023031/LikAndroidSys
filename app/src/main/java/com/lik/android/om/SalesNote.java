package com.lik.android.om;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;

public class SalesNote extends BaseSalesNote {

	private static final long serialVersionUID = -4583002289619122199L;

	@Override
	public SalesNote doInsert(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		Log.d(TAG,"db isOpen="+db.isOpen());
		InsertHelper ih = adapter.getInsertHelper(getTableName());
		ih.prepareForInsert();
		ih.bind(2, getReportKey());
		ih.bind(3, getUserNo());
		ih.bind(4, getNote());
		ih.bind(5, sdf.format(getIssueTime()));
		ih.bind(6, getCustomerID());
        if(getDeliverOrder()!=null) ih.bind(7, getDeliverOrder());
		ih.bind(8, isUpload()?1:0);
	    long rid = ih.execute();
	    if(rid != -1) setRid(0);
		return this;
	}

	@Override
	public SalesNote doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_REPORTKEY, getReportKey());
        updateValues.put(COLUMN_NAME_USERNO, getUserNo());
        updateValues.put(COLUMN_NAME_NOTE, getNote());
        updateValues.put(COLUMN_NAME_ISSUETIME, sdf.format(getIssueTime()));
        updateValues.put(COLUMN_NAME_CUSTOMERID, getCustomerID());
        updateValues.put(COLUMN_NAME_DELIVERORDER, getDeliverOrder());
        updateValues.put(COLUMN_NAME_ISUPLOAD, isUpload()?1:0);
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(getTableName(), updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
        closedb(adapter);
		return this;		
	}

	@Override
	public SalesNote doDelete(LikDBAdapter adapter) {
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
	public SalesNote findByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_REPORTKEY+"='"+getReportKey()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SALESNOTE_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_SALESNOTE_SERIALID_INDEX));
        	setReportKey(c.getString(READ_SALESNOTE_REPORTKEY_INDEX));
        	setUserNo(c.getString(READ_SALESNOTE_USERNO_INDEX));
        	setNote(c.getString(READ_SALESNOTE_NOTE_INDEX));
            try {
            	if(c.getString(READ_SALESNOTE_ISSUETIME_INDEX) != null) {
            		Date date = sdf.parse(c.getString(READ_SALESNOTE_ISSUETIME_INDEX));
            		setIssueTime(date);
            	}
            } catch(ParseException pe) {
            	setIssueTime(null); // primary key not null
            }
            setCustomerID(c.getInt(READ_SALESNOTE_CUSTOMERID_INDEX));
        	if(c.getString(READ_SALESNOTE_DELIVERORDER_INDEX)!=null) setDeliverOrder(c.getInt(READ_SALESNOTE_DELIVERORDER_INDEX));
        	setUpload(c.getInt(READ_SALESNOTE_ISUPLOAD_INDEX)==1?true:false);
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public SalesNote queryBySerialID(LikDBAdapter adapter) {
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
				READ_SALESNOTE_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_SALESNOTE_SERIALID_INDEX));
        	setReportKey(c.getString(READ_SALESNOTE_REPORTKEY_INDEX));
        	setUserNo(c.getString(READ_SALESNOTE_USERNO_INDEX));
        	setNote(c.getString(READ_SALESNOTE_NOTE_INDEX));
            try {
            	if(c.getString(READ_SALESNOTE_ISSUETIME_INDEX) != null) {
            		Date date = sdf.parse(c.getString(READ_SALESNOTE_ISSUETIME_INDEX));
            		setIssueTime(date);
            	}
            } catch(ParseException pe) {
            	setIssueTime(null); // primary key not null
            }
            setCustomerID(c.getInt(READ_SALESNOTE_CUSTOMERID_INDEX));
        	if(c.getString(READ_SALESNOTE_DELIVERORDER_INDEX)!=null) setDeliverOrder(c.getInt(READ_SALESNOTE_DELIVERORDER_INDEX));
        	setUpload(c.getInt(READ_SALESNOTE_ISUPLOAD_INDEX)==1?true:false);
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}
	
	private boolean isAll = false;
	

	public boolean isAll() {
		return isAll;
	}

	public void setAll(boolean isAll) {
		this.isAll = isAll;
	}

	public List<SalesNote> getSalesNote(LikDBAdapter adapter) {
		List<SalesNote> result = new ArrayList<SalesNote>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_USERNO+"='"+getUserNo()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_CUSTOMERID+"="+getCustomerID());
		if(!isAll) qb.appendWhere(" and "+COLUMN_NAME_ISUPLOAD+"=0");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SALESNOTE_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				COLUMN_NAME_ISSUETIME+" desc "        // The sort order
				);
		if(c != null && c.moveToFirst()) {
            do {
            	SalesNote om = new SalesNote();
            	om.setSerialID(c.getInt(READ_SALESNOTE_SERIALID_INDEX));
            	om.setReportKey(c.getString(READ_SALESNOTE_REPORTKEY_INDEX));
            	om.setUserNo(c.getString(READ_SALESNOTE_USERNO_INDEX));
            	om.setNote(c.getString(READ_SALESNOTE_NOTE_INDEX));
                try {
                	if(c.getString(READ_SALESNOTE_ISSUETIME_INDEX) != null) {
                		Date date = sdf.parse(c.getString(READ_SALESNOTE_ISSUETIME_INDEX));
                		om.setIssueTime(date);
                	}
                } catch(ParseException pe) {
                	om.setIssueTime(null); // primary key not null
                }
                om.setCustomerID(c.getInt(READ_SALESNOTE_CUSTOMERID_INDEX));
            	if(c.getString(READ_SALESNOTE_DELIVERORDER_INDEX)!=null) om.setDeliverOrder(c.getInt(READ_SALESNOTE_DELIVERORDER_INDEX));
            	om.setUpload(c.getInt(READ_SALESNOTE_ISUPLOAD_INDEX)==1?true:false);
            	result.add(om);
            } while(c.moveToNext());
		} else setRid(-1);		
		c.close();
        closedb(adapter);		
		return result;
	}
	
	public SalesNote deleteDataBeforeDate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_USERNO+"='"+getUserNo()+"'";
		whereClause += " and "+COLUMN_NAME_ISSUETIME+"<'"+sdf.format(getIssueTime())+"'";
		if(isDebug) Log.d(TAG,whereClause);
		//String[] whereArgs = {String.valueOf(getSerialID())};
		int rid = db.delete(getTableName(), whereClause, null);
        setRid(rid);
		if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
        closedb(adapter);
		return this;		
	}
	

}
