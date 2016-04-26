package com.bdx.rainbow.service.ydzf.templet.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTemplet;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTempletExample;
import com.bdx.rainbow.mapper.ydzf.TYdzfMonitorTempletMapper;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.templet.IYDZFMonitorTempletService;
import com.bdx.rainbow.urs.entity.IUserInfo;


@Service("ydzfMonitorTempletService")
public class YDZFMonitorTempletService extends BaseService implements IYDZFMonitorTempletService {
	private static final Logger log = LoggerFactory
			.getLogger(YDZFMonitorTempletService.class);
	@Autowired
	protected TYdzfMonitorTempletMapper ydzfMonitorTempletMapper;

	@Override
	@Transactional
	public int insertMonitorTemplete(TYdzfMonitorTemplet record)
			throws BusinessException, SystemException {
		ydzfMonitorTempletMapper.insertSelective(record);
		return record.getMonitorTempletId();
	}

	@Override
	@Transactional
	public int updateMonitorTemplete(TYdzfMonitorTemplet record)
			throws BusinessException, SystemException {
		ydzfMonitorTempletMapper.updateByPrimaryKeySelective(record);
		return record.getMonitorTempletId();
	}

	@Override
	@Transactional
	public int delMonitorTemplete(int monitorTempletId,IUserInfo userInfo)
			throws BusinessException, SystemException {
		TYdzfMonitorTemplet t= new TYdzfMonitorTemplet();
		t.setMonitorTempletId(monitorTempletId);
		t.setIsDel(YDZFConstants.SYSTEM.IS_DEL);
		t.setUpdateDate(DateUtil.getCurrent());
		t.setUpdateUserid(userInfo.getUserId());
		ydzfMonitorTempletMapper.updateByPrimaryKeySelective(t);
		return t.getMonitorTempletId();
	}

	@Override
	public List<TYdzfMonitorTemplet> queryMonitorTempleteList(
			TYdzfMonitorTemplet record, Integer start, Integer count)
			throws SystemException, BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countMonitorTemplete(TYdzfMonitorTemplet record)
			throws SystemException, BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TYdzfMonitorTemplet queryMonitorTempleteInfo(int monitorTempletId)
			throws SystemException, BusinessException {
		return ydzfMonitorTempletMapper.selectByPrimaryKey(monitorTempletId);
	}

	@Override
	public List<TYdzfMonitorTemplet> queryMonitorTempleteAll(
			TYdzfMonitorTemplet record) throws SystemException, BusinessException {
		TYdzfMonitorTempletExample example=getCondition(record);
		return ydzfMonitorTempletMapper.selectByExample(example);
	}
	

	
	private TYdzfMonitorTempletExample getCondition(TYdzfMonitorTemplet record) {
		TYdzfMonitorTempletExample example=new TYdzfMonitorTempletExample();
		if (record != null) {
			com.bdx.rainbow.entity.ydzf.TYdzfMonitorTempletExample.Criteria cr=example.createCriteria();

			if(record.getMonitorTempletId() != null && record.getMonitorTempletId()>0) {
				cr.andMonitorTempletIdEqualTo(record.getMonitorTempletId());
			}
			if(StringUtils.isNotBlank(record.getIsDel())) {
				cr.andIsDelEqualTo(record.getIsDel());
			}
			
		}
		return example;
	}


}
