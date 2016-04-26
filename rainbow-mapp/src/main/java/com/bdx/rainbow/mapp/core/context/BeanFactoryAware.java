package com.bdx.rainbow.mapp.core.context;

public interface BeanFactoryAware extends Aware {

	public void setBeanFactory(BeanFactory beanFactory) throws Exception;
}
