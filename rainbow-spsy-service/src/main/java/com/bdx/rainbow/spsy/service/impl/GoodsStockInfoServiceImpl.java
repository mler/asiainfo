package com.bdx.rainbow.spsy.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.spsy.bean.GoodStockInfo;
import com.bdx.rainbow.spsy.common.DateUtil;
import com.bdx.rainbow.spsy.common.SpsyConstants;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginGoodsStockDAO;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginMerchantStockOutDAO;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginSkuAgencyRelationDAO;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginAgencyCompany;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginGoodsStock;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginGoodsStockExample;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginMerchantStockOut;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginMerchantStockOutExample;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginSkuAgencyRelationKey;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginMerchantStockOutExample.Criteria;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginSkuAgencyRelation;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginSkuAgencyRelationExample;
import com.bdx.rainbow.spsy.service.IAgencyCompanyService;
import com.bdx.rainbow.spsy.service.IGoodsStockInfoService;

public class GoodsStockInfoServiceImpl implements IGoodsStockInfoService {
    @Autowired
    private TOriginMerchantStockOutDAO originMerchantStockOutDAO;
    
    @Autowired
    private TOriginGoodsStockDAO originGoodsStockDAO;
	
    @Autowired
    private TOriginSkuAgencyRelationDAO originSkuAgencyRelationDAO;
    
    @Autowired
    private IAgencyCompanyService agencyCompanyService;
    
	@Override
	public List<TOriginMerchantStockOut> getMerchantStockList(TOriginMerchantStockOut condition, int start, int limit) throws BusinessException {
		TOriginMerchantStockOutExample example = convertExample(condition);	
		if(start >= 0){
			example.setLimitClauseCount(limit);
			example.setLimitClauseStart(start);
		}
		return originMerchantStockOutDAO.selectByExample(example);
	}

	@Override
	public Integer countMerchantStock(TOriginMerchantStockOut condition) throws BusinessException {
		TOriginMerchantStockOutExample example = convertExample(condition);		
		return originMerchantStockOutDAO.countByExample(example);
	}

	@Override
	public void insertMerchantStockOut(TOriginMerchantStockOut stockOut) throws BusinessException {
		stockOut.setCreateTime(DateUtil.getCurrent());
		originMerchantStockOutDAO.insertSelective(stockOut);
	}
	
	@Override
	public void updateMerchantStockOut(TOriginMerchantStockOut stockOut) throws BusinessException {
	    stockOut.setUpdateTime(DateUtil.getCurrent());	
	    originMerchantStockOutDAO.updateByPrimaryKeySelective(stockOut);
	}
	
	@Override
	public TOriginMerchantStockOut getMerchantStock(int operationOutId) throws BusinessException {		
		return originMerchantStockOutDAO.selectByPrimaryKey(operationOutId);
	}
	
	private TOriginMerchantStockOutExample convertExample(TOriginMerchantStockOut stockOut) {
		TOriginMerchantStockOutExample example = new TOriginMerchantStockOutExample();
		Criteria cr = example.createCriteria();
		if(stockOut==null){
		  return example;
		}
		if(StringUtils.isNotBlank(stockOut.getSkuName())){
		    cr.andSkuNameLike("%"+stockOut.getSkuName()+"%"); 
		}
		if(stockOut.getEnterpriseId()!=null){
		    cr.andEnterpriseIdEqualTo(stockOut.getEnterpriseId());
		}
		return example;
	}

	@Override
	public List<TOriginGoodsStock> getGoodsStockList(TOriginGoodsStock condition, int start, int limit) throws BusinessException {
		TOriginGoodsStockExample example = convertGoodsExample(condition);
		if(start >= 0){
			example.setLimitClauseCount(limit);
			example.setLimitClauseStart(start);
		}
		return originGoodsStockDAO.selectByExample(example);
	}

	@Override
	public Integer countGoodsStock(TOriginGoodsStock condition) throws BusinessException {
		TOriginGoodsStockExample example = convertGoodsExample(condition);
		return originGoodsStockDAO.countByExample(example);
	}
	
	@Override
	public void insertGoodsStock(TOriginGoodsStock stock) throws BusinessException {
		stock.setCreateTime(DateUtil.getCurrent());
		stock.setStatus(SpsyConstants.STATUS_STOCK_NO_OUT);
		originGoodsStockDAO.insertSelective(stock);		
	}

	@Override
	public void updateGoodsStock(TOriginGoodsStock stock) throws BusinessException {
		stock.setUpdateTime(DateUtil.getCurrent());
		originGoodsStockDAO.updateByPrimaryKeySelective(stock);
	}

	@Override
	public TOriginGoodsStock getGoodsStock(int operationId) throws BusinessException {
		return originGoodsStockDAO.selectByPrimaryKey(operationId);
	}
	
	private TOriginGoodsStockExample convertGoodsExample(TOriginGoodsStock stock) {
		TOriginGoodsStockExample example = new TOriginGoodsStockExample();
		com.bdx.rainbow.spsy.dal.ibatis.model.TOriginGoodsStockExample.Criteria cr = example.createCriteria();
		if(stock==null){
		  return example;
		}
		if(StringUtils.isNotBlank(stock.getSkuName())){
		    cr.andSkuNameLike("%"+stock.getSkuName()+"%"); 
		}
		if(StringUtils.isNotBlank(stock.getOperationType())){
		    cr.andOperationTypeEqualTo(stock.getOperationType());
		}
		if(stock.getBuyAgencyId()!=null){
			cr.andBuyAgencyIdEqualTo(stock.getBuyAgencyId());
		}
		if(StringUtils.isNotBlank(stock.getSkuBatch())){
			cr.andSkuBatchEqualTo(stock.getSkuBatch());
		}
		if(StringUtils.isNotBlank(stock.getSkuBarCode())){
			cr.andSkuBarCodeLike("%"+stock.getSkuBarCode()+"%");
		}
		if(stock.getProductionDate()!=null){
			cr.andProductionDateEqualTo(stock.getProductionDate());
		}
		if(StringUtils.isNotBlank(stock.getStatus())){
			cr.andStatusEqualTo(stock.getStatus());
		}
		return example;
	}

	@Override
	public List<TOriginSkuAgencyRelation> getSkuList(TOriginSkuAgencyRelation condition) throws BusinessException {
		TOriginSkuAgencyRelationExample example = convertSkuExample(condition);
		return originSkuAgencyRelationDAO.selectByExample(example);
	}

    private TOriginSkuAgencyRelationExample convertSkuExample(TOriginSkuAgencyRelation condition) {
    	TOriginSkuAgencyRelationExample example = new TOriginSkuAgencyRelationExample();
    	com.bdx.rainbow.spsy.dal.ibatis.model.TOriginSkuAgencyRelationExample.Criteria cr = example.createCriteria();
    	if(condition==null){
    		return example;
    	}
    	if(StringUtils.isNotBlank(condition.getSkuName())){
    		cr.andSkuNameLike("%"+condition.getSkuName()+"%");
    	}
    	if(condition.getAgencyId()!=null){
    		cr.andAgencyIdEqualTo(condition.getAgencyId());
    	}
    	return example;
    }

	@Override
	public void insertGoodsStockFromIn(GoodStockInfo stock,Integer enterpriseId) throws BusinessException {
		// TODO Auto-generated method stub
		//经销商是否存在
		if(stock.getBuyAgencyId()==null){
			boolean agencyexist = this.getAgencyInfo(stock.getBuyAgencyName(),stock.getAgencyLinkPhone());
			if(!agencyexist){
				TOriginAgencyCompany company = new TOriginAgencyCompany();
				company.setAgencyName(stock.getBuyAgencyName());
				company.setAgencyResponsiblePerson(stock.getAgencyManagePerson());
				company.setLinkPhone(stock.getAgencyLinkPhone());
				company.setCreateEnterpriseId(enterpriseId);
				int agencyId = agencyCompanyService.insertAgency(company);
				stock.setBuyAgencyId(agencyId);
			}
		}
		// 添加出库信息
		stock.setCreateTime(DateUtil.getCurrent());
		String stockins = stock.getOprationIds();
		if (stockins != null && !stockins.equals("")) {
			String[] oprationIds = stockins.split(";");
			for (int i = 0; i < oprationIds.length; i++) {
				String operationId = oprationIds[i];
				TOriginGoodsStock stockin = originGoodsStockDAO.selectByPrimaryKey(Integer.parseInt(operationId));
				TOriginGoodsStock newstock = new TOriginGoodsStock();
				BeanUtils.copyProperties(stockin, newstock);
				newstock.setOperationType("02");
				newstock.setManagerPerson(stock.getManagerPerson());
				newstock.setBuyAgencyId(stock.getBuyAgencyId());
				newstock.setBuyAgencyName(stock.getBuyAgencyName());
				newstock.setCreateTime(stock.getCreateTime());
				newstock.setCreateStaff(stock.getCreateStaff());
				newstock.setSupplyAgencyId(stock.getSupplyAgencyId());
				newstock.setSupplyAgencyName(stock.getSupplyAgencyName());
				newstock.setOperationDate(stock.getCreateTime());
				originGoodsStockDAO.insertSelective(newstock);
				// 修改入库操作记录的状态
				stockin.setStatus(SpsyConstants.STATUS_STOCK_OUT);
				originGoodsStockDAO.updateByPrimaryKeySelective(stockin);

				// 添加产品和经销商的关系
				TOriginSkuAgencyRelationKey key = new TOriginSkuAgencyRelationKey();
				key.setAgencyId(stock.getBuyAgencyId());
				key.setSkuId(stockin.getSkuId());
				TOriginSkuAgencyRelation relation = originSkuAgencyRelationDAO.selectByPrimaryKey(key);
				if (relation == null) {
					relation = new TOriginSkuAgencyRelation();
					relation.setSkuId(stockin.getSkuId());
					relation.setSkuBarCode(stockin.getSkuBarCode());
					relation.setSkuName(stockin.getSkuName());
					relation.setAgencyId(stock.getBuyAgencyId());
					relation.setCreateTime(DateUtil.getCurrent());
					relation.setCreateStaff(stock.getCreateStaff());
					originSkuAgencyRelationDAO.insertSelective(relation);
				}
			}
		}		
	}
	
	public boolean getAgencyInfo(String agencyName,String linkPhone) throws BusinessException{
		boolean exist = false;
		TOriginAgencyCompany company = new TOriginAgencyCompany();
		company.setAgencyName(agencyName);
		company.setLinkPhone(linkPhone);
		List<TOriginAgencyCompany> companys = agencyCompanyService.getAgencyList(company,0,10);
		if(companys!=null && companys.size()>0){
			exist = false;
		}
		return  exist;
	}

	@Override
	public void updateGoodStockOut(GoodStockInfo stock, Integer enterpriseId) throws BusinessException {
		// TODO Auto-generated method stub
		// 经销商是否存在
		if (stock.getBuyAgencyId() == null) {
			boolean agencyexist = this.getAgencyInfo(stock.getBuyAgencyName(),stock.getAgencyLinkPhone());
			if (!agencyexist) {
				TOriginAgencyCompany company = new TOriginAgencyCompany();
				company.setAgencyName(stock.getBuyAgencyName());
				company.setAgencyResponsiblePerson(stock.getAgencyManagePerson());
				company.setLinkPhone(stock.getAgencyLinkPhone());
				company.setCreateEnterpriseId(enterpriseId);
				int agencyId = agencyCompanyService.insertAgency(company);
				stock.setBuyAgencyId(agencyId);
			}
		}
		
		TOriginGoodsStock oldstock = originGoodsStockDAO.selectByPrimaryKey(stock.getOperationId());
		// 添加产品和经销商的关系
		if(!stock.getBuyAgencyId().equals(oldstock.getBuyAgencyId())){
			TOriginSkuAgencyRelationKey key = new TOriginSkuAgencyRelationKey();
			key.setAgencyId(stock.getBuyAgencyId());
			key.setSkuId(stock.getSkuId());
			TOriginSkuAgencyRelation relation = originSkuAgencyRelationDAO.selectByPrimaryKey(key);
			if (relation == null) {
				relation = new TOriginSkuAgencyRelation();
				relation.setSkuId(oldstock.getSkuId());
				relation.setSkuBarCode(oldstock.getSkuBarCode());
				relation.setSkuName(oldstock.getSkuName());
				relation.setAgencyId(stock.getBuyAgencyId());
				relation.setCreateTime(DateUtil.getCurrent());
				relation.setCreateStaff(stock.getCreateStaff());
				originSkuAgencyRelationDAO.insertSelective(relation);
			}
		}
		
		//修改出库记录
		TOriginGoodsStock stockout = new TOriginGoodsStock();
		stockout.setUpdateTime(DateUtil.getCurrent());
		stockout.setUpdateStaff(stock.getUpdateStaff());
		stockout.setBuyAgencyId(stock.getBuyAgencyId());
		stockout.setBuyAgencyName(stock.getBuyAgencyName());
		stockout.setOperationId(stock.getOperationId());
		stockout.setManagerPerson(stock.getManagerPerson());		
		originGoodsStockDAO.updateByPrimaryKeySelective(stockout);		
	}
}
