package com.bdx.rainbow.service.ydzf.templet;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorTempletAndContentVO;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTempletContent;
import com.bdx.rainbow.urs.entity.IUserInfo;


public interface IYDZFMonitorTempletContentService {
	
	
	
	/**
	 * 插入模版内容
	 * @param record
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	int insertMonitorTempleteContent(TYdzfMonitorTempletContent record)throws BusinessException,SystemException;
	
	/**
	 * 插入模版内容列表
	 * @param recordList
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	void insertMonitorTempleteContents(List<TYdzfMonitorTempletContent> recordList)throws BusinessException,SystemException;
	
	/**
	 * 更新模版内容
	 * @param t
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	int updateMonitorTempleteContent(TYdzfMonitorTempletContent record)throws BusinessException,SystemException;
	
	/**
	 * 删除模版内容
	 * @param monitorTempletContentId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	int delMonitorTempleteContentByid(int monitorTempletContentId,IUserInfo userInfo)throws BusinessException,SystemException;
	
	
	/**
	 * 删除模版内容(通过模版id)
	 * @param monitorTempletId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	int delMonitorTempleteContentByTempId(int monitorTempletId,IUserInfo userInfo)throws BusinessException,SystemException;
	
	
	
	/**
	 * 查询模版内容所有
	 * @return
	 */
	List<TYdzfMonitorTempletContent> queryMonitorTempleteContenAll(TYdzfMonitorTempletContent record)throws SystemException,BusinessException;
	
	
	/**
	 * 查询模版内容
	 * @return
	 */
	List<TYdzfMonitorTempletContent> queryMonitorTempleteContenList(TYdzfMonitorTempletContent record,Integer start,Integer count)throws SystemException,BusinessException;
	
	/**
	 * 查询总数
	 */
	int countMonitorTempleteContent(TYdzfMonitorTempletContent t) throws SystemException,BusinessException;
	
	
	/**
	 * 查询详情
	 * @param monitorTempletId
	 * @return
	 */
	TYdzfMonitorTempletContent queryMonitorTempleteContentInfo(int monitorTempletContentId)throws SystemException,BusinessException;
	
	/**
	 * 根据monitorPlanId 查询模版名称和条目内容
	 * @param monitorPlanId
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	YdzfMonitorTempletAndContentVO queryMonitorTempleteAndContentInfoByPlanId(Integer monitorPlanId) throws SystemException,BusinessException;

    /**
     *
     * @param t
     * @param monitorTempletId 查询条件
     * @return
     */
    int updateMonitorByMonitorTempletId(TYdzfMonitorTempletContent t,int monitorTempletId);
}
