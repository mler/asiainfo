package com.bdx.rainbow.mapp.core.monitor;

public interface Node {
	
	String getNodeName();
	
	boolean isAvailable();
	
	void destroy();
	
}
