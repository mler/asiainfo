package com.bdx.rainbow.urs.controller;

import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.urs.SysMappApnsToken;
import com.bdx.rainbow.urs.common.Constants;
import com.bdx.rainbow.urs.entity.SysUserInfo;
import com.bdx.rainbow.urs.service.ImappService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Elbert on 2016/3/16.
 */

@Controller
@RequestMapping("/sys")
public class MappController {
    private static Logger log = LoggerFactory.getLogger(MappController.class);
    @Autowired
    ImappService mappService;


    @RequestMapping(value = {"/mappInit"}, method = {RequestMethod.GET})
    public ModelAndView initMapp() {

        return new ModelAndView("/sys/mapp/mappInit");

    }

    @RequestMapping(value = {"/mappList"})
    public ModelAndView list(Model model, PageInfo pageInfo, SysMappApnsToken mapp) throws BusinessException, SystemException {
        log.info("-------------------------SYS_PLAT 分页查询----------------------------");
        List<SysMappApnsToken> mapps = mappService.getMapps(mapp, pageInfo.getPageStart(), pageInfo.getPageCount());
        int total = mappService.countMapp(mapp);
        pageInfo.setTotalCount(total);
        model.addAttribute("mapps", mapps);
        model.addAttribute("pageinfo", pageInfo);
        return new ModelAndView("/sys/mapp/mappList");


    }

    @RequestMapping(value = {"/mapp/create"}, method = RequestMethod.GET)

    public ModelAndView createForm(Model model) {

        model.addAttribute("nextAction", "create");
        model.addAttribute("action", "新增终端");
        return new ModelAndView("/sys/mapp/mappEdit");

    }

    @RequestMapping(value = {"/mapp/create"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> create(HttpServletRequest request, PageInfo pageinfo, SysMappApnsToken apns) {

        Map<String, Object> result = new HashMap<String, Object>();
        if (mappService.getMapps(apns, pageinfo.getPageStart(), pageinfo.getPageCount()).size() != 0) {
            result.put("success", false);
            result.put("message", "该终端已存在");
        }
        SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);
        apns.setValid("1");
        apns.setStatus("0");
        try {
            mappService.insertMapp(apns);
            result.put("success", true);
            result.put("message", "添加成功");
        } catch (SystemException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "系统异常，请重试！");
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/mapp/update", method = RequestMethod.GET)
    public ModelAndView updateForm(@RequestParam("token") String token, Model model) throws com.bdx.rainbow.common.SystemException, com.bdx.rainbow.common.BusinessException, com.bdx.rainbow.common.exception.SystemException, com.bdx.rainbow.common.exception.BusinessException {
        model.addAttribute("mapp", mappService.getMappsBytoken(token));
        model.addAttribute("action", "更新终端");
        model.addAttribute("nextAction", "update");

        return new ModelAndView("/sys/mapp/mappEdit");
    }

    @RequestMapping(value = "/mapp/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(SysMappApnsToken apns) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            mappService.updateMapp(apns);
            result.put("success", true);
            result.put("message", "更新成功");
        } catch (SystemException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "系统异常，请重试！");
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/mapp/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changeStatus(@RequestParam("mappIds") List<String> mappIds, @RequestParam("valid") String valid) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {

            mappService.updateMappValidBytokens(mappIds, valid);
            result.put("success", true);
            result.put("message", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "系统异常，请重试！");
        }
        return result;
    }

}
