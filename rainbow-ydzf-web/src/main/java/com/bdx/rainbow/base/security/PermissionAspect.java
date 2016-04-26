package com.bdx.rainbow.base.security;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bdx.rainbow.base.annotation.Security;
import com.bdx.rainbow.base.contextHolder.SpringContextHolder;


@Component
@Aspect
public class PermissionAspect {
	private List<SecurityCheckHandler> checkHandlers;
    @Before(value = "com.bdx.rainbow.base.security.SystemArchitecture.userAccess()&&"
		+ "@annotation(security)",argNames = "security")
	public void checkPermission(Security security)
			throws Exception {
       	PermissionCheckHandler permissionCheckHandler=SpringContextHolder.getBean("basicAuthorCheckHandler") ;
    	 permissionCheckHandler.isPermission(security);
    	 return;
	}


	

}