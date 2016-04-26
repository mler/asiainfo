package com.bdx.rainbow.mapp.core.monitor;

import java.io.Serializable;


/**
 * 统计信息，每个接口一个总的统计信息
 * @author mler
 * @2016年4月18日
 */
public class AccessStatLog implements Serializable {

	private static final long serialVersionUID = 3770946848860902150L;
	
	private String nodeName;//节点名称，多节点的时候使用
	
	private String lastUserId;//id
	
	private long totalTimes;//请求总次数
	
	private long success;//成功次数
	
	private long fail;//失败次数
	
	private long maxInvokeTime;//最大的成功调用时长
	
	private long slowTimes;//超过限定请求时间指标的次数，指标见MonitorSetting
	
	private long concurrent;//最大并发
	
	private long maxInput;//最大请求参数
	
	private long maxOutput;//最大响应参数
	
	private long totalSuccessInvokeTime;//调用成功时的响应时间
	
	private long totalInvokeTime;//总调用时间
	
	private long startTime;
	
	private long endTime;

	public AccessStatLog() {
		super();
	}
	
	public AccessStatLog(long startTime) {
		super();
		this.startTime = startTime;
	}

	public String getLastUserId() {
		return lastUserId;
	}

	public void setLastUserId(String lastUserId) {
		this.lastUserId = lastUserId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public long getTotalSuccessInvokeTime() {
		return totalSuccessInvokeTime;
	}

	public void setTotalSuccessInvokeTime(long totalSuccessInvokeTime) {
		this.totalSuccessInvokeTime = totalSuccessInvokeTime;
	}

	public long getTotalInvokeTime() {
		return totalInvokeTime;
	}

	public void setTotalInvokeTime(long totalInvokeTime) {
		this.totalInvokeTime = totalInvokeTime;
	}

	public long getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(long totalTimes) {
		this.totalTimes = totalTimes;
	}

	public long getSuccess() {
		return success;
	}

	public void setSuccess(long sucess) {
		this.success = sucess;
	}

	public long getFail() {
		return fail;
	}

	public void setFail(long fail) {
		this.fail = fail;
	}

	public long getMaxInvokeTime() {
		return maxInvokeTime;
	}

	public void setMaxInvokeTime(long maxInvokeTime) {
		this.maxInvokeTime = maxInvokeTime;
	}

	public long getSlowTimes() {
		return slowTimes;
	}

	public void setSlowTimes(long slowTimes) {
		this.slowTimes = slowTimes;
	}

	public long getConcurrent() {
		return concurrent;
	}

	public void setConcurrent(long concurrent) {
		this.concurrent = concurrent;
	}
	
	public long getMaxInput() {
		return maxInput;
	}

	public void setMaxInput(long maxInput) {
		this.maxInput = maxInput;
	}

	public long getMaxOutput() {
		return maxOutput;
	}

	public void setMaxOutput(long maxOutput) {
		this.maxOutput = maxOutput;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	
}
