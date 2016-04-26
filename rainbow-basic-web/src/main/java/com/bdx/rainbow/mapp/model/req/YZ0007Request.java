package com.bdx.rainbow.mapp.model.req;

import org.hibernate.validator.constraints.NotBlank;

import com.bdx.rainbow.mapp.model.BDXBody;

public class YZ0007Request extends BDXBody {
	/**
	 * 操作类型 0：注册 1：修改，如修改密码
	 */
	private String optType;
	
	private String userLoginName;
	
	private String pwd;//密码
	
	private String email;
	
	private String userName;
	
	private String mobilePhone;
	
	private String userType;
	
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
}



