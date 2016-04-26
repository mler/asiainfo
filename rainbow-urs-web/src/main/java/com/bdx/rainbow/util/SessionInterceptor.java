package com.bdx.rainbow.util;

import com.bdx.rainbow.urs.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by core on 16/2/24.
 */
public class SessionInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(SessionInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (null==httpServletRequest.getSession().getAttribute(Constants.SESSION_FRAME)){
                if (log.isInfoEnabled()) {
                    log.info("---Interceptor:no logon, redirct logon web---");
                }
                String requestType=httpServletRequest.getHeader("X-Requested-With");
                httpServletResponse.addHeader("P3P", "CP=CAO PSA OUR");
                StringBuffer sb = new StringBuffer();
                sb.append("http://").append(httpServletRequest.getServerName()).append(":").append(httpServletRequest.getServerPort()).append(httpServletRequest.getContextPath()).append("/login");
                if (requestType!=null&&requestType.equals("XMLHttpRequest")){
                    httpServletResponse.getWriter().print("<script language=\"JavaScript\"> window.location.href = '"+sb.toString()+"';</script>");
                }else{
                    httpServletResponse.sendRedirect(sb.toString());
                }

                return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
