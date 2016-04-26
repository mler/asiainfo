package com.bdx.rainbow.service.vfs;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

/**
 * Spring实例对象工厂
 *
 * @author Administrator
 */
@Repository("springBeanFactoryVfs")
public class SpringBeanFactory implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/** 
	 * @see ApplicationContextAware#setApplicationContext(ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext ac) {
		SpringBeanFactory.applicationContext = ac;
	}

	public static Object getSpringBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	public static <T> T getSpringBean(Class<T> classType) {
		return applicationContext.getBean(classType);
	}
}
