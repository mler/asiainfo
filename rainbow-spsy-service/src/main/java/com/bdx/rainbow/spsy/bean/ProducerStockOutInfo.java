package com.bdx.rainbow.spsy.bean;

import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockOut;

public class ProducerStockOutInfo extends TOriginProducerStockOut {
    //入库操作编码集合  
	private String oprationIds;
	
	private String agencyManagePerson;
	
	private String agencyLinkPhone;
	
	public String getOprationIds() {
		return oprationIds;
	}

	public void setOprationIds(String oprationIds) {
		this.oprationIds = oprationIds;
	}

	public String getAgencyManagePerson() {
		return agencyManagePerson;
	}

	public void setAgencyManagePerson(String agencyManagePerson) {
		this.agencyManagePerson = agencyManagePerson;
	}

	public String getAgencyLinkPhone() {
		return agencyLinkPhone;
	}

	public void setAgencyLinkPhone(String agencyLinkPhone) {
		this.agencyLinkPhone = agencyLinkPhone;
	}
	
}
