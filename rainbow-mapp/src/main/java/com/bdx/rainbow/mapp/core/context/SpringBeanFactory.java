/**
 * 
 */
package com.bdx.rainbow.mapp.core.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanFactory extends BeanDefinitionFactory implements BeanFactory,ApplicationContextAware {

	private static SpringBeanFactory instance = new SpringBeanFactory();
	
	private ApplicationContext context;
	
	public static SpringBeanFactory instance() throws Exception
	{
		return instance;
	}

	@Override
	public Object getBeanByType(Class<?> clazz) throws Exception {
		return context.getBean(clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}

}
