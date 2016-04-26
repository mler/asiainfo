package com.bdx.rainbow.service.html_analysis.constant;

import java.util.HashMap;
import java.util.Map;

import com.bdx.rainbow.service.html_analysis.entity.ArticleModule;

public class ArticleConstant {

	public static Map<String,String> CLASS_MAP = new HashMap<String, String>();
	
	public static Map<String,ArticleModule> MODULE_MAP = new HashMap<String, ArticleModule>();
	
	public static final String ARTICLE_PROXY_FLAG_KEY = "ARTICLE_PROXY_FLAG";//是否使用代理

	public static final String ARTICLE_PROXY_URL_KEY = "ARTICLE_PROXY_URL";//当proxy_flag==true时，proxy_url为代理地址

	public static final String ARTICLE_PROXY_PORT_KEY = "ARTICLE_PROXY_PORT";//当proxy_flag==true时，proxy_port为代理端口
	
	public static final Boolean ARTICLE_PROXY_FLAG_VALUE =true;//是否使用代理

	public static final String ARTICLE_PROXY_URL_VALUE = "10.10.10.78";//当proxy_flag==true时，proxy_url为代理地址

	public static final Integer ARTICLE_PROXY_PORT_VALUE = 8080;//当proxy_flag==true时，proxy_port为代理端口

	public static final String PAGEINFO_PAGE_COUNT = "PAGE_COUNT";
	
	public static final String PAGEINFO_TOTAL_COUNT = "TOTAL_COUNT";
	
	public static final String PAGEINFO_CUR_PAGE = "CUR_PAGE";
	
	public static final String PAGEINFO_PRE_PAGE_URL = "PRE_PAGE_URL";
	
	public static final String PAGEINFO_NEXT_PAGE_URL = "NEXT_PAGE_URL";
	
	public static final String ANALYZE_TYPE_CFDA = "cfda";
	
	public static final String ANALYZE_TYPE_XIANYANG = "xianyang";
	
	static{
		CLASS_MAP.put("001", "信息公开");
		CLASS_MAP.put("002", "公众服务");
		CLASS_MAP.put("003", "数据查询");
		
		MODULE_MAP.put("003001",new ArticleModule("003001",ANALYZE_TYPE_CFDA ,"总局药品GMP认证", 	"http://www.sfda.gov.cn/WS01/CL0080/index.html"));
		MODULE_MAP.put("003002",new ArticleModule("003002",ANALYZE_TYPE_CFDA ,"食品抽检", 		"http://www.sfda.gov.cn/WS01/CL1664/index.html"));
		MODULE_MAP.put("003003",new ArticleModule("003003",ANALYZE_TYPE_CFDA ,"曝光栏", 			"http://www.sfda.gov.cn/WS01/CL0606/index.html"));
		MODULE_MAP.put("003004",new ArticleModule("003004",ANALYZE_TYPE_CFDA ,"公告通知", 		"http://www.sfda.gov.cn/WS01/CL0007/index.html"));
		MODULE_MAP.put("003005",new ArticleModule("003005",ANALYZE_TYPE_CFDA ,"食品生产监管", 		"http://www.sfda.gov.cn/WS01/CL1598/index.html"));
		MODULE_MAP.put("003006",new ArticleModule("003006",ANALYZE_TYPE_CFDA ,"产品召回", 		"http://www.sfda.gov.cn/WS01/CL0935/index.html"));
		MODULE_MAP.put("003007",new ArticleModule("003007",ANALYZE_TYPE_CFDA ,"征求意见", 		"http://www.sfda.gov.cn/WS01/CL0014/index.html"));
		MODULE_MAP.put("003008",new ArticleModule("003008",ANALYZE_TYPE_CFDA ,"法规文件", 		"http://www.sfda.gov.cn/WS01/CL0006/index.html"));
	}
	
}
