package com.bdx.rainbow.common;

import java.io.Serializable;

/**
 * 通用返回结果bean
 * flag:true,false;
 * msg:
 * data:object
 * @author fox
 *
 */
public class ResultBean{

	private Boolean flag;
	private String msg;
	private Object data;
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public ResultBean(boolean flag,String msg)
	{
	
		this.flag=flag;
		this.msg=msg;
	}
	public ResultBean(boolean flag)
	{
		this.flag=flag;
	}

}
