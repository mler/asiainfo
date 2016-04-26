package com.bdx.rainbow.service.mapp.impl;

import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.entity.vfs.FileServerConfig;
import com.bdx.rainbow.service.mapp.ISK0003Service;
import com.bdx.rainbow.service.vfs.IFile;
import com.bdx.rainbow.ygcf.entity.Employee;
import com.bdx.rainbow.ygcf.entity.Health;
import com.bdx.rainbow.ygcf.entity.HealthExample;
import com.bdx.rainbow.ygcf.mapper.EmployeeMapper;
import com.bdx.rainbow.ygcf.mapper.HealthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * sk0003接口
 * Created by fusj on 16/3/21.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class SK0003ServiceImpl implements ISK0003Service {

    private static final Logger logger = LoggerFactory.getLogger(SK0003ServiceImpl.class);

    @Autowired
    private IFile fileService;

    @Autowired
    private VfsSetting vfsSetting;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private HealthMapper healthMapper;

    /**
     * 新增或修改健康证信息
     * @param employee      员工信息
     * @param health        健康证
     * @param picturePath   头像
     * @param fileUrl       健康证图片
     * @param issuingDate   发证日期 yyyy-MM-dd
     * @param validity      有效期 yyyy-MM-dd
     * @throws Exception
     */
    @Override
    public void insertOrUpdateEmployee(Employee employee, Health health, byte[] picturePath, byte[] fileUrl, String issuingDate, String validity, int userId) throws Exception {
        logger.debug("issuingDate>>>>>>>>>>>>" + issuingDate);
        logger.debug("validity>>>>>>>>>>>>>>>" + validity);

        // vfs上传配置
        FileServerConfig config = fileService.getServerConfig(vfsSetting.getServerName());
        String healthPath = (fileUrl == null) ? null : fileService.uploadFile(config, fileUrl, "jpg", userId + "_" + System.currentTimeMillis()+"_" + Math.random(), userId, false, true);

        // 过期日期
        Timestamp validDate = DateUtil.getTimestamp(validity, DateUtil.DATE_FORMAT2);
        // 发证日期
        Timestamp issueDate = DateUtil.getTimestamp(issuingDate, DateUtil.DATE_FORMAT2);

        // 修改
        if(StringUtil.isNotEmptyObject(employee.getEmployeeId())) {
            // 健康证图片
            employee.setHealthPath(healthPath);
            employee.setValidDate(validDate);

            employee.setUpdateUserid(userId);
            employee.setUpdateDate(DateUtil.getCurrent());

            employeeMapper.updateByPrimaryKeySelective(employee);

            // 把以前健康证的状态设置为失效
            HealthExample example = new HealthExample();
            example.createCriteria().andEmployeeIdEqualTo(employee.getEmployeeId())
                    .andStatusEqualTo("1");

            Health healthData = new Health();
            healthData.setStatus("0");

            healthMapper.updateByExampleSelective(healthData, example);
        }
        // 新增
        else {
            // 健康证图片
            employee.setHealthPath(healthPath);
            employee.setValidDate(validDate);

            // 默认审核通过
            employee.setAuditStatus("1");
            // 来源手机端
            employee.setFromType("2");
            employee.setIsDel("0");
            employee.setCreateUserid(userId);
            employee.setCreateDate(DateUtil.getCurrent());

            employeeMapper.insertSelective(employee);
        }

        // 健康证信息
        health.setEmployeeId(employee.getEmployeeId());
        health.setValidDate(validDate);
        health.setIssueDate(issueDate);
        health.setHealthPath(healthPath);
        health.setStatus("1");
        health.setIsDel("0");
        health.setCreateUserid(userId);
        health.setCreateDate(DateUtil.getCurrent());

        healthMapper.insertSelective(health);
    }
}
