package com.lik.android.view;

import java.util.Date;

public class InstantMessagesView {

    private long serialID; //key
    private String userNo;
    private String content;
    private Date publishTime;
    private Date receiveTime;
    private String owner;
    private boolean isRead;
	public long getSerialID() {
		return serialID;
	}
	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

    
}
