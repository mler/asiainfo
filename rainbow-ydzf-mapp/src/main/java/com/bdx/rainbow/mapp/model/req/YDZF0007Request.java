package com.bdx.rainbow.mapp.model.req;

import com.bdx.rainbow.mapp.model.BDXBody;



public class YDZF0007Request extends BDXBody {
	
	private Integer monitorPlanId;
	
	private  String checkPlanType;

	public Integer getMonitorPlanId() {
		return monitorPlanId;
	}

	public String getCheckPlanType() {
		return checkPlanType;
	}

	public void setMonitorPlanId(Integer monitorPlanId) {
		this.monitorPlanId = monitorPlanId;
	}

	public void setCheckPlanType(String checkPlanType) {
		this.checkPlanType = checkPlanType;
	}







}
