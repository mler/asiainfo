package com.bdx.rainbow.kafka;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.integration.kafka.support.KafkaHeaders;

import com.bdx.rainbow.kafka.producer.ProducerService;

public class TestProducerProcess implements Runnable
    {
    	private ProducerService producerService;
    	
    	private String contextid;
    	
    	private String topic;
    	
    	private long time;
    	
		public TestProducerProcess(String contextid,ProducerService producerService,String topic,long time) {
			super();
			this.producerService = producerService;
			this.contextid = contextid;
			this.topic = topic;
			this.time = time;
		}

		@Override
		public void run() {
		
			try {
				Random random = new Random();
				
				int i = 1;
				
				while(true)
				{
					System.out.println("############################# "+contextid+" 发送信息 ["+i+"] ##############################");
					Map<String,Object> headers = new HashMap<String, Object>();
					headers.put("pkey", random.nextInt(3));
					headers.put(KafkaHeaders.TOPIC, this.topic);
					headers.put(KafkaHeaders.PARTITION_ID,random.nextInt(3));
					headers.put(BdxJmsHeaders.MSG_HEADER_FROM, BdxJmsConstant.SYS_NAME_BASE);
					headers.put(BdxJmsHeaders.MSG_HEADER_CONTEXTID, contextid);
					
					producerService.send(contextid,headers, "第["+i+"]条消息， 【"+contextid+"】卢阳消息系统测试 ");
					
					System.out.println("############################## "+contextid+" 发送结束 ["+i+"] #############################");
					
					i++;
					
					Thread.sleep(time);
				}
			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    }