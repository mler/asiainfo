package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;

import java.util.List;

/*
 * 日常检查详情
 */
public class SK0007Response extends BDXBody {

	private String name;//检查项目名称
	private String orgId;//执法单位
	private String result;//检查结果
	private String checkStartTime;//开始时间
	private String checkEndTime;//结束时间
	private List<Content> contents;//检查内容列表 
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
	public String getCheckStartTime() {
		return checkStartTime;
	}
	public void setCheckStartTime(String checkStartTime) {
		this.checkStartTime = checkStartTime;
	}
	public String getCheckEndTime() {
		return checkEndTime;
	}
	public void setCheckEndTime(String checkEndTime) {
		this.checkEndTime = checkEndTime;
	}
	public List<Content> getContents() {
		return contents;
	}
	public void setContents(List<Content> contents) {
		this.contents = contents;
	}
	
	public static class Content{
		private String	itemName;//检查内容
		private String  manner;//检查方式
		private String  isLacuna;//合理性缺项
		private String  significance;//重要性
		private String  score;//分值
		private String  result;//检查结果
		private String  remark;//说明
		public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
		public String getManner() {
			return manner;
		}
		public void setManner(String manner) {
			this.manner = manner;
		}
		public String getIsLacuna() {
			return isLacuna;
		}
		public void setIsLacuna(String isLacuna) {
			this.isLacuna = isLacuna;
		}
		public String getSignificance() {
			return significance;
		}
		public void setSignificance(String significance) {
			this.significance = significance;
		}
		public String getScore() {
			return score;
		}
		public void setScore(String score) {
			this.score = score;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		
	}
}
