package com.bdx.rainbow.dto.ydzf.vo;

import java.util.List;

import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlan;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTemplet;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTempletContent;

public class YdzfMonitorPlanTaskInfoResultVO {
	private TYdzfMonitorPlan ydzfMonitorPlan;
	private List<TYdzfMonitorTempletContent> ydzfMonitorTempletContentList;
	private TYdzfMonitorTemplet ydzfMonitorTemplet;
	private YdzfMonitorPlanRelEnterpriseVO ydzfMonitorPlanRelEnterpriseVO;
	public TYdzfMonitorPlan getYdzfMonitorPlan() {
		return ydzfMonitorPlan;
	}
	public List<TYdzfMonitorTempletContent> getYdzfMonitorTempletContentList() {
		return ydzfMonitorTempletContentList;
	}
	public TYdzfMonitorTemplet getYdzfMonitorTemplet() {
		return ydzfMonitorTemplet;
	}
	public YdzfMonitorPlanRelEnterpriseVO getYdzfMonitorPlanRelEnterpriseVO() {
		return ydzfMonitorPlanRelEnterpriseVO;
	}
	public void setYdzfMonitorPlan(TYdzfMonitorPlan ydzfMonitorPlan) {
		this.ydzfMonitorPlan = ydzfMonitorPlan;
	}
	public void setYdzfMonitorTempletContentList(
			List<TYdzfMonitorTempletContent> ydzfMonitorTempletContentList) {
		this.ydzfMonitorTempletContentList = ydzfMonitorTempletContentList;
	}
	public void setYdzfMonitorTemplet(TYdzfMonitorTemplet ydzfMonitorTemplet) {
		this.ydzfMonitorTemplet = ydzfMonitorTemplet;
	}
	public void setYdzfMonitorPlanRelEnterpriseVO(
			YdzfMonitorPlanRelEnterpriseVO ydzfMonitorPlanRelEnterpriseVO) {
		this.ydzfMonitorPlanRelEnterpriseVO = ydzfMonitorPlanRelEnterpriseVO;
	}

}
