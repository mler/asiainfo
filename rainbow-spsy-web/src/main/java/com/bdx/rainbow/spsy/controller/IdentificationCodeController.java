package com.bdx.rainbow.spsy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginIdentificationCode;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginIdentificationCodeDetail;
import com.bdx.rainbow.spsy.service.IIdentificationCodeService;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.util.JsonObject;

@Controller
@RequestMapping("code")
public class IdentificationCodeController {
	private final static Logger log=LoggerFactory.getLogger(IdentificationCodeController.class);

	@Autowired
	private IIdentificationCodeService identificationCodeService;
		
	@RequestMapping(value = {"/init"},method = RequestMethod.GET )
	public ModelAndView init(Model model) throws BusinessException{
		log.info("标识码信息查询。。。。。");
		return new ModelAndView("/code/codeInit");
	}
	
	@RequestMapping(value = {"/list"},method = RequestMethod.POST)
	public ModelAndView listmerchant(Model model,TOriginIdentificationCode condition,PageInfo pageinfo,HttpServletRequest request) throws BusinessException{
		log.info("查询列表。。。。");
		String pagestart = request.getParameter("pageStart");
		if(pagestart!=null){
          pageinfo.setPageStart(Integer.parseInt(pagestart));
		}
		int limit = pageinfo.getPageCount();
		int start = pageinfo.getPageStart();
		IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
        int companyId = userInfo.getUser().getCorpId();
        condition.setEnterpriseId(companyId);
		List<TOriginIdentificationCode> codes = identificationCodeService.getCodeList(condition, start, limit);
		int total = identificationCodeService.countCodes(condition);
		pageinfo.setTotalCount(total);
		model.addAttribute("codes", codes);
		model.addAttribute("pageinfo", pageinfo);
		return new ModelAndView("/code/codeList");
	}
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.GET)
	public ModelAndView createMerchantStock(Model model) throws BusinessException{
		log.info("标识码信息新增。。。。");
		model.addAttribute("nextAction", "/create");	
		return new ModelAndView("/code/codeEdit");
	}
	
	@RequestMapping(value = "/detaillist", method = RequestMethod.GET)
	public ModelAndView detaillist(Model model,TOriginIdentificationCode condition,HttpServletRequest request) throws BusinessException{
		IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
        int companyId = userInfo.getUser().getCorpId();
        condition.setEnterpriseId(companyId);
        List<TOriginIdentificationCodeDetail> codedetails = identificationCodeService.getCodeDetailList(condition);
        model.addAttribute("codedetails", codedetails);
        model.addAttribute("type", condition.getCodeType());
		return new ModelAndView("/code/codeDetailList");
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonObject saveMerchantStock(TOriginIdentificationCode code,RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		JsonObject jo = new JsonObject(true,"生成成功");
		try{
			IUserInfo userInfo = (IUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
			String userLoginName = userInfo.getUser().getUserLoginName();
			int userId = userInfo.getUser().getUserId();
			code.setCreateStaff(userLoginName);	
			String skuIdCodeType = request.getParameter("skuIdCodeType");
			int companyId = userInfo.getUser().getCorpId();
			code.setEnterpriseId(companyId);
			identificationCodeService.insertCode(code, skuIdCodeType,userId);
			jo.setData(code.getCodeType());
		}catch(BusinessException e){
			e.printStackTrace();
			jo.setSuccess(false);
			jo.setMessage("生成异常,请重试！");
		}
		return jo;
	}
	
	@RequestMapping(value = "/selectdetail", method = RequestMethod.GET)
	public ModelAndView selectDetail(Model model,@RequestParam(value = "buildId", required = false) Integer buildId) throws BusinessException{		
		String vfsId = identificationCodeService.getCodeNumFirst(buildId);
		model.addAttribute("vfsId", vfsId);
		return new ModelAndView("/code/codeSelect");
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downLoadReport(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String fileName = "code.xml";
		try{
			String buildId = request.getParameter("buildId");
			String codeType = request.getParameter("codeType");
			String xmlcode = identificationCodeService.createXml(Integer.parseInt(buildId), codeType);
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename="+ fileName);
			response.getWriter().write(xmlcode);			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
