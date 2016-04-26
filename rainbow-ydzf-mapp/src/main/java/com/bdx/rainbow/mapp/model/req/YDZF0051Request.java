package com.bdx.rainbow.mapp.model.req;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.PageInfo;


//监管历史接口(列表详情)
public class YDZF0051Request extends BDXBody {
	
	@NotNull(message="企业ID不能为空")
    private Integer manageEnterpriseId;
    //日常检查类型
    private String checkPlanType;
    //查询稽查("monitor","inspect","all")
    private String queryType;
    //备用
    private int deptId;
    private Timestamp startDate;
    private Timestamp endDate;
    private PageInfo pageInfo;
	public Integer getManageEnterpriseId() {
		return manageEnterpriseId;
	}
	public String getCheckPlanType() {
		return checkPlanType;
	}
	public String getQueryType() {
		return queryType;
	}
	public int getDeptId() {
		return deptId;
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
	public void setCheckPlanType(String checkPlanType) {
		this.checkPlanType = checkPlanType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
	
	
}
