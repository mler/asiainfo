package com.bdx.rainbow.etl;

import java.util.Map;

public class EtlContext {
	
	public static final String POOL = "RAIN_BOW_POOL";//IMEI
	
	/** 将属性放入ThreadLocal中保存 **/
	private ThreadLocal<Map<String, Object>> context = new ThreadLocal<Map<String, Object>>();
}
