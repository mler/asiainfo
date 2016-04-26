package com.bdx.rainbow.service.etl.analyze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bdx.rainbow.entity.etl.FoodSafetyStandard;
import com.bdx.rainbow.etl.analyze.Analyze;
import com.bdx.rainbow.etl.entity.seed.HttpSeed;
import com.bdx.rainbow.service.etl.IFoodSafetyStandardService;
import com.bdx.rainbow.service.etl.util.AnalyzeUtil;
import com.bdx.rainbow.service.etl.util.SpringBeanFactory;

/**
 * 解析食品安全国家标准HTML页面
 * 
 * @author Administrator
 *
 */
public class FoodSafetyStandardAnalyze implements Analyze<HttpSeed, HttpSeed, Collection<FoodSafetyStandard>> {

	/**
	 *	食品安全国家标准
	 */
	public static final String FOOD_SAFETY_STANTARD_URL = "http://125.35.6.7:80/db?task=listStandardGJ&accessData=gj";
	
	/**
	 * 详情页面抓取URL
	 * 	其中%s需要替换成相应的JS中的guid值
	 */
	private static final String STANDARD_DETAIL_URL = "http://bz.cfsa.net.cn/staticPages/%s.html";
	
	/**
	 * 解析食品安全国家标准详情页面
	 */
	@Override
	public Collection<FoodSafetyStandard> findResult(Collection<HttpSeed> seeds)
			throws Exception {
		if (null == seeds || seeds.isEmpty()) {
			return null;
		}
		
		Collection<FoodSafetyStandard> pages = new ArrayList<FoodSafetyStandard>();
		
		for (HttpSeed seed : seeds) {
			Document doc = Jsoup.parse(seed.getHtml());
			
			FoodSafetyStandard foodSafetyStandard = this.analyzeDetailHtml(doc);
			
			pages.add(foodSafetyStandard);
		}
		
		try {
			// 数据入库
			IFoodSafetyStandardService foodSafetyStandardService = (IFoodSafetyStandardService) SpringBeanFactory.getSpringBean("foodSafetyStandardService");
//			foodSafetyStandardService.insertBatch((List<FoodSafetyStandard>) pages);
		} catch (Exception e) {
			System.out.println(new ArrayList<FoodSafetyStandard>(pages).get(0).getGbNo());
			System.out.println(new ArrayList<FoodSafetyStandard>(pages).get(0).getContent());
			
			throw e;
		}
		
		return pages;
	}
	
	/**
	 * 解析HTML对象
	 * 
	 * @param document
	 */
	private FoodSafetyStandard analyzeDetailHtml(Document document) throws Exception {

		// 食品安全标准对象
		FoodSafetyStandard foodSafetyStandard = new FoodSafetyStandard();

		// 详情标题
		Elements btDiv = document.select("div.bz_tit_a");
		String btHtml = btDiv.html();

		// 国标号码
		String GB_No = btHtml.substring(0, btHtml.indexOf("<i>")).trim();
		GB_No = dealAnnotationHtml(GB_No);
		GB_No = regexColon(GB_No);

		foodSafetyStandard.setGbNo(GB_No);

		// 使用状态
		String status = btDiv.select("i").html().trim();
		status = dealAnnotationHtml(status);
		status = regexColon(status);

		foodSafetyStandard.setStatus(status);

		// 详情内容
		Elements nrDiv = document.select("div.bz_cont");

		// 内容简介
		Elements jjSpan = nrDiv.select("span.list_zt_top");
		Elements jji = jjSpan.select("i");

		int i = 0;
		for (Element i_ele : jji) {
			String jj = i_ele.html().trim();
			jj = dealAnnotationHtml(jj);
			jj = regexColon(jj);

			switch (i) {
			// 标准名称
			case 0:
				foodSafetyStandard.setChnName(jj);
				break;
			// 英文名称
			case 1:
				foodSafetyStandard.setEnName(jj);
				break;
			// 标准类型
			case 2:
				foodSafetyStandard.setType(jj);
				break;
			// 发布日期
			case 3:
				foodSafetyStandard.setPublishDate(jj);
				break;
			// 实施日期
			case 4:
				foodSafetyStandard.setMaterialDate(jj);
				break;
			case 5:
				foodSafetyStandard.setTag(jj);
				break;
			case 6: 
				foodSafetyStandard.setDraftCompany(jj);
				break;
			}

			i++;
		}

		// 标准摘要
		Element jyDiv = nrDiv.select("div.bz_det_cot").get(0);
		Elements jySpan = jyDiv.select("p>span");
		
		StringBuffer content = new StringBuffer();
		
		for (Element span_ele : jySpan) {
			String jy = span_ele.text().trim();
			jy = dealAnnotationHtml(jy);
			jy = regexColon(jy);

			content.append(jy);
		}
		foodSafetyStandard.setContent(content.toString());

		// 附件
		Elements fjSpan = nrDiv.select("span.contenrs_pdf");
		if (!fjSpan.isEmpty()) {
			String fjName = fjSpan
					.html()
					.substring(fjSpan.html().indexOf("</i>") + 4,
							fjSpan.html().indexOf("<b>")).trim();

			fjName = dealAnnotationHtml(fjName);
			fjName = regexColon(fjName);

			foodSafetyStandard.setAttachName(fjName);
		}
		
		// 公告
		Elements ggDiv = nrDiv.select("div.list_cont_list");
		String gg = ggDiv.select("span").html().trim();
		gg = dealAnnotationHtml(gg);
		gg = regexColon(gg);

		// 代替
		Elements dtDiv = nrDiv.select("div.contents_yy .contents_yy_cont");
		String dt = dtDiv.select("span").get(0).html().trim();
		dt = dealAnnotationHtml(dt);
		dt = regexColon(dt);

		// 引用
		Elements yyDiv = nrDiv.select("div.contents_cy .contents_yy_cont");
		String yy = yyDiv.select("span").get(0).html().trim();
		yy = dealAnnotationHtml(yy);
		yy = regexColon(yy);
		
		return foodSafetyStandard;
	}
	
	/**
	 * 截取冒号后面的值
	 * @param str
	 * @return
	 * @throws Exception
	 */
	private String regexColon(String str) throws Exception {
		String regex = ".+?：(.*?)";
		
		Object obj = AnalyzeUtil.regex(str, regex);
		if(null == obj) {
			return str;
		}
		
		return obj.toString() + "";
	}

	/**
	 * 解析食品安全国家标准列表页面
	 */
	@Override
	public Collection<HttpSeed> findSeed(Collection<HttpSeed> seeds)
			throws Exception {
		if(null == seeds || seeds.isEmpty()) {
			return null;
		}
		
		Collection<HttpSeed> seedGroups = new ArrayList<HttpSeed>();
		
		for (HttpSeed seed : seeds) {
			Document doc = Jsoup.parse(seed.getHtml());
			
			// 列表div
			Elements listDiv = doc.select("div.tab_table");

			// 列表table
			Elements listTable = listDiv.select("table.table_list");

			// 列表tr
			Elements listTr = listTable.select("tr");

			// 遍历TR, 第一个TR是表头，不存具体数据
			for(int i = 1; i < listTr.size(); i++) {
				HttpSeed result = this.analyzeTr(listTr.get(i));
				
				if(null != result) {
					seedGroups.add(result);
				}
			}
		}
		
		return seedGroups;
	}
	
	
	/**
	 * 解析表格TR
	 * 
	 * @param element
	 * @throws Exception
	 */
	private HttpSeed analyzeTr(Element element) throws Exception {
		// 内容TD
		Elements td_elements = element.select("td");
		
		// 获取第1个TD，即查看的TD
		Element tdElement = td_elements.get(1);
		
		return dealATag(tdElement.select("a"));
	}
	
	/**
	 * 处理注释的HTML内容
	 * 
	 * @param tdHtml
	 * @return
	 */
	private String dealAnnotationHtml(String tdHtml) throws Exception {
		tdHtml = tdHtml.replace("\t", "").replace("\n", "").replace("\r", "");
		
		String start_tag = "<!--";
		String end_tag = "-->";

		int startIndex = tdHtml.indexOf(start_tag);

		if (startIndex != -1) {
			int endIndex = tdHtml.indexOf(end_tag);

			String annotationHtml = tdHtml.substring(startIndex, endIndex
					+ end_tag.length());

			tdHtml = tdHtml.replace(annotationHtml, "");
			startIndex = tdHtml.indexOf(start_tag);

			if (startIndex != -1) {
				// 递归截取注释的字符串
				tdHtml = dealAnnotationHtml(tdHtml);
			}
		}

		return tdHtml;
	}
	
	/**
	 * 解析A标签
	 * 
	 * @param aHtml
	 * @return
	 */
	private HttpSeed dealATag(Elements aElement)
			throws Exception {
		// 截取正则表达式
		String regex = ".+?'(.+?)',.+?";
		
		Object result = AnalyzeUtil.regex(aElement.attr("onclick"), regex);
		if(null == result) {
			return null;
		}
		
		return initDetailHttpSeed(result.toString());
	}
	
	/**
	 * 初始化HttpSeed
	 * 
	 * @return
	 * @throws Exception
	 */
	private HttpSeed initDetailHttpSeed(String idStr) throws Exception {
		HttpSeed httpSeed = new HttpSeed();

		String url = String.format(STANDARD_DETAIL_URL, idStr);

		httpSeed.setUrl(url);
		httpSeed.setCreateTime(System.currentTimeMillis());
		// 解析页面内容
		httpSeed.setResolveTypes(new HashSet<String>());
		httpSeed.getResolveTypes().add(HttpSeed.RESOLVETYPE_RESULT);

		return httpSeed;
	}

	/**
	 * 没有分页，不需实现
	 */
	@Override
	public Collection<HttpSeed> findPageSeed(Collection<HttpSeed> seeds)
			throws Exception {
		return null;
	}

	/**
	 * 插入错误数据
	 */
	@Override
	public void error(Map<String, Collection<HttpSeed>> seeds) throws Exception {
		
	}
}
