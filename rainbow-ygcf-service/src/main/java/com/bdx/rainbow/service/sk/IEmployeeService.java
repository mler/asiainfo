package com.bdx.rainbow.service.sk;

import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.ygcf.entity.Employee;
import com.bdx.rainbow.ygcf.entity.Health;

import java.util.List;
import java.util.Map;

/**
 * 企业员工接口
 * Created by fusj on 16/3/2.
 */
public interface IEmployeeService {

    /**
     * 列表数据
     * @param employee
     * @param pageInfo
     * @return
     */
    PageInfo list(Employee employee, PageInfo pageInfo, List<Integer> enterpriseIdList);

    /**
     * 根据主键查询对象
     * @param employeeId
     * @return
     */
    Employee get(Integer employeeId);

    /**
     * 编辑保存
     * @param employee
     */
    void edit(Employee employee) throws Exception;

    /**
     * 新增员工信息
     * @param employee
     * @param health
     * @return
     */
    int addEmployee(Employee employee, Health health);

    /**
     * 删除
     * @param employee
     */
    void del(Employee employee);

    /**
     * 健康证数量统计
     * @param enterpriseId
     * @return
     */
    Map<String,Integer> healthStat(Integer enterpriseId);

    /**
     * 统计数量显示健康证信息
     * @param employee
     * @param type
     * @return
     */
    List<Employee> statList(Employee employee, String type);
}
