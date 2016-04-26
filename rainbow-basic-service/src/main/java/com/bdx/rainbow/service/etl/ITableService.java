package com.bdx.rainbow.service.etl;

import java.util.List;

public interface ITableService {
	
	public void insertBatch(List<Object> records, String mapperName) throws Exception;
	
}
