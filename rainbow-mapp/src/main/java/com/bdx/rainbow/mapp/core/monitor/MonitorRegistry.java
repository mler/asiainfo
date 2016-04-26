package com.bdx.rainbow.mapp.core.monitor;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdx.rainbow.mapp.core.util.JacksonUtils;

public class MonitorRegistry  {
	
	private static ScheduledExecutorService scheduledExecutor;
	
	private static Logger logger = LoggerFactory.getLogger(MonitorRegistry.class);
	
	private static Map<String,AtomicReference<AccessStatLog>> statInfoMap = new HashMap<String, AtomicReference<AccessStatLog>>();
	
	private static Map<String,AtomicLong> concurrentMap = new ConcurrentHashMap<String, AtomicLong>();
	
	private static Map<String,List<AccessLog>> accessLogMap = new HashMap<String, List<AccessLog>>();
	
	private static Thread shutdownHook;
	
	static{
		registerShutdownHook();
	}
	
	private static void updateStatInfo(String key,AccessLog accessLog) throws Exception
	{
		if(StringUtils.isBlank(key) || accessLog == null)
			return;
		
		if(statInfoMap.containsKey(key) == false)
			statInfoMap.put(key, new AtomicReference<AccessStatLog>(new AccessStatLog(System.currentTimeMillis())));
		
		/** 更新统计对象 **/
		AtomicReference<AccessStatLog> ref= statInfoMap.get(key);
		AccessStatLog except;
		AccessStatLog update = new AccessStatLog();
		do{
			except = ref.get();
			/** 更新statinfo **/
			update.setNodeName(except.getNodeName());
			update.setConcurrent(concurrentMap.get(key).get());
			update.setFail(accessLog.isSuccess()?except.getFail():except.getFail()+1);
			update.setMaxInput(accessLog.getReq().length() > except.getMaxInput()?accessLog.getReq().length():except.getMaxInput());
			update.setMaxOutput(accessLog.getRsp().length() > except.getMaxOutput()?accessLog.getRsp().length():except.getMaxOutput());
			long invokeTime = accessLog.getEndTime()-accessLog.getCreateTime();
			update.setMaxInvokeTime(invokeTime>except.getMaxInvokeTime()?invokeTime:except.getMaxInvokeTime());
			update.setSlowTimes(invokeTime>5000?except.getSlowTimes()+1:except.getSlowTimes());
			update.setSuccess(accessLog.isSuccess()?except.getSuccess()+1:except.getSuccess());
			update.setTotalTimes(except.getTotalTimes()+1);
			update.setLastUserId(accessLog.getUserId());
			update.setTotalInvokeTime(except.getTotalInvokeTime()+invokeTime);
			update.setTotalSuccessInvokeTime(accessLog.isSuccess()?except.getTotalSuccessInvokeTime()+invokeTime:except.getTotalSuccessInvokeTime());
			update.setEndTime(except.getEndTime()< accessLog.getEndTime()?accessLog.getEndTime():except.getEndTime());
			update.setStartTime(except.getStartTime());
		}while(ref.compareAndSet(except, update) == false);
		
	}
	
	public static void addAccessLog(String key,AccessLog accessLog) throws Exception
	{
		if(StringUtils.isBlank(key))
			return;
		
		if(accessLogMap.containsKey(key) == false)
			accessLogMap.put(key, Collections.synchronizedList(new LinkedList<AccessLog>()));
		
		accessLogMap.get(key).add(accessLog);
		
		/** 更新统计信息 **/
		updateStatInfo(key, accessLog);
		
		logger.debug("AccessLog add successfully,and Statinfo ["+key+"] update");
	}
	
	/**
	 * 放入定时任务
	 * @param command
	 * @param initialDelay 延迟多久开始执行
	 * @param delay 每隔多久开始执行
	 * @throws Exception
	 */
	public static void submitScheduleTask(Runnable command,long initialDelay, long delay) throws Exception
	{
		scheduledExecutor.scheduleWithFixedDelay(command, initialDelay, delay, TimeUnit.MILLISECONDS);
	}
	
	public static void initScheduledExecutor(ScheduledExecutorService  executor)
	{
		scheduledExecutor = executor;
	}
	
	public static void registerShutdownHook() {
		if (shutdownHook == null) {
			// No shutdown hook registered yet.
			shutdownHook = new Thread() {
				@Override
				public void run() {
					destory();
					logger.info("==============MonitorRegistery is cleared===============");
				}
			};
			Runtime.getRuntime().addShutdownHook(shutdownHook);
		}
	}
	
	private static void destory()
	{
		if(scheduledExecutor != null)
			scheduledExecutor.shutdownNow();
		
		statInfoMap = null;
		concurrentMap = null;
		accessLogMap = null;
	}
	
	public static List<AccessLog> getAccessLogByKey(String key)
	{
		if(StringUtils.isBlank(key))
			return null;
		
		return accessLogMap.get(key);
	}
	
	public static AccessStatLog getAccessStatLogByKey(String key)
	{
		if(StringUtils.isBlank(key))
			return null;
		
		return statInfoMap.get(key).get();
	}
	
	public static long incrementAndGet(String key)
	{
		if(concurrentMap.get(key) == null)
			concurrentMap.put(key, new AtomicLong(0));
		
		return concurrentMap.get(key).incrementAndGet();
	}
	
	public static long decrementAndGet(String key)
	{
		if(concurrentMap.get(key) == null)
			concurrentMap.put(key, new AtomicLong(0));
		
		return concurrentMap.get(key).decrementAndGet();
	}
	
	public static String[] getMonitorKeys()
	{
		Set<String> keys = statInfoMap.keySet();
		if(keys == null || keys.isEmpty())
			return null;
		return keys.toArray(new String[keys.size()]);
	}
	
	/**
	 * 采集统计信息，这段时间内的统计信息
	 * @param key
	 * @return
	 */
	public static AccessStatLog collectStat(String key)
	{
		AtomicReference<AccessStatLog> reference = statInfoMap.get(key);
		AccessStatLog collected_log = reference.get();
		AccessStatLog current;
		AccessStatLog update = new AccessStatLog();
        do {
            current = reference.get();
            if (current != null) 
            {
            	/** 更新statinfo **/
            	update.setNodeName(current.getNodeName());
    			update.setConcurrent(concurrentMap.get(key).get());
    			update.setFail(current.getFail()-collected_log.getFail());
    			update.setMaxInput(current.getMaxInput()>collected_log.getMaxInput()?current.getMaxInput():0);
    			update.setMaxOutput(current.getMaxOutput()>collected_log.getMaxOutput()?current.getMaxOutput():0);
    			update.setMaxInvokeTime(current.getMaxInvokeTime()>collected_log.getMaxInvokeTime()?current.getMaxInvokeTime():0);
    			update.setSlowTimes(current.getSlowTimes()-collected_log.getSlowTimes());
    			update.setSuccess(current.getSuccess()-collected_log.getSuccess());
    			update.setTotalTimes(current.getTotalTimes()-collected_log.getTotalTimes());
    			update.setLastUserId(current.getLastUserId());
    			update.setTotalInvokeTime(current.getTotalInvokeTime()-collected_log.getTotalInvokeTime());
    			update.setTotalSuccessInvokeTime(current.getTotalSuccessInvokeTime()-collected_log.getTotalSuccessInvokeTime());
    			update.setStartTime(current.getEndTime());
    		}
        } while (! reference.compareAndSet(current, update));
		
		try {
			logger.debug("AccessStatLog :"+JacksonUtils.toJson(collected_log));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("打印日志出错："+e.getMessage());
		}
		
		return collected_log;
	}
	
	/**
	 * 采集的日志，将日志列表返回，同时在监控中心将这段时间的日志删除。
	 * @param key
	 * @return
	 */
	public static List<AccessLog> collectLog(String key)
	{
		List<AccessLog> logs = new CopyOnWriteArrayList<AccessLog>(accessLogMap.get(key));
		accessLogMap.get(key).removeAll(logs);
		return logs;
	}
	
}
