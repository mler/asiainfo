package com.bdx.rainbow.mapper.ydzf.self;

import java.util.List;
import java.util.Map;

public interface TYdzfBackLogSelfMapper {
	  int queryBackLogListNum(Map<String,Object> paramMap);
	  //id,relId,type
	  List<Map<String,Object>> queryBackLogListInfo(Map<String,Object> paramMap);
}