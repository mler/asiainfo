package com.bdx.rainbow.service.ydzf.inspect.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.JSONObject;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.dto.ydzf.form.YdzfInspectCaseAddForm;
import com.bdx.rainbow.dto.ydzf.form.YdzfInspectCaseListQueryInfo;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseAuditResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseExecutePunishResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseFinishResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseListResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseReadyPunishResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseWitnessesResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseAudit;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidence;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseExample;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseExecutePunish;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseFinish;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseReadyPunish;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseWitnesses;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseMapper;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseAuditService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseExecutePunishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseFinishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseReadyPunishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseWitnessesService;
import com.fasterxml.jackson.databind.ObjectMapper;



@Service("ydzfInspectCaseService")
public class YDZFInspectCaseService extends BaseService implements IYDZFInspectCaseService{
	
	private static final Logger log = LoggerFactory
			.getLogger(YDZFInspectCaseService.class);
	@Autowired
	protected TYdzfInspectCaseMapper ydzfInspectCaseMapper;
	@Autowired
	protected IYDZFInspectCaseEvidenceService ydzfInspectCaseEvidenceService;
	@Autowired
	protected IYDZFInspectCaseReadyPunishService ydzfInspectCaseReadyPunishService;
	@Autowired
	protected IYDZFInspectCaseExecutePunishService ydzfInspectCaseExecutePunishService;
	@Autowired
	protected IYDZFInspectCaseAuditService ydzfInspectCaseAuditService;
	@Autowired
	protected IYDZFInspectCaseWitnessesService ydzfInspectCaseWitnessesService;
	@Autowired
	protected IYDZFInspectCaseFinishService ydzfInspectCaseFinishService;
	
	
	
	
	
	
	@Autowired
	protected IYDZFCommonService ydzfCommonService;

	@Override
	@Transactional
	public int addInspectCase(YdzfInspectCaseAddForm ydzfInspectCaseAddForm)
			throws BusinessException, SystemException {
		TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseAddForm.getYdzfInspectCase();
		ydzfInspectCaseMapper.insert(ydzfInspectCase);
		return ydzfInspectCase.getInspectCaseId();
	}


	@Override
	public List<YdzfInspectCaseListResultVO> queryInspectCaseList(
			YdzfInspectCaseListQueryInfo ydzfInspectCaseListQueryInfo, Integer start, Integer limit)
			throws BusinessException, SystemException {
		TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseListQueryInfo.getYdzfInspectCase();
		List<YdzfInspectCaseListResultVO> voList = new ArrayList<YdzfInspectCaseListResultVO>();
		TYdzfInspectCaseExample example = this.getCondition(ydzfInspectCase);
        if (ydzfInspectCaseListQueryInfo.getStartTime()!=null&&org.apache.commons.lang.StringUtils.isNotBlank(ydzfInspectCaseListQueryInfo.getStartTime())){
            if(example.getOredCriteria()!=null&&example.getOredCriteria().size()>0){
                example.getOredCriteria().get(0).andInspectCaseCreatDateGreaterThanOrEqualTo(DateUtil.getTimestamp(ydzfInspectCaseListQueryInfo.getStartTime()));
            }else{
                example.createCriteria().andInspectCaseCreatDateGreaterThanOrEqualTo(DateUtil.getTimestamp(ydzfInspectCaseListQueryInfo.getStartTime()));
            }
        }
        if (ydzfInspectCaseListQueryInfo.getEndTime()!=null&&org.apache.commons.lang.StringUtils.isNotBlank(ydzfInspectCaseListQueryInfo.getEndTime())){
            if(example.getOredCriteria()!=null&&example.getOredCriteria().size()>0){
                example.getOredCriteria().get(0).andInspectCaseCreatDateLessThanOrEqualTo(DateUtil.getTimestamp(ydzfInspectCaseListQueryInfo.getEndTime()));
            }else{
                example.createCriteria().andInspectCaseCreatDateLessThanOrEqualTo(DateUtil.getTimestamp(ydzfInspectCaseListQueryInfo.getEndTime()));
            }
        }
		if (start!=null&&start >= 0 && limit!=null&&limit > 0) {
			example.setLimitClauseStart(start);
			example.setLimitClauseCount(limit);
		}
		example.setOrderByClause(" CREATE_DATE desc ");
		List<TYdzfInspectCase> inspectCaseList = ydzfInspectCaseMapper
				.selectByExample(example);
		for (TYdzfInspectCase ydzfInspectCaseResult : inspectCaseList) {
			YdzfInspectCaseListResultVO vo = new YdzfInspectCaseListResultVO();
			vo.setYdzfInspectCase(ydzfInspectCaseResult);
			vo.setManageEnterpriseName(ydzfCommonService.getEnterpriseNameByIdDubbo(ydzfInspectCaseResult.getInspectCaseEnterpriseId()));
			voList.add(vo);
		}
		return voList;
		
	}
	
	@Override
	public int countInspectCaseList(YdzfInspectCaseListQueryInfo ydzfInspectCaseListQueryInfo)
			throws BusinessException, SystemException {
		TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseListQueryInfo.getYdzfInspectCase();
		TYdzfInspectCaseExample example = this.getCondition(ydzfInspectCase);
        if (ydzfInspectCaseListQueryInfo.getStartTime()!=null&&org.apache.commons.lang.StringUtils.isNotBlank(ydzfInspectCaseListQueryInfo.getStartTime())){
            if(example.getOredCriteria()!=null&&example.getOredCriteria().size()>0){
                example.getOredCriteria().get(0).andInspectCaseCreatDateGreaterThanOrEqualTo(DateUtil.getTimestamp(ydzfInspectCaseListQueryInfo.getStartTime()));
            }else{
                example.createCriteria().andInspectCaseCreatDateGreaterThanOrEqualTo(DateUtil.getTimestamp(ydzfInspectCaseListQueryInfo.getStartTime()));
            }
        }
        if (ydzfInspectCaseListQueryInfo.getEndTime()!=null&&org.apache.commons.lang.StringUtils.isNotBlank(ydzfInspectCaseListQueryInfo.getEndTime())){
            if(example.getOredCriteria()!=null&&example.getOredCriteria().size()>0){
                example.getOredCriteria().get(0).andInspectCaseCreatDateLessThanOrEqualTo(DateUtil.getTimestamp(ydzfInspectCaseListQueryInfo.getEndTime()));
            }else{
                example.createCriteria().andInspectCaseCreatDateLessThanOrEqualTo(DateUtil.getTimestamp(ydzfInspectCaseListQueryInfo.getEndTime()));
            }
        }
		return ydzfInspectCaseMapper.countByExample(example);
		
				
	}
	
	

	@Override
	@Transactional
	public int updateInspectCase(TYdzfInspectCase ydzfInspectCase)
			throws BusinessException, SystemException {
		ydzfInspectCaseMapper.updateByPrimaryKeySelective(ydzfInspectCase);
		return ydzfInspectCase.getInspectCaseId();
	}
	
	@Override
	public YdzfInspectCaseListResultVO queryInspectCaseInfoAndRel(
			Integer inspectCaseId) throws BusinessException, SystemException {
		YdzfInspectCaseListResultVO vo = new YdzfInspectCaseListResultVO();
		TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseMapper.selectByPrimaryKey(inspectCaseId);
		if(ydzfInspectCase==null)
		{
			return vo;
		}
		vo.setYdzfInspectCase(ydzfInspectCase);
		vo.setManageEnterpriseName(ydzfCommonService.getEnterpriseNameByIdDubbo(ydzfInspectCase.getInspectCaseEnterpriseId()));
		vo.setInspectCaseRelInfoJson(this.bulidInspectCaseRelInfoJson(this.bulidInspectCaseRelInfoList(ydzfInspectCase)));
		return vo;
		
	}
	
	
	//{"status_info": [{"status": "1","id":"1", "datetime": "2016-01-01 12:00:00"}, {"status": "2","id":"1", "datetime": "2016-01-01 12:00:00"} ] }
	private List<Map<String,String>> bulidInspectCaseRelInfoList(TYdzfInspectCase ydzfInspectCase) throws BusinessException, SystemException
	{
		Integer inspectCaseId =ydzfInspectCase.getInspectCaseId();
		List<Map<String,String>> resultList= new ArrayList<Map<String,String>>();
		Map<String,String> waitMap= new HashMap<String,String>();
		//稽查办案立案
		waitMap.put("status", YDZFConstants.INSPECTCASE.INSPECT_STATUS_WAIT);
		waitMap.put("id", inspectCaseId.toString());
		waitMap.put("datetime", DateUtil.getStryyyyMMddHH24Miss(ydzfInspectCase.getCreateDate()));
		resultList.add(waitMap);
		// 稽查案件执行状态－－1：调查取证
		TYdzfInspectCaseEvidence ydzfInspectCaseEvidence= new TYdzfInspectCaseEvidence();
		ydzfInspectCaseEvidence.setInspectCaseId(inspectCaseId);
		List<TYdzfInspectCaseEvidence> evidenceList=ydzfInspectCaseEvidenceService.queryInspectCaseEvidenceList(ydzfInspectCaseEvidence);
		if(CollectionUtils.isEmpty(evidenceList))
		{
			return resultList;
		}
		else
		{
			ydzfInspectCaseEvidence=evidenceList.get(0);
			Map<String,String> evidenceMap= new HashMap<String,String>();
			evidenceMap.put("status", YDZFConstants.INSPECTCASE.INSPECT_STATUS_EVIDENCE);
			evidenceMap.put("id", ydzfInspectCaseEvidence.getInspectCaseEvidenceId().toString());
			evidenceMap.put("datetime", DateUtil.getStryyyyMMddHH24Miss(ydzfInspectCaseEvidence.getCreateDate()));
			resultList.add(evidenceMap);
		}
		// 稽查案件执行状态－－2：拟处罚
		TYdzfInspectCaseReadyPunish ydzfInspectCaseReadyPunish= new TYdzfInspectCaseReadyPunish();
		ydzfInspectCaseReadyPunish.setInspectCaseId(inspectCaseId);
		List<YdzfInspectCaseReadyPunishResultVO> readyList=ydzfInspectCaseReadyPunishService.queryInspectCaseReadyPunishInfoList(ydzfInspectCaseReadyPunish);
		if(CollectionUtils.isEmpty(readyList))
		{
			return resultList;
		}
		else
		{
			ydzfInspectCaseReadyPunish=readyList.get(0).getYdzfInspectCaseReadyPunish();
			Map<String,String> readyMap= new HashMap<String,String>();
			readyMap.put("status", YDZFConstants.INSPECTCASE.INSPECT_STATUS_READY_PUNISH);
			readyMap.put("id", ydzfInspectCaseReadyPunish.getInspectCaseReadyPunishId().toString());
			readyMap.put("datetime", DateUtil.getStryyyyMMddHH24Miss(ydzfInspectCaseReadyPunish.getCreateDate()));
			resultList.add(readyMap);
		}
		// 稽查案件执行状态－－3：案件审核
		TYdzfInspectCaseAudit ydzfInspectCaseAudit= new TYdzfInspectCaseAudit();
		ydzfInspectCaseAudit.setInspectCaseId(inspectCaseId);
		List<YdzfInspectCaseAuditResultVO> auditList=ydzfInspectCaseAuditService.queryInspectCaseAuditInfoVOList(ydzfInspectCaseAudit);
		if(CollectionUtils.isEmpty(auditList))
		{
			//则无案件听证，有可能含有案件执行处罚
		}
		else
		{
			ydzfInspectCaseAudit=auditList.get(0).getYdzfInspectCaseAudit();
			Map<String,String> auditMap= new HashMap<String,String>();
			auditMap.put("status", YDZFConstants.INSPECTCASE.INSPECT_STATUS_AUDIT);
			auditMap.put("id", ydzfInspectCaseAudit.getInspectCaseAuditId().toString());
			auditMap.put("datetime", DateUtil.getStryyyyMMddHH24Miss(ydzfInspectCaseAudit.getCreateDate()));
			resultList.add(auditMap);
			
			// 稽查案件执行状态－－4：听证
			TYdzfInspectCaseWitnesses ydzfInspectCaseWitnesses= new TYdzfInspectCaseWitnesses();
			ydzfInspectCaseWitnesses.setInspectCaseId(inspectCaseId);
			List<YdzfInspectCaseWitnessesResultVO> witnessesList=ydzfInspectCaseWitnessesService.queryInspectCaseWitnessesInfoVOList(ydzfInspectCaseWitnesses);
			if(CollectionUtils.isEmpty(witnessesList))
			{
				//有可能含有案件执行处罚
			}
			else
			{
				ydzfInspectCaseWitnesses=witnessesList.get(0).getYdzfInspectCaseWitnesses();
				Map<String,String> witnessesMap= new HashMap<String,String>();
				witnessesMap.put("status", YDZFConstants.INSPECTCASE.INSPECT_STATUS_WITNESSES);
				witnessesMap.put("id", ydzfInspectCaseWitnesses.getInspectCaseWitnessesId().toString());
				witnessesMap.put("datetime", DateUtil.getStryyyyMMddHH24Miss(ydzfInspectCaseWitnesses.getCreateDate()));
				resultList.add(auditMap);
			}
		}
		// 稽查案件执行状态－－5：执行处罚(保存),6.提交
		TYdzfInspectCaseExecutePunish ydzfInspectCaseExecutePunish= new TYdzfInspectCaseExecutePunish();
		ydzfInspectCaseExecutePunish.setInspectCaseId(inspectCaseId);
		List<YdzfInspectCaseExecutePunishResultVO> executeList=ydzfInspectCaseExecutePunishService.queryExecutePunishInfoVOList(ydzfInspectCaseExecutePunish);
		if(CollectionUtils.isEmpty(executeList))
		{
			return resultList;
		}
		else
		{
			ydzfInspectCaseExecutePunish=executeList.get(0).getYdzfInspectCaseExecutePunish();
			Map<String,String> executeMap= new HashMap<String,String>();
			if(ydzfInspectCaseExecutePunish.getExecuteStatus().equals(YDZFConstants.INSPECTCASE.EXECUTE_PUNISH_STATUS_SUBMIT))
			{
				executeMap.put("status", YDZFConstants.INSPECTCASE.INSPECT_STATUS_EXECUTE_PUNISH_SUBMIT);
				executeMap.put("id", ydzfInspectCaseExecutePunish.getInspectCaseExecutePunishId().toString());
				executeMap.put("datetime", DateUtil.getStryyyyMMddHH24Miss(ydzfInspectCaseExecutePunish.getCreateDate()));
				resultList.add(executeMap);
			}
			else
			{
				return resultList;
			}
			
		}
		// 稽查案件执行状态－－7：结案
		TYdzfInspectCaseFinish ydzfInspectCaseFinish= new TYdzfInspectCaseFinish();
		ydzfInspectCaseFinish.setInspectCaseId(inspectCaseId);
		List<YdzfInspectCaseFinishResultVO> finshList=ydzfInspectCaseFinishService.queryInspectCaseFinishInfoVOList(ydzfInspectCaseFinish);
		if(CollectionUtils.isEmpty(finshList))
		{
			return resultList;
		}
		else
		{
			ydzfInspectCaseFinish=finshList.get(0).getYdzfInspectCaseFinish();
			Map<String,String> finshMap= new HashMap<String,String>();
			finshMap.put("status", YDZFConstants.INSPECTCASE.INSPECT_STATUS_FINISH);
			finshMap.put("id", ydzfInspectCaseFinish.getInspectCaseFinishId().toString());
			finshMap.put("datetime", DateUtil.getStryyyyMMddHH24Miss(ydzfInspectCaseExecutePunish.getCreateDate()));
			resultList.add(finshMap);
		}
		
		return resultList;
	}
	private String bulidInspectCaseRelInfoJson(List<Map<String,String>> resultList)
	{
		if(CollectionUtils.isEmpty(resultList))
		{
			return "";
		}
		Map<String,List<Map<String,String>>> resultMap = new HashMap<String,List<Map<String,String>>>();
		resultMap.put("status_info", resultList);
		ObjectMapper mapper = new ObjectMapper();
		  try
	        {
			  return mapper.writeValueAsString(resultMap);
	        } catch (IOException e)
	        {
	        	log.error("组成InspectCaseRelInfoJson异常",e);
	        }
		  return "";
	}
	
	private TYdzfInspectCaseExample getCondition(TYdzfInspectCase record) {
		TYdzfInspectCaseExample example=new TYdzfInspectCaseExample();
			if (record != null) {
		com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseExample.Criteria cr=example.createCriteria();
		if(record.getInspectCaseId()!= null && record.getInspectCaseId()>0) {
		cr.andInspectCaseIdEqualTo(record.getInspectCaseId());
		}
		if(record.getInspectCaseSourceType()!= null && record.getInspectCaseSourceType()>0) {
		cr.andInspectCaseSourceTypeEqualTo(record.getInspectCaseSourceType());
		}
		if(record.getInspectCaseSourceId()!= null && record.getInspectCaseSourceId()>0) {
		cr.andInspectCaseSourceIdEqualTo(record.getInspectCaseSourceId());
		}
		if(record.getInspectCaseCreatDepId()!= null && record.getInspectCaseCreatDepId()>0) {
		cr.andInspectCaseCreatDepIdEqualTo(record.getInspectCaseCreatDepId());
		}
		if(record.getInspectCaseCreatUserId()!= null && record.getInspectCaseCreatUserId()>0) {
		cr.andInspectCaseCreatUserIdEqualTo(record.getInspectCaseCreatUserId());
		}
		if(record.getInspectCaseCreatDate()!= null){
		cr.andInspectCaseCreatDateEqualTo(record.getInspectCaseCreatDate());
		}
		if(record.getInspectCaseMainDepId()!= null && record.getInspectCaseMainDepId()>0) {
		cr.andInspectCaseMainDepIdEqualTo(record.getInspectCaseMainDepId());
		}
		if(record.getInspectCaseAssistDepId()!= null && record.getInspectCaseAssistDepId()>0) {
		cr.andInspectCaseAssistDepIdEqualTo(record.getInspectCaseAssistDepId());
		}
		if(record.getInspectCaseMainUserId()!= null && record.getInspectCaseMainUserId()>0) {
		cr.andInspectCaseMainUserIdEqualTo(record.getInspectCaseMainUserId());
		}
		if(record.getInspectCaseMainAssistId()!= null && record.getInspectCaseMainAssistId()>0) {
		cr.andInspectCaseMainAssistIdEqualTo(record.getInspectCaseMainAssistId());
		}
		if(record.getInspectCaseEnterpriseId()!= null && record.getInspectCaseEnterpriseId()>0) {
		cr.andInspectCaseEnterpriseIdEqualTo(record.getInspectCaseEnterpriseId());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getInspectCaseName())){
		cr.andInspectCaseNameEqualTo(record.getInspectCaseName());
		}
		if(record.getInspectCaseTempletId()!= null && record.getInspectCaseTempletId()>0) {
		cr.andInspectCaseTempletIdEqualTo(record.getInspectCaseTempletId());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getInspectCaseContent())){
		cr.andInspectCaseContentEqualTo(record.getInspectCaseContent());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getInspectCaseExplain())){
		cr.andInspectCaseExplainEqualTo(record.getInspectCaseExplain());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getRemark())){
		cr.andRemarkEqualTo(record.getRemark());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getInspectCaseStatus())){
		cr.andInspectCaseStatusEqualTo(record.getInspectCaseStatus());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getIsDel())){
		cr.andIsDelEqualTo(record.getIsDel());
		}
		if(record.getCreateDate()!= null){
		cr.andCreateDateEqualTo(record.getCreateDate());
		}
		if(record.getCreateUserid()!= null && record.getCreateUserid()>0) {
		cr.andCreateUseridEqualTo(record.getCreateUserid());
		}
		if(record.getUpdateDate()!= null){
		cr.andUpdateDateEqualTo(record.getUpdateDate());
		}
		if(record.getUpdateUserid()!= null && record.getUpdateUserid()>0) {
		cr.andUpdateUseridEqualTo(record.getUpdateUserid());
		}
			}
			return example;
		}




}
