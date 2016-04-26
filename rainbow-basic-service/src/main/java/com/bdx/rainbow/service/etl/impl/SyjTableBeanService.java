package com.bdx.rainbow.service.etl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.entity.etl.SyjTableBean;
import com.bdx.rainbow.entity.etl.SyjTableBeanExample;
import com.bdx.rainbow.mapper.etl.SyjTableBeanMapper;
import com.bdx.rainbow.service.etl.ISyjTableBeanService;

@Service 
@Transactional(rollbackFor=Exception.class,value="transactionManager") 
public class SyjTableBeanService implements ISyjTableBeanService {

	@Autowired
	private SyjTableBeanMapper syjTableBeanMapper;
	
	@Override
	public List<SyjTableBean> list(SyjTableBeanExample example) throws Exception {
		example.setOrderByClause("id asc");

		List<SyjTableBean> list = syjTableBeanMapper.selectByExample(example);
		
		return list;
	}

	@Override
	public void update(SyjTableBean syjTableBean) throws Exception {
		
		syjTableBeanMapper.updateByPrimaryKey(syjTableBean);
	}

	@Override
	public List<SyjTableBean> pageList(Integer pageStart, Integer pageCount) {
		SyjTableBeanExample where = new SyjTableBeanExample();
		where.setLimitClauseStart(pageStart);
		where.setLimitClauseCount(pageCount);

		where.setOrderByClause("id asc");

		return syjTableBeanMapper.selectByExample(where);
	}

	@Override
	public int countPageList() {
		SyjTableBeanExample where = new SyjTableBeanExample();

		return syjTableBeanMapper.countByExample(where);
	}

	@Override
	public void truncateTable(String tableName) {
		syjTableBeanMapper.truncateTable(tableName);
	}
}
