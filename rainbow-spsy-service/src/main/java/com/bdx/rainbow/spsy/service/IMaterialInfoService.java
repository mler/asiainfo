package com.bdx.rainbow.spsy.service;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.spsy.bean.MaterialPurchaseInfo;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginMaterialInfo;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginPurchaseInfo;

/**
 * 原料信息和采购原料的操作
 * @author tanglian 2016-01-27
 *
 */
public interface IMaterialInfoService {
	 /**
	  * 新增原料数据
	  * @param materialInfo
	  * @throws BusinessException
	  */
     public void insertMaterialInfo(TOriginMaterialInfo materialInfo) throws BusinessException;
     
     /**
	  * 修改原料数据
	  * @param materialInfo
	  * @throws BusinessException
	  */
     public void updateMaterialInfo(TOriginMaterialInfo materialInfo) throws BusinessException;
     
     /**
      * 新增采购订单
      * @param purchaseInfo
      * @throws BusinessException
      */
     public void insertPurchaseInfo(MaterialPurchaseInfo purchaseInfo) throws BusinessException;
     
     /**
      * 修改采购订单
      * @param purchaseInfo
      * @throws BusinessException
      */
     public void updatePurchaseInfo(MaterialPurchaseInfo purchaseInfo) throws BusinessException;
     
     /**
      * 查询采购订单数据
      * @param purchaseInfo
      * @param start
      * @param size
      * @return
      * @throws BusinessException
      */
     public List<MaterialPurchaseInfo> getPurchaseInfoList(TOriginPurchaseInfo purchaseInfo,int start,int size) throws BusinessException;
     
     /**
      * 根据主键查询采购订单
      * @param purchaseId
      * @return
      * @throws BusinessException
      */
     public MaterialPurchaseInfo getPurchaseInfoBykey(Integer purchaseId) throws BusinessException;
     
     /**
      * 查询的记录总数
      * @param purchaseInfo
      * @return
      * @throws BusinessException
      */
     public Integer countPurchaseInfo(TOriginPurchaseInfo purchaseInfo) throws BusinessException;
     
     /**
      * 查询采购订单信息
      * @param purchaseInfo
      * @return
      * @throws BusinessException
      */
     public List<MaterialPurchaseInfo> getPurchaseInfos(MaterialPurchaseInfo purchaseInfo) throws BusinessException;
}
