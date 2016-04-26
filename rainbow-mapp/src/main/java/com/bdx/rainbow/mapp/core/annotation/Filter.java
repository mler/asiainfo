package com.bdx.rainbow.mapp.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface Filter {

	public enum FilterType {
        //注：枚举写在最前面，否则编译出错
        BEFORE, AFTER,AROUND;
	}
	
	int order() default 0;
	
	String[] filterMethod() default {};
	
	boolean required() default true;
	
	FilterType filterType() default FilterType.BEFORE;
	
}
