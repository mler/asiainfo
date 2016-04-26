package com.bdx.rainbow.mapp.model.rsp;


import java.util.Map;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseAuditMB;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseEvidenceMB;
import com.bdx.rainbow.mapp.model.bean.YdzfEnterpriseInfoVOMB;

//案件审核查询
public class YDZF0047Response extends BDXBody {
	private TYdzfInspectCaseAuditMB ydzfInspectCaseAuditMB;
	//other
    private Map<String,String> auditFileidHttpUrlMap;
    private Map<String,String> signFileidHttpUrlMap;
    private YdzfEnterpriseInfoVOMB ydzfEnterpriseInfoVOMB;
    private String punishAdvide;
    private TYdzfInspectCaseEvidenceMB ydzfInspectCaseEvidenceMB;
	public TYdzfInspectCaseAuditMB getYdzfInspectCaseAuditMB() {
		return ydzfInspectCaseAuditMB;
	}
	public Map<String, String> getAuditFileidHttpUrlMap() {
		return auditFileidHttpUrlMap;
	}
	public Map<String, String> getSignFileidHttpUrlMap() {
		return signFileidHttpUrlMap;
	}
	public YdzfEnterpriseInfoVOMB getYdzfEnterpriseInfoVOMB() {
		return ydzfEnterpriseInfoVOMB;
	}
	public String getPunishAdvide() {
		return punishAdvide;
	}
	public TYdzfInspectCaseEvidenceMB getYdzfInspectCaseEvidenceMB() {
		return ydzfInspectCaseEvidenceMB;
	}
	public void setYdzfInspectCaseAuditMB(
			TYdzfInspectCaseAuditMB ydzfInspectCaseAuditMB) {
		this.ydzfInspectCaseAuditMB = ydzfInspectCaseAuditMB;
	}
	public void setAuditFileidHttpUrlMap(Map<String, String> auditFileidHttpUrlMap) {
		this.auditFileidHttpUrlMap = auditFileidHttpUrlMap;
	}
	public void setSignFileidHttpUrlMap(Map<String, String> signFileidHttpUrlMap) {
		this.signFileidHttpUrlMap = signFileidHttpUrlMap;
	}
	public void setYdzfEnterpriseInfoVOMB(
			YdzfEnterpriseInfoVOMB ydzfEnterpriseInfoVOMB) {
		this.ydzfEnterpriseInfoVOMB = ydzfEnterpriseInfoVOMB;
	}
	public void setPunishAdvide(String punishAdvide) {
		this.punishAdvide = punishAdvide;
	}
	public void setYdzfInspectCaseEvidenceMB(
			TYdzfInspectCaseEvidenceMB ydzfInspectCaseEvidenceMB) {
		this.ydzfInspectCaseEvidenceMB = ydzfInspectCaseEvidenceMB;
	}
	
}
