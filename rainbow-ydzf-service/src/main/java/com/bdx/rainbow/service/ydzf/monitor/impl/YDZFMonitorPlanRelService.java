package com.bdx.rainbow.service.ydzf.monitor.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlanRel;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlanRelExample;
import com.bdx.rainbow.mapper.ydzf.TYdzfMonitorPlanMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfMonitorPlanRelMapper;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorPlanRelService;
import com.bdx.rainbow.urs.entity.IUserInfo;

@Service("ydzfMonitorPlanRelService")
public class YDZFMonitorPlanRelService extends BaseService implements IYDZFMonitorPlanRelService  {
	private static final Logger log = LoggerFactory
			.getLogger(YDZFMonitorPlanRelService.class);
	
	@Autowired
	protected TYdzfMonitorPlanMapper ydzfMonitorPlanMapper;
	@Autowired
	protected TYdzfMonitorPlanRelMapper ydzfMonitorPlanRelMapper;
	@Override
	public List<TYdzfMonitorPlanRel> queryMonitorPlanRelListAll(
			TYdzfMonitorPlanRel planRel) throws BusinessException,
			SystemException {
		TYdzfMonitorPlanRelExample example=getCondition(planRel);
		return ydzfMonitorPlanRelMapper.selectByExample(example);
	}
	
	
	private TYdzfMonitorPlanRelExample getCondition(TYdzfMonitorPlanRel record) {
		TYdzfMonitorPlanRelExample example=new TYdzfMonitorPlanRelExample();
		if (record != null) {
			com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlanRelExample.Criteria cr=example.createCriteria();
			if(record.getMonitorPlanId() != null && record.getMonitorPlanId()>0) {
				cr.andMonitorPlanIdEqualTo(record.getMonitorPlanId());
			}
			if(record.getMonitorPlanRelId() != null && record.getMonitorPlanRelId()>0) {
				cr.andMonitorPlanRelIdEqualTo(record.getMonitorPlanRelId());
			}
			
		}
		return example;
	}


	@Override
	@Transactional
	public int delMonitorPlanRelByPlanId(IUserInfo userInfo,
			Integer monitorPlanId) throws BusinessException, SystemException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
