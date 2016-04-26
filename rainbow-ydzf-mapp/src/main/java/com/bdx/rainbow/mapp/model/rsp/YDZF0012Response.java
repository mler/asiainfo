package com.bdx.rainbow.mapp.model.rsp;


import java.util.List;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfMonitorPlanMB;
import com.bdx.rainbow.mapp.model.bean.TYdzfMonitorTempletContentMB;
import com.bdx.rainbow.mapp.model.bean.TYdzfMonitorTempletMB;
import com.bdx.rainbow.mapp.model.bean.YdzfMonitorPlanRelEnterpriseVOMB;


public class YDZF0012Response extends BDXBody {
	private TYdzfMonitorPlanMB ydzfMonitorPlanMB;
	private List<TYdzfMonitorTempletContentMB> ydzfMonitorTempletContentMBList;
	private TYdzfMonitorTempletMB ydzfMonitorTempletMB;
	private YdzfMonitorPlanRelEnterpriseVOMB ydzfMonitorPlanRelEnterpriseVOMB;
	public TYdzfMonitorPlanMB getYdzfMonitorPlanMB() {
		return ydzfMonitorPlanMB;
	}
	public List<TYdzfMonitorTempletContentMB> getYdzfMonitorTempletContentMBList() {
		return ydzfMonitorTempletContentMBList;
	}
	public TYdzfMonitorTempletMB getYdzfMonitorTempletMB() {
		return ydzfMonitorTempletMB;
	}
	public YdzfMonitorPlanRelEnterpriseVOMB getYdzfMonitorPlanRelEnterpriseVOMB() {
		return ydzfMonitorPlanRelEnterpriseVOMB;
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
	public void setYdzfMonitorPlanRelEnterpriseVOMB(
			YdzfMonitorPlanRelEnterpriseVOMB ydzfMonitorPlanRelEnterpriseVOMB) {
		this.ydzfMonitorPlanRelEnterpriseVOMB = ydzfMonitorPlanRelEnterpriseVOMB;
	}
	
	
	
}
