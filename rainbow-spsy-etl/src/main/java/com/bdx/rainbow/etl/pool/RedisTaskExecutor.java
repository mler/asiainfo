package com.bdx.rainbow.etl.pool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;

import com.bdx.rainbow.etl.entity.seed.HttpSeed;
import com.bdx.rainbow.etl.entity.seed.Seed;
import com.bdx.rainbow.etl.task.Seed2Callable;
//import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author mler
 * @param <S> 种子的类型 继承与Seed接口
 * @param <V> 返回的类型
 */
public class RedisTaskExecutor<S extends Seed,V> extends Excutor<S, V> {
	
	private final String REDIS_TASK_KEY = "_redis_task_";
	
	private final Logger log = LoggerFactory.getLogger(LocalTaskExcutor.class);
	
	/** 用于计算种子线程池中存货的线程 **/
	private AtomicLong thread_submit = new AtomicLong(0);
	
	/** 用于计算种子线程池中存货的线程 **/
	private AtomicLong thread_completed = new AtomicLong(0);
	
	protected ThreadPoolExecutor pool; 
	
	/** Result处理的completionService **/
	private CompletionService<V> completionService;
	
	public RedisTaskExecutor() throws Exception {
		super();
		this.pool = EtlTheadPoolFactory.getPoolInstance();
		
		
		completionService = new ExecutorCompletionService<V>(pool,new LinkedBlockingQueue<Future<V>>());
	}
	
	public RedisTaskExecutor(Seed2Callable<S, V> root_task,ThreadPoolExecutor pool) {
		super();
		this.pool = pool;
		if(this.pool == null)
			this.pool = EtlTheadPoolFactory.getPoolInstance();
		
		completionService = new ExecutorCompletionService<V>(pool,new LinkedBlockingQueue<Future<V>>());
	}
	
	public RedisTaskExecutor(String name) throws Exception {
		super();
		this.pool = EtlTheadPoolFactory.getPoolInstance(name);
		completionService = new ExecutorCompletionService<V>(pool,new LinkedBlockingQueue<Future<V>>());
	}
	
	public RedisTaskExecutor(String name,Seed2Callable<S, V> root_task,ThreadPoolExecutor pool) {
		super();
		this.pool = pool;
		if(this.pool == null)
			this.pool = EtlTheadPoolFactory.getPoolInstance(name);
		
		completionService = new ExecutorCompletionService<V>(pool,new LinkedBlockingQueue<Future<V>>());
	}

	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.Excutor#addSeed(com.bdx.rainbow.etl.task.Seed2Callable)
	 */
	@Override
	public synchronized void addSeed(Seed2Callable<S ,V> seed)
	{
//		pool.awaitTermination(timeout, unit)
		completionService.submit(seed);
		log.info("有新任务提交，总数["+thread_submit.addAndGet(1)+"]");
	}
	
	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.Excutor#addSeeds(java.util.Collection)
	 */
	@Override
	public void addSeeds(Collection<Seed2Callable<S,V>> seed_collection)
	{
		//由于dealSeed中会对seeds做remove操作，因此需要加锁
		if(seed_collection ==  null || seed_collection.isEmpty())
			return;
		
		for(Seed2Callable<S,V> seed : seed_collection)
			addSeed(seed);
	}
	
	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.Excutor#findFailSeed()
	 */
	public Map<Seed,Integer> findFailSeed()
	{
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.Excutor#addFailSeed(com.bdx.rainbow.etl.entity.seed.Seed)
	 */
	@Override
	@CachePut(value="", key="failSeed")
	public synchronized Map<Seed,Integer> addFailSeed(Seed failSeed)
	{
		Map<Seed,Integer> failMap = findFailSeed();
		if(failMap == null)
			failMap = new HashMap<Seed, Integer>();
		
		Integer fail_times = failMap.get(failSeed);
		
		fail_times = fail_times == null ? 1:fail_times+1; 
		
		failMap.put(failSeed, fail_times);
		
		return failMap;
	}
	
	private void dealSeed()
	{
//		ObjectMapper mapper = new ObjectMapper();
		do
		{
			try {
				V pages =  completionService.take().get();//堵塞
				//log.info("["+thread_completed.get()+"],共"+(pages == null?"0":((Collection)pages).size())+"个 :"+mapper.writeValueAsString(pages));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				//无论任务是成功还是失败，将完成数量减1，失败的任务会重新发起新的任务。
				thread_completed.addAndGet(1);
			}
			log.info("[thread_submit:"+thread_submit.get()+" == thread_completed:"+thread_completed.get()+"]");
		}
		while(thread_submit.get() > thread_completed.get());
		
//		if(thread_submit.get() == thread_completed.get())
		
		log.info("================dealResult 执行完毕 [thread_submit:"+thread_submit.get()+" == thread_completed:"+thread_completed.get()+"]==================");
		
	}
	
	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.Excutor#runRootTask(com.bdx.rainbow.etl.task.Seed2Callable)
	 */
	@Override
	public void runRootTask(Seed2Callable<S, V> root_task){

		root_task.setExecutor(this);
		 
		thread_submit = new AtomicLong(0);
		thread_completed = new AtomicLong(0);
		
		long t1 = System.currentTimeMillis();
		
		addSeed(root_task);
		
		dealSeed();
		
		log.info("============================[完成结果处理时间："+(System.currentTimeMillis()-t1)+" ms ]===================================");
	}
	
	/* (non-Javadoc)
	 * @see com.bdx.rainbow.etl.pool.Excutor#shutDown()
	 */
	@Override
	public void shutDown() throws InterruptedException
	{
		List<Runnable> runs = pool.shutdownNow();
		
		log.info("Runnable 数量："+runs.size());
		
		while(true){  
            if(pool.isTerminated()){  
                log.info("所有的子线程都结束了！");  
                break;  
            }  
            Thread.sleep(1000);    
        }  
	}
	
	public final static void main(String[] args) throws Exception
	{
		ThreadPoolExecutor pool = EtlTheadPoolFactory.getPoolInstance("test");
		
		pool.wait();
		
		pool.notify();
		
		
		long t1 = System.currentTimeMillis();
		
		HttpSeed seed = new HttpSeed();
		seed.setCreateTime(System.currentTimeMillis());
		seed.setUrl("http://app1.sfda.gov.cn/datasearch/face3/search.jsp");
		Set<String> resolveTypes = new HashSet<String>();
		resolveTypes.add(HttpSeed.RESOLVETYPE_SEED_PAGE);
		seed.setResolveTypes(resolveTypes);
		seed.setParam(new HashMap<String, String>());
		seed.getParam().put("tableId", "25");
		seed.getParam().put("bcId", "124356560303886909015737447882");
		seed.getParam().put("curstart", "1");
		seed.getParam().put("tableName", "TABLE25");
		seed.getParam().put("viewtitleName", "COLUMN167");
		seed.getParam().put("viewsubTitleName", "COLUMN166,COLUMN170,COLUMN821");
		seed.getParam().put("tableView", "%E5%9B%BD%E4%BA%A7%E8%8D%AF%E5%93%81");
		
		org.apache.log4j.Logger.getLogger("org.hibernate").setLevel(Level.ERROR);  
		
		Collection<HttpSeed> root_seeds = new ArrayList<HttpSeed>();
		root_seeds.add(seed);
		
//		HomeDrugAnalyze analyze = new HomeDrugAnalyze();
//		LocalTaskExcutor<HttpSeed, Collection<OrgiPage>> executor = new LocalTaskExcutor<HttpSeed, Collection<OrgiPage>>();
//		HttpSeedDispatcher<Collection<OrgiPage>> dispatcher = new HttpSeedDispatcher<Collection<OrgiPage>>(executor,analyze);
//		HttpSeed2Task<Collection<OrgiPage>> root_task = new HttpSeed2Task<Collection<OrgiPage>>(root_seeds ,dispatcher);
		
//		Thread.sleep(1000000000);
		
//		executor.runRootTask(root_task);
		
//		TaskExcutor.dealSeed();
//		
		System.out.println("============================[完成结果处理时间："+(System.currentTimeMillis()-t1)+" ms ]===================================");
//		
//		executor.shutDown();
		
		
	}
	
}
