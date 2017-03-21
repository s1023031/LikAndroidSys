package com.lik.android.om;

import java.util.HashMap;

public abstract class BaseUsers extends BaseOM<Users> {

	private static final long serialVersionUID = 950296176664671153L;

	public static final String TABLE_NAME = "Users";

	public static final String TABLE_CH_NAME = "業務員帳號姓名資料";

	/**
     * Column name for 序號 of the Users
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for 使用者帳號 of the Users
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_USER_NO = "USER_NO";

	/**
     * Column name for 姓名 of the Users
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_USER_NAME = "USER_NAME";

	/**
     * Column name for 業務尋蹤 of the Users
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_LOOK_MAPTRACK = "LOOK_MAPTRACK";

    /**
     * Column name for 主管 of the Users
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_BOSS_USERNO = "BOSS_USERNO";

    /**
     * Column name for 版本 of the Users
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_VERSIONNO = "VersionNo";

    public static final String CREATE_CMD = "CREATE TABLE "+TABLE_NAME+" ("+
    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
    		COLUMN_NAME_USER_NO+" TEXT,"+
    		COLUMN_NAME_USER_NAME+" TEXT,"+
    		COLUMN_NAME_LOOK_MAPTRACK+" TEXT,"+
    		COLUMN_NAME_BOSS_USERNO+" TEXT);";
    
    public static final String[] CREATE_INDEX_CMD = {
	    	"CREATE  INDEX IF NOT EXISTS "+TABLE_NAME+"P1 ON "+TABLE_NAME+" ("+COLUMN_NAME_USER_NO+");",
	    	"CREATE  INDEX IF NOT EXISTS "+TABLE_NAME+"P2 ON "+TABLE_NAME+" ("+COLUMN_NAME_LOOK_MAPTRACK+");",
	    	"CREATE  INDEX IF NOT EXISTS "+TABLE_NAME+"P3 ON "+TABLE_NAME+" ("+COLUMN_NAME_BOSS_USERNO+");",
	    };

    public static final String DROP_CMD = "DROP TABLE IF EXISTS "+TABLE_NAME;

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_USERS_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, the Users SerialID
    	COLUMN_NAME_USER_NO, // Projection position 1, the Users USER_NO
    	COLUMN_NAME_USER_NAME,  // Projection position 2, the Users USER_NAME
    	COLUMN_NAME_LOOK_MAPTRACK,  // Projection position 3, the Users LOOK_MAPTRACK
    	COLUMN_NAME_BOSS_USERNO  // Projection position 4, the Users BOSS_USERNO
    };
    protected static final int READ_USERS_SERIALID_INDEX = 0;
    protected static final int READ_USERS_USER_NO_INDEX = 1;
    protected static final int READ_USERS_USER_NAME_INDEX = 2;
    protected static final int READ_USERS_LOOK_MAPTRACK_INDEX = 3;
    protected static final int READ_USERS_BOSS_USERNO_INDEX = 4;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();


    public BaseUsers() {
    	// Maps the string "SerialID" to the column name "SerialID"
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);

    	// Maps the string "USER_NO" to the column name "USER_NO"
    	projectionMap.put(COLUMN_NAME_USER_NO, COLUMN_NAME_USER_NO);

    	// Maps the string "USER_NAME" to the column name "USER_NAME"
    	projectionMap.put(COLUMN_NAME_USER_NAME, COLUMN_NAME_USER_NAME);

    	// Maps the string "LOOK_MAPTRACK" to the column name "LOOK_MAPTRACK"
    	projectionMap.put(COLUMN_NAME_LOOK_MAPTRACK, COLUMN_NAME_LOOK_MAPTRACK);

    	// Maps the string "BOSS_USERNO" to the column name "BOSS_USERNO"
    	projectionMap.put(COLUMN_NAME_BOSS_USERNO, COLUMN_NAME_BOSS_USERNO);

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
		return CREATE_INDEX_CMD;
	}

	@Override
	public String getDropCMD() {
		return DROP_CMD;
	}

    private String USER_NO;
    private long serialID; //key
	private String USER_NAME;
	private String LOOK_MAPTRACK;
	private String BOSS_USERNO;


	public String getUSER_NO() {
		return USER_NO;
	}

	public void setUSER_NO(String uSER_NO) {
		USER_NO = uSER_NO;
	}

	public long getSerialID() {
		return serialID;
	}

	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
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

	
}
