package com.lik.android.om;

import java.util.HashMap;

import com.lik.android.main.MainMenuActivity;

public abstract class BaseCustomersBackup extends BaseOM<Customers> {

	private static final long serialVersionUID = -6728565690285290175L;

	public static final String TABLE_NAME = "Customers";

	public static final String TABLE_CH_NAME = "�Ȥ���";
	
	public static final String ISLIMIT_Y = "Y";
	public static final String NORETURN_Y = "Y";
	public static final String BEVISIT_Y = "Y";
	public static final String DIRTYPAY_Y = "Y";

	/**
     * Column name for �Ǹ� of the Customers
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for ���q�y���� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";

    /**
     * Column name for �ާ@�H���N�� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_USERNO = "UserNO";

    /**
     * Column name for �Ȥ�y���� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CUSTOMERID = "CustomerID";

    /**
     * Column name for �Ȥ�N�� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CUSTOMERNO = "CustomerNO";

    /**
     * Column name for �Ȥ�²�� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SHORTNAME = "ShortName";

    /**
     * Column name for �Ȥ���� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_FULLNAME = "FullName";

    /**
     * Column name for �l���ϸ� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ZIPNO = "ZipNo";

    /**
     * Column name for ��~�a�} of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ADDRESS = "Address";

    /**
     * Column name for �q��(�@) of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_TEL1 = "Tel1";

    /**
     * Column name for �q��(�G) of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_TEL2 = "Tel2";

    /**
     * Column name for ����q�� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ACTTEL = "ActTel";

    /**
     * Column name for ���b�覡 of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PAYTYPE = "PayType";

    /**
     * Column name for �~�ȭ��y���� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SALESID = "SalesID";

    /**
     * Column name for ������� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PRICEGRADE = "PriceGrade";

    /**
     * Column name for �P�P�p���y���� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PROMOTEGROUPID = "PromoteGroupID";

    /**
     * Column name for �������X���u Y/N of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_BEVISIT = "BeVisit";

    /**
     * Column name for ���b�� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SETTLEDAY = "SettleDay";

    /**
     * Column name for ���ڦ� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_REVCASH_DISRATE = "RevCash_Disrate";

    /**
     * Column name for SalesName of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SALESNAME = "SalesName";

    /**
     * Column name for SalesNO of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SALESNO = "SalesNO";

    /**
     * Column name for IsLimit of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ISLIMIT = "IsLimit";

    /**
     * Column name for ���b�� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DELIVERYWAY = "DeliveryWay";

    /**
     * Column name for ���b�� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_REQUESTDAY = "RequestDay";

    /**
     * Column name for ���b�� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CHECKDAY = "CheckDay";

    /**
     * Column name for ���b�� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_VISITLINE = "VisitLine";

    /**
     * Column name for ���i�h�f of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_NORETURN = "NoReturn";

    /**
     * Column name for ���i�h�f of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CUSTTYPE = "CustType";

    /**
     * Column name for DeliverOrder of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DELIVERORDER = "DeliverOrder";

    /**
     * Column name for SellAmount of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SELLAMOUNT = "SellAmount";

    /**
     * Column name for DirtyPay of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DIRTYPAY = "DirtyPay";

    /**
     * Column name for BillDays of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_BILLDAYS = "BillDays";

    /**
     * Column name for BackRate of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_BACKRATE = "BackRate";

    /**
     * Column name for IsDirty of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ISDIRTY = "IsDirty"; //HAO 104.04.24
    
    /**
     * Column name for ���� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_VERSIONNO = "VersionNo";

//    private String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
//    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
//    		COLUMN_NAME_COMPANYID+" INTEGER,"+
//    		COLUMN_NAME_USERNO+" TEXT,"+
//    		COLUMN_NAME_CUSTOMERID+" INTEGER,"+
//    		COLUMN_NAME_CUSTOMERNO+" TEXT,"+
//    		COLUMN_NAME_SHORTNAME+" TEXT,"+
//    		COLUMN_NAME_FULLNAME+" TEXT,"+
//    		COLUMN_NAME_ZIPNO+" TEXT,"+
//    		COLUMN_NAME_ADDRESS+" TEXT,"+
//    		COLUMN_NAME_TEL1+" TEXT,"+
//    		COLUMN_NAME_TEL2+" TEXT,"+
//    		COLUMN_NAME_PAYTYPE+" TEXT,"+
//    		COLUMN_NAME_SALESID+" INTEGER,"+
//    		COLUMN_NAME_PRICEGRADE+" TEXT,"+
//    		COLUMN_NAME_PROMOTEGROUPID+" INTEGER,"+
//    		COLUMN_NAME_BEVISIT+" TEXT,"+
//    		COLUMN_NAME_VERSIONNO+" TEXT);";
//
//    private String[] CREATE_INDEX_CMD = {
//    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+COLUMN_NAME_COMPANYID+","+COLUMN_NAME_USERNO+","+COLUMN_NAME_BEVISIT+");",
//    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P2 ON "+getTableName()+" ("+COLUMN_NAME_SHORTNAME+");",
//    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P3 ON "+getTableName()+" ("+COLUMN_NAME_CUSTOMERNO+");",
//    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P4 ON "+getTableName()+" ("+COLUMN_NAME_COMPANYID+","+COLUMN_NAME_USERNO+","+COLUMN_NAME_CUSTOMERID+");",
//    };
//    
//    private String DROP_CMD = "DROP TABLE IF EXISTS "+getTableName();

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_CUSTOMERS_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_COMPANYID,  // Projection position 1, 
    	COLUMN_NAME_USERNO, // Projection position 2, 
    	COLUMN_NAME_CUSTOMERID, // Projection position 3, 
    	COLUMN_NAME_CUSTOMERNO, // Projection position 4, 
    	COLUMN_NAME_SHORTNAME, // Projection position 5, 
    	COLUMN_NAME_FULLNAME, // Projection position 6, 
    	COLUMN_NAME_ZIPNO, // Projection position 7, 
    	COLUMN_NAME_ADDRESS, // Projection position 8, 
    	COLUMN_NAME_TEL1, // Projection position 9, 
    	COLUMN_NAME_TEL2, // Projection position 10, 
    	COLUMN_NAME_ACTTEL, // Projection position 11, 
    	COLUMN_NAME_PAYTYPE, // Projection position 12, 
    	COLUMN_NAME_SALESID, // Projection position 13, 
    	COLUMN_NAME_PRICEGRADE, // Projection position 14, 
    	COLUMN_NAME_PROMOTEGROUPID, // Projection position 15, 
    	COLUMN_NAME_BEVISIT, // Projection position 16, 
    	COLUMN_NAME_SETTLEDAY, // Projection position 17, 
    	COLUMN_NAME_REVCASH_DISRATE, // Projection position 18, 
    	COLUMN_NAME_SALESNAME, // Projection position 19, 
    	COLUMN_NAME_SALESNO, // Projection position 20, 
    	COLUMN_NAME_ISLIMIT, // Projection position 21, 
    	COLUMN_NAME_DELIVERYWAY, // Projection position 22, 
    	COLUMN_NAME_REQUESTDAY, // Projection position 23, 
    	COLUMN_NAME_CHECKDAY, // Projection position 24, 
    	COLUMN_NAME_VISITLINE, // Projection position 25, 
    	COLUMN_NAME_NORETURN, // Projection position 26, 
    	COLUMN_NAME_CUSTTYPE, // Projection position 27, 
    	COLUMN_NAME_DELIVERORDER, // Projection position 28, 
    	COLUMN_NAME_SELLAMOUNT, // Projection position 29, 
    	COLUMN_NAME_DIRTYPAY, // Projection position 30, 
    	COLUMN_NAME_BILLDAYS, // Projection position 31, 
    	COLUMN_NAME_BACKRATE, // Projection position 32,
    	COLUMN_NAME_ISDIRTY, // Projection position 33, HAO 104.04.24
    	COLUMN_NAME_VERSIONNO, // Projection position 34, 
    };
    protected static final int READ_CUSTOMERS_SERIALID_INDEX = 0;
    protected static final int READ_CUSTOMERS_COMPANYID_INDEX = 1;
    protected static final int READ_CUSTOMERS_USERNO_INDEX = 2;
    protected static final int READ_CUSTOMERS_CUSTOMERID_INDEX = 3;
    protected static final int READ_CUSTOMERS_CUSTOMERNO_INDEX = 4;
    protected static final int READ_CUSTOMERS_SHORTNAME_INDEX = 5;
    protected static final int READ_CUSTOMERS_FULLNAME_INDEX = 6;
    protected static final int READ_CUSTOMERS_ZIPNO_INDEX = 7;
    protected static final int READ_CUSTOMERS_ADDRESS_INDEX = 8;
    protected static final int READ_CUSTOMERS_TEL1_INDEX = 9;
    protected static final int READ_CUSTOMERS_TEL2_INDEX = 10;
    protected static final int READ_CUSTOMERS_ACTTEL_INDEX = 11;
    protected static final int READ_CUSTOMERS_PAYTYPE_INDEX = 12;
    protected static final int READ_CUSTOMERS_SALESID_INDEX = 13;
    protected static final int READ_CUSTOMERS_PRICEGRADE_INDEX = 14;
    protected static final int READ_CUSTOMERS_PROMOTEGROUPID_INDEX = 15;
    protected static final int READ_CUSTOMERS_BEVISIT_INDEX = 16;
    protected static final int READ_CUSTOMERS_SETTLEDAY_INDEX = 17;
    protected static final int READ_CUSTOMERS_REVCASH_DISRATE_INDEX = 18;
    protected static final int READ_CUSTOMERS_SALESNAME_INDEX = 19;
    protected static final int READ_CUSTOMERS_SALESNO_INDEX = 20;
    protected static final int READ_CUSTOMERS_ISLIMIT_INDEX = 21;
    protected static final int READ_CUSTOMERS_DELIVERYWAY_INDEX = 22;
    protected static final int READ_CUSTOMERS_REQUESTDAY_INDEX = 23;
    protected static final int READ_CUSTOMERS_CHECKDAY_INDEX = 24;
    protected static final int READ_CUSTOMERS_VISITLINE_INDEX = 25;
    protected static final int READ_CUSTOMERS_NORETURN_INDEX = 26;
    protected static final int READ_CUSTOMERS_CUSTTYPE_INDEX = 27;
    protected static final int READ_CUSTOMERS_DELIVERORDER_INDEX = 28;
    protected static final int READ_CUSTOMERS_SELLAMOUNT_INDEX = 29;
    protected static final int READ_CUSTOMERS_DIRTYPAY_INDEX = 30;
    protected static final int READ_CUSTOMERS_BILLDAYS_INDEX = 31;
    protected static final int READ_CUSTOMERS_BACKRATE_INDEX = 32;
    protected static final int READ_CUSTOMERS_ISDIRTY_INDEX = 33; //HAO 104.04.24
    protected static final int READ_CUSTOMERS_VERSIONNO_INDEX = 34;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseCustomersBackup() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
    	projectionMap.put(COLUMN_NAME_USERNO, COLUMN_NAME_USERNO);
    	projectionMap.put(COLUMN_NAME_CUSTOMERID, COLUMN_NAME_CUSTOMERID);
    	projectionMap.put(COLUMN_NAME_CUSTOMERNO, COLUMN_NAME_CUSTOMERNO);
    	projectionMap.put(COLUMN_NAME_SHORTNAME, COLUMN_NAME_SHORTNAME);
    	projectionMap.put(COLUMN_NAME_FULLNAME, COLUMN_NAME_FULLNAME);
    	projectionMap.put(COLUMN_NAME_ZIPNO, COLUMN_NAME_ZIPNO);
    	projectionMap.put(COLUMN_NAME_ADDRESS, COLUMN_NAME_ADDRESS);
    	projectionMap.put(COLUMN_NAME_TEL1, COLUMN_NAME_TEL1);
    	projectionMap.put(COLUMN_NAME_TEL2, COLUMN_NAME_TEL2);
    	projectionMap.put(COLUMN_NAME_ACTTEL, COLUMN_NAME_ACTTEL);
    	projectionMap.put(COLUMN_NAME_PAYTYPE, COLUMN_NAME_PAYTYPE);
    	projectionMap.put(COLUMN_NAME_SALESID, COLUMN_NAME_SALESID);
    	projectionMap.put(COLUMN_NAME_PRICEGRADE, COLUMN_NAME_PRICEGRADE);
    	projectionMap.put(COLUMN_NAME_PROMOTEGROUPID, COLUMN_NAME_PROMOTEGROUPID);
    	projectionMap.put(COLUMN_NAME_BEVISIT, COLUMN_NAME_BEVISIT);
    	projectionMap.put(COLUMN_NAME_SETTLEDAY, COLUMN_NAME_SETTLEDAY);
    	projectionMap.put(COLUMN_NAME_REVCASH_DISRATE, COLUMN_NAME_REVCASH_DISRATE);
    	projectionMap.put(COLUMN_NAME_SALESNAME, COLUMN_NAME_SALESNAME);
    	projectionMap.put(COLUMN_NAME_SALESNO, COLUMN_NAME_SALESNO);
    	projectionMap.put(COLUMN_NAME_ISLIMIT, COLUMN_NAME_ISLIMIT);
    	projectionMap.put(COLUMN_NAME_DELIVERYWAY, COLUMN_NAME_DELIVERYWAY);
    	projectionMap.put(COLUMN_NAME_REQUESTDAY, COLUMN_NAME_REQUESTDAY);
    	projectionMap.put(COLUMN_NAME_CHECKDAY, COLUMN_NAME_CHECKDAY);
    	projectionMap.put(COLUMN_NAME_VISITLINE, COLUMN_NAME_VISITLINE);
    	projectionMap.put(COLUMN_NAME_NORETURN, COLUMN_NAME_NORETURN);
    	projectionMap.put(COLUMN_NAME_CUSTTYPE, COLUMN_NAME_CUSTTYPE);
    	projectionMap.put(COLUMN_NAME_DELIVERORDER, COLUMN_NAME_DELIVERORDER);
    	projectionMap.put(COLUMN_NAME_SELLAMOUNT, COLUMN_NAME_SELLAMOUNT);
    	projectionMap.put(COLUMN_NAME_DIRTYPAY, COLUMN_NAME_DIRTYPAY);
    	projectionMap.put(COLUMN_NAME_BILLDAYS, COLUMN_NAME_BILLDAYS);
    	projectionMap.put(COLUMN_NAME_BACKRATE, COLUMN_NAME_BACKRATE);
    	projectionMap.put(COLUMN_NAME_ISDIRTY, COLUMN_NAME_ISDIRTY); //HAO 104.04.24
    	projectionMap.put(COLUMN_NAME_VERSIONNO, COLUMN_NAME_VERSIONNO);
    }

    @Override
	public String getTableName() {
    	setTableCompanyID(0);
		setCompanyParent(null);
    	if(getTableCompanyID()==0) 
    		//setTableCompanyID(MainMenuActivity.currentDept.getCompanyID());
    		setTableCompanyID(MainMenuActivity.companyID);
    	if(getCompanyParent()==null)
    		//setCompanyParent(MainMenuActivity.omCurrentSysProfile.getCompanyNo());
    		setCompanyParent(MainMenuActivity.companyParent);
    
		return TABLE_NAME+"_"+getCompanyParent()+getTableCompanyID();
    	//return TABLE_NAME+"_"+MainMenuActivity.getCurrentCompany().getCompanyName()+MainMenuActivity.getCurrentCompany().getDeptNO();
	}

	@Override
	public String getCreateCMD() {
		   String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
		    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
		    		COLUMN_NAME_COMPANYID+" INTEGER,"+
		    		COLUMN_NAME_USERNO+" TEXT,"+
		    		COLUMN_NAME_CUSTOMERID+" INTEGER,"+
		    		COLUMN_NAME_CUSTOMERNO+" TEXT,"+
		    		COLUMN_NAME_SHORTNAME+" TEXT,"+
		    		COLUMN_NAME_FULLNAME+" TEXT,"+
		    		COLUMN_NAME_ZIPNO+" TEXT,"+
		    		COLUMN_NAME_ADDRESS+" TEXT,"+
		    		COLUMN_NAME_TEL1+" TEXT,"+
		    		COLUMN_NAME_TEL2+" TEXT,"+
		    		COLUMN_NAME_ACTTEL+" TEXT,"+
		    		COLUMN_NAME_PAYTYPE+" TEXT,"+
		    		COLUMN_NAME_SALESID+" INTEGER,"+
		    		COLUMN_NAME_PRICEGRADE+" TEXT,"+
		    		COLUMN_NAME_PROMOTEGROUPID+" INTEGER,"+
		    		COLUMN_NAME_BEVISIT+" TEXT,"+
		    		COLUMN_NAME_SETTLEDAY+" INTEGER,"+
		    		COLUMN_NAME_REVCASH_DISRATE+" REAL,"+
		    		COLUMN_NAME_SALESNAME+" TEXT,"+
		    		COLUMN_NAME_SALESNO+" TEXT,"+
		    		COLUMN_NAME_ISLIMIT+" TEXT,"+
		    		COLUMN_NAME_DELIVERYWAY+" TEXT,"+
		    		COLUMN_NAME_REQUESTDAY+" TEXT,"+
		    		COLUMN_NAME_CHECKDAY+" TEXT,"+
		    		COLUMN_NAME_VISITLINE+" TEXT,"+
		    		COLUMN_NAME_NORETURN+" TEXT,"+
		    		COLUMN_NAME_CUSTTYPE+" INTEGER,"+
		    		COLUMN_NAME_DELIVERORDER+" INTEGER,"+
		    		COLUMN_NAME_SELLAMOUNT+" REAL,"+
		    		COLUMN_NAME_DIRTYPAY+" TEXT,"+
		    		COLUMN_NAME_BILLDAYS+" INTEGER,"+
		    		COLUMN_NAME_BACKRATE+" REAL,"+
		    		COLUMN_NAME_ISDIRTY+" TEXT,"+ //HAO 104.04.24
		    		COLUMN_NAME_VERSIONNO+" TEXT)";
		    		
		    		
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+COLUMN_NAME_COMPANYID+","+COLUMN_NAME_USERNO+","+COLUMN_NAME_BEVISIT+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P2 ON "+getTableName()+" ("+COLUMN_NAME_SHORTNAME+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P3 ON "+getTableName()+" ("+COLUMN_NAME_CUSTOMERNO+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P4 ON "+getTableName()+" ("+COLUMN_NAME_COMPANYID+","+COLUMN_NAME_USERNO+","+COLUMN_NAME_CUSTOMERID+");",
		    	"CREATE UNIQUE INDEX IF NOT EXISTS P5 ON "+getTableName()+"("+COLUMN_NAME_COMPANYID+","+COLUMN_NAME_USERNO+","+COLUMN_NAME_CUSTOMERID+");",
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
	private String userNO;
	private int customerID;
	private String customerNO;
	private String shortName;
	private String fullName;
	private String zipNo;
	private String address;
	private String tel1;
	private String tel2;
	private String actTel;
	private String payType;
	private int salesID;
	private String priceGrade;
	private int promoteGroupID;
	private String beVisit;
	private int settleDay;
	private double revCash_Disrate;
	private String salesName;
	private String salesNO;
	private String isLimit;
    private String deliveryWay;
    private String requestDay;
    private String checkDay;
    private String visitLine;
    private String noReturn;
	private int custType;
	private Integer deliverOrder;
	private String versionNo;
	private double sellAmount;
    private String dirtyPay; //SND
    private Integer billDays; //SYU
	private Double backRate;
	private String isDirty; //SND HAO 104.04.23

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

	public String getUserNO() {
		return userNO;
	}

	public void setUserNO(String userNO) {
		this.userNO = userNO;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getCustomerNO() {
		return customerNO;
	}

	public void setCustomerNO(String customerNO) {
		this.customerNO = customerNO;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getZipNo() {
		return zipNo;
	}

	public void setZipNo(String zipNo) {
		this.zipNo = zipNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getActTel() {
		return actTel;
	}

	public void setActTel(String actTel) {
		this.actTel = actTel;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public int getSalesID() {
		return salesID;
	}

	public void setSalesID(int salesID) {
		this.salesID = salesID;
	}

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

	public String getBeVisit() {
		return beVisit;
	}

	public void setBeVisit(String beVisit) {
		this.beVisit = beVisit;
	}

	public int getSettleDay() {
		return settleDay;
	}

	public void setSettleDay(int settleDay) {
		this.settleDay = settleDay;
	}

	public double getRevCash_Disrate() {
		return revCash_Disrate;
	}

	public void setRevCash_Disrate(double revCash_Disrate) {
		this.revCash_Disrate = revCash_Disrate;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getSalesNO() {
		return salesNO;
	}

	public void setSalesNO(String salesNO) {
		this.salesNO = salesNO;
	}

	public String getIsLimit() {
		return isLimit;
	}

	public void setIsLimit(String isLimit) {
		this.isLimit = isLimit;
	}

	public String getDeliveryWay() {
		return deliveryWay;
	}

	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay;
	}

	public String getRequestDay() {
		return requestDay;
	}

	public void setRequestDay(String requestDay) {
		this.requestDay = requestDay;
	}

	public String getCheckDay() {
		return checkDay;
	}

	public void setCheckDay(String checkDay) {
		this.checkDay = checkDay;
	}

	public String getVisitLine() {
		return visitLine;
	}

	public void setVisitLine(String visitLine) {
		this.visitLine = visitLine;
	}

	public String getNoReturn() {
		return noReturn;
	}

	public void setNoReturn(String noReturn) {
		this.noReturn = noReturn;
	}

	public int getCustType() {
		return custType;
	}

	public void setCustType(int custType) {
		this.custType = custType;
	}

	public Integer getDeliverOrder() {
		return deliverOrder;
	}

	public void setDeliverOrder(Integer deliverOrder) {
		this.deliverOrder = deliverOrder;
	}

	public double getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(double sellAmount) {
		this.sellAmount = sellAmount;
	}

	public String getDirtyPay() {
		return dirtyPay;
	}

	public void setDirtyPay(String dirtyPay) {
		this.dirtyPay = dirtyPay;
	}

	public Integer getBillDays() {
		return billDays;
	}

	public void setBillDays(Integer billDays) {
		this.billDays = billDays;
	}

	//HAO 104.04.24
	public String getIsDirty() {
		return isDirty;
	}

	//HAO 104.04.24
	public void setIsDirty(String isDirty) {
		this.isDirty = isDirty;
	}
	
	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public Double getBackRate() {
		return backRate;
	}

	public void setBackRate(Double backRate) {
		this.backRate = backRate;
	}

}
