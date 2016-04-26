package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;

/**
 * 查询执行状态
 * Created by luli on 16/2/22.
 */
public class YDZF0003Response extends BDXBody {
	
	/**
	 * 服务器上最新版本
	 */
	private String lastVersion;
	
	/**
	 * 服务器上最低版本，若客户端低于这个版本，就强制升级
	 */
	private String compatibleVersion;
	
	/**
	 * 更新地址
	 */
	private String updateURL;
	
	/**
	 * APP简要介绍
	 */
	private String introduction;
	
	public String getLastVersion() {
		return lastVersion;
	}
	public void setLastVersion(String lastVersion) {
		this.lastVersion = lastVersion;
	}
	public String getCompatibleVersion() {
		return compatibleVersion;
	}
	public void setCompatibleVersion(String compatibleVersion) {
		this.compatibleVersion = compatibleVersion;
	}
	public String getUpdateURL() {
		return updateURL;
	}
	public void setUpdateURL(String updateURL) {
		this.updateURL = updateURL;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

}
