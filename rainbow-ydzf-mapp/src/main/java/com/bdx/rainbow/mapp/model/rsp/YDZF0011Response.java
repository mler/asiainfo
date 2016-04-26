package com.bdx.rainbow.mapp.model.rsp;


import java.util.List;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.YdzfBackLogListInfoVOMB;

//查询执法状态纪录列表
public class YDZF0011Response extends BDXBody {

  
    private List<YdzfBackLogListInfoVOMB> YdzfBackLogListInfoVOMBList;
	private PageInfo pageInfo;
	public List<YdzfBackLogListInfoVOMB> getYdzfBackLogListInfoVOMBList() {
		return YdzfBackLogListInfoVOMBList;
	}
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setYdzfBackLogListInfoVOMBList(
			List<YdzfBackLogListInfoVOMB> ydzfBackLogListInfoVOMBList) {
		YdzfBackLogListInfoVOMBList = ydzfBackLogListInfoVOMBList;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}



}
