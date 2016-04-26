package com.bdx.rainbow.mapp.model.req;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseExecutePunishMB;


//执行处罚新增修改
public class YDZF0023Request extends BDXBody {
	@NotNull(message="参数不能为空")
	@Valid
	private TYdzfInspectCaseExecutePunishMB ydzfInspectCaseExecutePunishMB;

	public TYdzfInspectCaseExecutePunishMB getYdzfInspectCaseExecutePunishMB() {
		return ydzfInspectCaseExecutePunishMB;
	}

	public void setYdzfInspectCaseExecutePunishMB(
			TYdzfInspectCaseExecutePunishMB ydzfInspectCaseExecutePunishMB) {
		this.ydzfInspectCaseExecutePunishMB = ydzfInspectCaseExecutePunishMB;
	}


	

	
	
}
