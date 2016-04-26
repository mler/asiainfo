package com.bdx.rainbow.service.basic.impl;

import java.util.HashMap;
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

import com.bdx.rainbow.basic.common.SkuConstant;
import com.bdx.rainbow.common.configuration.Constants;
import com.bdx.rainbow.entity.basic.mysql.TBasicPGds;
import com.bdx.rainbow.entity.basic.mysql.TBasicProductorGds;
import com.bdx.rainbow.entity.basic.mysql.TBasicSku;
import com.bdx.rainbow.mapper.basic.mysql.TBasicPGdsMapper;
import com.bdx.rainbow.mapper.basic.mysql.TBasicProductorGdsMapper;
import com.bdx.rainbow.service.basic.IBasicSkuService;
import com.bdx.rainbow.service.basic.ISkuAnalyzeService;

@Service
@Transactional(rollbackFor=Exception.class,value="transactionManager") 
public class SkuAnalyzeService implements ISkuAnalyzeService {

	
	@Autowired
	private TBasicPGdsMapper tBasicPGdsMapper;
	
	
	@Autowired
	private TBasicProductorGdsMapper tBasicProductorGdsMapper;
	
	@Autowired
	private IBasicSkuService basicSkuService;
	
	private int timeout = 10000;
	
	private final static String  SUPPLIER_URL = "http://www.anccnet.com/info_search/factory/Detail.aspx";
	
	private final static String  GOODS_URL = "http://www.anccnet.com/goods.aspx";
	
	public void gdsAnalyze(String barcode,Map<String,String> contentMap,String userLoginName) throws Exception
	{
		
		if(StringUtil.isBlank(barcode) )
			throw new Exception("解析参数有误，请检查");
		
		if(contentMap == null || contentMap.isEmpty())
			throw new Exception("未找到二维码["+barcode+"]相关信息");
		
		TBasicPGds goods = null;TBasicProductorGds supplier = null;
	
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
			
			TBasicProductorGds old = tBasicProductorGdsMapper.selectByPrimaryKey(supplier.getpCode());
			if(old == null)
				tBasicProductorGdsMapper.insertSelective(supplier);
			else
				tBasicProductorGdsMapper.updateByPrimaryKeySelective(supplier);
		}
		
		if(goods != null)
		{
			TBasicPGds old = tBasicPGdsMapper.selectByPrimaryKey(barcode);
			if(old == null)
				tBasicPGdsMapper.insertSelective(goods);
			else 
				tBasicPGdsMapper.updateByPrimaryKeySelective(goods);
			
			TBasicSku sku = new TBasicSku();
			changeGds2Sku(sku, goods, supplier);
			sku.setStatus(SkuConstant.STATUS_WAIT_FOR_AUDIT);
			basicSkuService.simpleSaveSkuByBarcode(sku,StringUtils.isEmpty(userLoginName)?Constants.UNKNOW_USER_ID.toString():userLoginName);
			
		}
		
		
	}
	
	private TBasicPGds gdsAnalyzeByHtml(String barcode,String html) throws Exception
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
		
		
		TBasicPGds p = new TBasicPGds();
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
		p.setHeight(pMap.get("高度"));
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
	
	private TBasicProductorGds gdsAnalyzeByHtml(String html) throws Exception
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
		
		TBasicProductorGds p = new TBasicProductorGds();
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
	
	
	private void changeGds2Sku( TBasicSku sku,TBasicPGds good,TBasicProductorGds supplier){
		
		if (good != null) {
			sku.setSkuBarcode(good.getBarcode());
			sku.setSkuName(good.getName());
			sku.setSkuEname(good.getEname());
			sku.setUnspsc(good.getUnspsc());
			sku.setBrand(good.getBrand());
			sku.setSpec(good.getSpec());
			sku.setOrgiCountry(good.getOrgiCountry());
			sku.setProductionArea(good.getProductArea());
			
			
//			good.getCodeType(pMap.get("条码类型"));
//			good.getComposition(pMap.get("配料"));
//			good.getDeep(pMap.get("深度"));
//			good.getGreenfoodCode(pMap.get("绿色食品证号"));
//			good.getHealthfoodCode(pMap.get("保健食品证号"));
//			good.getHealthPermit(pMap.get("卫生许可证号"));
//			good.getHeight(pMap.get("高度"));
//			good.getPackMat(pMap.get("包装材料"));
			sku.setCompanyName(good.getProductor());
//			good.getProductPermit(pMap.get("生产许可证号"));
			sku.setStandExeCode(good.getProStandard());
			
//			good.getpType(pMap.get("产品所处层级"));
//			good.getQsType(pMap.get("QS分类"));
			sku.setLifeTime(good.getValidDate());
//			good.getWidth(pMap.get("宽度"));
		}
			
		if (supplier != null) {
			sku.setRegisterAddress(supplier.getAddress());
			sku.setRegisterAddressEn(supplier.getEaddress());
			sku.setCompanyName(supplier.getName());
			sku.setCompanyNameEn(supplier.getEname());
			sku.setManufactoryCode(supplier.getpCode());
//				supplier.getPostcode(pMap.get("注册地址邮政编码"));
		}
			
		
	}
	
}
