package com.bdx.rainbow.service.ydzf.history;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.form.YdzfEnterpriseHistoryQueryInfo;
import com.bdx.rainbow.dto.ydzf.vo.YdzfEnterpriseHistoryCountVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfEnterpriseHistoryListInfoVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfPunishAdviceFinishHistoryVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfPunishAdviceHistoryVO;
import com.bdx.rainbow.urs.entity.IUserInfo;

public interface IYDZFHistoryService {

	/**
	 * 查询企业监管历史数目
	 * 
	 * @param ydzfEnterpriseHistoryQueryInfo
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public YdzfEnterpriseHistoryCountVO queryEnterpriseHistoryCount(
			YdzfEnterpriseHistoryQueryInfo ydzfEnterpriseHistoryQueryInfo)
			throws BusinessException, SystemException;

	/**
	 * 查询企业监管历史列表
	 * 
	 * @param ydzfEnterpriseHistoryQueryInfo
	 * @param start
	 * @param limit
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public List<YdzfEnterpriseHistoryListInfoVO> queryEnterpriseHistoryListInfo(
			YdzfEnterpriseHistoryQueryInfo ydzfEnterpriseHistoryQueryInfo,
			Integer start, Integer limit) throws BusinessException,
			SystemException;

	/**
	 * 查询企业监管历史列表总数
	 * 
	 * @param ydzfEnterpriseHistoryQueryInfo
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int queryEnterpriseHistoryListInfoCount(
			YdzfEnterpriseHistoryQueryInfo ydzfEnterpriseHistoryQueryInfo)
			throws BusinessException, SystemException;
	
	/**
	 * 查询历史类似处罚方案参考总数
	 * @param userInfo
	 * @param inspectCaseId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int queryPunishAdviceHistoryListInfoCount(IUserInfo userInfo,int inspectCaseId)	throws BusinessException, SystemException;
	/**
	 * 查询历史类似处罚方案参考列表
	 * @param userInfo
	 * @param inspectCaseId
	 * @param start
	 * @param limit
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public List<YdzfPunishAdviceHistoryVO> queryPunishAdviceHistoryListInfo(IUserInfo userInfo,int inspectCaseId,Integer start,
			Integer limit)	throws BusinessException, SystemException;
	
	/**
	 * 查询历史类似案件结案信息列表
	 * @param userInfo
	 * @param inspectCaseIds
	 * @param start
	 * @param limit
	 * @return 
	 * key:volist, value:list<YdzfPunishAdviceHistoryVO> volist
	 * key:inspectCaseIds,value:inspectCaseIds;
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public List<YdzfPunishAdviceFinishHistoryVO> queryPunishAdviceFinishHistoryListInfo(IUserInfo userInfo,String inspectCaseIds,Integer start, Integer limit)	throws BusinessException, SystemException;
	
	
}
