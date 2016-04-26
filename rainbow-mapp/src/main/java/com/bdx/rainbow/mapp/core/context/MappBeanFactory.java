/**
 * 
 */
package com.bdx.rainbow.mapp.core.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mapp方式初始化上下文时用的factory
 */
public class MappBeanFactory extends BeanDefinitionFactory {

	private static MappBeanFactory instance = new MappBeanFactory();
	
	public static MappBeanFactory instance() throws Exception
	{
		return instance;
	}
	
	private Map<Class<?>,Object> singleBeanMap;

	public Object getBeanByType(Class<?> clazz) throws Exception {
		return singleBeanMap.get(clazz);
	}

	public void registSingleBean(Class<?> clazz) throws Exception {
		
		if(singleBeanMap == null || singleBeanMap.isEmpty())
			singleBeanMap = new ConcurrentHashMap<Class<?>, Object>();
		
		singleBeanMap .put(clazz,clazz.newInstance());
	}

}
