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
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseFinishResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseReadyPunishResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseFinish;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseFinishExample;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseReadyPunish;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseReadyPunishExample;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseFinishMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseMapper;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseFinishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseReadyPunishService;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseService;



@Service("ydzfInspectCaseFinishService")
public class YDZFInspectCaseFinishService extends BaseService implements IYDZFInspectCaseFinishService{
	
	private static final Logger log = LoggerFactory
			.getLogger(YDZFInspectCaseFinishService.class);
	@Autowired
	protected TYdzfInspectCaseFinishMapper ydzfInspectCaseFinishMapper;
	@Autowired
	protected TYdzfInspectCaseMapper ydzfInspectCaseMapper;
	@Autowired
	protected IYDZFInspectCaseService ydzfInspectCaseService;
	@Autowired
	protected IYDZFCommonService ydzfCommonService;
	@Autowired
	protected IYDZFInspectCaseReadyPunishService ydzfInspectCaseReadyPunishService;
	
	@Override
	@Transactional
	public int addInspectCaseFinish(TYdzfInspectCaseFinish ydzfInspectCaseFinish)
			throws BusinessException, SystemException {
		 ydzfInspectCaseFinishMapper.insert(ydzfInspectCaseFinish);
		 TYdzfInspectCase ydzfInspectCase= new TYdzfInspectCase();
		 ydzfInspectCase.setInspectCaseId(ydzfInspectCaseFinish.getInspectCaseId());
		 ydzfInspectCase.setInspectCaseStatus(YDZFConstants.INSPECTCASE.INSPECT_STATUS_FINISH);
		 ydzfInspectCase.setUpdateDate(ydzfInspectCaseFinish.getCreateDate());
		 ydzfInspectCase.setUpdateUserid(ydzfInspectCaseFinish.getCreateUserid());
		 ydzfInspectCaseService.updateInspectCase(ydzfInspectCase);
		 return ydzfInspectCaseFinish.getInspectCaseFinishId();
	}
	@Override
	@Transactional
	public int updateInspectCaseFinish(
			TYdzfInspectCaseFinish ydzfInspectCaseFinish)
			throws BusinessException, SystemException {
		//结案了不允许修改
		TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseMapper.selectByPrimaryKey(ydzfInspectCaseFinish.getInspectCaseId());
		InspectCaseUtils.checkUpdate(ydzfInspectCase);
		ydzfInspectCaseFinishMapper.updateByPrimaryKeySelective(ydzfInspectCaseFinish);
		return ydzfInspectCaseFinish.getInspectCaseFinishId();
	}
	@Override
	public YdzfInspectCaseFinishResultVO queryInspectCaseFinishInfo(
			int inspectCaseFinishId) throws BusinessException,
			SystemException {
		YdzfInspectCaseFinishResultVO resultVO = new YdzfInspectCaseFinishResultVO();
		TYdzfInspectCaseFinish ydzfInspectCaseFinish=ydzfInspectCaseFinishMapper.selectByPrimaryKey(inspectCaseFinishId);
		resultVO.setYdzfInspectCaseFinish(ydzfInspectCaseFinish);
		Integer inspectCaseId=ydzfInspectCaseFinish.getInspectCaseId();
		TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseMapper.selectByPrimaryKey(ydzfInspectCaseFinish.getInspectCaseId());
		resultVO.setYdzfInspectCase(ydzfInspectCase);
		YdzfEnterpriseInfoVO ydzfEnterpriseInfoVO=ydzfCommonService.getEnterpriseInfoByIdDubbo(ydzfInspectCase.getInspectCaseEnterpriseId());
		resultVO.setYdzfEnterpriseInfoVO(ydzfEnterpriseInfoVO);
		TYdzfInspectCaseReadyPunish ydzfInspectCaseReadyPunish= new TYdzfInspectCaseReadyPunish();
		ydzfInspectCaseReadyPunish.setInspectCaseId(inspectCaseId);
		 List<YdzfInspectCaseReadyPunishResultVO> readyVOList=ydzfInspectCaseReadyPunishService.queryInspectCaseReadyPunishInfoList(ydzfInspectCaseReadyPunish);
		 if(!CollectionUtils.isEmpty(readyVOList))
		 {
			 if(readyVOList.size()>1)
			 {
				 log.error("案件id对应多条执行处罚记录");
				throw new SystemException(new DefaultExceptionCode("案件id对应多条执行处罚纪录"));
			 }
			 YdzfInspectCaseReadyPunishResultVO ydzfInspectCaseReadyPunishResultVO= readyVOList.get(0);
			 resultVO.setPunishAdvide(ydzfInspectCaseReadyPunishResultVO.getYdzfInspectCaseReadyPunish().getPunishAdvice());
			 resultVO.setLawJson(ydzfInspectCaseReadyPunishResultVO.getLawJson());
		 }
			try {
				List<String> fileIdList = StringMyUtils.transArrayToListString(
						ydzfInspectCaseFinish.getFileIds(), ",");
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
	public List<YdzfInspectCaseFinishResultVO> queryInspectCaseFinishInfoVOList(
			TYdzfInspectCaseFinish ydzfInspectCaseFinish)
			throws BusinessException, SystemException {
		TYdzfInspectCaseFinishExample example=this.getCondition(ydzfInspectCaseFinish);
		List<TYdzfInspectCaseFinish> list= ydzfInspectCaseFinishMapper.selectByExample(example);
		//TODO:待优化
		 List<YdzfInspectCaseFinishResultVO>  volist =new ArrayList<YdzfInspectCaseFinishResultVO>();
		 if(!CollectionUtils.isEmpty(list))
		 {
			 for(TYdzfInspectCaseFinish bean:list)
			 {
				 //TODO:待优化
				 YdzfInspectCaseFinishResultVO vo = new YdzfInspectCaseFinishResultVO();
				 vo=this.queryInspectCaseFinishInfo(bean.getInspectCaseFinishId());
				 volist.add(vo);
			 }
			
		 }
		 return volist;
		
	}
	private TYdzfInspectCaseFinishExample getCondition(TYdzfInspectCaseFinish record) {
		TYdzfInspectCaseFinishExample example=new TYdzfInspectCaseFinishExample();
			if (record != null) {
		com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseFinishExample.Criteria cr=example.createCriteria();
		if(record.getInspectCaseFinishId()!= null && record.getInspectCaseFinishId()>0) {
		cr.andInspectCaseFinishIdEqualTo(record.getInspectCaseFinishId());
		}
		if(record.getInspectCaseId()!= null && record.getInspectCaseId()>0) {
		cr.andInspectCaseIdEqualTo(record.getInspectCaseId());
		}
		if(record.getInspectCaseExecutePunishId()!= null && record.getInspectCaseExecutePunishId()>0) {
		cr.andInspectCaseExecutePunishIdEqualTo(record.getInspectCaseExecutePunishId());
		}
		if(record.getFinishDate()!= null){
		cr.andFinishDateEqualTo(record.getFinishDate());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getFileIds())){
		cr.andFileIdsEqualTo(record.getFileIds());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getPunishStatus())){
		cr.andPunishStatusEqualTo(record.getPunishStatus());
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
