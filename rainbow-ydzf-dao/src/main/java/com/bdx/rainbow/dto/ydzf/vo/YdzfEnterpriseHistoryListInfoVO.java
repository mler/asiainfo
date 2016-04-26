package com.bdx.rainbow.dto.ydzf.vo;

import java.sql.Timestamp;


public class YdzfEnterpriseHistoryListInfoVO {
	private int id;
	private int relId;
	private String type;
    private Timestamp appointDate;
    //日常检查类型
    private String checkPlanType;
    //检查结果
    private String checkTaskResult;
    private int monitorTempletTotal;
    //稽查办案处罚
    private String pubishAdvide;
	public int getId() {
		return id;
	}
	public int getRelId() {
		return relId;
	}
	public String getType() {
		return type;
	}
	public Timestamp getAppointDate() {
		return appointDate;
	}
	public String getCheckPlanType() {
		return checkPlanType;
	}
	public String getCheckTaskResult() {
		return checkTaskResult;
	}
	public int getMonitorTempletTotal() {
		return monitorTempletTotal;
	}
	public String getPubishAdvide() {
		return pubishAdvide;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setRelId(int relId) {
		this.relId = relId;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setAppointDate(Timestamp appointDate) {
		this.appointDate = appointDate;
	}
	public void setCheckPlanType(String checkPlanType) {
		this.checkPlanType = checkPlanType;
	}
	public void setCheckTaskResult(String checkTaskResult) {
		this.checkTaskResult = checkTaskResult;
	}
	public void setMonitorTempletTotal(int monitorTempletTotal) {
		this.monitorTempletTotal = monitorTempletTotal;
	}
	public void setPubishAdvide(String pubishAdvide) {
		this.pubishAdvide = pubishAdvide;
	}
    


	

}
