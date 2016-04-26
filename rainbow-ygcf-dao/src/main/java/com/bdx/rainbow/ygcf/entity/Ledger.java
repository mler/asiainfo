package com.bdx.rainbow.ygcf.entity;

import java.sql.Timestamp;

public class Ledger {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_ledger.LEDGER_ID
     *
     * @mbggenerated
     */
    private Integer ledgerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_ledger.ENTERPRISE_ID
     *
     * @mbggenerated
     */
    private Integer enterpriseId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_ledger.LEDGER_TYPE
     *
     * @mbggenerated
     */
    private String ledgerType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_ledger.LEDGER_DATE
     *
     * @mbggenerated
     */
    private String ledgerDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_ledger.IS_UPLOAD
     *
     * @mbggenerated
     */
    private String isUpload;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_ledger.IS_DEL
     *
     * @mbggenerated
     */
    private String isDel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_ledger.CREATE_DATE
     *
     * @mbggenerated
     */
    private Timestamp createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_ledger.CREATE_USERID
     *
     * @mbggenerated
     */
    private Integer createUserid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_ledger.UPDATE_DATE
     *
     * @mbggenerated
     */
    private Timestamp updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_ledger.UPDATE_USERID
     *
     * @mbggenerated
     */
    private Integer updateUserid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_ledger.LEDGER_ID
     *
     * @return the value of t_ygcf_ledger.LEDGER_ID
     *
     * @mbggenerated
     */
    public Integer getLedgerId() {
        return ledgerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_ledger.LEDGER_ID
     *
     * @param ledgerId the value for t_ygcf_ledger.LEDGER_ID
     *
     * @mbggenerated
     */
    public void setLedgerId(Integer ledgerId) {
        this.ledgerId = ledgerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_ledger.ENTERPRISE_ID
     *
     * @return the value of t_ygcf_ledger.ENTERPRISE_ID
     *
     * @mbggenerated
     */
    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_ledger.ENTERPRISE_ID
     *
     * @param enterpriseId the value for t_ygcf_ledger.ENTERPRISE_ID
     *
     * @mbggenerated
     */
    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_ledger.LEDGER_TYPE
     *
     * @return the value of t_ygcf_ledger.LEDGER_TYPE
     *
     * @mbggenerated
     */
    public String getLedgerType() {
        return ledgerType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_ledger.LEDGER_TYPE
     *
     * @param ledgerType the value for t_ygcf_ledger.LEDGER_TYPE
     *
     * @mbggenerated
     */
    public void setLedgerType(String ledgerType) {
        this.ledgerType = ledgerType == null ? null : ledgerType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_ledger.LEDGER_DATE
     *
     * @return the value of t_ygcf_ledger.LEDGER_DATE
     *
     * @mbggenerated
     */
    public String getLedgerDate() {
        return ledgerDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_ledger.LEDGER_DATE
     *
     * @param ledgerDate the value for t_ygcf_ledger.LEDGER_DATE
     *
     * @mbggenerated
     */
    public void setLedgerDate(String ledgerDate) {
        this.ledgerDate = ledgerDate == null ? null : ledgerDate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_ledger.IS_UPLOAD
     *
     * @return the value of t_ygcf_ledger.IS_UPLOAD
     *
     * @mbggenerated
     */
    public String getIsUpload() {
        return isUpload;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_ledger.IS_UPLOAD
     *
     * @param isUpload the value for t_ygcf_ledger.IS_UPLOAD
     *
     * @mbggenerated
     */
    public void setIsUpload(String isUpload) {
        this.isUpload = isUpload == null ? null : isUpload.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_ledger.IS_DEL
     *
     * @return the value of t_ygcf_ledger.IS_DEL
     *
     * @mbggenerated
     */
    public String getIsDel() {
        return isDel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_ledger.IS_DEL
     *
     * @param isDel the value for t_ygcf_ledger.IS_DEL
     *
     * @mbggenerated
     */
    public void setIsDel(String isDel) {
        this.isDel = isDel == null ? null : isDel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_ledger.CREATE_DATE
     *
     * @return the value of t_ygcf_ledger.CREATE_DATE
     *
     * @mbggenerated
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_ledger.CREATE_DATE
     *
     * @param createDate the value for t_ygcf_ledger.CREATE_DATE
     *
     * @mbggenerated
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_ledger.CREATE_USERID
     *
     * @return the value of t_ygcf_ledger.CREATE_USERID
     *
     * @mbggenerated
     */
    public Integer getCreateUserid() {
        return createUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_ledger.CREATE_USERID
     *
     * @param createUserid the value for t_ygcf_ledger.CREATE_USERID
     *
     * @mbggenerated
     */
    public void setCreateUserid(Integer createUserid) {
        this.createUserid = createUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_ledger.UPDATE_DATE
     *
     * @return the value of t_ygcf_ledger.UPDATE_DATE
     *
     * @mbggenerated
     */
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_ledger.UPDATE_DATE
     *
     * @param updateDate the value for t_ygcf_ledger.UPDATE_DATE
     *
     * @mbggenerated
     */
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_ledger.UPDATE_USERID
     *
     * @return the value of t_ygcf_ledger.UPDATE_USERID
     *
     * @mbggenerated
     */
    public Integer getUpdateUserid() {
        return updateUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_ledger.UPDATE_USERID
     *
     * @param updateUserid the value for t_ygcf_ledger.UPDATE_USERID
     *
     * @mbggenerated
     */
    public void setUpdateUserid(Integer updateUserid) {
        this.updateUserid = updateUserid;
    }
}