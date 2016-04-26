package com.bdx.rainbow.service.ydzf.history.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.dto.ydzf.form.YdzfEnterpriseHistoryQueryInfo;
import com.bdx.rainbow.dto.ydzf.vo.YdzfEnterpriseHistoryCountVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfEnterpriseHistoryListInfoVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfEnterpriseInfoVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfPunishAdviceFinishHistoryVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfPunishAdviceHistoryVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidence;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseMapper;
import com.bdx.rainbow.mapper.ydzf.self.TYdzfHistorySelfMapper;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.common.IYDZFLawService;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.history.IYDZFHistoryService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseFinishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseReadyPunishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseService;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;



@Service("ydzfHistoryService")
public class YDZFHistoryService extends BaseService implements IYDZFHistoryService{
	
	private static final Logger log = LoggerFactory
			.getLogger(YDZFHistoryService.class);
	@Autowired
	protected TYdzfHistorySelfMapper ydzfHistorySelfMapper;
	@Autowired
	protected IYDZFCommonService ydzfCommonService;
	@Autowired
	protected IYDZFInspectCaseEvidenceService ydzfInspectCaseEvidenceService;
	@Autowired
	protected IYDZFInspectCaseReadyPunishService ydzfInspectCaseReadyPunishService;
	@Autowired
	protected IYDZFInspectCaseFinishService ydzfInspectCaseFinishService;
	@Autowired
	protected IYDZFInspectCaseService ydzfInspectCaseService;
	@Autowired
	protected TYdzfInspectCaseMapper ydzfInspectCaseMapper;
	@Autowired
	protected IYDZFLawService ydzfLawService;
	
	@Override
	public YdzfEnterpriseHistoryCountVO queryEnterpriseHistoryCount(
			YdzfEnterpriseHistoryQueryInfo ydzfEnterpriseHistoryQueryInfo)
			throws BusinessException, SystemException {
		if(ydzfEnterpriseHistoryQueryInfo==null||ydzfEnterpriseHistoryQueryInfo.getManageEnterpriseId()==null||ydzfEnterpriseHistoryQueryInfo.getManageEnterpriseId()==0)
		{
			log.error("企业ID为空");
			throw new SystemException(new DefaultExceptionCode("参数异常，企业ID不能为空"));
		}
		Map<String,Object> paramMap= new HashMap<String,Object>();
		paramMap.put("manageEnterpriseId",ydzfEnterpriseHistoryQueryInfo.getManageEnterpriseId());
		//paramMap.put("checkPlanType",ydzfEnterpriseHistoryQueryInfo.getCheckPlanType());
		paramMap.put("startDate",ydzfEnterpriseHistoryQueryInfo.getStartDate());
		paramMap.put("endDate",ydzfEnterpriseHistoryQueryInfo.getEndDate());
		YdzfEnterpriseHistoryCountVO vo = new YdzfEnterpriseHistoryCountVO();
		vo.setCheckJsonInfo(bulidCheckJsonInfo(paramMap));
		YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO=ydzfCommonService.getEnterpriseInfoByIdDubbo(ydzfEnterpriseHistoryQueryInfo.getManageEnterpriseId());
		vo.setYdzfEnterpriseInfoVO(ydzfEnterpriseInfoVO);
		return vo;
	}
	@Override
	public List<YdzfEnterpriseHistoryListInfoVO> queryEnterpriseHistoryListInfo(
			YdzfEnterpriseHistoryQueryInfo ydzfEnterpriseHistoryQueryInfo,
			Integer start, Integer limit) throws BusinessException,
			SystemException {
		ydzfEnterpriseHistoryQueryInfo.setLimitClauseStart(start);
		ydzfEnterpriseHistoryQueryInfo.setLimitClauseCount(limit);
		return ydzfHistorySelfMapper.queryEnterpriseHistoryListInfo(ydzfEnterpriseHistoryQueryInfo);
	}
	@Override
	public int queryEnterpriseHistoryListInfoCount(
			YdzfEnterpriseHistoryQueryInfo ydzfEnterpriseHistoryQueryInfo)
			throws BusinessException, SystemException {
		return ydzfHistorySelfMapper.queryEnterpriseHistoryListInfoCount(ydzfEnterpriseHistoryQueryInfo);
	}
	
	@Override
	public int queryPunishAdviceHistoryListInfoCount(IUserInfo userInfo,
			int inspectCaseId) throws BusinessException, SystemException {
		if(inspectCaseId<=0)
		{
			log.error("inspectCaseId为空");
			throw new SystemException(new DefaultExceptionCode("参数异常"));
		}
		TYdzfInspectCaseEvidence ydzfInspectCaseEvidence=ydzfInspectCaseEvidenceService.queryInspectCaseEvidenceInfoByInspectId(inspectCaseId);
		Map<String,Object> paramMap= new HashMap<String,Object>();
		paramMap.put("inspectCaseTempletId", ydzfInspectCaseEvidence.getInspectCaseTempletId());
		paramMap.put("inspectCaseTempletTotal", ydzfInspectCaseEvidence.getInspectCaseTempletTotal());
		return ydzfHistorySelfMapper.queryPunishAdviceHistoryListInfoCount(paramMap);
	}
	@Override
	public List<YdzfPunishAdviceHistoryVO> queryPunishAdviceHistoryListInfo(
			IUserInfo userInfo, int inspectCaseId, Integer start,
			Integer limit) throws BusinessException,
			SystemException {
		/**
		 * 1.查询相同的稽查模版，得分<=历史得分
		 * 2.历史案件为结案状态。
		 */
		if(inspectCaseId<=0)
		{
			log.error("inspectCaseId为空");
			throw new SystemException(new DefaultExceptionCode("参数异常"));
		}
		TYdzfInspectCaseEvidence ydzfInspectCaseEvidence=ydzfInspectCaseEvidenceService.queryInspectCaseEvidenceInfoByInspectId(inspectCaseId);
		Map<String,Object> paramMap= new HashMap<String,Object>();
		paramMap.put("inspectCaseTempletId", ydzfInspectCaseEvidence.getInspectCaseTempletId());
		paramMap.put("inspectCaseTempletTotal", ydzfInspectCaseEvidence.getInspectCaseTempletTotal());
		if (start!=null&&start >= 0 && limit!=null&&limit > 0) {
			paramMap.put("limitClauseStart", start);
			paramMap.put("limitClauseCount", limit);
		}
		 List<YdzfPunishAdviceHistoryVO> list= ydzfHistorySelfMapper.queryPunishAdviceHistoryListInfo(paramMap);
		 if(CollectionUtils.isEmpty(list))
		 {
			 return null;
		 }
		 //填充lawInfo
		 for(YdzfPunishAdviceHistoryVO vo:list)
		 {
			 vo.setLawJson(ydzfLawService.getLawJsonDubbo(vo.getLawIds()));
		 }
		 return list;
	}
	@Override
	public List<YdzfPunishAdviceFinishHistoryVO> queryPunishAdviceFinishHistoryListInfo(
			IUserInfo userInfo, String inspectCaseIds, Integer start,
			Integer limit) throws BusinessException, SystemException {
		if(StringUtils.isBlank(inspectCaseIds))
		{
			log.error("inspectCaseIds为空");
			throw new SystemException(new DefaultExceptionCode("参数异常，inspectCaseIds不能为空"));
		}
		Map<String,Object> paramMap= new HashMap<String,Object>();
		paramMap.put("inspectCaseIds", inspectCaseIds.split(","));
		if (start!=null&&start >= 0 && limit!=null&&limit > 0) {
			paramMap.put("limitClauseStart", start);
			paramMap.put("limitClauseCount", limit);
		}
		List<YdzfPunishAdviceFinishHistoryVO> list=ydzfHistorySelfMapper.queryPunishAdviceFinishHistoryListInfo(paramMap);
		return list;
	}
	
	
	private String bulidCheckJsonInfo(Map<String,Object> paramMap)
	{
		String checkJsonInfo="";
		List<Map<String,Object>> inspectQueryList=ydzfHistorySelfMapper.queryInspectEnterpriseHistoryCount(paramMap);
		List<Map<String,Object>> monitorQueryList=ydzfHistorySelfMapper.queryMonitorEnterpriseHistoryCount(paramMap);
		//计算inpsect
		int inspect_total=0;
		for(Map<String,Object> inspectQueryMap :inspectQueryList)
		{
			int num=Integer.parseInt(inspectQueryMap.get("num").toString());
			inspect_total=inspect_total+num;
		}
		//计算monitor
		List<Map<String,Object>> monitorResultList= new ArrayList<Map<String,Object>>();
		for (YDZFConstants.MONITOR.CHECKPLANTYPE e : YDZFConstants.MONITOR.CHECKPLANTYPE.values()) {
			Map<String,Object> monitorTempMap=new HashMap<String,Object>();
			monitorTempMap.put("checkType", e.getCode());
			monitorTempMap.put("total", 0);
			monitorTempMap.put("pass_num", 0);
			monitorTempMap.put("fail_num", 0);
			monitorResultList.add(monitorTempMap);
		}
		for(Map<String,Object> monitorResultMap:monitorResultList)
		{
			for(Map<String,Object> monitorQueryMap :monitorQueryList)
			{
				String checkType=monitorQueryMap.get("checkType").toString();
				String result=monitorQueryMap.get("result").toString();
				int num=Integer.parseInt(monitorQueryMap.get("num").toString());
				if(monitorResultMap.get("checkType").equals(checkType))
				{
					//合格
					if(result.equals(YDZFConstants.SYSTEM.STATUS_VALID))
					{
						monitorResultMap.put("pass_num", Integer.parseInt(monitorResultMap.get("pass_num").toString())+num);
					}
					else if(result.equals(YDZFConstants.SYSTEM.STATUS_INVALID))
					{
						monitorResultMap.put("fail_num", Integer.parseInt(monitorResultMap.get("fail_num").toString())+num);
					}
					monitorResultMap.put("total",  Integer.parseInt(monitorResultMap.get("pass_num").toString())+Integer.parseInt(monitorResultMap.get("fail_num").toString()));
					
				}
			}
		}
		//result
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("monitor_info", monitorResultList);
		resultMap.put("inspect_total", inspect_total);
		resultMap.put("inspect_info", inspectQueryList);
		ObjectMapper mapper = new ObjectMapper();
		  try
	        {
			  checkJsonInfo=mapper.writeValueAsString(resultMap);
	        } catch (IOException e)
	        {
	        	log.error("组成checkJsonInfo异常",e);
	        }
		  return checkJsonInfo;
	}
	
	
	
	


}
