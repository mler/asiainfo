package com.bdx.rainbow.service.ydzf.inspect;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.form.YdzfInspectCaseAddForm;
import com.bdx.rainbow.dto.ydzf.form.YdzfInspectCaseListQueryInfo;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseListResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;

public interface IYDZFInspectCaseService {
	
	
	/**
	 * 新增执法稽查案件(初始化调查取证)
	 * @param ydzfInspectCaseAddForm
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int addInspectCase(YdzfInspectCaseAddForm ydzfInspectCaseAddForm) throws BusinessException,SystemException;

	
	/**
	 * 查询稽查案件列表
	 * @param ydzfInspectCase
	 * @param start
	 * @param limit
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public List<YdzfInspectCaseListResultVO> queryInspectCaseList(YdzfInspectCaseListQueryInfo ydzfInspectCaseListQueryInfo,Integer start,
			Integer limit)throws BusinessException,SystemException;
	
	/**
	 * 查询稽查案件列表总数
	 * @param ydzfInspectCase
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int countInspectCaseList(YdzfInspectCaseListQueryInfo ydzfInspectCaseListQueryInfo)
			throws BusinessException, SystemException ;
	
	
	/**
	 * 更新执法稽查案件
	 * @param ydzfInspectCase
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int updateInspectCase(TYdzfInspectCase ydzfInspectCase) throws BusinessException,SystemException;

	
	/**
	 * 查询稽查案件详情（需要关联步骤信息）
	 * @param insepctCaseId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public YdzfInspectCaseListResultVO queryInspectCaseInfoAndRel(Integer insepctCaseId)throws BusinessException,SystemException;

}
