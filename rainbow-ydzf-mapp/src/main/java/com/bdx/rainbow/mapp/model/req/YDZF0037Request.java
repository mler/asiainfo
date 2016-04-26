package com.bdx.rainbow.mapp.model.req;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseEvidenceSampleMB;


//样品编辑
public class YDZF0037Request extends BDXBody {
	@NotNull(message="参数不能为空")
	@Valid
	private TYdzfInspectCaseEvidenceSampleMB ydzfInspectCaseEvidenceSampleMB;

	public TYdzfInspectCaseEvidenceSampleMB getYdzfInspectCaseEvidenceSampleMB() {
		return ydzfInspectCaseEvidenceSampleMB;
	}

	public void setYdzfInspectCaseEvidenceSampleMB(
			TYdzfInspectCaseEvidenceSampleMB ydzfInspectCaseEvidenceSampleMB) {
		this.ydzfInspectCaseEvidenceSampleMB = ydzfInspectCaseEvidenceSampleMB;
	}
	

	
}
