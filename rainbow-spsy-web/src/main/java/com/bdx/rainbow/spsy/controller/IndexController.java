package com.bdx.rainbow.spsy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bdx.rainbow.common.SysContants;
import com.bdx.rainbow.spsy.web.DefaultTree;
import com.bdx.rainbow.spsy.web.ITree;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.urs.entity.SysMenuTree;
import com.bdx.rainbow.urs.entity.SysUserInfo;

@Controller
@RequestMapping("/")
public class IndexController {
	private final static Logger logger=LoggerFactory.getLogger(IndexController.class);
	
	@RequestMapping(value = {"/index"}, method = RequestMethod.GET)
	public ModelAndView index(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("登录后的首页。。。");
		//用户姓名获取
		IUserInfo sysUserInfo = (SysUserInfo) request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY);
		model.addAttribute("userName", String.format("%s(%s)", sysUserInfo.getUser().getUserName(), sysUserInfo.getUserName()));
		//获取菜单
		DefaultTree<SysMenuTree> s = this.getSysMenuTree(sysUserInfo.getuMenus(), "0");
		model.addAttribute("PNodes", s.getChildren());
		List<ITree<SysMenuTree>> cs = s.getChildren();
		int size = cs.size();
		List<ITree<SysMenuTree>> SNodes = new ArrayList<ITree<SysMenuTree>>();
		for (int i = 0; i < size; i++) {
			ITree<SysMenuTree> c = cs.get(i);
			DefaultTree<SysMenuTree> sd = this.getSysMenuTree(sysUserInfo.getuMenus(), c.getNodeId());
			if (sd != null && sd.getChildren() != null)
				SNodes.addAll(sd.getChildren());
		}
		model.addAttribute("SNodes", SNodes);
		return new ModelAndView("/index");
	}
	
	public DefaultTree<SysMenuTree> getSysMenuTree(List<SysMenuTree> menus, String nodeId) throws Exception {
		DefaultTree<SysMenuTree> tree = new DefaultTree<SysMenuTree>(nodeId, menus);
		return	tree;
	}
}
