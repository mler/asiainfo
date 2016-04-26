package com.bdx.rainbow.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.bdx.rainbow.common.configuration.HttpClientConfig;
import com.bdx.rainbow.common.configuration.HttpClientETLConfig;
import com.bdx.rainbow.common.configuration.HttpClientPropertyMBean;



/**
 * 连接池初始化，获取HttpClient（根据配置文件来初始化信息）
 * @author luyang
 *
 */
public class HttpClientETL{

	public static final Log log = LogFactory.getLog(HttpClientETL.class);
	
	public static PoolingHttpClientConnectionManager poolConnManager;
	
	public static RequestConfig defaultConfig;
	
	public static CookieStore defaultCookieStore;
	
	public static Collection<? extends Header> defaultHeaders;
	
	public static CookieStore lastCookieStore;
	private static  HttpClientPropertyMBean httpClientPropertyMBean;
	
	

	static {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(HttpClientConfig.class);
		httpClientPropertyMBean = ctx.getBean(HttpClientPropertyMBean.class);
		//HttpClientETLConfig config = ctx.getBean(HttpClientETLConfig.class);
		/** 加载连接池 **/
		poolConnManager = initPoolingHttpClientConnectionManager(poolConnManager);
		defaultConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
	            .setCookieSpec(CookieSpecs.BEST_MATCH)
	            .setConnectTimeout(httpClientPropertyMBean.getConnectTimeout())//建立连接的时间
	            .setConnectionRequestTimeout(httpClientPropertyMBean.getConnectionRequestTimeout())
	            .setSocketTimeout(httpClientPropertyMBean.getSocketTimeout())//数据传输时间
	            .build();
		
		defaultCookieStore = new BasicCookieStore();
		
	}


	
	private static HttpContext createHttpClientContext(Map<String,String> cookies)
	{
		HttpClientContext context = HttpClientContext.create();
		context.setRequestConfig(defaultConfig);
		if(lastCookieStore == null)
			lastCookieStore = defaultCookieStore;
		context.setCookieStore(lastCookieStore);
		/**
		 * 设置cookies信息
		 */
		if(cookies != null && cookies.isEmpty() == false)
		{
			for(String key : cookies.keySet())
			{
				BasicClientCookie cookie = new BasicClientCookie(key, cookies.get(key));
				cookie.setVersion(1);
				cookie.setDomain("www.10010.com");
				cookie.setPath("/");
				lastCookieStore.addCookie(cookie);	
			}
		}
		return context;
	}
	
	/**
	 * 初始化httpClient连接池
	 * @param connManager
	 * @return
	 */
	private static  PoolingHttpClientConnectionManager initPoolingHttpClientConnectionManager(PoolingHttpClientConnectionManager connManager)
	{
		try {

//			SSLContext sslContext = SSLContexts.custom().useTLS().build();
//			sslContext.init(null,
//			       new TrustManager[] { new X509TrustManager() {
//			       	public X509Certificate[] getAcceptedIssuers() {
//			               return null;
//			           }
//	
//			           public void checkClientTrusted(
//			                   X509Certificate[] certs, String authType) {
//			           }
//	
//			           public void checkServerTrusted(
//			                   X509Certificate[] certs, String authType) {
//			           }
//	
//			}}, null);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
			           .register("http", PlainConnectionSocketFactory.INSTANCE)
//			           .register("https", new SSLConnectionSocketFactory(sslContext))
			           .build();
			
			connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			// Create socket configuration
			SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
			connManager.setDefaultSocketConfig(socketConfig);
			// Create message constraints
	      	MessageConstraints messageConstraints = MessageConstraints.custom()
	           .setMaxHeaderCount(httpClientPropertyMBean.getMaxHeaderCount())
	           .setMaxLineLength(httpClientPropertyMBean.getMaxLineLength())
	           .build();
	      	// Create connection configuration
	      	ConnectionConfig connectionConfig = ConnectionConfig.custom()
	           .setMalformedInputAction(CodingErrorAction.IGNORE)
	           .setUnmappableInputAction(CodingErrorAction.IGNORE)
	           .setCharset(Consts.UTF_8)
	           .setMessageConstraints(messageConstraints)
	           .build();
	      	connManager.setDefaultConnectionConfig(connectionConfig);
			connManager.setMaxTotal(httpClientPropertyMBean.getMaxTotal());
			connManager.setDefaultMaxPerRoute(httpClientPropertyMBean.getDefaultMaxPerRout());
		} 
//		catch (KeyManagementException e) {
//			log.error("KeyManagementException", e);
//			e.printStackTrace();
//		} 
//		catch (NoSuchAlgorithmException e) {
//			log.error("NoSuchAlgorithmException", e);
//			e.printStackTrace();
//		}
		catch (Exception e) {
			// TODO: handle exception
			log.error("Exception", e);
			e.printStackTrace();
		}
		
		return connManager;
	}
	
	/**
	 * 通过连接池获取httpClient对象
	 * @param config
	 * @return
	 */
	public static CloseableHttpClient getHttpClient(RequestConfig config,Collection<? extends Header> headers,CookieStore cookieStore,HttpHost proxy)
	{
		if(config == null)
			config = defaultConfig;
		
		if(headers == null)
			headers = defaultHeaders;
		
		if(cookieStore == null && lastCookieStore == null)
			cookieStore = defaultCookieStore;
		else if(cookieStore == null)
			cookieStore = lastCookieStore;
		
		
		HttpClientBuilder builder =  HttpClients.custom()
				.setDefaultRequestConfig(config)
				.setConnectionManager(poolConnManager)
				.setDefaultCookieStore(cookieStore)
				.setDefaultHeaders(headers);
		
		//设置代理服务器
		DefaultProxyRoutePlanner routePlanner = proxy == null ? null : new DefaultProxyRoutePlanner(proxy);
		if(routePlanner != null)
			builder.setRoutePlanner(routePlanner);
		
		CloseableHttpClient httpclient = builder.build();
				
		
		return httpclient;
	}
	
	public static CloseableHttpClient getHttpClient(Map<String,String> headers,Map<String,String> cookies)
	{
		
		if(lastCookieStore == null)
			lastCookieStore = defaultCookieStore;
		
		if(cookies != null && cookies.isEmpty() == false)
		{
			for(String key : cookies.keySet())
			{
				BasicClientCookie cookie = new BasicClientCookie(key, cookies.get(key));
				cookie.setVersion(1);
				cookie.setDomain("www.10010.com");
				cookie.setPath("/");
				lastCookieStore.addCookie(cookie);	
			}
		}
		
		List<Header> headerConfig = new  ArrayList<Header>(0);
		
		CloseableHttpClient httpclient = HttpClients.custom()
				.setConnectionManager(poolConnManager)
				.setDefaultCookieStore(lastCookieStore)
				.setDefaultHeaders(headerConfig)
				.build();
		
		return httpclient;
	}
	
	/**
	 * 通过连接池获取httpClient对象
	 * @param config
	 * @return
	 */
	public static CloseableHttpClient getHttpClientDefault()
	{
		return getHttpClient(null, null, null,null);
	}
	
	/**
	 * 通过连接池获取httpClient对象
	 * @param config
	 * @return
	 */
	public static CloseableHttpClient getProxyHttpClient(String proxy_url,int port)
	{
		log.debug(String.format("启用代理:proxy_url=%s ,port=%s",proxy_url,port));
		HttpHost proxy = null;
		
		if(proxy_url != null && StringUtils.isEmpty(proxy_url) == false
				&& port > 0)
		{
			proxy = new HttpHost(proxy_url, port);
		}
		
		return getHttpClient(null, null, null,proxy);
	}
	
	
	public static void downloadFile(String url,Map<String,String> headers,Map<String,String> cookies,Map<String,String> parameter, String savePath) throws Exception 
	{
		BufferedReader in = null;
		BufferedWriter out= null;
		  
		HttpPost httpPost = new HttpPost(url);
		
		/** 初始化包头 **/
		if(headers != null && headers.isEmpty() == false)
		{
			for(String key:headers.keySet())
			{
				httpPost.addHeader(key,headers.get(key));
			}
		}
		
		CloseableHttpClient client = HttpClientETL.getHttpClientDefault();
		CloseableHttpResponse rsp = client.execute(httpPost,createHttpClientContext(cookies));
		/* 执行 HTTP GET 请求 */
		try {
			
			int statusCode =  rsp.getStatusLine().getStatusCode();
			// 判断访问的状态码
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "+ rsp.getStatusLine());
				throw new Exception("访问失败，url="+url+", "+rsp.getStatusLine());
			}
			
			/* 处理 HTTP 响应内容 */
			ContentType contentType = ContentType.get(rsp.getEntity());
            Charset charset = contentType.getCharset();
			in = new BufferedReader(new InputStreamReader(rsp.getEntity().getContent(),charset));
			out= new BufferedWriter(new FileWriter(new File(savePath)));
			
			/* 保存文件 */
			String line = null;
	        while ((line = in.readLine()) != null) {
	        	out.write(line);
	        }
	        out.flush();
	        
		} catch (Exception e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			e.printStackTrace();
			log.error("请检查你的请求地址："+url);
//			CrawlUtil.addFailedUrl(url,"3");
			
		} finally { 
			
			if(in != null) {  
                try {  
                    in.close();  
                }catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }
			
            if(out != null) {  
                try {  
                    out.close();  
                }catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
			
			// 释放连接
            rsp.close();
//          httpPost.releaseConnection();
		}
	}
	
	
	/**
	 * 返回字符串的请求，例如，json,xml等
	 * @param url
	 * @param cookies
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getStringResponse(String url,Map<String,String> headers, Map<String,String> cookies,Map<String,String> params) throws Exception
	{
		HttpPost httpPost = new HttpPost(url);
		
		/** 初始化包头 **/
		if(headers != null && headers.isEmpty() == false)
		{
			for(String key:headers.keySet())
			{
				httpPost.addHeader(key,headers.get(key));
			}
		}
		
		/**
		 * 设置请求参数，post方式 form提交
		 */
		if(params != null && params.isEmpty() == false)
		{
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			
			for(String key : params.keySet() )
			{
				formparams.add(new BasicNameValuePair(key, params.get(key)));
			}
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
			httpPost.setEntity(entity);
		}
		
		String json = null;
		CloseableHttpResponse rsp = null;

		try {
			CloseableHttpClient httpclient;
			if("1".equals(httpClientPropertyMBean.getProxy_switch()))
			{
				httpclient=getProxyHttpClient(httpClientPropertyMBean.getProxy_url(), httpClientPropertyMBean.getProxy_port());
			}
			else
			{
				httpclient= getHttpClientDefault();
			}
		
			HttpContext context = createHttpClientContext(cookies);
			rsp = httpclient.execute(httpPost,context);
			
			int statusCode =  rsp.getStatusLine().getStatusCode();
		
			// 判断访问的状态码
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "+ rsp.getStatusLine());
				throw new Exception("访问失败，url="+url+", "+rsp.getStatusLine());
			}
			
		    HttpEntity entity = rsp.getEntity();
		    lastCookieStore = (CookieStore)context.getAttribute(HttpClientContext.COOKIE_STORE);
		    json = EntityUtils.toString(entity,httpClientPropertyMBean.getContent_charset());
		    
		} finally {
			if(rsp != null)
		    rsp.close();
		}
		
		return json;
	}
	
	
	/**
	 * 无需等待返回的请求
	 * @param url
	 * @param cookies
	 * @param params
	 * @throws Exception
	 */
	public static void getNoResponse(String url,Map<String,String> cookies,Map<String,String> params) throws Exception
	{
		HttpPost httpPost = new HttpPost(url);
		/**
		 * 设置请求参数，post方式 form提交
		 */
		if(params != null && params.isEmpty() == false)
		{
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			
			for(String key : params.keySet() )
			{
				formparams.add(new BasicNameValuePair(key, params.get(key)));
			}
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
			httpPost.setEntity(entity);
		}
		
		HttpContext context = createHttpClientContext(cookies);
		
		CloseableHttpClient httpclient = getHttpClientDefault();
		
		CloseableHttpResponse rsp = httpclient.execute(httpPost,context);
		
	    lastCookieStore = (CookieStore)context.getAttribute(HttpClientContext.COOKIE_STORE);
		
		try {
		    httpPost.abort();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
		    rsp.close();
		}
		
		
	}


	public static PoolingHttpClientConnectionManager getPoolConnManager() {
		return poolConnManager;
	}


	public static RequestConfig getDefaultConfig() {
		return defaultConfig;
	}


	public static void setPoolConnManager(
			PoolingHttpClientConnectionManager poolConnManager) {
		HttpClientETL.poolConnManager = poolConnManager;
	}


	public static void setDefaultConfig(RequestConfig defaultConfig) {
		HttpClientETL.defaultConfig = defaultConfig;
	}
	
	
	public static void main(String[] args) throws Exception {
		String dirHtml = HttpClientETL.getStringResponse("http://app1.sfda.gov.cn/datasearch/face3/"
				+ "dir.html", null, null, null);
		Thread.sleep(Long.MAX_VALUE);
	}
}
