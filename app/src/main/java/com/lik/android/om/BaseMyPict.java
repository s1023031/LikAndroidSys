package com.lik.android.om;

import java.util.Date;
import java.util.HashMap;

public abstract class BaseMyPict extends BaseOM<MyPict>{

	private static final long serialVersionUID = -3638747058339305129L;
	
	public static final String TABLE_NAME = "MyPict";
	
    public static final String COLUMN_NAME_SERIALID = "SerialID";
	 public static final String COLUMN_NAME_SNO = "Sno";
	 public static final String COLUMN_NAME_FILENAME = "FileName";
	 public static final String COLUMN_NAME_TNO = "Tno";
	 public static final String COLUMN_NAME_SFLAG = "Sflag";
	 public static final String COLUMN_NAME_LASTDATE = "LastDate";
	 public static final String COLUMN_NAME_DETAIL = "Detail";
	 public static final String COLUMN_NAME_DIR = "Dir";
	 
	 
	  /**
	   * Standard projection for the interesting columns.
	   */
	 protected static final String[] READ_MYPICT_PROJECTION = new String[] {
		 	COLUMN_NAME_SERIALID, // Projection position 0, 
	    	COLUMN_NAME_SNO,  // Projection position 1, 
	    	COLUMN_NAME_FILENAME, // Projection position 2, 
	    	COLUMN_NAME_TNO, // Projection position 3, 
	    	COLUMN_NAME_SFLAG, // Projection position 4, 
	    	COLUMN_NAME_LASTDATE, // Projection position 5, 
	    	COLUMN_NAME_DETAIL, // Projection position 6, 
	    	COLUMN_NAME_DIR, // Projection position 7, 
	  };
	 
	    protected static final int READ_MYPICT_SERIALID_INDEX = 0;
	    protected static final int READ_MYPICT_SNO_INDEX = 1;
	    protected static final int READ_MYPICT_FILENAME_INDEX = 2;
	    protected static final int READ_MYPICT_TNO_INDEX = 3;
	    protected static final int READ_MYPICT_SFLAG_INDEX = 4;
	    protected static final int READ_MYPICT_LASTDATE_INDEX = 5;
	    protected static final int READ_MYPICT_DETAIL_INDEX = 6;
	    protected static final int READ_MYPICT_DIR_INDEX = 7;
	    
	    // Creates a new projection map instance. The map returns a column name
		// given a string. The two are usually equal.
		HashMap<String, String> projectionMap = new HashMap<String, String>();
		
	    public BaseMyPict() {
	    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
	    	projectionMap.put(COLUMN_NAME_SNO, COLUMN_NAME_SNO);
	    	projectionMap.put(COLUMN_NAME_FILENAME, COLUMN_NAME_FILENAME);
	    	projectionMap.put(COLUMN_NAME_TNO, COLUMN_NAME_TNO);
	    	projectionMap.put(COLUMN_NAME_SFLAG, COLUMN_NAME_SFLAG);
	    	projectionMap.put(COLUMN_NAME_LASTDATE, COLUMN_NAME_LASTDATE);
	    	projectionMap.put(COLUMN_NAME_DETAIL, COLUMN_NAME_DETAIL);
	    	projectionMap.put(COLUMN_NAME_DIR, COLUMN_NAME_DIR);
	    }
	 
	@Override
	public String getCreateCMD() {
		String CREATE_CMD ="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+
				COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
				COLUMN_NAME_SNO+" VARCHAR(10),"+
	    		COLUMN_NAME_FILENAME+" VARCHAR(200),"+
	    		COLUMN_NAME_TNO+" VARCHAR(50),"+
	    		COLUMN_NAME_SFLAG+" VARCHAR(50),"+
	    		COLUMN_NAME_LASTDATE+" VARCHAR(50),"+
	    		COLUMN_NAME_DETAIL+" TEXT,"+
	    		COLUMN_NAME_DIR+" TEXT);";
		return CREATE_CMD;
	}
	
	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+TABLE_NAME+"P1 ON "+TABLE_NAME+" ("+COLUMN_NAME_SERIALID+","+COLUMN_NAME_SNO+");",
		    	"CREATE  INDEX IF NOT EXISTS "+TABLE_NAME+"P2 ON "+TABLE_NAME+" ("+COLUMN_NAME_SNO+");",
		    	"CREATE  INDEX IF NOT EXISTS "+TABLE_NAME+"P3 ON "+TABLE_NAME+" ("+COLUMN_NAME_SFLAG+");",
		    };
		return CREATE_INDEX_CMD;
	}
	
	@Override
	public String getTableName() {
		return TABLE_NAME;
	}
	
    private long serialID; //key
    private String Sno;
    private String FileName;
    private String Tno;
    private String SFlag;
    private Date LastDate;
    private String Detail;
    private String Dir;
	

	public long getSerialID() {
		return serialID;
	}

	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}

	public String getSno() {
		return Sno;
	}

	public void setSno(String sno) {
		Sno = sno;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileNmae(String fileName) {
		FileName = fileName;
	}

	public String getTno() {
		return Tno;
	}

	public void setTno(String tno) {
		Tno = tno;
	}

	public String getSFlag() {
		return SFlag;
	}

	public void setSFlag(String sFlag) {
		SFlag = sFlag;
	}

	public Date getLastDate() {
		return LastDate;
	}

	public void setLastDate(Date lastDate) {
		LastDate = lastDate;
	}

	public String getDetail() {
		return Detail;
	}

	public void setDetail(String detail) {
		Detail = detail;
	}

	public String getDir() {
		return Dir;
	}

	public void setDir(String dir) {
		Dir = dir;
	}

	@Override
	public String getDropCMD() {
		String DROP_CMD = "DROP TABLE IF EXISTS "+TABLE_NAME;
		return DROP_CMD;
	}
	



}
