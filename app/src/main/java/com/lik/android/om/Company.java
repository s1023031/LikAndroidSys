package com.lik.android.om;

import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;
import com.lik.android.main.LikSysActivity;
import com.lik.android.main.MainMenuActivity;

public class Company extends BaseCompany implements ProcessDownloadInterface {

	private static final long serialVersionUID = 624386524546415212L;

	public Company getCompanyByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_USERNO+"='"+getUserNO()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_COMPANYPARENT+"='"+getCompanyParent()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_COMPANY_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_COMPANY_SERIALID_INDEX));
        	setCompanyNO(c.getString(READ_COMPANY_COMPANYNO_INDEX));
        	setCompanyNM(c.getString(READ_COMPANY_COMPANYNM_INDEX));
        	setAddress(c.getString(READ_COMPANY_ADDRESS_INDEX));
        	setTelNo(c.getString(READ_COMPANY_TELNO_INDEX));
        	setDateFormat(c.getString(READ_COMPANY_DATEFORMAT_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

	public Company insertCompany(LikDBAdapter adapter) {
		  InsertHelper ih = adapter.getInsertHelper(Company.TABLE_NAME);
		  ih.prepareForInsert();
        ih.bind(2, getCompanyID());
        ih.bind(3, getUserNO());
        ih.bind(4, getCompanyNO());
        ih.bind(5, getCompanyNM());
        ih.bind(6, getAddress());
        ih.bind(7, getTelNo());
        ih.bind(8, getDateFormat());
        ih.bind(9, getCompanyParent());
  	    long rid = ih.execute();
        if(rid != -1) setRid(0);
		return this;
	}

	public Company insertCompanyNoTransaction(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_NAME_COMPANYID, getCompanyID());
        initialValues.put(COLUMN_NAME_USERNO, getUserNO());
        initialValues.put(COLUMN_NAME_COMPANYNO, getCompanyNO());
        initialValues.put(COLUMN_NAME_COMPANYNM, getCompanyNM());
        initialValues.put(COLUMN_NAME_ADDRESS, getAddress());
        initialValues.put(COLUMN_NAME_TELNO, getTelNo());
        initialValues.put(COLUMN_NAME_DATEFORMAT, getDateFormat());
        initialValues.put(COLUMN_NAME_COMPANYPARENT, getCompanyParent());
        long rid = db.insert(TABLE_NAME, null, initialValues);
        setRid(rid);
        closedb(adapter);
		return this;
	}

	public Company updateCompany(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();

        updateValues.put(COLUMN_NAME_COMPANYNO, getCompanyNO());
        updateValues.put(COLUMN_NAME_COMPANYNM, getCompanyNM());
        updateValues.put(COLUMN_NAME_ADDRESS, getAddress());
        updateValues.put(COLUMN_NAME_TELNO, getTelNo());
        updateValues.put(COLUMN_NAME_DATEFORMAT, getDateFormat());
        updateValues.put(COLUMN_NAME_COMPANYPARENT, getCompanyParent());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(TABLE_NAME, updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); 
        closedb(adapter);
		return this;		
	}
	
	public Company deleteCompany(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SERIALID+"="+getSerialID();
		if(isDebug) Log.d(TAG,whereClause);
		//String[] whereArgs = {String.valueOf(getSerialID())};
		int rid = db.delete(TABLE_NAME, whereClause, null);
        setRid(rid);
		if(rid==0) setRid(-1); 
        closedb(adapter);
		return this;

	}

	@Override
	public boolean processDownload(LikDBAdapter adapter, Map<String, String> detail, boolean isOnlyInsert) {
		boolean result = true;
		String flag = detail.get(FLAG_KEY);
		setCompanyID(Integer.parseInt(detail.get(COLUMN_NAME_COMPANYID)));
		setUserNO(detail.get(COLUMN_NAME_USERNO));
		if(!isOnlyInsert) getCompanyByKey(adapter);
		setCompanyNO(detail.get(COLUMN_NAME_COMPANYNO));
		setCompanyNM(detail.get(COLUMN_NAME_COMPANYNM));
		setAddress(detail.get(COLUMN_NAME_ADDRESS));
		setTelNo(detail.get(COLUMN_NAME_TELNO));
		setDateFormat(detail.get(COLUMN_NAME_DATEFORMAT));
		setCompanyParent(MainMenuActivity.omCurrentSysProfile.getCompanyNo());
	
		Log.d(TAG,"MainMenuActivity.companyParent="+getCompanyParent());
		if(isOnlyInsert) {
			insertCompany(adapter);
			UserCompy omUC = new UserCompy();
			omUC.setAccountNo(getUserNO());
			omUC.setCompanyID(getCompanyID());
			omUC.setCompanyParent(MainMenuActivity.omCurrentSysProfile.getCompanyNo());
			omUC.findByKey(adapter);
			if(omUC.getRid()<0) 
				omUC.insertUserCompyFromCompany(adapter,this);
		} else {
			if(getRid()<0) {
				insertCompanyNoTransaction(adapter);
				UserCompy omUC = new UserCompy();
				omUC.insertUserCompyFromCompanyNoTransaction(adapter,this);
			} else {
				if(flag.equals(FLAG_DELETE)) doDelete(adapter);
				else updateCompany(adapter);
			}
		}
		if(getRid()<0) result = false;
		return result;
	}

	@Override
	public Company doInsert(LikDBAdapter adapter) {
		return insertCompany(adapter);
	}

	@Override
	public Company doUpdate(LikDBAdapter adapter) {
		return updateCompany(adapter);
	}

	@Override
	public Company doDelete(LikDBAdapter adapter) {
		return deleteCompany(adapter);
	}

	@Override
	public Company findByKey(LikDBAdapter adapter) {
		return getCompanyByKey(adapter);
	}

	@Override
	public Company queryBySerialID(LikDBAdapter adapter) {
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
				READ_COMPANY_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_COMPANY_SERIALID_INDEX));
    		setCompanyID(c.getInt(READ_COMPANY_COMPANYID_INDEX));
    		setUserNO(c.getString(READ_COMPANY_USERNO_INDEX));
        	setCompanyNO(c.getString(READ_COMPANY_COMPANYNO_INDEX));
        	setCompanyNM(c.getString(READ_COMPANY_COMPANYNM_INDEX));
        	setAddress(c.getString(READ_COMPANY_ADDRESS_INDEX));
        	setTelNo(c.getString(READ_COMPANY_TELNO_INDEX));
        	setDateFormat(c.getString(READ_COMPANY_DATEFORMAT_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

}
