package com.lik.android.om;

import java.util.HashMap;

public abstract class BaseSEe5a extends BaseOM<SEe5a> {

	private static final long serialVersionUID = 7873426620734841571L;

	public static final String TABLE_NAME = "SEe5a";
	public static final String TABLE_GROUP_NAME = "銷售帳款統計表";

	public static final String TABLE_CH_NAME = "銷售帳款統計表(當日)";

	/**
     * Column name for 序號 of the SEe5
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

	/**
     * Column name for SALE_SALES_NO of the SEe5
     * <P>Type: STRING</P>
     */
    public static final String COLUMN_NAME_SALE_SALES_NO = "SALE_SALES_NO";

	/**
     * Column name for SALE_NAME of the SEe5
     * <P>Type: STRING</P>
     */
    public static final String COLUMN_NAME_SALE_NAME = "SALE_NAME";

	/**
     * Column name for S_AMOUNT of the SEe5
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_S_AMOUNT = "S_AMOUNT";

	/**
     * Column name for S_RETURNED of the SEe5
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_S_RETURNED = "S_RETURNED";

	/**
     * Column name for S_BAD_DEBT of the SEe5
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_S_BAD_DEBT = "S_BAD_DEBT";

	/**
     * Column name for S_SAMPLE_COST of the SEe5
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_S_SAMPLE_COST = "S_SAMPLE_COST";

	/**
     * Column name for S_SELL_COST of the SEe5
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_S_SELL_COST = "S_SELL_COST";

	/**
     * Column name for S_SALE_COST of the SEe5
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_S_SALE_COST = "S_SALE_COST";

	/**
     * Column name for S_BACK_COST of the SEe5
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_S_BACK_COST = "S_BACK_COST";

	/**
     * Column name for S_SEND_COST of the SEe5
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_S_SEND_COST = "S_SEND_COST";

	/**
     * Column name for S_RECEIVABLE of the SEe5
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_S_RECEIVABLE = "S_RECEIVABLE";

	/**
     * Column name for S_GROSS_PROFIT of the SEe5
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_S_GROSS_PROFIT = "S_GROSS_PROFIT";

	/**
     * Column name for S_RATE of the SEe5
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_S_RATE = "S_RATE";

	/**
     * Column name for S_AMOUNT of the SEe5
     * <P>Type: DOUBLE</P>
     */
    public static final String COLUMN_NAME_S_RETURN_RATE = "S_RETURN_RATE";

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_SEE5_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_SALE_SALES_NO,  // Projection position 1, 
    	COLUMN_NAME_SALE_NAME, // Projection position 2, 
    	COLUMN_NAME_S_AMOUNT, // Projection position 3, 
    	COLUMN_NAME_S_RETURNED, // Projection position 4, 
    	COLUMN_NAME_S_BAD_DEBT, // Projection position 5, 
    	COLUMN_NAME_S_SAMPLE_COST, // Projection position 6, 
    	COLUMN_NAME_S_SELL_COST, // Projection position 7, 
    	COLUMN_NAME_S_SALE_COST, // Projection position 8, 
    	COLUMN_NAME_S_BACK_COST, // Projection position 9, 
    	COLUMN_NAME_S_SEND_COST, // Projection position 10, 
    	COLUMN_NAME_S_RECEIVABLE, // Projection position 11, 
    	COLUMN_NAME_S_GROSS_PROFIT, // Projection position 12, 
    	COLUMN_NAME_S_RATE, // Projection position 13, 
    	COLUMN_NAME_S_RETURN_RATE, // Projection position 14, 
    };
    protected static final int READ_SEE5_SERIALID_INDEX = 0;
    protected static final int READ_SEE5_SALE_SALES_NO_INDEX = 1;
    protected static final int READ_SEE5_SALE_NAME_INDEX = 2;
    protected static final int READ_SEE5_S_AMOUNT_INDEX = 3;
    protected static final int READ_SEE5_S_RETURNED_INDEX = 4;
    protected static final int READ_SEE5_S_BAD_DEBT_INDEX = 5;
    protected static final int READ_SEE5_S_SAMPLE_COST_INDEX = 6;
    protected static final int READ_SEE5_S_SELL_COST_INDEX = 7;
    protected static final int READ_SEE5_S_SALE_COST_INDEX = 8;
    protected static final int READ_SEE5_S_BACK_COST_INDEX = 9;
    protected static final int READ_SEE5_S_SEND_COST_INDEX = 10;
    protected static final int READ_SEE5_S_RECEIVABLE_INDEX = 11;
    protected static final int READ_SEE5_S_GROSS_PROFIT_INDEX = 12;
    protected static final int READ_SEE5_S_RATE_INDEX = 13;
    protected static final int READ_SEE5_S_RETURN_RATE_INDEX = 14;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseSEe5a() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_SALE_SALES_NO, COLUMN_NAME_SALE_SALES_NO);
    	projectionMap.put(COLUMN_NAME_SALE_NAME, COLUMN_NAME_SALE_NAME);
    	projectionMap.put(COLUMN_NAME_S_AMOUNT, COLUMN_NAME_S_AMOUNT);
    	projectionMap.put(COLUMN_NAME_S_RETURNED, COLUMN_NAME_S_RETURNED);
    	projectionMap.put(COLUMN_NAME_S_BAD_DEBT, COLUMN_NAME_S_BAD_DEBT);
    	projectionMap.put(COLUMN_NAME_S_SAMPLE_COST, COLUMN_NAME_S_SAMPLE_COST);
    	projectionMap.put(COLUMN_NAME_S_SELL_COST, COLUMN_NAME_S_SELL_COST);
    	projectionMap.put(COLUMN_NAME_S_SALE_COST, COLUMN_NAME_S_SALE_COST);
    	projectionMap.put(COLUMN_NAME_S_BACK_COST, COLUMN_NAME_S_BACK_COST);
    	projectionMap.put(COLUMN_NAME_S_SEND_COST, COLUMN_NAME_S_SEND_COST);
    	projectionMap.put(COLUMN_NAME_S_RECEIVABLE, COLUMN_NAME_S_RECEIVABLE);
    	projectionMap.put(COLUMN_NAME_S_GROSS_PROFIT, COLUMN_NAME_S_GROSS_PROFIT);
    	projectionMap.put(COLUMN_NAME_S_RATE, COLUMN_NAME_S_RATE);
    	projectionMap.put(COLUMN_NAME_S_RETURN_RATE, COLUMN_NAME_S_RETURN_RATE);
    }

    @Override
	public String getTableName() {
//		return TABLE_NAME;
		return TABLE_NAME+"_"+getTableCompanyID();
	}

	@Override
	public String getCreateCMD() {
		String CREATE_CMD = "CREATE TABLE IF NOT EXISTS "+getTableName()+" ("+
	    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
	    		COLUMN_NAME_SALE_SALES_NO+" TEXT,"+
	    		COLUMN_NAME_SALE_NAME+" TEXT,"+
	    		COLUMN_NAME_S_AMOUNT+" REAL,"+
	    		COLUMN_NAME_S_RETURNED+" REAL,"+
	    		COLUMN_NAME_S_BAD_DEBT+" REAL,"+
	    		COLUMN_NAME_S_SAMPLE_COST+" REAL,"+
	    		COLUMN_NAME_S_SELL_COST+" REAL,"+
	    		COLUMN_NAME_S_SALE_COST+" REAL,"+
	    		COLUMN_NAME_S_BACK_COST+" REAL,"+
	    		COLUMN_NAME_S_SEND_COST+" REAL,"+
	    		COLUMN_NAME_S_RECEIVABLE+" REAL,"+
	    		COLUMN_NAME_S_GROSS_PROFIT+" REAL,"+
	    		COLUMN_NAME_S_RATE+" REAL,"+
	    		COLUMN_NAME_S_RETURN_RATE+" REAL);";
		return CREATE_CMD;
	}

	@Override
	public String[] getCreateIndexCMD() {
		String[] CREATE_INDEX_CMD = {
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P1 ON "+getTableName()+" ("+COLUMN_NAME_SALE_SALES_NO+");",
		    	"CREATE  INDEX IF NOT EXISTS "+getTableName()+"P2 ON "+getTableName()+" ("+COLUMN_NAME_SALE_NAME+");",
		    };
		return CREATE_INDEX_CMD;
	}

	@Override
	public String getDropCMD() {
		String DROP_CMD = "DROP TABLE IF EXISTS "+getTableName();
		return DROP_CMD;
	}

    private long serialID; //key
    private String SALE_SALES_NO;
    private String SALE_NAME;
    private double S_AMOUNT;
    private double S_RETURNED;
    private double S_BAD_DEBT;
    private double S_SAMPLE_COST;
    private double S_SELL_COST;
    private double S_SALE_COST;
    private double S_BACK_COST;
    private double S_SEND_COST;
    private double S_RECEIVABLE;
    private double S_GROSS_PROFIT;
    private double S_RATE;
    private double S_RETURN_RATE;

	public long getSerialID() {
		return serialID;
	}

	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}

	public String getSALE_SALES_NO() {
		return SALE_SALES_NO;
	}

	public void setSALE_SALES_NO(String sALE_SALES_NO) {
		SALE_SALES_NO = sALE_SALES_NO;
	}

	public String getSALE_NAME() {
		return SALE_NAME;
	}

	public void setSALE_NAME(String sALE_NAME) {
		SALE_NAME = sALE_NAME;
	}

	public double getS_AMOUNT() {
		return S_AMOUNT;
	}

	public void setS_AMOUNT(double s_AMOUNT) {
		S_AMOUNT = s_AMOUNT;
	}

	public double getS_RETURNED() {
		return S_RETURNED;
	}

	public void setS_RETURNED(double s_RETURNED) {
		S_RETURNED = s_RETURNED;
	}

	public double getS_BAD_DEBT() {
		return S_BAD_DEBT;
	}

	public void setS_BAD_DEBT(double s_BAD_DEBT) {
		S_BAD_DEBT = s_BAD_DEBT;
	}

	public double getS_SAMPLE_COST() {
		return S_SAMPLE_COST;
	}

	public void setS_SAMPLE_COST(double s_SAMPLE_COST) {
		S_SAMPLE_COST = s_SAMPLE_COST;
	}

	public double getS_SELL_COST() {
		return S_SELL_COST;
	}

	public void setS_SELL_COST(double s_SELL_COST) {
		S_SELL_COST = s_SELL_COST;
	}

	public double getS_SALE_COST() {
		return S_SALE_COST;
	}

	public void setS_SALE_COST(double s_SALE_COST) {
		S_SALE_COST = s_SALE_COST;
	}

	public double getS_BACK_COST() {
		return S_BACK_COST;
	}

	public void setS_BACK_COST(double s_BACK_COST) {
		S_BACK_COST = s_BACK_COST;
	}

	public double getS_SEND_COST() {
		return S_SEND_COST;
	}

	public void setS_SEND_COST(double s_SEND_COST) {
		S_SEND_COST = s_SEND_COST;
	}

	public double getS_RECEIVABLE() {
		return S_RECEIVABLE;
	}

	public void setS_RECEIVABLE(double s_RECEIVABLE) {
		S_RECEIVABLE = s_RECEIVABLE;
	}

	public double getS_GROSS_PROFIT() {
		return S_GROSS_PROFIT;
	}

	public void setS_GROSS_PROFIT(double s_GROSS_PROFIT) {
		S_GROSS_PROFIT = s_GROSS_PROFIT;
	}

	public double getS_RATE() {
		return S_RATE;
	}

	public void setS_RATE(double s_RATE) {
		S_RATE = s_RATE;
	}

	public double getS_RETURN_RATE() {
		return S_RETURN_RATE;
	}

	public void setS_RETURN_RATE(double s_RETURN_RATE) {
		S_RETURN_RATE = s_RETURN_RATE;
	}
    
    
}
