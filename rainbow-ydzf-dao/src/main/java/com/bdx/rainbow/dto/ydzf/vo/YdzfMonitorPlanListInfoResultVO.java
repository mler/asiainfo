package com.bdx.rainbow.dto.ydzf.vo;

import java.util.List;

import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlan;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTemplet;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTempletContent;

public class YdzfMonitorPlanListInfoResultVO {
	private TYdzfMonitorPlan ydzfMonitorPlan;
	private List<TYdzfMonitorTempletContent> ydzfMonitorTempletContentList;
	private TYdzfMonitorTemplet ydzfMonitorTemplet;
    private List<YdzfMonitorPlanRelEnterpriseVO> enterpriseAllVOList;
    private List<YdzfMonitorPlanRelEnterpriseVO> enterpriseWaitVOList;
    private List<YdzfMonitorPlanRelEnterpriseVO> enterpriseFinishVOList;
    private String planCreatUserName;
	private String planCreatUserPhone;
	public TYdzfMonitorPlan getYdzfMonitorPlan() {
		return ydzfMonitorPlan;
	}
	public List<YdzfMonitorPlanRelEnterpriseVO> getEnterpriseAllVOList() {
		return enterpriseAllVOList;
	}
	public List<YdzfMonitorPlanRelEnterpriseVO> getEnterpriseWaitVOList() {
		return enterpriseWaitVOList;
	}
	public List<YdzfMonitorPlanRelEnterpriseVO> getEnterpriseFinishVOList() {
		return enterpriseFinishVOList;
	}
	public void setYdzfMonitorPlan(TYdzfMonitorPlan ydzfMonitorPlan) {
		this.ydzfMonitorPlan = ydzfMonitorPlan;
	}
	public void setEnterpriseAllVOList(
			List<YdzfMonitorPlanRelEnterpriseVO> enterpriseAllVOList) {
		this.enterpriseAllVOList = enterpriseAllVOList;
	}
	public void setEnterpriseWaitVOList(
			List<YdzfMonitorPlanRelEnterpriseVO> enterpriseWaitVOList) {
		this.enterpriseWaitVOList = enterpriseWaitVOList;
	}
	public void setEnterpriseFinishVOList(
			List<YdzfMonitorPlanRelEnterpriseVO> enterpriseFinishVOList) {
		this.enterpriseFinishVOList = enterpriseFinishVOList;
	}
	public TYdzfMonitorTemplet getYdzfMonitorTemplet() {
		return ydzfMonitorTemplet;
	}
	public void setYdzfMonitorTemplet(TYdzfMonitorTemplet ydzfMonitorTemplet) {
		this.ydzfMonitorTemplet = ydzfMonitorTemplet;
	}
	public List<TYdzfMonitorTempletContent> getYdzfMonitorTempletContentList() {
		return ydzfMonitorTempletContentList;
	}
	public void setYdzfMonitorTempletContentList(
			List<TYdzfMonitorTempletContent> ydzfMonitorTempletContentList) {
		this.ydzfMonitorTempletContentList = ydzfMonitorTempletContentList;
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
