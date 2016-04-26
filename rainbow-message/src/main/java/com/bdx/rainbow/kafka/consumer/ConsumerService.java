package com.bdx.rainbow.kafka.consumer;

import org.springframework.messaging.Message;

public interface ConsumerService {

	public abstract Message<?> receive();

}