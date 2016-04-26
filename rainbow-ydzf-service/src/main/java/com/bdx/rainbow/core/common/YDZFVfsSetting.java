package com.bdx.rainbow.core.common;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(value="vfs" )
public class YDZFVfsSetting  {

	private String serverName;
	
	private String desKey;

	private String fileReadService;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getDesKey() {
		return desKey;
	}

	public void setDesKey(String desKey) {
		this.desKey = desKey;
	}

	public String getFileReadService() {
		return fileReadService;
	}

	public void setFileReadService(String fileReadService) {
		this.fileReadService = fileReadService;
	}
}
