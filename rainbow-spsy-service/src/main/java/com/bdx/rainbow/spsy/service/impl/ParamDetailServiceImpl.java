package com.bdx.rainbow.spsy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bdx.rainbow.spsy.service.IParamDetailService;
import com.bdx.rainbow.urs.dubbo.IDubDicService;
import com.bdx.rainbow.urs.entity.ParamDetail;

public class ParamDetailServiceImpl implements IParamDetailService {
    @Autowired
	private IDubDicService dicService;
	
	@Override
	public List<ParamDetail> findCacheByKey(String key) throws Exception {		
		return dicService.findCacheByKey(key);
	}

	@Override
	public String getParamName(String key, String value) throws Exception {
		String paramName = dicService.getParamName(key, value, null);
		return paramName;
	}

}
