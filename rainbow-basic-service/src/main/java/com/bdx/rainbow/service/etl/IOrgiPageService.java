package com.bdx.rainbow.service.etl;

import java.util.List;

import com.bdx.rainbow.entity.etl.OrgiPage;

public interface IOrgiPageService {

	void save(OrgiPage page) throws Exception;

	void saveBatch(List<OrgiPage> pages) throws Exception;

	List<OrgiPage> getOrgiPageList(Integer start, Integer count) throws Exception;

	int countOrgiPage();
}