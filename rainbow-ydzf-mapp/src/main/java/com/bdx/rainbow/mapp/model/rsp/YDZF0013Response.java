package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.YdzfEnterpriseInfoVOMB;

//监管历史接口
public class YDZF0013Response extends BDXBody {
	private YdzfEnterpriseInfoVOMB ydzfEnterpriseInfoVOMB;
//	{"monitor_info": [{"checkType": "1", "total": "3","pass_num":"1","fail_num":"2"},{"checkType": "1", "total": "3","pass_num":"1","fail_num":"2"}],
//	"inspect_total": "5",
//	"inspect_info": [{"punishAdvice":"1","num":"2"},{"punishAdvice":"1","num":"2"}]}
	private String checkJsonInfo;
	public YdzfEnterpriseInfoVOMB getYdzfEnterpriseInfoVOMB() {
		return ydzfEnterpriseInfoVOMB;
	}
	public String getCheckJsonInfo() {
		return checkJsonInfo;
	}
	public void setYdzfEnterpriseInfoVOMB(
			YdzfEnterpriseInfoVOMB ydzfEnterpriseInfoVOMB) {
		this.ydzfEnterpriseInfoVOMB = ydzfEnterpriseInfoVOMB;
	}
	public void setCheckJsonInfo(String checkJsonInfo) {
		this.checkJsonInfo = checkJsonInfo;
	}
	
}
