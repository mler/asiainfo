package com.bdx.rainbow.service.html_analysis.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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

@Service
public class CfdaArticleServiceImpl implements INetArticleService{

	private final Log log = LogFactory.getLog(CfdaArticleServiceImpl.class);
	
	private final static String domain = "http://www.sfda.gov.cn/WS01";
	
	public Map<String,Long> getPageInfo(Document doc) throws Exception {

		Elements pageInfo_elements = doc.select("td.pageTdSTR15");
		
//		if(td_elements == null && td_elements.size() < 65)
//			return null;
		/**
		 * <p>共<span>237</span> 条信息 共<span>10</span>页 第1页 
		 * [<a href="?site_id=CMSsfda&amp;cat_id=22333&amp;cur_page=1">首页</a>]
		 * [<a href="?site_id=CMSsfda&amp;cat_id=22333&amp;cur_page=1">上一页</a>]
		 * [<a href="?site_id=CMSsfda&amp;cat_id=22333&amp;cur_page=2">下一页</a>]
		 * [<a href="?site_id=CMSsfda&amp;cat_id=22333&amp;cur_page=10">未页</a>]</p>
		 */
		
//		<tr>
//			<td class="pageTdSTR15">第1页/共124页</td>
//			<td class="pageTdSTR15">共2480条</td>
//		</tr>
		String cur_page_text = pageInfo_elements.get(0).text();
		int start = cur_page_text.indexOf("第")+1;
		int end = cur_page_text.indexOf("页");
		String cur_page_str = cur_page_text.substring(start,end);//当前页数
		
		start = cur_page_text.indexOf("共")+1;
		end = cur_page_text.lastIndexOf("页");
		String page_count_str = cur_page_text.substring(start,end);//总页数
		
		String total_count_text = pageInfo_elements.get(1).text();
		start = total_count_text.indexOf("共")+1;
		end = total_count_text.indexOf("条");
		String total_count_str = total_count_text.substring(start,end);//当前页数
		
		Map<String,Long> pageInfo = new HashMap<String, Long>();
//		PageInfo pageInfo = new PageInfo();
		pageInfo.put(ArticleConstant.PAGEINFO_TOTAL_COUNT,(total_count_str == null || StringUtils.isEmpty(total_count_str)?0:Long.valueOf(total_count_str)));
		pageInfo.put(ArticleConstant.PAGEINFO_PAGE_COUNT,(page_count_str == null || StringUtils.isEmpty(page_count_str)?1L:Long.valueOf(page_count_str)));
		
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
		
//		log.debug(pageInfo_e.outerHtml());
		
		return pageInfo;
	
	}

	public List<Article> listArticle(Document doc) throws Exception {

		Elements td_elements = doc.select("td.ListColumnClass15");
		
		/**
		 * 处理文章列表页面
		 * **/
		
		List<Article> a_list = new ArrayList<Article>(td_elements.size());
		
		for(Element td_e : td_elements)
		{
			log.debug(td_e.outerHtml());
			
//			单个td示例：
//			<td class="ListColumnClass15">
//				<a href="../CL0885/137375.html" target="_blank">
//					<font style="" color="">湖北省食品药品监督管理局 2015年第26期食品安全监督抽检情况</font> 
//				</a> 
//				<span class="listtddate15">(2015-12-03) </span> 
//			</td>

			
			Elements a_elements = td_e.select("a[href]");
			if(a_elements == null || a_elements.size() ==0 )
				continue;
			
			Element a_e = a_elements.get(0);
			
			Article a = new Article();
			a_list.add(a);
			a.setUrl(domain+a_e.attr("href").replaceAll("\\.\\.", ""));
			a.setTitle(a_e.text());
			
			Elements span_elements = td_e.select("span.listtddate15");
			if(span_elements != null && span_elements.size() >0 )
			{
				String createTime = span_elements.get(0).text().trim();
				a.setCreateTime(createTime.replaceAll("\\(", "").replaceAll("\\)", ""));
			}
			
		}
	
		//调试
		if(log.isDebugEnabled())
		{
			for(Article a : a_list)
				log.debug(a.getTitle()+ " : "+a.getCreateTime());
		}
		
		return a_list;
	
	}

	public Document listArticlePageHtml(String uri, Long cur_page)
			throws Exception {
		if(cur_page == null)
			cur_page = 1L;
		String url = (uri.indexOf(domain)>=0?uri:domain+uri);
		
		url = url.substring(0, url.lastIndexOf("/")+1)+(cur_page<=1?"index.html":"index_"+(cur_page-1)+".html");
		
		boolean proxy_flag = ArticleConstant.ARTICLE_PROXY_FLAG_VALUE;
		
		String html = null;
		
		if(proxy_flag == true)
		{
			
			String proxy_url = ArticleConstant.ARTICLE_PROXY_URL_VALUE;
			int proxy_port = ArticleConstant.ARTICLE_PROXY_PORT_VALUE;
			
			html = HttpClientUtil.getStringResponseByProxy(url,"get", null, null, null,proxy_url,proxy_port,"gbk");
		}
		else
			html = HttpClientUtil.getStringResponse(url,"get", null, null, null,"gbk");
		
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
			html = HttpClientUtil.getStringResponseByProxy(url,"get",null, null, null,proxy_url,proxy_port,"gbk");
		}
		else
			html = HttpClientUtil.getStringResponse(url, null, null, null,"gbk");
		
		Document doc = Jsoup.parse(html);
		log.debug(doc.outerHtml());
		
		Elements td_elements = doc.select("td.articlecontent2");
		if(td_elements == null || td_elements.isEmpty())
			return null;
		
		/**
		 * <div class='con'>为内容信息 
		 **/
		Element content_element = td_elements.get(0);
		
		Elements img_elements=content_element.select("img");
		Elements a_elements=content_element.select("a");
		for(Element e:img_elements)
		{
			
			String src=e.attr("src");
			if(StringUtils.isNotBlank(src) && src.startsWith("http") == false
					&&  src.startsWith("www") == false)
			{
				e.attr("src", domain+"/.."+src);
			}
		}
		for(Element e:a_elements)
		{
			
			String href=e.attr("href");
			if(StringUtils.isNotBlank(href) && href.startsWith("http") == false  && 
					href.startsWith("www") == false)
			{
				e.attr("href", domain+"/.."+href);
			}
		}
		
		Elements title_elements = doc.select("td.articletitle2");
		Elements createTime_elements = doc.select("td.articletddate2");
		
		Article a = new Article();
		a.setContent(content_element.html());
		a.setTitle(CollectionUtils.isEmpty(title_elements)?null:title_elements.get(0).text());
		a.setCreateTime(CollectionUtils.isEmpty(createTime_elements)?null:createTime_elements.get(0).text());
		return a;
	
	}
	
	public final static void main(String[] args) throws Exception
	{
		Document doc = Jsoup.parse(new File("/Users/mler/Desktop/cfdaList.html"),"gbk");
		CfdaArticleServiceImpl service = new CfdaArticleServiceImpl();
		Map<String,Long> pageMap = service.getPageInfo(doc);
		for(String key : pageMap.keySet())
			System.out.println(key+":"+pageMap.get(key));
		
	}
	
}

	
		
		
	


