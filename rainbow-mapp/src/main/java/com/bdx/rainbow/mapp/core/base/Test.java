package com.bdx.rainbow.mapp.core.base;

import org.springframework.stereotype.Service;

import com.bdx.rainbow.mapp.core.util.ClassUtils;

@Service
public class Test {
	
	public String helloName(String name)
	{
		System.out.println("helloName()");
		return "hello "+name;
	}
	
	public String hello2(String name)
	{
		System.out.println("hello2 "+name);
		
		return "hello2 "+name;
	}
	
	public final static void main(String[] args) throws Exception
	{
		System.out.println(AbstractMappAction.class.isAssignableFrom(M9999Action.class));
	}
}
