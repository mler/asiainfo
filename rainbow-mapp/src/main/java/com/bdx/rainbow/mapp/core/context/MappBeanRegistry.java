package com.bdx.rainbow.mapp.core.context;

import java.util.Set;

import com.bdx.rainbow.mapp.core.annotation.Action.SerializerType;


public interface MappBeanRegistry {

	public void registerFilter(FilterBeanDefinition beanDefinition) throws Exception;
	
	public Set<FilterBeanDefinition> lookupFilter(String name) throws Exception;
	
	public void registerAction(ActionBeanDefinition beanDefinition) throws Exception;
	
	public ActionBeanDefinition lookupAction(String bizcode,String version) throws Exception;
	
	public void registerSerializer(SerializerBeanDefinition beanDefinition) throws Exception;
	
	public SerializerBeanDefinition lookupSerializer(SerializerType type) throws Exception;
	
	public void registerMonitor(MonitorBeanDefinition beanDefinition) throws Exception;
	
	public MonitorBeanDefinition lookupMonitor(Class<?> clazz) throws Exception;
	
}
