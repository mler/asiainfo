package com.bdx.rainbow.spsy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.spsy.bean.ProducerStockOutInfo;
import com.bdx.rainbow.spsy.common.DateUtil;
import com.bdx.rainbow.spsy.common.SpsyConstants;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginProducerStockDetailInDAO;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginProducerStockInDAO;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginProducerStockOutDAO;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginSkuAgencyRelationDAO;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginAgencyCompany;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockDetailIn;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockDetailInExample;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockIn;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockInExample;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginPurchaseInfo;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginSkuAgencyRelation;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginSkuAgencyRelationKey;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockInExample.Criteria;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockOut;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockOutExample;
import com.bdx.rainbow.spsy.service.IAgencyCompanyService;
import com.bdx.rainbow.spsy.service.IIdentificationCodeService;
import com.bdx.rainbow.spsy.service.IMaterialInfoService;
import com.bdx.rainbow.spsy.service.IProducerStockService;

public class ProducerStockServiceImpl implements IProducerStockService {
    @Autowired
    private TOriginProducerStockInDAO originProducerStockInDAO;
    
    @Autowired
    private TOriginProducerStockDetailInDAO originProducerStockDetailInDAO;
    
    @Autowired
    private TOriginProducerStockOutDAO originProducerStockOutDAO;
    
    @Autowired
    private IIdentificationCodeService identificationCodeService;
    
    @Autowired
    private IAgencyCompanyService agencyCompanyService;
    
    @Autowired
    private TOriginSkuAgencyRelationDAO originSkuAgencyRelationDAO;
	
    @Autowired
	private IMaterialInfoService materialInfoService;
    
	@Override
	public List<TOriginProducerStockIn> getProducerStockInList(TOriginProducerStockIn condition, int start, int limit) throws BusinessException {
		TOriginProducerStockInExample example = convertExample(condition);
		if(start >= 0){
			example.setLimitClauseCount(limit);
			example.setLimitClauseStart(start);
		}
		return originProducerStockInDAO.selectByExample(example);
	}

	@Override
	public Integer coutProducerStockIns(TOriginProducerStockIn condition) throws BusinessException {
		TOriginProducerStockInExample example = convertExample(condition);
		return originProducerStockInDAO.countByExample(example);
	}

	public TOriginProducerStockInExample convertExample(TOriginProducerStockIn condition) throws BusinessException{
		TOriginProducerStockInExample example = new TOriginProducerStockInExample();
		Criteria cr = example.createCriteria();
		if(condition==null){
			return example;
		}
		if(StringUtil.isNotBlank(condition.getSkuName())){
			cr.andSkuNameLike("%"+condition.getSkuName()+"%");
		}
		if(condition.getEnterpriseId()!=null){
			cr.andEnterpriseIdEqualTo(condition.getEnterpriseId());
		}
		if(StringUtil.isNotBlank(condition.getSkuBarCode())){
			cr.andSkuBarCodeLike("%"+condition.getSkuBarCode()+"%");
		}
		if(condition.getProductionDate()!=null){
			cr.andProductionDateEqualTo(condition.getProductionDate());
		}
		if(StringUtil.isNotBlank(condition.getSkuBatch())){
			cr.andSkuBatchEqualTo(condition.getSkuBatch());
		}
		if(StringUtil.isNotBlank(condition.getStatus())){
			cr.andStatusEqualTo(condition.getStatus());
		}
		return example;
	}

	@Override
	public void insertProducerStockIn(TOriginProducerStockIn stockIn, String codelist) throws BusinessException {
		stockIn.setCreateTime(DateUtil.getCurrent());
		stockIn.setStatus(SpsyConstants.STATUS_STOCK_NO_OUT);
		stockIn.setOperationDate(DateUtil.getCurrent());
		int operationInId = originProducerStockInDAO.insertSelective(stockIn);
		
		TOriginProducerStockDetailIn detailin = new TOriginProducerStockDetailIn();
        BeanUtils.copyProperties(stockIn, detailin);
        detailin.setOperationInId(operationInId);
		if(codelist!=null && !codelist.equals("")){
			String[] codes = codelist.split(";");
			for(int i=0;i<codes.length;i++){
				detailin.setSkuIdCode(codes[i]);
				originProducerStockDetailInDAO.insertSelective(detailin);
				identificationCodeService.changeStatus(codes[i],SpsyConstants.CODE_USED);
			}
		}
		//修改标识码的状态
		if(stockIn.getPackingIdCode()!=null && !stockIn.getPackingIdCode().equals("")){
			identificationCodeService.changeStatus(stockIn.getPackingIdCode(),SpsyConstants.CODE_USED);
		}
	}
	
	@Override
	public List<TOriginProducerStockOut> getProducerStockOutList(TOriginProducerStockOut condition, int start, int limit) throws BusinessException {
		TOriginProducerStockOutExample example = convertOutExample(condition);
		if(start >= 0){
			example.setLimitClauseCount(limit);
			example.setLimitClauseStart(start);
		}
		return originProducerStockOutDAO.selectByExample(example);
	}

	@Override
	public Integer countProducerStockOuts(TOriginProducerStockOut condition) throws BusinessException {
		TOriginProducerStockOutExample example = convertOutExample(condition);
		return originProducerStockOutDAO.countByExample(example);
	}
	
	@Override
	public void insertProducerStockOut(ProducerStockOutInfo stockOut,Integer enterpriseId) throws BusinessException {
		//经销商是否存在
		if(stockOut.getAgencyId()==null){
			   int oldagencyId = agencyCompanyService.getAgencyInfo(stockOut.getAgencyName(),stockOut.getAgencyLinkPhone());
			   if(oldagencyId==0){
				   TOriginAgencyCompany company = new TOriginAgencyCompany();
				   company.setAgencyName(stockOut.getAgencyName());
				   company.setAgencyResponsiblePerson(stockOut.getAgencyManagePerson());
				   company.setLinkPhone(stockOut.getAgencyLinkPhone());
				   company.setCreateEnterpriseId(enterpriseId);
				   int agencyId = agencyCompanyService.insertAgency(company);
				   stockOut.setAgencyId(agencyId);
			   }
		}
		
		//添加出库信息
		stockOut.setCreateTime(DateUtil.getCurrent());
		String stockins = stockOut.getOprationIds();
		if(stockins!=null && !stockins.equals("")){
			String[] oprationIds = stockins.split(";");
			for(int i=0;i<oprationIds.length;i++){
				String operationId = oprationIds[i];
				TOriginProducerStockIn stock = originProducerStockInDAO.selectByPrimaryKey(Integer.parseInt(operationId));
				TOriginProducerStockOut newstock = new TOriginProducerStockOut();
				BeanUtils.copyProperties(stock, newstock);
				newstock.setManagePerson(stockOut.getManagePerson());
				newstock.setEnterpriseId(stockOut.getEnterpriseId());
				newstock.setCreateTime(stockOut.getCreateTime());
				newstock.setCreateStaff(stockOut.getCreateStaff());
				newstock.setAgencyId(stockOut.getAgencyId());
				newstock.setAgencyName(stockOut.getAgencyName());
				newstock.setPackingStartNumber(stock.getPackingNumber());
				newstock.setOutSingleQuantity(stock.getSingleQuantity());
				newstock.setOutStorageType(stock.getStorageType());
				newstock.setOperationDate(stockOut.getCreateTime());
				originProducerStockOutDAO.insertSelective(newstock);
				//修改入库操作记录的状态
				stock.setStatus(SpsyConstants.STATUS_STOCK_OUT);
				originProducerStockInDAO.updateByPrimaryKeySelective(stock);
				
				//添加产品和经销商的关系
				TOriginSkuAgencyRelationKey key = new TOriginSkuAgencyRelationKey();
				key.setAgencyId(stockOut.getAgencyId());
				key.setSkuId(stock.getSkuId());
				TOriginSkuAgencyRelation relation = originSkuAgencyRelationDAO.selectByPrimaryKey(key);
				if(relation==null){
					relation = new TOriginSkuAgencyRelation();
					relation.setSkuId(stock.getSkuId());
					relation.setSkuBarCode(stock.getSkuBarCode());
					relation.setSkuName(stock.getSkuName());
					relation.setAgencyId(stockOut.getAgencyId());
					relation.setCreateTime(DateUtil.getCurrent());
					relation.setCreateStaff(stockOut.getCreateStaff());
					originSkuAgencyRelationDAO.insertSelective(relation);
				}
			}
		}		
	}
	
	public TOriginProducerStockOutExample convertOutExample(TOriginProducerStockOut condition) throws BusinessException{
		TOriginProducerStockOutExample example = new TOriginProducerStockOutExample();
		com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockOutExample.Criteria cr = example.createCriteria();
		if(condition==null){
			return example;
		}
		if(StringUtil.isNotBlank(condition.getSkuName())){
			cr.andSkuNameLike("%"+condition.getSkuName()+"%");
		}
		if(condition.getEnterpriseId()!=null){
			cr.andEnterpriseIdEqualTo(condition.getEnterpriseId());
		}
		return example;
	}

	@Override
	public Map<String, Object> getProducerStockIn(Integer operationInId) throws BusinessException {
		Map<String,Object> map = new HashMap<String,Object>();
		String codelist = "";
		String materialshow = "";
		TOriginProducerStockIn stockIn = originProducerStockInDAO.selectByPrimaryKey(operationInId);
		if(stockIn!=null){
			TOriginProducerStockDetailInExample example = new TOriginProducerStockDetailInExample();
			example.createCriteria().andOperationInIdEqualTo(stockIn.getOperationInId());
			List<TOriginProducerStockDetailIn> detailins = originProducerStockDetailInDAO.selectByExample(example);
			if(detailins!=null && detailins.size()>0){
				for(int i=0;i<detailins.size();i++){
					TOriginProducerStockDetailIn detailin = detailins.get(i);
					codelist = codelist + detailin.getSkuIdCode();
				}
			}
			
			String materialstr = stockIn.getMaterialInfo();
			if(materialstr!=null && !materialstr.equals("")){
				String[] materials = materialstr.split(";");
				for(int j=0;j<materials.length;j++){
					TOriginPurchaseInfo purchaseInfo = materialInfoService.getPurchaseInfoBykey(Integer.parseInt(materials[j]));
					if(purchaseInfo!=null){
						materialshow = materialshow+purchaseInfo.getMaterialName()+";";
					}
				}
			}
		}
		map.put("stock", stockIn);
		map.put("codelist", codelist);
		map.put("materialshow", materialshow);
		return map;
	}

	@Override
	public void updateProducerStockIn(TOriginProducerStockIn stockIn,String codelist) throws BusinessException {
		TOriginProducerStockIn oldstockin = originProducerStockInDAO.selectByPrimaryKey(stockIn.getOperationInId());
		//修改装箱标识码
		if(oldstockin.getPackingIdCode()!=null && !oldstockin.getPackingIdCode().equals("")){
			if(stockIn.getPackingIdCode()!=null && !stockIn.getPackingIdCode().equals("")){
				if(!stockIn.getPackingIdCode().equals(oldstockin.getPackingIdCode())){
					identificationCodeService.changeStatus(oldstockin.getPackingIdCode(),SpsyConstants.CODE_UN_USE);
					identificationCodeService.changeStatus(stockIn.getPackingIdCode(),SpsyConstants.CODE_USED);
				}
			}else{
				identificationCodeService.changeStatus(oldstockin.getPackingIdCode(),SpsyConstants.CODE_UN_USE);
			}
		}else{
			if(stockIn.getPackingIdCode()!=null && !stockIn.getPackingIdCode().equals("")){
				identificationCodeService.changeStatus(stockIn.getPackingIdCode(),SpsyConstants.CODE_USED);
			}
		}
			
		stockIn.setUpdateTime(DateUtil.getCurrent());
		originProducerStockInDAO.updateByPrimaryKeySelective(stockIn);
		
		//删除原有的标识码
		TOriginProducerStockDetailInExample example = new TOriginProducerStockDetailInExample();
		example.createCriteria().andOperationInIdEqualTo(stockIn.getOperationInId());
		List<TOriginProducerStockDetailIn> detailins = originProducerStockDetailInDAO.selectByExample(example);
		if(detailins!=null && detailins.size()>0){
		    for(int j=0;j<detailins.size();j++){
		    	TOriginProducerStockDetailIn detail = detailins.get(j);
		    	identificationCodeService.changeStatus(detail.getSkuIdCode(),SpsyConstants.CODE_UN_USE);
		    }			
			originProducerStockDetailInDAO.deleteByExample(example);
		}
		
		//新增标识码
		TOriginProducerStockDetailIn detailin = new TOriginProducerStockDetailIn();
        BeanUtils.copyProperties(stockIn, detailin);
        detailin.setOperationInId(stockIn.getOperationInId());
		if(codelist!=null && !codelist.equals("")){
			String[] codes = codelist.split(";");
			for(int i=0;i<codes.length;i++){
				detailin.setSkuIdCode(codes[i]);
				originProducerStockDetailInDAO.insertSelective(detailin);
				identificationCodeService.changeStatus(codes[i],SpsyConstants.CODE_USED);
			}
		}		
	}

	@Override
	public Map<String,Object> getStockOutInf(Integer operationOutId) throws BusinessException {
		Map<String,Object> map = new HashMap<String,Object>();
		TOriginProducerStockOut stockOut = originProducerStockOutDAO.selectByPrimaryKey(operationOutId);
		String materialshow = "";
		if(stockOut!=null){
			String materialstr = stockOut.getMaterialInfo();
			if(materialstr!=null && !materialstr.equals("")){
				String[] materials = materialstr.split(";");
				for(int j=0;j<materials.length;j++){
					TOriginPurchaseInfo purchaseInfo = materialInfoService.getPurchaseInfoBykey(Integer.parseInt(materials[j]));
					if(purchaseInfo!=null){
						materialshow = materialshow+purchaseInfo.getMaterialName()+";";
					}
				}
			}
		}
		map.put("stockOut", stockOut);
		map.put("materialshow", materialshow);
		return map;
	}

	@Override
	public void updateProducerStockOut(ProducerStockOutInfo stockOut,Integer enterpriseId) throws BusinessException {
		TOriginProducerStockOut oldstock = originProducerStockOutDAO.selectByPrimaryKey(stockOut.getOperationOutId());
		if(stockOut.getAgencyId()==null){
			int oldagencyId = agencyCompanyService.getAgencyInfo(stockOut.getAgencyName(),stockOut.getAgencyLinkPhone());
			if (oldagencyId==0) {
				TOriginAgencyCompany company = new TOriginAgencyCompany();
				company.setAgencyName(stockOut.getAgencyName());
				company.setAgencyResponsiblePerson(stockOut.getAgencyManagePerson());
				company.setLinkPhone(stockOut.getAgencyLinkPhone());
				company.setCreateEnterpriseId(stockOut.getEnterpriseId());
				int agencyId = agencyCompanyService.insertAgency(company);
				stockOut.setAgencyId(agencyId);
			}
		}
		if(!stockOut.getAgencyId().equals(oldstock.getAgencyId())){
			//添加产品和经销商的关系
			TOriginSkuAgencyRelationKey key = new TOriginSkuAgencyRelationKey();
			key.setAgencyId(stockOut.getAgencyId());
			key.setSkuId(stockOut.getSkuId());
			TOriginSkuAgencyRelation relation = originSkuAgencyRelationDAO.selectByPrimaryKey(key);
			if(relation==null){
				relation = new TOriginSkuAgencyRelation();
				relation.setSkuId(oldstock.getSkuId());
				relation.setSkuBarCode(oldstock.getSkuBarCode());
				relation.setSkuName(oldstock.getSkuName());
				relation.setAgencyId(stockOut.getAgencyId());
				relation.setCreateTime(DateUtil.getCurrent());
				relation.setCreateStaff(stockOut.getUpdatStaff());
				originSkuAgencyRelationDAO.insertSelective(relation);
			}
		}
		
		TOriginProducerStockOut newstock = new TOriginProducerStockOut();
		newstock.setAgencyId(stockOut.getAgencyId());
		newstock.setAgencyName(stockOut.getAgencyName());
		newstock.setManagePerson(stockOut.getManagePerson());
		newstock.setOperationOutId(stockOut.getOperationOutId());
		newstock.setUpdatStaff(stockOut.getUpdatStaff());
		newstock.setUpdateTime(DateUtil.getCurrent());
		originProducerStockOutDAO.updateByPrimaryKeySelective(newstock);
	}

}
