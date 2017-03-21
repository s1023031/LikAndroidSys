package com.lik.android.view;

import java.io.Serializable;
import java.util.Date;

/**
 * 未上傳訂單資料ListView所使用的data view
 * @author charles
 *
 */
public class QueryNotUploadView implements Serializable {
	
	private static final long serialVersionUID = 5493360004231286762L;
	
	private long serialID; // Orders.SerialID
	private String shortName; // Customers.ShortName
	private String fullName; // Customers.FullName
	private String tel1; // Customers.Tel1
	private Integer payNextMonth; // Orders.PayNextMonth
	private Date orderDT; // Orders.OrderDT
	private Date sellDT; // Orders.SellDT
	private Date lastDT; // Orders.LastDT
	private String customerNO; // Customers.CustomerNO
	private int customerID; // Customers.CustomerID
	private String note1; // Orders.Note1
	private String note2; // Orders.Note2
	private String payType; // Customers.PayType
	private String priceGrade; // Customers.PriceGrade
	private boolean isSelected; // 用來標記此item是否selected
	private String uploadFlag; // Orders.UploadFlag
	private String replyFlag; // Orders.ReplyFlag
	private int orderID; // Orders.OrderID
	private int settleDay; // Customers.SettleDay
	private int promoteGroupID; // Customers.PromoteGroupID
	private String address; // Customers.Address
	private Integer customerStock; // Orders.CustomerStock
	private double revCash_Disrate; // Customers.RevCash_Disrate
	private String salesName; // Customers.SalesName
	private String salesNO; // Customers.SalesNO
	private String isLimit; // Customers.IsLimit
    private String deliveryWay; // Customers.deliveryWay
    private String requestDay; // Customers.requestDay
    private String checkDay; // Customers.checkDay
    private String visitLine; // Customers.visitLine
    private String noReturn; // Customers.NoReturn
	private int custType; // Customers.CustType
	private String beVisit; // Customers.BeVisit
	private Integer deliverOrder; // Customers.DeliverOrder
	private String dirtyPay; // Customers.DirtyPay
	private Integer billDays; // Customers.BillDays
	private Double backRate; // Customers.BackRate
	
	public long getSerialID() {
		return serialID;
	}
	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public Integer getPayNextMonth() {
		return payNextMonth;
	}
	public void setPayNextMonth(Integer payNextMonth) {
		this.payNextMonth = payNextMonth;
	}
	public Date getSellDT() {
		return sellDT;
	}
	public void setSellDT(Date sellDT) {
		this.sellDT = sellDT;
	}
	public Date getLastDT() {
		return lastDT;
	}
	public void setLastDT(Date lastDT) {
		this.lastDT = lastDT;
	}
	public String getCustomerNO() {
		return customerNO;
	}
	public void setCustomerNO(String customerNO) {
		this.customerNO = customerNO;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
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
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPriceGrade() {
		return priceGrade;
	}
	public void setPriceGrade(String priceGrade) {
		this.priceGrade = priceGrade;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
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
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getSettleDay() {
		return settleDay;
	}
	public void setSettleDay(int settleDay) {
		this.settleDay = settleDay;
	}
	public int getPromoteGroupID() {
		return promoteGroupID;
	}
	public void setPromoteGroupID(int promoteGroupID) {
		this.promoteGroupID = promoteGroupID;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getCustomerStock() {
		return customerStock;
	}
	public void setCustomerStock(Integer customerStock) {
		this.customerStock = customerStock;
	}
	public double getRevCash_Disrate() {
		return revCash_Disrate;
	}
	public void setRevCash_Disrate(double revCash_Disrate) {
		this.revCash_Disrate = revCash_Disrate;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public String getSalesNO() {
		return salesNO;
	}
	public void setSalesNO(String salesNO) {
		this.salesNO = salesNO;
	}
	public String getIsLimit() {
		return isLimit;
	}
	public void setIsLimit(String isLimit) {
		this.isLimit = isLimit;
	}
	public Date getOrderDT() {
		return orderDT;
	}
	public void setOrderDT(Date orderDT) {
		this.orderDT = orderDT;
	}
	public String getDeliveryWay() {
		return deliveryWay;
	}
	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay;
	}
	public String getRequestDay() {
		return requestDay;
	}
	public void setRequestDay(String requestDay) {
		this.requestDay = requestDay;
	}
	public String getCheckDay() {
		return checkDay;
	}
	public void setCheckDay(String checkDay) {
		this.checkDay = checkDay;
	}
	public String getVisitLine() {
		return visitLine;
	}
	public void setVisitLine(String visitLine) {
		this.visitLine = visitLine;
	}
	public String getNoReturn() {
		return noReturn;
	}
	public void setNoReturn(String noReturn) {
		this.noReturn = noReturn;
	}
	public int getCustType() {
		return custType;
	}
	public void setCustType(int custType) {
		this.custType = custType;
	}
	public String getBeVisit() {
		return beVisit;
	}
	public void setBeVisit(String beVisit) {
		this.beVisit = beVisit;
	}
	public Integer getDeliverOrder() {
		return deliverOrder;
	}
	public void setDeliverOrder(Integer deliverOrder) {
		this.deliverOrder = deliverOrder;
	}
	public String getDirtyPay() {
		return dirtyPay;
	}
	public void setDirtyPay(String dirtyPay) {
		this.dirtyPay = dirtyPay;
	}
	public Integer getBillDays() {
		return billDays;
	}
	public void setBillDays(Integer billDays) {
		this.billDays = billDays;
	}
	public Double getBackRate() {
		return backRate;
	}
	public void setBackRate(Double backRate) {
		this.backRate = backRate;
	}

}
