package com.bdx.rainbow.mapp.model.req;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseFinishMB;


//案件结案新增
public class YDZF0024Request extends BDXBody {
	@NotNull(message="参数不能为空")
	@Valid
	private TYdzfInspectCaseFinishMB ydzfInspectCaseFinishMB;

	public TYdzfInspectCaseFinishMB getYdzfInspectCaseFinishMB() {
		return ydzfInspectCaseFinishMB;
	}

	public void setYdzfInspectCaseFinishMB(
			TYdzfInspectCaseFinishMB ydzfInspectCaseFinishMB) {
		this.ydzfInspectCaseFinishMB = ydzfInspectCaseFinishMB;
	}




	

	
	
}
