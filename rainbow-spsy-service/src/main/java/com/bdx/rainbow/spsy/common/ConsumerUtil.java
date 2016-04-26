package com.bdx.rainbow.spsy.common;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerUtil {
	
	public static ClassPathXmlApplicationContext getContextBasic(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "dubbo-consumer-demo.xml" });
		return context;		
	}	
	
	public static ClassPathXmlApplicationContext getContextUrs(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "dubbo-urs-consumer.xml" });
		return context;	
	}
}
