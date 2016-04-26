package com.bdx.rainbow.urs.controller;

import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.Encrypt;
import com.bdx.rainbow.entity.urs.*;
import com.bdx.rainbow.entity.urs.SysDept;
import com.bdx.rainbow.entity.urs.SysRole;
import com.bdx.rainbow.entity.urs.SysUser;
import com.bdx.rainbow.urs.common.Constants;
import com.bdx.rainbow.urs.dubbo.IDubDeptService;
import com.bdx.rainbow.urs.dubbo.IDubDicService;
import com.bdx.rainbow.urs.dubbo.IDubUserService;
import com.bdx.rainbow.urs.entity.*;
import com.bdx.rainbow.urs.entity.SysMenuTree;
import com.bdx.rainbow.urs.service.IDeptService;
import com.bdx.rainbow.urs.service.IPlantService;
import com.bdx.rainbow.urs.service.IRoleService;
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

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private IDubUserService service;
    @Autowired
    private IDubDeptService deptService;
    @Autowired
    private IDeptService iDeptService;
    @Autowired
    private IDubDicService dicService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPlantService platService;

    @RequestMapping(value = {"/userInit"}, method = {RequestMethod.GET})
    public ModelAndView initUser() {
        return new ModelAndView("/sys/user/userInit");
    }

    @RequestMapping(value = {"/user/list"})
    public ModelAndView list(Model model, PageInfo pageinfo, SysUser user) throws BusinessException, SystemException {
        log.info("-------------------------SYS_PLAT 分页查询----------------------------");

        List<SysUser> users = userService.getUsers(user, pageinfo.getPageStart(), pageinfo.getPageCount());
        int total = userService.countUser(user);
        pageinfo.setTotalCount(total);
        model.addAttribute("pageinfo", pageinfo);
        model.addAttribute("users", users);
        return new ModelAndView("/sys/user/userList");


    }

    @RequestMapping(value = "/user/create", method = RequestMethod.GET)

    public ModelAndView createForm(Model model, SysUser user, SysDept dept) {

        try {
            List<SysDept> depts = iDeptService.getDepts(dept, null, null);
            model.addAttribute("depts", depts);
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        model.addAttribute("action", "新增平台");
        model.addAttribute("nextAction", "create");
        return new ModelAndView("/sys/user/userEdit");

    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> create(SysUser user, HttpServletRequest request, PageInfo pageinfo) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);

        try {
            if (userService.getUsers(user, pageinfo.getPageStart(), pageinfo.getPageCount()).size() != 0) {
                result.put("success", false);
                result.put("message", "该用户已存在");
            }
            user.setLoginPwd(Encrypt.encode(user.getLoginPwd(), Encrypt.MD5, false));
            SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);
            user.setCreater(userInfo.getUserId());
            user.setUserStatus("1");
            user.setCorpId(1);

            if (request.getParameter("userType").equals("0")) {
                user.setAdminUser("is_Admin");

            } else {
                user.setAdminUser(user.getUserName());
            }

            userService.insertUser(user);
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

    @RequestMapping(value = "/user/changeStatus")
    @ResponseBody
    public Map<String, Object> changeStatus(@RequestParam("userIds") List<Integer> userIds, @RequestParam("userStatus") String userStatus, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);
        Integer userId = userInfo.getUserId();
        try {
            userService.updateUserStatusByUserIds(userIds, userStatus, userId);
            result.put("success", true);
            result.put("message", "操作成功");
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return result;

    }

    @RequestMapping(value = {"/rest"}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String register(String userName, String loginName, String loginPwd) {
        try {
//            Map<String, Object> sysLogon=new HashMap<String, Object>();
//            sysLogon.put(Constants.SYS_LOGON_LOGONIP,"10.10.31.228");
//            SysUserInfo user=(SysUserInfo)service.login(loginName, loginPwd, 1, sysLogon);

            return dicService.findCacheByKey("USER_STATUS").toString();//service.getUsers(user.getUser(),1,5,user).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
//            return e.getErrorMsg();
//        }catch (BusinessException e1){
//            return e1.getErrorMsg();
//
//        }

    }

    @RequestMapping(value = "/user/update", method = RequestMethod.GET)
    public ModelAndView updateForm(Model model, @RequestParam("userId") Integer userId) throws BusinessException, SystemException {
        SysUser user = userService.getUserById(userId);

        model.addAttribute("user", user);
        model.addAttribute("action", "更新用户");
        model.addAttribute("nextAction", "update");
        return new ModelAndView("/sys/user/userEdit");
    }

    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(SysUser user, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);
        user.setUpdater(userInfo.getUserId());
        try {
            userService.updateUser(user);
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


    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView main(Model model, HttpServletRequest request) {
        log.info("============= INDEX ==============");
        try {
            SysUserInfo user = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);
//            if (null==user){
//                return new ModelAndView("redirect:/sys/login");
//            }
            List<SysMenuTree> menuTrees = user.getuMenus();
            Map<Object, List<SysMenuTree>> menuTree = new HashMap<Object, List<SysMenuTree>>();
            for (int i = 0; i < menuTrees.size(); i++) {
                SysMenuTree menu = menuTrees.get(i);
                if (menuTree.get(menu.getMenuPid()) != null) {
                    List<SysMenuTree> menub = menuTree.get(menu.getMenuPid());
                    menub.add(menu);
                    menuTree.put(menu.getMenuPid(), menub);
                } else {
                    List<SysMenuTree> menusdetail = new ArrayList<SysMenuTree>();
                    menusdetail.add(menu);
                    menuTree.put(menu.getMenuPid(), menusdetail);
                }
            }
            model.addAttribute("menuTree", menuTree);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("/sys/index");
    }


    @RequestMapping(value = "/user/userToPlat", method = RequestMethod.GET)
    public ModelAndView userPlat(Model model, ServletRequest request) throws SystemException, BusinessException {
        String userId = request.getParameter("userId");
        String flag = request.getParameter("operflag");
        SysPlatfrom plat = new SysPlatfrom();
        plat.setPlatStatus("1");//获取全部平台
        List<SysPlatfrom> plats = platService.getPlats(plat, null, null);


        //根据Userid获取对应User2plat对象
        Map<SysPlatfrom, List<SysRole>> roleMap = new HashMap<SysPlatfrom, List<SysRole>>();
        for (int i = 0; i < plats.size(); i++) {
            SysRole role = new SysRole();//根据平台id获取其所有角色 并将其放置于Map
            role.setPlatId(plats.get(i).getPlatId());//Map<platId,List<SysRole>>
            List<SysRole> roles = roleService.getRoles(role, null, null);
            roleMap.put(plats.get(i), roles);
        }


        List<SysUser2Plat> userPlatList = userService.getUser2Plats(userId);
        Map<Object, Integer> user2PlatsMap = new HashMap<Object, Integer>();
        for (int i = 0; i < userPlatList.size(); i++) {
            SysUser2Plat user2Plat = userPlatList.get(i);
            if (user2PlatsMap.get(user2Plat.getPlatId()) == null) {
                user2PlatsMap.put(user2Plat.getPlatId(), user2Plat.getPlatId());
            }

        }
        List<SysUser2Role> user2RoleList = userService.getUser2Roles(userId);
        Map<Object, Integer> user2RoleMap = new HashMap<Object, Integer>();

        for (int i = 0; i < user2RoleList.size(); i++) {
            SysUser2Role user2Role = user2RoleList.get(i);
            if (user2RoleMap.get(user2Role.getRoleId()) == null) {
                user2RoleMap.put(user2Role.getRoleId(), user2Role.getRoleId());
            }
        }
        model.addAttribute("user2RoleMap", user2RoleMap);
        model.addAttribute("roleMap", roleMap);
        model.addAttribute("user2PlatsMap", user2PlatsMap);
        model.addAttribute("selflag", "0");
        model.addAttribute("plats", plats);
        model.addAttribute("userId", userId);
        model.addAttribute("operflag", flag);


        return new ModelAndView("/sys/user/userToPlat");
    }

    @RequestMapping(value = "/user/saveUser2", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveRole2Menu(Model model, ServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        String userId = request.getParameter("userId");
        String[] toBedeletedIds = request.getParameter("toBedeletedIds").split(";");
        String[] toBeinsertdIds = request.getParameter("toBeinsertdIds").split(";");
        String[] oldroleIds = null;
        if (request.getParameter("oldroleIds") != null && request.getParameter("oldroleIds") != "") {
            oldroleIds = request.getParameter("oldroleIds").split(";");
        }


        String[] newroleIds = request.getParameter("newroleIds").split(";");
        try {
            userService.insertUser2Plat(userId, toBedeletedIds, toBeinsertdIds);
            userService.insertUser2Role(userId, oldroleIds, newroleIds);
            result.put("message", "操作成功");
        } catch (SystemException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "保存异常，请重试");
        } catch (BusinessException e) {
            e.printStackTrace();
        }


        return result;
    }
@RequestMapping(value = "/user/check", method = RequestMethod.GET)
@ResponseBody
     public Map<String,Object> checkUname(HttpServletRequest request)  {
         Map<String, Object> result = new HashMap<String, Object>();
         String username=request.getParameter("username");

    try {
        if(userService.getUserByName(username).size()!=0){

            result.put("success", false);
            result.put("message", "该用户名已存在");
            return result;
        }else{
            result.put("success", true);
            result.put("message", "该用户名可以使用");
            return result;
        }
    } catch (SystemException e) {
        e.printStackTrace();
        result.put("success", false);
        result.put("message", "保存异常，请重试");
    } catch (BusinessException e) {
        e.printStackTrace();
    }
    return result;
}
}