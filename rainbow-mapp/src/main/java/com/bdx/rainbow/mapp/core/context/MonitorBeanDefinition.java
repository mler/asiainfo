package com.bdx.rainbow.mapp.core.context;


public class MonitorBeanDefinition implements MappBeanDefinition {
	
	private Class<?> monitorClass;
	
	private String methodName;
	
	private String[] keys;
	
	/** 内存日志存在的最大时间,超过这个时间将日志进行操作 **/
	private long maxTime;
	
	/** 内存日志存在的最大占用内存大小，超过这个大小将对日志进行处理 **/
    private long maxSize;
    
    /** 监控统计信息采集间隔，对上面2项进行检测的间隔时间 **/
    private long collectTime;
    
    /**日志是否保存到数据库，操作时是否保存到数据库 **/
    private boolean saveTodb;

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	public long getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(long maxTime) {
		this.maxTime = maxTime;
	}

	public long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

	public long getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(long collectTime) {
		this.collectTime = collectTime;
	}

	public boolean isSaveTodb() {
		return saveTodb;
	}

	public void setSaveTodb(boolean saveTodb) {
		this.saveTodb = saveTodb;
	}

	public Class<?> getMonitorClass() {
		return monitorClass;
	}

	public void setMonitorClass(Class<?> monitorClass) {
		this.monitorClass = monitorClass;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
