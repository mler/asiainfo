package com.bdx.rainbow.spsy.dal.ibatis.dao;

import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockIn;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockInExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class TOriginProducerStockInDAOImpl extends SqlMapClientDaoSupport implements TOriginProducerStockInDAO {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_producer_stock_in
     *
     * @mbggenerated
     */
    public TOriginProducerStockInDAOImpl() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_producer_stock_in
     *
     * @mbggenerated
     */
    public int countByExample(TOriginProducerStockInExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("t_origin_producer_stock_in.countByExample", example);
        return count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_producer_stock_in
     *
     * @mbggenerated
     */
    public int deleteByExample(TOriginProducerStockInExample example) {
        int rows = getSqlMapClientTemplate().delete("t_origin_producer_stock_in.deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_producer_stock_in
     *
     * @mbggenerated
     */
    public int deleteByPrimaryKey(Integer operationInId) {
        TOriginProducerStockIn _key = new TOriginProducerStockIn();
        _key.setOperationInId(operationInId);
        int rows = getSqlMapClientTemplate().delete("t_origin_producer_stock_in.deleteByPrimaryKey", _key);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_producer_stock_in
     *
     * @mbggenerated
     */
    public Integer insert(TOriginProducerStockIn record) {
        Object newKey = getSqlMapClientTemplate().insert("t_origin_producer_stock_in.insert", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_producer_stock_in
     *
     * @mbggenerated
     */
    public Integer insertSelective(TOriginProducerStockIn record) {
        Object newKey = getSqlMapClientTemplate().insert("t_origin_producer_stock_in.insertSelective", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_producer_stock_in
     *
     * @mbggenerated
     */
    @SuppressWarnings("unchecked")
    public List<TOriginProducerStockIn> selectByExample(TOriginProducerStockInExample example) {
        List<TOriginProducerStockIn> list = getSqlMapClientTemplate().queryForList("t_origin_producer_stock_in.selectByExample", example);
        return list;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_producer_stock_in
     *
     * @mbggenerated
     */
    public TOriginProducerStockIn selectByPrimaryKey(Integer operationInId) {
        TOriginProducerStockIn _key = new TOriginProducerStockIn();
        _key.setOperationInId(operationInId);
        TOriginProducerStockIn record = (TOriginProducerStockIn) getSqlMapClientTemplate().queryForObject("t_origin_producer_stock_in.selectByPrimaryKey", _key);
        return record;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_producer_stock_in
     *
     * @mbggenerated
     */
    public int updateByExampleSelective(TOriginProducerStockIn record, TOriginProducerStockInExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("t_origin_producer_stock_in.updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_producer_stock_in
     *
     * @mbggenerated
     */
    public int updateByExample(TOriginProducerStockIn record, TOriginProducerStockInExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("t_origin_producer_stock_in.updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_producer_stock_in
     *
     * @mbggenerated
     */
    public int updateByPrimaryKeySelective(TOriginProducerStockIn record) {
        int rows = getSqlMapClientTemplate().update("t_origin_producer_stock_in.updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_producer_stock_in
     *
     * @mbggenerated
     */
    public int updateByPrimaryKey(TOriginProducerStockIn record) {
        int rows = getSqlMapClientTemplate().update("t_origin_producer_stock_in.updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_origin_producer_stock_in
     *
     * @mbggenerated
     */
    protected static class UpdateByExampleParms extends TOriginProducerStockInExample {
        private Object record;

        public UpdateByExampleParms(Object record, TOriginProducerStockInExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}