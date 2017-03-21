package com.lik.android.om;

import java.util.Date;
import java.util.HashMap;

import com.lik.android.main.MainMenuActivity;

public abstract class BaseSalesNote extends BaseOM<SalesNote> {

	private static final long serialVersionUID = -6866205197337763691L;

	public static final String TABLE_NAME = "SalesNote";
	public static final String TABLE_CH_NAME = "�~�ȳ��i";
	
	/**
     * Column name for �Ǹ� of the SalesNote
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for userNo of the SalesNote
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_REPORTKEY = "ReportKey";

    /**
     * Column name for userNo of the SalesNote
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_USERNO = "UserNo";

    /**
     * Column name for �T�����e of the SalesNote
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_NOTE = "Note";

    /**
     * Column name for �T���o�G�ɶ� of the SalesNote
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ISSUETIME = "IssueTime";

    /**
     * Column name for �T���o�G�ɶ� of the SalesNote
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CUSTOMERID = "CustomerID";

    /**
     * Column name for DeliverOrder of the Customers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DELIVERORDER = "DeliverOrder";

    /**
     * Column name for �O�_�W�� of the SalesNote
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ISUPLOAD = "isUpload";

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_SALESNOTE_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_REPORTKEY, // Projection position 1, 
    	COLUMN_NAME_USERNO, // Projection position 2, 
    	COLUMN_NAME_NOTE, // Projection position 3, 
    	COLUMN_NAME_ISSUETIME, // Projection position 4, 
    	COLUMN_NAME_CUSTOMERID, // Projection position 5, 
    	COLUMN_NAME_DELIVERORDER, // Projection position 6, 
    	COLUMN_NAME_ISUPLOAD, // Projection position 7, 
    };
    protected static final int READ_SALESNOTE_SERIALID_INDEX = 0;
    protected static final int READ_SALESNOTE_REPORTKEY_INDEX = 1;
    protected static final int READ_SALESNOTE_USERNO_INDEX = 2;
    protected static final int READ_SALESNOTE_NOTE_INDEX = 3;
    protected static final int READ_SALESNOTE_ISSUETIME_INDEX = 4;
    protected static final int READ_SALESNOTE_CUSTOMERID_INDEX = 5;
    protected static final int READ_SALESNOTE_DELIVERORDER_INDEX = 6;
    protected static final int READ_SALESNOTE_ISUPLOAD_INDEX = 7;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseSalesNote() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_REPORTKEY, COLUMN_NAME_REPORTKEY);
    	projectionMap.put(COLUMN_NAME_USERNO, COLUMN_NAME_USERNO);
    	projectionMap.put(COLUMN_NAME_NOTE, COLUMN_NAME_NOTE);
    	projectionMap.put(COLUMN_NAME_ISSUETIME, COLUMN_NAME_ISSUETIME);
    	projectionMap.put(COLUMN_NAME_CUSTOMERID, COLUMN_NAME_CUSTOMERID);
    	projectionMap.put(COLUMN_NAME_DELIVERORDER, COLUMN_NAME_DELIVERORDER);
    	projectionMap.put(COLUMN_NAME_ISUPLOAD, COLUMN_NAME_ISUPLOAD);
    }

    @Override
	public String getTableName() {
		return TABLE_NAME+"_"+MainMenuActivity.omCurrentSysProfile.getCompanyNo()+getTableCompanyID();
	}

	@Override
	public String getCreateCMD() {
		String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
	    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
	    		COLUMN_NAME_REPORTKEY+" TEXT,"+
	    		COLUMN_NAME_USERNO+" TEXT,"+
	    		COLUMN_NAME_NOTE+" TEXT,"+
	    		COLUMN_NAME_ISSUETIME+" TEXT,"+
	    		COLUMN_NAME_CUSTOMERID+" INTEGER,"+
	    		COLUMN_NAME_DELIVERORDER+" INTEGER,"+
	    		COLUMN_NAME_ISUPLOAD+" INTEGER);";
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+COLUMN_NAME_REPORTKEY+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P2 ON "+getTableName()+" ("+COLUMN_NAME_USERNO+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P3 ON "+getTableName()+" ("+COLUMN_NAME_ISSUETIME+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P4 ON "+getTableName()+" ("+COLUMN_NAME_CUSTOMERID+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P5 ON "+getTableName()+" ("+COLUMN_NAME_DELIVERORDER+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P6 ON "+getTableName()+" ("+COLUMN_NAME_ISUPLOAD+");",
		    };
		return CREATE_INDEX_CMD;
	}

	@Override
	public String getDropCMD() {
		String DROP_CMD = "DROP TABLE IF EXISTS "+getTableName();
		return DROP_CMD;
	}

    private long serialID; //key
    private String reportKey;
    private String userNo;
    private String note;
    private Date issueTime;
    private int customerID;
    private Integer deliverOrder;
    private boolean isUpload;

	public long getSerialID() {
		return serialID;
	}

	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}

	public String getReportKey() {
		return reportKey;
	}

	public void setReportKey(String reportKey) {
		this.reportKey = reportKey;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public Integer getDeliverOrder() {
		return deliverOrder;
	}

	public void setDeliverOrder(Integer deliverOrder) {
		this.deliverOrder = deliverOrder;
	}

	public boolean isUpload() {
		return isUpload;
	}

	public void setUpload(boolean isUpload) {
		this.isUpload = isUpload;
	}

    
}
