package com.bdx.rainbow.dto.ydzf.vo;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseFinish;

public class YdzfInspectCaseFinishResultVO {
	
	private TYdzfInspectCaseFinish ydzfInspectCaseFinish;
	//other
    private Map<String,String> fileidHttpUrlMap;
    private YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO;
	//{"law_info": [{"id": "1", "title": "标题1"},{"id": "2", "title": "标题2"},]}
	private String lawJson;
    private TYdzfInspectCase ydzfInspectCase;
    private String punishAdvide;
	public TYdzfInspectCaseFinish getYdzfInspectCaseFinish() {
		return ydzfInspectCaseFinish;
	}
	public Map<String, String> getFileidHttpUrlMap() {
		return fileidHttpUrlMap;
	}
	public YdzfEnterpriseInfoVO getYdzfEnterpriseInfoVO() {
		return ydzfEnterpriseInfoVO;
	}
	public String getLawJson() {
		return lawJson;
	}
	public TYdzfInspectCase getYdzfInspectCase() {
		return ydzfInspectCase;
	}
	public String getPunishAdvide() {
		return punishAdvide;
	}
	public void setYdzfInspectCaseFinish(
			TYdzfInspectCaseFinish ydzfInspectCaseFinish) {
		this.ydzfInspectCaseFinish = ydzfInspectCaseFinish;
	}
	public void setFileidHttpUrlMap(Map<String, String> fileidHttpUrlMap) {
		this.fileidHttpUrlMap = fileidHttpUrlMap;
	}
	public void setYdzfEnterpriseInfoVO(YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO) {
		this.ydzfEnterpriseInfoVO = ydzfEnterpriseInfoVO;
	}
	public void setLawJson(String lawJson) {
		this.lawJson = lawJson;
	}
	public void setYdzfInspectCase(TYdzfInspectCase ydzfInspectCase) {
		this.ydzfInspectCase = ydzfInspectCase;
	}
	public void setPunishAdvide(String punishAdvide) {
		this.punishAdvide = punishAdvide;
	}
	
}
