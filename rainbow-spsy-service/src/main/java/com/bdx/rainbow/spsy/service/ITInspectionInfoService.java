package com.bdx.rainbow.spsy.service;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginInspectionInfo;

/**
 * 抽检信息的操作
 * @author tanglian 2016-1-25
 *
 */
public interface ITInspectionInfoService {
    /**
     * 获取抽检信息列表
     * @param example
     * @return
     * @throws BusinessException
     */
	public List<TOriginInspectionInfo> getInspectionInfoList(TOriginInspectionInfo inspectionInfo,int start,int limit) throws BusinessException;
	
	/**
	 * 检测报告录入和修改
	 * @param inspectionInfo
	 * @throws BusinessException
	 */
	public void insertInspectionInfo(TOriginInspectionInfo inspectionInfo) throws BusinessException;
	
	/**
	 * 查询检测报告
	 * @param productDetectionId
	 * @return
	 * @throws BusinessException
	 */
	public TOriginInspectionInfo getInspectionInfo(Integer productDetectionId) throws BusinessException;
	
	/**
	 * 获取查询到的记录总数
	 * @param inspectionInfo
	 * @return
	 * @throws BusinessException
	 */
	public Integer countInspectionInfo(TOriginInspectionInfo inspectionInfo) throws BusinessException;
	
	/**
	 * 删除检测报告
	 * @param inspeIds
	 * @throws BusinessException
	 */
	public void deleteInspection(List<Integer> inspeIds) throws BusinessException;
	
	/**
	 * 查询检测报告
	 * @param inspectionInfo
	 * @return
	 * @throws BusinessException
	 */
	public Integer getSelectInspe(TOriginInspectionInfo inspectionInfo) throws BusinessException;
}
