package com.lik.android.om;

import java.util.HashMap;

import com.lik.android.main.MainMenuActivity;

public abstract class BaseCustomers extends BaseOM<Customers> {

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
     * Column name for �~�ȭ��y���� of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SALESID = "SalesID";



    /**
     * Column name for �������X���u Y/N of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_BEVISIT = "BeVisit";


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
;

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
    	COLUMN_NAME_SALESID, // Projection position 12, 
    	COLUMN_NAME_BEVISIT, // Projection position 13, 
    	COLUMN_NAME_SALESNAME, // Projection position 14, 
    	COLUMN_NAME_SALESNO, // Projection position 15, 
    
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
    protected static final int READ_CUSTOMERS_SALESID_INDEX = 12;
    protected static final int READ_CUSTOMERS_BEVISIT_INDEX = 13; 
    protected static final int READ_CUSTOMERS_SALESNAME_INDEX = 14;
    protected static final int READ_CUSTOMERS_SALESNO_INDEX = 15;
   

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseCustomers() {
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
    	projectionMap.put(COLUMN_NAME_SALESID, COLUMN_NAME_SALESID);
    	projectionMap.put(COLUMN_NAME_BEVISIT, COLUMN_NAME_BEVISIT);
    	projectionMap.put(COLUMN_NAME_SALESNAME, COLUMN_NAME_SALESNAME);
    	projectionMap.put(COLUMN_NAME_SALESNO, COLUMN_NAME_SALESNO);

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
		    		COLUMN_NAME_SALESID+" INTEGER,"+
		    		COLUMN_NAME_BEVISIT+" TEXT,"+
		    		COLUMN_NAME_SALESNAME+" TEXT,"+
		    		COLUMN_NAME_SALESNO+" TEXT)";
	
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
	private int salesID;
	private String beVisit;
	private String salesName;
	private String salesNO;

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


	public int getSalesID() {
		return salesID;
	}

	public void setSalesID(int salesID) {
		this.salesID = salesID;
	}

	
	public String getBeVisit() {
		return beVisit;
	}

	public void setBeVisit(String beVisit) {
		this.beVisit = beVisit;
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
}
