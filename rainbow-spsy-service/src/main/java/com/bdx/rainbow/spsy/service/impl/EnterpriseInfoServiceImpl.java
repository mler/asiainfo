package com.bdx.rainbow.spsy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.bean.DubboLicense;
import com.bdx.rainbow.basic.dubbo.service.IEnterpriseDubboService;
import com.bdx.rainbow.spsy.bean.EnterpriseInfo;
import com.bdx.rainbow.spsy.common.SpsyConstants;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginAgencyCompanyDAO;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginAgencyCompany;
import com.bdx.rainbow.spsy.service.IAgencyCompanyService;
import com.bdx.rainbow.spsy.service.IEnterpriseInfoService;
import com.bdx.rainbow.urs.dubbo.IDubUserService;
import com.bdx.rainbow.urs.entity.SysUser;

public class EnterpriseInfoServiceImpl implements IEnterpriseInfoService {

	@Autowired
    private IEnterpriseDubboService enterpriseDubboService;
		
	@Autowired
	private IAgencyCompanyService agencyCompanyService;
	
	@Autowired
	private IDubUserService userService;
	
	@SuppressWarnings("unchecked")
	@Override
	public EnterpriseInfo getEnterpriseInfo(Integer EnterpriseId) throws Exception {
		Map<String,Object> map = enterpriseDubboService.getEnterpriseDetailById(EnterpriseId);
		DubboEnterpriseInfo enterpriseInfo = (DubboEnterpriseInfo) map.get("info");
		List<DubboLicense> licenses = (List<DubboLicense>) map.get("licenses");
		EnterpriseInfo enterprise = new EnterpriseInfo();
		BeanUtils.copyProperties(enterpriseInfo, enterprise);
		if(licenses!=null && licenses.size()>0){
			for(int i=0;i<licenses.size();i++){
				DubboLicense license = licenses.get(i);
				if(license.getLicenseType().equals(SpsyConstants.LINCESE_TYPE_PRODUCTION))
					enterprise.setProductionLicense(license.getLicenseCode());
				else if(license.getLicenseType().equals(SpsyConstants.LINCESE_TYPE_CIRCULATION))
					enterprise.setCirculationLicense(license.getLicenseCode());
				else if(license.getLicenseType().equals(SpsyConstants.LINCESE_TYPE_MANAGE))
					enterprise.setManageLincense(license.getLicenseCode());
				else if(license.getLicenseType().equals(SpsyConstants.LINCESE_TYPE_SALE))
					enterprise.setSaleLicense(license.getLicenseCode());
			}
		}
		return enterprise;
	}

	@Override
	public void updateEnterpriseInfo(EnterpriseInfo enterpriseInfo,String userLoginName) throws Exception {
		List<DubboLicense> licenses = new ArrayList<DubboLicense>();
		DubboLicense license = null;
		if(enterpriseInfo.getProductionLicense()!=null && !enterpriseInfo.getProductionLicense().equals("")){
			license = new DubboLicense();
			license.setLicenseType(SpsyConstants.LINCESE_TYPE_PRODUCTION);
			license.setLicenseCode(enterpriseInfo.getProductionLicense());
			licenses.add(license);
		}
		if(enterpriseInfo.getCirculationLicense()!=null && !enterpriseInfo.getCirculationLicense().equals("")){
			license = new DubboLicense();
			license.setLicenseType(SpsyConstants.LINCESE_TYPE_CIRCULATION);
			license.setLicenseCode(enterpriseInfo.getCirculationLicense());
			licenses.add(license);
		}
		if(enterpriseInfo.getManageLincense()!=null && !enterpriseInfo.getManageLincense().equals("")){
			license = new DubboLicense();
			license.setLicenseType(SpsyConstants.LINCESE_TYPE_MANAGE);
			license.setLicenseCode(enterpriseInfo.getManageLincense());
			licenses.add(license);
		}
		if(enterpriseInfo.getSaleLicense()!=null && !enterpriseInfo.getSaleLicense().equals("")){
			license = new DubboLicense();
			license.setLicenseType(SpsyConstants.LINCESE_TYPE_SALE);
			license.setLicenseCode(enterpriseInfo.getSaleLicense());
			licenses.add(license);
		}
		DubboEnterpriseInfo dubboEnterpriseInfo = new DubboEnterpriseInfo();
		BeanUtils.copyProperties(enterpriseInfo, dubboEnterpriseInfo);
		int enterpriseId = enterpriseDubboService.saveEnterpriseInfo(dubboEnterpriseInfo, userLoginName,licenses);
		TOriginAgencyCompany agency = new TOriginAgencyCompany();
		if(enterpriseInfo.getEnterpriseId()==null){
			agency.setAgencyName(enterpriseInfo.getEnterpriseName());
			agency.setAgencyResponsiblePerson(enterpriseInfo.getLegalPerson());
			agency.setAgencyAddress(enterpriseInfo.getRegiAddress());
			agency.setFoodDistributionLicense(enterpriseInfo.getCirculationLicense());
			agency.setLinkPhone(enterpriseInfo.getLegalPersonPhone());
			agency.setEnterpriseId(enterpriseId);
			//判断这个商户是否存在			
			int oldagencyId = agencyCompanyService.getAgencyInfo(enterpriseInfo.getEnterpriseName(), enterpriseInfo.getLegalPersonPhone());
			if(oldagencyId==0){
				agencyCompanyService.insertAgency(agency);
			}else{
				agency.setAgencyId(oldagencyId);
				agencyCompanyService.updateAgency(agency);
			}			
		
			if(userLoginName==null){
				//新建用户
				SysUser user = new SysUser();
				user.setUserLoginName(enterpriseInfo.getLegalPersonPhone());
				user.setUserName(enterpriseInfo.getEnterpriseName());
				user.setMobilePhone(enterpriseInfo.getLegalPersonPhone());
				user.setCorpId(enterpriseId);
				user.setUserStatus(SpsyConstants.STATUS_ACTIVE);
				user.setUserType(SpsyConstants.USER_TYPE_ENTERPRISE);
				userService.insertUser(user, SpsyConstants.PLAT_ID_SOURCE, enterpriseInfo.getBusinessType());
			}
		}
	}
}
