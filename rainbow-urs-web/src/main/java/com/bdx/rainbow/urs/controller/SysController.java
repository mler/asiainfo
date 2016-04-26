package com.bdx.rainbow.urs.controller;

import com.bdx.rainbow.common.BusinessException;
import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.SystemException;
import com.bdx.rainbow.entity.urs.SysParamDetail;
import com.bdx.rainbow.urs.service.ISysParamService;
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
 * Created by core on 16/2/17.
 */
@Controller
@RequestMapping("/sys")
public class SysController {
    private static Logger log = LoggerFactory.getLogger(SysController.class);
    @Autowired
    private ISysParamService paramService;

    @RequestMapping(value = {"/paramInit"}, method = {RequestMethod.GET})
    public ModelAndView initParam(Model model)
            throws Exception {
        return new ModelAndView("/sys/paramInit");
    }

    @RequestMapping(value = "/param/list")
    public ModelAndView getParamList(Model model, PageInfo pageinfo, SysParamDetail record, HttpServletRequest req) {

        List<SysParamDetail> detail = paramService.getParams(record, pageinfo.getPageStart(), pageinfo.getPageCount());
        int total = paramService.countParam(record);
        pageinfo.setTotalCount(total);
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("objs", detail);
        return new ModelAndView("/sys/ParamList");
    }

    @RequestMapping(value = "/param/create", method = RequestMethod.GET)
    public ModelAndView createForm(Model model) {
        model.addAttribute("action", "新增参数");
        model.addAttribute("nextAction", "create");

        return new ModelAndView("/sys/paramEdit");
    }

    @RequestMapping(value = "/param/create", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createParam(SysParamDetail obj,PageInfo pageinfo) {
        Map<String, Object> result = new HashMap<String, Object>();

        if (paramService.getParams(obj, pageinfo.getPageStart(), pageinfo.getPageCount()).size()!=0) {
            result.put("success", false);
            result.put("message", "该参数已存在");

        } else {
            try {
                paramService.insertParam(obj);
                result.put("success", true);
                result.put("message", "添加成功");

            } catch (Exception e) {
                log.error("新增Detail异常", e.fillInStackTrace());
                result.put("success", false);
                result.put("message", "系统异常，请重试！");

            }
        }
        return result;
    }

    @RequestMapping(value = "/param/update", method = RequestMethod.GET)
    public ModelAndView updateForm(@RequestParam("pKey") String pKey, @RequestParam("pValue") String pValue, Model model) throws SystemException, BusinessException, com.bdx.rainbow.common.exception.SystemException, com.bdx.rainbow.common.exception.BusinessException {
        SysParamDetail detail = paramService.getParam(pKey, pValue);
        model.addAttribute("obj", detail);
        model.addAttribute("action", "更新参数");
        model.addAttribute("nextAction", "update");
        return new ModelAndView("/sys/paramEdit");
    }

    @RequestMapping(value = "/param/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(SysParamDetail obj, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        String keyId = request.getParameter("keyId");
        String valueId = request.getParameter("valueId");
        try {
            paramService.updateParam(obj, keyId, valueId);
            result.put("message", "修改参数成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "系统异常，请重试！");
        }
        return result;
    }

    @RequestMapping(value = "/param/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delParams(@RequestParam("pValue") List<String> pValues, SysParamDetail obj) {
//        String items = request.getParameter("delitems");
//        String[] item = items.split(",");

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            SysParamDetail detail = new SysParamDetail();
            for (int i = 0; i < pValues.size(); i++) {
                String pKey = pValues.get(i).split(";")[0];
                String pValue = pValues.get(i).split(";")[1];
                String pDesc = pValues.get(i).split(";")[2];
                detail.setpKey(pKey);
                detail.setpValue(pValue);
                detail.setpDesc(pDesc);
                paramService.deleteParam(detail);
            }

            result.put("success", true);
            result.put("message", "删除成功！");

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "系统异常，请重试！");
        }
        return result;
    }

    @RequestMapping(value = "/param/delete2", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delParam(SysParamDetail obj) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            paramService.deleteParam(obj);
            result.put("success", true);
            result.put("message", "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "系统异常，请重试！");
        }
        return result;
    }


}
