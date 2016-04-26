package com.bdx.rainbow.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class StringMyUtils {
	
	public static List<String> transArrayToListString(String contents,String splitFlag)
	{
		if(StringUtils.isNotBlank(contents))
		{
			List<String> strList = new ArrayList<String>();
			strList = Arrays.asList(contents.split(splitFlag));
			return strList;
		}
		return null;
		
	}
	
	
	public static List<Integer> transArrayToListInteger(String contents,String splitFlag)
	{
		if(StringUtils.isNotBlank(contents))
		{
			List<Integer> strList = new ArrayList<Integer>();
			String[] contentsArr=contents.split(splitFlag);
			if(contentsArr!=null)
			{
				for(String content:contentsArr)
				{
					strList.add(Integer.parseInt(content));	
				}
				return strList;
			}
			
		}
		return null;
		
	}

}
