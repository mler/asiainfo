package com.bdx.rainbow.dto.ydzf.vo;

import java.util.Map;

import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidenceSample;

public class YdzfInspectCaseEvidenceSampleResultInfoVO {
	
	private TYdzfInspectCaseEvidenceSample ydzfInspectCaseEvidenceSample;
	//other
    private Map<String,String> sampleFileidHttpUrlMap;
	public TYdzfInspectCaseEvidenceSample getYdzfInspectCaseEvidenceSample() {
		return ydzfInspectCaseEvidenceSample;
	}
	public Map<String, String> getSampleFileidHttpUrlMap() {
		return sampleFileidHttpUrlMap;
	}
	public void setYdzfInspectCaseEvidenceSample(
			TYdzfInspectCaseEvidenceSample ydzfInspectCaseEvidenceSample) {
		this.ydzfInspectCaseEvidenceSample = ydzfInspectCaseEvidenceSample;
	}
	public void setSampleFileidHttpUrlMap(Map<String, String> sampleFileidHttpUrlMap) {
		this.sampleFileidHttpUrlMap = sampleFileidHttpUrlMap;
	}
	

}
