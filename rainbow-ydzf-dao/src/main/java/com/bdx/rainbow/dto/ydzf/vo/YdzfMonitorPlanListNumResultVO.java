package com.bdx.rainbow.dto.ydzf.vo;

public class YdzfMonitorPlanListNumResultVO {
	private String checkPlanType;
	private String checkPlanStatus;
	private int num;
	public String getCheckPlanType() {
		return checkPlanType;
	}

	public int getNum() {
		return num;
	}
	public void setCheckPlanType(String checkPlanType) {
		this.checkPlanType = checkPlanType;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getCheckPlanStatus() {
		return checkPlanStatus;
	}

	public void setCheckPlanStatus(String checkPlanStatus) {
		this.checkPlanStatus = checkPlanStatus;
	}
}
