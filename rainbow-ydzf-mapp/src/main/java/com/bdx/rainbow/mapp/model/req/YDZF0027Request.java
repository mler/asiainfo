package com.bdx.rainbow.mapp.model.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.PageInfo;


//稽查案件参考列表接口
public class YDZF0027Request extends BDXBody {
	
	@NotNull(message="稽查案件ID不能为空")
	@Min(message="稽查案件ID非法", value = 1)
	private Integer inspectCaseId;
	private PageInfo pageinfo;
	public Integer getInspectCaseId() {
		return inspectCaseId;
	}
	public PageInfo getPageinfo() {
		return pageinfo;
	}
	public void setInspectCaseId(Integer inspectCaseId) {
		this.inspectCaseId = inspectCaseId;
	}
	public void setPageinfo(PageInfo pageinfo) {
		this.pageinfo = pageinfo;
	}
	


	
	
}
