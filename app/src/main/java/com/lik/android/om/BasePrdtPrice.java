package com.lik.android.om;

import java.util.HashMap;

public abstract class BasePrdtPrice extends BaseOM<PrdtPrice> {

	private static final long serialVersionUID = -2311067221446238799L;

	public static final String TABLE_NAME = "PrdtPrice";

	public static final String TABLE_CH_NAME = "產品等級售價資料";

	/**
     * Column name for 序號 of the PrdtPrice
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for 使用單位流水號 of the PrdtPrice
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";

    /**
     * Column name for 產品流水號 of the PrdtPrice
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ITEMID = "ItemID";

    /**
     * Column name for 買賣單位 of the PrdtPrice
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UNIT = "Unit";

    /**
     * Column name for 售價等級 of the PrdtPrice
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_GRADE = "Grade";

    /**
     * Column name for 售價(月結) of the PrdtPrice
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_STDPRICE = "StdPrice";

    /**
     * Column name for 售價(現金) of the PrdtPrice
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CASHPRICE = "CashPrice";

    /**
     * Column name for 折扣率 of the PrdtPrice
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DISCRATE = "DiscRate";

    /**
     * Column name for LowestSPrice本 of the PrdtPrice
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_LOWESTSPRICE = "LowestSPrice";

    /**
     * Column name for LowestCPrice of the PrdtPrice
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_LOWESTCPRICE = "LowestCPrice";

    /**
     * Column name for 版本 of the PrdtPrice
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_VERSIONNO = "VersionNo";

//    private String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
//    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
//    		COLUMN_NAME_COMPANYID+" INTEGER,"+
//    		COLUMN_NAME_ITEMID+" INTEGER,"+
//    		COLUMN_NAME_UNIT+" TEXT,"+
//    		COLUMN_NAME_GRADE+" TEXT,"+
//    		COLUMN_NAME_STDPRICE+" REAL,"+
//    		COLUMN_NAME_CASHPRICE+" REAL,"+
//    		COLUMN_NAME_DISCRATE+" REAL,"+
//    		COLUMN_NAME_VERSIONNO+" TEXT);";
//
//    private String[] CREATE_INDEX_CMD = {
//    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+
//    			COLUMN_NAME_COMPANYID+","+COLUMN_NAME_ITEMID+","+COLUMN_NAME_UNIT+","+COLUMN_NAME_GRADE+");",
//    };
//    
//    private String DROP_CMD = "DROP TABLE IF EXISTS "+getTableName();

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_PRDTPRICE_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_COMPANYID,  // Projection position 1, 
    	COLUMN_NAME_ITEMID, // Projection position 2, 
    	COLUMN_NAME_UNIT, // Projection position 3, 
    	COLUMN_NAME_GRADE, // Projection position 4, 
    	COLUMN_NAME_STDPRICE, // Projection position 5, 
    	COLUMN_NAME_CASHPRICE, // Projection position 6, 
    	COLUMN_NAME_DISCRATE, // Projection position 7, 
    	COLUMN_NAME_LOWESTSPRICE, // Projection position 8, 
    	COLUMN_NAME_LOWESTCPRICE, // Projection position 9, 
    	COLUMN_NAME_VERSIONNO, // Projection position 10, 
    };
    protected static final int READ_PRDTPRICE_SERIALID_INDEX = 0;
    protected static final int READ_PRDTPRICE_COMPANYID_INDEX = 1;
    protected static final int READ_PRDTPRICE_ITEMID_INDEX = 2;
    protected static final int READ_PRDTPRICE_UNIT_INDEX = 3;
    protected static final int READ_PRDTPRICE_GRADE_INDEX = 4;
    protected static final int READ_PRDTPRICE_STDPRICE_INDEX = 5;
    protected static final int READ_PRDTPRICE_CASHPRICE_INDEX = 6;
    protected static final int READ_PRDTPRICE_DISCRATE_INDEX = 7;
    protected static final int READ_PRDTPRICE_LOWESTSPRICE_INDEX = 8;
    protected static final int READ_PRDTPRICE_LOWESTCPRICE_INDEX = 9;
    protected static final int READ_PRDTPRICE_VERSIONNO_INDEX = 10;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BasePrdtPrice() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
    	projectionMap.put(COLUMN_NAME_ITEMID, COLUMN_NAME_ITEMID);
    	projectionMap.put(COLUMN_NAME_UNIT, COLUMN_NAME_UNIT);
    	projectionMap.put(COLUMN_NAME_GRADE, COLUMN_NAME_GRADE);
    	projectionMap.put(COLUMN_NAME_STDPRICE, COLUMN_NAME_STDPRICE);
    	projectionMap.put(COLUMN_NAME_CASHPRICE, COLUMN_NAME_CASHPRICE);
    	projectionMap.put(COLUMN_NAME_DISCRATE, COLUMN_NAME_DISCRATE);
    	projectionMap.put(COLUMN_NAME_LOWESTSPRICE, COLUMN_NAME_LOWESTSPRICE);
    	projectionMap.put(COLUMN_NAME_LOWESTCPRICE, COLUMN_NAME_LOWESTCPRICE);
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
	    		COLUMN_NAME_ITEMID+" INTEGER,"+
	    		COLUMN_NAME_UNIT+" TEXT,"+
	    		COLUMN_NAME_GRADE+" TEXT,"+
	    		COLUMN_NAME_STDPRICE+" REAL,"+
	    		COLUMN_NAME_CASHPRICE+" REAL,"+
	    		COLUMN_NAME_DISCRATE+" REAL,"+
	    		COLUMN_NAME_LOWESTSPRICE+" REAL,"+
	    		COLUMN_NAME_LOWESTCPRICE+" REAL,"+
	    		COLUMN_NAME_VERSIONNO+" TEXT);";
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+
		    			COLUMN_NAME_COMPANYID+","+COLUMN_NAME_ITEMID+","+COLUMN_NAME_UNIT+","+COLUMN_NAME_GRADE+");",
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
	private int itemID;
	private String unit;
	private String grade;
	private double stdPrice;
	private double cashPrice;
	private double discRate;
	private Double lowestSPrice;
	private Double lowestCPrice;
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

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public double getStdPrice() {
		return stdPrice;
	}

	public void setStdPrice(double stdPrice) {
		this.stdPrice = stdPrice;
	}

	public double getCashPrice() {
		return cashPrice;
	}

	public void setCashPrice(double cashPrice) {
		this.cashPrice = cashPrice;
	}

	public double getDiscRate() {
		return discRate;
	}

	public void setDiscRate(double discRate) {
		this.discRate = discRate;
	}

	public Double getLowestSPrice() {
		return lowestSPrice;
	}

	public void setLowestSPrice(Double lowestSPrice) {
		this.lowestSPrice = lowestSPrice;
	}

	public Double getLowestCPrice() {
		return lowestCPrice;
	}

	public void setLowestCPrice(Double lowestCPrice) {
		this.lowestCPrice = lowestCPrice;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	
}
