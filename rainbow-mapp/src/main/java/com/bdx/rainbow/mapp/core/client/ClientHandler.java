/**
 * 
 */
package com.bdx.rainbow.mapp.core.client;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.base.MappUser;
import com.bdx.rainbow.mapp.core.context.ActionBeanDefinition;
import com.bdx.rainbow.mapp.core.context.BeanDefinitionFactory;
import com.bdx.rainbow.mapp.core.context.MappApplicationContext;
import com.bdx.rainbow.mapp.core.context.MappApplicationContextAware;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.ActionException;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.ErrorCodeDefine;
import com.bdx.rainbow.mapp.core.exception.MappException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.core.model.IMappDatapackage;
import com.bdx.rainbow.mapp.core.model.MappHeader;
import com.bdx.rainbow.mapp.core.model.MappPkg;
import com.bdx.rainbow.mapp.core.monitor.AccessLog;
import com.bdx.rainbow.mapp.core.monitor.MonitorRegistry;

/**
 * 
 * @author mler 重构此Class，将filters 独立出来
 */
@Component
public class ClientHandler implements MappApplicationContextAware {

	private final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
	
	private MappApplicationContext context = MappApplicationContext.instance();
	
	/**
	 * 传入请求对象，并调用方法获得结果
	 * @param request
	 * @return
	 * @throws SystemException
	 * @throws MappException
	 */
	public String doHandle(String bizcode,String version,String requestString,final Map<String, Object> attributes)
			throws Exception {
		
		logger.info("=======================开始请求====================");
		String responseString = "";
		AbstractMappAction<?,?> action = null;
		String logKey = getActionLogKey(bizcode,version);
		MappContext.setRequestString(requestString);
		try{
			MonitorRegistry.incrementAndGet(logKey);
			action = (AbstractMappAction<?,?>)context.getCachedAcionProxy(bizcode, version);
			if(action == null)
				throw new MappException("未找到Action代理类[bizcode="+bizcode+",version="+version+"]");
			action.initMappContext(bizcode, version, requestString, attributes);
			action.handler();
			setResponseInfo(ErrorCodeDefine.SUCCESS,"");
		} catch (ActionException e) {
			logger.error(e.getEx().getMessage(), e.getEx());
			setResponseInfo(e.getCode(),e.getEx().getMessage());
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			setResponseInfo(ErrorCodeDefine.EXPECT_ERROR,e.getMessage());
		} catch (SystemException e) {
			logger.error(e.getMessage(), e);
			setResponseInfo(ErrorCodeDefine.SERVER_ERROR,e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			setResponseInfo(ErrorCodeDefine.UNKNOW_ERROR,e.getMessage());
		} finally {
			responseString = action.responseToString(MappContext.getResponse());
			MappContext.setResponseString(responseString);
			logger.info("=======================请求结束====================");
			monitorLog(bizcode,version);
			MonitorRegistry.decrementAndGet(logKey);
		}
		
		return responseString;
	}
	
	private void monitorLog(String bizcode,String version) throws Exception
	{
		BeanDefinitionFactory beanFactory = ((BeanDefinitionFactory)context.getDefaultBeanFactory());
		ActionBeanDefinition action = beanFactory.lookupAction(bizcode, version);
		if(action.isMonitor() ==  false)
			return;
		
		MappUser user = (MappUser)MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
		IMappDatapackage response = MappContext.getResponse();
		AccessLog log = new AccessLog();
		log.setNodeName(context.getNodeName());
		log.setBizcode(bizcode);
		log.setCreateTime((long)MappContext.getAttribute(MappContext.MAPPCONTEXT_CREATETIME));
		log.setEndTime(System.currentTimeMillis());
		log.setReq(MappContext.getRequestString());
		log.setRsp(MappContext.getResponseString());
		log.setUserId(user == null?null:user.getUserId());
		log.setSuccess(response==null || ErrorCodeDefine.SUCCESS.equals(response.getHeader().getRespCode()) == false?false:true);
		MonitorRegistry.addAccessLog(getActionLogKey(bizcode,version), log);
		
	}
	
	private String getActionLogKey(String bizcode,String version){
		return bizcode+"$$"+version;
	}
	
	/**
	 * 设置返回的字符串
	 * @param rspCode
	 * @param message
	 */
	private void setResponseInfo(String rspCode,String message)
	{
		/**
		 * 只有在initMappContext()方法失败时才会出现：1，2的情况
		 * 	1.MappContext.getResponse() == null
		 * 	2.MappContext.getResponse().getHeader() == null
		 *  因此生成默认的对象
		 */
		if(MappContext.getResponse() == null)
			MappContext.setResponse(new MappPkg());
		
		if(MappContext.getResponse().getHeader() == null)
			MappContext.getResponse().setHeader(new MappHeader());
		
		MappContext.getResponse().getHeader().setRespCode(rspCode);
		MappContext.getResponse().getHeader().setRespMsg(message);
		
	}
	
	@Override
	public void setMappApplicationContext(MappApplicationContext context)
			throws Exception {
		this.context = context;
	}

	

}
