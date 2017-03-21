package com.lik.android.om;

import java.util.Date;
import java.util.HashMap;

import com.lik.android.main.MainMenuActivity;

public abstract class BaseResPict extends BaseOM<ResPict>{

	private static final long serialVersionUID = 773587438873367620L;
	
	public static final String TABLE_NAME = "RestPict";

	public static final String COLUMN_NAME_SERIALID = "SerialID";
	public static final String COLUMN_NAME_PNO = "Pno";
	public static final String COLUMN_NAME_TYPE = "type";
	public static final String COLUMN_NAME_DATE = "Date";
	public static final String COLUMN_NAME_BASE64 = "Base64";
	public static final String COLUMN_NAME_PATH = "Path";
	
	
	   /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_RESPICT_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_PNO,  // Projection position 1, 
    	COLUMN_NAME_TYPE, // Projection position 2, 
    	COLUMN_NAME_DATE, // Projection position 3, 
    	COLUMN_NAME_BASE64, // Projection position 4, 
    	COLUMN_NAME_PATH, // Projection position 5,
    };
    
    protected static final int READ_RESPICT_SERIALID_INDEX = 0;
    protected static final int READ_RESPICT_PNO_INDEX = 1;
    protected static final int READ_RESPICT_TYPE_INDEX = 2;
    protected static final int READ_RESPICT_DATE_INDEX = 3;
    protected static final int READ_RESPICT_BASE64_INDEX = 4;
    protected static final int READ_RESPICT_PATH_INDEX = 5;
    
    // Creates a new projection map instance. The map returns a column name
   	// given a string. The two are usually equal.
   	HashMap<String, String> projectionMap = new HashMap<String, String>();
    
    public BaseResPict() 
    {
	      projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
	      projectionMap.put(COLUMN_NAME_PNO, COLUMN_NAME_PNO);
	      projectionMap.put(COLUMN_NAME_TYPE, COLUMN_NAME_TYPE);
	      projectionMap.put(COLUMN_NAME_DATE, COLUMN_NAME_DATE);
	      projectionMap.put(COLUMN_NAME_BASE64, COLUMN_NAME_BASE64);
	      projectionMap.put(COLUMN_NAME_PATH, COLUMN_NAME_PATH);
    }
    
    @Override
	public String getTableName() {
    	setTableCompanyID(0);
		setCompanyParent(null);
		if(getTableCompanyID()==0) 
    		setTableCompanyID(MainMenuActivity.companyID);
    	if(getCompanyParent()==null)
    		setCompanyParent(MainMenuActivity.companyParent);
    	return TABLE_NAME+"_"+getCompanyParent()+getTableCompanyID();                                                                                                                                                                                                                                    
	}

	@Override
	public String getCreateCMD() {
		String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
	    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
	    		COLUMN_NAME_PNO+"  VARCHAR(10),"+
	    		COLUMN_NAME_TYPE+" VARCHAR(10),"+
	    		COLUMN_NAME_DATE+" TEXT,"+
	    		COLUMN_NAME_BASE64+" BLOB,"+
	    		COLUMN_NAME_PATH+" TEXT)";
	    		 
		return CREATE_CMD;
	}
	
	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+COLUMN_NAME_SERIALID+");",
		    };
		return CREATE_INDEX_CMD;
	}

	@Override
	public String getDropCMD() {
		String DROP_CMD = "DROP TABLE IF EXISTS "+getTableName();
		return DROP_CMD;
	}
	
	private long serialID; //key
	private String pNO;
	private String type;
	private String Date;
	private byte[] base64;
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSerialID() {
		return serialID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}

	public String getpNO() {
		return pNO;
	}

	public void setpNO(String pNO) {
		this.pNO = pNO;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public byte[] getBase64() {
		return base64;
	}

	public void setBase64(byte[] base64) {
		this.base64 = base64;
	}
	
	

	

}
