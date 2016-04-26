package com.bdx.rainbow.mapp.model.req;



import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;


//样品查询
public class YDZF0034Request extends BDXBody {
	@NotNull(message="样品ID不能为空")
	@Min(message="样品ID非法", value = 1)
	private Integer inspectCaseEvidenceSampleId;

	public Integer getInspectCaseEvidenceSampleId() {
		return inspectCaseEvidenceSampleId;
	}

	public void setInspectCaseEvidenceSampleId(Integer inspectCaseEvidenceSampleId) {
		this.inspectCaseEvidenceSampleId = inspectCaseEvidenceSampleId;
	}

	
}
