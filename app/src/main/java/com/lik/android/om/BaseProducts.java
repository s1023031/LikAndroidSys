package com.lik.android.om;

import java.util.HashMap;

public abstract class BaseProducts extends BaseOM<Products> {

	private static final long serialVersionUID = -8789560028590963554L;

	public static final String TABLE_NAME = "Products";

	public static final String TABLE_CH_NAME = "產品資料";
	
	public static final String COLUMN_NAME_NEWPRODUCT_Y = "Y";
	public static final String COLUMN_NAME_NEWPRODUCT_N = "N";
	public static final String COLUMN_NAME_EMPTYSTOCK_Y = "Y";
	public static final String COLUMN_NAME_EMPTYSTOCK_N = "N";
	public static final String COLUMN_NAME_NORETURN_Y = "Y";
	public static final String COLUMN_NAME_NORETURN_N = "N";
	public static final int COLUMN_NAME_KIND_4 = 4; // 表示停售
	public static final int ORDERBY_ITEMNO = 0;
	public static final int ORDERBY_SUBCLASSIFY_ITEMNO = 1;

	/**
     * Column name for 序號 of the Products
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for 公司流水號 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";

    /**
     * Column name for 產品流水號 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ITEMID = "ItemID";

    /**
     * Column name for 產品代號 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ITEMNO = "ItemNO";

    /**
     * Column name for 品    名 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ITEMNM = "ItemNM";

    /**
     * Column name for 規    格 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DIMENSION = "Dimension";

    /**
     * Column name for 點貨單位 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UNIT = "Unit";

    /**
     * Column name for 點貨單位 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UNIT1 = "Unit1";

    /**
     * Column name for 點貨單位 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UNIT2 = "Unit2";

    /**
     * Column name for 點貨單位 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UNIT3 = "Unit3";

    /**
     * Column name for 點貨單位 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UNIT4 = "Unit4";

    /**
     * Column name for 點貨單位 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UNIT5 = "Unit5";

    /**
     * Column name for 入    數 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PIECE = "Piece";

    /**
     * Column name for Unit1與主單位換算率 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_RATIO1 = "Ratio1";

    /**
     * Column name for Unit2與主單位換算率 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_RATIO2 = "Ratio2";

    /**
     * Column name for Unit3與主單位換算率 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_RATIO3 = "Ratio3";

    /**
     * Column name for 產品分類 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CLASSIFY = "Classify";

    /**
     * Column name for 廠商代號 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SUPLNO = "SuplNO";

    /**
     * Column name for 主單位條碼 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_BARCODE = "BarCode";

    /**
     * Column name for 主單位月結售價 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SALEPRICE = "SalePrice";

    /**
     * Column name for 主單位建議售價 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SUGGESTPRICE = "SuggestPrice";

    /**
     * Column name for 銷售別 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_KIND = "Kind";

    /**
     * Column name for 新品 Y/N of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_NEWPRODUCT = "NewProduct";

    /**
     * Column name for 無退 Y/N of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_NORETURN = "NoReturn";

    /**
     * Column name for 可追溯天數 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SELLDAYS = "SellDays";

    /**
     * Column name for 即時庫存 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_REALSTOCK = "RealStock";

    /**
     * Column name for 備註 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_REMARK = "Remark";

    /**
     * Column name for 版本 of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_VERSIONNO = "VersionNo";

    /**
     * Column name for SubClassify of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SUBCLASSIFY = "SubClassify";

    /**
     * Column name for SellMultiple of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SELLMULTIPLE = "SellMultiple";
    
    /**
     * Column name for StockQty of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_STOCKQTY = "StockQty";
    
    /**
     * Column name for StockQty of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CUBICMEASURE = "CubicMeasure";
    
    /**
     * Column name for PrdtUnitWeight of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PRDTUNITWEIGHT = "PrdtUnitWeight";
    
    /**
     * Column name for WeightUnit of the Products
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_WEIGHTUNIT = "WeightUnit";
    
//    private String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
//    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
//    		COLUMN_NAME_COMPANYID+" INTEGER,"+
//    		COLUMN_NAME_ITEMID+" INTEGER,"+
//    		COLUMN_NAME_ITEMNO+" TEXT,"+
//    		COLUMN_NAME_ITEMNM+" TEXT,"+
//    		COLUMN_NAME_DIMENSION+" TEXT,"+
//    		COLUMN_NAME_UNIT+" TEXT,"+
//    		COLUMN_NAME_UNIT1+" TEXT,"+
//    		COLUMN_NAME_UNIT2+" TEXT,"+
//    		COLUMN_NAME_UNIT3+" TEXT,"+
//    		COLUMN_NAME_UNIT4+" TEXT,"+
//    		COLUMN_NAME_UNIT5+" TEXT,"+
//    		COLUMN_NAME_PIECE+" REAL,"+
//    		COLUMN_NAME_RATIO1+" REAL,"+
//    		COLUMN_NAME_RATIO2+" REAL,"+
//    		COLUMN_NAME_RATIO3+" REAL,"+
//    		COLUMN_NAME_CLASSIFY+" TEXT,"+
//    		COLUMN_NAME_SUPLNO+" TEXT,"+
//    		COLUMN_NAME_BARCODE+" TEXT,"+
//    		COLUMN_NAME_SALEPRICE+" REAL,"+
//    		COLUMN_NAME_SUGGESTPRICE+" REAL,"+
//    		COLUMN_NAME_KIND+" INTEGER,"+
//    		COLUMN_NAME_NEWPRODUCT+" TEXT,"+
//    		COLUMN_NAME_NORETURN+" TEXT,"+
//    		COLUMN_NAME_EMPTYSTOCK+" TEXT,"+
//    		COLUMN_NAME_VERSIONNO+" TEXT);";
//
//    private String[] CREATE_INDEX_CMD = {
//    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+
//    			COLUMN_NAME_COMPANYID+","+COLUMN_NAME_ITEMID+");",
//    };
//    
//    private String DROP_CMD = "DROP TABLE IF EXISTS "+getTableName();

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_PRODUCTS_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_COMPANYID,  // Projection position 1, 
    	COLUMN_NAME_ITEMID, // Projection position 2, 
    	COLUMN_NAME_ITEMNO, // Projection position 3, 
    	COLUMN_NAME_ITEMNM, // Projection position 4, 
    	COLUMN_NAME_DIMENSION, // Projection position 5, 
    	COLUMN_NAME_UNIT, // Projection position 6, 
    	COLUMN_NAME_UNIT1, // Projection position 7, 
    	COLUMN_NAME_UNIT2, // Projection position 8, 
    	COLUMN_NAME_UNIT3, // Projection position 9, 
    	COLUMN_NAME_UNIT4, // Projection position 10, 
    	COLUMN_NAME_UNIT5, // Projection position 11, 
    	COLUMN_NAME_PIECE, // Projection position 12, 
    	COLUMN_NAME_RATIO1, // Projection position 13, 
    	COLUMN_NAME_RATIO2, // Projection position 14, 
    	COLUMN_NAME_RATIO3, // Projection position 15, 
    	COLUMN_NAME_CLASSIFY, // Projection position 16, 
    	COLUMN_NAME_SUPLNO, // Projection position 17, 
    	COLUMN_NAME_BARCODE, // Projection position 18, 
    	COLUMN_NAME_SALEPRICE, // Projection position 19, 
    	COLUMN_NAME_SUGGESTPRICE, // Projection position 20, 
    	COLUMN_NAME_KIND, // Projection position 21, 
    	COLUMN_NAME_NEWPRODUCT, // Projection position 22, 
    	COLUMN_NAME_NORETURN, // Projection position 23, 
    	COLUMN_NAME_SELLDAYS, // Projection position 24, 
    	COLUMN_NAME_REALSTOCK, // Projection position 25, 
    	COLUMN_NAME_REMARK, // Projection position 26, 
    	COLUMN_NAME_VERSIONNO, // Projection position 27, 
    	COLUMN_NAME_SUBCLASSIFY, // Projection position 28, 
    	COLUMN_NAME_SELLMULTIPLE, // Projection position 29, 
    	COLUMN_NAME_STOCKQTY, // Projection position 30, 
    	COLUMN_NAME_CUBICMEASURE, // Projection position 31, 
    	COLUMN_NAME_PRDTUNITWEIGHT, // Projection position 32, 
    	COLUMN_NAME_WEIGHTUNIT // Projection position 33, 
    };
    protected static final int READ_PRODUCTS_SERIALID_INDEX = 0;
    protected static final int READ_PRODUCTS_COMPANYID_INDEX = 1;
    protected static final int READ_PRODUCTS_ITEMID_INDEX = 2;
    protected static final int READ_PRODUCTS_ITEMNO_INDEX = 3;
    protected static final int READ_PRODUCTS_ITEMNM_INDEX = 4;
    protected static final int READ_PRODUCTS_DIMENSION_INDEX = 5;
    protected static final int READ_PRODUCTS_UNIT_INDEX = 6;
    protected static final int READ_PRODUCTS_UNIT1_INDEX = 7;
    protected static final int READ_PRODUCTS_UNIT2_INDEX = 8;
    protected static final int READ_PRODUCTS_UNIT3_INDEX = 9;
    protected static final int READ_PRODUCTS_UNIT4_INDEX = 10;
    protected static final int READ_PRODUCTS_UNIT5_INDEX = 11;
    protected static final int READ_PRODUCTS_PIECE_INDEX = 12;
    protected static final int READ_PRODUCTS_RATIO1_INDEX = 13;
    protected static final int READ_PRODUCTS_RATIO2_INDEX = 14;
    protected static final int READ_PRODUCTS_RATIO3_INDEX = 15;
    protected static final int READ_PRODUCTS_CLASSIFY_INDEX = 16;
    protected static final int READ_PRODUCTS_SUPLNO_INDEX = 17;
    protected static final int READ_PRODUCTS_BARCODE_INDEX = 18;
    protected static final int READ_PRODUCTS_SALEPRICE_INDEX = 19;
    protected static final int READ_PRODUCTS_SUGGESTPRICE_INDEX = 20;
    protected static final int READ_PRODUCTS_KIND_INDEX = 21;
    protected static final int READ_PRODUCTS_NEWPRODUCT_INDEX = 22;
    protected static final int READ_PRODUCTS_NORETURN_INDEX = 23;
    protected static final int READ_PRODUCTS_SELLDAYS_INDEX = 24;
    protected static final int READ_PRODUCTS_REALSTOCK_INDEX = 25;
    protected static final int READ_PRODUCTS_REMARK_INDEX = 26;
    protected static final int READ_PRODUCTS_VERSIONNO_INDEX = 27;
    protected static final int READ_PRODUCTS_SUBCLASSIFY_INDEX = 28;
    protected static final int READ_PRODUCTS_SELLMULTIPLE_INDEX = 29;
    protected static final int READ_PRODUCTS_STOCKQTY_INDEX = 30;
    protected static final int READ_PRODUCTS_CUBICMEASURE_INDEX = 31;
    protected static final int READ_PRODUCTS_PRDTUNITWEIGHT_INDEX = 32;
    protected static final int READ_PRODUCTS_WEIGHTUNIT_INDEX = 33;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseProducts() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
    	projectionMap.put(COLUMN_NAME_ITEMID, COLUMN_NAME_ITEMID);
    	projectionMap.put(COLUMN_NAME_ITEMNO, COLUMN_NAME_ITEMNO);
    	projectionMap.put(COLUMN_NAME_ITEMNM, COLUMN_NAME_ITEMNM);
    	projectionMap.put(COLUMN_NAME_DIMENSION, COLUMN_NAME_DIMENSION);
    	projectionMap.put(COLUMN_NAME_UNIT, COLUMN_NAME_UNIT);
    	projectionMap.put(COLUMN_NAME_UNIT1, COLUMN_NAME_UNIT1);
    	projectionMap.put(COLUMN_NAME_UNIT2, COLUMN_NAME_UNIT2);
    	projectionMap.put(COLUMN_NAME_UNIT3, COLUMN_NAME_UNIT3);
    	projectionMap.put(COLUMN_NAME_UNIT4, COLUMN_NAME_UNIT4);
    	projectionMap.put(COLUMN_NAME_UNIT5, COLUMN_NAME_UNIT5);
    	projectionMap.put(COLUMN_NAME_PIECE, COLUMN_NAME_PIECE);
    	projectionMap.put(COLUMN_NAME_RATIO1, COLUMN_NAME_RATIO1);
    	projectionMap.put(COLUMN_NAME_RATIO2, COLUMN_NAME_RATIO2);
    	projectionMap.put(COLUMN_NAME_RATIO3, COLUMN_NAME_RATIO3);
    	projectionMap.put(COLUMN_NAME_CLASSIFY, COLUMN_NAME_CLASSIFY);
    	projectionMap.put(COLUMN_NAME_SUPLNO, COLUMN_NAME_SUPLNO);
    	projectionMap.put(COLUMN_NAME_BARCODE, COLUMN_NAME_BARCODE);
    	projectionMap.put(COLUMN_NAME_SALEPRICE, COLUMN_NAME_SALEPRICE);
    	projectionMap.put(COLUMN_NAME_SUGGESTPRICE, COLUMN_NAME_SUGGESTPRICE);
    	projectionMap.put(COLUMN_NAME_KIND, COLUMN_NAME_KIND);
    	projectionMap.put(COLUMN_NAME_NEWPRODUCT, COLUMN_NAME_NEWPRODUCT);
    	projectionMap.put(COLUMN_NAME_NORETURN, COLUMN_NAME_NORETURN);
    	projectionMap.put(COLUMN_NAME_SELLDAYS, COLUMN_NAME_SELLDAYS);
    	projectionMap.put(COLUMN_NAME_REALSTOCK, COLUMN_NAME_REALSTOCK);
    	projectionMap.put(COLUMN_NAME_REMARK, COLUMN_NAME_REMARK);
    	projectionMap.put(COLUMN_NAME_VERSIONNO, COLUMN_NAME_VERSIONNO);
    	projectionMap.put(COLUMN_NAME_SUBCLASSIFY, COLUMN_NAME_SUBCLASSIFY);
    	projectionMap.put(COLUMN_NAME_SELLMULTIPLE, COLUMN_NAME_SELLMULTIPLE);
    	projectionMap.put(COLUMN_NAME_STOCKQTY, COLUMN_NAME_STOCKQTY);
    	projectionMap.put(COLUMN_NAME_CUBICMEASURE, COLUMN_NAME_CUBICMEASURE);
    	projectionMap.put(COLUMN_NAME_PRDTUNITWEIGHT, COLUMN_NAME_PRDTUNITWEIGHT);
    	projectionMap.put(COLUMN_NAME_WEIGHTUNIT, COLUMN_NAME_WEIGHTUNIT);
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
	    		COLUMN_NAME_ITEMNO+" TEXT,"+
	    		COLUMN_NAME_ITEMNM+" TEXT,"+
	    		COLUMN_NAME_DIMENSION+" TEXT,"+
	    		COLUMN_NAME_UNIT+" TEXT,"+
	    		COLUMN_NAME_UNIT1+" TEXT,"+
	    		COLUMN_NAME_UNIT2+" TEXT,"+
	    		COLUMN_NAME_UNIT3+" TEXT,"+
	    		COLUMN_NAME_UNIT4+" TEXT,"+
	    		COLUMN_NAME_UNIT5+" TEXT,"+
	    		COLUMN_NAME_PIECE+" REAL,"+
	    		COLUMN_NAME_RATIO1+" REAL,"+
	    		COLUMN_NAME_RATIO2+" REAL,"+
	    		COLUMN_NAME_RATIO3+" REAL,"+
	    		COLUMN_NAME_CLASSIFY+" TEXT,"+
	    		COLUMN_NAME_SUPLNO+" TEXT,"+
	    		COLUMN_NAME_BARCODE+" TEXT,"+
	    		COLUMN_NAME_SALEPRICE+" REAL,"+
	    		COLUMN_NAME_SUGGESTPRICE+" REAL,"+
	    		COLUMN_NAME_KIND+" INTEGER,"+
	    		COLUMN_NAME_NEWPRODUCT+" TEXT,"+
	    		COLUMN_NAME_NORETURN+" TEXT,"+
	    		COLUMN_NAME_SELLDAYS+" REAL,"+
	    		COLUMN_NAME_REALSTOCK+" TEXT,"+
	    		COLUMN_NAME_REMARK+" TEXT,"+
	    		COLUMN_NAME_VERSIONNO+" TEXT,"+
	    		COLUMN_NAME_SUBCLASSIFY+" TEXT,"+
	    		COLUMN_NAME_SELLMULTIPLE+" TEXT,"+
	    		COLUMN_NAME_STOCKQTY+" REAL,"+
	    		COLUMN_NAME_CUBICMEASURE+" REAL,"+
	    		COLUMN_NAME_PRDTUNITWEIGHT+" REAL,"+
	    		COLUMN_NAME_WEIGHTUNIT+" TEXT);";
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+
		    			COLUMN_NAME_COMPANYID+","+COLUMN_NAME_ITEMID+");",
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
	private String itemNO;
	private String itemNM;
	private String dimension;
	private String unit;
	private String unit1;
	private String unit2;
	private String unit3;
	private String unit4;
	private String unit5;
	private double piece;
	private double ratio1;
	private double ratio2;
	private double ratio3;
	private String classify;
	private String suplNO;
	private String barCode;
	private double salePrice;
	private double suggestPrice;
	private int kind;
	private String newProduct;
	private String noReturn;
	private double sellDays;
	private String realStock;
	private String remark;
	private String versionNo;
	private String subClassify;
	private double sellMultiple;
	private int order; // 0: default(itemNo) 1:subClassify+itemNo
	private double stockQty;
	private double cubicMeasure;
    private double prdtUnitWeight;
    private String weightUnit;

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

	public String getItemNO() {
		return itemNO;
	}

	public void setItemNO(String itemNO) {
		this.itemNO = itemNO;
	}

	public String getItemNM() {
		return itemNM;
	}

	public void setItemNM(String itemNM) {
		this.itemNM = itemNM;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnit1() {
		return unit1;
	}

	public void setUnit1(String unit1) {
		this.unit1 = unit1;
	}

	public String getUnit2() {
		return unit2;
	}

	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}

	public String getUnit3() {
		return unit3;
	}

	public void setUnit3(String unit3) {
		this.unit3 = unit3;
	}

	public String getUnit4() {
		return unit4;
	}

	public void setUnit4(String unit4) {
		this.unit4 = unit4;
	}

	public String getUnit5() {
		return unit5;
	}

	public void setUnit5(String unit5) {
		this.unit5 = unit5;
	}

	public double getPiece() {
		return piece;
	}

	public void setPiece(double piece) {
		this.piece = piece;
	}

	public double getRatio1() {
		return ratio1;
	}

	public void setRatio1(double ratio1) {
		this.ratio1 = ratio1;
	}

	public double getRatio2() {
		return ratio2;
	}

	public void setRatio2(double ratio2) {
		this.ratio2 = ratio2;
	}

	public double getRatio3() {
		return ratio3;
	}

	public void setRatio3(double ratio3) {
		this.ratio3 = ratio3;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getSuplNO() {
		return suplNO;
	}

	public void setSuplNO(String suplNO) {
		this.suplNO = suplNO;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
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

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getNewProduct() {
		return newProduct;
	}

	public void setNewProduct(String newProduct) {
		this.newProduct = newProduct;
	}

	public String getNoReturn() {
		return noReturn;
	}

	public void setNoReturn(String noReturn) {
		this.noReturn = noReturn;
	}

	public double getSellDays() {
		return sellDays;
	}

	public void setSellDays(double sellDays) {
		this.sellDays = sellDays;
	}

	public String getRealStock() {
		return realStock;
	}

	public void setRealStock(String realStock) {
		this.realStock = realStock;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getSubClassify() {
		return subClassify;
	}

	public void setSubClassify(String subClassify) {
		this.subClassify = subClassify;
	}

	public double getSellMultiple() {
		return sellMultiple;
	}

	public void setSellMultiple(double sellMultiple) {
		this.sellMultiple = sellMultiple;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public double getStockQty() {
		return stockQty;
	}

	public void setStockQty(double stockQty) {
		this.stockQty = stockQty;
	}

	public double getCubicMeasure() {
		return cubicMeasure;
	}

	public void setCubicMeasure(double cubicMeasure) {
		this.cubicMeasure = cubicMeasure;
	}

	public double getPrdtUnitWeight() {
		return prdtUnitWeight;
	}

	public void setPrdtUnitWeight(double prdtUnitWeight) {
		this.prdtUnitWeight = prdtUnitWeight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	
}
