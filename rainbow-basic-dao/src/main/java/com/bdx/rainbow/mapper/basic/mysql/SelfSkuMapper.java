package com.bdx.rainbow.mapper.basic.mysql;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.entity.basic.mysql.TBasicSku;

public interface SelfSkuMapper {
	
	public List<TBasicSku> getSkus(Map params);
	
	public int countSku(Map params);
}
