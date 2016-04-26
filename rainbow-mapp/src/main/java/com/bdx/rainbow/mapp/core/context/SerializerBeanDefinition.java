package com.bdx.rainbow.mapp.core.context;

import com.bdx.rainbow.mapp.core.annotation.Action.SerializerType;

public class SerializerBeanDefinition implements MappBeanDefinition {

	private SerializerType type;
	
	private Class<?> serializerClass;

	public SerializerType getType() {
		return type;
	}

	public void setType(SerializerType type) {
		this.type = type;
	}

	public Class<?> getSerializerClass() {
		return serializerClass;
	}

	public void setSerializerClass(Class<?> serializerClass) {
		this.serializerClass = serializerClass;
	}
}
