package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;

import java.util.List;

//日常检查列表
public class SK0006Response extends BDXBody {
	private List<Check> CheckList;//检查列表
	public List<Check> getCheckList() {
		return CheckList;
	}
	public void setCheckList(List<Check> CheckList) {
		this.CheckList = CheckList;
	}
	public static class Check{
		private String id;//检查id
		private String name;//检查项目名称
		private String orgId;//执法单位
		private String result;//检查结果
		private String createTime;//检查日期
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getOrgId() {
			return orgId;
		}
		public void setOrgId(String orgId) {
			this.orgId = orgId;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		
	}
}
