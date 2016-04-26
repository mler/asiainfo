package com.bdx.rainbow.service.sk;

import com.bdx.rainbow.ygcf.entity.Health;

import java.util.List;

/**
 * 健康证
 * Created by fusj on 16/3/3.
 */
public interface IHealthService {

    /**
     * 根据员工编号，查询有效的健康证信息
     * @param employeeId
     * @return
     */
    Health selectByEmployeeId(Integer employeeId) throws Exception;

    /**
     * 根据员工编号，查询所有健康证信息
     * @param employeeId
     * @return
     */
    List<Health> selectAllByEmployeeId(Integer employeeId);

    /**
     * 健康证换证
     * @param health
     */
    void change(Health health) throws Exception;
}
