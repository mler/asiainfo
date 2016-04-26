package com.bdx.rainbow.mapp.core.serializer;

import com.bdx.rainbow.mapp.core.model.IMappDatapackage;

public interface MappSerializer<T extends IMappDatapackage> {

	public T stringToRequest(String requestString) throws Exception;
	
	public String responseToString(T responseObject) throws Exception;
	
	public String getName();
	
}
