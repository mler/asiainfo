package com.bdx.rainbow.mapp.model.rsp;


import java.util.Map;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseEvidenceSampleMB;


public class YDZF0034Response extends BDXBody {
	
	private TYdzfInspectCaseEvidenceSampleMB ydzfInspectCaseEvidenceSampleMB;
	//other
    private Map<String,String> sampleFileidHttpUrlMap;
	public TYdzfInspectCaseEvidenceSampleMB getYdzfInspectCaseEvidenceSampleMB() {
		return ydzfInspectCaseEvidenceSampleMB;
	}
	public Map<String, String> getSampleFileidHttpUrlMap() {
		return sampleFileidHttpUrlMap;
	}
	public void setYdzfInspectCaseEvidenceSampleMB(
			TYdzfInspectCaseEvidenceSampleMB ydzfInspectCaseEvidenceSampleMB) {
		this.ydzfInspectCaseEvidenceSampleMB = ydzfInspectCaseEvidenceSampleMB;
	}
	public void setSampleFileidHttpUrlMap(Map<String, String> sampleFileidHttpUrlMap) {
		this.sampleFileidHttpUrlMap = sampleFileidHttpUrlMap;
	}
	


	
	
}
