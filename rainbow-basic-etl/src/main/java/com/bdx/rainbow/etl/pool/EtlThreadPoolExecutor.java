package com.bdx.rainbow.etl.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 給线程池增加一个name属性
 * 
 * @author mler
 *
 */
public class EtlThreadPoolExecutor extends ThreadPoolExecutor implements EtlThreadPoolExecutorMBean {
	
	private Logger log = LoggerFactory.getLogger(EtlThreadPoolExecutor.class);
	
	protected int                              	activePeak              = 0;
	protected long                             	activePeakTime          = 0;
    protected long							   	poolCreateTime 		   	= 0;//线程池生成时间
    protected AtomicLong 					   	submitCount 		   	= new AtomicLong(0);//提交总数量
	protected AtomicLong 						errorCount 	   	   		= new AtomicLong(0);//错误数量
    protected long								maxExecuteTime			= 0;//最长执行时间	
    protected long								averageExecuteTime		= 0;//执行平均时间
    protected long								totalExcuteTime			= 0;//执行总时间
    protected long								runningCount			= 0;//执行中的数量
	
	private final String poolName;

	public EtlThreadPoolExecutor(String poolName, int corePoolSize,
			int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				handler);
		this.poolName = poolName;
		this.poolCreateTime = System.currentTimeMillis();
	}

	public EtlThreadPoolExecutor(String poolName, int corePoolSize,
			int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		this.poolName = poolName;
		this.poolCreateTime = System.currentTimeMillis();
	}

	public EtlThreadPoolExecutor(String poolName, int corePoolSize,
			int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				threadFactory);
		this.poolName = poolName;
		this.poolCreateTime = System.currentTimeMillis();
	}

	public EtlThreadPoolExecutor(String poolName, int corePoolSize,
			int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
			RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				threadFactory, handler);
		this.poolName = poolName;
		this.poolCreateTime = System.currentTimeMillis();
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getPoolName()
	 */
	@Override
	public String getPoolName() {
		return poolName;
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getName()
	 */
	@Override
	public String getName() {
		return this.poolName;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void beforeExecute(Thread thread,Runnable runnable)
	{
		
//		if(runnable instanceof Seed2Callable)
//		{
//			Seed2Callable seed = (Seed2Callable)runnable;
//			seed.setStartTime(System.currentTimeMillis());
			/** 线程执行runWork针对线程是有锁的，所以是线程安全的 **/
			int activeCount = getActiveCount();
			if(this.activePeak < activeCount)
			{
				this.activePeak = activeCount;
				this.activePeakTime = System.currentTimeMillis();
			}
			
//		}
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void afterExecute(Runnable runnable,Throwable e)
	{
//		if(runnable instanceof Seed2Callable)
//		{
//		QueueingFuture seed = (QueueingFuture)runnable;
			totalExcuteTime = System.currentTimeMillis()-poolCreateTime;
//			long completed_time =System.currentTimeMillis()-seed.getStartTime();
			if(getCompletedCount() > 0)
				averageExecuteTime = totalExcuteTime/getCompletedCount();
			
//			log.info(seed.getName()+" 线程开始时间："+seed.getStartTime()+", 完成时间："+completed_time);
//		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getActivePeak()
	 */
	@Override
	public int getActivePeak() {
		return activePeak;
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#setActivePeak(int)
	 */
	@Override
	public void setActivePeak(int activePeak) {
		this.activePeak = activePeak;
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getActivePeakTime()
	 */
	@Override
	public long getActivePeakTime() {
		return activePeakTime;
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#setActivePeakTime(long)
	 */
	@Override
	public void setActivePeakTime(long activePeakTime) {
		this.activePeakTime = activePeakTime;
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getPoolCreateTime()
	 */
	@Override
	public long getPoolCreateTime() {
		return poolCreateTime;
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#setPoolCreateTime(long)
	 */
	@Override
	public void setPoolCreateTime(long poolCreateTime) {
		this.poolCreateTime = poolCreateTime;
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getCorePoolSize()
	 */
	@Override
	public int getCorePoolSize() {
		return super.getCorePoolSize();
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#setCorePoolSize(int)
	 */
	@Override
	public void setCorePoolSize(int corePoolSize) {
		super.setCorePoolSize(corePoolSize);
	}
	
	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#setMaximumPoolSize(int)
	 */
	@Override
	public void setMaximumPoolSize(int maximumPoolSize) {
		super.setMaximumPoolSize(maximumPoolSize);
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getMaximumPoolSize()
	 */
	@Override
	public int getMaximumPoolSize() {
		return super.getMaximumPoolSize();
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getKeepAliveTime()
	 */
	@Override
	public long getKeepAliveTime() {
		return super.getKeepAliveTime(TimeUnit.MICROSECONDS);
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#setKeepAliveTime(long)
	 */
	@Override
	public void setKeepAliveTime(long keepAliveTime) {
		super.setKeepAliveTime(keepAliveTime, TimeUnit.MICROSECONDS);
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#isShutdown()
	 */
	@Override
	public boolean isShutdown() {
		return super.isShutdown();
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getLargestPoolSize()
	 */
	@Override
	public int getLargestPoolSize() {
		return super.getLargestPoolSize();
	}
	
	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getCompletedCount()
	 */
	@Override
	public long getCompletedCount() {
		return super.getCompletedTaskCount();
	}
	
	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getSubmitCount()
	 */
	@Override
	public AtomicLong getSubmitCount() {
		return submitCount;
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getErrorCount()
	 */
	@Override
	public AtomicLong getErrorCount() {
		return errorCount;
	}
	
	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getMaxExecuteTime()
	 */
	@Override
	public long getMaxExecuteTime() {
		return maxExecuteTime;
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getAverageExecuteTime()
	 */
	@Override
	public long getAverageExecuteTime() {
		return averageExecuteTime;
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getTotalExcuteTime()
	 */
	@Override
	public long getTotalExcuteTime() {
		return totalExcuteTime;
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.EtlThreadPoolExecutorMBean1#getWaitCount()
	 */
	@Override
	public long getWaitCount() {
		return super.getQueue().size();
	}
	
}
