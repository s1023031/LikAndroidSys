package com.lik.android.om;

import java.util.Date;
import java.util.HashMap;

import android.util.Log;

import com.lik.android.main.MainMenuActivity;

public abstract class BasePhotoProject extends BaseOM<PhotoProject> {

	private static final long serialVersionUID = -3428022592996687461L;

	public static final String TABLE_NAME = "PhotoProject";

	public static final String TABLE_CH_NAME = "�Ӭ۱M��";

	/**
     * Column name for �Ǹ� of the PhotoProject
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for ���q�y���� of the PhotoProject
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";

    /**
     * Column name for UserNO of the PhotoProject
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_USERNO = "UserNO";

    /**
     * Column name for YearMonth of the PhotoProject
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_YEARMONTH = "YearMonth";

    /**
     * Column name for Name of the PhotoProject
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_NAME = "Name";

    /**
     * Column name for SupplierNO of the PhotoProject
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SUPPLIERNO = "SupplierNO";

    /**
     * Column name for SupplierNM of the PhotoProject
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SUPPLIERNM = "SupplierNM";

    /**
     * Column name for Remark of the PhotoProject
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_REMARK = "Remark";

    /**
     * Column name for SalesNo of the PhotoProject
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SALESNO = "SalesNo";
    
    /**
     * Column name for SalesName of the PhotoProject
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SALESNAME = "SalesName";
    
    /**
     * Column name for Count of the PhotoProject
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COUNT = "Count";
    
    /**
     * Column name for FinishDate of the PhotoProject
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_FINISHDATE = "FinishDate";
    
    /**
     * Column name for TakePhotoID of the PhotoProject
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_TAKEPHOTOID = "TakePhotoID";
    
    /**
     * Column name for CustomerID of the PhotoProject
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CUSTOMERID = "CustomerID";
    
    /**
     * Column name for PROJECTNO of the PhotoProject
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PROJECTNO = "ProjectNO";
    
    public static final String COLUMN_NAME_PHOTODISPLAY1 = "PhotoDisplay1";
    public static final String COLUMN_NAME_PHOTODISPLAY2 = "PhotoDisplay2";
    public static final String COLUMN_NAME_PHOTODISPLAY3 = "PhotoDisplay3";
    public static final String COLUMN_NAME_PHOTODISPLAY4 = "PhotoDisplay4";
    public static final String COLUMN_NAME_PHOTODISPLAY5 = "PhotoDisplay5";
    public static final String COLUMN_NAME_PHOTODISPLAY6 = "PhotoDisplay6";
    public static final String COLUMN_NAME_PHOTODISPLAY7= "PhotoDisplay7";
    public static final String COLUMN_NAME_PHOTODISPLAY8 = "PhotoDisplay8";
    
    
    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_PHOTOPROJECT_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_COMPANYID,  // Projection position 1, 
    	COLUMN_NAME_USERNO, // Projection position 2, 
    	COLUMN_NAME_YEARMONTH, // Projection position 3, 
    	COLUMN_NAME_NAME, // Projection position 4, 
    	COLUMN_NAME_SUPPLIERNO, // Projection position 5, 
    	COLUMN_NAME_SUPPLIERNM, // Projection position 6, 
    	COLUMN_NAME_REMARK, // Projection position 7, 
    	COLUMN_NAME_SALESNO, // Projection position 8, 
    	COLUMN_NAME_SALESNAME, // Projection position 9, 
    	COLUMN_NAME_COUNT, // Projection position 10, 
    	COLUMN_NAME_FINISHDATE, // Projection position 11, 
    	COLUMN_NAME_TAKEPHOTOID, // Projection position 12, 
    	COLUMN_NAME_CUSTOMERID,  // Projection position 13, 
    	COLUMN_NAME_PROJECTNO, // Projection position 14,
    	COLUMN_NAME_PHOTODISPLAY1, // Projection position 15,
    	COLUMN_NAME_PHOTODISPLAY2, // Projection position 16,
    	COLUMN_NAME_PHOTODISPLAY3, // Projection position 17,
    	COLUMN_NAME_PHOTODISPLAY4, // Projection position 18,
    	COLUMN_NAME_PHOTODISPLAY5, // Projection position 19,
    	COLUMN_NAME_PHOTODISPLAY6, // Projection position 20,
    	COLUMN_NAME_PHOTODISPLAY7, // Projection position 21,
    	COLUMN_NAME_PHOTODISPLAY8, // Projection position 22,
    };
    protected static final int READ_PHOTOPROJECT_SERIALID_INDEX = 0;
    protected static final int READ_PHOTOPROJECT_COMPANYID_INDEX = 1;
    protected static final int READ_PHOTOPROJECT_USERNO_INDEX = 2;
    protected static final int READ_PHOTOPROJECT_YEARMONTH_INDEX = 3;
    protected static final int READ_PHOTOPROJECT_NAME_INDEX = 4;
    protected static final int READ_PHOTOPROJECT_SUPPLIERNO_INDEX = 5;
    protected static final int READ_PHOTOPROJECT_SUPPLIERNM_INDEX = 6;
    protected static final int READ_PHOTOPROJECT_REMARK_INDEX = 7;
    protected static final int READ_PHOTOPROJECT_SALESNO_INDEX = 8;
    protected static final int READ_PHOTOPROJECT_SALESNAME_INDEX = 9;
    protected static final int READ_PHOTOPROJECT_COUNT_INDEX = 10;
    protected static final int READ_PHOTOPROJECT_FINISHDATE_INDEX = 11;
    protected static final int READ_PHOTOPROJECT_TAKEPHOTOID_INDEX = 12;
    protected static final int READ_PHOTOPROJECT_CUSTOMERID_INDEX = 13;
    protected static final int READ_PHOTOPROJECT_PROJECTNO_INDEX = 14;
    protected static final int READ_PHOTOPROJECT_PHOTODISPLAY1_INDEX = 15;
    protected static final int READ_PHOTOPROJECT_PHOTODISPLAY2_INDEX = 16;
    protected static final int READ_PHOTOPROJECT_PHOTODISPLAY3_INDEX = 17;
    protected static final int READ_PHOTOPROJECT_PHOTODISPLAY4_INDEX = 18;
    protected static final int READ_PHOTOPROJECT_PHOTODISPLAY5_INDEX = 19;
    protected static final int READ_PHOTOPROJECT_PHOTODISPLAY6_INDEX = 20;
    protected static final int READ_PHOTOPROJECT_PHOTODISPLAY7_INDEX = 21;
    protected static final int READ_PHOTOPROJECT_PHOTODISPLAY8_INDEX = 22;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BasePhotoProject() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
    	projectionMap.put(COLUMN_NAME_USERNO, COLUMN_NAME_USERNO);
    	projectionMap.put(COLUMN_NAME_YEARMONTH, COLUMN_NAME_YEARMONTH);
    	projectionMap.put(COLUMN_NAME_NAME, COLUMN_NAME_NAME);
    	projectionMap.put(COLUMN_NAME_SUPPLIERNO, COLUMN_NAME_SUPPLIERNO);
    	projectionMap.put(COLUMN_NAME_SUPPLIERNM, COLUMN_NAME_SUPPLIERNM);
    	projectionMap.put(COLUMN_NAME_REMARK, COLUMN_NAME_REMARK);
    	projectionMap.put(COLUMN_NAME_SALESNO, COLUMN_NAME_SALESNO);
    	projectionMap.put(COLUMN_NAME_SALESNAME, COLUMN_NAME_SALESNAME);
    	projectionMap.put(COLUMN_NAME_COUNT, COLUMN_NAME_COUNT);
    	projectionMap.put(COLUMN_NAME_FINISHDATE, COLUMN_NAME_FINISHDATE);
    	projectionMap.put(COLUMN_NAME_TAKEPHOTOID, COLUMN_NAME_TAKEPHOTOID);
    	projectionMap.put(COLUMN_NAME_CUSTOMERID, COLUMN_NAME_CUSTOMERID);
     	projectionMap.put(COLUMN_NAME_PROJECTNO, COLUMN_NAME_PROJECTNO);
     	projectionMap.put(COLUMN_NAME_PHOTODISPLAY1, COLUMN_NAME_PHOTODISPLAY1);
     	projectionMap.put(COLUMN_NAME_PHOTODISPLAY2, COLUMN_NAME_PHOTODISPLAY2);
     	projectionMap.put(COLUMN_NAME_PHOTODISPLAY3, COLUMN_NAME_PHOTODISPLAY3);
     	projectionMap.put(COLUMN_NAME_PHOTODISPLAY4, COLUMN_NAME_PHOTODISPLAY4);
     	projectionMap.put(COLUMN_NAME_PHOTODISPLAY5, COLUMN_NAME_PHOTODISPLAY5);
     	projectionMap.put(COLUMN_NAME_PHOTODISPLAY6, COLUMN_NAME_PHOTODISPLAY6);
     	projectionMap.put(COLUMN_NAME_PHOTODISPLAY7, COLUMN_NAME_PHOTODISPLAY7);
     	projectionMap.put(COLUMN_NAME_PHOTODISPLAY8, COLUMN_NAME_PHOTODISPLAY8);
    }

    @Override
	public String getTableName() {
    	setTableCompanyID(0);
		setCompanyParent(null);
    	Log.d(TAG, "getTable PhotoProject = "+getTableCompanyID() + " " + getCompanyParent());
//    	Log.d(TAG, "getTable PhotoProject Main = "+MainMenuActivity.currentDept.getCompanyID() + " " + MainMenuActivity.omCurrentSysProfile.getCompanyNo());
     	if(getTableCompanyID()==0) 
    		setTableCompanyID(MainMenuActivity.companyID);
    	if(getCompanyParent()==null)
    		setCompanyParent(MainMenuActivity.companyParent);
    	Log.d(TAG, "getTable PhotoProject after set= "+getTableCompanyID() + " " + getCompanyParent());
		return TABLE_NAME+"_"+getCompanyParent()+(getTableCompanyID()==0?companyID:getTableCompanyID());
    	//return TABLE_NAME+"_"+MainMenuActivity.getCurrentCompany().getCompanyName()+MainMenuActivity.getCurrentCompany().getDeptNO();
	}

	@Override
	public String getCreateCMD() {
		String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
	    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
	    		COLUMN_NAME_COMPANYID+" INTEGER,"+
	    		COLUMN_NAME_USERNO+" TEXT,"+
	    		COLUMN_NAME_YEARMONTH+" TEXT,"+
	    		COLUMN_NAME_NAME+" TEXT,"+
	    		COLUMN_NAME_SUPPLIERNO+" TEXT,"+
	    		COLUMN_NAME_SUPPLIERNM+" TEXT,"+
	    		COLUMN_NAME_REMARK+" TEXT,"+
	    		COLUMN_NAME_SALESNO+" TEXT,"+
	    		COLUMN_NAME_SALESNAME+" TEXT,"+
	    		COLUMN_NAME_COUNT+" INTEGER,"+
	    		COLUMN_NAME_FINISHDATE+" TEXT,"+
	    		COLUMN_NAME_TAKEPHOTOID+" INTEGER,"+
	    		COLUMN_NAME_CUSTOMERID+" INTEGER,"+
	    		COLUMN_NAME_PROJECTNO+" TEXT,"+
	    		COLUMN_NAME_PHOTODISPLAY1+ " TEXT,"+
	    		COLUMN_NAME_PHOTODISPLAY2+ " TEXT,"+
	    		COLUMN_NAME_PHOTODISPLAY3+ " TEXT,"+
	    		COLUMN_NAME_PHOTODISPLAY4+ " TEXT,"+
	    		COLUMN_NAME_PHOTODISPLAY5+ " TEXT,"+
	    		COLUMN_NAME_PHOTODISPLAY6+ " TEXT,"+
	    		COLUMN_NAME_PHOTODISPLAY7+ " TEXT,"+
	    		COLUMN_NAME_PHOTODISPLAY8+" TEXT)";
	    		
	    		
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+COLUMN_NAME_COMPANYID+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P2 ON "+getTableName()+" ("+COLUMN_NAME_USERNO+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P3 ON "+getTableName()+" ("+COLUMN_NAME_YEARMONTH+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P4 ON "+getTableName()+" ("+COLUMN_NAME_NAME+");",
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
	private String userNO;
	private String yearMonth;
	private String name;
	private String supplierNO;
	private String supplierNM;
	private String remark;
	private String salesNo;
	private String salesName;
	private int count;
	private Date finishDate;
	private int takePhotoID;
	private int customerID;
	private String projectNO;
	private String photoDisplay1;
	private String photoDisplay2;
	private String photoDisplay3;
	private String photoDisplay4;
	private String photoDisplay5;
	private String photoDisplay6;
	private String photoDisplay7;
	private String photoDisplay8;
	
	
	public String getPhotoDisplay1() {
		return photoDisplay1;
	}

	public void setPhotoDisplay1(String photoDisplay1) {
		this.photoDisplay1 = photoDisplay1;
	}

	public String getPhotoDisplay2() {
		return photoDisplay2;
	}

	public void setPhotoDisplay2(String photoDisplay2) {
		this.photoDisplay2 = photoDisplay2;
	}

	public String getPhotoDisplay3() {
		return photoDisplay3;
	}

	public void setPhotoDisplay3(String photoDisplay3) {
		this.photoDisplay3 = photoDisplay3;
	}

	public String getPhotoDisplay4() {
		return photoDisplay4;
	}

	public void setPhotoDisplay4(String photoDisplay4) {
		this.photoDisplay4 = photoDisplay4;
	}

	public String getPhotoDisplay5() {
		return photoDisplay5;
	}

	public void setPhotoDisplay5(String photoDisplay5) {
		this.photoDisplay5 = photoDisplay5;
	}

	public String getPhotoDisplay6() {
		return photoDisplay6;
	}

	public void setPhotoDisplay6(String photoDisplay6) {
		this.photoDisplay6 = photoDisplay6;
	}

	public String getPhotoDisplay7() {
		return photoDisplay7;
	}

	public void setPhotoDisplay7(String photoDisplay7) {
		this.photoDisplay7 = photoDisplay7;
	}

	public String getPhotoDisplay8() {
		return photoDisplay8;
	}

	public void setPhotoDisplay8(String photoDisplay8) {
		this.photoDisplay8 = photoDisplay8;
	}

	public String getProjectNO() {
		return projectNO;
	}

	public void setProjectNO(String projectNO) {
		this.projectNO = projectNO;
	}

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

	public String getUserNO() {
		return userNO;
	}

	public void setUserNO(String userNO) {
		this.userNO = userNO;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSalesNo() {
		return salesNo;
	}

	public void setSalesNo(String salesNo) {
		this.salesNo = salesNo;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public int getTakePhotoID() {
		return takePhotoID;
	}

	public void setTakePhotoID(int takePhotoID) {
		this.takePhotoID = takePhotoID;
	}
	
	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	
}
