package com.bdx.rainbow.mapp.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value="mapp")
public class MappSettings extends AbstractSettings {

	
//	#mapp配置
//	action.package=com.bdx.syj.mapp.action
//	VALID_IP=127.0.0.1,localhost,192.168.1.101,10.10.10.78,42.121.57.229,10.10.19.4,211.140.18.100
//	action.cryptPermission=false
//	encrypt.des.default.key=AILK_MAPP_DESKEY
//	APP_DOWNLOAD = 10.10.19.102
//	APP_REQUIRED_VERSION = 1.0
//	APP_VERSION = 1.1
	
	private String actionPackage;
	
	private String validIp;
	
	private boolean encryptPermission;
	
	private String defaultKey;
	
	public String getActionPackage() {
		return actionPackage;
	}
	public void setActionPackage(String actionPackage) {
		this.actionPackage = actionPackage;
	}
	public String getValidIp() {
		return validIp;
	}
	public void setValidIp(String validIp) {
		this.validIp = validIp;
	}
	public boolean isEncryptPermission() {
		return encryptPermission;
	}
	public void setEncryptPermission(boolean encryptPermission) {
		this.encryptPermission = encryptPermission;
	}
	public String getDefaultKey() {
		return defaultKey;
	}
	public void setDefaultKey(String defaultKey) {
		this.defaultKey = defaultKey;
	}

}
