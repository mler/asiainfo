package com.bdx.rainbow.etl.analyze;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bdx.rainbow.entity.etl.OrgiPage;
import com.bdx.rainbow.etl.entity.seed.HttpSeed;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * 
 * 药店详细页面解析
 * @author mler
 *
 */
public class HomeDrugAnalyze implements Analyze<HttpSeed,HttpSeed,Collection<OrgiPage>> {

	private final Logger log = Logger.getLogger(HomeDrugAnalyze.class);
	
	private final static String DOMAIN = "http://app1.sfda.gov.cn/datasearch/face3/";
	
	/**
	 *	返回内容的页面html
	 */
	@Override
	public Collection<OrgiPage> findResult(Collection<HttpSeed> seed_collection) throws Exception {
		long t1 = System.currentTimeMillis();
		if(seed_collection == null || seed_collection.isEmpty())
			return null;
		
		Collection<OrgiPage> pages = new ArrayList<OrgiPage>();
		
		for(HttpSeed seed : seed_collection)
		{
			Document doc = Jsoup.parse(seed.getHtml(),"utf-8");
			Elements elements_td = doc.select("tr td");
			String code = elements_td.get(2).html();
			String name = elements_td.get(4).html();
			
//			System.out.println(code+" "+name);
			
			OrgiPage page = new OrgiPage();
			page.setContent("");
			page.setUpdateDate(new Timestamp(System.currentTimeMillis()));
//			page.setUrl(code+" "+name);
			pages.add(page);
		}
		
		log.info("生成结果对象(Result)："+new ObjectMapper().writeValueAsString(pages));
		
		log.info("解析结果对象用时：["+(System.currentTimeMillis()-t1)+"]");
		
		return pages;
	}

	/**
	 * 返回需要再次抓去的url
	 */
	@Override
	public Collection<HttpSeed> findSeed(Collection<HttpSeed> seed_collection) throws Exception {
		
		long t1 = System.currentTimeMillis();
		
		if(seed_collection == null || seed_collection.isEmpty())
			return null;
		
//		Collection<HttpSeed> seed_tasks = new ArrayList<HttpSeed>();
		
//		Collection<Collection<HttpSeed>> seeds_group = new ArrayList<Collection<HttpSeed>>();
		Collection<HttpSeed> seeds = new ArrayList<HttpSeed>();
		
		for(HttpSeed seed : seed_collection)
		{
			Document doc = Jsoup.parse(seed.getHtml(), "utf-8");
	//		<td a="0" style="padding-left:15px;color:#000000;font-size:12px" width="241">葡萄糖注射液</td>
			Elements drug_elements = doc.select("a[href]");
			
			if(drug_elements.isEmpty())
				return null;
			
//			Collection<HttpSeed> seeds = new ArrayList<HttpSeed>();
			
			for(Element drug_e : drug_elements)
			{
				String href_string = drug_e.attr("href");
				//javascript:commitForECMA(callbackC,'content.jsp?tableId=25&tableName=TABLE25&tableView=国产药品&Id=56634',null)
				String uri = href_string.substring(href_string.indexOf("'")+1,href_string.lastIndexOf("'"));
				
				if(StringUtils.isBlank(uri))
					continue;
				
				HttpSeed s = new HttpSeed();
				s.setCreateTime(System.currentTimeMillis());
				s.setUrl(DOMAIN+uri);
				s.setResolveTypes(new HashSet<String>());
				s.getResolveTypes().add(HttpSeed.RESOLVETYPE_RESULT);
				seeds.add(s);
				log.info("生成结果种子(Result Seed)："+s.getUrl()+",参数："+new ObjectMapper().writeValueAsString(s.getParam()));
			}
			
//			seeds_group.add(seeds);
		}
		log.info("解析结果种子用时：["+(System.currentTimeMillis()-t1)+"]");
		return seeds;
	}
	
	/**
	 * <form method="post" id="pageForm" name="pageForm" action="search.jsp">
		<input type="hidden" name="tableId" value="25">
		<input type="hidden" name="bcId" value="124356560303886909015737447882">
		<input type="hidden" name="curstart" value="">
		<input type="hidden" name="tableName" value="TABLE25">
		<input type="hidden" name="viewtitleName" value="COLUMN167">
		<input type="hidden" name="viewsubTitleName" value="COLUMN166,COLUMN170,COLUMN821">
		<input type="hidden" name="keyword" value="">
		<input type="hidden" name="tableView" value="国产药品">
		</form>
		
		获取分页的种子
	 */
	@Override
	public Collection<HttpSeed> findPageSeed(Collection<HttpSeed> seed_collection) throws Exception
	{
		long t1 = System.currentTimeMillis();
		if(seed_collection == null || seed_collection.isEmpty())
		{
			return null;
		}
		
//		Collection<Collection<HttpSeed>> seeds_group = new ArrayList<Collection<HttpSeed>>();
		Collection<HttpSeed> seeds = new ArrayList<HttpSeed>();
		
		for(HttpSeed s : seed_collection)
		{
			Document doc = Jsoup.parse(s.getHtml(), "utf-8");
			Elements page_form_elements = doc.select("#pageForm");
			if(page_form_elements.isEmpty())
				return null;
			
			Element page_form_e = page_form_elements.get(0);
			Elements param_elements = page_form_e.select("input");
			
			String action = page_form_e.attr("action");
		
			//获取页码，根据页码循环组装成新的SEED
			Element table_e = doc.select("table").get(4);
			String page_string = table_e.select("tr td").get(0).html();
			page_string = page_string.split("页")[1];
			Long num = Long.valueOf(page_string.substring(page_string.indexOf("共")+1).trim());
			
			if(num >1 )
			{
//				Collection<HttpSeed> seeds = new ArrayList<HttpSeed>();
				
				for(int i=1; i<num;i++)
				{
					if(i > 1000)
						continue;
					
					HttpSeed seed = new HttpSeed();
					seed.setUrl(DOMAIN+action);
					seed.setParam(new HashMap<String, String>());
					seed.setCreateTime(System.currentTimeMillis());
	//				Map<String,String> param = new HashMap<String,String>();
					for(Element param_e : param_elements)
					{
						seed.getParam().put(param_e.attr("name"), param_e.attr("value"));
					}
					seed.getParam().put("curstart", i+"");
					Set<String> resolverTypes = new HashSet<String>();
					resolverTypes.add(HttpSeed.RESOLVETYPE_SEED_COMMON);
					seed.setResolveTypes(resolverTypes);
					
					seeds.add(seed);
					
					log.info("生成分页种子对象(PageTask)："+seed.getUrl()+",参数："+new ObjectMapper().writeValueAsString(seed.getParam()));
				}
//				HttpSeed2Task<Collection<OrgiPage>> task = new HttpSeed2Task<Collection<OrgiPage>>(seeds,this);
//				tasks.add(task);
				
//				seeds_group.add(seeds);
			}
		}
		
		log.info("解析分页种子用时：["+(System.currentTimeMillis()-t1)+"]");
		
		return seeds;
	}

	public final static void main(String[] args) throws Exception
	{
		File file = new File("/Users/mler/Desktop/食药监数据库页面/2222.html");
		Document doc = Jsoup.parse(file,"utf-8");
		
		Elements elements_td = doc.select("tr td");
		String code = elements_td.get(2).html();
		String name = elements_td.get(4).html();
		
		System.out.println(code+" "+name);
	}

	@Override
	public void error(Map<String, Collection<HttpSeed>> seed_collection) throws Exception {
		// TODO Auto-generated method stub
		
	}


}
