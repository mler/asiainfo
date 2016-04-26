package com.bdx.rainbow.spsy.controller;

import javax.servlet.http.HttpServletRequest;

import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginProducerStockOutDAO;
import com.bdx.rainbow.spsy.dal.ibatis.model.SelfTrace;
import com.bdx.rainbow.spsy.service.ITraceService;

import org.json.simple.JSONObject;
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

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.service.IEnterpriseDubboService;
import com.bdx.rainbow.basic.dubbo.service.ISkuDubboService;
import com.bdx.rainbow.common.SysContants;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.spsy.bean.EnterpriseInfo;
import com.bdx.rainbow.spsy.common.ConsumerUtil;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginInspectionInfoDAO;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.util.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller("traceController")
@RequestMapping("/trace")
public class TraceController {
	
	private final static Logger log=LoggerFactory.getLogger(TraceController.class);
	
//	private ISkuDubboService skuDubboService = (ISkuDubboService) ConsumerUtil.getContextBasic().getBean("skuDubboService");
	

    @Autowired
    private ITraceService traceService;
    
    private IEnterpriseDubboService enterpriseDubboService;
	
	@RequestMapping(value = {"/spzs"}, method = RequestMethod.GET)
	public ModelAndView goodTrace(Model model,HttpServletRequest request) throws Exception{
		log.info("商品追溯。。。。");
		String barcode = request.getParameter("barcode");
		IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
//        int companyId = userInfo.getUser().getCorpId();
		model.addAttribute("spzsSkuBarCode", request.getParameter("spzsSkuBarCode"));
//		DubboSku sku = skuDubboService.getDubboSku(barcode, companyId, 0, -1);
		return new ModelAndView("/trace/spzs");
	}
	
	@RequestMapping(value = {"/spjg"}, method = RequestMethod.GET)
	public ModelAndView goodInspectInit(Model model,HttpServletRequest request) throws Exception{
		log.info("商品监管init。。。。");
		List<String> months = DateUtil.getLastMonthList("yyyyMM", 3);
		model.addAttribute("lastMonths", months);
		return new ModelAndView("/trace/spjg");
	}
	
	@RequestMapping(value = {"/spjgList"})
	public ModelAndView goodInspect(Model model,PageInfo pageinfo,HttpServletRequest request) throws Exception{
		log.info("商品监管list。。。。");
        String pagestart = request.getParameter("pageStart");
        if(pagestart!=null){
            pageinfo.setPageStart(Integer.parseInt(pagestart));
        }
        int limit = pageinfo.getPageCount();
        int start = pageinfo.getPageStart();
		String barcode = request.getParameter("skuName");
        SelfTrace condition = new SelfTrace();
        condition.setQueryCondition(barcode);
		IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
        int companyId = userInfo.getUser().getCorpId();
//		DubboSku sku = skuDubboService.getDubboSku(barcode, companyId, 0, -1);
        try {
        	 Map<String,Object> goodsMap = traceService.getSpjgTraces(condition,start,limit);
             int totalSpjgTrace = traceService.totalSpjgTrace(condition);
             List<SelfTrace> goods = (List<SelfTrace>) goodsMap.get("list");
             int total = (Integer) goodsMap.get("total");
             pageinfo.setTotalCount(total);
             model.addAttribute("goods", goods);
             model.addAttribute("totalSpjgTrace", totalSpjgTrace);
             model.addAttribute("pageinfo", pageinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
       
		return new ModelAndView("/trace/spjgList");
	}
	
	@RequestMapping(value = {"/getSpjgChart"}, method = RequestMethod.POST)
	@ResponseBody
	public JsonObject getSpjgChartData(Model model,@RequestParam("yyyyMM") String yyyyMM,@RequestParam("skuId") Integer skuId,HttpServletRequest request) throws Exception{
		log.info("获取图表信息。。。");
		JsonObject jo = new JsonObject(true, "OK");
		try{
			Map<String, Object> chartMap = traceService.getSpjgChartData(skuId, yyyyMM);
			String msg = JSONObject.toJSONString(chartMap);
			System.out.println( "data is :" +msg);
			jo.setData(msg);
		}catch(Exception e){
			e.printStackTrace();
			jo.setSuccess(false);
			jo.setMessage(e.getMessage());
		}
		return jo;
	}
	
	@RequestMapping(value = {"/qyjg"}, method = RequestMethod.GET)
	public ModelAndView corInspectInit(Model model,HttpServletRequest request) throws Exception{
		log.info("企业监管。。。。");
		List<String> months = DateUtil.getLastMonthList("yyyyMM", 3);
		model.addAttribute("lastMonths", months);
		return new ModelAndView("/trace/qyjg");
	}
	
	@RequestMapping(value = {"/qyjgList"})
	public ModelAndView corInspect(Model model,PageInfo pageinfo,HttpServletRequest request) throws Exception{
		log.info("企业监管list。。。。");
        String pagestart = request.getParameter("pageStart");
        if(pagestart!=null){
            pageinfo.setPageStart(Integer.parseInt(pagestart));
        }
        int limit = pageinfo.getPageCount();
        int start = pageinfo.getPageStart();
		String corpName = request.getParameter("corpName");
        SelfTrace condition = new SelfTrace();
        condition.setInName(corpName);
		Map<String, Object> result = traceService.getQyjgTraces(condition, start, limit);
		int totalQyjgTrace = traceService.totalQyjgTrace(condition);
		List<SelfTrace> list = (List<SelfTrace>) result.get("list");
		int total = (Integer) result.get("total");
		List<Integer> ids = new ArrayList<Integer>();
				
        pageinfo.setTotalCount(total);
        model.addAttribute("corps", list);
        model.addAttribute("totalQyjgTrace", totalQyjgTrace);
        model.addAttribute("pageinfo", pageinfo);
		return new ModelAndView("/trace/qyjgList");
	}
	
	@RequestMapping(value = {"/getQyjgChart"}, method = RequestMethod.POST)
	@ResponseBody
	public JsonObject getQyjgChartData(Model model,@RequestParam("yyyyMM") String yyyyMM,@RequestParam("enterpriseId") Integer enterpriseId,HttpServletRequest request) throws Exception{
		log.info("获取企业图表信息。。。");
		JsonObject jo = new JsonObject(true, "OK");
		try{
			Map<String, Object> chartMap = traceService.getQyjgChartData(enterpriseId, yyyyMM);
			String msg = JSONObject.toJSONString(chartMap);
			System.out.println( "data is :" +msg);
			jo.setData(msg);
		}catch(Exception e){
			e.printStackTrace();
			jo.setSuccess(false);
			jo.setMessage(e.getMessage());
		}
		return jo;
	}
	
	@RequestMapping(value = {"/spzsSales"})
	public ModelAndView spzsSaleList(Model model,PageInfo pageinfo,HttpServletRequest request) throws Exception{
		log.info("销售信息list。。。。");
        String pagestart = request.getParameter("pageStart");
        if(pagestart!=null){
            pageinfo.setPageStart(Integer.parseInt(pagestart));
        }
        int limit = pageinfo.getPageCount();
        int start = pageinfo.getPageStart();
        SelfTrace condition = new SelfTrace();
        condition.setSkuBarCode(request.getParameter("spzsSkuBarCode"));
        condition.setSkuBatch(request.getParameter("spzsSkuBatch"));
        condition.setInName(request.getParameter("spzsInName"));
        condition.setSkuIdCode(request.getParameter("spzsSkuIdCode"));
        
        List<SelfTrace> list = traceService.getSpzsSale(condition, start, limit);
//		int total = (Integer) result.get("total");
				
        model.addAttribute("sales", list);
        model.addAttribute("pageinfo", pageinfo);
		return new ModelAndView("/trace/spzsSales");
	}
	
	@RequestMapping(value = {"/spzsPasses"})
	public ModelAndView spzsPassList(Model model,PageInfo pageinfo,HttpServletRequest request) throws Exception{
		log.info("流通信息list。。。。");
        String pagestart = request.getParameter("pageStart");
        if(pagestart!=null){
            pageinfo.setPageStart(Integer.parseInt(pagestart));
        }
        int limit = pageinfo.getPageCount();
        int start = pageinfo.getPageStart();
        SelfTrace condition = new SelfTrace();
        condition.setSkuBarCode(request.getParameter("spzsSkuBarCode"));
        condition.setSkuBatch(request.getParameter("spzsSkuBatch"));
        condition.setInName(request.getParameter("spzsInName"));
        condition.setSkuIdCode(request.getParameter("spzsSkuIdCode"));
        
        List<SelfTrace> list = traceService.getSpzsPass(condition, start, limit);
				
        model.addAttribute("passes", list);
        model.addAttribute("pageinfo", pageinfo);
		return new ModelAndView("/trace/spzsPasses");
	}
	
	@RequestMapping(value = {"/spzsProduces"})
	public ModelAndView spzsProduceList(Model model,PageInfo pageinfo,HttpServletRequest request) throws Exception{
		log.info("生产信息list。。。。");
        String pagestart = request.getParameter("pageStart");
        if(pagestart!=null){
            pageinfo.setPageStart(Integer.parseInt(pagestart));
        }
        int limit = pageinfo.getPageCount();
        int start = pageinfo.getPageStart();
        SelfTrace condition = new SelfTrace();
        condition.setSkuBarCode(request.getParameter("spzsSkuBarCode"));
        condition.setSkuBatch(request.getParameter("spzsSkuBatch"));
        condition.setInName(request.getParameter("spzsInName"));
        condition.setSkuIdCode(request.getParameter("spzsSkuIdCode"));
        
        List<SelfTrace> list = traceService.getSpzsProduce(condition, start, limit);
//		int total = (Integer) result.get("total");
				
        model.addAttribute("produces", list);
        model.addAttribute("pageinfo", pageinfo);
		return new ModelAndView("/trace/spzsProduces");
	}
	
}
