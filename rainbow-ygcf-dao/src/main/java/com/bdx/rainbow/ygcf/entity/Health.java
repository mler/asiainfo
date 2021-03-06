package com.bdx.rainbow.ygcf.entity;

import java.sql.Timestamp;

public class Health {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_health.HEALTH_ID
     *
     * @mbggenerated
     */
    private Integer healthId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_health.EMPLOYEE_ID
     *
     * @mbggenerated
     */
    private Integer employeeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_health.HEALTH_CODE
     *
     * @mbggenerated
     */
    private String healthCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_health.JOBS
     *
     * @mbggenerated
     */
    private String jobs;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_health.HEALTH_RESULT
     *
     * @mbggenerated
     */
    private String healthResult;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_health.ISSUE_DATE
     *
     * @mbggenerated
     */
    private Timestamp issueDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_health.VALID_DATE
     *
     * @mbggenerated
     */
    private Timestamp validDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_health.HEALTH_PATH
     *
     * @mbggenerated
     */
    private String healthPath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_health.CHECK_UNIT
     *
     * @mbggenerated
     */
    private String checkUnit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_health.STATUS
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_health.IS_DEL
     *
     * @mbggenerated
     */
    private String isDel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_health.CREATE_DATE
     *
     * @mbggenerated
     */
    private Timestamp createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_health.CREATE_USERID
     *
     * @mbggenerated
     */
    private Integer createUserid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_health.UPDATE_DATE
     *
     * @mbggenerated
     */
    private Timestamp updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_ygcf_health.UPDATE_USERID
     *
     * @mbggenerated
     */
    private Integer updateUserid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_health.HEALTH_ID
     *
     * @return the value of t_ygcf_health.HEALTH_ID
     *
     * @mbggenerated
     */
    public Integer getHealthId() {
        return healthId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_health.HEALTH_ID
     *
     * @param healthId the value for t_ygcf_health.HEALTH_ID
     *
     * @mbggenerated
     */
    public void setHealthId(Integer healthId) {
        this.healthId = healthId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_health.EMPLOYEE_ID
     *
     * @return the value of t_ygcf_health.EMPLOYEE_ID
     *
     * @mbggenerated
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_health.EMPLOYEE_ID
     *
     * @param employeeId the value for t_ygcf_health.EMPLOYEE_ID
     *
     * @mbggenerated
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_health.HEALTH_CODE
     *
     * @return the value of t_ygcf_health.HEALTH_CODE
     *
     * @mbggenerated
     */
    public String getHealthCode() {
        return healthCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_health.HEALTH_CODE
     *
     * @param healthCode the value for t_ygcf_health.HEALTH_CODE
     *
     * @mbggenerated
     */
    public void setHealthCode(String healthCode) {
        this.healthCode = healthCode == null ? null : healthCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_health.JOBS
     *
     * @return the value of t_ygcf_health.JOBS
     *
     * @mbggenerated
     */
    public String getJobs() {
        return jobs;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_health.JOBS
     *
     * @param jobs the value for t_ygcf_health.JOBS
     *
     * @mbggenerated
     */
    public void setJobs(String jobs) {
        this.jobs = jobs == null ? null : jobs.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_health.HEALTH_RESULT
     *
     * @return the value of t_ygcf_health.HEALTH_RESULT
     *
     * @mbggenerated
     */
    public String getHealthResult() {
        return healthResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_health.HEALTH_RESULT
     *
     * @param healthResult the value for t_ygcf_health.HEALTH_RESULT
     *
     * @mbggenerated
     */
    public void setHealthResult(String healthResult) {
        this.healthResult = healthResult == null ? null : healthResult.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_health.ISSUE_DATE
     *
     * @return the value of t_ygcf_health.ISSUE_DATE
     *
     * @mbggenerated
     */
    public Timestamp getIssueDate() {
        return issueDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_health.ISSUE_DATE
     *
     * @param issueDate the value for t_ygcf_health.ISSUE_DATE
     *
     * @mbggenerated
     */
    public void setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_health.VALID_DATE
     *
     * @return the value of t_ygcf_health.VALID_DATE
     *
     * @mbggenerated
     */
    public Timestamp getValidDate() {
        return validDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_health.VALID_DATE
     *
     * @param validDate the value for t_ygcf_health.VALID_DATE
     *
     * @mbggenerated
     */
    public void setValidDate(Timestamp validDate) {
        this.validDate = validDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_health.HEALTH_PATH
     *
     * @return the value of t_ygcf_health.HEALTH_PATH
     *
     * @mbggenerated
     */
    public String getHealthPath() {
        return healthPath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_health.HEALTH_PATH
     *
     * @param healthPath the value for t_ygcf_health.HEALTH_PATH
     *
     * @mbggenerated
     */
    public void setHealthPath(String healthPath) {
        this.healthPath = healthPath == null ? null : healthPath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_health.CHECK_UNIT
     *
     * @return the value of t_ygcf_health.CHECK_UNIT
     *
     * @mbggenerated
     */
    public String getCheckUnit() {
        return checkUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_health.CHECK_UNIT
     *
     * @param checkUnit the value for t_ygcf_health.CHECK_UNIT
     *
     * @mbggenerated
     */
    public void setCheckUnit(String checkUnit) {
        this.checkUnit = checkUnit == null ? null : checkUnit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_health.STATUS
     *
     * @return the value of t_ygcf_health.STATUS
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_health.STATUS
     *
     * @param status the value for t_ygcf_health.STATUS
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_health.IS_DEL
     *
     * @return the value of t_ygcf_health.IS_DEL
     *
     * @mbggenerated
     */
    public String getIsDel() {
        return isDel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_health.IS_DEL
     *
     * @param isDel the value for t_ygcf_health.IS_DEL
     *
     * @mbggenerated
     */
    public void setIsDel(String isDel) {
        this.isDel = isDel == null ? null : isDel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_health.CREATE_DATE
     *
     * @return the value of t_ygcf_health.CREATE_DATE
     *
     * @mbggenerated
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_health.CREATE_DATE
     *
     * @param createDate the value for t_ygcf_health.CREATE_DATE
     *
     * @mbggenerated
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_health.CREATE_USERID
     *
     * @return the value of t_ygcf_health.CREATE_USERID
     *
     * @mbggenerated
     */
    public Integer getCreateUserid() {
        return createUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_health.CREATE_USERID
     *
     * @param createUserid the value for t_ygcf_health.CREATE_USERID
     *
     * @mbggenerated
     */
    public void setCreateUserid(Integer createUserid) {
        this.createUserid = createUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_health.UPDATE_DATE
     *
     * @return the value of t_ygcf_health.UPDATE_DATE
     *
     * @mbggenerated
     */
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_health.UPDATE_DATE
     *
     * @param updateDate the value for t_ygcf_health.UPDATE_DATE
     *
     * @mbggenerated
     */
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_ygcf_health.UPDATE_USERID
     *
     * @return the value of t_ygcf_health.UPDATE_USERID
     *
     * @mbggenerated
     */
    public Integer getUpdateUserid() {
        return updateUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_ygcf_health.UPDATE_USERID
     *
     * @param updateUserid the value for t_ygcf_health.UPDATE_USERID
     *
     * @mbggenerated
     */
    public void setUpdateUserid(Integer updateUserid) {
        this.updateUserid = updateUserid;
    }
}