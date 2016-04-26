/**
 * 
 */
package com.bdx.rainbow.mapp.core.exception;

/**
 * @author mler
 *
 */
public class BusinessException extends Exception {
	
	private static final long serialVersionUID = 1301102510038881782L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}
	
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
  
    public BusinessException(Throwable cause) {
        super(cause);
    }
	
}
