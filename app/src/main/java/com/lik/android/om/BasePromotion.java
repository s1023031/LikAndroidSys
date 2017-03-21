package com.lik.android.om;

import java.util.Date;
import java.util.HashMap;

public abstract class BasePromotion extends BaseOM<Promotion> {

	private static final long serialVersionUID = 1505733435971485795L;

	public static final String TABLE_NAME = "Promotion";

	public static final String TABLE_CH_NAME = "產品促銷資料";

	/**
     * Column name for 序號 of the Promotion
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for 公司流水號 of the Promotion
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";

    /**
     * Column name for 產品促銷流水號 of the Promotion
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PROMOTIONID = "PromotionID";

    /**
     * Column name for 產品促銷流水號 of the Promotion
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ITEMID = "ItemID";

    /**
     * Column name for 售價等級 of the Promotion
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PRICEGRADE = "PriceGrade";

//    /**
//     * Column name for 售價等級 of the Promotion
//     * <P>Type: TEXT</P>
//     */
//    public static final String COLUMN_NAME_CLASSIFY = "Classify";
//
//    /**
//     * Column name for 售價等級 of the Promotion
//     * <P>Type: TEXT</P>
//     */
//    public static final String COLUMN_NAME_AREA = "Area";

    /**
     * Column name for 客戶流水號 of the Promotion
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CUSTID = "CustID";

    /**
     * Column name for 促銷起日 of the Promotion
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DATEFROM = "DateFrom";

    /**
     * Column name for 促銷止日 of the Promotion
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DATETO = "DateTo";

    /**
     * Column name for 促銷條件 of the Promotion
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PROMOTETERMS = "PromoteTerms";

    /**
     * Column name for 售    價 of the Promotion
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_STDPRICE = "StdPrice";

    /**
     * Column name for 單  位 of the Promotion
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UNIT = "Unit";

    /**
     * Column name for LowestSPrice of the Promotion
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_LOWESTSPRICE = "LowestSPrice";

    /**
     * Column name for 版本 of the Promotion
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_VERSIONNO = "VersionNo";

//    private String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
//    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
//    		COLUMN_NAME_COMPANYID+" INTEGER,"+
//    		COLUMN_NAME_PROMOTIONID+" INTEGER,"+
//    		COLUMN_NAME_ITEMID+" INTEGER,"+
//    		COLUMN_NAME_PRICEGRADE+" TEXT,"+
//    		COLUMN_NAME_CUSTID+" INTEGER,"+
//    		COLUMN_NAME_DATEFROM+" TEXT,"+
//    		COLUMN_NAME_DATETO+" TEXT,"+
//    		COLUMN_NAME_PROMOTETERMS+" TEXT,"+
//    		COLUMN_NAME_STDPRICE+" REAL,"+
//    		COLUMN_NAME_UNIT+" TEXT,"+
//    		COLUMN_NAME_VERSIONNO+" TEXT);";
//
//    private String[] CREATE_INDEX_CMD = {
//    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+
//    			COLUMN_NAME_COMPANYID+","+COLUMN_NAME_PROMOTIONID+");",
//    };
//    
//    private String DROP_CMD = "DROP TABLE IF EXISTS "+getTableName();

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_PROMOTION_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_COMPANYID,  // Projection position 1, 
    	COLUMN_NAME_PROMOTIONID, // Projection position 2, 
    	COLUMN_NAME_ITEMID, // Projection position 3, 
    	COLUMN_NAME_PRICEGRADE, // Projection position 4, 
//    	COLUMN_NAME_CLASSIFY, // Projection position 5, 
//    	COLUMN_NAME_AREA, // Projection position 6, 
    	COLUMN_NAME_CUSTID, // Projection position 5, 
    	COLUMN_NAME_DATEFROM, // Projection position 6, 
    	COLUMN_NAME_DATETO, // Projection position 7, 
    	COLUMN_NAME_PROMOTETERMS, // Projection position 8, 
    	COLUMN_NAME_STDPRICE, // Projection position 9, 
    	COLUMN_NAME_UNIT, // Projection position 10, 
    	COLUMN_NAME_LOWESTSPRICE, // Projection position 11, 
    	COLUMN_NAME_VERSIONNO, // Projection position 12, 
    };
    protected static final int READ_PROMOTION_SERIALID_INDEX = 0;
    protected static final int READ_PROMOTION_COMPANYID_INDEX = 1;
    protected static final int READ_PROMOTION_PROMOTIONID_INDEX = 2;
    protected static final int READ_PROMOTION_ITEMID_INDEX = 3;
    protected static final int READ_PROMOTION_PRICEGRADE_INDEX = 4;
//    protected static final int READ_PROMOTION_CLASSIFY_INDEX = 5;
//    protected static final int READ_PROMOTION_AREA_INDEX = 6;
    protected static final int READ_PROMOTION_CUSTID_INDEX = 5;
    protected static final int READ_PROMOTION_DATEFROM_INDEX = 6;
    protected static final int READ_PROMOTION_DATETO_INDEX = 7;
    protected static final int READ_PROMOTION_PROMOTETERMS_INDEX = 8;
    protected static final int READ_PROMOTION_STDPRICE_INDEX = 9;
    protected static final int READ_PROMOTION_UNIT_INDEX = 10;
    protected static final int READ_PROMOTION_LOWESTSPRICE_INDEX = 11;
    protected static final int READ_PROMOTION_VERSIONNO_INDEX = 12;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BasePromotion() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
    	projectionMap.put(COLUMN_NAME_PROMOTIONID, COLUMN_NAME_PROMOTIONID);
    	projectionMap.put(COLUMN_NAME_ITEMID, COLUMN_NAME_ITEMID);
    	projectionMap.put(COLUMN_NAME_PRICEGRADE, COLUMN_NAME_PRICEGRADE);
//    	projectionMap.put(COLUMN_NAME_CLASSIFY, COLUMN_NAME_CLASSIFY);
//    	projectionMap.put(COLUMN_NAME_AREA, COLUMN_NAME_AREA);
    	projectionMap.put(COLUMN_NAME_CUSTID, COLUMN_NAME_CUSTID);
    	projectionMap.put(COLUMN_NAME_DATEFROM, COLUMN_NAME_DATEFROM);
    	projectionMap.put(COLUMN_NAME_DATETO, COLUMN_NAME_DATETO);
    	projectionMap.put(COLUMN_NAME_PROMOTETERMS, COLUMN_NAME_PROMOTETERMS);
    	projectionMap.put(COLUMN_NAME_STDPRICE, COLUMN_NAME_STDPRICE);
    	projectionMap.put(COLUMN_NAME_UNIT, COLUMN_NAME_UNIT);
    	projectionMap.put(COLUMN_NAME_LOWESTSPRICE, COLUMN_NAME_LOWESTSPRICE);
    	projectionMap.put(COLUMN_NAME_VERSIONNO, COLUMN_NAME_VERSIONNO);
    }

    @Override
	public String getTableName() {
//		return TABLE_NAME;
//		return TABLE_NAME+"_"+getTableCompanyID();
		return TABLE_NAME+"_"+(getTableCompanyID()==0?companyID:getTableCompanyID());
	}

	@Override
	public String getCreateCMD() {
		String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
	    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
	    		COLUMN_NAME_COMPANYID+" INTEGER,"+
	    		COLUMN_NAME_PROMOTIONID+" INTEGER,"+
	    		COLUMN_NAME_ITEMID+" INTEGER,"+
	    		COLUMN_NAME_PRICEGRADE+" TEXT,"+
//	    		COLUMN_NAME_CLASSIFY+" TEXT,"+
//	    		COLUMN_NAME_AREA+" TEXT,"+
	    		COLUMN_NAME_CUSTID+" INTEGER,"+
	    		COLUMN_NAME_DATEFROM+" TEXT,"+
	    		COLUMN_NAME_DATETO+" TEXT,"+
	    		COLUMN_NAME_PROMOTETERMS+" TEXT,"+
	    		COLUMN_NAME_STDPRICE+" REAL,"+
	    		COLUMN_NAME_UNIT+" TEXT,"+
	    		COLUMN_NAME_LOWESTSPRICE+" REAL,"+
	    		COLUMN_NAME_VERSIONNO+" TEXT);";
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+
		    			COLUMN_NAME_COMPANYID+","+COLUMN_NAME_PROMOTIONID+");",
		    };
		return CREATE_INDEX_CMD;
	}

	@Override
	public String getDropCMD() {
		String DROP_CMD = "DROP TABLE IF EXISTS "+getTableName();
		return DROP_CMD;
	}

    private long serialID; //key
	private int companyID;
	private int promotionID;
	private int itemID;
	private String priceGrade;
	private String classify;
	private String area;
	private int custID;
	private Date dateFrom;
	private Date dateTo;
	private String promoteTerms;
	private Double stdPrice;
	private String unit;
	private Double lowestSPrice;
	private String versionNo;

	public long getSerialID() {
		return serialID;
	}

	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public int getPromotionID() {
		return promotionID;
	}

	public void setPromotionID(int promotionID) {
		this.promotionID = promotionID;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getPriceGrade() {
		return priceGrade;
	}

	public void setPriceGrade(String priceGrade) {
		this.priceGrade = priceGrade;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getCustID() {
		return custID;
	}

	public void setCustID(int custID) {
		this.custID = custID;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getPromoteTerms() {
		return promoteTerms;
	}

	public void setPromoteTerms(String promoteTerms) {
		this.promoteTerms = promoteTerms;
	}

	public Double getStdPrice() {
		return stdPrice;
	}

	public void setStdPrice(Double stdPrice) {
		this.stdPrice = stdPrice;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getLowestSPrice() {
		return lowestSPrice;
	}

	public void setLowestSPrice(Double lowestSPrice) {
		this.lowestSPrice = lowestSPrice;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

}
