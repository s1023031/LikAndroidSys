package com.lik.android.om;

import java.util.Date;
import java.util.HashMap;

import com.lik.android.main.MainMenuActivity;

public abstract class BaseInstantMessages extends BaseOM<InstantMessages> {

	private static final long serialVersionUID = -6866205197337763690L;

	public static final String TABLE_NAME = "InstantMessages";
	public static final String TABLE_CH_NAME = "即時訊息";
	
	/**
     * Column name for 序號 of the InstantMessages
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for userNo of the InstantMessages
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_USERNO = "UserNo";

    /**
     * Column name for �T�����e of the InstantMessages
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CONTENT = "Content";

    /**
     * Column name for �T���o�G�ɶ� of the InstantMessages
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PUBLISHTIME = "PublishTime";

    /**
     * Column name for �T������ɶ� of the InstantMessages
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_RECEIVETIME = "ReceiveTime";

    /**
     * Column name for �o�G�H of the InstantMessages
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_OWNER = "Owner";

    /**
     * Column name for �D�� of the InstantMessages
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SUBJECT = "Subject";

    /**
     * Column name for �O�_Ū�L of the InstantMessages
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ISREAD = "IsRead";

    /**
     * Column name for �O�_Ū�L of the InstantMessages
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_MESSAGEKEY = "MessageKey";

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_INSTANTMESSAGES_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_USERNO, // Projection position 1, 
    	COLUMN_NAME_CONTENT, // Projection position 2, 
    	COLUMN_NAME_PUBLISHTIME, // Projection position 3, 
    	COLUMN_NAME_RECEIVETIME, // Projection position 4, 
    	COLUMN_NAME_OWNER, // Projection position 5, 
    	COLUMN_NAME_SUBJECT, // Projection position 6, 
    	COLUMN_NAME_ISREAD, // Projection position 7, 
    	COLUMN_NAME_MESSAGEKEY, // Projection position 8, 
    };
    protected static final int READ_INSTANTMESSAGES_SERIALID_INDEX = 0;
    protected static final int READ_INSTANTMESSAGES_USERNO_INDEX = 1;
    protected static final int READ_INSTANTMESSAGES_CONTENT_INDEX = 2;
    protected static final int READ_INSTANTMESSAGES_PUBLISHTIME_INDEX = 3;
    protected static final int READ_INSTANTMESSAGES_RECEIVETIME_INDEX = 4;
    protected static final int READ_INSTANTMESSAGES_OWNER_INDEX = 5;
    protected static final int READ_INSTANTMESSAGES_SUBJECT_INDEX = 6;
    protected static final int READ_INSTANTMESSAGES_ISREAD_INDEX = 7;
    protected static final int READ_INSTANTMESSAGES_MESSAGEKEY_INDEX = 8;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseInstantMessages() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_USERNO, COLUMN_NAME_USERNO);
    	projectionMap.put(COLUMN_NAME_CONTENT, COLUMN_NAME_CONTENT);
    	projectionMap.put(COLUMN_NAME_PUBLISHTIME, COLUMN_NAME_PUBLISHTIME);
    	projectionMap.put(COLUMN_NAME_RECEIVETIME, COLUMN_NAME_RECEIVETIME);
    	projectionMap.put(COLUMN_NAME_OWNER, COLUMN_NAME_OWNER);
    	projectionMap.put(COLUMN_NAME_SUBJECT, COLUMN_NAME_SUBJECT);
    	projectionMap.put(COLUMN_NAME_ISREAD, COLUMN_NAME_ISREAD);
    	projectionMap.put(COLUMN_NAME_MESSAGEKEY, COLUMN_NAME_MESSAGEKEY);
    }

    @Override
	public String getTableName() {
		return TABLE_NAME+"_"+getCompanyParent()+getTableCompanyID();
	}

	@Override
	public String getCreateCMD() {
		String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
	    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
	    		COLUMN_NAME_USERNO+" TEXT,"+
	    		COLUMN_NAME_CONTENT+" TEXT,"+
	    		COLUMN_NAME_PUBLISHTIME+" TEXT,"+
	    		COLUMN_NAME_RECEIVETIME+" TEXT,"+
	    		COLUMN_NAME_OWNER+" TEXT,"+
	    		COLUMN_NAME_SUBJECT+" TEXT,"+
	    		COLUMN_NAME_ISREAD+" INTEGER,"+
	    		COLUMN_NAME_MESSAGEKEY+" TEXT);";
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+COLUMN_NAME_USERNO+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P2 ON "+getTableName()+" ("+COLUMN_NAME_ISREAD+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P3 ON "+getTableName()+" ("+COLUMN_NAME_MESSAGEKEY+");",
		    };
		return CREATE_INDEX_CMD;
	}

	@Override
	public String getDropCMD() {
		String DROP_CMD = "DROP TABLE IF EXISTS "+getTableName();
		return DROP_CMD;
	}

    private long serialID; //key
    private String userNo;
    private String content;
    private Date publishTime;
    private Date receiveTime;
    private String owner;
    private String subject;
    private boolean isRead;
    private String messageKey;

	public long getSerialID() {
		return serialID;
	}

	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}


    
}
