package com.lik.android.om;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lik.Constant;
import com.lik.android.main.LikDBAdapter;

public class Orders extends BaseOrders implements ProcessDownloadInterface {

	private static final long serialVersionUID = -8192604458123559463L;

	public Orders getOrdersByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_TABLETSERIALNO+"='"+getTabletSerialNO()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_ORDERID+"="+getOrderID());
		qb.appendWhere(" and "+COLUMN_NAME_VIEWORDER+"="+getViewOrder());
		qb.appendWhere(" and "+COLUMN_NAME_COMPANYID+"="+getCompanyID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_ORDERS_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_ORDERS_SERIALID_INDEX));
        	setUserNO(c.getString(READ_ORDERS_USERNO_INDEX));
            try {
            	if(c.getString(READ_ORDERS_ORDERDT_INDEX) != null) {
            		Date orderDT = sdf.parse(c.getString(READ_ORDERS_ORDERDT_INDEX));
            		setOrderDT(orderDT);
            	}
            } catch(ParseException pe) {
            	setOrderDT(null);
            }
            try {
            	if(c.getString(READ_ORDERS_LASTDT_INDEX) != null) {
            		Date lastDT = sdf.parse(c.getString(READ_ORDERS_LASTDT_INDEX));
            		setLastDT(lastDT);
            	}
            } catch(ParseException pe) {
            	setLastDT(null);
            }
            try {
            	if(c.getString(READ_ORDERS_SELLDT_INDEX) != null) {
            		Date sellDT = sdf.parse(c.getString(READ_ORDERS_SELLDT_INDEX));
            		setSellDT(sellDT);
            	}
            } catch(ParseException pe) {
            	setSellDT(null);
            }
        	setCustomerID(c.getInt(READ_ORDERS_CUSTOMERID_INDEX));
        	setPayKind(c.getInt(READ_ORDERS_PAYKIND_INDEX));
        	if(c.getString(READ_ORDERS_PAYNEXTMONTH_INDEX) != null) setPayNextMonth(c.getInt(READ_ORDERS_PAYNEXTMONTH_INDEX));
        	setSalesID(c.getInt(READ_ORDERS_SALESID_INDEX));
        	setStatus(c.getInt(READ_ORDERS_STATUS_INDEX));
        	setNote1(c.getString(READ_ORDERS_NOTE1_INDEX));
        	setNote2(c.getString(READ_ORDERS_NOTE2_INDEX));
        	setCustomerNO(c.getString(READ_ORDERS_CUSTOMERNO_INDEX));
        	setPdaId(c.getInt(READ_ORDERS_PDAID_INDEX));
        	setUploadFlag(c.getString(READ_ORDERS_UPLOADFLAG_INDEX));
        	setReplyFlag(c.getString(READ_ORDERS_REPLYFLAG_INDEX));
        	if(c.getString(READ_ORDERS_CUSTOMERSTOCK_INDEX) != null) setCustomerStock(c.getInt(READ_ORDERS_CUSTOMERSTOCK_INDEX));
        	if(c.getString(READ_ORDERS_RECEIVEAMT_INDEX) != null) setReceiveAmt(c.getDouble(READ_ORDERS_RECEIVEAMT_INDEX));
            setVersionNo(c.getString(READ_ORDERS_VERSIONNO_INDEX));
        	if(c.getString(READ_ORDERS_DELIVER_VIEWORDER_INDEX) != null) setDeliverViewOrder(c.getInt(READ_ORDERS_DELIVER_VIEWORDER_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}
	
	public Orders insertOrders(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_NAME_TABLETSERIALNO, getTabletSerialNO());
        initialValues.put(COLUMN_NAME_ORDERID, getOrderID());
        initialValues.put(COLUMN_NAME_VIEWORDER, getViewOrder());
        initialValues.put(COLUMN_NAME_COMPANYID, getCompanyID());
        initialValues.put(COLUMN_NAME_USERNO, getUserNO());
        if(getOrderDT() != null) initialValues.put(COLUMN_NAME_ORDERDT, sdf.format(getOrderDT()));
        if(getLastDT() != null) initialValues.put(COLUMN_NAME_LASTDT, sdf.format(getLastDT()));
        if(getSellDT() != null) initialValues.put(COLUMN_NAME_SELLDT, sdf.format(getSellDT()));
        initialValues.put(COLUMN_NAME_CUSTOMERID, getCustomerID());
        initialValues.put(COLUMN_NAME_PAYKIND, getPayKind());
        initialValues.put(COLUMN_NAME_PAYNEXTMONTH, getPayNextMonth());
        initialValues.put(COLUMN_NAME_SALESID, getSalesID());
        initialValues.put(COLUMN_NAME_STATUS, getStatus());
        initialValues.put(COLUMN_NAME_NOTE1, getNote1());
        initialValues.put(COLUMN_NAME_NOTE2, getNote2());
        initialValues.put(COLUMN_NAME_CUSTOMERNO, getCustomerNO());
        initialValues.put(COLUMN_NAME_PDAID, getPdaId());
        initialValues.put(COLUMN_NAME_UPLOADFLAG, getUploadFlag());
        initialValues.put(COLUMN_NAME_REPLYFLAG, getReplyFlag());
        initialValues.put(COLUMN_NAME_CUSTOMERSTOCK, getCustomerStock());
        initialValues.put(COLUMN_NAME_RECEIVEAMT, getReceiveAmt());
        initialValues.put(COLUMN_NAME_DELIVER_VIEWORDER, getDeliverViewOrder());
        initialValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
        initialValues.put(COLUMN_NAME_COMPANYPARENT, getCompanyParent());
        long rid = db.insert(TABLE_NAME, null, initialValues);
        setRid(rid);
//        db.close();
        closedb(adapter);
		return this;
	}

	public Orders updateOrders(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();

        updateValues.put(COLUMN_NAME_USERNO, getUserNO());
        if(getOrderDT() != null) updateValues.put(COLUMN_NAME_ORDERDT, sdf.format(getOrderDT()));
        if(getLastDT() != null) updateValues.put(COLUMN_NAME_LASTDT, sdf.format(getLastDT()));
        if(getSellDT() != null) updateValues.put(COLUMN_NAME_SELLDT, sdf.format(getSellDT()));
        updateValues.put(COLUMN_NAME_CUSTOMERID, getCustomerID());
        updateValues.put(COLUMN_NAME_PAYKIND, getPayKind());
        updateValues.put(COLUMN_NAME_PAYNEXTMONTH, getPayNextMonth());
        updateValues.put(COLUMN_NAME_SALESID, getSalesID());
        updateValues.put(COLUMN_NAME_STATUS, getStatus());
        updateValues.put(COLUMN_NAME_NOTE1, getNote1());
        updateValues.put(COLUMN_NAME_NOTE2, getNote2());
        updateValues.put(COLUMN_NAME_CUSTOMERNO, getCustomerNO());
        updateValues.put(COLUMN_NAME_PDAID, getPdaId());
        updateValues.put(COLUMN_NAME_UPLOADFLAG, getUploadFlag());
        updateValues.put(COLUMN_NAME_REPLYFLAG, getReplyFlag());
        updateValues.put(COLUMN_NAME_CUSTOMERSTOCK, getCustomerStock());
        updateValues.put(COLUMN_NAME_RECEIVEAMT, getReceiveAmt());
        updateValues.put(COLUMN_NAME_DELIVER_VIEWORDER, getDeliverViewOrder());
        updateValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(TABLE_NAME, updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
//        db.close();
        closedb(adapter);
		return this;		
	}
	
	public Orders deleteOrders(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SERIALID+"="+getSerialID();
		if(isDebug) 
			Log.d(TAG,whereClause);

		int rid = db.delete(TABLE_NAME, whereClause, null);
        setRid(rid);
		if(rid==0) setRid(-1); 
        closedb(adapter);
		return this;
	}

	@Override
	public boolean processDownload(LikDBAdapter adapter,
			Map<String, String> detail, boolean isOnlyInsert) {
		boolean result = true;
		String flag = detail.get(FLAG_KEY);
		setTabletSerialNO(detail.get(COLUMN_NAME_TABLETSERIALNO));
		setOrderID(Integer.parseInt(detail.get(COLUMN_NAME_ORDERID)));
		setViewOrder(Integer.parseInt(detail.get(COLUMN_NAME_VIEWORDER)));
		setCompanyID(Integer.parseInt(detail.get(COLUMN_NAME_COMPANYID)));
		if(!isOnlyInsert) getOrdersByKey(adapter);
		setUserNO(detail.get(COLUMN_NAME_USERNO));
		try {
			if(detail.get(COLUMN_NAME_ORDERDT) != null) setOrderDT(sdf2.parse(detail.get(COLUMN_NAME_ORDERDT)));
			if(detail.get(COLUMN_NAME_LASTDT) != null) setLastDT(sdf2.parse(detail.get(COLUMN_NAME_LASTDT)));
			if(detail.get(COLUMN_NAME_SELLDT) != null) setSellDT(sdf2.parse(detail.get(COLUMN_NAME_SELLDT)));
		} catch (ParseException e) {
			Log.e(TAG,e.getMessage());
		}
		setCustomerID(Integer.parseInt(detail.get(COLUMN_NAME_CUSTOMERID)));
		setPayKind(Integer.parseInt(detail.get(COLUMN_NAME_PAYKIND)));
		if(detail.get(COLUMN_NAME_PAYNEXTMONTH) != null) setPayNextMonth(Integer.parseInt(detail.get(COLUMN_NAME_PAYNEXTMONTH)));
		setSalesID(Integer.parseInt(detail.get(COLUMN_NAME_SALESID)));
		setStatus(Integer.parseInt(detail.get(COLUMN_NAME_STATUS)));
		setNote1(detail.get(COLUMN_NAME_NOTE1));
		setNote2(detail.get(COLUMN_NAME_NOTE2));
		setCustomerNO(detail.get(COLUMN_NAME_CUSTOMERNO));
		setPdaId(Integer.parseInt(detail.get(COLUMN_NAME_PDAID)));
		setUploadFlag(detail.get(COLUMN_NAME_UPLOADFLAG));
		setReplyFlag(detail.get(COLUMN_NAME_REPLYFLAG));
		if(detail.get(COLUMN_NAME_CUSTOMERSTOCK) != null) setCustomerStock(Integer.parseInt(detail.get(COLUMN_NAME_CUSTOMERSTOCK)));
		if(detail.get(COLUMN_NAME_RECEIVEAMT) != null) setReceiveAmt(Double.parseDouble(detail.get(COLUMN_NAME_RECEIVEAMT)));
		if(detail.get(COLUMN_NAME_DELIVER_VIEWORDER) != null) setDeliverViewOrder(Integer.parseInt(detail.get(COLUMN_NAME_DELIVER_VIEWORDER)));
		setVersionNo(detail.get(COLUMN_NAME_VERSIONNO));
		if(isOnlyInsert) insertOrders(adapter);
		else {
			if(getRid()<0) insertOrders(adapter);
			else {
				if(flag.equals(FLAG_DELETE)) doDelete(adapter);
				else updateOrders(adapter);
			}
		}
		if(getRid()<0) result = false;
		return result;
	}
	
	public List<Orders> getOrdersByUserNO(LikDBAdapter adapter) {
		List<Orders> result = new ArrayList<Orders>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_USERNO+"='"+getUserNO()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_COMPANYPARENT+"='"+getCompanyParent()+"'");
		if(getCompanyID() != 0) qb.appendWhere(" and "+COLUMN_NAME_COMPANYID+"="+getCompanyID());
		if(getUploadFlag() != null) qb.appendWhere(" and "+COLUMN_NAME_UPLOADFLAG+"='"+getUploadFlag()+"'");

		Cursor c = qb.query(
				db,            // The database to query
				READ_ORDERS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				COLUMN_NAME_UPLOADFLAG+" asc, "+COLUMN_NAME_VIEWORDER+" asc"        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	Orders om = new Orders();
            	om.setSerialID(c.getInt(READ_ORDERS_SERIALID_INDEX));
            	om.setTabletSerialNO(c.getString(READ_ORDERS_TABLETSERIALNO_INDEX));
            	om.setOrderID(c.getInt(READ_ORDERS_ORDERID_INDEX));
            	om.setViewOrder(c.getInt(READ_ORDERS_VIEWORDER_INDEX));
            	om.setCompanyID(c.getInt(READ_ORDERS_COMPANYID_INDEX));
            	om.setUserNO(c.getString(READ_ORDERS_USERNO_INDEX));
                try {
                	if(c.getString(READ_ORDERS_ORDERDT_INDEX) != null) {
                		Date orderDT = sdf.parse(c.getString(READ_ORDERS_ORDERDT_INDEX));
                    	om.setOrderDT(orderDT);
                	}
                } catch(ParseException pe) {
                	om.setOrderDT(null);
                }
                try {
                	if(c.getString(READ_ORDERS_LASTDT_INDEX) != null) {
                		Date lastDT = sdf.parse(c.getString(READ_ORDERS_LASTDT_INDEX));
                		om.setLastDT(lastDT);
                	}
                } catch(ParseException pe) {
                	om.setLastDT(null);
                }
                try {
                	if(c.getString(READ_ORDERS_SELLDT_INDEX) != null) {
                		Date sellDT = sdf.parse(c.getString(READ_ORDERS_SELLDT_INDEX));
                		om.setSellDT(sellDT);
                	}
                } catch(ParseException pe) {
                	om.setSellDT(null);
                }
                om.setCustomerID(c.getInt(READ_ORDERS_CUSTOMERID_INDEX));
                om.setPayKind(c.getInt(READ_ORDERS_PAYKIND_INDEX));
                if(c.getString(READ_ORDERS_PAYNEXTMONTH_INDEX) != null) om.setPayNextMonth(c.getInt(READ_ORDERS_PAYNEXTMONTH_INDEX));
                om.setSalesID(c.getInt(READ_ORDERS_SALESID_INDEX));
                om.setStatus(c.getInt(READ_ORDERS_STATUS_INDEX));
                om.setNote1(c.getString(READ_ORDERS_NOTE1_INDEX));
                om.setNote2(c.getString(READ_ORDERS_NOTE2_INDEX));
                om.setCustomerNO(c.getString(READ_ORDERS_CUSTOMERNO_INDEX));
                om.setPdaId(c.getInt(READ_ORDERS_PDAID_INDEX));
                om.setUploadFlag(c.getString(READ_ORDERS_UPLOADFLAG_INDEX));
                om.setReplyFlag(c.getString(READ_ORDERS_REPLYFLAG_INDEX));
            	if(c.getString(READ_ORDERS_CUSTOMERSTOCK_INDEX) != null) om.setCustomerStock(c.getInt(READ_ORDERS_CUSTOMERSTOCK_INDEX));
            	if(c.getString(READ_ORDERS_RECEIVEAMT_INDEX) != null) om.setReceiveAmt(c.getDouble(READ_ORDERS_RECEIVEAMT_INDEX));
            	if(c.getString(READ_ORDERS_DELIVER_VIEWORDER_INDEX) != null) om.setDeliverViewOrder(c.getInt(READ_ORDERS_DELIVER_VIEWORDER_INDEX));
                om.setVersionNo(c.getString(READ_ORDERS_VERSIONNO_INDEX));
                om.setCompanyParent(c.getString(READ_ORDERS_COMPANYPARENT_INDEX));
                om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
        }
        c.close();
        closedb(adapter);
		return result;
	}
	
	public List<Orders> getTodayOrdersByUserNO(LikDBAdapter adapter) {
		List<Orders> result = new ArrayList<Orders>();
		Calendar cal = Calendar.getInstance();
		Date dstart = Constant.truncate(cal.getTime(),Calendar.DAY_OF_MONTH);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date dend = Constant.truncate(cal.getTime(),Calendar.DAY_OF_MONTH);
		Log.d(TAG,"dstart="+dstart+",dend="+dend);
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_USERNO+"='"+getUserNO()+"'");
		if(getCompanyID() != 0) qb.appendWhere(" and "+COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and ("+COLUMN_NAME_UPLOADFLAG+"='N' or ("+
			COLUMN_NAME_ORDERDT+">='"+sdf.format(dstart)+"' and "+
			COLUMN_NAME_ORDERDT+"<'"+sdf.format(dend)+"' and "+
			COLUMN_NAME_UPLOADFLAG+"='Y'))");
		// �S�]�Adefault ����
		//else qb.appendWhere(" and "+COLUMN_NAME_UPLOADFLAG+"='"+UPLOADFLAG_N+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_ORDERS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				COLUMN_NAME_UPLOADFLAG+" asc, "+COLUMN_NAME_VIEWORDER+" asc"        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	Orders om = new Orders();
            	om.setSerialID(c.getInt(READ_ORDERS_SERIALID_INDEX));
            	om.setTabletSerialNO(c.getString(READ_ORDERS_TABLETSERIALNO_INDEX));
            	om.setOrderID(c.getInt(READ_ORDERS_ORDERID_INDEX));
            	om.setViewOrder(c.getInt(READ_ORDERS_VIEWORDER_INDEX));
            	om.setCompanyID(c.getInt(READ_ORDERS_COMPANYID_INDEX));
            	om.setUserNO(c.getString(READ_ORDERS_USERNO_INDEX));
                try {
                	if(c.getString(READ_ORDERS_ORDERDT_INDEX) != null) {
                		Date orderDT = sdf.parse(c.getString(READ_ORDERS_ORDERDT_INDEX));
                    	om.setOrderDT(orderDT);
                	}
                } catch(ParseException pe) {
                	om.setOrderDT(null);
                }
                try {
                	if(c.getString(READ_ORDERS_LASTDT_INDEX) != null) {
                		Date lastDT = sdf.parse(c.getString(READ_ORDERS_LASTDT_INDEX));
                		om.setLastDT(lastDT);
                	}
                } catch(ParseException pe) {
                	om.setLastDT(null);
                }
                try {
                	if(c.getString(READ_ORDERS_SELLDT_INDEX) != null) {
                		Date sellDT = sdf.parse(c.getString(READ_ORDERS_SELLDT_INDEX));
                		om.setSellDT(sellDT);
                	}
                } catch(ParseException pe) {
                	om.setSellDT(null);
                }
                om.setCustomerID(c.getInt(READ_ORDERS_CUSTOMERID_INDEX));
                om.setPayKind(c.getInt(READ_ORDERS_PAYKIND_INDEX));
                if(c.getString(READ_ORDERS_PAYNEXTMONTH_INDEX) != null) om.setPayNextMonth(c.getInt(READ_ORDERS_PAYNEXTMONTH_INDEX));
                om.setSalesID(c.getInt(READ_ORDERS_SALESID_INDEX));
                om.setStatus(c.getInt(READ_ORDERS_STATUS_INDEX));
                om.setNote1(c.getString(READ_ORDERS_NOTE1_INDEX));
                om.setNote2(c.getString(READ_ORDERS_NOTE2_INDEX));
                om.setCustomerNO(c.getString(READ_ORDERS_CUSTOMERNO_INDEX));
                om.setPdaId(c.getInt(READ_ORDERS_PDAID_INDEX));
                om.setUploadFlag(c.getString(READ_ORDERS_UPLOADFLAG_INDEX));
                om.setReplyFlag(c.getString(READ_ORDERS_REPLYFLAG_INDEX));
            	if(c.getString(READ_ORDERS_CUSTOMERSTOCK_INDEX) != null) om.setCustomerStock(c.getInt(READ_ORDERS_CUSTOMERSTOCK_INDEX));
            	if(c.getString(READ_ORDERS_RECEIVEAMT_INDEX) != null) om.setReceiveAmt(c.getDouble(READ_ORDERS_RECEIVEAMT_INDEX));
            	if(c.getString(READ_ORDERS_DELIVER_VIEWORDER_INDEX) != null) om.setDeliverViewOrder(c.getInt(READ_ORDERS_DELIVER_VIEWORDER_INDEX));
                om.setVersionNo(c.getString(READ_ORDERS_VERSIONNO_INDEX));
                om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
        }
//        db.close();
        c.close();
        closedb(adapter);
		return result;
	}
	
	public int getMaxViewOrderByUserNO(LikDBAdapter adapter) {
    	int max = 0;
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_USERNO+"='"+getUserNO()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_COMPANYPARENT+"='"+getCompanyParent()+"'");
		if(getCompanyID() != 0) qb.appendWhere(" and "+COLUMN_NAME_COMPANYID+"="+getCompanyID());
		if(getUploadFlag() != null) qb.appendWhere(" and "+COLUMN_NAME_UPLOADFLAG+"='"+getUploadFlag()+"'");
		// �S�]�Adefault ����
		//else qb.appendWhere(" and "+COLUMN_NAME_UPLOADFLAG+"='"+UPLOADFLAG_N+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_ORDERS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				COLUMN_NAME_UPLOADFLAG+" asc, "+COLUMN_NAME_VIEWORDER+" asc"        // The sort order
				);
        if (c.getCount()>0 && c.moveToFirst()) {
            do {
            	if(max<c.getInt(READ_ORDERS_VIEWORDER_INDEX)) {
            		max = c.getInt(READ_ORDERS_VIEWORDER_INDEX);
            	}
            } while(c.moveToNext());
        }
        c.close();
        closedb(adapter);
		return max;
	}
	
	public Orders getOrdersBySerialID(LikDBAdapter adapter) {
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
				READ_ORDERS_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_ORDERS_SERIALID_INDEX));
        	setTabletSerialNO(c.getString(READ_ORDERS_TABLETSERIALNO_INDEX));
        	setOrderID(c.getInt(READ_ORDERS_ORDERID_INDEX));
        	setViewOrder(c.getInt(READ_ORDERS_VIEWORDER_INDEX));
        	setCompanyID(c.getInt(READ_ORDERS_COMPANYID_INDEX));
        	setUserNO(c.getString(READ_ORDERS_USERNO_INDEX));
            try {
            	if(c.getString(READ_ORDERS_ORDERDT_INDEX) != null) {
            		Date orderDT = sdf.parse(c.getString(READ_ORDERS_ORDERDT_INDEX));
            		setOrderDT(orderDT);
            	}
            } catch(ParseException pe) {
            	setOrderDT(null);
            }
            try {
            	if(c.getString(READ_ORDERS_LASTDT_INDEX) != null) {
            		Date lastDT = sdf.parse(c.getString(READ_ORDERS_LASTDT_INDEX));
            		setLastDT(lastDT);
            	}
            } catch(ParseException pe) {
            	setLastDT(null);
            }
            try {
            	if(c.getString(READ_ORDERS_SELLDT_INDEX) != null) {
            		Date sellDT = sdf.parse(c.getString(READ_ORDERS_SELLDT_INDEX));
            		setSellDT(sellDT);
            	}
            } catch(ParseException pe) {
            	setSellDT(null);
            }
        	setCustomerID(c.getInt(READ_ORDERS_CUSTOMERID_INDEX));
        	setPayKind(c.getInt(READ_ORDERS_PAYKIND_INDEX));
        	if(c.getString(READ_ORDERS_PAYNEXTMONTH_INDEX) != null) setPayNextMonth(c.getInt(READ_ORDERS_PAYNEXTMONTH_INDEX));
        	setSalesID(c.getInt(READ_ORDERS_SALESID_INDEX));
        	setStatus(c.getInt(READ_ORDERS_STATUS_INDEX));
        	setNote1(c.getString(READ_ORDERS_NOTE1_INDEX));
        	setNote2(c.getString(READ_ORDERS_NOTE2_INDEX));
        	setCustomerNO(c.getString(READ_ORDERS_CUSTOMERNO_INDEX));
            setPdaId(c.getInt(READ_ORDERS_PDAID_INDEX));
            setUploadFlag(c.getString(READ_ORDERS_UPLOADFLAG_INDEX));
            setReplyFlag(c.getString(READ_ORDERS_REPLYFLAG_INDEX));
        	if(c.getString(READ_ORDERS_CUSTOMERSTOCK_INDEX) != null) setCustomerStock(c.getInt(READ_ORDERS_CUSTOMERSTOCK_INDEX));
        	if(c.getString(READ_ORDERS_RECEIVEAMT_INDEX) != null) setReceiveAmt(c.getDouble(READ_ORDERS_RECEIVEAMT_INDEX));
        	if(c.getString(READ_ORDERS_DELIVER_VIEWORDER_INDEX) != null) setDeliverViewOrder(c.getInt(READ_ORDERS_DELIVER_VIEWORDER_INDEX));
            setVersionNo(c.getString(READ_ORDERS_VERSIONNO_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;		
	}
	
	public List<Orders> getOrdersByCustomer(LikDBAdapter adapter) {
		List<Orders> result = new ArrayList<Orders>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_USERNO+"='"+getUserNO()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_CUSTOMERID+"="+getCustomerID());
		qb.appendWhere(" and "+COLUMN_NAME_UPLOADFLAG+"='"+getUploadFlag()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_COMPANYID+"="+getCompanyID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_ORDERS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
		if(c != null && c.moveToFirst()) {
			do {
				Orders om = new Orders();
				om.setSerialID(c.getInt(READ_ORDERS_SERIALID_INDEX));
				om.setUserNO(c.getString(READ_ORDERS_USERNO_INDEX));
            	om.setTabletSerialNO(c.getString(READ_ORDERS_TABLETSERIALNO_INDEX));
            	om.setOrderID(c.getInt(READ_ORDERS_ORDERID_INDEX));
            	om.setViewOrder(c.getInt(READ_ORDERS_VIEWORDER_INDEX));
            	om.setCompanyID(c.getInt(READ_ORDERS_COMPANYID_INDEX));
	            try {
	            	if(c.getString(READ_ORDERS_ORDERDT_INDEX) != null) {
	            		Date orderDT = sdf.parse(c.getString(READ_ORDERS_ORDERDT_INDEX));
	            		om.setOrderDT(orderDT);
	            	}
	            } catch(ParseException pe) {
	            	om.setOrderDT(null);
	            }
	            try {
	            	if(c.getString(READ_ORDERS_LASTDT_INDEX) != null) {
	            		Date lastDT = sdf.parse(c.getString(READ_ORDERS_LASTDT_INDEX));
	            		om.setLastDT(lastDT);
	            	}
	            } catch(ParseException pe) {
	            	om.setLastDT(null);
	            }
	            try {
	            	if(c.getString(READ_ORDERS_SELLDT_INDEX) != null) {
	            		Date sellDT = sdf.parse(c.getString(READ_ORDERS_SELLDT_INDEX));
	            		om.setSellDT(sellDT);
	            	}
	            } catch(ParseException pe) {
	            	om.setSellDT(null);
	            }
	            om.setCustomerID(c.getInt(READ_ORDERS_CUSTOMERID_INDEX));
	            om.setPayKind(c.getInt(READ_ORDERS_PAYKIND_INDEX));
	        	if(c.getString(READ_ORDERS_PAYNEXTMONTH_INDEX) != null) om.setPayNextMonth(c.getInt(READ_ORDERS_PAYNEXTMONTH_INDEX));
	        	om.setSalesID(c.getInt(READ_ORDERS_SALESID_INDEX));
	        	om.setStatus(c.getInt(READ_ORDERS_STATUS_INDEX));
	        	om.setNote1(c.getString(READ_ORDERS_NOTE1_INDEX));
	        	om.setNote2(c.getString(READ_ORDERS_NOTE2_INDEX));
	        	om.setCustomerNO(c.getString(READ_ORDERS_CUSTOMERNO_INDEX));
	        	om.setPdaId(c.getInt(READ_ORDERS_PDAID_INDEX));
	        	om.setUploadFlag(c.getString(READ_ORDERS_UPLOADFLAG_INDEX));
	        	om.setReplyFlag(c.getString(READ_ORDERS_REPLYFLAG_INDEX));
	        	if(c.getString(READ_ORDERS_CUSTOMERSTOCK_INDEX) != null) om.setCustomerStock(c.getInt(READ_ORDERS_CUSTOMERSTOCK_INDEX));
	        	if(c.getString(READ_ORDERS_RECEIVEAMT_INDEX) != null) om.setReceiveAmt(c.getDouble(READ_ORDERS_RECEIVEAMT_INDEX));
            	if(c.getString(READ_ORDERS_DELIVER_VIEWORDER_INDEX) != null) om.setDeliverViewOrder(c.getInt(READ_ORDERS_DELIVER_VIEWORDER_INDEX));
	        	om.setVersionNo(c.getString(READ_ORDERS_VERSIONNO_INDEX));
	        	om.setRid(0);
	        	result.add(om);
            } while(c.moveToNext());
	        c.close();
        } else setRid(-1);
        closedb(adapter);
		return result;
	}

	public void deleteAllOrders(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		db.execSQL("delete from "+TABLE_NAME);
		closedb(adapter);
	}

	public void deleteAllOrdersByUserNo(LikDBAdapter adapter) {
		Log.d(TAG,"deleteAllOrdersByUserNo Begin");
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_COMPANYID+"="+getCompanyID();
		whereClause +=  " and "+COLUMN_NAME_USERNO+"='"+getUserNO()+"'";
		whereClause +=  " and "+COLUMN_NAME_COMPANYPARENT+"='"+getCompanyParent()+"'";
		whereClause +=  " and "+COLUMN_NAME_CUSTOMERNO+"='"+getCustomerNO()+"'";
		
		Log.d(TAG,"delete whereClause="+whereClause);
		int rid = db.delete(TABLE_NAME, whereClause, null);
        setRid(rid);
		if(rid==0) setRid(-1); 
		closedb(adapter);
		Log.d(TAG,"deleteAllOrdersByUserNo End");
	}

	public void deleteUploadedOrdersByUserNo(LikDBAdapter adapter) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		Date date = Constant.truncate(cal.getTime(),Calendar.DAY_OF_MONTH);
		Log.d(TAG,"7 days before="+date);
		SQLiteDatabase db = getdb(adapter);
		db.execSQL("delete from "+TABLE_NAME+" where "+
				COLUMN_NAME_COMPANYID+"="+getCompanyID()+" and "+COLUMN_NAME_USERNO+"='"+getUserNO()+"' and "+
				COLUMN_NAME_ORDERDT+"<'"+sdf.format(date)+"' and "+
				COLUMN_NAME_UPLOADFLAG+"='Y'");
		closedb(adapter);
	}

	@Override
	public Orders doInsert(LikDBAdapter adapter) {
		return insertOrders(adapter);
	}

	@Override
	public Orders doUpdate(LikDBAdapter adapter) {
		return updateOrders(adapter);
	}

	@Override
	public Orders doDelete(LikDBAdapter adapter) {
		return deleteOrders(adapter);
	}

	@Override
	public Orders findByKey(LikDBAdapter adapter) {
		return getOrdersByKey(adapter);
	}

	@Override
	public Orders queryBySerialID(LikDBAdapter adapter) {
		return getOrdersBySerialID(adapter);
	}

}
