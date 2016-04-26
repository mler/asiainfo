/**
 * 
 */
package com.bdx.rainbow.mapp.core.model;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.PropertyUtils;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author mler
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public abstract class IBody {

	@Override
	public String toString() 
	{
		String ret = "";
		
		try{
			
			StringBuffer string = new StringBuffer("");
			
			PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(this.getClass());
			
			if(props == null || props.length==0)
			{
				return this.getClass().getName() + "[ ]";
			}
			
			for(PropertyDescriptor prop : props)
			{
				if(prop.getPropertyType() == java.lang.Class.class)
					continue;

				string.append(prop.getName() + "=" + PropertyUtils.getProperty(this, prop.getName()) + ", ");
			}
			
			ret = string.toString().substring(0, string.toString().lastIndexOf(","));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.getClass().getName() + "[ "+ret+" ] ";
	}
	
}
