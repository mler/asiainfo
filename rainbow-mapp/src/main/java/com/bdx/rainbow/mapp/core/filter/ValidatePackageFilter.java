package com.bdx.rainbow.mapp.core.filter;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bdx.rainbow.mapp.core.annotation.Filter;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.model.IMappDatapackage;

@Component
@Filter(filterMethod={"com.bdx.rainbow.mapp.core.base.AbstractMappAction.doHandlePackage"})
public class ValidatePackageFilter implements IFilter<Object[]> {

	private Logger logger = LoggerFactory.getLogger(ValidatePackageFilter.class);
	
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] doFilter(Object[] args) throws Exception {
		
		String bizcode = (String)args[0];
		String version = (String)args[1];
		String requestString = (String)args[2];
		Map<String,Object> context = (Map<String,Object>)args[3];
		
		if(context == null)
//			throw new BusinessException("MAPP 上下文信息为 NULL");
//		
//		IMappDatapackage request = (IMappDatapackage)context.get(MappContext.MAPPCONTEXT_REQUEST);
		
		if(StringUtils.isBlank(requestString))
			throw new BusinessException("MAPP 请求信息为空");
		
		if(version == null || StringUtils.isBlank(version))
			throw new BusinessException("MAPP 接口版本信息version为空");
		
		if(bizcode == null || StringUtils.isBlank(bizcode) || StringUtils.isBlank(bizcode))
			throw new BusinessException("MAPP bizcode 参数为空");
		
//		if(StringUtils.isBlank(request.getHeader().getIdentityId()))
//			logger.warn("MAPP 报文流水为空");
		
		logger.info("请求报文检测通过，报文信息："+requestString);
		
		return args;
		
	}

}
