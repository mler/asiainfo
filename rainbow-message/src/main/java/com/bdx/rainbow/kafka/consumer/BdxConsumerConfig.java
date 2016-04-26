package com.bdx.rainbow.kafka.consumer;

import java.io.Serializable;
import java.util.Map;

public class BdxConsumerConfig implements Serializable {

	private String topic;

	private String groupId;

	private Map<String, Integer> topicStreamMap;

	private int maxMsg;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Map<String, Integer> getTopicStreamMap() {
		return topicStreamMap;
	}

	public void setTopicStreamMap(Map<String, Integer> topicStreamMap) {
		this.topicStreamMap = topicStreamMap;
	}

	public int getMaxMsg() {
		return maxMsg;
	}

	public void setMaxMsg(int maxMsg) {
		this.maxMsg = maxMsg;
	}

}