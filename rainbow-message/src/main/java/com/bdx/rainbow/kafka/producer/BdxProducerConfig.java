package com.bdx.rainbow.kafka.producer;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class BdxProducerConfig {
	
	@Bean(name="producerChannel")
	public MessageChannel bdxProducerChannel()
	{
		DirectChannel channel = new DirectChannel();
		return channel;
	}
	
	@ServiceActivator(inputChannel="producerChannel")
	@Bean
	public MessageHandler handler() throws Exception {
//		KafkaProducerMessageHandler handler = new KafkaProducerMessageHandler(kafkaProducerContext);
		BdxKafkaProducerMessageHandler handler = new BdxKafkaProducerMessageHandler();
//		handler.setTopicExpression(new LiteralExpression(this.topic));
//		handler.setMessageKeyExpression(new LiteralExpression(this.messageKey));
//		handler.setPartitionIdExpression(new LiteralExpression(this.messageKey));
		return handler;
	}
	
	@Bean(name="kafkaExecutor")
	public Executor kafkaExecutor()
	{
		Executor executor = new ThreadPoolExecutor(70,70, 3, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
		return executor;
	}
	
	
}
