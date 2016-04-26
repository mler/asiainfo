package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseReadyPunishMB;

//拟处罚查询
public class YDZF0042Response extends BDXBody {
	
	
	private TYdzfInspectCaseReadyPunishMB ydzfInspectCaseReadyPunishMB;
	//other
	//{"law_info": [{"id": "1", "title": "标题1"},{"id": "2", "title": "标题2"},]}
	private String lawJson;
	public TYdzfInspectCaseReadyPunishMB getYdzfInspectCaseReadyPunishMB() {
		return ydzfInspectCaseReadyPunishMB;
	}
	public String getLawJson() {
		return lawJson;
	}
	public void setYdzfInspectCaseReadyPunishMB(
			TYdzfInspectCaseReadyPunishMB ydzfInspectCaseReadyPunishMB) {
		this.ydzfInspectCaseReadyPunishMB = ydzfInspectCaseReadyPunishMB;
	}
	public void setLawJson(String lawJson) {
		this.lawJson = lawJson;
	}

	
	
}
