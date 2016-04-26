package com.bdx.rainbow.kafka.producer;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.kafka.BdxJmsContext;

@Service
public class BdxProducerService implements ProducerService {

	private Logger logger = LoggerFactory.getLogger(BdxProducerService.class);
	
	@Autowired
	@Qualifier(value="producerChannel")
	private MessageChannel bdxProducerChannel;
	
	@Override
	public <K> boolean send(String contextid,Map<String,Object> headers,K content)
	{
		BdxJmsContext.setProducerContextid(contextid);
		
		MessageBuilder<K> messageBuild = MessageBuilder.withPayload(content);
		
		for(String key : headers.keySet()){
			messageBuild.setHeader(key, headers.get(key));
		}
				
		Message<K> msg = messageBuild.build();
				
		boolean success = bdxProducerChannel.send(msg);
		
		logger.info("BdxProducerService.bdxProducerChannel.send() return ["+success+"]");
		
		return success;
	}

}
