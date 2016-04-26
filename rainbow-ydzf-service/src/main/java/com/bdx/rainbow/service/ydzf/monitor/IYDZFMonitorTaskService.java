package com.bdx.rainbow.service.ydzf.monitor;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.form.YdzfMonitorPlanTaskInfoAddForm;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanTaskInfoResultVO;
import com.bdx.rainbow.urs.entity.IUserInfo;

public interface IYDZFMonitorTaskService {
	
	/**
	 * 更新任务详情
	 * @param taskInfoAddForm
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int updateMonitorPlanTaskDetailInfo(YdzfMonitorPlanTaskInfoAddForm taskInfoAddForm)throws BusinessException,SystemException;
	
	
	
	
	/**
	 * 查询日常监督任务，任务详情
	 * @param monitorTaskId
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public  YdzfMonitorPlanTaskInfoResultVO queryMonitorPlanTaskInfo(int monitorTaskId)throws BusinessException,SystemException;
	
	
	
	/**
	 * 根据计划ID批量删除
	 * @param userInfo
	 * @param monitorPlanId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int delMonitorPlanTaskByPlanId(IUserInfo userInfo,Integer monitorPlanId)throws BusinessException,SystemException;
	

}
