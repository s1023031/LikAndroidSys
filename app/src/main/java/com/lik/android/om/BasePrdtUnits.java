package com.lik.android.om;

import java.util.HashMap;

public abstract class BasePrdtUnits extends BaseOM<PrdtUnits> {

	private static final long serialVersionUID = 3011787724434027413L;

	public static final String TABLE_NAME = "PrdtUnits";

	public static final String TABLE_CH_NAME = "產品買賣單位資料";

	/**
     * Column name for 序號 of the PrdtUnits
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for 使用單位流水號 of the PrdtUnits
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";

    /**
     * Column name for 產品流水號 of the PrdtUnits
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ITEMID = "ItemID";

    /**
     * Column name for 買賣單位 of the PrdtUnits
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UNIT = "Unit";

    /**
     * Column name for 條碼編號 of the PrdtUnits
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_BARCODE = "BarCode";

    /**
     * Column name for packages of the PrdtUnits
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PACKAGES = "Packages";

    /**
     * Column name for 單位售價(月結) of the PrdtUnits
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SALEPRICE = "SalePrice";

    /**
     * Column name for 單位建議售價 of the PrdtUnits
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SUGGESTPRICE = "SuggestPrice";

    /**
     * Column name for 最低售價 of the PrdtUnits
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_LOWESTPRICE = "LowestPrice";

    /**
     * Column name for 單位售價(現金) of the PrdtUnits
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CASHPRICE = "CashPrice";

    /**
     * Column name for 與主單位換算率 of the PrdtUnits
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_RATIO = "Ratio";

    /**
     * Column name for 版本 of the PrdtUnits
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_VERSIONNO = "VersionNo";

    /**
     * Column name for 現金最低售價 of the PrdtUnits
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CASHLOWESTPRICE = "CashLowestPrice";

    /**
     * Column name for 業代成本 of the PrdtUnits
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SALECOST = "SaleCost";

    /**
     * Column name for LowestSPrice of the PrdtPrice
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_LOWESTSPRICE = "LowestSPrice";

    /**
     * Column name for LowestCPrice of the PrdtPrice
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_LOWESTCPRICE = "LowestCPrice";
    
//    private String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
//    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
//    		COLUMN_NAME_COMPANYID+" INTEGER,"+
//    		COLUMN_NAME_ITEMID+" INTEGER,"+
//    		COLUMN_NAME_UNIT+" TEXT,"+
//    		COLUMN_NAME_BARCODE+" TEXT,"+
//    		COLUMN_NAME_PACKAGES+" INTEGER,"+
//    		COLUMN_NAME_SALEPRICE+" REAL,"+
//    		COLUMN_NAME_SUGGESTPRICE+" REAL,"+
//    		COLUMN_NAME_LOWESTPRICE+" REAL,"+
//    		COLUMN_NAME_CASHPRICE+" REAL,"+
//    		COLUMN_NAME_VERSIONNO+" TEXT);";
//
//    private String[] CREATE_INDEX_CMD = {
//    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+
//    			COLUMN_NAME_COMPANYID+","+COLUMN_NAME_ITEMID+","+COLUMN_NAME_UNIT+");",
//    };
//    
//    private String DROP_CMD = "DROP TABLE IF EXISTS "+getTableName();

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_PRDTUNITS_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_COMPANYID,  // Projection position 1, 
    	COLUMN_NAME_ITEMID, // Projection position 2, 
    	COLUMN_NAME_UNIT, // Projection position 3, 
    	COLUMN_NAME_BARCODE, // Projection position 4, 
    	COLUMN_NAME_PACKAGES, // Projection position 5, 
    	COLUMN_NAME_SALEPRICE, // Projection position 6, 
    	COLUMN_NAME_SUGGESTPRICE, // Projection position 7, 
    	COLUMN_NAME_LOWESTPRICE, // Projection position 8, 
    	COLUMN_NAME_CASHPRICE, // Projection position 9, 
    	COLUMN_NAME_RATIO, // Projection position 10, 
    	COLUMN_NAME_VERSIONNO, // Projection position 11, 
    	COLUMN_NAME_CASHLOWESTPRICE, // Projection position 12, 
    	COLUMN_NAME_SALECOST, // Projection position 13, 
    	COLUMN_NAME_LOWESTSPRICE, // Projection position 14, 
    	COLUMN_NAME_LOWESTCPRICE, // Projection position 15, 
   };
    protected static final int READ_PRDTUNITS_SERIALID_INDEX = 0;
    protected static final int READ_PRDTUNITS_COMPANYID_INDEX = 1;
    protected static final int READ_PRDTUNITS_ITEMID_INDEX = 2;
    protected static final int READ_PRDTUNITS_UNIT_INDEX = 3;
    protected static final int READ_PRDTUNITS_BARCODE_INDEX = 4;
    protected static final int READ_PRDTUNITS_PACKAGES_INDEX = 5;
    protected static final int READ_PRDTUNITS_SALEPRICE_INDEX = 6;
    protected static final int READ_PRDTUNITS_SUGGESTPRICE_INDEX = 7;
    protected static final int READ_PRDTUNITS_LOWESTPRICE = 8;
    protected static final int READ_PRDTUNITS_CASHPRICE_INDEX = 9;
    protected static final int READ_PRDTUNITS_RATIO_INDEX = 10;
    protected static final int READ_PRDTUNITS_VERSIONNO_INDEX = 11;
    protected static final int READ_PRDTUNITS_CASHLOWESTPRICE_INDEX = 12;
    protected static final int READ_PRDTUNITS_SALECOST_INDEX = 13;
    protected static final int READ_PRDTUNITS_LOWESTSPRICE_INDEX = 14;
    protected static final int READ_PRDTUNITS_LOWESTCPRICE_INDEX = 15;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BasePrdtUnits() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
    	projectionMap.put(COLUMN_NAME_ITEMID, COLUMN_NAME_ITEMID);
    	projectionMap.put(COLUMN_NAME_UNIT, COLUMN_NAME_UNIT);
    	projectionMap.put(COLUMN_NAME_BARCODE, COLUMN_NAME_BARCODE);
    	projectionMap.put(COLUMN_NAME_PACKAGES, COLUMN_NAME_PACKAGES);
    	projectionMap.put(COLUMN_NAME_SALEPRICE, COLUMN_NAME_SALEPRICE);
    	projectionMap.put(COLUMN_NAME_SUGGESTPRICE, COLUMN_NAME_SUGGESTPRICE);
    	projectionMap.put(COLUMN_NAME_LOWESTPRICE, COLUMN_NAME_LOWESTPRICE);
    	projectionMap.put(COLUMN_NAME_CASHPRICE, COLUMN_NAME_CASHPRICE);
    	projectionMap.put(COLUMN_NAME_RATIO, COLUMN_NAME_RATIO);
    	projectionMap.put(COLUMN_NAME_VERSIONNO, COLUMN_NAME_VERSIONNO);
    	projectionMap.put(COLUMN_NAME_CASHLOWESTPRICE, COLUMN_NAME_CASHLOWESTPRICE);
    	projectionMap.put(COLUMN_NAME_SALECOST, COLUMN_NAME_SALECOST);
    	projectionMap.put(COLUMN_NAME_LOWESTSPRICE, COLUMN_NAME_LOWESTSPRICE);
    	projectionMap.put(COLUMN_NAME_LOWESTCPRICE, COLUMN_NAME_LOWESTCPRICE);
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
	    		COLUMN_NAME_BARCODE+" TEXT,"+
	    		COLUMN_NAME_PACKAGES+" INTEGER,"+
	    		COLUMN_NAME_SALEPRICE+" REAL,"+
	    		COLUMN_NAME_SUGGESTPRICE+" REAL,"+
	    		COLUMN_NAME_LOWESTPRICE+" REAL,"+
	    		COLUMN_NAME_CASHPRICE+" REAL,"+
	    		COLUMN_NAME_RATIO+" REAL,"+
	    		COLUMN_NAME_VERSIONNO+" TEXT,"+
	    		COLUMN_NAME_CASHLOWESTPRICE+" REAL,"+
	    		COLUMN_NAME_SALECOST+" REAL,"+
	    		COLUMN_NAME_LOWESTSPRICE+" REAL,"+
	    		COLUMN_NAME_LOWESTCPRICE+" REAL);";
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+
		    			COLUMN_NAME_COMPANYID+","+COLUMN_NAME_ITEMID+","+COLUMN_NAME_UNIT+");",
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
	private String barCode;
	private int packages;
	private double salePrice;
	private double suggestPrice;
	private double lowestPrice;
	private double cashPrice;
	private double ratio;
	private String versionNo;
	private double cashLowestPrice;
	private double SaleCost;
	private Double lowestSPrice;
	private Double lowestCPrice;

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

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public int getPackages() {
		return packages;
	}

	public void setPakages(int packages) {
		this.packages = packages;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public double getSuggestPrice() {
		return suggestPrice;
	}

	public void setSuggestPrice(double suggestPrice) {
		this.suggestPrice = suggestPrice;
	}

	public double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public double getCashPrice() {
		return cashPrice;
	}

	public void setCashPrice(double cashPrice) {
		this.cashPrice = cashPrice;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public double getCashLowestPrice() {
		return cashLowestPrice;
	}

	public void setCashLowestPrice(double cashLowestPrice) {
		this.cashLowestPrice = cashLowestPrice;
	}

	public double getSaleCost() {
		return SaleCost;
	}

	public void setSaleCost(double saleCost) {
		SaleCost = saleCost;
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

	
}
