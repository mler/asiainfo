package com.bdx.rainbow.service.ydzf.inspect;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseExecutePunishResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseExecutePunish;
import com.bdx.rainbow.urs.entity.IUserInfo;

public interface IYDZFInspectCaseExecutePunishService {
	
	/**
	 * 新增或者更新执行处罚详情（根据是否有id判断）
	 * @param taskInfoAddForm
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int addOrUpdateExecutePunishInfo(TYdzfInspectCaseExecutePunish ydzfInspectCaseExecutePunish,IUserInfo userInfo)throws BusinessException,SystemException;
	
	
	
	
	/**
	 * 查询执行处罚详情
	 * @param monitorTaskId
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public  YdzfInspectCaseExecutePunishResultVO queryExecutePunishInfo(int insepctCaseExecutePunishId)throws BusinessException,SystemException;
	
	
	/**
	 * 查询执行处罚详情列表
	 * @param monitorTaskId
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public  List<YdzfInspectCaseExecutePunishResultVO> queryExecutePunishInfoVOList(TYdzfInspectCaseExecutePunish ydzfInspectCaseExecutePunish)throws BusinessException,SystemException;

}
