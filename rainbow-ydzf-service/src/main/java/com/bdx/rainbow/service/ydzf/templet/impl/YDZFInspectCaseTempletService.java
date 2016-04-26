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
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTemplet;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTempletExample;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseTempletMapper;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.templet.IYDZFInspectCaseTempletService;
import com.bdx.rainbow.urs.entity.IUserInfo;


@Service("ydzfInspectCaseTempletService")
public class YDZFInspectCaseTempletService extends BaseService implements IYDZFInspectCaseTempletService {
	private static final Logger log = LoggerFactory
			.getLogger(YDZFInspectCaseTempletService.class);
	@Autowired
	protected TYdzfInspectCaseTempletMapper ydzfInspectCaseTempletMapper;

	@Override
	@Transactional
	public int insertInspectCaseTemplete(TYdzfInspectCaseTemplet record)
			throws BusinessException, SystemException {
		ydzfInspectCaseTempletMapper.insertSelective(record);
		return record.getInspectCaseTempletId();
	}

	@Override
	@Transactional
	public int updateInspectCaseTemplete(TYdzfInspectCaseTemplet record)
			throws BusinessException, SystemException {
		ydzfInspectCaseTempletMapper.updateByPrimaryKeySelective(record);
		return record.getInspectCaseTempletId();
	}

	@Override
	@Transactional
	public int delInspectCaseTemplete(int InspectCaseTempletId,IUserInfo userInfo)
			throws BusinessException, SystemException {
		TYdzfInspectCaseTemplet t= new TYdzfInspectCaseTemplet();
		t.setInspectCaseTempletId(InspectCaseTempletId);
		t.setIsDel(YDZFConstants.SYSTEM.IS_DEL);
		t.setUpdateDate(DateUtil.getCurrent());
		t.setUpdateUserid(userInfo.getUserId());
		ydzfInspectCaseTempletMapper.updateByPrimaryKeySelective(t);
		return t.getInspectCaseTempletId();
	}

	@Override
	public List<TYdzfInspectCaseTemplet> queryInspectCaseTempleteList(
			TYdzfInspectCaseTemplet record, Integer start, Integer count)
			throws SystemException, BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countInspectCaseTemplete(TYdzfInspectCaseTemplet record)
			throws SystemException, BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TYdzfInspectCaseTemplet queryInspectCaseTempleteInfo(int InspectCaseTempletId)
			throws SystemException, BusinessException {
		return ydzfInspectCaseTempletMapper.selectByPrimaryKey(InspectCaseTempletId);
	}

	@Override
	public List<TYdzfInspectCaseTemplet> queryInspectCaseTempleteAll(
			TYdzfInspectCaseTemplet record) throws SystemException, BusinessException {
		TYdzfInspectCaseTempletExample example=getCondition(record);
		return ydzfInspectCaseTempletMapper.selectByExample(example);
	}
	

	
	private TYdzfInspectCaseTempletExample getCondition(TYdzfInspectCaseTemplet record) {
		TYdzfInspectCaseTempletExample example=new TYdzfInspectCaseTempletExample();
		if (record != null) {
			com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTempletExample.Criteria cr=example.createCriteria();

			if(record.getInspectCaseTempletId() != null && record.getInspectCaseTempletId()>0) {
				cr.andInspectCaseTempletIdEqualTo(record.getInspectCaseTempletId());
			}
			if(StringUtils.isNotBlank(record.getIsDel())) {
				cr.andIsDelEqualTo(record.getIsDel());
			}
			
		}
		return example;
	}


}
