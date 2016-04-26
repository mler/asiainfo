package com.bdx.rainbow.spsy.dal.ibatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CommonQueryDAOImpl extends SqlMapClientDaoSupport implements CommonQueryDAO {

	public List getModels(Object obj, String sqlKey) {
		return getSqlMapClientTemplate().queryForList(sqlKey, obj);
	}
	

	public void addModel(Object object, String sqlKey) {
		getSqlMapClientTemplate().insert(sqlKey, object);
	}



	public void delModel(String serialno, String sqlKey) {
		getSqlMapClientTemplate().delete(sqlKey, serialno);
		
	}

	public Object findByPkey(String serialno, String sqlKey) {
		return getSqlMapClientTemplate().queryForObject(sqlKey, serialno);
	}

	public void modModel(Object object, String sqlKey) {
		getSqlMapClientTemplate().update(sqlKey, object);
		
	}

	public Object findModel(Object object, String sqlKey) {
		return getSqlMapClientTemplate().queryForObject(sqlKey, object);
	}

	public Object getRealName(Object obj, String sqlKey) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForObject(sqlKey, obj);
	}


	@Override
	public void insertHISByCondition(String key, Map<String, Object> condition) {
		getSqlMapClientTemplate().insert(key, condition);
	}

    @Override
    public Map getMap(String sqlKey,Object obj,String keyProperty,  String valueProperty) {
        if (valueProperty==null|| StringUtils.isBlank(valueProperty)){
            return getSqlMapClientTemplate().queryForMap(sqlKey,obj,keyProperty);
        }
        return getSqlMapClientTemplate().queryForMap(sqlKey,obj,keyProperty,valueProperty );
    }


}
