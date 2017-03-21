package com.lik.android.om;

import java.util.HashMap;

import com.lik.android.main.LikDBAdapter;

public abstract class BaseConfigControl extends BaseOM<BaseConfigControl>{

	private static final long serialVersionUID = 6781621790250675720L;
	
	public static final String TABLE_NAME = "ConfigControl";
	
	public static final String COLUMN_NAME_SERIALID = "SerialID";

    public static final String COLUMN_NAME_LOGINFLAG = "LoginFlag";
	
    public static final String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+
    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
    		COLUMN_NAME_LOGINFLAG+" TEXT );";
    
    public static final String DROP_CMD = "DROP TABLE IF EXISTS "+TABLE_NAME;
    
    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_CONFIGCONTROL_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_LOGINFLAG,
    };
    
    protected static final int READ_CONFIGCONTROL_SERIALID_INDEX = 0;
    protected static final int READ_CONFIGCONTROL_COMPANYID_INDEX = 1;
    
    HashMap<String, String> projectionMap = new HashMap<String, String>();
    
    public BaseConfigControl() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_LOGINFLAG, COLUMN_NAME_LOGINFLAG);

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
  	public String getDropCMD() {
  		return DROP_CMD;
  	}

	@Override
	public String[] getCreateIndexCMD() {
		// TODO Auto-generated method stub
		return null;
	}

	  private long serialID; //key
	  private String loginFlag;

	public long getSerialID() {
		return serialID;
	}

	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}
	

}
