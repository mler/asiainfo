package com.bdx.rainbow.mapp.core.monitor;

public class MonitorCollectSchedule implements Runnable {

	private MonitorService monitorService;
	
	private MonitorConfig config;
	
	public MonitorCollectSchedule(MonitorService monitorService,MonitorConfig config) {
		super();
		this.monitorService = monitorService;
		this.config = config;
	}

	@Override
	public void run() {
		monitorService.scheduleCollect(config);
	}

}
