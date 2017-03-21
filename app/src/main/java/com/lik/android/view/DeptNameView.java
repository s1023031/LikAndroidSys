package com.lik.android.view;

import java.io.Serializable;

public class DeptNameView implements Serializable {
	
	private static final long serialVersionUID = 2663320248694700365L;
	
	private int companyID;
	private String companyNO;
	private String companyNM;
	private String dateFormat;
	private String uiFormat;
	private String isDiscount;
	private int sdisctInteger;
	private int sdisctDecimal;
	private int nsuprDecimal;
	private int nsamtDecimal;
	private int bsqtyDecimal;
	private int tkqtyDecimal;
	private int acDecimal;
	private String telNo;


	public String getCompanyNM() {
		return companyNM;
	}

	public void setCompanyNM(String companyNM) {
		this.companyNM = companyNM;
	}

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getUiFormat() {
		return uiFormat;
	}

	public void setUiFormat(String uiFormat) {
		this.uiFormat = uiFormat;
	}

	public String getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(String isDiscount) {
		this.isDiscount = isDiscount;
	}

	public int getSdisctInteger() {
		return sdisctInteger;
	}

	public void setSdisctInteger(int sdisctInteger) {
		this.sdisctInteger = sdisctInteger;
	}

	public int getSdisctDecimal() {
		return sdisctDecimal;
	}

	public void setSdisctDecimal(int sdisctDecimal) {
		this.sdisctDecimal = sdisctDecimal;
	}

	public int getNsuprDecimal() {
		return nsuprDecimal;
	}

	public void setNsuprDecimal(int nsuprDecimal) {
		this.nsuprDecimal = nsuprDecimal;
	}

	public int getNsamtDecimal() {
		return nsamtDecimal;
	}

	public String getCompanyNO() {
		return companyNO;
	}

	public void setCompanyNO(String companyNO) {
		this.companyNO = companyNO;
	}

	public void setNsamtDecimal(int nsamtDecimal) {
		this.nsamtDecimal = nsamtDecimal;
	}

	public int getBsqtyDecimal() {
		return bsqtyDecimal;
	}

	public void setBsqtyDecimal(int bsqtyDecimal) {
		this.bsqtyDecimal = bsqtyDecimal;
	}

	public int getTkqtyDecimal() {
		return tkqtyDecimal;
	}

	public void setTkqtyDecimal(int tkqtyDecimal) {
		this.tkqtyDecimal = tkqtyDecimal;
	}

	public int getAcDecimal() {
		return acDecimal;
	}

	public void setAcDecimal(int acDecimal) {
		this.acDecimal = acDecimal;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	
}
