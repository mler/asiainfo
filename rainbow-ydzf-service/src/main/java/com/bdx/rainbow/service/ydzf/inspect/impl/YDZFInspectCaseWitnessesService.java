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
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseWitnessesResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseExecutePunishResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseReadyPunishResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseWitnesses;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseWitnessesExample;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidence;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseExecutePunish;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseReadyPunish;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseReadyPunishExample;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseWitnessesMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseMapper;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseWitnessesService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceSampleService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseExecutePunishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseReadyPunishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseService;



@Service("ydzfInspectCaseWitnessesService")
public class YDZFInspectCaseWitnessesService extends BaseService implements IYDZFInspectCaseWitnessesService{
	
	private static final Logger log = LoggerFactory
			.getLogger(YDZFInspectCaseWitnessesService.class);
	@Autowired
	protected TYdzfInspectCaseWitnessesMapper ydzfInspectCaseWitnessesMapper;
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
	public int addInspectCaseWitnesses(TYdzfInspectCaseWitnesses ydzfInspectCaseWitnesses)
			throws BusinessException, SystemException {
		 ydzfInspectCaseWitnessesMapper.insert(ydzfInspectCaseWitnesses);
		 TYdzfInspectCase ydzfInspectCase= new TYdzfInspectCase();
		 ydzfInspectCase.setInspectCaseId(ydzfInspectCaseWitnesses.getInspectCaseId());
		 ydzfInspectCase.setInspectCaseStatus(YDZFConstants.INSPECTCASE.INSPECT_STATUS_WITNESSES);
		 ydzfInspectCase.setUpdateDate(ydzfInspectCaseWitnesses.getCreateDate());
		 ydzfInspectCase.setUpdateUserid(ydzfInspectCaseWitnesses.getCreateUserid());
		 ydzfInspectCaseService.updateInspectCase(ydzfInspectCase);
		 return ydzfInspectCaseWitnesses.getInspectCaseWitnessesId();
	}
	@Override
	@Transactional
	public int updateInspectCaseWitnesses(
			TYdzfInspectCaseWitnesses ydzfInspectCaseWitnesses)
			throws BusinessException, SystemException {
		//结案了不允许修改
		TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseMapper.selectByPrimaryKey(ydzfInspectCaseWitnesses.getInspectCaseId());
		InspectCaseUtils.checkUpdate(ydzfInspectCase);
		ydzfInspectCaseWitnessesMapper.updateByPrimaryKeySelective(ydzfInspectCaseWitnesses);
		return ydzfInspectCaseWitnesses.getInspectCaseWitnessesId();
	}
	@Override
	public YdzfInspectCaseWitnessesResultVO queryInspectCaseWitnessesInfo(
			int inspectCaseWitnessesId) throws BusinessException,
			SystemException {
		YdzfInspectCaseWitnessesResultVO resultVO = new YdzfInspectCaseWitnessesResultVO();
		TYdzfInspectCaseWitnesses ydzfInspectCaseWitnesses=ydzfInspectCaseWitnessesMapper.selectByPrimaryKey(inspectCaseWitnessesId);
		resultVO.setYdzfInspectCaseWitnesses(ydzfInspectCaseWitnesses);
		Integer inspectCaseId=ydzfInspectCaseWitnesses.getInspectCaseId();
		TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseMapper.selectByPrimaryKey(inspectCaseId);
		YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO=ydzfCommonService.getEnterpriseInfoByIdDubbo(ydzfInspectCase.getInspectCaseEnterpriseId());
		resultVO.setYdzfEnterpriseInfoVO(ydzfEnterpriseInfoVO);
			try {
				List<String> fileIdList = StringMyUtils.transArrayToListString(
						ydzfInspectCaseWitnesses.getWitnessesVideoIds(), ",");
				if (fileIdList != null) {
					resultVO.setFileidHttpUrlMap(ydzfCommonService
							.getFileHttpUrlsDubbo(fileIdList));
				}
			} catch (Exception e) {
				log.error("获取文件异常", e);
				throw new SystemException(new DefaultExceptionCode("获取文件异常"));
			}
		return resultVO;
	}
	@Override
	public List<YdzfInspectCaseWitnessesResultVO> queryInspectCaseWitnessesInfoVOList(
			TYdzfInspectCaseWitnesses ydzfInspectCaseWitnesses)
			throws BusinessException, SystemException {
		TYdzfInspectCaseWitnessesExample example=this.getCondition(ydzfInspectCaseWitnesses);
		List<TYdzfInspectCaseWitnesses> list= ydzfInspectCaseWitnessesMapper.selectByExample(example);
		//TODO:待优化
		 List<YdzfInspectCaseWitnessesResultVO>  volist =new ArrayList<YdzfInspectCaseWitnessesResultVO>();
		 if(!CollectionUtils.isEmpty(list))
		 {
			 for(TYdzfInspectCaseWitnesses bean:list)
			 {
				 //TODO:待优化
				 YdzfInspectCaseWitnessesResultVO vo = new YdzfInspectCaseWitnessesResultVO();
				 vo=this.queryInspectCaseWitnessesInfo(bean.getInspectCaseWitnessesId());
				 volist.add(vo);
			 }
			
		 }
		 return volist;
		
	}
	private TYdzfInspectCaseWitnessesExample getCondition(TYdzfInspectCaseWitnesses record) {
		TYdzfInspectCaseWitnessesExample example=new TYdzfInspectCaseWitnessesExample();
			if (record != null) {
		com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseWitnessesExample.Criteria cr=example.createCriteria();
		if(record.getInspectCaseWitnessesId()!= null && record.getInspectCaseWitnessesId()>0) {
		cr.andInspectCaseWitnessesIdEqualTo(record.getInspectCaseWitnessesId());
		}
		if(record.getInspectCaseId()!= null && record.getInspectCaseId()>0) {
		cr.andInspectCaseIdEqualTo(record.getInspectCaseId());
		}
		if(record.getWitnessesDate()!= null){
		cr.andWitnessesDateEqualTo(record.getWitnessesDate());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getWitnessesVideoIds())){
		cr.andWitnessesVideoIdsEqualTo(record.getWitnessesVideoIds());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getWitnessesResult())){
		cr.andWitnessesResultEqualTo(record.getWitnessesResult());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getWitnessesAdvice())){
		cr.andWitnessesAdviceEqualTo(record.getWitnessesAdvice());
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
