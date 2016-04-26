package com.bdx.rainbow.common.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties({HttpClientETLConfig.class}) 
public class HttpClientConfig {

	
	@Bean
    public org.springframework.jmx.export.annotation.AnnotationMBeanExporter annotationMBeanExporter()
    {
		return new org.springframework.jmx.export.annotation.AnnotationMBeanExporter();
    	
    }

//	@Bean(initMethod="init")
//	 public  HttpClientPropertyMBean httpClientPropertyMBean() {  
//		HttpClientPropertyMBean httpClientPropertyMBean=new HttpClientPropertyMBean();
//         return new HttpClientPropertyMBean();
//	}  
	

	
	
	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(HttpClientConfig.class);
		HttpClientPropertyMBean server = ctx.getBean(HttpClientPropertyMBean.class);
		System.out.println(server.getConnectTimeout());
		HttpClientETLConfig config = ctx.getBean(HttpClientETLConfig.class);
		System.out.println(config.getConnectTimeout());
		System.out.println(config.getDefaultMaxPerRout());
	}



}
