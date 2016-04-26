package com.bdx.rainbow.mapp.model.rsp;


import java.util.List;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.YdzfInspectCaseListResultVOMB;


public class YDZF0019Response extends BDXBody {
	private List<YdzfInspectCaseListResultVOMB> YdzfInspectCaseListResultVOMBList;
	private PageInfo pageInfo;
	public List<YdzfInspectCaseListResultVOMB> getYdzfInspectCaseListResultVOMBList() {
		return YdzfInspectCaseListResultVOMBList;
	}
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setYdzfInspectCaseListResultVOMBList(
			List<YdzfInspectCaseListResultVOMB> ydzfInspectCaseListResultVOMBList) {
		YdzfInspectCaseListResultVOMBList = ydzfInspectCaseListResultVOMBList;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}


}
