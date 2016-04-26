package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;

/**
 * Created by core on 15/10/15.
 * 企业详情
 */
public class SK0013Response extends BDXBody {
    private String fileUrl;//用户头像
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
    /**
     * 原材料来源
     */
    private String materialSource;

    /**
     * 许可范围类型
     */
    private String permissionType;

    /**
     * 许可范围备注
     */
    private String permissionMark;

    /**
     * 有效期
     */
    private String validity;
    private  String  honesty;

    public String getHonesty() {
        return honesty;
    }

    public void setHonesty(String honesty) {
        this.honesty = honesty;
    }

    public String getMaterialSource() {
        return materialSource;
    }

    public void setMaterialSource(String materialSource) {
        this.materialSource = materialSource;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public String getPermissionMark() {
        return permissionMark;
    }

    public void setPermissionMark(String permissionMark) {
        this.permissionMark = permissionMark;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
