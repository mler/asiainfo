package com.bdx.rainbow.entity.urs;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SysPlatfromExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    protected String suffix = "";

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    protected int limitClauseStart = -1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    protected int limitClauseCount = -1;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public SysPlatfromExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public void setLimitClauseStart(int limitClauseStart) {
        this.limitClauseStart=limitClauseStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public int getLimitClauseStart() {
        return limitClauseStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public void setLimitClauseCount(int limitClauseCount) {
        this.limitClauseCount=limitClauseCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public int getLimitClauseCount() {
        return limitClauseCount;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andPlatIdIsNull() {
            addCriterion("PLAT_ID is null");
            return (Criteria) this;
        }

        public Criteria andPlatIdIsNotNull() {
            addCriterion("PLAT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPlatIdEqualTo(Integer value) {
            addCriterion("PLAT_ID =", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdNotEqualTo(Integer value) {
            addCriterion("PLAT_ID <>", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdGreaterThan(Integer value) {
            addCriterion("PLAT_ID >", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("PLAT_ID >=", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdLessThan(Integer value) {
            addCriterion("PLAT_ID <", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdLessThanOrEqualTo(Integer value) {
            addCriterion("PLAT_ID <=", value, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdIn(List<Integer> values) {
            addCriterion("PLAT_ID in", values, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdNotIn(List<Integer> values) {
            addCriterion("PLAT_ID not in", values, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdBetween(Integer value1, Integer value2) {
            addCriterion("PLAT_ID between", value1, value2, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatIdNotBetween(Integer value1, Integer value2) {
            addCriterion("PLAT_ID not between", value1, value2, "platId");
            return (Criteria) this;
        }

        public Criteria andPlatNameIsNull() {
            addCriterion("PLAT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPlatNameIsNotNull() {
            addCriterion("PLAT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPlatNameEqualTo(String value) {
            addCriterion("PLAT_NAME =", value, "platName");
            return (Criteria) this;
        }

        public Criteria andPlatNameNotEqualTo(String value) {
            addCriterion("PLAT_NAME <>", value, "platName");
            return (Criteria) this;
        }

        public Criteria andPlatNameGreaterThan(String value) {
            addCriterion("PLAT_NAME >", value, "platName");
            return (Criteria) this;
        }

        public Criteria andPlatNameGreaterThanOrEqualTo(String value) {
            addCriterion("PLAT_NAME >=", value, "platName");
            return (Criteria) this;
        }

        public Criteria andPlatNameLessThan(String value) {
            addCriterion("PLAT_NAME <", value, "platName");
            return (Criteria) this;
        }

        public Criteria andPlatNameLessThanOrEqualTo(String value) {
            addCriterion("PLAT_NAME <=", value, "platName");
            return (Criteria) this;
        }

        public Criteria andPlatNameLike(String value) {
            addCriterion("PLAT_NAME like", value, "platName");
            return (Criteria) this;
        }

        public Criteria andPlatNameNotLike(String value) {
            addCriterion("PLAT_NAME not like", value, "platName");
            return (Criteria) this;
        }

        public Criteria andPlatNameIn(List<String> values) {
            addCriterion("PLAT_NAME in", values, "platName");
            return (Criteria) this;
        }

        public Criteria andPlatNameNotIn(List<String> values) {
            addCriterion("PLAT_NAME not in", values, "platName");
            return (Criteria) this;
        }

        public Criteria andPlatNameBetween(String value1, String value2) {
            addCriterion("PLAT_NAME between", value1, value2, "platName");
            return (Criteria) this;
        }

        public Criteria andPlatNameNotBetween(String value1, String value2) {
            addCriterion("PLAT_NAME not between", value1, value2, "platName");
            return (Criteria) this;
        }

        public Criteria andPlatStatusIsNull() {
            addCriterion("PLAT_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andPlatStatusIsNotNull() {
            addCriterion("PLAT_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andPlatStatusEqualTo(String value) {
            addCriterion("PLAT_STATUS =", value, "platStatus");
            return (Criteria) this;
        }

        public Criteria andPlatStatusNotEqualTo(String value) {
            addCriterion("PLAT_STATUS <>", value, "platStatus");
            return (Criteria) this;
        }

        public Criteria andPlatStatusGreaterThan(String value) {
            addCriterion("PLAT_STATUS >", value, "platStatus");
            return (Criteria) this;
        }

        public Criteria andPlatStatusGreaterThanOrEqualTo(String value) {
            addCriterion("PLAT_STATUS >=", value, "platStatus");
            return (Criteria) this;
        }

        public Criteria andPlatStatusLessThan(String value) {
            addCriterion("PLAT_STATUS <", value, "platStatus");
            return (Criteria) this;
        }

        public Criteria andPlatStatusLessThanOrEqualTo(String value) {
            addCriterion("PLAT_STATUS <=", value, "platStatus");
            return (Criteria) this;
        }

        public Criteria andPlatStatusLike(String value) {
            addCriterion("PLAT_STATUS like", value, "platStatus");
            return (Criteria) this;
        }

        public Criteria andPlatStatusNotLike(String value) {
            addCriterion("PLAT_STATUS not like", value, "platStatus");
            return (Criteria) this;
        }

        public Criteria andPlatStatusIn(List<String> values) {
            addCriterion("PLAT_STATUS in", values, "platStatus");
            return (Criteria) this;
        }

        public Criteria andPlatStatusNotIn(List<String> values) {
            addCriterion("PLAT_STATUS not in", values, "platStatus");
            return (Criteria) this;
        }

        public Criteria andPlatStatusBetween(String value1, String value2) {
            addCriterion("PLAT_STATUS between", value1, value2, "platStatus");
            return (Criteria) this;
        }

        public Criteria andPlatStatusNotBetween(String value1, String value2) {
            addCriterion("PLAT_STATUS not between", value1, value2, "platStatus");
            return (Criteria) this;
        }

        public Criteria andPlatUrlIsNull() {
            addCriterion("PLAT_URL is null");
            return (Criteria) this;
        }

        public Criteria andPlatUrlIsNotNull() {
            addCriterion("PLAT_URL is not null");
            return (Criteria) this;
        }

        public Criteria andPlatUrlEqualTo(String value) {
            addCriterion("PLAT_URL =", value, "platUrl");
            return (Criteria) this;
        }

        public Criteria andPlatUrlNotEqualTo(String value) {
            addCriterion("PLAT_URL <>", value, "platUrl");
            return (Criteria) this;
        }

        public Criteria andPlatUrlGreaterThan(String value) {
            addCriterion("PLAT_URL >", value, "platUrl");
            return (Criteria) this;
        }

        public Criteria andPlatUrlGreaterThanOrEqualTo(String value) {
            addCriterion("PLAT_URL >=", value, "platUrl");
            return (Criteria) this;
        }

        public Criteria andPlatUrlLessThan(String value) {
            addCriterion("PLAT_URL <", value, "platUrl");
            return (Criteria) this;
        }

        public Criteria andPlatUrlLessThanOrEqualTo(String value) {
            addCriterion("PLAT_URL <=", value, "platUrl");
            return (Criteria) this;
        }

        public Criteria andPlatUrlLike(String value) {
            addCriterion("PLAT_URL like", value, "platUrl");
            return (Criteria) this;
        }

        public Criteria andPlatUrlNotLike(String value) {
            addCriterion("PLAT_URL not like", value, "platUrl");
            return (Criteria) this;
        }

        public Criteria andPlatUrlIn(List<String> values) {
            addCriterion("PLAT_URL in", values, "platUrl");
            return (Criteria) this;
        }

        public Criteria andPlatUrlNotIn(List<String> values) {
            addCriterion("PLAT_URL not in", values, "platUrl");
            return (Criteria) this;
        }

        public Criteria andPlatUrlBetween(String value1, String value2) {
            addCriterion("PLAT_URL between", value1, value2, "platUrl");
            return (Criteria) this;
        }

        public Criteria andPlatUrlNotBetween(String value1, String value2) {
            addCriterion("PLAT_URL not between", value1, value2, "platUrl");
            return (Criteria) this;
        }

        public Criteria andCreaterIsNull() {
            addCriterion("CREATER is null");
            return (Criteria) this;
        }

        public Criteria andCreaterIsNotNull() {
            addCriterion("CREATER is not null");
            return (Criteria) this;
        }

        public Criteria andCreaterEqualTo(Integer value) {
            addCriterion("CREATER =", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotEqualTo(Integer value) {
            addCriterion("CREATER <>", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThan(Integer value) {
            addCriterion("CREATER >", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThanOrEqualTo(Integer value) {
            addCriterion("CREATER >=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThan(Integer value) {
            addCriterion("CREATER <", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThanOrEqualTo(Integer value) {
            addCriterion("CREATER <=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterIn(List<Integer> values) {
            addCriterion("CREATER in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotIn(List<Integer> values) {
            addCriterion("CREATER not in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterBetween(Integer value1, Integer value2) {
            addCriterion("CREATER between", value1, value2, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotBetween(Integer value1, Integer value2) {
            addCriterion("CREATER not between", value1, value2, "creater");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Timestamp value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Timestamp value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Timestamp value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Timestamp value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Timestamp value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Timestamp value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Timestamp> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Timestamp> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Timestamp value1, Timestamp value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Timestamp value1, Timestamp value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdaterIsNull() {
            addCriterion("UPDATER is null");
            return (Criteria) this;
        }

        public Criteria andUpdaterIsNotNull() {
            addCriterion("UPDATER is not null");
            return (Criteria) this;
        }

        public Criteria andUpdaterEqualTo(Integer value) {
            addCriterion("UPDATER =", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotEqualTo(Integer value) {
            addCriterion("UPDATER <>", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterGreaterThan(Integer value) {
            addCriterion("UPDATER >", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterGreaterThanOrEqualTo(Integer value) {
            addCriterion("UPDATER >=", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLessThan(Integer value) {
            addCriterion("UPDATER <", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLessThanOrEqualTo(Integer value) {
            addCriterion("UPDATER <=", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterIn(List<Integer> values) {
            addCriterion("UPDATER in", values, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotIn(List<Integer> values) {
            addCriterion("UPDATER not in", values, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterBetween(Integer value1, Integer value2) {
            addCriterion("UPDATER between", value1, value2, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotBetween(Integer value1, Integer value2) {
            addCriterion("UPDATER not between", value1, value2, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Timestamp value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Timestamp value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Timestamp value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Timestamp value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Timestamp value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Timestamp value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Timestamp> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Timestamp> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Timestamp value1, Timestamp value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Timestamp value1, Timestamp value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andPlatTypeIsNull() {
            addCriterion("PLAT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPlatTypeIsNotNull() {
            addCriterion("PLAT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPlatTypeEqualTo(String value) {
            addCriterion("PLAT_TYPE =", value, "platType");
            return (Criteria) this;
        }

        public Criteria andPlatTypeNotEqualTo(String value) {
            addCriterion("PLAT_TYPE <>", value, "platType");
            return (Criteria) this;
        }

        public Criteria andPlatTypeGreaterThan(String value) {
            addCriterion("PLAT_TYPE >", value, "platType");
            return (Criteria) this;
        }

        public Criteria andPlatTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PLAT_TYPE >=", value, "platType");
            return (Criteria) this;
        }

        public Criteria andPlatTypeLessThan(String value) {
            addCriterion("PLAT_TYPE <", value, "platType");
            return (Criteria) this;
        }

        public Criteria andPlatTypeLessThanOrEqualTo(String value) {
            addCriterion("PLAT_TYPE <=", value, "platType");
            return (Criteria) this;
        }

        public Criteria andPlatTypeLike(String value) {
            addCriterion("PLAT_TYPE like", value, "platType");
            return (Criteria) this;
        }

        public Criteria andPlatTypeNotLike(String value) {
            addCriterion("PLAT_TYPE not like", value, "platType");
            return (Criteria) this;
        }

        public Criteria andPlatTypeIn(List<String> values) {
            addCriterion("PLAT_TYPE in", values, "platType");
            return (Criteria) this;
        }

        public Criteria andPlatTypeNotIn(List<String> values) {
            addCriterion("PLAT_TYPE not in", values, "platType");
            return (Criteria) this;
        }

        public Criteria andPlatTypeBetween(String value1, String value2) {
            addCriterion("PLAT_TYPE between", value1, value2, "platType");
            return (Criteria) this;
        }

        public Criteria andPlatTypeNotBetween(String value1, String value2) {
            addCriterion("PLAT_TYPE not between", value1, value2, "platType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("USER_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("USER_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(String value) {
            addCriterion("USER_TYPE =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(String value) {
            addCriterion("USER_TYPE <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(String value) {
            addCriterion("USER_TYPE >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(String value) {
            addCriterion("USER_TYPE >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(String value) {
            addCriterion("USER_TYPE <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(String value) {
            addCriterion("USER_TYPE <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLike(String value) {
            addCriterion("USER_TYPE like", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotLike(String value) {
            addCriterion("USER_TYPE not like", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<String> values) {
            addCriterion("USER_TYPE in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<String> values) {
            addCriterion("USER_TYPE not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(String value1, String value2) {
            addCriterion("USER_TYPE between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(String value1, String value2) {
            addCriterion("USER_TYPE not between", value1, value2, "userType");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_sys_platform
     *
     * @mbggenerated do_not_delete_during_merge Fri Mar 11 20:31:12 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_sys_platform
     *
     * @mbggenerated Fri Mar 11 20:31:12 CST 2016
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}