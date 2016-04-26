package com.bdx.rainbow.mapp.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bdx.rainbow.mapp.core.annotation.Filter;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.BusinessException;

@Component
@Filter(filterMethod={"com.bdx.rainbow.mapp.core.base.AbstractMappAction.handler"})
public class UserCheckFilter implements IFilter<Object[]> {

	Logger logger = LoggerFactory.getLogger(UserCheckFilter.class);
	
	@Override
	public void init() {
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public Object[] doFilter(Object[] args) throws Exception {
		
		Object user = MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
		if(user == null)
			throw new BusinessException("MAPP 用户信息为 null");
		
		logger.debug("############# 用户检测通过  ###########");
		
		return args;
	}

}
