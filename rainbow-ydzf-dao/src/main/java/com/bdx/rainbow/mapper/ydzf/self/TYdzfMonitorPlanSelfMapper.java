package com.bdx.rainbow.mapper.ydzf.self;

import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanListInfoResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanListNumResultVO;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanTaskInfoResultVO;

import java.util.List;
import java.util.Map;

public interface TYdzfMonitorPlanSelfMapper {
	  List<YdzfMonitorPlanListNumResultVO> queryPlanListNum(Map<String,Object> paramMap);
	  YdzfMonitorPlanListInfoResultVO queryMonitorPlanListInfo(Map<String,Object> paramMap);
	  YdzfMonitorPlanTaskInfoResultVO queryMonitorPlanTaskInfo(Map<String,Object> paramMap);
}