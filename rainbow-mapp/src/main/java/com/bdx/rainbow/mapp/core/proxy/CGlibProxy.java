package com.bdx.rainbow.mapp.core.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.core.annotation.AnnotationUtils;

import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.annotation.Action.SerializerType;
import com.bdx.rainbow.mapp.core.annotation.Filter.FilterType;
import com.bdx.rainbow.mapp.core.base.MappAction;
import com.bdx.rainbow.mapp.core.context.ActionBeanDefinition;
import com.bdx.rainbow.mapp.core.context.BeanDefinitionFactory;
import com.bdx.rainbow.mapp.core.context.BeanFactory;
import com.bdx.rainbow.mapp.core.context.BeanFactoryAware;
import com.bdx.rainbow.mapp.core.context.FilterBeanDefinition;
import com.bdx.rainbow.mapp.core.context.SerializerBeanDefinition;
import com.bdx.rainbow.mapp.core.filter.FilterChain;
import com.bdx.rainbow.mapp.core.filter.IFilter;
import com.bdx.rainbow.mapp.core.filter.IpCheckFilter;
import com.bdx.rainbow.mapp.core.filter.RightCheckFilter;
import com.bdx.rainbow.mapp.core.filter.UserCheckFilter;
import com.bdx.rainbow.mapp.core.model.IMappDatapackage;
import com.bdx.rainbow.mapp.core.serializer.MappSerializer;

public class CGlibProxy implements MethodInterceptor,BeanFactoryAware {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private BeanFactory beanFactory;
	
	private Object delegate;
	
	private ActionBeanDefinition beanDefinition;
	
	public Object bind(Object delegate) throws Exception
	{
		this.delegate = delegate;
		
		if(delegate != null && delegate instanceof MappAction)
		{
			Action action = AnnotationUtils.findAnnotation(delegate.getClass(), Action.class);
			if(action != null)
			{
				beanDefinition = ((BeanDefinitionFactory) beanFactory).lookupAction(action.bizcode(), action.version());
				initSerializer();
			}
		}
		
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.delegate.getClass());
		enhancer.setCallback(this);
		Object proxy = enhancer.create();
		logger.debug("create proxy["+proxy+"], delegate class : "+this.delegate.getClass().getName());
		return proxy;
	}

	/**
	 * 如果需要代理的对象是MappAction对象，则根据MappAction上的工具初始化其序列化的属性
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initSerializer() throws Exception
	{
		SerializerType serializerType = ((ActionBeanDefinition)beanDefinition).getSerializer();
		SerializerBeanDefinition serializerBD = ((BeanDefinitionFactory) beanFactory).lookupSerializer(serializerType);
		MappSerializer<IMappDatapackage> serializer = (MappSerializer<IMappDatapackage>) beanFactory.getBeanByType(serializerBD.getSerializerClass());
		((MappAction) delegate).setSerializer(serializer);
	}
	
	/**
	 * 拦截目标类所有方法的调用
	 * 
	 * @param obj
	 *            目标类的实例
	 * @param method
	 *            目标方法的反射对象
	 * @param args
	 *            目标方法的参数
	 * @param proxy
	 *            代理类的实例
	 */
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
			
		System.out.println("****** ["+method.getName()+"] *****");
		
		List<IFilter<Object[]>> brefore_filters = new ArrayList<IFilter<Object[]>>();
		List<IFilter<Object>> after_filters = new ArrayList<IFilter<Object>>();
		
		
		/** 获取Action相关filter并执行 **/
		Map<String,Set<FilterBeanDefinition>> methodFilterSetMap = ((BeanDefinitionFactory)beanFactory).lookupFilterForClass(this.delegate.getClass());
		if(methodFilterSetMap != null && methodFilterSetMap.isEmpty() == false )
		{
			Set<FilterBeanDefinition> filterBeanDefinitions = methodFilterSetMap.get(method.getName());
			if(filterBeanDefinitions != null && filterBeanDefinitions.isEmpty() == false)
			{
				for(FilterBeanDefinition filterBD : filterBeanDefinitions)
				{
					if(UserCheckFilter.class == filterBD.getFilterClass() && beanDefinition.isUsercheck()==false)
						continue;
					
					if(IpCheckFilter.class == filterBD.getFilterClass() && beanDefinition.isIpcheck()==false)
						continue;
					
					if(RightCheckFilter.class == filterBD.getFilterClass() && beanDefinition.isRightcheck()==false)
						continue;
						
					IFilter filter = (IFilter)beanFactory.getBeanByType(filterBD.getFilterClass());
					if(filter == null)
						continue;
					
					if(FilterType.BEFORE.equals(filterBD.getFilterType()))
					{
							brefore_filters.add(filter);
					}
					else if(FilterType.AFTER.equals(filterBD.getFilterType()))
					{
						after_filters.add(filter);
					}
					else
					{
						logger.warn("Around filter is not support ，FilterClass = "+filterBD.getFilterClass().getName());
					}
				}
			}
		}
		
		/** 执行前置filter **/
		if(brefore_filters != null && brefore_filters.isEmpty() == false)
		{
			FilterChain<Object[]> before_chain = new FilterChain<Object[]>();
			before_chain.setFilters(brefore_filters);
			args = before_chain.doFilterChain(args);
		}
		
		Object result =  proxy.invoke(this.delegate, args);
		
		/** 执行后置filter **/
		if(brefore_filters != null && brefore_filters.isEmpty() == false)
		{
			FilterChain<Object> after_chain = new FilterChain<Object>();
			after_chain.setFilters(after_filters);
			result = after_chain.doFilterChain(result);
		}
		
		return result;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws Exception {
		this.beanFactory = beanFactory;
	}

}
