package com.bdx.rainbow.service.ydzf.templet.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorTempletAndContentVO;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlan;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTemplet;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTempletContent;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTempletContentExample;
import com.bdx.rainbow.mapper.ydzf.TYdzfMonitorPlanMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfMonitorTempletContentMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfMonitorTempletMapper;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.templet.IYDZFMonitorTempletContentService;
import com.bdx.rainbow.urs.entity.IUserInfo;


@Service("ydzfMonitorTempletContentService")
public class YDZFMonitorTempletContentService extends BaseService implements
		IYDZFMonitorTempletContentService {
	private static final Logger log = LoggerFactory
			.getLogger(YDZFMonitorTempletContentService.class);
	@Autowired
	protected TYdzfMonitorTempletContentMapper ydzfMonitorTempletContentMapper;
	@Autowired
	protected TYdzfMonitorTempletMapper ydzfMonitorTempletMapper;
	@Autowired
	protected TYdzfMonitorPlanMapper ydzfMonitorPlanMapper;

	@Override
	@Transactional
	public int insertMonitorTempleteContent(TYdzfMonitorTempletContent record)
			throws BusinessException, SystemException {
		ydzfMonitorTempletContentMapper.insert(record);
		return record.getMonitorTempletContentId();
	}
	
	@Override
	@Transactional
	public void insertMonitorTempleteContents(List<TYdzfMonitorTempletContent> recordList){
		 ydzfMonitorTempletContentMapper.insertBatch(recordList);
	}


	@Override
	@Transactional
	public int updateMonitorTempleteContent(TYdzfMonitorTempletContent record){
	int id=ydzfMonitorTempletContentMapper.updateByPrimaryKeySelective(record);
	return id;
	
	}

	@Override
	public int delMonitorTempleteContentByid(int monitorTempletContentId,
			IUserInfo userInfo) throws BusinessException, SystemException {
		TYdzfMonitorTempletContent t= new TYdzfMonitorTempletContent();
		t.setMonitorTempletContentId(monitorTempletContentId);
		t.setIsDel(YDZFConstants.SYSTEM.IS_DEL);
		t.setUpdateDate(DateUtil.getCurrent());
		t.setUpdateUserid(userInfo.getUserId());
		ydzfMonitorTempletContentMapper.updateByPrimaryKeySelective(t);
		return t.getMonitorTempletId();
	}

	@Override
	public int delMonitorTempleteContentByTempId(int monitorTempletId,
			IUserInfo userInfo) throws BusinessException, SystemException {
		TYdzfMonitorTempletContent record= new TYdzfMonitorTempletContent();
		record.setIsDel(YDZFConstants.SYSTEM.IS_DEL);
		record.setUpdateDate(DateUtil.getCurrent());
		record.setUpdateUserid(userInfo.getUserId());
		TYdzfMonitorTempletContentExample example=new TYdzfMonitorTempletContentExample();
		com.bdx.rainbow.entity.ydzf.TYdzfMonitorTempletContentExample.Criteria cr=example.createCriteria();
		cr.andMonitorTempletIdEqualTo(monitorTempletId);
		return ydzfMonitorTempletContentMapper.updateByExample(record, example);
	}

	@Override
	public List<TYdzfMonitorTempletContent> queryMonitorTempleteContenList(
			TYdzfMonitorTempletContent record, Integer start, Integer count)
			throws SystemException, BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countMonitorTempleteContent(TYdzfMonitorTempletContent t)
			throws SystemException, BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TYdzfMonitorTempletContent queryMonitorTempleteContentInfo(
			int monitorTempletContentId) throws SystemException,
			BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TYdzfMonitorTempletContent> queryMonitorTempleteContenAll(
			TYdzfMonitorTempletContent record) throws SystemException,
			BusinessException {
		TYdzfMonitorTempletContentExample example=getCondition(record);
		return ydzfMonitorTempletContentMapper.selectByExample(example);
	}
	
	private TYdzfMonitorTempletContentExample getCondition(TYdzfMonitorTempletContent record) {
		TYdzfMonitorTempletContentExample example=new TYdzfMonitorTempletContentExample();
		if (record != null) {
			com.bdx.rainbow.entity.ydzf.TYdzfMonitorTempletContentExample.Criteria cr=example.createCriteria();

			if(record.getMonitorTempletId() != null && record.getMonitorTempletId()>0) {
				cr.andMonitorTempletIdEqualTo(record.getMonitorTempletId());
			}
			if(StringUtils.isNotBlank(record.getIsDel())) {
				cr.andIsDelEqualTo(record.getIsDel());
			}
			
		}
		return example;
	}

	@Override
	public YdzfMonitorTempletAndContentVO queryMonitorTempleteAndContentInfoByPlanId(
			Integer monitorPlanId) throws SystemException, BusinessException {
		if(monitorPlanId==null||monitorPlanId==0)
		{
			throw new SystemException(new DefaultExceptionCode("参数异常"));
		}
		YdzfMonitorTempletAndContentVO vo = new YdzfMonitorTempletAndContentVO();
		TYdzfMonitorPlan ydzfMonitorPlan=ydzfMonitorPlanMapper.selectByPrimaryKey(monitorPlanId);
		TYdzfMonitorTemplet ydzfMonitorTemplet=ydzfMonitorTempletMapper.selectByPrimaryKey(ydzfMonitorPlan.getMonitorTempletId());
		TYdzfMonitorTempletContent record = new TYdzfMonitorTempletContent();
		record.setMonitorTempletId(ydzfMonitorTemplet.getMonitorTempletId());
		List<TYdzfMonitorTempletContent> list=this.queryMonitorTempleteContenAll(record);
		vo.setYdzfMonitorTemplet(ydzfMonitorTemplet);
		vo.setYdzfMonitorTempletContentList(list);
		return vo;
	}

    @Override
    public int updateMonitorByMonitorTempletId(TYdzfMonitorTempletContent t,int monitorTempletId) {
        TYdzfMonitorTempletContent record=new TYdzfMonitorTempletContent();
        record.setMonitorTempletId(monitorTempletId);
        TYdzfMonitorTempletContentExample example=getCondition(record);
        return ydzfMonitorTempletContentMapper.updateByExampleSelective(t,example);
    }

}
