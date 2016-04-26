package com.bdx.rainbow.spsy.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.spsy.common.SpsyConstants;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginInspectionInfoDAO;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginInspectionInfo;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginInspectionInfoExample;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginInspectionInfoExample.Criteria;
import com.bdx.rainbow.spsy.service.ITInspectionInfoService;


public class TInspectionInfoServiceImpl implements ITInspectionInfoService {
    @Autowired
    private TOriginInspectionInfoDAO originInspectionInfoDAO;
    
	@Override
	public List<TOriginInspectionInfo> getInspectionInfoList(TOriginInspectionInfo inspectionInfo,int start,int limit) throws BusinessException {
		TOriginInspectionInfoExample example = convertExample(inspectionInfo);
		if(start >= 0){
			example.setLimitClauseCount(limit);
			example.setLimitClauseStart(start);
		}
		return originInspectionInfoDAO.selectByExample(example);
	}

	@Override
	public TOriginInspectionInfo getInspectionInfo(Integer productDetectionId) throws BusinessException {
		return originInspectionInfoDAO.selectByPrimaryKey(productDetectionId);
	}
	
	@Override
	public void insertInspectionInfo(TOriginInspectionInfo inspectionInfo) throws BusinessException {
		Timestamp createTime = DateUtil.getCurrent();
		Integer productDetectionId = inspectionInfo.getProductDetectionId();
		if(productDetectionId!=null){
			inspectionInfo.setCreateTime(createTime);
			originInspectionInfoDAO.updateByPrimaryKeySelective(inspectionInfo);
		}else{
			inspectionInfo.setUpdateTime(createTime);
			inspectionInfo.setStatus(SpsyConstants.STATUS_ACTIVE);
			originInspectionInfoDAO.insertSelective(inspectionInfo);
		}
	}
	
	@Override
	public Integer countInspectionInfo(TOriginInspectionInfo inspectionInfo) throws BusinessException {
		TOriginInspectionInfoExample example = convertExample(inspectionInfo);
		return originInspectionInfoDAO.countByExample(example);
	}
	
	private TOriginInspectionInfoExample convertExample(TOriginInspectionInfo inspectionInfo) {		
		TOriginInspectionInfoExample example = new TOriginInspectionInfoExample();
		Criteria criteria= example.createCriteria();		
		if(inspectionInfo == null ){
			return example;
		}
		
		if(StringUtils.isNotBlank(inspectionInfo.getDetectionObject())){
			criteria.andDetectionObjectEqualTo(inspectionInfo.getDetectionObject());
		}
		if(StringUtils.isNotBlank(inspectionInfo.getDetectionBatch())){
			criteria.andDetectionBatchEqualTo(inspectionInfo.getDetectionBatch());
		}
		if(StringUtils.isNotBlank(inspectionInfo.getStatus())){
			criteria.andStatusEqualTo(inspectionInfo.getStatus());
		}
		if(inspectionInfo.getCompanyId()!=null){
			criteria.andCompanyIdEqualTo(inspectionInfo.getCompanyId());
		}
		return example;
	}

	@Override
	public void deleteInspection(List<Integer> inspeIds) throws BusinessException {
		for(int i=0;i<inspeIds.size();i++){
			int inspe = inspeIds.get(i);
			TOriginInspectionInfo inspeinfo = new TOriginInspectionInfo();
			inspeinfo.setProductDetectionId(inspe);
			inspeinfo.setStatus(SpsyConstants.STATUS_INACTIVE);
			originInspectionInfoDAO.updateByPrimaryKeySelective(inspeinfo);
		}
	}

	@Override
	public Integer getSelectInspe(TOriginInspectionInfo inspectionInfo) throws BusinessException {
		List<TOriginInspectionInfo> infos =  this.getInspectionInfoList(inspectionInfo,-1,0);
		Integer reportId = 0;
		if(infos!=null && infos.size()>0){
			TOriginInspectionInfo info = infos.get(0);
			reportId = info.getProductDetectionId();
		}
		return reportId;
	}
}
