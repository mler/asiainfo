package com.bdx.rainbow.mapp.model.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;


//稽查案件列表查询
public class YDZF0020Request extends BDXBody {
	@NotNull(message="稽查案件ID不能为空")
	@Min(message="稽查案件ID非法", value = 1)
    private Integer inspectCaseId;

	public Integer getInspectCaseId() {
		return inspectCaseId;
	}

	public void setInspectCaseId(Integer inspectCaseId) {
		this.inspectCaseId = inspectCaseId;
	}


	
	
}
