package com.lik.android.om;

import java.util.HashMap;

public abstract class BaseSiteInfo extends BaseOM<SiteInfo> {

	private static final long serialVersionUID = -255979313826010543L;
	
	public static final String TABLE_NAME = "SiteInfo";

	public static final String TABLE_CH_NAME = "站台資料";

	/**
     * Column name for SiteName of the SiteInfo
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SITENAME = "SiteName";

	/**
     * Column name for 上層SiteName of the SiteInfo
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PARENT = "Parent";

	/**
     * Column name for 0: 後台中心 1: sub-site 2: tablet of the SiteInfo
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_TYPE = "Type";

    public static final String CREATE_CMD = "CREATE TABLE "+TABLE_NAME+" ("+
    		COLUMN_NAME_SITENAME+" TEXT PRIMARY KEY,"+
    		COLUMN_NAME_PARENT+" TEXT,"+
    		COLUMN_NAME_TYPE+" TEXT);";
    
    public static final String DROP_CMD = "DROP TABLE IF EXISTS "+TABLE_NAME;

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_SYSPROFILE_PROJECTION = new String[] {
    	COLUMN_NAME_SITENAME, // Projection position 0, the SiteInfo's SiteName
    	COLUMN_NAME_PARENT, // Projection position 1, the SiteInfo's Parent
    	COLUMN_NAME_TYPE,  // Projection position 2, the SiteInfo's Type
    };
    protected static final int READ_SITEINFO_SITENAME_INDEX = 0;
    protected static final int READ_SITEINFO_COMPANYID_INDEX = 1;
    protected static final int READ_SITEINFO_SYSTEMNO_INDEX = 2;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();


    public BaseSiteInfo() {
    	// Maps the string "SiteName" to the column name "SiteName"
    	projectionMap.put(COLUMN_NAME_SITENAME, COLUMN_NAME_SITENAME);

    	// Maps the string "Parent" to the column name "Parent"
    	projectionMap.put(COLUMN_NAME_PARENT, COLUMN_NAME_PARENT);

    	// Maps the string "Type" to the column name "Type"
    	projectionMap.put(COLUMN_NAME_TYPE, COLUMN_NAME_TYPE);

    }
    
    private String siteName;
    private String parent;
    private String type;


    public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
