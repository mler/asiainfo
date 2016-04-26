package com.bdx.rainbow.spsy.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.spsy.bean.MaterialPurchaseInfo;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginMaterialInfoDAO;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginPurchaseInfoDAO;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginAgencyCompany;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginMaterialInfo;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginMaterialInfoExample;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginPurchaseInfo;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginPurchaseInfoExample;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginPurchaseInfoExample.Criteria;
import com.bdx.rainbow.spsy.service.IAgencyCompanyService;
import com.bdx.rainbow.spsy.service.IMaterialInfoService;

public class MaterialInfoServiceImpl implements IMaterialInfoService {    
	@Autowired
    private TOriginMaterialInfoDAO originMaterialInfoDAO;
	
	@Autowired
	private TOriginPurchaseInfoDAO originPurchaseInfoDAO;
	
	@Autowired
	private IAgencyCompanyService agencyCompanyService;
	
	@Override
	public void insertMaterialInfo(TOriginMaterialInfo materialInfo) throws BusinessException {
		originMaterialInfoDAO.insertSelective(materialInfo);
	}

	@Override
	public void updateMaterialInfo(TOriginMaterialInfo materialInfo) throws BusinessException {
		originMaterialInfoDAO.updateByPrimaryKeySelective(materialInfo);
	}
	
	@Override
	public void insertPurchaseInfo(MaterialPurchaseInfo purchaseInfo) throws BusinessException {
		Timestamp createTime = DateUtil.getCurrent();
		TOriginPurchaseInfo materpurchase = new TOriginPurchaseInfo();
		BeanUtils.copyProperties(purchaseInfo, materpurchase);
		if(purchaseInfo.getSupplierId()==null){
		   boolean agencyexist = this.getAgencyInfo(purchaseInfo);
		   if(!agencyexist){
			   TOriginAgencyCompany company = new TOriginAgencyCompany();
			   company.setAgencyName(purchaseInfo.getSupplierName());
			   company.setAgencyResponsiblePerson(purchaseInfo.getManagePerson());
			   company.setLinkPhone(purchaseInfo.getLinkPhone());
			   company.setCreateEnterpriseId(purchaseInfo.getEnterpriseId());
			   int agencyId = agencyCompanyService.insertAgency(company);
			   materpurchase.setSupplierId(agencyId);
		   }
		}
		materpurchase.setCreateTime(createTime);
		originPurchaseInfoDAO.insertSelective(materpurchase);
		
		//新增原料信息
		Integer materialId = this.getMaterial(materpurchase);
		TOriginMaterialInfo materialInfo = new TOriginMaterialInfo();
		materialInfo.setMaterialName(materpurchase.getMaterialName());
		materialInfo.setMaterialType(materpurchase.getMaterialType());
		materialInfo.setMaterialBatch(materpurchase.getPurchaseBatch());
		materialInfo.setSupplierId(materpurchase.getSupplierId());
		if(materialId==0){			
	        materialInfo.setCreateTime(createTime);
	        materialInfo.setCreateStaff(materpurchase.getCreateStaff());
	        this.insertMaterialInfo(materialInfo);
		}else{
			materialInfo.setMaterialId(materialId);
			materialInfo.setUpdateTime(createTime);
			materialInfo.setUpdateStaff(materpurchase.getCreateStaff());
			this.updateMaterialInfo(materialInfo);
		}
	}
	
	@Override
	public void updatePurchaseInfo(MaterialPurchaseInfo purchaseInfo) throws BusinessException{
		Timestamp updatetime = DateUtil.getCurrent();
		purchaseInfo.setUpdateTime(updatetime);
		TOriginPurchaseInfo materpurchase = new TOriginPurchaseInfo();
		BeanUtils.copyProperties(purchaseInfo, materpurchase);		
		if(purchaseInfo.getSupplierId()==null){
			   boolean agencyexist = this.getAgencyInfo(purchaseInfo);
			   if(!agencyexist){
				   TOriginAgencyCompany company = new TOriginAgencyCompany();
				   company.setAgencyName(purchaseInfo.getSupplierName());
				   company.setAgencyResponsiblePerson(purchaseInfo.getManagePerson());
				   company.setLinkPhone(purchaseInfo.getLinkPhone());
				   company.setCreateEnterpriseId(purchaseInfo.getEnterpriseId());
				   int agencyId = agencyCompanyService.insertAgency(company);
				   materpurchase.setSupplierId(agencyId);
				   purchaseInfo.setSupplierId(agencyId);
			   }
		}
		originPurchaseInfoDAO.updateByPrimaryKeySelective(materpurchase);
				
		Integer materialId = this.getMaterial(purchaseInfo);
		TOriginMaterialInfo materialInfo = new TOriginMaterialInfo();
		materialInfo.setMaterialName(purchaseInfo.getMaterialName());
		materialInfo.setMaterialType(purchaseInfo.getMaterialType());
		materialInfo.setMaterialBatch(purchaseInfo.getPurchaseBatch());
		materialInfo.setSupplierId(purchaseInfo.getSupplierId());
		if(materialId==0){
	        materialInfo.setCreateTime(updatetime);
	        materialInfo.setCreateStaff(purchaseInfo.getCreateStaff());
	        this.insertMaterialInfo(materialInfo);
		}else{
			materialInfo.setMaterialId(materialId);
			materialInfo.setUpdateTime(updatetime);
			materialInfo.setUpdateStaff(purchaseInfo.getCreateStaff());
			this.updateMaterialInfo(materialInfo);
		}
	}

	public Integer getMaterial(TOriginPurchaseInfo purchaseInfo) throws BusinessException{
		Integer materialId = 0;
		TOriginMaterialInfoExample example = new TOriginMaterialInfoExample();
		com.bdx.rainbow.spsy.dal.ibatis.model.TOriginMaterialInfoExample.Criteria criteria =  example.createCriteria();
		criteria.andMaterialNameEqualTo(purchaseInfo.getMaterialName());
		criteria.andMaterialBatchEqualTo(purchaseInfo.getPurchaseBatch());
		criteria.andSupplierIdEqualTo(purchaseInfo.getSupplierId());
		List<TOriginMaterialInfo> materials = originMaterialInfoDAO.selectByExample(example);
		if(materials!=null && materials.size()>0){
			TOriginMaterialInfo materialinfo = materials.get(0);
			materialId = materialinfo.getMaterialId(); 
		}
		return materialId;
	}
	
	public boolean getAgencyInfo(MaterialPurchaseInfo purchaseInfo) throws BusinessException{
		boolean exist = false;
		TOriginAgencyCompany company = new TOriginAgencyCompany();
		company.setAgencyName(purchaseInfo.getSupplierName());
		company.setLinkPhone(purchaseInfo.getLinkPhone());
		List<TOriginAgencyCompany> companys = agencyCompanyService.getAgencyList(company,0,10);
		if(companys!=null && companys.size()>0){
			exist = false;
		}
		return  exist;
	}
	
	@Override
	public List<MaterialPurchaseInfo> getPurchaseInfoList(TOriginPurchaseInfo purchaseInfo, int start, int size) throws BusinessException {
		TOriginPurchaseInfoExample example = convertExample(purchaseInfo);
		if(start >= 0){
			example.setLimitClauseCount(size);
			example.setLimitClauseStart(start);
		}
		List<TOriginPurchaseInfo> purchaseInfos = originPurchaseInfoDAO.selectByExample(example);
		List<MaterialPurchaseInfo> infos = new ArrayList<MaterialPurchaseInfo>();
		if(purchaseInfos!=null && purchaseInfos.size()>0){
			for(int i=0;i<purchaseInfos.size();i++){
				TOriginPurchaseInfo info = purchaseInfos.get(i);
				MaterialPurchaseInfo materinfo = this.getAgency(info.getSupplierId(), info);
				infos.add(materinfo);
			}
		}
		return infos;
	}
	
	@Override
	public MaterialPurchaseInfo getPurchaseInfoBykey(Integer purchaseId) throws BusinessException {
		TOriginPurchaseInfo info = originPurchaseInfoDAO.selectByPrimaryKey(purchaseId);
		MaterialPurchaseInfo materinfo = this.getAgency(info.getSupplierId(), info);
		return materinfo;	
	}
	
	public MaterialPurchaseInfo getAgency(Integer agencyId,TOriginPurchaseInfo purchaseInfo) throws BusinessException {
		TOriginAgencyCompany agency = agencyCompanyService.getAgency(agencyId);
		MaterialPurchaseInfo materInfo = new MaterialPurchaseInfo();
		BeanUtils.copyProperties(purchaseInfo, materInfo);
		if(agency!=null){
			materInfo.setSupplierId(agency.getAgencyId());
			materInfo.setSupplierName(agency.getAgencyName());
			materInfo.setManagePerson(agency.getAgencyResponsiblePerson());
			materInfo.setLinkPhone(agency.getLinkPhone());
		}
		return materInfo;
	}
	
	@Override
	public Integer countPurchaseInfo(TOriginPurchaseInfo purchaseInfo) throws BusinessException {
		TOriginPurchaseInfoExample example = convertExample(purchaseInfo);
		return originPurchaseInfoDAO.countByExample(example);
	}
	
    private TOriginPurchaseInfoExample convertExample(TOriginPurchaseInfo purchaseInfo) {		
    	TOriginPurchaseInfoExample example = new TOriginPurchaseInfoExample();
		Criteria criteria= example.createCriteria();		
		if(purchaseInfo == null ){
			return example;
		}
		if(purchaseInfo.getEnterpriseId()!=null){
		   criteria.andEnterpriseIdEqualTo(purchaseInfo.getEnterpriseId());
		}
		
		if(StringUtils.isNotBlank(purchaseInfo.getMaterialName())){
			criteria.andMaterialNameLike("%" + purchaseInfo.getMaterialName() + "%");
		}
		
		if(StringUtils.isNotBlank(purchaseInfo.getPurchaseBatch())){
			criteria.andPurchaseBatchEqualTo(purchaseInfo.getPurchaseBatch());
		}		
		return example;
	}

	@Override
	public List<MaterialPurchaseInfo> getPurchaseInfos(MaterialPurchaseInfo purchaseInfo) throws BusinessException {
		TOriginPurchaseInfoExample example = convertSelectExample(purchaseInfo);
		example.setLimitClauseCount(20);
		example.setLimitClauseStart(0);		
		List<TOriginPurchaseInfo> purchaseInfos = originPurchaseInfoDAO.selectByExample(example);
		List<MaterialPurchaseInfo> infos = new ArrayList<MaterialPurchaseInfo>();
		if(purchaseInfos!=null && purchaseInfos.size()>0){
			for(int i=0;i<purchaseInfos.size();i++){
				TOriginPurchaseInfo info = purchaseInfos.get(i);
				MaterialPurchaseInfo materinfo = this.getAgency(info.getSupplierId(), info);
				infos.add(materinfo);
			}
		}
		return infos;
	}
	
	private TOriginPurchaseInfoExample convertSelectExample(MaterialPurchaseInfo condition) {		
    	TOriginPurchaseInfoExample example = new TOriginPurchaseInfoExample();
		Criteria criteria= example.createCriteria();		
		if(condition == null ){
			return example;
		}
		if(condition.getEnterpriseId()!=null){
		   criteria.andEnterpriseIdEqualTo(condition.getEnterpriseId());
		}
		if(condition.getStartTime()!=null){
		   criteria.andPurchaseTimeGreaterThanOrEqualTo(condition.getStartTime());
		}
		if(condition.getEndTime()!=null){
		   criteria.andPurchaseTimeLessThanOrEqualTo(condition.getEndTime());
		}
		return example;
	}

}
