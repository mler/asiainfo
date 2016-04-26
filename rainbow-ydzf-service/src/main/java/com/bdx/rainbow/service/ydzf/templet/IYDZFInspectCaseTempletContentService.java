package com.bdx.rainbow.service.ydzf.templet;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseTempletAndContentVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTemplet;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTempletContent;
import com.bdx.rainbow.urs.entity.IUserInfo;


public interface IYDZFInspectCaseTempletContentService {
	
	
	
	/**
	 * 插入模版内容
	 * @param record
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	int insertInspectCaseTempleteContent(TYdzfInspectCaseTempletContent record)throws BusinessException,SystemException;
	
	/**
	 * 插入模版内容列表
	 * @param recordList
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	void insertInspectCaseTempleteContents(List<TYdzfInspectCaseTempletContent> recordList)throws BusinessException,SystemException;
	
	/**
	 * 更新模版内容
	 * @param t
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	int updateInspectCaseTempleteContent(TYdzfInspectCaseTempletContent record)throws BusinessException,SystemException;
	
	/**
	 * 删除模版内容
	 * @param InspectCaseTempletContentId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	int delInspectCaseTempleteContentByid(int InspectCaseTempletContentId,IUserInfo userInfo)throws BusinessException,SystemException;
	
	
	/**
	 * 删除模版内容(通过模版id)
	 * @param InspectCaseTempletId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	int delInspectCaseTempleteContentByTempId(int InspectCaseTempletId,IUserInfo userInfo)throws BusinessException,SystemException;
	
	
	
	/**
	 * 查询模版内容所有
	 * @return
	 */
	List<TYdzfInspectCaseTempletContent> queryInspectCaseTempleteContenAll(TYdzfInspectCaseTempletContent record)throws SystemException,BusinessException;
	
	
	/**
	 * 查询模版内容
	 * @return
	 */
	List<TYdzfInspectCaseTempletContent> queryInspectCaseTempleteContenList(TYdzfInspectCaseTempletContent record,Integer start,Integer count)throws SystemException,BusinessException;
	
	/**
	 * 查询总数
	 */
	int countInspectCaseTempleteContent(TYdzfInspectCaseTempletContent t) throws SystemException,BusinessException;
	
	
	/**
	 * 查询详情
	 * @param InspectCaseTempletId
	 * @return
	 */
	TYdzfInspectCaseTempletContent queryInspectCaseTempleteContentInfo(int InspectCaseTempletContentId)throws SystemException,BusinessException;
	
	/**
	 * 根据InspectCaseId 查询模版名称和条目内容
	 * @param InspectCaseId
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	YdzfInspectCaseTempletAndContentVO queryInspectCaseTempleteAndContentInfoByCaseId(Integer InspectCaseId) throws SystemException,BusinessException;

    /**
     *
     * @param t
     * @param inspectCaseTempletId 查询条件
     * @return
     */
    int updateInspectByInspectTempletId(TYdzfInspectCaseTempletContent t,int inspectCaseTempletId);
}
