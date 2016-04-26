package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;

import java.util.List;

//员工信息
public class SK0002Response extends BDXBody {

	private List<Empl> employees; // 该企业员工列表
	
	
	public List<Empl> getEmployees() {
		return employees;
	}


	public void setEmployees(List<Empl> employees) {
		this.employees = employees;
	}


	public static class Empl{
		
		public String getPicturePath() {
			return picturePath;
		}
		public void setPicturePath(String picturePath) {
			this.picturePath = picturePath;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getJobTitle() {
			return jobTitle;
		}
		public void setJobTitle(String jobTitle) {
			this.jobTitle = jobTitle;
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}

		private String picturePath;//头像
		private String name;//姓名
		private String jobTitle;//职称
		private String id;//员工id
		
		
		
	}
}
