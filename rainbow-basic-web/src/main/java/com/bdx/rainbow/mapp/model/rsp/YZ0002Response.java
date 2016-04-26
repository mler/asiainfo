package com.bdx.rainbow.mapp.model.rsp;

import com.bdx.rainbow.mapp.model.BDXBody;

import java.io.Serializable;

public class YZ0002Response extends BDXBody implements Serializable {

    private String jgmCode;

    private String licenseNumber;

    private String lastTime;

    private String flow;

    private String pkgSpec;

    private String saleTime;

    private String title;

    private String prepnType;

    private String prepnUnit;

    private String pkgUnit;

    private String issueExpiry;

    private String firstQuery;

    private String lastEnt;

    private String productionBatch;
    
    private String productionDate;

    private String specifications;

    private String category;

    private String manufacturer;

    private String expiryDate;
    
    private String status;
    
    private String saleEnt;
    
    private String thumbUrl;
    
    private String barcode;
    
    private boolean ifExpiry;

	public String getJgmCode() {
		return jgmCode;
	}

	public void setJgmCode(String jgmCode) {
		this.jgmCode = jgmCode;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	public String getPkgSpec() {
		return pkgSpec;
	}

	public void setPkgSpec(String pkgSpec) {
		this.pkgSpec = pkgSpec;
	}

	public String getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrepnType() {
		return prepnType;
	}

	public void setPrepnType(String prepnType) {
		this.prepnType = prepnType;
	}

	public String getPrepnUnit() {
		return prepnUnit;
	}

	public void setPrepnUnit(String prepnUnit) {
		this.prepnUnit = prepnUnit;
	}

	public String getPkgUnit() {
		return pkgUnit;
	}

	public void setPkgUnit(String pkgUnit) {
		this.pkgUnit = pkgUnit;
	}

	public String getIssueExpiry() {
		return issueExpiry;
	}

	public void setIssueExpiry(String issueExpiry) {
		this.issueExpiry = issueExpiry;
	}

	public String getFirstQuery() {
		return firstQuery;
	}

	public void setFirstQuery(String firstQuery) {
		this.firstQuery = firstQuery;
	}

	public String getLastEnt() {
		return lastEnt;
	}

	public void setLastEnt(String lastEnt) {
		this.lastEnt = lastEnt;
	}

	public String getProductionBatch() {
		return productionBatch;
	}

	public void setProductionBatch(String productionBatch) {
		this.productionBatch = productionBatch;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSaleEnt() {
		return saleEnt;
	}

	public void setSaleEnt(String saleEnt) {
		this.saleEnt = saleEnt;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public boolean isIfExpiry() {
		return ifExpiry;
	}

	public void setIfExpiry(boolean ifExpiry) {
		this.ifExpiry = ifExpiry;
	}

	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	
}
