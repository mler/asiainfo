package com.bdx.rainbow.mapp.model.req;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseEvidenceSampleMB;


//稽查案件模版查询
public class YDZF0038Request extends BDXBody {
	
	@NotNull(message="稽查案件ID不能为空")
	@Min(message="稽查案件ID非法", value = 1)
	private Integer InspectCaseId;

	public Integer getInspectCaseId() {
		return InspectCaseId;
	}

	public void setInspectCaseId(Integer inspectCaseId) {
		InspectCaseId = inspectCaseId;
	}
	
	

	
}
