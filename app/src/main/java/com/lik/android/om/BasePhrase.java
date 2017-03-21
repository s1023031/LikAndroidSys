package com.lik.android.om;

import java.util.HashMap;

import com.lik.android.main.MainMenuActivity;

public abstract class BasePhrase extends BaseOM<Phrase> {

	private static final long serialVersionUID = 6366446459657201803L;

	public static final String TABLE_NAME = "Phrase";

	public static final String TABLE_CH_NAME = "���J���";

	public static final int PHKINDNO_1 = 1; // -- PDA�b�O
	public static final int PHKINDNO_2 = 2; // -- PDA����O
	public static final int PHKINDNO_3 = 3; // -- �Ȥ�������
	public static final int PHKINDNO_4 = 4; // -- PDA����Ƶ� (��0238)
	public static final int PHKINDNO_5 = 5; // -- PDA�]�w
	public static final int PHKINDNO_6 = 6; // -- ERROR code 
	public static final int PHKINDNO_7 = 7; // -- ERROR code 
	public static final int PHKINDNO_8 = 8; // -- �ʶR�Ȥ�ID 
	public static final int PHKINDNO_9 = 9; // -- �馩�v 
	public static final int PHKINDNO_10 = 10; // -- ������k
	public static final int PHKINDNO_11 = 11; // -- ������O
	public static final int PHKINDNO_12 = 12; // -- bank
	public static final int PHKINDNO_13 = 13; // -- �w�p�I�{��
	public static final int PHKINDNO_14 = 14; // -- �M�׷ӬۤW�ǥؿ�
	public static final int PHKINDNO_15 = 15; // -- �¦ʦ���
	public static final int PHKINDNO_18 = 18; // -- �̧C����ˬd
	/*
	 *           �ť� (���])  �~��\�M�צW��\�~�ȥN��\�Ȥ�W�� (�ثe�{��)
                 1       �W�ǥؿ�=>�~��
                 2       �W�ǥؿ�=>�~��\�M�צW��
                 3       �W�ǥؿ�=>�~��\�M�צW��\�~�ȥN��

	 */

	public static final String PHPHRASENO_1 = "1"; // -- ����b�O�w�]�w�q 
	public static final String PHPHRASENO_4 = "4"; // -- ������O4 
	public static final String PHPHRASENO_5 = "5"; // ���O�W�O�_�n��J�馩  
	public static final String PHPHRASENO_6 = "6"; // -- ����qty�p�Ʀ�� 
	public static final String PHPHRASENO_7 = "7"; // -- PHRASE_DESC=S �� �L�����J   ���O���C�Ӥ��H�t�Ӭ��D
	public static final String PHPHRASENO_8 = "8"; // ���O�W�O�_�n��J�馩�O�_�ο諸
	public static final String PHPHRASENO_1_1 = "1"; // -- ����b�O-1
	public static final String PHPHRASENO_1_2 = "2"; // -- ����b�O-2
	public static final String PHPHRASENO_1_3 = "3"; // -- ����b�O-3
	public static final String PHPHRASENO_1_4 = "4"; // -- ����b�O-4
	public static final String PHPHRASENO_18_1 = "1"; // -- �̧C����ˬd-1
	public static final String PHPHRASENO_18_2 = "2"; // -- �̧C����ˬd-2
	public static final String PHPHRASENO_18_3 = "3"; // -- �̧C����ˬd-3
	public static final String PHPHRASENO_18_4 = "4"; // -- �̧C����ˬd-4
	public static final String PHPHRASENO_18_5 = "5"; // -- �̧C����ˬd-5
	public static final String PHPHRASENO_18_6 = "6"; // -- �̧C����ˬd-6
	public static final String PHPHRASENO_18_7 = "7"; // -- �̧C����ˬd-7
	public static final String PHPHRASENO_PRICE = "������k"; // ������k
	public static final String PHPHRASENO_MARK = "������O"; // ������O
	public static final String PHPHRASENO_ID = "ID"; // ID
	public static final String PHRASE_DESC_S = "S"; // -- ���O���C�Ӥ��H�t�Ӭ��D
	public static final String PHRASE_DESC_C = "C"; // -- ���O���C�Ӥ��H�Ȥᬰ�D
	public static final String PHRASE_DESC_Y = "Y"; 
	public static final String PHRASE_DESC_1 = "1"; 
	public static final String PHRASE_DESC_SHD = "SHD"; // �@��
	public static final String PHRASE_DESC_YOX = "YOX"; // �S��
	public static final String PHRASE_DESC_ZFS = "ZFS"; // �i�ײ�
	public static final String PHRASE_DESC_JYU = "JYU"; // �[�|
	public static final String PHRASE_DESC_FTN = "FTN"; // �o��
	public static final String PHRASE_DESC_HEB = "HEB"; // �¦�
	public static final String PHRASE_DESC_YLN = "YLN"; // �o��
	public static final String PHRASE_DESC_XIJ = "XIJ"; // �H�[
	public static final String PHRASE_DESC_SND = "SND"; // ��@�o
	public static final String PHRASE_DESC_WHU = "WHU"; // �U��
	public static final String PHRASE_DESC_XYU = "XYU"; // �s��
	public static final String PHRASE_DESC_LIK = "LIK"; // �O�a
	public static final String PHRASE_DESC_SXI = "SXI"; // �@��
	public static final String PHRASE_DESC_TEST = "1234"; // ����
	public static final String PHRASE_DESC_QHU = "QHU"; // �d�~
	
	/**
     * Column name for �Ǹ� of the Phrase
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

	/**
     * Column name for kindNo of the Phrase
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_PHKINDNO = "PhkindNO";

	/**
     * Column name for ���J�N�� of the Phrase
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_PHRASENO = "PhraseNO";

	/**
     * Column name for ���J���e of the Phrase
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_PHRASEDESC = "PhraseDESC";

    /**
     * Column name for ���� of the Phrase
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_VERSIONNO = "VersionNo";

    public static final String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+
    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
    		COLUMN_NAME_PHKINDNO+" INTEGER,"+
    		COLUMN_NAME_PHRASENO+" TEXT,"+
    		COLUMN_NAME_PHRASEDESC+" TEXT,"+
    		COLUMN_NAME_VERSIONNO+" TEXT);";

    public static final String[] CREATE_INDEX_CMD = {
    	"CREATE  INDEX IF NOT EXISTS "+TABLE_NAME+"P1 ON "+TABLE_NAME+" ("+COLUMN_NAME_PHKINDNO+");",
    	"CREATE  INDEX IF NOT EXISTS "+TABLE_NAME+"P2 ON "+TABLE_NAME+" ("+COLUMN_NAME_PHKINDNO+","+COLUMN_NAME_PHRASENO+");",
    };
    
    public static final String DROP_CMD = "DROP TABLE IF EXISTS "+TABLE_NAME;

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_PHRASE_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_PHKINDNO,  // Projection position 1, 
    	COLUMN_NAME_PHRASENO, // Projection position 2, 
    	COLUMN_NAME_PHRASEDESC, // Projection position 3, 
    	COLUMN_NAME_VERSIONNO, // Projection position 4, 
    };
    protected static final int READ_PHRASE_SERIALID_INDEX = 0;
    protected static final int READ_PHRASE_PHKINDNO_INDEX = 1;
    protected static final int READ_PHRASE_PHRASENO_INDEX = 2;
    protected static final int READ_PHRASE_PHRASEDESC_INDEX = 3;
    protected static final int READ_PHRASE_VERSIONNO_INDEX = 4;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BasePhrase() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_PHKINDNO, COLUMN_NAME_PHKINDNO);
    	projectionMap.put(COLUMN_NAME_PHRASENO, COLUMN_NAME_PHRASENO);
    	projectionMap.put(COLUMN_NAME_PHRASEDESC, COLUMN_NAME_PHRASEDESC);
    	projectionMap.put(COLUMN_NAME_VERSIONNO, COLUMN_NAME_VERSIONNO);
    }

    @Override
	public String getTableName() {
		setCompanyParent(null);
    	if(getCompanyParent()==null)
    		//setCompanyParent(MainMenuActivity.omCurrentSysProfile.getCompanyNo());
    		setCompanyParent(MainMenuActivity.companyParent);
    
		return TABLE_NAME+"_"+getCompanyParent();
	}

	@Override
	public String getCreateCMD() {
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		return CREATE_INDEX_CMD;
	}

	@Override
	public String getDropCMD() {
		return DROP_CMD;
	}

    private long serialID; //key
	private int phkindNO;
	private String phraseNO;
	private String phraseDESC;
	private String versionNo;

	public long getSerialID() {
		return serialID;
	}

	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}

	public int getPhkindNO() {
		return phkindNO;
	}

	public void setPhkindNO(int phkindNO) {
		this.phkindNO = phkindNO;
	}

	public String getPhraseNO() {
		return phraseNO;
	}

	public void setPhraseNO(String phraseNO) {
		this.phraseNO = phraseNO;
	}

	public String getPhraseDESC() {
		return phraseDESC;
	}

	public void setPhraseDESC(String phraseDESC) {
		this.phraseDESC = phraseDESC;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	
}
