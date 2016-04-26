package com.bdx.rainbow.mapp.model.rsp;


import java.util.List;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.YdzfMonitorPlanListResultVOMB;


public class YDZF0005Response extends BDXBody {

  
    private List<YdzfMonitorPlanListResultVOMB> YdzfMonitorPlanListResultVOMBList;
	private PageInfo pageInfo;
	public List<YdzfMonitorPlanListResultVOMB> getYdzfMonitorPlanListResultVOMBList() {
		return YdzfMonitorPlanListResultVOMBList;
	}
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setYdzfMonitorPlanListResultVOMBList(
			List<YdzfMonitorPlanListResultVOMB> ydzfMonitorPlanListResultVOMBList) {
		YdzfMonitorPlanListResultVOMBList = ydzfMonitorPlanListResultVOMBList;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

   

}
