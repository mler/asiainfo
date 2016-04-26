package com.bdx.rainbow.etl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.TYPE)
public @interface Analyze {

	
//	/**
//	 * 匹配的url
//	 * @return
//	 */
//	String url() default "";
//	/**
//	 * param参数
//	 * @return
//	 */
//	String method() default "";
//	
//	/** 接口是否需要ip验证 **/
//	boolean ipCheck() default false;
//	
//	/** 用户调用接口权限验证 **/
//	boolean rightCheck() default false;
//	
//	/** 是否加密，默认不加密 **/
//	boolean encrypt() default false;
}
