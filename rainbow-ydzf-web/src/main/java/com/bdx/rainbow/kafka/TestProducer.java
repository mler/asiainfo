package com.bdx.rainbow.kafka;

import java.util.*;
 
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
 
public class TestProducer {
    public static void main(String[] args) {
        long events = 5;
        Random rnd = new Random();
 
        Properties props = new Properties();
        props.put("metadata.broker.list", "60.194.3.162:9090,60.194.3.162:9091,60.194.3.162:9092");
//        props.put("metadata.broker.list", "localhost:9090,localhost:9091,localhost:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "com.bdx.rainbow.kafka.SimplePartitioner");
        props.put("request.required.acks", "1");
//        props.put("producer.type", "async");
 
        ProducerConfig config = new ProducerConfig(props);
 
        Producer<String, String> producer = new Producer<String, String>(config);
 
        for (long nEvents = 0; nEvents < events; nEvents++) { 
               long runtime = new Date().getTime();  
               String ip = "192.168.2." + rnd.nextInt(255); 
               String msg = "["+nEvents+"] "+runtime + ",mler hello," + ip; 
               KeyedMessage<String, String> data = new KeyedMessage<String, String>("mler_test_topic", ip, msg);
               producer.send(data);
        }
        producer.close();
    }
}