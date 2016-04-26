package com.bdx.rainbow.service.basic.impl;

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
import com.bdx.rainbow.entity.basic.mysql.TBasicSkuItem;
import com.bdx.rainbow.entity.basic.mysql.TBasicSkuItemExample;
import com.bdx.rainbow.mapper.basic.mysql.TBasicSkuItemMapper;
import com.bdx.rainbow.mapper.basic.mysql.TBasicSkuMapper;
import com.bdx.rainbow.mapper.basic.mysql.TBasicSkuMedicineMapper;
import com.bdx.rainbow.mapper.jc.DrcMapper;
import com.bdx.rainbow.service.basic.IMedicineService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional(rollbackFor=Exception.class,value="transactionManager")
public class MedicineService implements IMedicineService {

	private final Logger logger = LoggerFactory.getLogger(MedicineService.class);
	
	@Autowired
	private DrcMapper drcMapper;
	
	@Autowired
	private TBasicSkuMedicineMapper tBasicSkuMedicineMapper;
	
	@Autowired
	private TBasicSkuMapper tBasicSkuMapper;
	
	@Autowired
	private TBasicSkuItemMapper tBasicSkuItemMapper;
	

	public void insertDrugItem(TBasicSkuItem record) throws Exception
	{
		tBasicSkuItemMapper.insertSelective(record);
		
		
	}
	
	public TBasicSkuItem selectDrcByCode(String jgmCode) throws Exception
	{
		if(StringUtil.isBlank(jgmCode))
			throw new Exception("监管码不能为空");
		TBasicSkuItemExample example =new TBasicSkuItemExample();
		example.createCriteria().andItemBarcodeEqualTo(jgmCode);
		example.setOrderByClause(" CREATE_TIME desc ");
		List<TBasicSkuItem> drcs = tBasicSkuItemMapper.selectByExample(example);
		
		if (drcs != null && drcs.size() != 0 ) {
			return drcs.get(0);
		}else {
			return null;
		}
		
	}
	
	public TBasicSkuItem findDrcFromCFDA(String jgmCode) throws Exception
	{
		if(StringUtils.isBlank(jgmCode))
			throw new Exception("监管码为空");
		
		TBasicSkuItem drc = selectDrcByCode(jgmCode);
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
					drc = new TBasicSkuItem();
					drc.setApprovalNum(infoMap.get("license_number") ==null?"":infoMap.get("license_number").toString());//
					drc.setLastFlowTime(infoMap.get("last_time") ==null?"":infoMap.get("last_time").toString());//
					drc.setFlow(infoMap.get("flow") ==null?"":infoMap.get("flow").toString());
					drc.setPkgSpec(infoMap.get("pkg_spec") ==null?"":infoMap.get("pkg_spec").toString());
					drc.setSaleTime(infoMap.get("sale_time") ==null?"":infoMap.get("sale_time").toString());
					drc.setItemName(infoMap.get("title") ==null?"":infoMap.get("title").toString());//
					drc.setForm(infoMap.get("prepn_type") ==null?"":infoMap.get("prepn_type").toString());//
					drc.setFormUnit(infoMap.get("prepn_unit") ==null?"":infoMap.get("prepn_unit").toString());//
					drc.setPkgUnit(infoMap.get("pkg_unit") ==null?"":infoMap.get("pkg_unit").toString());
					drc.setIssueExpiry(infoMap.get("issue_expiry") ==null?"":infoMap.get("issue_expiry").toString());
//					drc.setFirstQuery(infoMap.get("first_query") ==null?"":infoMap.get("first_query").toString());
					drc.setLastEnt(infoMap.get("last_ent") ==null?"":infoMap.get("last_ent").toString());
					drc.setBatchNo(infoMap.get("production_batch") ==null?"":infoMap.get("production_batch").toString());
					drc.setFlow(infoMap.get("status") ==null?"":infoMap.get("status").toString());//
					drc.setSaleEnt(infoMap.get("sale_ent") ==null?"":infoMap.get("sale_ent").toString());//
					drc.setThumbUrl(infoMap.get("thumb_url") ==null?"":infoMap.get("thumb_url").toString());
					drc.setItemBarcode(jgmCode);//
					drc.setProductionDate(infoMap.get("production_date") ==null?"":infoMap.get("production_date").toString());
					drc.setSpec(infoMap.get("specifications") ==null?"":infoMap.get("specifications").toString());//
					drc.setCategory(infoMap.get("category") ==null?"":infoMap.get("category").toString());
					drc.setManufactory(infoMap.get("manufacturer") ==null?"":infoMap.get("manufacturer").toString());//
					drc.setExpireDate(infoMap.get("expiry_date") ==null?"":infoMap.get("expiry_date").toString());//
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
			
			insertDrugItem(drc);
		}
		
		return drc;
		
	}
	
	private void changeDrug2Drc(){
		
	}
	
}
