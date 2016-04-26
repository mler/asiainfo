/**
 * 
 */
package com.bdx.rainbow.mapp.core.filter;



/**
 * @author yangqx
 *
 */
public interface IFilter<T>	 
{
	public void init() throws Exception;
	
	public void destroy() throws Exception;
	
	public T doFilter(T args) throws Exception;
}
