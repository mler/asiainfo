package com.bdx.rainbow.mapp.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.common.SystemException;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.model.req.YZ0005Request;
import com.bdx.rainbow.mapp.model.rsp.YZ0005Response;
import com.bdx.rainbow.service.html_analysis.INetArticleService;
import com.bdx.rainbow.service.html_analysis.constant.ArticleConstant;
import com.bdx.rainbow.service.html_analysis.entity.Article;
import com.bdx.rainbow.service.html_analysis.entity.ArticleModule;

/**
 * 
 * mapp demo 2014/11/19 
 *
 */
@Service("yz0005")
@Action(bizcode="yz0005",version="1.0",usercheck=true, ipcheck=false)
@Scope("prototype")
public class YZ0005Action extends AbstractMappAction<YZ0005Request, YZ0005Response> implements ApplicationContextAware {
	
	private INetArticleService articleService;
	
	private ApplicationContext context;
	
	@Override
	public void doAction(YZ0005Request request, YZ0005Response response) throws Exception {
		
		String url = request.getUrl();
		
		if(StringUtils.isBlank(url))
			throw new Exception("请求的url不能为空");
		
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
		
		if(StringUtils.isNotBlank(url))
		{
			Article a = articleService.getArticleContent(url);
			response.setTitle(a.getTitle());
			response.setContent(a.getContent());
			response.setCreateTime(a.getCreateTime());
			response.setUrl(url);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
		
	}

}
