package com.bdx.rainbow.mapp.model.req;

import com.bdx.rainbow.mapp.model.BDXBody;

/**
 * 根据barcode到物流中心码网站爬取商品信息和生产商信息的页面html返回
 * @author mler
 *
 */
public class YZ0002Request extends BDXBody {

    private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
