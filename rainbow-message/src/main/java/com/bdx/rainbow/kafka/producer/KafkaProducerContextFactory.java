package com.bdx.rainbow.kafka.producer;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import kafka.producer.Partitioner;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.kafka.support.KafkaProducerContext;
import org.springframework.integration.kafka.support.ProducerConfiguration;
import org.springframework.integration.kafka.support.ProducerFactoryBean;
import org.springframework.integration.kafka.support.ProducerMetadata;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerContextFactory implements ApplicationContextAware {

	private  ApplicationContext context;
	
	private  Logger logger = LoggerFactory.getLogger(KafkaProducerContextFactory.class);
	
	private static Map<String,KafkaProducerContext> producerContext = new ConcurrentHashMap<String, KafkaProducerContext>();
	
	private  KafkaProducerContext kafkaProducerContext;
	
	public final static String DEFAULT_PRODUCER_CONTEXT = "default_producer_context";
	
	private  Lock lock = new ReentrantLock();  
	
	public KafkaProducerContextFactory() {
		super();
		checkAndInstance(DEFAULT_PRODUCER_CONTEXT);
		kafkaProducerContext = producerContext.get(DEFAULT_PRODUCER_CONTEXT);
	}
	
	public static KafkaProducerContext getContext(String contextid)
	{
		return producerContext.get(contextid);
	}
	
	/**
	 * 初始化上下文
	 * @param contextid
	 * @return
	 */
	private  void checkAndInstance(String contextid)
	{
		lock.lock();
		
		try{
			if(producerContext.get(contextid) == null)
			{
				KafkaProducerContext _kafkaProducerContext = new KafkaProducerContext();
				_kafkaProducerContext.setProducerConfigurations(new ConcurrentHashMap<String,ProducerConfiguration<?,?>>());
				producerContext.put(contextid, _kafkaProducerContext);
				logger.info("KafkaProducerContext[id="+contextid+"] init successed");
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
	private  KafkaProducerContext refreshContext(String contextid,ProducerConfiguration<?,?> configuration) throws Exception
	{
		if(StringUtils.isBlank(contextid))
			throw new Exception("kafkaContext的contextid不能为空");
		
		if(configuration == null)
			throw new Exception("ProducerConfiguration 配置不能为空");
		
		if(configuration.getProducerMetadata() == null)
			throw new Exception("ProducerConfiguration 中 ProducerMetadata 信息不能为空");
		/**
		KafkaProducerContext kafkaProducerContext = context.getBean(contextid,KafkaProducerContext.class);
		if(kafkaProducerContext == null)
			register(contextid,null);
		else
		{
			kafkaProducerContext.getProducerConfigurations().put(configuration.getProducerMetadata().getTopic(), configuration);
			register(contextid,kafkaProducerContext.getProducerConfigurations());
		}
		
		return context.getBean(contextid,KafkaProducerContext.class);
		**/
		
		if(producerContext.get(contextid) == null)
			checkAndInstance(contextid);
			
		KafkaProducerContext kafkaProducerContext = producerContext.get(contextid);
		kafkaProducerContext.getProducerConfigurations().put(configuration.getProducerMetadata().getTopic(), configuration);
		logger.info("KafkaProducerContext refresh successed");
		
		return producerContext.get(contextid);
	}
	
	private void registerSpringApplicationContext(String contextid,String brokerAddress,String topic,Partitioner partitioner,Properties props)
	{
		//将applicationContext转换为ConfigurableApplicationContext  
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;  
				  
		// 获取bean工厂并转换为DefaultListableBeanFactory  
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();  
		
		
		
		// 通过BeanDefinitionBuilder创建bean定义
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
				.genericBeanDefinition(KafkaProducerContext.class);
		// 设置属性ProducerConfigurations,此属性引用已经定义的bean:userAcctDAO
//		if(producerConfigurations == null)
			beanDefinitionBuilder
				.addPropertyValue("producerConfigurations",new ConcurrentHashMap<String, ProducerConfiguration<?, ?>>());
//		else
//			beanDefinitionBuilder.addPropertyValue("producerConfigurations",producerConfigurations);

		// 注册bean  
		defaultListableBeanFactory.registerBeanDefinition("contextid",beanDefinitionBuilder.getRawBeanDefinition());		
		logger.info("KafkaProducerContext [id="+contextid+"] register to ApplicationContext successed");
	
	}
	
	public  KafkaProducerContext registerLocal(String contextid,String brokerAddress,String topic,Partitioner partitioner,Properties props) throws Exception 
	{
		try{
			ProducerMetadata<String,String> producerMetadata = new ProducerMetadata<>(topic, String.class,
					String.class, new StringSerializer(), new StringSerializer());
			producerMetadata.setPartitioner(partitioner);
			
			//生产者配置，见：http://kafka.apache.org/documentation.html#producerconfigs
			ProducerFactoryBean<String,String> producer =
					new ProducerFactoryBean<>(producerMetadata, brokerAddress, props);
			
			ProducerConfiguration<String,String> config =
					new ProducerConfiguration<>(producerMetadata, producer.getObject());
			
			refreshContext(contextid,config);
			
			return producerContext.get(contextid);
		}
		catch(Exception e)
		{
			logger.error("ProducerConfig register fail", e);
			throw e;
		}
	}
	
	public void registerBean()
	{
		//将applicationContext转换为ConfigurableApplicationContext  
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;  
		  
		// 获取bean工厂并转换为DefaultListableBeanFactory  
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();  
		  
		// 通过BeanDefinitionBuilder创建bean定义  
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder  
		        .genericBeanDefinition(BdxProducerService.class);  
		// 设置属性userAcctDAO,此属性引用已经定义的bean:userAcctDAO  
//		beanDefinitionBuilder  
//		        .addPropertyReference("userAcctDAO", "UserAcctDAO");  
		  
		// 注册bean  
		defaultListableBeanFactory.registerBeanDefinition("bdxProducerService",  
		        beanDefinitionBuilder.getRawBeanDefinition());
	}
	
//	@ServiceActivator(inputChannel="bdxProducerChannel")
//	@Bean
//	public MessageHandler handler() throws Exception {
////		KafkaProducerMessageHandler handler = new KafkaProducerMessageHandler(kafkaProducerContext);
//		BdxKafkaProducerMessageHandler handler = new BdxKafkaProducerMessageHandler();
////		handler.setTopicExpression(new LiteralExpression(this.topic));
////		handler.setMessageKeyExpression(new LiteralExpression(this.messageKey));
////		handler.setPartitionIdExpression(new LiteralExpression(this.messageKey));
//		return handler;
//	}
	
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}
	
}
