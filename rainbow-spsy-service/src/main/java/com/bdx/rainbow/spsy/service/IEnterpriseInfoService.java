package com.bdx.rainbow.spsy.service;

import com.bdx.rainbow.spsy.bean.EnterpriseInfo;

/**
 * 企业信息的操作
 * @author tanglian 2016-01-28
 *
 */
public interface IEnterpriseInfoService {
    /**
     * 查询当前用户所属的企业
     * @param EnterpriseId
     * @return
     * @throws Exception
     */
	public EnterpriseInfo getEnterpriseInfo(Integer EnterpriseId) throws Exception;
	
	/**
	 * 修改或新增企业信息
	 * @param enterpriseInfo
	 * @param userLoginName
	 * @throws Exception
	 */
	public void updateEnterpriseInfo(EnterpriseInfo enterpriseInfo,String userLoginName) throws Exception;
	
}
