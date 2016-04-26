package com.bdx.rainbow.service.html_analysis;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;

import com.bdx.rainbow.service.html_analysis.entity.Article;

public interface INetArticleService {
	 
	/**
	 * 根据文章的列表页面的html，分析分页信息
	 * @param doc 文章页面的html代码
	 * @return
	 */
	 public Map<String,Long> getPageInfo(Document doc) throws Exception;
	 /**
	 * 根据相对路径，获取文章的列表信息，使用已有的HmArticle为模板对象
	 * Article详细内容的地址，暂时借用aContent字段
	 * @param doc 列表页面的html
	 * @throws Exception
	 */
	 public List<Article> listArticle(Document doc) throws Exception;
	 /**
	 * 根据cat_id和当前页码，获取文章的列表页面的Document对象
	 * @param cat_id
	 * @param cur_page
	 * @return
	 * @throws Exception
	 */
	public Document listArticlePageHtml(String uri,Long cur_page) throws Exception;
		
	/**
	 * 根据文章的uri获取文章的内容
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public Article getArticleContent(String uri) throws Exception;
	
}
