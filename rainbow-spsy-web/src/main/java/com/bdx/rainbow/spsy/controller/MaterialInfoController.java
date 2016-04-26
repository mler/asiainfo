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
import com.bdx.rainbow.spsy.bean.MaterialPurchaseInfo;
import com.bdx.rainbow.spsy.common.DateUtil;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginPurchaseInfo;
import com.bdx.rainbow.spsy.service.IMaterialInfoService;
import com.bdx.rainbow.spsy.service.IParamDetailService;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.urs.entity.ParamDetail;
import com.bdx.rainbow.util.JsonObject;

@Controller
@RequestMapping("/material")
public class MaterialInfoController {
	private final static Logger log=LoggerFactory.getLogger(MaterialInfoController.class);
	
	@Autowired
	private IMaterialInfoService materialInfoService;
	
	@Autowired
	private IParamDetailService paramDetailService;
	
	@RequestMapping(value = {"/init"}, method = RequestMethod.GET)
	public ModelAndView init(Model model)throws Exception{
		log.info("采购订单查询。。。");
		return new ModelAndView("/material/materialInit");
    }
	
	@RequestMapping(value = {"/list"}, method = RequestMethod.POST)
	public ModelAndView list(Model model,TOriginPurchaseInfo purchaseInfo,PageInfo pageinfo,HttpServletRequest request) throws BusinessException{
		log.info("查询列表。。。。");		
		String pagestart = request.getParameter("pageStart");
		if(pagestart!=null){
          pageinfo.setPageStart(Integer.parseInt(pagestart));
		}
		int limit = pageinfo.getPageCount();
		int start = pageinfo.getPageStart();
		IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
		int companyId = userInfo.getUser().getCorpId();
		purchaseInfo.setEnterpriseId(companyId);
		List<MaterialPurchaseInfo> purchaseInfos =  materialInfoService.getPurchaseInfoList(purchaseInfo, start, limit);
		int total = materialInfoService.countPurchaseInfo(purchaseInfo);
		pageinfo.setTotalCount(total);
		model.addAttribute("purchaseInfos", purchaseInfos);
		model.addAttribute("pageinfo", pageinfo);
		return new ModelAndView("/material/materialList");		
	}
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	public ModelAndView create(Model model)throws Exception{
		log.info("采购订单新增。。。");
		List<ParamDetail> params = paramDetailService.findCacheByKey("MATERIAL_TYPE");
		model.addAttribute("params", params);
		model.addAttribute("action", "原材料采购信息添加");
		model.addAttribute("nextAction", "create");	
		return new ModelAndView("/material/materialEdit");
    }
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonObject savePurchase(MaterialPurchaseInfo purchaseInfo, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		JsonObject jo = new JsonObject(true, "保存成功");
		try {
			String purchaseDate = request.getParameter("purchaseDate");
			if(purchaseDate!=null && !purchaseDate.equals("")){
				purchaseInfo.setPurchaseTime(DateUtil.getTimestamp(purchaseDate));
			}
			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
			String userLoginName = userInfo.getUser().getUserLoginName();
			int companyId = userInfo.getUser().getCorpId();
			purchaseInfo.setEnterpriseId(companyId);
            purchaseInfo.setCreateStaff(userLoginName);
			materialInfoService.insertPurchaseInfo(purchaseInfo);
		} catch (Exception e) {
			e.printStackTrace();
			jo.setSuccess(false);
			jo.setMessage("保存异常，请重试");
		}
		return jo;
	}
	
	@RequestMapping(value = {"/update"}, method = RequestMethod.GET)
	public ModelAndView update(Model model, HttpServletRequest request)throws Exception{
		log.info("采购订单修改。。。");
		String purchaseId = request.getParameter("purchaseId");
		MaterialPurchaseInfo purchaseInfo = materialInfoService.getPurchaseInfoBykey(Integer.parseInt(purchaseId));		
		List<ParamDetail> params = paramDetailService.findCacheByKey("MATERIAL_TYPE");
		model.addAttribute("params", params);
		model.addAttribute("purchaseInfo",purchaseInfo);
		model.addAttribute("action", "原材料采购信息修改");
		model.addAttribute("nextAction", "update");	
		return new ModelAndView("/material/materialEdit");
    }
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonObject updatePurchase(MaterialPurchaseInfo purchaseInfo, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		JsonObject jo = new JsonObject(true, "修改成功");
		try {
			String purchaseDate = request.getParameter("purchaseDate");
			if(purchaseDate!=null && !purchaseDate.equals("")){
				purchaseInfo.setPurchaseTime(DateUtil.getTimestampFromString(purchaseDate, "yyyy-MM-dd"));
			}
			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
			String userLoginName = userInfo.getUser().getUserLoginName();
            purchaseInfo.setUpdateStaff(userLoginName);
			materialInfoService.updatePurchaseInfo(purchaseInfo);
		} catch (Exception e) {
			e.printStackTrace();
			jo.setSuccess(false);
			jo.setMessage("保存异常，请重试");
		}
		return jo;
	}
	
	@RequestMapping(value = {"/selectlist"}, method = RequestMethod.POST)
	public ModelAndView selectlist(Model model,HttpServletRequest request) throws BusinessException{
		log.info("查询列表。。。。");		
		MaterialPurchaseInfo purchaseInfo = new MaterialPurchaseInfo();
		IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
		int companyId = userInfo.getUser().getCorpId();
		purchaseInfo.setEnterpriseId(companyId);
		String startDate = request.getParameter("startDate");
		if(startDate!=null && !startDate.equals("")){
			purchaseInfo.setStartTime(DateUtil.getTimestamp(startDate));
		}
		String endDate = request.getParameter("endDate");
		if(endDate!=null && !endDate.equals("")){
			purchaseInfo.setEndTime(DateUtil.getTimestamp(endDate));
		}
		List<MaterialPurchaseInfo> purchaseInfos =  materialInfoService.getPurchaseInfos(purchaseInfo);
		model.addAttribute("purchaseInfos", purchaseInfos);
		return new ModelAndView("/material/materialSelectList");		
	}
	
	@RequestMapping(value = {"/selectDecList"}, method = RequestMethod.POST)
	public ModelAndView list(Model model,TOriginPurchaseInfo purchaseInfo,HttpServletRequest request) throws BusinessException{
		log.info("查询列表。。。。");		
		IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
		int companyId = userInfo.getUser().getCorpId();
		purchaseInfo.setEnterpriseId(companyId);
		List<MaterialPurchaseInfo> purchaseInfos =  materialInfoService.getPurchaseInfoList(purchaseInfo, -1, 0);
		model.addAttribute("purchaseInfos", purchaseInfos);
		return new ModelAndView("/material/materialdecList");		
	}
}
