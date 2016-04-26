package com.bdx.rainbow.controller.ydzf.inspect;


import javax.servlet.http.HttpServletRequest;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseListResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfComplaint;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTemplet;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.common.IYDZFComplaintService;
import com.bdx.rainbow.service.ydzf.templet.IYDZFInspectCaseTempletService;
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

import com.bdx.rainbow.base.annotation.Security;
import com.bdx.rainbow.base.request.SessionUtils;
import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.controller.core.BaseController;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.dto.ydzf.form.YdzfInspectCaseAddForm;
import com.bdx.rainbow.dto.ydzf.form.YdzfInspectCaseListQueryInfo;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseService;
import com.bdx.rainbow.urs.entity.IUserInfo;

import java.util.List;

@Controller
@RequestMapping("/inspect/case")
public class YDZFInspectCaseController  extends BaseController{
	private static final Logger log = LoggerFactory
			.getLogger(YDZFInspectCaseController.class);
    @Autowired
    private IYDZFComplaintService complaintService;
	@Autowired
	private IYDZFInspectCaseService ydzfInspectCaseService;
    @Autowired
    private IYDZFInspectCaseTempletService templetService;
    @Autowired
    private IYDZFCommonService commonService;
	
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	@Security()
	public ModelAndView monitorInit(Model model) throws Exception{
        SysUserInfo userInfo= (SysUserInfo)SessionUtils.getUserSession();
        model.addAttribute("depts",complaintService.getDepts(userInfo.getUser()));
		return new ModelAndView("inspect/case/init");
	}
	
	@RequestMapping("/list")
	@Security()
	public ModelAndView monitorList(Model model,PageInfo pageinfo, YdzfInspectCaseListQueryInfo ydzfInspectCaseListQueryInfo) throws Exception{
        ydzfInspectCaseListQueryInfo.getYdzfInspectCase().setIsDel("0");
        List<YdzfInspectCaseListResultVO> resultVOs= ydzfInspectCaseService.queryInspectCaseList(ydzfInspectCaseListQueryInfo, pageinfo.getPageStart(), pageinfo.getPageCount());
        int total=ydzfInspectCaseService.countInspectCaseList(ydzfInspectCaseListQueryInfo);
        pageinfo.setTotalCount(total);
        model.addAttribute("pageinfo",pageinfo);
        model.addAttribute("rows",resultVOs);
        return new ModelAndView("inspect/case/list");
	}
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @Security()
    public ModelAndView createForm(Model model) {
        SysUserInfo userInfo= (SysUserInfo)SessionUtils.getUserSession();
        model.addAttribute("depts",complaintService.getDepts(userInfo.getUser()));
        try {
            TYdzfInspectCaseTemplet record=new TYdzfInspectCaseTemplet();
            record.setIsDel("0");
            model.addAttribute("templets",templetService.queryInspectCaseTempleteAll(record));
        } catch (Exception e) {
            log.error("稽查模版加载：", e);
        }
        model.addAttribute("nextAction", "create");
        return new ModelAndView("inspect/case/edit");
    }
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
    @Security()
	public JsonObject inspectCreate(Model model,YdzfInspectCaseAddForm caseForm,HttpServletRequest request) throws Exception{
		JsonObject jo = new JsonObject(true, "新增成功！");	
		try {
            SysUserInfo userInfo= (SysUserInfo)SessionUtils.getUserSession();
            caseForm.getYdzfInspectCase().setInspectCaseSourceType(3);
            caseForm.getYdzfInspectCase().setIsDel(YDZFConstants.SYSTEM.IS_NOT_DEL);
            caseForm.getYdzfInspectCase().setInspectCaseCreatDate(DateUtil.getCurrent());
			caseForm.getYdzfInspectCase().setCreateUserid(userInfo.getUser().getDeptId());
            caseForm.getYdzfInspectCase().setCreateDate(DateUtil.getCurrent());
            caseForm.getYdzfInspectCase().setInspectCaseCreatDepId(userInfo.getUser().getDeptId());
            caseForm.getYdzfInspectCase().setInspectCaseCreatUserId(userInfo.getUserId());
            caseForm.getYdzfInspectCase().setInspectCaseStatus(YDZFConstants.INSPECTCASE.INSPECT_STATUS_WAIT);
			ydzfInspectCaseService.addInspectCase(caseForm);
		} catch (Exception e) {
			log.error(e.getMessage(), e.fillInStackTrace());
			jo.setMessage("系统繁忙！");
			jo.setSuccess(false);
		}
		return jo;
	
	}
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @Security()
    public ModelAndView updateForm(Model model,@RequestParam("inspectCaseId") Integer inspectCaseId) {
        try {
            SysUserInfo userInfo= (SysUserInfo)SessionUtils.getUserSession();
            model.addAttribute("depts",complaintService.getDepts(userInfo.getUser()));
            TYdzfInspectCaseTemplet record=new TYdzfInspectCaseTemplet();
            record.setIsDel("0");
            model.addAttribute("templets",templetService.queryInspectCaseTempleteAll(record));
            model.addAttribute("obj",ydzfInspectCaseService.queryInspectCaseInfoAndRel(inspectCaseId));

        } catch (Exception e) {
            log.error("稽查模版加载：", e);
        }
        model.addAttribute("nextAction", "update");
        return new ModelAndView("inspect/case/edit");
    }
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
    @Security()
	public JsonObject inspectUpdate(Model model,YdzfInspectCaseAddForm caseForm,HttpServletRequest request) throws Exception{
		JsonObject jo = new JsonObject(true, "修改成功！");
		try {
            SysUserInfo userInfo= (SysUserInfo)SessionUtils.getUserSession();
            TYdzfInspectCase ydzfInspectCase=caseForm.getYdzfInspectCase();
            ydzfInspectCase.setUpdateDate(DateUtil.getCurrent());
            ydzfInspectCase.setUpdateUserid(userInfo.getUserId());
			ydzfInspectCaseService.updateInspectCase(ydzfInspectCase);
		} catch (Exception e) {
			log.error(e.getMessage(), e.fillInStackTrace());
			jo.setMessage("系统繁忙！");
			jo.setSuccess(false);
		}
		return jo;

	}

    @RequestMapping(value = "/delete")
    @Security()
    @ResponseBody
    public JsonObject changeStatus(@RequestParam("caseIds") List<Integer> caseIds, HttpServletRequest request) {
        JsonObject result=new JsonObject(true, "操作成功");
        SysUserInfo userInfo = (SysUserInfo)SessionUtils.getUserSession();
        Integer userId = userInfo.getUserId();
        TYdzfInspectCase obj=new TYdzfInspectCase();
        obj.setUpdateUserid(userId);
        obj.setUpdateDate(DateUtil.getCurrent());
        obj.setIsDel(YDZFConstants.SYSTEM.IS_DEL);
        try {
            for (Integer id:caseIds){
                obj.setInspectCaseId(id);
                ydzfInspectCaseService.updateInspectCase(obj);
            }
        } catch (Exception e) {
            log.error("稽查撤案：", e);
            result.setSuccess(false);
            result.setMessage("系统繁忙");
        }
        return result;

    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    @Security()
    public ModelAndView show(Model model,@RequestParam("inspectCaseId") Integer inspectCaseId) {
        try {
            YdzfInspectCaseListResultVO resultVO=ydzfInspectCaseService.queryInspectCaseInfoAndRel(inspectCaseId);
            model.addAttribute("obj",resultVO);
            model.addAttribute("corp",commonService.getEnterpriseInfoByIdDubbo(resultVO.getYdzfInspectCase().getInspectCaseEnterpriseId()));
        } catch (Exception e) {
            log.error("稽查查看：", e);
        }
        return new ModelAndView("inspect/case/show");
    }

}
