package com.lik.android.om;

import java.util.HashMap;

public abstract class BaseCompany extends BaseOM<Company> {

	private static final long serialVersionUID = -1992517845496501114L;

	public static final String TABLE_NAME = "Company";

	public static final String TABLE_CH_NAME = "���q���";

	public static final String DATE_FPRMAT_1 = "yyyy/MM/dd"; // ����
	public static final String DATE_FPRMAT_2 = "yyyy/MM/dd"; // ���
	public static final String DATE_FPRMAT_3 = "MM/dd/yyyy"; // ���
	public static final String DATE_FPRMAT_4 = "dd/MM/yyyy"; // ���

	public static final String UI_FPRMAT_2 = "2"; // V2
	public static final String UI_FPRMAT_3 = "3"; // V3

	/**
     * Column name for �Ǹ� of the Company
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for ���q�y���� of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";

    /**
     * Column name for �ާ@�H���N�� of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_USERNO = "UserNO";

    /**
     * Column name for ���q�N�� of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYNO = "CompanyNO";

    /**
     * Column name for ���q���� of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYNM = "CompanyNM";

    /**
     * Column name for ���q�a�} of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ADDRESS = "Address";

    /**
     * Column name for ��ʹq�� of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_TELNO = "TelNo";

    /**
     * Column name for ����榡 of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DATEFORMAT = "DateFormat";

    /**
     * Column name for UI�榡 of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UIFORMAT = "UiFormat";

    /**
     * Column name for �i�X���榳�馩�_ Y/N of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ISDISCOUNT = "IsDiscount";

    /**
     * Column name for �P�f��ƾ�Ʀ�� of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SDISCTINTEGER = "SdisctInteger";

    /**
     * Column name for �P�f��Ƥp�Ʀ�� of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SDISCTDECIMAL = "SdisctDecimal";

    /**
     * Column name for �P�f��������p�Ʀ�� of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_NSUPRDECIMAL = "NsuprDecimal";

    /**
     * Column name for ���B�p�Ʀ�� of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_NSAMTDECIMAL = "NsamtDecimal";

    /**
     * Column name for ���B�p�Ʀ�� of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_BSQTYDECIMAL = "BsqtyDecimal";

    /**
     * Column name for ���B�p�Ʀ�� of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_TKQTYDECIMAL = "TkqtyDecimal";

    /**
     * Column name for ���B�p�Ʀ�� of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ACDECIMAL = "AcDecimal";

    /**
     * Column name for ���� of the Company
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_VERSIONNO = "VersionNo";
    
    public static final String COLUMN_NAME_COMPANYPARENT = "CompanyParent";

    public static final String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+
    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
    		COLUMN_NAME_COMPANYID+" INTEGER,"+
    		COLUMN_NAME_USERNO+" TEXT,"+
    		COLUMN_NAME_COMPANYNO+" TEXT,"+
    		COLUMN_NAME_COMPANYNM+" TEXT,"+
    		COLUMN_NAME_ADDRESS+" TEXT,"+
    		COLUMN_NAME_TELNO+" TEXT,"+
    		COLUMN_NAME_DATEFORMAT+" TEXT,"+
    		COLUMN_NAME_COMPANYPARENT+" TEXT );";
    

    public static final String[] CREATE_INDEX_CMD = {
    	"CREATE  INDEX IF NOT EXISTS "+TABLE_NAME+"P1 ON "+TABLE_NAME+" ("+COLUMN_NAME_COMPANYID+");",
    	"CREATE  INDEX IF NOT EXISTS "+TABLE_NAME+"P2 ON "+TABLE_NAME+" ("+COLUMN_NAME_USERNO+");",
    	"CREATE  INDEX IF NOT EXISTS "+TABLE_NAME+"P3 ON "+TABLE_NAME+" ("+COLUMN_NAME_COMPANYID+","+COLUMN_NAME_USERNO+");",
    };
    
    public static final String DROP_CMD = "DROP TABLE IF EXISTS "+TABLE_NAME;

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_COMPANY_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_COMPANYID,  // Projection position 1, 
    	COLUMN_NAME_USERNO, // Projection position 2, 
    	COLUMN_NAME_COMPANYNO, // Projection position 3, 
    	COLUMN_NAME_COMPANYNM, // Projection position 4, 
    	COLUMN_NAME_ADDRESS, // Projection position 5, 
    	COLUMN_NAME_TELNO, // Projection position 6, 
    	COLUMN_NAME_DATEFORMAT, // Projection position 7, 
    	COLUMN_NAME_COMPANYPARENT, // Projection position 8, 
    };
    protected static final int READ_COMPANY_SERIALID_INDEX = 0;
    protected static final int READ_COMPANY_COMPANYID_INDEX = 1;
    protected static final int READ_COMPANY_USERNO_INDEX = 2;
    protected static final int READ_COMPANY_COMPANYNO_INDEX = 3;
    protected static final int READ_COMPANY_COMPANYNM_INDEX = 4;
    protected static final int READ_COMPANY_ADDRESS_INDEX = 5;
    protected static final int READ_COMPANY_TELNO_INDEX = 6;
    protected static final int READ_COMPANY_DATEFORMAT_INDEX = 7;
    protected static final int READ_COMPANY_COMPANYPARENT_INDEX = 8;
    /*protected static final int READ_COMPANY_UIFORMAT_INDEX = 8;
    protected static final int READ_COMPANY_ISDISCOUNT_INDEX = 9;
    protected static final int READ_COMPANY_SDISCTINTEGER_INDEX = 10;
    protected static final int READ_COMPANY_SDISCTDECIMAL_INDEX = 11;
    protected static final int READ_COMPANY_NSUPRDECIMAL_INDEX = 12;
    protected static final int READ_COMPANY_NSAMTDECIMAL_INDEX = 13;
    protected static final int READ_COMPANY_BSQTYDECIMAL_INDEX = 14;
    protected static final int READ_COMPANY_TKQTYDECIMAL_INDEX = 15;
    protected static final int READ_COMPANY_ACDECIMAL_INDEX = 16;
    protected static final int READ_COMPANY_VERSIONNO_INDEX = 17;*/

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseCompany() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
    	projectionMap.put(COLUMN_NAME_USERNO, COLUMN_NAME_USERNO);
    	projectionMap.put(COLUMN_NAME_COMPANYNO, COLUMN_NAME_COMPANYNO);
    	projectionMap.put(COLUMN_NAME_COMPANYNM, COLUMN_NAME_COMPANYNM);
    	projectionMap.put(COLUMN_NAME_ADDRESS, COLUMN_NAME_ADDRESS);
    	projectionMap.put(COLUMN_NAME_TELNO, COLUMN_NAME_TELNO);
    	projectionMap.put(COLUMN_NAME_DATEFORMAT, COLUMN_NAME_DATEFORMAT);
    	projectionMap.put(COLUMN_NAME_COMPANYPARENT, COLUMN_NAME_COMPANYPARENT);
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
		return CREATE_INDEX_CMD;
	}

    private long serialID; //key
	private int companyID;
	private String userNO;
	private String companyNO;
	private String companyNM;
	private String address;
	private String telNo;
	private String dateFormat;
	private String companyParent;

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


	public String getCompanyNO() {
		return companyNO;
	}


	public void setCompanyNO(String companyNO) {
		this.companyNO = companyNO;
	}


	public String getCompanyNM() {
		return companyNM;
	}


	public void setCompanyNM(String companyNM) {
		this.companyNM = companyNM;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getTelNo() {
		return telNo;
	}


	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}


	public String getDateFormat() {
		return dateFormat;
	}


	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getCompanyParent() {
		return companyParent;
	}

	public void setCompanyParent(String companyParent) {
		this.companyParent = companyParent;
	}
	
}
