package com.bdx.rainbow.controller.ydzf.templet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseTempletAndContentVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorTempletAndContentVO;
import com.bdx.rainbow.entity.ydzf.*;
import com.bdx.rainbow.urs.entity.SysUserInfo;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bdx.rainbow.base.annotation.Security;
import com.bdx.rainbow.base.request.SessionUtils;
import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.controller.core.BaseController;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.service.ydzf.templet.IYDZFInspectCaseTempletContentService;
import com.bdx.rainbow.service.ydzf.templet.IYDZFInspectCaseTempletService;
import com.bdx.rainbow.service.ydzf.templet.IYDZFMonitorTempletContentService;
import com.bdx.rainbow.service.ydzf.templet.IYDZFMonitorTempletService;
import com.bdx.rainbow.urs.entity.IUserInfo;
@Controller
@RequestMapping("/templet")
public class YDZFTempletController  extends BaseController{
	private static final Logger log = LoggerFactory
			.getLogger(YDZFTempletController.class);
	@Autowired
	private IYDZFMonitorTempletService ydzfMonitorTempletService;
	@Autowired
	private  IYDZFMonitorTempletContentService ydzfMonitorTempletContentService;
	@Autowired
	private IYDZFInspectCaseTempletService ydzfInspectCaseTempletService;
	@Autowired
	private  IYDZFInspectCaseTempletContentService ydzfInspectCaseTempletContentService;
	
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public ModelAndView Init(Model model,HttpServletRequest request) throws Exception{
        model.addAttribute("type",request.getParameter("type"));
		return new ModelAndView("templet/init");
	}
	
	@RequestMapping("/monitor/list")
	public ModelAndView monitorList(Model model,PageInfo pageinfo,TYdzfMonitorTemplet templet) throws Exception{
		List<TYdzfMonitorTemplet> list=new ArrayList<TYdzfMonitorTemplet>();
        templet.setIsDel(YDZFConstants.SYSTEM.IS_NOT_DEL);
		list=ydzfMonitorTempletService.queryMonitorTempleteList(templet, pageinfo.getPageStart(), pageinfo.getTotalCount());
		int total=ydzfMonitorTempletService.countMonitorTemplete(templet);
		pageinfo.setTotalCount(total);
		model.addAttribute("pageinfo", pageinfo);
		model.addAttribute("rows", list);
        model.addAttribute("type","monitor");
		return new ModelAndView("templet/list");
	}
	@RequestMapping( "/inspectCase/list")
	public ModelAndView inspectList(Model model,PageInfo pageinfo,TYdzfInspectCaseTemplet templet) throws Exception{
		List<TYdzfInspectCaseTemplet> list=new ArrayList<TYdzfInspectCaseTemplet>();
        templet.setIsDel(YDZFConstants.SYSTEM.IS_NOT_DEL);
        list=ydzfInspectCaseTempletService.queryInspectCaseTempleteList(templet, pageinfo.getPageStart(), pageinfo.getTotalCount());
		int total=ydzfInspectCaseTempletService.countInspectCaseTemplete(templet);
		pageinfo.setTotalCount(total);
		model.addAttribute("pageinfo", pageinfo);
		model.addAttribute("rows", list);
        model.addAttribute("type","inspectCase");
		return new ModelAndView("templet/list");
	}
	
	@RequestMapping(value = "/inspectCase/create", method = RequestMethod.GET)
	@Security()
	public ModelAndView inspectCreateForm(Model model,HttpServletRequest request) throws Exception{
        model.addAttribute("nextAction", "create");
        model.addAttribute("type", request.getParameter("type"));
        return new ModelAndView("templet/edit");
	}

	@RequestMapping(value = "/inspectCase/create", method = RequestMethod.POST)
	@ResponseBody
	@Security(returnType="json")
	public JsonObject inspectCreate(Model model,YdzfInspectCaseTempletAndContentVO templetAndContentVO) throws Exception{
		JsonObject jo = new JsonObject(true, "新增成功！");
		try {
			IUserInfo userInfo=SessionUtils.getUserSession();
            templetAndContentVO.getYdzfInspectCaseTemplet().setIsDel(YDZFConstants.SYSTEM.IS_NOT_DEL);
            templetAndContentVO.getYdzfInspectCaseTemplet().setCreateUserid(userInfo.getUserId());
            templetAndContentVO.getYdzfInspectCaseTemplet().setCreateDate(DateUtil.getCurrent());
            templetAndContentVO.getYdzfInspectCaseTemplet().setUpdateDate(DateUtil.getCurrent());
            templetAndContentVO.getYdzfInspectCaseTemplet().setUpdateUserid(userInfo.getUserId());
			int templetId= ydzfInspectCaseTempletService.insertInspectCaseTemplete(templetAndContentVO.getYdzfInspectCaseTemplet());
            if(templetAndContentVO.getYdzfInspectCaseTempletContentList()!=null&&templetAndContentVO.getYdzfInspectCaseTempletContentList().size()>0){
                for (TYdzfInspectCaseTempletContent content:templetAndContentVO.getYdzfInspectCaseTempletContentList()){
                    content.setIsDel(YDZFConstants.SYSTEM.IS_NOT_DEL);
                    content.setInspectCaseTempletId(templetId);
                    content.setCreateUserid(userInfo.getUserId());
                    content.setCreateDate(DateUtil.getCurrent());
                    content.setUpdateDate(DateUtil.getCurrent());
                    content.setUpdateUserid(userInfo.getUserId());
                    ydzfInspectCaseTempletContentService.insertInspectCaseTempleteContent(content);
                }
            }

		} catch (Exception e) {
			log.error(e.getMessage(), e.fillInStackTrace());
			jo.setMessage("系统繁忙");
			jo.setSuccess(false);
		}
		return jo;
	}
	@RequestMapping(value = "/inspectCase/update", method = RequestMethod.GET)
	@Security()
	public ModelAndView inspectUpdateForm(Model model,@RequestParam("templetId") Integer templetId,@RequestParam("type") String type) throws Exception{
        model.addAttribute("nextAction", "update");
        model.addAttribute("type", type);
        TYdzfInspectCaseTemplet templet=ydzfInspectCaseTempletService.queryInspectCaseTempleteInfo(templetId);
        TYdzfInspectCaseTempletContent caseTempletContent=new TYdzfInspectCaseTempletContent();
        caseTempletContent.setInspectCaseTempletId(templetId);
        caseTempletContent.setIsDel(YDZFConstants.SYSTEM.IS_NOT_DEL);
        List<TYdzfInspectCaseTempletContent> list=ydzfInspectCaseTempletContentService.queryInspectCaseTempleteContenAll(caseTempletContent);
        model.addAttribute("obj",templet);
        model.addAttribute("lists",list);
        return new ModelAndView("templet/edit");
	}

	@RequestMapping(value = "inspectCase/update", method = RequestMethod.POST)
	@ResponseBody
	@Security(returnType="json")
	public JsonObject inspectUpdate(Model model,YdzfInspectCaseTempletAndContentVO templetAndContentVO) throws Exception{
		JsonObject jo = new JsonObject(true, "修改成功！");
		try {
			IUserInfo userInfo=SessionUtils.getUserSession();
            templetAndContentVO.getYdzfInspectCaseTemplet().setUpdateDate(DateUtil.getCurrent());
            templetAndContentVO.getYdzfInspectCaseTemplet().setUpdateUserid(userInfo.getUserId());
            ydzfInspectCaseTempletService.updateInspectCaseTemplete(templetAndContentVO.getYdzfInspectCaseTemplet());
            TYdzfInspectCaseTempletContent caseTempletContent=new TYdzfInspectCaseTempletContent();
            caseTempletContent.setIsDel(YDZFConstants.SYSTEM.IS_DEL);
            ydzfInspectCaseTempletContentService.updateInspectByInspectTempletId(caseTempletContent, templetAndContentVO.getYdzfInspectCaseTemplet().getInspectCaseTempletId());
            if(templetAndContentVO.getYdzfInspectCaseTempletContentList()!=null&&templetAndContentVO.getYdzfInspectCaseTempletContentList().size()>0){
                for (TYdzfInspectCaseTempletContent content:templetAndContentVO.getYdzfInspectCaseTempletContentList()) {
                    content.setUpdateDate(DateUtil.getCurrent());
                    content.setUpdateUserid(userInfo.getUserId());
                    content.setIsDel(YDZFConstants.SYSTEM.IS_NOT_DEL);
                   if (content.getInspectCaseTempletContentId()!=null){
                       ydzfInspectCaseTempletContentService.updateInspectCaseTempleteContent(content);
                   }else {
                       content.setCreateDate(DateUtil.getCurrent());
                       content.setCreateUserid(userInfo.getUserId());
                       content.setInspectCaseTempletId(templetAndContentVO.getYdzfInspectCaseTemplet().getInspectCaseTempletId());
                       ydzfInspectCaseTempletContentService.insertInspectCaseTempleteContent(content);
                   }


                }
            }

		} catch (Exception e) {
			log.error(e.getMessage(), e.fillInStackTrace());
			jo.setMessage("系统繁忙");
			jo.setSuccess(false);
		}
		return jo;
	}

	@RequestMapping(value = "/monitor/create", method = RequestMethod.GET)
	@Security()
	public ModelAndView createForm(Model model,HttpServletRequest request) throws Exception{
        model.addAttribute("nextAction", "create");
        model.addAttribute("type", request.getParameter("type"));
        return new ModelAndView("templet/edit");
	}

	@RequestMapping(value = "/monitor/create", method = RequestMethod.POST)
	@ResponseBody
	@Security(returnType="json")
	public JsonObject create(Model model,YdzfMonitorTempletAndContentVO templetAndContentVO) throws Exception{
		JsonObject jo = new JsonObject(true, "新增成功！");
		try {
			IUserInfo userInfo=SessionUtils.getUserSession();
            templetAndContentVO.getYdzfMonitorTemplet().setIsDel(YDZFConstants.SYSTEM.IS_NOT_DEL);
            templetAndContentVO.getYdzfMonitorTemplet().setCreateUserid(userInfo.getUserId());
            templetAndContentVO.getYdzfMonitorTemplet().setCreateDate(DateUtil.getCurrent());
            templetAndContentVO.getYdzfMonitorTemplet().setUpdateDate(DateUtil.getCurrent());
            templetAndContentVO.getYdzfMonitorTemplet().setUpdateUserid(userInfo.getUserId());
			int templetId=ydzfMonitorTempletService.insertMonitorTemplete(templetAndContentVO.getYdzfMonitorTemplet());
            if (templetAndContentVO.getYdzfMonitorTempletContentList()!=null&&templetAndContentVO.getYdzfMonitorTempletContentList().size()>0){
                for (TYdzfMonitorTempletContent content:templetAndContentVO.getYdzfMonitorTempletContentList()){
                    content.setIsDel(YDZFConstants.SYSTEM.IS_NOT_DEL);
                    content.setMonitorTempletId(templetId);
                    content.setCreateUserid(userInfo.getUserId());
                    content.setCreateDate(DateUtil.getCurrent());
                    content.setUpdateDate(DateUtil.getCurrent());
                    content.setUpdateUserid(userInfo.getUserId());
                    ydzfMonitorTempletContentService.insertMonitorTempleteContent(content);
                }
            }

		} catch (Exception e) {
			log.error(e.getMessage(), e.fillInStackTrace());
			jo.setMessage("系统繁忙");
			jo.setSuccess(false);
		}
		return jo;
	}
	@RequestMapping(value = "/monitor/update", method = RequestMethod.GET)
	@Security()
	public ModelAndView updateForm(Model model,@RequestParam("templetId") Integer templetId,@RequestParam("type") String type) throws Exception{
        model.addAttribute("nextAction", "update");
        model.addAttribute("type", type);
        TYdzfMonitorTemplet templet= ydzfMonitorTempletService.queryMonitorTempleteInfo(templetId);
        TYdzfMonitorTempletContent content=new TYdzfMonitorTempletContent();
        content.setMonitorTempletId(templetId);
        content.setIsDel(YDZFConstants.SYSTEM.IS_NOT_DEL);
        List<TYdzfMonitorTempletContent> list= ydzfMonitorTempletContentService.queryMonitorTempleteContenAll(content);
        model.addAttribute("obj",templet);
        model.addAttribute("lists",list);
        return new ModelAndView("templet/edit");
	}

	@RequestMapping(value = "/monitor/update", method = RequestMethod.POST)
	@ResponseBody
	@Security(returnType="json")
	public JsonObject update(Model model,YdzfMonitorTempletAndContentVO templetAndContentVO) throws Exception{
		JsonObject jo = new JsonObject(true, "修改成功！");
		try {
			IUserInfo userInfo=SessionUtils.getUserSession();
            templetAndContentVO.getYdzfMonitorTemplet().setUpdateDate(DateUtil.getCurrent());
            templetAndContentVO.getYdzfMonitorTemplet().setUpdateUserid(userInfo.getUserId());
            TYdzfMonitorTempletContent templetContent=new TYdzfMonitorTempletContent();
            templetContent.setIsDel(YDZFConstants.SYSTEM.IS_DEL);
            ydzfMonitorTempletContentService.updateMonitorByMonitorTempletId(templetContent, templetAndContentVO.getYdzfMonitorTemplet().getMonitorTempletId());
			ydzfMonitorTempletService.updateMonitorTemplete(templetAndContentVO.getYdzfMonitorTemplet());
            if (templetAndContentVO.getYdzfMonitorTempletContentList()!=null&&templetAndContentVO.getYdzfMonitorTempletContentList().size()>0){
                for (TYdzfMonitorTempletContent content:templetAndContentVO.getYdzfMonitorTempletContentList()){
                    content.setUpdateDate(DateUtil.getCurrent());
                    content.setUpdateUserid(userInfo.getUserId());
                    content.setIsDel(YDZFConstants.SYSTEM.IS_NOT_DEL);
                    if (content.getMonitorTempletContentId()!=null){
                        ydzfMonitorTempletContentService.updateMonitorTempleteContent(content);
                    }else{
                        if (content.getItemContent()!=null){
                            content.setCreateDate(DateUtil.getCurrent());
                            content.setCreateUserid(userInfo.getUserId());
                            content.setMonitorTempletId(templetAndContentVO.getYdzfMonitorTemplet().getMonitorTempletId());
                            ydzfMonitorTempletContentService.insertMonitorTempleteContent(content);
                        }
                    }
                }
            }
		} catch (Exception e) {
			log.error(e.getMessage(), e.fillInStackTrace());
			jo.setMessage("系统繁忙");
			jo.setSuccess(false);
		}
		return jo;
	}

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    @Security()
    public ModelAndView show(Model model,@RequestParam("templetId") Integer templetId,@RequestParam("type") String type) {
        try {
            model.addAttribute("type",type);
            if ("monitor".equals(type)){
                TYdzfMonitorTemplet templet= ydzfMonitorTempletService.queryMonitorTempleteInfo(templetId);
                TYdzfMonitorTempletContent content=new TYdzfMonitorTempletContent();
                content.setMonitorTempletId(templetId);
                content.setIsDel(YDZFConstants.SYSTEM.IS_NOT_DEL);
                List<TYdzfMonitorTempletContent> list= ydzfMonitorTempletContentService.queryMonitorTempleteContenAll(content);
                model.addAttribute("obj",templet);
                model.addAttribute("lists",list);
            }else if("inspectCase".equals(type)){
                TYdzfInspectCaseTemplet templet=ydzfInspectCaseTempletService.queryInspectCaseTempleteInfo(templetId);
                TYdzfInspectCaseTempletContent caseTempletContent=new TYdzfInspectCaseTempletContent();
                caseTempletContent.setInspectCaseTempletId(templetId);
                caseTempletContent.setIsDel(YDZFConstants.SYSTEM.IS_NOT_DEL);
                List<TYdzfInspectCaseTempletContent> list=ydzfInspectCaseTempletContentService.queryInspectCaseTempleteContenAll(caseTempletContent);
                model.addAttribute("obj",templet);
                model.addAttribute("lists",list);
            }
        } catch (Exception e) {
            log.error("稽查查看：", e);
        }
        return new ModelAndView("inspect/case/show");
    }


    @RequestMapping(value = "/delete")
    @Security()
    @ResponseBody
    public JsonObject changeStatus(@RequestParam("templetIds") List<Integer> templetIds, HttpServletRequest request) {
        JsonObject result=new JsonObject(true, "操作成功");
        SysUserInfo userInfo = (SysUserInfo)SessionUtils.getUserSession();
        Integer userId = userInfo.getUserId();
        String type=request.getParameter("type");
        if ("monitor".equals(type)){
            TYdzfMonitorTemplet templet=new TYdzfMonitorTemplet();
            templet.setIsDel(YDZFConstants.SYSTEM.IS_DEL);
            templet.setUpdateDate(DateUtil.getCurrent());
            templet.setUpdateUserid(userId);
            try {
                for (Integer id:templetIds){
                    templet.setMonitorTempletId(id);
                    ydzfMonitorTempletService.updateMonitorTemplete(templet);
                    ydzfMonitorTempletContentService.delMonitorTempleteContentByTempId(id,userInfo);
                }
            }catch (Exception e) {
                log.error("稽查撤案：", e);
                result.setSuccess(false);
                result.setMessage("系统繁忙");
            }
        }else if("inspectCase".equals(type)){
            TYdzfInspectCaseTemplet templet=new TYdzfInspectCaseTemplet();
            templet.setIsDel(YDZFConstants.SYSTEM.IS_DEL);
            templet.setUpdateDate(DateUtil.getCurrent());
            templet.setUpdateUserid(userId);
            try {
                for (Integer id:templetIds){
                    templet.setInspectCaseTempletId(id);
                    ydzfInspectCaseTempletService.updateInspectCaseTemplete(templet);
                    TYdzfInspectCaseTempletContent content=new TYdzfInspectCaseTempletContent();
                    content.setIsDel(YDZFConstants.SYSTEM.IS_DEL);
                    content.setInspectCaseTempletId(id);
                    content.setUpdateUserid(userId);
                    content.setUpdateDate(DateUtil.getCurrent());
                    ydzfInspectCaseTempletContentService.delInspectCaseTempleteContentByTempId(id,userInfo);
                }
            }catch (Exception e) {
                log.error("稽查撤案：", e);
                result.setSuccess(false);
                result.setMessage("系统繁忙");
            }
        }
        return result;

    }
	
	
	

}
