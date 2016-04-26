package com.bdx.rainbow.mapp.core.monitor;

public interface MonitorService {

	public void scheduleCollect(MonitorConfig config);
	
	public void lookup(String bizcode,String version);
	
}
