package com.bdx.rainbow.service.mapp;

import com.bdx.rainbow.ygcf.entity.Employee;

import java.util.List;
import java.util.Map;

/**
 * sk0002接口
 * Created by fusj on 16/3/21.
 */
public interface ISK0002Service {
    /**
     * 员工列表
     * @param param
     * @return
     */
    List<Employee> selectListByCompanyId(Map param);
}
