package com.bdx.rainbow.etl.pool;

import java.util.concurrent.atomic.AtomicLong;


/**
 * 线程池监控的MBean
 * @author mler
 *
 */
public interface EtlThreadPoolExecutorMBean {

	public abstract String getPoolName();

	public abstract String getName();

	public abstract int getActivePeak();

	public abstract void setActivePeak(int activePeak);

	public abstract long getActivePeakTime();

	public abstract void setActivePeakTime(long activePeakTime);

	public abstract long getPoolCreateTime();

	public abstract void setPoolCreateTime(long poolCreateTime);

	public abstract int getCorePoolSize();

	public abstract void setCorePoolSize(int corePoolSize);

	public abstract void setMaximumPoolSize(int maximumPoolSize);

	public abstract int getMaximumPoolSize();

	public abstract long getKeepAliveTime();

	public abstract void setKeepAliveTime(long keepAliveTime);

	public abstract boolean isShutdown();

	public abstract int getLargestPoolSize();

	public abstract long getCompletedCount();

	public abstract AtomicLong getSubmitCount();

	public abstract AtomicLong getErrorCount();

	public abstract long getMaxExecuteTime();

	public abstract long getAverageExecuteTime();

	public abstract long getTotalExcuteTime();

	public abstract long getWaitCount();

}
