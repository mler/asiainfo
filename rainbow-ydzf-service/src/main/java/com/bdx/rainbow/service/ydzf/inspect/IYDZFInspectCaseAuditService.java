package com.bdx.rainbow.service.ydzf.inspect;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseAuditResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseAudit;

public interface IYDZFInspectCaseAuditService {

	/**
	 * 新增案件审核信息
	 * @param ydzfInspectCaseAudit
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int addInspectCaseAudit(TYdzfInspectCaseAudit ydzfInspectCaseAudit) throws BusinessException,SystemException;

	
	
	/**
	 * 修改案件审核信息
	 * @param ydzfInspectCaseAudit
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int updateInspectCaseAudit(TYdzfInspectCaseAudit ydzfInspectCaseAudit) throws BusinessException,SystemException;
	
	
	
	/**
	 * 查询案件审核信息
	 * @param insepctCaseAuditId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public YdzfInspectCaseAuditResultVO queryInspectCaseAuditInfo(int insepctCaseAuditId)throws BusinessException,SystemException;
	
	
	
	/**
	 * 查询案件审核信息
	 * @param insepctCaseAuditId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public List<YdzfInspectCaseAuditResultVO> queryInspectCaseAuditInfoVOList(TYdzfInspectCaseAudit ydzfInspectCaseAudit)throws BusinessException,SystemException;

	

	
}
