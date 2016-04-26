package com.bdx.rainbow.service.demo;

import java.util.List;
import java.util.concurrent.Future;

import com.bdx.rainbow.entity.demo.HmArticle;
import com.bdx.rainbow.entity.demo.HmArticleExample;

public interface IHmArticleService {

    public int delete(String title) throws Exception;
    
    public void delete(String aid,String aid2) throws Exception;
    
    public List<HmArticle> list(HmArticleExample example) throws Exception;

	public Future<Void> execute() ;
	
    public Future<HmArticle> findOne(String aid);

	public Future<Void> addHmArticle( String title );
    
}
