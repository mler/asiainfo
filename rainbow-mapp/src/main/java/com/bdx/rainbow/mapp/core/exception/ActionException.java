package com.bdx.rainbow.mapp.core.exception;

@SuppressWarnings("serial")
public class ActionException extends Exception {

	private Exception ex;
	
	private String code;
	
	public ActionException(String code,Exception ex) {
		super(code,ex);
		this.code = code;
		this.ex = ex;
	}
	
	public ActionException(String code,String message) {
		super(message);
		this.ex = new Exception(message);
		this.code = code;
		
	}


	public Exception getEx() {
		return ex;
	}

	public String getCode() {
		return code;
	}
	
	public String getMessage()
	{
		return ex==null?"ActionException.ex is null":ex.getMessage();
	}
}
