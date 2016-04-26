package com.bdx.rainbow.mapp.model.req;

import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.PageInfo;


//稽查案件历史详情接口 
public class YDZF0029Request extends BDXBody {
	
	@NotNull(message="稽查案件ID不能为空")
	private String inspectCaseIds;
	private PageInfo pageinfo;
	public String getInspectCaseIds() {
		return inspectCaseIds;
	}
	public PageInfo getPageinfo() {
		return pageinfo;
	}
	public void setInspectCaseIds(String inspectCaseIds) {
		this.inspectCaseIds = inspectCaseIds;
	}
	public void setPageinfo(PageInfo pageinfo) {
		this.pageinfo = pageinfo;
	}



	
	
}
