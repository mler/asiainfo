package com.bdx.rainbow.service.sk.impl;

import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.entity.vfs.FileServerConfig;
import com.bdx.rainbow.mapper.vfs.FileServerConfigMapper;
import com.bdx.rainbow.service.sk.IHealthService;
import com.bdx.rainbow.service.vfs.IFile;
import com.bdx.rainbow.ygcf.entity.Employee;
import com.bdx.rainbow.ygcf.entity.Health;
import com.bdx.rainbow.ygcf.entity.HealthExample;
import com.bdx.rainbow.ygcf.mapper.EmployeeMapper;
import com.bdx.rainbow.ygcf.mapper.HealthMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 健康证
 * Created by fusj on 16/3/3.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class HealthServiceImpl implements IHealthService {

    @Autowired
    private HealthMapper healthMapper;

    @Autowired
    private FileServerConfigMapper fileServerConfigMapper;

    @Autowired
    private VfsSetting vfsSetting;

    @Autowired
    private IFile fileManager;

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 根据员工编号查询有效的健康证
     * @param employeeId
     * @return
     */
    @Override
    public Health selectByEmployeeId(Integer employeeId) throws Exception {
        Health health = new Health();
        health.setEmployeeId(employeeId);
        health.setStatus("1");

        HealthExample example = getCondition(health);

        List<Health> healthList = healthMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(healthList)) {
            return new Health();
        }

        health = healthList.get(0);

        FileServerConfig fileServerConfig = fileServerConfigMapper.selectByPrimaryKey(vfsSetting.getServerName());
        try {
            String filePath = fileManager.getHttpUrl(health.getHealthPath(), fileServerConfig);
            health.setHealthPath(filePath);

        } catch (Exception ex) {
            throw new SystemException(new DefaultExceptionCode("500", ex.getMessage()));
        }

        return healthList.get(0);
    }

    /**
     * 根据员工编号查询所有健康证信息
     * @param employeeId
     * @return
     */
    @Override
    public List<Health> selectAllByEmployeeId(Integer employeeId) {
        Health health = new Health();
        health.setEmployeeId(employeeId);

        HealthExample example = getCondition(health);
        example.setOrderByClause(" health_id asc ");

        List<Health> list = healthMapper.selectByExample(example);

        return list;
    }

    /**
     * 健康证换证保存
     * @param health
     */
    @Override
    public void change(Health health) throws Exception {

        // 设置之前的健康证为失效
        HealthExample example = new HealthExample();
        example.createCriteria().andEmployeeIdEqualTo(health.getEmployeeId());

        Health healthOld = new Health();
        healthOld.setStatus("0");
        healthMapper.updateByExampleSelective(healthOld, example);

        // 插入新的健康证
        health.setCreateDate(new Timestamp(new Date().getTime()));
        health.setCreateUserid(1);

        health.setStatus("1");
        health.setIsDel("0");

        healthMapper.insertSelective(health);

        // 同步更新员工信息中的健康证信息
        Employee employee = employeeMapper.selectByPrimaryKey(health.getEmployeeId());
        employee.setHealthCode(health.getHealthCode());
        employee.setHealthPath(health.getHealthPath());
        employee.setValidDate(health.getValidDate());

        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 组装查询条件
     * @param health
     * @return
     */
    private HealthExample getCondition(Health health) {
        HealthExample example = new HealthExample();

        if(null != health) {
            HealthExample.Criteria criteria = example.createCriteria();

            if(StringUtil.isNotEmptyObject(health.getEmployeeId())) {
                criteria.andEmployeeIdEqualTo(health.getEmployeeId());
            }

            if(StringUtil.isNotEmptyObject(health.getStatus())) {
                criteria.andStatusEqualTo(health.getStatus());
            }
        }

        return example;
    }
}
