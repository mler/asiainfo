package com.bdx.rainbow.service.basic.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseLicense;
import com.bdx.rainbow.basic.dubbo.bean.DubboLicense;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.entity.basic.mysql.SelfEnterpriseLicense;
import com.bdx.rainbow.entity.basic.mysql.TBasicEnterpriseInfo;
import com.bdx.rainbow.entity.basic.mysql.TBasicEnterpriseInfoExample;
import com.bdx.rainbow.entity.basic.mysql.TBasicEnterpriseInfoHis;
import com.bdx.rainbow.entity.basic.mysql.TBasicLicense;
import com.bdx.rainbow.entity.basic.mysql.TBasicLicenseExample;
import com.bdx.rainbow.mapper.basic.mysql.SelfEnterpriseMapper;
import com.bdx.rainbow.mapper.basic.mysql.TBasicEnterpriseInfoHisMapper;
import com.bdx.rainbow.mapper.basic.mysql.TBasicEnterpriseInfoMapper;
import com.bdx.rainbow.mapper.basic.mysql.TBasicLicenseMapper;
import com.bdx.rainbow.service.basic.IEnterpriseService;

@Service("enterpriseService")
@Transactional(rollbackFor=Exception.class,value="transactionManager")
public class EnterpriseService implements IEnterpriseService{
	
	@Autowired
	private TBasicEnterpriseInfoMapper tBasicEnterpriseInfoMapper;
	
	@Autowired
	private TBasicEnterpriseInfoHisMapper tBasicEnterpriseInfoHisMapper;
	
	@Autowired
	private TBasicLicenseMapper tBasicLicenseMapper;
	
	@Autowired
	private SelfEnterpriseMapper selfEnterpriseMapper;

	@Override
	public Map<String, Object> getEnterpriseInfos(
			DubboEnterpriseInfo condition, Integer start, Integer limit)
			throws Exception {
		TBasicEnterpriseInfoExample where = convertCondition(condition);
		if (start >= 0 && limit > 0) {
			where.setLimitClauseStart(start);
			where.setLimitClauseCount(limit);
		}
		where.setOrderByClause("CREATE_TIME desc");
		List<TBasicEnterpriseInfo> list = tBasicEnterpriseInfoMapper.selectByExample(where);
		List<DubboEnterpriseInfo> dubboList = null;
		DubboEnterpriseInfo dubboEnterpriseInfo = null;
		int total = tBasicEnterpriseInfoMapper.countByExample(where);
		if (list != null && list.size() != 0) {
			dubboList = new ArrayList<DubboEnterpriseInfo>(list.size());
			for (TBasicEnterpriseInfo enterpriseInfo : list) {
				dubboEnterpriseInfo = new DubboEnterpriseInfo();
				PropertyUtils.copyProperties(dubboEnterpriseInfo, enterpriseInfo);
				dubboList.add(dubboEnterpriseInfo);
			}
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list", dubboList);
		resultMap.put("total", total);
		return resultMap;
	}

	@Override
	public Integer saveEnterpriseInfo(DubboEnterpriseInfo dubboEnterpriseInfo,String userLoginName,List<DubboLicense> licenses)
			throws Exception {
		TBasicEnterpriseInfo enterpriseInfo = new TBasicEnterpriseInfo();
		PropertyUtils.copyProperties(enterpriseInfo, dubboEnterpriseInfo);
		TBasicEnterpriseInfo enterpriseInfoOld = null;
		int enterpriseId = -1;
		if (enterpriseInfo != null) {
			if (enterpriseInfo.getEnterpriseId() == null) {//判断 insert or update
				if (StringUtils.isNotEmpty(enterpriseInfo.getOrganizationCode())) {
					enterpriseInfoOld = getEnterpriseInfoByOrganizationCode(enterpriseInfo.getOrganizationCode());
					if (enterpriseInfoOld != null) {
						enterpriseInfo.setEnterpriseId(enterpriseInfoOld.getEnterpriseId());
					}
				}
			}else {
				enterpriseInfoOld = tBasicEnterpriseInfoMapper.selectByPrimaryKey(enterpriseInfo.getEnterpriseId());
			}
			
			Timestamp now = DateUtil.getCurrent();
			
			if (enterpriseInfoOld == null){//insert
				enterpriseInfo.setCreateTime(now);
				enterpriseInfo.setCreater(userLoginName);
				tBasicEnterpriseInfoMapper.insertSelective(enterpriseInfo);
				enterpriseId = enterpriseInfo.getEnterpriseId();
			}else {//update
				enterpriseId = enterpriseInfoOld.getEnterpriseId();
				enterpriseInfo.setUpdater(userLoginName);
				enterpriseInfo.setUpdateTime(now);
				TBasicEnterpriseInfoHis his = new TBasicEnterpriseInfoHis();
				PropertyUtils.copyProperties(his, enterpriseInfoOld);
				tBasicEnterpriseInfoHisMapper.insertSelective(his);
				tBasicEnterpriseInfoMapper.updateByPrimaryKeySelective(enterpriseInfo);
			}
			//保存证书
			if (licenses != null && licenses.size() != 0) {
				List<TBasicLicense> bLicenses = new ArrayList<TBasicLicense>();
				TBasicLicense destLicense = null;
				for (DubboLicense dubboLicense:licenses) {
					destLicense = new TBasicLicense();
					PropertyUtils.copyProperties(destLicense, dubboLicense);
					destLicense.setEnterpriseId(enterpriseId);
					bLicenses.add(destLicense);
				}
				saveLicense(bLicenses, userLoginName);
			}
		}
		return enterpriseId;
	}

	private TBasicEnterpriseInfoExample convertCondition(DubboEnterpriseInfo condition){
		TBasicEnterpriseInfoExample example = new TBasicEnterpriseInfoExample();
		TBasicEnterpriseInfoExample.Criteria criteria = example.createCriteria();
		
		if (condition != null) {
			if (condition.getEnterpriseId() != null) {
				criteria.andEnterpriseIdEqualTo(condition.getEnterpriseId());
			}
			if (StringUtils.isNotEmpty(condition.getEnterpriseName())) {
				criteria.andEnterpriseNameLike("%" + condition.getEnterpriseName() + "%");
			}
			
			if (StringUtils.isNotEmpty(condition.getLegalPerson())) {
				criteria.andLegalPersonLike("%" + condition.getLegalPerson() + "%");
			}
			
			if (StringUtils.isNotEmpty(condition.getOrganizationCode())) {
				criteria.andOrganizationCodeEqualTo(condition.getOrganizationCode());
			}
			
			if (StringUtils.isNotEmpty(condition.getLegalPersonPhone())) {
				criteria.andLegalPersonPhoneEqualTo(condition.getLegalPersonPhone());
			}
			
			if (StringUtils.isNotEmpty(condition.getBusinessType())) {
				criteria.andBusinessTypeEqualTo(condition.getBusinessType());
			}
		}
		
		return example;
	}

	@Override
	public TBasicEnterpriseInfo getEnterpriseInfoByOrganizationCode(
			String organizationCode) throws Exception {
		TBasicEnterpriseInfoExample example = new TBasicEnterpriseInfoExample();
		TBasicEnterpriseInfoExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause("CREATE_TIME desc");
		criteria.andOrganizationCodeEqualTo(organizationCode);
		List<TBasicEnterpriseInfo> enterpriseInfos = tBasicEnterpriseInfoMapper.selectByExample(example);
		if (enterpriseInfos == null || enterpriseInfos.size() == 0) {
			return null;
		}else {
			return enterpriseInfos.get(0);
		}
	}

	@Override
	public TBasicEnterpriseInfo getEnterpriseInfoById(Integer enterpriseId)
			throws Exception {
		return tBasicEnterpriseInfoMapper.selectByPrimaryKey(enterpriseId);
	}

	@Override
	public void saveLicense(List<TBasicLicense> licenses,String userLoginName) throws Exception {
		if (licenses != null && licenses.size() != 0) {
			Timestamp now = DateUtil.getCurrent();
			for (TBasicLicense license:licenses) {
				if (license != null) {
					if (license.getLicenseId() != null) {//update
//						license.set
						tBasicLicenseMapper.updateByPrimaryKeySelective(license);
					}else {//insert
						if (license.getEnterpriseId() == null) {
//							throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.UNKNOW_ERROR.getCode(), "请补充相应企业ID"));
							throw new Exception("请补充相应企业ID");
						}
						
						if (StringUtils.isEmpty(license.getLicenseCode())) {
//							throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.UNKNOW_ERROR.getCode(), "缺少证书编号"));
							throw new Exception("缺少证书编号");
						}
						//判断证书号是否有重复
						TBasicLicense oldlLicense = getLicenseByCode(null, license.getLicenseCode());
						if (oldlLicense != null) {
//							throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.UNKNOW_ERROR.getCode(), "该证书编号:" + license.getLicenseCode() + " 已存在"));
							throw new Exception("该证书编号:" + license.getLicenseCode() + " 已存在");
						}
						license.setCreateTime(now);
						license.setCreater(userLoginName);
						tBasicLicenseMapper.insertSelective(license);
					}
				}
			}
//			tBasicLicenseMapper.insertBatch(licenses);
		}
		
		
		
	}

	@Override
	public TBasicLicense getLicenseByCode(Integer enterpriseId,
			String licenseCode) throws Exception {
		TBasicLicenseExample example = new TBasicLicenseExample();
		TBasicLicenseExample.Criteria criteria =example.createCriteria();
		if (enterpriseId != null) {
			criteria.andEnterpriseIdEqualTo(enterpriseId);
		}
		if (StringUtils.isNotEmpty(licenseCode)) {
			
			criteria.andLicenseCodeEqualTo(licenseCode);
		}
		List<TBasicLicense> ls = tBasicLicenseMapper.selectByExample(example);
		if (ls != null && ls.size() != 0) {
			return ls.get(0);
		}
		return null;
	}

	@Override
	public List<TBasicLicense> getEnterpriseLicenses(Integer enterpriseId)
			throws Exception {
		TBasicLicenseExample example = new TBasicLicenseExample();
		TBasicLicenseExample.Criteria criteria =example.createCriteria();
		criteria.andEnterpriseIdEqualTo(enterpriseId);
		List<TBasicLicense> ls = tBasicLicenseMapper.selectByExample(example);
		return ls;
	}

	@Override
	public Map<String, Object> getEnterpriseDetailById(Integer enterpriseId)
			throws Exception {
		TBasicEnterpriseInfo info = tBasicEnterpriseInfoMapper.selectByPrimaryKey(enterpriseId);
		if (info != null) {
			Map<String, Object> result = new HashMap<String, Object>();
			DubboEnterpriseInfo dInfo = new DubboEnterpriseInfo();
			PropertyUtils.copyProperties(dInfo, info);
			result.put("info", dInfo);
			List<TBasicLicense> licenses = getEnterpriseLicenses(enterpriseId);
			if (licenses != null && licenses.size() != 0) {
				List<DubboLicense> dLicenses = new ArrayList<DubboLicense>();
				DubboLicense dLicense = null;
				for(TBasicLicense license:licenses){
					dLicense = new DubboLicense();
					PropertyUtils.copyProperties(dLicense, license);
					dLicenses.add(dLicense);
				}
				result.put("licenses", dLicenses);
			}
			return result;
		}
		return null;
	}

	@Override
	public List<TBasicEnterpriseInfo> getEnterpriseInfoByIds(
			List<Integer> enterpriseIds) throws Exception {
		TBasicEnterpriseInfoExample example = new TBasicEnterpriseInfoExample();
		example.createCriteria().andEnterpriseIdIn(enterpriseIds);
		example.setOrderByClause("CREATE_TIME desc");
		List<TBasicEnterpriseInfo> list = tBasicEnterpriseInfoMapper.selectByExample(example);
		
		return list;
	}

	@Override
	public List<DubboEnterpriseInfo> getDubboEnterpriseInfoByIds(
			List<Integer> enterpriseIds) throws Exception {
		List<TBasicEnterpriseInfo> list = getEnterpriseInfoByIds(enterpriseIds);
		List<DubboEnterpriseInfo> dubboList = null;
		if (list != null && list.size() != 0) {
			DubboEnterpriseInfo dubboEnterpriseInfo = null;
			if (list != null && list.size() != 0) {
				dubboList = new ArrayList<DubboEnterpriseInfo>(list.size());
				for (TBasicEnterpriseInfo enterpriseInfo : list) {
					dubboEnterpriseInfo = new DubboEnterpriseInfo();
					PropertyUtils.copyProperties(dubboEnterpriseInfo, enterpriseInfo);
					dubboList.add(dubboEnterpriseInfo);
				}
			}
		}
		return dubboList;
	}

	@Override
	public Map<String, Object> getMoreEnterpriseLicense(
			DubboEnterpriseLicense condition, Integer start, Integer limit)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (condition != null) {
			if (StringUtils.isNotEmpty(condition.getOrganizationCode())) {
				paramMap.put("organizationCode",condition.getOrganizationCode());
			}
			if (StringUtils.isNotEmpty(condition.getLicenseCode())) {
				paramMap.put("licenseCode","%" + condition.getLicenseCode() + "%");
			}
			if (StringUtils.isNotEmpty(condition.getStatus())) {
				paramMap.put("status",condition.getStatus());
			}
			if (StringUtils.isNotEmpty(condition.getEnterpriseName())) {
				paramMap.put("enterpriseName","%" + condition.getEnterpriseName() + "%");
			}
			if (StringUtils.isNotEmpty(condition.getLicenseCode())) {
				paramMap.put("licenseCode","%" + condition.getLicenseCode() + "%");
			}
			
			if (condition.getValidDate() != null) {
				paramMap.put("validDate",condition.getValidDate());
			}
			if (condition.getInvalidDate() != null) {
				paramMap.put("invalidDate",condition.getInvalidDate());
			}
			
			if (condition.getInvalidDateStart() != null) {
				paramMap.put("invalidDateStart",condition.getInvalidDateStart());
			}
			if (condition.getInvalidDateEnd() != null) {
				paramMap.put("invalidDateEnd",condition.getInvalidDateEnd());
			}
			
			if (condition.getValidDateStart() != null) {
				paramMap.put("validDateStart",condition.getValidDateStart());
			}
			if (condition.getValidDateEnd() != null) {
				paramMap.put("validDateEnd",condition.getValidDateEnd());
			}
			
		}
		
		if (start >= 0 && limit > 0) {
			paramMap.put("limitClauseStart", start);
			paramMap.put("limitClauseCount", limit);
		}
		
		
		int total = selfEnterpriseMapper.countEnterpriseLicenses(paramMap);
		List<DubboEnterpriseLicense> list = null;
		if (total != 0) {
			List<SelfEnterpriseLicense> listSrc = selfEnterpriseMapper.getEnterpriseLicenses(paramMap);
			list = new ArrayList<DubboEnterpriseLicense>();
			DubboEnterpriseLicense enterpriseLicense = null;
			for (SelfEnterpriseLicense tmp:listSrc) {
				enterpriseLicense = new DubboEnterpriseLicense();
				PropertyUtils.copyProperties(enterpriseLicense, tmp);
				list.add(enterpriseLicense);
			}
		}
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
		result.put("total",total);
		return result;
	}
}
