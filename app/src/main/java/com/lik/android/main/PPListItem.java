package com.lik.android.main;

public class PPListItem {
	String topic;
	String lastPictDate;
	String detail;
	String kind;
	public PPListItem(String topic,String lastPictDate,String kind){
		this.topic=topic;
		this.lastPictDate=lastPictDate;
		this.kind=kind;
		
	}
	
	public void setDetail(String detail){
		this.detail=detail;
	}
}
