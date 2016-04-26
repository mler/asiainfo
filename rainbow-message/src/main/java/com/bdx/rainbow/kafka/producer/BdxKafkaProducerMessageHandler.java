package com.bdx.rainbow.kafka.producer;

import org.apache.commons.lang.StringUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.kafka.support.KafkaHeaders;
import org.springframework.integration.kafka.support.KafkaProducerContext;
import org.springframework.messaging.Message;

import com.bdx.rainbow.kafka.BdxJmsContext;

public class BdxKafkaProducerMessageHandler extends AbstractMessageHandler {

	private EvaluationContext evaluationContext;

	private volatile Expression topicExpression;

	private volatile Expression messageKeyExpression;

	private volatile Expression partitionExpression;

	@SuppressWarnings("unchecked")
	public BdxKafkaProducerMessageHandler() {
		super();
	}

	public void setTopicExpression(Expression topicExpression) {
		this.topicExpression = topicExpression;
	}

	public void setMessageKeyExpression(Expression messageKeyExpression) {
		this.messageKeyExpression = messageKeyExpression;
	}

	public void setPartitionExpression(Expression partitionExpression) {
		this.partitionExpression = partitionExpression;
	}

	@Override
	protected void onInit() throws Exception {
		super.onInit();
		this.evaluationContext = ExpressionUtils.createStandardEvaluationContext(getBeanFactory());
	}

	@Override
	protected void handleMessageInternal(final Message<?> message) throws Exception {
		String topic = this.topicExpression != null ?
				this.topicExpression.getValue(this.evaluationContext, message, String.class)
				: message.getHeaders().get(KafkaHeaders.TOPIC, String.class);

		Integer partitionId = this.partitionExpression != null ?
				this.partitionExpression.getValue(this.evaluationContext, message, Integer.class)
				: message.getHeaders().get(KafkaHeaders.PARTITION_ID, Integer.class);

		Object messageKey = this.messageKeyExpression != null
				? this.messageKeyExpression.getValue(this.evaluationContext, message)
				: message.getHeaders().get(KafkaHeaders.MESSAGE_KEY);
				
	    String contextid = BdxJmsContext.getProducerContextid();
	    
	    if(StringUtils.isBlank(contextid))
	    	throw new Exception("消息未指定上下文ID");
	    
		KafkaProducerContext context =KafkaProducerContextFactory.getContext(contextid);
		context.send(topic, partitionId, messageKey, message.getPayload());
	}

	@Override
	public String getComponentType() {
		return "kafka:outbound-channel-adapter";
	}


	
}
