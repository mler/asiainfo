package com.bdx.rainbow.etl.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bdx.rainbow.common.configuration.HttpClientConfig;
import com.bdx.rainbow.common.util.HttpClientETL;
import com.bdx.rainbow.common.util.HttpClientUtil;
import com.bdx.rainbow.etl.entity.seed.HttpSeed;

public class SYJTableCreatUtil {

	private static String SYJdomain = "http://app1.sfda.gov.cn/datasearch/face3/";

	// 抓取的table序号
	private static Set<Integer> needTableSet = new HashSet<Integer>();
	static {
		needTableSet.add(26);
		needTableSet.add(28);
		needTableSet.add(30);
		needTableSet.add(32);
		needTableSet.add(34);
		needTableSet.add(36);
		needTableSet.add(38);
		needTableSet.add(40);
	}
	// A标签
	private static Set<String> needTableASet = new LinkedHashSet<String>();
	


	private Set<String> getTotalTableUrl() {
		try {
			List<String> list = new ArrayList<String>();
			String dirHtml = HttpClientETL.getStringResponse(SYJdomain
					+ "dir.html", null, null, null);
			Document dirDoc = Jsoup.parse(dirHtml, "gb2312");
			Elements dirElements_table = dirDoc.select("table");
			for (int i = 0; i < dirElements_table.size(); i++) {
				if (needTableSet.contains(i)) {
					Elements links = dirElements_table.get(i).getElementsByTag(
							"a");
				
					
					for (Element link : links) {
						Elements pageElements_count = link.select("font.new_gray");
						String count=pageElements_count.get(0).text().substring(1,pageElements_count.get(0).text().length()-1);
						needTableASet.add(count+"||"+link.attr("href"));
						list.add(count+"||"+link.attr("href"));
					}

				}

			}
			System.out.println(list.size());
			System.out.println(needTableASet.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return needTableASet;
	}

	private SYJTableBean getSingleTablePageUrl(String content) {
		// base.jsp?tableId=91&amp;tableName=TABLE91&amp;title=食品生产许可获证企业&amp;bcId=137413698768984683499699272988
		String tableId = "";
		String tableName = "";
		String title = "";
		String bcId = "";
		Pattern p = Pattern.compile("\\=(.*?)\\&");
		Matcher m = p.matcher(content);
		int i = 0;
		while (m.find()) {
			i++;
			if (i == 1)
				tableId = m.group(1);
			if (i == 2)
				tableName = m.group(1);
			if (i == 3)
				title = m.group(1);
		}
		bcId = content.substring(content.lastIndexOf("bcId=") + 5,
				content.length());
		// search.jsp?tableId=91&tableName=TABLE91&bcId=137413698768984683499699272988&curstart=1
		String searchPageUrl = "search.jsp?tableId=" + tableId + "&tableName="
				+ tableName + "&bcId=" + bcId + "&curstart=1";
		SYJTableBean syjTableBean = new SYJTableBean();
		syjTableBean.setBcId(bcId);
		syjTableBean.setTableId(tableId);
		syjTableBean.setTitle(title);
		syjTableBean.setTableName(tableName);
		syjTableBean.setTableClass(tableName);
		syjTableBean.setSearchPageUrl(searchPageUrl);
		System.out.println(content.split("\\|\\|")[0]);
		syjTableBean.setTotal(Integer.parseInt(content.split("\\|\\|")[0]));
		return syjTableBean;
	}

	private SYJTableBean getSingleTableDetailUrl(SYJTableBean syjTableBean,
			String pageHtml) {
		try {
		
			Document pageDoc = Jsoup.parse(pageHtml, "gb2312");
			Elements pageElements_a = pageDoc.select("a[href]");

			String href_string = pageElements_a.get(0).attr("href");
		
			// javascript:commitForECMA(callbackC,'content.jsp?tableId=91&tableName=TABLE91&tableView=食品生产许可获证企业&Id=667',null)
			String detailId = href_string.substring(
					href_string.indexOf("&Id=") + 4,
					href_string.lastIndexOf("'"));
			syjTableBean.setDetailId(detailId);
			syjTableBean.setSearchDetailUrl("content.jsp?tableId="
					+ syjTableBean.getTableId() + "&tableName="
					+ syjTableBean.getTableName() + "&Id="
					+ syjTableBean.getDetailId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return syjTableBean;
	}

	private SYJAutoSQLBean creatSql(String detailHtml, SYJTableBean syjTableBean) {
		Map<String, String> columnMap = new LinkedHashMap<String, String>();
		Document detailDoc = Jsoup.parse(detailHtml, "gb2312");

		Elements detailElements_td = detailDoc.select("td[nowrap=true]");
		for (int i = 0; i < detailElements_td.size(); i++) {
			columnMap.put("td" + (i + 1), detailElements_td.get(i).text());
		}
		columnMap.put("td" + (detailElements_td.size() + 1), "注");

		// String zhu_elements_tr = detailDoc
		// .select("table").get(0).getElementsByTag("tr").last().getElementsByTag("td").first().text();

		SYJAutoSQLBean bean = new SYJAutoSQLBean();
		bean.setTableName(syjTableBean.getTableName());
		bean.setTableComment(syjTableBean.getTitle());
		bean.setColumnMap(columnMap);
		return bean;
	}

	public List<SYJAutoSQLBean> getSqlBean() {
		
	
	
		List<SYJAutoSQLBean> list = new ArrayList<SYJAutoSQLBean>();
		SYJTableCreatUtil syjTableCreat = new SYJTableCreatUtil();
		// http://app1.sfda.gov.cn/datasearch/face3/dir.html
		try {
			needTableASet = syjTableCreat.getTotalTableUrl();
			for (String content : needTableASet) {
				SYJTableBean syjTableBean = syjTableCreat
						.getSingleTablePageUrl(content);

				String pageHtml = HttpClientUtil.getStringResponseByProxy(
						SYJdomain + syjTableBean.getSearchPageUrl(), null,
						null, null, "hzproxy.asiainfo.com", 8080, "gb2312");

				syjTableBean = syjTableCreat.getSingleTableDetailUrl(
						syjTableBean, pageHtml);

				String detailHtml = HttpClientUtil.getStringResponseByProxy(
						SYJdomain + syjTableBean.getSearchDetailUrl(), null,
						null, null, "hzproxy.asiainfo.com", 8080, "gb2312");
				SYJAutoSQLBean sqlBean = syjTableCreat.creatSql(detailHtml,
						syjTableBean);
				System.out.println(sqlBean.getCreatTableSql());
				list.add(sqlBean);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	

	public static void main(String[] args) throws Exception {
		
	
		List<SYJTableBean> list = new ArrayList<SYJTableBean>();
		SYJTableCreatUtil syjTableCreat = new SYJTableCreatUtil();
		// http://app1.sfda.gov.cn/datasearch/face3/dir.html
		try {
			needTableASet = syjTableCreat.getTotalTableUrl();
			for (String content : needTableASet) {
				SYJTableBean syjTableBean = syjTableCreat
						.getSingleTablePageUrl(content);

				list.add(syjTableBean);
			
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if(needTableASet.size()==list.size())
			{
			for(SYJTableBean syjTableBean :list)
			{
				System.out.println(syjTableBean.getInsertSql());
			}
			}
		
		
//		SYJTableCreatUtil util= new SYJTableCreatUtil();
//		List<SYJAutoSQLBean> list =util.getSqlBean();
//		System.out.println(list.size());
//		
//		if(needTableASet.size()==list.size())
//		{
//			System.out.println("----begin----");
//		for(SYJAutoSQLBean bean :list)
//		{
//			System.out.println(bean.getCreatTableSql());
//		}
//		System.out.println("----end----");
//		}
		
//		SYJTableCreatUtil util= new SYJTableCreatUtil();
//		util.getTotalTableUrl();
		

	}

}
