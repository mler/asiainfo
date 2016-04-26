package com.bdx.rainbow.mapper.basic.mysql;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.entity.basic.mysql.SelfBasicLaw;
import com.bdx.rainbow.entity.basic.mysql.TBasicLawDbExample;

public interface SelfLawMapper {
	public List<SelfBasicLaw> getLawList(Map<String,Object> param);
	
	public int countLaw(Map<String, Object> param);
	
	public List<SelfBasicLaw> getNodeList(Map<String, Object> param);
	
	public String getTitles(Map<String, Object> param);

}
