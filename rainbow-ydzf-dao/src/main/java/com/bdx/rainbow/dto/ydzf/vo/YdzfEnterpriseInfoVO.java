package com.bdx.rainbow.dto.ydzf.vo;

public class YdzfEnterpriseInfoVO {
	private int manageEnterpriseId;
	private String manageEnterpriseName;
	private String legalPerson;
	private String legalPersonCert;
	private String legalPersonPhone;
	private String regiAddress;
    private String businessType;

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getManageEnterpriseName() {
		return manageEnterpriseName;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public String getLegalPersonCert() {
		return legalPersonCert;
	}
	public String getLegalPersonPhone() {
		return legalPersonPhone;
	}
	public void setManageEnterpriseName(String manageEnterpriseName) {
		this.manageEnterpriseName = manageEnterpriseName;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public void setLegalPersonCert(String legalPersonCert) {
		this.legalPersonCert = legalPersonCert;
	}
	public void setLegalPersonPhone(String legalPersonPhone) {
		this.legalPersonPhone = legalPersonPhone;
	}

	public String getRegiAddress() {
		return regiAddress;
	}
	public void setRegiAddress(String regiAddress) {
		this.regiAddress = regiAddress;
	}

	public int getManageEnterpriseId() {
		return manageEnterpriseId;
	}

	public void setManageEnterpriseId(int manageEnterpriseId) {
		this.manageEnterpriseId = manageEnterpriseId;
	}

}
