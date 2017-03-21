package com.lik.android.om;

import java.util.HashMap;

public abstract class BaseCurrentCompany extends BaseOM<BaseCurrentCompany>{

	private static final long serialVersionUID = -8611723827957160816L;
	
	public static final String TABLE_NAME = "CurrentCompany";
	
	public static final String COLUMN_NAME_SERIALID = "SerialID";

    public static final String COLUMN_NAME_COMPANYNAME = "CompanyName";
    
    public static final String COLUMN_NAME_DEPTNO= "DeptNO";
    
    public static final String COLUMN_NAME_COMPANYNO= "CompanyNO";
	
    public static final String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+
    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
    		COLUMN_NAME_COMPANYNAME+" TEXT,"+
    		COLUMN_NAME_DEPTNO+" TEXT,"+
    		COLUMN_NAME_COMPANYNO+" TEXT)";
    
    public static final String DROP_CMD = "DROP TABLE IF EXISTS "+TABLE_NAME;
    
    protected static final String[] READ_CURRENTCOMPANY_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_COMPANYNAME,
    	COLUMN_NAME_DEPTNO,
    	COLUMN_NAME_COMPANYNO,
    };
    
    protected static final int READ_CURRENTCOMPANY_SERIALID_INDEX = 0;
    protected static final int READ_CURRENTCOMPANY__COMPANYNAME_INDEX = 1;
    protected static final int READ_CURRENTCOMPANY__DEPTNO_INDEX = 2;
    protected static final int READ_CURRENTCOMPANY__COMPANYNO_INDEX = 3;
    
    HashMap<String, String> projectionMap = new HashMap<String, String>();
    
    public BaseCurrentCompany() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_COMPANYNAME, COLUMN_NAME_COMPANYNAME);
    	projectionMap.put(COLUMN_NAME_DEPTNO, COLUMN_NAME_DEPTNO);
    	projectionMap.put(COLUMN_NAME_COMPANYNO, COLUMN_NAME_COMPANYNO);

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
  	public String getDropCMD() {
  		return DROP_CMD;
  	}

	@Override
	public String[] getCreateIndexCMD() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private long serialID; 
	private String companyName;
	private String deptNO;
	private String companyNO;

	public HashMap<String, String> getProjectionMap() {
		return projectionMap;
	}

	public void setProjectionMap(HashMap<String, String> projectionMap) {
		this.projectionMap = projectionMap;
	}
	
	  public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getSerialID() {
		return serialID;
	}

	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}

	public String getDeptNO() {
		return deptNO;
	}

	public void setDeptNO(String deptNO) {
		this.deptNO = deptNO;
	}

	public String getCompanyNO() {
		return companyNO;
	}

	public void setCompanyNO(String companyNO) {
		this.companyNO = companyNO;
	}
	  

}
