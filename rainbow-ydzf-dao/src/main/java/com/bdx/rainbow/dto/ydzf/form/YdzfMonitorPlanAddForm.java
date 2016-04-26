package com.bdx.rainbow.dto.ydzf.form;

import java.util.List;

import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlan;

public class YdzfMonitorPlanAddForm {
	
	private TYdzfMonitorPlan ydzfMonitorPlan;
    //other
    private List<Integer> enterpriseIdList;
	public TYdzfMonitorPlan getYdzfMonitorPlan() {
		return ydzfMonitorPlan;
	}
	public List<Integer> getEnterpriseIdList() {
		return enterpriseIdList;
	}
	public void setYdzfMonitorPlan(TYdzfMonitorPlan ydzfMonitorPlan) {
		this.ydzfMonitorPlan = ydzfMonitorPlan;
	}
	public void setEnterpriseIdList(List<Integer> enterpriseIdList) {
		this.enterpriseIdList = enterpriseIdList;
	}


	

}
