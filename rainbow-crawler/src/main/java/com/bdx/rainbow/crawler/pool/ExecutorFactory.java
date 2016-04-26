package com.bdx.rainbow.crawler.pool;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdx.rainbow.crawler.monitor.StatInfo;
import com.bdx.rainbow.crawler.seed.Seed;

/**
 * 自定义线程执行者工厂,通过枚举实现单例
 * @author mler
 * @2016年4月16日
 */
public enum ExecutorFactory {
	
	INSTANCE;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public Map<String,Executor> executorMap;
	
	public Map<String,Map<Class<?>,Collection<Seed>>> errorSeedMap;
	
	public Executor defaultExecutor;
	
	public final static String DEFAULT_POOL_EXECUTOR = "default_executor";
	
	public final static String DEFAULT_SCHEDULE_EXECUTOR = "default_schedule";
	
	ExecutorFactory()
	{
		executorMap = new ConcurrentHashMap<String, Executor>(0);
	}
	
	public Executor getExecutor(String name)
	{
		name = StringUtils.isBlank(name)?DEFAULT_POOL_EXECUTOR : name; 
		try{
			if(StringUtils.isBlank(name))
				throw new Exception("初始化线程池出错，name is blank");
			
			return executorMap.get(name);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void registerExecutor(Executor executor)
	{
		executorMap.put(executor.getName(), executor);
	}

	
}
