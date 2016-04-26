package com.bdx.rainbow.service.ydzf.inspect;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseWitnessesResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseWitnesses;

public interface IYDZFInspectCaseWitnessesService {

	/**
	 * 新增听证信息
	 * @param ydzfInspectCaseWitnesses
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int addInspectCaseWitnesses(TYdzfInspectCaseWitnesses ydzfInspectCaseWitnesses) throws BusinessException,SystemException;

	
	
	/**
	 * 修改听证信息
	 * @param ydzfInspectCaseWitnesses
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int updateInspectCaseWitnesses(TYdzfInspectCaseWitnesses ydzfInspectCaseWitnesses) throws BusinessException,SystemException;
	
	
	
	/**
	 * 查询听证信息
	 * @param insepctCaseWitnessesId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public YdzfInspectCaseWitnessesResultVO queryInspectCaseWitnessesInfo(int insepctCaseWitnessesId)throws BusinessException,SystemException;
	
	
	
	/**
	 * 查询听证信息
	 * @param insepctCaseWitnessesId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public List<YdzfInspectCaseWitnessesResultVO> queryInspectCaseWitnessesInfoVOList(TYdzfInspectCaseWitnesses ydzfInspectCaseWitnesses)throws BusinessException,SystemException;

	

	
}
