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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.SysContants;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginAgencyCompany;
import com.bdx.rainbow.spsy.service.IAgencyCompanyService;
import com.bdx.rainbow.spsy.service.IParamDetailService;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.urs.entity.ParamDetail;
import com.bdx.rainbow.util.JsonObject;

/**
 * 经销商管理
 * @author tanglian 2016-02-03
 *
 */
@Controller
@RequestMapping("agency")
public class AgencyInfoController {
	private final static Logger log=LoggerFactory.getLogger(AgencyInfoController.class);
	
	@Autowired
	private IAgencyCompanyService agencyCompanyService;
	
	@Autowired
	private IParamDetailService paramDetailService;
	
	@RequestMapping(value = {"/init"},method = RequestMethod.GET)
	public ModelAndView init(Model model) throws BusinessException{
		log.info("经销商查询。。。。");
		return new ModelAndView("/agency/agencyInit");
	}
	
	@RequestMapping(value = {"/list"}, method = RequestMethod.POST)
	public ModelAndView list(Model model,TOriginAgencyCompany agencyInfo,PageInfo pageinfo,HttpServletRequest request) throws BusinessException{
		log.info("查询列表。。。。");
		String pagestart = request.getParameter("pageStart");
		if(pagestart!=null){
          pageinfo.setPageStart(Integer.parseInt(pagestart));
		}
		int limit = pageinfo.getPageCount();
		int start = pageinfo.getPageStart();
		IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
		int companyId = userInfo.getUser().getCorpId();
		agencyInfo.setCreateEnterpriseId(companyId);
		List<TOriginAgencyCompany> agencys = agencyCompanyService.getAgencyList(agencyInfo, start, limit);
		int total = agencyCompanyService.countAgency(agencyInfo);
		pageinfo.setTotalCount(total);
		model.addAttribute("agencys", agencys);
		model.addAttribute("pageinfo", pageinfo);
		return new ModelAndView("/agency/agencyList");
	}
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	public ModelAndView create(Model model) throws Exception{
		log.info("新增经销商信息。。。。");
		List<ParamDetail> salemains = paramDetailService.findCacheByKey("DETECTION_OBJECT_TYPE");
		List<ParamDetail> agencyTypes = paramDetailService.findCacheByKey("AGENCY_TYPE");
		model.addAttribute("salemains", salemains);
		model.addAttribute("agencytypes", agencyTypes);
		model.addAttribute("action", "新增经销商");
		model.addAttribute("nextAction", "create");	
		return new ModelAndView("/agency/agencyEdit");
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonObject saveAgency(TOriginAgencyCompany agencyInfo, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		JsonObject jo = new JsonObject(true, "保存成功");
		try {
			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
			String userLoginName =userInfo.getUser().getUserLoginName();
			int companyId = userInfo.getUser().getCorpId();
			agencyInfo.setCreateStaff(userLoginName);
			agencyInfo.setCreateEnterpriseId(companyId);
			agencyCompanyService.insertAgency(agencyInfo);
		} catch (Exception e) {
			e.printStackTrace();
			jo.setSuccess(false);
			jo.setMessage("保存异常，请重试");
		}
		return jo;
	}
	
	@RequestMapping(value = {"/update"}, method = RequestMethod.GET)
	public ModelAndView update(Model model, HttpServletRequest request) throws Exception{
		log.info("修改经销商信息。。。。");
		String agencyId = request.getParameter("agencyId");
		TOriginAgencyCompany agency = agencyCompanyService.getAgency(Integer.parseInt(agencyId));
		List<ParamDetail> salemains = paramDetailService.findCacheByKey("DETECTION_OBJECT_TYPE");
		List<ParamDetail> agencyTypes = paramDetailService.findCacheByKey("AGENCY_TYPE");
		model.addAttribute("salemains", salemains);
		model.addAttribute("agencytypes", agencyTypes);
		model.addAttribute("action", "修改经销商");
		model.addAttribute("nextAction", "update");
		model.addAttribute("agency", agency);
		return new ModelAndView("/agency/agencyEdit");
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonObject updateAgency(TOriginAgencyCompany agencyInfo, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		JsonObject jo = new JsonObject(true, "修改成功");
		try {
			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
			String userLoginName =userInfo.getUser().getUserLoginName();
			agencyInfo.setUpdateStaff(userLoginName);
			agencyCompanyService.updateAgency(agencyInfo);
		} catch (Exception e) {
			e.printStackTrace();
			jo.setSuccess(false);
			jo.setMessage("保存异常，请重试");
		}
		return jo;
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = {"/shoplist"}, method = RequestMethod.GET)
	public ModelAndView list(Model model,HttpServletRequest request) throws BusinessException{
		log.info("查询商户列表。。。。");
        String agencyName = request.getParameter("agencyName");
        String suname = "";
        if(!agencyName.equals("")){
            suname = java.net.URLDecoder.decode(agencyName);
        }
        TOriginAgencyCompany agencyInfo = new TOriginAgencyCompany();
        agencyInfo.setAgencyName(suname);
		List<TOriginAgencyCompany> agencys = agencyCompanyService.getAgencyList(agencyInfo, -1, 0);
		model.addAttribute("agencys", agencys);
		return new ModelAndView("/agency/agencySelectList");
	}
}
