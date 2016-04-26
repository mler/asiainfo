package com.bdx.rainbow.spsy.service;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginIdentificationCode;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginIdentificationCodeDetail;

/**
 * 标识码操作
 * @author tanglian 2016-2-25
 *
 */
public interface IIdentificationCodeService {
	/**
	 * 查询符合条件的标识码列表
	 * @param condition
	 * @param start
	 * @param limit
	 * @return
	 * @throws BusinessException
	 */
    public List<TOriginIdentificationCode> getCodeList(TOriginIdentificationCode condition,int start,int limit) throws BusinessException;

    /**
     * 查询符合条件的记录数
     * @param condition
     * @return
     * @throws BusinessException
     */
    public Integer countCodes(TOriginIdentificationCode condition) throws BusinessException;
    
    /**
     * 新增标识码记录
     * @param code
     * @param skuIdCodeType
     * @throws BusinessException
     */
    public void insertCode(TOriginIdentificationCode code,String skuIdCodeType,int userId) throws Exception;
    
    /**
     * 查询标识码具体信息列表
     * @param condition
     * @return
     * @throws BusinessException
     */
    public List<TOriginIdentificationCodeDetail> getCodeDetailList(TOriginIdentificationCode condition) throws BusinessException;
    
    /**
     * 改变标识码的状态
     * @param codeNumber
     * @param status
     * @throws BusinessException
     */
    public void changeStatus(String codeNumber,String status) throws BusinessException;
    
    /**
     * 判断这个产品是否附过码
     * @param skuId
     * @return 0否 1是
     * @throws Exception
     */
    public String isDefinitionCode(Integer skuId) throws Exception;
    
    /**
     * 查询一条记录的第一个标识码
     * @param buildId
     * @return
     * @throws BusinessException
     */
    public String getCodeNumFirst(Integer buildId) throws BusinessException;
    
    /**
     * 生成xml字符串
     * @param buildId
     * @param codeType
     * @return
     * @throws Exception
     */
    public String createXml(Integer buildId,String codeType) throws Exception;
        
}
