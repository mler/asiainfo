package com.bdx.rainbow.controller.demo;

import com.bdx.rainbow.entity.demo.HmArticle;
import com.bdx.rainbow.entity.demo.HmArticleExample;
import com.bdx.rainbow.service.demo.IHmArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * spring boot mvc demo
 */
@Controller
@RequestMapping("/article")
public class MainController {
	
	@Autowired
	private IHmArticleService hmArticleService;
	
	@RequestMapping
	public String index(Model model) {
		model.addAttribute("testparam", "你好");

		return "index";
	}

	@RequestMapping(value = { "/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public HmArticle findOne( @PathVariable("id") String id ) throws Exception {
		Future<HmArticle> res = hmArticleService.findOne(id);
		return res.get(1000, TimeUnit.MILLISECONDS);
	}


	@RequestMapping(value = { "/" }, method = { RequestMethod.GET })
	@ResponseBody
	public String register( @RequestParam("title") String title ) throws Exception {
		hmArticleService.delete(title);
		return "success";
	}

	@RequestMapping(value = { "/list" }, method = { RequestMethod.GET })
	@ResponseBody
	public String findAll() throws Exception {
		
		HmArticleExample e = new HmArticleExample();
		e.setLimitClauseStart(2);
		e.setLimitClauseCount(20);
		
		
		List<HmArticle> articles = hmArticleService.list(e);
		System.out.println("=================================================");
		for(HmArticle a : articles)
		{
			System.out.println(a.getaId()+":"+a.getaTitle());
		}
		System.out.println("=================================================");
		return "success";
	}

	@RequestMapping("/velocity")
	public ModelAndView velocity( Model model ) {
		
		model.addAttribute("testparam", "hi boot..");
		return new ModelAndView("index");
	}
}
