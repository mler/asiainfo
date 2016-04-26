package com.bdx.rainbow.spsy.service.impl;

import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.spsy.dal.ibatis.dao.SelfTraceDAO;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginProducerStockDetailInDAO;
import com.bdx.rainbow.spsy.dal.ibatis.model.SelfTrace;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockDetailIn;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockDetailInExample;
import com.bdx.rainbow.spsy.service.ITraceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengwenjuan on 16/3/7.
 */
public class TraceServiceImpl implements ITraceService {
    @Autowired
    private SelfTraceDAO selfTraceDAO;
    
    @Autowired
    private TOriginProducerStockDetailInDAO tOriginProducerStockDetailInDAO;

    @Override
    public Map<String, Object> getSpjgTraces(SelfTrace condition,int start,int limit) throws Exception {
        Map<String,Object> param = convertCondition(condition);
        Map<String,Object> result = new HashMap<String,Object>();
        if (param == null) {
			return result;
		}
        if (start >= 0 && limit > 0){
            param.put("limitClauseStart",start);
            param.put("limitClauseCount",limit);
        }
        try {
        	 List<SelfTrace> list = selfTraceDAO.getSpjgTrace(param);
             int total = selfTraceDAO.countSpjgTrace(param);
             result.put("list",list);
             result.put("total",total);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
        return result;
    }

    private Map<String,Object> convertCondition(SelfTrace condition)throws Exception{
        Map<String,Object> param = new HashMap<String,Object>();
        if (condition != null){
            if(StringUtils.isNotEmpty(condition.getQueryCondition())){
                param.put("queryCondition", "%" + condition.getQueryCondition() + "%");

            }
            if (StringUtils.isNotEmpty(condition.getEnterpriseName())){
				param.put("enterpriseName", "%" + condition.getEnterpriseName() + "%");
			}
            if (condition.getEnterpriseId() != null){
				param.put("enterpriseId", condition.getEnterpriseId());
			}
            if (condition.getSkuId() != null){
				param.put("skuId", condition.getSkuId());
			}
            if (StringUtils.isNotEmpty(condition.getInName())){
				param.put("inName", "%" + condition.getInName() + "%");
			}
            if (StringUtils.isNotEmpty(condition.getBuyAgencyIdsStr())){
				param.put("buyAgencyIdsStr", condition.getBuyAgencyIdsStr());
			}
            if (StringUtils.isNotEmpty(condition.getSkuIdCode())) {
    			TOriginProducerStockDetailIn sym = getSkuBarCodeAndBatchBySYM(condition.getSkuIdCode());
    			if (sym == null) {
					return null;
				}
    			condition.setSkuBarCode(sym.getSkuBarCode());
    			condition.setSkuBatch(sym.getSkuBatch());
    		}
            if (StringUtils.isNotEmpty(condition.getSkuBarCode())){
				param.put("skuBarCode", condition.getSkuBarCode());
			}
            if (StringUtils.isNotEmpty(condition.getSkuBatch())){
				param.put("skuBatch", condition.getSkuBatch());
			}
            
            
        }
        return param;
    }

	@Override
	public Map<String, Object> getSpjgChartData(Integer skuId, String YYYYMM)
			throws Exception {
		if (skuId == null || StringUtils.isEmpty(YYYYMM)) {
			throw new Exception("lost some parameter");
		}
		Map<String,Object> param = new HashMap<String,Object>();
        param.put("skuId",skuId);
        Timestamp productionDate_start = DateUtil.getMonthFirst(YYYYMM, "yyyyMM");
        Timestamp productionDate_end = DateUtil.getMonthLast(YYYYMM, "yyyyMM");
        param.put("productionDate_start", productionDate_start);
        param.put("productionDate_end", productionDate_end);
        //获得数据
        List<SelfTrace> traces = selfTraceDAO.getSpjgTraceChart(param);
        //解析数据
        List<String> xAxis = new ArrayList<String>();//商品批次号
        List<Integer> yAxis_produce = new ArrayList<Integer>();//出产量
        List<Integer> yAxis_pass = new ArrayList<Integer>();//经销量
        List<Integer> yAxis_sale = new ArrayList<Integer>();//销售量
        Map<String, Object> result = new HashMap<String,Object>(); 
        if (traces != null && traces.size() != 0) {
			for (SelfTrace trace:traces) {
				xAxis.add(trace.getSkuBatch());
				yAxis_produce.add(trace.getEnterpriseOutRecords());
				yAxis_pass.add(trace.getPassOutRecords());
				yAxis_sale.add(trace.getMerchantOutRecords());
				result.put("skuName", trace.getSkuName());
			}
			result.put("xAxis", xAxis);
			result.put("yAxis_produce", yAxis_produce);
			result.put("yAxis_pass", yAxis_pass);
			result.put("yAxis_sale", yAxis_sale);
			
		}
		return result;
	}

	@Override
	public Map<String, Object> getQyjgTraces(SelfTrace condition, int start,
			int limit) throws Exception {
		Map<String,Object> param = convertCondition(condition);
        Map<String,Object> result = new HashMap<String,Object>();
        if (param == null) {
			return result;
		}
        if (start >= 0 && limit > 0){
            param.put("limitClauseStart",start);
            param.put("limitClauseCount",limit);
        }
        try {
        	 List<SelfTrace> list = selfTraceDAO.getQyjgTrace(param);
             int total = selfTraceDAO.countQyjgTrace(param);
             result.put("list",list);
             result.put("total",total);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
        return result;
	}

	@Override
	public int totalSpjgTrace(SelfTrace condition) throws Exception {
		Map<String,Object> param = convertCondition(condition);
		if (param == null) {
			return 0;
		}
		int total= selfTraceDAO.totalSpjgTrace(param);
		return total;
	}

	@Override
	public int totalQyjgTrace(SelfTrace condition) throws Exception {
		Map<String,Object> param = convertCondition(condition);
		if (param == null) {
			return 0;
		}
		int total= selfTraceDAO.totalQyjgTrace(param);
		return total;
	}

	@Override
	public Map<String, Object> getQyjgChartData(Integer enterpriseId,
			String YYYYMM) throws Exception {
		if (enterpriseId == null || StringUtils.isEmpty(YYYYMM)) {
			throw new Exception("lost some parameter");
		}
		Map<String,Object> param = new HashMap<String,Object>();
        param.put("enterpriseId",enterpriseId);
        Timestamp productionDate_start = DateUtil.getMonthFirst(YYYYMM, "yyyyMM");
        Timestamp productionDate_end = DateUtil.getMonthLast(YYYYMM, "yyyyMM");
        param.put("productionDate_start", productionDate_start);
        param.put("productionDate_end", productionDate_end);
        //获得数据
        List<SelfTrace> traces = selfTraceDAO.getQyjgTraceChart(param);
        //解析数据
        List<String> xAxis = new ArrayList<String>();//商品批次号
        List<Integer> yAxis = new ArrayList<Integer>();//出产量
        Map<String, Object> result = new HashMap<String,Object>(); 
        if (traces != null && traces.size() != 0) {
			for (SelfTrace trace:traces) {
				xAxis.add(trace.getSkuName());
				yAxis.add(trace.getTracedSkuNum());
			}
			result.put("xAxis", xAxis);
			result.put("yAxis", yAxis);
			
		}
		return result;
	}

	@Override
	public List<SelfTrace> getSpzsSale(SelfTrace condition, int start, int limit)
			throws Exception {
		Map<String,Object> param = convertCondition(condition);
		if (param == null) {
			return null;
		}
		List<SelfTrace> sales = selfTraceDAO.getSpzsSale(param);
		return sales;
	}

	@Override
	public List<SelfTrace> getSpzsPass(SelfTrace condition, int start, int limit)
			throws Exception {
		Map<String,Object> param = convertCondition(condition);
		if (param == null) {
			return null;
		}
		List<SelfTrace> passes = null;
		try {
			if (StringUtils.isEmpty(condition.getInName())) {//如果销售名为空 则出全部结果
				passes = selfTraceDAO.getSpzsPass(param);
			}else {
				String buyAgencyIdsStr = selfTraceDAO.getSpzsLastAgencyIds(param);
				if (buyAgencyIdsStr == null) {
					return null;
				}else {
					
					param.put("buyAgencyIdsStr", buyAgencyIdsStr);
					buyAgencyIdsStr = selfTraceDAO.getTraceFromOut(param);
					if ("$,".equalsIgnoreCase(buyAgencyIdsStr)) {
						return null;
					}
					param.put("buyAgencyIdsStr", buyAgencyIdsStr.replace("$,", ""));
					param.remove("inName");
					passes = selfTraceDAO.getSpzsPass(param);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return passes;
		
	}

	@Override
	public List<SelfTrace> getSpzsProduce(SelfTrace condition, int start,
			int limit) throws Exception {
		try {
			Map<String,Object> param = convertCondition(condition);
			if (param == null) {
				return null;
			}
			List<SelfTrace> produces = selfTraceDAO.getSpzsProduce(param);
			return produces;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TOriginProducerStockDetailIn getSkuBarCodeAndBatchBySYM(String sym) throws Exception {
		if (StringUtils.isEmpty(sym)) {
			return null;
		}
		TOriginProducerStockDetailInExample example = new TOriginProducerStockDetailInExample();
		example.createCriteria().andSkuIdCodeEqualTo(sym);
		List<TOriginProducerStockDetailIn> list = tOriginProducerStockDetailInDAO.selectByExample(example);
		if (list != null && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}
	
}
