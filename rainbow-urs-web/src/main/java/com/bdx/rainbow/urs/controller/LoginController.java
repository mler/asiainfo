package com.bdx.rainbow.urs.controller;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.urs.common.Constants;
import com.bdx.rainbow.urs.entity.SysUser;
import com.bdx.rainbow.urs.entity.SysUserInfo;
import com.bdx.rainbow.urs.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by core on 16/2/18.
 */
@Controller
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private IUserService userService;
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView goLoginView(Model model,HttpServletRequest request) {

        HttpSession session2 = request.getSession();
        log.debug("-------session is new : "+session2.isNew()+" : "+new Timestamp(session2.getCreationTime()));

        if (log.isDebugEnabled()) {
            log.debug("welcome to logon web");
        }
        SysUserInfo userInfo =(SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);
        if (userInfo != null) {
            SysUser sysUser = userInfo.getUser();
            request.getSession().setAttribute("userName", sysUser.getUserLoginName());
            return new ModelAndView("redirect:/sys/main");
        }
        model.addAttribute("title", "欢迎登录");
        return new ModelAndView("/sys/login");
    }
    @RequestMapping("/")
    public String index() {
        return "redirect:/sys/login";
    }
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        if (log.isDebugEnabled()) {
            log.debug("logon start");
        }

        String userName = request.getParameter("userName");
        String pwd = request.getParameter("pwd");
        Map<String, Object> sysLogon = new HashMap<>(7);
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-real-ip");
        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        sysLogon.put("logonIp", ip);
        try {
            SysUserInfo user=(SysUserInfo)userService.login(userName, pwd, 1, sysLogon);
            request.getSession().setAttribute(Constants.SESSION_FRAME, user);
            return result;
        } catch (BusinessException e) {
            result.put("success", false);
            result.put("message", e.getErrorMsg());
            return result;
        } catch (SystemException e) {
            result.put("success", false);
            result.put("message", e.getErrorMsg());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "系统错误，请联系管理员");
            return result;
        }

    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        try {
            if (null != request.getSession().getAttribute(Constants.SESSION_FRAME)) {
                request.getSession().removeAttribute(Constants.SESSION_FRAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }
}
