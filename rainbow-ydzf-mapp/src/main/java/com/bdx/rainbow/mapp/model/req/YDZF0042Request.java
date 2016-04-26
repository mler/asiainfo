package com.bdx.rainbow.mapp.model.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;


//拟处罚查询
public class YDZF0042Request extends BDXBody {
	@NotNull(message="拟处罚ID不能为空")
	@Min(message="拟处罚ID非法", value = 1)
	private Integer inspectCaseReadyPunishId;

	public Integer getInspectCaseReadyPunishId() {
		return inspectCaseReadyPunishId;
	}

	public void setInspectCaseReadyPunishId(Integer inspectCaseReadyPunishId) {
		this.inspectCaseReadyPunishId = inspectCaseReadyPunishId;
	}

	

	
	
}
