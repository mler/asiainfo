package com.bdx.rainbow.mapp.model.bean;


/**
 * 历史类似处罚方案参考列表VO
 * @author fox
 *
 */
public class YdzfPunishAdviceHistoryVOMB {
	
	private String inspectCaseIds;
	private String punishAdvice;
	private String lawIds;
	//{"law_info": [{"id": "1", "title": "标题1"},{"id": "2", "title": "标题2"},]}
	private String lawJson;
	public String getInspectCaseIds() {
		return inspectCaseIds;
	}
	public String getPunishAdvice() {
		return punishAdvice;
	}
	public String getLawIds() {
		return lawIds;
	}
	public String getLawJson() {
		return lawJson;
	}
	public void setInspectCaseIds(String inspectCaseIds) {
		this.inspectCaseIds = inspectCaseIds;
	}
	public void setPunishAdvice(String punishAdvice) {
		this.punishAdvice = punishAdvice;
	}
	public void setLawIds(String lawIds) {
		this.lawIds = lawIds;
	}
	public void setLawJson(String lawJson) {
		this.lawJson = lawJson;
	}



}
