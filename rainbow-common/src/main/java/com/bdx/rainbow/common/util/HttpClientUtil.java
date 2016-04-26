package com.bdx.rainbow.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

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
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
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
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;


/**
 * 连接池初始化，获取HttpClient
 * @author luyang
 *
 */
public class HttpClientUtil{

	public static final Log log = LogFactory.getLog(HttpClientUtil.class);
	
	public static PoolingHttpClientConnectionManager poolConnManager;
	
	public static RequestConfig defaultConfig;
	
	public static CookieStore defaultCookieStore;
	
	public static Collection<? extends Header> defaultHeaders;
	
	public static CookieStore lastCookieStore;
	
	static {
		/** 加载连接池 **/
		poolConnManager = initPoolingHttpClientConnectionManager(poolConnManager);
		
		defaultConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
	            .setCookieSpec(CookieSpecs.BEST_MATCH)
	            .setConnectTimeout(600000)//建立连接的时间
	            .setConnectionRequestTimeout(6000000)
	            .setSocketTimeout(600000)//数据传输时间
	            .setStaleConnectionCheckEnabled(true)
	            .build();
		
		defaultCookieStore = new BasicCookieStore();
		
	}
	
	private static HttpContext createHttpClientContextDefault()
	{
		HttpClientContext context = HttpClientContext.create();
		context.setRequestConfig(defaultConfig);
		context.setCookieStore(defaultCookieStore);
		return context;
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
	private static PoolingHttpClientConnectionManager initPoolingHttpClientConnectionManager(PoolingHttpClientConnectionManager connManager)
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
	           .setMaxHeaderCount(200)
	           .setMaxLineLength(20000000)
	           .build();
	      	// Create connection configuration
	      	ConnectionConfig connectionConfig = ConnectionConfig.custom()
	           .setMalformedInputAction(CodingErrorAction.IGNORE)
	           .setUnmappableInputAction(CodingErrorAction.IGNORE)
	           .setCharset(Consts.UTF_8)
	           .setMessageConstraints(messageConstraints)
	           .build();
	      	connManager.setDefaultConnectionConfig(connectionConfig);
			connManager.setMaxTotal(200);
			connManager.setDefaultMaxPerRoute(100);
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
		
//		proxy = new HttpHost("10.10.10.78", 8080);
		
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
		
		CloseableHttpClient client = HttpClientUtil.getHttpClientDefault();
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
	
	
	public static Map<HttpContext,InputStream> downloadInputStream(String url,Map<String,String> headers,HttpContext context,Map<String,String> parameter) throws Exception 
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
		
		CloseableHttpResponse rsp = null;
		/* 执行 HTTP 请求 */
		try {
			
			CloseableHttpClient httpclient = getHttpClientDefault();
			if(context == null)
				context = createHttpClientContext(null);
			
			rsp = httpclient.execute(httpPost,context);
			
			int statusCode =  rsp.getStatusLine().getStatusCode();
			// 判断访问的状态码
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "+ rsp.getStatusLine());
				throw new Exception("访问失败，url="+url+", "+rsp.getStatusLine());
			}
			
			Map<HttpContext,InputStream> retMap = new HashMap<HttpContext, InputStream>(1);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			ImageIO.write(ImageIO.read(rsp.getEntity().getContent()), "jpg", out);
			retMap.put(context, new ByteArrayInputStream(out.toByteArray()));
			
            return retMap;
            
		} catch (Exception e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			e.printStackTrace();
			log.error("请检查你的请求地址："+url);
			
		} finally { 
			
			// 释放连接
            rsp.close();
		}
		
		return null;
	}
	
	
	/**
	 * 返回字符串的请求，例如，json,xml等
	 * @param url
	 * @param cookies
	 * @param params
	 * @return
	 * @throws Exception
	 */
	
	public static String getStringResponse(String url,Map<String,String> headers, Map<String,String> cookies,Map<String,String> params,String charset) throws Exception
	{
		return getStringResponse(url, "post",headers, cookies, params, charset);
	}
	
	/**
	 * 返回字符串的请求，例如，json,xml等
	 * @param url
	 * @param cookies
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getStringResponse(String url,String type,Map<String,String> headers, Map<String,String> cookies,Map<String,String> params,String charset) throws Exception
	{
		HttpRequestBase httpMethod = "get".equals(type)?new HttpGet(url):new HttpPost(url);
	
		/** 初始化包头 **/
		if(headers != null && headers.isEmpty() == false)
		{
			for(String key:headers.keySet())
			{
				httpMethod.addHeader(key,headers.get(key));
			}
		}
		
		/**
		 * 设置请求参数，post方式 form提交
		 */
		if("get".equals(type) == false && params != null && params.isEmpty() == false)
		{
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			
			for(String key : params.keySet() )
			{
				formparams.add(new BasicNameValuePair(key, params.get(key)));
			}
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
			((HttpPost)httpMethod).setEntity(entity);
		}
		
		
		
		String json = null;
		CloseableHttpResponse rsp = null;

		try {
			
			CloseableHttpClient httpclient = getHttpClientDefault();
			HttpContext context = createHttpClientContext(cookies);
			
			rsp = httpclient.execute(httpMethod,context);
			
			int statusCode =  rsp.getStatusLine().getStatusCode();
		
			// 判断访问的状态码
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "+ rsp.getStatusLine());
				throw new Exception("访问失败，url="+url+", "+rsp.getStatusLine());
			}
			
		    HttpEntity entity = rsp.getEntity();
		    lastCookieStore = (CookieStore)context.getAttribute(HttpClientContext.COOKIE_STORE);
		    json = EntityUtils.toString(entity,(charset==null||charset.equals(""))?"UTF-8":charset);
		}catch (IOException ex) {  
			httpMethod.abort();  
	    } catch (RuntimeException ex) { 
	    	System.out.println("http code: " + rsp.getStatusLine().getStatusCode());  
			throw ex; 
		} finally {
			
			if(rsp != null)
			{
				 rsp.close();
			}
		}
		
		return json;
	}
	
	/**
	 * 返回字符串的请求，例如，json,xml等
	 * @param url
	 * @param cookies
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getStringResponseByProxy(String url,Map<String,String> headers, Map<String,String> cookies,Map<String,String> params,String proxyUrl,int proxyPort,String charset) throws Exception
	{
		return getStringResponseByProxy(proxyUrl, "post", headers, cookies, params, proxyUrl, proxyPort, charset);
	}
	
	/**
	 * 返回字符串的请求，例如，json,xml等
	 * @param url
	 * @param cookies
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getStringResponseByProxy(String url,String type,Map<String,String> headers, Map<String,String> cookies,Map<String,String> params,String proxyUrl,int proxyPort,String charset) throws Exception
	{
		HttpRequestBase httpMethod = "get".equals(type)?new HttpGet(url):new HttpPost(url);
		
		/** 初始化包头 **/
		if(headers != null && headers.isEmpty() == false)
		{
			for(String key:headers.keySet())
			{
				httpMethod.addHeader(key,headers.get(key));
			}
		}
		
		
		/**
		 * 设置请求参数，post方式 form提交
		 */
		if("get".equals(type) == false && params != null && params.isEmpty() == false)
		{
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			
			for(String key : params.keySet() )
			{
				formparams.add(new BasicNameValuePair(key, params.get(key)));
			}
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
			((HttpPost)httpMethod).setEntity(entity);
		}
		
		log.debug("创建代理："+proxyUrl+",端口："+proxyPort);
		CloseableHttpClient httpclient = getProxyHttpClient(proxyUrl, proxyPort);
//		httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) " +
//                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");
		
		HttpContext context = createHttpClientContext(cookies);
		CloseableHttpResponse rsp = httpclient.execute(httpMethod,context);
		
		String json = null;

		try {
			
			int statusCode =  rsp.getStatusLine().getStatusCode();
		
			// 判断访问的状态码
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "+ rsp.getStatusLine());
				throw new Exception("访问失败，url="+url+", "+rsp.getStatusLine());
			}
			
		    HttpEntity entity = rsp.getEntity();
//		    System.out.println(entity.getContentEncoding().getValue());
//		    System.out.println(entity.getContentType().getValue());
		    lastCookieStore = (CookieStore)context.getAttribute(HttpClientContext.COOKIE_STORE);
		    json = EntityUtils.toString(entity,(charset==null||charset.equals(""))?"UTF-8":charset);
		} finally {
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
	
	
	/**
	 * 返回字符串的请求，例如，json,xml等
	 * @param url
	 * @param cookies
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static Map<HttpContext,String> getHttpContextAndResponse(String url,Map<String,String> headers, HttpContext context,Map<String,String> params,String charset) throws Exception
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
		
		Map<HttpContext,String> contextRsp = null;
		CloseableHttpResponse rsp = null;

		try {
			
			CloseableHttpClient httpclient = getHttpClientDefault();
			if(context == null)
				context = createHttpClientContext(null);
			
			rsp = httpclient.execute(httpPost,context);
			
			int statusCode =  rsp.getStatusLine().getStatusCode();
		
			// 判断访问的状态码
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "+ rsp.getStatusLine());
				throw new Exception("访问失败，url="+url+", "+rsp.getStatusLine());
			}
			
		    HttpEntity entity = rsp.getEntity();
		    String rspString = EntityUtils.toString(entity,(charset==null||charset.equals(""))?"UTF-8":charset);
		    
		    System.out.println(rspString);
		    
		    contextRsp = new HashMap<HttpContext, String>(1);
		    
		    contextRsp.put(context, rspString);
		    
		} finally {
			if(rsp != null)
		    rsp.close();
		}
		
		return contextRsp;
	}
	
}
