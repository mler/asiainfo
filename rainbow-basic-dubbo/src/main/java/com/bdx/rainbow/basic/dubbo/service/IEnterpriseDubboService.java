package com.bdx.rainbow.basic.dubbo.service;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseLicense;
import com.bdx.rainbow.basic.dubbo.bean.DubboLicense;

public interface IEnterpriseDubboService {
	
	/**
	 * 分页查询企业信息
	 * @param condition
	 * @param start
	 * @param limit
	 * @return Map.list  Map.total
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public Map<String, Object> getEnterpriseInfos(DubboEnterpriseInfo condition,Integer start,Integer limit)throws Exception;
	
	/**
	 * 获得企业详细信息
	 * @param enterpriseId
	 * @return Map.info(DubboEnterpriseInfo)  Map.licenses(List<DubboLicense>)
	 * @throws Exception
	 */
	public Map<String, Object> getEnterpriseDetailById(Integer enterpriseId)throws Exception;
	
	/**
	 * 根据enterpriseInfo.enterpriseInfoId是否有值 或 组织机构代码enterprise.organizationCode 的唯一性 来决定insert or update
	 * 注：组织机构代码于企业是唯一的
	 * @param enterpriseInfo
	 * @param userLoginName
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public Integer saveEnterpriseInfo(DubboEnterpriseInfo enterpriseInfo,String userLoginName,List<DubboLicense> licenses)throws Exception;
	
	/**
	 * 通过企业ID获得企业对象列表
	 * @param enterpriseIds
	 * @return
	 * @throws Exception
	 */
	public List<DubboEnterpriseInfo> getDubboEnterpriseInfoByIds(List<Integer> enterpriseIds)throws Exception;
	
	/**
	 * 获得企业与许可证的笛卡尔积
	 * @param condition
	 * @param start
	 * @param limit
	 * @return Map.list  Map.total
	 * @throws Exception
	 */
	public Map<String, Object> getMoreEnterpriseLicense(DubboEnterpriseLicense condition,Integer start,Integer limit)throws Exception;
	
}
