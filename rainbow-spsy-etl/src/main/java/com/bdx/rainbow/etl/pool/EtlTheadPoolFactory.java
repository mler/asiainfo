package com.bdx.rainbow.etl.pool;

import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.alibaba.druid.util.StringUtils;

public final class EtlTheadPoolFactory {
	/** 线程池 */
	
	public static Map<String,ThreadPoolExecutor > poolMap = new ConcurrentHashMap<String, ThreadPoolExecutor>(0);
	
	public static EtlThreadPoolExecutor executor = new EtlThreadPoolExecutor("defaultPool",70,70, 3, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>()) ;
	
	static
	{
		register(executor.getName(),executor);
	}
	
	public static EtlThreadPoolExecutor getPoolInstance()
	{
		return executor;
	}
	
	public static EtlThreadPoolExecutor getPoolInstance(String name)
	{
		try{
			if(name == null || StringUtils.isEmpty(name))
				return getPoolInstance();
			
			if(name == null || poolMap.containsKey(name))
				throw new Exception("初始化线程池出错，name="+name);
			
			EtlThreadPoolExecutor pool = new EtlThreadPoolExecutor(name,70,70, 3, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
			register(pool.getName(),pool);
			
			return pool;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static void register(String name,ThreadPoolExecutor pool)
	{
		poolMap.put(name, pool);
		
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		ObjectName objectName = null;
		try {
			objectName = new ObjectName("EtlThreadPoolExecutor:name="+name);
			mBeanServer.registerMBean(pool, objectName);
			
			  //创建适配器，用于能够通过浏览器访问MBean  
//	        HtmlAdaptorServer adapter = new HtmlAdaptorServer();  
//	        adapter.setPort(9999);  
//	        mBeanServer.registerMBean(adapter, objectName);
//	        adapter.start();

//			System.out.println("register is ok,num:"+ mBeanServer.getMBeanCount());
//			System.out.println("corePoolSize:"+ mBeanServer.getAttribute(objectName, "CorePoolSize"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public final static void main(String[] args)
	{
		EtlTheadPoolFactory.getPoolInstance();
	}
}
