package com.bdx.rainbow.mapp.model.rsp;


import java.util.List;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.YdzfPunishAdviceFinishHistoryVOMB;

//稽查案件参考列表接口
public class YDZF0029Response extends BDXBody {
	private PageInfo pageInfo;
	private List<YdzfPunishAdviceFinishHistoryVOMB> ydzfPunishAdviceFinishHistoryVOMBList;
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public List<YdzfPunishAdviceFinishHistoryVOMB> getYdzfPunishAdviceFinishHistoryVOMBList() {
		return ydzfPunishAdviceFinishHistoryVOMBList;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	public void setYdzfPunishAdviceFinishHistoryVOMBList(
			List<YdzfPunishAdviceFinishHistoryVOMB> ydzfPunishAdviceFinishHistoryVOMBList) {
		this.ydzfPunishAdviceFinishHistoryVOMBList = ydzfPunishAdviceFinishHistoryVOMBList;
	}
	
	
}
