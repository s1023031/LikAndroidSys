package com.lik.android.view;

import java.util.Date;

/**
 * 接單ListView所使用的data view
 * @author charles
 *
 */
public class SubAddProductsView {
	
    private long serialID; //key
	private int companyID;
	private int itemID;
	private String itemNO;
	private String itemNM;
	private String dimension;
	private String unit;
	private String unit1;
	private String unit2;
	private String unit3;
	private String unit4;
	private String unit5;
	private double piece;
	private double ratio1;
	private double ratio2;
	private double ratio3;
	private String classify;
	private String suplNO;
	private String barCode;
	private double salePrice;
	private double suggestPrice;
	private int kind;
	private String newProduct;
	private String noReturn;
	private double sellDays;
	private String realStock;
	private String remark;
	private String versionNo;
	private String subClassify;
	private double sellMultiple;
	private double stockQty;
	private double cubicMeasure;
    private double prdtUnitWeight;
    private String weightUnit;
	private String sellPayType; // SellDetail.SellPayType
	private String priceUnit; // SellDetail.PriceUnit
	private double uprice; // SellDetail.UPrice
	private double uprice1; // SellDetail.UPrice1
	private Date sellDate; // SellDetail.SellDate
	private double discRate; // // SellDetail.DiscRate
	private String promotionUnit; // Promotion.Unit
	private Double promotionPrice; // Promotion.StdPrice
	private Double promotionLowestSPrice; // Promotion.LowestSPrice
	private boolean isActivated; 
	
	private String stockFTN; // FTN 庫存量
	private String packAmount; // stockQty庫存量分單位
	
	public long getSerialID() {
		return serialID;
	}
	public void setSerialID(long serialID) {
		this.serialID = serialID;
	}
	public int getCompanyID() {
		return companyID;
	}
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public String getItemNO() {
		return itemNO;
	}
	public void setItemNO(String itemNO) {
		this.itemNO = itemNO;
	}
	public String getItemNM() {
		return itemNM;
	}
	public void setItemNM(String itemNM) {
		this.itemNM = itemNM;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnit1() {
		return unit1;
	}
	public void setUnit1(String unit1) {
		this.unit1 = unit1;
	}
	public String getUnit2() {
		return unit2;
	}
	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}
	public String getUnit3() {
		return unit3;
	}
	public void setUnit3(String unit3) {
		this.unit3 = unit3;
	}
	public String getUnit4() {
		return unit4;
	}
	public void setUnit4(String unit4) {
		this.unit4 = unit4;
	}
	public String getUnit5() {
		return unit5;
	}
	public void setUnit5(String unit5) {
		this.unit5 = unit5;
	}
	public double getPiece() {
		return piece;
	}
	public void setPiece(double piece) {
		this.piece = piece;
	}
	public double getRatio1() {
		return ratio1;
	}
	public void setRatio1(double ratio1) {
		this.ratio1 = ratio1;
	}
	public double getRatio2() {
		return ratio2;
	}
	public void setRatio2(double ratio2) {
		this.ratio2 = ratio2;
	}
	public double getRatio3() {
		return ratio3;
	}
	public void setRatio3(double ratio3) {
		this.ratio3 = ratio3;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getSuplNO() {
		return suplNO;
	}
	public void setSuplNO(String suplNO) {
		this.suplNO = suplNO;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	public double getSuggestPrice() {
		return suggestPrice;
	}
	public void setSuggestPrice(double suggestPrice) {
		this.suggestPrice = suggestPrice;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public String getNewProduct() {
		return newProduct;
	}
	public void setNewProduct(String newProduct) {
		this.newProduct = newProduct;
	}
	public String getNoReturn() {
		return noReturn;
	}
	public void setNoReturn(String noReturn) {
		this.noReturn = noReturn;
	}
	public double getSellDays() {
		return sellDays;
	}
	public void setSellDays(double sellDays) {
		this.sellDays = sellDays;
	}
	public String getRealStock() {
		return realStock;
	}
	public void setRealStock(String realStock) {
		this.realStock = realStock;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public double getSellMultiple() {
		return sellMultiple;
	}
	public void setSellMultiple(double sellMultiple) {
		this.sellMultiple = sellMultiple;
	}
	public double getStockQty() {
		return stockQty;
	}
	public void setStockQty(double stockQty) {
		this.stockQty = stockQty;
	}
	public double getCubicMeasure() {
		return cubicMeasure;
	}
	public void setCubicMeasure(double cubicMeasure) {
		this.cubicMeasure = cubicMeasure;
	}
	public double getPrdtUnitWeight() {
		return prdtUnitWeight;
	}
	public void setPrdtUnitWeight(double prdtUnitWeight) {
		this.prdtUnitWeight = prdtUnitWeight;
	}
	public String getWeightUnit() {
		return weightUnit;
	}
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	public String getSellPayType() {
		return sellPayType;
	}
	public void setSellPayType(String sellPayType) {
		this.sellPayType = sellPayType;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	public double getUprice() {
		return uprice;
	}
	public void setUprice(double uprice) {
		this.uprice = uprice;
	}
	public double getUprice1() {
		return uprice1;
	}
	public void setUprice1(double uprice1) {
		this.uprice1 = uprice1;
	}
	public Date getSellDate() {
		return sellDate;
	}
	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}
	public double getDiscRate() {
		return discRate;
	}
	public void setDiscRate(double discRate) {
		this.discRate = discRate;
	}
	public boolean isActivated() {
		return isActivated;
	}
	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}
	public String getStockFTN() {
		return stockFTN;
	}
	public void setStockFTN(String stockFTN) {
		this.stockFTN = stockFTN;
	}
	public String getSubClassify() {
		return subClassify;
	}
	public void setSubClassify(String subClassify) {
		this.subClassify = subClassify;
	}
	public String getPromotionUnit() {
		return promotionUnit;
	}
	public void setPromotionUnit(String promotionUnit) {
		this.promotionUnit = promotionUnit;
	}
	public Double getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(Double promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	public Double getPromotionLowestSPrice() {
		return promotionLowestSPrice;
	}
	public void setPromotionLowestSPrice(Double promotionLowestSPrice) {
		this.promotionLowestSPrice = promotionLowestSPrice;
	}
	public String getPackAmount() {
		return packAmount;
	}
	public void setPackAmount(String packAmount) {
		this.packAmount = packAmount;
	}
	
	
}
