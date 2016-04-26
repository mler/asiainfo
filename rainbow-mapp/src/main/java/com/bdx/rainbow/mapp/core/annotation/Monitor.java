package com.bdx.rainbow.mapp.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 针对需要监控的方法进行注释
 * @author mler
 * @2016年4月7日
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target({ElementType.TYPE})
@Documented
public @interface Monitor {
	
	String[] keys() default {};
	
	/** 内存日志存在的最大时间,超过这个时间将日志进行操作 **/
	long maxTime() default 3600*1000;
	
	/** 内存日志存在的最大占用内存大小，超过这个大小将对日志进行处理 **/
    int maxSize() default 100*1000;
    
    /** 监控统计信息采集间隔，对上面2项进行检测的间隔时间 **/
    long collectTime() default 5*1000;
    
    /**日志是否保存到数据库，操作时是否保存到数据库 **/
    boolean saveTodb() default false;
    
}
