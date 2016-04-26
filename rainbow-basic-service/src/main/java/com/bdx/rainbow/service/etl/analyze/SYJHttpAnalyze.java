package com.bdx.rainbow.service.etl.analyze;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdx.rainbow.entity.etl.OrgiPage;
import com.bdx.rainbow.entity.etl.SyjTableBean;
import com.bdx.rainbow.etl.analyze.Analyze;
import com.bdx.rainbow.etl.entity.seed.HttpSeed;
import com.bdx.rainbow.mapper.etl.SyjTableBeanMapper;
import com.bdx.rainbow.service.etl.IOrgiPageService;
import com.bdx.rainbow.service.etl.util.AnalyzeUtil;
import com.bdx.rainbow.service.etl.util.SpringBeanFactory;

/**
 * HTML解析器
 * 
 * @author Administrator
 * 
 */
public class SYJHttpAnalyze implements Analyze<HttpSeed, HttpSeed, Collection<Object>> {

	private final Logger logger = LoggerFactory.getLogger(SYJHttpAnalyze.class);
	
	/**
	 * 数据抓取请求域名
	 */
	public static final String DOMAIN = "http://app1.sfda.gov.cn/datasearch/face3/";

	/**
	 * 实体类属性前缀
	 */
	private static final String PREFIX_ATTRIBUTE = "setTd";

	/**
	 * Entity类路径前缀
	 */
	private static final String PREFIX_ENTITY_PATH = "com.bdx.rainbow.entity.etl.";

	/**
	 * 数据库入库执行方法名
	 */
	public static final String INSERT_METHOD = "insertBatch";

	/**
	 * 映射对象
	 */
	private SyjTableBean syjTableBean;
	
	/**
	 * 映射表DAO执行对象
	 */
	private SyjTableBeanMapper syjTableBeanMapper;

	/**
	 * 线程同步锁
	 */
	private String correntLock = "correntLock";
	
	/**
	 * 正确数量
	 */
	private long corrent = 0l;

	/**
	 * 线程同步锁
	 */
	private String errorLock = "errorLock";
	
	/**
	 * 错误数量
	 */
	private long error = 0l;

	public SYJHttpAnalyze() {
		this.syjTableBeanMapper = (SyjTableBeanMapper) SpringBeanFactory.getSpringBean("syjTableBeanMapper");
	}
	
	/**
	 * 解析详情页
	 * 
	 * @param document
	 * @return
	 * @throws Exception
	 */
	private Object analyzeLicenseDetail(HttpSeed seed) throws Exception {
		
		Document doc = parse(seed.getHtml());

		Elements eleTable = doc.select(".listmain table");
		// 所有的TR
		Elements eleTrs = eleTable.get(0).select("tr");

		// 初始化实体类
		Object entity = AnalyzeUtil.getInstant(PREFIX_ENTITY_PATH + syjTableBean.getTableClass());

		// 第一行tr无效，每个tr的第二个td是实际数据
		int rowNo = 1;
		for (int i = 0; i < eleTrs.size(); i++) {
			Element eleTr = eleTrs.get(i);

			// 如果不是最后一行，则判断tr的第一个td是否包含属性nowrap，并且nowrap是否为true
			if (i != eleTrs.size() - 1 && (!eleTr.select("td").get(0).hasAttr("nowrap")
					|| !"true".equals(eleTr.select("td").get(0).attr("nowrap")))) {
				continue;
			}

			// td抓取内容
			String tdVal = parseDetailTr(eleTr);

			// TABLE74的第11个字段，不插入
			if (syjTableBean.getTableClass().equals("TABLE74") && rowNo == 11) {
				continue;
			}

			// 设置entity属性
			AnalyzeUtil.executeMethod(entity, PREFIX_ATTRIBUTE + rowNo++, new Object[] { tdVal },
					new Class[] { String.class });
		}

		// 插入ID, 暂时插入到createEmpCode字段
		String regex = ".+?&Id=(.+?)";
		Object obj = AnalyzeUtil.regex(seed.getUrl(), regex);

		if (null == obj) {
			// 设置ID
			AnalyzeUtil.executeMethod(entity, "setContentId", new Object[] { 0l }, new Class[] { Long.class });
		} else {
			// 设置ID
			AnalyzeUtil.executeMethod(entity, "setContentId", new Object[] { Long.valueOf(obj.toString()) }, new Class[] { Long.class });
		}

		// 插入创建时间
		AnalyzeUtil.executeMethod(entity, "setCreateTime", new Object[] { new Timestamp(new Date().getTime()) },
				new Class[] { Timestamp.class });

		return entity;
	}

	/**
	 * 解析详情页解析
	 */
	@Override
	public Collection<Object> findResult(Collection<HttpSeed> seeds) throws Exception {
		Collection<Object> pages = new ArrayList<Object>();
		if (CollectionUtils.isEmpty(seeds)) {
			return null;
		}

		for (HttpSeed seed : seeds) {
			// 解析详情页数据
			Object t = analyzeLicenseDetail(seed);

			pages.add(t);
		}

		Object tableService = SpringBeanFactory.getSpringBean("tableService");

		try {
			// 数据入库
			AnalyzeUtil.executeMethod(tableService, INSERT_METHOD,
					new Object[] { pages, syjTableBean.getTableClass() + "Mapper" },
					new Class[] { List.class, String.class });

			// 统计正确入库总数
			synchronized (correntLock) {
				syjTableBean.setCorrect(syjTableBean.getCorrect() + pages.size());
				
				corrent += pages.size();
				
				// 每2000条数据库更新一次
				if(corrent > 2000) {
					try {
						syjTableBeanMapper.updateByPrimaryKey(syjTableBean);
					} catch (Exception e) {
					}
					corrent = 0l;
					
					logger.info("[" + syjTableBean.getTitle() + "]入库数量[" + syjTableBean.getCorrect() + "]/总数[" + syjTableBean.getTotal() + "]");
				}
			}

		} catch (Exception e) {
			throw new Exception(e);
		}

		return pages;
	}

	/**
	 * 解析列表页面,页表数据
	 */
	@Override
	public Collection<HttpSeed> findSeed(Collection<HttpSeed> seeds) throws Exception {

		if (CollectionUtils.isEmpty(seeds)) {
			return null;
		}

		Collection<HttpSeed> seedGroups = new ArrayList<HttpSeed>();

		// 解析HTML中A标签链接
		for (HttpSeed seed : seeds) {
			Document doc = parse(seed.getHtml());

			Elements drug_elements = doc.select("a[href]");

			if (drug_elements.isEmpty()) {
				return null;
			}

			for (Element drug_e : drug_elements) {
				String href_string = drug_e.attr("href");
				String uri = href_string.substring(href_string.indexOf("'") + 1, href_string.lastIndexOf("'"));

				if (StringUtils.isBlank(uri)) {
					continue;
				}

				seedGroups.add(initDetailHttpSeed(DOMAIN + uri));
			}
		}

		return seedGroups;
	}

	/**
	 * 解析列表页面分页参数
	 */
	@Override
	public Collection<HttpSeed> findPageSeed(Collection<HttpSeed> seeds) throws Exception {

		if (CollectionUtils.isEmpty(seeds)) {
			return null;
		}

		Collection<HttpSeed> seedGroups = new ArrayList<HttpSeed>();

		for (HttpSeed seed : seeds) {
			Document doc = parse(seed.getHtml());

			// 获取列表页URL请求链接
			Elements page_form_elements = doc.select("#pageForm");
			if (page_form_elements.isEmpty()) {
				return null;
			}

			Element page_form_e = page_form_elements.get(0);
			// 请求URL链接
			String url = DOMAIN + page_form_e.attr("action");
			Elements param_elements = page_form_e.select("input");

			// 获得总页数
			int totalPageNum = this.getTotalPageNum(doc);

			for (int pageNo = 1; pageNo <= totalPageNum; pageNo++) {

				// 设置参数
				Map<String, String> params = new HashMap<String, String>();
				for (Element param_e : param_elements) {
					params.put(param_e.attr("name"), param_e.attr("value"));
				}
				// 列表页数
				params.put("curstart", String.valueOf(pageNo));

				HttpSeed httpSeed = this.initListHttpSeed(url, params);

				seedGroups.add(httpSeed);
			}
		}

		return seedGroups;
	}

	/**
	 * 插入错误数据
	 */
	@Override
	public void error(Map<String, Collection<HttpSeed>> seeds) throws Exception {
		IOrgiPageService orgiPageService = (IOrgiPageService) SpringBeanFactory.getSpringBean("orgiPageService");

		for (Entry<String, Collection<HttpSeed>> entry : seeds.entrySet()) {
			List<HttpSeed> list = new ArrayList<HttpSeed>(entry.getValue());

			List<OrgiPage> orgiList = new ArrayList<OrgiPage>();
			for (HttpSeed seed : list) {
				OrgiPage orgiPage = new OrgiPage();
				orgiPage.setContent(seed.getHtml());
				orgiPage.setResolveType(entry.getKey());
				orgiPage.setClassName(syjTableBean.getTableClass());
				orgiPage.setStatus((short) 0);
				orgiPage.setUpdateDate(new Timestamp(new Date().getTime()));

				orgiList.add(orgiPage);
			}

			orgiPageService.saveBatch(orgiList);
			
			// 统计正确入库总数
			synchronized (errorLock) {
				syjTableBean.setError(syjTableBean.getError() + orgiList.size());
				
				error += orgiList.size();
				
				if(error >= 2000) {
					try {
						syjTableBeanMapper.updateByPrimaryKey(syjTableBean);
					} catch(Exception e) {
					}
					error = 0l;
					
					logger.info("[" + syjTableBean.getTitle() + "]错误入库数量[" + syjTableBean.getError() + "]/总数[" + syjTableBean.getTotal() + "]");
				}
			}
		}
	}

	/**
	 * 初始化HttpSeed
	 * 
	 * @return
	 * @throws Exception
	 */
	private HttpSeed initDetailHttpSeed(String url) throws Exception {
		HttpSeed httpSeed = new HttpSeed();

		httpSeed.setUrl(url);
		httpSeed.setCreateTime(System.currentTimeMillis());
		// 解析页面内容
		httpSeed.setResolveTypes(new HashSet<String>());
		httpSeed.getResolveTypes().add(HttpSeed.RESOLVETYPE_RESULT);

		return httpSeed;
	}

	/**
	 * 获取总页数
	 * 
	 * @throws Exception
	 */
	private int getTotalPageNum(Document document) throws Exception {
		// 页面第四个table，表示分页信息
		Element pageTable = document.select("table").get(4);

		String pageHtml = pageTable.select("tr td").get(0).html();

		// 截取正则表达式
		String regex = ".+?共(.+?)页.+?";

		Object result = AnalyzeUtil.regex(pageHtml, regex);
		if (null == result) {
			return 0;
		}

		int totalPageNum = Integer.parseInt(result.toString());

		return totalPageNum;
	}

	/**
	 * 初始化HttpSeed
	 * 
	 * @return
	 * @throws Exception
	 */
	private HttpSeed initListHttpSeed(String url, Map<String, String> params) throws Exception {
		HttpSeed httpSeed = new HttpSeed();

		httpSeed.setUrl(url);
		httpSeed.setParam(params);
		httpSeed.setCreateTime(System.currentTimeMillis());
		// 解析页面内容
		httpSeed.setResolveTypes(new HashSet<String>());
		httpSeed.getResolveTypes().add(HttpSeed.RESOLVETYPE_SEED_COMMON);

		return httpSeed;
	}

	/**
	 * 把HTML字符串转换为Document对象
	 * 
	 * @param html
	 * @return
	 * @throws Exception
	 */
	private Document parse(String html) throws Exception {
		return Jsoup.parse(html, "UTF-8");
	}

	/**
	 * 抓取详细界面数据
	 * 
	 * @param eleTrs
	 * @param rowNo
	 * @return
	 */
	private String parseDetailTr(Element eleTr) throws Exception {
		Element eleTd = eleTr.select("td").get(1);

		// 如果td下，还有标签，则去下一层标签里的内容
		if (eleTd.children().size() > 0) {
			return eleTd.child(0).html();
		} else {
			return eleTd.html().trim();
		}
	}

	/**
	 * @return the syjTableBean
	 */
	public SyjTableBean getSyjTableBean() {
		return syjTableBean;
	}

	/**
	 * @param syjTableBean
	 *            the syjTableBean to set
	 */
	public void setSyjTableBean(SyjTableBean syjTableBean) {
		this.syjTableBean = syjTableBean;
	}
}
