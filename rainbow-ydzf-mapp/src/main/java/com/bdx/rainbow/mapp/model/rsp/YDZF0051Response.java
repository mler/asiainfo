package com.bdx.rainbow.mapp.model.rsp;


import java.util.List;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.YdzfEnterpriseHistoryListInfoVOMB;

//监管历史接口(列表详情)
public class YDZF0051Response extends BDXBody {
	private PageInfo pageInfo;
	private List<YdzfEnterpriseHistoryListInfoVOMB> ydzfEnterpriseHistoryListInfoVOMBList;
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public List<YdzfEnterpriseHistoryListInfoVOMB> getYdzfEnterpriseHistoryListInfoVOMBList() {
		return ydzfEnterpriseHistoryListInfoVOMBList;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	public void setYdzfEnterpriseHistoryListInfoVOMBList(
			List<YdzfEnterpriseHistoryListInfoVOMB> ydzfEnterpriseHistoryListInfoVOMBList) {
		this.ydzfEnterpriseHistoryListInfoVOMBList = ydzfEnterpriseHistoryListInfoVOMBList;
	}

}
