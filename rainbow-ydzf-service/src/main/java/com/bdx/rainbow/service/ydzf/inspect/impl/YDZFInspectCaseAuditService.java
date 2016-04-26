package com.bdx.rainbow.service.ydzf.inspect.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.core.utils.InspectCaseUtils;
import com.bdx.rainbow.core.utils.StringMyUtils;
import com.bdx.rainbow.dto.ydzf.vo.YdzfEnterpriseInfoVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseAuditResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseReadyPunishResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseAudit;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseAuditExample;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidence;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseReadyPunish;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseAuditMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseMapper;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseAuditService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseReadyPunishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseService;



@Service("ydzfInspectCaseAuditService")
public class YDZFInspectCaseAuditService extends BaseService implements IYDZFInspectCaseAuditService{
	
	private static final Logger log = LoggerFactory
			.getLogger(YDZFInspectCaseAuditService.class);
	@Autowired
	protected TYdzfInspectCaseAuditMapper ydzfInspectCaseAuditMapper;
	@Autowired
	protected TYdzfInspectCaseMapper ydzfInspectCaseMapper;
	@Autowired
	protected IYDZFInspectCaseService ydzfInspectCaseService;
	@Autowired
	protected IYDZFCommonService ydzfCommonService;
	@Autowired
	protected IYDZFInspectCaseReadyPunishService ydzfInspectCaseReadyPunishService;
	@Autowired
	protected IYDZFInspectCaseEvidenceService ydzfInspectCaseEvidenceService;
	
	@Override
	@Transactional
	public int addInspectCaseAudit(TYdzfInspectCaseAudit ydzfInspectCaseAudit)
			throws BusinessException, SystemException {
		 ydzfInspectCaseAuditMapper.insert(ydzfInspectCaseAudit);
		 TYdzfInspectCase ydzfInspectCase= new TYdzfInspectCase();
		 ydzfInspectCase.setInspectCaseId(ydzfInspectCaseAudit.getInspectCaseId());
		 ydzfInspectCase.setInspectCaseStatus(YDZFConstants.INSPECTCASE.INSPECT_STATUS_AUDIT);
		 ydzfInspectCase.setUpdateDate(ydzfInspectCaseAudit.getCreateDate());
		 ydzfInspectCase.setUpdateUserid(ydzfInspectCaseAudit.getCreateUserid());
		 ydzfInspectCaseService.updateInspectCase(ydzfInspectCase);
		 return ydzfInspectCaseAudit.getInspectCaseAuditId();
	}
	@Override
	@Transactional
	public int updateInspectCaseAudit(
			TYdzfInspectCaseAudit ydzfInspectCaseAudit)
			throws BusinessException, SystemException {
		//结案了不允许修改
		TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseMapper.selectByPrimaryKey(ydzfInspectCaseAudit.getInspectCaseId());
		InspectCaseUtils.checkUpdate(ydzfInspectCase);
		ydzfInspectCaseAuditMapper.updateByPrimaryKeySelective(ydzfInspectCaseAudit);
		return ydzfInspectCaseAudit.getInspectCaseAuditId();
	}
	@Override
	public YdzfInspectCaseAuditResultVO queryInspectCaseAuditInfo(
			int inspectCaseAuditId) throws BusinessException,
			SystemException {
		YdzfInspectCaseAuditResultVO resultVO = new YdzfInspectCaseAuditResultVO();
		TYdzfInspectCaseAudit ydzfInspectCaseAudit=ydzfInspectCaseAuditMapper.selectByPrimaryKey(inspectCaseAuditId);
		resultVO.setYdzfInspectCaseAudit(ydzfInspectCaseAudit);
		Integer inspectCaseId=ydzfInspectCaseAudit.getInspectCaseId();
		TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseMapper.selectByPrimaryKey(ydzfInspectCaseAudit.getInspectCaseId());
		YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO=ydzfCommonService.getEnterpriseInfoByIdDubbo(ydzfInspectCase.getInspectCaseEnterpriseId());
		resultVO.setYdzfEnterpriseInfoVO(ydzfEnterpriseInfoVO);
		TYdzfInspectCaseReadyPunish ydzfInspectCaseReadyPunish= new TYdzfInspectCaseReadyPunish();
		ydzfInspectCaseReadyPunish.setInspectCaseId(inspectCaseId);
		 List<YdzfInspectCaseReadyPunishResultVO> readyVOList=ydzfInspectCaseReadyPunishService.queryInspectCaseReadyPunishInfoList(ydzfInspectCaseReadyPunish);
		 if(!CollectionUtils.isEmpty(readyVOList))
		 {
			 if(readyVOList.size()>1)
			 {
				 log.error("案件id对应多条案件拟处罚记录");
				throw new SystemException(new DefaultExceptionCode("案件id对应多条拟处罚记录"));
			 }
			 YdzfInspectCaseReadyPunishResultVO ydzfInspectCaseReadyPunishResultVO= readyVOList.get(0);
			 resultVO.setPunishAdvide(ydzfInspectCaseReadyPunishResultVO.getYdzfInspectCaseReadyPunish().getPunishAdvice());
		 }
		 TYdzfInspectCaseEvidence ydzfInspectCaseEvidence= new TYdzfInspectCaseEvidence();
		 ydzfInspectCaseEvidence.setInspectCaseId(inspectCaseId);
		 List<TYdzfInspectCaseEvidence> evidenceList= ydzfInspectCaseEvidenceService.queryInspectCaseEvidenceList(ydzfInspectCaseEvidence);
		 if(!CollectionUtils.isEmpty(evidenceList))
		 {
			 if(readyVOList.size()>1)
			 {
				 log.error("案件id对应多条调查取证记录");
				throw new SystemException(new DefaultExceptionCode("案件id对应多条调查取证记录"));
			 }
			 ydzfInspectCaseEvidence= evidenceList.get(0);
			 resultVO.setYdzfInspectCaseEvidence(ydzfInspectCaseEvidence);
		 }
		 
			try {
				List<String> fileIdList = StringMyUtils.transArrayToListString(
						ydzfInspectCaseAudit.getAuditFileIds(), ",");
				if (fileIdList != null) {
					resultVO.setAuditFileidHttpUrlMap(ydzfCommonService
							.getFileHttpUrlsDubbo(fileIdList));
				}
				List<String> signFileIdList = StringMyUtils.transArrayToListString(
						ydzfInspectCaseAudit.getSignFileIds(), ",");
				if (signFileIdList != null) {
					resultVO.setSignFileidHttpUrlMap(ydzfCommonService
							.getFileHttpUrlsDubbo(signFileIdList));
				}
			} catch (Exception e) {
				log.error("获取文件异常", e);
				throw new SystemException(new DefaultExceptionCode("获取文件异常"));
			}
		return resultVO;
	}
	@Override
	public List<YdzfInspectCaseAuditResultVO> queryInspectCaseAuditInfoVOList(
			TYdzfInspectCaseAudit ydzfInspectCaseAudit)
			throws BusinessException, SystemException {
		TYdzfInspectCaseAuditExample example=this.getCondition(ydzfInspectCaseAudit);
		List<TYdzfInspectCaseAudit> list= ydzfInspectCaseAuditMapper.selectByExample(example);
		//TODO:待优化
		 List<YdzfInspectCaseAuditResultVO>  volist =new ArrayList<YdzfInspectCaseAuditResultVO>();
		 if(!CollectionUtils.isEmpty(list))
		 {
			 for(TYdzfInspectCaseAudit bean:list)
			 {
				 //TODO:待优化
				 YdzfInspectCaseAuditResultVO vo = new YdzfInspectCaseAuditResultVO();
				 vo=this.queryInspectCaseAuditInfo(bean.getInspectCaseAuditId());
				 volist.add(vo);
			 }
			
		 }
		 return volist;
		
	}
	private TYdzfInspectCaseAuditExample getCondition(TYdzfInspectCaseAudit record) {
		TYdzfInspectCaseAuditExample example=new TYdzfInspectCaseAuditExample();
			if (record != null) {
		com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseAuditExample.Criteria cr=example.createCriteria();
		if(record.getInspectCaseAuditId()!= null && record.getInspectCaseAuditId()>0) {
		cr.andInspectCaseAuditIdEqualTo(record.getInspectCaseAuditId());
		}
		if(record.getInspectCaseId()!= null && record.getInspectCaseId()>0) {
		cr.andInspectCaseIdEqualTo(record.getInspectCaseId());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getAuditResult())){
		cr.andAuditResultEqualTo(record.getAuditResult());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getAuditAdvice())){
		cr.andAuditAdviceEqualTo(record.getAuditAdvice());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getAuditEmpCode())){
		cr.andAuditEmpCodeEqualTo(record.getAuditEmpCode());
		}
		if(record.getAuditDate()!= null){
		cr.andAuditDateEqualTo(record.getAuditDate());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getAuditFileIds())){
		cr.andAuditFileIdsEqualTo(record.getAuditFileIds());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getSignFileIds())){
		cr.andSignFileIdsEqualTo(record.getSignFileIds());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getRemark())){
		cr.andRemarkEqualTo(record.getRemark());
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
