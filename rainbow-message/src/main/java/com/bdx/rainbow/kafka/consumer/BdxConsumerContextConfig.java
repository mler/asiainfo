package com.bdx.rainbow.kafka.consumer;

import java.util.Properties;
import java.util.Set;

public class BdxConsumerContextConfig {

	private String zkConnect;
	
	private Properties props;
	
	private Set<BdxConsumerConfig> configs;

	public String getZkConnect() {
		return zkConnect;
	}

	public void setZkConnect(String zkConnect) {
		this.zkConnect = zkConnect;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public Set<BdxConsumerConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(Set<BdxConsumerConfig> configs) {
		this.configs = configs;
	}
	
	
	
}
