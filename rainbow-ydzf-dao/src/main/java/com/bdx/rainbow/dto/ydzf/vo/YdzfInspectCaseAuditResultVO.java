package com.bdx.rainbow.dto.ydzf.vo;

import java.util.Map;

import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseAudit;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidence;

public class YdzfInspectCaseAuditResultVO {
	
	private TYdzfInspectCaseAudit ydzfInspectCaseAudit;
	//other
    private Map<String,String> auditFileidHttpUrlMap;
    private Map<String,String> signFileidHttpUrlMap;
    private YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO;
    private String punishAdvide;
    private TYdzfInspectCaseEvidence ydzfInspectCaseEvidence;
	public TYdzfInspectCaseAudit getYdzfInspectCaseAudit() {
		return ydzfInspectCaseAudit;
	}
	public Map<String, String> getAuditFileidHttpUrlMap() {
		return auditFileidHttpUrlMap;
	}
	public Map<String, String> getSignFileidHttpUrlMap() {
		return signFileidHttpUrlMap;
	}
	public YdzfEnterpriseInfoVO getYdzfEnterpriseInfoVO() {
		return ydzfEnterpriseInfoVO;
	}
	public String getPunishAdvide() {
		return punishAdvide;
	}
	public TYdzfInspectCaseEvidence getYdzfInspectCaseEvidence() {
		return ydzfInspectCaseEvidence;
	}
	public void setYdzfInspectCaseAudit(TYdzfInspectCaseAudit ydzfInspectCaseAudit) {
		this.ydzfInspectCaseAudit = ydzfInspectCaseAudit;
	}
	public void setAuditFileidHttpUrlMap(Map<String, String> auditFileidHttpUrlMap) {
		this.auditFileidHttpUrlMap = auditFileidHttpUrlMap;
	}
	public void setSignFileidHttpUrlMap(Map<String, String> signFileidHttpUrlMap) {
		this.signFileidHttpUrlMap = signFileidHttpUrlMap;
	}
	public void setYdzfEnterpriseInfoVO(YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO) {
		this.ydzfEnterpriseInfoVO = ydzfEnterpriseInfoVO;
	}
	public void setPunishAdvide(String punishAdvide) {
		this.punishAdvide = punishAdvide;
	}
	public void setYdzfInspectCaseEvidence(
			TYdzfInspectCaseEvidence ydzfInspectCaseEvidence) {
		this.ydzfInspectCaseEvidence = ydzfInspectCaseEvidence;
	}
	
	
}
