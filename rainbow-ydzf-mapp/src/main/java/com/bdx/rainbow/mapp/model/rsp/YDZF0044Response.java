package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseExecutePunishMB;
import com.bdx.rainbow.mapp.model.bean.YdzfEnterpriseInfoVOMB;

//执行处罚查询
public class YDZF0044Response extends BDXBody {
	
	private TYdzfInspectCaseExecutePunishMB ydzfInspectCaseExecutePunishMB;
	//other
	private YdzfEnterpriseInfoVOMB ydzfEnterpriseInfoVOMB;
	//{"law_info": [{"id": "1", "title": "标题1"},{"id": "2", "title": "标题2"},]}
	private String lawJson;
	public TYdzfInspectCaseExecutePunishMB getYdzfInspectCaseExecutePunishMB() {
		return ydzfInspectCaseExecutePunishMB;
	}
	public YdzfEnterpriseInfoVOMB getYdzfEnterpriseInfoVOMB() {
		return ydzfEnterpriseInfoVOMB;
	}
	public String getLawJson() {
		return lawJson;
	}
	public void setYdzfInspectCaseExecutePunishMB(
			TYdzfInspectCaseExecutePunishMB ydzfInspectCaseExecutePunishMB) {
		this.ydzfInspectCaseExecutePunishMB = ydzfInspectCaseExecutePunishMB;
	}
	public void setYdzfEnterpriseInfoVOMB(
			YdzfEnterpriseInfoVOMB ydzfEnterpriseInfoVOMB) {
		this.ydzfEnterpriseInfoVOMB = ydzfEnterpriseInfoVOMB;
	}
	public void setLawJson(String lawJson) {
		this.lawJson = lawJson;
	}
	
	
}
