package com.bdx.rainbow.spsy.dal.ibatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface CommonQueryDAO {

	/**
	 * list
	 * 
	 * @param obj
	 */
	public List getModels(Object obj,String sqlKey);
	
	 /**
	  * 
	  * @param serialno
	  * @param sqlKey
	  * @return
	  */
	  public Object findByPkey(java.lang.String serialno, String sqlKey);

	  /**
	   * insert object
	   * @param object
	   * @param sqlKey
	   */
	  public void addModel(Object object, String sqlKey);

	  /**
	   * update object
	   * @param object
	   * @param sqlKey
	   */
	  public void modModel(Object object, String sqlKey);


	  /**
	   * delete object
	   * @param serialno
	   * @param sqlKey
	   */
	  public void delModel(java.lang.String serialno, String sqlKey);

	  
	  public Object findModel(Object object, String sqlKey);
	  public Object getRealName(Object obj, String sqlKey);
	  
	  /**
	   * 根据条件和语句  insert  一般用于插入一条记录
	   * @param key
	   * @param condition
	   */
	  public void insertHISByCondition(String key, Map<String, Object> condition);
      public Map getMap(String sqlKey,Object obj,String keyProperty,  String valueProperty);
}
