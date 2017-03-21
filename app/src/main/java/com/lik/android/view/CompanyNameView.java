package com.lik.android.view;

import java.io.Serializable;

public class CompanyNameView implements Serializable{

	private static final long serialVersionUID = 3444697724739939692L;
	
	private String companyNo;
	private String systemNo;
	private String pdaID;
	private String versionInfo;
	private String stockInfo;
	private int sdisctInteger;
	private int sdisctDecimal;
	private int nsuprDecimal;
	private int nsamtDecimal;
	private int bsqtyDecimal;
	private int tkqtyDecimal;
	private int acDecimal;
	private String telNo;
	private String companyName;
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyNO() {
		return companyNo;
	}

	public void setCompanyNO(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getSystemNo() {
		return systemNo;
	}

	public void setSystemNo(String systemNo) {
		this.systemNo = systemNo;
	}

	public String getPdaID() {
		return pdaID;
	}

	public void setPdaID(String pdaID) {
		this.pdaID = pdaID;
	}

	public String getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(String versionInfo) {
		this.versionInfo = versionInfo;
	}

	public String getStockInfo() {
		return stockInfo;
	}

	public void setStockInfo(String stockInfo) {
		this.stockInfo = stockInfo;
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
