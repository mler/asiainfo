package com.bdx.rainbow.service.ydzf.inspect;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseFinishResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseFinish;

public interface IYDZFInspectCaseFinishService {

	/**
	 * 新增结案信息
	 * @param ydzfInspectCaseFinish
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int addInspectCaseFinish(TYdzfInspectCaseFinish ydzfInspectCaseFinish) throws BusinessException,SystemException;

	
	
	/**
	 * 修改结案信息
	 * @param ydzfInspectCaseFinish
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int updateInspectCaseFinish(TYdzfInspectCaseFinish ydzfInspectCaseFinish) throws BusinessException,SystemException;
	
	
	
	/**
	 * 查询结案信息
	 * @param insepctCaseFinishId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public YdzfInspectCaseFinishResultVO queryInspectCaseFinishInfo(int insepctCaseFinishId)throws BusinessException,SystemException;
	
	
	
	/**
	 * 查询结案信息
	 * @param insepctCaseFinishId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public List<YdzfInspectCaseFinishResultVO> queryInspectCaseFinishInfoVOList(TYdzfInspectCaseFinish ydzfInspectCaseFinish)throws BusinessException,SystemException;

	

	
}
