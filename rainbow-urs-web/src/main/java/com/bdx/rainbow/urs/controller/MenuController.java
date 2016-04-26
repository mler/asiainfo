package com.bdx.rainbow.urs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bdx.rainbow.entity.urs.SysPlatfrom;
import com.bdx.rainbow.urs.common.Constants;
import com.bdx.rainbow.urs.entity.SysUserInfo;
import com.bdx.rainbow.urs.service.IPlantService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bdx.rainbow.common.BusinessException;
import com.bdx.rainbow.common.PageInfo;
import com.bdx.rainbow.common.SystemException;
import com.bdx.rainbow.common.tree.DefaultTree;
import com.bdx.rainbow.entity.urs.IUserInfo;
import com.bdx.rainbow.entity.urs.SysMenu;
import com.bdx.rainbow.entity.urs.SysMenuTree;
import com.bdx.rainbow.urs.service.IMenuService;

@Controller
@RequestMapping("/sys")
public class MenuController {
	private static Logger log = LoggerFactory.getLogger(MenuController.class);	

	
	@Autowired
	private IMenuService menuService;
    @Autowired
    private IPlantService plantService;
    @Autowired
    private ISysViewService viewService;
	/**
	 * 根据操作员归属组织ID，构建树形结构
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/menu/getMenuTree" }, method = { RequestMethod.GET })
	public ModelAndView getMenuTree(Model model, ServletRequest request)
			throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		String menu_id = request.getParameter("cur_menuId");
		condition.put("menuStatus", "1");		
		DefaultTree<SysMenuTree> tree = menuService.getSysMenuTree(condition);
		List<DefaultTree<SysMenuTree>> s = new ArrayList<DefaultTree<SysMenuTree>>();
		tree.getNodes(s, "-1");
		for(int i=0;i<s.size();i++){
		  DefaultTree<SysMenuTree> menutree = s.get(i);
		  if(menutree.getNodeId().equals("-1")){
			  s.remove(i);
		  }
		}
		model.addAttribute("menuTree", s);
		model.addAttribute("cur_menuId", menu_id);
		return new ModelAndView("/sys/menuTree");
	}
	
	/**
	 * 根据菜单ID，构建树形结构
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/menu/getSysMenuTree" }, method = { RequestMethod.GET })
	public ModelAndView getSysMenuTree(Model model, @RequestParam(value = "nodeId", defaultValue = "0") String nodeId, ServletRequest request)
			throws Exception {		
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String cur_menuId = request.getParameter("cur_menuId");
		String cur_menuType = request.getParameter("cur_menuType");
		condition.put("menuStatus", "1");
		DefaultTree<SysMenuTree> tree = menuService.getSysMenuTree(condition);
		List<DefaultTree<SysMenuTree>> s = new ArrayList<DefaultTree<SysMenuTree>>();
		tree.getNodes(s, "0");
		model.addAttribute("roleTree", s);
		model.addAttribute("cur_menuId", cur_menuId);
		model.addAttribute("cur_menuType", cur_menuType);
		
		return new ModelAndView("/sys/menuTree");
	}
	

	/**
	 * 菜单初始化
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/menuInit" }, method = { RequestMethod.GET })
	public ModelAndView initMenu(Model model)
			throws Exception {
		
//		List<ViewCache> sysIds = viewCacheService.findCacheByKey("SYSIDS");		
//		model.addAttribute("SYSIDS", sysIds);
		
		return new ModelAndView("/sys/menuInit");
	}
	/**
	 * 分页查询组织机构信息
	 * @param model
	 * @param pageinfo
	 * @param menu
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/menu/list")
	public ModelAndView menuList(Model model, HttpServletResponse response,PageInfo pageinfo, SysMenu menu) throws SystemException, BusinessException {
		log.info("-------------------------SYS_MENU 分页查询----------------------------");
		try {
			List<SysMenu> menus = menuService.getMenus(menu, pageinfo.getPageStart(), pageinfo.getPageCount());
			int total = menuService.countMenu(menu);
		    pageinfo.setTotalCount(total);
		    model.addAttribute("pageinfo", pageinfo);
		    model.addAttribute("menus", menus);
		}catch (Exception e) {
			e.getStackTrace();
			log.error(e.getMessage());
		}
		return new ModelAndView("/sys/menuList");
	}
	
	/**
	 * 创建菜单初始界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/menu/create", method = RequestMethod.GET)
	public ModelAndView createForm(Model model,HttpServletRequest request) {
		model.addAttribute("action", "新增菜单");
		model.addAttribute("nextAction", "create");
        SysUserInfo user=(SysUserInfo)request.getSession().getAttribute(Constants.SESSION_FRAME);
        Map<String, Object> condition = new HashMap<String, Object>();
//        condition.put("userId", user.getUser().getUserId());
        condition.put("platStatus",Constants.PLAT_STATUS_NORMAL_1);
        List<SysPlatfrom> plats=plantService.getPlatsByUserId(condition);
        model.addAttribute("plats",plats);
        model.addAttribute("icons", viewService.findCacheByKey("MENU_ICON"));
//	    model.addAttribute("status", viewCacheService.findCacheByKey("MENU_STATUS"));
//	    model.addAttribute("systags", viewCacheService.findCacheByKey("SYS_TAG"));
		return new ModelAndView("/sys/menuEdit");
	}
	
	/**
	 * 新建保存菜单信息
	 * @param menu
	 * @param redirectAttributes
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 * @throws com.bdx.rainbow.common.exception.SystemException 
	 * @throws com.bdx.rainbow.common.exception.BusinessException
     */
    @RequestMapping(value = "/menu/create", method = RequestMethod.POST)
    @ResponseBody
	public Map<String, Object> create(SysMenu menu, RedirectAttributes redirectAttributes,HttpServletRequest request)  {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        SysUserInfo userInfo = ( SysUserInfo)request.getSession().getAttribute(Constants.SESSION_FRAME);
		
		menu.setCreater(userInfo.getUserId());
		menu.setMenuStatus("1");
        try {
            menuService.insertMenu(menu);
            result.put("message","添加成功");
        } catch (Exception e){
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "系统异常，请重试！");
        }


        return result;
	}
	
	/**
	 * 更新菜单，被更新菜单信息获取
	 * @param menuId
	 * @param model
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 * @throws com.bdx.rainbow.common.exception.SystemException 
	 * @throws com.bdx.rainbow.common.exception.BusinessException 
	 */
	@RequestMapping(value = "/menu/update", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("menuId") Integer menuId, Model model, HttpServletRequest request) throws SystemException, BusinessException, com.bdx.rainbow.common.exception.SystemException, com.bdx.rainbow.common.exception.BusinessException {
		model.addAttribute("menu", menuService.getMenuByKey(menuId));
		model.addAttribute("action", "更新菜单");
		model.addAttribute("nextAction", "update");
        SysUserInfo user=(SysUserInfo)request.getSession().getAttribute(Constants.SESSION_FRAME);
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userId", user.getUser().getUserId());
        condition.put("platStatus",Constants.PLAT_STATUS_NORMAL_1);
        List<SysPlatfrom> plats=plantService.getPlatsByUserId(condition);
        model.addAttribute("plats",plats);
        model.addAttribute("icons",viewService.findCacheByKey("MENU_ICON"));
//	    model.addAttribute("status", viewCacheService.findCacheByKey("MENU_STATUS"));
//	    model.addAttribute("systags", viewCacheService.findCacheByKey("SYS_TAG"));
		return new ModelAndView("/sys/menuEdit");
	}
	
	/**
	 * 保存更新菜单信息
	 * @param menu
	 * @param redirectAttributes
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 * @throws com.bdx.rainbow.common.exception.SystemException 
	 * @throws com.bdx.rainbow.common.exception.BusinessException 
	 */
	@RequestMapping(value = "/menu/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(SysMenu menu, RedirectAttributes redirectAttributes, HttpServletRequest request)  {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        SysUserInfo userInfo = ( SysUserInfo)request.getSession().getAttribute(Constants.SESSION_FRAME);

		menu.setUpdater(userInfo.getUserId());
        try {
            menuService.updateMenu(menu);
            result.put("message","编辑成功");
        }catch (Exception e){
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "系统异常，请重试！");
        }
		return result;
	}

	/**
	 * 菜单状态变更 	1 有效 2 无效
	 * @param menuIds
	 * @param menuStatus
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/menu/changeStatus")
	@ResponseBody
	public Map<String, Object> changeStatus(@RequestParam("menuIds") List<Integer> menuIds, @RequestParam("menuStatus") String menuStatus, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();	
		result.put("success", true);
		try {
            SysUserInfo userInfo = ( SysUserInfo)request.getSession().getAttribute(Constants.SESSION_FRAME);
//			IUserinfo userInfo = SessionUtil.getUserSession(Constants.SESSION_FRAME);

			Integer userId = userInfo.getUserId();
			menuService.updateMenuStatusByMenuIds(menuIds, menuStatus, userId);
            result.put("message","操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "系统异常，请重试！");
		} 
		return result;
	}
    @RequestMapping(value = "/menu/getMenuPid")
    @ResponseBody
    public List<SysMenu> getMenuPidByPlat(Integer platId){
        SysMenu obj=new SysMenu();
        obj.setPlatId(platId);
        obj.setMenuStatus(Constants.MENU_STATUS_NORMAL_1);
        obj.setMenuPid(0);
        try {
            return menuService.getMenus(obj,null,null);
        } catch (com.bdx.rainbow.common.exception.SystemException e) {
            e.printStackTrace();
        } catch (com.bdx.rainbow.common.exception.BusinessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
