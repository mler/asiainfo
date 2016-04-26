package com.bdx.rainbow.spsy.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginPurchaseInfo;

public class MaterialPurchaseInfo extends TOriginPurchaseInfo implements Serializable{
	private static final long serialVersionUID = 1L;
    
	//供应商名称
	private String supplierName;
	
	//负责人
	private String managePerson;
      
	//联系电话
    private String linkPhone;
    
    //采购开始时间
    private Timestamp startTime;
    
    //采购结束时间
    private Timestamp endTime;
        
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getManagePerson() {
		return managePerson;
	}

	public void setManagePerson(String managePerson) {
		this.managePerson = managePerson;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
}
