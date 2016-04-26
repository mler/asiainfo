package com.bdx.rainbow.service.ydzf.monitor;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.dto.ydzf.form.YdzfMonitorPlanAddForm;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanListInfoResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanListNumResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanListResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlan;
import com.bdx.rainbow.urs.entity.IUserInfo;

import java.util.List;

public interface IYDZFMonitorPlanService {
	
	

	/**
	 *  查询日常监督计划数目列表
	 * @param deptId
	 * @param checkPlanType(null查询所有)
	 * @param checkPlanStatus(null查询所有)
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<YdzfMonitorPlanListNumResultVO> queryPlanListNum(int deptId,String checkPlanType,String checkPlanStatus)throws SystemException,BusinessException;
	
	/**
	 * 修改日常监督计划
	 * @param ydzfMonitorPlanForm
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
    public  int updateMonitorPlan(YdzfMonitorPlanAddForm ydzfMonitorPlanForm)throws BusinessException,SystemException;
	
    
	/**
	 * 新增日常监督计划
	 * @param ydzfDayCheckPlanForm
	 * @return
	 */
    public int addMonitorPlan(YdzfMonitorPlanAddForm dzfMonitorPlanForm) throws BusinessException,SystemException;

	/**
	 * 查询日常监督计划列表
	 * @param ydzfMonitorPlan
	 * @param start
	 * @param limit
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public List<YdzfMonitorPlanListResultVO> queryMonitorPlanList(TYdzfMonitorPlan ydzfMonitorPlan,Integer start,
			Integer limit)throws BusinessException,SystemException;
	
	
	/**
	 *  查询日常监督计划列表纪录总数
	 * @param ydzfMonitorPlan
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int  countMonitorPlanList(TYdzfMonitorPlan ydzfMonitorPlan)throws BusinessException,SystemException;
	

	/**
	 * 查询日常监督计划明细列表
	 * @return
	 */
	public YdzfMonitorPlanListInfoResultVO queryMonitorPlanListInfo(Integer monitorPlanId,String checkPlanType)throws BusinessException,SystemException;
	
	/**
	 * 删除日常监督计划
	 * @param userInfo
	 * @param monitorPlanIds
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	public int delMonitor(IUserInfo userInfo,List<Integer> monitorPlanIds)throws BusinessException,SystemException;

}
