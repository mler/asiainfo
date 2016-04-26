package com.bdx.rainbow.mapp.model.req;


import com.bdx.rainbow.mapp.model.BDXBody;

/**
 * Created by core on 15/10/15.
 * 企业更新
 */
public class SK0014Request extends BDXBody {
    private byte[] fileUrl;//用户头像
    private String companyName;
    private String licenseNumber;
    private String economicNature; //经济性质
    private String fixedAssets;//固定资产(万元)
    private String legalRepresentative;//法人
    //法人手机
    private String legalRepresentativePhone;
    private String businessLicenseNumber;//营业执照号
    //负责人
    private String responsiblePersons;
    //负责人手机
    private String contactPhone;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getEconomicNature() {
        return economicNature;
    }

    public void setEconomicNature(String economicNature) {
        this.economicNature = economicNature;
    }

    public String getFixedAssets() {
        return fixedAssets;
    }

    public void setFixedAssets(String fixedAssets) {
        this.fixedAssets = fixedAssets;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public String getLegalRepresentativePhone() {
        return legalRepresentativePhone;
    }

    public void setLegalRepresentativePhone(String legalRepresentativePhone) {
        this.legalRepresentativePhone = legalRepresentativePhone;
    }

    public String getBusinessLicenseNumber() {
        return businessLicenseNumber;
    }

    public void setBusinessLicenseNumber(String businessLicenseNumber) {
        this.businessLicenseNumber = businessLicenseNumber;
    }

    public String getResponsiblePersons() {
        return responsiblePersons;
    }

    public void setResponsiblePersons(String responsiblePersons) {
        this.responsiblePersons = responsiblePersons;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public byte[] getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(byte[] fileUrl) {
        this.fileUrl = fileUrl;
    }
}
