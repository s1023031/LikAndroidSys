package com.lik.android.om;

import java.util.Date;
import java.util.HashMap;

public abstract class BaseAccount extends BaseOM<Account> {

	private static final long serialVersionUID = -1347561343110719868L;

	public static final String TABLE_NAME = "Account";

	public static final String TABLE_CH_NAME = "�n�J�b�����";

	/**
     * Column name for �ϥΪ̱b�� of the Account
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ACCOUNTNO = "AccountNo";

    /**
     * Column name for �ʶR���t�ΥN�X of the Account
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PASSWORD = "Password";

	/**
     * Column name for �Ǹ� of the Account for foreign key of SysProfile
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

	/**
     * Column name for �~�ȴM�� of the Account
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_LOOK_MAPTRACK = "LOOK_MAPTRACK";

    /**
     * Column name for �D�� of the Account
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_BOSS_USERNO = "BOSS_USERNO";

	/**
     * Column name for �ϥΪ̱b�� of the Account
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ACCOUNTNAME = "AccountName";
    
    public static final String COLUMN_NAME_COMPANYPARENT = "CompanyParent";

    /**
     * Column name for �W����s��� of the Account
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_LASTMODIFIEDDATE = "LastModifiedDate";

    public static final String CREATE_CMD = "CREATE TABLE "+TABLE_NAME+" ("+
    		COLUMN_NAME_ACCOUNTNO+" TEXT,"+
    		COLUMN_NAME_PASSWORD+" TEXT,"+
    		COLUMN_NAME_SERIALID+" INTEGER  PRIMARY KEY,"+
    		COLUMN_NAME_LASTMODIFIEDDATE+" TEXT,"+
    		COLUMN_NAME_LOOK_MAPTRACK+" TEXT,"+
    		COLUMN_NAME_BOSS_USERNO+" TEXT,"+
    		COLUMN_NAME_ACCOUNTNAME+" TEXT,"+
    		COLUMN_NAME_COMPANYPARENT+" TEXT);";
    
    public static final String DROP_CMD = "DROP TABLE IF EXISTS "+TABLE_NAME;

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_ACCOUNT_PROJECTION = new String[] {
    	COLUMN_NAME_ACCOUNTNO, // Projection position 0, the Account's AccountNo
    	COLUMN_NAME_PASSWORD,  // Projection position 1, the Account's Password
    	COLUMN_NAME_SERIALID,  // Projection position 2, the Account's Password
    	COLUMN_NAME_LASTMODIFIEDDATE,  // Projection position 3, the Account's LastModifiedDate
    	COLUMN_NAME_LOOK_MAPTRACK,  // Projection position 4, the Users LOOK_MAPTRACK
    	COLUMN_NAME_BOSS_USERNO,  // Projection position 5, the Users BOSS_USERNO
    	COLUMN_NAME_ACCOUNTNAME,  // Projection position 6, the Users ACCOUNTNAME
    	COLUMN_NAME_COMPANYPARENT
    };
    protected static final int READ_ACCOUNT_ACCOUNTNO_INDEX = 0;
    protected static final int READ_ACCOUNT_PASSWORD_INDEX = 1;
    protected static final int READ_ACCOUNT_SERIALID_INDEX = 2;
    protected static final int READ_ACCOUNT_LASTMODIFIEDDATE_INDEX = 3;
    protected static final int READ_ACCOUNT_LOOK_MAPTRACK_INDEX = 4;
    protected static final int READ_ACCOUNT_BOSS_USERNO_INDEX = 5;
    protected static final int READ_ACCOUNT_ACCOUNTNAME_INDEX = 6;
    protected static final int READ_ACCOUNT_COMPANYPARENT_INDEX = 7;
    
    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseAccount() {
    	// Maps the string "CompanyID" to the column name "CompanyID"
    	projectionMap.put(COLUMN_NAME_ACCOUNTNO, COLUMN_NAME_ACCOUNTNO);

    	// Maps the string "SystemNo" to the column name "SystemNo"
    	projectionMap.put(COLUMN_NAME_PASSWORD, COLUMN_NAME_PASSWORD);

    	// Maps the string "SerialID" to the column name "SerialID"
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);

    	// Maps the string "LastModifiedDate" to the column name "LastModifiedDate"
    	projectionMap.put(COLUMN_NAME_LASTMODIFIEDDATE, COLUMN_NAME_LASTMODIFIEDDATE);

    	// Maps the string "LOOK_MAPTRACK" to the column name "LOOK_MAPTRACK"
    	projectionMap.put(COLUMN_NAME_LOOK_MAPTRACK, COLUMN_NAME_LOOK_MAPTRACK);

    	// Maps the string "BOSS_USERNO" to the column name "BOSS_USERNO"
    	projectionMap.put(COLUMN_NAME_BOSS_USERNO, COLUMN_NAME_BOSS_USERNO);

    	// Maps the string "COLUMN_NAME_ACCOUNTNAME" to the column name "COLUMN_NAME_ACCOUNTNAME"
    	projectionMap.put(COLUMN_NAME_ACCOUNTNAME, COLUMN_NAME_ACCOUNTNAME);
    	
    	projectionMap.put(COLUMN_NAME_COMPANYPARENT, COLUMN_NAME_COMPANYPARENT);
    }

    private String accountNo;
    private String password;
    private long serialID;
    private Date lastModifiedDate;
	private String LOOK_MAPTRACK;
	private String BOSS_USERNO;
    private String accountName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
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
		return null;
	}

	@Override
	public String getDropCMD() {
		return DROP_CMD;
	}

	public String getLOOK_MAPTRACK() {
		return LOOK_MAPTRACK;
	}

	public void setLOOK_MAPTRACK(String lOOK_MAPTRACK) {
		LOOK_MAPTRACK = lOOK_MAPTRACK;
	}

	public String getBOSS_USERNO() {
		return BOSS_USERNO;
	}

	public void setBOSS_USERNO(String bOSS_USERNO) {
		BOSS_USERNO = bOSS_USERNO;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCompanyParent() {
		return companyParent;
	}

	public void setCompanyParent(String companyParent) {
		this.companyParent = companyParent;
	}
	
	
}
