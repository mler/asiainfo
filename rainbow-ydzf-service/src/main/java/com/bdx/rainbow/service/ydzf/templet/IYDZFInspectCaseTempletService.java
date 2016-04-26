package com.bdx.rainbow.service.ydzf.templet;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTemplet;
import com.bdx.rainbow.urs.entity.IUserInfo;


public interface IYDZFInspectCaseTempletService {
	
	
	
	/**
	 * 插入模版
	 * @param t
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	int insertInspectCaseTemplete(TYdzfInspectCaseTemplet record)throws BusinessException,SystemException;
	
	/**
	 * 更新模版
	 * @param t
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	int updateInspectCaseTemplete(TYdzfInspectCaseTemplet record)throws BusinessException,SystemException;
	
	/**
	 * 删除模版
	 * @param InspectCaseTempletId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	int delInspectCaseTemplete(int InspectCaseTempletId,IUserInfo userInfo)throws BusinessException,SystemException;
	
	
	
	/**
	 * 查询所有模版
	 * @return
	 */
	List<TYdzfInspectCaseTemplet> queryInspectCaseTempleteAll(TYdzfInspectCaseTemplet record)throws SystemException,BusinessException;

	
	/**
	 * 查询模版
	 * @return
	 */
	List<TYdzfInspectCaseTemplet> queryInspectCaseTempleteList(TYdzfInspectCaseTemplet record,Integer start,Integer count)throws SystemException,BusinessException;
	
	/**
	 * 查询总数
	 */
	int countInspectCaseTemplete(TYdzfInspectCaseTemplet record) throws SystemException,BusinessException;
	
	
	/**
	 * 查询详情
	 * @param InspectCaseTempletId
	 * @return
	 */
	TYdzfInspectCaseTemplet queryInspectCaseTempleteInfo(int InspectCaseTempletId)throws SystemException,BusinessException;
	


}
