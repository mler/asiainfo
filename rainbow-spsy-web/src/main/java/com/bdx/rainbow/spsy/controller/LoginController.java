package com.bdx.rainbow.spsy.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bdx.rainbow.common.SysContants;
import com.bdx.rainbow.spsy.bean.EnterpriseInfo;
import com.bdx.rainbow.spsy.common.SpsyConstants;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginAgencyCompany;
import com.bdx.rainbow.spsy.service.IAgencyCompanyService;
import com.bdx.rainbow.spsy.service.IEnterpriseInfoService;
import com.bdx.rainbow.urs.dubbo.IDubUserService;
import com.bdx.rainbow.urs.entity.IUserInfo;

@Controller
@RequestMapping("/")
public class LoginController {
	private final static Logger logger=LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private IDubUserService userService;
	
	@Autowired
    private  IEnterpriseInfoService enterpriseInfoService;
	
	@Autowired
	private IAgencyCompanyService agencyCompanyService;
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public ModelAndView login(Model model)throws Exception{
		logger.info("登录初始页。。。");
		return new ModelAndView("/login");
    }
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.POST)
	public ModelAndView doLogin(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info("登录。。。。");
		ModelAndView modelAndview = new ModelAndView();
		String username = request.getParameter("userName");
		String password = request.getParameter("password");
		try{
			this.doLogin(username, password, false, request, response);
			modelAndview.setViewName("redirect:/index.do");
		}catch(Exception e){
			e.printStackTrace();
			modelAndview.addObject("errmsg", e.getMessage());
			modelAndview.setViewName("/login");
		}
		return modelAndview;		
	}

	@RequestMapping(value = {"/poplogin"}, method = RequestMethod.POST)
	public ModelAndView poplogin(Model model,HttpServletRequest request)throws Exception{
		logger.info("登录初始页。。。");
		String userName = request.getParameter("userName");
		model.addAttribute("userName", userName);
		model.addAttribute("pwd", "123456");
		return new ModelAndView("/login");
    }
	
    public void doLogin(String username, String pwd, boolean pwdEncoded, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String realIP = request.getHeader("x-forwarded-for");// 代理服务器会在转发时将头信息放在头信息中
        logger.debug(request.getRemoteHost());
        if (realIP != null && realIP.length() != 0) {
            while ((realIP != null) && (realIP.equals("unknown"))) {// 如果有x-forwarded-for信息,并且等于unknown则继续读
                realIP = request.getHeader("x-forwarded-for");
            }
        }
        if (realIP == null || realIP.length() == 0) {
            realIP = request.getHeader("Proxy-Client-IP");
        }
        if (realIP == null || realIP.length() == 0) {
            realIP = request.getHeader("WL-Proxy-Client-IP");
        }
        if (realIP == null || realIP.length() == 0) {
            realIP = request.getRemoteAddr();
        }
        Map<String, Object> sysLogon = new HashMap<String, Object>();

        try {
            InetAddress serverIp = InetAddress.getLocalHost();
            sysLogon.put(SysContants.SYS_LOGON_SERVERIP, serverIp);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
//
        sysLogon.put(SysContants.SYS_LOGON_LOGONIP, realIP);
        sysLogon.put(SysContants.SYS_LOGON_SYS, "spsy");
    	IUserInfo userInfo = userService.login(username, pwd, SpsyConstants.PLAT_ID_SOURCE,sysLogon);
    	request.getSession().setAttribute(SysContants.SESSION_USER_INFO_KEY, userInfo);
    	if(userInfo!=null){
	    	int companyId = userInfo.getUser().getCorpId();
	    	if(companyId!=0){
	    		TOriginAgencyCompany agency = agencyCompanyService.getAgencyFromEnterprise(companyId);
	    		if(agency!=null){
	    			request.getSession().setAttribute("agencyId", agency.getAgencyId());
	    		}
		    	request.getSession().setAttribute("enterpriseName", agency.getAgencyName());
	    	}
	    }
    }
	
	@RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
	public ModelAndView doLogout(HttpServletRequest request,HttpServletResponse response,Model model) {
		logger.info("退出登陆。。。。");
		request.getSession().setAttribute(SysContants.SESSION_USER_INFO_KEY, null);
		request.getSession().invalidate();
		return new ModelAndView("redirect:/login.do");
	}
}
