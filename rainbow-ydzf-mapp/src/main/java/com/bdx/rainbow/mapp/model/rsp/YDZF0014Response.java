package com.bdx.rainbow.mapp.model.rsp;


import java.util.List;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.DubboLawMB;

//法律列表查询
public class YDZF0014Response extends BDXBody {
	private PageInfo pageInfo;
	private List<DubboLawMB> dubboLawMBList;
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public List<DubboLawMB> getDubboLawMBList() {
		return dubboLawMBList;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	public void setDubboLawMBList(List<DubboLawMB> dubboLawMBList) {
		this.dubboLawMBList = dubboLawMBList;
	}
}
