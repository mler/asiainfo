package com.bdx.rainbow.core.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlan;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTempletContent;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTempletContentExample;

public class CreatExampleUtil {
	
	
	
	public static String getCode(String classFullName)throws Exception 
	{
		String packageName=classFullName.substring(0,classFullName.lastIndexOf("."));
		String className=classFullName.substring(classFullName.lastIndexOf(".")+1,classFullName.length());
		StringBuffer sb = new StringBuffer();
		sb.append("private ").append(className+"Example getCondition(").append(className+" record) {").append("\n");
		sb.append(className+"Example example=new ").append(className).append("Example();").append("\n");
		Class clazz = Class.forName(classFullName); 
	     Field[] fields = clazz.getDeclaredFields();//获得属性
		sb.append("	if (record != null) {").append("\n");
		sb.append(packageName).append(".").append(className).append("Example.Criteria cr=example.createCriteria();").append("\n");
		   for (Field field : fields) {
			    PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
					      clazz);
			Method getMethod = pd.getReadMethod();//获得get方法
			   if(getMethod.getReturnType().getName().equals("java.lang.Integer"))
			   {
				   sb.append("if(record.").append(getMethod.getName()+"()").append("!= null && record.").append(getMethod.getName()+"()").append(">0) {").append("\n");;
				   sb.append("cr.and").append(getMethod.getName().substring(3,getMethod.getName().length())).append("EqualTo(record.").append(getMethod.getName()+"()").append(");").append("\n").append("}").append("\n");
			   }
			   if(getMethod.getReturnType().getName().equals("java.lang.String"))
			   {
				   sb.append("if(org.apache.commons.lang.StringUtils.isNotBlank(record.").append(getMethod.getName()+"()").append(")){").append("\n");
				   sb.append("cr.and").append(getMethod.getName().substring(3,getMethod.getName().length())).append("EqualTo(record.").append(getMethod.getName()+"()").append(");").append("\n").append("}").append("\n");
			   }
			   if(getMethod.getReturnType().getName().equals("java.sql.Timestamp"))
			   {
				   sb.append("if(record.").append(getMethod.getName()+"()").append("!= null){").append("\n");;
				   if(getMethod.getName().toLowerCase().contains("begin")||getMethod.getName().toLowerCase().contains("start"))
				   {
						 sb.append("cr.and").append(getMethod.getName().substring(3,getMethod.getName().length())).append("GreaterThanOrEqualTo(record.").append(getMethod.getName()+"()").append(");").append("\n").append("}").append("\n");
				   }
				   else if(getMethod.getName().toLowerCase().contains("end"))
				   {
					   sb.append("cr.and").append(getMethod.getName().substring(3,getMethod.getName().length())).append("LessThanOrEqualTo(record.").append(getMethod.getName()+"()").append(");").append("\n").append("}").append("\n");
				   }
				   else
				   {
					   sb.append("cr.and").append(getMethod.getName().substring(3,getMethod.getName().length())).append("EqualTo(record.").append(getMethod.getName()+"()").append(");").append("\n").append("}").append("\n");
				   }
			
			
			  }
			   
			   
		   }
			sb.append("	}").append("\n");
			sb.append("	return example;").append("\n");
			sb.append("}");
		return sb.toString();
		
	}
	public static void main(String[] args) throws Exception {
		System.out.println(getCode("com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlan"));
//		System.out.println(getCode("com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseFinish"));
//		System.out.println(getCode("com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseAudit"));
//		System.out.println(getCode("com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseWitnesses"));
//		System.out.println(getCode("com.bdx.rainbow.entity.ydzf.TYdzfInspectCase"));
		
		   }
		    
	}
	



