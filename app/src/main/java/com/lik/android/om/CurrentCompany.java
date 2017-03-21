package com.lik.android.om;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.lik.android.main.LikDBAdapter;

public class CurrentCompany extends BaseCurrentCompany {

	private static final long serialVersionUID = -1344025635457076445L;

	public CurrentCompany getCurrentCompany(LikDBAdapter adapter){
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		//qb.appendWhere(COLUMN_NAME_COMPANYNAME+"='"+getCompanyName()+"'");
		//qb.appendWhere(" and "+COLUMN_NAME_DEPTNO+"='"+getDeptNO()+"'");
		Cursor c = qb.query(
				db,            // The database to query
				READ_CURRENTCOMPANY_PROJECTION,    // The columns to return from the query
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
	        	setSerialID(c.getInt(READ_CURRENTCOMPANY_SERIALID_INDEX));
	        	setCompanyName(c.getString(READ_CURRENTCOMPANY__COMPANYNAME_INDEX));
	        	setDeptNO(c.getString(READ_CURRENTCOMPANY__DEPTNO_INDEX));;
	        	setCompanyNO(c.getString(READ_CURRENTCOMPANY__COMPANYNO_INDEX));;
	        	setRid(0);
	        } else setRid(-1);
	        c.close();
	        closedb(adapter);
		return this;

	}
	
	public CurrentCompany insertCurrentCompany(LikDBAdapter adapter) {
		InsertHelper ih = adapter.getInsertHelper(CurrentCompany.TABLE_NAME);
		ih.prepareForInsert();
		ih.bind(2, getCompanyName());
		ih.bind(3, getDeptNO());
		ih.bind(4,getCompanyNO());
		long rid = ih.execute();
		if (rid != -1)
			setRid(0);
		return this;
	}

	public CurrentCompany updateCurrentCompany(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		ContentValues updateValues = new ContentValues();

		// updateValues.put(COLUMN_NAME_SERIALID, getSerialID());
		updateValues.put(COLUMN_NAME_COMPANYNAME, getCompanyName());
		updateValues.put(COLUMN_NAME_DEPTNO, getDeptNO());
		updateValues.put(COLUMN_NAME_COMPANYNO, getCompanyNO());

		// String[] whereArgs = {String.valueOf(getSerialID())};
		// long rid = db.update(TABLE_NAME, updateValues,
		// COLUMN_NAME_SERIALID+"=?", whereArgs);
		long rid = db.update(TABLE_NAME, updateValues, null, null);
		setRid(rid);
		if (rid == 0)
			setRid(-1);
		closedb(adapter);
		return this;
	}

	@Override
	public BaseCurrentCompany doInsert(LikDBAdapter adapter) {
		return insertCurrentCompany(adapter);
	}

	@Override
	public BaseCurrentCompany doUpdate(LikDBAdapter adapter) {
		return updateCurrentCompany(adapter);
	}

	@Override
	public BaseCurrentCompany doDelete(LikDBAdapter adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseCurrentCompany findByKey(LikDBAdapter adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseCurrentCompany queryBySerialID(LikDBAdapter adapter) {
		// TODO Auto-generated method stub
		return null;
	}

}
