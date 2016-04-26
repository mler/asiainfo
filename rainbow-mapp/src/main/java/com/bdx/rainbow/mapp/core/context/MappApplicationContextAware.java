package com.bdx.rainbow.mapp.core.context;

public interface MappApplicationContextAware extends Aware {
	
	public void setMappApplicationContext(MappApplicationContext context) throws Exception;
	
}
