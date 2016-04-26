/**
 * 
 */
package com.bdx.rainbow.kafka;

import java.util.HashMap;
import java.util.Map;

/**
 * Bdx的JMS上下文
 */
public class BdxJmsContext {
	
	public static final String JMSCONTEXT_CONSUMER_CONTEXTID = "consumer_contextid";//消费者的topic
	
	public static final String JMSCONTEXT_PRODUCER_CONTEXTID = "producer_contextid";//生产者topic
	
	/** 将属性放入ThreadLocal中保存 **/
	private static ThreadLocal<Map<String, Object>> context = new ThreadLocal<Map<String, Object>>();

	public static void setConsumerContextid(final String contextid) {
		if(context.get() == null)
			context.set(new HashMap<String, Object>(0));
		
		context.get().put(JMSCONTEXT_CONSUMER_CONTEXTID, contextid);
	}
	
	public static void setProducerContextid(final String contextid) {
		if(context.get() == null)
			context.set(new HashMap<String, Object>(0));
		
		context.get().put(JMSCONTEXT_PRODUCER_CONTEXTID, contextid);
	}



	public static String getConsumerContextid() {
		if(context.get() == null || context.get().isEmpty())
			return null;
		
		return (String)context.get().get(JMSCONTEXT_CONSUMER_CONTEXTID);
	}
	
	
	public static String getProducerContextid() {
		if(context.get() == null || context.get().isEmpty())
			return null;
		
		return (String)context.get().get(JMSCONTEXT_PRODUCER_CONTEXTID);
	}
	
}
