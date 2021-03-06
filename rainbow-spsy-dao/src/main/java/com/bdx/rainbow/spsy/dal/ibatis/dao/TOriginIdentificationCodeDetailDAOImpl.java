package com.bdx.rainbow.spsy.dal.ibatis.dao;

import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginIdentificationCodeDetail;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginIdentificationCodeDetailExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class TOriginIdentificationCodeDetailDAOImpl extends SqlMapClientDaoSupport implements TOriginIdentificationCodeDetailDAO {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code_detail
     *
     * @mbggenerated
     */
    public TOriginIdentificationCodeDetailDAOImpl() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code_detail
     *
     * @mbggenerated
     */
    public int countByExample(TOriginIdentificationCodeDetailExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("t_origin_identification_code_detail.countByExample", example);
        return count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code_detail
     *
     * @mbggenerated
     */
    public int deleteByExample(TOriginIdentificationCodeDetailExample example) {
        int rows = getSqlMapClientTemplate().delete("t_origin_identification_code_detail.deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code_detail
     *
     * @mbggenerated
     */
    public int deleteByPrimaryKey(Integer buildDetailId) {
        TOriginIdentificationCodeDetail _key = new TOriginIdentificationCodeDetail();
        _key.setBuildDetailId(buildDetailId);
        int rows = getSqlMapClientTemplate().delete("t_origin_identification_code_detail.deleteByPrimaryKey", _key);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code_detail
     *
     * @mbggenerated
     */
    public Integer insert(TOriginIdentificationCodeDetail record) {
        Object newKey = getSqlMapClientTemplate().insert("t_origin_identification_code_detail.insert", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code_detail
     *
     * @mbggenerated
     */
    public Integer insertSelective(TOriginIdentificationCodeDetail record) {
        Object newKey = getSqlMapClientTemplate().insert("t_origin_identification_code_detail.insertSelective", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code_detail
     *
     * @mbggenerated
     */
    @SuppressWarnings("unchecked")
    public List<TOriginIdentificationCodeDetail> selectByExample(TOriginIdentificationCodeDetailExample example) {
        List<TOriginIdentificationCodeDetail> list = getSqlMapClientTemplate().queryForList("t_origin_identification_code_detail.selectByExample", example);
        return list;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code_detail
     *
     * @mbggenerated
     */
    public TOriginIdentificationCodeDetail selectByPrimaryKey(Integer buildDetailId) {
        TOriginIdentificationCodeDetail _key = new TOriginIdentificationCodeDetail();
        _key.setBuildDetailId(buildDetailId);
        TOriginIdentificationCodeDetail record = (TOriginIdentificationCodeDetail) getSqlMapClientTemplate().queryForObject("t_origin_identification_code_detail.selectByPrimaryKey", _key);
        return record;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code_detail
     *
     * @mbggenerated
     */
    public int updateByExampleSelective(TOriginIdentificationCodeDetail record, TOriginIdentificationCodeDetailExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("t_origin_identification_code_detail.updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code_detail
     *
     * @mbggenerated
     */
    public int updateByExample(TOriginIdentificationCodeDetail record, TOriginIdentificationCodeDetailExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("t_origin_identification_code_detail.updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code_detail
     *
     * @mbggenerated
     */
    public int updateByPrimaryKeySelective(TOriginIdentificationCodeDetail record) {
        int rows = getSqlMapClientTemplate().update("t_origin_identification_code_detail.updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code_detail
     *
     * @mbggenerated
     */
    public int updateByPrimaryKey(TOriginIdentificationCodeDetail record) {
        int rows = getSqlMapClientTemplate().update("t_origin_identification_code_detail.updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_origin_identification_code_detail
     *
     * @mbggenerated
     */
    protected static class UpdateByExampleParms extends TOriginIdentificationCodeDetailExample {
        private Object record;

        public UpdateByExampleParms(Object record, TOriginIdentificationCodeDetailExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}