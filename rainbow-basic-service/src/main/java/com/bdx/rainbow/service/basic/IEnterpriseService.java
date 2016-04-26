package com.bdx.rainbow.service.basic;


import java.util.List;

import com.bdx.rainbow.basic.dubbo.service.IEnterpriseDubboService;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.basic.mysql.TBasicEnterpriseInfo;
import com.bdx.rainbow.entity.basic.mysql.TBasicEnterpriseInfoExample;
import com.bdx.rainbow.entity.basic.mysql.TBasicLicense;

public interface IEnterpriseService extends IEnterpriseDubboService{
	
	/**
	 * 根据组织机构代码号 获得唯一企业信息
	 * @param organizationCode
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public TBasicEnterpriseInfo getEnterpriseInfoByOrganizationCode(String organizationCode)throws Exception;
	
	/**
	 * 根据ID获得企业信息
	 * @param enterpriseId
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public TBasicEnterpriseInfo getEnterpriseInfoById(Integer enterpriseId)throws Exception;
	
	/**
	 * 保存证书
	 * @param license
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public void saveLicense(List<TBasicLicense> licenses,String userLoginName)throws Exception;
	
	/**
	 * 根据证书号获得证书
	 * @param enterpriseId
	 * @param licenseCode
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public TBasicLicense getLicenseByCode(Integer enterpriseId,String licenseCode)throws Exception;
	
	
	/**
	 * 根据企业ID获得证书
	 * @param enterpriseId
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<TBasicLicense> getEnterpriseLicenses(Integer enterpriseId)throws Exception;
	
	/**
	 * 通过企业ID list获得企业对象
	 * @param enterpriseIds
	 * @return
	 * @throws Exception
	 */
	public List<TBasicEnterpriseInfo> getEnterpriseInfoByIds(List<Integer> enterpriseIds) throws Exception;
}
