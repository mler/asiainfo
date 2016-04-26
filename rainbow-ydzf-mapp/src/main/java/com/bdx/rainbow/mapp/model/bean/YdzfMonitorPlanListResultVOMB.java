package com.bdx.rainbow.mapp.model.bean;

import java.sql.Timestamp;

public class YdzfMonitorPlanListResultVOMB {
    private Integer monitorPlanId;
    private String checkPlanType;
    private String checkPlanCode;
    private String checkPlanName;
    private Timestamp checkPlanBeginDate;
    private Timestamp checkPlanEndDate;
    private Timestamp checkPlanFinishDate;
    private String checkPlanStatus;
    private String remark;
    private String isDel;
    private String checkEnterpriseType;
    private Integer checkExecuteDepId;
    //other
    //总数
    private Integer planCountTotal;
    //完成数
    private Integer planCountFinish;
    //完成百分比
    private String planCountPercent;
	public Integer getMonitorPlanId() {
		return monitorPlanId;
	}
	public String getCheckPlanType() {
		return checkPlanType;
	}
	public String getCheckPlanCode() {
		return checkPlanCode;
	}
	public String getCheckPlanName() {
		return checkPlanName;
	}
	public Timestamp getCheckPlanBeginDate() {
		return checkPlanBeginDate;
	}
	public Timestamp getCheckPlanEndDate() {
		return checkPlanEndDate;
	}
	public String getCheckPlanStatus() {
		return checkPlanStatus;
	}
	public String getRemark() {
		return remark;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setMonitorPlanId(Integer monitorPlanId) {
		this.monitorPlanId = monitorPlanId;
	}
	public void setCheckPlanType(String checkPlanType) {
		this.checkPlanType = checkPlanType;
	}
	public void setCheckPlanCode(String checkPlanCode) {
		this.checkPlanCode = checkPlanCode;
	}
	public void setCheckPlanName(String checkPlanName) {
		this.checkPlanName = checkPlanName;
	}
	public void setCheckPlanBeginDate(Timestamp checkPlanBeginDate) {
		this.checkPlanBeginDate = checkPlanBeginDate;
	}
	public void setCheckPlanEndDate(Timestamp checkPlanEndDate) {
		this.checkPlanEndDate = checkPlanEndDate;
	}
	public void setCheckPlanStatus(String checkPlanStatus) {
		this.checkPlanStatus = checkPlanStatus;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public Integer getPlanCountFinish() {
		return planCountFinish;
	}
	public String getPlanCountPercent() {
		return planCountPercent;
	}
	public void setPlanCountFinish(Integer planCountFinish) {
		this.planCountFinish = planCountFinish;
	}
	public void setPlanCountPercent(String planCountPercent) {
		this.planCountPercent = planCountPercent;
	}
	public Integer getPlanCountTotal() {
		return planCountTotal;
	}
	public void setPlanCountTotal(Integer planCountTotal) {
		this.planCountTotal = planCountTotal;
	}
	public Timestamp getCheckPlanFinishDate() {
		return checkPlanFinishDate;
	}
	public void setCheckPlanFinishDate(Timestamp checkPlanFinishDate) {
		this.checkPlanFinishDate = checkPlanFinishDate;
	}
	public String getCheckEnterpriseType() {
		return checkEnterpriseType;
	}
	public void setCheckEnterpriseType(String checkEnterpriseType) {
		this.checkEnterpriseType = checkEnterpriseType;
	}
	public Integer getCheckExecuteDepId() {
		return checkExecuteDepId;
	}
	public void setCheckExecuteDepId(Integer checkExecuteDepId) {
		this.checkExecuteDepId = checkExecuteDepId;
	}


}
