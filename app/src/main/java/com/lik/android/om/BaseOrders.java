package com.lik.android.om;

import java.util.Date;
import java.util.HashMap;

import com.lik.Constant;

public abstract class BaseOrders extends BaseOM<Orders> {

	private static final long serialVersionUID = 7162982374791791255L;

	public static final String TABLE_NAME = "Orders";

	public static final String TABLE_CH_NAME = "�C��Ǹ����";
	
	public static final String UPLOADFLAG_Y = "Y";
	public static final String UPLOADFLAG_N = "N";
	public static final String REPLYFLAG_Y = "Y";
	public static final String REPLYFLAG_N = "N";

	/**
     * Column name for �Ǹ� of the Orders
     * <P>Type: INTEGER</P>
     */
    public static final String COLUMN_NAME_SERIALID = "SerialID";

    /**
     * Column name for tablet�Ǹ� of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_TABLETSERIALNO = "TabletSerialNO";

    /**
     * Column name for �q��y���� of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ORDERID = "OrderID";

    /**
     * Column name for ��J���� of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_VIEWORDER = "ViewOrder";

    /**
     * Column name for ���q�y���� of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_COMPANYID = "CompanyID";

    /**
     * Column name for �ާ@�H���N�� of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_USERNO = "UserNO";

    /**
     * Column name for �}�l����ɶ� of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_ORDERDT = "OrderDT";

    /**
     * Column name for �}�l����ɶ� of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_LASTDT = "LastDT";

    /**
     * Column name for ���w�X�f�� of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SELLDT = "SellDT";

    /**
     * Column name for �Ȥ�y���� of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CUSTOMERID = "CustomerID";

    /**
     * Column name for ���b�覡 of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PAYKIND = "PayKind";

    /**
     * Column name for 0-��� 1-�U�� of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PAYNEXTMONTH = "PayNextMonth";

    /**
     * Column name for �~�ȭ��y���� of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_SALESID = "SalesID";

    /**
     * Column name for ���A of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_STATUS = "Status";

    /**
     * Column name for �X�f�Ƶ� of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_NOTE1 = "Note1";

    /**
     * Column name for �h�f�Ƶ� of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_NOTE2 = "Note2";

    /**
     * Column name for �Ȥ�s�� of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CUSTOMERNO = "CustomerNO";

    /**
     * Column name for pdaid of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_PDAID = "PdaId";

    /**
     * Column name for UploadFlag of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_UPLOADFLAG = "UploadFlag";

    /**
     * Column name for ReplyFlag of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_REPLYFLAG = "ReplyFlag";

    /**
     * Column name for CustomerStock of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_CUSTOMERSTOCK = "CustomerStock";

    /**
     * Column name for ReceiveAmt of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_RECEIVEAMT = "ReceiveAmt";

    /**
     * Column name for DeliverViewOrder of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_DELIVER_VIEWORDER = "DeliverViewOrder";
    
    public static final String COLUMN_NAME_COMPANYPARENT = "CompanyParent";

    /**
     * Column name for ���� of the Orders
     * <P>Type: TEXT</P>
     */
    public static final String COLUMN_NAME_VERSIONNO = "VersionNo";

    public static final String CREATE_CMD = "CREATE TABLE "+TABLE_NAME+" ("+
    		COLUMN_NAME_SERIALID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
    		COLUMN_NAME_TABLETSERIALNO+" TEXT,"+
    		COLUMN_NAME_ORDERID+" INTEGER,"+
    		COLUMN_NAME_VIEWORDER+" INTEGER,"+
    		COLUMN_NAME_COMPANYID+" INTEGER,"+
    		COLUMN_NAME_USERNO+" TEXT,"+
    		COLUMN_NAME_ORDERDT+" TEXT,"+
    		COLUMN_NAME_LASTDT+" TEXT,"+
    		COLUMN_NAME_SELLDT+" TEXT,"+
    		COLUMN_NAME_CUSTOMERID+" INTEGER,"+
    		COLUMN_NAME_PAYKIND+" INTEGER,"+
    		COLUMN_NAME_PAYNEXTMONTH+" INTEGER,"+
    		COLUMN_NAME_SALESID+" INTEGER,"+
    		COLUMN_NAME_STATUS+" INTEGER,"+
    		COLUMN_NAME_NOTE1+" TEXT,"+
    		COLUMN_NAME_NOTE2+" TEXT,"+
    		COLUMN_NAME_CUSTOMERNO+" TEXT,"+
    		COLUMN_NAME_PDAID+" INTEGER,"+
    		COLUMN_NAME_UPLOADFLAG+" TEXT,"+
    		COLUMN_NAME_REPLYFLAG+" TEXT,"+
    		COLUMN_NAME_CUSTOMERSTOCK+" INTEGER,"+
    		COLUMN_NAME_RECEIVEAMT+" REAL,"+
    		COLUMN_NAME_DELIVER_VIEWORDER+" INTEGER,"+
    		COLUMN_NAME_VERSIONNO+" TEXT,"+
    		COLUMN_NAME_COMPANYPARENT+" TEXT );";

    public static final String[] CREATE_INDEX_CMD = {
    	"CREATE  INDEX IF NOT EXISTS "+TABLE_NAME+"P1 ON "+TABLE_NAME+" ("+
    			COLUMN_NAME_TABLETSERIALNO+","+COLUMN_NAME_ORDERID+","+COLUMN_NAME_VIEWORDER+
    			","+COLUMN_NAME_COMPANYID+");",
    };
    
    public static final String DROP_CMD = "DROP TABLE IF EXISTS "+TABLE_NAME;

    /**
     * Standard projection for the interesting columns.
     */
    protected static final String[] READ_ORDERS_PROJECTION = new String[] {
    	COLUMN_NAME_SERIALID, // Projection position 0, 
    	COLUMN_NAME_TABLETSERIALNO, // Projection position 1, 
    	COLUMN_NAME_ORDERID, // Projection position 2, 
    	COLUMN_NAME_VIEWORDER, // Projection position 3, 
    	COLUMN_NAME_COMPANYID,  // Projection position 4, 
    	COLUMN_NAME_USERNO, // Projection position 5, 
    	COLUMN_NAME_ORDERDT, // Projection position 6, 
    	COLUMN_NAME_LASTDT, // Projection position 7, 
    	COLUMN_NAME_SELLDT, // Projection position 8, 
    	COLUMN_NAME_CUSTOMERID, // Projection position 9, 
    	COLUMN_NAME_PAYKIND, // Projection position 10, 
    	COLUMN_NAME_PAYNEXTMONTH, // Projection position 11, 
    	COLUMN_NAME_SALESID, // Projection position 12, 
    	COLUMN_NAME_STATUS, // Projection position 13, 
    	COLUMN_NAME_NOTE1, // Projection position 14, 
    	COLUMN_NAME_NOTE2, // Projection position 15, 
    	COLUMN_NAME_CUSTOMERNO, // Projection position 16, 
    	COLUMN_NAME_PDAID, // Projection position 17, 
    	COLUMN_NAME_UPLOADFLAG, // Projection position 18, 
    	COLUMN_NAME_REPLYFLAG, // Projection position 19, 
    	COLUMN_NAME_CUSTOMERSTOCK, // Projection position 20,
    	COLUMN_NAME_RECEIVEAMT, // Projection position 21,
    	COLUMN_NAME_DELIVER_VIEWORDER, // Projection position 22,
    	COLUMN_NAME_VERSIONNO,
    	COLUMN_NAME_COMPANYPARENT,// Projection position 23, 
    };
    protected static final int READ_ORDERS_SERIALID_INDEX = 0;
    protected static final int READ_ORDERS_TABLETSERIALNO_INDEX = 1;
    protected static final int READ_ORDERS_ORDERID_INDEX = 2;
    protected static final int READ_ORDERS_VIEWORDER_INDEX = 3;
    protected static final int READ_ORDERS_COMPANYID_INDEX = 4;
    protected static final int READ_ORDERS_USERNO_INDEX = 5;
    protected static final int READ_ORDERS_ORDERDT_INDEX = 6;
    protected static final int READ_ORDERS_LASTDT_INDEX = 7;
    protected static final int READ_ORDERS_SELLDT_INDEX = 8;
    protected static final int READ_ORDERS_CUSTOMERID_INDEX = 9;
    protected static final int READ_ORDERS_PAYKIND_INDEX = 10;
    protected static final int READ_ORDERS_PAYNEXTMONTH_INDEX = 11;
    protected static final int READ_ORDERS_SALESID_INDEX = 12;
    protected static final int READ_ORDERS_STATUS_INDEX = 13;
    protected static final int READ_ORDERS_NOTE1_INDEX = 14;
    protected static final int READ_ORDERS_NOTE2_INDEX = 15;
    protected static final int READ_ORDERS_CUSTOMERNO_INDEX = 16;
    protected static final int READ_ORDERS_PDAID_INDEX = 17;
    protected static final int READ_ORDERS_UPLOADFLAG_INDEX = 18;
    protected static final int READ_ORDERS_REPLYFLAG_INDEX = 19;
    protected static final int READ_ORDERS_CUSTOMERSTOCK_INDEX = 20;
    protected static final int READ_ORDERS_RECEIVEAMT_INDEX = 21;
    protected static final int READ_ORDERS_DELIVER_VIEWORDER_INDEX = 22;
    protected static final int READ_ORDERS_VERSIONNO_INDEX = 23;
    protected static final int READ_ORDERS_COMPANYPARENT_INDEX = 24;

    // Creates a new projection map instance. The map returns a column name
	// given a string. The two are usually equal.
	HashMap<String, String> projectionMap = new HashMap<String, String>();

    public BaseOrders() {
    	projectionMap.put(COLUMN_NAME_SERIALID, COLUMN_NAME_SERIALID);
    	projectionMap.put(COLUMN_NAME_TABLETSERIALNO, COLUMN_NAME_TABLETSERIALNO);
    	projectionMap.put(COLUMN_NAME_ORDERID, COLUMN_NAME_ORDERID);
    	projectionMap.put(COLUMN_NAME_VIEWORDER, COLUMN_NAME_VIEWORDER);
    	projectionMap.put(COLUMN_NAME_COMPANYID, COLUMN_NAME_COMPANYID);
    	projectionMap.put(COLUMN_NAME_USERNO, COLUMN_NAME_USERNO);
    	projectionMap.put(COLUMN_NAME_ORDERDT, COLUMN_NAME_ORDERDT);
    	projectionMap.put(COLUMN_NAME_LASTDT, COLUMN_NAME_LASTDT);
    	projectionMap.put(COLUMN_NAME_SELLDT, COLUMN_NAME_SELLDT);
    	projectionMap.put(COLUMN_NAME_CUSTOMERID, COLUMN_NAME_CUSTOMERID);
    	projectionMap.put(COLUMN_NAME_PAYKIND, COLUMN_NAME_PAYKIND);
    	projectionMap.put(COLUMN_NAME_PAYNEXTMONTH, COLUMN_NAME_PAYNEXTMONTH);
    	projectionMap.put(COLUMN_NAME_SALESID, COLUMN_NAME_SALESID);
    	projectionMap.put(COLUMN_NAME_STATUS, COLUMN_NAME_STATUS);
    	projectionMap.put(COLUMN_NAME_NOTE1, COLUMN_NAME_NOTE1);
    	projectionMap.put(COLUMN_NAME_NOTE2, COLUMN_NAME_NOTE2);
    	projectionMap.put(COLUMN_NAME_CUSTOMERNO, COLUMN_NAME_CUSTOMERNO);
    	projectionMap.put(COLUMN_NAME_PDAID, COLUMN_NAME_PDAID);
    	projectionMap.put(COLUMN_NAME_UPLOADFLAG, COLUMN_NAME_UPLOADFLAG);
    	projectionMap.put(COLUMN_NAME_REPLYFLAG, COLUMN_NAME_REPLYFLAG);
    	projectionMap.put(COLUMN_NAME_CUSTOMERSTOCK, COLUMN_NAME_CUSTOMERSTOCK);
    	projectionMap.put(COLUMN_NAME_RECEIVEAMT, COLUMN_NAME_RECEIVEAMT);
    	projectionMap.put(COLUMN_NAME_DELIVER_VIEWORDER, COLUMN_NAME_DELIVER_VIEWORDER);
    	projectionMap.put(COLUMN_NAME_VERSIONNO, COLUMN_NAME_VERSIONNO);
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
	public String[] getCreateIndexCMD() {
		return CREATE_INDEX_CMD;
	}

	@Override
	public String getDropCMD() {
		return DROP_CMD;
	}

    private long serialID; //key
	private String tabletSerialNO;
	private int orderID;
	private int viewOrder;
	private int companyID;
	private String userNO;
	private Date orderDT;
	private Date lastDT;
	private Date sellDT;
	private int customerID;
	private int payKind;
	private Integer payNextMonth;
	private int salesID;
	private int status;
	private String note1;
	private String note2;
	private String customerNO;
	private int pdaId;
	private String uploadFlag;
	private String replyFlag;
	private Integer customerStock;
	private Double receiveAmt; // HEB ���ڲ{��
	private Integer deliverViewOrder; // SND
	private String versionNo;
	private String companyParent;

	public String getCompanyParent() {
		return companyParent;
	}

	public void setCompanyParent(String companyParent) {
		this.companyParent = companyParent;
	}

	public long getSerialID() {
		return serialID;
	}

	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}

	public String getTabletSerialNO() {
		return tabletSerialNO;
	}

	public void setTabletSerialNO(String tabletSerialNO) {
		this.tabletSerialNO = tabletSerialNO;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getViewOrder() {
		return viewOrder;
	}

	public void setViewOrder(int viewOrder) {
		this.viewOrder = viewOrder;
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

	public Date getOrderDT() {
		return orderDT;
	}

	public void setOrderDT(Date orderDT) {
		this.orderDT = orderDT;
	}

	public Date getLastDT() {
		return lastDT;
	}

	public void setLastDT(Date lastDT) {
		this.lastDT = lastDT;
	}

	public Date getSellDT() {
		return sellDT;
	}

	public void setSellDT(Date sellDT) {
		this.sellDT = sellDT;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getPayKind() {
		return payKind;
	}

	public void setPayKind(int payKind) {
		this.payKind = payKind;
	}

	public Integer getPayNextMonth() {
		return payNextMonth;
	}

	public void setPayNextMonth(Integer payNextMonth) {
		this.payNextMonth = payNextMonth;
	}

	public int getSalesID() {
		return salesID;
	}

	public void setSalesID(int salesID) {
		this.salesID = salesID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNote1() {
		return note1;
	}

	public void setNote1(String note1) {
		this.note1 = note1;
	}

	public String getNote2() {
		return note2;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}

	public String getCustomerNO() {
		return customerNO;
	}

	public void setCustomerNO(String customerNO) {
		this.customerNO = customerNO;
	}

	public int getPdaId() {
		return pdaId;
	}

	public void setPdaId(int pdaId) {
		this.pdaId = pdaId;
	}

	public String getUploadFlag() {
		return uploadFlag;
	}

	public void setUploadFlag(String uploadFlag) {
		this.uploadFlag = uploadFlag;
	}

	public String getReplyFlag() {
		return replyFlag;
	}

	public void setReplyFlag(String replyFlag) {
		this.replyFlag = replyFlag;
	}

	public Integer getCustomerStock() {
		return customerStock;
	}

	public void setCustomerStock(Integer customerStock) {
		this.customerStock = customerStock;
	}

	public Double getReceiveAmt() {
		return receiveAmt;
	}

	public void setReceiveAmt(Double receiveAmt) {
		this.receiveAmt = receiveAmt;
	}

	public Integer getDeliverViewOrder() {
		return deliverViewOrder;
	}

	public void setDeliverViewOrder(Integer deliverViewOrder) {
		this.deliverViewOrder = deliverViewOrder;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String toXML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<TabletSerialNO>").append(getTabletSerialNO()).append("</TabletSerialNO>").append("\n");
		sb.append("<OrderID>").append(getOrderID()).append("</OrderID>").append("\n");
		sb.append("<ViewOrder>").append(getViewOrder()).append("</ViewOrder>").append("\n");
		sb.append("<CompanyID>").append(getCompanyID()).append("</CompanyID>").append("\n");
		sb.append("<UserNO>").append(getUserNO()).append("</UserNO>").append("\n");
		if(getOrderDT()!=null) sb.append("<OrderDT>").append(Constant.sqliteDF.format(getOrderDT())).append("</OrderDT>").append("\n");
		if(getLastDT()!=null) sb.append("<LastDT>").append(Constant.sqliteDF.format(getLastDT())).append("</LastDT>").append("\n");
		if(getSellDT()!=null) sb.append("<SellDT>").append(Constant.sqliteDFS.format(getSellDT())).append("</SellDT>").append("\n");
		sb.append("<CustomerID>").append(getCustomerID()).append("</CustomerID>").append("\n");
		sb.append("<PayKind>").append(getPayKind()).append("</PayKind>").append("\n");
		if(getPayNextMonth()!=null) sb.append("<PayNextMonth>").append(getPayNextMonth()).append("</PayNextMonth>").append("\n");
		sb.append("<SalesID>").append(getSalesID()).append("</SalesID>").append("\n");
		sb.append("<Status>").append(getStatus()).append("</Status>").append("\n");
		if(getNote1()!=null) sb.append("<Note1>").append("<![CDATA[").append(getNote1()).append("]]>").append("</Note1>").append("\n");
		if(getNote2()!=null) sb.append("<Note2>").append("<![CDATA[").append(getNote2()).append("]]>").append("</Note2>").append("\n");
		sb.append("<CustomerNO>").append(getCustomerNO()).append("</CustomerNO>").append("\n");
		sb.append("<PdaId>").append(getPdaId()).append("</PdaId>").append("\n");
		if(getUploadFlag()!=null) sb.append("<UploadFlag>").append(getUploadFlag()).append("</UploadFlag>").append("\n");
		if(getReplyFlag()!=null) sb.append("<ReplyFlag>").append(getReplyFlag()).append("</ReplyFlag>").append("\n");
		if(getCustomerStock()!=null) sb.append("<CustomerStock>").append(getCustomerStock()).append("</CustomerStock>").append("\n");
		if(getReceiveAmt()!=null) sb.append("<ReceiveAmt>").append(getReceiveAmt()).append("</ReceiveAmt>").append("\n");		
		if(getDeliverViewOrder()!=null) sb.append("<DeliverViewOrder>").append(getDeliverViewOrder()).append("</DeliverViewOrder>").append("\n");
		return sb.toString();
	}
	
	@Override
	public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("Orders:\n");
        str.append("TabletSerialNO = ")
           .append(getTabletSerialNO())
           .append("\n");
        str.append("OrderID = ")
           .append(getOrderID())
           .append("\n");
        str.append("ViewOrder = ")
           .append(getViewOrder())
           .append("\n");
        str.append("CompanyID = ")
           .append(getCompanyID())
           .append("\n");
        str.append("UserNO = ")
           .append(getUserNO())
           .append("\n");
        str.append("OrderDT = ")
           .append(getOrderDT()==null?"null":sdf.format(getOrderDT()))
           .append("\n");
        str.append("LastDT = ")
           .append(getLastDT()==null?"null":sdf.format(getLastDT()))
           .append("\n");
        str.append("SellDT = ")
           .append(getSellDT()==null?"null":sdf2.format(getSellDT()))
           .append("\n");
        str.append("CustomerID = ")
           .append(getCustomerID())
           .append("\n");
        str.append("PayKind = ")
           .append(getPayKind())
           .append("\n");
        str.append("PayNextMonth = ")
           .append(getPayNextMonth())
           .append("\n");
        str.append("SalesID = ")
           .append(getSalesID())
           .append("\n");
        str.append("Status = ")
           .append(getStatus())
           .append("\n");
        str.append("Note1 = ")
           .append(getNote1())
           .append("\n");
        str.append("Note2 = ")
           .append(getNote2())
           .append("\n");
        str.append("CustomerNO = ")
        .append(getCustomerNO())
        .append("\n");
        str.append("PdaId = ")
        .append(getPdaId())
        .append("\n");
        str.append("UploadFlag = ")
        .append(getUploadFlag())
        .append("\n");
        str.append("ReplyFlag = ")
        .append(getReplyFlag())
        .append("\n");
        str.append("CustomerStock = ")
        .append(getCustomerStock())
        .append("\n");
        str.append("ReceiveAmt = ")
        .append(getReceiveAmt())
        .append("\n");
        str.append("DeliverViewOrder = ")
        .append(getDeliverViewOrder())
        .append("\n");
        return(str.toString());
		
	}
}
