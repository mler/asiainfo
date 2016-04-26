package com.bdx.rainbow.spsy.service;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginAgencyCompany;

/**
 * 经销商信息操作
 * @author tanglian 2016-02-03
 *
 */
public interface IAgencyCompanyService {
    /**
     * 查询经销商信息列表
     * @param agencyInfo
     * @param start
     * @param limit
     * @return
     * @throws BusinessException
     */
	public List<TOriginAgencyCompany> getAgencyList(TOriginAgencyCompany agencyInfo,int start,int limit) throws BusinessException;
	
	/**
	 * 查询符合条件的总记录数
	 * @param agencyInfo
	 * @return
	 * @throws BusinessException
	 */
	public Integer countAgency(TOriginAgencyCompany agencyInfo) throws BusinessException;
	
	/**
	 * 根据主键查询经销商信息
	 * @param agencyId
	 * @return
	 * @throws BusinessException
	 */
	public TOriginAgencyCompany getAgency(Integer agencyId) throws BusinessException;
	
	/**
	 * 经销商信息新增
	 * @param agencyInfo
	 * @throws BusinessException
	 */
	public Integer insertAgency(TOriginAgencyCompany agencyInfo) throws BusinessException;
	
	/**
	 * 经销商信息修改
	 * @param agencyInfo
	 * @throws BusinessException
	 */
	public void updateAgency(TOriginAgencyCompany agencyInfo) throws BusinessException;	
	
	/**
	 * 判断这个商户是否存在
	 * @param agencyName
	 * @param linkPhone
	 * @return
	 * @throws BusinessException
	 */
	public Integer getAgencyInfo(String agencyName,String linkPhone) throws BusinessException;
	
	/**
	 * 根据基础数据企业id查询商户信息
	 * @param enterpriseId
	 * @return
	 * @throws BusinessException
	 */
	public TOriginAgencyCompany getAgencyFromEnterprise(Integer enterpriseId) throws BusinessException;
}
