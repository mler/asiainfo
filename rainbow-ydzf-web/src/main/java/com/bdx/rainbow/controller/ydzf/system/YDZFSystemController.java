package com.bdx.rainbow.controller.ydzf.system;

import com.bdx.rainbow.base.annotation.Security;
import com.bdx.rainbow.base.request.SessionUtils;
import com.bdx.rainbow.controller.core.BaseController;
import com.bdx.rainbow.urs.entity.SysMenuTree;
import com.bdx.rainbow.urs.entity.SysUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by core on 16/4/12.
 */
@Controller
@RequestMapping("/sys")
public class YDZFSystemController extends BaseController {
    private static final Logger log = LoggerFactory
            .getLogger(YDZFSystemController.class);

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    @Security()
    public ModelAndView main(Model model, HttpServletRequest request) {
        log.info("============= INDEX ==============");
        try {
            SysUserInfo user = (SysUserInfo) SessionUtils.getUserSession();
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
        return new ModelAndView("index");
    }
}
