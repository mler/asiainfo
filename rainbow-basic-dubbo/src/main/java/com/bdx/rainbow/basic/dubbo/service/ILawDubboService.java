package com.bdx.rainbow.basic.dubbo.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.bdx.rainbow.basic.dubbo.bean.DubboLaw;

/**
 * 法律法规DUBBO接口
 * @author zhengwenjuan
 *
 */
public interface ILawDubboService {
	
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
	public Map<String, Object> getNodeList(DubboLaw condition, Timestamp startTime,Timestamp endTime,int start,int limit)throws Exception;
	
	/**
	 * 获得一条明细法律
	 * @param lawId
	 * @return
	 * @throws Exception
	 */
	public DubboLaw getLawInfo(Integer lawId)throws Exception;
	
	/**
	 * 获得层级title
	 * @param lawId
	 * @return 以逗号间隔 如 《医疗器械监督管理条例》（国务院令第650号） ,第一章,第二条,第一款
	 * @throws Exception
	 */
	public String getTitlesById(Integer lawId)throws Exception;

}
