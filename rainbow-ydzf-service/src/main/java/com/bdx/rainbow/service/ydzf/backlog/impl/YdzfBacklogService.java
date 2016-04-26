package com.bdx.rainbow.service.ydzf.backlog.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.dto.ydzf.vo.YdzfBackLogListInfoVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidence;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseExecutePunish;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseReadyPunish;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlan;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlanRel;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTaskInfo;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseEvidenceMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseExecutePunishMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseReadyPunishMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfMonitorPlanMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfMonitorPlanRelMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfMonitorTaskInfoMapper;
import com.bdx.rainbow.mapper.ydzf.self.TYdzfBackLogSelfMapper;
import com.bdx.rainbow.service.ydzf.backlog.IYDZFBacklogService;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceService;
import com.bdx.rainbow.urs.entity.IUserInfo;

@Service("ydzfBacklogService")
public class YdzfBacklogService extends BaseService implements IYDZFBacklogService {
	private static final Logger log = LoggerFactory
			.getLogger(YdzfBacklogService.class);
	@Autowired
	protected TYdzfBackLogSelfMapper ydzfBackLogSelfMapper;
	@Autowired
	protected TYdzfMonitorPlanMapper ydzfMonitorPlanMapper;
	@Autowired
	protected TYdzfMonitorTaskInfoMapper ydzfMonitorTaskInfoMapper;
	@Autowired
	protected TYdzfMonitorPlanRelMapper ydzfMonitorPlanRelMapper;
	@Autowired
	protected TYdzfInspectCaseMapper ydzfInspectCaseMapper;
	@Autowired
	protected TYdzfInspectCaseEvidenceMapper ydzfInspectCaseEvidenceMapper;
	@Autowired
	protected TYdzfInspectCaseExecutePunishMapper ydzfInspectCaseExecutePunishMapper;
	@Autowired
	protected TYdzfInspectCaseReadyPunishMapper ydzfInspectCaseReadyPunishMapper;
	@Autowired
	protected IYDZFInspectCaseEvidenceService ydzfInspectCaseEvidenceService;
	@Autowired
	protected IYDZFCommonService ydzfCommonService;

	@Override
	public List<YdzfBackLogListInfoVO> queryYdzfBackLogListInfo(IUserInfo userInfo,
			String saveStatus, int start, int limit) throws BusinessException,
			SystemException {
		Map<String,Object> paramMap= new HashMap<String,Object>();
		paramMap.put("userId", userInfo.getUserId());
		paramMap.put("taskStatus", saveStatus);
		paramMap.put("punishStatus", saveStatus);
		paramMap.put("limitClauseStart", start);
		paramMap.put("limitClauseCount", limit);
		List<Map<String,Object>> resultList=ydzfBackLogSelfMapper.queryBackLogListInfo(paramMap);
		//id,relId,type
		if(CollectionUtils.isEmpty(resultList))
		{
			return null;
		}
		else
		{
			List<YdzfBackLogListInfoVO> volist= new ArrayList<YdzfBackLogListInfoVO>();
			for(Map resultMap :resultList )
			{
				YdzfBackLogListInfoVO vo = new YdzfBackLogListInfoVO();
				if(resultMap.get("backLogType").toString().equals("monitor"))
				{
					//日常监督
					int monitorPlanId=Integer.parseInt(resultMap.get("id").toString());
					int monitorTaskId=Integer.parseInt(resultMap.get("relId").toString());
					TYdzfMonitorPlan ydzfMonitorPlan=ydzfMonitorPlanMapper.selectByPrimaryKey(monitorPlanId);
					BeanUtils.copyProperties(ydzfMonitorPlan, vo);
					TYdzfMonitorTaskInfo ydzfMonitorTaskInfo=ydzfMonitorTaskInfoMapper.selectByPrimaryKey(monitorTaskId);
					vo.setMonitorTaskId(monitorTaskId);
					vo.setCheckTaskResult(ydzfMonitorTaskInfo.getCheckTaskResult());
					vo.setCheckTaskStatus(ydzfMonitorTaskInfo.getCheckTaskStatus());
					//
					TYdzfMonitorPlanRel ydzfMonitorPlanRel=ydzfMonitorPlanRelMapper.selectByPrimaryKey(ydzfMonitorTaskInfo.getMonitorPlanRelId());
					vo.setUpdateUserid(ydzfMonitorTaskInfo.getUpdateUserid());
					vo.setUpdateDate(ydzfMonitorTaskInfo.getUpdateDate());
					vo.setManageEnterpriseId(ydzfMonitorPlanRel.getManageEnterpriseId());
					vo.setEnterpriseName(this.ydzfCommonService.getEnterpriseNameByIdDubbo(ydzfMonitorPlanRel.getManageEnterpriseId()));
					vo.setBackLogType("monitor");
				}
				else if(resultMap.get("backLogType").toString().equals("inspect"))
				{
					//稽查办案
					int inspectCaseId=Integer.parseInt(resultMap.get("id").toString());
					int inspectCaseExecutePunishId=Integer.parseInt(resultMap.get("relId").toString());
					//
					TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseMapper.selectByPrimaryKey(inspectCaseId);
					vo.setInspectCaseName(ydzfInspectCase.getInspectCaseName());
					vo.setInspectCaseId(inspectCaseId);
					vo.setInspectCaseExecutePunishId(inspectCaseExecutePunishId);
					TYdzfInspectCaseExecutePunish  ydzfInspectCaseExecutePunish=ydzfInspectCaseExecutePunishMapper.selectByPrimaryKey(inspectCaseExecutePunishId);
					int inspectCaseReadyPunishId=ydzfInspectCaseExecutePunish.getInspectCaseReadyPunishId();
					vo.setExecuteStatus(ydzfInspectCaseExecutePunish.getExecuteStatus());
					TYdzfInspectCaseReadyPunish ydzfInspectCaseReadyPunish=ydzfInspectCaseReadyPunishMapper.selectByPrimaryKey(inspectCaseReadyPunishId);
					vo.setPunishAdvide(ydzfInspectCaseReadyPunish.getPunishAdvice());
					TYdzfInspectCaseEvidence ydzfInspectCaseEvidence=ydzfInspectCaseEvidenceService.queryInspectCaseEvidenceInfoByInspectId(inspectCaseId);
					vo.setEvidenceResult(ydzfInspectCaseEvidence.getEvidenceResult());
					//
					vo.setUpdateUserid(ydzfInspectCaseExecutePunish.getUpdateUserid());
					vo.setUpdateDate(ydzfInspectCaseExecutePunish.getUpdateDate());
					vo.setInspectCaseEnterpriseId(ydzfInspectCase.getInspectCaseEnterpriseId());
					vo.setEnterpriseName(this.ydzfCommonService.getEnterpriseNameByIdDubbo(ydzfInspectCase.getInspectCaseEnterpriseId()));
					vo.setBackLogType("inspect");
					
				}
				volist.add(vo);
			}
			return volist;
		}

		
		
		
	}

	@Override
	public int querySubmitCount(IUserInfo userInfo) {
		Map<String,Object> paramMap= new HashMap<String,Object>();
		paramMap.put("userId", userInfo.getUserId());
		paramMap.put("taskStatus", YDZFConstants.MONITOR.TASK_STATUS_EXECUTE_SUBMIT);
		paramMap.put("punishStatus", YDZFConstants.INSPECTCASE.EXECUTE_PUNISH_STATUS_SUBMIT);
		return ydzfBackLogSelfMapper.queryBackLogListNum(paramMap);
	}

	@Override
	public int querySaveCount(IUserInfo userInfo) {
		Map<String,Object> paramMap= new HashMap<String,Object>();
		paramMap.put("userId", userInfo.getUserId());
		paramMap.put("taskStatus", YDZFConstants.MONITOR.TASK_STATUS_EXECUTE_SAVE);
		paramMap.put("punishStatus", YDZFConstants.INSPECTCASE.EXECUTE_PUNISH_STATUS_SAVE);
		return ydzfBackLogSelfMapper.queryBackLogListNum(paramMap);
	}

}
