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

@Service
public class BdxConsumerService implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
	public Message<?> receive(String id)
	{
		PollableChannel bdxConsumerChannel = applicationContext.getBean(id+BdxConsumerConfigFactory.CHANNEL_SUFFIX, PollableChannel.class);
		return bdxConsumerChannel.receive();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext  = applicationContext;
	}
}
