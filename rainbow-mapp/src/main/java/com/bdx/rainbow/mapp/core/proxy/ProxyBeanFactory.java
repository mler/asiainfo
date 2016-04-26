package com.bdx.rainbow.mapp.core.proxy;

import org.apache.commons.lang3.StringUtils;

import com.bdx.rainbow.mapp.core.base.MappAction;
import com.bdx.rainbow.mapp.core.context.ActionBeanDefinition;
import com.bdx.rainbow.mapp.core.context.BeanDefinitionFactory;
import com.bdx.rainbow.mapp.core.context.BeanFactory;
import com.bdx.rainbow.mapp.core.context.MappApplicationContext;
import com.bdx.rainbow.mapp.core.context.MappApplicationContextAware;
import com.bdx.rainbow.mapp.core.exception.MappException;

public class ProxyBeanFactory implements MappApplicationContextAware {
	
	private MappApplicationContext context;
	
	public ProxyBeanFactory(MappApplicationContext context)
	{
		this.context = context;
	}
	
	public MappAction<?,?> createActionProxy(String bizcode,String version) throws Exception
	{
		if(StringUtils.isBlank(bizcode) || StringUtils.isBlank(version))
			throw new MappException("bizcode and version can't be blank");
		
		final BeanFactory beanFactory = context.getDefaultBeanFactory();
		
		final ActionBeanDefinition actionBD = ((BeanDefinitionFactory)beanFactory).lookupAction(bizcode,version);
		if(actionBD == null)
			throw new MappException("can't find the BeanDefinition for bizcode="+bizcode);
		
		MappAction<?,?> action = (MappAction<?,?>)beanFactory.getBeanByType(actionBD.getActionClass());
		
		CGlibProxy cglibProxy = new CGlibProxy();
		cglibProxy.setBeanFactory(context.getDefaultBeanFactory());
		return (MappAction<?,?>)cglibProxy.bind(action);
	}
	
	@Override
	public void setMappApplicationContext(MappApplicationContext context)
			throws Exception {
		this.context = MappApplicationContext.instance();
	}
}
