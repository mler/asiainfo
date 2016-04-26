package com.bdx.rainbow.mapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.common.SystemException;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.model.req.YZ0004Request;
import com.bdx.rainbow.mapp.model.rsp.YZ0004Response;
import com.bdx.rainbow.service.html_analysis.INetArticleService;
import com.bdx.rainbow.service.html_analysis.constant.ArticleConstant;
import com.bdx.rainbow.service.html_analysis.entity.Article;
import com.bdx.rainbow.service.html_analysis.entity.ArticleModule;

/**
 * 根据ModelCode查询CFDA信息
 * mapp demo 2014/11/19 
 *
 */
@Service("yz0004")
@Action(bizcode="yz0004",version="1.0",usercheck=true, ipcheck=false)
@Scope("prototype")
public class YZ0004Action extends AbstractMappAction<YZ0004Request, YZ0004Response> implements ApplicationContextAware{
	
	private ApplicationContext context;
	
	private INetArticleService articleService;
	
	@Override
	public void doAction(YZ0004Request request, YZ0004Response response) throws BusinessException, SystemException,
			Exception {
		
		String moduleCode = request.getModuleCode();
		
		if(StringUtils.isBlank(moduleCode) 
				|| ArticleConstant.MODULE_MAP.get(moduleCode) == null )
			throw new Exception("未知的moduleCode");
		
		ArticleModule module = ArticleConstant.MODULE_MAP.get(moduleCode);
		
		if(ArticleConstant.ANALYZE_TYPE_CFDA.equals(module.getAnalyzeType()))
			articleService = (INetArticleService)context.getBean("cfdaArticleServiceImpl");
		else if(ArticleConstant.ANALYZE_TYPE_XIANYANG.equals(module.getAnalyzeType()))
			articleService = (INetArticleService)context.getBean("xianyangArticleServiceImpl");
		else
			throw new Exception("ArticleModule["+moduleCode+"].analyzeType 类型未知");
		

		try {
			Document doc = articleService.listArticlePageHtml(module.getUrl(), Long.valueOf(request.getPageNumber()));
			Map<String,Long> pageinfo = articleService.getPageInfo(doc);
			List<Article> articleList = articleService.listArticle(doc);
			
			if(articleList != null && articleList.isEmpty() == false)
			{
				List<YZ0004Response.Article> alist = new ArrayList<YZ0004Response.Article>(articleList.size());
				
				for(Article a : articleList)
				{
					YZ0004Response.Article _a = new YZ0004Response.Article();
					_a.setTitle(a.getTitle());
					_a.setUrl(a.getUrl());
					_a.setCreateTime(a.getCreateTime());
					alist.add(_a);
				}
				
				response.setAlist(alist);
			}
			
			if(pageinfo != null)
			{
				response.setTotal(pageinfo.get(ArticleConstant.PAGEINFO_TOTAL_COUNT));
				response.setTotalPage(pageinfo.get(ArticleConstant.PAGEINFO_PAGE_COUNT));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("获取信息失败："+e.getMessage());
		}
		
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}

}
