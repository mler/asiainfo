package com.bdx.rainbow.etl.task;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdx.rainbow.common.util.HttpClientUtil;
import com.bdx.rainbow.etl.analyze.Analyze;
import com.bdx.rainbow.etl.dispatcher.Dispatcher;
import com.bdx.rainbow.etl.entity.seed.HttpSeed;
import com.bdx.rainbow.etl.entity.seed.Seed;
import com.bdx.rainbow.etl.filter.FilterAware;
import com.bdx.rainbow.etl.pool.Excutor;
import com.bdx.rainbow.etl.pool.LocalTaskExcutor;

/**
 * 处理种子的线程
 * @author mler
 * 
 * T为传入的Seed类型
 * V为返回类型
 * @param <V>
 */
public abstract class Seed2Callable<T extends Seed,V> extends FilterAware<Collection<T>> implements Callable<V> {

	public Logger log = LoggerFactory.getLogger(Seed2Callable.class);
	
	protected long 							startTime;//开始时间
	
	protected long 							endTime;//结束时间
	
	protected String 						name;//线程名称
	
	protected Collection<T> 				seeds;//执行的种子
	
	protected Excutor<T, V> 				executor; //执行的线程池
	
	protected Class<? extends Analyze> 		clazz;//解析器
	
	protected Collection<T> 				fail = new HashSet<T>();
	
	protected Dispatcher<T,V> dispatcher;
	
	public Seed2Callable(Collection<T> seeds) {
		super();
		this.seeds = seeds;
	}

	public void setExecutor(Excutor<T, V> executor) {
		this.executor = executor;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dispatcher<T, V> getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Dispatcher<T, V> dispatcher) {
		this.dispatcher = dispatcher;
	}

	public abstract V execute(Collection<T> seed) throws Exception;
	
	@Override
	public V call() throws Exception{
		
		//TODO filter处理
//		seeds = doFilter(seeds);
		
		return execute(seeds);
		
	}
	
}
