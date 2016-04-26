package com.bdx.rainbow.entity.ydzf;

import java.sql.Timestamp;

public class TYdzfMonitorPlanRel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ydzf_monitor_plan_rel.MONITOR_PLAN_REL_ID
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    private Integer monitorPlanRelId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ydzf_monitor_plan_rel.MONITOR_PLAN_ID
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    private Integer monitorPlanId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ydzf_monitor_plan_rel.MANAGE_ENTERPRISE_ID
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    private Integer manageEnterpriseId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ydzf_monitor_plan_rel.MANAGE_ITEM_STATUS
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    private String manageItemStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ydzf_monitor_plan_rel.REMARK
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ydzf_monitor_plan_rel.CREATE_DATE
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    private Timestamp createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ydzf_monitor_plan_rel.CREATE_USERID
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    private Integer createUserid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ydzf_monitor_plan_rel.UPDATE_DATE
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    private Timestamp updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ydzf_monitor_plan_rel.UPDATE_USERID
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    private Integer updateUserid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ydzf_monitor_plan_rel.MONITOR_PLAN_REL_ID
     *
     * @return the value of t_ydzf_monitor_plan_rel.MONITOR_PLAN_REL_ID
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public Integer getMonitorPlanRelId() {
        return monitorPlanRelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ydzf_monitor_plan_rel.MONITOR_PLAN_REL_ID
     *
     * @param monitorPlanRelId the value for t_ydzf_monitor_plan_rel.MONITOR_PLAN_REL_ID
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public void setMonitorPlanRelId(Integer monitorPlanRelId) {
        this.monitorPlanRelId = monitorPlanRelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ydzf_monitor_plan_rel.MONITOR_PLAN_ID
     *
     * @return the value of t_ydzf_monitor_plan_rel.MONITOR_PLAN_ID
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public Integer getMonitorPlanId() {
        return monitorPlanId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ydzf_monitor_plan_rel.MONITOR_PLAN_ID
     *
     * @param monitorPlanId the value for t_ydzf_monitor_plan_rel.MONITOR_PLAN_ID
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public void setMonitorPlanId(Integer monitorPlanId) {
        this.monitorPlanId = monitorPlanId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ydzf_monitor_plan_rel.MANAGE_ENTERPRISE_ID
     *
     * @return the value of t_ydzf_monitor_plan_rel.MANAGE_ENTERPRISE_ID
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public Integer getManageEnterpriseId() {
        return manageEnterpriseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ydzf_monitor_plan_rel.MANAGE_ENTERPRISE_ID
     *
     * @param manageEnterpriseId the value for t_ydzf_monitor_plan_rel.MANAGE_ENTERPRISE_ID
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public void setManageEnterpriseId(Integer manageEnterpriseId) {
        this.manageEnterpriseId = manageEnterpriseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ydzf_monitor_plan_rel.MANAGE_ITEM_STATUS
     *
     * @return the value of t_ydzf_monitor_plan_rel.MANAGE_ITEM_STATUS
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public String getManageItemStatus() {
        return manageItemStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ydzf_monitor_plan_rel.MANAGE_ITEM_STATUS
     *
     * @param manageItemStatus the value for t_ydzf_monitor_plan_rel.MANAGE_ITEM_STATUS
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public void setManageItemStatus(String manageItemStatus) {
        this.manageItemStatus = manageItemStatus == null ? null : manageItemStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ydzf_monitor_plan_rel.REMARK
     *
     * @return the value of t_ydzf_monitor_plan_rel.REMARK
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ydzf_monitor_plan_rel.REMARK
     *
     * @param remark the value for t_ydzf_monitor_plan_rel.REMARK
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ydzf_monitor_plan_rel.CREATE_DATE
     *
     * @return the value of t_ydzf_monitor_plan_rel.CREATE_DATE
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ydzf_monitor_plan_rel.CREATE_DATE
     *
     * @param createDate the value for t_ydzf_monitor_plan_rel.CREATE_DATE
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ydzf_monitor_plan_rel.CREATE_USERID
     *
     * @return the value of t_ydzf_monitor_plan_rel.CREATE_USERID
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public Integer getCreateUserid() {
        return createUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ydzf_monitor_plan_rel.CREATE_USERID
     *
     * @param createUserid the value for t_ydzf_monitor_plan_rel.CREATE_USERID
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public void setCreateUserid(Integer createUserid) {
        this.createUserid = createUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ydzf_monitor_plan_rel.UPDATE_DATE
     *
     * @return the value of t_ydzf_monitor_plan_rel.UPDATE_DATE
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ydzf_monitor_plan_rel.UPDATE_DATE
     *
     * @param updateDate the value for t_ydzf_monitor_plan_rel.UPDATE_DATE
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ydzf_monitor_plan_rel.UPDATE_USERID
     *
     * @return the value of t_ydzf_monitor_plan_rel.UPDATE_USERID
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public Integer getUpdateUserid() {
        return updateUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ydzf_monitor_plan_rel.UPDATE_USERID
     *
     * @param updateUserid the value for t_ydzf_monitor_plan_rel.UPDATE_USERID
     *
     * @mbggenerated Thu Apr 07 21:33:00 GMT+08:00 2016
     */
    public void setUpdateUserid(Integer updateUserid) {
        this.updateUserid = updateUserid;
    }
}