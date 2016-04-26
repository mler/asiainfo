/**
 * 
 */
package com.bdx.rainbow.etl.filter;


/**
 * @author yangqx
 *
 */
public interface Filter<T> 
{
	public void init() throws Exception;
	
	public void destroy() throws Exception;
	
	public T doFilter(T context) throws Exception;
}
