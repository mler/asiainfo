/**
 * 
 */
package com.bdx.rainbow.urs.exception;

import java.io.Serializable;


/**
 * @author yangqx
 *
 */
public abstract class BaseException extends Exception implements Serializable {

	private String errorCode;

	private String errorMsg;
	
	
	public <T extends ExceptionCode> BaseException(T exCode) {
		this.errorCode=exCode.getKey();
		this.errorMsg=exCode.getValue();
	}
	public <T extends ExceptionCode> BaseException(T exCode,Throwable t) {
		this.errorCode=exCode.getKey();
		this.errorMsg=exCode.getValue();
		this.initCause(t);
	}

    public BaseException() {
        super();
    }

    public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	
}
