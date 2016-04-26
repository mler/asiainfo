package com.bdx.rainbow.service.jc.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.common.util.HttpClientUtil;
import com.bdx.rainbow.entity.jc.Pcsdn;
import com.bdx.rainbow.entity.jc.PcsdnExample;
import com.bdx.rainbow.entity.jc.PcsdnExample.Criteria;
import com.bdx.rainbow.entity.jc.Pgds;
import com.bdx.rainbow.entity.jc.ProductorGds;
import com.bdx.rainbow.mapper.jc.PcsdnMapper;
import com.bdx.rainbow.mapper.jc.PgdsMapper;
import com.bdx.rainbow.mapper.jc.ProductorGdsMapper;
import com.bdx.rainbow.service.jc.IProductAnalyzeService;
import com.bdx.rainbow.service.jc.ISkuService;
//import com.gargoylesoftware.htmlunit.BrowserVersion;
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Service
@Transactional
public class ProductAnalyzeService implements IProductAnalyzeService {

	@Autowired
	private PcsdnMapper pcsdnMapper;
	
	@Autowired
	private PgdsMapper pgdsMapper;
	
	@Autowired
	private ProductorGdsMapper productorGdsMapper;
	
	private int timeout = 10000;
	
	private final static String  SUPPLIER_URL = "http://www.anccnet.com/info_search/factory/Detail.aspx";
	
	private final static String  GOODS_URL = "http://www.anccnet.com/goods.aspx";
	
	public void gdsAnalyze(String barcode,Map<String,String> contentMap,String userid) throws Exception
	{
		
		if(StringUtil.isBlank(barcode) )
			throw new Exception("解析参数有误，请检查");
		
		if(contentMap == null || contentMap.isEmpty())
			throw new Exception("未找到二维码["+barcode+"]相关信息");
		
		Pgds goods = null;ProductorGds supplier = null;
	
		for(String key:contentMap.keySet())
		{
			if(key.startsWith(GOODS_URL))
			{
				goods = gdsAnalyzeByHtml(barcode, contentMap.get(key));
				goods.setUpdateUrl(key);
			}
			else if(key.startsWith(SUPPLIER_URL))
			{
				supplier = gdsAnalyzeByHtml(contentMap.get(key));
				supplier.setUpdateUrl(key);
			}
		}
		
		if(supplier != null)
		{
			if(goods != null)
			{
				if(StringUtils.isBlank(goods.getProductor()))
					goods.setProductor(supplier.getName());
				
				goods.setProductCode(supplier.getpCode());
			}
			
			ProductorGds old = productorGdsMapper.selectByPrimaryKey(supplier.getpCode());
			if(old == null)
				productorGdsMapper.insertSelective(supplier);
			else
				productorGdsMapper.updateByPrimaryKeySelective(supplier);
		}
		
		if(goods != null)
		{
			Pgds old = pgdsMapper.selectByPrimaryKey(barcode);
			if(old == null)
				pgdsMapper.insertSelective(goods);
			else 
				pgdsMapper.updateByPrimaryKeySelective(goods);
			
			//如果在pgds和pcsdn同时存在的话，将pcsdn的gds标记为设置为1
			PcsdnExample where = new PcsdnExample();
			Criteria c = where.createCriteria();
			c.andBarcodeEqualTo(barcode);
			Pcsdn update = new Pcsdn();
			update.setGdsFlag("1");
			pcsdnMapper.updateByExampleSelective(update, where);
			
		}
		
	}
	
	private Pgds gdsAnalyzeByHtml(String barcode,String html) throws Exception
	{
		if(StringUtil.isBlank(barcode) || StringUtil.isBlank(html))
			throw new Exception("商品信息有误，解析失败");
		
        String script = html.substring(html.indexOf("SetValue('Att"), html.indexOf("delNullRow();}"));

        script = script.replaceAll("&nbsp;", "");

        String[] arr = script.split(";");
        for (String s : arr) {
            if (s.trim().length() != 0) {
                System.out.println(s);
                String key = s.substring(s.indexOf("Att"), s.indexOf(",") - 1);
                String value = s.substring((s.indexOf(",'") + 2), s.indexOf("')"));

                html = html.replaceAll("<span id=\"" + key + "\"></span>", value);
            }
        }
		
		Document doc = Jsoup.parse(html,"gbk");
		
		Elements tr_elements = doc.select("table#productsInfo > tbody:eq(0) > tr");
		
		Map<String,String> pMap= new HashMap<String, String>();
		
		for(Element e : tr_elements)
		{
			Elements td = e.select("td");
			System.out.println(td.get(0).html()+":"+td.get(1).text());
			pMap.put(td.get(0).html().trim(),td.get(1).text().trim());
		}
		
		
		Pgds p = new Pgds();
		p.setBarcode(barcode);
		p.setName(pMap.get("产品名称"));
		p.setEname(pMap.get("产品名称(英文)"));
		p.setUnspsc(pMap.get("UNSPSC分类"));
		p.setBrand(pMap.get("品牌名称"));
		p.setSpec(pMap.get("规格型号"));
		p.setOrgiCountry(pMap.get("原产国"));
		p.setProductArea(pMap.get("产地"));
		p.setCodeType(pMap.get("条码类型"));
		p.setComposition(pMap.get("配料"));
		p.setDeep(pMap.get("深度"));
		p.setGreenfoodCode(pMap.get("绿色食品证号"));
		p.setHealthfoodCode(pMap.get("保健食品证号"));
		p.setHealthPermit(pMap.get("卫生许可证号"));
		p.setHeigt(pMap.get("高度"));
		p.setPackMat(pMap.get("包装材料"));
		p.setProductor(pMap.get(""));
		p.setProductPermit(pMap.get("生产许可证号"));
		p.setProStandard(pMap.get("产品执行标准代号"));
		p.setpType(pMap.get("产品所处层级"));
		p.setQsType(pMap.get("QS分类"));
		p.setValidDate(pMap.get("保质期(天)"));
		p.setWidth(pMap.get("宽度"));
		
//		pgdsMapper.insert(p);
		
		return p;
	}
	
	private ProductorGds gdsAnalyzeByHtml(String html) throws Exception
	{
		if(StringUtil.isBlank(html))
			throw new Exception("生产厂商信息有误，解析失败");
		
		Document doc = Jsoup.parse(html,"gbk");
		
		Elements tr_elements = doc.select("div#mainDiv > table:eq(0) > tbody:eq(0) > tr");
		
		Map<String,String> pMap= new HashMap<String, String>();
		
		for(Element e : tr_elements)
		{
			Elements td = e.select("td");
			String key = td.get(0).html().replaceAll(":", "").replaceAll("：", "");
			System.out.println(td.get(0).html()+":"+td.get(1).text().trim());
			pMap.put(key.trim(),td.get(1).text().trim());
		}
		
		ProductorGds p = new ProductorGds();
		p.setAddress(pMap.get("注册地址（中文）"));
		p.setEaddress(pMap.get("注册地址（英文）"));
		p.setEname(pMap.get("企业名称（英文）"));
		p.setName(pMap.get("企业名称（中文）"));
		p.setpCode(pMap.get("厂商识别代码"));
		p.setPostcode(pMap.get("注册地址邮政编码"));
		
		return p;
	}
	
	/**
	 * gds网站通过条形码搜索的首页面解析
	 * 解析出商品详细信息和厂家信息的详细页面内容
	 */
	private void gdsAnalyze(String url) throws Exception
	{
		Map<String,String> resultMap = new HashMap<String, String>(0);
		
		Document doc = null;
        Connection conn = Jsoup.connect(url);
        conn.timeout(timeout);
        conn.header("Host", "search.anccnet.com");
        conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.header("Accept-Encoding", "gzip,deflate,sdch");
        conn.header("Proxy-Connection", "keep-alive");
        conn.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");
        conn.referrer(url);

        doc = conn.get();

        Elements dls = doc.getElementsByTag("dl");

        for (Element dl : dls) {
            
            Elements dts = dl.getElementsByTag("dt");
            Elements dds = dl.getElementsByTag("dd");

            for (int i = 0; i < dts.size(); i++) {
                if (dts.get(i).text().contains("商品条码") || dts.get(i).text().contains("发布厂家")) {
                    String gdsUrl = dds.get(i).select("a").attr("href");
                    resultMap.put(gdsUrl, Jsoup.connect(gdsUrl).timeout(timeout).post().html());
                }
            }
        }
	}
	
	public List<Pcsdn> csdnAnalyzeByDoc(File file) throws Exception
	{
		File file1 = new File("/Users/mler/Desktop/preduct.bak");
		
		InputStreamReader in = new InputStreamReader(new FileInputStream(file1),"gbk");
		
		BufferedReader buff_in = new BufferedReader(in);
		
		List<Pcsdn> plist = new ArrayList<Pcsdn>(0);
		
		String line = buff_in.readLine();
		
		int batch_num = 0;
		
		while((line = buff_in.readLine()) != null)
		{
			//"item_no"	"item_name"	"pym"	"item_size"	"unit_no"	"product_area"
			batch_num++;
			Pcsdn p = new Pcsdn();
			try{
				String[] parts = line.split("\\\t");
				p.setBarcode(parts[0].replaceAll("\"", ""));
				p.setName(parts[1].replaceAll("\"", ""));
				p.setPtype(parts[2].replaceAll("\"", ""));
				p.setSpec(parts[3].replaceAll("\"", ""));
				p.setUnit(parts[4].replaceAll("\"", ""));
				p.setArea(parts[5].replaceAll("\"", ""));
				pcsdnMapper.insert(p);
				System.out.println("["+batch_num+"]:SUCCESS");
			}
			catch(Exception e)
			{
				System.out.println("["+batch_num+"]:ERROR:"+line);
				plist.add(p);
				e.printStackTrace();
			}
//			plist.add(p);
			
//			batch_num++;
//			
//			if(batch_num == 2000)
//			{
//				pcsdnMapper.insertBatch(plist);
//				batch_num = 0;
//				plist.clear();
//			}
		}
		
		buff_in.close();
		
		in.close();
		
		if(plist.isEmpty() == false)
		{
			for(Pcsdn p:plist)
				System.out.println(p.getBarcode());
		}
//			pcsdnMapper.insertBatch(plist);
		
		return null;
	}
	
	
//	private Pgds findByHtmlUnit(String barcode,String url) throws Exception
//	{
//		if(StringUtil.isBlank(url))
//			throw new Exception("URL 为空");
//		/** HtmlUnit请求web页面 */  
//	    WebClient wc = new WebClient(BrowserVersion.CHROME);  
//	    wc.getOptions().setUseInsecureSSL(true);  
//	    wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true  
//	    wc.getOptions().setCssEnabled(false); // 禁用css支持  
//	    wc.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常  
//	    wc.getOptions().setTimeout(100000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待  
//	    wc.getOptions().setDoNotTrackEnabled(false);
////	    ProxyConfig proxyConfig = new ProxyConfig();
////	    proxyConfig.setProxyHost("10.10.19.78");
////	    proxyConfig.setProxyPort(8080);
////	    proxyConfig.setSocksProxy(false);
////	    wc.getOptions().setProxyConfig(proxyConfig);
//	    HtmlPage page = wc.getPage(url);  
//	  
//	    String html = page.asXml();
//	    
//		return gdsAnalyzeByHtml(barcode, html);
//	}
	
	public final static void main(String[] args) throws Exception
	{
		String url = "http://localhost:8080/rest";
		
		
		
		Map<String,String> param = new HashMap<String, String>(1);
//		param.put("msg", "")
		
		
		String html = HttpClientUtil.getStringResponse(url, null, null, null, "gbk");
		
		String a = html.substring(html.indexOf("SetValue('Att_Sys_zh-cn_101_G'"),html.indexOf("delNullRow();}"));
		
		System.out.println(a);
		
		String[] parts = a.split("\\n");
		
		Map<String,String> valueMap = new HashMap<String, String>(0);
		
		for(String p :parts)
		{
			System.out.println("[test]"+p);
			p = p.replaceAll("SetValue\\(\\'", "").replaceAll("\\'\\);", "");
			
			String[] pp = p.split("\\',\\'");
			if(pp.length >= 2)
			{
				
				valueMap.put(pp[0], pp[1]);
			}
		}
		
		System.out.println("===========================");
		
		for(String key : valueMap.keySet())
			System.out.println(key+":"+valueMap.get(key));
		
		
//		System.out.println(a);
		
		/**
		Document doc = Jsoup.parse(html);
		
		Elements tr_elements = doc.select("table#productsInfo > tbody:eq(0) > tr");
		
		Map<String,String> pMap= new HashMap<String, String>();
		
		for(Element e : tr_elements)
		{
			Elements td = e.select("td");
			System.out.println(td.get(0).html()+":"+td.get(1).text());
			pMap.put(td.get(0).html().trim(),td.get(1).text().trim());
		}
		
		
		Pgds p = new Pgds();
//		p.setBarcode(barcode);
		p.setName(pMap.get("产品名称"));
		p.setEname(pMap.get("产品名称(英文)"));
		p.setUnspsc(pMap.get("UNSPSC分类"));
		p.setBrand(pMap.get("品牌名称"));
		p.setSpec(pMap.get("规格型号"));
		p.setOrgiCountry(pMap.get("原产国"));
		p.setProductArea(pMap.get("产地"));
		p.setCodeType(pMap.get("条码类型"));
		p.setComposition(pMap.get("配料"));
		p.setDeep(pMap.get("深度"));
		p.setGreenfoodCode(pMap.get("绿色食品证号"));
		p.setHealthfoodCode(pMap.get("保健食品证号"));
		p.setHealthPermit(pMap.get("卫生许可证号"));
		p.setHeigt(pMap.get("高度"));
		p.setPackMat(pMap.get("包装材料"));
		p.setProductor(pMap.get(""));
		p.setProductPermit(pMap.get("生产许可证号"));
		p.setProStandard(pMap.get("产品执行标准代号"));
		p.setpType(pMap.get("产品所处层级"));
		p.setQsType(pMap.get("QS分类"));
		p.setValidDate(pMap.get("保质期(天)"));
		p.setWidth(pMap.get("宽度"));
		
//		pgdsMapper.insert(p);
 		*/
	}
}
