package com.bdx.rainbow.mapp.model.req;

import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.PageInfo;


//查询执法状态纪录列表
public class YDZF0011Request extends BDXBody {
	
	private PageInfo pageInfo;
	
	
	//1.已经保存，9:已提交
	@NotNull(message="提交状态不能为空")
	private String saveStatus;


	public PageInfo getPageInfo() {
		return pageInfo;
	}


	public String getSaveStatus() {
		return saveStatus;
	}


	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}


	public void setSaveStatus(String saveStatus) {
		this.saveStatus = saveStatus;
	}


	





}
