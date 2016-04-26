package com.bdx.rainbow.mapp.core.exception;

public class SystemException extends Exception {

	private static final long serialVersionUID = -5883791263568417490L;

	public SystemException() {
		super();
	}

	public SystemException(String message) {
		super(message);
	}
	
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
  
    public SystemException(Throwable cause) {
        super(cause);
    }
}
