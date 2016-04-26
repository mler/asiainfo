package com.bdx.rainbow.controller.ydzf.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bdx.rainbow.common.configuration.Constants;
import com.bdx.rainbow.urs.entity.SysUser;
import com.bdx.rainbow.urs.entity.SysUserInfo;
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

import com.bdx.rainbow.base.request.SessionUtils;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.controller.core.BaseController;
import com.bdx.rainbow.service.ydzf.system.IYDZFLoginService;
import com.bdx.rainbow.urs.entity.IUserInfo;

import java.sql.Timestamp;

@Controller
public class YDZFLoginController extends BaseController {
	private static final Logger log = LoggerFactory
			.getLogger(YDZFLoginController.class);

	@Autowired
	private IYDZFLoginService ydzfLoginService;


	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(Model model,HttpServletRequest request)throws Exception{
        HttpSession session2 = request.getSession();
        log.debug("-------session is new : "+session2.isNew()+" : "+new Timestamp(session2.getCreationTime()));

        if (log.isDebugEnabled()) {
            log.debug("welcome to logon web");
        }
        SysUserInfo userInfo =SessionUtils.getUserSession();
        if (userInfo != null) {
            return new ModelAndView("redirect:/sys/main");
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    @ResponseBody
	public JsonObject login(@RequestParam("loginName") String loginName,
			@RequestParam("pwd") String pwd, Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
            SysUserInfo userInfo = (SysUserInfo)ydzfLoginService.login(loginName, pwd, null);
			if (userInfo != null) {
				SessionUtils.setUserSession(userInfo);
				return new JsonObject(true, "SUC");
			} else {
				return new JsonObject(false, "登录失败");
			}
		} catch (com.bdx.rainbow.urs.exception.BusinessException e) {
			return new JsonObject(false, e.getErrorCode(), e.getErrorMsg());
		} catch (Exception e) {
			log.error("login登录异常：", e);
			return new JsonObject(false, "系统繁忙");
		}

	}
    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public ModelAndView doLogout(HttpServletRequest request,HttpServletResponse response,Model model) {
        log.info("退出登陆。。。。");
        SessionUtils.setUserSession(null);
        return new ModelAndView("redirect:/login");
    }
}
