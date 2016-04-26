package com.bdx.rainbow.dto.ydzf.vo;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidence;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTemplet;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTempletContent;

public class YdzfInspectCaseEvidenceResultVO {
	
	private TYdzfInspectCaseEvidence ydzfInspectCaseEvidence;
	//other
	private  String manageEnterpriseName;
    private Map<String,String> evidenceFileidHttpUrlMap;
    private Map<String,String> evidenceVoiceIdHttpUrlMap;
    private List<String> sampleNameList;
	private List<TYdzfInspectCaseTempletContent> ydzfInspectCaseTempletContentList;
	private TYdzfInspectCaseTemplet ydzfInspectCaseTemplet;
	public TYdzfInspectCaseEvidence getYdzfInspectCaseEvidence() {
		return ydzfInspectCaseEvidence;
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
	public List<TYdzfInspectCaseTempletContent> getYdzfInspectCaseTempletContentList() {
		return ydzfInspectCaseTempletContentList;
	}
	public TYdzfInspectCaseTemplet getYdzfInspectCaseTemplet() {
		return ydzfInspectCaseTemplet;
	}
	public void setYdzfInspectCaseEvidence(
			TYdzfInspectCaseEvidence ydzfInspectCaseEvidence) {
		this.ydzfInspectCaseEvidence = ydzfInspectCaseEvidence;
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
	public void setYdzfInspectCaseTempletContentList(
			List<TYdzfInspectCaseTempletContent> ydzfInspectCaseTempletContentList) {
		this.ydzfInspectCaseTempletContentList = ydzfInspectCaseTempletContentList;
	}
	public void setYdzfInspectCaseTemplet(
			TYdzfInspectCaseTemplet ydzfInspectCaseTemplet) {
		this.ydzfInspectCaseTemplet = ydzfInspectCaseTemplet;
	}
	public List<String> getSampleNameList() {
		return sampleNameList;
	}
	public void setSampleNameList(List<String> sampleNameList) {
		this.sampleNameList = sampleNameList;
	}

}
