package com.bdx.rainbow.service.ydzf.inspect;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseEvidenceSampleResultInfoVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidenceSample;
import com.bdx.rainbow.urs.entity.IUserInfo;

public interface IYDZFInspectCaseEvidenceSampleService {
	
	
	/**
	 * 新增调查取证样品取样信息
	 * @param ydzfInspectCaseEvidenceSample
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int addInspectCaseEvidenceSample(TYdzfInspectCaseEvidenceSample ydzfInspectCaseEvidenceSample) throws BusinessException,SystemException;

	
	
	/**
	 * 修改调查取证样品取样信息
	 * @param ydzfInspectCase
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int updateInspectCaseEvidenceSample(TYdzfInspectCaseEvidenceSample ydzfInspectCaseEvidenceSample) throws BusinessException,SystemException;
	
	/**
	 * 删除调查取证样品取样信息
	 * @param insepctCaseEvidenceSampleId
	 * @param userInfo
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int delInspectCaseEvidenceSample(Integer insepctCaseEvidenceSampleId,IUserInfo userInfo) throws BusinessException, SystemException ;
	
	
	/**
	 * 查询调查取证样品取样信息
	 * @param insepctCaseEvidenceSampleId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public YdzfInspectCaseEvidenceSampleResultInfoVO queryInspectCaseEvidenceSampleInfoById(Integer insepctCaseEvidenceSampleId) throws BusinessException,SystemException;
	
	
	
	/**
	 * 查询调查取证样品取样列表
	 * @param ydzfInspectCase
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public List<TYdzfInspectCaseEvidenceSample> queryInspectCaseEvidenceSampleListAll(Integer insepctCaseEvidenceId) throws BusinessException,SystemException;
	
	
	
	/**
	 * 查询调查取证样品取样列表
	 * @param insepctCaseEvidenceSampleIdList
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public List<TYdzfInspectCaseEvidenceSample> queryInspectCaseEvidenceSampleListAll(List<Integer> insepctCaseEvidenceSampleIdList) throws BusinessException,SystemException;
	
	
	

	
	


	

}
