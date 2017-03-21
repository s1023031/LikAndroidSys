package com.lik.android.main;

public class MessageItem {
	long mesgID;
	String title;
	String message;
	Boolean isRead;
	
	public MessageItem(long mesgID,String title,String message,Boolean isRead){
		this.mesgID=mesgID;
		this.title=title;
		this.message=message;
		this.isRead=isRead;
		
	}
}
