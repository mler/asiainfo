package com.bdx.rainbow.spsy.web;

import org.apache.commons.lang.StringUtils;

import com.bdx.rainbow.spsy.service.IParamDetailService;
import com.bdx.rainbow.util.BeanFactory;

public class CachedDict {
	public static String getCachedName(String key, String value, String parent) throws Exception {
		if (value != null && StringUtils.isNotEmpty(value)) {
			IParamDetailService paramService = (IParamDetailService) BeanFactory.getObject("paramDetailService");
			String vc = paramService.getParamName(key, value);
			return vc;
		}else{
			return "";
		}
	}
}
