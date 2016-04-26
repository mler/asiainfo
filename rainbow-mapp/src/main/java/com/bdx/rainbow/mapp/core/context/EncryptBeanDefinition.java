package com.bdx.rainbow.mapp.core.context;

import com.bdx.rainbow.mapp.core.annotation.Action.EncryptType;
import com.bdx.rainbow.mapp.core.encrypt.Encryptor;


public class EncryptBeanDefinition {

	private EncryptType type;
	
	private Class<? extends Encryptor> encryptClass;

	public EncryptType getType() {
		return type;
	}

	public void setType(EncryptType type) {
		this.type = type;
	}

	public Class<? extends Encryptor> getEncryptClass() {
		return encryptClass;
	}

	public void setEncryptClass(Class<? extends Encryptor> encryptClass) {
		this.encryptClass = encryptClass;
	}
	
}
