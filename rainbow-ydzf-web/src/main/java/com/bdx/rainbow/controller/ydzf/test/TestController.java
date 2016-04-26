package com.bdx.rainbow.controller.ydzf.test;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.controller.core.BaseController;
import com.bdx.rainbow.core.common.YDZFVfsSetting;
import com.bdx.rainbow.dto.ydzf.form.YdzfInspectCaseListQueryInfo;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseEvidenceResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseListResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseTempletAndContentVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanListNumResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidence;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlan;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseAuditService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseExecutePunishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseFinishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseReadyPunishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseWitnessesService;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorPlanService;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorTaskService;
import com.bdx.rainbow.service.ydzf.system.IYDZFLoginService;
import com.bdx.rainbow.service.ydzf.system.IYDZFTestService;
import com.bdx.rainbow.service.ydzf.templet.IYDZFInspectCaseTempletContentService;

@Controller
public class TestController extends BaseController{
	private static final Logger log = LoggerFactory
			.getLogger(TestController.class);
	
	@Autowired
	private IYDZFTestService ydzfTestService;
	@Autowired
	private IYDZFMonitorPlanService ydzfMonitorPlanService;
	@Autowired
	private IYDZFMonitorTaskService ydzfMonitorTaskService;
	@Autowired
	private IYDZFInspectCaseService ydzfInspectCaseService;
	@Autowired
	protected IYDZFInspectCaseEvidenceService ydzfInspectCaseEvidenceService;
	@Autowired
	protected IYDZFInspectCaseReadyPunishService ydzfInspectCaseReadyPunishService;
	@Autowired
	protected IYDZFInspectCaseExecutePunishService ydzfInspectCaseExecutePunishService;
	@Autowired
	protected IYDZFInspectCaseAuditService ydzfInspectCaseAuditService;
	@Autowired
	protected IYDZFInspectCaseWitnessesService ydzfInspectCaseWitnessesService;
	@Autowired
	protected IYDZFInspectCaseFinishService ydzfInspectCaseFinishService;
	@Autowired
	protected IYDZFInspectCaseTempletContentService ydzfInspectCaseTempletContentService;
	
	
	@Autowired
	private YDZFVfsSetting ydzfVfsSetting;
	
//	@Autowired
//	private IDubUserService dubUserService;
	@Autowired
	private IYDZFLoginService ydzfLoginService;
//	@Autowired
//	private IDubboService dubboService;
	
	
	
	

	@RequestMapping(value = "/hello2")
	@ResponseBody
	public String hello2(Model model) throws Exception{
		log.info(ydzfTestService.toString());
		log.info(ydzfMonitorPlanService.toString());
		List<YdzfMonitorPlanListNumResultVO> numList=ydzfMonitorPlanService.queryPlanListNum(0,null,null);
		return numList.toString();

	}

	@RequestMapping(value = "/test/queryMonitorPlanList")
	@ResponseBody
	public JsonObject login(Model model) throws Exception {
		try {
			TYdzfMonitorPlan plan= new TYdzfMonitorPlan();
			return new JsonObject(ydzfMonitorPlanService.queryMonitorPlanList( plan,0,10));
		} catch (BusinessException e) {
			return new JsonObject(false, e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			log.error("login登录异常：", e);
			return new JsonObject(false, "系统繁忙");
		}

	}
	
	@RequestMapping(value = "/test/queryMonitorPlanListInfo")
	@ResponseBody
	public JsonObject queryMonitorPlanListInfo(@RequestParam("checkPlanType") String checkPlanType,@RequestParam("monitorPlanId") int monitorPlanId,Model model) throws Exception {
		try {
			return new JsonObject(ydzfMonitorPlanService.queryMonitorPlanListInfo(monitorPlanId, checkPlanType));
		} catch (BusinessException e) {
			return new JsonObject(false, e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			log.error("login登录异常：", e);
			return new JsonObject(false, "系统繁忙");
		}

	}
	@RequestMapping(value = "/test/queryMonitorPlanTaskInfo")
	@ResponseBody
	public JsonObject queryMonitorPlanTaskInfo(@RequestParam("monitorTaskId") int monitorTaskId,Model model) throws Exception {
		try {
			System.out.println(ydzfVfsSetting);
			return new JsonObject(ydzfMonitorTaskService.queryMonitorPlanTaskInfo(monitorTaskId));
		} catch (BusinessException e) {
			return new JsonObject(false, e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			log.error("login登录异常：", e);
			return new JsonObject(false, "系统繁忙");
		}

	}
	
	
	//查询稽查案件列表
	@RequestMapping(value = "/test/queryInspectCaseList")
	@ResponseBody
	public JsonObject queryInspectCaseList(YdzfInspectCaseListQueryInfo ydzfInspectCaseListQueryInfo,Model model) throws Exception {
		try {
			List<YdzfInspectCaseListResultVO> resultvoList=ydzfInspectCaseService.queryInspectCaseList(ydzfInspectCaseListQueryInfo, 0,10);
	    	int total=ydzfInspectCaseService.countInspectCaseList(ydzfInspectCaseListQueryInfo);
	    	System.out.println(total);
			return new JsonObject(resultvoList);
		} catch (BusinessException e) {
			return new JsonObject(false, e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			log.error("login登录异常：", e);
			return new JsonObject(false, "系统繁忙");
		}

	}
	
	//查询稽查案件详情
	@RequestMapping(value = "/test/queryInspectCaseInfoAndRel")
	@ResponseBody
	public JsonObject queryInspectCaseInfoAndRel(Integer insepctCaseId,Model model) throws Exception {
		try {
			YdzfInspectCaseListResultVO vo=ydzfInspectCaseService.queryInspectCaseInfoAndRel(insepctCaseId);
			return new JsonObject(vo);
		} catch (BusinessException e) {
			return new JsonObject(false, e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			log.error("login登录异常：", e);
			return new JsonObject(false, "系统繁忙");
		}

	}
	
	//
	@RequestMapping(value = "/test/ydzf0021")
	@ResponseBody
	public JsonObject ydzf0021(Integer insepctCaseId,Model model) throws Exception {
		try {
			TYdzfInspectCaseEvidence ydzfInspectCaseEvidence= new TYdzfInspectCaseEvidence();
			ydzfInspectCaseEvidence.setInspectCaseId(insepctCaseId);
			ydzfInspectCaseEvidence.setInspectCaseTempletId(1);
			ydzfInspectCaseEvidence.setInspectCaseTempletTotal(5);
			ydzfInspectCaseEvidence.setInspectCaseTempletValues("{\"temp_info_json\": [{\"id\": \"1\", \"value\": \"1\", \"remark\": \"1\"}, {\"id\": \"2\", \"value\": \"2\", \"remark\": \"2\"} ] }");
			ydzfInspectCaseEvidence.setCreateDate(DateUtil.getCurrent());
			ydzfInspectCaseEvidence.setCreateUserid(30);
			ydzfInspectCaseEvidenceService.addInspectCaseEvidence(ydzfInspectCaseEvidence);
			return new JsonObject(ydzfInspectCaseEvidence);
		} catch (BusinessException e) {
			return new JsonObject(false, e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			log.error("login登录异常：", e);
			return new JsonObject(false, "系统繁忙");
		}

	}
	//
	@RequestMapping(value = "/test/ydzf0038")
	@ResponseBody
	public JsonObject ydzf0038(Integer insepctCaseId,Model model) throws Exception {
		try {
	    	YdzfInspectCaseTempletAndContentVO vo=ydzfInspectCaseTempletContentService.queryInspectCaseTempleteAndContentInfoByCaseId(insepctCaseId);
			return new JsonObject(vo);
		} catch (BusinessException e) {
			return new JsonObject(false, e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			log.error("login登录异常：", e);
			return new JsonObject(false, "系统繁忙");
		}

	}
	//
	@RequestMapping(value = "/test/ydzf0040")
	@ResponseBody
	public JsonObject ydzf0040(Integer evidenceId,Model model) throws Exception {
		try {
			YdzfInspectCaseEvidenceResultVO vo=ydzfInspectCaseEvidenceService.queryInspectCaseEvidenceInfo(evidenceId);
			return new JsonObject(vo);
		} catch (BusinessException e) {
			return new JsonObject(false, e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			log.error("login登录异常：", e);
			return new JsonObject(false, "系统繁忙");
		}

	}
	
	
	

	

	
	
	
	
	
	
//	@RequestMapping(value = "/test/dubbo")
//	@ResponseBody
//	public JsonObject dubbo(@RequestParam("loginName") String loginName,
//			@RequestParam("pwd") String pwd, Model model) throws Exception {
//		try {
//			dubboService.dubboTest("111");
//			dubboService.dubboTest("222");
//			return null;
//		} catch (BusinessException e) {
//			System.out.println("11111111111111111");
//			return new JsonObject(false, e.getErrorCode(), e.getErrorMsg());
//		} catch (Exception e) {
//			log.error("login登录异常：", e);
//			return new JsonObject(false, "系统繁忙");
//		}
//
//	}

	
	
	


}
