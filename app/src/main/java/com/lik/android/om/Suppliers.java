package com.lik.android.om;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;

public class Suppliers extends BaseSuppliers
	implements ProcessDownloadInterface, Comparator<Suppliers> {

	private static final long serialVersionUID = -1031274889320524742L;

	@Override
	public Suppliers doInsert(LikDBAdapter adapter) {
		getdb(adapter);
		  InsertHelper ih = adapter.getInsertHelper(getTableName());
		  ih.prepareForInsert();
        ih.bind(2, getCompanyID());
        ih.bind(3, getClassify());
        ih.bind(4, getSupplierNO());
        ih.bind(5, getSupplierNM());
        ih.bind(6, getVersionNo());
  	    long rid = ih.execute();
        if(rid != -1) setRid(0);
		return this;
	}

//	@Override
//	public Suppliers doInsert(LikDBAdapter adapter) {
//		SQLiteDatabase db = getdb(adapter);
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(COLUMN_NAME_COMPANYID, getCompanyID());
//        initialValues.put(COLUMN_NAME_CLASSIFY, getClassify());
//        initialValues.put(COLUMN_NAME_SUPPLIERNO, getSupplierNO());
//        initialValues.put(COLUMN_NAME_SUPPLIERNM, getSupplierNM());
//        initialValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
//        long rid = db.insert(TABLE_NAME, null, initialValues);
//        setRid(rid);
////        db.close();
//        closedb(adapter);
//		return this;
//	}

	@Override
	public Suppliers doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();

        updateValues.put(COLUMN_NAME_SUPPLIERNM, getSupplierNM());
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
	public Suppliers doDelete(LikDBAdapter adapter) {
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
	public Suppliers findByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_CLASSIFY+"='"+getClassify()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_SUPPLIERNO+"='"+getSupplierNO()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SUPPLIER_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_SUPPLIER_SERIALID_INDEX));
        	setSupplierNM(c.getString(READ_SUPPLIER_SUPPLIERNM_INDEX));
            setVersionNo(c.getString(READ_SUPPLIER_VERSIONNO_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public Suppliers queryBySerialID(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_SERIALID+"="+getCompanyID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SUPPLIER_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_SUPPLIER_SERIALID_INDEX));
        	setCompanyID(c.getInt(READ_SUPPLIER_COMPANYID_INDEX));
        	setClassify(c.getString(READ_SUPPLIER_CLASSIFY_INDEX));
        	setSupplierNO(c.getString(READ_SUPPLIER_SUPPLIERNO_INDEX));
        	setSupplierNM(c.getString(READ_SUPPLIER_SUPPLIERNM_INDEX));
            setVersionNo(c.getString(READ_SUPPLIER_VERSIONNO_INDEX));
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
		setClassify(detail.get(COLUMN_NAME_CLASSIFY).trim());
		setSupplierNO(detail.get(COLUMN_NAME_SUPPLIERNO));
		if(!isOnlyInsert) findByKey(adapter);
		setSupplierNM(detail.get(COLUMN_NAME_SUPPLIERNM));
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
	
	public List<Suppliers> queryByCompanyIDClassify(LikDBAdapter adapter) {
		List<Suppliers> result = new ArrayList<Suppliers>();
		TreeSet<Suppliers> ts = new TreeSet<Suppliers>(this);
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		if(getClassify() != null) qb.appendWhere(" and "+COLUMN_NAME_CLASSIFY+"='"+getClassify()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SUPPLIER_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	Suppliers om = new Suppliers();
            	om.setSerialID(c.getInt(READ_SUPPLIER_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_SUPPLIER_COMPANYID_INDEX));
            	om.setClassify(c.getString(READ_SUPPLIER_CLASSIFY_INDEX));
                om.setSupplierNO(c.getString(READ_SUPPLIER_SUPPLIERNO_INDEX));
                om.setSupplierNM(c.getString(READ_SUPPLIER_SUPPLIERNM_INDEX));
                om.setVersionNo(c.getString(READ_SUPPLIER_VERSIONNO_INDEX));
                om.setRid(0);
//            	result.add(om);
            	ts.add(om);
            } while(c.moveToNext());
        }
//        db.close();
        for(Suppliers omS : ts) {
        	result.add(omS);
        }
        c.close();
        closedb(adapter);
		return result;
	}

	@Override
	public int compare(Suppliers lhs, Suppliers rhs) {
		return lhs.getSupplierNO().compareTo(rhs.getSupplierNO());
	}

}
