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

public class SEe5 extends BaseSEe5 implements ProcessDownloadInterface {

	private static final long serialVersionUID = -4528140517762891591L;

	@Override
	public SEe5 doInsert(LikDBAdapter adapter) {
		getdb(adapter);
		InsertHelper ih = adapter.getInsertHelper(getTableName());
		ih.prepareForInsert();
		ih.bind(2, getSALE_SALES_NO());
		ih.bind(3, getSALE_NAME());
		ih.bind(4, getS_AMOUNT());
		ih.bind(5, getS_RETURNED());
		ih.bind(6, getS_BAD_DEBT());
		ih.bind(7, getS_SAMPLE_COST());
		ih.bind(8, getS_SELL_COST());
		ih.bind(9, getS_SALE_COST());
		ih.bind(10, getS_BACK_COST());
		ih.bind(11, getS_SEND_COST());
		ih.bind(12, getS_RECEIVABLE());
		ih.bind(13, getS_GROSS_PROFIT());
		ih.bind(14, getS_RATE());
		ih.bind(15, getS_RETURN_RATE());
	    long rid = ih.execute();
	    if(rid != -1) setRid(0);
		return this;
	}

	@Override
	public SEe5 doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_SALE_SALES_NO, getSALE_SALES_NO());
        updateValues.put(COLUMN_NAME_SALE_NAME, getSALE_NAME());
        updateValues.put(COLUMN_NAME_S_AMOUNT, getS_AMOUNT());
        updateValues.put(COLUMN_NAME_S_RETURNED, getS_RETURNED());
        updateValues.put(COLUMN_NAME_S_BAD_DEBT, getS_BAD_DEBT());
        updateValues.put(COLUMN_NAME_S_SAMPLE_COST, getS_SAMPLE_COST());
        updateValues.put(COLUMN_NAME_S_SELL_COST, getS_SELL_COST());
        updateValues.put(COLUMN_NAME_S_SALE_COST, getS_SALE_COST());
        updateValues.put(COLUMN_NAME_S_BACK_COST, getS_BACK_COST());
        updateValues.put(COLUMN_NAME_S_SEND_COST, getS_SEND_COST());
        updateValues.put(COLUMN_NAME_S_RECEIVABLE, getS_RECEIVABLE());
        updateValues.put(COLUMN_NAME_S_GROSS_PROFIT, getS_GROSS_PROFIT());
        updateValues.put(COLUMN_NAME_S_RATE, getS_RATE());
        updateValues.put(COLUMN_NAME_S_RETURN_RATE, getS_RETURN_RATE());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(getTableName(), updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
        closedb(adapter);
		return this;		
	}

	@Override
	public SEe5 doDelete(LikDBAdapter adapter) {
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
	public SEe5 findByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_SALE_SALES_NO+"="+getSALE_SALES_NO());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SEE5_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_SEE5_SERIALID_INDEX));
        	setSALE_SALES_NO(c.getString(READ_SEE5_SALE_SALES_NO_INDEX));
        	setSALE_NAME(c.getString(READ_SEE5_SALE_NAME_INDEX));
        	setS_AMOUNT(c.getDouble(READ_SEE5_S_AMOUNT_INDEX));
        	setS_RETURNED(c.getDouble(READ_SEE5_S_RETURNED_INDEX));
        	setS_BAD_DEBT(c.getDouble(READ_SEE5_S_BAD_DEBT_INDEX));
        	setS_SAMPLE_COST(c.getDouble(READ_SEE5_S_SAMPLE_COST_INDEX));
        	setS_SELL_COST(c.getDouble(READ_SEE5_S_SELL_COST_INDEX));
        	setS_SALE_COST(c.getDouble(READ_SEE5_S_SALE_COST_INDEX));
        	setS_BACK_COST(c.getDouble(READ_SEE5_S_BACK_COST_INDEX));
        	setS_SEND_COST(c.getDouble(READ_SEE5_S_SEND_COST_INDEX));
        	setS_RECEIVABLE(c.getDouble(READ_SEE5_S_RECEIVABLE_INDEX));
        	setS_GROSS_PROFIT(c.getDouble(READ_SEE5_S_GROSS_PROFIT_INDEX));
        	setS_RATE(c.getDouble(READ_SEE5_S_RATE_INDEX));
        	setS_RETURN_RATE(c.getDouble(READ_SEE5_S_RETURN_RATE_INDEX));
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public SEe5 queryBySerialID(LikDBAdapter adapter) {
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
				READ_SEE5_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_SEE5_SERIALID_INDEX));
        	setSALE_SALES_NO(c.getString(READ_SEE5_SALE_SALES_NO_INDEX));
        	setSALE_NAME(c.getString(READ_SEE5_SALE_NAME_INDEX));
        	setS_AMOUNT(c.getDouble(READ_SEE5_S_AMOUNT_INDEX));
        	setS_RETURNED(c.getDouble(READ_SEE5_S_RETURNED_INDEX));
        	setS_BAD_DEBT(c.getDouble(READ_SEE5_S_BAD_DEBT_INDEX));
        	setS_SAMPLE_COST(c.getDouble(READ_SEE5_S_SAMPLE_COST_INDEX));
        	setS_SELL_COST(c.getDouble(READ_SEE5_S_SELL_COST_INDEX));
        	setS_SALE_COST(c.getDouble(READ_SEE5_S_SALE_COST_INDEX));
        	setS_BACK_COST(c.getDouble(READ_SEE5_S_BACK_COST_INDEX));
        	setS_SEND_COST(c.getDouble(READ_SEE5_S_SEND_COST_INDEX));
        	setS_RECEIVABLE(c.getDouble(READ_SEE5_S_RECEIVABLE_INDEX));
        	setS_GROSS_PROFIT(c.getDouble(READ_SEE5_S_GROSS_PROFIT_INDEX));
        	setS_RATE(c.getDouble(READ_SEE5_S_RATE_INDEX));
        	setS_RETURN_RATE(c.getDouble(READ_SEE5_S_RETURN_RATE_INDEX));
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
		setSALE_SALES_NO(detail.get(COLUMN_NAME_SALE_SALES_NO));
		if(!isOnlyInsert) findByKey(adapter);
		setSALE_NAME(detail.get(COLUMN_NAME_SALE_NAME));
		setS_AMOUNT(Double.parseDouble(detail.get(COLUMN_NAME_S_AMOUNT)));
		setS_RETURNED(Double.parseDouble(detail.get(COLUMN_NAME_S_RETURNED)));
		setS_BAD_DEBT(Double.parseDouble(detail.get(COLUMN_NAME_S_BAD_DEBT)));
		setS_SAMPLE_COST(Double.parseDouble(detail.get(COLUMN_NAME_S_SAMPLE_COST)));
		setS_SELL_COST(Double.parseDouble(detail.get(COLUMN_NAME_S_SELL_COST)));
		setS_SALE_COST(Double.parseDouble(detail.get(COLUMN_NAME_S_SALE_COST)));
		setS_BACK_COST(Double.parseDouble(detail.get(COLUMN_NAME_S_BACK_COST)));
		setS_SEND_COST(Double.parseDouble(detail.get(COLUMN_NAME_S_SEND_COST)));
		setS_RECEIVABLE(Double.parseDouble(detail.get(COLUMN_NAME_S_RECEIVABLE)));
		setS_GROSS_PROFIT(Double.parseDouble(detail.get(COLUMN_NAME_S_GROSS_PROFIT)));
		setS_RATE(Double.parseDouble(detail.get(COLUMN_NAME_S_RATE)));
		setS_RETURN_RATE(Double.parseDouble(detail.get(COLUMN_NAME_S_RETURN_RATE)));
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

	public List<SEe5> queryAllSEe5(LikDBAdapter adapter) {
		List<SEe5> result = new ArrayList<SEe5>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SEE5_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
		if(c != null && c.getCount()>0) {
			c.moveToFirst();
            do {
            	SEe5 om = new SEe5();
            	om.setSerialID(c.getInt(READ_SEE5_SERIALID_INDEX));
            	om.setSALE_SALES_NO(c.getString(READ_SEE5_SALE_SALES_NO_INDEX));
            	om.setSALE_NAME(c.getString(READ_SEE5_SALE_NAME_INDEX));
            	om.setS_AMOUNT(c.getDouble(READ_SEE5_S_AMOUNT_INDEX));
            	om.setS_RETURNED(c.getDouble(READ_SEE5_S_RETURNED_INDEX));
            	om.setS_BAD_DEBT(c.getDouble(READ_SEE5_S_BAD_DEBT_INDEX));
            	om.setS_SAMPLE_COST(c.getDouble(READ_SEE5_S_SAMPLE_COST_INDEX));
            	om.setS_SELL_COST(c.getDouble(READ_SEE5_S_SELL_COST_INDEX));
            	om.setS_SALE_COST(c.getDouble(READ_SEE5_S_SALE_COST_INDEX));
            	om.setS_BACK_COST(c.getDouble(READ_SEE5_S_BACK_COST_INDEX));
            	om.setS_SEND_COST(c.getDouble(READ_SEE5_S_SEND_COST_INDEX));
            	om.setS_RECEIVABLE(c.getDouble(READ_SEE5_S_RECEIVABLE_INDEX));
            	om.setS_GROSS_PROFIT(c.getDouble(READ_SEE5_S_GROSS_PROFIT_INDEX));
            	om.setS_RATE(c.getDouble(READ_SEE5_S_RATE_INDEX));
            	om.setS_RETURN_RATE(c.getDouble(READ_SEE5_S_RETURN_RATE_INDEX));
            	om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
		} 		
        closedb(adapter);
		return result;
		
	}
}
