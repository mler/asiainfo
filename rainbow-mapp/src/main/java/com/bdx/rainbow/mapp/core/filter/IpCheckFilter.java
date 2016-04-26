package com.bdx.rainbow.mapp.core.filter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bdx.rainbow.mapp.core.annotation.Filter;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.BusinessException;

/**
 * ip校验过滤器
 * @author mler
 *
 */
@Component
@Filter(filterMethod={"com.bdx.rainbow.mapp.core.base.AbstractMappAction.doHandlePackage"})
public class IpCheckFilter implements IFilter<Object[]> {

	Logger logger = LoggerFactory.getLogger(RightCheckFilter.class);
	
	private static String IP_SPLIT_CHAR = ",";
	
	public void init() {
		
	}

	@Override
	public void destroy() {

	}

	@Override
	public Object[] doFilter(Object[] args) throws Exception {
		
		Map<String,Object> context = (Map<String,Object>)args[3];
		
		//TODO validIps 获取方式 ，以后系统进行配置
		Set<String> validIps = new HashSet<String>();
		validIps.add("localhost");
		validIps.add("10.10.31.30");
		validIps.add("127.0.0.1");
		
		String requestIP = context==null?null:(String) context.get(MappContext.MAPPCONTEXT_REQUEST_IP);
		if(validIps == null || validIps.isEmpty())
		{
			logger.debug("系统对请求ip无要求");
		}
		else if(validIps != null && validIps.isEmpty() ==false
				&& validIps.contains(requestIP) == false)
		{
			throw new BusinessException("未被允许的IP请求，IP:"+requestIP);
		}
		
		logger.debug("#################   IP合法性检测通过 [请求IP:"+requestIP+", 合法IP="+validIps+"] ###################");
		
		return args;

	}

}
