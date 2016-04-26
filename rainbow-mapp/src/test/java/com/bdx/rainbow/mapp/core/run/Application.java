package com.bdx.rainbow.mapp.core.run;

/*
 * Copyright 2015 the original author or authors.
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

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bdx.rainbow.mapp.core.client.ClientHandler;
import com.bdx.rainbow.mapp.core.configuration.MappSettings;
import com.bdx.rainbow.mapp.core.listener.MappContextLoaderLisener;
import com.bdx.rainbow.mapp.core.model.M9999Request;
import com.bdx.rainbow.mapp.core.model.MappHeader;
import com.bdx.rainbow.mapp.core.model.MappPkg;
import com.bdx.rainbow.mapp.core.util.JacksonUtils;
@Configuration
@ComponentScan({"com.bdx.rainbow.mapp.core"})
@EnableConfigurationProperties({ MappSettings.class })
@EnableAutoConfiguration
public class Application extends WebMvcConfigurerAdapter {
	
    public static void main(String[] args) throws IOException, Exception {
    	 
    	 SpringApplication.run(Application.class, args);
    	
    	 Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				 for(int i=0;i<10;i++)
		         {
					 try{
			 	    	 MappPkg pkg = new MappPkg();
			 	    	 MappHeader header = new MappHeader();
			 	    	 header.setIdentityId(System.currentTimeMillis()+"-thread1-"+i);
			 	    	 header.setBizCode("m9999");
			 	    	 pkg.setHeader(header);
			 	    	 
			 	    	 pkg.setBody(new M9999Request());
			 	    	 
			 	    	 ClientHandler client = new ClientHandler();
			 	//    	 ClientHandler client = context.getBean(ClientHandler.class);
			 	    	String ret = client.doHandle("m9999", "1.0", JacksonUtils.toJson(pkg), null);
			 	    	System.out.println(ret); 
			 	    	
			 	    	Thread.sleep(1000*15);
					 }catch(Exception e)
					 {
						 e.printStackTrace();
					 }
		         }
			}
		});
    	 
    	 Thread t2 = new Thread(new Runnable() {
 			
 			@Override
 			public void run() {
 				 for(int i=0;i<10;i++)
 		         {
 					 try{
 			 	    	 MappPkg pkg = new MappPkg();
 			 	    	 MappHeader header = new MappHeader();
 			 	    	 header.setIdentityId(System.currentTimeMillis()+"-thread2-"+i);
 			 	    	 header.setBizCode("m9999");
 			 	    	 pkg.setHeader(header);
 			 	    	 
 			 	    	 pkg.setBody(new M9999Request());
 			 	    	 
 			 	    	 ClientHandler client = new ClientHandler();
 			 	//    	 ClientHandler client = context.getBean(ClientHandler.class);
 			 	    	String ret = client.doHandle("m9999", "1.0", JacksonUtils.toJson(pkg), null);
 			 	    	System.out.println(ret); 
 			 	    	
 			 	    	Thread.sleep(1000*15);
 					 }catch(Exception e)
 					 {
 						 e.printStackTrace();
 					 }
 		         }
 			}
 		});
    	
    	 t1.start();
    	 t2.start();
    	
    }
	
	@Bean
    public ServletListenerRegistrationBean<MappContextLoaderLisener> servletRegistrationBean() {
        return new ServletListenerRegistrationBean<MappContextLoaderLisener>(new MappContextLoaderLisener());
    }

}