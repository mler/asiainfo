package com.bdx.rainbow.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bdx.rainbow.base.security.PermissionCheckHandler;



@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Security {

	/**
	 * 是否必须登录,默认必须登录
	 */
	boolean mustLogin() default true;
	
	
	/**
	 * 用户权限处理类
	 * 可以是字符串或者类型
	 * order 4
	 */
	Class<? extends PermissionCheckHandler> authorCheckType() default PermissionCheckHandler.class;

	/**
	 * 返回登录页面，返回json串
	 * {"page","json"}
	 * @return
	 */
	String returnType() default "page";

	/**
	 * 请求地址信息 请求说明：用于说明请求的URL的用途
	 */
	String comment() default "";

}
