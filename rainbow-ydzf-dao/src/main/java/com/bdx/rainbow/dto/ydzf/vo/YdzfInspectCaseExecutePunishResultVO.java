package com.bdx.rainbow.dto.ydzf.vo;

import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseExecutePunish;

public class YdzfInspectCaseExecutePunishResultVO {
	private TYdzfInspectCaseExecutePunish ydzfInspectCaseExecutePunish;
	//other
	private YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO;
	private String punishAdvide;
	//{"law_info": [{"id": "1", "title": "标题1"},{"id": "2", "title": "标题2"},]}
	private String lawJson;
	public TYdzfInspectCaseExecutePunish getYdzfInspectCaseExecutePunish() {
		return ydzfInspectCaseExecutePunish;
	}
	public YdzfEnterpriseInfoVO getYdzfEnterpriseInfoVO() {
		return ydzfEnterpriseInfoVO;
	}
	public String getPunishAdvide() {
		return punishAdvide;
	}
	public String getLawJson() {
		return lawJson;
	}
	public void setYdzfInspectCaseExecutePunish(
			TYdzfInspectCaseExecutePunish ydzfInspectCaseExecutePunish) {
		this.ydzfInspectCaseExecutePunish = ydzfInspectCaseExecutePunish;
	}
	public void setYdzfEnterpriseInfoVO(YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO) {
		this.ydzfEnterpriseInfoVO = ydzfEnterpriseInfoVO;
	}
	public void setPunishAdvide(String punishAdvide) {
		this.punishAdvide = punishAdvide;
	}
	public void setLawJson(String lawJson) {
		this.lawJson = lawJson;
	}

	

}
