/**
 * 
 */
package com.bdx.rainbow.mapp.core.context;

import java.net.InetAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.annotation.Action.SerializerType;
import com.bdx.rainbow.mapp.core.annotation.Filter;
import com.bdx.rainbow.mapp.core.annotation.Filter.FilterType;
import com.bdx.rainbow.mapp.core.annotation.Monitor;
import com.bdx.rainbow.mapp.core.exception.MappException;
import com.bdx.rainbow.mapp.core.filter.IFilter;
import com.bdx.rainbow.mapp.core.monitor.MonitorCollectSchedule;
import com.bdx.rainbow.mapp.core.monitor.MonitorConfig;
import com.bdx.rainbow.mapp.core.monitor.MonitorRegistry;
import com.bdx.rainbow.mapp.core.monitor.MonitorService;
import com.bdx.rainbow.mapp.core.proxy.ProxyBeanFactory;
import com.bdx.rainbow.mapp.core.serializer.JsonSerializer;
import com.bdx.rainbow.mapp.core.serializer.MappSerializer;
import com.bdx.rainbow.mapp.core.util.ClassUtils;

/**
 * @author mler
 * 初始化Mapp上下文的Loader
 */
public class MappContextLoader implements BeanFactoryAware {
	
	private Logger logger = LoggerFactory.getLogger(MappContextLoader.class);
	
	public static final String CREATE_TYPE_SPRING = "spring";
	
	public static final String CREATE_TYPE_MAPP = "mapp";
	
	private BeanFactory beanFactory;
	
	private MappApplicationContext context;
	
	private static MappContextLoader contextLoader = new MappContextLoader();
	
	public static MappContextLoader instance()
	{
		return contextLoader;
	}
	
	public void initializeMappApplicationContext(String[] packages,String creator) throws Exception
	{	
		logger.info("======================MappApplicationContext start initialize=================");
		this.context = MappApplicationContext.instance();
		this.context.setBeanFactory(beanFactory);
		this.context.setCreator(creator);
		this.context.setProxyBeanFactory(new ProxyBeanFactory(this.context));
		this.context.setNodeName(InetAddress.getLocalHost().getHostAddress());
		logger.info("====================== scan packages:"+packages+"==================");
		if(packages == null || packages.length == 0)
			return;
		
		Collection<Class<?>> clazzes = new HashSet<Class<?>>();
		
		for(String p : packages)
			clazzes.addAll(ClassUtils.getClasssFromPackage(p));
		
    	for(Class<?> clazz:clazzes) {
    		
    		/** 先将filter和serializer进行初始化，再对action进行初始化 **/
    		contextLoadFilter(clazz);
    		contextLoadSerializer(clazz);
    		contextLoadAction(clazz);
    		contextLoadMonitor(clazz);
    	}
    	
    	/** 初始化上下文和监控 **/
    	initializeMonitor();
    	
    	logger.info("======================MappApplicationContext initialized=================");
	}
	
	/**
	 * 初始化监控的线程池，并提交监控任务
	 * @throws Exception 
	 */
	private void initializeMonitor() throws Exception
	{
		/** 启动监控 **/
    	Map<Class<?>, MonitorBeanDefinition> monitorBDMap = ((BeanDefinitionFactory)beanFactory).getMonitorBeanDefinitions();
    	if(monitorBDMap == null || monitorBDMap.isEmpty())
    		return;
    	/** 初始化监控的线程池 **/
    	MonitorRegistry.initScheduledExecutor(new ScheduledThreadPoolExecutor(monitorBDMap.size()));
    	
    	for(MonitorBeanDefinition monitorBD : monitorBDMap.values())
    	{
    		MonitorConfig config = new MonitorConfig();
    		config.setKeys(monitorBD.getKeys());
    		config.setMaxSize(monitorBD.getMaxSize());
    		config.setMaxTime(monitorBD.getMaxTime());
    		config.setSaveTodb(monitorBD.isSaveTodb());
    		MonitorService service = (MonitorService)beanFactory.getBeanByType(monitorBD.getMonitorClass());
    		MonitorCollectSchedule scheduleTask = new MonitorCollectSchedule(service,config);
    		MonitorRegistry.submitScheduleTask(scheduleTask, monitorBD.getCollectTime(), monitorBD.getCollectTime());
    	}
    	logger.info("监控启动成功.........");
	}
	
	private void contextLoadAction(Class<?> clazz) throws Exception
	{
		Action action=AnnotationUtils.findAnnotation(clazz, Action.class);
		if(action == null) 
			return;
		
		String bizcode=AnnotationUtils.getValue(action, "bizcode").toString();
		String version=AnnotationUtils.getValue(action, "version").toString();
		
		boolean usercheck=(boolean)AnnotationUtils.getValue(action, "usercheck");
		boolean ipcheck=(boolean)AnnotationUtils.getValue(action, "ipcheck");
		boolean rightcheck=(boolean)AnnotationUtils.getValue(action, "rightcheck");
		
		boolean encrypt=(boolean)AnnotationUtils.getValue(action, "encrypt");
		boolean base64=(boolean)AnnotationUtils.getValue(action, "base64");
		boolean gzip=(boolean)AnnotationUtils.getValue(action, "gzip");
		String encryptType=AnnotationUtils.getValue(action, "encryptType").toString();
		SerializerType type = (SerializerType)AnnotationUtils.getValue(action, "serializer");
		String[] filters = (String[])AnnotationUtils.getValue(action, "filters");
		boolean monitor=(boolean)AnnotationUtils.getValue(action, "monitor");
		
		
		ActionBeanDefinition beanDefinition = new ActionBeanDefinition();
		beanDefinition.setActionClass(clazz);
		beanDefinition.setBase64(base64);
		beanDefinition.setBizcode(bizcode);
		beanDefinition.setUsercheck(usercheck);
		beanDefinition.setIpcheck(ipcheck);
		beanDefinition.setRightcheck(rightcheck);
		beanDefinition.setEncrypt(encrypt);
		beanDefinition.setEncryptType(encryptType);
		beanDefinition.setGzip(gzip);
		beanDefinition.setFilters(filters);
		beanDefinition.setVersion(version);
		beanDefinition.setSerializer(type);
		beanDefinition.setMethodFilterSet(((BeanDefinitionFactory)beanFactory).lookupFilterForClass(clazz));
		beanDefinition.setMonitor(monitor);
		Class<?>[] parameterized_clazzes = ClassUtils.getParameterizedType(clazz);
		if(parameterized_clazzes != null && parameterized_clazzes.length >= 2)
		{
			beanDefinition.setRequestClass(parameterized_clazzes[0]);
			beanDefinition.setResponseClass(parameterized_clazzes[1]);
		}
		
//		/** 如果需要进行监控的话，则在监控中心进行注册 **/
//		if(monitor)
//		{
//			
//		}
		
		((BeanDefinitionFactory)beanFactory).registerAction(beanDefinition);
	}
	
	/**
	 * 判断class是否为Filter类型，如果是则
	 * @param clazz
	 */
	private void contextLoadFilter(Class<?> clazz) throws Exception
	{
		Filter filter=AnnotationUtils.findAnnotation(clazz, Filter.class);
		if(filter == null) 
			return;
		
		if(IFilter.class.isAssignableFrom(clazz) == false)
		{
			logger.error("Class has @IFilter must be implemented interface IFilter,please check {}",clazz.getName());
			throw new MappException("Class has @IFilter must be implemented interface IFilter,please check {"+clazz.getName()+"}");
		}
		
		String[] filterMethod=(String[])AnnotationUtils.getValue(filter, "filterMethod");
		boolean required=(boolean)AnnotationUtils.getValue(filter, "required");
		FilterType filterTYpe=(FilterType)AnnotationUtils.getValue(filter, "filterType");
		int order=(int)AnnotationUtils.getValue(filter, "order");
		
		FilterBeanDefinition beanDefinition = new FilterBeanDefinition();
		beanDefinition.setFilterMethod(filterMethod);
		beanDefinition.setFilterType(filterTYpe);
		beanDefinition.setOrder(order);
		beanDefinition.setRequired(required);
		beanDefinition.setFilterClass(clazz);
		
		((BeanDefinitionFactory)beanFactory).registerFilter(beanDefinition);
			
	}
	
	private void contextLoadSerializer(Class<?> clazz) throws Exception
	{
		if(MappSerializer.class.isAssignableFrom(clazz) == false)
			return;
		
		SerializerBeanDefinition beanDefinition = new SerializerBeanDefinition();
		beanDefinition.setType(SerializerType.JSON);
		beanDefinition.setSerializerClass(JsonSerializer.class);
		
		((BeanDefinitionFactory)beanFactory).registerSerializer(beanDefinition);
		
//		((BeanDefinitionFactory)beanFactory).registerSerializer(SerializerType.XML,beanDefinition);
	}
	
	private void contextLoadMonitor(Class<?> clazz) throws Exception
	{
		if(MonitorService.class.isAssignableFrom(clazz) == false)
			return;
			
		Monitor monitor=AnnotationUtils.findAnnotation(clazz, Monitor.class);
		if(monitor == null)
			return;
		
		MonitorBeanDefinition beanDefinition = new MonitorBeanDefinition();
		beanDefinition.setCollectTime(monitor.collectTime());
		beanDefinition.setKeys(monitor.keys());
		beanDefinition.setMaxSize(monitor.maxSize());
		beanDefinition.setMaxTime(monitor.maxTime());
		beanDefinition.setSaveTodb(monitor.saveTodb());
		beanDefinition.setMonitorClass(clazz);
		
		((BeanDefinitionFactory)beanFactory).registerMonitor(beanDefinition);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws Exception {
		this.beanFactory = beanFactory;
	}
	
}
