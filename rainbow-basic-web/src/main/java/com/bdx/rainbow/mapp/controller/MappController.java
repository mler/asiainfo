package com.bdx.rainbow.mapp.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdx.rainbow.mapp.core.client.ClientHandler;
import com.bdx.rainbow.mapp.core.context.MappContext;

@Controller
public class MappController {
	
	public final Logger log = LoggerFactory.getLogger(MappController.class);
	public static final Set<String> sessionKeys = new HashSet<String>();

	static {
		sessionKeys.add(MappContext.MAPPCONTEXT_USER);
		sessionKeys.add(MappContext.MAPPCONTEXT_RIGHT);
		sessionKeys.add(MappContext.MAPPCONTEXT_IMEI);
		sessionKeys.add(MappContext.MAPPCONTEXT_DEVICE);
	}
	@Autowired
    private ClientHandler jsonHandler;
	
	@RequestMapping(value = {"/rest"}, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getJsonRsp(@RequestParam(value = "bizCode", required = false) String bizCode,
	                             @RequestParam(value = "version", required = false) String version,
	                             @RequestParam(value = "msg", required = false) String msg, HttpServletRequest request) throws Exception 
	{
		StringBuffer url = request.getRequestURL();
        System.out.println("================="+url+"===============");
		System.out.println("MappController.getJsonRsp.msg:"+msg);
		
		if (StringUtils.isBlank(msg)) {
			return "msg isBlank";
		}
		
		/**  **/
		Set<String> rights = new HashSet<String>(0);

		Map<String,Object> attrMap = new HashMap<String, Object>(0);
		attrMap.put(MappContext.MAPPCONTEXT_REQUEST_IP,request.getRemoteHost());
		attrMap.put(MappContext.MAPPCONTEXT_RIGHT, rights);
		attrMap.put(MappContext.MAPPCONTEXT_SESSIONID, request.getSession().getId());
		attrMap.put(MappContext.MAPPCONTEXT_USER, request.getSession().getAttribute(MappContext.MAPPCONTEXT_USER));
		attrMap.put(MappContext.MAPPCONTEXT_IMEI, request.getSession().getAttribute(MappContext.MAPPCONTEXT_IMEI));
		attrMap.put(MappContext.MAPPCONTEXT_DEVICE, request.getSession().getAttribute(MappContext.MAPPCONTEXT_DEVICE));
		
		attrMap.put("ServletContext", request.getSession().getServletContext());
//        StringBuffer url = request.getRequestURL();
//        System.out.println("================="+url+"===============");
		
		/*
		 * StringBuffer url = request.getRequestURL();  
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString(); 
		Integer port = request.getServerPort();	
		String contextPath = "";
		if(tempContextUrl.indexOf(port.toString())>0){
			contextPath += tempContextUrl + request.getContextPath();
		}else{
			contextPath += tempContextUrl;
		}*/
		//attrMap.put(MappContext.MAPPCONTEXT_CONTEXTPATH,contextPath);
		
		MappContext.clearContext();
		
		try
		{
			
//			log.info("sessionid {}", RequestContext.getHttpSession().getId());
			String ret = jsonHandler.doHandle(bizCode, version, msg, attrMap);
			
			log.info("ret:"+ret);

			updateSession(request);
			
			return ret;
		}
		catch (Exception e) {
			log.error("mapp access error", e);
		}
		return null;
	}
	
	private void updateSession(HttpServletRequest request) {
		if (log.isInfoEnabled()) {
			log.info("==================MappContext.context:"+request.getSession().getId()+"======================");
		}
		
		for(String key : sessionKeys)
		{
			if(MappContext.getAttribute(key) != null)
			{
//				if(MappContext.getAttribute(key) instanceof SysUser)
//				{
//					if (log.isInfoEnabled()) {
//						log.info(((SysUser)MappContext.getAttribute(key)).getUserName()+"======"+((SysUser)MappContext.getAttribute(key)).getUserName());
//					}
//				}
//				if (log.isInfoEnabled()) {
//					log.info("{},,,{}", key, MappContext.getAttribute(key));
//				}
				request.getSession().setAttribute(key, MappContext.getAttribute(key));
			}
		}
	}


//	public final static void main(String[] args) throws Exception
//	{
//
////		File file = new File("/Users/mler/Desktop/5.jpg");
//////		BufferedImage img = ImageIO.read(file);
////		byte[] data = null;
////		FileImageInputStream input = null;
////		try {
////			input = new FileImageInputStream(file);
////			ByteArrayOutputStream output = new ByteArrayOutputStream();
////			byte[] buf = new byte[1024];
////			int numBytesRead = 0;
////			while ((numBytesRead = input.read(buf)) != -1) {
////				output.write(buf, 0, numBytesRead);
////			}
////			data = output.toByteArray();
////			output.close();
////			input.close();
////		} catch (FileNotFoundException ex1) {
////			ex1.printStackTrace();
////		} catch (IOException ex1) {
////			ex1.printStackTrace();
////		}
//////		http://60.194.3.162/rainbow/rest
////		String url = "http://60.194.3.162/rainbow/rest";
//		String url = "http://localhost:8080/rest";
//		BDXDatapackage pkg = new BDXDatapackage();
//		BDXHeader header = new BDXHeader();
//		header.setBizCode("yz0004");
//		pkg.setHeader(header);
////
////
//		YZ0004Request req = new YZ0004Request();
//		req.setModuleCode("003001");
//		req.setPageNumber(1);
////		YZ0003Request req = new YZ0003Request();
////		req.setCode("222222222");
////		req.setImgs(new ArrayList<byte[]>(1));
////		req.getImgs().add(data);
//
//		pkg.setBody(req);
//
//		String msg = new ObjectMapper().writeValueAsString(pkg);
//		System.out.println("请求："+msg);
//		Map<String,String> param = new HashMap<String, String>(1);
//		param.put("msg", msg);
//
//		Map<String,String> header1 = new HashMap<String, String>(1);
//		header1.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
//		header1.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//
//		if(url.indexOf("localhost")<0)
//		{
//	//		HttpClientUtil.getStringResponseByProxy(url, type, headers, cookies, params, proxyUrl, proxyPort, charset)
//			String html = HttpClientUtil.getStringResponseByProxy(url,"post",header1, null, param,  "10.10.10.78", 8080,"utf-8");
////			String html = HttpClientUtil.getStringResponse(url,"post",header1, null, param,"utf-8");
//
//			System.out.println(html);
//		}
//		else
//		{
////			HttpClientUtil.getStringResponseByProxy(url, type, headers, cookies, params, proxyUrl, proxyPort, charset)
////			String html = HttpClientUtil.getStringResponseByProxy(url,"post",header1, null, param,  "10.10.10.78", 8080,"utf-8");
//			String html = HttpClientUtil.getStringResponse(url,"post",header1, null, param,"utf-8");
//
//			System.out.println(html);
//		}
//
//	}
}
