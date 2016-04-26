package com.bdx.rainbow.spsy.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bdx.rainbow.common.SysContants;
import com.bdx.rainbow.spsy.exceptions.AuthorException;
import com.bdx.rainbow.urs.entity.IUserInfo;

public class SessionCheckInterceptor extends HandlerInterceptorAdapter {
	protected List<String> excludeURLs;
//	private static final Logger logger=LoggerFactory.getLogger(SessionCheckInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		String url=request.getRequestURI();
		if(this.excludeURLs != null) {
			for(String excludeUrl:this.excludeURLs) {
				if(url.matches(excludeUrl)) {
					return true;
				}
			}
		}
		if(!hasLogin(request)){
			throw new AuthorException();
		}
		
		return super.preHandle(request, response, handler);
	}
	
	public List<String> getExcludeURLs() {
		return excludeURLs;
	}
	
	public void setExcludeURLs(List<String> excludeURLs) {
		this.excludeURLs = excludeURLs;
	}

	public boolean hasLogin(HttpServletRequest request) {
		return request.getSession().getAttribute(SysContants.SESSION_USER_INFO_KEY)!=null;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
//		RequestContext.clear();//请求结束后clear掉
	}
}
