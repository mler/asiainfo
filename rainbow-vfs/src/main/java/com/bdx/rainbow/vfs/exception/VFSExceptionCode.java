package com.bdx.rainbow.vfs.exception;

import com.bdx.rainbow.common.exception.ExceptionCode;

public class VFSExceptionCode extends ExceptionCode{

	protected VFSExceptionCode(String key) {
		super(key);
		// TODO Auto-generated constructor stub
	}

	public static VFSExceptionCode VFSEXCEPTION_CODE_FILE_NOTFIND = new  VFSExceptionCode("exception.vfs.filenotfind");
	
	public static VFSExceptionCode VFSEXCEPTION_CODE_NOT_CONNECT = new  VFSExceptionCode("exception.vfs.notconnect");
	
	public static VFSExceptionCode VFSEXCEPTION_CODE_NETWORK_ERROR = new  VFSExceptionCode("exception.vfs.networkerror");

}
