package com.bdx.rainbow.mapp.model.req;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;


//监管历史接口
public class YDZF0013Request extends BDXBody {
	
	@NotNull(message="企业ID不能为空")
    private Integer manageEnterpriseId;
    private Timestamp startDate;
    private Timestamp endDate;
	public Integer getManageEnterpriseId() {
		return manageEnterpriseId;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setManageEnterpriseId(Integer manageEnterpriseId) {
		this.manageEnterpriseId = manageEnterpriseId;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
   
	
	
}
