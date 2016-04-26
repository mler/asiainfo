package com.bdx.rainbow.dto.ydzf.vo;

import java.util.Map;

import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseWitnesses;

public class YdzfInspectCaseWitnessesResultVO {
	
	private TYdzfInspectCaseWitnesses ydzfInspectCaseWitnesses;
	//other
    private Map<String,String> fileidHttpUrlMap;
    private YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO;
	public TYdzfInspectCaseWitnesses getYdzfInspectCaseWitnesses() {
		return ydzfInspectCaseWitnesses;
	}
	public Map<String, String> getFileidHttpUrlMap() {
		return fileidHttpUrlMap;
	}
	public YdzfEnterpriseInfoVO getYdzfEnterpriseInfoVO() {
		return ydzfEnterpriseInfoVO;
	}
	public void setYdzfInspectCaseWitnesses(
			TYdzfInspectCaseWitnesses ydzfInspectCaseWitnesses) {
		this.ydzfInspectCaseWitnesses = ydzfInspectCaseWitnesses;
	}
	public void setFileidHttpUrlMap(Map<String, String> fileidHttpUrlMap) {
		this.fileidHttpUrlMap = fileidHttpUrlMap;
	}
	public void setYdzfEnterpriseInfoVO(YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO) {
		this.ydzfEnterpriseInfoVO = ydzfEnterpriseInfoVO;
	}
	
	
}
