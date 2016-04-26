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
import com.bdx.rainbow.spsy.bean.GoodStockInfo;
import com.bdx.rainbow.spsy.common.DateUtil;
import com.bdx.rainbow.spsy.common.SpsyConstants;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginGoodsStock;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginMerchantStockOut;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginSkuAgencyRelation;
import com.bdx.rainbow.spsy.service.IGoodsStockInfoService;
import com.bdx.rainbow.spsy.service.IParamDetailService;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.urs.entity.ParamDetail;
import com.bdx.rainbow.util.JsonObject;

@Controller
@RequestMapping("stock")
public class GoodsStockInfoController {	
	private final static Logger log=LoggerFactory.getLogger(GoodsStockInfoController.class);
	
	@Autowired
	private IGoodsStockInfoService goodsStockInfoService;
	
	@Autowired
	private IParamDetailService paramDetailService;
	
	@RequestMapping(value = {"/merchant/init"},method = RequestMethod.GET )
	public ModelAndView initmerchant(Model model) throws BusinessException{
		log.info("零售商出库信息查询。。。。。");
		return new ModelAndView("/stock/merchantInit");
	}
	
	@RequestMapping(value = {"/merchant/list"},method = RequestMethod.POST)
	public ModelAndView listmerchant(Model model,TOriginMerchantStockOut condition,PageInfo pageinfo,HttpServletRequest request) throws BusinessException{
		log.info("查询列表。。。。");
		String pagestart = request.getParameter("pageStart");
		if(pagestart!=null){
          pageinfo.setPageStart(Integer.parseInt(pagestart));
		}
		int limit = pageinfo.getPageCount();
		int start = pageinfo.getPageStart();
		int companyId =(Integer)request.getSession().getAttribute("agencyId");
		condition.setEnterpriseId(companyId);
		List<TOriginMerchantStockOut> stockouts = goodsStockInfoService.getMerchantStockList(condition, start, limit);
		int total = goodsStockInfoService.countMerchantStock(condition);
		pageinfo.setTotalCount(total);
		model.addAttribute("stockouts", stockouts);
		model.addAttribute("pageinfo", pageinfo);
		return new ModelAndView("/stock/merchantList");
	}
	
	@RequestMapping(value = {"/merchant/create"}, method = RequestMethod.GET)
	public ModelAndView createMerchantStock(Model model) throws Exception{
		log.info("零售商出库信息新增。。。。");
		List<ParamDetail> params = paramDetailService.findCacheByKey("SALE_UNIT");
		model.addAttribute("params", params);
		model.addAttribute("action", "零售商出库信息添加");
		model.addAttribute("nextAction", "/merchant/create");	
		return new ModelAndView("/stock/merchantEdit");
	}
	
	@RequestMapping(value = "/merchant/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonObject saveMerchantStock(TOriginMerchantStockOut stockOut,RedirectAttributes redirectAttributes,HttpServletRequest request) throws BusinessException{
		JsonObject jo = new JsonObject(true,"保存成功");
		try{
			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
			String userLoginName = userInfo.getUser().getUserLoginName();
			stockOut.setCreateStaff(userLoginName);
			int companyId =(Integer)request.getSession().getAttribute("agencyId");
			stockOut.setEnterpriseId(companyId);
			String saleDate = request.getParameter("saleTime");
			if(saleDate!=null && !saleDate.equals("")){
				stockOut.setSaleDate(DateUtil.getTimestampFromString(saleDate, "yyyy-MM-dd HH:mm:ss"));
			}
			goodsStockInfoService.insertMerchantStockOut(stockOut);
		}catch(BusinessException e){
			e.printStackTrace();
			jo.setSuccess(false);
			jo.setMessage("保存异常,请重试！");
		}
		return jo;
	}
	
	@RequestMapping(value = {"/merchant/update"}, method = RequestMethod.GET)
	public ModelAndView updateMerchantStock(Model model,HttpServletRequest request) throws Exception{
		String operationOutId = request.getParameter("operationOutId");
		TOriginMerchantStockOut stockOut = goodsStockInfoService.getMerchantStock(Integer.parseInt(operationOutId));
		model.addAttribute("stockout", stockOut);
		List<ParamDetail> params = paramDetailService.findCacheByKey("SALE_UNIT");
		model.addAttribute("params", params);
		model.addAttribute("action", "零售商出库信息修改");
		model.addAttribute("nextAction", "/merchant/update");
		return new ModelAndView("/stock/merchantEdit");
	}
	
	@RequestMapping(value = "/merchant/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonObject updateMerchantStock(TOriginMerchantStockOut stockOut,RedirectAttributes redirectAttributes,HttpServletRequest request) throws BusinessException{
		JsonObject jo = new JsonObject(true,"修改成功");
		try{
			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
			String userLoginName = userInfo.getUser().getUserLoginName();
			stockOut.setUpdateStaff(userLoginName);
			String saleDate = request.getParameter("saleTime");
			if(saleDate!=null && !saleDate.equals("")){
				stockOut.setSaleDate(DateUtil.getTimestampFromString(saleDate, "yyyy-MM-dd HH:mm:ss"));
			}
			goodsStockInfoService.updateMerchantStockOut(stockOut);
		}catch(BusinessException e){
			e.printStackTrace();
			jo.setSuccess(false);
			jo.setMessage("修改异常,请重试！");
		}
		return jo;
	}
	
	@RequestMapping(value = {"/goods/init"},method = RequestMethod.GET )
	public ModelAndView initgoods(Model model, HttpServletRequest request) throws BusinessException{
		log.info("商品出入库信息查询。。。。。");
		//type=01 经销商入库管理 type=02 经销商出库管理 type=03 零售商入库管理
		String type = request.getParameter("type");
		model.addAttribute("type", type);
		return new ModelAndView("/stock/goodsInit");
	}
	
	@RequestMapping(value = {"/goods/list"},method = RequestMethod.POST )
	public ModelAndView listgoods(Model model, TOriginGoodsStock condition,PageInfo pageinfo,HttpServletRequest request) throws BusinessException{
		log.info("查询列表。。。。");
		String pagestart = request.getParameter("pageStart");
		if(pagestart!=null){
          pageinfo.setPageStart(Integer.parseInt(pagestart));
		}
		int limit = pageinfo.getPageCount();
		int start = pageinfo.getPageStart();
		int companyId =(Integer)request.getSession().getAttribute("agencyId");
		String type = request.getParameter("type");
		if(type!=null){
			condition.setOperationType(type);
			if(type.equals("02")){
				condition.setSupplyAgencyId(companyId);
			}else{
				condition.setBuyAgencyId(companyId);
			}
		}
		
		List<TOriginGoodsStock> stocks = goodsStockInfoService.getGoodsStockList(condition, start, limit);
		int total = goodsStockInfoService.countGoodsStock(condition);
		pageinfo.setTotalCount(total);
		model.addAttribute("stocks", stocks);
		model.addAttribute("pageinfo", pageinfo);
		model.addAttribute("type", type);
		return new ModelAndView("/stock/goodsList");
	}
	
	@RequestMapping(value = {"/goods/create"}, method = RequestMethod.GET)
	public ModelAndView createGoods(Model model,HttpServletRequest request) throws BusinessException{
		log.info("商品库存信息新增。。。。");
		String type = request.getParameter("type");
		model.addAttribute("type", type);
		model.addAttribute("nextAction", "/goods/create");	
		if(type.equals("01")){
			model.addAttribute("action","添加经销商入库信息");
		}else{
			model.addAttribute("action", "添加零售商入库信息");
		}
		return new ModelAndView("/stock/goodsEdit");
	}
	
	@RequestMapping(value = "/goods/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonObject saveGoodsStock(TOriginGoodsStock stock,RedirectAttributes redirectAttributes,HttpServletRequest request) throws BusinessException{
		JsonObject jo = new JsonObject(true,"保存成功");
		try{
			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
			String enterpriseName = (String) request.getSession().getAttribute("enterpriseName");
			String userLoginName = userInfo.getUser().getUserLoginName();
			stock.setCreateStaff(userLoginName);
			int companyId =(Integer)request.getSession().getAttribute("agencyId");
			String type = request.getParameter("type");
			if(type.equals("02")){
				stock.setSupplyAgencyId(companyId);
				stock.setSupplyAgencyName(enterpriseName);
			}else{
				stock.setBuyAgencyId(companyId);
				stock.setBuyAgencyName(enterpriseName);
			}
			String productionDate = request.getParameter("productionTime");
			if(productionDate!=null && !productionDate.equals("")){
				stock.setProductionDate(DateUtil.getTimestampFromString(productionDate, "yyyy-MM-dd"));
			}
			String operationDate = request.getParameter("operationTime");
			if(operationDate!=null && !operationDate.equals("")){
				stock.setOperationDate(DateUtil.getTimestampFromString(operationDate, "yyyy-MM-dd"));
			}
			stock.setOperationType(type);
			goodsStockInfoService.insertGoodsStock(stock);
		}catch(BusinessException e){
			e.printStackTrace();
			jo.setSuccess(false);
			jo.setMessage("保存异常,请重试！");
		}
		return jo;
	}
	
	@RequestMapping(value = {"/goods/update"}, method = RequestMethod.GET)
	public ModelAndView updateGoods(Model model,HttpServletRequest request) throws BusinessException{
		log.info("商品库存信息新增。。。。");
		String type = request.getParameter("type");
		String operationId = request.getParameter("operationId");
		TOriginGoodsStock stock = goodsStockInfoService.getGoodsStock(Integer.parseInt(operationId));
		model.addAttribute("type", type);
		model.addAttribute("stock", stock);
		if(type.equals("01")){
			model.addAttribute("action","修改经销商入库信息");
		}else if(type.equals("02")){
			model.addAttribute("action", "修改经销商出库信息");
		}else{
			model.addAttribute("action", "修改零售商入库信息");
		}
		model.addAttribute("nextAction", "/goods/update");	
		return new ModelAndView("/stock/goodsEdit");
	}
	
	@RequestMapping(value = "/goods/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonObject saveUpdateGoodsStock(TOriginGoodsStock stock,RedirectAttributes redirectAttributes,HttpServletRequest request) throws BusinessException{
		JsonObject jo = new JsonObject(true,"保存成功");
		try{
			
			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
			String enterpriseName = (String) request.getSession().getAttribute("enterpriseName");
			String userLoginName = userInfo.getUser().getUserLoginName();
			stock.setCreateStaff(userLoginName);
			int companyId =(Integer)request.getSession().getAttribute("agencyId");
			String type = request.getParameter("type");
			if(type.equals("02")){
				stock.setSupplyAgencyId(companyId);
				stock.setSupplyAgencyName(enterpriseName);
			}else{
				stock.setBuyAgencyId(companyId);
				stock.setBuyAgencyName(enterpriseName);
			}
			String productionDate = request.getParameter("productionTime");
			if(productionDate!=null && !productionDate.equals("")){
				stock.setProductionDate(DateUtil.getTimestampFromString(productionDate, "yyyy-MM-dd"));
			}
			String operationDate = request.getParameter("operationTime");
			if(operationDate!=null && !operationDate.equals("")){
				stock.setOperationDate(DateUtil.getTimestampFromString(operationDate, "yyyy-MM-dd"));
			}
			stock.setOperationType(type);			
			goodsStockInfoService.updateGoodsStock(stock);
		}catch(BusinessException e){
			e.printStackTrace();
			jo.setSuccess(false);
			jo.setMessage("保存异常,请重试！");
		}
		return jo;
	}
	
	@RequestMapping(value = "/goods/skulist", method = RequestMethod.POST)
	public ModelAndView selectSkuList(Model model,TOriginSkuAgencyRelation condition,HttpServletRequest request) throws BusinessException{
        int companyId =(Integer)request.getSession().getAttribute("agencyId");
        condition.setAgencyId(companyId);
		List<TOriginSkuAgencyRelation> skus = goodsStockInfoService.getSkuList(condition);
		model.addAttribute("skus", skus); 
		return new ModelAndView("/stock/agencySkuList");
	}
	
	 @RequestMapping(value = {"/goods/selectlist"}, method = RequestMethod.POST)
 	 public ModelAndView GoodsSelectList(Model model,TOriginGoodsStock condition,HttpServletRequest request)throws BusinessException{
 		log.info("从入库信息导入。。。");
 		int companyId =(Integer)request.getSession().getAttribute("agencyId");
		condition.setBuyAgencyId(companyId);
		String productionTime = request.getParameter("productionTime");
		if(productionTime!=null && !productionTime.equals("")){
			condition.setProductionDate(DateUtil.getTimestamp(productionTime));
		}
		condition.setStatus(SpsyConstants.STATUS_STOCK_NO_OUT);
		List<TOriginGoodsStock> stockins = goodsStockInfoService.getGoodsStockList(condition, -1, 0);
		model.addAttribute("stocks", stockins);			
 		return new ModelAndView("/stock/goodsSelectIn");
     }
	 
	 @RequestMapping(value = {"/goods/createoutin"}, method = RequestMethod.GET)
	 public ModelAndView createOutStock(Model model,HttpServletRequest request) throws BusinessException{
		 log.info("经销商出库信息新增。。。。");
		 model.addAttribute("nextAction", "/goods/createoutin");	
		 model.addAttribute("action", "导入出库信息");
		 return new ModelAndView("/stock/goodsOutInEdit");
	 }
	 
	 @RequestMapping(value = {"/goods/createoutin"}, method = RequestMethod.POST)
     @ResponseBody
     public JsonObject saveOutInfo(Model model,GoodStockInfo stockIn,HttpServletRequest request) throws BusinessException{
    	 log.info("生产企业出库信息添加。。。。。");
    	 JsonObject jo = new JsonObject(true,"导入成功");
    	 try{
    		 IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
    		 String enterpriseName = (String) request.getSession().getAttribute("enterpriseName");
    		 int companyId =(Integer)request.getSession().getAttribute("agencyId");
    	 	 String userLoginName = userInfo.getUser().getUserLoginName();
    	 	 int enterpriseId = userInfo.getUser().getCorpId();
    	 	 stockIn.setSupplyAgencyId(companyId);
    	 	 stockIn.setSupplyAgencyName(enterpriseName);
    	 	 stockIn.setCreateStaff(userLoginName);
    	 	goodsStockInfoService.insertGoodsStockFromIn(stockIn,enterpriseId);
    	 }catch(BusinessException e){
    		 e.printStackTrace();
    		 jo.setSuccess(false);
    		 jo.setMessage("导入失败!");
    	 }
    	 return jo;
     }
	 
	 @RequestMapping(value = {"/goods/updateoutin"}, method = RequestMethod.GET)
	 public ModelAndView updateoutin(Model model,HttpServletRequest request) throws BusinessException{
		 log.info("经销商出库信息修改。。。。");
		 String operationId = request.getParameter("operationId");
		 TOriginGoodsStock stock = goodsStockInfoService.getGoodsStock(Integer.parseInt(operationId));
		 model.addAttribute("stock", stock);
		 model.addAttribute("nextAction", "/goods/updateoutin");	
		 model.addAttribute("action", "修改出库信息");
		 return new ModelAndView("/stock/goodsOutInEdit");
	 }
	 
	 @RequestMapping(value = {"/goods/updateoutin"}, method = RequestMethod.POST)
     @ResponseBody
     public JsonObject updateOutInfo(Model model,GoodStockInfo stockIn,HttpServletRequest request) throws BusinessException{
    	 log.info("生产企业出库信息添加。。。。。");
    	 JsonObject jo = new JsonObject(true,"修改成功");
    	 try{
    		 IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
    		 String enterpriseName = (String) request.getSession().getAttribute("enterpriseName");
    		 int companyId =(Integer)request.getSession().getAttribute("agencyId");
    	 	 String userLoginName = userInfo.getUser().getUserLoginName();
    	 	 int enterpriseId = userInfo.getUser().getCorpId();
    	 	 stockIn.setSupplyAgencyId(companyId);
    	 	 stockIn.setSupplyAgencyName(enterpriseName);
    	 	 stockIn.setUpdateStaff(userLoginName);
    	 	goodsStockInfoService.updateGoodStockOut(stockIn,enterpriseId);
    	 }catch(BusinessException e){
    		 e.printStackTrace();
    		 jo.setSuccess(false);
    		 jo.setMessage("修改失败!");
    	 }
    	 return jo;
     }
}
