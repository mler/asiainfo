package com.bdx.rainbow.service.ydzf.templet;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTemplet;
import com.bdx.rainbow.urs.entity.IUserInfo;


public interface IYDZFMonitorTempletService {
	
	
	
	/**
	 * 插入模版
	 * @param t
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	int insertMonitorTemplete(TYdzfMonitorTemplet record)throws BusinessException,SystemException;
	
	/**
	 * 更新模版
	 * @param t
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	int updateMonitorTemplete(TYdzfMonitorTemplet record)throws BusinessException,SystemException;
	
	/**
	 * 删除模版
	 * @param monitorTempletId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	int delMonitorTemplete(int monitorTempletId,IUserInfo userInfo)throws BusinessException,SystemException;
	
	
	
	/**
	 * 查询所有模版
	 * @return
	 */
	List<TYdzfMonitorTemplet> queryMonitorTempleteAll(TYdzfMonitorTemplet record)throws SystemException,BusinessException;

	
	/**
	 * 查询模版
	 * @return
	 */
	List<TYdzfMonitorTemplet> queryMonitorTempleteList(TYdzfMonitorTemplet record,Integer start,Integer count)throws SystemException,BusinessException;
	
	/**
	 * 查询总数
	 */
	int countMonitorTemplete(TYdzfMonitorTemplet record) throws SystemException,BusinessException;
	
	
	/**
	 * 查询详情
	 * @param monitorTempletId
	 * @return
	 */
	TYdzfMonitorTemplet queryMonitorTempleteInfo(int monitorTempletId)throws SystemException,BusinessException;
	


}
