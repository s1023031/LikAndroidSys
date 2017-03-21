package com.lik.android.om;

import java.util.HashMap;

public abstract class BaseSettings extends BaseOM<Settings> {

	private static final long serialVersionUID = 1208606716561986007L;

	public static final String TABLE_NAME = "Settings";

	public static final String TABLE_CH_NAME = "UI設定資料";

	/**
     * Column name for 序號 of the Settings
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

	/**
     * Column name for 標籤 of the Settings
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_TAG = "Tag";

	/**
     * Column name for 欄位序號 of the Settings
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_COLUMNNO = "ColumnNO";

	/**
     * Column name for 設定值 of the Settings
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_WIDTHWALUE = "WidthValue";

	/**
     * Column name for 欄位排序 of the Settings
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SEQUENCE = "Sequence";

    public static final String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+
    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
    		COLUMN_NAME_TAG+" TEXT,"+
    		COLUMN_NAME_COLUMNNO+" INTEGER,"+
    		COLUMN_NAME_WIDTHWALUE+" INTEGER,"+
    		COLUMN_NAME_SEQUENCE+" INTEGER);";

    public static final String DROP_CMD = "DROP TABLE IF EXISTS "+TABLE_NAME;

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_SETTINGS_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_TAG,  // Projection position 1, 
    	COLUMN_NAME_COLUMNNO, // Projection position 2, 
    	COLUMN_NAME_WIDTHWALUE, // Projection position 3, 
    	COLUMN_NAME_SEQUENCE, // Projection position 4, 
    };
    protected static final int READ_SETTINGS_SERIALID_INDEX = 0;
    protected static final int READ_SETTINGS_TAG_INDEX = 1;
    protected static final int READ_SETTINGS_COLUMNNO_INDEX = 2;
    protected static final int READ_SETTINGS_WIDTHWALUE_INDEX = 3;
    protected static final int READ_SETTINGS_SEQUENCE_INDEX = 4;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseSettings() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_TAG, COLUMN_NAME_TAG);
    	projectionMap.put(COLUMN_NAME_COLUMNNO, COLUMN_NAME_COLUMNNO);
    	projectionMap.put(COLUMN_NAME_WIDTHWALUE, COLUMN_NAME_WIDTHWALUE);
    	projectionMap.put(COLUMN_NAME_SEQUENCE, COLUMN_NAME_SEQUENCE);

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

    private long serialID; //key
	private String tag;
	private int columnNO;
	private int widthValue;
	private int sequence;

	public long getSerialID() {
		return serialID;
	}

	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getColumnNO() {
		return columnNO;
	}

	public void setColumnNO(int columnNO) {
		this.columnNO = columnNO;
	}

	public int getWidthValue() {
		return widthValue;
	}

	public void setWidthValue(int widthValue) {
		this.widthValue = widthValue;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	
}
