package com.bdx.rainbow.entity.basic.mysql;

import java.sql.Timestamp;

public class SelfEnterpriseLicense {
	
	private Integer enterpriseId;

    private String enterpriseName;

    private String legalPerson;

    private String legalPersonCert;

    private String legalPersonPhone;

    private String organizationCode;

    private String organizationType;

    private String regiAddress;

    private String businessLicence;

    private String regiCapital;

    private String businessScope;
    
    private Integer licenseId;

    private String licenseCode;

    private String licenseType;

    private String licenseName;

    private String registerAddress;

    private String licenseAddress;

    private String licenseScope;

    private Timestamp validDate;

    private Timestamp invalidDate;

    private String licenseAuthority;

    private Timestamp licenseTime;

    private String validPeriod;

    private Timestamp approveExtendDate;

    private Timestamp extendExpiredate;

    private String versionFrom;

    private String status;
    
    /**
     * 查询条件 生效时间起始
     */
    private Timestamp validDateStart;
    
    /**
     * 查询条件 生效时间结束
     */
    private Timestamp validDateEnd;
    

	public Timestamp getValidDateStart() {
		return validDateStart;
	}

	public void setValidDateStart(Timestamp validDateStart) {
		this.validDateStart = validDateStart;
	}

	public Timestamp getValidDateEnd() {
		return validDateEnd;
	}

	public void setValidDateEnd(Timestamp validDateEnd) {
		this.validDateEnd = validDateEnd;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getLegalPersonCert() {
		return legalPersonCert;
	}

	public void setLegalPersonCert(String legalPersonCert) {
		this.legalPersonCert = legalPersonCert;
	}

	public String getLegalPersonPhone() {
		return legalPersonPhone;
	}

	public void setLegalPersonPhone(String legalPersonPhone) {
		this.legalPersonPhone = legalPersonPhone;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

	public String getRegiAddress() {
		return regiAddress;
	}

	public void setRegiAddress(String regiAddress) {
		this.regiAddress = regiAddress;
	}

	public String getBusinessLicence() {
		return businessLicence;
	}

	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}

	public String getRegiCapital() {
		return regiCapital;
	}

	public void setRegiCapital(String regiCapital) {
		this.regiCapital = regiCapital;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public Integer getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(Integer licenseId) {
		this.licenseId = licenseId;
	}

	public String getLicenseCode() {
		return licenseCode;
	}

	public void setLicenseCode(String licenseCode) {
		this.licenseCode = licenseCode;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	public String getLicenseAddress() {
		return licenseAddress;
	}

	public void setLicenseAddress(String licenseAddress) {
		this.licenseAddress = licenseAddress;
	}

	public String getLicenseScope() {
		return licenseScope;
	}

	public void setLicenseScope(String licenseScope) {
		this.licenseScope = licenseScope;
	}

	public Timestamp getValidDate() {
		return validDate;
	}

	public void setValidDate(Timestamp validDate) {
		this.validDate = validDate;
	}

	public Timestamp getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Timestamp invalidDate) {
		this.invalidDate = invalidDate;
	}

	public String getLicenseAuthority() {
		return licenseAuthority;
	}

	public void setLicenseAuthority(String licenseAuthority) {
		this.licenseAuthority = licenseAuthority;
	}

	public Timestamp getLicenseTime() {
		return licenseTime;
	}

	public void setLicenseTime(Timestamp licenseTime) {
		this.licenseTime = licenseTime;
	}

	public String getValidPeriod() {
		return validPeriod;
	}

	public void setValidPeriod(String validPeriod) {
		this.validPeriod = validPeriod;
	}

	public Timestamp getApproveExtendDate() {
		return approveExtendDate;
	}

	public void setApproveExtendDate(Timestamp approveExtendDate) {
		this.approveExtendDate = approveExtendDate;
	}

	public Timestamp getExtendExpiredate() {
		return extendExpiredate;
	}

	public void setExtendExpiredate(Timestamp extendExpiredate) {
		this.extendExpiredate = extendExpiredate;
	}

	public String getVersionFrom() {
		return versionFrom;
	}

	public void setVersionFrom(String versionFrom) {
		this.versionFrom = versionFrom;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}
