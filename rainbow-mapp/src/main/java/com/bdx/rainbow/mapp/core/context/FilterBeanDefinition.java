package com.bdx.rainbow.mapp.core.context;

import java.util.Collection;

import com.bdx.rainbow.mapp.core.annotation.Filter.FilterType;

public class FilterBeanDefinition implements MappBeanDefinition {

	private int order;
	
	private Class<?> filterClass;
	
	private String[] filterMethod;
	
	private boolean required;
	
	private FilterType filterType;
	
	private Collection<String[]> classAndMethod;

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Class<?> getFilterClass() {
		return filterClass;
	}

	public void setFilterClass(Class<?> filterClass) {
		this.filterClass = filterClass;
	}

	public String[] getFilterMethod() {
		return filterMethod;
	}

	public void setFilterMethod(String[] filterMethod) {
		this.filterMethod = filterMethod;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public FilterType getFilterType() {
		return filterType;
	}

	public void setFilterType(FilterType filterType) {
		this.filterType = filterType;
	}

	public Collection<String[]> getClassAndMethod() {
		return classAndMethod;
	}

	public void setClassAndMethod(Collection<String[]> classAndMethod) {
		this.classAndMethod = classAndMethod;
	}

}
