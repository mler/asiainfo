package com.bdx.rainbow.spsy.service;

import java.util.List;

import com.bdx.rainbow.urs.entity.ParamDetail;

/**
 * 参数操作接口
 * @author tanglian 2016-02-03
 *
 */
public interface IParamDetailService {

	/**
	 * 根据key查询出数据字典
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<ParamDetail> findCacheByKey(String key)throws Exception;
	
	/**
	 * 查询中文翻译
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public String getParamName(String key,String value) throws Exception;
}
