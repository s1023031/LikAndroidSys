package com.lik.android.om;

import java.text.ParseException;
import java.util.Date;

import com.lik.android.main.LikDBAdapter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class Account extends BaseAccount {

	private static final long serialVersionUID = -9113848463593009865L;

	/**
	 * 
	 * @param adapter
	 * @return
	 */
	public Account getAccountByAccountNo(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_ACCOUNTNO+"='"+getAccountNo()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_SERIALID+"='"+getSerialID()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_ACCOUNT_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null) {
            boolean notEmpty = c.moveToFirst();
            if(notEmpty) {
            	setAccountNo(c.getString(READ_ACCOUNT_ACCOUNTNO_INDEX));
            	setPassword(c.getString(READ_ACCOUNT_PASSWORD_INDEX));
                try {
                	Date lastModifiedDate = sdf.parse(c.getString(READ_ACCOUNT_LASTMODIFIEDDATE_INDEX));
                	setLastModifiedDate(lastModifiedDate);
            		setLOOK_MAPTRACK(c.getString(READ_ACCOUNT_LOOK_MAPTRACK_INDEX));
            		setBOSS_USERNO(c.getString(READ_ACCOUNT_BOSS_USERNO_INDEX));
            		setAccountName(c.getString(READ_ACCOUNT_ACCOUNTNAME_INDEX));
                } catch(ParseException pe) {
                	setLastModifiedDate(null);
                }
            	setRid(0);
            } else setRid(-1);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}
	
	public Account getAccountByCompanyParent(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
	//	qb.appendWhere(COLUMN_NAME_COMPANYPARENT+"='"+getCompanyParent()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_ACCOUNT_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null) {
            boolean notEmpty = c.moveToFirst();
            if(notEmpty) {
            	setAccountNo(c.getString(READ_ACCOUNT_ACCOUNTNO_INDEX));
            	setPassword(c.getString(READ_ACCOUNT_PASSWORD_INDEX));
                try {
                	Date lastModifiedDate = sdf.parse(c.getString(READ_ACCOUNT_LASTMODIFIEDDATE_INDEX));
                	setLastModifiedDate(lastModifiedDate);
            		setLOOK_MAPTRACK(c.getString(READ_ACCOUNT_LOOK_MAPTRACK_INDEX));
            		setBOSS_USERNO(c.getString(READ_ACCOUNT_BOSS_USERNO_INDEX));
            		setAccountName(c.getString(READ_ACCOUNT_ACCOUNTNAME_INDEX));
                } catch(ParseException pe) {
                	setLastModifiedDate(null);
                }
            	setRid(0);
            } else setRid(-1);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}
	
	public Account insertAccount(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_NAME_ACCOUNTNO, getAccountNo());
        initialValues.put(COLUMN_NAME_PASSWORD, getPassword());
        initialValues.put(COLUMN_NAME_LASTMODIFIEDDATE, sdf.format(getLastModifiedDate()));
        initialValues.put(COLUMN_NAME_SERIALID, getSerialID());
        initialValues.put(COLUMN_NAME_LOOK_MAPTRACK, getLOOK_MAPTRACK());
        initialValues.put(COLUMN_NAME_BOSS_USERNO, getBOSS_USERNO());
        initialValues.put(COLUMN_NAME_ACCOUNTNAME, getAccountName());
        initialValues.put(COLUMN_NAME_COMPANYPARENT, getCompanyParent());
        long rid = db.insert(TABLE_NAME, null, initialValues);
        setRid(rid);
//        db.close();
        closedb(adapter);
		return this;
	}
	
	public Account updateAccount(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_PASSWORD, getPassword());
        updateValues.put(COLUMN_NAME_LASTMODIFIEDDATE, sdf.format(getLastModifiedDate()));
        updateValues.put(COLUMN_NAME_SERIALID, getSerialID());
        updateValues.put(COLUMN_NAME_LOOK_MAPTRACK, getLOOK_MAPTRACK());
        updateValues.put(COLUMN_NAME_BOSS_USERNO, getBOSS_USERNO());
        updateValues.put(COLUMN_NAME_ACCOUNTNAME, getAccountName());
        String[] whereArgs = {getAccountNo()};
        long rid = db.update(TABLE_NAME, updateValues, COLUMN_NAME_ACCOUNTNO+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
//        db.close();
        closedb(adapter);
		return this;		
	}

	public Account deleteAccount(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_ACCOUNTNO+"='"+getAccountNo()+"'";
		if(isDebug) Log.d(TAG,whereClause);
		int rid = db.delete(TABLE_NAME, whereClause, null);
		setRid(rid);
		if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
//		db.close();
        closedb(adapter);
		return this;
	}

	@Override
	public Account doInsert(LikDBAdapter adapter) {
		return insertAccount(adapter);
	}

	@Override
	public Account doUpdate(LikDBAdapter adapter) {
		return updateAccount(adapter);
	}

	@Override
	public Account doDelete(LikDBAdapter adapter) {
		return deleteAccount(adapter);
	}

	@Override
	public Account findByKey(LikDBAdapter adapter) {
		return getAccountByAccountNo(adapter);
	}

	@Override
	public Account queryBySerialID(LikDBAdapter adapter) {
		return findByKey(adapter);
	}

}
