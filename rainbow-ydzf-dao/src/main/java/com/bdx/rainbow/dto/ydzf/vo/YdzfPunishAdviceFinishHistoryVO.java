package com.bdx.rainbow.dto.ydzf.vo;

import java.sql.Timestamp;


/**
 * 历史类似案件结案信息列表VO
 * @author fox
 *
 */
public class YdzfPunishAdviceFinishHistoryVO {
    private Integer inspectCaseFinishId;
    private Integer inspectCaseId;
    private String inspectCaseName;
    private Integer inspectCaseEnterpriseId;
    private String inspectCaseEnterpriseName;
    private String evidenceResult;
    private String punishAdvide; 
    private Timestamp finishDate;
	public Integer getInspectCaseFinishId() {
		return inspectCaseFinishId;
	}
	public Integer getInspectCaseId() {
		return inspectCaseId;
	}
	public String getInspectCaseName() {
		return inspectCaseName;
	}
	public Integer getInspectCaseEnterpriseId() {
		return inspectCaseEnterpriseId;
	}
	public String getInspectCaseEnterpriseName() {
		return inspectCaseEnterpriseName;
	}
	public String getEvidenceResult() {
		return evidenceResult;
	}
	public String getPunishAdvide() {
		return punishAdvide;
	}
	public Timestamp getFinishDate() {
		return finishDate;
	}
	public void setInspectCaseFinishId(Integer inspectCaseFinishId) {
		this.inspectCaseFinishId = inspectCaseFinishId;
	}
	public void setInspectCaseId(Integer inspectCaseId) {
		this.inspectCaseId = inspectCaseId;
	}
	public void setInspectCaseName(String inspectCaseName) {
		this.inspectCaseName = inspectCaseName;
	}
	public void setInspectCaseEnterpriseId(Integer inspectCaseEnterpriseId) {
		this.inspectCaseEnterpriseId = inspectCaseEnterpriseId;
	}
	public void setInspectCaseEnterpriseName(String inspectCaseEnterpriseName) {
		this.inspectCaseEnterpriseName = inspectCaseEnterpriseName;
	}
	public void setEvidenceResult(String evidenceResult) {
		this.evidenceResult = evidenceResult;
	}
	public void setPunishAdvide(String punishAdvide) {
		this.punishAdvide = punishAdvide;
	}
	public void setFinishDate(Timestamp finishDate) {
		this.finishDate = finishDate;
	}
}
