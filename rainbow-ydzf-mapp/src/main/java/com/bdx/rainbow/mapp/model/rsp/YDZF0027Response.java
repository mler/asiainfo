package com.bdx.rainbow.mapp.model.rsp;


import java.util.List;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.YdzfPunishAdviceHistoryVOMB;

//稽查案件参考列表接口
public class YDZF0027Response extends BDXBody {
	private PageInfo pageInfo;
	private List<YdzfPunishAdviceHistoryVOMB> ydzfPunishAdviceHistoryVOMBList;
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public List<YdzfPunishAdviceHistoryVOMB> getYdzfPunishAdviceHistoryVOMBList() {
		return ydzfPunishAdviceHistoryVOMBList;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	public void setYdzfPunishAdviceHistoryVOMBList(
			List<YdzfPunishAdviceHistoryVOMB> ydzfPunishAdviceHistoryVOMBList) {
		this.ydzfPunishAdviceHistoryVOMBList = ydzfPunishAdviceHistoryVOMBList;
	}
	
	
}
