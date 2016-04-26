package com.bdx.rainbow.mapper.ydzf.self;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.dto.ydzf.form.YdzfEnterpriseHistoryQueryInfo;
import com.bdx.rainbow.dto.ydzf.vo.YdzfEnterpriseHistoryListInfoVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfPunishAdviceFinishHistoryVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfPunishAdviceHistoryVO;

public interface TYdzfHistorySelfMapper {
	  List<Map<String,Object>> queryInspectEnterpriseHistoryCount(Map<String,Object> paramMap);
	  List<Map<String,Object>> queryMonitorEnterpriseHistoryCount(Map<String,Object> paramMap);
	  int queryEnterpriseHistoryListInfoCount(YdzfEnterpriseHistoryQueryInfo ydzfEnterpriseHistoryQueryInfo);
	  List<YdzfEnterpriseHistoryListInfoVO> queryEnterpriseHistoryListInfo(YdzfEnterpriseHistoryQueryInfo ydzfEnterpriseHistoryQueryInfo);
	  int queryPunishAdviceHistoryListInfoCount(Map<String,Object> paramMap);
	  List<YdzfPunishAdviceHistoryVO> queryPunishAdviceHistoryListInfo(Map<String,Object> paramMap);
	  List<YdzfPunishAdviceFinishHistoryVO> queryPunishAdviceFinishHistoryListInfo(Map<String,Object> paramMap)	;
		
	  
}