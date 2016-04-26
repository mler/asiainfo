package com.bdx.rainbow.service.jc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.common.util.HttpClientUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.entity.jc.Drc;
import com.bdx.rainbow.entity.vfs.FileServerConfig;
import com.bdx.rainbow.mapper.etl.TABLE25Mapper;
import com.bdx.rainbow.mapper.jc.DrcMapper;
import com.bdx.rainbow.service.jc.IDrugService;
import com.bdx.rainbow.service.vfs.IFile;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class DrugService implements IDrugService {

	private final Logger logger = LoggerFactory.getLogger(DrugService.class);
	
	@Autowired
	private DrcMapper drcMapper;
	
	@Autowired
	private TABLE25Mapper table25Mapper;

	public void saveDrc(Drc record) throws Exception
	{
		drcMapper.insertSelective(record);
	}
	
	public Drc selectDrcByCode(String jgmCode) throws Exception
	{
		if(StringUtil.isBlank(jgmCode))
			throw new Exception("监管码不能为空");
		
		Drc drc = drcMapper.selectByPrimaryKey(jgmCode);
		
		return drc;
	}
	
	public Drc findDrcFromCFDA(String jgmCode) throws Exception
	{
		if(StringUtils.isBlank(jgmCode))
			throw new Exception("监管码为空");
		
		Drc drc = selectDrcByCode(jgmCode);
		if(drc != null)
			return drc;
		
		String url = "http://mobile.cfda.gov.cn/services/code/codeQueryCFDA?code="+jgmCode+"&phone=EC03E7D2-7AEF-46AE-AD0D-F6F0B1804A5A";
		
		logger.debug(url);
		
		Map<String,String> header = new HashMap<String,String>(0);
		
		String json = HttpClientUtil.getStringResponse(url, "get", header, null, null, "utf-8");
		
//		String json = HttpClientUtil.getStringResponseByProxy(url, "get", header, null, null, "10.10.10.78",8080,"utf-8");
		System.out.println("#########################");
		System.out.println("返回json："+json);
		System.out.println("#########################");
		
		if(StringUtils.isBlank(json) == false)
		{
			try{
				ObjectMapper mapper = new ObjectMapper();
				Map map = (Map)mapper.readValue(json, Map.class);
				Map infoMap = (Map)map.get("info");
				
				Integer retcode = (Integer)infoMap.get("retcode");
				
				if(infoMap !=null && retcode != null && retcode.intValue() == 0)
				{
					drc = new Drc();
					drc.setLicenseNumber(infoMap.get("license_number") ==null?"":infoMap.get("license_number").toString());
					drc.setLastTime(infoMap.get("last_time") ==null?"":infoMap.get("last_time").toString());
					drc.setFlow(infoMap.get("flow") ==null?"":infoMap.get("flow").toString());
					drc.setPkgSpec(infoMap.get("pkg_spec") ==null?"":infoMap.get("pkg_spec").toString());
					drc.setSaleTime(infoMap.get("sale_time") ==null?"":infoMap.get("sale_time").toString());
					drc.setTitle(infoMap.get("title") ==null?"":infoMap.get("title").toString());
					drc.setPrepnType(infoMap.get("prepn_type") ==null?"":infoMap.get("prepn_type").toString());
					drc.setPrepnUnit(infoMap.get("prepn_unit") ==null?"":infoMap.get("prepn_unit").toString());
					drc.setPkgUnit(infoMap.get("pkg_unit") ==null?"":infoMap.get("pkg_unit").toString());
					drc.setIssueExpiry(infoMap.get("issue_expiry") ==null?"":infoMap.get("issue_expiry").toString());
					drc.setFirstQuery(infoMap.get("first_query") ==null?"":infoMap.get("first_query").toString());
					drc.setLastEnt(infoMap.get("last_ent") ==null?"":infoMap.get("last_ent").toString());
					drc.setProductionBatch(infoMap.get("production_batch") ==null?"":infoMap.get("production_batch").toString());
					drc.setStatus(infoMap.get("status") ==null?"":infoMap.get("status").toString());
					drc.setSaleEn(infoMap.get("sale_ent") ==null?"":infoMap.get("sale_ent").toString());
					drc.setThumbUrl(infoMap.get("thumb_url") ==null?"":infoMap.get("thumb_url").toString());
					drc.setJgmCode(jgmCode);
					drc.setProductionDate(infoMap.get("production_date") ==null?"":infoMap.get("production_date").toString());
					drc.setSpecifications(infoMap.get("specifications") ==null?"":infoMap.get("specifications").toString());
					drc.setCategory(infoMap.get("category") ==null?"":infoMap.get("category").toString());
					drc.setManufacturer(infoMap.get("manufacturer") ==null?"":infoMap.get("manufacturer").toString());
					drc.setExpiryDate(infoMap.get("expiry_date") ==null?"":infoMap.get("expiry_date").toString());
					logger.debug("drc 数据:"+mapper.writeValueAsString(drc));
				}
				
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		
		}
		
		if(drc != null)
		{
			
			saveDrc(drc);
		}
		
		return drc;
		
	}
	
	public static final void main(String[] arg) throws Exception
	{
//		String json = "{"
//					+"\"info\": {"
//				    	+"\"expired\": false, "
//				    	+"\"license_number\": \"国药准字Z11020356\"," 
//				        +"\"thumb_url\": \"\", "
//				        +"\"last_time\": \"2015年04月02日\", "
//				        +"\"flow\": \"保定市汇丰医药有限公司,\", "
//				        +"\"pkg_spec\": \"盒10袋\", "
//				        +"\"sale_time\": \"\", "
//				        +"\"title\": \"板蓝根颗粒\", "
//				        +"\"prepn_type\": \"颗粒剂\", "
//				        +"\"query_time\": 15, "
//				        +"\"prepn_unit\": \"袋\", "
//				        +"\"pkg_unit\": \"盒\", "
//				        +"\"issue_expiry\": \"2015年08月11日\", "
//				        +"\"first_query\": \"2015年11月06日  09时49分27秒\", "
//				        +"\"is_sale\": false, "
//				        +"\"retcode\": 0, "
//				        +"\"last_ent\": \"隆化县天天乐大药房\", "
//				        +"\"production_batch\": 13112522, "
//				        +"\"status\": \"该药品已流向“隆化县天天乐大药房”\", "
//				        +"\"sale_ent\": \"\", "
//				        +"\"code\": 8.120280038460442E19, "
//				        +"\"production_date\": \"2013年05月15日\", "
//				        +"\"specifications\": \"每袋装5g\", "
//				        +"\"category\": \"普通药品\", "
//				        +"\"manufacturer\": \"北京同仁堂科技发展股份有限公司制药厂\", "
//				        +"\"expiry_date\": \"2016年04月30日\""
//			        +"}"
//				+"}";
//		
//		ObjectMapper mapper = new ObjectMapper();
//		Map map = (Map)mapper.readValue(json, Map.class);
//		Map infoMap = (Map)map.get("info");
//		
//	
//		Drc	drc = new Drc();
//			drc.setCategory((String)infoMap.get("license_number"));
//			drc.setLastTime((String)infoMap.get("last_time"));
//			drc.setFlow((String)infoMap.get("flow"));
//			drc.setPkgSpec((String)infoMap.get("pkg_spec"));
//			drc.setSaleTime((String)infoMap.get("sale_time"));
//			drc.setTitle((String)infoMap.get("title"));
//			drc.setPrepnType((String)infoMap.get("prepn_type"));
//			drc.setPrepnUnit((String)infoMap.get("prepn_unit"));
//			drc.setPkgUnit((String)infoMap.get("pkg_unit"));
//			drc.setIssueExpiry((String)infoMap.get("issue_expiry"));
//			drc.setFirstQuery((String)infoMap.get("first_query"));
//			drc.setLastEnt((String)infoMap.get("last_ent"));
//			drc.setProductionBatch((Integer)infoMap.get("production_batch")+"");
////			drc.setSt((String)infoMap.get("status"));
////			drc.set((String)infoMap.get("sale_ent"));
//			drc.setJgmCode((Double)infoMap.get("code")+"");
//			drc.setProductionBatch((String)infoMap.get("production_date"));
//			drc.setSpecifications((String)infoMap.get("specifications"));
//			drc.setCategory((String)infoMap.get("category"));
//			drc.setManufacturer((String)infoMap.get("manufacturer"));
//			drc.setExpiryDate((String)infoMap.get("expiry_date"));
//			
////			81202800384604426739
//			BigDecimal v = new BigDecimal((Double)infoMap.get("code"));
//			
//			System.out.println(v.toBigInteger());
			
			
		Integer i = new Integer(0);
		System.out.println(i.toString());
		
			
//		System.out.println(mapper.writeValueAsString(drc));
	}
	
}
