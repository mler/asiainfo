package com.bdx.rainbow.controller.ydzf.monitor;


import java.sql.Timestamp;
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

import com.bdx.rainbow.base.annotation.Security;
import com.bdx.rainbow.base.request.SessionUtils;
import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.controller.core.BaseController;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.dto.ydzf.form.YdzfMonitorPlanAddForm;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanListInfoResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanListResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanRelEnterpriseVO;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlan;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorPlanService;
import com.bdx.rainbow.urs.dubbo.IDubDeptService;
import com.bdx.rainbow.urs.entity.DeptModel;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.urs.entity.SysDeptTree;
import com.bdx.rainbow.urs.entity.SysUserInfo;

@Controller
@RequestMapping("/monitor")
public class YDZFMonitorController extends BaseController {
    private static final Logger log = LoggerFactory
            .getLogger(YDZFMonitorController.class);
    @Autowired
    private IYDZFMonitorPlanService ydzfMonitorPlanService;

    @Autowired
    private IDubDeptService deptService;
    @Autowired
    private IYDZFCommonService commonService;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @Security()
    public ModelAndView monitorInit(Model model, @RequestParam("checkPlanType") String checkPlanType) throws Exception {
        SysUserInfo userInfo= (SysUserInfo)SessionUtils.getUserSession();
        model.addAttribute("depts",deptService.getDeptByLogon(userInfo));
        return new ModelAndView("/monitor/init" + checkPlanType);
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @Security()
    public ModelAndView monitorList(Model model, PageInfo pageinfo, TYdzfMonitorPlan ydzfMonitorPlan, @RequestParam("checkPlanType") String checkPlanType) throws Exception {
        ydzfMonitorPlan.setCheckPlanType(checkPlanType);
        //TODO：部门怎么划分????
        IUserInfo userInfo = SessionUtils.getUserSession();
        Integer deptId=userInfo.getUser().getDeptId();
        ydzfMonitorPlan.setCheckPlanCreatDepId(deptId);
        List<YdzfMonitorPlanListResultVO> lists = ydzfMonitorPlanService.queryMonitorPlanList(ydzfMonitorPlan, pageinfo.getPageStart(), pageinfo.getPageCount());
        int count = ydzfMonitorPlanService.countMonitorPlanList(ydzfMonitorPlan);
        pageinfo.setTotalCount(count);
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("rows", lists);
        return new ModelAndView("/monitor/list" + checkPlanType);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createForm(Model model, @RequestParam("checkPlanType") String checkPlanType) {
        String planCode = YDZFConstants.MONITOR.CHECKPLANTYPE.getByCode(checkPlanType).getAbb() + DateUtil.getDateString("yyyyMMddHHmmss");
        IUserInfo userInfo = SessionUtils.getUserSession();
        String username = userInfo.getUserName();
        Integer userId = userInfo.getUserId();
        String time = DateUtil.getTime("");

        try {
            List<SysDeptTree> depts = deptService.getDeptByLogon((SysUserInfo) userInfo);
            DeptModel dept = deptService.getDeptAndExtById(userInfo.getUser().getDeptId());
            model.addAttribute("depts", depts);
            model.addAttribute("dept", dept);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("time", time);
        model.addAttribute("username", username);
        model.addAttribute("userId", userId);
        model.addAttribute("action", "新增日常巡检任务");
        model.addAttribute("nextAction", "create");
        model.addAttribute("planCode", planCode);
        return new ModelAndView("monitor/edit" + checkPlanType);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    @Security()
    public JsonObject monitorAdd(TYdzfMonitorPlan ydzfMonitorPlan, HttpServletRequest request) throws Exception {
        String checkPlanType = request.getParameter("checkPlanType");
        if ((checkPlanType != null)) {
            ydzfMonitorPlan.setCheckPlanType(checkPlanType);
        }

        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        IUserInfo userInfo = SessionUtils.getUserSession();

        JsonObject jo = new JsonObject(true, "新增成功！");
        try {
            Timestamp beginTime = DateUtil.getTimestamp(beginDate);
            Timestamp endTime = DateUtil.getTimestamp(endDate);

            if (beginTime != null && endTime != null) {
                ydzfMonitorPlan.setCheckPlanBeginDate(beginTime);
                ydzfMonitorPlan.setCheckPlanEndDate(endTime);
            }


//			ydzfMonitorPlan.setCheckPlanName("日常见愁任务名称");
//			ydzfMonitorPlan.setCheckPlanContent("任务内容内容！！！");
            ydzfMonitorPlan.setMonitorTempletId(1);

            ydzfMonitorPlan.setCheckPlanStatus(YDZFConstants.MONITOR.PLAN_STATUS_EXECUTE_WAIT);
            ydzfMonitorPlan.setIsDel(YDZFConstants.SYSTEM.IS_NOT_DEL);
            ydzfMonitorPlan.setCreateUserid(userInfo.getUserId());
            ydzfMonitorPlan.setCreateDate(DateUtil.getCurrent());


            YdzfMonitorPlanAddForm planForm = new YdzfMonitorPlanAddForm();
            planForm.setYdzfMonitorPlan(ydzfMonitorPlan);
            planForm.setEnterpriseIdList(new ArrayList<Integer>() {{
                add(36);
            }});
            ydzfMonitorPlanService.addMonitorPlan(planForm);

        } catch (Exception e) {
            log.error(e.getMessage(), e.fillInStackTrace());
            jo.setMessage("系统异常");
            jo.setSuccess(false);
        }
        return jo;

    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateForm(Model model, @RequestParam("checkPlanType") String checkPlanType, @RequestParam("monitorPlanId") Integer monitorPlanId) throws Exception {
        YdzfMonitorPlanListInfoResultVO obj = ydzfMonitorPlanService.queryMonitorPlanListInfo(monitorPlanId, checkPlanType);
        IUserInfo userInfo = SessionUtils.getUserSession();
        List<SysDeptTree> depts = deptService.getDeptByLogon((SysUserInfo) userInfo);
        model.addAttribute("depts", depts);
        model.addAttribute("obj", obj);
        model.addAttribute("action", "更新部门");
        model.addAttribute("nextAction", "update");


        return new ModelAndView("monitor/edit" + checkPlanType);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody

    public JsonObject monitorUpdate(@RequestParam("checkPlanType") String checkPlanType, TYdzfMonitorPlan ydzfMonitorPlan, HttpServletRequest request) throws Exception {
        JsonObject jo = new JsonObject(true, "更新成功！");
        try {
            SysUserInfo userInfo = (SysUserInfo) SessionUtils.getUserSession();
            String beginDate = request.getParameter("beginDate");
            String endDate = request.getParameter("endDate");
            String createDate = request.getParameter("creatDate");
            Timestamp beginTime = DateUtil.getTimestamp(beginDate);
            Timestamp endTime = DateUtil.getTimestamp(endDate);


            if (beginTime != null && endTime != null) {
                ydzfMonitorPlan.setCheckPlanBeginDate(beginTime);
                ydzfMonitorPlan.setCheckPlanEndDate(endTime);

            }
            ydzfMonitorPlan.setUpdateUserid(userInfo.getUserId());
            ydzfMonitorPlan.setUpdateDate(DateUtil.getCurrent());
            YdzfMonitorPlanAddForm planForm = new YdzfMonitorPlanAddForm();
            planForm.setYdzfMonitorPlan(ydzfMonitorPlan);
            ydzfMonitorPlanService.updateMonitorPlan(planForm);
        } catch (Exception e) {
            log.error(e.getMessage(), e.fillInStackTrace());
            jo.setMessage("系统繁忙！");
            jo.setSuccess(false);
        }

        return jo;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Security()
    @ResponseBody
    public JsonObject delMonitor(@RequestParam("moplanIds") List<Integer> monitorPlanIds) {
        JsonObject jo = new JsonObject(true, "删除成功！");
        SysUserInfo userInfo = (SysUserInfo) SessionUtils.getUserSession();
        try {
        	   ydzfMonitorPlanService.delMonitor(userInfo, monitorPlanIds);
        } catch (Exception e) {
            log.error(e.getMessage(), e.fillInStackTrace());
            jo.setMessage("系统繁忙！");
            jo.setSuccess(false);
        }
        return jo;
    }

    @RequestMapping(value = "/searchEnterprise", method = RequestMethod.POST)
    @Security()
    @ResponseBody
    public JsonObject searchEnterprise(HttpServletRequest request) {
        String type = request.getParameter("enterpriseBusinessType");
        String name = request.getParameter("enterpriseName");
        DubboEnterpriseInfo condition = new DubboEnterpriseInfo();
        condition.setBusinessType(type);
        condition.setEnterpriseName(name);
        JsonObject result = new JsonObject(true, "SUC");
        List<DubboEnterpriseInfo> list = commonService.queryEnterpriseInfos(condition);
        if (list != null && list.size() > 0) {
            String[] arr = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                arr[i] = list.get(i).getEnterpriseName() + ";" + list.get(i).getEnterpriseId();
            }
            result.setData(arr);
        }

        return result;
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    @Security()
    public ModelAndView show(Model model, @RequestParam("checkPlanType") String checkPlanType, @RequestParam("monitorPlanId") Integer monitorPlanId, PageInfo pageInfo) {
        try {
            YdzfMonitorPlanListInfoResultVO obj = ydzfMonitorPlanService.queryMonitorPlanListInfo(monitorPlanId, checkPlanType);
            List<YdzfMonitorPlanListResultVO> listResultVO = ydzfMonitorPlanService.queryMonitorPlanList(obj.getYdzfMonitorPlan(), pageInfo.getPageStart(), pageInfo.getPageCount());

            model.addAttribute("listResultVO", listResultVO.get(0));
            model.addAttribute("obj", obj);
//            List<YdzfEnterpriseInfoVO> corps = new ArrayList<YdzfEnterpriseInfoVO>();
           List<YdzfMonitorPlanRelEnterpriseVO> enterpriseVOs = obj.getEnterpriseAllVOList();
//            for (YdzfMonitorPlanListResultVO c : listResultVO) {
//                YdzfEnterpriseInfoVO corp = commonService.getEnterpriseInfoByIdDubbo(c.getMonitorPlanId());
//                corps.add(corp);
//            }


            model.addAttribute("corps", enterpriseVOs);
        } catch (Exception e) {
            log.error("稽查查看：", e);
        }
        return new ModelAndView("monitor/show");
    }
}
