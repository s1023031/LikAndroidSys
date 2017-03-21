package com.lik.android.main;

import java.io.Serializable;

public class PpItemObject  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8908983116341250428L;
	String title;
	String no; 
	String shopName;
	String OrderID;
	String customerID;
	String takePhotoID;
	String projectNO;
	String projectID;
	String salesID;
	String yearMonth;
	String remark;
	String type;
	
	public PpItemObject(String title,String no,String shopName,String OrderID,String customerID,String takePhotoID, String projectNO,String salesID ,String yearMonth,String remark){
		this.title=title;
		this.no=no;
		this.shopName=shopName;
		this.OrderID=OrderID;
		this.customerID=customerID;
		this.takePhotoID=takePhotoID;
		this.projectNO = projectNO;
		this.salesID = salesID;
		this.yearMonth = yearMonth;
		this.remark = remark;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
