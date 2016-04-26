package com.bdx.rainbow.spsy.service;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginAgencyCompany;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginGoodsStock;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockOut;

import java.util.List;
import java.util.Map;

/**
 * Created by core on 16/3/2.
 */
public interface IProductTrackService {
    /**
     * 查询符合条件的出库信息
     * @param condition
     * @param start
     * @param limit
     * @return
     * @throws com.bdx.rainbow.common.exception.BusinessException
     */
    public List<TOriginProducerStockOut> getStockOutList(TOriginProducerStockOut condition,Integer start,Integer limit) throws BusinessException;

    /**
     * 查询符合条件的出库信息总数
     * @param condition
     * @return
     * @throws BusinessException
     */
    public Integer countStockOuts(TOriginProducerStockOut condition) throws BusinessException;

    /**
     * 根据出库信息查找对应生产商信息
     * @param out
     * @return
     * @throws Exception
     */
    public Map<Integer,DubboEnterpriseInfo> getEnterprise(List<Map> out)throws Exception;

    /**
     *根据产品信息和经销商编码查找对应的商品入库信息
     * @param buyAgencyIds
     * @param  operationType 区分零售商和经销商入库
     * @return
     */
    public List<TOriginGoodsStock> getGoodsStockByBuyAgencyIds(TOriginGoodsStock stock,List<Integer> buyAgencyIds,String operationType);

    /**
     * 根据产品信息和上级经销商编码查找对应的下级经销商入库信息
     * @param supplyAgencyIds
     * @return
     */
    public Map<Integer,List<TOriginGoodsStock>> getGoodsStockBySupplyAgency(TOriginGoodsStock goodsStock,List<Integer> supplyAgencyIds);

    public List<Map> getProducer(Map param);

    /**
     * 根据搜索信息从生产出库表中得到一级经销商信息
     * @param param
     * @return
     */
    public List<Integer> getFirAgencyIds(Map param);

    public List<TOriginAgencyCompany> getAgencyByIds(List<Integer> agencyIds);
    public Map getCirculates(Map param);
    public Map getSales(Map param);

    public Integer getSkuBatchByProCode(Map param);

    /**
     * 得到商品生产商与销售商的树json
     * @param param
     * @return
     */
    public String getTree(Map param) throws Exception;
}
