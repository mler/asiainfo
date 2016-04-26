package com.bdx.rainbow.view;

import com.bdx.rainbow.common.util.DateUtil;
import jxl.write.DateTime;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * 台账类型前段展示
 * Created by fusj on 16/2/1.
 */
public class LedgerView {

    private Integer ledgerId;
    /**
     * 企业ID
     */
    private Integer enterpriseId;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 法人代表
     */
    private String legalPerson;

    /**
     * 所属食药监局
     */
    private String dept;

    /**
     * 台账日期
     */
    private String ledgerDate;

    /**
     * 上传日期
     */
    private String createDate;

    /**
     * 上传用户ID
     */
    private Integer createUserid;

    /**
     * 上传用户姓名
     */
    private String createUserName;

    public Integer getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(Integer ledgerId) {
        this.ledgerId = ledgerId;
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

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getLedgerDate() {
        return ledgerDate;
    }

    public void setLedgerDate(String ledgerDate) {
        this.ledgerDate = ledgerDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = DateUtil.parse(createDate.getTime(), DateUtil.DATE_FORMAT7);
    }

    public Integer getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(Integer createUserid) {
        this.createUserid = createUserid;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
