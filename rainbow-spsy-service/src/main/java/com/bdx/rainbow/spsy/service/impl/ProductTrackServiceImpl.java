package com.bdx.rainbow.spsy.service.impl;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.service.IEnterpriseDubboService;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.spsy.common.ConsumerUtil;
import com.bdx.rainbow.spsy.dal.ibatis.dao.CommonQueryDAO;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginAgencyCompanyDAO;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginGoodsStockDAO;
import com.bdx.rainbow.spsy.dal.ibatis.dao.TOriginProducerStockOutDAO;
import com.bdx.rainbow.spsy.dal.ibatis.model.*;
import com.bdx.rainbow.spsy.service.IProductTrackService;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by core on 16/3/3.
 */
public class ProductTrackServiceImpl implements IProductTrackService {
    @Autowired
    private TOriginProducerStockOutDAO originProducerStockOutDAO;
    @Autowired
    private TOriginGoodsStockDAO goodsStockDAO;
    @Autowired
    private CommonQueryDAO queryDAO;
    @Autowired
    private TOriginAgencyCompanyDAO originAgencyCompanyDAO;

    @Autowired
    private IEnterpriseDubboService enterpriseDubboService ;

    @Override
    public List<TOriginProducerStockOut> getStockOutList(TOriginProducerStockOut condition, Integer start, Integer limit) throws BusinessException {
        TOriginProducerStockOutExample example =convertOutExample(condition);
        if (start!=null){
            example.setLimitClauseCount(limit);
            example.setLimitClauseStart(start);
        }
        example.setDistinct(true);
        return originProducerStockOutDAO.selectByExample(example);
    }

    @Override
    public Integer countStockOuts(TOriginProducerStockOut condition) throws BusinessException {
        TOriginProducerStockOutExample example =convertOutExample(condition);
        example.setDistinct(true);
        return originProducerStockOutDAO.countByExample(example);
    }

    @Override
        public Map<Integer, DubboEnterpriseInfo> getEnterprise(List<Map> out) throws Exception {
        Map<Integer, DubboEnterpriseInfo> result =new HashMap<Integer, DubboEnterpriseInfo>();
        for (int i=0;i<out.size();i++){
            Map<String, Object> corp = new HashMap<String, Object>();
            if (result.get((Integer)out.get(i).get("producerId"))==null){
                corp=enterpriseDubboService.getEnterpriseDetailById((Integer)out.get(i).get("producerId"));
                result.put((Integer)out.get(i).get("producerId"),(DubboEnterpriseInfo)corp.get("info"));
            }
        }
        return result;
    }

    @Override
    public List<TOriginGoodsStock> getGoodsStockByBuyAgencyIds(TOriginGoodsStock stock,List<Integer> buyAgencyIds,String operationType) {
        TOriginGoodsStockExample example=new TOriginGoodsStockExample();
        example.setDistinct(true);
        TOriginGoodsStockExample.Criteria cr = example.createCriteria();
        cr.andBuyAgencyIdIn(buyAgencyIds).andOperationTypeEqualTo(operationType);
        if (StringUtil.isNotBlank(stock.getSkuBatch())){
            cr.andSkuBatchEqualTo(stock.getSkuBatch());
        }
        if(StringUtil.isNotBlank(stock.getSkuName())){
            cr.andSkuNameLike("%" + stock.getSkuName() + "%");
        }
        if(stock.getSkuId()!=null&&StringUtil.isNotBlank(stock.getSkuId().toString())){
            cr.andSkuIdEqualTo(stock.getSkuId());
        }
        return goodsStockDAO.selectByExample(example);
    }

    @Override
    public Map<Integer, List<TOriginGoodsStock>> getGoodsStockBySupplyAgency(TOriginGoodsStock goodsStock,List<Integer> supplyAgencyIds) {
        TOriginGoodsStockExample example=new TOriginGoodsStockExample();
        example.setDistinct(true);
        TOriginGoodsStockExample.Criteria cr = example.createCriteria();
        cr.andOperationTypeEqualTo("01").andSupplyAgencyIdIn(supplyAgencyIds);
        if (StringUtil.isNotBlank(goodsStock.getSkuBatch())){
            cr.andSkuBatchEqualTo(goodsStock.getSkuBatch());
        }
        if(StringUtil.isNotBlank(goodsStock.getSkuName())){
            cr.andSkuNameLike("%" + goodsStock.getSkuName() + "%");
        }
        if(goodsStock.getSkuId()!=null&&StringUtil.isNotBlank(goodsStock.getSkuId().toString())){
            cr.andSkuIdEqualTo(goodsStock.getSkuId());
        }
        List<TOriginGoodsStock> stocks=goodsStockDAO.selectByExample(example);

        Map<Integer, List<TOriginGoodsStock>> results=new HashMap<Integer, List<TOriginGoodsStock>>();
        for (TOriginGoodsStock stock:stocks){
            if (results.get(stock.getSupplyAgencyId())!=null){
                List<TOriginGoodsStock> stocksChild=results.get(stock.getSupplyAgencyId());
                stocksChild.add(stock);
                results.put(stock.getSupplyAgencyId(),stocksChild);
            }else{
                List<TOriginGoodsStock> newStocksChild=new ArrayList<TOriginGoodsStock>();
                newStocksChild.add(stock);
                results.put(stock.getSupplyAgencyId(),newStocksChild);
            }
        }
        return results;
    }

    @Override
    public List<Map> getProducer(Map param) {
        return queryDAO.getModels(param,"track.selectCorp");
    }

    @Override
    public List<Integer> getFirAgencyIds(Map param) {
        return queryDAO.getModels(param,"track.selectFirAgency");
    }

    @Override
    public List<TOriginAgencyCompany> getAgencyByIds(List<Integer> agencyIds) {
        TOriginAgencyCompanyExample example=new TOriginAgencyCompanyExample();
        example.createCriteria().andAgencyIdIn(agencyIds);
        return originAgencyCompanyDAO.selectByExample(example);
    }

    @Override
    public Map getCirculates(Map param) {
        return queryDAO.getMap("track.selectCirculate",param,"buyAgencyId",null);
    }

    @Override
    public Map getSales(Map param) {
        return queryDAO.getMap("track.selectSale",param,"agencyId",null);
    }

    @Override
    public Integer getSkuBatchByProCode(Map param) {
        List<Integer> list=queryDAO.getModels(param,"track.selectSkuBatch");
        if (list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public String getTree(Map param) throws Exception {
        List<Map> producer=getProducer(param);
        if (producer.size()>0){
//            String skuName=producer.get(0).get("skuName").toString() ;
//            Map<String, Object> corp =enterpriseDubboService.getEnterpriseDetailById((Integer)producer.get(0).get("producerId"));
//            String enterpriseName= ((DubboEnterpriseInfo) corp.get("info")).getEnterpriseName();

        }

        return null;
    }

    private TOriginProducerStockOutExample convertOutExample(TOriginProducerStockOut condition) throws BusinessException{
        TOriginProducerStockOutExample example = new TOriginProducerStockOutExample();
        TOriginProducerStockOutExample.Criteria cr = example.createCriteria();
        if(condition==null){
            return example;
        }
        if(StringUtil.isNotBlank(condition.getSkuName())){
            cr.andSkuNameLike("%"+condition.getSkuName()+"%");
        }
        if(StringUtil.isNotBlank(condition.getSkuBatch())){
            cr.andSkuBatchEqualTo(condition.getSkuBatch());
        }
        if(StringUtil.isNotBlank(condition.getProductIdCode())){
            cr.andProductIdCodeLike("%" + condition.getProductIdCode() + "%");
        }
        return example;
    }

}
