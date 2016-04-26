package com.bdx.rainbow.mapp.model.rsp;


import java.util.Map;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseWitnessesMB;
import com.bdx.rainbow.mapp.model.bean.YdzfEnterpriseInfoVOMB;

//听证查询
public class YDZF0049Response extends BDXBody {
	private TYdzfInspectCaseWitnessesMB ydzfInspectCaseWitnessesMB;
	//other
    private Map<String,String> fileidHttpUrlMap;
    private YdzfEnterpriseInfoVOMB ydzfEnterpriseInfoVOMB;
	public TYdzfInspectCaseWitnessesMB getYdzfInspectCaseWitnessesMB() {
		return ydzfInspectCaseWitnessesMB;
	}
	public Map<String, String> getFileidHttpUrlMap() {
		return fileidHttpUrlMap;
	}
	public YdzfEnterpriseInfoVOMB getYdzfEnterpriseInfoVOMB() {
		return ydzfEnterpriseInfoVOMB;
	}
	public void setYdzfInspectCaseWitnessesMB(
			TYdzfInspectCaseWitnessesMB ydzfInspectCaseWitnessesMB) {
		this.ydzfInspectCaseWitnessesMB = ydzfInspectCaseWitnessesMB;
	}
	public void setFileidHttpUrlMap(Map<String, String> fileidHttpUrlMap) {
		this.fileidHttpUrlMap = fileidHttpUrlMap;
	}
	public void setYdzfEnterpriseInfoVOMB(
			YdzfEnterpriseInfoVOMB ydzfEnterpriseInfoVOMB) {
		this.ydzfEnterpriseInfoVOMB = ydzfEnterpriseInfoVOMB;
	}
	
}
