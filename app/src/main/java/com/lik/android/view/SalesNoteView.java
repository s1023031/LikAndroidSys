package com.lik.android.view;

import java.util.Date;

public class SalesNoteView {

    private long serialID; //key
    private String reportKey;
    private String userNo;
    private String note;
    private Date issueTime;
    private int CustomerID;
	private Integer deliverOrder; // SalesNote.DeliverOrder
    private boolean isUpload;
    
	public long getSerialID() {
		return serialID;
	}
	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}
	public String getReportKey() {
		return reportKey;
	}
	public void setReportKey(String reportKey) {
		this.reportKey = reportKey;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getIssueTime() {
		return issueTime;
	}
	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}
	public int getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(int customerID) {
		CustomerID = customerID;
	}
	public Integer getDeliverOrder() {
		return deliverOrder;
	}
	public void setDeliverOrder(Integer deliverOrder) {
		this.deliverOrder = deliverOrder;
	}
	public boolean isUpload() {
		return isUpload;
	}
	public void setUpload(boolean isUpload) {
		this.isUpload = isUpload;
	}

    
}
