package com.bdx.rainbow.kafka.consumer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import kafka.consumer.ConsumerConfig;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.LifecycleProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.DefaultLifecycleProcessor;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.endpoint.AbstractPollingEndpoint;
import org.springframework.integration.endpoint.PollingConsumer;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.integration.kafka.inbound.KafkaHighLevelConsumerMessageSource;
import org.springframework.integration.kafka.support.ConsumerConfiguration;
import org.springframework.integration.kafka.support.ConsumerConnectionProvider;
import org.springframework.integration.kafka.support.ConsumerMetadata;
import org.springframework.integration.kafka.support.KafkaConsumerContext;
import org.springframework.integration.kafka.support.MessageLeftOverTracker;
import org.springframework.integration.kafka.support.ZookeeperConnect;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Configuration
@Service
public class BdxConsumerConfigFactory implements BeanFactoryAware {

	private Logger logger = LoggerFactory.getLogger(BdxConsumerConfigFactory.class);
	
	private BeanFactory beanFactory;
	
	public static final String CHANNEL_SUFFIX = "^^channel^^";

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}
	
	public static KafkaConsumerContext<?,?> kafkaConsumerContext(String zkConnect, Collection<BdxConsumerConfig> configs,Properties props) throws Exception 
	{
		if(configs == null || configs.isEmpty())
			throw new Exception("no BdxConsumerConfig is empty");
		
		Map<String,ConsumerConfiguration<?,?>> configMap = new HashMap<String,ConsumerConfiguration<?,?>>();
		
		for(BdxConsumerConfig config : configs)
		{
			configMap.put(config.getTopic(), kafkaConsumerConfigturation(config,props));
		}
		
		KafkaConsumerContext context = new KafkaConsumerContext<>();
		context.setConsumerConfigurations(configMap);
		context.setConsumerTimeout("30000");
		if(StringUtils.isBlank(zkConnect) == false)
			context.setZookeeperConnect(new ZookeeperConnect(zkConnect));
		
		return context;
	}
	
	private static ConsumerMetadata<?,?> consumerMetadata(BdxConsumerConfig config) 
	{
		ConsumerMetadata<String,String> consumerMetadata = new ConsumerMetadata<>();
		consumerMetadata.setGroupId(config.getGroupId());
		consumerMetadata.setTopicStreamMap(config.getTopicStreamMap());
		consumerMetadata.setKeyDecoder(new StringDecoder(new VerifiableProperties()));
		consumerMetadata.setValueDecoder(new StringDecoder(new VerifiableProperties()));
		consumerMetadata.setTopic(config.getTopic());
		
		return consumerMetadata;
	}
	
	private static ConsumerConnectionProvider bdxConsumerConnectionProvider(Properties props)
	{
		ConsumerConnectionProvider config = new ConsumerConnectionProvider(new ConsumerConfig(props));
		return config;
	}
	
	private static ConsumerConfiguration<?,?> kafkaConsumerConfigturation(BdxConsumerConfig config,Properties props) throws Exception 
	{
		@SuppressWarnings("unchecked")
		ConsumerConfiguration<?, ?> consumerConfiguration = new ConsumerConfiguration(
				consumerMetadata(config), bdxConsumerConnectionProvider(props),
				new MessageLeftOverTracker<>());
		consumerConfiguration.setMaxMessages(config.getMaxMsg());
		consumerConfiguration.setExecutor(kafkaExecutor());
		
		return consumerConfiguration;
	}
	
	private static Executor kafkaExecutor()
	{
		Executor executor = new ThreadPoolExecutor(70,70, 3, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
		return executor;
	}
	
	public KafkaHighLevelConsumerMessageSource<?,?> create(String zkConnect, Collection<BdxConsumerConfig> configs,Properties props) throws Exception
	{
		KafkaHighLevelConsumerMessageSource<?,?> kafkaHighLevelConsumerMessageSource =
				new KafkaHighLevelConsumerMessageSource<>(kafkaConsumerContext(zkConnect,configs,props));
		return kafkaHighLevelConsumerMessageSource;
	}
	
	public void register(String adapterName,String zkConnect, Collection<BdxConsumerConfig> configs,Properties props) throws Exception
	{
		if(StringUtils.isBlank(adapterName))
			adapterName = System.currentTimeMillis()+"";
		
		SourcePollingChannelAdapter adapter = new SourcePollingChannelAdapter();
		PollableChannel channle = new QueueChannel();
		((ConfigurableListableBeanFactory)beanFactory).registerSingleton(adapterName+CHANNEL_SUFFIX, channle);
		adapter.setOutputChannel(channle);
		adapter.setSource(create(zkConnect, configs, props));
//		adapter.setTaskExecutor(kafkaExecutor());
//		this.configurePollingEndpoint(adapter, annotations);
		//poller設置
		
//		PollerMetadata pollerMetadata = PollerMetadata.getDefaultPollerMetadata(this.beanFactory);
//		if(pollerMetadata == null)
//		{
//			logger.warn("the poller is null，there is no defaultPollerMetadata for SourcePollingChannelAdapter["+adapterName+"]");
////			Assert.notNull(pollerMetadata, "No poller has been defined for Annotation-based endpoint, " +
////				"and no default poller is available within the context.");
//		}
//		else
//		{
//			adapter.setTrigger(pollerMetadata.getTrigger());
//			adapter.setTaskExecutor(pollerMetadata.getTaskExecutor());
//			adapter.setTrigger(pollerMetadata.getTrigger());
//			adapter.setAdviceChain(pollerMetadata.getAdviceChain());
//			adapter.setMaxMessagesPerPoll(pollerMetadata.getMaxMessagesPerPoll());
//			adapter.setErrorHandler(pollerMetadata.getErrorHandler());
////			if (((AbstractPollingEndpoint)adapter) instanceof PollingConsumer) {
////				((PollingConsumer) adapter).setReceiveTimeout(pollerMetadata.getReceiveTimeout());
////			}
//			adapter.setTransactionSynchronizationFactory(pollerMetadata.getTransactionSynchronizationFactory());
//		}
		((ConfigurableListableBeanFactory)beanFactory).registerSingleton(adapterName, adapter);
			adapter.setBeanFactory(beanFactory);
		//刷新非自動啟動的lifecycle
		getLifecycleProcessor().onRefresh();
	}
	
	public LifecycleProcessor getLifecycleProcessor()
	{
		LifecycleProcessor lifecycleProcessor =null;
		
		ConfigurableListableBeanFactory factory = (ConfigurableListableBeanFactory)beanFactory;
		if (factory.containsLocalBean(AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME)) {
			lifecycleProcessor = factory.getBean(AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME, LifecycleProcessor.class);
			if (logger.isDebugEnabled()) {
				logger.debug("Using LifecycleProcessor [" + lifecycleProcessor + "]");
			}
		}
		else {
			DefaultLifecycleProcessor defaultProcessor = new DefaultLifecycleProcessor();
			defaultProcessor.setBeanFactory(factory);
			lifecycleProcessor = defaultProcessor;
			factory.registerSingleton(AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME, lifecycleProcessor);
			if (logger.isDebugEnabled()) {
				logger.debug("Unable to locate LifecycleProcessor with name '" +
						AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME +
						"': using default [" + lifecycleProcessor + "]");
			}
		}
		return lifecycleProcessor;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
