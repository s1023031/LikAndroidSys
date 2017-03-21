package com.lik.android.om;

import java.util.HashMap;

public abstract class BaseUserCompy extends BaseOM<UserCompy> {

	private static final long serialVersionUID = 950296176664671153L;

	public static final String TABLE_NAME = "UserCompy";
	public static final String MAPPING_TABLE_NAME = "Company";

	public static final String TABLE_CH_NAME = "�ϥΪ̤��q�������";

	/**
     * Column name for �Ǹ� of the UserCompy
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for �ϥΪ̱b�� of the UserCompy
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ACCOUNTNO = "AccountNo";

	/**
     * Column name for ���q�N�X of the UserCompy
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";
    
    public static final String COLUMN_NAME_COMPANYPARENT = "CompanyParent";


    public static final String CREATE_CMD = "CREATE TABLE "+TABLE_NAME+" ("+
    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
    		COLUMN_NAME_ACCOUNTNO+" TEXT,"+
    		COLUMN_NAME_COMPANYID+" INTEGER,"+
    		COLUMN_NAME_COMPANYPARENT+" TEXT );";
    
    public static final String DROP_CMD = "DROP TABLE IF EXISTS "+TABLE_NAME;

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_USERCOMPY_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, the UserCompy's SerialID
    	COLUMN_NAME_ACCOUNTNO, // Projection position 1, the UserCompy's AccountNo
    	COLUMN_NAME_COMPANYID,  // Projection position 2, the UserCompy's CompanyID
    	COLUMN_NAME_COMPANYPARENT,   // Projection position 3, the UserCompy's CompanyParent
    };
    
    protected static final int READ_USERCOMPY_SERIALID_INDEX = 0;
    protected static final int READ_USERCOMPY_ACCOUNTNO_INDEX = 1;
    protected static final int READ_USERCOMPY_COMPANYID_INDEX = 2;


    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();


    public BaseUserCompy() {
    	// Maps the string "SerialID" to the column name "SerialID"
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);

    	// Maps the string "AccountNo" to the column name "AccountNo"
    	projectionMap.put(COLUMN_NAME_ACCOUNTNO, COLUMN_NAME_ACCOUNTNO);

    	// Maps the string "CompanyID" to the column name "CompanyID"
    	projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
    	
    	projectionMap.put(COLUMN_NAME_COMPANYPARENT, COLUMN_NAME_COMPANYPARENT);
    }

	@Override
    public String getTableName() {
		return TABLE_NAME;
	}
	
	@Override
	public String getCreateCMD() {
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDropCMD() {
		return DROP_CMD;
	}

    private String accountNo;
	private int companyID;
    private long serialID; //key
	private String companyParent;

	public long getSerialID() {
		return serialID;
	}

	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public String getCompanyParent() {
		return companyParent;
	}

	public void setCompanyParent(String companyParent) {
		this.companyParent = companyParent;
	}
	
}
