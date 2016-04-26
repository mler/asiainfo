package com.bdx.rainbow.mapp.model.req;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseReadyPunishMB;

//拟处罚更新
public class YDZF0043Request extends BDXBody {

	@NotNull(message = "参数不能为空")
	@Valid
	private TYdzfInspectCaseReadyPunishMB ydzfInspectCaseReadyPunishMB;

	public TYdzfInspectCaseReadyPunishMB getYdzfInspectCaseReadyPunishMB() {
		return ydzfInspectCaseReadyPunishMB;
	}

	public void setYdzfInspectCaseReadyPunishMB(
			TYdzfInspectCaseReadyPunishMB ydzfInspectCaseReadyPunishMB) {
		this.ydzfInspectCaseReadyPunishMB = ydzfInspectCaseReadyPunishMB;

	}

}
