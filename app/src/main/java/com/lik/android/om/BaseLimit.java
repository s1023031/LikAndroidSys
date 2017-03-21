package com.lik.android.om;

import java.util.Date;
import java.util.HashMap;

public abstract class BaseLimit extends BaseOM<Limit> {

	private static final long serialVersionUID = 7873426620734841571L;

	public static final String TABLE_NAME = "Limit";

	public static final String TABLE_CH_NAME = "客戶產品銷售限制檔";
	public static final String TABLE_GROUP_NAME = "產品資料";

	/**
     * Column name for 序號 of the Limit
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for 公司流水號 of the Limit
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";

	/**
     * Column name for ItemID of the Limit
     * <P>Type: STRING</P>
     */
    public static final String COLUMN_NAME_ITEMID = "ItemID";

    /**
     * Column name for 客戶流水號 of the Limit
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CUSTOMERID = "CustomerID";

	/**
     * Column name for UserNo of the Limit
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_USERNO = "UserNo";

	/**
     * Column name for StradeDate of the Limit
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_STRADEDATE = "StradeDate";

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_LIMIT_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_COMPANYID,  // Projection position 1, 
    	COLUMN_NAME_ITEMID, // Projection position 2, 
    	COLUMN_NAME_CUSTOMERID, // Projection position 3, 
    	COLUMN_NAME_USERNO, // Projection position 4, 
    	COLUMN_NAME_STRADEDATE, // Projection position 5, 
    };
    protected static final int READ_LIMIT_SERIALID_INDEX = 0;
    protected static final int READ_LIMIT_COMPANYID_INDEX = 1;
    protected static final int READ_LIMIT_ITEMID_INDEX = 2;
    protected static final int READ_LIMIT_CUSTOMERID_INDEX = 3;
    protected static final int READ_LIMIT_USERNO_INDEX = 4;
    protected static final int READ_LIMIT_STRADEDATE_INDEX = 5;
  
    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseLimit() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
    	projectionMap.put(COLUMN_NAME_ITEMID, COLUMN_NAME_ITEMID);
    	projectionMap.put(COLUMN_NAME_CUSTOMERID, COLUMN_NAME_CUSTOMERID);
    	projectionMap.put(COLUMN_NAME_USERNO, COLUMN_NAME_USERNO);
    	projectionMap.put(COLUMN_NAME_STRADEDATE, COLUMN_NAME_STRADEDATE);
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
	    		COLUMN_NAME_ITEMID+" INTEGER,"+
	    		COLUMN_NAME_CUSTOMERID+" INTEGER,"+
	    		COLUMN_NAME_USERNO+" TEXT,"+
	    		COLUMN_NAME_STRADEDATE+" TEXT);";
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+COLUMN_NAME_COMPANYID+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P2 ON "+getTableName()+" ("+COLUMN_NAME_ITEMID+");",
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
    private int itemID;
    private int customerID;
    private String userNo;
    private Date stradeDate;

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

	public Date getStradeDate() {
		return stradeDate;
	}

	public void setStradeDate(Date stradeDate) {
		this.stradeDate = stradeDate;
	}

   
}
