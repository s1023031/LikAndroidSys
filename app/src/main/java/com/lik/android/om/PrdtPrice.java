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

public class PrdtPrice extends BasePrdtPrice
	implements ProcessDownloadInterface {

	private static final long serialVersionUID = -2944230436685158684L;

	@Override
	public PrdtPrice doInsert(LikDBAdapter adapter) {
		getdb(adapter);
		  InsertHelper ih = adapter.getInsertHelper(getTableName());
		  ih.prepareForInsert();
        ih.bind(2, getCompanyID());
        ih.bind(3, getItemID());
        ih.bind(4, getUnit());
        ih.bind(5, getGrade());
        ih.bind(6, getStdPrice());
        ih.bind(7, getCashPrice());
        ih.bind(8, getDiscRate());
        if(getLowestSPrice()!=null) ih.bind(9, getLowestSPrice());
        if(getLowestCPrice()!=null) ih.bind(10, getLowestCPrice());
        ih.bind(11, getVersionNo());
  	    long rid = ih.execute();
        if(rid != -1) setRid(0);
		return this;
	}

	//	@Override
//	public PrdtPrice doInsert(LikDBAdapter adapter) {
//		SQLiteDatabase db = getdb(adapter);
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(COLUMN_NAME_COMPANYID, getCompanyID());
//        initialValues.put(COLUMN_NAME_ITEMID, getItemID());
//        initialValues.put(COLUMN_NAME_UNIT, getUnit());
//        initialValues.put(COLUMN_NAME_GRADE, getGrade());
//        initialValues.put(COLUMN_NAME_STDPRICE, getStdPrice());
//        initialValues.put(COLUMN_NAME_CASHPRICE, getCashPrice());
//        initialValues.put(COLUMN_NAME_DISCRATE, getDiscRate());
//        initialValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
//        long rid = db.insert(TABLE_NAME, null, initialValues);
//        setRid(rid);
////        db.close();
//        closedb(adapter);
//		return this;
//	}

	@Override
	public PrdtPrice doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_STDPRICE, getStdPrice());
        updateValues.put(COLUMN_NAME_CASHPRICE, getCashPrice());
        updateValues.put(COLUMN_NAME_DISCRATE, getDiscRate());
        updateValues.put(COLUMN_NAME_LOWESTSPRICE, getLowestSPrice());
        updateValues.put(COLUMN_NAME_LOWESTCPRICE, getLowestCPrice());
        updateValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(getTableName(), updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
//        db.close();
        closedb(adapter);
		return this;		
	}

	@Override
	public PrdtPrice doDelete(LikDBAdapter adapter) {
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
	public PrdtPrice findByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_ITEMID+"="+getItemID());
		qb.appendWhere(" and "+COLUMN_NAME_UNIT+"='"+getUnit()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_GRADE+"='"+getGrade()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PRDTPRICE_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_PRDTPRICE_SERIALID_INDEX));
        	setStdPrice(c.getDouble(READ_PRDTPRICE_STDPRICE_INDEX));
        	setCashPrice(c.getDouble(READ_PRDTPRICE_CASHPRICE_INDEX));
        	setDiscRate(c.getDouble(READ_PRDTPRICE_DISCRATE_INDEX));
        	if(c.getString(READ_PRDTPRICE_LOWESTSPRICE_INDEX)!=null) setLowestSPrice(c.getDouble(READ_PRDTPRICE_LOWESTSPRICE_INDEX));
        	if(c.getString(READ_PRDTPRICE_LOWESTCPRICE_INDEX)!=null) setLowestCPrice(c.getDouble(READ_PRDTPRICE_LOWESTCPRICE_INDEX));
            setVersionNo(c.getString(READ_PRDTPRICE_VERSIONNO_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public PrdtPrice queryBySerialID(LikDBAdapter adapter) {
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
				READ_PRDTPRICE_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_PRDTPRICE_SERIALID_INDEX));
    		setCompanyID(c.getInt(READ_PRDTPRICE_COMPANYID_INDEX));
    		setItemID(c.getInt(READ_PRDTPRICE_ITEMID_INDEX));
    		setUnit(c.getString(READ_PRDTPRICE_UNIT_INDEX));
    		setGrade(c.getString(READ_PRDTPRICE_GRADE_INDEX));
        	setStdPrice(c.getDouble(READ_PRDTPRICE_STDPRICE_INDEX));
        	setCashPrice(c.getDouble(READ_PRDTPRICE_CASHPRICE_INDEX));
        	setDiscRate(c.getDouble(READ_PRDTPRICE_DISCRATE_INDEX));
        	if(c.getString(READ_PRDTPRICE_LOWESTSPRICE_INDEX)!=null) setLowestSPrice(c.getDouble(READ_PRDTPRICE_LOWESTSPRICE_INDEX));
        	if(c.getString(READ_PRDTPRICE_LOWESTCPRICE_INDEX)!=null) setLowestCPrice(c.getDouble(READ_PRDTPRICE_LOWESTCPRICE_INDEX));
            setVersionNo(c.getString(READ_PRDTPRICE_VERSIONNO_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public boolean processDownload(LikDBAdapter adapter,
			Map<String, String> detail, boolean isOnlyInsert) {
		boolean result = true;
		String flag = detail.get(FLAG_KEY);
		setCompanyID(Integer.parseInt(detail.get(COLUMN_NAME_COMPANYID)));
		setItemID(Integer.parseInt(detail.get(COLUMN_NAME_ITEMID)));
		setUnit(detail.get(COLUMN_NAME_UNIT));
		setGrade(detail.get(COLUMN_NAME_GRADE));
		if(!isOnlyInsert) findByKey(adapter);
		setStdPrice(Double.parseDouble(detail.get(COLUMN_NAME_STDPRICE)));
		setCashPrice(Double.parseDouble(detail.get(COLUMN_NAME_CASHPRICE)));
		setDiscRate(Double.parseDouble(detail.get(COLUMN_NAME_DISCRATE)));
		if(detail.get(COLUMN_NAME_LOWESTSPRICE)!=null) setLowestSPrice(Double.parseDouble(detail.get(COLUMN_NAME_LOWESTSPRICE)));
		else setLowestSPrice(null);
		if(detail.get(COLUMN_NAME_LOWESTCPRICE)!=null) setLowestCPrice(Double.parseDouble(detail.get(COLUMN_NAME_LOWESTCPRICE)));
		else setLowestCPrice(null);
		setVersionNo(detail.get(COLUMN_NAME_VERSIONNO));
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
	
	public List<PrdtPrice> findByUnit(LikDBAdapter adapter) {
		List<PrdtPrice> result = new ArrayList<PrdtPrice>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_ITEMID+"="+getItemID());
		qb.appendWhere(" and "+COLUMN_NAME_UNIT+"='"+getUnit()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PRDTPRICE_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
		if(c == null) {
			setRid(-1);
			return result;
		} 	
        boolean notEmpty = c.moveToFirst();
        if(notEmpty) {
        	do {
        		PrdtPrice om = new PrdtPrice();
        		om.setSerialID(c.getInt(READ_PRDTPRICE_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_PRDTPRICE_COMPANYID_INDEX));
        		om.setItemID(c.getInt(READ_PRDTPRICE_ITEMID_INDEX));
        		om.setUnit(c.getString(READ_PRDTPRICE_UNIT_INDEX));
        		om.setGrade(c.getString(READ_PRDTPRICE_GRADE_INDEX));
	        	om.setStdPrice(c.getDouble(READ_PRDTPRICE_STDPRICE_INDEX));
	        	om.setCashPrice(c.getDouble(READ_PRDTPRICE_CASHPRICE_INDEX));
	        	om.setDiscRate(c.getDouble(READ_PRDTPRICE_DISCRATE_INDEX));
	        	if(c.getString(READ_PRDTPRICE_LOWESTSPRICE_INDEX)!=null) om.setLowestSPrice(c.getDouble(READ_PRDTPRICE_LOWESTSPRICE_INDEX));
	        	if(c.getString(READ_PRDTPRICE_LOWESTCPRICE_INDEX)!=null) om.setLowestCPrice(c.getDouble(READ_PRDTPRICE_LOWESTCPRICE_INDEX));
	        	om.setVersionNo(c.getString(READ_PRDTPRICE_VERSIONNO_INDEX));
	        	om.setRid(0);
	        	result.add(om);
        	} while(c.moveToNext());
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return result;
	}


}
