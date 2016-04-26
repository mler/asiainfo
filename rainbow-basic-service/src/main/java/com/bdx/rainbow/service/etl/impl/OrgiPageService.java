package com.bdx.rainbow.service.etl.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.bdx.rainbow.entity.etl.OrgiPageExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.entity.etl.OrgiPage;
import com.bdx.rainbow.mapper.etl.OrgiPageMapper;
import com.bdx.rainbow.service.etl.IOrgiPageService;

@Service
@Transactional(rollbackFor=Exception.class,value="transactionManager")
public class OrgiPageService implements IOrgiPageService {

	@Autowired
	private OrgiPageMapper orgiPageMapper;
	
	public void save(OrgiPage page) throws Exception
	{
		if(page == null)
			return;
//			throw new NullPointerException("OrigPage is null");
		
		orgiPageMapper.insert(page);
	}
	
	public void saveBatch(List<OrgiPage> pages) throws Exception
	{
		if(pages == null || pages.isEmpty())
			return;
//			throw new NullPointerException("List<OrgiPage> is null or empty");
		
		orgiPageMapper.insertBatch(pages);
	}

	@Override
	public List<OrgiPage> getOrgiPageList(Integer start, Integer count) throws Exception {
		OrgiPageExample where = new OrgiPageExample();
		where.setLimitClauseStart(start);
		where.setLimitClauseCount(count);

		return orgiPageMapper.selectByExample(where);
	}

	@Override
	public int countOrgiPage() {
		OrgiPageExample where = new OrgiPageExample();
		return orgiPageMapper.countByExample(where);
	}

	public void saveHtmls(String className,Map<String,Collection<String>> htmlsMap) throws Exception
	{
		if(htmlsMap == null || htmlsMap.isEmpty())
			return;
//			throw new NullPointerException("List<OrgiPage> is null or empty");
		
		List<OrgiPage> pages = new ArrayList<OrgiPage>();
		
		for(String type : htmlsMap.keySet())
		{
			for(String html : htmlsMap.get(type))
			{
				OrgiPage p = new OrgiPage();
				p.setClassName(className);
				p.setResolveType(type);
				p.setContent(html);
				p.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				p.setStatus((short)0);
				
				pages.add(p);
			}
		}
		saveBatch(pages);
	}
}
