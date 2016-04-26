package com.bdx.rainbow.kafka.consumer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.kafka.BdxJmsContext;

public class AbstractBdxConsumerService implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
	public Message<?> receive(String adapterName)
	{
		PollableChannel bdxConsumerChannel = applicationContext.getBean(adapterName+BdxConsumerConfigFactory.CHANNEL_SUFFIX, PollableChannel.class);
		return bdxConsumerChannel.receive();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext  = applicationContext;
	}
}
