package com.bdx.rainbow.mapp.core.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdx.rainbow.mapp.core.base.MappAction;
import com.bdx.rainbow.mapp.core.proxy.ProxyBeanFactory;

public class MappApplicationContext implements BeanFactoryAware,BeanFactory  {
	
	private Logger logger = LoggerFactory.getLogger(MappApplicationContext.class);
	
	private final static String APPEND_STRING = "&&";
	
	private String creator;
	
	private BeanFactory beanFactory;
	
	private ProxyBeanFactory proxyBeanFactory;
	
	private String nodeName;
	
	/**
	 * 缓存bizcode=xxx的Action代理类
	 */
	private Map<String,MappAction<?,?>> actionProxyMap = new ConcurrentHashMap<String, MappAction<?,?>>();
	
	private static MappApplicationContext context = new MappApplicationContext();
	
	public static MappApplicationContext instance()
	{
		return context;
	}
	
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public void cacheProxy(String bizcode,String version,MappAction<?,?> actionProxy)
	{
		if(StringUtils.isBlank(bizcode) || StringUtils.isBlank(version) || actionProxy == null)
		{
			return;
		}
	
		actionProxyMap.put(bizcode+APPEND_STRING+version, actionProxy);
	}
	
	@Deprecated
	public MappAction<?,?> getAcion(String bizcode,String version) throws Exception
	{
		ActionBeanDefinition actionBD = ((BeanDefinitionFactory)beanFactory).lookupAction(bizcode, version);
		
		if(actionBD == null)
		{
			logger.warn("ActionBeanDefinition for [bizcode="+bizcode+" and version="+version+"] is null");
			return null;
		}
		
		MappAction<?,?> action = (MappAction<?,?>)beanFactory.getBeanByType(actionBD.getActionClass());
		return action;
	}
	
	public MappAction<?,?> getCachedAcionProxy(String bizcode,String version) throws Exception
	{
		MappAction<?,?> proxy = actionProxyMap.get(bizcode+APPEND_STRING+version);
		
		if(proxy == null)
		{
			proxy = proxyBeanFactory.createActionProxy(bizcode, version);
			cacheProxy(bizcode,version, proxy);
		}
		return proxy;
	}
	
	public BeanFactory getDefaultBeanFactory()
	{
		return this.beanFactory;
	}

	public void setProxyBeanFactory(ProxyBeanFactory proxyBeanFactory) {
		this.proxyBeanFactory = proxyBeanFactory;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws Exception {
		this.beanFactory = beanFactory;
	}

	@Override
	public Object getBeanByType(Class<?> clazz) throws Exception {
		
		if(clazz == null)
		{
			logger.error("Bean Type is null");
			return null;
		}
		
		return beanFactory.getBeanByType(clazz);
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
}
