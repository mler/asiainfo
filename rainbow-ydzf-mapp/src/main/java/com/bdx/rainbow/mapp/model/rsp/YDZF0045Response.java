package com.bdx.rainbow.mapp.model.rsp;


import java.util.Map;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseFinishMB;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseMB;
import com.bdx.rainbow.mapp.model.bean.YdzfEnterpriseInfoVOMB;

//结案查询
public class YDZF0045Response extends BDXBody {
	

	private TYdzfInspectCaseFinishMB ydzfInspectCaseFinishMB;
	//other
    private Map<String,String> fileidHttpUrlMap;
    private YdzfEnterpriseInfoVOMB ydzfEnterpriseInfoVOMB;
	//{"law_info": [{"id": "1", "title": "标题1"},{"id": "2", "title": "标题2"},]}
	private String lawJson;
    private TYdzfInspectCaseMB ydzfInspectCaseMB;
    private String punishAdvide;
	public TYdzfInspectCaseFinishMB getYdzfInspectCaseFinishMB() {
		return ydzfInspectCaseFinishMB;
	}
	public Map<String, String> getFileidHttpUrlMap() {
		return fileidHttpUrlMap;
	}
	public YdzfEnterpriseInfoVOMB getYdzfEnterpriseInfoVOMB() {
		return ydzfEnterpriseInfoVOMB;
	}
	public String getLawJson() {
		return lawJson;
	}
	public TYdzfInspectCaseMB getYdzfInspectCaseMB() {
		return ydzfInspectCaseMB;
	}
	public String getPunishAdvide() {
		return punishAdvide;
	}
	public void setYdzfInspectCaseFinishMB(
			TYdzfInspectCaseFinishMB ydzfInspectCaseFinishMB) {
		this.ydzfInspectCaseFinishMB = ydzfInspectCaseFinishMB;
	}
	public void setFileidHttpUrlMap(Map<String, String> fileidHttpUrlMap) {
		this.fileidHttpUrlMap = fileidHttpUrlMap;
	}
	public void setYdzfEnterpriseInfoVOMB(
			YdzfEnterpriseInfoVOMB ydzfEnterpriseInfoVOMB) {
		this.ydzfEnterpriseInfoVOMB = ydzfEnterpriseInfoVOMB;
	}
	public void setLawJson(String lawJson) {
		this.lawJson = lawJson;
	}
	public void setYdzfInspectCaseMB(TYdzfInspectCaseMB ydzfInspectCaseMB) {
		this.ydzfInspectCaseMB = ydzfInspectCaseMB;
	}
	public void setPunishAdvide(String punishAdvide) {
		this.punishAdvide = punishAdvide;
	}
	
	
}
