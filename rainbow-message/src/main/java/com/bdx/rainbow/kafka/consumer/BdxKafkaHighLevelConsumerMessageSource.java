/*
 * Copyright 2002-2013 the original author or authors.
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
package com.bdx.rainbow.kafka.consumer;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;

import com.bdx.rainbow.kafka.BdxJmsContext;

/**
 * @author Soby Chacko
 * @since 0.5
 *
 */
public class BdxKafkaHighLevelConsumerMessageSource<K,V> extends IntegrationObjectSupport implements MessageSource<Map<String, Map<Integer, List<Object>>>> {

	private Logger logger = LoggerFactory.getLogger(BdxKafkaHighLevelConsumerMessageSource.class);
	
	@Override
	public Message<Map<String, Map<Integer, List<Object>>>> receive() {
		
		String contextid = BdxJmsContext.getConsumerContextid();
	    
	    if(StringUtils.isBlank(contextid))
	    {
	    	logger.warn("消费者未指定上下文ID");
	    	return null;
	    }
		
		return KafkaConsumerContextFactory.getConsumerContext(contextid).receive();
	}

	@Override
	public String getComponentType() {
		return "kafka:inbound-channel-adapter";
	}
}
