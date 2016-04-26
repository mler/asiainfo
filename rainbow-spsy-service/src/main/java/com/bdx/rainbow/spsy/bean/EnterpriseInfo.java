package com.bdx.rainbow.spsy.bean;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;

public class EnterpriseInfo extends DubboEnterpriseInfo{
	private static final long serialVersionUID = 1L;

	//生产许可证
	private String productionLicense;
     
	//流通许可证
    private String circulationLicense;
     
    //销售许可证
    private String saleLicense;
     
    //经营许可证
    private String manageLincense;

	public String getProductionLicense() {
		return productionLicense;
	}

	public void setProductionLicense(String productionLicense) {
		this.productionLicense = productionLicense;
	}

	public String getCirculationLicense() {
		return circulationLicense;
	}

	public void setCirculationLicense(String circulationLicense) {
		this.circulationLicense = circulationLicense;
	}

	public String getSaleLicense() {
		return saleLicense;
	}

	public void setSaleLicense(String saleLicense) {
		this.saleLicense = saleLicense;
	}

	public String getManageLincense() {
		return manageLincense;
	}

	public void setManageLincense(String manageLincense) {
		this.manageLincense = manageLincense;
	}    
}
