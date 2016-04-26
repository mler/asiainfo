package com.bdx.rainbow.service.html_analysis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.common.util.HttpClientUtil;
import com.bdx.rainbow.service.html_analysis.INetArticleService;
import com.bdx.rainbow.service.html_analysis.constant.ArticleConstant;
import com.bdx.rainbow.service.html_analysis.entity.Article;

@Service("xianyangArticleServiceImpl")

public class XianYangArticleServiceImpl implements INetArticleService{

	private final Log log = LogFactory.getLog(XianYangArticleServiceImpl.class);
	
	private final static String domain = "http://www.xysfda.gov.cn";
	
	public Map<String,Long> getPageInfo(Document doc) throws Exception {

		Elements td_elements = doc.select("div#center").select("table").get(0).select("td");
		
//		if(td_elements == null && td_elements.size() < 65)
//			return null;
		/**
		 * <p>共<span>237</span> 条信息 共<span>10</span>页 第1页 
		 * [<a href="?site_id=CMSsfda&amp;cat_id=22333&amp;cur_page=1">首页</a>]
		 * [<a href="?site_id=CMSsfda&amp;cat_id=22333&amp;cur_page=1">上一页</a>]
		 * [<a href="?site_id=CMSsfda&amp;cat_id=22333&amp;cur_page=2">下一页</a>]
		 * [<a href="?site_id=CMSsfda&amp;cat_id=22333&amp;cur_page=10">未页</a>]</p>
		 */
		
		int i=0;
		for(Element td_e : td_elements)
		{
			log.debug("======================================[  "+i+"  ]================================");
			log.debug(td_e.outerHtml());
			i++;
		}
		
		Element pageInfo_e 		= td_elements.last();
		Elements span_elements  = pageInfo_e.select("span");
//		Elements a_elements 	= pageInfo_e.select("a");
		log.debug(pageInfo_e.html());
		
		String total_str 			= span_elements.get(0).text().trim();//总记录数
		String total_page_str		= span_elements.get(1).text().trim();//总页数
		String pageinfo_text = pageInfo_e.text();
		int start = pageinfo_text.indexOf("第")+1;
		int end = pageinfo_text.indexOf("页", start);
		String cur_page_str = pageInfo_e.text().substring(start,end);//当前页数
		log.debug("当前页数："+cur_page_str);
		
		Map<String,Long> pageInfo = new HashMap<String, Long>();
//		PageInfo pageInfo = new PageInfo();
		pageInfo.put(ArticleConstant.PAGEINFO_TOTAL_COUNT,(total_str == null || StringUtils.isEmpty(total_str)?0:Long.valueOf(total_str)));
		pageInfo.put(ArticleConstant.PAGEINFO_PAGE_COUNT,(total_page_str == null || StringUtils.isEmpty(total_page_str)?1L:Long.valueOf(total_page_str)));
		
		/**
		 * 对当前页面进行处理，以防止页面出现页码为负数和0，以及大于总页数的情况
		 * 当前页>总页数 则当前页=总页数
		 * 当前页<1 则 当前页=1
		 * 1<当前页<总页数 则当前页即为本值
		 * 
		 */
		Long cur_page = cur_page_str == null || StringUtils.isEmpty(cur_page_str)?1:Long.valueOf(cur_page_str);
		if(cur_page > pageInfo.get(ArticleConstant.PAGEINFO_PAGE_COUNT))
			pageInfo.put(ArticleConstant.PAGEINFO_PAGE_COUNT,(pageInfo.get(ArticleConstant.PAGEINFO_PAGE_COUNT)));
		else if(cur_page < 1)
			pageInfo.put(ArticleConstant.PAGEINFO_CUR_PAGE,1L);
		else
			pageInfo.put(ArticleConstant.PAGEINFO_CUR_PAGE,(cur_page_str == null || StringUtils.isEmpty(cur_page_str)?1L:Long.valueOf(cur_page_str)));
		
		log.debug(pageInfo_e.outerHtml());
		
		return pageInfo;
	
	}

	public List<Article> listArticle(Document doc) throws Exception {

		Elements td_elements = doc.select("div#center").select("table").get(0).select("td");
		
		/**
		 * 处理文章列表页面
		 * **/
		
//		if(td_elements == null || td_elements.size() < 64)
//			return null;
		//列表在倒数第二个td上
		Element articles_e = td_elements.get(td_elements.size()-2);
		log.debug(articles_e.outerHtml());
		
		Elements row_elements = articles_e.select("div");
		
		if(row_elements == null || row_elements.isEmpty())
			return null;
		
		List<Article> a_list = new ArrayList<Article>(row_elements.size());
		
		for(Element row_e : row_elements)
		{
			log.debug(row_e.outerHtml());
			
			//获取文章列表中，每一行的中的列对象，即每个cell
			Elements cell_elements = row_e.select("div > div");
			if(row_e.select("div") == null || row_e.select("div").size() <2)
				continue;
			
			//判断是否有<a>标签
			Elements a_elements = cell_elements.get(0).select("a");
			if(a_elements == null || a_elements.isEmpty())
				continue;
			
			Element a_e = a_elements.get(0);
			Article a = new Article();
			a_list.add(a);
			a.setUrl(a_e.attr("href"));
			a.setTitle(a_e.attr("title"));
			
			Element date_e = cell_elements.get(1);
			if(date_e != null && StringUtils.isEmpty(date_e.text()) == false)
			{
				a.setCreateTime(date_e.text());
			}
		}
	
		//调试
		if(log.isDebugEnabled())
		{
			for(Article a : a_list)
				log.debug(a.getTitle()+ " : "+a.getContent());
		}
		
		return a_list;
	
	}

	public Document listArticlePageHtml(String uri, Long cur_page)
			throws Exception {
		if(cur_page == null)
			cur_page = 1L;
		String url = (uri.indexOf(domain)>=0?uri:domain+uri)+"&cur_page="+cur_page;
		
		boolean proxy_flag = ArticleConstant.ARTICLE_PROXY_FLAG_VALUE;
		
		String html = null;
		
		if(proxy_flag == true)
		{
			
			String proxy_url = ArticleConstant.ARTICLE_PROXY_URL_VALUE;
			int proxy_port = ArticleConstant.ARTICLE_PROXY_PORT_VALUE;
			
			html = HttpClientUtil.getStringResponseByProxy(url,"get", null, null, null,proxy_url,proxy_port,"utf-8");
		}
		else
			html = HttpClientUtil.getStringResponse(url,"get", null, null, null,"utf-8");
		
		Document doc = Jsoup.parse(html);
		
		return doc;
	
	}

	public Article getArticleContent(String uri) throws Exception {
		String url = (uri.indexOf(domain)>=0?uri:domain+uri);
		boolean proxy_flag = ArticleConstant.ARTICLE_PROXY_FLAG_VALUE;
		String html = null;
		
		if(proxy_flag == true)
		{
			String proxy_url = ArticleConstant.ARTICLE_PROXY_URL_VALUE;
			int proxy_port = ArticleConstant.ARTICLE_PROXY_PORT_VALUE;
			html = HttpClientUtil.getStringResponseByProxy(url, null, null, null,proxy_url,proxy_port,"utf-8");
		}
		else
			html = HttpClientUtil.getStringResponse(url, null, null, null,"utf-8");
		
		Document doc = Jsoup.parse(html);
		log.debug(doc.outerHtml());
		
		Elements div_elements = doc.select("div.con");
		if(div_elements == null || div_elements.isEmpty())
			return null;
		
		/**
		 * <div class='con'>为内容信息 
		 **/
		Element content_element = div_elements.get(0);
		
		Elements img_elements=content_element.select("img");
		Elements a_elements=content_element.select("a");
		for(Element e:img_elements)
		{
			
			String src=e.attr("src");
			if(StringUtils.isNotBlank(src) && src.startsWith("http") == false
					&&  src.startsWith("www") == false)
			{
				e.attr("src", domain+src);
			}
		}
		for(Element e:a_elements)
		{
			
			String href=e.attr("href");
			if(StringUtils.isNotBlank(href) && href.startsWith("http") == false  && 
					href.startsWith("www") == false)
			{
				e.attr("href", domain+href);
			}
		}
		
		Article a = new Article();
		a.setContent(content_element.outerHtml());
		return a;
	
	}
	
}

	
		
		
	


