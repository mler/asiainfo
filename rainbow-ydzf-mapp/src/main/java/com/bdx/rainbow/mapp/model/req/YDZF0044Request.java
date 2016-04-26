package com.bdx.rainbow.mapp.model.req;

import com.bdx.rainbow.mapp.model.BDXBody;


//执行处罚查询
public class YDZF0044Request extends BDXBody {

	private Integer inspectCaseExecutePunishId;
	
	private Integer inspectCaseId;

	public Integer getInspectCaseExecutePunishId() {
		return inspectCaseExecutePunishId;
	}

	public Integer getInspectCaseId() {
		return inspectCaseId;
	}

	public void setInspectCaseExecutePunishId(Integer inspectCaseExecutePunishId) {
		this.inspectCaseExecutePunishId = inspectCaseExecutePunishId;
	}

	public void setInspectCaseId(Integer inspectCaseId) {
		this.inspectCaseId = inspectCaseId;
	}



	

	
	
}
