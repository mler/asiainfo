package com.bdx.rainbow.mapp.core.serializer;

import com.bdx.rainbow.mapp.core.model.IMappDatapackage;

/**
 * 序列化模块
 * @author mler
 *
 */
public interface SerializerAware {
	
	public void setSerializer(MappSerializer<IMappDatapackage> serializer);
	
	public MappSerializer<IMappDatapackage> setSerializer();
}
