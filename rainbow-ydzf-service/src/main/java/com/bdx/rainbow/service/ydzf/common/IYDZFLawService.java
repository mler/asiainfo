package com.bdx.rainbow.service.ydzf.common;

import java.sql.Timestamp;
import java.util.Map;

import com.bdx.rainbow.basic.dubbo.bean.DubboLaw;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;

public interface IYDZFLawService {

	/**
	 * 返回法律法规列表
	 * @param condition
	 * @param startTime
	 * @param endTime
	 * @param start
	 * @param limit
	 * @return Map.total Map.list
	 * @throws Exception
	 */
	public Map<String, Object> getNodeListDubbo(DubboLaw condition, Timestamp startTime,Timestamp endTime,int start,int limit)throws BusinessException, SystemException;

	
	/**
	 * 获得一条明细法律
	 * @param lawId
	 * @return
	 * @throws Exception
	 */
	public DubboLaw getLawInfoDubbo(Integer lawId)throws BusinessException, SystemException;

	
	/**
	 * 获得层级title
	 * @param lawId
	 * @return 以逗号间隔 如 《医疗器械监督管理条例》（国务院令第650号） ,第一章,第二条,第一款
	 * @throws Exception
	 */
	public String getTitlesByIdDubbo(Integer lawId)throws BusinessException, SystemException;

	
	
	/**
	 * 	{"law_info": [{"id": "1", "title": "标题1"},{"id": "2", "title": "标题2"},]}
	 * @param lawIds
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public String getLawJsonDubbo(String lawIds)throws BusinessException, SystemException;
}
