package com.bdx.rainbow.service.sk.impl;

import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.ExceptionCode;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.service.sk.IEmployeeService;
import com.bdx.rainbow.ygcf.entity.Employee;
import com.bdx.rainbow.ygcf.entity.EmployeeExample;
import com.bdx.rainbow.ygcf.entity.Health;
import com.bdx.rainbow.ygcf.mapper.EmployeeMapper;
import com.bdx.rainbow.ygcf.mapper.HealthMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业信息
 * Created by fusj on 16/3/2.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private HealthMapper healthMapper;

    /**
     * 列表数据
     * @param employee
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo list(Employee employee, PageInfo pageInfo, List<Integer> enterpriseIdList) {
        employee.setIsDel("0");

        EmployeeExample example = getCondition(employee, enterpriseIdList);

        if(null != pageInfo && null != pageInfo.getPageStart()) {
            example.setLimitClauseStart(pageInfo.getPageStart());
            example.setLimitClauseCount(pageInfo.getPageCount());
        }

        List<Employee> employeeList = employeeMapper.selectByExample(example);
        int totalCount = employeeMapper.countByExample(example);

        pageInfo.setList(employeeList);
        pageInfo.setTotalCount(totalCount);

        return pageInfo;
    }

    /**
     * 根据主键查询对象
     * @param employeeId
     * @return
     */
    @Override
    public Employee get(Integer employeeId) {
        if(!StringUtil.isNotEmptyObject(employeeId)) {
            return new Employee();
        }

        Employee employee = employeeMapper.selectByPrimaryKey(employeeId);

        return employee;
    }

    /**
     * 编辑保存
     * @param employee
     */
    @Override
    public void edit(Employee employee) throws Exception {
        if(!StringUtil.isNotEmptyObject(employee.getEmployeeId())) {
            throw new BusinessException(new DefaultExceptionCode("500", "员工编号为空"));
        }

        Employee employeeData = employeeMapper.selectByPrimaryKey(employee.getEmployeeId());

        employeeData.setFullName(employee.getFullName());
        employeeData.setSex(employee.getSex());
        employeeData.setIdType(employee.getIdType());
        employeeData.setIdCode(employee.getIdCode());
        employeeData.setBirthday(employee.getBirthday());
        employeeData.setNation(employee.getNation());
        employeeData.setCitizenship(employee.getCitizenship());
        employeeData.setPoliticalLandscape(employee.getPoliticalLandscape());
        employeeData.setEducation(employee.getEducation());
        employeeData.setSpecialty(employee.getSpecialty());
        employeeData.setGraduationTime(employee.getGraduationTime());
        employeeData.setPost(employee.getPost());
        employeeData.setJobTitle(employee.getJobTitle());
        employeeData.setFirsttimeWork(employee.getFirsttimeWork());
        employeeData.setWorkExperience(employee.getWorkExperience());
        employeeData.setPersonalPhone(employee.getPersonalPhone());
        employeeData.setPersonalAddredd(employee.getPersonalAddredd());
        employeeData.setIsneedHealth(employee.getIsneedHealth());
        employeeData.setHouseholdRegister(employee.getHouseholdRegister());

        employeeData.setUpdateDate(new Timestamp(new Date().getTime()));

        employeeMapper.updateByPrimaryKey(employeeData);
    }

    /**
     * 新增员工信息
     * @param employee
     * @param health
     * @return
     */
    @Override
    public int addEmployee(Employee employee, Health health) {
        employee.setEnterpriseId(1);
        employee.setIsDel("0");
        employee.setCreateDate(new Timestamp(new Date().getTime()));
        employee.setCreateUserid(1);

        employeeMapper.insertSelective(employee);

        health.setEmployeeId(employee.getEmployeeId());
        health.setStatus("1");
        health.setIsDel("0");
        health.setCreateDate(new Timestamp(new Date().getTime()));
        health.setCreateUserid(1);

        health.setHealthPath("R2015121616411963904");

        healthMapper.insertSelective(health);

        return employee.getEmployeeId();
    }

    /**
     * 删除
     * @param employee
     */
    @Override
    public void del(Employee employee) {
        employee.setIsDel("1");

        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 健康证数量统计
     * @param enterpriseId
     * @return
     */
    @Override
    public Map<String, Integer> healthStat(Integer enterpriseId) {

        Map<String, Integer> resultMap = new HashMap<>();

        // 办理健康证人数
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEnterpriseIdEqualTo(enterpriseId);
        criteria.andHealthCodeIsNotNull();
        criteria.andHealthCodeNotEqualTo("");

        int count1 = employeeMapper.countByExample(example);

        // 未办理健康证人数
        example = new EmployeeExample();

        criteria = example.createCriteria();
        criteria.andEnterpriseIdEqualTo(enterpriseId);
        criteria.andIsneedHealthEqualTo("1");
        criteria.andHealthCodeEqualTo("");

        EmployeeExample.Criteria criteria1 = example.createCriteria();
        criteria1.andEnterpriseIdEqualTo(enterpriseId);
        criteria1.andIsneedHealthEqualTo("1");
        criteria1.andHealthCodeIsNull();

        example.or(criteria1);

        int count2 = employeeMapper.countByExample(example);

        // 健康证到期预警
        example = new EmployeeExample();

        criteria = example.createCriteria();
        criteria.andEnterpriseIdEqualTo(enterpriseId);
        criteria.andIsneedHealthEqualTo("1");
        criteria.andValidDateLessThan(DateUtil.getTimestamp(DateUtil.getNextMonth()));
        criteria.andValidDateGreaterThanOrEqualTo(DateUtil.getTimestamp(DateUtil.getDateString(DateUtil.DATE_FORMAT2)));

        int count3 = employeeMapper.countByExample(example);

        // 健康证到期
        example = new EmployeeExample();

        criteria = example.createCriteria();
        criteria.andEnterpriseIdEqualTo(enterpriseId);
        criteria.andIsneedHealthEqualTo("1");
        criteria.andValidDateLessThan(DateUtil.getTimestamp(DateUtil.getDateString(DateUtil.DATE_FORMAT2)));

        int count4 = employeeMapper.countByExample(example);

        resultMap.put("health1", count1);
        resultMap.put("health2", count2);
        resultMap.put("health3", count3);
        resultMap.put("health4", count4);

        return resultMap;
    }

    /**
     * 统计数量显示健康证信息
     * @param employee
     * @param type
     * @return
     */
    @Override
    public List<Employee> statList(Employee employee, String type) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();

        int enterpriseId = employee.getEnterpriseId();
        if("1".equals(type)) {
            // 办理健康证人数
            criteria.andEnterpriseIdEqualTo(enterpriseId);
            criteria.andHealthCodeIsNotNull();
            criteria.andHealthCodeNotEqualTo("");
        } else if ("2".equals(type)) {
            criteria.andEnterpriseIdEqualTo(enterpriseId);
            criteria.andIsneedHealthEqualTo("1");
            criteria.andHealthCodeEqualTo("");

            EmployeeExample.Criteria criteria1 = example.createCriteria();
            criteria1.andEnterpriseIdEqualTo(enterpriseId);
            criteria1.andIsneedHealthEqualTo("1");
            criteria1.andHealthCodeIsNull();

            example.or(criteria1);
        } else if("3".equals(type)) {
            criteria.andEnterpriseIdEqualTo(enterpriseId);
            criteria.andIsneedHealthEqualTo("1");
            criteria.andValidDateLessThan(DateUtil.getTimestamp(DateUtil.getNextMonth()));
            criteria.andValidDateGreaterThanOrEqualTo(DateUtil.getTimestamp(DateUtil.getDateString(DateUtil.DATE_FORMAT2)));
        } else if ("4".equals(type)) {
            criteria.andEnterpriseIdEqualTo(enterpriseId);
            criteria.andIsneedHealthEqualTo("1");
            criteria.andValidDateLessThan(DateUtil.getTimestamp(DateUtil.getDateString(DateUtil.DATE_FORMAT2)));
        }

        List<Employee> list = employeeMapper.selectByExample(example);

        return list;
    }

    /**
     * 查询条件拼装
     * @param employee
     * @return
     */
    private EmployeeExample getCondition(Employee employee, List<Integer> enterpriseIds) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();

        if(null != employee) {

            if(StringUtil.isNotEmptyObject(employee.getEnterpriseId())) {
                criteria.andEnterpriseIdEqualTo(employee.getEnterpriseId());
            }

            if(StringUtil.isNotEmptyObject(employee.getFullName())) {
                criteria.andFullNameLike("%" + employee.getFullName() + "%");
            }

            if(StringUtil.isNotEmptyObject(employee.getPost())) {
                criteria.andPostLike("%" + employee.getPost() + "%");
            }

            if(StringUtil.isNotEmptyObject(employee.getJobTitle())) {
                criteria.andJobTitleLike("%" + employee.getJobTitle() + "%");
            }

            if(StringUtil.isNotEmptyObject(employee.getIsDel())) {
                criteria.andIsDelEqualTo(employee.getIsDel());
            }
        }

        // 企业名称查询，先调用dubbo接口查询企业列表
        if(!CollectionUtils.isEmpty(enterpriseIds)) {
            criteria.andEnterpriseIdIn(enterpriseIds);
        }

        return example;
    }
}
