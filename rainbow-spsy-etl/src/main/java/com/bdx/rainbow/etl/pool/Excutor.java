package com.bdx.rainbow.etl.pool;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.bdx.rainbow.etl.entity.seed.Seed;
import com.bdx.rainbow.etl.task.Seed2Callable;

public abstract class Excutor<S extends Seed, V> {

	protected volatile long 	last_time 		= 0;//上次http请求的时间
	
	protected  AtomicInteger 	error_times 	= new AtomicInteger(0);//http请求连续出错次数
	
	protected volatile long 	interval_time 	= 10;//每次http请求要求的间隔时间
	
	/**
	 * 添加seed任务，由于在dealSeed中可能会新增新的
	 * @param seed
	 */
	public abstract void addSeed(Seed2Callable<S, V> seed);

	/**
	 * 所有的添加seed任务组。
	 * @param seed_collection
	 */
	public abstract void addSeeds(
			Collection<Seed2Callable<S, V>> seed_collection);

	public abstract Map<Seed, Integer> findFailSeed();

	public abstract Map<Seed, Integer> addFailSeed(Seed failSeed);

	public abstract void runRootTask(Seed2Callable<S, V> root_task);

	public abstract void shutDown() throws InterruptedException;

	public long getLast_time() {
		return last_time;
	}

	public void setLast_time(long last_time) {
		this.last_time = last_time;
	}

	public AtomicInteger getError_times() {
		return error_times;
	}

	public void setError_times(AtomicInteger error_times) {
		this.error_times = error_times;
	}

	public long getInterval_time() {
		return interval_time;
	}

	public void setInterval_time(long interval_time) {
		this.interval_time = interval_time;
	}

}