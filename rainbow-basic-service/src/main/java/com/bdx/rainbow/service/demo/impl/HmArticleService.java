package com.bdx.rainbow.service.demo.impl;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.entity.demo.HmArticle;
import com.bdx.rainbow.entity.demo.HmArticleExample;
import com.bdx.rainbow.mapper.demo.HmArticleMapper;
import com.bdx.rainbow.service.demo.IHmArticleService;

@Service
@Transactional
public class HmArticleService implements IHmArticleService {

    @Autowired
    private HmArticleMapper hmArticleMapper;

    public int delete(String title) throws Exception {
    	
    	HmArticle article = new HmArticle();
//    	article.setaId("99999");
		article.setaTitle(title);
		article.setaContent(title);
		hmArticleMapper.insertSelective(article);
//        throw new Exception("测试事务 ");
        return 0;
    }
    
    public void delete(String aid,String aid2) throws Exception{
    	
    	hmArticleMapper.deleteByPrimaryKey(aid);
    	
    	hmArticleMapper.deleteByPrimaryKey(aid2);
    	
    	throw new Exception("测试事务");
    }
    
    
    public List<HmArticle> list(HmArticleExample example) throws Exception{
    	
    	return hmArticleMapper.selectByExample(example);
    }

	@Async
	public Future<Void> execute() {
		return new AsyncResult<Void>(null);
	}
	
	@Async
    public Future<HmArticle> findOne(String aid)
    {
    	return new AsyncResult<HmArticle>(hmArticleMapper.selectByPrimaryKey(aid));
    }

	@Async
	public Future<Void> addHmArticle( String title ) {
		HmArticle article = new HmArticle();
		article.setaTitle(title);
		article.setaContent(title);
		hmArticleMapper.insertSelective(article);
		return new AsyncResult<Void>(null);
	}
    
}
