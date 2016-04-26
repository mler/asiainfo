package com.bdx.rainbow.util;

import java.util.List;

public class JsonObject {
	private int total;
	private boolean success;
	private String message;
	
	private Object data;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public JsonObject() {}
	
	public JsonObject(List data) {
		this(true,data);
	}
	
	public JsonObject(boolean success,List data) {
		if(data != null) {
			this.total=data.size();
			this.data=data;
			this.success=success;
		}
	}
	
	public JsonObject(boolean success,String msg) {
		this.success =success;
		this.message=msg;
	}
	public JsonObject(boolean success,String msg,Object data) {
		this.success =success;
		this.message=msg;
		this.data=data;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}