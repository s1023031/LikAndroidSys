package com.lik.android.om;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
import com.lik.android.main.R;
import com.lik.android.main.LikDBAdapter;
import com.lik.android.main.MainMenuActivity;
import com.lik.android.view.SubAddProductsView;

public class Products extends BaseProducts
	implements ProcessDownloadInterface, Comparator<Products> {

	private static final long serialVersionUID = 5164840716152889523L;

	String SellDetailStorageType = "DB"; // default

	public Products getProductsByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_ITEMID+"="+getItemID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PRODUCTS_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_PRODUCTS_SERIALID_INDEX));
        	setItemNO(c.getString(READ_PRODUCTS_ITEMNO_INDEX));
        	setItemNM(c.getString(READ_PRODUCTS_ITEMNM_INDEX));
        	setDimension(c.getString(READ_PRODUCTS_DIMENSION_INDEX));
        	setUnit(c.getString(READ_PRODUCTS_UNIT_INDEX));
        	setUnit1(c.getString(READ_PRODUCTS_UNIT1_INDEX));
        	setUnit2(c.getString(READ_PRODUCTS_UNIT2_INDEX));
        	setUnit3(c.getString(READ_PRODUCTS_UNIT3_INDEX));
        	setUnit4(c.getString(READ_PRODUCTS_UNIT4_INDEX));
        	setUnit5(c.getString(READ_PRODUCTS_UNIT5_INDEX));
        	setPiece(c.getDouble(READ_PRODUCTS_PIECE_INDEX));
        	setRatio1(c.getDouble(READ_PRODUCTS_RATIO1_INDEX));
        	setRatio2(c.getDouble(READ_PRODUCTS_RATIO2_INDEX));
        	setRatio3(c.getDouble(READ_PRODUCTS_RATIO3_INDEX));
        	setClassify(c.getString(READ_PRODUCTS_CLASSIFY_INDEX));
        	setSuplNO(c.getString(READ_PRODUCTS_SUPLNO_INDEX));
        	setBarCode(c.getString(READ_PRODUCTS_BARCODE_INDEX));
        	setSalePrice(c.getDouble(READ_PRODUCTS_SALEPRICE_INDEX));
        	setSuggestPrice(c.getDouble(READ_PRODUCTS_SUGGESTPRICE_INDEX));
        	setKind(c.getInt(READ_PRODUCTS_KIND_INDEX));
        	setNewProduct(c.getString(READ_PRODUCTS_NEWPRODUCT_INDEX));
        	setNoReturn(c.getString(READ_PRODUCTS_NORETURN_INDEX));
        	setSellDays(c.getDouble(READ_PRODUCTS_SELLDAYS_INDEX));
        	setRealStock(c.getString(READ_PRODUCTS_REALSTOCK_INDEX));
        	setRemark(c.getString(READ_PRODUCTS_REMARK_INDEX));
            setVersionNo(c.getString(READ_PRODUCTS_VERSIONNO_INDEX));
            setSubClassify(c.getString(READ_PRODUCTS_SUBCLASSIFY_INDEX));
        	setSellMultiple(c.getDouble(READ_PRODUCTS_SELLMULTIPLE_INDEX));
        	setStockQty(c.getDouble(READ_PRODUCTS_STOCKQTY_INDEX));
        	setCubicMeasure(c.getDouble(READ_PRODUCTS_CUBICMEASURE_INDEX));
        	setPrdtUnitWeight(c.getDouble(READ_PRODUCTS_PRDTUNITWEIGHT_INDEX));
        	setWeightUnit(c.getString(READ_PRODUCTS_WEIGHTUNIT_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}
	
	public Products insertProducts(LikDBAdapter adapter) {
		getdb(adapter);
	  InsertHelper ih = adapter.getInsertHelper(getTableName());
	  ih.prepareForInsert();
	  ih.bind(2, getCompanyID());
	  ih.bind(3, getItemID());
	  ih.bind(4, getItemNO());
	  ih.bind(5, getItemNM());
	  ih.bind(6, getDimension());
	  ih.bind(7, getUnit());
	  ih.bind(8, getUnit1());
	  ih.bind(9, getUnit2());
	  ih.bind(10, getUnit3());
	  ih.bind(11, getUnit4());
	  ih.bind(12, getUnit5());
	  ih.bind(13, getPiece());
	  ih.bind(14, getRatio1());
	  ih.bind(15, getRatio2());
	  ih.bind(16, getRatio3());
	  ih.bind(17, getClassify());
	  ih.bind(18, getSuplNO());
	  ih.bind(19, getBarCode());
	  ih.bind(20, getSalePrice());
	  ih.bind(21, getSuggestPrice());
	  ih.bind(22, getKind());
	  ih.bind(23, getNewProduct());
	  ih.bind(24, getNoReturn());
	  ih.bind(25, getSellDays());
	  ih.bind(26, getRealStock());
	  ih.bind(27, getRemark());
	  ih.bind(28, getVersionNo());
	  ih.bind(29, getSubClassify());
	  ih.bind(30, getSellMultiple());
	  ih.bind(31, getStockQty());
	  ih.bind(32, getCubicMeasure());
	  ih.bind(33, getPrdtUnitWeight());
	  ih.bind(34, getWeightUnit());
	    long rid = ih.execute();
        if(rid != -1) setRid(0);
	  return this;
	}

//	public Products insertProducts(LikDBAdapter adapter) {
//		SQLiteDatabase db = getdb(adapter);
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(COLUMN_NAME_COMPANYID, getCompanyID());
//        initialValues.put(COLUMN_NAME_ITEMID, getItemID());
//        initialValues.put(COLUMN_NAME_ITEMNO, getItemNO());
//        initialValues.put(COLUMN_NAME_ITEMNM, getItemNM());
//        initialValues.put(COLUMN_NAME_DIMENSION, getDimension());
//        initialValues.put(COLUMN_NAME_UNIT, getUnit());
//        initialValues.put(COLUMN_NAME_UNIT1, getUnit1());
//        initialValues.put(COLUMN_NAME_UNIT2, getUnit2());
//        initialValues.put(COLUMN_NAME_UNIT3, getUnit3());
//        initialValues.put(COLUMN_NAME_UNIT4, getUnit4());
//        initialValues.put(COLUMN_NAME_UNIT5, getUnit5());
//        initialValues.put(COLUMN_NAME_PIECE, getPiece());
//        initialValues.put(COLUMN_NAME_RATIO1, getRatio1());
//        initialValues.put(COLUMN_NAME_RATIO2, getRatio2());
//        initialValues.put(COLUMN_NAME_RATIO3, getRatio3());
//        initialValues.put(COLUMN_NAME_CLASSIFY, getClassify());
//        initialValues.put(COLUMN_NAME_SUPLNO, getSuplNO());
//        initialValues.put(COLUMN_NAME_BARCODE, getBarCode());
//        initialValues.put(COLUMN_NAME_SALEPRICE, getSalePrice());
//        initialValues.put(COLUMN_NAME_SUGGESTPRICE, getSuggestPrice());
//        initialValues.put(COLUMN_NAME_KIND, getKind());
//        initialValues.put(COLUMN_NAME_NEWPRODUCT, getNewProduct());
//        initialValues.put(COLUMN_NAME_NORETURN, getNoReturn());
//        initialValues.put(COLUMN_NAME_EMPTYSTOCK, getEmptyStock());
//        initialValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
//        long rid = db.insert(TABLE_NAME, null, initialValues);
//        setRid(rid);
////        db.close();
//        closedb(adapter);
//		return this;
//	}

	public Products updateProducts(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();

        updateValues.put(COLUMN_NAME_ITEMNO, getItemNO());
        updateValues.put(COLUMN_NAME_ITEMNM, getItemNM());
        updateValues.put(COLUMN_NAME_DIMENSION, getDimension());
        updateValues.put(COLUMN_NAME_UNIT, getUnit());
        updateValues.put(COLUMN_NAME_UNIT1, getUnit1());
        updateValues.put(COLUMN_NAME_UNIT2, getUnit2());
        updateValues.put(COLUMN_NAME_UNIT3, getUnit3());
        updateValues.put(COLUMN_NAME_UNIT4, getUnit4());
        updateValues.put(COLUMN_NAME_UNIT5, getUnit5());
        updateValues.put(COLUMN_NAME_PIECE, getPiece());
        updateValues.put(COLUMN_NAME_RATIO1, getRatio1());
        updateValues.put(COLUMN_NAME_RATIO2, getRatio2());
        updateValues.put(COLUMN_NAME_RATIO3, getRatio3());
        updateValues.put(COLUMN_NAME_CLASSIFY, getClassify());
        updateValues.put(COLUMN_NAME_SUPLNO, getSuplNO());
        updateValues.put(COLUMN_NAME_BARCODE, getBarCode());
        updateValues.put(COLUMN_NAME_SALEPRICE, getSalePrice());
        updateValues.put(COLUMN_NAME_SUGGESTPRICE, getSuggestPrice());
        updateValues.put(COLUMN_NAME_KIND, getKind());
        updateValues.put(COLUMN_NAME_NEWPRODUCT, getNewProduct());
        updateValues.put(COLUMN_NAME_NORETURN, getNoReturn());
        updateValues.put(COLUMN_NAME_SELLDAYS, getSellDays());
        updateValues.put(COLUMN_NAME_REALSTOCK, getRealStock());
        updateValues.put(COLUMN_NAME_REMARK, getRemark());
        updateValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
        updateValues.put(COLUMN_NAME_SUBCLASSIFY, getSubClassify());
        updateValues.put(COLUMN_NAME_SELLMULTIPLE, getSellMultiple());
        updateValues.put(COLUMN_NAME_STOCKQTY, getStockQty());
        updateValues.put(COLUMN_NAME_CUBICMEASURE, getCubicMeasure());
        updateValues.put(COLUMN_NAME_PRDTUNITWEIGHT, getPrdtUnitWeight());
        updateValues.put(COLUMN_NAME_WEIGHTUNIT, getWeightUnit());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(getTableName(), updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
//        db.close();
        closedb(adapter);
		return this;		
	}
	
	public Products deleteProducts(LikDBAdapter adapter) {
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
	public boolean processDownload(LikDBAdapter adapter,
			Map<String, String> detail, boolean isOnlyInsert) {
		boolean result = true;
		Log.d(TAG,"seq="+detail.get("DownloadSeq"));
		String flag = detail.get(FLAG_KEY);
		setCompanyID(Integer.parseInt(detail.get(COLUMN_NAME_COMPANYID)));
		setItemID(Integer.parseInt(detail.get(COLUMN_NAME_ITEMID)));
		if(!isOnlyInsert) getProductsByKey(adapter);
		setItemNO(detail.get(COLUMN_NAME_ITEMNO));
		setItemNM(detail.get(COLUMN_NAME_ITEMNM));
		setDimension(detail.get(COLUMN_NAME_DIMENSION));
		setUnit(detail.get(COLUMN_NAME_UNIT));
		setUnit1(detail.get(COLUMN_NAME_UNIT1));
		setUnit2(detail.get(COLUMN_NAME_UNIT2));
		setUnit3(detail.get(COLUMN_NAME_UNIT3));
		setUnit4(detail.get(COLUMN_NAME_UNIT4));
		setUnit5(detail.get(COLUMN_NAME_UNIT5));
		setPiece(Double.parseDouble(detail.get(COLUMN_NAME_PIECE)));
		setRatio1(Double.parseDouble(detail.get(COLUMN_NAME_RATIO1)));
		setRatio2(Double.parseDouble(detail.get(COLUMN_NAME_RATIO2)));
		setRatio3(Double.parseDouble(detail.get(COLUMN_NAME_RATIO3)));
		setClassify(detail.get(COLUMN_NAME_CLASSIFY)==null?null:detail.get(COLUMN_NAME_CLASSIFY).trim());
		setSuplNO(detail.get(COLUMN_NAME_SUPLNO));
		setBarCode(detail.get(COLUMN_NAME_BARCODE));
		setSalePrice(Double.parseDouble(detail.get(COLUMN_NAME_SALEPRICE)));
		setSuggestPrice(Double.parseDouble(detail.get(COLUMN_NAME_SUGGESTPRICE)));
		setKind(Integer.parseInt(detail.get(COLUMN_NAME_KIND)));
		setNewProduct(detail.get(COLUMN_NAME_NEWPRODUCT));
		setNoReturn(detail.get(COLUMN_NAME_NORETURN));
		setSellDays(Double.parseDouble(detail.get(COLUMN_NAME_SELLDAYS)));
		setRealStock(detail.get(COLUMN_NAME_REALSTOCK));
		setRemark(detail.get(COLUMN_NAME_REMARK));
		setVersionNo(detail.get(COLUMN_NAME_VERSIONNO));
		setSubClassify(detail.get(COLUMN_NAME_SUBCLASSIFY));
		setSellMultiple(Double.parseDouble(detail.get(COLUMN_NAME_SELLMULTIPLE)));
		setStockQty(Double.parseDouble(detail.get(COLUMN_NAME_STOCKQTY)));
		setCubicMeasure(Double.parseDouble(detail.get(COLUMN_NAME_CUBICMEASURE)));
		setPrdtUnitWeight(Double.parseDouble(detail.get(COLUMN_NAME_PRDTUNITWEIGHT)));
		setWeightUnit(detail.get(COLUMN_NAME_WEIGHTUNIT));
		if(isOnlyInsert) insertProducts(adapter);
		else {
			if(getRid()<0) insertProducts(adapter);
			else {
				if(flag.equals(FLAG_DELETE)) doDelete(adapter);
				else updateProducts(adapter);
			}
		}
		if(getRid()<0) result = false;
		return result;
	}

	@Override
	public Products doInsert(LikDBAdapter adapter) {
		return insertProducts(adapter);
	}

	@Override
	public Products doUpdate(LikDBAdapter adapter) {
		return updateProducts(adapter);
	}

	@Override
	public Products doDelete(LikDBAdapter adapter) {
		return deleteProducts(adapter);
	}

	@Override
	public Products findByKey(LikDBAdapter adapter) {
		return getProductsByKey(adapter);
	}

	@Override
	public Products queryBySerialID(LikDBAdapter adapter) {
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
				READ_PRODUCTS_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_PRODUCTS_SERIALID_INDEX));
    		setCompanyID(c.getInt(READ_PRODUCTS_COMPANYID_INDEX));
    		setItemID(c.getInt(READ_PRODUCTS_ITEMID_INDEX));
        	setItemNO(c.getString(READ_PRODUCTS_ITEMNO_INDEX));
        	setItemNM(c.getString(READ_PRODUCTS_ITEMNM_INDEX));
        	setDimension(c.getString(READ_PRODUCTS_DIMENSION_INDEX));
        	setUnit(c.getString(READ_PRODUCTS_UNIT_INDEX));
        	setUnit1(c.getString(READ_PRODUCTS_UNIT1_INDEX));
        	setUnit2(c.getString(READ_PRODUCTS_UNIT2_INDEX));
        	setUnit3(c.getString(READ_PRODUCTS_UNIT3_INDEX));
        	setUnit4(c.getString(READ_PRODUCTS_UNIT4_INDEX));
        	setUnit5(c.getString(READ_PRODUCTS_UNIT5_INDEX));
        	setPiece(c.getDouble(READ_PRODUCTS_PIECE_INDEX));
        	setRatio1(c.getDouble(READ_PRODUCTS_RATIO1_INDEX));
        	setRatio2(c.getDouble(READ_PRODUCTS_RATIO2_INDEX));
        	setRatio3(c.getDouble(READ_PRODUCTS_RATIO3_INDEX));
        	setClassify(c.getString(READ_PRODUCTS_CLASSIFY_INDEX));
        	setSuplNO(c.getString(READ_PRODUCTS_SUPLNO_INDEX));
        	setBarCode(c.getString(READ_PRODUCTS_BARCODE_INDEX));
        	setSalePrice(c.getDouble(READ_PRODUCTS_SALEPRICE_INDEX));
        	setSuggestPrice(c.getDouble(READ_PRODUCTS_SUGGESTPRICE_INDEX));
        	setKind(c.getInt(READ_PRODUCTS_KIND_INDEX));
        	setNewProduct(c.getString(READ_PRODUCTS_NEWPRODUCT_INDEX));
        	setNoReturn(c.getString(READ_PRODUCTS_NORETURN_INDEX));
        	setSellDays(c.getDouble(READ_PRODUCTS_SELLDAYS_INDEX));
        	setRealStock(c.getString(READ_PRODUCTS_REALSTOCK_INDEX));
        	setRemark(c.getString(READ_PRODUCTS_REMARK_INDEX));
            setVersionNo(c.getString(READ_PRODUCTS_VERSIONNO_INDEX));
            setSubClassify(c.getString(READ_PRODUCTS_SUBCLASSIFY_INDEX));
        	setSellMultiple(c.getDouble(READ_PRODUCTS_SELLMULTIPLE_INDEX));
        	setStockQty(c.getDouble(READ_PRODUCTS_STOCKQTY_INDEX));
        	setCubicMeasure(c.getDouble(READ_PRODUCTS_CUBICMEASURE_INDEX));
        	setPrdtUnitWeight(c.getDouble(READ_PRODUCTS_PRDTUNITWEIGHT_INDEX));
        	setWeightUnit(c.getString(READ_PRODUCTS_WEIGHTUNIT_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}
	
	public List<String> queryClassifyByCompanyID(LikDBAdapter adapter) {
		List<String> result = new ArrayList<String>();
		String[] R_PRODUCTS_PROJECTION = new String[] { COLUMN_NAME_CLASSIFY};
		SQLiteDatabase db = getdb(adapter);
		String[] selectionArgs = {String.valueOf(getCompanyID())};
		Cursor c = db.query(getTableName(), 
				R_PRODUCTS_PROJECTION, 
				COLUMN_NAME_COMPANYID+"=?", 
				selectionArgs, 
				COLUMN_NAME_CLASSIFY, 
				null, 
				COLUMN_NAME_CLASSIFY+" asc");
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

	public List<Products> queryProductsByClassifySuppliers(LikDBAdapter adapter) {
		List<Products> result = new ArrayList<Products>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		Log.d("xxxx","getClassify="+getClassify());
		Log.d("xxxx","getSuplNO="+getSuplNO());
		if(getClassify() != null && !getClassify().trim().equals("")) qb.appendWhere(" and "+COLUMN_NAME_CLASSIFY+"='"+getClassify()+"'");
		if(getSuplNO() != null && !getSuplNO().trim().equals("")) qb.appendWhere(" and "+COLUMN_NAME_SUPLNO+"='"+getSuplNO()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PRODUCTS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				getOrder()==ORDERBY_ITEMNO?(COLUMN_NAME_ITEMNO+" asc"):(COLUMN_NAME_SUBCLASSIFY+","+COLUMN_NAME_ITEMNO+" asc")        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	Products om = new Products();
            	om.setSerialID(c.getInt(READ_PRODUCTS_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_PRODUCTS_COMPANYID_INDEX));
            	om.setItemID(c.getInt(READ_PRODUCTS_ITEMID_INDEX));
            	om.setItemNO(c.getString(READ_PRODUCTS_ITEMNO_INDEX));
            	om.setItemNM(c.getString(READ_PRODUCTS_ITEMNM_INDEX));
            	om.setDimension(c.getString(READ_PRODUCTS_DIMENSION_INDEX));
            	om.setUnit(c.getString(READ_PRODUCTS_UNIT_INDEX));
            	om.setUnit1(c.getString(READ_PRODUCTS_UNIT1_INDEX));
            	om.setUnit2(c.getString(READ_PRODUCTS_UNIT2_INDEX));
            	om.setUnit3(c.getString(READ_PRODUCTS_UNIT3_INDEX));
            	om.setUnit4(c.getString(READ_PRODUCTS_UNIT4_INDEX));
            	om.setUnit5(c.getString(READ_PRODUCTS_UNIT5_INDEX));
            	om.setPiece(c.getDouble(READ_PRODUCTS_PIECE_INDEX));
            	om.setRatio1(c.getDouble(READ_PRODUCTS_RATIO1_INDEX));
            	om.setRatio2(c.getDouble(READ_PRODUCTS_RATIO2_INDEX));
            	om.setRatio3(c.getDouble(READ_PRODUCTS_RATIO3_INDEX));
            	om.setClassify(c.getString(READ_PRODUCTS_CLASSIFY_INDEX));
            	om.setSuplNO(c.getString(READ_PRODUCTS_SUPLNO_INDEX));
            	om.setBarCode(c.getString(READ_PRODUCTS_BARCODE_INDEX));
            	om.setSalePrice(c.getDouble(READ_PRODUCTS_SALEPRICE_INDEX));
            	om.setSuggestPrice(c.getDouble(READ_PRODUCTS_SUGGESTPRICE_INDEX));
            	om.setKind(c.getInt(READ_PRODUCTS_KIND_INDEX));
            	om.setNewProduct(c.getString(READ_PRODUCTS_NEWPRODUCT_INDEX));
            	om.setNoReturn(c.getString(READ_PRODUCTS_NORETURN_INDEX));
            	om.setSellDays(c.getDouble(READ_PRODUCTS_SELLDAYS_INDEX));
            	om.setRealStock(c.getString(READ_PRODUCTS_REALSTOCK_INDEX));
            	om.setRemark(c.getString(READ_PRODUCTS_REMARK_INDEX));
            	om.setVersionNo(c.getString(READ_PRODUCTS_VERSIONNO_INDEX));
                om.setSubClassify(c.getString(READ_PRODUCTS_SUBCLASSIFY_INDEX));
            	om.setSellMultiple(c.getDouble(READ_PRODUCTS_SELLMULTIPLE_INDEX));
            	om.setStockQty(c.getDouble(READ_PRODUCTS_STOCKQTY_INDEX));
            	om.setCubicMeasure(c.getDouble(READ_PRODUCTS_CUBICMEASURE_INDEX));
            	om.setPrdtUnitWeight(c.getDouble(READ_PRODUCTS_PRDTUNITWEIGHT_INDEX));
            	om.setWeightUnit(c.getString(READ_PRODUCTS_WEIGHTUNIT_INDEX));
                om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
        }
//        db.close();		
        c.close();
        closedb(adapter);
		return result;
	}

	private int customerID; // used for join column
	private Integer deliverOrder; // used for join column
	
	
	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public Integer getDeliverOrder() {
		return deliverOrder;
	}

	public void setDeliverOrder(Integer deliverOrder) {
		this.deliverOrder = deliverOrder;
	}

	/**
	 * ���o�Ȥ����L���~���
	 * CustomerID
	 * CompanyID
	 * ItemNO --- optional
	 * ItemNM --- optional
	 * BarCode --- optional
	 * @param adapter
	 * @return
	 */
	public List<SubAddProductsView> getProductsBySellCache(LikDBAdapter adapter) {
		List<SubAddProductsView> result = new ArrayList<SubAddProductsView>();
		boolean isSHD = false;
		if(adapter.getCtx() instanceof MainMenuActivity) {
			MainMenuActivity mmf = (MainMenuActivity)adapter.getCtx();
	        if(mmf.omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_SHD)) {
	        	isSHD = true;
	        }
		}
		SQLiteDatabase db = getdb(adapter);
//		String sql = "select * from Products, SellDetail where Products.ItemNO=SellDetail.ItemNO and Products.CompanyID=SellDetail.CompanyID and SellDetail.CompanyID=? and SellDetail.CustomerID=? and SellDetail.selldate >= '2011-05-29 00:00:00.000'";
		String sql = "select * from "+getTableName()+" where Products_"+adapter.getCompanyID()+".ItemNO in (select distinct SellDetail_"+
				adapter.getCompanyID()+".ItemNO from SellDetail_"+adapter.getCompanyID()+" where SellDetail_"+adapter.getCompanyID()+
				".CompanyID=? and SellDetail_"+adapter.getCompanyID()+".CustomerID=?) and Products_"+adapter.getCompanyID()+".CompanyID=? order by "+(getOrder()==ORDERBY_ITEMNO?(getTableName()+".ItemNO"):(getTableName()+".SubClassify,"+getTableName()+".ItemNO"));
		Log.d(TAG,sql);
		String[] selectionArgs = {String.valueOf(getCompanyID()),String.valueOf(getCustomerID()),String.valueOf(getCompanyID())};
		Cursor c = db.rawQuery(sql, selectionArgs);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	SubAddProductsView om = new SubAddProductsView();
            	om.setSerialID(c.getInt(READ_PRODUCTS_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_PRODUCTS_COMPANYID_INDEX));
            	om.setItemID(c.getInt(READ_PRODUCTS_ITEMID_INDEX));
            	om.setItemNO(c.getString(READ_PRODUCTS_ITEMNO_INDEX));
            	om.setItemNM(c.getString(READ_PRODUCTS_ITEMNM_INDEX));
            	om.setDimension(c.getString(READ_PRODUCTS_DIMENSION_INDEX));
            	om.setUnit(c.getString(READ_PRODUCTS_UNIT_INDEX));
            	om.setUnit1(c.getString(READ_PRODUCTS_UNIT1_INDEX));
            	om.setUnit2(c.getString(READ_PRODUCTS_UNIT2_INDEX));
            	om.setUnit3(c.getString(READ_PRODUCTS_UNIT3_INDEX));
            	om.setUnit4(c.getString(READ_PRODUCTS_UNIT4_INDEX));
            	om.setUnit5(c.getString(READ_PRODUCTS_UNIT5_INDEX));
            	om.setPiece(c.getDouble(READ_PRODUCTS_PIECE_INDEX));
            	om.setRatio1(c.getDouble(READ_PRODUCTS_RATIO1_INDEX));
            	om.setRatio2(c.getDouble(READ_PRODUCTS_RATIO2_INDEX));
            	om.setRatio3(c.getDouble(READ_PRODUCTS_RATIO3_INDEX));
            	om.setClassify(c.getString(READ_PRODUCTS_CLASSIFY_INDEX));
            	om.setSuplNO(c.getString(READ_PRODUCTS_SUPLNO_INDEX));
            	om.setBarCode(c.getString(READ_PRODUCTS_BARCODE_INDEX));
            	om.setSalePrice(c.getDouble(READ_PRODUCTS_SALEPRICE_INDEX));
            	om.setSuggestPrice(c.getDouble(READ_PRODUCTS_SUGGESTPRICE_INDEX));
            	om.setKind(c.getInt(READ_PRODUCTS_KIND_INDEX));
            	om.setNewProduct(c.getString(READ_PRODUCTS_NEWPRODUCT_INDEX));
            	om.setNoReturn(c.getString(READ_PRODUCTS_NORETURN_INDEX));
            	om.setSellDays(c.getDouble(READ_PRODUCTS_SELLDAYS_INDEX));
            	om.setRealStock(c.getString(READ_PRODUCTS_REALSTOCK_INDEX));
            	om.setRemark(c.getString(READ_PRODUCTS_REMARK_INDEX));
            	om.setVersionNo(c.getString(READ_PRODUCTS_VERSIONNO_INDEX));
                om.setSubClassify(c.getString(READ_PRODUCTS_SUBCLASSIFY_INDEX));
            	om.setSellMultiple(c.getDouble(READ_PRODUCTS_SELLMULTIPLE_INDEX));
            	om.setStockQty(c.getDouble(READ_PRODUCTS_STOCKQTY_INDEX));
            	om.setCubicMeasure(c.getDouble(READ_PRODUCTS_CUBICMEASURE_INDEX));
            	om.setPrdtUnitWeight(c.getDouble(READ_PRODUCTS_PRDTUNITWEIGHT_INDEX));
            	om.setWeightUnit(c.getString(READ_PRODUCTS_WEIGHTUNIT_INDEX));
            	om.setPackAmount(getPackAmount(om));
//            	om.setSellPayType(c.getString(READ_PRODUCTS_VERSIONNO_INDEX+BaseSellDetail.READ_SELLDETAIL_SELLPAYTYPE_INDEX+1)); // SellDetail.SellPayType
//            	om.setPriceUnit(c.getString(READ_PRODUCTS_VERSIONNO_INDEX+BaseSellDetail.READ_SELLDETAIL_PRICEUNIT_INDEX+1));  // SellDetail.PriceUnit
//            	om.setUprice(c.getDouble(READ_PRODUCTS_VERSIONNO_INDEX+BaseSellDetail.READ_SELLDETAIL_UPRICE_INDEX+1));  // SellDetail.UPrice
                if(isSHD) {
                	if(getItemNO() != null && om.getItemNO().indexOf(getItemNO())<0) continue;
                } else {
                	if(getItemNO() != null && !om.getItemNO().startsWith(getItemNO())) continue;
                }
                if(getBarCode() != null) {
                	// find barcode in prdtunit 2012/8/21
                	PrdtUnits omPU = new PrdtUnits();
                	omPU.setBarCode(getBarCode());
                	if(!omPU.isExistsBarCode(adapter)) continue;
//                	if(om.getBarCode() == null) continue;
//                	if(!om.getBarCode().startsWith(getBarCode())) continue;
                }
                if(getItemNM() != null && om.getItemNM().indexOf(getItemNM()) == -1) continue;
            	result.add(om);
            } while(c.moveToNext());
        }
//        db.close();		
        c.close();
        closedb(adapter);
		return result;
	}

	public List<SubAddProductsView> getProductsBySellCacheXML(LikDBAdapter adapter) {
		HashMap<String,Date> hm = new HashMap<String,Date>();
		String itemNoList = null;
		String dataDir = Environment.getExternalStorageDirectory()+adapter.getCtx().getResources().getString(R.string.SellDetalFileDir);
		InputSource is = null;
		FileReader fr = null;
		try {
			String fileDir = dataDir;				
			fileDir += getCompanyID();
			fileDir += "/";
			fileDir += getCustomerID();
			fileDir += "/";
			if(getDeliverOrder()!=null) {
				fileDir += getDeliverOrder();
				fileDir += "/";
			}
	        fileDir += "summary.xml";
	        Log.d(TAG,"fileDir="+fileDir);
	        File file = new File(fileDir);
	        if(!file.exists()) {
	        	Log.e(TAG,"not found file="+fileDir);
	        	return getProductsBySellCacheXML(adapter,itemNoList,hm);
	        }
	        fr = new FileReader(fileDir);
			is = new InputSource(new FileReader(fileDir));
            DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
            Document doc = dfactory.newDocumentBuilder().parse(is);
            Element root = doc.getDocumentElement();
            Log.d(TAG,"root name="+root.getNodeName());
            NodeList nlist = root.getChildNodes();
        	StringBuffer sb = new StringBuffer();
            for(int i=0;i<nlist.getLength();i++) {
                Node node = nlist.item(i);
//        		if(i != 0) sb.append(",");
                if(node.getNodeName().equals(SellDetail.TABLE_NAME)) {
                	NodeList nlist2 = node.getChildNodes();
                	String itemNo = null;
                	String sSellDate = null;
                	for(int j=0;j<nlist2.getLength();j++) {
                		Node node2 = nlist2.item(j);
                		if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_ITEMNO)) {
                			itemNo = node2.getFirstChild().getNodeValue();
                			sb.append("'").append(itemNo).append("'").append(",");   			
                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SELLDATE)) {
                			String s = node2.getFirstChild().getNodeValue();
                			if(s.indexOf(",")>=0) {
                				String[] split = s.split(",");
                				sSellDate = split[0];
                			} else {
                				sSellDate = node2.getFirstChild().getNodeValue();
                			}
                		}
                	}
					try {
						hm.put(itemNo, Constant.sqliteDFS.parse(sSellDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
                }
            }
            itemNoList = sb.toString().substring(0, sb.toString().length()-1);
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
		return getProductsBySellCacheXML(adapter,itemNoList,hm);
	}
	
	private List<SubAddProductsView> getProductsBySellCacheXML(LikDBAdapter adapter, String itemNoList, HashMap<String,Date> hm) {
		List<SubAddProductsView> result = new ArrayList<SubAddProductsView>();
		boolean isSHD = false;
		if(adapter.getCtx() instanceof MainMenuActivity) {
			MainMenuActivity mmf = (MainMenuActivity)adapter.getCtx();
	        if(mmf.omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_SHD)) {
	        	isSHD = true;
	        }
		}
		TreeSet<SubAddProductsView> set = new TreeSet<SubAddProductsView>(new Comparator<SubAddProductsView>() {

			@Override
			public int compare(SubAddProductsView lhs, SubAddProductsView rhs) {
				if(getOrder()==ORDERBY_SUBCLASSIFY_ITEMNO) {
					StringBuffer sbl = new StringBuffer();
					StringBuffer sbr = new StringBuffer();
					String lhsid =  lhs.getSubClassify()==null?"":lhs.getSubClassify();
					String rhsid =  rhs.getSubClassify()==null?"":rhs.getSubClassify();
					int diff = lhsid.length()-rhsid.length();
					if(diff>0) {
						for(int i=0;i<diff;i++) {
							rhsid = rhsid+"0";
						}
					} else if(diff<0) {
						for(int i=0;i<-diff;i++) {
							lhsid = lhsid+"0";
						}					
					}
					sbl.append(lhsid).append(lhs.getItemNO());
					sbr.append(rhsid).append(rhs.getItemNO());
					return sbl.toString().compareTo(sbr.toString());
				} else return Long.valueOf(lhs.getSerialID()).compareTo(Long.valueOf(rhs.getSerialID()));
			}
			
		});
		if(itemNoList == null) return result; 
		SQLiteDatabase db = getdb(adapter);
		String sql = "select * from "+getTableName()+",PrdtUnits_"+adapter.getCompanyID()+" where Products_"+adapter.getCompanyID()+".ItemNO in ("+
				itemNoList+
				") and Products_"+adapter.getCompanyID()+".CompanyID=PrdtUnits_"+adapter.getCompanyID()+".CompanyID"+
				" and Products_"+adapter.getCompanyID()+".ItemID=PrdtUnits_"+adapter.getCompanyID()+".ItemID"+
				(getBarCode()==null?"":" and PrdtUnits_"+adapter.getCompanyID()+".BarCode like '%"+getBarCode()+"'")+
				" and Products_"+adapter.getCompanyID()+".CompanyID=? order by "+(getOrder()==ORDERBY_ITEMNO?(getTableName()+".ItemNO"):(getTableName()+".SubClassify,"+getTableName()+".ItemNO"));
		Log.d(TAG,sql);
		String[] selectionArgs = {String.valueOf(getCompanyID())};
		Cursor c = db.rawQuery(sql, selectionArgs);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	SubAddProductsView om = new SubAddProductsView();
            	om.setSerialID(c.getInt(READ_PRODUCTS_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_PRODUCTS_COMPANYID_INDEX));
            	om.setItemID(c.getInt(READ_PRODUCTS_ITEMID_INDEX));
            	om.setItemNO(c.getString(READ_PRODUCTS_ITEMNO_INDEX));
            	om.setItemNM(c.getString(READ_PRODUCTS_ITEMNM_INDEX));
            	om.setDimension(c.getString(READ_PRODUCTS_DIMENSION_INDEX));
            	om.setUnit(c.getString(READ_PRODUCTS_UNIT_INDEX));
            	om.setUnit1(c.getString(READ_PRODUCTS_UNIT1_INDEX));
            	om.setUnit2(c.getString(READ_PRODUCTS_UNIT2_INDEX));
            	om.setUnit3(c.getString(READ_PRODUCTS_UNIT3_INDEX));
            	om.setUnit4(c.getString(READ_PRODUCTS_UNIT4_INDEX));
            	om.setUnit5(c.getString(READ_PRODUCTS_UNIT5_INDEX));
            	om.setPiece(c.getDouble(READ_PRODUCTS_PIECE_INDEX));
            	om.setRatio1(c.getDouble(READ_PRODUCTS_RATIO1_INDEX));
            	om.setRatio2(c.getDouble(READ_PRODUCTS_RATIO2_INDEX));
            	om.setRatio3(c.getDouble(READ_PRODUCTS_RATIO3_INDEX));
            	om.setClassify(c.getString(READ_PRODUCTS_CLASSIFY_INDEX));
            	om.setSuplNO(c.getString(READ_PRODUCTS_SUPLNO_INDEX));
            	om.setBarCode(c.getString(READ_PRODUCTS_BARCODE_INDEX));
            	om.setSalePrice(c.getDouble(READ_PRODUCTS_SALEPRICE_INDEX));
            	om.setSuggestPrice(c.getDouble(READ_PRODUCTS_SUGGESTPRICE_INDEX));
            	om.setKind(c.getInt(READ_PRODUCTS_KIND_INDEX));
            	om.setNewProduct(c.getString(READ_PRODUCTS_NEWPRODUCT_INDEX));
            	om.setNoReturn(c.getString(READ_PRODUCTS_NORETURN_INDEX));
            	om.setSellDays(c.getDouble(READ_PRODUCTS_SELLDAYS_INDEX));
            	om.setRealStock(c.getString(READ_PRODUCTS_REALSTOCK_INDEX));
            	om.setRemark(c.getString(READ_PRODUCTS_REMARK_INDEX));
            	om.setVersionNo(c.getString(READ_PRODUCTS_VERSIONNO_INDEX));
                om.setSubClassify(c.getString(READ_PRODUCTS_SUBCLASSIFY_INDEX));
            	om.setSellMultiple(c.getDouble(READ_PRODUCTS_SELLMULTIPLE_INDEX));
            	om.setStockQty(c.getDouble(READ_PRODUCTS_STOCKQTY_INDEX));
            	om.setCubicMeasure(c.getDouble(READ_PRODUCTS_CUBICMEASURE_INDEX));
            	om.setPrdtUnitWeight(c.getDouble(READ_PRODUCTS_PRDTUNITWEIGHT_INDEX));
            	om.setWeightUnit(c.getString(READ_PRODUCTS_WEIGHTUNIT_INDEX));
            	om.setPackAmount(getPackAmount(om));
            	if(hm.get(om.getItemNO()) != null) om.setSellDate(hm.get(om.getItemNO()));
            	if(isSHD) {
            		if(getItemNO() != null && om.getItemNO().indexOf(getItemNO())<0) continue;
            	} else {
            		if(getItemNO() != null && !om.getItemNO().startsWith(getItemNO())) continue;
            	}
//                if(getBarCode() != null) {
//                	// find barcode in prdtunit 2012/8/21
//                	PrdtUnits omPU = new PrdtUnits();
//                	omPU.setBarCode(getBarCode());
//                	if(!omPU.isExistsBarCode(adapter)) continue;
////                	if(om.getBarCode() == null) continue;
////                	if(!om.getBarCode().startsWith(getBarCode())) continue;
//                }
                if(getItemNM() != null && om.getItemNM().indexOf(getItemNM()) == -1) continue;
//            	result.add(om);
                set.add(om);
            } while(c.moveToNext());
        }
        c.close();
        closedb(adapter);
        for(SubAddProductsView omview:set) {
    	  result.add(omview);
    	  Log.d(TAG,"subclassify="+omview.getSubClassify()+",itemNo="+omview.getItemNO());
      	}
		return result;
	}
	
	boolean isPriceMarkRuleEnabled = true;
	
	public boolean isPriceMarkRuleEnabled() {
		return isPriceMarkRuleEnabled;
	}

	public void setPriceMarkRuleEnabled(boolean isPriceMarkRuleEnabled) {
		this.isPriceMarkRuleEnabled = isPriceMarkRuleEnabled;
	}

	public SubAddProductsView getProductsWithSellCache(LikDBAdapter adapter) {
		SubAddProductsView result = null;
		findByKey(adapter);
		if(getRid()>=0) {
			SellDetail omSD = new SellDetail();
			omSD.setCompanyID(getCompanyID());
			omSD.setCustomerID(getCustomerID());
			omSD.setItemNo(getItemNO());
			omSD.setCustDelverViewOrder(getDeliverOrder());
			omSD.setPriceMarkRuleEnabled(isPriceMarkRuleEnabled);
			if(adapter.getCtx().getText(R.string.SellDetailStorageType).equals("XML")) {
				omSD.getSellDetailByLatestSellDateXML(adapter);
			} else {
				omSD.getSellDetailByLatestSellDate(adapter);
			}
			result = new SubAddProductsView();
        	result.setSerialID(getSerialID());
        	result.setCompanyID(getCompanyID());
        	result.setItemID(getItemID());
        	result.setItemNO(getItemNO());
        	result.setItemNM(getItemNM());
        	result.setDimension(getDimension());
        	result.setUnit(getUnit());
        	result.setUnit1(getUnit1());
        	result.setUnit2(getUnit2());
        	result.setUnit3(getUnit3());
        	result.setUnit4(getUnit4());
        	result.setUnit5(getUnit5());
        	result.setPiece(getPiece());
        	result.setRatio1(getRatio1());
        	result.setRatio2(getRatio2());
        	result.setRatio3(getRatio3());
        	result.setClassify(getClassify());
        	result.setSuplNO(getSuplNO());
        	result.setBarCode(getBarCode());
        	result.setSalePrice(getSalePrice());
        	result.setSuggestPrice(getSuggestPrice());
        	result.setKind(getKind());
        	result.setNewProduct(getNewProduct());
        	result.setNoReturn(getNoReturn());
        	result.setSellDays(getSellDays());
        	result.setRealStock(getRealStock());
        	result.setRemark(getRemark());
        	result.setSubClassify(getSubClassify());
        	result.setSellMultiple(getSellMultiple());
        	result.setStockQty(getStockQty());
        	result.setCubicMeasure(getCubicMeasure());
        	result.setPrdtUnitWeight(getPrdtUnitWeight());
        	result.setWeightUnit(getWeightUnit());
        	if(omSD.getRid()>=0) {
            	result.setSellPayType(omSD.getSellPayType());
            	result.setPriceUnit(omSD.getPriceUnit());
            	result.setUprice(omSD.getuPrice());
            	result.setUprice1(omSD.getuPrice1());
            	result.setDiscRate(omSD.getDiscRate());
			}
		}
		return result;
	}

	private String priceGrade; // Promotion.PriceGrade
	private int promoteGroupID; // Promotion.CustID
	private String groupPriceGrade; // promoteGroupID.PriceGrade
	
	
	public String getPriceGrade() {
		return priceGrade;
	}

	public void setPriceGrade(String priceGrade) {
		this.priceGrade = priceGrade;
	}

	public int getPromoteGroupID() {
		return promoteGroupID;
	}

	public void setPromoteGroupID(int promoteGroupID) {
		this.promoteGroupID = promoteGroupID;
	}

	public String getGroupPriceGrade() {
		return groupPriceGrade;
	}

	public void setGroupPriceGrade(String groupPriceGrade) {
		this.groupPriceGrade = groupPriceGrade;
	}

	/**
	 * 1. getCompanyID+getItemID+getCustID
	 * 2.0. getCompanyID+getItemID+getPromoteGroupID
	 * 2. getCompanyID+getItemID+GroupPriceGrade+Promotion.CustID is NULL
	 * 2.1. getCompanyID+getItemID+getPriceGrade+Promotion.CustID is NULL
	 * 3. getCompanyID+getItemID+getPriceGrade is null + getCustID = 0 // cancelled 2012/8/17
	 * @return
	 */
	public List<Products> getProductsByPromotion(LikDBAdapter adapter) {
		List<Products> result = new ArrayList<Products>();
		TreeSet<Products> set = new TreeSet<Products>(this);
		SQLiteDatabase db = getdb(adapter);
		String sql = "select * from "+getTableName()+" where "+getTableName()+".CompanyID=? and "+getTableName()+
				".ItemID in (select Promotion_"+adapter.getCompanyID()+".ItemID from Promotion_"+adapter.getCompanyID()+
				" where Promotion_"+adapter.getCompanyID()+".CompanyID=? and Promotion_"+adapter.getCompanyID()+
				".CustID=?)";
		Log.d(TAG,sql);
		String[] selectionArgs = {String.valueOf(getCompanyID()),String.valueOf(getCompanyID()),String.valueOf(getCustomerID())};
		Cursor c = db.rawQuery(sql, selectionArgs);
        if (c != null && c.getCount()>0) {
            Log.d(TAG,"found in rule 1");
        	c.moveToFirst();
            do {
            	Products om = new Products();
            	om.setSerialID(c.getInt(READ_PRODUCTS_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_PRODUCTS_COMPANYID_INDEX));
            	om.setItemID(c.getInt(READ_PRODUCTS_ITEMID_INDEX));
            	om.setItemNO(c.getString(READ_PRODUCTS_ITEMNO_INDEX));
            	om.setItemNM(c.getString(READ_PRODUCTS_ITEMNM_INDEX));
            	om.setDimension(c.getString(READ_PRODUCTS_DIMENSION_INDEX));
            	om.setUnit(c.getString(READ_PRODUCTS_UNIT_INDEX));
            	om.setUnit1(c.getString(READ_PRODUCTS_UNIT1_INDEX));
            	om.setUnit2(c.getString(READ_PRODUCTS_UNIT2_INDEX));
            	om.setUnit3(c.getString(READ_PRODUCTS_UNIT3_INDEX));
            	om.setUnit4(c.getString(READ_PRODUCTS_UNIT4_INDEX));
            	om.setUnit5(c.getString(READ_PRODUCTS_UNIT5_INDEX));
            	om.setPiece(c.getDouble(READ_PRODUCTS_PIECE_INDEX));
            	om.setRatio1(c.getDouble(READ_PRODUCTS_RATIO1_INDEX));
            	om.setRatio2(c.getDouble(READ_PRODUCTS_RATIO2_INDEX));
            	om.setRatio3(c.getDouble(READ_PRODUCTS_RATIO3_INDEX));
            	om.setClassify(c.getString(READ_PRODUCTS_CLASSIFY_INDEX));
            	om.setSuplNO(c.getString(READ_PRODUCTS_SUPLNO_INDEX));
            	om.setBarCode(c.getString(READ_PRODUCTS_BARCODE_INDEX));
            	om.setSalePrice(c.getDouble(READ_PRODUCTS_SALEPRICE_INDEX));
            	om.setSuggestPrice(c.getDouble(READ_PRODUCTS_SUGGESTPRICE_INDEX));
            	om.setKind(c.getInt(READ_PRODUCTS_KIND_INDEX));
            	om.setNewProduct(c.getString(READ_PRODUCTS_NEWPRODUCT_INDEX));
            	om.setNoReturn(c.getString(READ_PRODUCTS_NORETURN_INDEX));
            	om.setSellDays(c.getDouble(READ_PRODUCTS_SELLDAYS_INDEX));
            	om.setRealStock(c.getString(READ_PRODUCTS_REALSTOCK_INDEX));
            	om.setRemark(c.getString(READ_PRODUCTS_REMARK_INDEX));
            	om.setVersionNo(c.getString(READ_PRODUCTS_VERSIONNO_INDEX));
                om.setSubClassify(c.getString(READ_PRODUCTS_SUBCLASSIFY_INDEX));
            	om.setSellMultiple(c.getDouble(READ_PRODUCTS_SELLMULTIPLE_INDEX));
            	om.setStockQty(c.getDouble(READ_PRODUCTS_STOCKQTY_INDEX));
            	om.setCubicMeasure(c.getDouble(READ_PRODUCTS_CUBICMEASURE_INDEX));
            	om.setPrdtUnitWeight(c.getDouble(READ_PRODUCTS_PRDTUNITWEIGHT_INDEX));
            	om.setWeightUnit(c.getString(READ_PRODUCTS_WEIGHTUNIT_INDEX));
                om.setRid(0);              	
            	set.add(om);
            } while(c.moveToNext());
        }
        c.close();
		sql = "select * from "+getTableName()+" where "+getTableName()+".CompanyID=? and "+getTableName()+
				".ItemID in (select Promotion_"+adapter.getCompanyID()+".ItemID from Promotion_"+adapter.getCompanyID()+
				" where Promotion_"+adapter.getCompanyID()+".CompanyID=? and Promotion_"+adapter.getCompanyID()+
				".CustID=?)";
		Log.d(TAG,sql);
		String[] selectionArgs20 = {String.valueOf(getCompanyID()),String.valueOf(getCompanyID()),String.valueOf(promoteGroupID)};
		c = db.rawQuery(sql, selectionArgs20);
        if (c != null && c.getCount()>0) {
            Log.d(TAG,"found in rule 2.0");
        	c.moveToFirst();
            do {
            	Products om = new Products();
            	om.setSerialID(c.getInt(READ_PRODUCTS_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_PRODUCTS_COMPANYID_INDEX));
            	om.setItemID(c.getInt(READ_PRODUCTS_ITEMID_INDEX));
            	om.setItemNO(c.getString(READ_PRODUCTS_ITEMNO_INDEX));
            	om.setItemNM(c.getString(READ_PRODUCTS_ITEMNM_INDEX));
            	om.setDimension(c.getString(READ_PRODUCTS_DIMENSION_INDEX));
            	om.setUnit(c.getString(READ_PRODUCTS_UNIT_INDEX));
            	om.setUnit1(c.getString(READ_PRODUCTS_UNIT1_INDEX));
            	om.setUnit2(c.getString(READ_PRODUCTS_UNIT2_INDEX));
            	om.setUnit3(c.getString(READ_PRODUCTS_UNIT3_INDEX));
            	om.setUnit4(c.getString(READ_PRODUCTS_UNIT4_INDEX));
            	om.setUnit5(c.getString(READ_PRODUCTS_UNIT5_INDEX));
            	om.setPiece(c.getDouble(READ_PRODUCTS_PIECE_INDEX));
            	om.setRatio1(c.getDouble(READ_PRODUCTS_RATIO1_INDEX));
            	om.setRatio2(c.getDouble(READ_PRODUCTS_RATIO2_INDEX));
            	om.setRatio3(c.getDouble(READ_PRODUCTS_RATIO3_INDEX));
            	om.setClassify(c.getString(READ_PRODUCTS_CLASSIFY_INDEX));
            	om.setSuplNO(c.getString(READ_PRODUCTS_SUPLNO_INDEX));
            	om.setBarCode(c.getString(READ_PRODUCTS_BARCODE_INDEX));
            	om.setSalePrice(c.getDouble(READ_PRODUCTS_SALEPRICE_INDEX));
            	om.setSuggestPrice(c.getDouble(READ_PRODUCTS_SUGGESTPRICE_INDEX));
            	om.setKind(c.getInt(READ_PRODUCTS_KIND_INDEX));
            	om.setNewProduct(c.getString(READ_PRODUCTS_NEWPRODUCT_INDEX));
            	om.setNoReturn(c.getString(READ_PRODUCTS_NORETURN_INDEX));
            	om.setSellDays(c.getDouble(READ_PRODUCTS_SELLDAYS_INDEX));
            	om.setRealStock(c.getString(READ_PRODUCTS_REALSTOCK_INDEX));
            	om.setRemark(c.getString(READ_PRODUCTS_REMARK_INDEX));
            	om.setVersionNo(c.getString(READ_PRODUCTS_VERSIONNO_INDEX));
                om.setSubClassify(c.getString(READ_PRODUCTS_SUBCLASSIFY_INDEX));
            	om.setSellMultiple(c.getDouble(READ_PRODUCTS_SELLMULTIPLE_INDEX));
            	om.setStockQty(c.getDouble(READ_PRODUCTS_STOCKQTY_INDEX));
            	om.setCubicMeasure(c.getDouble(READ_PRODUCTS_CUBICMEASURE_INDEX));
            	om.setPrdtUnitWeight(c.getDouble(READ_PRODUCTS_PRDTUNITWEIGHT_INDEX));
            	om.setWeightUnit(c.getString(READ_PRODUCTS_WEIGHTUNIT_INDEX));
                om.setRid(0);              	
            	set.add(om);
            } while(c.moveToNext());
        }
        c.close();
        // rule 2
        if(groupPriceGrade!=null) {
	    	sql = "select * from "+getTableName()+" where "+getTableName()+".CompanyID=? and "+getTableName()+
					".ItemID in (select Promotion_"+adapter.getCompanyID()+".ItemID from Promotion_"+adapter.getCompanyID()+
					" where Promotion_"+adapter.getCompanyID()+".CompanyID=? and Promotion_"+adapter.getCompanyID()+
					".CustID=0 and Promotion_"+adapter.getCompanyID()+".PriceGrade=?)";
			String[] selectionArgs2 = {String.valueOf(getCompanyID()),String.valueOf(getCompanyID()),groupPriceGrade};
			c = db.rawQuery(sql, selectionArgs2);
	        if (c != null && c.getCount()>0) {
	            Log.d(TAG,"found in rule 2");
	            c.moveToFirst();
	            do {
	            	Products om = new Products();
	            	om.setSerialID(c.getInt(READ_PRODUCTS_SERIALID_INDEX));
	            	om.setCompanyID(c.getInt(READ_PRODUCTS_COMPANYID_INDEX));
	            	om.setItemID(c.getInt(READ_PRODUCTS_ITEMID_INDEX));
	            	om.setItemNO(c.getString(READ_PRODUCTS_ITEMNO_INDEX));
	            	om.setItemNM(c.getString(READ_PRODUCTS_ITEMNM_INDEX));
	            	om.setDimension(c.getString(READ_PRODUCTS_DIMENSION_INDEX));
	            	om.setUnit(c.getString(READ_PRODUCTS_UNIT_INDEX));
	            	om.setUnit1(c.getString(READ_PRODUCTS_UNIT1_INDEX));
	            	om.setUnit2(c.getString(READ_PRODUCTS_UNIT2_INDEX));
	            	om.setUnit3(c.getString(READ_PRODUCTS_UNIT3_INDEX));
	            	om.setUnit4(c.getString(READ_PRODUCTS_UNIT4_INDEX));
	            	om.setUnit5(c.getString(READ_PRODUCTS_UNIT5_INDEX));
	            	om.setPiece(c.getDouble(READ_PRODUCTS_PIECE_INDEX));
	            	om.setRatio1(c.getDouble(READ_PRODUCTS_RATIO1_INDEX));
	            	om.setRatio2(c.getDouble(READ_PRODUCTS_RATIO2_INDEX));
	            	om.setRatio3(c.getDouble(READ_PRODUCTS_RATIO3_INDEX));
	            	om.setClassify(c.getString(READ_PRODUCTS_CLASSIFY_INDEX));
	            	om.setSuplNO(c.getString(READ_PRODUCTS_SUPLNO_INDEX));
	            	om.setBarCode(c.getString(READ_PRODUCTS_BARCODE_INDEX));
	            	om.setSalePrice(c.getDouble(READ_PRODUCTS_SALEPRICE_INDEX));
	            	om.setSuggestPrice(c.getDouble(READ_PRODUCTS_SUGGESTPRICE_INDEX));
	            	om.setKind(c.getInt(READ_PRODUCTS_KIND_INDEX));
	            	om.setNewProduct(c.getString(READ_PRODUCTS_NEWPRODUCT_INDEX));
	            	om.setNoReturn(c.getString(READ_PRODUCTS_NORETURN_INDEX));
	            	om.setSellDays(c.getDouble(READ_PRODUCTS_SELLDAYS_INDEX));
	            	om.setRealStock(c.getString(READ_PRODUCTS_REALSTOCK_INDEX));
	            	om.setRemark(c.getString(READ_PRODUCTS_REMARK_INDEX));
	            	om.setVersionNo(c.getString(READ_PRODUCTS_VERSIONNO_INDEX));
	                om.setSubClassify(c.getString(READ_PRODUCTS_SUBCLASSIFY_INDEX));
	            	om.setSellMultiple(c.getDouble(READ_PRODUCTS_SELLMULTIPLE_INDEX));
	            	om.setStockQty(c.getDouble(READ_PRODUCTS_STOCKQTY_INDEX));
	            	om.setCubicMeasure(c.getDouble(READ_PRODUCTS_CUBICMEASURE_INDEX));
	            	om.setPrdtUnitWeight(c.getDouble(READ_PRODUCTS_PRDTUNITWEIGHT_INDEX));
	            	om.setWeightUnit(c.getString(READ_PRODUCTS_WEIGHTUNIT_INDEX));
	                om.setRid(0);              	
	            	set.add(om);
	            } while(c.moveToNext());
	        }
	        c.close();
        }
        // rule 2.1
        if(priceGrade!=null) {
	    	sql = "select * from "+getTableName()+" where "+getTableName()+".CompanyID=? and "+getTableName()+
					".ItemID in (select Promotion_"+adapter.getCompanyID()+".ItemID from Promotion_"+adapter.getCompanyID()+
					" where Promotion_"+adapter.getCompanyID()+".CompanyID=? and Promotion_"+adapter.getCompanyID()+
					".CustID=0 and Promotion_"+adapter.getCompanyID()+".PriceGrade=?)";
			String[] selectionArgs21 = {String.valueOf(getCompanyID()),String.valueOf(getCompanyID()),priceGrade};
			c = db.rawQuery(sql, selectionArgs21);
			Log.d(TAG,sql);
	        if (c != null && c.getCount()>0) {
	            Log.d(TAG,"found in rule 2.1");
	            c.moveToFirst();
	            do {
	            	Products om = new Products();
	            	om.setSerialID(c.getInt(READ_PRODUCTS_SERIALID_INDEX));
	            	om.setCompanyID(c.getInt(READ_PRODUCTS_COMPANYID_INDEX));
	            	om.setItemID(c.getInt(READ_PRODUCTS_ITEMID_INDEX));
	            	om.setItemNO(c.getString(READ_PRODUCTS_ITEMNO_INDEX));
	            	om.setItemNM(c.getString(READ_PRODUCTS_ITEMNM_INDEX));
	            	om.setDimension(c.getString(READ_PRODUCTS_DIMENSION_INDEX));
	            	om.setUnit(c.getString(READ_PRODUCTS_UNIT_INDEX));
	            	om.setUnit1(c.getString(READ_PRODUCTS_UNIT1_INDEX));
	            	om.setUnit2(c.getString(READ_PRODUCTS_UNIT2_INDEX));
	            	om.setUnit3(c.getString(READ_PRODUCTS_UNIT3_INDEX));
	            	om.setUnit4(c.getString(READ_PRODUCTS_UNIT4_INDEX));
	            	om.setUnit5(c.getString(READ_PRODUCTS_UNIT5_INDEX));
	            	om.setPiece(c.getDouble(READ_PRODUCTS_PIECE_INDEX));
	            	om.setRatio1(c.getDouble(READ_PRODUCTS_RATIO1_INDEX));
	            	om.setRatio2(c.getDouble(READ_PRODUCTS_RATIO2_INDEX));
	            	om.setRatio3(c.getDouble(READ_PRODUCTS_RATIO3_INDEX));
	            	om.setClassify(c.getString(READ_PRODUCTS_CLASSIFY_INDEX));
	            	om.setSuplNO(c.getString(READ_PRODUCTS_SUPLNO_INDEX));
	            	om.setBarCode(c.getString(READ_PRODUCTS_BARCODE_INDEX));
	            	om.setSalePrice(c.getDouble(READ_PRODUCTS_SALEPRICE_INDEX));
	            	om.setSuggestPrice(c.getDouble(READ_PRODUCTS_SUGGESTPRICE_INDEX));
	            	om.setKind(c.getInt(READ_PRODUCTS_KIND_INDEX));
	            	om.setNewProduct(c.getString(READ_PRODUCTS_NEWPRODUCT_INDEX));
	            	om.setNoReturn(c.getString(READ_PRODUCTS_NORETURN_INDEX));
	            	om.setSellDays(c.getDouble(READ_PRODUCTS_SELLDAYS_INDEX));
	            	om.setRealStock(c.getString(READ_PRODUCTS_REALSTOCK_INDEX));
	            	om.setRemark(c.getString(READ_PRODUCTS_REMARK_INDEX));
	            	om.setVersionNo(c.getString(READ_PRODUCTS_VERSIONNO_INDEX));
	                om.setSubClassify(c.getString(READ_PRODUCTS_SUBCLASSIFY_INDEX));
	            	om.setSellMultiple(c.getDouble(READ_PRODUCTS_SELLMULTIPLE_INDEX));
	            	om.setStockQty(c.getDouble(READ_PRODUCTS_STOCKQTY_INDEX));
	            	om.setCubicMeasure(c.getDouble(READ_PRODUCTS_CUBICMEASURE_INDEX));
	            	om.setPrdtUnitWeight(c.getDouble(READ_PRODUCTS_PRDTUNITWEIGHT_INDEX));
	            	om.setWeightUnit(c.getString(READ_PRODUCTS_WEIGHTUNIT_INDEX));
	                om.setRid(0);              	
	            	set.add(om);
	            } while(c.moveToNext());
	        }
	        c.close();
        }
        // rule 3
		sql = "select * from "+getTableName()+" where "+getTableName()+".CompanyID=? and "+getTableName()+
				".ItemID in (select Promotion_"+adapter.getCompanyID()+".ItemID from Promotion_"+adapter.getCompanyID()+
				" where Promotion_"+adapter.getCompanyID()+".CompanyID=? and Promotion_"+adapter.getCompanyID()+
				".CustID=0 and Promotion_"+adapter.getCompanyID()+".PriceGrade is null)";
		String[] selectionArgs3 = {String.valueOf(getCompanyID()),String.valueOf(getCompanyID())};
		c = db.rawQuery(sql, selectionArgs3);        	
		Log.d(TAG,sql);
        if (c != null && c.getCount()>0) {
            Log.d(TAG,"found in rule 3");
            c.moveToFirst();
            do {
            	Products om = new Products();
            	om.setSerialID(c.getInt(READ_PRODUCTS_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_PRODUCTS_COMPANYID_INDEX));
            	om.setItemID(c.getInt(READ_PRODUCTS_ITEMID_INDEX));
            	om.setItemNO(c.getString(READ_PRODUCTS_ITEMNO_INDEX));
            	om.setItemNM(c.getString(READ_PRODUCTS_ITEMNM_INDEX));
            	om.setDimension(c.getString(READ_PRODUCTS_DIMENSION_INDEX));
            	om.setUnit(c.getString(READ_PRODUCTS_UNIT_INDEX));
            	om.setUnit1(c.getString(READ_PRODUCTS_UNIT1_INDEX));
            	om.setUnit2(c.getString(READ_PRODUCTS_UNIT2_INDEX));
            	om.setUnit3(c.getString(READ_PRODUCTS_UNIT3_INDEX));
            	om.setUnit4(c.getString(READ_PRODUCTS_UNIT4_INDEX));
            	om.setUnit5(c.getString(READ_PRODUCTS_UNIT5_INDEX));
            	om.setPiece(c.getDouble(READ_PRODUCTS_PIECE_INDEX));
            	om.setRatio1(c.getDouble(READ_PRODUCTS_RATIO1_INDEX));
            	om.setRatio2(c.getDouble(READ_PRODUCTS_RATIO2_INDEX));
            	om.setRatio3(c.getDouble(READ_PRODUCTS_RATIO3_INDEX));
            	om.setClassify(c.getString(READ_PRODUCTS_CLASSIFY_INDEX));
            	om.setSuplNO(c.getString(READ_PRODUCTS_SUPLNO_INDEX));
            	om.setBarCode(c.getString(READ_PRODUCTS_BARCODE_INDEX));
            	om.setSalePrice(c.getDouble(READ_PRODUCTS_SALEPRICE_INDEX));
            	om.setSuggestPrice(c.getDouble(READ_PRODUCTS_SUGGESTPRICE_INDEX));
            	om.setKind(c.getInt(READ_PRODUCTS_KIND_INDEX));
            	om.setNewProduct(c.getString(READ_PRODUCTS_NEWPRODUCT_INDEX));
            	om.setNoReturn(c.getString(READ_PRODUCTS_NORETURN_INDEX));
            	om.setSellDays(c.getDouble(READ_PRODUCTS_SELLDAYS_INDEX));
            	om.setRealStock(c.getString(READ_PRODUCTS_REALSTOCK_INDEX));
            	om.setRemark(c.getString(READ_PRODUCTS_REMARK_INDEX));
            	om.setVersionNo(c.getString(READ_PRODUCTS_VERSIONNO_INDEX));
                om.setSubClassify(c.getString(READ_PRODUCTS_SUBCLASSIFY_INDEX));
            	om.setSellMultiple(c.getDouble(READ_PRODUCTS_SELLMULTIPLE_INDEX));
            	om.setStockQty(c.getDouble(READ_PRODUCTS_STOCKQTY_INDEX));
            	om.setCubicMeasure(c.getDouble(READ_PRODUCTS_CUBICMEASURE_INDEX));
            	om.setPrdtUnitWeight(c.getDouble(READ_PRODUCTS_PRDTUNITWEIGHT_INDEX));
            	om.setWeightUnit(c.getString(READ_PRODUCTS_WEIGHTUNIT_INDEX));
                om.setRid(0);              	
            	set.add(om);
            } while(c.moveToNext());
        }
        c.close();
        closedb(adapter);
        for(Iterator<Products> ir=set.iterator();ir.hasNext();) {
    	  result.add(ir.next());
      	}
		return result;
	}

	public List<Products> getProductsByPromotionFTN(LikDBAdapter adapter) {
		List<Products> result = new ArrayList<Products>();
		TreeSet<Products> set = new TreeSet<Products>(this);
		SQLiteDatabase db = getdb(adapter);
		String sql = 
			"select products_13.* "+
			"from promotion_13,products_13,customers_13 "+
			"where  promotion_13.itemid=products_13.itemid "+
			"and customers_13.customerID=promotion_13.custid "+
			"union "+
			"select products_13.* "+
			"from promotion_13,products_13,customers_13 "+
			"where  promotion_13.itemid=products_13.itemid "+
			"and customers_13.area=promotion_13.area "+
			"union "+
			"select products_13.* "+
			"from promotion_13,products_13,customers_13 "+
			"where  promotion_13.classify=products_13.classify "+
			"and customers_13.area=promotion_13.area "+
			"union "+
			"select products_13.* "+
			"from promotion_13,products_13,customers_13 "+
			"where  promotion_13.classify=products_13.classify "+
			"and customers_13.customerID=promotion_13.custid";
		sql = sql.replaceAll("products_13", new Products().getTableName());
		sql = sql.replaceAll("promotion_13", new Promotion().getTableName());
		sql = sql.replaceAll("customers_13", new Customers().getTableName());
		Log.d(TAG,sql);
		String[] selectionArgs = {String.valueOf(getCompanyID()),String.valueOf(getCompanyID()),String.valueOf(promoteGroupID)};
		Cursor c = db.rawQuery(sql, selectionArgs);
        if (c != null && c.getCount()>0) {
            Log.d(TAG,"found in rule 1");
        	c.moveToFirst();
            do {
            	Products om = new Products();
            	om.setSerialID(c.getInt(READ_PRODUCTS_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_PRODUCTS_COMPANYID_INDEX));
            	om.setItemID(c.getInt(READ_PRODUCTS_ITEMID_INDEX));
            	om.setItemNO(c.getString(READ_PRODUCTS_ITEMNO_INDEX));
            	om.setItemNM(c.getString(READ_PRODUCTS_ITEMNM_INDEX));
            	om.setDimension(c.getString(READ_PRODUCTS_DIMENSION_INDEX));
            	om.setUnit(c.getString(READ_PRODUCTS_UNIT_INDEX));
            	om.setUnit1(c.getString(READ_PRODUCTS_UNIT1_INDEX));
            	om.setUnit2(c.getString(READ_PRODUCTS_UNIT2_INDEX));
            	om.setUnit3(c.getString(READ_PRODUCTS_UNIT3_INDEX));
            	om.setUnit4(c.getString(READ_PRODUCTS_UNIT4_INDEX));
            	om.setUnit5(c.getString(READ_PRODUCTS_UNIT5_INDEX));
            	om.setPiece(c.getDouble(READ_PRODUCTS_PIECE_INDEX));
            	om.setRatio1(c.getDouble(READ_PRODUCTS_RATIO1_INDEX));
            	om.setRatio2(c.getDouble(READ_PRODUCTS_RATIO2_INDEX));
            	om.setRatio3(c.getDouble(READ_PRODUCTS_RATIO3_INDEX));
            	om.setClassify(c.getString(READ_PRODUCTS_CLASSIFY_INDEX));
            	om.setSuplNO(c.getString(READ_PRODUCTS_SUPLNO_INDEX));
            	om.setBarCode(c.getString(READ_PRODUCTS_BARCODE_INDEX));
            	om.setSalePrice(c.getDouble(READ_PRODUCTS_SALEPRICE_INDEX));
            	om.setSuggestPrice(c.getDouble(READ_PRODUCTS_SUGGESTPRICE_INDEX));
            	om.setKind(c.getInt(READ_PRODUCTS_KIND_INDEX));
            	om.setNewProduct(c.getString(READ_PRODUCTS_NEWPRODUCT_INDEX));
            	om.setNoReturn(c.getString(READ_PRODUCTS_NORETURN_INDEX));
            	om.setSellDays(c.getDouble(READ_PRODUCTS_SELLDAYS_INDEX));
            	om.setRealStock(c.getString(READ_PRODUCTS_REALSTOCK_INDEX));
            	om.setRemark(c.getString(READ_PRODUCTS_REMARK_INDEX));
            	om.setVersionNo(c.getString(READ_PRODUCTS_VERSIONNO_INDEX));
                om.setSubClassify(c.getString(READ_PRODUCTS_SUBCLASSIFY_INDEX));
            	om.setSellMultiple(c.getDouble(READ_PRODUCTS_SELLMULTIPLE_INDEX));
            	om.setStockQty(c.getDouble(READ_PRODUCTS_STOCKQTY_INDEX));
            	om.setCubicMeasure(c.getDouble(READ_PRODUCTS_CUBICMEASURE_INDEX));
            	om.setPrdtUnitWeight(c.getDouble(READ_PRODUCTS_PRDTUNITWEIGHT_INDEX));
            	om.setWeightUnit(c.getString(READ_PRODUCTS_WEIGHTUNIT_INDEX));
                om.setRid(0);              	
            	set.add(om);
            } while(c.moveToNext());
        }
        c.close();
        closedb(adapter);
        for(Iterator<Products> ir=set.iterator();ir.hasNext();) {
    	  result.add(ir.next());
      	}
		return result;
	}
	
	public List<Products> getProductsByNew(LikDBAdapter adapter) {
		List<Products> result = new ArrayList<Products>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_NEWPRODUCT+"='"+getNewProduct()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PRODUCTS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				getOrder()==ORDERBY_ITEMNO?(COLUMN_NAME_ITEMNO+" asc"):(COLUMN_NAME_SUBCLASSIFY+","+COLUMN_NAME_ITEMNO+" asc")        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	Products om = new Products();
            	om.setSerialID(c.getInt(READ_PRODUCTS_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_PRODUCTS_COMPANYID_INDEX));
            	om.setItemID(c.getInt(READ_PRODUCTS_ITEMID_INDEX));
            	om.setItemNO(c.getString(READ_PRODUCTS_ITEMNO_INDEX));
            	om.setItemNM(c.getString(READ_PRODUCTS_ITEMNM_INDEX));
            	om.setDimension(c.getString(READ_PRODUCTS_DIMENSION_INDEX));
            	om.setUnit(c.getString(READ_PRODUCTS_UNIT_INDEX));
            	om.setUnit1(c.getString(READ_PRODUCTS_UNIT1_INDEX));
            	om.setUnit2(c.getString(READ_PRODUCTS_UNIT2_INDEX));
            	om.setUnit3(c.getString(READ_PRODUCTS_UNIT3_INDEX));
            	om.setUnit4(c.getString(READ_PRODUCTS_UNIT4_INDEX));
            	om.setUnit5(c.getString(READ_PRODUCTS_UNIT5_INDEX));
            	om.setPiece(c.getDouble(READ_PRODUCTS_PIECE_INDEX));
            	om.setRatio1(c.getDouble(READ_PRODUCTS_RATIO1_INDEX));
            	om.setRatio2(c.getDouble(READ_PRODUCTS_RATIO2_INDEX));
            	om.setRatio3(c.getDouble(READ_PRODUCTS_RATIO3_INDEX));
            	om.setClassify(c.getString(READ_PRODUCTS_CLASSIFY_INDEX));
            	om.setSuplNO(c.getString(READ_PRODUCTS_SUPLNO_INDEX));
            	om.setBarCode(c.getString(READ_PRODUCTS_BARCODE_INDEX));
            	om.setSalePrice(c.getDouble(READ_PRODUCTS_SALEPRICE_INDEX));
            	om.setSuggestPrice(c.getDouble(READ_PRODUCTS_SUGGESTPRICE_INDEX));
            	om.setKind(c.getInt(READ_PRODUCTS_KIND_INDEX));
            	om.setNewProduct(c.getString(READ_PRODUCTS_NEWPRODUCT_INDEX));
            	om.setNoReturn(c.getString(READ_PRODUCTS_NORETURN_INDEX));
            	om.setSellDays(c.getDouble(READ_PRODUCTS_SELLDAYS_INDEX));
            	om.setRealStock(c.getString(READ_PRODUCTS_REALSTOCK_INDEX));
            	om.setRemark(c.getString(READ_PRODUCTS_REMARK_INDEX));
            	om.setVersionNo(c.getString(READ_PRODUCTS_VERSIONNO_INDEX));
                om.setSubClassify(c.getString(READ_PRODUCTS_SUBCLASSIFY_INDEX));
            	om.setSellMultiple(c.getDouble(READ_PRODUCTS_SELLMULTIPLE_INDEX));
            	om.setStockQty(c.getDouble(READ_PRODUCTS_STOCKQTY_INDEX));
            	om.setCubicMeasure(c.getDouble(READ_PRODUCTS_CUBICMEASURE_INDEX));
            	om.setPrdtUnitWeight(c.getDouble(READ_PRODUCTS_PRDTUNITWEIGHT_INDEX));
            	om.setWeightUnit(c.getString(READ_PRODUCTS_WEIGHTUNIT_INDEX));
                om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
        }
//        db.close();		
        c.close();
        closedb(adapter);
		return result;
	}

//	public List<Products> getProductsByKeyWord(LikDBAdapter adapter) {
//		List<Products> result = new ArrayList<Products>();
//		SQLiteDatabase db = getdb(adapter);
//		// Constructs a new query builder and sets its table name
//		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//		qb.setTables(getTableName());
//		qb.setProjectionMap(projectionMap);
//		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
//		if(getItemNO() != null) qb.appendWhere(" and "+COLUMN_NAME_ITEMNO+" like '"+getItemNO()+"%'");
//		if(getItemNM() != null) qb.appendWhere(" and "+COLUMN_NAME_ITEMNM+" like '%"+getItemNM()+"%'");
//		if(getBarCode() != null) qb.appendWhere(" and "+COLUMN_NAME_BARCODE+" like '%"+getBarCode()+"'");
//		/*
//		 * Performs the query. If no problems occur trying to read the database, then a Cursor
//		 * object is returned; otherwise, the cursor variable contains null. If no records were
//		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
//		 */
//		Cursor c = qb.query(
//				db,            // The database to query
//				READ_PRODUCTS_PROJECTION,    // The columns to return from the query
//				null,     // The columns for the where clause
//				null, // The values for the where clause
//				null,          // don't group the rows
//				null,          // don't filter by row groups
//				COLUMN_NAME_ITEMNO+" asc"        // The sort order
//				);
//        if (c != null && c.getCount()>0) {
//            c.moveToFirst();
//            do {
//            	Products om = new Products();
//            	om.setSerialID(c.getInt(READ_PRODUCTS_SERIALID_INDEX));
//            	om.setCompanyID(c.getInt(READ_PRODUCTS_COMPANYID_INDEX));
//            	om.setItemID(c.getInt(READ_PRODUCTS_ITEMID_INDEX));
//            	om.setItemNO(c.getString(READ_PRODUCTS_ITEMNO_INDEX));
//            	om.setItemNM(c.getString(READ_PRODUCTS_ITEMNM_INDEX));
//            	om.setDimension(c.getString(READ_PRODUCTS_DIMENSION_INDEX));
//            	om.setUnit(c.getString(READ_PRODUCTS_UNIT_INDEX));
//            	om.setUnit1(c.getString(READ_PRODUCTS_UNIT1_INDEX));
//            	om.setUnit2(c.getString(READ_PRODUCTS_UNIT2_INDEX));
//            	om.setUnit3(c.getString(READ_PRODUCTS_UNIT3_INDEX));
//            	om.setUnit4(c.getString(READ_PRODUCTS_UNIT4_INDEX));
//            	om.setUnit5(c.getString(READ_PRODUCTS_UNIT5_INDEX));
//            	om.setPiece(c.getDouble(READ_PRODUCTS_PIECE_INDEX));
//            	om.setRatio1(c.getDouble(READ_PRODUCTS_RATIO1_INDEX));
//            	om.setRatio2(c.getDouble(READ_PRODUCTS_RATIO2_INDEX));
//            	om.setRatio3(c.getDouble(READ_PRODUCTS_RATIO3_INDEX));
//            	om.setClassify(c.getString(READ_PRODUCTS_CLASSIFY_INDEX));
//            	om.setSuplNO(c.getString(READ_PRODUCTS_SUPLNO_INDEX));
//            	om.setBarCode(c.getString(READ_PRODUCTS_BARCODE_INDEX));
//            	om.setSalePrice(c.getDouble(READ_PRODUCTS_SALEPRICE_INDEX));
//            	om.setSuggestPrice(c.getDouble(READ_PRODUCTS_SUGGESTPRICE_INDEX));
//            	om.setKind(c.getInt(READ_PRODUCTS_KIND_INDEX));
//            	om.setNewProduct(c.getString(READ_PRODUCTS_NEWPRODUCT_INDEX));
//            	om.setNoReturn(c.getString(READ_PRODUCTS_NORETURN_INDEX));
//            	om.setSellDays(c.getDouble(READ_PRODUCTS_SELLDAYS_INDEX));
//            	om.setRealStock(c.getString(READ_PRODUCTS_REALSTOCK_INDEX));
//            	om.setVersionNo(c.getString(READ_PRODUCTS_VERSIONNO_INDEX));
//                om.setRid(0);
//            	result.add(om);
//            } while(c.moveToNext());
//        }
////        db.close();		
//        c.close();
//        closedb(adapter);
//		return result;
//	}

	public List<Products> getProductsByKeyWord(LikDBAdapter adapter) {
		List<Products> result = new ArrayList<Products>();
		boolean isSHD = false;
		if(adapter.getCtx() instanceof MainMenuActivity) {
			MainMenuActivity mmf = (MainMenuActivity)adapter.getCtx();
	        if(mmf.omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_SHD)) {
	        	isSHD = true;
	        }
		}
		Log.d(TAG,"isSHD="+isSHD);
		SQLiteDatabase db = getdb(adapter);
		TreeSet<Products> set = new TreeSet<Products>(new Comparator<Products>() {

			@Override
			public int compare(Products lhs, Products rhs) {
				if(getOrder()==ORDERBY_SUBCLASSIFY_ITEMNO) {
					StringBuffer sbl = new StringBuffer();
					StringBuffer sbr = new StringBuffer();
					String lhsid =  lhs.getSubClassify()==null?"":lhs.getSubClassify();
					String rhsid =  rhs.getSubClassify()==null?"":rhs.getSubClassify();
					int diff = lhsid.length()-rhsid.length();
					if(diff>0) {
						for(int i=0;i<diff;i++) {
							rhsid = rhsid+"0";
						}
					} else if(diff<0) {
						for(int i=0;i<-diff;i++) {
							lhsid = lhsid+"0";
						}					
					}
					sbl.append(lhsid).append(lhs.getItemNO());
					sbr.append(rhsid).append(rhs.getItemNO());
					return sbl.toString().compareTo(sbr.toString());
				} else return Long.valueOf(lhs.getSerialID()).compareTo(Long.valueOf(rhs.getSerialID()));
			}
			
		});
		String sql = null;
		if(getBarCode()==null) {
			sql = "select * from "+getTableName()+" where Products_"+adapter.getCompanyID()+".CompanyID=?"+
					(getItemNO()==null?"":" and "+COLUMN_NAME_ITEMNO+" like '"+(isSHD?("%"+getItemNO()+"%'"):(getItemNO()+"%'")))+
					(getItemNM()==null?"":" and "+COLUMN_NAME_ITEMNM+" like '%"+getItemNM()+"%'")+
					(getClassify()==null?"":" and "+COLUMN_NAME_CLASSIFY+"='"+getClassify()+"'")+
					" order by "+getTableName()+".ItemNO";					
		} else {
			sql = "select * from "+getTableName()+",PrdtUnits_"+adapter.getCompanyID()+
				" where Products_"+adapter.getCompanyID()+".CompanyID=PrdtUnits_"+adapter.getCompanyID()+".CompanyID"+
				" and Products_"+adapter.getCompanyID()+".ItemID=PrdtUnits_"+adapter.getCompanyID()+".ItemID"+
				" and PrdtUnits_"+adapter.getCompanyID()+".BarCode like '%"+getBarCode()+"'"+
				(getClassify()==null?"":" and "+COLUMN_NAME_CLASSIFY+"='"+getClassify()+"'")+
				" and Products_"+adapter.getCompanyID()+".CompanyID=? order by "+getTableName()+".ItemNO";
		}
		Log.d(TAG,sql);
		String[] selectionArgs = {String.valueOf(getCompanyID())};
		Cursor c = db.rawQuery(sql, selectionArgs);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	Products om = new Products();
            	om.setSerialID(c.getInt(READ_PRODUCTS_SERIALID_INDEX));
            	om.setCompanyID(c.getInt(READ_PRODUCTS_COMPANYID_INDEX));
            	om.setItemID(c.getInt(READ_PRODUCTS_ITEMID_INDEX));
            	om.setItemNO(c.getString(READ_PRODUCTS_ITEMNO_INDEX));
            	om.setItemNM(c.getString(READ_PRODUCTS_ITEMNM_INDEX));
            	om.setDimension(c.getString(READ_PRODUCTS_DIMENSION_INDEX));
            	om.setUnit(c.getString(READ_PRODUCTS_UNIT_INDEX));
            	om.setUnit1(c.getString(READ_PRODUCTS_UNIT1_INDEX));
            	om.setUnit2(c.getString(READ_PRODUCTS_UNIT2_INDEX));
            	om.setUnit3(c.getString(READ_PRODUCTS_UNIT3_INDEX));
            	om.setUnit4(c.getString(READ_PRODUCTS_UNIT4_INDEX));
            	om.setUnit5(c.getString(READ_PRODUCTS_UNIT5_INDEX));
            	om.setPiece(c.getDouble(READ_PRODUCTS_PIECE_INDEX));
            	om.setRatio1(c.getDouble(READ_PRODUCTS_RATIO1_INDEX));
            	om.setRatio2(c.getDouble(READ_PRODUCTS_RATIO2_INDEX));
            	om.setRatio3(c.getDouble(READ_PRODUCTS_RATIO3_INDEX));
            	om.setClassify(c.getString(READ_PRODUCTS_CLASSIFY_INDEX));
            	om.setSuplNO(c.getString(READ_PRODUCTS_SUPLNO_INDEX));
            	om.setBarCode(c.getString(READ_PRODUCTS_BARCODE_INDEX));
            	om.setSalePrice(c.getDouble(READ_PRODUCTS_SALEPRICE_INDEX));
            	om.setSuggestPrice(c.getDouble(READ_PRODUCTS_SUGGESTPRICE_INDEX));
            	om.setKind(c.getInt(READ_PRODUCTS_KIND_INDEX));
            	om.setNewProduct(c.getString(READ_PRODUCTS_NEWPRODUCT_INDEX));
            	om.setNoReturn(c.getString(READ_PRODUCTS_NORETURN_INDEX));
            	om.setSellDays(c.getDouble(READ_PRODUCTS_SELLDAYS_INDEX));
            	om.setRealStock(c.getString(READ_PRODUCTS_REALSTOCK_INDEX));
            	om.setRemark(c.getString(READ_PRODUCTS_REMARK_INDEX));
            	om.setVersionNo(c.getString(READ_PRODUCTS_VERSIONNO_INDEX));
                om.setSubClassify(c.getString(READ_PRODUCTS_SUBCLASSIFY_INDEX));
            	om.setSellMultiple(c.getDouble(READ_PRODUCTS_SELLMULTIPLE_INDEX));
            	om.setStockQty(c.getDouble(READ_PRODUCTS_STOCKQTY_INDEX));
            	om.setCubicMeasure(c.getDouble(READ_PRODUCTS_CUBICMEASURE_INDEX));
            	om.setPrdtUnitWeight(c.getDouble(READ_PRODUCTS_PRDTUNITWEIGHT_INDEX));
            	om.setWeightUnit(c.getString(READ_PRODUCTS_WEIGHTUNIT_INDEX));
            	om.setRid(0);
            	set.add(om);
            } while(c.moveToNext());
        }
        c.close();
        closedb(adapter);
        for(Iterator<Products> ir=set.iterator();ir.hasNext();) {
    	  result.add(ir.next());
      	}
		return result;
	}
	
	public Products getProductsByBarCode(LikDBAdapter adapter) {
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
				READ_PRODUCTS_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_PRODUCTS_SERIALID_INDEX));
        	setCompanyID(c.getInt(READ_PRODUCTS_COMPANYID_INDEX));
        	setItemID(c.getInt(READ_PRODUCTS_ITEMID_INDEX));
        	setItemNO(c.getString(READ_PRODUCTS_ITEMNO_INDEX));
        	setItemNM(c.getString(READ_PRODUCTS_ITEMNM_INDEX));
        	setDimension(c.getString(READ_PRODUCTS_DIMENSION_INDEX));
        	setUnit(c.getString(READ_PRODUCTS_UNIT_INDEX));
        	setUnit1(c.getString(READ_PRODUCTS_UNIT1_INDEX));
        	setUnit2(c.getString(READ_PRODUCTS_UNIT2_INDEX));
        	setUnit3(c.getString(READ_PRODUCTS_UNIT3_INDEX));
        	setUnit4(c.getString(READ_PRODUCTS_UNIT4_INDEX));
        	setUnit5(c.getString(READ_PRODUCTS_UNIT5_INDEX));
        	setPiece(c.getDouble(READ_PRODUCTS_PIECE_INDEX));
        	setRatio1(c.getDouble(READ_PRODUCTS_RATIO1_INDEX));
        	setRatio2(c.getDouble(READ_PRODUCTS_RATIO2_INDEX));
        	setRatio3(c.getDouble(READ_PRODUCTS_RATIO3_INDEX));
        	setClassify(c.getString(READ_PRODUCTS_CLASSIFY_INDEX));
        	setSuplNO(c.getString(READ_PRODUCTS_SUPLNO_INDEX));
        	setBarCode(c.getString(READ_PRODUCTS_BARCODE_INDEX));
        	setSalePrice(c.getDouble(READ_PRODUCTS_SALEPRICE_INDEX));
        	setSuggestPrice(c.getDouble(READ_PRODUCTS_SUGGESTPRICE_INDEX));
        	setKind(c.getInt(READ_PRODUCTS_KIND_INDEX));
        	setNewProduct(c.getString(READ_PRODUCTS_NEWPRODUCT_INDEX));
        	setNoReturn(c.getString(READ_PRODUCTS_NORETURN_INDEX));
        	setSellDays(c.getDouble(READ_PRODUCTS_SELLDAYS_INDEX));
        	setRealStock(c.getString(READ_PRODUCTS_REALSTOCK_INDEX));
        	setRemark(c.getString(READ_PRODUCTS_REMARK_INDEX));
            setVersionNo(c.getString(READ_PRODUCTS_VERSIONNO_INDEX));
            setSubClassify(c.getString(READ_PRODUCTS_SUBCLASSIFY_INDEX));
        	setSellMultiple(c.getDouble(READ_PRODUCTS_SELLMULTIPLE_INDEX));
        	setStockQty(c.getDouble(READ_PRODUCTS_STOCKQTY_INDEX));
        	setCubicMeasure(c.getDouble(READ_PRODUCTS_CUBICMEASURE_INDEX));
        	setPrdtUnitWeight(c.getDouble(READ_PRODUCTS_PRDTUNITWEIGHT_INDEX));
        	setWeightUnit(c.getString(READ_PRODUCTS_WEIGHTUNIT_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

	private String userNo;
	
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public List<SubAddProductsView> getLimitProdList(LikDBAdapter adapter) {
		ArrayList<SubAddProductsView> result = new ArrayList<SubAddProductsView>();
		SQLiteDatabase db = getdb(adapter);
		Limit omL = new Limit();
		omL.getdb(adapter);
		String sql = "select * from "+getTableName()+
				" where ItemID in ("+
				"select ItemID from "+omL.getTableName()+" where "+
				omL.getTableName()+".CompanyID=? and "+
				omL.getTableName()+".CustomerID=x1 and "+omL.getTableName()+".UserNo='x2' and ("+
				omL.getTableName()+".StradeDate is NULL OR ("+omL.getTableName()+".StradeDate is not NULL and "+omL.getTableName()+".StradeDate>'x3'))"+
				") ORDER BY "+getTableName()+"."+COLUMN_NAME_ITEMNO;
		sql = sql.replaceFirst("x1", String.valueOf(getCustomerID()));
		sql = sql.replaceFirst("x2", getUserNo());
		sql = sql.replaceFirst("x3", sdf.format(new Date()));
		Log.d(TAG,"sql="+sql);
		String[] selectionArgs = {String.valueOf(getCompanyID())};
		Cursor c = db.rawQuery(sql, selectionArgs);
		if(c != null && c.moveToFirst()) {
            do {
            	SubAddProductsView view = new SubAddProductsView();
            	view.setSerialID(c.getInt(Products.READ_PRODUCTS_SERIALID_INDEX));
            	view.setCompanyID(c.getInt(Products.READ_PRODUCTS_COMPANYID_INDEX));
            	view.setItemID(c.getInt(Products.READ_PRODUCTS_ITEMID_INDEX));
            	view.setItemNO(c.getString(Products.READ_PRODUCTS_ITEMNO_INDEX));
            	view.setItemNM(c.getString(Products.READ_PRODUCTS_ITEMNM_INDEX));
            	view.setDimension(c.getString(Products.READ_PRODUCTS_DIMENSION_INDEX));
            	view.setUnit(c.getString(Products.READ_PRODUCTS_UNIT_INDEX));
            	view.setUnit1(c.getString(Products.READ_PRODUCTS_UNIT1_INDEX));
            	view.setUnit2(c.getString(Products.READ_PRODUCTS_UNIT2_INDEX));
            	view.setUnit3(c.getString(Products.READ_PRODUCTS_UNIT3_INDEX));
            	view.setUnit4(c.getString(Products.READ_PRODUCTS_UNIT4_INDEX));
            	view.setUnit5(c.getString(Products.READ_PRODUCTS_UNIT5_INDEX));
            	view.setPiece(c.getDouble(Products.READ_PRODUCTS_PIECE_INDEX));
            	view.setRatio1(c.getDouble(Products.READ_PRODUCTS_RATIO1_INDEX));
            	view.setRatio2(c.getDouble(Products.READ_PRODUCTS_RATIO2_INDEX));
            	view.setRatio3(c.getDouble(Products.READ_PRODUCTS_RATIO3_INDEX));
            	view.setClassify(c.getString(Products.READ_PRODUCTS_CLASSIFY_INDEX));
            	view.setSuplNO(c.getString(Products.READ_PRODUCTS_SUPLNO_INDEX));
            	view.setBarCode(c.getString(Products.READ_PRODUCTS_BARCODE_INDEX));
            	view.setSalePrice(c.getDouble(Products.READ_PRODUCTS_SALEPRICE_INDEX));
            	view.setSuggestPrice(c.getDouble(Products.READ_PRODUCTS_SUGGESTPRICE_INDEX));
            	view.setKind(c.getInt(Products.READ_PRODUCTS_KIND_INDEX));
            	view.setNewProduct(c.getString(Products.READ_PRODUCTS_NEWPRODUCT_INDEX));
            	view.setNoReturn(c.getString(Products.READ_PRODUCTS_NORETURN_INDEX));
            	view.setSellDays(c.getDouble(Products.READ_PRODUCTS_SELLDAYS_INDEX));
            	view.setRealStock(c.getString(Products.READ_PRODUCTS_REALSTOCK_INDEX));
            	view.setRemark(c.getString(Products.READ_PRODUCTS_REMARK_INDEX));
            	view.setVersionNo(c.getString(Products.READ_PRODUCTS_VERSIONNO_INDEX));
            	view.setSubClassify(c.getString(Products.READ_PRODUCTS_SUBCLASSIFY_INDEX));
            	view.setSellMultiple(c.getDouble(Products.READ_PRODUCTS_SELLMULTIPLE_INDEX));
            	view.setStockQty(c.getDouble(Products.READ_PRODUCTS_STOCKQTY_INDEX));
            	view.setCubicMeasure(c.getDouble(READ_PRODUCTS_CUBICMEASURE_INDEX));
            	view.setPrdtUnitWeight(c.getDouble(READ_PRODUCTS_PRDTUNITWEIGHT_INDEX));
            	view.setWeightUnit(c.getString(READ_PRODUCTS_WEIGHTUNIT_INDEX));
            	view.setPackAmount(getPackAmount(view));
            	result.add(view);
            } while(c.moveToNext());
		} 		
        closedb(adapter);		
		return result;
	}

	@Override
	public int compare(Products lhs, Products rhs) {
		if(getOrder()==ORDERBY_SUBCLASSIFY_ITEMNO) {
			StringBuffer sbl = new StringBuffer();
			StringBuffer sbr = new StringBuffer();
			String lhsid =  lhs.getSubClassify()==null?"":lhs.getSubClassify();
			String rhsid =  rhs.getSubClassify()==null?"":rhs.getSubClassify();
			int diff = lhsid.length()-rhsid.length();
			if(diff>0) {
				for(int i=0;i<diff;i++) {
					rhsid = rhsid+"0";
				}
			} else if(diff<0) {
				for(int i=0;i<-diff;i++) {
					lhsid = lhsid+"0";
				}					
			}
			sbl.append(lhsid).append(lhs.getItemNO());
			sbr.append(rhsid).append(rhs.getItemNO());
			return sbl.toString().compareTo(sbr.toString());
		} else return Long.valueOf(lhs.getSerialID()).compareTo(Long.valueOf(rhs.getSerialID()));
	}
	
	private String getPackAmount(SubAddProductsView omP) {
		NumberFormat nf = NumberFormat.getInstance();
		StringBuffer result = new StringBuffer();
		String unit = omP.getUnit5(); // �D���
		double qty = omP.getStockQty();
		double amount=0;
		if(unit.equals(omP.getUnit1())) {
			amount = omP.getRatio1()*qty;
		} else if(unit.equals(omP.getUnit2())) {
			amount = omP.getRatio2()*qty;
		} else if(unit.equals(omP.getUnit3())) {
			amount = omP.getRatio3()*qty;
		}
		int qty1=0,qty2=0;
		if(amount==0)  result.append(0+unit);
		else {
			qty1 = (int)(amount/omP.getRatio1());
			amount = amount%omP.getRatio1();
			if(omP.getUnit2()!=null) {
				qty2 = (int)(amount/omP.getRatio2());
				amount = amount%omP.getRatio2();
			}
			result.append(nf.format(qty1)).append(omP.getUnit1());
			if(omP.getUnit3()!=null) {
				result.append("/").append(nf.format(qty2)).append(omP.getUnit2());
				result.append("/").append(nf.format(amount)).append(omP.getUnit3());
			} else if(omP.getUnit2()!=null) {
				result.append("/").append(nf.format(qty2)).append(omP.getUnit2());
			}
		}
		return result.toString();
	}


}
