package com.lik.android.om;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Environment;
import android.util.Log;

import com.lik.Constant;
import com.lik.android.main.LikDBAdapter;
import com.lik.android.main.MainMenuActivity;
import com.lik.android.main.R;
import com.lik.android.util.XMLUtil;

public class SellDetail extends BaseSellDetail
	implements ProcessDownloadInterface {

	private static final long serialVersionUID = 8739771250286458840L;
	private static HashMap<String, XMLUtil.SellDetallXML> hm = null;
	private static int customerIDc;
	private static Integer deliverOrderc;
	
	static public void clearCache() {
		hm = null;
		customerIDc = 0;
		deliverOrderc = null;
	}

	@Override
	public SellDetail doInsert(LikDBAdapter adapter) {
		getdb(adapter);
		InsertHelper ih = adapter.getInsertHelper(getTableName());
		ih.prepareForInsert();
		// Add the data for each column
		ih.bind(READ_SELLDETAIL_COMPANYID_INDEX+1, getCompanyID());
		ih.bind(READ_SELLDETAIL_CUSTOMERID_INDEX+1, getCustomerID());
		ih.bind(READ_SELLDETAIL_SELLDATE_INDEX+1, sdf.format(getSellDate()));
		ih.bind(READ_SELLDETAIL_ITEMNO_INDEX+1, getItemNo());
		ih.bind(READ_SELLDETAIL_SEQ_INDEX+1, getSeq());
		ih.bind(READ_SELLDETAIL_SELLID_INDEX+1, getSellID());
		ih.bind(READ_SELLDETAIL_SELLKIND_INDEX+1, getSellKind());
		ih.bind(READ_SELLDETAIL_SKIND_INDEX+1, getsKind());
		ih.bind(READ_SELLDETAIL_SELLPAYTYPE_INDEX+1, getSellPayType());
		ih.bind(READ_SELLDETAIL_PRDTNAME_INDEX+1, getPrdtName());
		ih.bind(READ_SELLDETAIL_UNIT1_INDEX+1, getUnit1());
		ih.bind(READ_SELLDETAIL_UNIT2_INDEX+1, getUnit2());
		ih.bind(READ_SELLDETAIL_UNIT3_INDEX+1, getUnit3());
		ih.bind(READ_SELLDETAIL_SELLQTY1_INDEX+1, getSellQty1());
		ih.bind(READ_SELLDETAIL_SELLQTY2_INDEX+1, getSellQty2());
		ih.bind(READ_SELLDETAIL_SELLQTY3_INDEX+1, getSellQty3());
		ih.bind(READ_SELLDETAIL_SENDQTY1_INDEX+1, getSendQty1());
		ih.bind(READ_SELLDETAIL_SENDQTY2_INDEX+1, getSendQty2());
		ih.bind(READ_SELLDETAIL_SENDQTY3_INDEX+1, getSendQty3());
		ih.bind(READ_SELLDETAIL_GIVEQTY1_INDEX+1, getGiveQty1());
		ih.bind(READ_SELLDETAIL_GIVEQTY2_INDEX+1, getGiveQty2());
		ih.bind(READ_SELLDETAIL_GIVEQTY3_INDEX+1, getGiveQty3());
		ih.bind(READ_SELLDETAIL_DEALKIND_INDEX+1, getDealKind());
		ih.bind(READ_SELLDETAIL_QUANTITY_INDEX+1, getQuantity());
		ih.bind(READ_SELLDETAIL_QTUNIT_INDEX+1, getqTUnit());
		ih.bind(READ_SELLDETAIL_PRICEUNIT_INDEX+1, getPriceUnit());
		ih.bind(READ_SELLDETAIL_UPRICE_INDEX+1, getuPrice());
		ih.bind(READ_SELLDETAIL_PRICEUNIT1_INDEX+1, getPriceUnit1());
		ih.bind(READ_SELLDETAIL_UPRICE1_INDEX+1, getuPrice1());
		ih.bind(READ_SELLDETAIL_DISCRATE_INDEX+1, getDiscRate());
		ih.bind(READ_SELLDETAIL_PRICETOOLOW_INDEX+1, getPriceTooLow());
		ih.bind(READ_SELLDETAIL_PRICEMARK_INDEX+1, getPriceMark());
		ih.bind(READ_SELLDETAIL_VERSIONNO_INDEX+1, getVersionNo());
		// Insert the row into the database.
  	    long rid = ih.execute();
        if(rid != -1) setRid(0);
		return this;
	}

//	@Override
//	public SellDetail doInsert(LikDBAdapter adapter) {
//		SQLiteDatabase db = getdb(adapter);
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(COLUMN_NAME_COMPANYID, getCompanyID());
//        initialValues.put(COLUMN_NAME_CUSTOMERID, getCustomerID());
//        if(getSellDate() != null) initialValues.put(COLUMN_NAME_SELLDATE, sdf.format(getSellDate()));
//        initialValues.put(COLUMN_NAME_ITEMNO, getItemNo());
//        initialValues.put(COLUMN_NAME_SEQ, getSeq());
//        initialValues.put(COLUMN_NAME_SELLID, getSellID());
//        initialValues.put(COLUMN_NAME_SELLKIND, getSellKind());
//        initialValues.put(COLUMN_NAME_SKIND, getsKind());
//        initialValues.put(COLUMN_NAME_SELLPAYTYPE, getSellPayType());
//        initialValues.put(COLUMN_NAME_PRDTNAME, getPrdtName());
//        initialValues.put(COLUMN_NAME_UNIT1, getUnit1());
//        initialValues.put(COLUMN_NAME_UNIT2, getUnit2());
//        initialValues.put(COLUMN_NAME_UNIT3, getUnit3());
//        initialValues.put(COLUMN_NAME_SELLQTY1, getSellQty1());
//        initialValues.put(COLUMN_NAME_SELLQTY2, getSellQty2());
//        initialValues.put(COLUMN_NAME_SELLQTY3, getSellQty3());
//        initialValues.put(COLUMN_NAME_SENDQTY1, getSendQty1());
//        initialValues.put(COLUMN_NAME_SENDQTY2, getSendQty2());
//        initialValues.put(COLUMN_NAME_SENDQTY3, getSendQty3());
//        initialValues.put(COLUMN_NAME_GIVEQTY1, getGiveQty1());
//        initialValues.put(COLUMN_NAME_GIVEQTY2, getGiveQty2());
//        initialValues.put(COLUMN_NAME_GIVEQTY3, getGiveQty3());
//        initialValues.put(COLUMN_NAME_DEALKIND, getDealKind());
//        initialValues.put(COLUMN_NAME_QUANTITY, getQuantity());
//        initialValues.put(COLUMN_NAME_QTUNIT, getqTUnit());
//        initialValues.put(COLUMN_NAME_PRICEUNIT, getPriceUnit());
//        initialValues.put(COLUMN_NAME_UPRICE, getuPrice());
//        initialValues.put(COLUMN_NAME_PRICEUNIT1, getPriceUnit1());
//        initialValues.put(COLUMN_NAME_UPRICE1, getuPrice1());
//        initialValues.put(COLUMN_NAME_DISCRATE, getDiscRate());
//        initialValues.put(COLUMN_NAME_PRICETOOLOW, getPriceTooLow());
//        initialValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
//        long rid = db.insert(TABLE_NAME, null, initialValues);
//        setRid(rid);
////        db.close();
//        closedb(adapter);
//		return this;
//	}

	@Override
	public SellDetail doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();

        updateValues.put(COLUMN_NAME_SELLKIND, getSellKind());
        updateValues.put(COLUMN_NAME_SKIND, getsKind());
        updateValues.put(COLUMN_NAME_SELLPAYTYPE, getSellPayType());
        updateValues.put(COLUMN_NAME_PRDTNAME, getPrdtName());
        updateValues.put(COLUMN_NAME_UNIT1, getUnit1());
        updateValues.put(COLUMN_NAME_UNIT2, getUnit2());
        updateValues.put(COLUMN_NAME_UNIT3, getUnit3());
        updateValues.put(COLUMN_NAME_SELLQTY1, getSellQty1());
        updateValues.put(COLUMN_NAME_SELLQTY2, getSellQty2());
        updateValues.put(COLUMN_NAME_SELLQTY3, getSellQty3());
        updateValues.put(COLUMN_NAME_SENDQTY1, getSendQty1());
        updateValues.put(COLUMN_NAME_SENDQTY2, getSendQty2());
        updateValues.put(COLUMN_NAME_SENDQTY3, getSendQty3());
        updateValues.put(COLUMN_NAME_GIVEQTY1, getGiveQty1());
        updateValues.put(COLUMN_NAME_GIVEQTY2, getGiveQty2());
        updateValues.put(COLUMN_NAME_GIVEQTY3, getGiveQty3());
        updateValues.put(COLUMN_NAME_DEALKIND, getDealKind());
        updateValues.put(COLUMN_NAME_QUANTITY, getQuantity());
        updateValues.put(COLUMN_NAME_QTUNIT, getqTUnit());
        updateValues.put(COLUMN_NAME_PRICEUNIT, getPriceUnit());
        updateValues.put(COLUMN_NAME_UPRICE, getuPrice());
        updateValues.put(COLUMN_NAME_PRICEUNIT1, getPriceUnit1());
        updateValues.put(COLUMN_NAME_UPRICE1, getuPrice1());
        updateValues.put(COLUMN_NAME_DISCRATE, getDiscRate());
        updateValues.put(COLUMN_NAME_PRICETOOLOW, getPriceTooLow());
        updateValues.put(COLUMN_NAME_PRICEMARK, getPriceMark());
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
	public SellDetail doDelete(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SERIALID+"=?";
		Log.d(TAG,COLUMN_NAME_SERIALID+"="+getSerialID());
		String[] whereArgs = {String.valueOf(getSerialID())};
		int rid = db.delete(getTableName(), whereClause, whereArgs);
		Log.d(TAG,"rid="+rid);
        setRid(rid);
		if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
//		db.close();
        closedb(adapter);
		return this;
	}

	@Override
	public SellDetail findByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_CUSTOMERID+"="+getCustomerID());
		qb.appendWhere(" and "+COLUMN_NAME_SELLDATE+"='"+sdf.format(getSellDate())+"'");
		qb.appendWhere(" and "+COLUMN_NAME_ITEMNO+"='"+getItemNo()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_SEQ+"="+getSeq());
		qb.appendWhere(" and "+COLUMN_NAME_SELLID+"="+getSellID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SELLDETAIL_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_SELLDETAIL_SERIALID_INDEX));
        	setSellKind(c.getInt(READ_SELLDETAIL_SELLKIND_INDEX));
        	setsKind(c.getString(READ_SELLDETAIL_SKIND_INDEX));
        	setSellPayType(c.getString(READ_SELLDETAIL_SELLPAYTYPE_INDEX));
        	setPrdtName(c.getString(READ_SELLDETAIL_PRDTNAME_INDEX));
        	setUnit1(c.getString(READ_SELLDETAIL_UNIT1_INDEX));
        	setUnit2(c.getString(READ_SELLDETAIL_UNIT2_INDEX));
        	setUnit3(c.getString(READ_SELLDETAIL_UNIT3_INDEX));
        	setSellQty1(c.getDouble(READ_SELLDETAIL_SELLQTY1_INDEX));
        	setSellQty2(c.getDouble(READ_SELLDETAIL_SELLQTY2_INDEX));
        	setSellQty3(c.getDouble(READ_SELLDETAIL_SELLQTY3_INDEX));
        	setSendQty1(c.getDouble(READ_SELLDETAIL_SENDQTY1_INDEX));
        	setSendQty2(c.getDouble(READ_SELLDETAIL_SENDQTY2_INDEX));
        	setSendQty3(c.getDouble(READ_SELLDETAIL_SENDQTY3_INDEX));
        	setGiveQty1(c.getDouble(READ_SELLDETAIL_GIVEQTY1_INDEX));
        	setGiveQty2(c.getDouble(READ_SELLDETAIL_GIVEQTY2_INDEX));
        	setGiveQty3(c.getDouble(READ_SELLDETAIL_GIVEQTY3_INDEX));
        	setDealKind(c.getInt(READ_SELLDETAIL_DEALKIND_INDEX));
        	setQuantity(c.getDouble(READ_SELLDETAIL_QUANTITY_INDEX));
        	setqTUnit(c.getString(READ_SELLDETAIL_QTUNIT_INDEX));
        	setPriceUnit(c.getString(READ_SELLDETAIL_PRICEUNIT_INDEX));
        	setuPrice(c.getDouble(READ_SELLDETAIL_UPRICE_INDEX));
        	setPriceUnit1(c.getString(READ_SELLDETAIL_PRICEUNIT1_INDEX));
        	setuPrice1(c.getDouble(READ_SELLDETAIL_UPRICE1_INDEX));
        	setDiscRate(c.getDouble(READ_SELLDETAIL_DISCRATE_INDEX));
        	setPriceTooLow(c.getString(READ_SELLDETAIL_PRICETOOLOW_INDEX));
        	setPriceMark(c.getInt(READ_SELLDETAIL_PRICEMARK_INDEX));
            setVersionNo(c.getString(READ_SELLDETAIL_VERSIONNO_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public SellDetail queryBySerialID(LikDBAdapter adapter) {
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
				READ_SELLDETAIL_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_SELLDETAIL_SERIALID_INDEX));
        	setCompanyID(c.getInt(READ_SELLDETAIL_COMPANYID_INDEX));
        	setCustomerID(c.getInt(READ_SELLDETAIL_CUSTOMERID_INDEX));
            try {
            	if(c.getString(READ_SELLDETAIL_SELLDATE_INDEX) != null) {
            		Date sellDate = sdf.parse(c.getString(READ_SELLDETAIL_SELLDATE_INDEX));
            		setSellDate(sellDate);
            	}
            } catch(ParseException pe) {
            	setSellDate(null); // primary key not null
            }
        	setItemNo(c.getString(READ_SELLDETAIL_ITEMNO_INDEX));
        	setSeq(c.getInt(READ_SELLDETAIL_SEQ_INDEX));
        	setSellID(c.getInt(READ_SELLDETAIL_SELLID_INDEX));
        	setSellKind(c.getInt(READ_SELLDETAIL_SELLKIND_INDEX));
        	setsKind(c.getString(READ_SELLDETAIL_SKIND_INDEX));
        	setSellPayType(c.getString(READ_SELLDETAIL_SELLPAYTYPE_INDEX));
        	setPrdtName(c.getString(READ_SELLDETAIL_PRDTNAME_INDEX));
        	setUnit1(c.getString(READ_SELLDETAIL_UNIT1_INDEX));
        	setUnit2(c.getString(READ_SELLDETAIL_UNIT2_INDEX));
        	setUnit3(c.getString(READ_SELLDETAIL_UNIT3_INDEX));
        	setSellQty1(c.getDouble(READ_SELLDETAIL_SELLQTY1_INDEX));
        	setSellQty2(c.getDouble(READ_SELLDETAIL_SELLQTY2_INDEX));
        	setSellQty3(c.getDouble(READ_SELLDETAIL_SELLQTY3_INDEX));
        	setSendQty1(c.getDouble(READ_SELLDETAIL_SENDQTY1_INDEX));
        	setSendQty2(c.getDouble(READ_SELLDETAIL_SENDQTY2_INDEX));
        	setSendQty3(c.getDouble(READ_SELLDETAIL_SENDQTY3_INDEX));
        	setGiveQty1(c.getDouble(READ_SELLDETAIL_GIVEQTY1_INDEX));
        	setGiveQty2(c.getDouble(READ_SELLDETAIL_GIVEQTY2_INDEX));
        	setGiveQty3(c.getDouble(READ_SELLDETAIL_GIVEQTY3_INDEX));
        	setDealKind(c.getInt(READ_SELLDETAIL_DEALKIND_INDEX));
        	setQuantity(c.getDouble(READ_SELLDETAIL_QUANTITY_INDEX));
        	setqTUnit(c.getString(READ_SELLDETAIL_QTUNIT_INDEX));
        	setPriceUnit(c.getString(READ_SELLDETAIL_PRICEUNIT_INDEX));
        	setuPrice(c.getDouble(READ_SELLDETAIL_UPRICE_INDEX));
        	setPriceUnit1(c.getString(READ_SELLDETAIL_PRICEUNIT1_INDEX));
        	setuPrice1(c.getDouble(READ_SELLDETAIL_UPRICE1_INDEX));
        	setDiscRate(c.getDouble(READ_SELLDETAIL_DISCRATE_INDEX));
        	setPriceTooLow(c.getString(READ_SELLDETAIL_PRICETOOLOW_INDEX));
        	setPriceMark(c.getInt(READ_SELLDETAIL_PRICEMARK_INDEX));
            setVersionNo(c.getString(READ_SELLDETAIL_VERSIONNO_INDEX));
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
		setCustomerID(Integer.parseInt(detail.get(COLUMN_NAME_CUSTOMERID)));
		try {
			if(detail.get(COLUMN_NAME_SELLDATE) != null) setSellDate(sdf2.parse(detail.get(COLUMN_NAME_SELLDATE)));
		} catch (ParseException e) {
			Log.e(TAG,e.getMessage());
		}
		setItemNo(detail.get(COLUMN_NAME_ITEMNO));
		setSeq(Integer.parseInt(detail.get(COLUMN_NAME_SEQ)));
		setSellID(Integer.parseInt(detail.get(COLUMN_NAME_SELLID)));
		if(!isOnlyInsert) findByKey(adapter);
    	setSellKind(Integer.parseInt(detail.get(COLUMN_NAME_SELLKIND)));
    	setsKind(detail.get(COLUMN_NAME_SKIND));
    	setSellPayType(detail.get(COLUMN_NAME_SELLPAYTYPE));
    	setPrdtName(detail.get(COLUMN_NAME_PRDTNAME));
    	setUnit1(detail.get(COLUMN_NAME_UNIT1));
    	setUnit2(detail.get(COLUMN_NAME_UNIT2));
    	setUnit3(detail.get(COLUMN_NAME_UNIT3));
    	setSellQty1(Double.parseDouble(detail.get(COLUMN_NAME_SELLQTY1)));
    	setSellQty2(Double.parseDouble(detail.get(COLUMN_NAME_SELLQTY2)));
    	setSellQty3(Double.parseDouble(detail.get(COLUMN_NAME_SELLQTY3)));
    	setSendQty1(Double.parseDouble(detail.get(COLUMN_NAME_SENDQTY1)));
    	setSendQty2(Double.parseDouble(detail.get(COLUMN_NAME_SENDQTY2)));
    	setSendQty3(Double.parseDouble(detail.get(COLUMN_NAME_SENDQTY3)));
    	setGiveQty1(Double.parseDouble(detail.get(COLUMN_NAME_GIVEQTY1)));
    	setGiveQty2(Double.parseDouble(detail.get(COLUMN_NAME_GIVEQTY2)));
    	setGiveQty3(Double.parseDouble(detail.get(COLUMN_NAME_GIVEQTY3)));
    	setDealKind(Integer.parseInt(detail.get(COLUMN_NAME_DEALKIND)));
    	setQuantity(Double.parseDouble(detail.get(COLUMN_NAME_QUANTITY)));
    	setqTUnit(detail.get(COLUMN_NAME_QTUNIT));
    	setPriceUnit(detail.get(COLUMN_NAME_PRICEUNIT));
    	setuPrice(Double.parseDouble(detail.get(COLUMN_NAME_UPRICE)));
    	setPriceUnit1(detail.get(COLUMN_NAME_PRICEUNIT1));
    	setuPrice1(Double.parseDouble(detail.get(COLUMN_NAME_UPRICE1)));
    	setDiscRate(Double.parseDouble(detail.get(COLUMN_NAME_DISCRATE)));
    	setPriceTooLow(detail.get(COLUMN_NAME_PRICETOOLOW));
    	if(detail.get(COLUMN_NAME_PRICEMARK)!=null) setPriceMark(Integer.parseInt(detail.get(COLUMN_NAME_PRICEMARK)));
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

	public List<SellDetail> getSellDetailBySellDate(LikDBAdapter adapter) {
		List<SellDetail> result = new ArrayList<SellDetail>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_CUSTOMERID+"="+getCustomerID());
		qb.appendWhere(" and "+COLUMN_NAME_SELLDATE+"='"+sdf.format(getSellDate())+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SELLDETAIL_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	SellDetail om = new SellDetail();
            	om.setSerialID(c.getInt(READ_SELLDETAIL_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_SELLDETAIL_COMPANYID_INDEX));
            	om.setCustomerID(c.getInt(READ_SELLDETAIL_CUSTOMERID_INDEX));
                try {
                	if(c.getString(READ_SELLDETAIL_SELLDATE_INDEX) != null) {
                		Date sellDate = sdf.parse(c.getString(READ_SELLDETAIL_SELLDATE_INDEX));
                		om.setSellDate(sellDate);
                	}
                } catch(ParseException pe) {
                	om.setSellDate(null); // primary key not null
                }
                om.setItemNo(c.getString(READ_SELLDETAIL_ITEMNO_INDEX));
            	om.setSeq(c.getInt(READ_SELLDETAIL_SEQ_INDEX));
            	om.setSellID(c.getInt(READ_SELLDETAIL_SELLID_INDEX));
            	om.setSellKind(c.getInt(READ_SELLDETAIL_SELLKIND_INDEX));
            	om.setsKind(c.getString(READ_SELLDETAIL_SKIND_INDEX));
            	om.setSellPayType(c.getString(READ_SELLDETAIL_SELLPAYTYPE_INDEX));
            	om.setPrdtName(c.getString(READ_SELLDETAIL_PRDTNAME_INDEX));
            	om.setUnit1(c.getString(READ_SELLDETAIL_UNIT1_INDEX));
            	om.setUnit2(c.getString(READ_SELLDETAIL_UNIT2_INDEX));
            	om.setUnit3(c.getString(READ_SELLDETAIL_UNIT3_INDEX));
            	om.setSellQty1(c.getDouble(READ_SELLDETAIL_SELLQTY1_INDEX));
            	om.setSellQty2(c.getDouble(READ_SELLDETAIL_SELLQTY2_INDEX));
            	om.setSellQty3(c.getDouble(READ_SELLDETAIL_SELLQTY3_INDEX));
            	om.setSendQty1(c.getDouble(READ_SELLDETAIL_SENDQTY1_INDEX));
            	om.setSendQty2(c.getDouble(READ_SELLDETAIL_SENDQTY2_INDEX));
            	om.setSendQty3(c.getDouble(READ_SELLDETAIL_SENDQTY3_INDEX));
            	om.setGiveQty1(c.getDouble(READ_SELLDETAIL_GIVEQTY1_INDEX));
            	om.setGiveQty2(c.getDouble(READ_SELLDETAIL_GIVEQTY2_INDEX));
            	om.setGiveQty3(c.getDouble(READ_SELLDETAIL_GIVEQTY3_INDEX));
            	om.setDealKind(c.getInt(READ_SELLDETAIL_DEALKIND_INDEX));
            	om.setQuantity(c.getDouble(READ_SELLDETAIL_QUANTITY_INDEX));
            	om.setqTUnit(c.getString(READ_SELLDETAIL_QTUNIT_INDEX));
            	om.setPriceUnit(c.getString(READ_SELLDETAIL_PRICEUNIT_INDEX));
            	om.setuPrice(c.getDouble(READ_SELLDETAIL_UPRICE_INDEX));
            	om.setPriceUnit1(c.getString(READ_SELLDETAIL_PRICEUNIT1_INDEX));
            	om.setuPrice1(c.getDouble(READ_SELLDETAIL_UPRICE1_INDEX));
            	om.setDiscRate(c.getDouble(READ_SELLDETAIL_DISCRATE_INDEX));
            	om.setPriceTooLow(c.getString(READ_SELLDETAIL_PRICETOOLOW_INDEX));
            	om.setPriceMark(c.getInt(READ_SELLDETAIL_PRICEMARK_INDEX));
            	om.setVersionNo(c.getString(READ_SELLDETAIL_VERSIONNO_INDEX));
                om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
        }
//        db.close();		
        c.close();
        closedb(adapter);
		return result;
	}

	public List<String> getSellDateList(LikDBAdapter adapter) {
		List<String> result = new ArrayList<String>();
		String[] R_SELLDETAIL_PROJECTION = new String[] { COLUMN_NAME_SELLDATE};
		SQLiteDatabase db = getdb(adapter);
		String[] selectionArgs = {String.valueOf(getCompanyID()),String.valueOf(getCustomerID())};
		Cursor c = db.query(getTableName(), 
				R_SELLDETAIL_PROJECTION, 
				COLUMN_NAME_COMPANYID+"=?"+" and "+COLUMN_NAME_CUSTOMERID+"=?", 
				selectionArgs, 
				COLUMN_NAME_SELLDATE, 
				null, 
				COLUMN_NAME_SELLDATE+" desc");
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	result.add(c.getString(0));
            } while(c.moveToNext());
        }
//        db.close();		
        c.close();
        closedb(adapter);
		return result;
	}

	public List<SellDetail> getSellDetailOrderBySellDate(LikDBAdapter adapter) {
		List<SellDetail> result = new ArrayList<SellDetail>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_CUSTOMERID+"="+getCustomerID());
		qb.appendWhere(" and "+COLUMN_NAME_ITEMNO+"='"+getItemNo()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SELLDETAIL_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				COLUMN_NAME_SELLDATE+" desc"        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	SellDetail om = new SellDetail();
            	om.setSerialID(c.getInt(READ_SELLDETAIL_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_SELLDETAIL_COMPANYID_INDEX));
            	om.setCustomerID(c.getInt(READ_SELLDETAIL_CUSTOMERID_INDEX));
                try {
                	if(c.getString(READ_SELLDETAIL_SELLDATE_INDEX) != null) {
                		Date sellDate = sdf.parse(c.getString(READ_SELLDETAIL_SELLDATE_INDEX));
                		om.setSellDate(sellDate);
                	}
                } catch(ParseException pe) {
                	om.setSellDate(null); // primary key not null
                }
                om.setItemNo(c.getString(READ_SELLDETAIL_ITEMNO_INDEX));
            	om.setSeq(c.getInt(READ_SELLDETAIL_SEQ_INDEX));
            	om.setSellID(c.getInt(READ_SELLDETAIL_SELLID_INDEX));
            	om.setSellKind(c.getInt(READ_SELLDETAIL_SELLKIND_INDEX));
            	om.setsKind(c.getString(READ_SELLDETAIL_SKIND_INDEX));
            	om.setSellPayType(c.getString(READ_SELLDETAIL_SELLPAYTYPE_INDEX));
            	om.setPrdtName(c.getString(READ_SELLDETAIL_PRDTNAME_INDEX));
            	om.setUnit1(c.getString(READ_SELLDETAIL_UNIT1_INDEX));
            	om.setUnit2(c.getString(READ_SELLDETAIL_UNIT2_INDEX));
            	om.setUnit3(c.getString(READ_SELLDETAIL_UNIT3_INDEX));
            	om.setSellQty1(c.getDouble(READ_SELLDETAIL_SELLQTY1_INDEX));
            	om.setSellQty2(c.getDouble(READ_SELLDETAIL_SELLQTY2_INDEX));
            	om.setSellQty3(c.getDouble(READ_SELLDETAIL_SELLQTY3_INDEX));
            	om.setSendQty1(c.getDouble(READ_SELLDETAIL_SENDQTY1_INDEX));
            	om.setSendQty2(c.getDouble(READ_SELLDETAIL_SENDQTY2_INDEX));
            	om.setSendQty3(c.getDouble(READ_SELLDETAIL_SENDQTY3_INDEX));
            	om.setGiveQty1(c.getDouble(READ_SELLDETAIL_GIVEQTY1_INDEX));
            	om.setGiveQty2(c.getDouble(READ_SELLDETAIL_GIVEQTY2_INDEX));
            	om.setGiveQty3(c.getDouble(READ_SELLDETAIL_GIVEQTY3_INDEX));
            	om.setDealKind(c.getInt(READ_SELLDETAIL_DEALKIND_INDEX));
            	om.setQuantity(c.getDouble(READ_SELLDETAIL_QUANTITY_INDEX));
            	om.setqTUnit(c.getString(READ_SELLDETAIL_QTUNIT_INDEX));
            	om.setPriceUnit(c.getString(READ_SELLDETAIL_PRICEUNIT_INDEX));
            	om.setuPrice(c.getDouble(READ_SELLDETAIL_UPRICE_INDEX));
            	om.setPriceUnit1(c.getString(READ_SELLDETAIL_PRICEUNIT1_INDEX));
            	om.setuPrice1(c.getDouble(READ_SELLDETAIL_UPRICE1_INDEX));
            	om.setDiscRate(c.getDouble(READ_SELLDETAIL_DISCRATE_INDEX));
            	om.setPriceTooLow(c.getString(READ_SELLDETAIL_PRICETOOLOW_INDEX));
            	om.setPriceMark(c.getInt(READ_SELLDETAIL_PRICEMARK_INDEX));
            	om.setVersionNo(c.getString(READ_SELLDETAIL_VERSIONNO_INDEX));
                om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
        }
//        db.close();		
        c.close();
        closedb(adapter);
		return result;
	}

	public SellDetail getSellDetailByLatestSellDate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
//		String sKind = adapter.getCtx().getResources().getString(R.string.Message33);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName()); 
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_CUSTOMERID+"="+getCustomerID());
		qb.appendWhere(" and "+COLUMN_NAME_ITEMNO+"='"+getItemNo()+"'");
//		qb.appendWhere(" and "+COLUMN_NAME_SKIND+"='"+sKind+"'"); // �����O'�X�f'
		qb.appendWhere(" and "+COLUMN_NAME_SELLKIND+"=1"); // �����O1
		if(((MainMenuActivity)adapter.getCtx()).currentDept.getUiFormat().equals(Company.UI_FPRMAT_2)) {
			qb.appendWhere(" and "+COLUMN_NAME_DEALKIND+"=1"); // �����O��=1
		} else {
			qb.appendWhere(" and ("+COLUMN_NAME_SELLQTY1+"!=0"); // �ƶq����
			qb.appendWhere(" or "+COLUMN_NAME_SELLQTY2+"!=0"); // �ƶq����
			qb.appendWhere(" or "+COLUMN_NAME_SELLQTY3+"!=0)"); // �ƶq����			
		}
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_SELLDETAIL_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				COLUMN_NAME_SELLDATE+" desc"        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
        	setSerialID(c.getInt(READ_SELLDETAIL_SERIALID_INDEX));
        	setCompanyID(c.getInt(READ_SELLDETAIL_COMPANYID_INDEX));
        	setCustomerID(c.getInt(READ_SELLDETAIL_CUSTOMERID_INDEX));
            try {
            	if(c.getString(READ_SELLDETAIL_SELLDATE_INDEX) != null) {
            		Date sellDate = sdf.parse(c.getString(READ_SELLDETAIL_SELLDATE_INDEX));
            		setSellDate(sellDate);
            	}
            } catch(ParseException pe) {
            	setSellDate(null); // primary key not null
            }
            setItemNo(c.getString(READ_SELLDETAIL_ITEMNO_INDEX));
        	setSeq(c.getInt(READ_SELLDETAIL_SEQ_INDEX));
        	setSellID(c.getInt(READ_SELLDETAIL_SELLID_INDEX));
        	setSellKind(c.getInt(READ_SELLDETAIL_SELLKIND_INDEX));
        	setsKind(c.getString(READ_SELLDETAIL_SKIND_INDEX));
        	setSellPayType(c.getString(READ_SELLDETAIL_SELLPAYTYPE_INDEX));
        	setPrdtName(c.getString(READ_SELLDETAIL_PRDTNAME_INDEX));
        	setUnit1(c.getString(READ_SELLDETAIL_UNIT1_INDEX));
        	setUnit2(c.getString(READ_SELLDETAIL_UNIT2_INDEX));
        	setUnit3(c.getString(READ_SELLDETAIL_UNIT3_INDEX));
        	setSellQty1(c.getDouble(READ_SELLDETAIL_SELLQTY1_INDEX));
        	setSellQty2(c.getDouble(READ_SELLDETAIL_SELLQTY2_INDEX));
        	setSellQty3(c.getDouble(READ_SELLDETAIL_SELLQTY3_INDEX));
        	setSendQty1(c.getDouble(READ_SELLDETAIL_SENDQTY1_INDEX));
        	setSendQty2(c.getDouble(READ_SELLDETAIL_SENDQTY2_INDEX));
        	setSendQty3(c.getDouble(READ_SELLDETAIL_SENDQTY3_INDEX));
        	setGiveQty1(c.getDouble(READ_SELLDETAIL_GIVEQTY1_INDEX));
        	setGiveQty2(c.getDouble(READ_SELLDETAIL_GIVEQTY2_INDEX));
        	setGiveQty3(c.getDouble(READ_SELLDETAIL_GIVEQTY3_INDEX));
        	setDealKind(c.getInt(READ_SELLDETAIL_DEALKIND_INDEX));
        	setQuantity(c.getDouble(READ_SELLDETAIL_QUANTITY_INDEX));
        	setqTUnit(c.getString(READ_SELLDETAIL_QTUNIT_INDEX));
        	setPriceUnit(c.getString(READ_SELLDETAIL_PRICEUNIT_INDEX));
        	setuPrice(c.getDouble(READ_SELLDETAIL_UPRICE_INDEX));
        	setPriceUnit1(c.getString(READ_SELLDETAIL_PRICEUNIT1_INDEX));
        	setuPrice1(c.getDouble(READ_SELLDETAIL_UPRICE1_INDEX));
        	setDiscRate(c.getDouble(READ_SELLDETAIL_DISCRATE_INDEX));
        	setPriceTooLow(c.getString(READ_SELLDETAIL_PRICETOOLOW_INDEX));
        	setPriceMark(c.getInt(READ_SELLDETAIL_PRICEMARK_INDEX));
        	setVersionNo(c.getString(READ_SELLDETAIL_VERSIONNO_INDEX));
            setRid(0);
        } else {
        	setRid(-1);
        }
//        db.close();		
        c.close();
        closedb(adapter);
		return this;
	}

	boolean isPriceMarkRuleEnabled = true;
	
	public boolean isPriceMarkRuleEnabled() {
		return isPriceMarkRuleEnabled;
	}

	public void setPriceMarkRuleEnabled(boolean isPriceMarkRuleEnabled) {
		this.isPriceMarkRuleEnabled = isPriceMarkRuleEnabled;
	}

	public SellDetail getSellDetailByLatestSellDateXML(LikDBAdapter adapter) {
		setRid(-1);
		String dataDir = Environment.getExternalStorageDirectory()+adapter.getCtx().getResources().getString(R.string.SellDetalFileDir);
		XMLUtil util = XMLUtil.getInstance(adapter.getCtx());
		int i1 = deliverOrderc==null?-1:deliverOrderc.intValue();
		int i2 = getCustDelverViewOrder()==null?-1:getCustDelverViewOrder().intValue();		
		if(hm == null || (customerIDc != getCustomerID() || i1 != i2)) {
			hm = util.getProductsMap(getCompanyID(), getCustomerID(), getCustDelverViewOrder());
			customerIDc = getCustomerID();
			deliverOrderc = getCustDelverViewOrder();
		}
		XMLUtil.SellDetallXML om = hm.get(getItemNo());
		if(om != null) {
			Log.d(TAG,"om.getSellDateList()="+om.getSellDateList());
			String[] aSellDate = om.getSellDateList().split(",");
			TreeSet<String> ts = new TreeSet<String>(new Comparator<String>() {

				@Override
				public int compare(String lhs, String rhs) {					
					return rhs.compareTo(lhs);
				}
				
			});
			for(String sSellDate : aSellDate) {
				ts.add(sSellDate);
			}
			for(String sSellDate : ts) {
				InputSource is = null;
				FileReader fr = null;
				try {
					String fileDir = dataDir;		
					fileDir += getCompanyID();
					fileDir += "/";
					fileDir += getCustomerID();
					fileDir += "/";
					if(getCustDelverViewOrder()!=null) {
						fileDir += getCustDelverViewOrder();
						fileDir += "/";
					}
					fileDir += sSellDate;
			        fileDir += ".xml";
			        Log.d(TAG,"fileDir="+fileDir);
			        File file = new File(fileDir);
			        if(!file.exists()) {
			        	Log.e(TAG,"not found file="+fileDir);
			        	setRid(-1);
			        	return this;
			        }
			        fr = new FileReader(fileDir);
					is = new InputSource(new FileReader(fileDir));
		            DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		            Document doc = dfactory.newDocumentBuilder().parse(is);
		            Element root = doc.getDocumentElement();
		            Log.d(TAG,"root name="+root.getNodeName());
		            NodeList nlist = root.getChildNodes();
		            for(int i=0;i<nlist.getLength();i++) {
		                Node node = nlist.item(i);
		                if(node.getNodeName().equals("Size")) {
		                	Log.d(TAG,"size="+node.getFirstChild().getNodeValue());
		                } else if(node.getNodeName().equals(SellDetail.TABLE_NAME)) {
		        			SellDetail omSD = new SellDetail();
		                	NodeList nlist2 = node.getChildNodes();
		                	boolean needAdd = false;
		                	for(int j=0;j<nlist2.getLength();j++) {
		                		Node node2 = nlist2.item(j);
		                		if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SERIALID)) {
		                			omSD.setSerialID(Integer.parseInt(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_PRDTNAME)) {
		                			omSD.setPrdtName(node2.getFirstChild().getNodeValue());
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_DEALKIND)) {
		                			omSD.setDealKind(Integer.parseInt(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SELLKIND)) {
		                			omSD.setSellKind(Integer.parseInt(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_QUANTITY)) {
		                			omSD.setQuantity(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_QTUNIT)) {
		                			omSD.setqTUnit(node2.getFirstChild().getNodeValue());
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_UPRICE)) {
		                			omSD.setuPrice(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_DISCRATE)) {
		                			omSD.setDiscRate(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SELLPAYTYPE)) {
		                			omSD.setSellPayType(node2.getFirstChild().getNodeValue());
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_ITEMNO)) {
		                			if(getItemNo().equals(node2.getFirstChild().getNodeValue())) needAdd = true;
		                			else break;
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SELLDATE)) {
		                			try {
										omSD.setSellDate(Constant.sqliteDFS.parse(node2.getFirstChild().getNodeValue()));
									} catch (DOMException e) {
										e.printStackTrace();
									} catch (ParseException e) {
										e.printStackTrace();
									}
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_PRICEUNIT)) {
		                			omSD.setPriceUnit(node2.getFirstChild().getNodeValue());
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_UPRICE1)) {
		                			omSD.setuPrice1(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SKIND)) {
		                			omSD.setsKind(node2.getFirstChild().getNodeValue());
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SELLQTY1)) {
		                			omSD.setSellQty1(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SELLQTY2)) {
		                			omSD.setSellQty2(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SELLQTY3)) {
		                			omSD.setSellQty3(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_PRICETOOLOW)) {
		                			omSD.setPriceTooLow(node2.getFirstChild().getNodeValue());
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_PRICEMARK)) {
		                			omSD.setPriceMark(Integer.parseInt(node2.getFirstChild().getNodeValue()));
		                			Log.d(TAG,"PriceMark="+omSD.getPriceMark());
		                			Log.d(TAG,"isPriceMarkRuleEnabled="+isPriceMarkRuleEnabled);
		                			if(isPriceMarkRuleEnabled) {
			                			if(omSD.getPriceMark()==1) {
			                				needAdd = false;
			                				break; // 2013/08/20 �S������
			                			}
		                			}
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_NOTE)) {
		                			if(node2.getFirstChild()!= null && node2.getFirstChild().getNodeValue()!=null) omSD.setNote(node2.getFirstChild().getNodeValue().replaceAll("&amp;", "&"));
		            
		                		//HAO 104.03.09
		                	    } else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SELLTITLE)) {
		                	    	if(node2.getFirstChild()!= null && node2.getFirstChild().getNodeValue()!=null) omSD.setSellTitle(node2.getFirstChild().getNodeValue());
	                		    }
		                		//
		                	}
		                	Log.d(TAG,"sKind="+omSD.getsKind());
		                	if(needAdd) {
//		                		String sKind = adapter.getCtx().getResources().getString(R.string.Message33);
		                		if(((MainMenuActivity)adapter.getCtx()).currentDept.getUiFormat().equals(Company.UI_FPRMAT_2)) {
//		                			if(omSD.getsKind()!= null && omSD.getsKind().equals(sKind) && omSD.getDealKind()==1) {
			                		if(omSD.getSellKind()==1 && omSD.getDealKind()==1) {
				                		Log.d(TAG,"found uiFormat 2");
		                				setSerialID(omSD.getSerialID());
				                		setPrdtName(omSD.getPrdtName());
				                		setDealKind(omSD.getDealKind());
				                		setQuantity(omSD.getQuantity());
				                		setqTUnit(omSD.getqTUnit());
				                		setuPrice(omSD.getuPrice());
				                		setDiscRate(omSD.getDiscRate());
				                		setSellPayType(omSD.getSellPayType());
				                		setSellDate(omSD.getSellDate());
				                		setPriceUnit(omSD.getPriceUnit());
				                		setuPrice1(omSD.getuPrice1());
				                		setsKind(omSD.getsKind());
				                		setPriceMark(omSD.getPriceMark());
				                		setNote(omSD.getNote());
				                		setRid(0);
				                		break;
		                			}
		                		} else { // uiFormat = 3
//		                			if(omSD.getsKind()!= null && omSD.getsKind().equals(sKind) &&
		                			if(omSD.getSellKind()==1 &&
		                					(omSD.getSellQty1() != 0 ||
		                					omSD.getSellQty2() != 0 ||
		                					omSD.getSellQty3() != 0)
		                					) {
				                		Log.d(TAG,"found uiFormat 3");
				                		setSerialID(omSD.getSerialID());
				                		setPrdtName(omSD.getPrdtName());
				                		setDealKind(omSD.getDealKind());
				                		setQuantity(omSD.getQuantity());
				                		setqTUnit(omSD.getqTUnit());
				                		setuPrice(omSD.getuPrice());
				                		setDiscRate(omSD.getDiscRate());
				                		setSellPayType(omSD.getSellPayType());
				                		setSellDate(omSD.getSellDate());
				                		setPriceUnit(omSD.getPriceUnit());
				                		setuPrice1(omSD.getuPrice1());
				                		setsKind(omSD.getsKind());
				                		setPriceMark(omSD.getPriceMark());
				                		setNote(omSD.getNote());
				                		setRid(0);
				                		break;
		                			}
		                			
		                		}
		                	}
		                }
		            }
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} finally {
					if(fr != null)
						try {
							fr.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
				if(getRid()>=0) break;
			}  
		}
		return this;
	}
	
	public List<SellDetail> getSellDetailOrderBySellDateXML(LikDBAdapter adapter) {
		List<SellDetail> result = new ArrayList<SellDetail>();
		String dataDir = Environment.getExternalStorageDirectory()+adapter.getCtx().getResources().getString(R.string.SellDetalFileDir);
		XMLUtil util = XMLUtil.getInstance(adapter.getCtx());
		int i1 = deliverOrderc==null?-1:deliverOrderc.intValue();
		int i2 = getCustDelverViewOrder()==null?-1:getCustDelverViewOrder().intValue();		
		if(hm == null || (customerIDc != getCustomerID() || i1 != i2)) {
			hm = util.getProductsMap(getCompanyID(), getCustomerID(), getCustDelverViewOrder());
			customerIDc = getCustomerID();
			deliverOrderc = getCustDelverViewOrder();
		}
		XMLUtil.SellDetallXML om = hm.get(getItemNo());
		if(om != null) {
			String[] aSellDate = om.getSellDateList().split(",");
			TreeSet<String> ts = new TreeSet<String>(new Comparator<String>() {

				@Override
				public int compare(String lhs, String rhs) {					
					return rhs.compareTo(lhs);
				}
				
			});
			for(String sSellDate : aSellDate) {
				ts.add(sSellDate);
			}
			for(String sSellDate : ts) {
				InputSource is = null;
				FileReader fr = null;
				try {
					String fileDir = dataDir;		
					fileDir += getCompanyID();
					fileDir += "/";
					fileDir += getCustomerID();
					fileDir += "/";
					if(getCustDelverViewOrder()!=null) {
						fileDir += getCustDelverViewOrder();
						fileDir += "/";
					}
					fileDir += sSellDate;
			        fileDir += ".xml";
			        Log.d(TAG,"fileDir="+fileDir);
			        File file = new File(fileDir);
			        if(!file.exists()) {
			        	Log.e(TAG,"not found file="+fileDir);
			        	continue;
			        }
			        fr = new FileReader(fileDir);
					is = new InputSource(new FileReader(fileDir));
		            DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		            Document doc = dfactory.newDocumentBuilder().parse(is);
		            Element root = doc.getDocumentElement();
		            Log.d(TAG,"root name="+root.getNodeName());
		            NodeList nlist = root.getChildNodes();
		            for(int i=0;i<nlist.getLength();i++) {
		                Node node = nlist.item(i);
		                if(node.getNodeName().equals("Size")) {
		                	Log.d(TAG,"size="+node.getFirstChild().getNodeValue());
		                } else if(node.getNodeName().equals(SellDetail.TABLE_NAME)) {
		        			SellDetail omSD = new SellDetail();
		                	NodeList nlist2 = node.getChildNodes();
		                	boolean needAdd = false;
		                	for(int j=0;j<nlist2.getLength();j++) {
		                		Node node2 = nlist2.item(j);
		                		if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SERIALID)) {
		                			omSD.setSerialID(Integer.parseInt(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_PRDTNAME)) {
		                			omSD.setPrdtName(node2.getFirstChild().getNodeValue());
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_DEALKIND)) {
		                			omSD.setDealKind(Integer.parseInt(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_QUANTITY)) {
		                			omSD.setQuantity(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_QTUNIT)) {
		                			omSD.setqTUnit(node2.getFirstChild().getNodeValue());
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_UPRICE)) {
		                			omSD.setuPrice(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_DISCRATE)) {
		                			omSD.setDiscRate(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SELLPAYTYPE)) {
		                			omSD.setSellPayType(node2.getFirstChild().getNodeValue());
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_ITEMNO)) {
		                			if(getItemNo().equals(node2.getFirstChild().getNodeValue())) needAdd = true;
		                			else break;
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SELLDATE)) {
		                			try {
										omSD.setSellDate(Constant.sqliteDFS.parse(node2.getFirstChild().getNodeValue()));
									} catch (DOMException e) {
										e.printStackTrace();
									} catch (ParseException e) {
										e.printStackTrace();
									}
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SKIND)) {
		                			omSD.setsKind(node2.getFirstChild().getNodeValue());
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SELLQTY1)) {
		                			omSD.setSellQty1(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SELLQTY2)) {
		                			omSD.setSellQty2(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SELLQTY3)) {
		                			omSD.setSellQty3(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SENDQTY1)) {
		                			omSD.setSendQty1(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SENDQTY2)) {
		                			omSD.setSendQty2(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SENDQTY3)) {
		                			omSD.setSendQty3(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_GIVEQTY1)) {
		                			omSD.setGiveQty1(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_GIVEQTY2)) {
		                			omSD.setGiveQty2(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_GIVEQTY3)) {
		                			omSD.setGiveQty3(Double.parseDouble(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_PRICEUNIT)) {
		                			omSD.setPriceUnit(node2.getFirstChild().getNodeValue());
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_PRICETOOLOW)) {
		                			omSD.setPriceTooLow(node2.getFirstChild().getNodeValue());
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_PRICEMARK)) {
		                			omSD.setPriceMark(Integer.parseInt(node2.getFirstChild().getNodeValue()));
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_NOTE)) {
		                			if(node2.getFirstChild()!= null && node2.getFirstChild().getNodeValue()!=null) omSD.setNote(node2.getFirstChild().getNodeValue().replaceAll("&amp;", "&"));
		                		//HAO 104.03.09
		                	    } else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SELLTITLE)) {
		                	    	if(node2.getFirstChild()!= null && node2.getFirstChild().getNodeValue()!=null) omSD.setSellTitle(node2.getFirstChild().getNodeValue());
		                		//
		                	    }  else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SENDDATE)) {
		                			try {
		                				if(node2.getFirstChild()!= null && node2.getFirstChild().getNodeValue()!=null) omSD.setSendDate(Constant.sqliteDFS.parse(node2.getFirstChild().getNodeValue()));
		                			} catch (ParseException e) {
		                				e.printStackTrace();
		                			}
		                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_ORDERDATE)) {
		                			try {
		                				if(node2.getFirstChild()!= null && node2.getFirstChild().getNodeValue()!=null) omSD.setOrderDate(Constant.sqliteDFS.parse(node2.getFirstChild().getNodeValue()));
		                			} catch (ParseException e) {
		                				e.printStackTrace();
		                			}
		                		}
		                	}
		                	if(needAdd) result.add(omSD);
		                }
		            }
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} finally {
					if(fr != null)
						try {
							fr.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
		        
			}
		}
		return result;		
	}
}
