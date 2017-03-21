package com.lik.android.om;

import java.util.HashMap;

public abstract class BaseSuppliers extends BaseOM<Suppliers> {

	private static final long serialVersionUID = -3598567406623644716L;

	public static final String TABLE_NAME = "Suppliers";

	public static final String TABLE_CH_NAME = "供應廠商資料";

	/**
     * Column name for 序號 of the Suppliers
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for 公司流水號 of the Suppliers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";

    /**
     * Column name for 公司流水號 of the Suppliers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CLASSIFY = "Classify";

    /**
     * Column name for 公司流水號 of the Suppliers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SUPPLIERNO = "SupplierNO";

    /**
     * Column name for 公司流水號 of the Suppliers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SUPPLIERNM = "SupplierNM";

    /**
     * Column name for 版本 of the Suppliers
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_VERSIONNO = "VersionNo";

//    private String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
//    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
//    		COLUMN_NAME_COMPANYID+" INTEGER,"+
//    		COLUMN_NAME_CLASSIFY+" TEXT,"+
//    		COLUMN_NAME_SUPPLIERNO+" TEXT,"+
//    		COLUMN_NAME_SUPPLIERNM+" TEXT,"+
//    		COLUMN_NAME_VERSIONNO+" TEXT);";
//
//    private String[] CREATE_INDEX_CMD = {
//    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+
//    			COLUMN_NAME_COMPANYID+","+COLUMN_NAME_CLASSIFY+","+COLUMN_NAME_SUPPLIERNO+");",
//    };
//    
//    private String DROP_CMD = "DROP TABLE IF EXISTS "+getTableName();

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_SUPPLIER_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_COMPANYID,  // Projection position 1, 
    	COLUMN_NAME_CLASSIFY, // Projection position 2, 
    	COLUMN_NAME_SUPPLIERNO, // Projection position 3, 
    	COLUMN_NAME_SUPPLIERNM, // Projection position 4, 
    	COLUMN_NAME_VERSIONNO, // Projection position 5, 
    };
    protected static final int READ_SUPPLIER_SERIALID_INDEX = 0;
    protected static final int READ_SUPPLIER_COMPANYID_INDEX = 1;
    protected static final int READ_SUPPLIER_CLASSIFY_INDEX = 2;
    protected static final int READ_SUPPLIER_SUPPLIERNO_INDEX = 3;
    protected static final int READ_SUPPLIER_SUPPLIERNM_INDEX = 4;
    protected static final int READ_SUPPLIER_VERSIONNO_INDEX = 5;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseSuppliers() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
    	projectionMap.put(COLUMN_NAME_CLASSIFY, COLUMN_NAME_CLASSIFY);
    	projectionMap.put(COLUMN_NAME_SUPPLIERNO, COLUMN_NAME_SUPPLIERNO);
    	projectionMap.put(COLUMN_NAME_SUPPLIERNM, COLUMN_NAME_SUPPLIERNM);
    	projectionMap.put(COLUMN_NAME_VERSIONNO, COLUMN_NAME_VERSIONNO);
    }

    @Override
	public String getTableName() {
//		return TABLE_NAME;
//		return TABLE_NAME+"_"+getTableCompanyID();
		return TABLE_NAME+"_"+(getTableCompanyID()==0?companyID:getTableCompanyID());
	}

	@Override
	public String getCreateCMD() {
		String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
	    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
	    		COLUMN_NAME_COMPANYID+" INTEGER,"+
	    		COLUMN_NAME_CLASSIFY+" TEXT,"+
	    		COLUMN_NAME_SUPPLIERNO+" TEXT,"+
	    		COLUMN_NAME_SUPPLIERNM+" TEXT,"+
	    		COLUMN_NAME_VERSIONNO+" TEXT);";
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+
		    			COLUMN_NAME_COMPANYID+","+COLUMN_NAME_CLASSIFY+","+COLUMN_NAME_SUPPLIERNO+");",
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
	private String classify;
	private String supplierNO;
	private String supplierNM;
	private String versionNo;

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

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getSupplierNO() {
		return supplierNO;
	}

	public void setSupplierNO(String supplierNO) {
		this.supplierNO = supplierNO;
	}

	public String getSupplierNM() {
		return supplierNM;
	}

	public void setSupplierNM(String supplierNM) {
		this.supplierNM = supplierNM;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	
}
