package com.bdx.rainbow.entity.etl;

import java.sql.Timestamp;

public class SyjTableBean {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syj_table_bean.ID
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syj_table_bean.URL
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syj_table_bean.TABLE_NAME
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    private String tableName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syj_table_bean.TABLE_CLASS
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    private String tableClass;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syj_table_bean.TITLE
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syj_table_bean.STATUS
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syj_table_bean.TOTAL
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    private Long total;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syj_table_bean.CORRECT
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    private Long correct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syj_table_bean.ERROR
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    private Long error;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syj_table_bean.CREATE_TIME
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    private Timestamp createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syj_table_bean.CREATE_EMP_CODE
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    private String createEmpCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syj_table_bean.UPDATE_TIME
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    private Timestamp updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column syj_table_bean.UPDATE_TIME_CODE
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    private String updateTimeCode;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syj_table_bean.ID
     *
     * @return the value of syj_table_bean.ID
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syj_table_bean.ID
     *
     * @param id the value for syj_table_bean.ID
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syj_table_bean.URL
     *
     * @return the value of syj_table_bean.URL
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syj_table_bean.URL
     *
     * @param url the value for syj_table_bean.URL
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syj_table_bean.TABLE_NAME
     *
     * @return the value of syj_table_bean.TABLE_NAME
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syj_table_bean.TABLE_NAME
     *
     * @param tableName the value for syj_table_bean.TABLE_NAME
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syj_table_bean.TABLE_CLASS
     *
     * @return the value of syj_table_bean.TABLE_CLASS
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public String getTableClass() {
        return tableClass;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syj_table_bean.TABLE_CLASS
     *
     * @param tableClass the value for syj_table_bean.TABLE_CLASS
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public void setTableClass(String tableClass) {
        this.tableClass = tableClass == null ? null : tableClass.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syj_table_bean.TITLE
     *
     * @return the value of syj_table_bean.TITLE
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syj_table_bean.TITLE
     *
     * @param title the value for syj_table_bean.TITLE
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syj_table_bean.STATUS
     *
     * @return the value of syj_table_bean.STATUS
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syj_table_bean.STATUS
     *
     * @param status the value for syj_table_bean.STATUS
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syj_table_bean.TOTAL
     *
     * @return the value of syj_table_bean.TOTAL
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public Long getTotal() {
        return total;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syj_table_bean.TOTAL
     *
     * @param total the value for syj_table_bean.TOTAL
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syj_table_bean.CORRECT
     *
     * @return the value of syj_table_bean.CORRECT
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public Long getCorrect() {
        return correct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syj_table_bean.CORRECT
     *
     * @param correct the value for syj_table_bean.CORRECT
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public void setCorrect(Long correct) {
        this.correct = correct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syj_table_bean.ERROR
     *
     * @return the value of syj_table_bean.ERROR
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public Long getError() {
        return error;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syj_table_bean.ERROR
     *
     * @param error the value for syj_table_bean.ERROR
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public void setError(Long error) {
        this.error = error;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syj_table_bean.CREATE_TIME
     *
     * @return the value of syj_table_bean.CREATE_TIME
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public Timestamp getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syj_table_bean.CREATE_TIME
     *
     * @param createTime the value for syj_table_bean.CREATE_TIME
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syj_table_bean.CREATE_EMP_CODE
     *
     * @return the value of syj_table_bean.CREATE_EMP_CODE
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public String getCreateEmpCode() {
        return createEmpCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syj_table_bean.CREATE_EMP_CODE
     *
     * @param createEmpCode the value for syj_table_bean.CREATE_EMP_CODE
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public void setCreateEmpCode(String createEmpCode) {
        this.createEmpCode = createEmpCode == null ? null : createEmpCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syj_table_bean.UPDATE_TIME
     *
     * @return the value of syj_table_bean.UPDATE_TIME
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syj_table_bean.UPDATE_TIME
     *
     * @param updateTime the value for syj_table_bean.UPDATE_TIME
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column syj_table_bean.UPDATE_TIME_CODE
     *
     * @return the value of syj_table_bean.UPDATE_TIME_CODE
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public String getUpdateTimeCode() {
        return updateTimeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column syj_table_bean.UPDATE_TIME_CODE
     *
     * @param updateTimeCode the value for syj_table_bean.UPDATE_TIME_CODE
     *
     * @mbggenerated Fri Aug 21 15:00:51 CST 2015
     */
    public void setUpdateTimeCode(String updateTimeCode) {
        this.updateTimeCode = updateTimeCode == null ? null : updateTimeCode.trim();
    }
}