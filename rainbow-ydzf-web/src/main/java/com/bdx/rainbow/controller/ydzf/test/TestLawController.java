package com.bdx.rainbow.controller.ydzf.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdx.rainbow.basic.dubbo.bean.DubboLaw;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.controller.core.BaseController;
import com.bdx.rainbow.core.common.YDZFVfsSetting;
import com.bdx.rainbow.service.ydzf.common.IYDZFLawService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseService;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorPlanService;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorTaskService;
import com.bdx.rainbow.service.ydzf.system.IYDZFLoginService;
import com.bdx.rainbow.service.ydzf.system.IYDZFTestService;

@Controller
public class TestLawController extends BaseController{
	private static final Logger log = LoggerFactory
			.getLogger(TestLawController.class);
	
	@Autowired
	private IYDZFTestService ydzfTestService;
	@Autowired
	private IYDZFMonitorPlanService ydzfMonitorPlanService;
	@Autowired
	private IYDZFMonitorTaskService ydzfMonitorTaskService;
	@Autowired
	private IYDZFInspectCaseService ydzfInspectCaseService;
	
	@Autowired
	private YDZFVfsSetting ydzfVfsSetting;
	
//	@Autowired
//	private IDubUserService dubUserService;
	@Autowired
	private IYDZFLoginService ydzfLoginService;
//	@Autowired
//	private IDubboService dubboService;
	@Autowired
	private IYDZFLawService ydzfLawService;
	
	
	@RequestMapping(value = "/test/getLawInfoDubbo")
	@ResponseBody
	public JsonObject getLawInfoDubbo(Integer lawId,Model model) throws Exception {
		try {
			
			return new JsonObject(ydzfLawService.getLawInfoDubbo(lawId));
		} catch (BusinessException e) {
			return new JsonObject(false, e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			log.error("login登录异常：", e);
			return new JsonObject(false, "系统繁忙");
		}

	}
	
	@RequestMapping(value = "/test/getTitlesByIdDubbo")
	@ResponseBody
	public JsonObject getTitlesByIdDubbo(Integer lawId,Model model) throws Exception {
		try {
			return new JsonObject(ydzfLawService.getTitlesByIdDubbo(lawId));
		} catch (BusinessException e) {
			return new JsonObject(false, e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			log.error("login登录异常：", e);
			return new JsonObject(false, "系统繁忙");
		}

	}
	
	@RequestMapping(value = "/test/getNodeListDubbo")
	@ResponseBody
	public JsonObject getNodeListDubbo(Integer parentid,Integer zIndex,Model model) throws Exception {
		try {
			DubboLaw condition= new DubboLaw();
			condition.setParentid(parentid);
			condition.setzIndex(zIndex);
			return new JsonObject(ydzfLawService.getNodeListDubbo(condition, null, null, 0, 10));
		} catch (BusinessException e) {
			return new JsonObject(false, e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			log.error("login登录异常：", e);
			return new JsonObject(false, "系统繁忙");
		}

	}
	

	

	
	
	
	
	
	


}
