package com.bdx.rainbow.mapp.core.context;

public interface BeanFactory {

	public Object getBeanByType(Class<?> clazz) throws Exception;
	
}
