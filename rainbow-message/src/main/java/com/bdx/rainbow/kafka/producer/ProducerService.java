package com.bdx.rainbow.kafka.producer;

import java.util.Map;

public interface ProducerService {

	public abstract <K> boolean send(String topic,Map<String,Object> headers,K content);

}