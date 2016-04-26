package com.bdx.rainbow.spsy.dal.ibatis.dao;

import com.bdx.rainbow.spsy.dal.ibatis.model.SelfTrace;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengwenjuan on 16/3/7.
 */
public class SelfTraceDAOImpl extends SqlMapClientDaoSupport implements SelfTraceDAO {
    @Override
    public List<SelfTrace> getSpjgTrace(Map<String, Object> map) throws Exception {
        List<SelfTrace> list = getSqlMapClientTemplate().queryForList("self_trace.selectBase_SPJG", map);
        return list;
    }

    @Override
    public int countSpjgTrace(Map<String, Object> map) throws Exception {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("self_trace.countBase_SPJG", map);
        return count;
    }

	@Override
	public List<SelfTrace> getSpjgTraceChart(Map<String, Object> map)
			throws Exception {
		List<SelfTrace> list = getSqlMapClientTemplate().queryForList("self_trace.selectChart_SPJG", map);
        return list;
	}

	@Override
	public List<SelfTrace> getQyjgTrace(Map<String, Object> map)
			throws Exception {
		 List<SelfTrace> list = getSqlMapClientTemplate().queryForList("self_trace.selectBase_QYJG", map);
	     return list;
	}
	
	 @Override
	    public int countQyjgTrace(Map<String, Object> map) throws Exception {
	        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("self_trace.countBase_QYJG", map);
	        return count;
	    }

	@Override
	public int totalQyjgTrace(Map<String, Object> map) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("self_trace.countTotal_QYJG", map);
        return count;
	}

	@Override
	public int totalSpjgTrace(Map<String, Object> map) throws Exception {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("self_trace.countTotal_SPJG", map);
        return count;
	}

	@Override
	public List<SelfTrace> getQyjgTraceChart(Map<String, Object> map)
			throws Exception {
		List<SelfTrace> list = getSqlMapClientTemplate().queryForList("self_trace.selectChart_QYJG", map);
		return list;
	}

	@Override
	public List<SelfTrace> getSpzsSale(Map<String, Object> map)
			throws Exception {
		List<SelfTrace> list = getSqlMapClientTemplate().queryForList("self_trace.selectSPZS_Sale", map);
		return list;
	}

	@Override
	public List<SelfTrace> getSpzsPass(Map<String, Object> map)
			throws Exception {
		List<SelfTrace> list = getSqlMapClientTemplate().queryForList("self_trace.selectSPZS_Pass", map);
		return list;
	}

	@Override
	public List<SelfTrace> getSpzsProduce(Map<String, Object> map)
			throws Exception {
		List<SelfTrace> list = getSqlMapClientTemplate().queryForList("self_trace.selectSPZS_Produce", map);
		return list;
	}

	@Override
	public String getSpzsLastAgencyIds(Map<String, Object> map)
			throws Exception {
		Object buyAgencyIdsStr =  getSqlMapClientTemplate().queryForObject("self_trace.selectLastAgency", map);
		if (buyAgencyIdsStr == null) {
			return null;
		}
		return (String)buyAgencyIdsStr;
	}

	@Override
	public String getTraceFromOut(Map<String, Object> map)
			throws Exception {
		String traceIds =  (String) getSqlMapClientTemplate().queryForObject("self_trace.selectTraceFromOut", map);
		return traceIds;
	}
}
