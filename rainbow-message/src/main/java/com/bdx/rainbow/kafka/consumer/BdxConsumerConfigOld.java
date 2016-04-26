package com.bdx.rainbow.kafka.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.kafka.inbound.KafkaHighLevelConsumerMessageSource;
import org.springframework.messaging.PollableChannel;

@Configuration
public class BdxConsumerConfigOld {

	private BdxConsumerConfigFactory factory;
	
	/**
	 * 消息输出管道
	 * @return
	 */
	@Bean(name="consumerChannel")
	public PollableChannel bdxConsumerChannel() {
		return new QueueChannel();
	}
	
//	@Bean
//	@InboundChannelAdapter(value="consumerChannel",poller=@Poller(fixedRate="10",maxMessagesPerPoll="5000"))
//	public KafkaHighLevelConsumerMessageSource<?,?> adapter() {
//		KafkaHighLevelConsumerMessageSource<?,?> kafkaHighLevelConsumerMessageSource =
////				new KafkaHighLevelConsumerMessageSource<>(BdxConsumerConfigFactory.kafkaConsumerContext(zkConnect, configs, props)(config, props)));
//		return kafkaHighLevelConsumerMessageSource;
//	}
	
}
