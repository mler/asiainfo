package com.bdx.rainbow.kafka;

/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.Message;

import com.bdx.rainbow.kafka.consumer.BdxConsumerConfig;
import com.bdx.rainbow.kafka.consumer.BdxConsumerConfigFactory;
import com.bdx.rainbow.kafka.consumer.BdxConsumerService;
import com.bdx.rainbow.kafka.producer.BdxProducerService;
import com.bdx.rainbow.kafka.producer.KafkaProducerContextFactory;
import com.bdx.rainbow.kafka.producer.ProducerService;

/**
 * @author mler
 */
@Configuration
@ComponentScan({"com.bdx.rainbow"})
@EnableAutoConfiguration
@EnableIntegration
public class Application {
	
	public static void main(String[] args) throws Exception {
		
		ConfigurableApplicationContext context
		= new SpringApplicationBuilder(Application.class)
		.web(false)
		.run(args);
		
//		Application.demoProducer(context);
		
		Application.demoConsumer(context);
		
		
	}
	
	public static void demoConsumer(ConfigurableApplicationContext context) throws Exception
	{
//		ConfigurableApplicationContext context
//		= new SpringApplicationBuilder(Application.class)
//		.web(false)
//		.run(args);
//		
		final String _topic = "px_00001";
		final String groupid = "test11";
		final String zookeeperConnect = "localhost:2181";
		
		//生产者配置，见：http://kafka.apache.org/documentation.html#consumerconfigs
		Properties _props = new Properties();
		_props.put("zookeeper.connect", zookeeperConnect);
		//zk连接超时
		_props.put("zookeeper.session.timeout.ms", "4000");
		_props.put("zookeeper.sync.time.ms", "200");
		_props.put("auto.offset.reset", "smallest");
		_props.put("socket.receive.buffer.bytes", "10485760");
		_props.put("fetch.message.max.bytes", "5242880");
		_props.put("auto.commit.interval.ms", "1000");
		//group 代表一个消费组
		_props.put("group.id", groupid);
		
		Map<String,Integer> topicAndStreamMap = new HashMap<String, Integer>();
		topicAndStreamMap.put(_topic, 1);
		
		Collection<BdxConsumerConfig> configs = new HashSet<BdxConsumerConfig>();
		BdxConsumerConfig cf = new BdxConsumerConfig();
		cf.setGroupId(groupid);
		cf.setMaxMsg(1);
		cf.setTopic(_topic);
		cf.setTopicStreamMap(topicAndStreamMap);
		configs.add(cf);
		
		String adapterName = "162_adapter";
		
		BdxConsumerConfigFactory factory_ = context.getBean("bdxConsumerConfigFactory", BdxConsumerConfigFactory.class);
		factory_.register(adapterName,zookeeperConnect, configs, _props);
		
		BdxConsumerService bdxConsumerService = context.getBean("bdxConsumerService", BdxConsumerService.class);
		AtomicLong count = new AtomicLong(0);
		int i = 1;
		while(true)
		{
			System.out.println("############################# 接收信息 ["+i+"] ##############################");
			
			Message<?> message = bdxConsumerService.receive(adapterName);
		//	Message<Map<String, Map<Integer, List<Object>>>> message = context1.receive();
			if(message != null)
			{
				Map<String, Map<Integer, List<Object>>> content = (Map<String, Map<Integer, List<Object>>>)message.getPayload();
				for(String t:content.keySet())
				{
					Map<Integer, List<Object>> pContent = content.get(t);
					for(Integer pId : pContent.keySet())
					{
						for(Object msg : pContent.get(pId))
						{
		//					Object c = ((Message)msg).getPayload();
							System.out.println(Thread.currentThread().getName()+"  一共："+count.addAndGet(1)+"条-------["+t+","+pId+","+msg+"]------");
							
						}
					}
				}
			}
			
		//	Message<?> msg = bdxJmsService.receive();
		//	System.out.println("---+++++++++++++++++["+msg==null?null:msg.getPayload()+"]+++++++++++++++++---");
			
			System.out.println("############################## 接收结束 ["+i+"] #############################");
			
			
			
			Thread.sleep(1000);
			
			i++;
		}
	}
	
	public static void demoProducer(ConfigurableApplicationContext context) throws Exception
	{
		
		final String topic = "px_00001";
//		final String zookeeperConnect = "localhost:2181";
		final String brokerList = "localhost:9090";
		/**
		  	删除topic
		    1) Stop Kafka server
			2) Delete the topic directory with rm -rf command
			3) Connect to Zookeeper instance
			4) ls /brokers/topics
			5) Remove the topic folder from ZooKeeper using rmr /brokers/topics/yourtopic
			6) Restart Kafka server
			7) Confirm if it was deleted or not by using this command
			   kafka-topics.sh --list --zookeeper yourip:port
		 */
			
		//生产者配置，见：http://kafka.apache.org/documentation.html#producerconfigs
		Properties props = new Properties();
		props.put("linger.ms", "10000");
		props.put("request.required.acks", "1");//
		
		KafkaProducerContextFactory factory = context.getBean("kafkaProducerContextFactory",KafkaProducerContextFactory.class);
		ProducerService producerService = context.getBean("bdxProducerService", BdxProducerService.class);
		
		factory.registerLocal(KafkaProducerContextFactory.DEFAULT_PRODUCER_CONTEXT,brokerList,topic, null, props);
//		factory.registerLocal("testContext","localhost:9090","bdx_topic_3", null, props);
//		
		Thread t1 = new Thread(new TestProducerProcess(KafkaProducerContextFactory.DEFAULT_PRODUCER_CONTEXT, producerService, topic,10000));
//		Thread t2 = new Thread(new TestProducerProcess("testContext", producerService, "bdx_topic_3",50));
//		
		t1.start();
//		t2.start();
		
	}

}