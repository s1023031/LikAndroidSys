package com.lik.android.om;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;

public class Promotion extends BasePromotion
	implements ProcessDownloadInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3863790984610101070L;

	@Override
	public Promotion doInsert(LikDBAdapter adapter) {
		getdb(adapter);
		  InsertHelper ih = adapter.getInsertHelper(getTableName());
		  ih.prepareForInsert();
        ih.bind(2, getCompanyID());
        ih.bind(3, getPromotionID());
        ih.bind(4, getItemID());
        ih.bind(5, getPriceGrade());
//        ih.bind(6, getClassify());
//        ih.bind(7, getArea());
        ih.bind(6, getCustID());
        if(getDateFrom() != null) ih.bind(7, sdf.format(getDateFrom()));
        if(getDateTo() != null) ih.bind(8, sdf.format(getDateTo()));
        ih.bind(9, getPromoteTerms());
        if(getStdPrice() != null) ih.bind(10, getStdPrice());
        ih.bind(11, getUnit());
        if(getLowestSPrice()!=null) ih.bind(12, getLowestSPrice());
        ih.bind(13, getVersionNo());
  	    long rid = ih.execute();
        if(rid != -1) setRid(0);
		return this;
	}

//	@Override
//	public Promotion doInsert(LikDBAdapter adapter) {
//		SQLiteDatabase db = getdb(adapter);
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(COLUMN_NAME_COMPANYID, getCompanyID());
//        initialValues.put(COLUMN_NAME_PROMOTIONID, getPromotionID());
//        initialValues.put(COLUMN_NAME_ITEMID, getItemID());
//        initialValues.put(COLUMN_NAME_PRICEGRADE, getPriceGrade());
//        initialValues.put(COLUMN_NAME_CUSTID, getCustID());
//        if(getDateFrom() != null) initialValues.put(COLUMN_NAME_DATEFROM, sdf.format(getDateFrom()));
//        if(getDateTo() != null) initialValues.put(COLUMN_NAME_DATETO, sdf.format(getDateTo()));
//        initialValues.put(COLUMN_NAME_PROMOTETERMS, getPromoteTerms());
//        initialValues.put(COLUMN_NAME_STDPRICE, getStdPrice());
//        initialValues.put(COLUMN_NAME_UNIT, getUnit());
//        initialValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
//        long rid = db.insert(TABLE_NAME, null, initialValues);
//        setRid(rid);
////        db.close();
//        closedb(adapter);
//		return this;
//	}

	@Override
	public Promotion doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();

        updateValues.put(COLUMN_NAME_ITEMID, getItemID());
        updateValues.put(COLUMN_NAME_PRICEGRADE, getPriceGrade());
//        updateValues.put(COLUMN_NAME_CLASSIFY, getClassify());
//        updateValues.put(COLUMN_NAME_AREA, getArea());
        updateValues.put(COLUMN_NAME_CUSTID, getCustID());
        if(getDateFrom() != null) updateValues.put(COLUMN_NAME_DATEFROM, sdf.format(getDateFrom()));
        if(getDateTo() != null) updateValues.put(COLUMN_NAME_DATETO, sdf.format(getDateTo()));
        updateValues.put(COLUMN_NAME_PROMOTETERMS, getPromoteTerms());
        updateValues.put(COLUMN_NAME_STDPRICE, getStdPrice());
        updateValues.put(COLUMN_NAME_UNIT, getUnit());
        updateValues.put(COLUMN_NAME_LOWESTSPRICE, getLowestSPrice());
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
	public Promotion doDelete(LikDBAdapter adapter) {
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
	public Promotion findByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"='"+getCompanyID()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_PROMOTIONID+"="+getPromotionID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PROMOTION_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_PROMOTION_SERIALID_INDEX));
        	setItemID(c.getInt(READ_PROMOTION_ITEMID_INDEX));
        	setPriceGrade(c.getString(READ_PROMOTION_PRICEGRADE_INDEX));
//        	setClassify(c.getString(READ_PROMOTION_CLASSIFY_INDEX));
//        	setArea(c.getString(READ_PROMOTION_AREA_INDEX));
        	setCustID(c.getInt(READ_PROMOTION_CUSTID_INDEX));
            try {
            	Date dateFrom = sdf.parse(c.getString(READ_PROMOTION_DATEFROM_INDEX));
            	setDateFrom(dateFrom);
            } catch(ParseException pe) {
            	setDateFrom(null);
            }
            try {
            	Date dateTo = sdf.parse(c.getString(READ_PROMOTION_DATETO_INDEX));
            	setDateTo(dateTo);
            } catch(ParseException pe) {
            	setDateTo(null);
            }
        	setPromoteTerms(c.getString(READ_PROMOTION_PROMOTETERMS_INDEX));
        	if(c.getString(READ_PROMOTION_STDPRICE_INDEX)!=null) setStdPrice(c.getDouble(READ_PROMOTION_STDPRICE_INDEX));
        	setUnit(c.getString(READ_PROMOTION_UNIT_INDEX));
        	if(c.getString(READ_PROMOTION_LOWESTSPRICE_INDEX)!=null) setLowestSPrice(c.getDouble(READ_PROMOTION_LOWESTSPRICE_INDEX));
            setVersionNo(c.getString(READ_PROMOTION_VERSIONNO_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public Promotion queryBySerialID(LikDBAdapter adapter) {
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
				READ_PROMOTION_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_PROMOTION_SERIALID_INDEX));
        	setCompanyID(c.getInt(READ_PROMOTION_COMPANYID_INDEX));
        	setPromotionID(c.getInt(READ_PROMOTION_PROMOTIONID_INDEX));
        	setItemID(c.getInt(READ_PROMOTION_ITEMID_INDEX));
        	setPriceGrade(c.getString(READ_PROMOTION_PRICEGRADE_INDEX));
//        	setClassify(c.getString(READ_PROMOTION_CLASSIFY_INDEX));
//        	setArea(c.getString(READ_PROMOTION_AREA_INDEX));
        	setCustID(c.getInt(READ_PROMOTION_CUSTID_INDEX));
            try {
            	Date dateFrom = sdf.parse(c.getString(READ_PROMOTION_DATEFROM_INDEX));
            	setDateFrom(dateFrom);
            } catch(ParseException pe) {
            	setDateFrom(null);
            }
            try {
            	Date dateTo = sdf.parse(c.getString(READ_PROMOTION_DATETO_INDEX));
            	setDateTo(dateTo);
            } catch(ParseException pe) {
            	setDateTo(null);
            }
        	setPromoteTerms(c.getString(READ_PROMOTION_PROMOTETERMS_INDEX));
        	if(c.getString(READ_PROMOTION_STDPRICE_INDEX)!=null) setStdPrice(c.getDouble(READ_PROMOTION_STDPRICE_INDEX));
        	setUnit(c.getString(READ_PROMOTION_UNIT_INDEX));
        	if(c.getString(READ_PROMOTION_LOWESTSPRICE_INDEX)!=null) setLowestSPrice(c.getDouble(READ_PROMOTION_LOWESTSPRICE_INDEX));
            setVersionNo(c.getString(READ_PROMOTION_VERSIONNO_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}
	
	int promoteGroupID;
	String groupPriceGrade;
	
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
	public Promotion getPromotionByRule(LikDBAdapter adapter) {
		Log.d(TAG,"getItemID="+getItemID());
		Log.d(TAG,"getCustID="+getCustID());
		Log.d(TAG,"promoteGroupID="+promoteGroupID);
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"='"+getCompanyID()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_ITEMID+"="+getItemID());
		qb.appendWhere(" and "+COLUMN_NAME_CUSTID+"="+getCustID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_PROMOTION_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if(c!= null && c.moveToFirst()) {
        	Log.d(TAG, "found in rule1");
        	setSerialID(c.getInt(READ_PROMOTION_SERIALID_INDEX));
        	setItemID(c.getInt(READ_PROMOTION_ITEMID_INDEX));
        	setPriceGrade(c.getString(READ_PROMOTION_PRICEGRADE_INDEX));
//        	setClassify(c.getString(READ_PROMOTION_CLASSIFY_INDEX));
//        	setArea(c.getString(READ_PROMOTION_AREA_INDEX));
        	setCustID(c.getInt(READ_PROMOTION_CUSTID_INDEX));
            try {
            	Date dateFrom = sdf.parse(c.getString(READ_PROMOTION_DATEFROM_INDEX));
            	setDateFrom(dateFrom);
            } catch(ParseException pe) {
            	setDateFrom(null);
            }
            try {
            	Date dateTo = sdf.parse(c.getString(READ_PROMOTION_DATETO_INDEX));
            	setDateTo(dateTo);
            } catch(ParseException pe) {
            	setDateTo(null);
            }
        	setPromoteTerms(c.getString(READ_PROMOTION_PROMOTETERMS_INDEX));
        	if(c.getString(READ_PROMOTION_STDPRICE_INDEX)!=null) setStdPrice(c.getDouble(READ_PROMOTION_STDPRICE_INDEX));
        	setUnit(c.getString(READ_PROMOTION_UNIT_INDEX));
        	if(c.getString(READ_PROMOTION_LOWESTSPRICE_INDEX)!=null) setLowestSPrice(c.getDouble(READ_PROMOTION_LOWESTSPRICE_INDEX));
            setVersionNo(c.getString(READ_PROMOTION_VERSIONNO_INDEX));
        	setRid(0);
        	if(c != null) c.close();
        } else {
        	qb = new SQLiteQueryBuilder();
    		qb.setTables(getTableName());
    		qb.setProjectionMap(projectionMap);
    		qb.appendWhere(COLUMN_NAME_COMPANYID+"='"+getCompanyID()+"'");
    		qb.appendWhere(" and "+COLUMN_NAME_ITEMID+"="+getItemID());
    		qb.appendWhere(" and "+COLUMN_NAME_CUSTID+"="+getPromoteGroupID());
    		c = qb.query(
    				db,            // The database to query
    				READ_PROMOTION_PROJECTION,    // The columns to return from the query
    				null,     // The columns for the where clause
    				null, // The values for the where clause
    				null,          // don't group the rows
    				null,          // don't filter by row groups
    				null        // The sort order
    				);
    		if(c!= null && c.moveToFirst()) {
            	Log.d(TAG, "found in rule2.0");
            	setSerialID(c.getInt(READ_PROMOTION_SERIALID_INDEX));
            	setItemID(c.getInt(READ_PROMOTION_ITEMID_INDEX));
            	setPriceGrade(c.getString(READ_PROMOTION_PRICEGRADE_INDEX));
//            	setClassify(c.getString(READ_PROMOTION_CLASSIFY_INDEX));
//            	setArea(c.getString(READ_PROMOTION_AREA_INDEX));
            	setCustID(c.getInt(READ_PROMOTION_CUSTID_INDEX));
                try {
                	Date dateFrom = sdf.parse(c.getString(READ_PROMOTION_DATEFROM_INDEX));
                	setDateFrom(dateFrom);
                } catch(ParseException pe) {
                	setDateFrom(null);
                }
                try {
                	Date dateTo = sdf.parse(c.getString(READ_PROMOTION_DATETO_INDEX));
                	setDateTo(dateTo);
                } catch(ParseException pe) {
                	setDateTo(null);
                }
            	setPromoteTerms(c.getString(READ_PROMOTION_PROMOTETERMS_INDEX));
            	if(c.getString(READ_PROMOTION_STDPRICE_INDEX)!=null) setStdPrice(c.getDouble(READ_PROMOTION_STDPRICE_INDEX));
            	setUnit(c.getString(READ_PROMOTION_UNIT_INDEX));
            	if(c.getString(READ_PROMOTION_LOWESTSPRICE_INDEX)!=null) setLowestSPrice(c.getDouble(READ_PROMOTION_LOWESTSPRICE_INDEX));
                setVersionNo(c.getString(READ_PROMOTION_VERSIONNO_INDEX));
            	setRid(0);
            	if(c != null) c.close();    			
            } else {
            	qb = new SQLiteQueryBuilder();
        		qb.setTables(getTableName());
        		qb.setProjectionMap(projectionMap);
        		qb.appendWhere(COLUMN_NAME_COMPANYID+"='"+getCompanyID()+"'");
        		qb.appendWhere(" and "+COLUMN_NAME_ITEMID+"="+getItemID());
        		qb.appendWhere(" and "+COLUMN_NAME_PRICEGRADE+"='"+getGroupPriceGrade()+"'");
        		qb.appendWhere(" and "+COLUMN_NAME_CUSTID+"=0");
        		c = qb.query(
        				db,            // The database to query
        				READ_PROMOTION_PROJECTION,    // The columns to return from the query
        				null,     // The columns for the where clause
        				null, // The values for the where clause
        				null,          // don't group the rows
        				null,          // don't filter by row groups
        				null        // The sort order
        				);
        		if(c!= null && c.moveToFirst()) {
                	Log.d(TAG, "found in rule2");
                	setSerialID(c.getInt(READ_PROMOTION_SERIALID_INDEX));
                	setItemID(c.getInt(READ_PROMOTION_ITEMID_INDEX));
                	setPriceGrade(c.getString(READ_PROMOTION_PRICEGRADE_INDEX));
//                	setClassify(c.getString(READ_PROMOTION_CLASSIFY_INDEX));
//                	setArea(c.getString(READ_PROMOTION_AREA_INDEX));
                	setCustID(c.getInt(READ_PROMOTION_CUSTID_INDEX));
                    try {
                    	Date dateFrom = sdf.parse(c.getString(READ_PROMOTION_DATEFROM_INDEX));
                    	setDateFrom(dateFrom);
                    } catch(ParseException pe) {
                    	setDateFrom(null);
                    }
                    try {
                    	Date dateTo = sdf.parse(c.getString(READ_PROMOTION_DATETO_INDEX));
                    	setDateTo(dateTo);
                    } catch(ParseException pe) {
                    	setDateTo(null);
                    }
                	setPromoteTerms(c.getString(READ_PROMOTION_PROMOTETERMS_INDEX));
                	if(c.getString(READ_PROMOTION_STDPRICE_INDEX)!=null) setStdPrice(c.getDouble(READ_PROMOTION_STDPRICE_INDEX));
                	setUnit(c.getString(READ_PROMOTION_UNIT_INDEX));
                	if(c.getString(READ_PROMOTION_LOWESTSPRICE_INDEX)!=null) setLowestSPrice(c.getDouble(READ_PROMOTION_LOWESTSPRICE_INDEX));
                    setVersionNo(c.getString(READ_PROMOTION_VERSIONNO_INDEX));
                	setRid(0);
                	if(c != null) c.close();    			
	    		} else {
	            	qb = new SQLiteQueryBuilder();
	        		qb.setTables(getTableName());
	        		qb.setProjectionMap(projectionMap);
	        		qb.appendWhere(COLUMN_NAME_COMPANYID+"='"+getCompanyID()+"'");
	        		qb.appendWhere(" and "+COLUMN_NAME_ITEMID+"="+getItemID());
	        		qb.appendWhere(" and "+COLUMN_NAME_PRICEGRADE+"='"+getPriceGrade()+"'");
	        		qb.appendWhere(" and "+COLUMN_NAME_CUSTID+"=0");
	        		c = qb.query(
	        				db,            // The database to query
	        				READ_PROMOTION_PROJECTION,    // The columns to return from the query
	        				null,     // The columns for the where clause
	        				null, // The values for the where clause
	        				null,          // don't group the rows
	        				null,          // don't filter by row groups
	        				null        // The sort order
	        				);
	        		if(c!= null && c.moveToFirst()) {
	                	Log.d(TAG, "found in rule2.1");
	                	setSerialID(c.getInt(READ_PROMOTION_SERIALID_INDEX));
	                	setItemID(c.getInt(READ_PROMOTION_ITEMID_INDEX));
	                	setPriceGrade(c.getString(READ_PROMOTION_PRICEGRADE_INDEX));
	//                	setClassify(c.getString(READ_PROMOTION_CLASSIFY_INDEX));
	//                	setArea(c.getString(READ_PROMOTION_AREA_INDEX));
	                	setCustID(c.getInt(READ_PROMOTION_CUSTID_INDEX));
	                    try {
	                    	Date dateFrom = sdf.parse(c.getString(READ_PROMOTION_DATEFROM_INDEX));
	                    	setDateFrom(dateFrom);
	                    } catch(ParseException pe) {
	                    	setDateFrom(null);
	                    }
	                    try {
	                    	Date dateTo = sdf.parse(c.getString(READ_PROMOTION_DATETO_INDEX));
	                    	setDateTo(dateTo);
	                    } catch(ParseException pe) {
	                    	setDateTo(null);
	                    }
	                	setPromoteTerms(c.getString(READ_PROMOTION_PROMOTETERMS_INDEX));
	                	if(c.getString(READ_PROMOTION_STDPRICE_INDEX)!=null) setStdPrice(c.getDouble(READ_PROMOTION_STDPRICE_INDEX));
	                	setUnit(c.getString(READ_PROMOTION_UNIT_INDEX));
	                	if(c.getString(READ_PROMOTION_LOWESTSPRICE_INDEX)!=null) setLowestSPrice(c.getDouble(READ_PROMOTION_LOWESTSPRICE_INDEX));
	                    setVersionNo(c.getString(READ_PROMOTION_VERSIONNO_INDEX));
	                	setRid(0);
	                	if(c != null) c.close();    	
	        		} else {
		            	qb = new SQLiteQueryBuilder();
		        		qb.setTables(getTableName());
		        		qb.setProjectionMap(projectionMap);
		        		qb.appendWhere(COLUMN_NAME_COMPANYID+"='"+getCompanyID()+"'");
		        		qb.appendWhere(" and "+COLUMN_NAME_ITEMID+"="+getItemID());
		        		qb.appendWhere(" and "+COLUMN_NAME_PRICEGRADE+" is null ");
		        		qb.appendWhere(" and "+COLUMN_NAME_CUSTID+"=0");
		        		c = qb.query(
		        				db,            // The database to query
		        				READ_PROMOTION_PROJECTION,    // The columns to return from the query
		        				null,     // The columns for the where clause
		        				null, // The values for the where clause
		        				null,          // don't group the rows
		        				null,          // don't filter by row groups
		        				null        // The sort order
		        				);
		        		if(c!= null && c.moveToFirst()) {
		                	Log.d(TAG, "found in rule3");
		                	setSerialID(c.getInt(READ_PROMOTION_SERIALID_INDEX));
		                	setItemID(c.getInt(READ_PROMOTION_ITEMID_INDEX));
		                	setPriceGrade(c.getString(READ_PROMOTION_PRICEGRADE_INDEX));
		//                	setClassify(c.getString(READ_PROMOTION_CLASSIFY_INDEX));
		//                	setArea(c.getString(READ_PROMOTION_AREA_INDEX));
		                	setCustID(c.getInt(READ_PROMOTION_CUSTID_INDEX));
		                    try {
		                    	Date dateFrom = sdf.parse(c.getString(READ_PROMOTION_DATEFROM_INDEX));
		                    	setDateFrom(dateFrom);
		                    } catch(ParseException pe) {
		                    	setDateFrom(null);
		                    }
		                    try {
		                    	Date dateTo = sdf.parse(c.getString(READ_PROMOTION_DATETO_INDEX));
		                    	setDateTo(dateTo);
		                    } catch(ParseException pe) {
		                    	setDateTo(null);
		                    }
		                	setPromoteTerms(c.getString(READ_PROMOTION_PROMOTETERMS_INDEX));
		                	if(c.getString(READ_PROMOTION_STDPRICE_INDEX)!=null) setStdPrice(c.getDouble(READ_PROMOTION_STDPRICE_INDEX));
		                	setUnit(c.getString(READ_PROMOTION_UNIT_INDEX));
		                	if(c.getString(READ_PROMOTION_LOWESTSPRICE_INDEX)!=null) setLowestSPrice(c.getDouble(READ_PROMOTION_LOWESTSPRICE_INDEX));
		                    setVersionNo(c.getString(READ_PROMOTION_VERSIONNO_INDEX));
		                	setRid(0);
		                	if(c != null) c.close();    	
		        		} else {
		        			setRid(-1);
		        		}
	        		}
	    		}
	        }
    	}
        closedb(adapter);		
		return this;
	}

	/**
	 * 1. getCompanyID+getItemID+getCustID
	 * 2. getCompanyID+getItemID+getArea+Promotion.getCustID = 0
	 * 3. getCompanyID+getItemID is null+getClassify+Promotion.CustID
	 * 4. getCompanyID+getItemID is null+getClassify+getArea+Promotion.getCustID = 0
	 * @return
	 */
//	public Promotion getPromotionByRuleFTN(LikDBAdapter adapter) {
//		SQLiteDatabase db = getdb(adapter);
//		// Constructs a new query builder and sets its table name
//		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//		qb.setTables(getTableName());
//		qb.setProjectionMap(projectionMap);
//		qb.appendWhere(COLUMN_NAME_COMPANYID+"='"+getCompanyID()+"'");
//		qb.appendWhere(" and "+COLUMN_NAME_ITEMID+"="+getItemID());
//		qb.appendWhere(" and "+COLUMN_NAME_CUSTID+"="+getCustID());
//		/*
//		 * Performs the query. If no problems occur trying to read the database, then a Cursor
//		 * object is returned; otherwise, the cursor variable contains null. If no records were
//		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
//		 */
//		Cursor c = qb.query(
//				db,            // The database to query
//				READ_PROMOTION_PROJECTION,    // The columns to return from the query
//				null,     // The columns for the where clause
//				null, // The values for the where clause
//				null,          // don't group the rows
//				null,          // don't filter by row groups
//				null        // The sort order
//				);
//        if(c!= null && c.moveToFirst()) {
//        	Log.d(TAG, "found in rule1");
//        	setSerialID(c.getInt(READ_PROMOTION_SERIALID_INDEX));
//        	setItemID(c.getInt(READ_PROMOTION_ITEMID_INDEX));
//        	setPriceGrade(c.getString(READ_PROMOTION_PRICEGRADE_INDEX));
//        	setClassify(c.getString(READ_PROMOTION_CLASSIFY_INDEX));
//        	setArea(c.getString(READ_PROMOTION_AREA_INDEX));
//        	setCustID(c.getInt(READ_PROMOTION_CUSTID_INDEX));
//            try {
//            	Date dateFrom = sdf.parse(c.getString(READ_PROMOTION_DATEFROM_INDEX));
//            	setDateFrom(dateFrom);
//            } catch(ParseException pe) {
//            	setDateFrom(null);
//            }
//            try {
//            	Date dateTo = sdf.parse(c.getString(READ_PROMOTION_DATETO_INDEX));
//            	setDateTo(dateTo);
//            } catch(ParseException pe) {
//            	setDateTo(null);
//            }
//        	setPromoteTerms(c.getString(READ_PROMOTION_PROMOTETERMS_INDEX));
//        	setStdPrice(c.getDouble(READ_PROMOTION_STDPRICE_INDEX));
//        	setUnit(c.getString(READ_PROMOTION_UNIT_INDEX));
//            setVersionNo(c.getString(READ_PROMOTION_VERSIONNO_INDEX));
//        	setRid(0);
//        	if(c != null) c.close();
//        } else {
//        	qb = new SQLiteQueryBuilder();
//    		qb.setTables(getTableName());
//    		qb.setProjectionMap(projectionMap);
//    		qb.appendWhere(COLUMN_NAME_COMPANYID+"='"+getCompanyID()+"'");
//    		qb.appendWhere(" and "+COLUMN_NAME_ITEMID+"=0");
//    		qb.appendWhere(" and "+COLUMN_NAME_AREA+"='"+getArea()+"'");
//    		qb.appendWhere(" and "+COLUMN_NAME_CUSTID+"="+getCustID());
//    		c = qb.query(
//    				db,            // The database to query
//    				READ_PROMOTION_PROJECTION,    // The columns to return from the query
//    				null,     // The columns for the where clause
//    				null, // The values for the where clause
//    				null,          // don't group the rows
//    				null,          // don't filter by row groups
//    				null        // The sort order
//    				);
//    		if(c!= null && c.moveToFirst()) {
//            	Log.d(TAG, "found in rule2");
//            	setSerialID(c.getInt(READ_PROMOTION_SERIALID_INDEX));
//            	setItemID(c.getInt(READ_PROMOTION_ITEMID_INDEX));
//            	setPriceGrade(c.getString(READ_PROMOTION_PRICEGRADE_INDEX));
//            	setClassify(c.getString(READ_PROMOTION_CLASSIFY_INDEX));
//            	setArea(c.getString(READ_PROMOTION_AREA_INDEX));
//            	setCustID(c.getInt(READ_PROMOTION_CUSTID_INDEX));
//                try {
//                	Date dateFrom = sdf.parse(c.getString(READ_PROMOTION_DATEFROM_INDEX));
//                	setDateFrom(dateFrom);
//                } catch(ParseException pe) {
//                	setDateFrom(null);
//                }
//                try {
//                	Date dateTo = sdf.parse(c.getString(READ_PROMOTION_DATETO_INDEX));
//                	setDateTo(dateTo);
//                } catch(ParseException pe) {
//                	setDateTo(null);
//                }
//            	setPromoteTerms(c.getString(READ_PROMOTION_PROMOTETERMS_INDEX));
//            	setStdPrice(c.getDouble(READ_PROMOTION_STDPRICE_INDEX));
//            	setUnit(c.getString(READ_PROMOTION_UNIT_INDEX));
//                setVersionNo(c.getString(READ_PROMOTION_VERSIONNO_INDEX));
//            	setRid(0);
//            	if(c != null) c.close();    			
//    		} else {
//            	qb = new SQLiteQueryBuilder();
//        		qb.setTables(getTableName());
//        		qb.setProjectionMap(projectionMap);
//        		qb.appendWhere(COLUMN_NAME_COMPANYID+"='"+getCompanyID()+"'");
//        		qb.appendWhere(" and "+COLUMN_NAME_ITEMID+"=0");
//        		qb.appendWhere(" and "+COLUMN_NAME_CLASSIFY+"='"+getClassify()+"'");
//        		qb.appendWhere(" and "+COLUMN_NAME_CUSTID+"="+getCustID());
//        		c = qb.query(
//        				db,            // The database to query
//        				READ_PROMOTION_PROJECTION,    // The columns to return from the query
//        				null,     // The columns for the where clause
//        				null, // The values for the where clause
//        				null,          // don't group the rows
//        				null,          // don't filter by row groups
//        				null        // The sort order
//        				);
//        		if(c!= null && c.moveToFirst()) {
//                	Log.d(TAG, "found in rule3");
//                	setSerialID(c.getInt(READ_PROMOTION_SERIALID_INDEX));
//                	setItemID(c.getInt(READ_PROMOTION_ITEMID_INDEX));
//                	setPriceGrade(c.getString(READ_PROMOTION_PRICEGRADE_INDEX));
//                	setClassify(c.getString(READ_PROMOTION_CLASSIFY_INDEX));
//                	setArea(c.getString(READ_PROMOTION_AREA_INDEX));
//                	setCustID(c.getInt(READ_PROMOTION_CUSTID_INDEX));
//                    try {
//                    	Date dateFrom = sdf.parse(c.getString(READ_PROMOTION_DATEFROM_INDEX));
//                    	setDateFrom(dateFrom);
//                    } catch(ParseException pe) {
//                    	setDateFrom(null);
//                    }
//                    try {
//                    	Date dateTo = sdf.parse(c.getString(READ_PROMOTION_DATETO_INDEX));
//                    	setDateTo(dateTo);
//                    } catch(ParseException pe) {
//                    	setDateTo(null);
//                    }
//                	setPromoteTerms(c.getString(READ_PROMOTION_PROMOTETERMS_INDEX));
//                	setStdPrice(c.getDouble(READ_PROMOTION_STDPRICE_INDEX));
//                	setUnit(c.getString(READ_PROMOTION_UNIT_INDEX));
//                    setVersionNo(c.getString(READ_PROMOTION_VERSIONNO_INDEX));
//                	setRid(0);
//                	if(c != null) c.close();    			
//        		} else {
//                	qb = new SQLiteQueryBuilder();
//            		qb.setTables(getTableName());
//            		qb.setProjectionMap(projectionMap);
//            		qb.appendWhere(COLUMN_NAME_COMPANYID+"='"+getCompanyID()+"'");
//            		qb.appendWhere(" and "+COLUMN_NAME_ITEMID+"=0");
//            		qb.appendWhere(" and "+COLUMN_NAME_CLASSIFY+"='"+getClassify()+"'");
//             		qb.appendWhere(" and "+COLUMN_NAME_AREA+"='"+getArea()+"'");
//             	    qb.appendWhere(" and "+COLUMN_NAME_CUSTID+"=0");
//            		c = qb.query(
//            				db,            // The database to query
//            				READ_PROMOTION_PROJECTION,    // The columns to return from the query
//            				null,     // The columns for the where clause
//            				null, // The values for the where clause
//            				null,          // don't group the rows
//            				null,          // don't filter by row groups
//            				null        // The sort order
//            				);
//            		if(c!= null && c.moveToFirst()) {
//                    	Log.d(TAG, "found in rule4");
//                    	setSerialID(c.getInt(READ_PROMOTION_SERIALID_INDEX));
//                    	setItemID(c.getInt(READ_PROMOTION_ITEMID_INDEX));
//                    	setPriceGrade(c.getString(READ_PROMOTION_PRICEGRADE_INDEX));
//                    	setClassify(c.getString(READ_PROMOTION_CLASSIFY_INDEX));
//                    	setArea(c.getString(READ_PROMOTION_AREA_INDEX));
//                    	setCustID(c.getInt(READ_PROMOTION_CUSTID_INDEX));
//                        try {
//                        	Date dateFrom = sdf.parse(c.getString(READ_PROMOTION_DATEFROM_INDEX));
//                        	setDateFrom(dateFrom);
//                        } catch(ParseException pe) {
//                        	setDateFrom(null);
//                        }
//                        try {
//                        	Date dateTo = sdf.parse(c.getString(READ_PROMOTION_DATETO_INDEX));
//                        	setDateTo(dateTo);
//                        } catch(ParseException pe) {
//                        	setDateTo(null);
//                        }
//                    	setPromoteTerms(c.getString(READ_PROMOTION_PROMOTETERMS_INDEX));
//                    	setStdPrice(c.getDouble(READ_PROMOTION_STDPRICE_INDEX));
//                    	setUnit(c.getString(READ_PROMOTION_UNIT_INDEX));
//                        setVersionNo(c.getString(READ_PROMOTION_VERSIONNO_INDEX));
//                    	setRid(0);
//                    	if(c != null) c.close();    			
//            		} else {
//            			setRid(-1);
//            		}        			
//        		}
//    		}
//        }
//        closedb(adapter);		
//		return this;
//    }
	
	@Override
	public boolean processDownload(LikDBAdapter adapter,
			Map<String, String> detail, boolean isOnlyInsert) {
		boolean result = true;
		String flag = detail.get(FLAG_KEY);
		setCompanyID(Integer.parseInt(detail.get(COLUMN_NAME_COMPANYID)));
		setPromotionID(Integer.parseInt(detail.get(COLUMN_NAME_PROMOTIONID)));
		if(!isOnlyInsert) findByKey(adapter);
		setItemID(Integer.parseInt(detail.get(COLUMN_NAME_ITEMID)));
		setPriceGrade(detail.get(COLUMN_NAME_PRICEGRADE));
//		setClassify(detail.get(COLUMN_NAME_CLASSIFY));
//		setArea(detail.get(COLUMN_NAME_AREA));
		setCustID(Integer.parseInt(detail.get(COLUMN_NAME_CUSTID)));
		try {
			if(detail.get(COLUMN_NAME_DATEFROM) != null) setDateFrom(sdf2.parse(detail.get(COLUMN_NAME_DATEFROM)));
			else setDateFrom(null);
			if(detail.get(COLUMN_NAME_DATETO) != null) setDateTo(sdf2.parse(detail.get(COLUMN_NAME_DATETO)));
			else setDateTo(null);
		} catch (ParseException e) {
			Log.e(TAG,e.getMessage());
		}
		setPromoteTerms(detail.get(COLUMN_NAME_PROMOTETERMS));
		Log.d(TAG,"detail.get(COLUMN_NAME_STDPRICE)==null?"+(detail.get(COLUMN_NAME_STDPRICE)==null));
		setStdPrice(detail.get(COLUMN_NAME_STDPRICE)==null?null:Double.valueOf(detail.get(COLUMN_NAME_STDPRICE)));
		setUnit(detail.get(COLUMN_NAME_UNIT));
		if(detail.get(COLUMN_NAME_LOWESTSPRICE)!=null) setLowestSPrice(Double.parseDouble(detail.get(COLUMN_NAME_LOWESTSPRICE)));
		else setLowestSPrice(null);
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

}
