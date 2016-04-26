package com.bdx.rainbow.service.ydzf.inspect.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseEvidenceResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidence;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidenceExample;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidenceSample;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseEvidenceMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseMapper;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceSampleService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseService;



@Service("ydzfInspectCaseEvidenceService")

public class YDZFInspectCaseEvidenceService extends BaseService implements IYDZFInspectCaseEvidenceService{
	
	private static final Logger log = LoggerFactory
			.getLogger(YDZFInspectCaseEvidenceService.class);
	@Autowired
	protected TYdzfInspectCaseEvidenceMapper ydzfInspectCaseEvidenceMapper;
	@Autowired
	protected TYdzfInspectCaseMapper ydzfInspectCaseMapper;
	@Autowired
	protected IYDZFInspectCaseService ydzfInspectCaseService;
	@Autowired
	protected IYDZFCommonService ydzfCommonService;
	@Autowired
	protected IYDZFInspectCaseEvidenceSampleService ydzfInspectCaseEvidenceSampleService;
	
	@Override
	@Transactional
	public int addInspectCaseEvidence(
			TYdzfInspectCaseEvidence ydzfInspectCaseEvidence)
			throws BusinessException, SystemException {
		 ydzfInspectCaseEvidenceMapper.insert(ydzfInspectCaseEvidence);
		 TYdzfInspectCase ydzfInspectCase= new TYdzfInspectCase();
		 ydzfInspectCase.setInspectCaseId(ydzfInspectCaseEvidence.getInspectCaseId());
		 ydzfInspectCase.setInspectCaseStatus(YDZFConstants.INSPECTCASE.INSPECT_STATUS_EVIDENCE);
		 ydzfInspectCase.setUpdateDate(ydzfInspectCaseEvidence.getCreateDate());
		 ydzfInspectCase.setUpdateUserid(ydzfInspectCaseEvidence.getCreateUserid());
		 ydzfInspectCaseService.updateInspectCase(ydzfInspectCase);
		 return ydzfInspectCaseEvidence.getInspectCaseEvidenceId();
	}
	@Override
	@Transactional
	public int updateInspectCaseEvidence(
			TYdzfInspectCaseEvidence ydzfInspectCaseEvidence)
			throws BusinessException, SystemException {
		//结案了不允许修改
		TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseMapper.selectByPrimaryKey(ydzfInspectCaseEvidence.getInspectCaseId());
		InspectCaseUtils.checkUpdate(ydzfInspectCase);
		ydzfInspectCaseEvidenceMapper.updateByPrimaryKeySelective(ydzfInspectCaseEvidence);
		return ydzfInspectCaseEvidence.getInspectCaseEvidenceId();
	}
	@Override
	public YdzfInspectCaseEvidenceResultVO queryInspectCaseEvidenceInfo(
			int inspectCaseEvidenceId) throws BusinessException,
			SystemException {
		YdzfInspectCaseEvidenceResultVO resultVO = new YdzfInspectCaseEvidenceResultVO();
		TYdzfInspectCaseEvidence ydzfInspectCaseEvidence=ydzfInspectCaseEvidenceMapper.selectByPrimaryKey(inspectCaseEvidenceId);
		TYdzfInspectCase ydzfInspectCase= ydzfInspectCaseMapper.selectByPrimaryKey(ydzfInspectCaseEvidence.getInspectCaseId());
		resultVO.setYdzfInspectCaseEvidence(ydzfInspectCaseEvidence);
		resultVO.setManageEnterpriseName(ydzfCommonService.getEnterpriseNameByIdDubbo(ydzfInspectCase.getInspectCaseEnterpriseId()));
		if (org.apache.commons.lang.StringUtils.isNotBlank(ydzfInspectCaseEvidence
				.getEvidenceFileIds())) {
			List<String> fileIdList = new ArrayList<String>();
			fileIdList = Arrays.asList(ydzfInspectCaseEvidence
					.getEvidenceFileIds()
					.split(","));
			try {
				resultVO.setEvidenceFileidHttpUrlMap(ydzfCommonService.getFileHttpUrlsDubbo(fileIdList));
			} catch (Exception e) {
				log.error("获取文件异常", e);
				throw new SystemException(new DefaultExceptionCode("获取文件异常"));
			}
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(ydzfInspectCaseEvidence
				.getEvidenceVoiceIds())) {
			List<String> fileIdList = new ArrayList<String>();
			fileIdList = Arrays.asList(ydzfInspectCaseEvidence
					.getEvidenceVoiceIds()
					.split(","));
			try {
				resultVO.setEvidenceVoiceIdHttpUrlMap(ydzfCommonService.getFileHttpUrlsDubbo(fileIdList));
			} catch (Exception e) {
				log.error("获取文件异常", e);
				throw new SystemException(new DefaultExceptionCode("获取文件异常"));
			}
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(ydzfInspectCaseEvidence
				.getInspectCaseEvidenceSampleIds())) {
			String sampleIds=ydzfInspectCaseEvidence.getInspectCaseEvidenceSampleIds();
			List<TYdzfInspectCaseEvidenceSample> sampleInfoList=ydzfInspectCaseEvidenceSampleService.queryInspectCaseEvidenceSampleListAll(StringMyUtils.transArrayToListInteger(sampleIds, ","));
			List<String> sampleNameList= new ArrayList<String>(sampleInfoList.size());
			for(TYdzfInspectCaseEvidenceSample sampleInfo:sampleInfoList)
			{
				sampleNameList.add(sampleInfo.getSampleName());
			}
			resultVO.setSampleNameList(sampleNameList);
		}
		return resultVO;
	}
	@Override
	public  List<TYdzfInspectCaseEvidence> queryInspectCaseEvidenceList(
			TYdzfInspectCaseEvidence ydzfInspectCaseEvidence)
			throws BusinessException, SystemException {
		TYdzfInspectCaseEvidenceExample example=this.getCondition(ydzfInspectCaseEvidence);
		return ydzfInspectCaseEvidenceMapper.selectByExample(example);
	}
	
	@Override
	public TYdzfInspectCaseEvidence queryInspectCaseEvidenceInfoByInspectId(
			int inspectCaseId) throws BusinessException, SystemException {
		TYdzfInspectCaseEvidence ydzfInspectCaseEvidence=new TYdzfInspectCaseEvidence();
		ydzfInspectCaseEvidence.setInspectCaseId(inspectCaseId);
		TYdzfInspectCaseEvidenceExample example=this.getCondition(ydzfInspectCaseEvidence);
		 List<TYdzfInspectCaseEvidence> list=ydzfInspectCaseEvidenceMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(list))
		{
			return null;
		}
		else
		{
			if(list.size()>1)
			{
				throw new SystemException(new DefaultExceptionCode("案件稽查对应多条调查取证纪录"));
			}
			return list.get(0);
		}
	}
	
	private TYdzfInspectCaseEvidenceExample getCondition(TYdzfInspectCaseEvidence record) {
		TYdzfInspectCaseEvidenceExample example=new TYdzfInspectCaseEvidenceExample();
			if (record != null) {
		com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidenceExample.Criteria cr=example.createCriteria();
		if(record.getInspectCaseEvidenceId()!= null && record.getInspectCaseEvidenceId()>0) {
		cr.andInspectCaseEvidenceIdEqualTo(record.getInspectCaseEvidenceId());
		}
		if(record.getInspectCaseId()!= null && record.getInspectCaseId()>0) {
		cr.andInspectCaseIdEqualTo(record.getInspectCaseId());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getInspectCaseEvidenceSampleIds())){
		cr.andInspectCaseEvidenceSampleIdsEqualTo(record.getInspectCaseEvidenceSampleIds());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getEvidenceResult())){
		cr.andEvidenceResultEqualTo(record.getEvidenceResult());
		}
		if(record.getEvidenceCheckDate()!= null){
		cr.andEvidenceCheckDateEqualTo(record.getEvidenceCheckDate());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getEvidenceCheckEmpCode())){
		cr.andEvidenceCheckEmpCodeEqualTo(record.getEvidenceCheckEmpCode());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getEvidenceFileIds())){
		cr.andEvidenceFileIdsEqualTo(record.getEvidenceFileIds());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getEvidenceVoiceIds())){
		cr.andEvidenceVoiceIdsEqualTo(record.getEvidenceVoiceIds());
		}
		if(record.getInspectCaseTempletId()!= null && record.getInspectCaseTempletId()>0) {
		cr.andInspectCaseTempletIdEqualTo(record.getInspectCaseTempletId());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getInspectCaseTempletValues())){
		cr.andInspectCaseTempletValuesEqualTo(record.getInspectCaseTempletValues());
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
