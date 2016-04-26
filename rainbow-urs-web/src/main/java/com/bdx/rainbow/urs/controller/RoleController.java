package com.bdx.rainbow.urs.controller;

import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.urs.*;
import com.bdx.rainbow.urs.common.Constants;
import com.bdx.rainbow.urs.entity.SysUserInfo;
import com.bdx.rainbow.urs.service.IMenuService;
import com.bdx.rainbow.urs.service.IPlantService;
import com.bdx.rainbow.urs.service.IRoleService;
import com.bdx.rainbow.urs.service.ISysViewService;
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

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by core on 16/2/2.
 */
@Controller
@RequestMapping("/sys")
public class RoleController {
    private static Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private IPlantService plantService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private ISysViewService viewCacheService;
    @Autowired
    private IMenuService menuService;

    /**
     * 管理权限初始化
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/roleInit"}, method = {RequestMethod.GET})
    public ModelAndView initRole(Model model)
            throws Exception {
        return new ModelAndView("/sys/roleInit");
    }

    /**
     * 分页查询管理权限信息
     *
     * @param model
     * @param pageinfo
     * @param role
     * @return
     */
    @RequestMapping(value = "/role/list")
    public ModelAndView roleList(Model model, PageInfo pageinfo, SysRole role) {
        log.info("-------------------------SYS_ROLE 分页查询----------------------------");
        try {
            List<SysRole> roles = roleService.getRoles(role, pageinfo.getPageStart(), pageinfo.getPageCount());
//            List<SysViews> roleNames = viewCacheService.findCacheByKey("SYS_ROLE");
            int total = roleService.countRole(role);
            pageinfo.setTotalCount(total);
            model.addAttribute("pageinfo", pageinfo);
            model.addAttribute("objs", roles);
//            model.addAttribute("ROLENAMES", roleNames);

        } catch (Exception e) {
            e.getStackTrace();
            log.error(e.getMessage());
        }
        return new ModelAndView("/sys/roleList");
    }

    /**
     * 创建管理权限初始界面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/role/create", method = RequestMethod.GET)
    public ModelAndView createForm(Model model, HttpServletRequest request) {
        model.addAttribute("action", "新增管理权限");
        model.addAttribute("nextAction", "create");
        SysUserInfo user = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("platStatus", Constants.PLAT_STATUS_NORMAL_1);
        List<SysPlatfrom> plats = plantService.getPlatsByUserId(condition);
        model.addAttribute("plats", plats);
        return new ModelAndView("/sys/roleEdit");
    }

    /**
     * 创建管理权限信息
     *
     * @param newRole
     * @param request
     * @return
     */
    @RequestMapping(value = "/role/create", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> create(SysRole newRole, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<String, Object>();

        SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);
        try {
            newRole.setCreater(userInfo.getUserId());
            if (Constants.USER_ADMIN_FLAG.equals(userInfo.getUser().getAdminUser())) {
                newRole.setAdminUser(userInfo.getUser().getUserName());
            } else {
                newRole.setAdminUser(userInfo.getUser().getAdminUser());
            }
            roleService.insertRole(newRole);
            result.put("success", true);
            result.put("message", "添加成功");
        } catch (Exception e) {
            log.error("新增角色异常", e.fillInStackTrace());
            result.put("success", false);
            result.put("message", "系统异常，请重试！");
        }
        return result;
    }

    /**
     * 获取更新的数据信息
     *
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping(value = "/role/update", method = RequestMethod.GET)
    public ModelAndView updateForm(@RequestParam("roleId") Integer roleId, Model model) {
        try {
            model.addAttribute("role", roleService.getRoleByKey(roleId));
            model.addAttribute("action", "更新用户权限");
            model.addAttribute("nextAction", "update");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/sys/roleEdit");
    }

    /**
     * 更新用户权限信息
     *
     * @param role
     * @param request
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(SysRole role, HttpServletRequest request) throws SystemException, BusinessException {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);
        try {
            role.setUpdater(userInfo.getUserId());
            roleService.updateRole(role);
            result.put("message", "修改角色成功");
        } catch (Exception e) {
            log.error("修改菜单异常", e.fillInStackTrace());
            result.put("success", false);
            result.put("message", "系统异常，请重试！");
        }
        return result;
    }

    @RequestMapping(value = "/role/getRolePid")
    @ResponseBody
    public List<SysRole> getRolePidByPlat(Integer platId) {
        SysRole obj = new SysRole();
        obj.setPlatId(platId);
        obj.setRolePid(0);

        try {
            return roleService.getRoles(obj, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/role/roleToMenu")
    public ModelAndView roleMenu(Model model, ServletRequest request) throws SystemException, BusinessException {
        String role_id = request.getParameter("roleId");
        String flag = request.getParameter("operflag");
        String menuName = request.getParameter("menuName");

        SysMenu sysmenu = new SysMenu();
        sysmenu.setMenuStatus("1");
        sysmenu.setPlatId(roleService.getRoleByKey(Integer.valueOf(role_id)).getPlatId());
        List<SysMenu> menus = menuService.getMenus(sysmenu, null, null);
        Map<Object, List<SysMenu>> menutree = new HashMap<Object, List<SysMenu>>();
        for (int i = 0; i < menus.size(); i++) {
            SysMenu menu = menus.get(i);
            if (menutree.get(menu.getMenuPid()) != null) {
                List<SysMenu> menub = menutree.get(menu.getMenuPid());
                menub.add(menu);
                menutree.put(menu.getMenuPid(), menub);
            } else {
                List<SysMenu> menusdetail = new ArrayList<SysMenu>();
                menusdetail.add(menu);
                menutree.put(menu.getMenuPid(), menusdetail);
            }
        }

        List<SysRole2Menu> role2list = roleService.getRole2Menus(role_id);
        Map<Object, Integer> role2menus = new HashMap<Object, Integer>();
        for (int j = 0; j < role2list.size(); j++) {
            SysRole2Menu rol2 = role2list.get(j);
            if (role2menus.get(rol2.getMenuId()) == null) {
                role2menus.put(rol2.getMenuId(), rol2.getMenuId());
            }
        }
        model.addAttribute("selflag", "0");
        model.addAttribute("role2menus", role2menus);
        model.addAttribute("roleId", role_id);
        model.addAttribute("menus", menutree);
        model.addAttribute("operflag", flag);
        return new ModelAndView("/sys/roleToMenu");
    }

    @RequestMapping(value = "/role/saveRole2", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveRole2Menu(Model model, ServletRequest request) {
        Map<String, Object> jo = new HashMap<String, Object>();
        jo.put("success", true);
        try {
            String roleid = request.getParameter("roleId");
            String menusd = request.getParameter("menus");
            String menuexist = request.getParameter("menuexist");
            String menus = "";
            if (menuexist != null && !menuexist.equals("")) {
                String[] menuex = menuexist.split(",");
                String[] menun = menusd.split(",");
                for (int i = 0; i < menun.length; i++) {
                    String flag = "0";
                    for (int j = 0; j < menuex.length; j++) {
                        if (menun[i].equals(menuex[j])) {
                            flag = "1";
                        }
                    }
                    if (flag.equals("0")) {
                        menus = menus + menun[i] + ",";
                    }
                }
            }
            if (menus.equals("")) {
                menus = menusd;
            }
            roleService.insertRoleMenuRe(roleid, menus);
            jo.put("message", "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            jo.put("success", false);
            jo.put("message", "保存异常，请重试");
        }
        return jo;
    }
}

