package com.bdx.rainbow.mapp.model.bean;


public class YdzfInspectCaseListResultVOMB {
	
	private TYdzfInspectCaseMB ydzfInspectCaseMB;
	//other
	private  String manageEnterpriseName;
	//{"status_info": [{"status": "1","id":"1", "datetime": "2016-01-01 12:00:00"}, {"status": "2","id":"1", "datetime": "2016-01-01 12:00:00"} ] }
	private  String inspectCaseRelInfoJson;
	public TYdzfInspectCaseMB getYdzfInspectCaseMB() {
		return ydzfInspectCaseMB;
	}
	public String getManageEnterpriseName() {
		return manageEnterpriseName;
	}
	public String getInspectCaseRelInfoJson() {
		return inspectCaseRelInfoJson;
	}
	public void setYdzfInspectCaseMB(TYdzfInspectCaseMB ydzfInspectCaseMB) {
		this.ydzfInspectCaseMB = ydzfInspectCaseMB;
	}
	public void setManageEnterpriseName(String manageEnterpriseName) {
		this.manageEnterpriseName = manageEnterpriseName;
	}
	public void setInspectCaseRelInfoJson(String inspectCaseRelInfoJson) {
		this.inspectCaseRelInfoJson = inspectCaseRelInfoJson;
	}



}
