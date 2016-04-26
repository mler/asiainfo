package com.bdx.rainbow.service.ydzf.inspect.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.core.utils.InspectCaseUtils;
import com.bdx.rainbow.dto.ydzf.vo.YdzfEnterpriseInfoVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseEvidenceSampleResultInfoVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseExecutePunishResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseReadyPunishResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidenceExample;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidenceSample;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseExecutePunish;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseExecutePunishExample;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseExecutePunishMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseMapper;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseExecutePunishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseReadyPunishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseService;
import com.bdx.rainbow.urs.entity.IUserInfo;


@Service("ydzfInspectCaseExecutePunishService")
public class YDZFInspectCaseExecutePunishService implements
		IYDZFInspectCaseExecutePunishService {
	private static final Logger log = LoggerFactory
			.getLogger(YDZFInspectCaseExecutePunishService.class);
	@Autowired
	protected TYdzfInspectCaseExecutePunishMapper ydzfInspectCaseExecutePunishMapper;
	@Autowired
	protected TYdzfInspectCaseMapper ydzfInspectCaseMapper;
	@Autowired
	protected IYDZFInspectCaseService ydzfInspectCaseService;
	
	@Autowired
	protected IYDZFInspectCaseReadyPunishService ydzfInspectCaseReadyPunishService;
	
	@Autowired
	protected IYDZFCommonService ydzfCommonService;
	@Override
	public int addOrUpdateExecutePunishInfo(
			TYdzfInspectCaseExecutePunish ydzfInspectCaseExecutePunish,IUserInfo userInfo)
			throws BusinessException, SystemException {
		if (ydzfInspectCaseExecutePunish == null || ydzfInspectCaseExecutePunish.getInspectCaseId() == null) {
			log.error("参数异常");
			throw new SystemException(new DefaultExceptionCode("参数异常"));
		}
		if(ydzfInspectCaseExecutePunish.getInspectCaseReadyPunishId()==null&&ydzfInspectCaseExecutePunish.getInspectCaseWitnessesId()==null)
		{
			log.error("参数异常");
			throw new SystemException(new DefaultExceptionCode("参数异常"));
		}
		//结案了不允许修改
		TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseMapper.selectByPrimaryKey(ydzfInspectCaseExecutePunish.getInspectCaseId());
		InspectCaseUtils.checkUpdate(ydzfInspectCase);
		Timestamp currentDate = DateUtil.getCurrent();
		if (ydzfInspectCaseExecutePunish.getInspectCaseExecutePunishId() == null
				|| ydzfInspectCaseExecutePunish.getInspectCaseExecutePunishId()  == 0) {
			ydzfInspectCaseExecutePunish.setCreateDate(currentDate);
			ydzfInspectCaseExecutePunish.setCreateUserid(userInfo.getUserId());
			//为了统计的时候根据更新时间查询
			ydzfInspectCaseExecutePunish.setUpdateDate(currentDate);
			ydzfInspectCaseExecutePunish.setUpdateUserid(userInfo.getUserId());
			ydzfInspectCaseExecutePunishMapper.insert(ydzfInspectCaseExecutePunish);
		} else {
			ydzfInspectCaseExecutePunish.setUpdateDate(currentDate);
			ydzfInspectCaseExecutePunish.setUpdateUserid(userInfo.getUserId());
			ydzfInspectCaseExecutePunishMapper.updateByPrimaryKeySelective(ydzfInspectCaseExecutePunish);
		}
		// 关联更新，监督管理关联表，日常监督管理
		this.updateExecutePunishRel(ydzfInspectCaseExecutePunish,userInfo,currentDate);
		return ydzfInspectCaseExecutePunish.getInspectCaseExecutePunishId();
	}
	
	private void updateExecutePunishRel(TYdzfInspectCaseExecutePunish ydzfInspectCaseExecutePunish,IUserInfo userInfo,Timestamp currentDate)
	throws BusinessException, SystemException {
		 TYdzfInspectCase ydzfInspectCase= new TYdzfInspectCase();
		 ydzfInspectCase.setInspectCaseId(ydzfInspectCaseExecutePunish.getInspectCaseId());
		 ydzfInspectCase.setUpdateDate(currentDate);
		 ydzfInspectCase.setUpdateUserid(userInfo.getUserId());
		if(YDZFConstants.INSPECTCASE.EXECUTE_PUNISH_STATUS_SUBMIT.equals(ydzfInspectCaseExecutePunish.getExecuteStatus()))
		{
		
			 ydzfInspectCase.setInspectCaseStatus(YDZFConstants.INSPECTCASE.INSPECT_STATUS_EXECUTE_PUNISH_SUBMIT);
			 ydzfInspectCaseService.updateInspectCase(ydzfInspectCase);
		}
		else if(YDZFConstants.INSPECTCASE.EXECUTE_PUNISH_STATUS_SUBMIT.equals(ydzfInspectCaseExecutePunish.getExecuteStatus()))
		{
			 ydzfInspectCase.setInspectCaseStatus(YDZFConstants.INSPECTCASE.INSPECT_STATUS_EXECUTE_PUNISH_SAVE);
			 ydzfInspectCaseService.updateInspectCase(ydzfInspectCase);
		}
		else
		{
			log.error("执行处罚状态异常");
			throw new SystemException(new DefaultExceptionCode("执行处罚状态异常"));
		}
		
	}

	@Override
	public YdzfInspectCaseExecutePunishResultVO queryExecutePunishInfo(
			int inspectCaseExecutePunishId) throws BusinessException,
			SystemException {
		YdzfInspectCaseExecutePunishResultVO resultVO = new YdzfInspectCaseExecutePunishResultVO();
		TYdzfInspectCaseExecutePunish ydzfInspectCaseExecutePunish = ydzfInspectCaseExecutePunishMapper
				.selectByPrimaryKey(inspectCaseExecutePunishId);
		resultVO.setYdzfInspectCaseExecutePunish(ydzfInspectCaseExecutePunish);
		YdzfInspectCaseReadyPunishResultVO vo=ydzfInspectCaseReadyPunishService.queryInspectCaseReadyPunishInfo(ydzfInspectCaseExecutePunish.getInspectCaseReadyPunishId());
		resultVO.setPunishAdvide(vo.getYdzfInspectCaseReadyPunish().getPunishAdvice());
		resultVO.setLawJson(vo.getLawJson());
		TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseMapper.selectByPrimaryKey(ydzfInspectCaseExecutePunish.getInspectCaseId());
		YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO=ydzfCommonService.getEnterpriseInfoByIdDubbo(ydzfInspectCase.getInspectCaseEnterpriseId());
		resultVO.setYdzfEnterpriseInfoVO(ydzfEnterpriseInfoVO);
		return resultVO;
	}

	@Override
	public List<YdzfInspectCaseExecutePunishResultVO> queryExecutePunishInfoVOList(
			TYdzfInspectCaseExecutePunish ydzfInspectCaseExecutePunish)
			throws BusinessException, SystemException {
		TYdzfInspectCaseExecutePunishExample example=this.getCondition(ydzfInspectCaseExecutePunish);
		 List<TYdzfInspectCaseExecutePunish> list= ydzfInspectCaseExecutePunishMapper.selectByExample(example);
		 List<YdzfInspectCaseExecutePunishResultVO>  volist =new ArrayList<YdzfInspectCaseExecutePunishResultVO> ();
		 if(!CollectionUtils.isEmpty(list))
		 {
			 for(TYdzfInspectCaseExecutePunish bean:list)
			 {
				 //TODO:待优化
				 YdzfInspectCaseExecutePunishResultVO vo = new YdzfInspectCaseExecutePunishResultVO();
				 vo=this.queryExecutePunishInfo(bean.getInspectCaseExecutePunishId());
				 volist.add(vo);
			 }
			
		 }
		 return volist;
	}
	
	private TYdzfInspectCaseExecutePunishExample getCondition(TYdzfInspectCaseExecutePunish record) {
		TYdzfInspectCaseExecutePunishExample example=new TYdzfInspectCaseExecutePunishExample();
			if (record != null) {
		com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseExecutePunishExample.Criteria cr=example.createCriteria();
		if(record.getInspectCaseExecutePunishId()!= null && record.getInspectCaseExecutePunishId()>0) {
		cr.andInspectCaseExecutePunishIdEqualTo(record.getInspectCaseExecutePunishId());
		}
		if(record.getInspectCaseId()!= null && record.getInspectCaseId()>0) {
		cr.andInspectCaseIdEqualTo(record.getInspectCaseId());
		}
		if(record.getInspectCaseReadyPunishId()!= null && record.getInspectCaseReadyPunishId()>0) {
		cr.andInspectCaseReadyPunishIdEqualTo(record.getInspectCaseReadyPunishId());
		}
		if(record.getInspectCaseWitnessesId()!= null && record.getInspectCaseWitnessesId()>0) {
		cr.andInspectCaseWitnessesIdEqualTo(record.getInspectCaseWitnessesId());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getExecuteMethod())){
		cr.andExecuteMethodEqualTo(record.getExecuteMethod());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getExecuteStatus())){
		cr.andExecuteStatusEqualTo(record.getExecuteStatus());
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
