package com.bdx.rainbow.controller.ydzf.complaint;

import com.bdx.rainbow.base.annotation.Security;
import com.bdx.rainbow.base.request.SessionUtils;
import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.controller.core.BaseController;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.dto.ydzf.form.YdzfInspectCaseAddForm;
import com.bdx.rainbow.entity.ydzf.TYdzfComplaint;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.common.IYDZFComplaintService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseService;
import com.bdx.rainbow.urs.entity.SysUser;
import com.bdx.rainbow.urs.entity.SysUserInfo;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by core on 16/4/12.
 */
@Controller
@RequestMapping("/complaint")
public class YDZFComplaintController extends BaseController{
    private static final Logger log = LoggerFactory
            .getLogger(YDZFComplaintController.class);
    @Autowired
    private IYDZFComplaintService complaintService;
    @Autowired
    private IYDZFCommonService commonService;

    @Autowired
     private IYDZFInspectCaseService caseService;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @Security()
    public ModelAndView init(Model model, HttpServletRequest request) {
        model.addAttribute("complaintInspectStatus",request.getParameter("complaintInspectStatus"));
        model.addAttribute("type",request.getParameter("type"));
        return new ModelAndView("complaint/init");
    }
    @RequestMapping("/list")
    @Security()
    public ModelAndView list(Model model, HttpServletRequest request,PageInfo pageinfo, TYdzfComplaint obj) {
        List<TYdzfComplaint> lists= complaintService.queryComplaintList(obj, pageinfo.getPageStart(), pageinfo.getPageCount());
        int count=complaintService.countComplaintList(obj);
        pageinfo.setTotalCount(count);
        model.addAttribute("pageinfo",pageinfo);
        model.addAttribute("rows",lists);
        model.addAttribute("type",request.getParameter("type"));
        return new ModelAndView("complaint/list");
    }
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @Security()
    public ModelAndView createForm(Model model) {

        model.addAttribute("action", "举报投诉录入");
        model.addAttribute("nextAction", "create");
        return new ModelAndView("complaint/edit");
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Security()
    @ResponseBody
    public JsonObject create(TYdzfComplaint obj, HttpServletRequest request) {
        JsonObject result=new JsonObject(true, "举报投诉录入录入成功");
        SysUserInfo userInfo= (SysUserInfo)SessionUtils.getUserSession();
        obj.setCreateUserid(userInfo.getUserId());
        obj.setComplaintCreatUserId(userInfo.getUserId());
        obj.setComplaintCreatDepId(userInfo.getUser().getDeptId());
        try {
            complaintService.insertComplaint(obj);
        } catch (Exception e) {
            log.error("举报投诉录入异常：", e);
            result.setSuccess(false);
            result.setMessage("系统繁忙");
        }
        return result;
    }
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @Security()
    public ModelAndView updateForm(Model model,@RequestParam("complaintId") Integer complaintId) {
        model.addAttribute("obj",complaintService.queryById(complaintId));
        model.addAttribute("action", "举报投诉修改");
        model.addAttribute("nextAction", "update");
        return new ModelAndView("complaint/edit");
    }
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    @Security()
    public ModelAndView showForm(Model model,@RequestParam("complaintId") Integer complaintId) {
        model.addAttribute("obj",complaintService.queryById(complaintId));
        model.addAttribute("action", "举报投诉修改");
        model.addAttribute("nextAction", "show");
        return new ModelAndView("complaint/edit");
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Security()
    @ResponseBody
    public JsonObject update(TYdzfComplaint obj, HttpServletRequest request) {
        JsonObject result=new JsonObject(true, "举报投诉修改成功");
        SysUserInfo userInfo= (SysUserInfo)SessionUtils.getUserSession();
        obj.setUpdateUserid(userInfo.getUserId());
        try {
            complaintService.updateCompalit(obj);
        } catch (Exception e) {
            log.error("举报投诉修改：", e);
            result.setSuccess(false);
            result.setMessage("系统繁忙");
        }
        return result;
    }
    @RequestMapping(value = "/accreditation", method = RequestMethod.GET)
    @Security()
    public ModelAndView accreditationForm(Model model,@RequestParam("complaintId") Integer complaintId) {
        SysUserInfo userInfo= (SysUserInfo)SessionUtils.getUserSession();
        model.addAttribute("depts",complaintService.getDepts(userInfo.getUser()));
        model.addAttribute("obj",complaintService.queryById(complaintId));
        model.addAttribute("action", "举报投诉立案");
        model.addAttribute("nextAction", "accreditation");
        return new ModelAndView("complaint/accreditation");
    }
    @RequestMapping(value = "/accreditation", method = RequestMethod.POST)
    @Security()
    @ResponseBody
    public JsonObject accreditation(TYdzfComplaint obj,TYdzfInspectCase inspectCase, HttpServletRequest request) {
        JsonObject result=new JsonObject(true, "举报投诉立案成功");
        SysUserInfo userInfo= (SysUserInfo)SessionUtils.getUserSession();
        obj.setUpdateUserid(userInfo.getUserId());
        obj.setComplaintInspectAuditUserid(userInfo.getUserId());
        obj.setComplaintInspectAuditDate(DateUtil.getCurrent());
        try {

            if (obj.getComplaintInspectStatus()==1){
                obj.setComplaintStatus(3);
                complaintService.updateCompalit(obj);
                obj=complaintService.queryById(obj.getComplaintId());
                YdzfInspectCaseAddForm addForm=new YdzfInspectCaseAddForm();
                inspectCase.setInspectCaseSourceType(1);
                inspectCase.setInspectCaseSourceId(obj.getComplaintId());
                inspectCase.setInspectCaseStatus(YDZFConstants.INSPECTCASE.INSPECT_STATUS_WAIT);
                inspectCase.setIsDel(YDZFConstants.SYSTEM.IS_NOT_DEL);
                inspectCase.setCreateDate(DateUtil.getCurrent());
                inspectCase.setCreateUserid(userInfo.getUserId());
                inspectCase.setInspectCaseCreatDepId(obj.getComplaintCreatDepId());
                inspectCase.setInspectCaseCreatUserId(obj.getComplaintCreatUserId());
                inspectCase.setInspectCaseCreatDate(DateUtil.getCurrent());
                addForm.setYdzfInspectCase(inspectCase);
                caseService.addInspectCase(addForm);
            }else{
                obj.setComplaintStatus(4);
                complaintService.updateCompalit(obj);
            }
        } catch (Exception e) {
            log.error("举报投诉审核：", e);
            result.setSuccess(false);
            result.setMessage("系统繁忙");
        }
        return result;
    }
    @RequestMapping(value = "/audit", method = RequestMethod.GET)
    @Security()
    public ModelAndView auditForm(Model model,@RequestParam("complaintId") Integer complaintId) {
        model.addAttribute("obj",complaintService.queryById(complaintId));
        model.addAttribute("action", "举报投诉审核");
        model.addAttribute("nextAction", "audit");
        return new ModelAndView("complaint/audit");
    }
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    @Security()
    @ResponseBody
    public JsonObject audit(TYdzfComplaint obj, HttpServletRequest request) {
        JsonObject result=new JsonObject(true, "举报投诉审核成功");
        SysUserInfo userInfo= (SysUserInfo)SessionUtils.getUserSession();
        obj.setUpdateUserid(userInfo.getUserId());
        obj.setComplaintAuditUserid(userInfo.getUserId());
        obj.setComplaintAuditDate(DateUtil.getCurrent());
        try {
            complaintService.updateCompalit(obj);
        } catch (Exception e) {
            log.error("举报投诉审核：", e);
            result.setSuccess(false);
            result.setMessage("系统繁忙");
        }
        return result;
    }
    @RequestMapping(value = "/searchEnterprise", method = RequestMethod.POST)
    @Security()
    @ResponseBody
    public JsonObject searchEnterprise( HttpServletRequest request) {
        String type=request.getParameter("enterpriseBusinessType");
        String name=request.getParameter("enterpriseName");
        DubboEnterpriseInfo condition=new DubboEnterpriseInfo();
        condition.setBusinessType(type);
        condition.setEnterpriseName(name);
        JsonObject result=new JsonObject(true, "SUC");
        List<DubboEnterpriseInfo> list=commonService.queryEnterpriseInfos(condition);
        if (list!=null&&list.size()>0){
            String[] arr=new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                arr[i]=list.get(i).getEnterpriseName()+";"+list.get(i).getEnterpriseId();
            }
            result.setData(arr);
        }

        return result;
    }
    @RequestMapping(value = "/delete")
    @Security()
    @ResponseBody
    public JsonObject changeStatus(@RequestParam("compIds") List<Integer> compIds, HttpServletRequest request) {
        JsonObject result=new JsonObject(true, "操作成功");
        SysUserInfo userInfo = (SysUserInfo)SessionUtils.getUserSession();
        Integer userId = userInfo.getUserId();
        TYdzfComplaint obj=new TYdzfComplaint();
        obj.setUpdateUserid(userId);
        obj.setIsDel(YDZFConstants.SYSTEM.IS_DEL);
        try {
            for (Integer id:compIds){
                obj.setComplaintId(id);
                complaintService.updateCompalit(obj);
            }
        } catch (Exception e) {
            log.error("举报投诉删除：", e);
            result.setSuccess(false);
            result.setMessage("系统繁忙");
        }
        return result;

    }
    @RequestMapping(value = "/getUsers")
    @Security()
    @ResponseBody
    public JsonObject getUsers(@RequestParam("deptId")Integer  deptId, HttpServletRequest request) {
        JsonObject result=new JsonObject(true, "操作成功");
        SysUserInfo userInfo = (SysUserInfo)SessionUtils.getUserSession();
        SysUser con=new SysUser();
        con.setDeptId(deptId);
        try {
            List<SysUser> users= commonService.getUsers(con, null, null, userInfo);
            List<SysUser> newUsers=new ArrayList<SysUser>();
            if(users!=null&&users.size()>0){
                for (SysUser user:users){
                    SysUser newUser=new SysUser();
                    newUser.setUserId(user.getUserId());
                    newUser.setUserName(user.getUserName());
                    newUsers.add(newUser);
                }
            }
            result.setData(newUsers);
        } catch (Exception e) {
            log.error("举报投诉立案人员：", e);
            result.setSuccess(false);
            result.setMessage("系统繁忙");
        }
        return result;

    }
}
