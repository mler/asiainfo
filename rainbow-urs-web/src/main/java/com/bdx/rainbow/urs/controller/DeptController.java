package com.bdx.rainbow.urs.controller;

import com.bdx.rainbow.common.BusinessException;
import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.SystemException;
import com.bdx.rainbow.entity.urs.SysDept;
import com.bdx.rainbow.entity.urs.SysUser;
import com.bdx.rainbow.entity.urs.SysViews;
import com.bdx.rainbow.urs.common.Constants;
import com.bdx.rainbow.urs.entity.SysUserInfo;
import com.bdx.rainbow.urs.service.IDeptService;
import com.bdx.rainbow.urs.service.ISysViewService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by core on 16/2/18.
 */
@Controller
@RequestMapping("/sys")
public class DeptController {
    private static Logger log = LoggerFactory.getLogger(DeptController.class);
    @Autowired
    private ISysViewService viewCacheService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IUserService userService;

    @RequestMapping(value = {"/deptInit"}, method = {RequestMethod.GET})
    public ModelAndView initDept(Model model)
            throws Exception {
        List<SysViews> deptStatus = viewCacheService.findCacheByKey("DEPT_STATUS");
        model.addAttribute("DEPT_STATUS", deptStatus);
        return new ModelAndView("/sys/dept/deptInit");
    }

    /**
     * 分页查询组织机构信息
     *
     * @param model
     * @param pageinfo
     * @param dept
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    @RequestMapping(value = "/dept/list")
    public ModelAndView list(Model model, PageInfo pageinfo, SysDept dept) throws SystemException, BusinessException {
        log.info("-------------------------SYS_DEPT 分页查询----------------------------");
        try {
            List<SysDept> depts = deptService.getDepts(dept, pageinfo.getPageStart(), pageinfo.getPageCount());
            int total = deptService.countDept(dept);
            pageinfo.setTotalCount(total);
            model.addAttribute("pageinfo", pageinfo);
            model.addAttribute("depts", depts);
        } catch (Exception e) {
            e.getStackTrace();
            log.error(e.getMessage());
        }
        return new ModelAndView("/sys/dept/deptList");
    }

    @RequestMapping(value = "/dept/create", method = RequestMethod.GET)
    public ModelAndView createForm(SysUser user, Model model, HttpServletRequest request) {
        model.addAttribute("action", "新增部门");
        model.addAttribute("nextAction", "create");
        user.setAdminUser(Constants.USER_ADMIN_FLAG);

        try {

            List<SysUser> users = userService.getUsers(user, null, null);
            model.addAttribute("users", users);
        } catch (com.bdx.rainbow.common.exception.SystemException e) {
            e.printStackTrace();
        } catch (com.bdx.rainbow.common.exception.BusinessException e) {
            e.printStackTrace();
        }


        return new ModelAndView("/sys/dept/deptEdit");
    }

    @RequestMapping(value = "/dept/create", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> create(HttpServletRequest request, PageInfo pageinfo, SysDept newDept) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {

            if (deptService.getDepts(newDept, pageinfo.getPageStart(), pageinfo.getPageCount()).size() != 0) {
                result.put("success", false);
                result.put("message", "该部门已存在");
            }
            SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);
            newDept.setCreater(userInfo.getUserId());
            newDept.setDeptStatus("1");

            deptService.insertDept(newDept);
            SysDept dept = deptService.getDepts(newDept, null, null).get(0);
            SysUser newuser = new SysUser();
            newuser.setUserName(dept.getAdmin());
            SysUser user = userService.getUsers(newuser, null, null).get(0);
            user.setDeptId(dept.getDeptId());
            userService.updateUser(user);

            result.put("success", true);
            result.put("message", "添加成功");
        } catch (Exception e) {
            log.error("新增部门异常", e.fillInStackTrace());
            result.put("success", false);
            result.put("message", "系统异常，请重试！");
        }
        return result;
    }

    @RequestMapping(value = "/dept/getDeptPid")
    @ResponseBody
    public List<SysDept> getDeptPidByType(@RequestParam("deptType") String deptType, @RequestParam("admin") String admin) {
        SysDept obj = new SysDept();

        obj.setDeptType(deptType);
        obj.setDeptPid(0);
        obj.setAdmin(admin);
        try {
            return deptService.getDepts(obj, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/dept/update", method = RequestMethod.GET)
    public ModelAndView updateForm(@RequestParam("deptId") Integer deptId, Model model, HttpServletRequest request) throws SystemException, BusinessException, com.bdx.rainbow.common.exception.SystemException, com.bdx.rainbow.common.exception.BusinessException {
        model.addAttribute("dept", deptService.getDeptById(deptId));
        model.addAttribute("action", "更新部门");
        model.addAttribute("nextAction", "update");

        return new ModelAndView("/sys/dept/deptEdit");
    }

    @RequestMapping(value = "/dept/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(SysDept dept, HttpServletRequest request) throws SystemException, BusinessException {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);
        try {
            dept.setUpdater(userInfo.getUserId());
            deptService.updateDept(dept);
            result.put("message", "修改部门成功");
        } catch (Exception e) {
            log.error("修改部门异常", e.fillInStackTrace());
            result.put("success", false);
            result.put("message", "系统异常，请重试！");
        }
        return result;
    }

    @RequestMapping(value = "/dept/changeStatus")
    @ResponseBody
    public Map<String, Object> changeStatus(@RequestParam("deptIds") List<Integer> deptIds, @RequestParam("deptStatus") String deptStatus, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);
            Integer userId = userInfo.getUserId();
            deptService.updateDeptStatusByDeptIds(deptIds, deptStatus, userId);
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
