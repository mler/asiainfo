package com.bdx.rainbow.etl.filter;

import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Frequency<T> implements Filter<T> {

	private final static Logger 	log = LoggerFactory.getLogger(Frequency.class);
	
	private ReentrantLock 			lock 			= new ReentrantLock();//http请求锁
	
	private final int 				unit_time 		= 500;//每次http请求要求的间隔时间
		
	private final int 				min_interval_time = 10;
	
	private final int 				max_interval_time = 99999;
	
	private volatile long 			last_time 		= 0;//上次http请求的时间
	
	private volatile int 			error_times 	= 0;//http请求连续出错次数
	
	private volatile long 			interval_time 	= 0;//每次http请求要求的间隔时间
	
	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 判断连续错误次数是否超过了3次，如果超过3次，则将请求间隔时间调成50秒
	 * @param current
	 * @return
	 */
	@Override
	public T doFilter(T context) throws Exception {
		
		long t = 0;
		
		do{
			t = canCall(System.currentTimeMillis());
			if(t > 0)
				Thread.sleep(t);
		}
		while(t > 0);
		
		return context;
	}
	
	/**
	 * 判断连续错误次数是否超过了3次，如果超过3次，则将请求间隔时间调成：（错误次数-3）* 间隔系数 秒
	 * @param current
	 * @return 返回需要sleep的时间
	 */
	public long canCall(long current)
	{
		try{
			
			lock.lock();
			
			/**
			 * 计算间隔时间，如果间隔时间<最小值 则间隔时间=最小值
			 */
			interval_time = (error_times-3) * unit_time;
			
			if(interval_time < min_interval_time)
				interval_time = min_interval_time;
			
			if(max_interval_time > 0 && max_interval_time > min_interval_time && interval_time > max_interval_time)
				interval_time = max_interval_time;
			
			log.info("请求间隔：["+current+" - "+last_time+" = "+(current - last_time)+"]");
			
			long time = current - last_time;
			
			if(time >= interval_time)
			{
				last_time = current;
				return 0;
			}
	
			return (interval_time - time);
		}
		finally
		{
			
			lock.unlock();
		}
	}
	
}
