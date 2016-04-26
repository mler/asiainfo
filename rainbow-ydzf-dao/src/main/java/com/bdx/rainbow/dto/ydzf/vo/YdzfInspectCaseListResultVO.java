package com.bdx.rainbow.dto.ydzf.vo;

import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;

public class YdzfInspectCaseListResultVO {
	
	private TYdzfInspectCase ydzfInspectCase;
	//other
	private  String manageEnterpriseName;
	//{"status_info": [{"status": "1","id":"1", "datetime": "2016-01-01 12:00:00"}, {"status": "2","id":"1", "datetime": "2016-01-01 12:00:00"} ] }
	private  String inspectCaseRelInfoJson;
	public TYdzfInspectCase getYdzfInspectCase() {
		return ydzfInspectCase;
	}
	public String getManageEnterpriseName() {
		return manageEnterpriseName;
	}
	public void setYdzfInspectCase(TYdzfInspectCase ydzfInspectCase) {
		this.ydzfInspectCase = ydzfInspectCase;
	}
	public void setManageEnterpriseName(String manageEnterpriseName) {
		this.manageEnterpriseName = manageEnterpriseName;
	}
	public String getInspectCaseRelInfoJson() {
		return inspectCaseRelInfoJson;
	}
	public void setInspectCaseRelInfoJson(String inspectCaseRelInfoJson) {
		this.inspectCaseRelInfoJson = inspectCaseRelInfoJson;
	}


}
