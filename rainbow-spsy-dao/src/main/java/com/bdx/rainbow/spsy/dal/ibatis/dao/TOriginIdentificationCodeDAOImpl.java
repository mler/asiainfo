package com.bdx.rainbow.spsy.dal.ibatis.dao;

import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginIdentificationCode;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginIdentificationCodeExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class TOriginIdentificationCodeDAOImpl extends SqlMapClientDaoSupport implements TOriginIdentificationCodeDAO {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code
     *
     * @mbggenerated
     */
    public TOriginIdentificationCodeDAOImpl() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code
     *
     * @mbggenerated
     */
    public int countByExample(TOriginIdentificationCodeExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("t_origin_identification_code.countByExample", example);
        return count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code
     *
     * @mbggenerated
     */
    public int deleteByExample(TOriginIdentificationCodeExample example) {
        int rows = getSqlMapClientTemplate().delete("t_origin_identification_code.deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code
     *
     * @mbggenerated
     */
    public int deleteByPrimaryKey(Integer buildId) {
        TOriginIdentificationCode _key = new TOriginIdentificationCode();
        _key.setBuildId(buildId);
        int rows = getSqlMapClientTemplate().delete("t_origin_identification_code.deleteByPrimaryKey", _key);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code
     *
     * @mbggenerated
     */
    public Integer insert(TOriginIdentificationCode record) {
        Object newKey = getSqlMapClientTemplate().insert("t_origin_identification_code.insert", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code
     *
     * @mbggenerated
     */
    public Integer insertSelective(TOriginIdentificationCode record) {
        Object newKey = getSqlMapClientTemplate().insert("t_origin_identification_code.insertSelective", record);
        return (Integer) newKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code
     *
     * @mbggenerated
     */
    @SuppressWarnings("unchecked")
    public List<TOriginIdentificationCode> selectByExample(TOriginIdentificationCodeExample example) {
        List<TOriginIdentificationCode> list = getSqlMapClientTemplate().queryForList("t_origin_identification_code.selectByExample", example);
        return list;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code
     *
     * @mbggenerated
     */
    public TOriginIdentificationCode selectByPrimaryKey(Integer buildId) {
        TOriginIdentificationCode _key = new TOriginIdentificationCode();
        _key.setBuildId(buildId);
        TOriginIdentificationCode record = (TOriginIdentificationCode) getSqlMapClientTemplate().queryForObject("t_origin_identification_code.selectByPrimaryKey", _key);
        return record;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code
     *
     * @mbggenerated
     */
    public int updateByExampleSelective(TOriginIdentificationCode record, TOriginIdentificationCodeExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("t_origin_identification_code.updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code
     *
     * @mbggenerated
     */
    public int updateByExample(TOriginIdentificationCode record, TOriginIdentificationCodeExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("t_origin_identification_code.updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code
     *
     * @mbggenerated
     */
    public int updateByPrimaryKeySelective(TOriginIdentificationCode record) {
        int rows = getSqlMapClientTemplate().update("t_origin_identification_code.updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_origin_identification_code
     *
     * @mbggenerated
     */
    public int updateByPrimaryKey(TOriginIdentificationCode record) {
        int rows = getSqlMapClientTemplate().update("t_origin_identification_code.updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_origin_identification_code
     *
     * @mbggenerated
     */
    protected static class UpdateByExampleParms extends TOriginIdentificationCodeExample {
        private Object record;

        public UpdateByExampleParms(Object record, TOriginIdentificationCodeExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}