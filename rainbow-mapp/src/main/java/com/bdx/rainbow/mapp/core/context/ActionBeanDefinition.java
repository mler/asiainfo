package com.bdx.rainbow.mapp.core.context;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bdx.rainbow.mapp.core.annotation.Action.SerializerType;
import com.bdx.rainbow.mapp.core.filter.IFilter;


public class ActionBeanDefinition implements MappBeanDefinition {

	private String bizcode;
	
	private String[] filters;
	
	private boolean usercheck;
	
	private boolean ipcheck;
	
	private boolean rightcheck;
	
	private boolean encrypt;
	
	private boolean base64;
	
	private boolean gzip;
	
	/** 版本号 **/
	private String version;
	/** 序列化对象 **/
	private SerializerType serializer;
	
	private String encryptType;
	
	private Class<?> actionClass;
	
	private Class<?> requestClass;
	
	private Class<?> responseClass;
	
	private List<IFilter<?>> filterList;
	
	private boolean ifMethod = false;
	
	private String methodName;
	
	private Object[] params;
	
	private boolean monitor;
	
	private Map<String,Set<FilterBeanDefinition>> methodFilterSet;
	
	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	public String[] getFilters() {
		return filters;
	}

	public void setFilters(String[] filters) {
		this.filters = filters;
	}

	public boolean isEncrypt() {
		return encrypt;
	}

	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}

	public boolean isBase64() {
		return base64;
	}

	public void setBase64(boolean base64) {
		this.base64 = base64;
	}

	public boolean isGzip() {
		return gzip;
	}

	public void setGzip(boolean gzip) {
		this.gzip = gzip;
	}

	public String getEncryptType() {
		return encryptType;
	}

	public void setEncryptType(String encryptType) {
		this.encryptType = encryptType;
	}

	public Class<?> getActionClass() {
		return actionClass;
	}

	public void setActionClass(Class<?> actionClass) {
		this.actionClass = actionClass;
	}

	public Class<?> getRequestClass() {
		return requestClass;
	}

	public void setRequestClass(Class<?> requestClass) {
		this.requestClass = requestClass;
	}

	public Class<?> getResponseClass() {
		return responseClass;
	}

	public void setResponseClass(Class<?> responseClass) {
		this.responseClass = responseClass;
	}

	public List<IFilter<?>> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<IFilter<?>> filterList) {
		this.filterList = filterList;
	}

	public boolean isIfMethod() {
		return ifMethod;
	}

	public void setIfMethod(boolean ifMethod) {
		this.ifMethod = ifMethod;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public SerializerType getSerializer() {
		return serializer;
	}

	public void setSerializer(SerializerType serializer) {
		this.serializer = serializer;
	}

	public Map<String, Set<FilterBeanDefinition>> getMethodFilterSet() {
		return methodFilterSet;
	}

	public void setMethodFilterSet(Map<String, Set<FilterBeanDefinition>> methodFilterSet) {
		this.methodFilterSet = methodFilterSet;
	}

	public boolean isUsercheck() {
		return usercheck;
	}

	public void setUsercheck(boolean usercheck) {
		this.usercheck = usercheck;
	}

	public boolean isIpcheck() {
		return ipcheck;
	}

	public void setIpcheck(boolean ipcheck) {
		this.ipcheck = ipcheck;
	}

	public boolean isRightcheck() {
		return rightcheck;
	}

	public void setRightcheck(boolean rightcheck) {
		this.rightcheck = rightcheck;
	}

	public boolean isMonitor() {
		return monitor;
	}

	public void setMonitor(boolean monitor) {
		this.monitor = monitor;
	}
}
