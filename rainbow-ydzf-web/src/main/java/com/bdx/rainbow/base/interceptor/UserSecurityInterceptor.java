package com.bdx.rainbow.base.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bdx.rainbow.base.annotation.Security;
import com.bdx.rainbow.base.contextHolder.SpringContextHolder;
import com.bdx.rainbow.base.security.PermissionCheckHandler;
public class UserSecurityInterceptor implements HandlerInterceptor {
	private static final Logger log = LoggerFactory
			.getLogger(UserSecurityInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
            Security security = ((HandlerMethod) handler).getMethodAnnotation(Security.class);
            //没有声明需要权限,或者声明不验证权限
                if(security == null || security.mustLogin()==false)
                {
                	   return true;	
                }
            else{     
            	PermissionCheckHandler permissionCheckHandler=SpringContextHolder.getBean("basicAuthorCheckHandler") ;
            	return permissionCheckHandler.isPermission(security);
            	
            }
        }
        else
            return true;   
     }
        
        
        
        
	

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}






	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}





}