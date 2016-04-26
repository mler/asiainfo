package com.bdx.rainbow.etl.dispatcher.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdx.rainbow.etl.analyze.Analyze;
import com.bdx.rainbow.etl.dispatcher.Dispatcher;
import com.bdx.rainbow.etl.entity.seed.HttpSeed;
import com.bdx.rainbow.etl.pool.Excutor;
import com.bdx.rainbow.etl.task.Seed2Callable;
import com.bdx.rainbow.etl.task.impl.HttpSeed2Task;

public class HttpSeedDispatcher<V> implements Dispatcher<HttpSeed,V> {

	private Logger log = LoggerFactory.getLogger(HttpSeedDispatcher.class);
	
	//分发到的目标线程池
	private Excutor<HttpSeed, V> excutor;
	
	private Analyze<HttpSeed, HttpSeed, V> analyze;
	
	public HttpSeedDispatcher(Excutor<HttpSeed, V> excutor) {
		super();
		this.excutor = excutor;
	}
	
	public HttpSeedDispatcher(Excutor<HttpSeed, V> excutor,
			Analyze<HttpSeed, HttpSeed, V> analyze) {
		super();
		this.excutor = excutor;
		this.analyze = analyze;
	}

	public void setExcutor(Excutor<HttpSeed, V> excutor) {
		this.excutor = excutor;
	}

	@Override
	public V dispatch(Collection<HttpSeed> success, Collection<HttpSeed> fail) throws Exception {

		Map<HttpSeed,String> seeds_htmls_map = new HashMap<HttpSeed,String>();//普通种子
		Map<HttpSeed,String> page_seeds_htmls_map = new HashMap<HttpSeed,String>();//分页种子
		Map<HttpSeed,String> result_htmls_map = new HashMap<HttpSeed,String>();//最终结果界面
		
		//解析失败的种子
		Map<String,Collection<HttpSeed>> error_htmls = new HashMap<String, Collection<HttpSeed>>();
		
		try{
			//将resolverType作为并列关系
			for(HttpSeed response : success)
			{
	//			if(response.getHtml() == null || StringUtils.isBlank(response.getHtml()))
	//				continue;
				
				if(response.getResolveTypes().contains(HttpSeed.RESOLVETYPE_SEED_PAGE))
				{
					page_seeds_htmls_map.put(response,response.getHtml());
				}
				if(response.getResolveTypes().contains(HttpSeed.RESOLVETYPE_SEED_COMMON))
				{
					seeds_htmls_map.put(response,response.getHtml());
				}
				if(response.getResolveTypes().contains(HttpSeed.RESOLVETYPE_RESULT))
				{
					result_htmls_map.put(response,response.getHtml());
				}
			}
			
			/** 普通页面处理失败捕获 **/
			Collection<HttpSeed> seeds_htmls_list = new ArrayList<HttpSeed>(seeds_htmls_map.keySet());
			try{
				excutor.addSeeds(createHttpSeed2Tasks(analyze.findSeed(seeds_htmls_list)));
			}catch(Exception e){
				e.printStackTrace();
				error_htmls.put(HttpSeed.RESOLVETYPE_SEED_COMMON, seeds_htmls_list);
			}
			
			/** 分页页面处理失败捕获 **/
			Collection<HttpSeed> page_seeds_htmls_list = new ArrayList<HttpSeed>(page_seeds_htmls_map.keySet());
			try{
				excutor.addSeeds(createHttpSeed2Tasks(analyze.findPageSeed(page_seeds_htmls_list)));
			}catch(Exception e){
				e.printStackTrace();
				error_htmls.put(HttpSeed.RESOLVETYPE_SEED_PAGE, page_seeds_htmls_list);
			}
			
			/** 结果处理失败捕获 **/
			Collection<HttpSeed> result_htmls_list = new ArrayList<HttpSeed>(result_htmls_map.keySet());
			try{
				V v = analyze.findResult(result_htmls_list);
				return v;
			}catch(Exception e){
				e.printStackTrace();
				error_htmls.put(HttpSeed.RESOLVETYPE_RESULT, result_htmls_list);
			}
		}
		finally
		{
			analyze.error(error_htmls);
			/** 将失败的加入线程池 **/
			if(fail != null && fail.isEmpty() == false)
				excutor.addSeeds(createHttpSeed2Tasks(fail));
		}
		
		return null;
	}
	
	/**
	 * 生成任务
	 * @param seeds_group
	 * @return
	 */
	private Collection<Seed2Callable<HttpSeed,V>> createHttpSeed2Tasks(Collection<HttpSeed> seeds)
	{
		log.debug(this.toString());
		
		if(seeds == null || seeds.isEmpty())
			return null;
		
		Collection<Seed2Callable<HttpSeed,V>> tasks = new ArrayList<Seed2Callable<HttpSeed,V>>();
//		int i=0;
		for(HttpSeed seed : seeds)
		{
			Collection<HttpSeed> seed_list = new ArrayList<HttpSeed>(1);
			seed_list.add(seed);
			
			
			tasks.add(new HttpSeed2Task<V>(seed_list,this));
		}
		
		return tasks;
			
	}

}
