package com.bdx.rainbow.service.ydzf.common.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bdx.rainbow.entity.ydzf.MappAppVersion;
import com.bdx.rainbow.entity.ydzf.MappAppVersionExample;
import com.bdx.rainbow.mapper.ydzf.MappAppVersionMapper;
import com.bdx.rainbow.service.ydzf.common.IMappAppVersionService;
import com.bdx.rainbow.service.ydzf.core.BaseService;

@Service("mappAppVersionService")
public class MappAppVersionService extends BaseService implements
		IMappAppVersionService {
	private static final Logger log = LoggerFactory
			.getLogger(MappAppVersionService.class);
	@Autowired
	protected MappAppVersionMapper mappAppVersionMapper;
	@Override
	public MappAppVersion getAppVersion(String itemkey) throws Exception {
		
		MappAppVersionExample ex= new MappAppVersionExample();
		MappAppVersionExample.Criteria cr=ex.createCriteria();
		cr.andItemkeyEqualTo(itemkey);
		List<MappAppVersion> list=mappAppVersionMapper.selectByExample(ex);
		if(!CollectionUtils.isEmpty(list))
		{
			return list.get(0);
		}
		return null;
	}



}
