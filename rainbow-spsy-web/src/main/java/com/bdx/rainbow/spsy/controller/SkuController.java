package com.bdx.rainbow.spsy.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bdx.rainbow.basic.dubbo.bean.DubboSku;
import com.bdx.rainbow.basic.dubbo.bean.DubboSkuFood;
import com.bdx.rainbow.common.BusinessException;
import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.SysContants;
import com.bdx.rainbow.spsy.common.SpsyConstants;
import com.bdx.rainbow.spsy.service.ISkuService;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.util.JsonObject;

@Controller
@RequestMapping("sku")
public class SkuController {
	private final static Logger log=LoggerFactory.getLogger(SkuController.class);
	
	@Autowired
	private ISkuService skuService;
	
	@RequestMapping(value = {"/init"}, method = RequestMethod.GET)
	public ModelAndView init(Model model) throws BusinessException{
		log.info("企业商品查询。。。。");
		return new ModelAndView("/sku/skuInit");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/list"}, method = RequestMethod.POST)
	public ModelAndView list(Model model,PageInfo pageinfo,HttpServletRequest request) throws Exception{
		log.info("商品信息列表。。。。");
		String pagestart = request.getParameter("pageStart");
		if(pagestart!=null){
          pageinfo.setPageStart(Integer.parseInt(pagestart));
		}
		int limit = pageinfo.getPageCount();
		int start = pageinfo.getPageStart();
		String keyword = request.getParameter("keyword");
		IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
        int companyId = userInfo.getUser().getCorpId();
		Map<String,Object> map = skuService.getSkuList(keyword,companyId, start, limit);
		List<DubboSku> skus = (List<DubboSku>) map.get("list");
		Integer total = (Integer) map.get("total");
		pageinfo.setTotalCount(total);
		model.addAttribute("skus", skus);
		model.addAttribute("pageinfo", pageinfo);
		return new ModelAndView("/sku/skuList");
	}
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	public ModelAndView create(Model model,HttpServletRequest request) throws Exception{
	   log.info("商品信息新增。。。。");
	   model.addAttribute("action", "产品信息添加");
	   model.addAttribute("nextAction", "create");	
	   return new ModelAndView("/sku/skuEdit");
	}
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.POST)
	@ResponseBody
	public JsonObject saveSku(Model model,DubboSkuFood sku,HttpServletRequest request) throws Exception{
	   log.info("商品信息保存。。。。");
	   JsonObject jo = new JsonObject(true,"保存成功");
	   try{
		    String timetype = request.getParameter("timetype");
			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
            String userLoginName = userInfo.getUser().getUserLoginName();
            int companyId = userInfo.getUser().getCorpId();
            sku.setLifeTime(sku.getLifeTime()+timetype);
            skuService.insertSkuFood(sku, companyId, userLoginName);
	   }catch (Exception e){
		   e.printStackTrace();
		   jo.setSuccess(false);
		   jo.setMessage("保存失败");
	   }
	   return jo;
	}
	
	@RequestMapping(value = {"/update"}, method = RequestMethod.GET)
	public ModelAndView update(Model model,HttpServletRequest request) throws Exception{
	   log.info("商品信息修改。。。。");
	   String skuId = request.getParameter("skuId");
	   DubboSkuFood sku = skuService.getSku(Integer.parseInt(skuId));
	   model.addAttribute("sku",sku);
	   model.addAttribute("action", "产品信息修改");
	   model.addAttribute("nextAction", "update");
	   return new ModelAndView("/sku/skuEdit");
	}
	
	@RequestMapping(value = {"/update"}, method = RequestMethod.POST)
	@ResponseBody
	public JsonObject updatesku(Model model,DubboSkuFood sku,HttpServletRequest request) throws Exception{
	   log.info("商品信息修改。。。。");
	   JsonObject jo = new JsonObject(true,"修改成功");
	   try{
		    String timetype = request.getParameter("timetype");
			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
            String userLoginName = userInfo.getUser().getUserLoginName();
            int companyId = userInfo.getUser().getCorpId();
            sku.setLifeTime(sku.getLifeTime()+timetype);
            skuService.insertSkuFood(sku, companyId, userLoginName);
	   }catch (Exception e){
		    e.printStackTrace();
		    jo.setSuccess(false);
		    jo.setMessage("修改失败");
	   }
	   return jo;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@RequestMapping(value = {"/selectlist"}, method = RequestMethod.GET)
	public ModelAndView selectlist(Model model,PageInfo pageinfo,HttpServletRequest request) throws Exception{
		log.info("商品信息列表。。。。");		
		String skuName = request.getParameter("skuName");
		String suname = "";
        if(!skuName.equals("")){
            suname = java.net.URLDecoder.decode(skuName);
        }
		IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
        int companyId = userInfo.getUser().getCorpId();
        DubboSku sku = new DubboSku();
        sku.setSkuName(suname);
		Map<String,Object> map = skuService.getAllSku(sku, companyId, -1, 0);
		List<DubboSku> skus = (List<DubboSku>) map.get("list");
		model.addAttribute("skus", skus);
		return new ModelAndView("/sku/skuSelectList");
	}
	
	@RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
	@ResponseBody
	public JsonObject changeStatus(Model model,@RequestParam("skuIds") List<Integer> skuIds,HttpServletRequest request) throws Exception{
		JsonObject jo = new JsonObject(true,"删除成功");
		try{
			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
            String userLoginName = userInfo.getUser().getUserLoginName();
            int companyId = userInfo.getUser().getCorpId();
            skuService.changeStatus(skuIds, SpsyConstants.STATUS_INACTIVE, companyId, userLoginName);
		}catch(Exception e){
			e.printStackTrace();
			jo.setSuccess(false);
			jo.setMessage("删除失败");
		}
		return jo;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/batchInsert" }, method = RequestMethod.POST)
	@ResponseBody
	public void skuInsert(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "file", required = true) MultipartFile file) throws Exception{
		String fileExtfile = FilenameUtils.getExtension(file.getOriginalFilename());
		IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
		String userLoginName = userInfo.getUser().getUserLoginName();
		int companyId = userInfo.getUser().getCorpId();
		Map<String,Object> map = readExel(fileExtfile,file);
		List<DubboSkuFood> skus = (List<DubboSkuFood>) map.get("skus");
		String errmsgs = (String) map.get("errmsgs");
		String errmsg = "";
		try{
			skuService.insertBatchSkuFood(skus, userLoginName, companyId);
		}catch(Exception e){
			e.printStackTrace();
			errmsg = e.getMessage();
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if(errmsg!=null && !errmsg.equals("")){
		    response.getWriter().write("<i class='iconfont icon-cuowu1'></i>"+errmsg);
		}else{
			response.getWriter().write(errmsgs);
		}
		out.flush();
		out.close();
	}
	
	public Map<String,Object> readExel(String fileExtfile,MultipartFile file) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>(); 
		Workbook wb = null;
		if("xlsx".equals(fileExtfile)){
			wb = new XSSFWorkbook(file.getInputStream());
		}else{
			wb = new HSSFWorkbook(file.getInputStream());
		}
		Sheet sheet = wb.getSheetAt(0);
		List<DubboSkuFood> skus = new ArrayList<DubboSkuFood>();
		DubboSkuFood food = new DubboSkuFood();
		String errmsgs = "";
		for (int i = 1; i <= sheet.getPhysicalNumberOfRows(); i++){
			Row row = sheet.getRow(i);			
			if (row == null) {
				continue;
			}
			food = new DubboSkuFood();
			Cell cell = row.getCell(0);
			if(cell!=null && cell.getStringCellValue()!=null && !cell.getStringCellValue().equals("")){
				food.setSkuName(cell.getStringCellValue());
			}else{
				errmsgs = errmsgs + "<i class='iconfont icon-cuowu1'></i>第"+i+"行产品名称不能为空</br>";
				continue;
			}
			
			cell = row.getCell(1);
			if(cell!=null && cell.getStringCellValue()!=null && !cell.getStringCellValue().equals("")){
				DubboSkuFood existfood = skuService.getSkuFood(cell.getStringCellValue());
				if(existfood!=null){
					errmsgs = errmsgs + "<i class='iconfont icon-cuowu1'></i>第"+i+"行产品已经存在</br>";
					continue;
				}else{
				   food.setSkuBarcode(cell.getStringCellValue());
				}
			}else{
				errmsgs = errmsgs + "<i class='iconfont icon-cuowu1'></i>第"+i+"行产品条码不能为空</br>";
				continue;
			}
			
			cell = row.getCell(2);
			if(cell!=null && cell.getStringCellValue()!=null && !cell.getStringCellValue().equals("")){
				food.setSpec(cell.getStringCellValue());
			}else{
				errmsgs = errmsgs + "<i class='iconfont icon-cuowu1'></i>第"+i+"行产品规格不能为空</br>";
				continue;
			}
			
			cell = row.getCell(3);
			if(cell!=null && cell.getStringCellValue()!=null && !cell.getStringCellValue().equals("")){
				food.setStandExeCode(cell.getStringCellValue());
			}else{
				errmsgs = errmsgs + "<i class='iconfont icon-cuowu1'></i>第"+i+"行产品标准号不能为空</br>";
				continue;
			}
			
			cell = row.getCell(4);
			if(cell!=null && cell.getStringCellValue()!=null && !cell.getStringCellValue().equals("")){
				food.setLifeTime(cell.getStringCellValue());
			}else{
				errmsgs = errmsgs + "<i class='iconfont icon-cuowu1'></i>第"+i+"行保质期不能为空</br>";
				continue;
			}
			
			cell = row.getCell(5);
			if(cell!=null && cell.getStringCellValue()!=null && !cell.getStringCellValue().equals("")){
				food.setFunctionComponent(cell.getStringCellValue());
			}
			
			cell = row.getCell(6);
			if(cell!=null && cell.getStringCellValue()!=null && !cell.getStringCellValue().equals("")){
				food.setMainMaterial(cell.getStringCellValue());
			}else{
				errmsgs = errmsgs + "<i class='iconfont icon-cuowu1'></i>第"+i+"行主要配料不能为空</br>";
				continue;
			}
			
			cell = row.getCell(7);
			if(cell!=null && cell.getStringCellValue()!=null && !cell.getStringCellValue().equals("")){
				food.setStorageMethod(cell.getStringCellValue());
			}else{
				errmsgs = errmsgs + "<i class='iconfont icon-cuowu1'></i>第"+i+"行贮存条件不能为空</br>";
				continue;
			}
			
			cell = row.getCell(8);
			if(cell!=null && cell.getStringCellValue()!=null && !cell.getStringCellValue().equals("")){
				food.setPatentNum(cell.getStringCellValue());
			}
			skus.add(food);			
		}
		map.put("skus", skus);
		map.put("errmsgs", errmsgs);
		return map;
	}
		
}
