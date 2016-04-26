package com.bdx.rainbow.mapp.core.filter;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bdx.rainbow.mapp.core.annotation.Filter;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.MappException;

/**
 * 可根据用户的接口权限进行检查
 * @author mler
 *
 */
@Component
@Filter(filterMethod={"com.bdx.rainbow.mapp.core.base.AbstractMappAction.handler"})
public class RightCheckFilter implements IFilter<Object[]> {

	Logger logger = LoggerFactory.getLogger(RightCheckFilter.class);
	
	@Override
	public void init() {
		
	}

	@Override
	public void destroy() {

	}

	@Override
	public Object[] doFilter(Object[] args) throws Exception {
		
		String bizcode = (String)args[0];
		String version = (String)args[1];
		
		if(StringUtils.isBlank(bizcode) || StringUtils.isBlank(version))
			throw new MappException("请求接口的bizcode和version信息不明");

		Set<String> rights = (Set<String>)MappContext.getAttribute(MappContext.MAPPCONTEXT_RIGHT);
		
		if(rights == null || rights.contains(bizcode+"&&"+version) == false)
		{
			throw new BusinessException("用户未被允许请求该接口("+bizcode+")");
		}

		logger.debug("############# 权限检测通过  ###########");
		
		return args;
	}

}
