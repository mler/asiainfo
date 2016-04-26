package com.bdx.rainbow.spsy.controller;

import java.util.List;
import java.util.Map;

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
import com.bdx.rainbow.spsy.bean.ProducerStockOutInfo;
import com.bdx.rainbow.spsy.common.DateUtil;
import com.bdx.rainbow.spsy.common.SpsyConstants;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockIn;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockOut;
import com.bdx.rainbow.spsy.service.IProducerStockService;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.util.JsonObject;

@Controller
@RequestMapping("/stock")
public class ProducerStockController {
     private final static Logger log=LoggerFactory.getLogger(ProducerStockController.class);
     
     @Autowired
     private IProducerStockService producerStockService;
     
     @RequestMapping(value = {"/producer/init"}, method = RequestMethod.GET)
 	 public ModelAndView producerInit(Model model,HttpServletRequest request)throws Exception{
 		log.info("生产企业库存信息查询。。。");
 		String type = request.getParameter("type");
 		//type=1入库信息 type=2出库信息
 		model.addAttribute("type", type);
 		return new ModelAndView("/stock/producerInit");
     }
     
     @RequestMapping(value = {"/producer/list"}, method = RequestMethod.POST)
 	 public ModelAndView producerList(Model model,PageInfo pageinfo,HttpServletRequest request)throws BusinessException{
 		log.info("生产企业库存信息列表。。。");
 		String pagestart = request.getParameter("pageStart");
 		String type = request.getParameter("type");
		if(pagestart!=null){
          pageinfo.setPageStart(Integer.parseInt(pagestart));
		}
		int limit = pageinfo.getPageCount();
		int start = pageinfo.getPageStart();
		int companyId =(Integer)request.getSession().getAttribute("agencyId");
		if(type.equals("1")){
			TOriginProducerStockIn condition = new TOriginProducerStockIn();
			condition.setEnterpriseId(companyId);
			List<TOriginProducerStockIn> stockins = producerStockService.getProducerStockInList(condition, start, limit);
			int total = producerStockService.coutProducerStockIns(condition);
			pageinfo.setTotalCount(total);
			model.addAttribute("stocks", stockins);
			model.addAttribute("pageinfo", pageinfo);
		}else{
			TOriginProducerStockOut condition = new TOriginProducerStockOut();
			condition.setEnterpriseId(companyId);
			List<TOriginProducerStockOut> stockouts = producerStockService.getProducerStockOutList(condition, start, limit);
			int total = producerStockService.countProducerStockOuts(condition);
			pageinfo.setTotalCount(total);
			model.addAttribute("stocks", stockouts);
			model.addAttribute("pageinfo", pageinfo);
		}
		model.addAttribute("type", type);
 		return new ModelAndView("/stock/producerList");
     }
     
     @RequestMapping(value = {"/producer/createin"}, method = RequestMethod.GET)
     public ModelAndView createin(Model model) throws BusinessException{
    	 log.info("生产企业入库信息新增。。。。。");
    	 model.addAttribute("nextAction", "createin");
    	 model.addAttribute("action","入库信息添加");
    	 return new ModelAndView("/stock/producerInEdit");
     }
     
    @RequestMapping(value = "/createin", method = RequestMethod.POST)
 	@ResponseBody
 	public JsonObject saveProducerStockIn(TOriginProducerStockIn stockin,RedirectAttributes redirectAttributes,HttpServletRequest request) throws BusinessException{
 		JsonObject jo = new JsonObject(true,"保存成功");
 		try{
 			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
 			String enterpriseName = (String) request.getSession().getAttribute("enterpriseName");
 			String userLoginName = userInfo.getUser().getUserLoginName();
 			stockin.setCreateStaff(userLoginName);
 			stockin.setEnterpriseName(enterpriseName);
 			int companyId =(Integer)request.getSession().getAttribute("agencyId");
 			stockin.setEnterpriseId(companyId); 			
 			String productionDate = request.getParameter("productionTime");
 			if(productionDate!=null && !productionDate.equals("")){
 				stockin.setProductionDate(DateUtil.getTimestamp(productionDate));
 			}
 			String skucodelist = request.getParameter("codelist");
 			producerStockService.insertProducerStockIn(stockin, skucodelist);
 		}catch(Exception e){
 			e.printStackTrace();
 			jo.setSuccess(false);
 			jo.setMessage("保存异常,请重试！");
 		}
 		return jo;
 	}
     
    @RequestMapping(value = {"/producer/updatein"}, method = RequestMethod.GET)
    public ModelAndView updatein(Model model,HttpServletRequest request) throws BusinessException{
	   	 log.info("生产企业入库信息修改。。。。。");
	   	 String operationInId = request.getParameter("operationInId");
	   	 Map<String,Object> map = producerStockService.getProducerStockIn(Integer.parseInt(operationInId));
	     model.addAttribute("stock", map.get("stock"));
	     model.addAttribute("codelist", map.get("codelist"));
	     model.addAttribute("materialshow", map.get("materialshow"));
	   	 model.addAttribute("nextAction", "updatein");
	   	 model.addAttribute("action","入库信息修改");
	   	 return new ModelAndView("/stock/producerInEdit");
    }
    
    @RequestMapping(value = "/updatein", method = RequestMethod.POST)
 	@ResponseBody
 	public JsonObject saveGoodsStockIn(TOriginProducerStockIn stockin,RedirectAttributes redirectAttributes,HttpServletRequest request) throws BusinessException{
 		JsonObject jo = new JsonObject(true,"修改成功");
 		try{
 			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
 			String enterpriseName = (String) request.getSession().getAttribute("enterpriseName");
 			String userLoginName = userInfo.getUser().getUserLoginName();
 			stockin.setUpdatStaff(userLoginName);
 			stockin.setEnterpriseName(enterpriseName);
 			int companyId =(Integer)request.getSession().getAttribute("agencyId");
 			stockin.setEnterpriseId(companyId);
 			String productionDate = request.getParameter("productionTime");
 			if(productionDate!=null && !productionDate.equals("")){
 				stockin.setProductionDate(DateUtil.getTimestamp(productionDate));
 			}
 			String skucodelist = request.getParameter("codelist");
 			producerStockService.updateProducerStockIn(stockin, skucodelist);
 		}catch(Exception e){
 			e.printStackTrace();
 			jo.setSuccess(false);
 			jo.setMessage("修改异常,请重试！");
 		}
 		return jo;
 	}
    
     @RequestMapping(value = {"/producer/createout"}, method = RequestMethod.GET)
     public ModelAndView createout(Model model,HttpServletRequest request) throws BusinessException{
    	 log.info("生产企业出库信息添加。。。。。");
    	 model.addAttribute("nextAction", "createout");
    	 model.addAttribute("action","导入出库信息");
    	 return new ModelAndView("/stock/producerOutInEdit");
     }
     
     @RequestMapping(value = {"/producer/updateout"}, method = RequestMethod.GET)
     public ModelAndView updateOut(Model model,HttpServletRequest request) throws BusinessException{
    	 log.info("生产企业出库信息修改。。。。。");
    	 String operationOutId = request.getParameter("operationOutId");
    	 Map<String,Object> resultmap = producerStockService.getStockOutInf(Integer.parseInt(operationOutId));
    	 model.addAttribute("stock", resultmap.get("stockOut"));
    	 model.addAttribute("materialshow", resultmap.get("materialshow"));
    	 model.addAttribute("nextAction", "updateout");
    	 model.addAttribute("action","出库信息修改");
    	 return new ModelAndView("/stock/producerOutInEdit");
     }
     
     @RequestMapping(value = {"/producer/selectlist"}, method = RequestMethod.POST)
 	 public ModelAndView producerSelectList(Model model,TOriginProducerStockIn condition,HttpServletRequest request)throws BusinessException{
 		log.info("从入库信息导入。。。");
 		int companyId =(Integer)request.getSession().getAttribute("agencyId");
		condition.setEnterpriseId(companyId);
		String productionTime = request.getParameter("productionTime");
		if(productionTime!=null && !productionTime.equals("")){
			condition.setProductionDate(DateUtil.getTimestamp(productionTime));
		}
		condition.setStatus(SpsyConstants.STATUS_STOCK_NO_OUT);
		List<TOriginProducerStockIn> stockins = producerStockService.getProducerStockInList(condition, -1, 0);
		model.addAttribute("stockins", stockins);			
 		return new ModelAndView("/stock/producerSelectIn");
     }
     
     @RequestMapping(value = {"/producer/createout"}, method = RequestMethod.POST)
     @ResponseBody
     public JsonObject saveOutInfo(Model model,ProducerStockOutInfo stockOut,HttpServletRequest request) throws BusinessException{
    	 log.info("生产企业出库信息添加。。。。。");
    	 JsonObject jo = new JsonObject(true,"导入成功");
    	 try{
    		 IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
    		 String enterpriseName = (String) request.getSession().getAttribute("enterpriseName");
    		 int companyId =(Integer)request.getSession().getAttribute("agencyId");
    	 	 String userLoginName = userInfo.getUser().getUserLoginName();
    	 	 int enterpriseId = userInfo.getUser().getCorpId();
    	 	 stockOut.setEnterpriseName(enterpriseName);
    	 	 stockOut.setEnterpriseId(companyId);
    	 	 stockOut.setCreateStaff(userLoginName);
    		 producerStockService.insertProducerStockOut(stockOut,enterpriseId);
    	 }catch(BusinessException e){
    		 e.printStackTrace();
    		 jo.setSuccess(false);
    		 jo.setMessage("导入失败!");
    	 }
    	 return jo;
     }
     
     @RequestMapping(value = "/producer/updateout", method = RequestMethod.POST)
  	@ResponseBody
  	public JsonObject UpdateGoodsStockIn(ProducerStockOutInfo stockOut,RedirectAttributes redirectAttributes,HttpServletRequest request) throws BusinessException{
  		JsonObject jo = new JsonObject(true,"修改成功");
  		try{
  			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
  			int companyId =(Integer)request.getSession().getAttribute("agencyId");
  			int enterpriseId = userInfo.getUser().getCorpId();
  			String userLoginName = userInfo.getUser().getUserLoginName();
  			stockOut.setUpdatStaff(userLoginName);
  			stockOut.setEnterpriseId(companyId);
  			producerStockService.updateProducerStockOut(stockOut,enterpriseId);
  		}catch(Exception e){
  			e.printStackTrace();
  			jo.setSuccess(false);
  			jo.setMessage("修改异常,请重试！");
  		}
  		return jo;
  	}
}
