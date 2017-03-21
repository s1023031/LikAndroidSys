package com.lik.android.om;

import java.util.Date;
import java.util.HashMap;

import com.lik.android.main.MainMenuActivity;

public abstract class BasePhotoProjectState extends BaseOM<PhotoProjectState>{
	
	public static final String TABLE_NAME = "PhotoProjectState";

    public static final String COLUMN_NAME_SERIALID = "SerialID";
    public static final String COLUMN_NAME_USERNO= "UserNO";
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";
    public static final String COLUMN_NAME_PROJECTID = "ProjectID";
    public static final String COLUMN_NAME_VIEWORDER = "ViewOrder";
    public static final String COLUMN_NAME_STATEID = "StateID";
    public static final String COLUMN_NAME_STATENO = "StateNO";
    public static final String COLUMN_NAME_STATENAME = "StateName";
    public static final String COLUMN_NAME_CUSTOMERID = "CustomerID";
    public static final String COLUMN_NAME_MAXDATE = "MaxDate";
    public static final String COLUMN_NAME_KIND = "Kind";
    
    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_PHOTOPROJECTSTATE_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_USERNO,  // Projection position 1, 
    	COLUMN_NAME_COMPANYID, // Projection position 2, 
    	COLUMN_NAME_PROJECTID, // Projection position 3, 
    	COLUMN_NAME_VIEWORDER, // Projection position 4, 
    	COLUMN_NAME_STATEID, // Projection position 5, 
    	COLUMN_NAME_STATENO, // Projection position 6, 
    	COLUMN_NAME_STATENAME, // Projection position 7, 
    	COLUMN_NAME_CUSTOMERID, // Projection position 8, 
    	COLUMN_NAME_MAXDATE, // Projection position 9, 
    	COLUMN_NAME_KIND, // Projection position 10,
    };
    
    protected static final int READ_PHOTOPROJECT_SERIALID_INDEX = 0;
    protected static final int READ_PHOTOPROJECT_USERNO_INDEX = 1;
    protected static final int READ_PHOTOPROJECT_COMPANYID_INDEX = 2;
    protected static final int READ_PHOTOPROJECT_PROJECTID_INDEX = 3;
    protected static final int READ_PHOTOPROJECT_VIEWORDER_INDEX = 4;
    protected static final int READ_PHOTOPROJECT_STATEID_INDEX = 5;
    protected static final int READ_PHOTOPROJECT_STATENO_INDEX = 6;
    protected static final int READ_PHOTOPROJECT_STATENAME_INDEX = 7;
    protected static final int READ_PHOTOPROJECT_CUSTOMERID_INDEX = 8;
    protected static final int READ_PHOTOPROJECT_MAXDATE_INDEX = 9;
    protected static final int READ_PHOTOPROJECT_KIND_INDEX = 10;

    // Creates a new projection map instance. The map returns a column name
   	// given a string. The two are usually equal.
   	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BasePhotoProjectState() 
    {
	      projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
	      projectionMap.put(COLUMN_NAME_USERNO, COLUMN_NAME_USERNO);
	      projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
	      projectionMap.put(COLUMN_NAME_PROJECTID, COLUMN_NAME_PROJECTID);
	      projectionMap.put(COLUMN_NAME_VIEWORDER, COLUMN_NAME_VIEWORDER);
	      projectionMap.put(COLUMN_NAME_STATEID, COLUMN_NAME_STATEID);
	      projectionMap.put(COLUMN_NAME_STATENAME, COLUMN_NAME_STATENAME);
	      projectionMap.put(COLUMN_NAME_MAXDATE, COLUMN_NAME_MAXDATE);
	      projectionMap.put(COLUMN_NAME_CUSTOMERID, COLUMN_NAME_CUSTOMERID);
	      projectionMap.put(COLUMN_NAME_KIND, COLUMN_NAME_KIND);
    }

    
    @Override
	public String getTableName() {
    	setTableCompanyID(0);
		setCompanyParent(null);
		if(getTableCompanyID()==0) 
    		//setTableCompanyID(MainMenuActivity.currentDept.getCompanyID());
    		setTableCompanyID(MainMenuActivity.companyID);
    	if(getCompanyParent()==null)
    		//setCompanyParent(MainMenuActivity.omCurrentSysProfile.getCompanyNo());
    		setCompanyParent(MainMenuActivity.companyParent);
		return TABLE_NAME+"_"+getCompanyParent()+(getTableCompanyID()==0 ?companyID:getTableCompanyID());
	}
    
	@Override
	public String getCreateCMD() {
		String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
	    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
	    		COLUMN_NAME_USERNO+" TEXT,"+
	    		COLUMN_NAME_COMPANYID+" INTEGER,"+
	    		COLUMN_NAME_PROJECTID+" INTEGER,"+
	    		COLUMN_NAME_VIEWORDER+" INTEGER,"+
	    		COLUMN_NAME_STATEID+" INTEGER,"+
	    		COLUMN_NAME_STATENO+" INTEGER,"+
	    		COLUMN_NAME_STATENAME+" TEXT,"+
	    		COLUMN_NAME_CUSTOMERID+" INTEGER,"+
	    		COLUMN_NAME_MAXDATE+" TEXT,"+
	    		COLUMN_NAME_KIND +" INTEGER)";
		return CREATE_CMD;
	}
	
	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+COLUMN_NAME_USERNO+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P2 ON "+getTableName()+" ("+COLUMN_NAME_COMPANYID+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P3 ON "+getTableName()+" ("+COLUMN_NAME_PROJECTID+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P4 ON "+getTableName()+" ("+COLUMN_NAME_CUSTOMERID+");",
		    };
		return CREATE_INDEX_CMD;
	}

	@Override
	public String getDropCMD() {
		String DROP_CMD = "DROP TABLE IF EXISTS "+getTableName();
		return DROP_CMD;
	}
	
	private long serialID; //key
	private String userNO;
	private int companyID;
	private int projectID;
	private int viewOrder;
	private int stateID;
	private int stateNO;
	private int kind;
	private String stateName;
	private int customerID;;
	private Date maxDate;

	public long getSerialID() {
		return serialID;
	}

	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}

	public String getUserNO() {
		return userNO;
	}

	public void setUserNO(String userNO) {
		this.userNO = userNO;
	}

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public int getViewOrder() {
		return viewOrder;
	}

	public void setViewOrder(int viewOrder) {
		this.viewOrder = viewOrder;
	}

	public int getStateID() {
		return stateID;
	}

	public void setStateID(int stateID) {
		this.stateID = stateID;
	}

	public int getStateNO() {
		return stateNO;
	}

	public void setStateNO(int stateNO) {
		this.stateNO = stateNO;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}


	public int getKind() {
		return kind;
	}


	public void setKind(int kind) {
		this.kind = kind;
	}



}
