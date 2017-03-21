package com.lik.android.om;

import java.util.Date;
import java.util.HashMap;

public abstract class BaseSysProfile extends BaseOM<SysProfile> {

	private static final long serialVersionUID = -6066138403477332227L;

	public static final String TABLE_NAME = "SysProfile";
	
	public static final String TABLE_CH_NAME = "�t��profile���";
	
	public static final String VERSIONINFO_1 = "1"; // 1:�椽�q��
	public static final String VERSIONINFO_2 = "2"; // 2:�h���q��
	public static final String STOCKINFO_R = "R"; // R:realtime stock
	public static final String STOCKINFO_N = "N"; // N:non-realtime stock
	public static final String CAMERAINFO_Y = "Y"; // Y:has camera
	public static final String CAMERAINFO_N = "N"; // N:no camera
	public static final String MAPINFO_Y = "Y"; // Y:has map
	public static final String MAPINFO_N = "N"; // N:no map
	public static final String TELEPHONEINFO_Y = "Y"; // Y:has tel 
	public static final String TELEPHONEINFO_N = "N"; // N:no tel
	public static final String MAPTRACKERINFO_Y = "Y"; // Y:has map tracker
	public static final String MAPTRACKERINFO_N = "N"; // N:no map tracker
	public static final String BUTTONALIGN_L = "L"; // left align
	public static final String BUTTONALIGN_R = "R"; // right align
	public static final String INSTANTMESSENGERINFO_Y = "Y"; // Y:has IM
	public static final String INSTANTMESSENGERINFO_N = "N"; // N:no IM
	public static final String WEBISSECURE_Y = "Y"; // Y:yes https
	public static final String WEBISSECURE_N = "N"; // N:no http

	/**
     * Column name for �Ǹ� of the SysProfile
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for ���q�N�X of the SysProfile
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_COMPANYNO = "CompanyNo";

    /**
     * Column name for �ʶR���t�ΥN�X of the SysProfile
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SYSTEMNO = "SystemNo";

    /**
     * Column name for pdaid of the SysProfile
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PDAID = "PdaId";

    /**
     * Column name for VersionInfo of the SysProfile
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_VERSIONINFO = "VersionInfo";

    /**
     * Column name for StockInfo of the SysProfile
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_STOCKINFO = "StockInfo";

    /**
     * Column name for CameraInfo of the SysProfile
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CAMERAINFO = "CameraInfo";

    /**
     * Column name for TelephoneInfo of the SysProfile
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_TELEPHONEINFO = "TelephoneInfo";

    /**
     * Column name for MapInfo of the SysProfile
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_MAPINFO = "MapInfo";

    /**
     * Column name for MapTrackerInfo of the SysProfile
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_MAPTRACKERINFO = "MapTrackerInfo";

    /**
     * Column name for HistoryPeriod of the SysProfile
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_HISTORYPERIOD = "HistoryPeriod";

    /**
     * Column name for ButtonAlign of the SysProfile
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_BUTTONALIGN = "ButtonAlign";

    /**
     * Column name for ButtonAlign of the SysProfile
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_DOWNLOADMINUTE = "DownLoadMinute";

    /**
     * Column name for InstantMessengerInfo of the SysProfile
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_INSTANTMESSENGERINFO = "InstantMessengerInfo";
    
    /**
     * Column name for �W����s��� of the SysProfile
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_LASTMODIFIEDDATE = "LastModifiedDate";

    /**
     * Column name for WebIsSecure of the SysProfile
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_WEBISSECURE = "WebIsSecure";
    
    /**
     * Column name for WebURL of the SysProfile
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_WEBURL = "WebURL";
    
    public static final String COLUMN_NAME_COMPANYNAME = "CompanyName";
    
    /**
     * Column name for QueuePort of the SysProfile
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_QUEUEPORT = "QueuePort";

    public static final String CREATE_CMD = "CREATE TABLE "+TABLE_NAME+" ("+
    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
    		COLUMN_NAME_COMPANYNO+" TEXT,"+
    		COLUMN_NAME_SYSTEMNO+" TEXT,"+
    		COLUMN_NAME_PDAID+" INTEGER,"+
    		COLUMN_NAME_VERSIONINFO+" TEXT,"+
    		COLUMN_NAME_STOCKINFO+" TEXT,"+
    		COLUMN_NAME_CAMERAINFO+" TEXT,"+
    		COLUMN_NAME_TELEPHONEINFO+" TEXT,"+
    		COLUMN_NAME_MAPINFO+" TEXT,"+
    		COLUMN_NAME_MAPTRACKERINFO+" TEXT,"+
    		COLUMN_NAME_HISTORYPERIOD+" INTEGER,"+
    		COLUMN_NAME_BUTTONALIGN+" TEXT,"+
    		COLUMN_NAME_DOWNLOADMINUTE+" INTEGER,"+
    		COLUMN_NAME_INSTANTMESSENGERINFO+" TEXT,"+
    		COLUMN_NAME_LASTMODIFIEDDATE+" TEXT,"+
    		COLUMN_NAME_WEBISSECURE+" TEXT,"+
    		COLUMN_NAME_WEBURL+" TEXT,"+
    		COLUMN_NAME_QUEUEPORT+" INTEGER,"+
    		COLUMN_NAME_COMPANYNAME+" TEXT);";
    
    public static final String DROP_CMD = "DROP TABLE IF EXISTS "+TABLE_NAME;

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_SYSPROFILE_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, the SysProfile's SerialID
    	COLUMN_NAME_COMPANYNO, // Projection position 1, the SysProfile's CompanyID
    	COLUMN_NAME_SYSTEMNO,  // Projection position 2, the SysProfile's SystemNo
    	COLUMN_NAME_PDAID, // Projection position 3, the SysProfile's PdaId
    	COLUMN_NAME_VERSIONINFO, // Projection position 4, the SysProfile's VersionInfo
    	COLUMN_NAME_STOCKINFO, // Projection position 5, the SysProfile's StockInfo
    	COLUMN_NAME_CAMERAINFO, // Projection position 6, the SysProfile's CameraInfo
    	COLUMN_NAME_TELEPHONEINFO, // Projection position 7, the SysProfile's TelephoneInfo
    	COLUMN_NAME_MAPINFO, // Projection position 8, the SysProfile's MapInfo
    	COLUMN_NAME_MAPTRACKERINFO, // Projection position 9, the SysProfile's MapInfo
    	COLUMN_NAME_HISTORYPERIOD, // Projection position 10, the SysProfile's HistoryPeriod
    	COLUMN_NAME_BUTTONALIGN, // Projection position 11, the SysProfile's HistoryPeriod
    	COLUMN_NAME_DOWNLOADMINUTE, // Projection position 12, the SysProfile's HistoryPeriod
    	COLUMN_NAME_INSTANTMESSENGERINFO, // Projection position 13, the SysProfile's MapInfo
    	COLUMN_NAME_LASTMODIFIEDDATE,  // Projection position 14, the SysProfile's LastModifiedDate
    	COLUMN_NAME_WEBISSECURE, // Projection position 15, the SysProfile's MapInfo
    	COLUMN_NAME_WEBURL, // Projection position 16, the SysProfile's MapInfo
    	COLUMN_NAME_QUEUEPORT , // Projection position 17, the SysProfile's MapInfo
    	COLUMN_NAME_COMPANYNAME // Projection position 18, the SysProfile's MapInfo
    };
    protected static final int READ_SYSPROFILE_SERIALID_INDEX = 0;
    protected static final int READ_SYSPROFILE_COMPANYNO_INDEX = 1;
    protected static final int READ_SYSPROFILE_SYSTEMNO_INDEX = 2;
    protected static final int READ_SYSPROFILE_PDAID_INDEX = 3;
    protected static final int READ_SYSPROFILE_VERSIONINFO_INDEX = 4;
    protected static final int READ_SYSPROFILE_STOCKINFO_INDEX = 5;
    protected static final int READ_SYSPROFILE_CAMERAINFO_INDEX = 6;
    protected static final int READ_SYSPROFILE_TELEPHONEINFO_INDEX = 7;
    protected static final int READ_SYSPROFILE_MAPINFO_INDEX = 8;
    protected static final int READ_SYSPROFILE_MAPTRACKERINFO_INDEX = 9;
    protected static final int READ_SYSPROFILE_HISTORYPERIOD_INDEX = 10;
    protected static final int READ_SYSPROFILE_BUTTONALIGN_INDEX = 11;
    protected static final int READ_SYSPROFILE_DOWNLOADMINUTE_INDEX = 12;
    protected static final int READ_SYSPROFILE_INSTANTMESSENGERINFO_INDEX = 13;
    protected static final int READ_SYSPROFILE_LASTMODIFIEDDATE_INDEX = 14;
    protected static final int READ_SYSPROFILE_WEBISSECURE_INDEX = 15;
    protected static final int READ_SYSPROFILE_WEBURL_INDEX = 16;
    protected static final int READ_SYSPROFILE_QUEUEPORT_INDEX = 17;
    protected static final int READ_SYSPROFILE_COMPANYNAME_INDEX = 18;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();


    public BaseSysProfile() {
    	// Maps the string "SerialID" to the column name "SerialID"
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);

    	// Maps the string "CompanyNo" to the column name "CompanyNo"
    	projectionMap.put(COLUMN_NAME_COMPANYNO, COLUMN_NAME_COMPANYNO);

    	// Maps the string "SystemNo" to the column name "SystemNo"
    	projectionMap.put(COLUMN_NAME_SYSTEMNO, COLUMN_NAME_SYSTEMNO);

    	// Maps the string "PdaId" to the column name "PdaId"
    	projectionMap.put(COLUMN_NAME_PDAID, COLUMN_NAME_PDAID);

    	// Maps the string "VersionInfo" to the column name "VersionInfo"
    	projectionMap.put(COLUMN_NAME_VERSIONINFO, COLUMN_NAME_VERSIONINFO);

    	// Maps the string "StockInfo" to the column name "StockInfo"
    	projectionMap.put(COLUMN_NAME_STOCKINFO, COLUMN_NAME_STOCKINFO);

    	// Maps the string "CameraInfo" to the column name "CameraInfo"
    	projectionMap.put(COLUMN_NAME_CAMERAINFO, COLUMN_NAME_CAMERAINFO);

    	// Maps the string "TelephoneInfo" to the column name "TelephoneInfo"
    	projectionMap.put(COLUMN_NAME_TELEPHONEINFO, COLUMN_NAME_TELEPHONEINFO);

    	// Maps the string "MapInfo" to the column name "MapInfo"
    	projectionMap.put(COLUMN_NAME_MAPINFO, COLUMN_NAME_MAPINFO);

    	// Maps the string "MapTrackerInfo" to the column name "MapTrackerInfo"
    	projectionMap.put(COLUMN_NAME_MAPTRACKERINFO, COLUMN_NAME_MAPTRACKERINFO);

    	// Maps the string "HistoryPeriod" to the column name "HistoryPeriod"
    	projectionMap.put(COLUMN_NAME_HISTORYPERIOD, COLUMN_NAME_HISTORYPERIOD);

    	// Maps the string "ButtonAlign" to the column name "ButtonAlign"
    	projectionMap.put(COLUMN_NAME_BUTTONALIGN, COLUMN_NAME_BUTTONALIGN);

    	// Maps the string "DownLoadMinute" to the column name "DownLoadMinute"
    	projectionMap.put(COLUMN_NAME_DOWNLOADMINUTE, COLUMN_NAME_DOWNLOADMINUTE);

    	// Maps the string "InstantMessengerInfo" to the column name "InstantMessengerInfo"
    	projectionMap.put(COLUMN_NAME_INSTANTMESSENGERINFO, COLUMN_NAME_INSTANTMESSENGERINFO);

    	// Maps the string "LastModifiedDate" to the column name "LastModifiedDate"
    	projectionMap.put(COLUMN_NAME_LASTMODIFIEDDATE, COLUMN_NAME_LASTMODIFIEDDATE);

    	// Maps the string "WebIsSecure" to the column name "WebIsSecure"
    	projectionMap.put(COLUMN_NAME_WEBISSECURE, COLUMN_NAME_WEBISSECURE);

    	// Maps the string "WebURL" to the column name "WebURL"
    	projectionMap.put(COLUMN_NAME_WEBURL, COLUMN_NAME_WEBURL);
    	
    	// Maps the string "QueuePort" to the column name "QueuePort"
    	projectionMap.put(COLUMN_NAME_QUEUEPORT, COLUMN_NAME_QUEUEPORT);
    	
    	// Maps the string "QueuePort" to the column name "QueuePort"
    	projectionMap.put(COLUMN_NAME_COMPANYNAME, COLUMN_NAME_COMPANYNAME);

    }

    private String companyNo;
    private String systemNo;
    private Date lastModifiedDate;
    private int serialID; //key
    private int pdaId;
    private String versionInfo;
    private int historyPeriod;
    private String stockInfo;
    private String cameraInfo;
    private String telephoneInfo;
    private String mapInfo;
    private String mapTrackerInfo;
    private String buttonAlign;
    private int downLoadMinute;
    private String instantMessengerInfo;
    private String webIsSecure;
    private String webURL;
    private int queuePort;
    private String companyName;

    public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getSerialID() {
		return serialID;
	}
	public void setSerialID(int serialID) {
		this.serialID = serialID;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getCompanyNo() {
		return companyNo;
	}
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}
	public String getSystemNo() {
		return systemNo;
	}
	public void setSystemNo(String systemNo) {
		this.systemNo = systemNo;
	}    	
	public int getPdaId() {
		return pdaId;
	}
	public void setPdaId(int pdaId) {
		this.pdaId = pdaId;
	}	
	public String getVersionInfo() {
		return versionInfo;
	}
	public void setVersionInfo(String versionInfo) {
		this.versionInfo = versionInfo;
	}
	public String getStockInfo() {
		return stockInfo;
	}
	public void setStockInfo(String stockInfo) {
		this.stockInfo = stockInfo;
	}
	public int getHistoryPeriod() {
		return historyPeriod;
	}
	public void setHistoryPeriod(int historyPeriod) {
		this.historyPeriod = historyPeriod;
	}	
	public String getCameraInfo() {
		return cameraInfo;
	}
	public void setCameraInfo(String cameraInfo) {
		this.cameraInfo = cameraInfo;
	}
	public String getTelephoneInfo() {
		return telephoneInfo;
	}
	public void setTelephoneInfo(String telephoneInfo) {
		this.telephoneInfo = telephoneInfo;
	}
	public String getMapInfo() {
		return mapInfo;
	}
	public void setMapInfo(String mapInfo) {
		this.mapInfo = mapInfo;
	}
	
	public String getMapTrackerInfo() {
		return mapTrackerInfo;
	}
	public void setMapTrackerInfo(String mapTrackerInfo) {
		this.mapTrackerInfo = mapTrackerInfo;
	}
	public String getButtonAlign() {
		return buttonAlign;
	}
	public void setButtonAlign(String buttonAlign) {
		this.buttonAlign = buttonAlign;
	}
	public int getDownLoadMinute() {
		return downLoadMinute;
	}
	public void setDownLoadMinute(int downLoadMinute) {
		this.downLoadMinute = downLoadMinute;
	}
	public String getInstantMessengerInfo() {
		return instantMessengerInfo;
	}
	public void setInstantMessengerInfo(String instantMessengerInfo) {
		this.instantMessengerInfo = instantMessengerInfo;
	}
	public String getWebIsSecure() {
		return webIsSecure;
	}
	public void setWebIsSecure(String webIsSecure) {
		this.webIsSecure = webIsSecure;
	}
	public String getWebURL() {
		return webURL;
	}
	public void setWebURL(String webURL) {
		this.webURL = webURL;
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
		return null;
	}

	@Override
	public String getDropCMD() {
		return DROP_CMD;
	}
}
