package com.bdx.rainbow.mapp.model.req;

import org.hibernate.validator.constraints.NotBlank;

import com.bdx.rainbow.mapp.model.BDXBody;

/**
 * 登陆接口
 * @author zhengwenjuan
 *
 */
public class YZ0006Request extends BDXBody {
	
	@NotBlank(message="登录账户不能为空")
	private String userLoginName;//登陆帐号
	
	/**
	 * MD5后
	 */
	@NotBlank(message="登录密码不能为空")	
	private String pwd;//密码
	
	private String svc;//手机号
	
	private String imsi;//imsi号
	
	private String imei;//imei号
	
	private String version;//客户端版本
	
	private String brand;//手机品牌
	
	private String model;//手机型号
	
	private String os;//手机操作系统
	/**
	 *	key=value
	 *	aPad=安卓Pad
	 *	aPhone=安卓Phone
	 *	iPad=iPad
	 *	iPhone=iPhone
	 *	web=web页面
	 */
	private String deviceType;
	
	/**
	 * 从哪个系统登录的 系统首字母
	 * 如 kx=快销
	 */
	private String logonSys;
	

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

	public String getSvc() {
		return svc;
	}

	public void setSvc(String svc) {
		this.svc = svc;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getLogonSys() {
		return logonSys;
	}

	public void setLogonSys(String logonSys) {
		this.logonSys = logonSys;
	}
	
}



