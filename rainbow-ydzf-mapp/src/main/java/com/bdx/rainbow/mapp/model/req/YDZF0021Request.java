package com.bdx.rainbow.mapp.model.req;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseEvidenceMB;


//调查取证新增
public class YDZF0021Request extends BDXBody {
	@NotNull(message="参数不能为空")
	@Valid
	private TYdzfInspectCaseEvidenceMB ydzfInspectCaseEvidenceMB;

	public TYdzfInspectCaseEvidenceMB getYdzfInspectCaseEvidenceMB() {
		return ydzfInspectCaseEvidenceMB;
	}

	public void setYdzfInspectCaseEvidenceMB(
			TYdzfInspectCaseEvidenceMB ydzfInspectCaseEvidenceMB) {
		this.ydzfInspectCaseEvidenceMB = ydzfInspectCaseEvidenceMB;
	}


	
	
}
