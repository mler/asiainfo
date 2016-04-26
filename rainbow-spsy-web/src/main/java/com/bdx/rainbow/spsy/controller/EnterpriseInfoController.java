package com.bdx.rainbow.spsy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bdx.rainbow.common.SysContants;
import com.bdx.rainbow.spsy.bean.EnterpriseInfo;
import com.bdx.rainbow.spsy.service.IEnterpriseInfoService;
import com.bdx.rainbow.spsy.service.IParamDetailService;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.urs.entity.ParamDetail;
import com.bdx.rainbow.util.JsonObject;

@Controller
@RequestMapping("enterprise")
public class EnterpriseInfoController {
	private final static Logger log=LoggerFactory.getLogger(EnterpriseInfoController.class);
	
	@Autowired
	private IEnterpriseInfoService enterpriseInfoService;
	
	@Autowired
	private IParamDetailService paramDetailService;
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	public ModelAndView init(Model model)throws Exception{
		log.info("企业注册。。。");	
		List<ParamDetail> params = paramDetailService.findCacheByKey("BUSINESS_TYPE");
		model.addAttribute("params", params);
		return new ModelAndView("/regist");
	}
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.POST)
	@ResponseBody
	public JsonObject saveEnterprise(Model model,EnterpriseInfo enterpriseInfo,HttpServletRequest request) throws Exception{
		log.info("新增企业信息。。。");
		JsonObject jo = new JsonObject(true, "注册成功!初始用户名:"+enterpriseInfo.getLegalPersonPhone()+"密码:123456");
		try{
			enterpriseInfoService.updateEnterpriseInfo(enterpriseInfo, null);
		}catch(Exception e){
			e.printStackTrace();
			jo.setSuccess(false);
			String errmsg = e.getMessage();
			if(errmsg!=null && !errmsg.equals("")){
			   jo.setMessage(errmsg);
			}else{
			   jo.setMessage("注册异常，请重试!");
			}
		}
		return jo;
	}
	
	@RequestMapping(value = {"/update"}, method = RequestMethod.GET)
	public ModelAndView update(Model model,HttpServletRequest request) throws Exception{
		log.info("企业信息修改。。。");
		IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
		int companyId = userInfo.getUser().getCorpId();
		EnterpriseInfo enterprise = enterpriseInfoService.getEnterpriseInfo(companyId);
		List<ParamDetail> params = paramDetailService.findCacheByKey("BUSINESS_TYPE");
		model.addAttribute("params", params);
		model.addAttribute("enterprise", enterprise);		
		return new ModelAndView("/enterprise/enterpriseEdit");
	}
	
	@RequestMapping(value = {"/saveEnterprise"}, method = RequestMethod.POST)
	public ModelAndView updateEnterprise(Model model,EnterpriseInfo enterpriseInfo,HttpServletRequest request) throws Exception{
		try{
			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
			String userLoginName =userInfo.getUser().getUserLoginName();
			enterpriseInfoService.updateEnterpriseInfo(enterpriseInfo, userLoginName);
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("enterprise", enterpriseInfo);
		return new ModelAndView("redirect:/enterprise/update.do");
	}
}
