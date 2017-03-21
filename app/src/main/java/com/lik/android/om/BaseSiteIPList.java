package com.lik.android.om;

import java.util.HashMap;

public abstract class BaseSiteIPList extends BaseOM<SiteIPList> {

	private static final long serialVersionUID = -8449031549341090058L;
	
	public static final String TABLE_NAME = "SiteIPList";
	
	public static final String TABLE_CH_NAME = "���x�ϥ�IP���";

	public static final String TYPE_DOWNLOAD = "D";
	public static final String TYPE_UPLOAD = "U";
	
	/**
     * Column name for �Ǹ� of the SiteIPList
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

	/**
     * Column name for IP of the SiteIPList
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_IP = "IP";

	/**
     * Column name for SiteName of the SiteIPList
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SITENAME = "SiteName";

	/**
     * Column name for Type of the SiteIPList
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_TYPE = "Type";

	/**
     * Column name for WebPort of the SiteIPList
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_WEBPORT = "WebPort";
    
    public static final String COLUMN_NAME_COMPANYPARENT = "CompanyParent";

    /**
     * Column name for QueuePort of the SiteIPList
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_QUEUEPORT = "QueuePort";
    
    public static final String CREATE_CMD = "CREATE TABLE "+TABLE_NAME+" ("+
    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
    		COLUMN_NAME_IP+" TEXT,"+
    		COLUMN_NAME_SITENAME+" TEXT,"+
    		COLUMN_NAME_TYPE+" TEXT,"+
    		COLUMN_NAME_WEBPORT+" INTEGER,"+
    		COLUMN_NAME_QUEUEPORT+" INTEGER,"+
    		COLUMN_NAME_COMPANYPARENT+" TEXT );";
    
    public static final String DROP_CMD = "DROP TABLE IF EXISTS "+TABLE_NAME;

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_SITEIPLIST_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, the SiteIPList's SerialID
    	COLUMN_NAME_IP, // Projection position 1, the SiteIPList's IP
    	COLUMN_NAME_SITENAME,  // Projection position 2, the SiteIPList's SiteName
    	COLUMN_NAME_TYPE,  // Projection position 3, the SiteIPList's Type
    	COLUMN_NAME_WEBPORT, // Projection position 4, the SiteIPList's WebPort
    	COLUMN_NAME_QUEUEPORT, // Projection position 5, the SiteIPList's QueuePort
    	COLUMN_NAME_COMPANYPARENT // Projection position 6, the SiteIPList's CompanyParent
    };
    protected static final int READ_SITEIPLIST_SERIALID_INDEX = 0;
    protected static final int READ_SITEIPLIST_IP_INDEX = 1;
    protected static final int READ_SITEIPLIST_SITENAME_INDEX = 2;
    protected static final int READ_SITEIPLIST_TYPE_INDEX = 3;
    protected static final int READ_SITEIPLIST_WEBPORT_INDEX = 4;
    protected static final int READ_SITEIPLIST_UEUEPORT_INDEX = 5;
    protected static final int READ_SITEIPLIST_COMPANYPARENT_INDEX = 6;
    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();
	
    public BaseSiteIPList() {
    	// Maps the string "SerialID" to the column name "SerialID"
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);

    	// Maps the string "IP" to the column name "IP"
    	projectionMap.put(COLUMN_NAME_IP, COLUMN_NAME_IP);

    	// Maps the string "SiteName" to the column name "SiteName"
    	projectionMap.put(COLUMN_NAME_SITENAME, COLUMN_NAME_SITENAME);

    	// Maps the string "Type" to the column name "Type"
    	projectionMap.put(COLUMN_NAME_TYPE, COLUMN_NAME_TYPE);

    	// Maps the string "WebPort" to the column name "WebPort"
    	projectionMap.put(COLUMN_NAME_WEBPORT, COLUMN_NAME_WEBPORT);

    	// Maps the string "QueuePort" to the column name "QueuePort"
    	projectionMap.put(COLUMN_NAME_QUEUEPORT, COLUMN_NAME_QUEUEPORT);
    	
    	projectionMap.put(COLUMN_NAME_COMPANYPARENT, COLUMN_NAME_COMPANYPARENT);
    }
    
    private int serialID;
    private String ip;
    private String siteName;
    private String type;
    private int webPort;
    private int queuePort;
    private String companyParent;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getWebPort() {
		return webPort;
	}

	public void setWebPort(int webPort) {
		this.webPort = webPort;
	}

	public int getQueuePort() {
		return queuePort;
	}

	public void setQueuePort(int queuePort) {
		this.queuePort = queuePort;
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
	
	public String getCompanyParent() {
		return companyParent;
	}

	public void setCompanyParent(String companyParent) {
		this.companyParent = companyParent;
	}

}
