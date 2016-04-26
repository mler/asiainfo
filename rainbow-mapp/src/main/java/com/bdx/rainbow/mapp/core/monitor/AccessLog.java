package com.bdx.rainbow.mapp.core.monitor;

/**
 * 每次监控生成调用日志，一次请求生成一条
 * @author mler
 * @2016年4月18日
 */
public class AccessLog implements java.io.Serializable,Node {
	
	private static final long serialVersionUID = 4064001089296997707L;
	
	private Class<?> clazz;
	
	private String method;
	
	private String nodeName;
	
	private String userId;

	private String url;

	private long createTime;

	private String req;

	private String rsp;

	private boolean success;

	private String msg;
	
	private String bizcode;
	
	private long endTime;
	
	private boolean available;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getReq() {
		return req;
	}

	public void setReq(String req) {
		this.req = req;
	}

	public String getRsp() {
		return rsp;
	}

	public void setRsp(String rsp) {
		this.rsp = rsp;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	@Override
	public String getNodeName() {
		// TODO Auto-generated method stub
		return nodeName;
	}

	@Override
	public boolean isAvailable() {
		return available;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setVailable(boolean vailable) {
		this.available = vailable;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}
