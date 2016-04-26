package com.bdx.rainbow.service.ydzf.monitor.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.core.utils.StringMyUtils;
import com.bdx.rainbow.dto.ydzf.form.YdzfMonitorPlanTaskInfoAddForm;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanRelEnterpriseVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanTaskInfoResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlan;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlanRel;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTaskInfo;
import com.bdx.rainbow.mapper.ydzf.TYdzfMonitorPlanMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfMonitorPlanRelMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfMonitorTaskInfoMapper;
import com.bdx.rainbow.mapper.ydzf.self.TYdzfMonitorPlanSelfMapper;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorPlanRelService;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorTaskService;
import com.bdx.rainbow.urs.entity.IUserInfo;

@Service("ydzfMonitorTaskService")
public class YDZFMonitorTaskService extends BaseService implements
		IYDZFMonitorTaskService {
	private static final Logger log = LoggerFactory
			.getLogger(YDZFMonitorTaskService.class);

	@Autowired
	protected TYdzfMonitorPlanMapper ydzfMonitorPlanMapper;
	@Autowired
	protected TYdzfMonitorPlanRelMapper ydzfMonitorPlanRelMapper;
	@Autowired
	protected TYdzfMonitorTaskInfoMapper ydzfMonitorTaskInfoMapper;
	@Autowired
	protected TYdzfMonitorPlanSelfMapper ydzfMonitorPlanSelfMapper;
	@Autowired
	protected IYDZFCommonService ydzfCommonService;
	@Autowired
	protected IYDZFMonitorPlanRelService ydzfMonitorPlanRelService;

	@Override
	public YdzfMonitorPlanTaskInfoResultVO queryMonitorPlanTaskInfo(
			int monitorTaskId) throws BusinessException, SystemException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("monitorTaskId", monitorTaskId);
		YdzfMonitorPlanTaskInfoResultVO taskInfoResultVO = ydzfMonitorPlanSelfMapper
				.queryMonitorPlanTaskInfo(paramMap);
		YdzfMonitorPlanRelEnterpriseVO enterpriseVO = taskInfoResultVO
				.getYdzfMonitorPlanRelEnterpriseVO();
		enterpriseVO.setManageEnterpriseName(ydzfCommonService
				.getEnterpriseNameByIdDubbo(enterpriseVO
						.getManageEnterpriseId()));
		try {
			List<String> fileIdList = StringMyUtils.transArrayToListString(
					taskInfoResultVO.getYdzfMonitorPlanRelEnterpriseVO()
							.getFileIds(), ",");
			if (fileIdList != null) {
				enterpriseVO.setFileidHttpUrlMap(ydzfCommonService
						.getFileHttpUrlsDubbo(fileIdList));
			}
		} catch (Exception e) {
			log.error("获取文件异常", e);
			throw new SystemException(new DefaultExceptionCode("获取文件异常"));
		}
		try {
			List<String> checkSignFileIdList = StringMyUtils.transArrayToListString(
					taskInfoResultVO.getYdzfMonitorPlanRelEnterpriseVO()
							.getCheckSignFileIds(), ",");
			if (checkSignFileIdList != null) {
				enterpriseVO.setCheckSignFileIdsHttpUrlMap(ydzfCommonService
						.getFileHttpUrlsDubbo(checkSignFileIdList));
			}
		} catch (Exception e) {
			log.error("获取文件异常", e);
			throw new SystemException(new DefaultExceptionCode("获取文件异常"));
		}
		
		try {
			List<String> enterpriseSignFileIdList = StringMyUtils.transArrayToListString(
					taskInfoResultVO.getYdzfMonitorPlanRelEnterpriseVO()
							.getEnterpriseSignFileIds(), ",");
			if (enterpriseSignFileIdList != null) {
				enterpriseVO.setEnterpriseSignFileIdsHttpUrlMap(ydzfCommonService
						.getFileHttpUrlsDubbo(enterpriseSignFileIdList));
			}
		} catch (Exception e) {
			log.error("获取文件异常", e);
			throw new SystemException(new DefaultExceptionCode("获取文件异常"));
		}

		return taskInfoResultVO;

	}

	@Override
	@Transactional
	public int updateMonitorPlanTaskDetailInfo(
			YdzfMonitorPlanTaskInfoAddForm taskInfoAddForm)
			throws BusinessException, SystemException {
		if (taskInfoAddForm == null || taskInfoAddForm.getTaskInfo() == null) {
			throw new SystemException(new DefaultExceptionCode("参数异常"));
		}

		TYdzfMonitorTaskInfo taskInfo = taskInfoAddForm.getTaskInfo();
		if (taskInfo.getCheckTaskUserId() == null
				|| taskInfo.getCheckTaskStatus() == null) {
			throw new SystemException(new DefaultExceptionCode("参数异常"));
		}
		Timestamp currentDate = DateUtil.getCurrent();
		taskInfo.setUpdateDate(currentDate);
		taskInfo.setUpdateUserid(taskInfo.getCheckTaskUserId());
		ydzfMonitorTaskInfoMapper.updateByPrimaryKeySelective(taskInfo);
		// 关联更新，监督管理关联表，日常监督管理
		this.updateTaskInfoRel(taskInfo, currentDate);
		return taskInfo.getMonitorTaskId();
	}

	/**
	 * 如果标志完成：则关联更新，监督管理关联表，日常监督管理
	 * 
	 * @param taskInfo
	 * @param currentDate
	 * @throws BusinessException
	 * @throws SystemException
	 */
	private void updateTaskInfoRel(TYdzfMonitorTaskInfo taskInfo,
			Timestamp currentDate) throws BusinessException, SystemException {

		if (taskInfo.getCheckTaskStatus().equals(
				YDZFConstants.MONITOR.TASK_STATUS_EXECUTE_SUBMIT)) {
			boolean planStatusFininshFlag = true;
			TYdzfMonitorPlanRel addPlanRel = new TYdzfMonitorPlanRel();
			addPlanRel.setMonitorPlanRelId(taskInfo.getMonitorPlanRelId());
			addPlanRel
					.setManageItemStatus(YDZFConstants.MONITOR.PLAN_REL_STATUS_EXECUTE_FINISH);
			addPlanRel.setUpdateDate(currentDate);
			addPlanRel.setUpdateUserid(taskInfo.getCheckTaskUserId());
			ydzfMonitorPlanRelMapper.updateByPrimaryKeySelective(addPlanRel);
			TYdzfMonitorPlanRel queryPlanRel = new TYdzfMonitorPlanRel();
			queryPlanRel.setMonitorPlanId(taskInfo.getMonitorPlanId());
			List<TYdzfMonitorPlanRel> planRelList = ydzfMonitorPlanRelService
					.queryMonitorPlanRelListAll(queryPlanRel);
			for (TYdzfMonitorPlanRel planRel : planRelList) {

				if (planRel.getManageItemStatus().equals(
						YDZFConstants.MONITOR.PLAN_REL_STATUS_EXECUTE_WAIT)) {
					planStatusFininshFlag = false;
					break;
				}
			}
			if (planStatusFininshFlag) {
				// 更新计划完成
				TYdzfMonitorPlan plan = new TYdzfMonitorPlan();
				plan.setMonitorPlanId(taskInfo.getMonitorPlanId());
				plan.setCheckPlanStatus(YDZFConstants.MONITOR.PLAN_STATUS_EXECUTE_FINISH);
				plan.setCheckPlanFinishDate(currentDate);
				ydzfMonitorPlanMapper.updateByPrimaryKeySelective(plan);
			}

		}
	}

	@Override
	@Transactional
	public int delMonitorPlanTaskByPlanId(IUserInfo userInfo,
			Integer monitorPlanId) throws BusinessException, SystemException {
		// TODO Auto-generated method stub
		return 0;
	}

}
