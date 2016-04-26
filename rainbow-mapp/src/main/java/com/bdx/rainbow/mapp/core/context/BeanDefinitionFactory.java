/**
 * 
 */
package com.bdx.rainbow.mapp.core.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdx.rainbow.mapp.core.annotation.Action.EncryptType;
import com.bdx.rainbow.mapp.core.annotation.Action.SerializerType;
import com.bdx.rainbow.mapp.core.exception.MappException;

/**
 *
 */
public abstract class BeanDefinitionFactory implements MappBeanRegistry,BeanFactory {

	private Logger logger = LoggerFactory.getLogger(BeanDefinitionFactory.class);
	
	/**
	 * ActionBean的相关信息
	 */
	
	private final static String APPEND_STRING = "&&";
	
	private Map<String,ActionBeanDefinition> actionDefinitionMap;
	
	private Map<String,Set<FilterBeanDefinition>> filterDefinitionMap;
	
	private Map<SerializerType,SerializerBeanDefinition> serializerBeanDefinitionMap;
	
	private Map<EncryptType,EncryptBeanDefinition> encryptBeanDefinitionMap;
	
	private Map<Class<?>,MonitorBeanDefinition> monitorBeanDefinitionMap;
	
	private Thread shutdownHook;//清理
	
	public BeanDefinitionFactory() {
		super();
		registerShutdownHook();
	}

	public void registerFilter(FilterBeanDefinition beanDefinition) throws Exception
	{
		if(beanDefinition == null)
			throw new MappException("method register BeanDefinition ,BeanDefinition can't be null");
		
		if(filterDefinitionMap == null)
			filterDefinitionMap = new HashMap<String, Set<FilterBeanDefinition>>();
		
		String[] filterMethod = beanDefinition.getFilterMethod();
		if(filterMethod == null || filterMethod.length ==0)
		{
			logger.warn(beanDefinition.getFilterClass()+" has no filterMethod");
			return;
		}
		
		for(String method : filterMethod)
		{
			if(filterDefinitionMap.containsKey(method) == false)
				filterDefinitionMap.put(method, new HashSet<FilterBeanDefinition>());
			filterDefinitionMap.get(method).add(beanDefinition);
		}
		
		logger.debug("FilterBeanDefinition["+beanDefinition.getFilterClass().getName()+"] is register sucess");
	}
	
	public static String[] resolveClassAndMethod(String filterKey)
	{
		String[] classNameAndMethodName = new String[2];
		int lastPoniter = filterKey.lastIndexOf(".");
		classNameAndMethodName[0] = filterKey.substring(0, lastPoniter);//className
		classNameAndMethodName[1] = filterKey.substring(lastPoniter+1);//methodName
		return classNameAndMethodName;
	}

	public Set<FilterBeanDefinition> lookupFilter(String method) throws Exception
	{
		if(filterDefinitionMap == null || StringUtils.isBlank(method))
			return null;
			
		return filterDefinitionMap.get(method);
	}
	
	
	public Map<String,Set<FilterBeanDefinition>> lookupFilterForClass(Class<?> clazz) throws Exception
	{
		if(clazz == null)
			throw new MappException(" Lookup Filters for class ，but class is null ");
			
		if(filterDefinitionMap == null)
			return null;
		
		Map<String,Set<FilterBeanDefinition>> methodFilterMap = new HashMap<String, Set<FilterBeanDefinition>>();
		
		for(String classAndMethodString : filterDefinitionMap.keySet())
		{
			String[] classAndMethod = resolveClassAndMethod(classAndMethodString);
			String className = classAndMethod[0];
			String methodName = classAndMethod[1];
			
			if(StringUtils.isBlank(className) == false 
					&& (className.equals(clazz.getName()) || Class.forName(className).isAssignableFrom(clazz)))
			{
				if(methodFilterMap.get(methodName) == null)
					methodFilterMap.put(methodName, new HashSet<FilterBeanDefinition>());
				
				methodFilterMap.put(methodName, filterDefinitionMap.get(classAndMethodString));
			}
		}
			
		return methodFilterMap;
	}
	
	public void registerAction(ActionBeanDefinition beanDefinition) throws Exception
	{
		if(beanDefinition == null)
			throw new MappException("method register BeanDefinition ,BeanDefinition can't be null");
		
		if(actionDefinitionMap == null)
			actionDefinitionMap = new ConcurrentHashMap<String, ActionBeanDefinition>();
		String name = beanDefinition.getBizcode()+APPEND_STRING+beanDefinition.getVersion();
		actionDefinitionMap.put(name, beanDefinition);
		
		logger.debug("ActionBeanDefinition["+beanDefinition.getBizcode()+"] is register sucess");
	}
	
	public ActionBeanDefinition lookupAction(String bizcode,String version) throws Exception
	{
		if(actionDefinitionMap == null || StringUtils.isBlank(bizcode) || StringUtils.isBlank(version))
			return null;
			
		return actionDefinitionMap.get(bizcode+APPEND_STRING+version);
	}
	
	public void registerSerializer(SerializerBeanDefinition beanDefinition) throws Exception
	{
		if(beanDefinition == null)
			throw new MappException("method register BeanDefinition ,BeanDefinition can't be null");
		
		if(serializerBeanDefinitionMap == null)
			serializerBeanDefinitionMap = new ConcurrentHashMap<SerializerType, SerializerBeanDefinition>();
		 
		serializerBeanDefinitionMap.put(beanDefinition.getType(), beanDefinition);
		
		logger.debug("SerializerBeanDefinition["+beanDefinition.getSerializerClass().getName()+"] is register sucess");
	}
	
	public SerializerBeanDefinition lookupSerializer(SerializerType type) throws Exception
	{
		if(serializerBeanDefinitionMap == null)
			return null;
			
		return serializerBeanDefinitionMap.get(type);
	}
	
	public void registerEncryptor(EncryptBeanDefinition beanDefinition) throws MappException
	{
		if(beanDefinition == null)
			throw new MappException("method register BeanDefinition ,BeanDefinition can't be null");
		
		
		if(encryptBeanDefinitionMap == null)
			encryptBeanDefinitionMap = new HashMap<EncryptType, EncryptBeanDefinition>();
		
		encryptBeanDefinitionMap.put(beanDefinition.getType(), beanDefinition);
		
	}
	
	public EncryptBeanDefinition lookupEncryptor(EncryptType type) throws Exception
	{
		if(encryptBeanDefinitionMap == null)
			return null;
			
		return encryptBeanDefinitionMap.get(type);
	}
	
	public void registerMonitor(MonitorBeanDefinition beanDefinition) throws Exception
	{
		if(beanDefinition == null)
			throw new MappException("method register BeanDefinition ,BeanDefinition can't be null");
		
		if(monitorBeanDefinitionMap == null)
			monitorBeanDefinitionMap = new ConcurrentHashMap<Class<?>, MonitorBeanDefinition>();
		 
		monitorBeanDefinitionMap.put(beanDefinition.getMonitorClass(), beanDefinition);
		
		logger.debug("MonitorBeanDefinitionMap["+beanDefinition.getMonitorClass().getName()+"] is register sucess");
	}
	
	public MonitorBeanDefinition lookupMonitor(Class<?> clazz) throws Exception
	{
		if(monitorBeanDefinitionMap == null)
			return null;
			
		return monitorBeanDefinitionMap.get(clazz);
	}
	
	public Map<Class<?>,MonitorBeanDefinition> getMonitorBeanDefinitions()
	{
		return monitorBeanDefinitionMap;
	}
	
	private void destory()
	{
		actionDefinitionMap=null;
		filterDefinitionMap=null;
		serializerBeanDefinitionMap=null;
		encryptBeanDefinitionMap=null;
		monitorBeanDefinitionMap=null;
	}
	
	public void registerShutdownHook() {
		if (this.shutdownHook == null) {
			// No shutdown hook registered yet.
			this.shutdownHook = new Thread() {
				@Override
				public void run() {
					destory();
					logger.info("===========BeanDefinitionFactory is cleared==========");
				}
			};
			Runtime.getRuntime().addShutdownHook(this.shutdownHook);
		}
	}
	
}
