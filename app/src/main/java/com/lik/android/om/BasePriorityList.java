package com.lik.android.om;

import java.util.HashMap;

public abstract class BasePriorityList extends BaseOM<PriorityList> {

	private static final long serialVersionUID = -4860593389979998778L;

	public static final String TABLE_NAME = "PriorityList";

	public static final String TABLE_CH_NAME = "優先順序資料";

	/**
     * Column name for 序號 of the PriorityList
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

	/**
     * Column name for SiteName of the PriorityList
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SITENAME = "SiteName";

	/**
     * Column name for Priority of the PriorityList
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PRIORITY = "Priority";

    public static final String CREATE_CMD = "CREATE TABLE "+TABLE_NAME+" ("+
    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
    		COLUMN_NAME_SITENAME+" TEXT,"+
    		COLUMN_NAME_PRIORITY+" INTEGER);";
    
    public static final String DROP_CMD = "DROP TABLE IF EXISTS "+TABLE_NAME;

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_PROIRITYLIST_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, the PriorityList's SerialID
    	COLUMN_NAME_SITENAME, // Projection position 1, the PriorityList's SiteName
    	COLUMN_NAME_PRIORITY,  // Projection position 2, the PriorityList's Priority
    };
    protected static final int READ_PROIRITYLIST_SERIALID_INDEX = 0;
    protected static final int READ_PROIRITYLIST_SITENAME_INDEX = 1;
    protected static final int READ_PROIRITYLIST_PRIORITY_INDEX = 2;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();


    public BasePriorityList() {
    	// Maps the string "SerialID" to the column name "SerialID"
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);

    	// Maps the string "SiteName" to the column name "SiteName"
    	projectionMap.put(COLUMN_NAME_SITENAME, COLUMN_NAME_SITENAME);

    	// Maps the string "Priority" to the column name "Priority"
    	projectionMap.put(COLUMN_NAME_PRIORITY, COLUMN_NAME_PRIORITY);
    }
    
    private int serialID;
    private String siteName;
    private int priority;

    public int getSerialID() {
		return serialID;
	}

	public void setSerialID(int serialID) {
		this.serialID = serialID;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
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

}
