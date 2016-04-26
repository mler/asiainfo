package com.bdx.rainbow.kafka;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;
 
public class MyConsumer {
 
    private final ConsumerConnector consumer;
    
    final String topic = "bdx_topic_0";
    
    public static AtomicLong count = new AtomicLong(0);
 
    private MyConsumer() {
    	
        Properties props = new Properties();
        //zookeeper 配置
        props.put("zookeeper.connect", "localhost:2181");
 
        //group 代表一个消费组
        props.put("group.id", "test0000000000");
 
        //zk连接超时
        props.put("zookeeper.session.timeout.ms", "4000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        //序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
 
        ConsumerConfig config = new ConsumerConfig(props);
 
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
    }
 
    void consume() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(5));
 
        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
 
        Map<String, List<KafkaStream<String, String>>> consumerMap =
                consumer.createMessageStreams(topicCountMap,keyDecoder,valueDecoder);
        KafkaStream<String, String> stream0 = consumerMap.get(topic).get(0);
        KafkaStream<String, String> stream1 = consumerMap.get(topic).get(1);
        KafkaStream<String, String> stream2 = consumerMap.get(topic).get(2);
        KafkaStream<String, String> stream3 = consumerMap.get(topic).get(3);
        KafkaStream<String, String> stream4 = consumerMap.get(topic).get(4);
        
        
        new Thread(new StreamProcess(stream0), "thread_stream0").start();
        new Thread(new StreamProcess(stream1), "thread_stream1").start();
        new Thread(new StreamProcess(stream2), "thread_stream2").start();
        new Thread(new StreamProcess(stream3), "thread_stream3").start();
        new Thread(new StreamProcess(stream4), "thread_stream4").start();
        
//        KafkaStream<String, String> stream = consumerMap.get(topic).get(0);
//        ConsumerIterator<String, String> it = stream.iterator();
//        int i = 0;
//        while (it.hasNext()){
//        	i = i+1;
//            System.out.println("一共："+count.getAndAdd(1)+"条,"+it.next().message());
//        }
        
//        consumer.commitOffsets();
    }
    
    public class StreamProcess implements Runnable
    {
    	private KafkaStream<String, String> stream;
    	
		public StreamProcess(KafkaStream<String, String> stream) {
			super();
			this.stream = stream;
		}



		@Override
		public void run() {
	        ConsumerIterator<String, String> it = stream.iterator();
	        int i = 0;
	        while (it.hasNext()){
	        	i = i+1;
	        	MessageAndMetadata<String, String> messageAndMetadata = it.next();
	            System.out.println(Thread.currentThread().getName()+ "  一共："+count.incrementAndGet()+"条,"+messageAndMetadata.message());
	        }
		}
    	
    }
 
    public static void main(String[] args) {
        new MyConsumer().consume();
    }
}