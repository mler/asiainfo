package com.bdx.rainbow.spsy.bean;

import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginGoodsStock;

public class GoodStockInfo extends TOriginGoodsStock{
	private static final long serialVersionUID = 1L;

	private String buyAgencyName;
    
    private String agencyManagePerson;
    
    private String agencyLinkPhone;
	
    private String oprationIds;
          
	public String getBuyAgencyName() {
		return buyAgencyName;
	}

	public void setBuyAgencyName(String buyAgencyName) {
		this.buyAgencyName = buyAgencyName;
	}

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
