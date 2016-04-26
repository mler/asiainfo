package com.bdx.rainbow.dto.ydzf.vo;


public class YdzfEnterpriseHistoryCountVO {
	
	private YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO;
//	{"monitor_info": [{"checkType": "1", "total": "3","pass_num":"1","fail_num":"2"},{"checkType": "1", "total": "3","pass_num":"1","fail_num":"2"}],
//	"inspect_total": "5",
//	"inspect_info": [{"punishAdvice":"1","num":"2"},{"punishAdvice":"1","num":"2"}]}
	private String checkJsonInfo;
	public YdzfEnterpriseInfoVO getYdzfEnterpriseInfoVO() {
		return ydzfEnterpriseInfoVO;
	}
	public String getCheckJsonInfo() {
		return checkJsonInfo;
	}
	public void setYdzfEnterpriseInfoVO(YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO) {
		this.ydzfEnterpriseInfoVO = ydzfEnterpriseInfoVO;
	}
	public void setCheckJsonInfo(String checkJsonInfo) {
		this.checkJsonInfo = checkJsonInfo;
	}
	
		
	
	

}
