package com.bdx.rainbow.mapp.core.monitor;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bdx.rainbow.mapp.core.annotation.Monitor;
import com.bdx.rainbow.mapp.core.util.JacksonUtils;

@Component
@Monitor(collectTime=1000*60,maxSize=5000,maxTime=1000*60,saveTodb=true)
public class StatInfoLogService implements MonitorService {

	private Logger logger = LoggerFactory.getLogger(StatInfoLogService.class);
	
	@Override
	public void scheduleCollect(MonitorConfig config) {
		try {
			logger.debug("StatInfoLogService["+System.currentTimeMillis()+"] collect :"+JacksonUtils.toJson(config));
			String[] keys = config.getKeys()==null||config.getKeys().length==0?MonitorRegistry.getMonitorKeys():config.getKeys();
			if(keys == null || keys.length == 0)
				return;
			
			for(String key : keys)
			{
				AccessStatLog accessStatLog = MonitorRegistry.collectStat(key);
				if(config.isSaveTodb())
				{
					/** save到数据库的操作 **/
					logger.debug("Save AccessStatLog :"+JacksonUtils.toJson(accessStatLog));
				}
				else
					logger.debug("AccessStatLog :"+JacksonUtils.toJson(accessStatLog));
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
