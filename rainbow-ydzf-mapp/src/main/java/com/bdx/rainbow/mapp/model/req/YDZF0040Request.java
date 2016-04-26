package com.bdx.rainbow.mapp.model.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;


//调查取证查询
public class YDZF0040Request extends BDXBody {
	@NotNull(message="调查取证ID不能为空")
	@Min(message="调查取证ID非法", value = 1)
    private Integer inspectCaseEvidenceId;

	public Integer getInspectCaseEvidenceId() {
		return inspectCaseEvidenceId;
	}

	public void setInspectCaseEvidenceId(Integer inspectCaseEvidenceId) {
		this.inspectCaseEvidenceId = inspectCaseEvidenceId;
	}



	
	
}
