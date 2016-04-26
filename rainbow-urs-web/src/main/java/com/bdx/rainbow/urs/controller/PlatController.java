package com.bdx.rainbow.urs.controller;

import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.urs.SysPlatfrom;
import com.bdx.rainbow.urs.common.Constants;
import com.bdx.rainbow.urs.entity.SysUserInfo;
import com.bdx.rainbow.urs.service.IPlantService;
import com.bdx.rainbow.urs.service.IUserService;
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
 * Created by Ban on 2016/3/3.
 */
@Controller
@RequestMapping("/sys")
public class PlatController {
    private static Logger log = LoggerFactory.getLogger(PlatController.class);
    @Autowired
    private IPlantService plantService;
    @Autowired
    private IUserService userService;

    @RequestMapping(value = {"/platInit"}, method = {RequestMethod.GET})
    public ModelAndView initPlat() {

        return new ModelAndView("/sys/plat/platInit");
    }

    @RequestMapping(value = "/plat/list")
    public ModelAndView list(Model model, PageInfo pageinfo, SysPlatfrom plat) throws BusinessException, SystemException {
        log.info("-------------------------SYS_PLAT 分页查询----------------------------");
        List<SysPlatfrom> plats = plantService.getPlats(plat, pageinfo.getPageStart(), pageinfo.getPageCount());
        int total = plantService.countPlat(plat);
        pageinfo.setTotalCount(total);

        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("plats", plats);
        return new ModelAndView("/sys/plat/platList");
    }

    @RequestMapping(value = "/plat/create", method = RequestMethod.GET)
    public ModelAndView createForm(Model model, SysPlatfrom plat) {
        model.addAttribute("action", "新增平台");
        model.addAttribute("nextAction", "create");

        return new ModelAndView("/sys/plat/platEdit");
    }

    @RequestMapping(value = "/plat/create", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> create(Model model, SysPlatfrom plat, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);

        plat.setCreater(userInfo.getUserId());
        plat.setPlatStatus("1");
        try {
            plantService.insertPlat(plat);
            result.put("success", true);
            result.put("message", "添加成功");
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "/plat/changeStatus")
    @ResponseBody
    public Map<String, Object> changeStatus(@RequestParam("platIds") List<Integer> platIds, @RequestParam("platStatus") String platStatus, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);
        Integer userId = userInfo.getUserId();
        try {
            plantService.updatePlatStatusByIds(platIds, platStatus, userId);
            result.put("success", true);
            result.put("message", "操作成功");
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/plat/update", method = RequestMethod.GET)
    public ModelAndView updateForm(Model model, @RequestParam("platId") Integer platId) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("platId", platId);
        condition.put("platStatus", Constants.PLAT_STATUS_NORMAL_1);
        SysPlatfrom record = new SysPlatfrom();
        record.setPlatId(platId);
        model.addAttribute("plat", plantService.getPlats(record, null, null).get(0));
        model.addAttribute("action", "更新部门");
        model.addAttribute("nextAction", "update");

        return new ModelAndView("/sys/plat/platEdit");
    }

    @RequestMapping(value = "/plat/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(SysPlatfrom plat, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);
        plat.setUpdater(userInfo.getUserId());
        try {
            plantService.updatePlat(plat);
            result.put("message", "编辑成功");
        } catch (SystemException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "系统异常，请重试！");
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return result;
    }

}
