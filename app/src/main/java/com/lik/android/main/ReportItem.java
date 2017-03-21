package com.lik.android.main;

public class ReportItem {
	String id;
	String title;
	String dateTime;
	Boolean isSend;
	String ReportKey;
	String CompanyID;
	String CustomerID;
	String DeliverOrder;
	
	public ReportItem(String id,String title,String dateTime,Boolean isSend,String ReportKey,String CompanyID,String CustomerID,String DeliverOrder){
		this.id=id;
		this.title=title;
		this.dateTime=dateTime;
		this.isSend=isSend;
		this.ReportKey=ReportKey;
		this.CompanyID=CompanyID;
		this.CustomerID=CustomerID;
		this.DeliverOrder=DeliverOrder;
	}
}
