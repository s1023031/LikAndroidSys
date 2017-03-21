package com.lik.android.om;

import java.util.HashMap;

public abstract class BaseTemporaryCredit extends BaseOM<TemporaryCredit> {

	private static final long serialVersionUID = 7873426620734841571L;

	public static final String TABLE_NAME = "TemporaryCredit";

	public static final String TABLE_CH_NAME = "陳列費";
	public static final String TABLE_GROUP_NAME = "客戶資料";

	/**
     * Column name for 序號 of the TemporaryCredit
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for 公司流水號 of the TemporaryCredit
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";

    /**
     * Column name for 客戶流水號 of the TemporaryCredit
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CUSTOMERID = "CustomerID";

	/**
     * Column name for UserNO of the TemporaryCredit
     * <P>Type: STRING</P>
     */
    public static final String COLUMN_NAME_USERNO = "UserNO";

	/**
     * Column name for AccountName of the TemporaryCredit
     * <P>Type: STRING</P>
     */
    public static final String COLUMN_NAME_ACCOUNTNAME = "AccountName";

	/**
     * Column name for AccountRemark of the TemporaryCredit
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_ACCOUNTREMARK = "AccountRemark";

	/**
     * Column name for AccountAmount of the TemporaryCredit
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_ACCOUNTAMOUNT = "AccountAmount";

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_TC_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_COMPANYID,  // Projection position 1, 
    	COLUMN_NAME_CUSTOMERID, // Projection position 2, 
    	COLUMN_NAME_USERNO, // Projection position 3, 
    	COLUMN_NAME_ACCOUNTNAME, // Projection position 4, 
    	COLUMN_NAME_ACCOUNTREMARK, // Projection position 5, 
    	COLUMN_NAME_ACCOUNTAMOUNT, // Projection position 6, 
    };
    protected static final int READ_TC_SERIALID_INDEX = 0;
    protected static final int READ_TC_COMPANYID_INDEX = 1;
    protected static final int READ_TC_CUSTOMERID_INDEX = 2;
    protected static final int READ_TC_USERNO_INDEX = 3;
    protected static final int READ_TC_ACCOUNTNAME_INDEX = 4;
    protected static final int READ_TC_ACCOUNTREMARK_INDEX = 5;
    protected static final int READ_TC_ACCOUNTAMOUNT_INDEX = 6;
  
    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseTemporaryCredit() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
    	projectionMap.put(COLUMN_NAME_CUSTOMERID, COLUMN_NAME_CUSTOMERID);
    	projectionMap.put(COLUMN_NAME_USERNO, COLUMN_NAME_USERNO);
    	projectionMap.put(COLUMN_NAME_ACCOUNTNAME, COLUMN_NAME_ACCOUNTNAME);
    	projectionMap.put(COLUMN_NAME_ACCOUNTREMARK, COLUMN_NAME_ACCOUNTREMARK);
    	projectionMap.put(COLUMN_NAME_ACCOUNTAMOUNT, COLUMN_NAME_ACCOUNTAMOUNT);
    }

    @Override
	public String getTableName() {
//		return TABLE_NAME+"_"+getTableCompanyID();
		return TABLE_NAME+"_"+(getTableCompanyID()==0?companyID:getTableCompanyID());
	}

	@Override
	public String getCreateCMD() {
		String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
	    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
	    		COLUMN_NAME_COMPANYID+" INTEGER,"+
	    		COLUMN_NAME_CUSTOMERID+" INTEGER,"+
	    		COLUMN_NAME_USERNO+" TEXT,"+
	    		COLUMN_NAME_ACCOUNTNAME+" TEXT,"+
	    		COLUMN_NAME_ACCOUNTREMARK+" TEXT,"+
	    		COLUMN_NAME_ACCOUNTAMOUNT+" REAL);";
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+COLUMN_NAME_COMPANYID+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P2 ON "+getTableName()+" ("+COLUMN_NAME_USERNO+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P3 ON "+getTableName()+" ("+COLUMN_NAME_CUSTOMERID+");",
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
    private String userNo;
    private String accountName;
    private String accountRemark;
    private double accountAmount;

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

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountRemark() {
		return accountRemark;
	}

	public void setAccountRemark(String accountRemark) {
		this.accountRemark = accountRemark;
	}

	public double getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(double accountAmount) {
		this.accountAmount = accountAmount;
	}

   
}
