package com.lik.android.om;

import java.util.HashMap;

public abstract class BaseDailySequence extends BaseOM<DailySequence> {

	private static final long serialVersionUID = 6659362381354099214L;

	public static final String TABLE_NAME = "DailySequence";

	public static final String TABLE_CH_NAME = "每日序號資料";

	/**
     * Column name for 序號 of the DailySequence
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

	/**
     * Column name for 欄位名 of the DailySequence
     * <P>Type: STRING</P>
     */
    public static final String COLUMN_NAME_COLUMNNAME = "ColumnName";

	/**
     * Column name for seq of the DailySequence
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SEQ = "Seq";

    public static final String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+
    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
    		COLUMN_NAME_COLUMNNAME+" TEXT,"+
    		COLUMN_NAME_SEQ+" INTEGER);";

    public static final String DROP_CMD = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public static final String CLEAR_CMD = "DELETE from "+TABLE_NAME;

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_DAILYSEQUENCE_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_COLUMNNAME, // Projection position 1, 
    	COLUMN_NAME_SEQ, // Projection position 2, 
    };
    protected static final int READ_DAILYSEQUENCE_SERIALID_INDEX = 0;
    protected static final int READ_DAILYSEQUENCE_COLUMNNAME_INDEX = 1;
    protected static final int READ_DAILYSEQUENCE_SEQ_INDEX = 2;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseDailySequence() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_COLUMNNAME, COLUMN_NAME_COLUMNNAME);
    	projectionMap.put(COLUMN_NAME_SEQ, COLUMN_NAME_SEQ);

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

    private int serialID;
    private String columnName;
    private int seq;

	public int getSerialID() {
		return serialID;
	}

	public void setSerialID(int serialID) {
		this.serialID = serialID;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

    
}
