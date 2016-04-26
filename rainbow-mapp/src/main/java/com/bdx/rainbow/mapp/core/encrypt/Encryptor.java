package com.bdx.rainbow.mapp.core.encrypt;

import com.bdx.rainbow.mapp.core.annotation.Action.EncryptType;

public interface Encryptor {
	
	public String encrypt(String source) throws Exception;
	
	public String dencrypt(String encryptString) throws Exception;
	
	public EncryptType getType();
	
}
