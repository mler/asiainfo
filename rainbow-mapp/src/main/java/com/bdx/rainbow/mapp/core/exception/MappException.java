package com.bdx.rainbow.mapp.core.exception;

public class MappException extends Exception {

	private static final long serialVersionUID = 6816525029102534982L;

	public MappException() {
		super();
	}

	public MappException(String message) {
		super(message);
	}
	 
    public MappException(String message, Throwable cause) {
        super(message, cause);
    }
  
    public MappException(Throwable cause) {
        super(cause);
    }
}
