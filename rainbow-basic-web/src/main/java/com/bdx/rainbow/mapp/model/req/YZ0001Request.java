package com.bdx.rainbow.mapp.model.req;

import java.util.Map;

import com.bdx.rainbow.mapp.model.BDXBody;

/**
 * 根据barcode到物流中心码网站爬取商品信息和生产商信息的页面html返回
 * @author mler
 *
 */
public class YZ0001Request extends BDXBody {

    private String barcode;

    private Map<String,String> contentMap;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Map<String, String> getContentMap() {
		return contentMap;
	}

	public void setContentMap(Map<String, String> contentMap) {
		this.contentMap = contentMap;
	}

    

}
