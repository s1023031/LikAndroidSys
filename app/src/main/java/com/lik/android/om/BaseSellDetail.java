package com.lik.android.om;

import java.util.Date;
import java.util.HashMap;

public abstract class BaseSellDetail extends BaseOM<SellDetail> {

	private static final long serialVersionUID = -5059578284220836106L;

	public static final String TABLE_NAME = "SellDetail";

	public static final String TABLE_CH_NAME = "客戶最近交易資料";

	/**
     * Column name for 序號 of the SellDetail
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for 使用單位流水號-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";

    /**
     * Column name for 客戶流水號-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CUSTOMERID = "CustomerID";

    /**
     * Column name for 銷售日-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SELLDATE = "SellDate";

    /**
     * Column name for 產品代號-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ITEMNO = "ItemNo";

    /**
     * Column name for 序號-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SEQ = "Seq";

    /**
     * Column name for 出貨流水號 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SELLID = "SellID";

    /**
     * Column name for 出貨別-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SELLKIND = "SellKind";

    /**
     * Column name for 出貨/進貨-V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SKIND = "SKind";

    /**
     * Column name for 結帳方式-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SELLPAYTYPE = "SellPayType";

    /**
     * Column name for 產品名稱-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PRDTNAME = "PrdtName";

    /**
     * Column name for 數量單位(大)-V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UNIT1 = "Unit1";

    /**
     * Column name for 數量單位(中)-V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UNIT2 = "Unit2";

    /**
     * Column name for 數量單位(小)-V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UNIT3 = "Unit3";

    /**
     * Column name for 訂購數量(大)-V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SELLQTY1 = "SellQty1";

    /**
     * Column name for 訂購數量(中)-V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SELLQTY2 = "SellQty2";

    /**
     * Column name for 訂購數量(小)-V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SELLQTY3 = "SellQty3";

    /**
     * Column name for 贈送數量(大)-V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SENDQTY1 = "SendQty1";

    /**
     * Column name for 贈送數量(中)-V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SENDQTY2 = "SendQty2";

    /**
     * Column name for 贈送數量(小)-V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SENDQTY3 = "SendQty3";

    /**
     * Column name for 自贈數量(大)-V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_GIVEQTY1 = "GiveQty1";

    /**
     * Column name for 自贈數量(中)-V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_GIVEQTY2 = "GiveQty2";

    /**
     * Column name for 自贈數量(小)-V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_GIVEQTY3 = "GiveQty3";

    /**
     * Column name for 備註-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_NOTE = "Note"; // 2012/8/15 added

    /**
     * Column name for 1.售 2.搭 3.贈-V2 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DEALKIND = "DealKind";

    /**
     * Column name for 數量-V2 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_QUANTITY = "Quantity";

    /**
     * Column name for 數量單位-V2 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_QTUNIT = "QTUnit";

    /**
     * Column name for 單價單位-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PRICEUNIT = "PriceUnit";

    /**
     * Column name for 單價-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UPRICE = "UPrice";

    /**
     * Column name for 單價單位1-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PRICEUNIT1 = "PriceUnit1";

    /**
     * Column name for 單價1-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UPRICE1 = "UPrice1";

    /**
     * Column name for 折扣率 %-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DISCRATE = "DiscRate";

    /**
     * Column name for Y/N-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PRICETOOLOW = "PriceTooLow";

    /**
     * Column name for 0/1-V2/V3 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PRICEMARK = "PriceMark";

    /**
     * Column name for CustDelverViewOrder of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CUSTDELIVERVIEWORDER = "CustDelverViewOrder";

    /**
     * Column name for 版本 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_VERSIONNO = "VersionNo";

    /**
     * Column name for 版本 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SENDDATE = "SendDate";

    /**
     * Column name for 版本 of the SellDetail
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ORDERDATE = "OrderDate";
    
    public static final String COLUMN_NAME_SELLTITLE = "SellTitle"; //HAO 104.03.09
    
//    private String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
//    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
//    		COLUMN_NAME_COMPANYID+" INTEGER,"+
//    		COLUMN_NAME_CUSTOMERID+" INTEGER,"+
//    		COLUMN_NAME_SELLDATE+" TEXT,"+
//    		COLUMN_NAME_ITEMNO+" TEXT,"+
//    		COLUMN_NAME_SEQ+" INTEGER,"+
//    		COLUMN_NAME_SELLID+" INTEGER,"+
//    		COLUMN_NAME_SELLKIND+" INTEGER,"+
//    		COLUMN_NAME_SKIND+" TEXT,"+
//    		COLUMN_NAME_SELLPAYTYPE+" TEXT,"+
//    		COLUMN_NAME_PRDTNAME+" TEXT,"+
//    		COLUMN_NAME_UNIT1+" TEXT,"+
//    		COLUMN_NAME_UNIT2+" TEXT,"+
//    		COLUMN_NAME_UNIT3+" TEXT,"+
//    		COLUMN_NAME_SELLQTY1+" REAL,"+
//    		COLUMN_NAME_SELLQTY2+" REAL,"+
//    		COLUMN_NAME_SELLQTY3+" REAL,"+
//    		COLUMN_NAME_SENDQTY1+" REAL,"+
//    		COLUMN_NAME_SENDQTY2+" REAL,"+
//    		COLUMN_NAME_SENDQTY3+" REAL,"+
//    		COLUMN_NAME_GIVEQTY1+" REAL,"+
//    		COLUMN_NAME_GIVEQTY2+" REAL,"+
//    		COLUMN_NAME_GIVEQTY3+" REAL,"+
//    		COLUMN_NAME_DEALKIND+" INTEGER,"+
//    		COLUMN_NAME_QUANTITY+" REAL,"+
//    		COLUMN_NAME_QTUNIT+" TEXT,"+
//    		COLUMN_NAME_PRICEUNIT+" TEXT,"+
//    		COLUMN_NAME_UPRICE+" REAL,"+
//    		COLUMN_NAME_PRICEUNIT1+" TEXT,"+
//    		COLUMN_NAME_UPRICE1+" REAL,"+
//    		COLUMN_NAME_DISCRATE+" REAL,"+
//    		COLUMN_NAME_PRICETOOLOW+" TEXT,"+
//    		COLUMN_NAME_VERSIONNO+" TEXT);";
//
//    private String[] CREATE_INDEX_CMD = {
//    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+COLUMN_NAME_COMPANYID+");",
//    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P2 ON "+getTableName()+" ("+COLUMN_NAME_CUSTOMERID+");",
//    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P3 ON "+getTableName()+" ("+COLUMN_NAME_SELLDATE+");",
//        "CREATE  INDEX IF NOT EXISTS "+getTableName()+"P4 ON "+getTableName()+" ("+COLUMN_NAME_ITEMNO+");",
//        "CREATE  INDEX IF NOT EXISTS "+getTableName()+"P5 ON "+getTableName()+
//    		" ("+COLUMN_NAME_COMPANYID+","+COLUMN_NAME_CUSTOMERID+","+
//    		COLUMN_NAME_SELLDATE+","+COLUMN_NAME_ITEMNO+","+COLUMN_NAME_SEQ+","+COLUMN_NAME_SELLID+");",
//
//    };
//    
//    private String DROP_CMD = "DROP TABLE IF EXISTS "+getTableName();

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_SELLDETAIL_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_COMPANYID,  // Projection position 1, 
    	COLUMN_NAME_CUSTOMERID, // Projection position 2, 
    	COLUMN_NAME_SELLDATE, // Projection position 3, 
    	COLUMN_NAME_ITEMNO, // Projection position 4, 
    	COLUMN_NAME_SEQ, // Projection position 5, 
    	COLUMN_NAME_SELLID, // Projection position 6, 
    	COLUMN_NAME_SELLKIND, // Projection position 7, 
    	COLUMN_NAME_SKIND, // Projection position 8, 
    	COLUMN_NAME_SELLPAYTYPE, // Projection position 9, 
    	COLUMN_NAME_PRDTNAME, // Projection position 10, 
    	COLUMN_NAME_UNIT1, // Projection position 11, 
    	COLUMN_NAME_UNIT2, // Projection position 12, 
    	COLUMN_NAME_UNIT3, // Projection position 13, 
    	COLUMN_NAME_SELLQTY1, // Projection position 14, 
    	COLUMN_NAME_SELLQTY2, // Projection position 15, 
    	COLUMN_NAME_SELLQTY3, // Projection position 16, 
    	COLUMN_NAME_SENDQTY1, // Projection position 17, 
    	COLUMN_NAME_SENDQTY2, // Projection position 18, 
    	COLUMN_NAME_SENDQTY3, // Projection position 19, 
    	COLUMN_NAME_GIVEQTY1, // Projection position 20, 
    	COLUMN_NAME_GIVEQTY2, // Projection position 21, 
    	COLUMN_NAME_GIVEQTY3, // Projection position 22, 
    	COLUMN_NAME_DEALKIND, // Projection position 23, 
    	COLUMN_NAME_QUANTITY, // Projection position 24, 
    	COLUMN_NAME_QTUNIT, // Projection position 25, 
    	COLUMN_NAME_PRICEUNIT, // Projection position 26, 
    	COLUMN_NAME_UPRICE, // Projection position 27, 
    	COLUMN_NAME_PRICEUNIT1, // Projection position 28, 
    	COLUMN_NAME_UPRICE1, // Projection position 29, 
    	COLUMN_NAME_DISCRATE, // Projection position 30, 
    	COLUMN_NAME_PRICETOOLOW, // Projection position 31, 
    	COLUMN_NAME_PRICEMARK, // Projection position 32, 
    	COLUMN_NAME_VERSIONNO, // Projection position 33, 
    };
    protected static final int READ_SELLDETAIL_SERIALID_INDEX = 0;
    protected static final int READ_SELLDETAIL_COMPANYID_INDEX = 1;
    protected static final int READ_SELLDETAIL_CUSTOMERID_INDEX = 2;
    protected static final int READ_SELLDETAIL_SELLDATE_INDEX = 3;
    protected static final int READ_SELLDETAIL_ITEMNO_INDEX = 4;
    protected static final int READ_SELLDETAIL_SEQ_INDEX = 5;
    protected static final int READ_SELLDETAIL_SELLID_INDEX = 6;
    protected static final int READ_SELLDETAIL_SELLKIND_INDEX = 7;
    protected static final int READ_SELLDETAIL_SKIND_INDEX = 8;
    protected static final int READ_SELLDETAIL_SELLPAYTYPE_INDEX = 9;
    protected static final int READ_SELLDETAIL_PRDTNAME_INDEX = 10;
    protected static final int READ_SELLDETAIL_UNIT1_INDEX = 11;
    protected static final int READ_SELLDETAIL_UNIT2_INDEX = 12;
    protected static final int READ_SELLDETAIL_UNIT3_INDEX = 13;
    protected static final int READ_SELLDETAIL_SELLQTY1_INDEX = 14;
    protected static final int READ_SELLDETAIL_SELLQTY2_INDEX = 15;
    protected static final int READ_SELLDETAIL_SELLQTY3_INDEX = 16;
    protected static final int READ_SELLDETAIL_SENDQTY1_INDEX = 17;
    protected static final int READ_SELLDETAIL_SENDQTY2_INDEX = 18;
    protected static final int READ_SELLDETAIL_SENDQTY3_INDEX = 19;
    protected static final int READ_SELLDETAIL_GIVEQTY1_INDEX = 20;
    protected static final int READ_SELLDETAIL_GIVEQTY2_INDEX = 21;
    protected static final int READ_SELLDETAIL_GIVEQTY3_INDEX = 22;
    protected static final int READ_SELLDETAIL_DEALKIND_INDEX = 23;
    protected static final int READ_SELLDETAIL_QUANTITY_INDEX = 24;
    protected static final int READ_SELLDETAIL_QTUNIT_INDEX = 25;
    protected static final int READ_SELLDETAIL_PRICEUNIT_INDEX = 26;
    protected static final int READ_SELLDETAIL_UPRICE_INDEX = 27;
    protected static final int READ_SELLDETAIL_PRICEUNIT1_INDEX = 28;
    protected static final int READ_SELLDETAIL_UPRICE1_INDEX = 29;
    protected static final int READ_SELLDETAIL_DISCRATE_INDEX = 30;
    protected static final int READ_SELLDETAIL_PRICETOOLOW_INDEX = 31;
    protected static final int READ_SELLDETAIL_PRICEMARK_INDEX = 32;
    protected static final int READ_SELLDETAIL_VERSIONNO_INDEX = 33;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseSellDetail() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
    	projectionMap.put(COLUMN_NAME_CUSTOMERID, COLUMN_NAME_CUSTOMERID);
    	projectionMap.put(COLUMN_NAME_SELLDATE, COLUMN_NAME_SELLDATE);
    	projectionMap.put(COLUMN_NAME_ITEMNO, COLUMN_NAME_ITEMNO);
    	projectionMap.put(COLUMN_NAME_SEQ, COLUMN_NAME_SEQ);
    	projectionMap.put(COLUMN_NAME_SELLID, COLUMN_NAME_SELLID);
    	projectionMap.put(COLUMN_NAME_SELLKIND, COLUMN_NAME_SELLKIND);
    	projectionMap.put(COLUMN_NAME_SKIND, COLUMN_NAME_SKIND);
    	projectionMap.put(COLUMN_NAME_SELLPAYTYPE, COLUMN_NAME_SELLPAYTYPE);
    	projectionMap.put(COLUMN_NAME_PRDTNAME, COLUMN_NAME_PRDTNAME);
    	projectionMap.put(COLUMN_NAME_UNIT1, COLUMN_NAME_UNIT1);
    	projectionMap.put(COLUMN_NAME_UNIT2, COLUMN_NAME_UNIT2);
    	projectionMap.put(COLUMN_NAME_UNIT3, COLUMN_NAME_UNIT3);
    	projectionMap.put(COLUMN_NAME_SELLQTY1, COLUMN_NAME_SELLQTY1);
    	projectionMap.put(COLUMN_NAME_SELLQTY2, COLUMN_NAME_SELLQTY2);
    	projectionMap.put(COLUMN_NAME_SELLQTY3, COLUMN_NAME_SELLQTY3);
    	projectionMap.put(COLUMN_NAME_SENDQTY1, COLUMN_NAME_SENDQTY1);
    	projectionMap.put(COLUMN_NAME_SENDQTY2, COLUMN_NAME_SENDQTY2);
    	projectionMap.put(COLUMN_NAME_SENDQTY3, COLUMN_NAME_SENDQTY3);
    	projectionMap.put(COLUMN_NAME_GIVEQTY1, COLUMN_NAME_GIVEQTY1);
    	projectionMap.put(COLUMN_NAME_GIVEQTY2, COLUMN_NAME_GIVEQTY2);
    	projectionMap.put(COLUMN_NAME_GIVEQTY3, COLUMN_NAME_GIVEQTY3);
    	projectionMap.put(COLUMN_NAME_DEALKIND, COLUMN_NAME_DEALKIND);
    	projectionMap.put(COLUMN_NAME_QUANTITY, COLUMN_NAME_QUANTITY);
    	projectionMap.put(COLUMN_NAME_QTUNIT, COLUMN_NAME_QTUNIT);
    	projectionMap.put(COLUMN_NAME_PRICEUNIT, COLUMN_NAME_PRICEUNIT);
    	projectionMap.put(COLUMN_NAME_UPRICE, COLUMN_NAME_UPRICE);
    	projectionMap.put(COLUMN_NAME_PRICEUNIT1, COLUMN_NAME_PRICEUNIT1);
    	projectionMap.put(COLUMN_NAME_UPRICE1, COLUMN_NAME_UPRICE1);
    	projectionMap.put(COLUMN_NAME_DISCRATE, COLUMN_NAME_DISCRATE);
    	projectionMap.put(COLUMN_NAME_PRICETOOLOW, COLUMN_NAME_PRICETOOLOW);
    	projectionMap.put(COLUMN_NAME_PRICEMARK, COLUMN_NAME_PRICEMARK);
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
	    		COLUMN_NAME_CUSTOMERID+" INTEGER,"+
	    		COLUMN_NAME_SELLDATE+" TEXT,"+
	    		COLUMN_NAME_ITEMNO+" TEXT,"+
	    		COLUMN_NAME_SEQ+" INTEGER,"+
	    		COLUMN_NAME_SELLID+" INTEGER,"+
	    		COLUMN_NAME_SELLKIND+" INTEGER,"+
	    		COLUMN_NAME_SKIND+" TEXT,"+
	    		COLUMN_NAME_SELLPAYTYPE+" TEXT,"+
	    		COLUMN_NAME_PRDTNAME+" TEXT,"+
	    		COLUMN_NAME_UNIT1+" TEXT,"+
	    		COLUMN_NAME_UNIT2+" TEXT,"+
	    		COLUMN_NAME_UNIT3+" TEXT,"+
	    		COLUMN_NAME_SELLQTY1+" REAL,"+
	    		COLUMN_NAME_SELLQTY2+" REAL,"+
	    		COLUMN_NAME_SELLQTY3+" REAL,"+
	    		COLUMN_NAME_SENDQTY1+" REAL,"+
	    		COLUMN_NAME_SENDQTY2+" REAL,"+
	    		COLUMN_NAME_SENDQTY3+" REAL,"+
	    		COLUMN_NAME_GIVEQTY1+" REAL,"+
	    		COLUMN_NAME_GIVEQTY2+" REAL,"+
	    		COLUMN_NAME_GIVEQTY3+" REAL,"+
	    		COLUMN_NAME_DEALKIND+" INTEGER,"+
	    		COLUMN_NAME_QUANTITY+" REAL,"+
	    		COLUMN_NAME_QTUNIT+" TEXT,"+
	    		COLUMN_NAME_PRICEUNIT+" TEXT,"+
	    		COLUMN_NAME_UPRICE+" REAL,"+
	    		COLUMN_NAME_PRICEUNIT1+" TEXT,"+
	    		COLUMN_NAME_UPRICE1+" REAL,"+
	    		COLUMN_NAME_DISCRATE+" REAL,"+
	    		COLUMN_NAME_PRICETOOLOW+" TEXT,"+
	    		COLUMN_NAME_PRICEMARK+" INTEGER,"+
	    		COLUMN_NAME_VERSIONNO+" TEXT);";
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+COLUMN_NAME_COMPANYID+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P2 ON "+getTableName()+" ("+COLUMN_NAME_CUSTOMERID+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P3 ON "+getTableName()+" ("+COLUMN_NAME_SELLDATE+");",
		        "CREATE  INDEX IF NOT EXISTS "+getTableName()+"P4 ON "+getTableName()+" ("+COLUMN_NAME_ITEMNO+");",
		        "CREATE  INDEX IF NOT EXISTS "+getTableName()+"P5 ON "+getTableName()+
		    		" ("+COLUMN_NAME_COMPANYID+","+COLUMN_NAME_CUSTOMERID+","+
		    		COLUMN_NAME_SELLDATE+","+COLUMN_NAME_ITEMNO+","+COLUMN_NAME_SEQ+","+COLUMN_NAME_SELLID+");",

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
	private int customerID;
	private Date sellDate;
	private String itemNo;
	private int seq;
	private int sellID;
	private int sellKind;
	private String sKind;
	private String sellPayType;
	private String prdtName;
	private String unit1;
	private String unit2;
	private String unit3;
	private double sellQty1;
	private double sellQty2;
	private double sellQty3;
	private double sendQty1;
	private double sendQty2;
	private double sendQty3;
	private double giveQty1;
	private double giveQty2;
	private double giveQty3;
	private String note; // 2012/8/15 added
	private int dealKind;
	private double quantity;
	private String qTUnit;
	private String priceUnit;
	private double uPrice;
	private String priceUnit1;
	private double uPrice1;
	private double discRate;
	private String priceTooLow;
	private int priceMark;
	private String versionNo;
	private Integer custDelverViewOrder;
	private Date sendDate;
	private Date orderDate;
	private String sellTitle; //HAO 1040.03.09
	
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

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public Date getSellDate() {
		return sellDate;
	}

	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getSellID() {
		return sellID;
	}

	public void setSellID(int sellID) {
		this.sellID = sellID;
	}

	public int getSellKind() {
		return sellKind;
	}

	public void setSellKind(int sellKind) {
		this.sellKind = sellKind;
	}

	public String getsKind() {
		return sKind;
	}

	public void setsKind(String sKind) {
		this.sKind = sKind;
	}

	public String getSellPayType() {
		return sellPayType;
	}

	public void setSellPayType(String sellPayType) {
		this.sellPayType = sellPayType;
	}

	public String getPrdtName() {
		return prdtName;
	}

	public void setPrdtName(String prdtName) {
		this.prdtName = prdtName;
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

	public double getSellQty1() {
		return sellQty1;
	}

	public void setSellQty1(double sellQty1) {
		this.sellQty1 = sellQty1;
	}

	public double getSellQty2() {
		return sellQty2;
	}

	public void setSellQty2(double sellQty2) {
		this.sellQty2 = sellQty2;
	}

	public double getSellQty3() {
		return sellQty3;
	}

	public void setSellQty3(double sellQty3) {
		this.sellQty3 = sellQty3;
	}

	public double getSendQty1() {
		return sendQty1;
	}

	public void setSendQty1(double sendQty1) {
		this.sendQty1 = sendQty1;
	}

	public double getSendQty2() {
		return sendQty2;
	}

	public void setSendQty2(double sendQty2) {
		this.sendQty2 = sendQty2;
	}

	public double getSendQty3() {
		return sendQty3;
	}

	public void setSendQty3(double sendQty3) {
		this.sendQty3 = sendQty3;
	}

	public double getGiveQty1() {
		return giveQty1;
	}

	public void setGiveQty1(double giveQty1) {
		this.giveQty1 = giveQty1;
	}

	public double getGiveQty2() {
		return giveQty2;
	}

	public void setGiveQty2(double giveQty2) {
		this.giveQty2 = giveQty2;
	}

	public double getGiveQty3() {
		return giveQty3;
	}

	public void setGiveQty3(double giveQty3) {
		this.giveQty3 = giveQty3;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getDealKind() {
		return dealKind;
	}

	public void setDealKind(int dealKind) {
		this.dealKind = dealKind;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getqTUnit() {
		return qTUnit;
	}

	public void setqTUnit(String qTUnit) {
		this.qTUnit = qTUnit;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public double getuPrice() {
		return uPrice;
	}

	public void setuPrice(double uPrice) {
		this.uPrice = uPrice;
	}

	public String getPriceUnit1() {
		return priceUnit1;
	}

	public void setPriceUnit1(String priceUnit1) {
		this.priceUnit1 = priceUnit1;
	}

	public double getuPrice1() {
		return uPrice1;
	}

	public void setuPrice1(double uPrice1) {
		this.uPrice1 = uPrice1;
	}

	public double getDiscRate() {
		return discRate;
	}

	public void setDiscRate(double discRate) {
		this.discRate = discRate;
	}

	public String getPriceTooLow() {
		return priceTooLow;
	}

	public void setPriceTooLow(String priceTooLow) {
		this.priceTooLow = priceTooLow;
	}

	public int getPriceMark() {
		return priceMark;
	}

	public void setPriceMark(int priceMark) {
		this.priceMark = priceMark;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public Integer getCustDelverViewOrder() {
		return custDelverViewOrder;
	}

	public void setCustDelverViewOrder(Integer custDelverViewOrder) {
		this.custDelverViewOrder = custDelverViewOrder;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	//HAO 1040.03.09
	public String getSellTitle() {
		return sellTitle;
	}

	public void setSellTitle(String sellTitle) {
		this.sellTitle = sellTitle;
	}
	//

}
