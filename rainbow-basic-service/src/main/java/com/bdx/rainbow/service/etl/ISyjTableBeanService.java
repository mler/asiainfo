package com.bdx.rainbow.service.etl;

import java.util.List;

import com.bdx.rainbow.entity.etl.SyjTableBean;
import com.bdx.rainbow.entity.etl.SyjTableBeanExample;

public interface ISyjTableBeanService {
	
	List<SyjTableBean> list(SyjTableBeanExample example) throws Exception;
	
	void update(SyjTableBean syjTableBean) throws Exception;

	List<SyjTableBean> pageList(Integer pageStart, Integer pageCount);

	int countPageList();

	void truncateTable(String tableName);
}
