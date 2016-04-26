package com.bdx.rainbow.kafka.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import kafka.consumer.ConsumerConfig;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.kafka.support.ConsumerConfiguration;
import org.springframework.integration.kafka.support.ConsumerConnectionProvider;
import org.springframework.integration.kafka.support.ConsumerMetadata;
import org.springframework.integration.kafka.support.KafkaConsumerContext;
import org.springframework.integration.kafka.support.MessageLeftOverTracker;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

//@Configuration
//@ComponentScan(basePackageClasses=com.bdx.rainbow.kafka.consumer.ConsumerConfig.class)
////@EnableAutoConfiguration
//@EnableIntegration
//@Service
public class KafkaConsumerContextFactory implements ApplicationContextAware
{
	private ApplicationContext context;
	
	private static Logger logger = LoggerFactory.getLogger(KafkaConsumerContextFactory.class);

	private static Map<String,KafkaConsumerContext<?,?>> consumerContextMap = new ConcurrentHashMap<String, KafkaConsumerContext<?,?>>();
	
	private static Lock lock = new ReentrantLock();
	
	private KafkaConsumerContext<?,?> kafkaConsumerContext;
	
	public final static String DEFAULT_CONSUMER_CONTEXT = "default_consumer_context";
	
	public KafkaConsumerContextFactory() throws Exception {
		super();
		checkAndInstance(DEFAULT_CONSUMER_CONTEXT);
		kafkaConsumerContext = consumerContextMap.get(DEFAULT_CONSUMER_CONTEXT);
		logger.info("KafkaConsumerContext["+DEFAULT_CONSUMER_CONTEXT+"] init successed");
	}
	
//	private void init() throws Exception
//	{
//		final String topic = "bdx_topic_2";
//		final String groupid = "test5";
//		
//		//生产者配置，见：http://kafka.apache.org/documentation.html#consumerconfigs
//		Properties props = new Properties();
//		props.put("zookeeper.connect", "localhost:2181");
//        //zk连接超时
//        props.put("zookeeper.session.timeout.ms", "4000");
//        props.put("zookeeper.sync.time.ms", "200");
//		props.put("auto.offset.reset", "smallest");
//		props.put("socket.receive.buffer.bytes", "10485760");
//		props.put("fetch.message.max.bytes", "5242880");
//		props.put("auto.commit.interval.ms", "1000");
//		//group 代表一个消费组
//        props.put("group.id", groupid);
//		
//		Map<String,Integer> topicAndStreamMap = new HashMap<String, Integer>();
//		topicAndStreamMap.put(topic, 1);
//		
////		KafkaConsumerContextFactory factory = context.getBean("kafkaConsumerContextFactory", KafkaConsumerContextFactory.class);
//		register(KafkaConsumerContextFactory.DEFAULT_CONSUMER_CONTEXT,topic,groupid, topicAndStreamMap, props);
//	}
	
	/**
	 * 初始化上下文
	 * @param contextid
	 * @return
	 */
	private static void checkAndInstance(String contextid)
	{
		lock.lock();
		
		try{
			if(consumerContextMap.get(contextid) == null)
			{
				KafkaConsumerContext _kafkaConsumerContext = new KafkaConsumerContext();
				_kafkaConsumerContext.setConsumerConfigurations(new ConcurrentHashMap<String,ConsumerConfiguration<?,?>>());
				consumerContextMap.put(contextid, _kafkaConsumerContext);
				logger.info("KafkaConsumerContext[id="+contextid+"] init successed");
			}
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	/**
	 * 
	 * @param contextid 上下文的id，获取从KafakContextFactory中获取kafkaContext使用
	 * @param topic 消息的topic
	 * @param configuration topic消息对应的配置
	 * @return
	 * @throws Exception
	 */
	private static  KafkaConsumerContext<?,?> refreshContext(String contextid,ConsumerConfiguration<?,?> configuration) throws Exception
	{
		if(StringUtils.isBlank(contextid))
			throw new Exception("kafkaContext的contextid不能为空");
		
		if(configuration == null)
			throw new Exception("ConsumerConfiguration 配置不能为空");
		
		if(configuration.getConsumerMetadata() == null)
			throw new Exception("ConsumerConfiguration 中 ConsumerMetadata 信息不能为空");
		
		if(consumerContextMap.get(contextid) == null)
			checkAndInstance(contextid);
			
		KafkaConsumerContext kafkaConsumerContext = consumerContextMap.get(contextid);
		kafkaConsumerContext.getConsumerConfigurations().put(configuration.getConsumerMetadata().getTopic(), configuration);
		logger.info("KafkaConsumerContext refresh successed");
		
		return getConsumerContext(contextid);
	}
	
	
	public static KafkaConsumerContext<?,?> register(String contextid,String topic,String groupId,Map<String,Integer> topicStreamMap,Properties props) throws Exception 
	{
		try{
			ConsumerMetadata<String,String> consumerMetadata = new ConsumerMetadata<>();
			consumerMetadata.setGroupId(groupId);
			consumerMetadata.setTopicStreamMap(topicStreamMap);
			consumerMetadata.setKeyDecoder(new StringDecoder(new VerifiableProperties()));
			consumerMetadata.setValueDecoder(new StringDecoder(new VerifiableProperties()));
			consumerMetadata.setTopic(topic);
			
			ConsumerConfig config = new ConsumerConfig(props);
			ConsumerConfiguration<String,String> consumerConfiguration =
					new ConsumerConfiguration<>(consumerMetadata, new ConsumerConnectionProvider(config) ,new MessageLeftOverTracker());
			consumerConfiguration.setMaxMessages(1);
			consumerConfiguration.setExecutor(kafkaExecutor());
			
			refreshContext(contextid, consumerConfiguration);
			
			return getConsumerContext(contextid);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("ConsumerConfig register fail", e);
			throw e;
		}
	}
	
	private static Executor kafkaExecutor()
	{
		Executor executor = new ThreadPoolExecutor(70,70, 3, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
		return executor;
	}

	
	public static KafkaConsumerContext<?, ?> getConsumerContext(String contextid)
	{
		if(StringUtils.isBlank(contextid))
			return consumerContextMap.get(DEFAULT_CONSUMER_CONTEXT);
		
		return consumerContextMap.get(contextid);
	}
	
	public void consumer(ConsumerService consumerService) throws Exception
	{
		final String topic = "bdx_topic_2";
		final String groupid = "test5";
		
		//生产者配置，见：http://kafka.apache.org/documentation.html#consumerconfigs
		Properties props = new Properties();
		props.put("zookeeper.connect", "localhost:2181");
        //zk连接超时
        props.put("zookeeper.session.timeout.ms", "4000");
        props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.offset.reset", "smallest");
		props.put("socket.receive.buffer.bytes", "10485760");
		props.put("fetch.message.max.bytes", "5242880");
		props.put("auto.commit.interval.ms", "1000");
		//group 代表一个消费组
        props.put("group.id", groupid);
		
		Map<String,Integer> topicAndStreamMap = new HashMap<String, Integer>();
		topicAndStreamMap.put(topic, 1);
		
//		KafkaConsumerContextFactory factory = context.getBean("kafkaConsumerContextFactory", KafkaConsumerContextFactory.class);
		register(KafkaConsumerContextFactory.DEFAULT_CONSUMER_CONTEXT,topic,groupid, topicAndStreamMap, props);
		
//		BdxConsumerService bdxConsumerService = context.getBean("bdxConsumerService", BdxConsumerService.class);
		AtomicLong count = new AtomicLong(0);
		int i = 1;
		while(true)
		{
			System.out.println("############################# 接收信息 ["+i+"] ##############################");
			
			Message<?> message = consumerService.receive();
//			Message<Map<String, Map<Integer, List<Object>>>> message = context1.receive();
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
//							Object c = ((Message)msg).getPayload();
							System.out.println(Thread.currentThread().getName()+"  一共："+count.addAndGet(1)+"条-------["+t+","+pId+","+msg+"]------");
							
						}
					}
				}
			}
			
//			Message<?> msg = bdxJmsService.receive();
//			System.out.println("---+++++++++++++++++["+msg==null?null:msg.getPayload()+"]+++++++++++++++++---");
			
			System.out.println("############################## 接收结束 ["+i+"] #############################");
			
//			Thread.sleep(10000);
			
			i++;
		}
	}
	
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context
				= new SpringApplicationBuilder(KafkaConsumerContextFactory.class)
				.web(false)
				.run(args);
		
		final String topic = "bdx_topic_3";
		final String groupid = "test5";
		
		//生产者配置，见：http://kafka.apache.org/documentation.html#consumerconfigs
		Properties props = new Properties();
		props.put("zookeeper.connect", "localhost:2181");
        //zk连接超时
        props.put("zookeeper.session.timeout.ms", "4000");
        props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.offset.reset", "smallest");
		props.put("socket.receive.buffer.bytes", "10485760");
		props.put("fetch.message.max.bytes", "5242880");
		props.put("auto.commit.interval.ms", "1000");
		//group 代表一个消费组
        props.put("group.id", groupid);
		
		Map<String,Integer> topicAndStreamMap = new HashMap<String, Integer>();
		topicAndStreamMap.put(topic, 1);
		
		KafkaConsumerContextFactory factory = context.getBean("kafkaConsumerContextFactory", KafkaConsumerContextFactory.class);
		factory.register(DEFAULT_CONSUMER_CONTEXT,topic,groupid, topicAndStreamMap, props);
		
		BdxConsumerService bdxConsumerService = context.getBean("bdxConsumerService", BdxConsumerService.class);
		AtomicLong count = new AtomicLong(0);
		int i = 1;
		while(true)
		{
			System.out.println("############################# 接收信息 ["+i+"] ##############################");
			
			Message<?> message = bdxConsumerService.receive(topic);
//			Message<Map<String, Map<Integer, List<Object>>>> message = context1.receive();
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
//							Object c = ((Message)msg).getPayload();
							System.out.println(Thread.currentThread().getName()+"  一共："+count.addAndGet(1)+"条-------["+t+","+pId+","+msg+"]------");
							
						}
					}
				}
			}
			
//			Message<?> msg = bdxJmsService.receive();
//			System.out.println("---+++++++++++++++++["+msg==null?null:msg.getPayload()+"]+++++++++++++++++---");
			
			System.out.println("############################## 接收结束 ["+i+"] #############################");
			
			
			
			Thread.sleep(10000);
			
			i++;
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		// TODO Auto-generated method stub
		this.context = context;
	}
	
}
