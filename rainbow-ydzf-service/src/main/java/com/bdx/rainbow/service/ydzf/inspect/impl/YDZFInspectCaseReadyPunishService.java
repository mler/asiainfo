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
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.core.utils.InspectCaseUtils;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseReadyPunishResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseReadyPunish;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseReadyPunishExample;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseReadyPunishMapper;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.common.IYDZFLawService;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseReadyPunishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseService;



@Service("ydzfInspectCaseReadyPunishService")
public class YDZFInspectCaseReadyPunishService extends BaseService implements IYDZFInspectCaseReadyPunishService{
	
	private static final Logger log = LoggerFactory
			.getLogger(YDZFInspectCaseReadyPunishService.class);
	@Autowired
	protected TYdzfInspectCaseReadyPunishMapper ydzfInspectCaseReadyPunishMapper;
	@Autowired
	protected TYdzfInspectCaseMapper ydzfInspectCaseMapper;
	@Autowired
	protected IYDZFInspectCaseService ydzfInspectCaseService;
	@Autowired
	protected IYDZFCommonService ydzfCommonService;
	@Autowired
	protected IYDZFLawService ydzfLawService;
	
	@Override
	@Transactional
	public int addInspectCaseReadyPunish(TYdzfInspectCaseReadyPunish ydzfInspectCaseReadyPunish)
			throws BusinessException, SystemException {
		 ydzfInspectCaseReadyPunishMapper.insert(ydzfInspectCaseReadyPunish);
		 TYdzfInspectCase ydzfInspectCase= new TYdzfInspectCase();
		 ydzfInspectCase.setInspectCaseId(ydzfInspectCaseReadyPunish.getInspectCaseId());
		 ydzfInspectCase.setInspectCaseStatus(YDZFConstants.INSPECTCASE.INSPECT_STATUS_READY_PUNISH);
		 ydzfInspectCase.setUpdateDate(ydzfInspectCaseReadyPunish.getCreateDate());
		 ydzfInspectCase.setUpdateUserid(ydzfInspectCaseReadyPunish.getCreateUserid());
		 ydzfInspectCaseService.updateInspectCase(ydzfInspectCase);
		 return ydzfInspectCaseReadyPunish.getInspectCaseReadyPunishId();
	}
	@Override
	@Transactional
	public int updateInspectCaseReadyPunish(
			TYdzfInspectCaseReadyPunish ydzfInspectCaseReadyPunish)
			throws BusinessException, SystemException {
		//结案了不允许修改
		TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseMapper.selectByPrimaryKey(ydzfInspectCaseReadyPunish.getInspectCaseId());
		InspectCaseUtils.checkUpdate(ydzfInspectCase);
		ydzfInspectCaseReadyPunishMapper.updateByPrimaryKeySelective(ydzfInspectCaseReadyPunish);
		return ydzfInspectCaseReadyPunish.getInspectCaseReadyPunishId();
	}
	@Override
	public YdzfInspectCaseReadyPunishResultVO queryInspectCaseReadyPunishInfo(
			int inspectCaseReadyPunishId) throws BusinessException,
			SystemException {
		YdzfInspectCaseReadyPunishResultVO resultVO = new YdzfInspectCaseReadyPunishResultVO();
		TYdzfInspectCaseReadyPunish ydzfInspectCaseReadyPunish=ydzfInspectCaseReadyPunishMapper.selectByPrimaryKey(inspectCaseReadyPunishId);
		//TYdzfInspectCase ydzfInspectCase= ydzfInspectCaseMapper.selectByPrimaryKey(ydzfInspectCaseReadyPunish.getInspectCaseId());
		resultVO.setYdzfInspectCaseReadyPunish(ydzfInspectCaseReadyPunish);
		resultVO.setLawJson(ydzfLawService.getLawJsonDubbo(ydzfInspectCaseReadyPunish.getLawIds()));
		return resultVO;
	}
	@Override
	public List<YdzfInspectCaseReadyPunishResultVO> queryInspectCaseReadyPunishInfoList(
			TYdzfInspectCaseReadyPunish ydzfInspectCaseReadyPunish)
			throws BusinessException, SystemException {
		TYdzfInspectCaseReadyPunishExample example=this.getCondition(ydzfInspectCaseReadyPunish);
		List<TYdzfInspectCaseReadyPunish> list= ydzfInspectCaseReadyPunishMapper.selectByExample(example);
		//TODO:待优化
		 List<YdzfInspectCaseReadyPunishResultVO>  volist =new ArrayList<YdzfInspectCaseReadyPunishResultVO>();
		 if(!CollectionUtils.isEmpty(list))
		 {
			 for(TYdzfInspectCaseReadyPunish bean:list)
			 {
				 //TODO:待优化
				 YdzfInspectCaseReadyPunishResultVO vo = new YdzfInspectCaseReadyPunishResultVO();
				 vo=this.queryInspectCaseReadyPunishInfo(bean.getInspectCaseReadyPunishId());
				 volist.add(vo);
			 }
			
		 }
		 return volist;
		
	}
	private TYdzfInspectCaseReadyPunishExample getCondition(TYdzfInspectCaseReadyPunish record) {
		TYdzfInspectCaseReadyPunishExample example=new TYdzfInspectCaseReadyPunishExample();
			if (record != null) {
		com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseReadyPunishExample.Criteria cr=example.createCriteria();
		if(record.getInspectCaseReadyPunishId()!= null && record.getInspectCaseReadyPunishId()>0) {
		cr.andInspectCaseReadyPunishIdEqualTo(record.getInspectCaseReadyPunishId());
		}
		if(record.getInspectCaseId()!= null && record.getInspectCaseId()>0) {
		cr.andInspectCaseIdEqualTo(record.getInspectCaseId());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getPunishAdvice())){
		cr.andPunishAdviceEqualTo(record.getPunishAdvice());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getExtraAdvice())){
		cr.andExtraAdviceEqualTo(record.getExtraAdvice());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getLawIds())){
		cr.andLawIdsEqualTo(record.getLawIds());
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
