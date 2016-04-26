package com.bdx.rainbow.mapp.model.req;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseAuditMB;


//案件审核新增
public class YDZF0025Request extends BDXBody {
	@NotNull(message="参数不能为空")
	@Valid
	private TYdzfInspectCaseAuditMB ydzfInspectCaseAuditMB;

	public TYdzfInspectCaseAuditMB getYdzfInspectCaseAuditMB() {
		return ydzfInspectCaseAuditMB;
	}

	public void setYdzfInspectCaseAuditMB(
			TYdzfInspectCaseAuditMB ydzfInspectCaseAuditMB) {
		this.ydzfInspectCaseAuditMB = ydzfInspectCaseAuditMB;
	}




	

	
	
}
