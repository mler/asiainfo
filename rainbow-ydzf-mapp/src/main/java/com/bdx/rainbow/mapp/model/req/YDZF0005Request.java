package com.bdx.rainbow.mapp.model.req;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.TYdzfMonitorPlanMB;


/**
 * 查询执行状态
 * Created by luli on 16/2/22.
 */
public class YDZF0005Request extends BDXBody {
	
	private PageInfo pageInfo;
	
	private TYdzfMonitorPlanMB ydzfMonitorPlanMB;


	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public TYdzfMonitorPlanMB getYdzfMonitorPlanMB() {
		return ydzfMonitorPlanMB;
	}

	public void setYdzfMonitorPlanMB(TYdzfMonitorPlanMB ydzfMonitorPlanMB) {
		this.ydzfMonitorPlanMB = ydzfMonitorPlanMB;
	}






}
