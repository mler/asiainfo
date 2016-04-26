package com.bdx.rainbow.mapp.model.rsp;


import java.util.List;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfMonitorPlanMB;
import com.bdx.rainbow.mapp.model.bean.TYdzfMonitorTempletContentMB;
import com.bdx.rainbow.mapp.model.bean.TYdzfMonitorTempletMB;
import com.bdx.rainbow.mapp.model.bean.YdzfMonitorPlanRelEnterpriseVOMB;


public class YDZF0006Response extends BDXBody {
	private TYdzfMonitorPlanMB ydzfMonitorPlanMB;
	private List<TYdzfMonitorTempletContentMB> ydzfMonitorTempletContentMBList;
	private TYdzfMonitorTempletMB ydzfMonitorTempletMB;
    private List<YdzfMonitorPlanRelEnterpriseVOMB> enterpriseAllVOMBList;
    private List<YdzfMonitorPlanRelEnterpriseVOMB> enterpriseWaitVOMBList;
    private List<YdzfMonitorPlanRelEnterpriseVOMB> enterpriseFinishVOMBList;
    private String planCreatUserName;
 	private String planCreatUserPhone;
	public TYdzfMonitorPlanMB getYdzfMonitorPlanMB() {
		return ydzfMonitorPlanMB;
	}
	public List<TYdzfMonitorTempletContentMB> getYdzfMonitorTempletContentMBList() {
		return ydzfMonitorTempletContentMBList;
	}
	public TYdzfMonitorTempletMB getYdzfMonitorTempletMB() {
		return ydzfMonitorTempletMB;
	}
	public List<YdzfMonitorPlanRelEnterpriseVOMB> getEnterpriseAllVOMBList() {
		return enterpriseAllVOMBList;
	}
	public List<YdzfMonitorPlanRelEnterpriseVOMB> getEnterpriseWaitVOMBList() {
		return enterpriseWaitVOMBList;
	}
	public List<YdzfMonitorPlanRelEnterpriseVOMB> getEnterpriseFinishVOMBList() {
		return enterpriseFinishVOMBList;
	}
	public void setYdzfMonitorPlanMB(TYdzfMonitorPlanMB ydzfMonitorPlanMB) {
		this.ydzfMonitorPlanMB = ydzfMonitorPlanMB;
	}
	public void setYdzfMonitorTempletContentMBList(
			List<TYdzfMonitorTempletContentMB> ydzfMonitorTempletContentMBList) {
		this.ydzfMonitorTempletContentMBList = ydzfMonitorTempletContentMBList;
	}
	public void setYdzfMonitorTempletMB(TYdzfMonitorTempletMB ydzfMonitorTempletMB) {
		this.ydzfMonitorTempletMB = ydzfMonitorTempletMB;
	}
	public void setEnterpriseAllVOMBList(
			List<YdzfMonitorPlanRelEnterpriseVOMB> enterpriseAllVOMBList) {
		this.enterpriseAllVOMBList = enterpriseAllVOMBList;
	}
	public void setEnterpriseWaitVOMBList(
			List<YdzfMonitorPlanRelEnterpriseVOMB> enterpriseWaitVOMBList) {
		this.enterpriseWaitVOMBList = enterpriseWaitVOMBList;
	}
	public void setEnterpriseFinishVOMBList(
			List<YdzfMonitorPlanRelEnterpriseVOMB> enterpriseFinishVOMBList) {
		this.enterpriseFinishVOMBList = enterpriseFinishVOMBList;
	}
	public String getPlanCreatUserName() {
		return planCreatUserName;
	}
	public String getPlanCreatUserPhone() {
		return planCreatUserPhone;
	}
	public void setPlanCreatUserName(String planCreatUserName) {
		this.planCreatUserName = planCreatUserName;
	}
	public void setPlanCreatUserPhone(String planCreatUserPhone) {
		this.planCreatUserPhone = planCreatUserPhone;
	}


}
