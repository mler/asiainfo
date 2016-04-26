package com.bdx.rainbow.spsy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.SysContants;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.spsy.common.DateUtil;
import com.bdx.rainbow.spsy.common.SpsyConstants;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginInspectionInfo;
import com.bdx.rainbow.spsy.service.IParamDetailService;
import com.bdx.rainbow.spsy.service.ITInspectionInfoService;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.urs.entity.ParamDetail;
import com.bdx.rainbow.util.JsonObject;

@Controller
@RequestMapping("inspe")
public class InspectionInfoController {
  private final static Logger log=LoggerFactory.getLogger(InspectionInfoController.class);
  
  @Autowired
  private ITInspectionInfoService inspectionInfoService;
  
  @Autowired
  private IParamDetailService paramDetailService;
  
  @RequestMapping(value = {"/init"}, method = RequestMethod.GET)
  public ModelAndView init(Model model)throws Exception{
		log.info("抽检信息查询。。。");
		return new ModelAndView("/inspe/inspeInit");
  }
  
  @RequestMapping(value = {"/list"}, method = RequestMethod.POST)
  public ModelAndView list(Model model,TOriginInspectionInfo inspeInfo,PageInfo pageinfo,HttpServletRequest request) throws BusinessException{
		log.info("查询列表。。。。");	
		String pagestart = request.getParameter("pageStart");
		if(pagestart!=null){
          pageinfo.setPageStart(Integer.parseInt(pagestart));
		}
		int limit = pageinfo.getPageCount();
		int start = pageinfo.getPageStart();
		IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
		int companyId = userInfo.getUser().getCorpId();
        inspeInfo.setCompanyId(companyId);
        inspeInfo.setStatus(SpsyConstants.STATUS_ACTIVE);
		List<TOriginInspectionInfo> inspeInfos =  inspectionInfoService.getInspectionInfoList(inspeInfo,start,limit);
		int total = inspectionInfoService.countInspectionInfo(inspeInfo);
		pageinfo.setTotalCount(total);
		model.addAttribute("inspeInfos", inspeInfos);
		model.addAttribute("pageinfo", pageinfo);
		return new ModelAndView("/inspe/inspelist");		
  }
  
  @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
  public ModelAndView create(Model model)throws Exception{
		log.info("采购订单新增。。。");
		List<ParamDetail> paramsObjects = paramDetailService.findCacheByKey("DETECTION_OBJECT_TYPE");
		List<ParamDetail> paramsMode = paramDetailService.findCacheByKey("DETECTION_MODE");
		List<ParamDetail> paramsResult = paramDetailService.findCacheByKey("DETECTION_RESULT");
		model.addAttribute("oparams", paramsObjects);
		model.addAttribute("mparams", paramsMode);
		model.addAttribute("rparams", paramsResult);
		model.addAttribute("action", "检测报告添加");
		model.addAttribute("nextAction", "create");	
		return new ModelAndView("/inspe/inspeEdit");
  }
  
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public JsonObject saveInspection(TOriginInspectionInfo inspeInfo, RedirectAttributes redirectAttributes,HttpServletRequest request) {
	  JsonObject jo = new JsonObject(true, "保存成功");
	  try {
		  String productionDate = request.getParameter("productionTime");
		  if(productionDate!=null && !productionDate.equals("")){
			 inspeInfo.setProductionDate(DateUtil.getTimestampFromString(productionDate, "yyyy-MM-dd"));
		  }	
		  String detectionTime = request.getParameter("detectionTime");
		  if(detectionTime!=null && !detectionTime.equals("")){
			 inspeInfo.setDetectionDate(DateUtil.getTimestampFromString(detectionTime, "yyyy-MM-dd"));  
		  }
		  IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
		  String userLoginName = userInfo.getUser().getUserLoginName();
		  int companyId = userInfo.getUser().getCorpId();
	      inspeInfo.setCompanyId(companyId);
		  inspeInfo.setCreateStaff(userLoginName);
		  inspectionInfoService.insertInspectionInfo(inspeInfo);
	  } catch (Exception e) {
		  e.printStackTrace();
		  jo.setSuccess(false);
		  jo.setMessage("保存异常，请重试");
	  }
	  return jo;
  }
  
  @RequestMapping(value = {"/update"}, method = RequestMethod.GET)
	public ModelAndView update(Model model, HttpServletRequest request)throws Exception{
		log.info("检测报告修改。。。");
		String detectionId = request.getParameter("productDetectionId");
		TOriginInspectionInfo inspectionInfo = inspectionInfoService.getInspectionInfo(Integer.parseInt(detectionId));
		String detectionReport = inspectionInfo.getDetectionReport();		
		if(detectionReport!=null && !detectionReport.equals("")){
			List<String> fileIds = new ArrayList<String>();
			String[] files = detectionReport.split(";");
			for(int i=0;i<files.length;i++){
				fileIds.add(files[i]);
			}
			model.addAttribute("fileIds", fileIds);
		}
		List<ParamDetail> paramsObjects = paramDetailService.findCacheByKey("DETECTION_OBJECT_TYPE");
		List<ParamDetail> paramsMode = paramDetailService.findCacheByKey("DETECTION_MODE");
		List<ParamDetail> paramsResult = paramDetailService.findCacheByKey("DETECTION_RESULT");
		model.addAttribute("oparams", paramsObjects);
		model.addAttribute("mparams", paramsMode);
		model.addAttribute("rparams", paramsResult);
		model.addAttribute("inspectionInfo",inspectionInfo);
		model.addAttribute("action", "检测报告修改");
		model.addAttribute("nextAction", "update");	
		return new ModelAndView("/inspe/inspeEdit");
  }
  
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  @ResponseBody
  public JsonObject updateInspection(TOriginInspectionInfo inspeInfo, RedirectAttributes redirectAttributes,HttpServletRequest request) {
	  JsonObject jo = new JsonObject(true, "修改成功");
	  try {
		  String productionDate = request.getParameter("productionTime");
		  if(productionDate!=null && !productionDate.equals("")){
			 inspeInfo.setProductionDate(DateUtil.getTimestamp(productionDate));
		  }	
		  String detectionTime = request.getParameter("detectionTime");
		  if(detectionTime!=null && !detectionTime.equals("")){
			 inspeInfo.setDetectionDate(DateUtil.getTimestamp(detectionTime));  
		  }
		  IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
		  String userLoginName = userInfo.getUser().getUserLoginName();
		  inspeInfo.setUpdateStaff(userLoginName);
		  inspectionInfoService.insertInspectionInfo(inspeInfo);
	  } catch (Exception e) {
		  e.printStackTrace();
		  jo.setSuccess(false);
		  jo.setMessage("保存异常，请重试");
	  }
	  return jo;
  }
  
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @ResponseBody
  public JsonObject deleteInspection(@RequestParam("productDetectionIds") List<Integer> inspeIds, RedirectAttributes redirectAttributes,HttpServletRequest request) {
	  JsonObject jo = new JsonObject(true, "删除成功");
	  try {		  
		  inspectionInfoService.deleteInspection(inspeIds);
	  } catch (Exception e) {
		  e.printStackTrace();
		  jo.setSuccess(false);
		  jo.setMessage("删除异常，请重试");
	  }
	  return jo;
  }
  
  @RequestMapping(value = "/selectInspe", method = RequestMethod.POST)
  @ResponseBody
  public JsonObject selectInspe(HttpServletRequest request,TOriginInspectionInfo inspeInfo) {
	  JsonObject jo = new JsonObject(true, "获取检测报告");
	  try {		  
		  int reportId = inspectionInfoService.getSelectInspe(inspeInfo);
		  jo.setData(reportId);
	  } catch (Exception e) {
		  e.printStackTrace();
		  jo.setSuccess(false);
		  jo.setMessage("删除异常，请重试");
	  }
	  return jo;
  }
}
