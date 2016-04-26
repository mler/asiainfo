package com.bdx.rainbow.mapp.core.monitor;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bdx.rainbow.mapp.core.annotation.Monitor;
import com.bdx.rainbow.mapp.core.util.JacksonUtils;

@Component
@Monitor(collectTime=30*1000,maxSize=5000,maxTime=1000*60,saveTodb=true,keys={})
public class AccessLogService implements MonitorService {
	
	private Logger logger = LoggerFactory.getLogger(AccessLogService.class);
	
	@Override
	public void scheduleCollect(MonitorConfig config) {
		try {
			logger.debug("AccessLogService["+System.currentTimeMillis()+"] collect :"+JacksonUtils.toJson(config));
			String[] keys = config.getKeys()==null||config.getKeys().length==0?MonitorRegistry.getMonitorKeys():config.getKeys();
			
			for(String key : keys)
			{
				List<AccessLog> logs = MonitorRegistry.collectLog(key);
				for(AccessLog log : logs)
				{
					if(config.isSaveTodb())
						logger.debug("Save AccessLog :"+JacksonUtils.toJson(log));
					else
						logger.debug("AccessLog :"+JacksonUtils.toJson(log));
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void lookup(String bizcode, String version) {
		// TODO Auto-generated method stub

	}

}
