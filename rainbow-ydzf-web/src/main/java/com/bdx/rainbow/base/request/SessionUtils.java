package com.bdx.rainbow.base.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * MultipartHttpServletRequest 不适用
 * 
 * @author fox
 *
 */
public class SessionUtils {

	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getResponse();
	}

	public static HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();

	}

	@SuppressWarnings("unchecked")
	public static <T> T getAttrSession(String attrName) {
		return (T) getSession().getAttribute(attrName);
	}

	public static <T> void setAttrSession(String attrName, T t) {
		getSession().setAttribute(attrName, t);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getUserSession() {
		return (T) getSession().getAttribute(WEBConstants.WEB_SESSION_USER);
	}

	public static <T> void setUserSession(T t) {
		getSession().setAttribute(WEBConstants.WEB_SESSION_USER, t);
	}

}
