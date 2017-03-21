package com.lik.android.om;

import java.util.Date;
import java.util.HashMap;

import com.lik.android.main.MainMenuActivity;

/**
 * ��Ƥ����q�s�� companyID_tableName
 * @author charles
 *
 */
public abstract class BaseBulletin extends BaseOM<Bulletin> {

	private static final long serialVersionUID = -4043155655359134798L;

	public static final String TABLE_NAME = "Bulletin";

	public static final String TABLE_CH_NAME = "���i���";

	/**
     * Column name for �Ǹ� of the Bulletin
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for ���q�y���� of the Bulletin
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";

    /**
     * Column name for PDA���G��y���� of the Bulletin
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_BULLETINID = "BulletinID";

    /**
     * Column name for ���G�_�� of the Bulletin
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DATEFROM = "DateFrom";

    /**
     * Column name for ���G��� of the Bulletin
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DATETO = "DateTo";

    /**
     * Column name for ���G��� of the Bulletin
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_BUILDDATE = "BuildDate";

    /**
     * Column name for ���i�� of the Bulletin
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_BULLETINFROM = "BulletinFrom";

    /**
     * Column name for �D  �� of the Bulletin
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_BULLETINSUBJECT = "BulletinSubject";

    /**
     * Column name for ��  �� of the Bulletin
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_BULLETINBODY = "BulletinBody";

    /**
     * Column name for ���� of the Bulletin
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_VERSIONNO = "VersionNo";

//    private String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
//    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
//    		COLUMN_NAME_COMPANYID+" INTEGER,"+
//    		COLUMN_NAME_BULLETINID+" INTEGER,"+
//    		COLUMN_NAME_DATEFROM+" TEXT,"+
//    		COLUMN_NAME_DATETO+" TEXT,"+
//    		COLUMN_NAME_BUILDDATE+" TEXT,"+
//    		COLUMN_NAME_BULLETINFROM+" TEXT,"+
//    		COLUMN_NAME_BULLETINSUBJECT+" TEXT,"+
//    		COLUMN_NAME_BULLETINBODY+" TEXT,"+
//    		COLUMN_NAME_VERSIONNO+" TEXT);";
//
//    private String[] CREATE_INDEX_CMD = {
//    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+COLUMN_NAME_COMPANYID+","+COLUMN_NAME_BULLETINID+");",
//    };
//    
//    private String DROP_CMD = "DROP TABLE IF EXISTS "+getTableName();

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_BULLETIN_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_COMPANYID,  // Projection position 1, 
    	COLUMN_NAME_BULLETINID, // Projection position 2, 
    	COLUMN_NAME_DATEFROM, // Projection position 3, 
    	COLUMN_NAME_DATETO, // Projection position 4, 
    	COLUMN_NAME_BUILDDATE, // Projection position 5, 
    	COLUMN_NAME_BULLETINFROM, // Projection position 6, 
    	COLUMN_NAME_BULLETINSUBJECT, // Projection position 7, 
    	COLUMN_NAME_BULLETINBODY, // Projection position 8, 
    	COLUMN_NAME_VERSIONNO, // Projection position 9, 
    };
    protected static final int READ_BULLETIN_SERIALID_INDEX = 0;
    protected static final int READ_BULLETIN_COMPANYID_INDEX = 1;
    protected static final int READ_BULLETIN_BULLETINID_INDEX = 2;
    protected static final int READ_BULLETIN_DATEFROM_INDEX = 3;
    protected static final int READ_BULLETIN_DATETO_INDEX = 4;
    protected static final int READ_BULLETIN_BUILDDATE_INDEX = 5;
    protected static final int READ_BULLETIN_BULLETINFROM_INDEX = 6;
    protected static final int READ_BULLETIN_BULLETINSUBJECT_INDEX = 7;
    protected static final int READ_BULLETIN_BULLETINBODY_INDEX = 8;
    protected static final int READ_BULLETIN_VERSIONNO_INDEX = 9;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseBulletin() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
    	projectionMap.put(COLUMN_NAME_BULLETINID, COLUMN_NAME_BULLETINID);
    	projectionMap.put(COLUMN_NAME_DATEFROM, COLUMN_NAME_DATEFROM);
    	projectionMap.put(COLUMN_NAME_DATETO, COLUMN_NAME_DATETO);
    	projectionMap.put(COLUMN_NAME_BUILDDATE, COLUMN_NAME_BUILDDATE);
    	projectionMap.put(COLUMN_NAME_BULLETINFROM, COLUMN_NAME_BULLETINFROM);
    	projectionMap.put(COLUMN_NAME_BULLETINSUBJECT, COLUMN_NAME_BULLETINSUBJECT);
    	projectionMap.put(COLUMN_NAME_BULLETINBODY, COLUMN_NAME_BULLETINBODY);
    	projectionMap.put(COLUMN_NAME_VERSIONNO, COLUMN_NAME_VERSIONNO);
    }

    @Override
	public String getTableName() {
    	setTableCompanyID(0);
		setCompanyParent(null);
		if(getTableCompanyID()==0) 
    		setTableCompanyID(MainMenuActivity.companyID);
    	if(getCompanyParent()==null)
    		setCompanyParent(MainMenuActivity.companyParent);

		return TABLE_NAME+"_"+getCompanyParent()+(getTableCompanyID()==0?companyID:getTableCompanyID());
	}

	@Override
	public String getCreateCMD() {
		String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
	    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
	    		COLUMN_NAME_COMPANYID+" INTEGER,"+
	    		COLUMN_NAME_BULLETINID+" INTEGER,"+
	    		COLUMN_NAME_DATEFROM+" TEXT,"+
	    		COLUMN_NAME_DATETO+" TEXT,"+
	    		COLUMN_NAME_BUILDDATE+" TEXT,"+
	    		COLUMN_NAME_BULLETINFROM+" TEXT,"+
	    		COLUMN_NAME_BULLETINSUBJECT+" TEXT,"+
	    		COLUMN_NAME_BULLETINBODY+" TEXT,"+
	    		COLUMN_NAME_VERSIONNO+" TEXT);";
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+COLUMN_NAME_COMPANYID+","+COLUMN_NAME_BULLETINID+");",
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
	private int bulletinID;
	private Date dateFrom;
	private Date dateTo;
	private Date buildDate;
	private String bulletinFrom;
	private String bulletinSubject;
	private String bulletinBody;
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

	public int getBulletinID() {
		return bulletinID;
	}

	public void setBulletinID(int bulletinID) {
		this.bulletinID = bulletinID;
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

	public Date getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	public String getBulletinFrom() {
		return bulletinFrom;
	}

	public void setBulletinFrom(String bulletinFrom) {
		this.bulletinFrom = bulletinFrom;
	}

	public String getBulletinSubject() {
		return bulletinSubject;
	}

	public void setBulletinSubject(String bulletinSubject) {
		this.bulletinSubject = bulletinSubject;
	}

	public String getBulletinBody() {
		return bulletinBody;
	}

	public void setBulletinBody(String bulletinBody) {
		this.bulletinBody = bulletinBody;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	
}
