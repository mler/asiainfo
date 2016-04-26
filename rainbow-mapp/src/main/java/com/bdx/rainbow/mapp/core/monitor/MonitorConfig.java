package com.bdx.rainbow.mapp.core.monitor;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MonitorConfig implements Serializable {

	/** 对应要监控的缓存的key **/
	private String[] keys;
	
	/** 内存日志存在的最大时间,超过这个时间将日志进行操作 **/
	private long maxTime = 3600*1000;
	
	/** 内存日志存在的最大占用内存大小，超过这个大小将对日志进行处理 **/
	private long maxSize = 100*1000;
    
    /**日志是否保存到数据库，操作时是否保存到数据库 **/
    private boolean saveTodb = false;

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

	public boolean isSaveTodb() {
		return saveTodb;
	}

	public void setSaveTodb(boolean saveTodb) {
		this.saveTodb = saveTodb;
	}

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}
}
