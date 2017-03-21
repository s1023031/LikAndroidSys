package com.lik.android.view;

import java.util.List;

import com.lik.android.om.TemporaryCredit;

/**
 * 未上傳訂單資料ListView所使用的data view
 * @author charles
 *
 */
public class AddVisitCustomerView {
	
	private long serialID; // Customers.SerialID
	private String shortName; // Customers.ShortName
	private String fullName; // Customers.FullName
	private String tel1; // Customers.Tel1
	private String actTel; // Customers.ActTel
	private String customerNO; // Customers.CustomerNO
	private int customerID; // Customers.CustomerID
	private int salesID; // Customers.SalesID
	private String payType; // Customers.PayType
	private String isLimit; // Customers.IsLimit
    private String deliveryWay; // Customers.deliveryWay
    private String requestDay; // Customers.requestDay
	private int settleDay; // Customers.settleDay
    private String checkDay; // Customers.checkDay
    private String visitLine; // Customers.visitLine
    private List<TemporaryCredit> temporaryCredit; // 陳列費
    private double amt; // 陳列費總金額
	private boolean isActivated;
	private Integer deliverOrder; // Customers.DeliverOrder for SND
    private String address; // Customers.visitLine for SND
    private double sellAmount; // Customers.SellAmount for SND
	private String dirtyPay; // Customers.DirtyPay
	private Integer billDays; // Customers.BillDays
	private String isDirty; // Customers.IsDirty HAO 104.04.27
	
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
	public String getActTel() {
		return actTel;
	}
	public void setActTel(String actTel) {
		this.actTel = actTel;
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
	public int getSalesID() {
		return salesID;
	}
	public void setSalesID(int salesID) {
		this.salesID = salesID;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getIsLimit() {
		return isLimit;
	}
	public void setIsLimit(String isLimit) {
		this.isLimit = isLimit;
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
	public int getSettleDay() {
		return settleDay;
	}
	public void setSettleDay(int settleDay) {
		this.settleDay = settleDay;
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
	public List<TemporaryCredit> getTemporaryCredit() {
		return temporaryCredit;
	}
	public void setTemporaryCredit(List<TemporaryCredit> temporaryCredit) {
		this.temporaryCredit = temporaryCredit;
	}
	public double getAmt() {
		return amt;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
	public boolean isActivated() {
		return isActivated;
	}
	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}
	public Integer getDeliverOrder() {
		return deliverOrder;
	}
	public void setDeliverOrder(Integer deliverOrder) {
		this.deliverOrder = deliverOrder;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getSellAmount() {
		return sellAmount;
	}
	public void setSellAmount(double sellAmount) {
		this.sellAmount = sellAmount;
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
	//HAO 104.04.27
	public String getIsDirty() {
		return isDirty;
	}
	//HAO 104.04.27
	public void setIsDirty(String isDirty) {
		this.isDirty = isDirty;
	}
}
