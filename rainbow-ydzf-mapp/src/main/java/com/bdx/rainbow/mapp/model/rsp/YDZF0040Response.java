package com.bdx.rainbow.mapp.model.rsp;


import java.util.List;
import java.util.Map;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseEvidenceMB;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseTempletContentMB;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseTempletMB;

//调查取证查询
public class YDZF0040Response extends BDXBody {
	
	private TYdzfInspectCaseEvidenceMB ydzfInspectCaseEvidenceMB;
	//other
	private  String manageEnterpriseName;
    private Map<String,String> evidenceFileidHttpUrlMap;
    private Map<String,String> evidenceVoiceIdHttpUrlMap;
    private List<String> sampleNameList;
	private List<TYdzfInspectCaseTempletContentMB> ydzfInspectCaseTempletContentMBList;
	private TYdzfInspectCaseTempletMB ydzfInspectCaseTempletMB;
	public TYdzfInspectCaseEvidenceMB getYdzfInspectCaseEvidenceMB() {
		return ydzfInspectCaseEvidenceMB;
	}
	public String getManageEnterpriseName() {
		return manageEnterpriseName;
	}
	public Map<String, String> getEvidenceFileidHttpUrlMap() {
		return evidenceFileidHttpUrlMap;
	}
	public Map<String, String> getEvidenceVoiceIdHttpUrlMap() {
		return evidenceVoiceIdHttpUrlMap;
	}
	public List<String> getSampleNameList() {
		return sampleNameList;
	}
	public List<TYdzfInspectCaseTempletContentMB> getYdzfInspectCaseTempletContentMBList() {
		return ydzfInspectCaseTempletContentMBList;
	}
	public TYdzfInspectCaseTempletMB getYdzfInspectCaseTempletMB() {
		return ydzfInspectCaseTempletMB;
	}
	public void setYdzfInspectCaseEvidenceMB(
			TYdzfInspectCaseEvidenceMB ydzfInspectCaseEvidenceMB) {
		this.ydzfInspectCaseEvidenceMB = ydzfInspectCaseEvidenceMB;
	}
	public void setManageEnterpriseName(String manageEnterpriseName) {
		this.manageEnterpriseName = manageEnterpriseName;
	}
	public void setEvidenceFileidHttpUrlMap(
			Map<String, String> evidenceFileidHttpUrlMap) {
		this.evidenceFileidHttpUrlMap = evidenceFileidHttpUrlMap;
	}
	public void setEvidenceVoiceIdHttpUrlMap(
			Map<String, String> evidenceVoiceIdHttpUrlMap) {
		this.evidenceVoiceIdHttpUrlMap = evidenceVoiceIdHttpUrlMap;
	}
	public void setSampleNameList(List<String> sampleNameList) {
		this.sampleNameList = sampleNameList;
	}
	public void setYdzfInspectCaseTempletContentMBList(
			List<TYdzfInspectCaseTempletContentMB> ydzfInspectCaseTempletContentMBList) {
		this.ydzfInspectCaseTempletContentMBList = ydzfInspectCaseTempletContentMBList;
	}
	public void setYdzfInspectCaseTempletMB(
			TYdzfInspectCaseTempletMB ydzfInspectCaseTempletMB) {
		this.ydzfInspectCaseTempletMB = ydzfInspectCaseTempletMB;
	}

	
	
}
