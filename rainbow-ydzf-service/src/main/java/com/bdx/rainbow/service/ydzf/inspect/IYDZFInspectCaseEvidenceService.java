package com.bdx.rainbow.service.ydzf.inspect;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseEvidenceResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidence;

public interface IYDZFInspectCaseEvidenceService {
	
	
	/**
	 * 新增稽查案件调查取证信息
	 * @param ydzfInspectCaseEvidence
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int addInspectCaseEvidence(TYdzfInspectCaseEvidence ydzfInspectCaseEvidence) throws BusinessException,SystemException;

	
	
	/**
	 * 修改执法稽查案件调查取证信息
	 * @param ydzfInspectCaseEvidence
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int updateInspectCaseEvidence(TYdzfInspectCaseEvidence ydzfInspectCaseEvidence) throws BusinessException,SystemException;
	
	
	
	/**
	 * 查询调查取证明细
	 * @param insepctCaseEvidenceId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public YdzfInspectCaseEvidenceResultVO queryInspectCaseEvidenceInfo(int insepctCaseEvidenceId)throws BusinessException,SystemException;

	
	
	/**
	 * 查询调查取证列表
	 * @param ydzfInspectCaseEvidence
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public List<TYdzfInspectCaseEvidence> queryInspectCaseEvidenceList(TYdzfInspectCaseEvidence ydzfInspectCaseEvidence)throws BusinessException,SystemException;

	
	/**
	 * 查询调查取证信息
	 * @param inspectCaseId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public TYdzfInspectCaseEvidence queryInspectCaseEvidenceInfoByInspectId(int inspectCaseId)throws BusinessException,SystemException;


	

}
