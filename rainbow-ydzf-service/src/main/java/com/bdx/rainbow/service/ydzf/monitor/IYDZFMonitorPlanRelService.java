package com.bdx.rainbow.service.ydzf.monitor;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlanRel;
import com.bdx.rainbow.urs.entity.IUserInfo;

public interface IYDZFMonitorPlanRelService {
	
	
	/**
	 * 查询监督管理关联表
	 * @param planRel
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public List<TYdzfMonitorPlanRel> queryMonitorPlanRelListAll(TYdzfMonitorPlanRel planRel)throws BusinessException,SystemException;
	
	/**
	 * 根据计划ID批量删除
	 * @param userInfo
	 * @param monitorPlanId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int delMonitorPlanRelByPlanId(IUserInfo userInfo,Integer monitorPlanId)throws BusinessException,SystemException;
	

}
