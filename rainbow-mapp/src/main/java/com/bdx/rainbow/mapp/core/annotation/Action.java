package com.bdx.rainbow.mapp.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) 
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface Action {

	public enum EncryptType {
        DESede,DES,RSA;
	}
	
	public enum SerializerType {
        JSON, XML;
	}
	
	/**
	 * action 对应的业务编码
	 * @return
	 */
	String bizcode() default "";
	/** 版本号 **/
	String version() default "1.0";
	/** 序列化对象 **/
	SerializerType serializer() default SerializerType.JSON;
	
	boolean usercheck() default true;
	
	boolean ipcheck() default false;
	
	boolean rightcheck() default false;
	
	/** 是否加密，默认不加密 **/
	boolean encrypt() default false;
	
	/**  是否base64  **/
	boolean base64() default true;
	
	/**  是否压缩  **/
	boolean gzip() default false;
	
	EncryptType encryptType() default EncryptType.DESede;
	
	/** 过滤器 **/
	String[] filters() default {};
	
	boolean monitor() default true;
}
