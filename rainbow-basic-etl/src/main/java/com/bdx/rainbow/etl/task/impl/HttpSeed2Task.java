package com.bdx.rainbow.etl.task.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdx.rainbow.common.util.HttpClientUtil;
import com.bdx.rainbow.etl.dispatcher.impl.HttpSeedDispatcher;
import com.bdx.rainbow.etl.entity.seed.HttpSeed;
import com.bdx.rainbow.etl.pool.LocalTaskExcutor;
import com.bdx.rainbow.etl.task.Seed2Callable;

public class HttpSeed2Task<V> extends Seed2Callable<HttpSeed,V> {
	
	public Logger log = LoggerFactory.getLogger(HttpSeed2Task.class);
	
	private Collection<HttpSeed> fail;
	
	private HttpSeedDispatcher<V> dispatcher;
	
	private boolean flag = false;
	
	public HttpSeed2Task(Collection<HttpSeed> seeds,HttpSeedDispatcher<V> dispatcher) {
		super(seeds);
		this.dispatcher = dispatcher;
	}
	
//	public HttpSeed2Task(Collection<HttpSeed> seeds,TaskExcutor<HttpSeed, V> executor,HttpSeedDispatcher<V> dispatcher) {
//		super(seeds);
//		this.dispatcher = dispatcher;
//		this.dispatcher.setExcutor(executor);
//	}
	
	public V execute(Collection<HttpSeed> seeds) throws Exception {
		
		for(HttpSeed seed : new CopyOnWriteArrayList<HttpSeed>(seeds))
		{
			
			try{
				if(seed != null && StringUtils.isEmpty(((HttpSeed)seed).getUrl()) == false)
				{
					if(flag)
					{
						long t = 0;
						
						do{
							t = LocalTaskExcutor.canCall(System.currentTimeMillis());
							if(t > 0)
								Thread.sleep(t);
						}
						while(t > 0);
					}
					long t1 = System.currentTimeMillis();
					String html = HttpClientUtil.getStringResponse(((HttpSeed)seed).getUrl(), null, null,((HttpSeed)seed).getParam(),(seed.getCharset()==null||seed.getCharset().equals(""))?"UTF-8":seed.getCharset());
					
//					String html = HttpClientUtil.getStringResponseByProxy(((HttpSeed)seed).getUrl(), null, null,((HttpSeed)seed).getParam(),"hzproxy.asiainfo.com",8080,(seed.getCharset()==null||seed.getCharset().equals(""))?"UTF-8":seed.getCharset());
					
					((HttpSeed)seed).setHtml(html);
					((HttpSeed)seed).setCreateTime(System.currentTimeMillis());
					LocalTaskExcutor.error_times = new AtomicInteger(0);
					log.info("创建连接到获取数据用时：["+(System.currentTimeMillis()-t1)+"]");
				}
			}
			catch(Exception e)
			{
				log.error("任务失败：["+getName()+"],连续出错："+LocalTaskExcutor.error_times.addAndGet(1));
				seeds.remove(seed);//在后期做处理的时候将seed为空的任务清除
//				seed.setHtml(null);
				if(fail == null)
					fail = new HashSet<HttpSeed>();
				fail.add(seed);
				e.printStackTrace();
				continue;
			}
			
		}
		
		/**
		 * 这里设置可以强制由具体的root_seed所生成的所有seed都在同一个executor下执行，保证计数器可用，才能确认是否完成
		 **/
//		dispatcher.setExcutor(executor);
		return dispatcher.dispatch(seeds,fail);
	}
}
