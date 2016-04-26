package com.bdx.rainbow.mapp.core.monitor;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value="monitor")
public class MonitorSetting {
	
	private long logScanTimeMillis = 3600*1000;
	
    private int logBatchSize = 500*1000;
    
    private long logSleepTimeMillis = 10*1000;
    
    private boolean saveLogTodb = false;
    
    private long maxTimeToLog = 5*1000;
    
    private String debugLevel = "debug";
    
	public long getLogScanTimeMillis() {
		return logScanTimeMillis;
	}
	public void setLogScanTimeMillis(long logScanTimeMillis) {
		this.logScanTimeMillis = logScanTimeMillis;
	}
	public int getLogBatchSize() {
		return logBatchSize;
	}
	public void setLogBatchSize(int logBatchSize) {
		this.logBatchSize = logBatchSize;
	}
	public long getLogSleepTimeMillis() {
		return logSleepTimeMillis;
	}
	public void setLogSleepTimeMillis(long logSleepTimeMillis) {
		this.logSleepTimeMillis = logSleepTimeMillis;
	}
	public boolean isSaveLogTodb() {
		return saveLogTodb;
	}
	public void setSaveLogTodb(boolean saveLogTodb) {
		this.saveLogTodb = saveLogTodb;
	}
	public long getMaxTimeToLog() {
		return maxTimeToLog;
	}
	public void setMaxTimeToLog(long maxTimeToLog) {
		this.maxTimeToLog = maxTimeToLog;
	}
	public String getDebugLevel() {
		return debugLevel;
	}
	public void setDebugLevel(String debugLevel) {
		this.debugLevel = debugLevel;
	}
	
}
