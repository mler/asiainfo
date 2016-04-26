package com.bdx.rainbow.common.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "httpclient", locations = "classpath:httpclientETL.properties")
public class HttpClientETLConfig {
	public Integer maxTotal;
	// max connections
	public Integer defaultMaxPerRout;
	// 建立连接的时间
	public Integer connectTimeout;
	// 读取时间
	public Integer connectionRequestTimeout;
	// 数据传输时间
	public Integer socketTimeout;
	// head部分
	public Integer maxHeaderCount;
	
	public Integer maxLineLength;
	// 代理部分(1：启用，0不启用)
	public String proxy_switch;
	
	public String proxy_url;
	
	public Integer proxy_port;
	
	public String content_charset;
	
	public Integer getMaxTotal() {
		return maxTotal;
	}
	public Integer getDefaultMaxPerRout() {
		return defaultMaxPerRout;
	}
	public Integer getConnectTimeout() {
		return connectTimeout;
	}
	public Integer getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}
	public Integer getSocketTimeout() {
		return socketTimeout;
	}
	public Integer getMaxHeaderCount() {
		return maxHeaderCount;
	}
	public Integer getMaxLineLength() {
		return maxLineLength;
	}
	public String getProxy_switch() {
		return proxy_switch;
	}
	public String getProxy_url() {
		return proxy_url;
	}
	public Integer getProxy_port() {
		return proxy_port;
	}
	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}
	public void setDefaultMaxPerRout(Integer defaultMaxPerRout) {
		this.defaultMaxPerRout = defaultMaxPerRout;
	}
	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}
	public void setSocketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	public void setMaxHeaderCount(Integer maxHeaderCount) {
		this.maxHeaderCount = maxHeaderCount;
	}
	public void setMaxLineLength(Integer maxLineLength) {
		this.maxLineLength = maxLineLength;
	}
	public void setProxy_switch(String proxy_switch) {
		this.proxy_switch = proxy_switch;
	}
	public void setProxy_url(String proxy_url) {
		this.proxy_url = proxy_url;
	}
	public void setProxy_port(Integer proxy_port) {
		this.proxy_port = proxy_port;
	}
	public String getContent_charset() {
		return content_charset;
	}
	public void setContent_charset(String content_charset) {
		this.content_charset = content_charset;
	}
	@Override
	public String toString() {
		return "HttpClientETLConfig [maxTotal=" + maxTotal
				+ ", defaultMaxPerRout=" + defaultMaxPerRout
				+ ", connectTimeout=" + connectTimeout
				+ ", connectionRequestTimeout=" + connectionRequestTimeout
				+ ", socketTimeout=" + socketTimeout + ", maxHeaderCount="
				+ maxHeaderCount + ", maxLineLength=" + maxLineLength
				+ ", proxy_switch=" + proxy_switch + ", proxy_url=" + proxy_url
				+ ", proxy_port=" + proxy_port + ", content_charset="
				+ content_charset + "]";
	}



}
