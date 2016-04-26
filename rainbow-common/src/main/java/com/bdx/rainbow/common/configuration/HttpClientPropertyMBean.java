package com.bdx.rainbow.common.configuration;

import javax.annotation.PostConstruct;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.bdx.rainbow.common.util.HttpClientETL;

@Component
@ManagedResource (objectName= "bean:name=HttpClientPropertyMBean" , description= "httpclient Managed Bean" )    
public class HttpClientPropertyMBean {
	
	private static final Logger log = LoggerFactory.getLogger(HttpClientPropertyMBean.class);
	
    @Autowired  
    private  HttpClientETLConfig httpClientETLConfig;  
    @ManagedAttribute()   
	public Integer getMaxTotal() {
		return httpClientETLConfig.getMaxTotal();
	}
    @ManagedAttribute()   
	public Integer getDefaultMaxPerRout() {
		return httpClientETLConfig.getDefaultMaxPerRout();
	}
    @ManagedAttribute()   
	public Integer getConnectTimeout() {
		return httpClientETLConfig.getConnectTimeout();
	}
    @ManagedAttribute()   
	public Integer getConnectionRequestTimeout() {
		return httpClientETLConfig.getConnectionRequestTimeout();
	}
    @ManagedAttribute()   
	public Integer getSocketTimeout() {
		return httpClientETLConfig.getConnectionRequestTimeout();
	}
    @ManagedAttribute()   
	public Integer getMaxHeaderCount() {
		return httpClientETLConfig.getConnectionRequestTimeout();
	}
    @ManagedAttribute()   
	public Integer getMaxLineLength() {
		return httpClientETLConfig.getConnectionRequestTimeout();
	}
    @ManagedAttribute()   
	public String getProxy_switch() {
		return httpClientETLConfig.getProxy_switch();
	}
    @ManagedAttribute()   
	public String getProxy_url() {
		return httpClientETLConfig.getProxy_url();
	}
    @ManagedAttribute()   
	public Integer getProxy_port() {
		return httpClientETLConfig.getProxy_port();
	}
    @ManagedAttribute()    
	public String getContent_charset() {
		return httpClientETLConfig.getContent_charset();
	}

    
    @ManagedOperation()
    @ManagedOperationParameter(name = "maxTotal", description = "")
	public void setMaxTotal(Integer maxTotal) {
		HttpClientETL.getPoolConnManager().setMaxTotal(maxTotal);
		httpClientETLConfig.setMaxTotal(maxTotal);
	}
    @ManagedOperation()
	public void setDefaultMaxPerRout(Integer defaultMaxPerRout) {
		HttpClientETL.getPoolConnManager().setDefaultMaxPerRoute(defaultMaxPerRout);
		httpClientETLConfig.setDefaultMaxPerRout(defaultMaxPerRout);
	}
    @ManagedOperation()
	public void setConnectTimeout(Integer connectTimeout) {
	
		HttpClientETL.setDefaultConfig(RequestConfig.custom()
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
		        .setCookieSpec(CookieSpecs.BEST_MATCH)
		        .setConnectTimeout(connectTimeout)//建立连接的时间
		        .setConnectionRequestTimeout(this.getConnectionRequestTimeout())
		        .setSocketTimeout(this.getSocketTimeout())//数据传输时间
		        .build());
		httpClientETLConfig.setConnectTimeout(connectTimeout);
	}
    @ManagedOperation()
	public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
		HttpClientETL.setDefaultConfig(RequestConfig.custom()
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
		        .setCookieSpec(CookieSpecs.BEST_MATCH)
		        .setConnectTimeout(this.getConnectTimeout())//建立连接的时间
		        .setConnectionRequestTimeout(connectionRequestTimeout)
		        .setSocketTimeout(this.getSocketTimeout())//数据传输时间
		        .build());
		httpClientETLConfig.setConnectionRequestTimeout(connectionRequestTimeout);
	}
    @ManagedOperation()
	public void setSocketTimeout(Integer socketTimeout) {
		HttpClientETL.setDefaultConfig(RequestConfig.custom()
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
		        .setCookieSpec(CookieSpecs.BEST_MATCH)
		        .setConnectTimeout(this.getConnectTimeout())//建立连接的时间
		        .setConnectionRequestTimeout(this.getConnectionRequestTimeout())
		        .setSocketTimeout(socketTimeout)//数据传输时间
		        .build());
		httpClientETLConfig.setSocketTimeout(socketTimeout);
	}
//	public void setMaxHeaderCount(Integer maxHeaderCount) {
//		this.maxHeaderCount = maxHeaderCount;
//	}
//	public void setMaxLineLength(Integer maxLineLength) {
//		this.maxLineLength = maxLineLength;
//	}
    @ManagedOperation()
	public void setProxy_switch(String proxy_switch) {
		httpClientETLConfig.setProxy_switch(proxy_switch);

	}
    @ManagedOperation()
	public void setProxy_url(String proxy_url) {
		httpClientETLConfig.setProxy_url(proxy_url);
	}
    @ManagedOperation()
	public void setProxy_port(Integer proxy_port) {
		httpClientETLConfig.setProxy_port(proxy_port);
	}
    @ManagedOperation()
	public void setContent_charset(String content_charset) {
		httpClientETLConfig.setContent_charset(content_charset);
	}

    @ManagedOperation()
  	public String getHttpClientETLConfigA() {
  		return httpClientETLConfig.toString();
  	}
    
    //初始化
    @PostConstruct 
    public void init(){
    	log.info("HttpClientPropertyMBean初始化");
    	
    }

	
	

}
