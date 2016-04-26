package com.bdx.rainbow.mapp.model.req;

import com.bdx.rainbow.mapp.model.BDXBody;


/**
 * 查询执行状态
 * Created by luli on 16/2/22.
 */
public class YDZF0003Request extends BDXBody {
	/**
	 * 客户端版本
	 */
	private String clientVersion;
	
	/**
	 * 手机品牌
	 */
	private String hardwareBrand;
	
	/**
	 * 手机型号
	 */
	private String hardwareModel;
	
	/**
	 * 手机操作系统  ios,android,wp
	 */
	private String os;
	
	/**
	 * 系统版本号
	 */
	private String osVersion;
	
	/**
	 * 关键字(ydzf_android)
	 */
	private String itemKey;

	
	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getHardwareBrand() {
		return hardwareBrand;
	}

	public void setHardwareBrand(String hardwareBrand) {
		this.hardwareBrand = hardwareBrand;
	}

	public String getHardwareModel() {
		return hardwareModel;
	}

	public void setHardwareModel(String hardwareModel) {
		this.hardwareModel = hardwareModel;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getItemKey() {
		return itemKey;
	}

	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}
	
}
