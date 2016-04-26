package com.bdx.rainbow.dto.ydzf.form;

import java.sql.Timestamp;

public class YdzfEnterpriseHistoryQueryInfo {
    private Integer manageEnterpriseId;
    //日常检查类型
    private String checkPlanType;
    //查询稽查("monitor","inspect","all")
    private String queryType;
    //备用
    private int deptId;
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer limitClauseStart;
    private Integer limitClauseCount;
	public Integer getManageEnterpriseId() {
		return manageEnterpriseId;
	}
	public String getCheckPlanType() {
		return checkPlanType;
	}
	
	public Timestamp getStartDate() {
		return startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setManageEnterpriseId(Integer manageEnterpriseId) {
		this.manageEnterpriseId = manageEnterpriseId;
	}
	public void setCheckPlanType(String checkPlanType) {
		this.checkPlanType = checkPlanType;
	}
	
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public Integer getLimitClauseStart() {
		return limitClauseStart;
	}
	public Integer getLimitClauseCount() {
		return limitClauseCount;
	}
	public void setLimitClauseStart(Integer limitClauseStart) {
		this.limitClauseStart = limitClauseStart;
	}
	public void setLimitClauseCount(Integer limitClauseCount) {
		this.limitClauseCount = limitClauseCount;
	}
	
		
	
	

}
