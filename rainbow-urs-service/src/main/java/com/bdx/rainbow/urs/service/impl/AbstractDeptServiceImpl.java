package com.bdx.rainbow.urs.service.impl;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.tree.DefaultTree;
import com.bdx.rainbow.entity.urs.SysDept;
import com.bdx.rainbow.entity.urs.SysDeptExample;
import com.bdx.rainbow.entity.urs.SysDeptExt;
import com.bdx.rainbow.entity.urs.SysDeptExtExample;
import com.bdx.rainbow.mapper.urs.*;
import com.bdx.rainbow.urs.common.SequenceUtil;
import com.bdx.rainbow.urs.service.IDeptService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDeptServiceImpl implements IDeptService {
    protected static final Logger log = LoggerFactory.getLogger(AbstractDeptServiceImpl.class);

    @Autowired
    protected SysDeptMapper sysDeptMapper;
    @Autowired
    protected SysDeptExtMapper sysDeptExtMapper;
    @Autowired
    protected SysPowerCommMapper sysPowerCommMapper;
    @Autowired
    protected SysUserMapper sysUserMapper;
    @Autowired
    protected  SysDeptHisMapper sysDeptHisMapper;
    @Override
    public Integer insertDept(SysDept record) throws SystemException, BusinessException {
        record.setCreateTime(new Timestamp(System.currentTimeMillis()));

        return  sysDeptMapper.insertSelective(record);
    }

    @Override
    public int updateDept(SysDept record) throws SystemException, BusinessException {
        record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return sysDeptMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void insertDeptExt(SysDeptExt deptExt) throws SystemException, BusinessException {
        sysDeptExtMapper.insert(deptExt);
    }

    @Override
    public int updateDeptExt(SysDeptExt deptExt) throws SystemException, BusinessException {
        SysDeptExtExample where = new SysDeptExtExample();
        where.createCriteria().andExtKeyEqualTo(deptExt.getExtKey()).andDeptIdEqualTo(deptExt.getDeptId());
        return sysDeptExtMapper.updateByExampleSelective(deptExt, where);
    }

    @Override
    public List<SysDept> getDepts(SysDept record, Integer start,
                                  Integer count) throws SystemException, BusinessException {
        SysDeptExample where = getCondition(record);

        if (start != null) {
            where.setLimitClauseStart(start);
            where.setLimitClauseCount(count);
        }
        return sysDeptMapper.selectByExample(where);
    }

    @Override
    public SysDept getDeptById(Integer deptId) throws SystemException, BusinessException {
        return sysDeptMapper.selectByPrimaryKey(deptId);
    }

    @Override
    public com.bdx.rainbow.entity.urs.SysDeptModel getDeptAndExtById(Integer deptId) throws SystemException, BusinessException {
        SysDept sc = this.getDeptById(deptId);
        if (sc != null) {
            com.bdx.rainbow.entity.urs.SysDeptModel scm = new com.bdx.rainbow.entity.urs.SysDeptModel();
            scm.setSc(sc);
            List<SysDeptExt> scExts = this.getDeptExtById(deptId);
            scm.setScExts(scExts);
            return scm;
        }
        return null;
    }

    @Override
    public List<SysDeptExt> getDeptExtById(Integer deptId) throws SystemException, BusinessException {
        SysDeptExtExample where = new SysDeptExtExample();
        where.createCriteria().andDeptIdEqualTo(deptId);
        return sysDeptExtMapper.selectByExample(where);
    }

    @Override
    public SysDeptExt getDeptExtByIdAndKey(Integer deptId, String key) throws SystemException, BusinessException {
        SysDeptExtExample where = new SysDeptExtExample();
        where.createCriteria().andDeptIdEqualTo(deptId).andExtKeyEqualTo(key);
        List<SysDeptExt> s = sysDeptExtMapper.selectByExample(where);
        if (s.isEmpty())
            return null;
        else
            return s.get(0);
    }

    @Override
    public int updateDeptStatusById(Integer deptId, String deptStatus, Integer updator) throws SystemException, BusinessException {
        Map<String, Object> condition = new HashMap<String, Object>();
        String bussin = SequenceUtil.getSequence();
        condition.put("bussin", bussin);
        condition.put("deptId", deptId);
        condition.put("bussin_type", "A");
        SysDept record = new SysDept();
        record.setDeptId(deptId);
        record.setUpdater(updator);
        record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        record.setDeptStatus(deptStatus);
        int count = sysDeptMapper.updateByPrimaryKeySelective(record);
        log.info("------update Dept status--------");
        return count;
    }

    @Override
    public int countDept(SysDept record) throws SystemException,
            BusinessException {
        SysDeptExample where = getCondition(record);
        return sysDeptMapper.countByExample(where);
    }

    @Override
    public int countDeptExtByIdAndKey(Integer deptId, String extKey)
            throws SystemException, BusinessException {
        SysDeptExtExample where = new SysDeptExtExample();
        where.createCriteria().andDeptIdEqualTo(deptId).andExtKeyEqualTo(extKey);
        return sysDeptExtMapper.countByExample(where);
    }

    @Override
    public DefaultTree<com.bdx.rainbow.entity.urs.SysDeptTree> getDeptTree(Map<String, Object> condition)
            throws SystemException, BusinessException {
        List<com.bdx.rainbow.entity.urs.SysDeptTree> depts = sysPowerCommMapper.getDeptTree(condition);
        DefaultTree<com.bdx.rainbow.entity.urs.SysDeptTree> tree = new DefaultTree<com.bdx.rainbow.entity.urs.SysDeptTree>("0", depts);

        return tree;
    }

    @Override
    public List<com.bdx.rainbow.entity.urs.SysDeptTree> getSysDeptTree(Map<String, Object> condition)
            throws SystemException, BusinessException {
        try {
            List<com.bdx.rainbow.entity.urs.SysDeptTree> depts = sysPowerCommMapper.getDeptTree(condition);

            if (depts == null || depts.size() <= 0) {
                throw new SystemException(new DefaultExceptionCode("8888", "该用户没有组织信息，请联系系统管理员"));
            }
            return depts;
        } catch (Exception e) {
            throw new SystemException(new DefaultExceptionCode("9999", e.getMessage()));
        }

    }

    private SysDeptExample getCondition(SysDept record) {
        SysDeptExample where = new SysDeptExample();
        if (record != null) {
            SysDeptExample.Criteria cr = where.createCriteria();
            if (record.getAdmin() != null && StringUtils.isNotEmpty(record.getAdmin())) {
                cr.andAdminLike("%" + record.getAdmin() + "%");
            }
            if (record.getDeptId() != null && record.getDeptId() > 0) {
                cr.andDeptIdEqualTo(record.getDeptId());
            }
            if (record.getDeptName() != null && StringUtils.isNotEmpty(record.getDeptName())) {
                cr.andDeptNameLike("%" + record.getDeptName() + "%");
            }
            if (record.getDeptType() != null && StringUtils.isNotEmpty(record.getDeptType())) {
                cr.andDeptTypeEqualTo(record.getDeptType());
            }
            if (record.getDeptPid() != null) {
                cr.andDeptPidEqualTo(record.getDeptPid());
            }
            if (record.getDeptStatus() != null && StringUtils.isNotEmpty(record.getDeptStatus())) {
                cr.andDeptStatusEqualTo(record.getDeptStatus());
            }
            if (record.getProvince() != null && StringUtils.isNotEmpty(record.getProvince())) {
                cr.andProvinceEqualTo(record.getProvince());
            }
            if (record.getAreaid() != null && StringUtils.isNotEmpty(record.getAreaid())) {
                cr.andAreaidEqualTo(record.getAreaid());
            }
            if (record.getCountyid() != null && StringUtils.isNotEmpty(record.getCountyid())) {
                cr.andCountyidEqualTo(record.getCountyid());
            }
        }
        return where;
    }

}
