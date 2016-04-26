package com.bdx.rainbow.service.mapp;

import com.bdx.rainbow.ygcf.entity.Employee;
import com.bdx.rainbow.ygcf.entity.Health;

/**
 * sk0003接口
 * Created by fusj on 16/3/21.
 */
public interface ISK0003Service {
    /**
     * 新增或修改健康证信息
     * @param employee      员工信息
     * @param health        健康证
     * @param picturePath   头像
     * @param fileUrl       健康证图片
     * @param issuingDate   发证日期
     * @param validity      有效期
     * @throws Exception
     */
    void insertOrUpdateEmployee(Employee employee, Health health, byte[] picturePath, byte[] fileUrl, String issuingDate, String validity, int userId) throws Exception;
}
