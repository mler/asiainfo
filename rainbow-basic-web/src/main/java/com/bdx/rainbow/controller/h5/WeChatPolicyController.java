package com.bdx.rainbow.controller.h5;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.bean.ResultBean;
import com.bdx.rainbow.service.html_analysis.INetArticleService;
import com.bdx.rainbow.service.html_analysis.constant.ArticleConstant;
import com.bdx.rainbow.service.html_analysis.entity.Article;
import com.bdx.rainbow.service.html_analysis.entity.ArticleModule;

@RequestMapping("/weChatPolicy")
@Controller
public class WeChatPolicyController {
	private static final Logger log = LoggerFactory.getLogger(WeChatPolicyController.class);
	
	@Autowired
	@Qualifier(value="cfdaArticleServiceImpl")
	private INetArticleService articleService;
	
	@RequestMapping(value = "/articleList")
	@ResponseBody
	public ResultBean listArticle(String moduleCode,Integer curPage,String title,String title2,Model model, ServletRequest request) throws Exception {
		
		if(curPage == null)
			curPage = 1;
		
		ArticleModule module = ArticleConstant.MODULE_MAP.get(moduleCode);
		
		ResultBean resultBean = new ResultBean(true);
		try {
			Document doc = articleService.listArticlePageHtml(module.getUrl(), Long.valueOf(curPage));
			Map<String,Long> pageinfoMap = articleService.getPageInfo(doc);
			List<Article> articleList = articleService.listArticle(doc);
			
			PageInfo pageinfo = new PageInfo();
			pageinfo.setPageCount(Integer.valueOf(pageinfoMap.get(ArticleConstant.PAGEINFO_PAGE_COUNT).toString()));
			pageinfo.setTotalCount(Integer.valueOf(pageinfoMap.get(ArticleConstant.PAGEINFO_TOTAL_COUNT).toString()));
			pageinfo.setPageStart(curPage);
//			if(articleList.size()>6)
//				articleList = articleList.subList(0, 6);//为了演示，暂时这样
			model.addAttribute("title", module.getName());
			model.addAttribute("title2", title2);
			model.addAttribute("moduleCode", moduleCode);
		    model.addAttribute("pageinfo", pageinfo);
		    model.addAttribute("articleList", articleList);
		    Map resultMap = new HashMap();
		    resultMap.put("articleList", articleList);
		    resultMap.put("title", title);
		    resultMap.put("title2", title2);
		    resultMap.put("moduleCode", moduleCode);
		    resultMap.put("pageinfo", pageinfo);
		    resultBean.setData(resultMap);
		}catch (Exception e) {
			resultBean.setFlag(false);
			log.error(e.getMessage(),e);
			throw new Exception("9999:无法获取数据信息");
		}	
		return resultBean;
	}
	
	
	@RequestMapping(value = "/articlePage")
	public ModelAndView pageArticle(String moduleCode,Integer curPage,String title,String title2,Model model, ServletRequest request) throws Exception {
		
		if(curPage ==null)
			curPage = 1;
		
		ArticleModule module = ArticleConstant.MODULE_MAP.get(moduleCode);
		
		ResultBean resultBean = new ResultBean(true);
		try {
			Document doc = articleService.listArticlePageHtml(module.getUrl(), Long.valueOf(curPage));
			Map<String,Long> pageinfoMap = articleService.getPageInfo(doc);
			List<Article> articleList = articleService.listArticle(doc);
			
			PageInfo pageinfo = new PageInfo();
			pageinfo.setPageCount(Integer.valueOf(pageinfoMap.get(ArticleConstant.PAGEINFO_PAGE_COUNT).toString()));
			pageinfo.setTotalCount(Integer.valueOf(pageinfoMap.get(ArticleConstant.PAGEINFO_TOTAL_COUNT).toString()));
			pageinfo.setPageStart(curPage);
//			if(articleList.size()>6)
//				articleList = articleList.subList(0, 6);//为了演示，暂时这样
			model.addAttribute("title", module.getName());
			model.addAttribute("title2", title2);
			model.addAttribute("moduleCode",moduleCode);
		    model.addAttribute("pageinfo", pageinfo);
		    model.addAttribute("articleList", articleList);
		}catch (Exception e) {
			resultBean.setFlag(false);
			log.error(e.getMessage(),e);
			throw new Exception("9999:无法获取数据信息");
		}	
		return new ModelAndView("/h5/weChat/article/articleList");
	}
	
	@RequestMapping(value = "/articleDetail")
	public ModelAndView getArticleContent(String uri,String moduleCode,Model model, ServletRequest request) throws Exception {
		try {
			
			ArticleModule module = ArticleConstant.MODULE_MAP.get(moduleCode);
			
			if(uri != null && StringUtils.isEmpty(uri) == false)
			{
				Article a = articleService.getArticleContent(uri);
//				a.setaTitle(titleContent);
			    model.addAttribute("a", a);
			    model.addAttribute("moduleCode", module.getCode());
			}
			    
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new Exception("9999:无法获取数据信息");
		}	
		return new ModelAndView("/h5/weChat/article/articleDetail");
	}
	
}