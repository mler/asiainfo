package com.bdx.rainbow.service.etl.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

/**
 * Spring实例对象工厂
 *
 * @author Administrator
 */
@Repository("basicSpringBeanFactory")
public class SpringBeanFactory implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/** 
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext ac) {
		SpringBeanFactory.applicationContext = ac;
	}

	public static Object getSpringBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
}
