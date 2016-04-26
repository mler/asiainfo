package com.bdx.rainbow.spsy.bean;

import com.bdx.rainbow.basic.dubbo.bean.DubboSku;

public class SkuInfo extends DubboSku {
	private static final long serialVersionUID = 1L;
	/**
     * 是否附码
     */
	private String flag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
     
}
