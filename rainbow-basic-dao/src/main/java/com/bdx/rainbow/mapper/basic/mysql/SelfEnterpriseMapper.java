package com.bdx.rainbow.mapper.basic.mysql;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.entity.basic.mysql.SelfEnterpriseLicense;

public interface SelfEnterpriseMapper {

	public List<SelfEnterpriseLicense> getEnterpriseLicenses(Map params);
	
	public int countEnterpriseLicenses(Map params);
}
