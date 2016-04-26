package com.bdx.rainbow.service.ydzf.inspect;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseReadyPunishResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseReadyPunish;

public interface IYDZFInspectCaseReadyPunishService {
	
	
	/**
	 * 新增拟处罚信息
	 * @param ydzfInspectCaseReadyPunish
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int addInspectCaseReadyPunish(TYdzfInspectCaseReadyPunish ydzfInspectCaseReadyPunish) throws BusinessException,SystemException;

	
	
	/**
	 * 修改拟处罚信息
	 * @param ydzfInspectCaseReadyPunish
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int updateInspectCaseReadyPunish(TYdzfInspectCaseReadyPunish ydzfInspectCaseReadyPunish) throws BusinessException,SystemException;
	
	
	
	/**
	 * 查询拟处罚明细
	 * @param insepctCaseReadyPunishId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public YdzfInspectCaseReadyPunishResultVO queryInspectCaseReadyPunishInfo(int insepctCaseReadyPunishId)throws BusinessException,SystemException;
	
	
	/**
	 * 查询执行处罚详情列表
	 * @param monitorTaskId
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public  List<YdzfInspectCaseReadyPunishResultVO> queryInspectCaseReadyPunishInfoList(TYdzfInspectCaseReadyPunish ydzfInspectCaseReadyPunish)throws BusinessException,SystemException;


	

	

}
