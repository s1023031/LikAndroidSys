package com.lik.android.om;

import java.util.ArrayList;
import java.util.List;

import com.lik.android.main.LikDBAdapter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class UserCompy extends BaseUserCompy {

	private static final long serialVersionUID = -6909984978774848405L;

	public UserCompy getUserCompyByPrimaryKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_ACCOUNTNO+"='"+getAccountNo()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_COMPANYPARENT+"='"+getCompanyParent()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_USERCOMPY_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null) {
        	boolean notEmpty = c.moveToFirst();
        	if(notEmpty) {
        		setSerialID(c.getInt(READ_USERCOMPY_SERIALID_INDEX));
        		setAccountNo(c.getString(READ_USERCOMPY_ACCOUNTNO_INDEX));
        		setCompanyID(c.getInt(READ_USERCOMPY_COMPANYID_INDEX));
        		setRid(0);
        	} else setRid(-1);
        }
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}
	
	public UserCompy insertUserCompy(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		 Log.d(TAG,"bbbb="+getCompanyParent());
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_NAME_ACCOUNTNO, getAccountNo());
        initialValues.put(COLUMN_NAME_COMPANYID, getCompanyID());
        initialValues.put(COLUMN_NAME_COMPANYPARENT, getCompanyParent());
        long rid = db.insert(TABLE_NAME, null, initialValues);
        setRid(rid);
        if(rid==0) setRid(-1); 
        closedb(adapter);
		return this;
	}

	public UserCompy updateUserCompy(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_ACCOUNTNO, getAccountNo());
        updateValues.put(COLUMN_NAME_COMPANYID,getCompanyID());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(TABLE_NAME, updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
//        db.close();
        closedb(adapter);
		return this;		
	}

	public UserCompy deleteUserCompy(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SERIALID+"="+getSerialID();
		if(isDebug) Log.d(TAG,whereClause);
		int rid = db.delete(TABLE_NAME, whereClause, null);
		if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
//		db.close();
        closedb(adapter);
		return this;
	}
	
	public List<UserCompy> getUserCompyByAccountNo(LikDBAdapter adapter) {
		List<UserCompy> result = new ArrayList<UserCompy>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_ACCOUNTNO+"='"+getAccountNo()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_COMPANYPARENT+"='"+getCompanyParent()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_USERCOMPY_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	UserCompy om = new UserCompy();
            	om.setSerialID(c.getInt(READ_USERCOMPY_SERIALID_INDEX));
            	om.setAccountNo(c.getString(READ_USERCOMPY_ACCOUNTNO_INDEX));
            	om.setCompanyID(c.getInt(READ_USERCOMPY_COMPANYID_INDEX));
                om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
        }
//        db.close();
        c.close();
        closedb(adapter);
		return result;
		
	}

	@Override
	public UserCompy doInsert(LikDBAdapter adapter) {
		return insertUserCompy(adapter);
	}

	@Override
	public UserCompy doUpdate(LikDBAdapter adapter) {
		return updateUserCompy(adapter);
	}

	@Override
	public UserCompy doDelete(LikDBAdapter adapter) {
		return deleteUserCompy(adapter);
	}

	@Override
	public UserCompy findByKey(LikDBAdapter adapter) {
		return getUserCompyByPrimaryKey(adapter);
	}

	@Override
	public UserCompy queryBySerialID(LikDBAdapter adapter) {
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
				READ_USERCOMPY_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null) {
        	boolean notEmpty = c.moveToFirst();
        	if(notEmpty) {
        		setSerialID(c.getInt(READ_USERCOMPY_SERIALID_INDEX));
        		setAccountNo(c.getString(READ_USERCOMPY_ACCOUNTNO_INDEX));
        		setCompanyID(c.getInt(READ_USERCOMPY_COMPANYID_INDEX));
        		setRid(0);
        	} else setRid(-1);
        }
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}
	
	public UserCompy insertUserCompyFromCompanyNoTransaction(LikDBAdapter adapter, Company omC) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_NAME_ACCOUNTNO, omC.getUserNO());
        initialValues.put(COLUMN_NAME_COMPANYID, omC.getCompanyID());
        initialValues.put(COLUMN_NAME_COMPANYPARENT, omC.getCompanyParent());
        long rid = db.insert(TABLE_NAME, null, initialValues);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
        closedb(adapter);
		return this;
	}

	public UserCompy insertUserCompyFromCompany(LikDBAdapter adapter, Company omC) {

	  InsertHelper ih = adapter.getInsertHelper(UserCompy.TABLE_NAME);
	  ih.prepareForInsert();
	  ih.bind(2, omC.getUserNO());
	  ih.bind(3, omC.getCompanyID());
	  ih.bind(5, omC.getCompanyParent());
	  long rid = ih.execute();
	  if(rid != -1) setRid(0);
		return this;

	}

}
