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

public class PrdtUnits extends BasePrdtUnits
	implements ProcessDownloadInterface {

	private static final long serialVersionUID = 2151377057076519991L;

	@Override
	public PrdtUnits doInsert(LikDBAdapter adapter) {
		getdb(adapter);
		  InsertHelper ih = adapter.getInsertHelper(getTableName());
		  ih.prepareForInsert();
        ih.bind(2, getCompanyID());
        ih.bind(3, getItemID());
        ih.bind(4, getUnit());
        ih.bind(5, getBarCode());
        ih.bind(6, getPackages());
        ih.bind(7, getSalePrice());
        ih.bind(8, getSuggestPrice());
        ih.bind(9, getLowestPrice());
        ih.bind(10, getCashPrice());
        ih.bind(11, getRatio());
        ih.bind(12, getVersionNo());
        ih.bind(13, getCashLowestPrice());
        ih.bind(14, getSaleCost());
        if(getLowestSPrice()!=null) ih.bind(15, getLowestSPrice());
        if(getLowestCPrice()!=null) ih.bind(16, getLowestCPrice());
  	    long rid = ih.execute();
        if(rid != -1) setRid(0);
		return this;
	}

//	@Override
//	public PrdtUnits doInsert(LikDBAdapter adapter) {
//		SQLiteDatabase db = getdb(adapter);
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(COLUMN_NAME_COMPANYID, getCompanyID());
//        initialValues.put(COLUMN_NAME_ITEMID, getItemID());
//        initialValues.put(COLUMN_NAME_UNIT, getUnit());
//        initialValues.put(COLUMN_NAME_BARCODE, getBarCode());
//        initialValues.put(COLUMN_NAME_PACKAGES, getPackages());
//        initialValues.put(COLUMN_NAME_SALEPRICE, getSalePrice());
//        initialValues.put(COLUMN_NAME_SUGGESTPRICE, getSuggestPrice());
//        initialValues.put(COLUMN_NAME_LOWESTPRICE, getLowestPrice());
//        initialValues.put(COLUMN_NAME_CASHPRICE, getCashPrice());
//        initialValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
//        long rid = db.insert(TABLE_NAME, null, initialValues);
//        setRid(rid);
////        db.close();
//        closedb(adapter);
//		return this;
//	}

	@Override
	public PrdtUnits doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_BARCODE, getBarCode());
        updateValues.put(COLUMN_NAME_PACKAGES, getPackages());
        updateValues.put(COLUMN_NAME_SALEPRICE, getSalePrice());
        updateValues.put(COLUMN_NAME_SUGGESTPRICE, getSuggestPrice());
        updateValues.put(COLUMN_NAME_LOWESTPRICE, getLowestPrice());
        updateValues.put(COLUMN_NAME_CASHPRICE, getCashPrice());
        updateValues.put(COLUMN_NAME_RATIO, getRatio());
        updateValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
        updateValues.put(COLUMN_NAME_CASHLOWESTPRICE, getCashLowestPrice());
        updateValues.put(COLUMN_NAME_SALECOST, getSaleCost());
        updateValues.put(COLUMN_NAME_LOWESTSPRICE, getLowestSPrice());
        updateValues.put(COLUMN_NAME_LOWESTCPRICE, getLowestCPrice());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(getTableName(), updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
//        db.close();
        closedb(adapter);
		return this;		
	}

	@Override
	public PrdtUnits doDelete(LikDBAdapter adapter) {
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
	public PrdtUnits findByKey(LikDBAdapter adapter) {
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
				READ_PRDTUNITS_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_PRDTUNITS_SERIALID_INDEX));
        	setBarCode(c.getString(READ_PRDTUNITS_BARCODE_INDEX));
        	setPakages(c.getInt(READ_PRDTUNITS_PACKAGES_INDEX));
        	setSalePrice(c.getDouble(READ_PRDTUNITS_SALEPRICE_INDEX));
        	setSuggestPrice(c.getDouble(READ_PRDTUNITS_SUGGESTPRICE_INDEX));
        	setLowestPrice(c.getDouble(READ_PRDTUNITS_LOWESTPRICE));
        	setCashPrice(c.getDouble(READ_PRDTUNITS_CASHPRICE_INDEX));
        	setRatio(c.getDouble(READ_PRDTUNITS_RATIO_INDEX));
            setVersionNo(c.getString(READ_PRDTUNITS_VERSIONNO_INDEX));
            setCashLowestPrice(c.getDouble(READ_PRDTUNITS_CASHLOWESTPRICE_INDEX));
            setSaleCost(c.getDouble(READ_PRDTUNITS_SALECOST_INDEX));
        	if(c.getString(READ_PRDTUNITS_LOWESTSPRICE_INDEX)!=null) setLowestSPrice(c.getDouble(READ_PRDTUNITS_LOWESTSPRICE_INDEX));
        	if(c.getString(READ_PRDTUNITS_LOWESTCPRICE_INDEX)!=null) setLowestCPrice(c.getDouble(READ_PRDTUNITS_LOWESTCPRICE_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public PrdtUnits queryBySerialID(LikDBAdapter adapter) {
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
				READ_PRDTUNITS_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_PRDTUNITS_SERIALID_INDEX));
    		setCompanyID(c.getInt(READ_PRDTUNITS_COMPANYID_INDEX));
    		setItemID(c.getInt(READ_PRDTUNITS_ITEMID_INDEX));
    		setUnit(c.getString(READ_PRDTUNITS_UNIT_INDEX));
        	setBarCode(c.getString(READ_PRDTUNITS_BARCODE_INDEX));
        	setPakages(c.getInt(READ_PRDTUNITS_PACKAGES_INDEX));
        	setSalePrice(c.getDouble(READ_PRDTUNITS_SALEPRICE_INDEX));
        	setSuggestPrice(c.getDouble(READ_PRDTUNITS_SUGGESTPRICE_INDEX));
        	setLowestPrice(c.getDouble(READ_PRDTUNITS_LOWESTPRICE));
        	setCashPrice(c.getDouble(READ_PRDTUNITS_CASHPRICE_INDEX));
        	setRatio(c.getDouble(READ_PRDTUNITS_RATIO_INDEX));
            setVersionNo(c.getString(READ_PRDTUNITS_VERSIONNO_INDEX));
            setCashLowestPrice(c.getDouble(READ_PRDTUNITS_CASHLOWESTPRICE_INDEX));
            setSaleCost(c.getDouble(READ_PRDTUNITS_SALECOST_INDEX));
        	if(c.getString(READ_PRDTUNITS_LOWESTSPRICE_INDEX)!=null) setLowestSPrice(c.getDouble(READ_PRDTUNITS_LOWESTSPRICE_INDEX));
        	if(c.getString(READ_PRDTUNITS_LOWESTCPRICE_INDEX)!=null) setLowestCPrice(c.getDouble(READ_PRDTUNITS_LOWESTCPRICE_INDEX));
            setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

	public List<PrdtUnits> getProductsByBarCode(LikDBAdapter adapter) {
		List<PrdtUnits> result = new ArrayList<PrdtUnits>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_BARCODE+"='"+getBarCode()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PRDTUNITS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	PrdtUnits om = new PrdtUnits();
            	om.setSerialID(c.getInt(READ_PRDTUNITS_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_PRDTUNITS_COMPANYID_INDEX));
            	om.setItemID(c.getInt(READ_PRDTUNITS_ITEMID_INDEX));
            	om.setUnit(c.getString(READ_PRDTUNITS_UNIT_INDEX));
            	om.setBarCode(c.getString(READ_PRDTUNITS_BARCODE_INDEX));
            	om.setPakages(c.getInt(READ_PRDTUNITS_PACKAGES_INDEX));
            	om.setSalePrice(c.getDouble(READ_PRDTUNITS_SALEPRICE_INDEX));
            	om.setSuggestPrice(c.getDouble(READ_PRDTUNITS_SUGGESTPRICE_INDEX));
            	om.setLowestPrice(c.getDouble(READ_PRDTUNITS_LOWESTPRICE));
            	om.setCashPrice(c.getDouble(READ_PRDTUNITS_CASHPRICE_INDEX));
            	om.setRatio(c.getDouble(READ_PRDTUNITS_RATIO_INDEX));
            	om.setVersionNo(c.getString(READ_PRDTUNITS_VERSIONNO_INDEX));
            	om.setCashLowestPrice(c.getDouble(READ_PRDTUNITS_CASHLOWESTPRICE_INDEX));
            	om.setSaleCost(c.getDouble(READ_PRDTUNITS_SALECOST_INDEX));
            	if(c.getString(READ_PRDTUNITS_LOWESTSPRICE_INDEX)!=null) om.setLowestSPrice(c.getDouble(READ_PRDTUNITS_LOWESTSPRICE_INDEX));
            	if(c.getString(READ_PRDTUNITS_LOWESTCPRICE_INDEX)!=null) om.setLowestCPrice(c.getDouble(READ_PRDTUNITS_LOWESTCPRICE_INDEX));
            	om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
        }
//        db.close();
        c.close();
        closedb(adapter);
		return result;
	}

	public boolean isExistsBarCode(LikDBAdapter adapter) {
		boolean result = false;
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_BARCODE+" like '"+getBarCode()+"%'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PRDTUNITS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
        	result = true;
        }
        c.close();
        closedb(adapter);
		return result;
	}

	@Override
	public boolean processDownload(LikDBAdapter adapter,
			Map<String, String> detail, boolean isOnlyInsert) {
		boolean result = true;
		String flag = detail.get(FLAG_KEY);
		setCompanyID(Integer.parseInt(detail.get(COLUMN_NAME_COMPANYID)));
		setItemID(Integer.parseInt(detail.get(COLUMN_NAME_ITEMID)));
		setUnit(detail.get(COLUMN_NAME_UNIT));
		if(!isOnlyInsert) findByKey(adapter);
		setBarCode(detail.get(COLUMN_NAME_BARCODE));
		setPakages(Integer.parseInt(detail.get(COLUMN_NAME_PACKAGES)));
		setSalePrice(Double.parseDouble(detail.get(COLUMN_NAME_SALEPRICE)));
		setSuggestPrice(Double.parseDouble(detail.get(COLUMN_NAME_SUGGESTPRICE)));
		setLowestPrice(Double.parseDouble(detail.get(COLUMN_NAME_LOWESTPRICE)));
		setCashPrice(Double.parseDouble(detail.get(COLUMN_NAME_CASHPRICE)));
		setRatio(Double.parseDouble(detail.get(COLUMN_NAME_RATIO)));
		setVersionNo(detail.get(COLUMN_NAME_VERSIONNO));
		setCashLowestPrice(Double.parseDouble(detail.get(COLUMN_NAME_CASHLOWESTPRICE)));
		setSaleCost(Double.parseDouble(detail.get(COLUMN_NAME_SALECOST)));
		if(detail.get(COLUMN_NAME_LOWESTSPRICE)!=null) setLowestSPrice(Double.parseDouble(detail.get(COLUMN_NAME_LOWESTSPRICE)));
		else setLowestSPrice(null);
		if(detail.get(COLUMN_NAME_LOWESTCPRICE)!=null) setLowestCPrice(Double.parseDouble(detail.get(COLUMN_NAME_LOWESTCPRICE)));
		else setLowestCPrice(null);
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
