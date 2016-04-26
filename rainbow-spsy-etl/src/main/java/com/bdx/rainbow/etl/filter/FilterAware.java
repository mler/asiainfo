package com.bdx.rainbow.etl.filter;

import java.util.List;


/**
 * 定义了带filter的切面
 * 
 * @author mler
 * 
 */
public abstract class FilterAware<T> {
	
	private List<Filter<T>> filters;
	
	public void setFilters(List<Filter<T>> filters){
		this.filters = filters;
	}
	
	public T doFilter(T param) throws Exception
	{
		if(filters == null || filters.isEmpty())
			return param;
		
		for(Filter<T> filter : filters)
			param = filter.doFilter(param);
		
		return param;
		
	}
	
	/**
	 * 释放资源
	 * @throws Exception
	 */
	public void destroyFilterChain() throws Exception
	{
		if(filters == null || filters.isEmpty())
			return;
		
		for(Filter<T> filter : filters)
			filter.destroy();
	}
	
}
