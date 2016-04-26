package com.bdx.rainbow.mapp.core.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface IHeader  {

	public String getBizCode();
	
	public String getIdentityId();
	
	public void setRespCode(String respCode);
	
	public void setRespMsg(String respMsg);
	
	public String getRespCode();
	
	public String getRespMsg();
	
	public String getSign();
	
	public String getMode();
	
	public void setSign(String sign);
	
}
