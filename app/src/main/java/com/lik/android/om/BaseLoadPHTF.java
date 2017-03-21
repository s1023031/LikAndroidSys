package com.lik.android.om;

import java.util.Date;
import java.util.HashMap;

/**
 * 資料分公司存放 companyID_tableName
 * @author charles
 *
 */
public abstract class BaseLoadPHTF extends BaseOM<LoadPHTF> {

	private static final long serialVersionUID = 7123148264081095254L;

	public static final String TABLE_NAME = "LoadPHTF";

	public static final String TABLE_CH_NAME = "上傳圖檔目錄資料";
	public static final String TABLE_GROUP_NAME = "客戶資料";

	/**
     * Column name for 序號 of the LoadPHTF
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for 公司流水號 of the LoadPHTF
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";

    /**
     * Column name for PhotoFile流水號 of the LoadPHTF
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PHOTOFILEID = "PhotoFileID";

    /**
     * Column name for 起日 of the LoadPHTF
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DATEFROM = "DateFrom";

    /**
     * Column name for 止日 of the LoadPHTF
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DATETO = "DateTo";

    /**
     * Column name for Departmentof the LoadPHTF
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DEPARTMENT = "Department";

    /**
     * Column name for 主  旨 of the LoadPHTF
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CLASSIFY= "Classify";

    /**
     * Column name for 本  文 of the LoadPHTF
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ITEM = "Item";

    /**
     * Column name for Statement of the LoadPHTF
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_STATEMENT = "Statement";

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_LOADPHTF_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_COMPANYID,  // Projection position 1, 
    	COLUMN_NAME_PHOTOFILEID, // Projection position 2, 
    	COLUMN_NAME_DATEFROM, // Projection position 3, 
    	COLUMN_NAME_DATETO, // Projection position 4, 
    	COLUMN_NAME_DEPARTMENT, // Projection position 5, 
    	COLUMN_NAME_CLASSIFY, // Projection position 6, 
    	COLUMN_NAME_ITEM, // Projection position 7, 
    	COLUMN_NAME_STATEMENT, // Projection position 8, 
    };
    protected static final int READ_LOADPHTF_SERIALID_INDEX = 0;
    protected static final int READ_LOADPHTF_COMPANYID_INDEX = 1;
    protected static final int READ_LOADPHTF_PHOTOFILEID_INDEX = 2;
    protected static final int READ_LOADPHTF_DATEFROM_INDEX = 3;
    protected static final int READ_LOADPHTF_DATETO_INDEX = 4;
    protected static final int READ_LOADPHTF_DEPARTMENT_INDEX = 5;
    protected static final int READ_LOADPHTF_CLASSIFY_INDEX = 6;
    protected static final int READ_LOADPHTF_ITEM_INDEX = 7;
    protected static final int READ_LOADPHTF_STATEMENT_INDEX = 8;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseLoadPHTF() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
    	projectionMap.put(COLUMN_NAME_PHOTOFILEID, COLUMN_NAME_PHOTOFILEID);
    	projectionMap.put(COLUMN_NAME_DATEFROM, COLUMN_NAME_DATEFROM);
    	projectionMap.put(COLUMN_NAME_DATETO, COLUMN_NAME_DATETO);
    	projectionMap.put(COLUMN_NAME_DEPARTMENT, COLUMN_NAME_DEPARTMENT);
    	projectionMap.put(COLUMN_NAME_CLASSIFY, COLUMN_NAME_CLASSIFY);
    	projectionMap.put(COLUMN_NAME_ITEM, COLUMN_NAME_ITEM);
    	projectionMap.put(COLUMN_NAME_STATEMENT, COLUMN_NAME_STATEMENT);
    }

    @Override
	public String getTableName() {
//		return TABLE_NAME+"_"+getTableCompanyID();
		return TABLE_NAME+"_"+(getTableCompanyID()==0?companyID:getTableCompanyID());
	}

	@Override
	public String getCreateCMD() {
		String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
	    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
	    		COLUMN_NAME_COMPANYID+" INTEGER,"+
	    		COLUMN_NAME_PHOTOFILEID+" INTEGER,"+
	    		COLUMN_NAME_DATEFROM+" TEXT,"+
	    		COLUMN_NAME_DATETO+" TEXT,"+
	    		COLUMN_NAME_DEPARTMENT+" TEXT,"+
	    		COLUMN_NAME_CLASSIFY+" TEXT,"+
	    		COLUMN_NAME_ITEM+" TEXT,"+
	    		COLUMN_NAME_STATEMENT+" TEXT);";
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+COLUMN_NAME_COMPANYID+","+COLUMN_NAME_PHOTOFILEID+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P2 ON "+getTableName()+" ("+COLUMN_NAME_DATEFROM+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P3 ON "+getTableName()+" ("+COLUMN_NAME_DATETO+");",
		    };
		return CREATE_INDEX_CMD;
	}

	@Override
	public String getDropCMD() {
		String DROP_CMD = "DROP TABLE IF EXISTS "+getTableName();
		return DROP_CMD;
	}

    private long serialID; //key
	private int companyID;
	private int photoFileID;
	private Date dateFrom;
	private Date dateTo;
	private String department;
	private String classify;
	private String item;
	private String statement;

	public long getSerialID() {
		return serialID;
	}

	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public int getPhotoFileID() {
		return photoFileID;
	}

	public void setPhotoFileID(int photoFileID) {
		this.photoFileID = photoFileID;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	
}
