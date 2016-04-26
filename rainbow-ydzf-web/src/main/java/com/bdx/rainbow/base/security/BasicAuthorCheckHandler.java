package com.bdx.rainbow.base.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bdx.rainbow.base.annotation.Security;
import com.bdx.rainbow.base.request.SessionUtils;
import com.bdx.rainbow.base.request.WEBConstants;
import com.bdx.rainbow.common.bean.ResultBean;
import com.bdx.rainbow.common.exception.ExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.controller.core.BaseController;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(value = "basicAuthorCheckHandler")
public class BasicAuthorCheckHandler implements SecurityCheckHandler {
	private static final Logger log = LoggerFactory
			.getLogger(BasicAuthorCheckHandler.class);

	private int order = Integer.MIN_VALUE;

	@Override
	public boolean isPermission(Security security) throws SystemException {
		if (security != null && security.mustLogin()) {
			return checkLogin(security);
		}
		return true;
	}

	protected boolean checkLogin(Security security) throws SystemException{
		HttpServletRequest request = SessionUtils.getRequest();
		HttpServletResponse resp =SessionUtils.getResponse();
		// 验证用户是否登陆
		Object obj =SessionUtils.getUserSession();
		if (obj == null) {
			if (security.returnType().equals("page")) {
				try {
					resp.sendRedirect(request.getContextPath() + "/login");
				} catch (IOException e) {
					log.error("sendRedirect error");
					throw new SystemException(ExceptionCode.EX_CORE_ISPERMISSIONERROR,e);
				} 
					return false;
				
			} else {
				BaseController base= new BaseController();
				BaseController.JsonObject jsonObject = base.new JsonObject(false,"未登录"); 
				try {
					resp.setCharacterEncoding("UTF-8");
					resp.setContentType("text/html;charset=UTF-8"); 
					resp.getWriter().write(jsonObject.toJsonString());
				} catch (IOException e) {
					if (e instanceof JsonProcessingException) {
						log.error("resultBean转json error");
					}
					else
					{
						log.error("resp.getWriter().write error");
					}
					throw new SystemException(ExceptionCode.EX_CORE_ISPERMISSIONERROR,e);
				}
				return false;
			}
		} else {
			return true;
		}
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
