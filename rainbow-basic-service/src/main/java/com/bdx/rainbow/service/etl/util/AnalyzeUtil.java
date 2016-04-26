package com.bdx.rainbow.service.etl.util;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析器工具类
 * 
 * @author Administrator
 * 
 */
public class AnalyzeUtil {

	/**
	 * 初始化实体类
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public static Object getInstant(String className) throws Exception {
		Object entity = Class.forName(className).newInstance();

		return entity;
	}
	
	/**
	 * 实体类设置属性
	 * @param entity
	 * @param setMethodName
	 * @param value
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Object executeMethod(Object entity, String methodName, Object[] value, Class[] clazz) throws Exception {
		Class<?> entityType = entity.getClass();
		
		// 设置set方法
		Method method = entityType.getMethod(methodName,
				clazz);
		
		return method.invoke(entity, value);
	}
	
	/**
	 * 通过正则表达式，截取字符串
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static Object regex(String str, String regex) throws Exception {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);

		if (matcher.matches()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}

	public static void main(String[] args) {
//		try {
//			Object entity = AnalyzeUtil
//					.getInstant("com.bdx.rainbow.entity.grab.FoodProductionLicense");
//			System.out.println(executeMethod(entity, "setLicenseNo", new Object[] { "123" },
//					new Class[] { String.class }));
//
//			System.out.println(executeMethod(entity, "getLicenseNo", new Object[] {  },
//					new Class[] { }));
//		} catch (Exception e) {
//
//		}
		try {
			String str = "123123：";
			String regex = ".+?：(.*?)";
			System.out.println(regex(str, regex));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
