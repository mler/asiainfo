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
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseTempletAndContentVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTemplet;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTempletContent;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTempletContentExample;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseTempletContentMapper;
import com.bdx.rainbow.mapper.ydzf.TYdzfInspectCaseTempletMapper;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.templet.IYDZFInspectCaseTempletContentService;
import com.bdx.rainbow.urs.entity.IUserInfo;


@Service("ydzfInspectCaseTempletContentService")
public class YDZFInspectCaseTempletContentService extends BaseService implements
		IYDZFInspectCaseTempletContentService {
	private static final Logger log = LoggerFactory
			.getLogger(YDZFInspectCaseTempletContentService.class);
	@Autowired
	protected TYdzfInspectCaseTempletContentMapper ydzfInspectCaseTempletContentMapper;
	@Autowired
	protected TYdzfInspectCaseTempletMapper ydzfInspectCaseTempletMapper;
	@Autowired
	protected TYdzfInspectCaseMapper ydzfInspectCaseMapper;

	@Override
	@Transactional
	public int insertInspectCaseTempleteContent(TYdzfInspectCaseTempletContent record)
			throws BusinessException, SystemException {
        try {
            ydzfInspectCaseTempletContentMapper.insert(record);
        }catch (Exception e){
            e.printStackTrace();
        }

		return record.getInspectCaseTempletContentId();
	}
	
	@Override
	@Transactional
	public void insertInspectCaseTempleteContents(List<TYdzfInspectCaseTempletContent> recordList){
		 ydzfInspectCaseTempletContentMapper.insertBatch(recordList);
	}


	@Override
	@Transactional
	public int updateInspectCaseTempleteContent(TYdzfInspectCaseTempletContent record){
	int id=ydzfInspectCaseTempletContentMapper.updateByPrimaryKeySelective(record);
	return id;
	
	}

	@Override
	public int delInspectCaseTempleteContentByid(int InspectCaseTempletContentId,
			IUserInfo userInfo) throws BusinessException, SystemException {
		TYdzfInspectCaseTempletContent t= new TYdzfInspectCaseTempletContent();
		t.setInspectCaseTempletContentId(InspectCaseTempletContentId);
		t.setIsDel(YDZFConstants.SYSTEM.IS_DEL);
		t.setUpdateDate(DateUtil.getCurrent());
		t.setUpdateUserid(userInfo.getUserId());
		ydzfInspectCaseTempletContentMapper.updateByPrimaryKeySelective(t);
		return t.getInspectCaseTempletId();
	}

	@Override
	public int delInspectCaseTempleteContentByTempId(int InspectCaseTempletId,
			IUserInfo userInfo) throws BusinessException, SystemException {
		TYdzfInspectCaseTempletContent record= new TYdzfInspectCaseTempletContent();
		record.setIsDel(YDZFConstants.SYSTEM.IS_DEL);
		record.setUpdateDate(DateUtil.getCurrent());
		record.setUpdateUserid(userInfo.getUserId());
		TYdzfInspectCaseTempletContentExample example=new TYdzfInspectCaseTempletContentExample();
		com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTempletContentExample.Criteria cr=example.createCriteria();
		cr.andInspectCaseTempletIdEqualTo(InspectCaseTempletId);
		return ydzfInspectCaseTempletContentMapper.updateByExample(record, example);
	}

	@Override
	public List<TYdzfInspectCaseTempletContent> queryInspectCaseTempleteContenList(
			TYdzfInspectCaseTempletContent record, Integer start, Integer count)
			throws SystemException, BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countInspectCaseTempleteContent(TYdzfInspectCaseTempletContent t)
			throws SystemException, BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TYdzfInspectCaseTempletContent queryInspectCaseTempleteContentInfo(
			int InspectCaseTempletContentId) throws SystemException,
			BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TYdzfInspectCaseTempletContent> queryInspectCaseTempleteContenAll(
			TYdzfInspectCaseTempletContent record) throws SystemException,
			BusinessException {
		TYdzfInspectCaseTempletContentExample example=getCondition(record);
		return ydzfInspectCaseTempletContentMapper.selectByExample(example);
	}
	
	private TYdzfInspectCaseTempletContentExample getCondition(TYdzfInspectCaseTempletContent record) {
		TYdzfInspectCaseTempletContentExample example=new TYdzfInspectCaseTempletContentExample();
		if (record != null) {
			com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTempletContentExample.Criteria cr=example.createCriteria();

			if(record.getInspectCaseTempletId() != null && record.getInspectCaseTempletId()>0) {
				cr.andInspectCaseTempletIdEqualTo(record.getInspectCaseTempletId());
			}
			if(StringUtils.isNotBlank(record.getIsDel())) {
				cr.andIsDelEqualTo(record.getIsDel());
			}
			
		}
		return example;
	}

	@Override
	public YdzfInspectCaseTempletAndContentVO queryInspectCaseTempleteAndContentInfoByCaseId(
			Integer InspectCaseId) throws SystemException, BusinessException {
		if(InspectCaseId==null||InspectCaseId==0)
		{
			throw new SystemException(new DefaultExceptionCode("参数异常"));
		}
		YdzfInspectCaseTempletAndContentVO vo = new YdzfInspectCaseTempletAndContentVO();
		TYdzfInspectCase ydzfInspectCase=ydzfInspectCaseMapper.selectByPrimaryKey(InspectCaseId);
		TYdzfInspectCaseTemplet ydzfInspectCaseTemplet=ydzfInspectCaseTempletMapper.selectByPrimaryKey(ydzfInspectCase.getInspectCaseTempletId());
		TYdzfInspectCaseTempletContent record = new TYdzfInspectCaseTempletContent();
		record.setInspectCaseTempletId(ydzfInspectCaseTemplet.getInspectCaseTempletId());
		List<TYdzfInspectCaseTempletContent> list=this.queryInspectCaseTempleteContenAll(record);
		vo.setYdzfInspectCaseTemplet(ydzfInspectCaseTemplet);
		vo.setYdzfInspectCaseTempletContentList(list);
		return vo;
	}

    @Override
    public int updateInspectByInspectTempletId(TYdzfInspectCaseTempletContent t, int inspectCaseTempletId) {
        TYdzfInspectCaseTempletContent record=new TYdzfInspectCaseTempletContent();
        TYdzfInspectCaseTempletContentExample example=getCondition(record);
        return ydzfInspectCaseTempletContentMapper.updateByExampleSelective(t,example);
    }

}
