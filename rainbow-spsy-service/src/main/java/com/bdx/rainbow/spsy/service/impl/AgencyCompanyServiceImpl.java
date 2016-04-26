package com.bdx.rainbow.spsy.service.impl;

import java.util.List;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.spsy.common.DateUtil;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginAgencyCompanyDAO;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginAgencyCompany;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginAgencyCompanyExample;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginAgencyCompanyExample.Criteria;
import com.bdx.rainbow.spsy.service.IAgencyCompanyService;

public class AgencyCompanyServiceImpl implements IAgencyCompanyService {
    @Autowired
    private TOriginAgencyCompanyDAO originAgencyCompanyDAO;
	
	@Override
	public List<TOriginAgencyCompany> getAgencyList(TOriginAgencyCompany agencyInfo, int start, int limit) throws BusinessException {
		TOriginAgencyCompanyExample example = convertExample(agencyInfo);
		if(start >= 0){
			example.setLimitClauseCount(limit);
			example.setLimitClauseStart(start);
		}
		return originAgencyCompanyDAO.selectByExample(example);
	}

	@Override
	public Integer countAgency(TOriginAgencyCompany agencyInfo) throws BusinessException {
		TOriginAgencyCompanyExample example = convertExample(agencyInfo);
		return originAgencyCompanyDAO.countByExample(example);
	}
	
	@Override
	public TOriginAgencyCompany getAgency(Integer agencyId) throws BusinessException {		
		return originAgencyCompanyDAO.selectByPrimaryKey(agencyId);
	}
	
	@Override
	public Integer insertAgency(TOriginAgencyCompany agencyInfo) throws BusinessException {
		agencyInfo.setCreateTime(DateUtil.getCurrent());
		Integer agencyId = originAgencyCompanyDAO.insertSelective(agencyInfo);
		return agencyId;
	}

	@Override
	public void updateAgency(TOriginAgencyCompany agencyInfo) throws BusinessException {
		agencyInfo.setUpdateTime(DateUtil.getCurrent());
		originAgencyCompanyDAO.updateByPrimaryKeySelective(agencyInfo);
	}
	
	private TOriginAgencyCompanyExample convertExample(TOriginAgencyCompany agencyInfo) throws BusinessException{
		TOriginAgencyCompanyExample example = new TOriginAgencyCompanyExample();
		Criteria criteria = example.createCriteria();
		if(StringUtil.isNotBlank(agencyInfo.getAgencyName())){
			criteria.andAgencyNameLike("%"+agencyInfo.getAgencyName()+"%");
		}
		if(agencyInfo.getEnterpriseId()!=null){
			criteria.andEnterpriseIdEqualTo(agencyInfo.getEnterpriseId());
		}
		if(agencyInfo.getCreateEnterpriseId()!=null){
			criteria.andCreateEnterpriseIdEqualTo(agencyInfo.getCreateEnterpriseId());
		}
		return example;
	}

	@Override
	public Integer getAgencyInfo(String agencyName, String linkPhone) throws BusinessException {
		int agencyId = 0;
		TOriginAgencyCompanyExample example = new TOriginAgencyCompanyExample();
		example.createCriteria().andAgencyNameEqualTo(agencyName).andLinkPhoneEqualTo(linkPhone);
		List<TOriginAgencyCompany> companys = originAgencyCompanyDAO.selectByExample(example);
		if(companys!=null && companys.size()>0){
			agencyId = companys.get(0).getAgencyId();
		}
		return  agencyId;
	}

	@Override
	public TOriginAgencyCompany getAgencyFromEnterprise(Integer enterpriseId) throws BusinessException {
		TOriginAgencyCompany agency = null;
		TOriginAgencyCompanyExample example = new TOriginAgencyCompanyExample();
		example.createCriteria().andEnterpriseIdEqualTo(enterpriseId);
		List<TOriginAgencyCompany> companys = originAgencyCompanyDAO.selectByExample(example);
		if(companys!=null && companys.size()>0){
			agency = companys.get(0);
		}
		return agency;
	}
}
