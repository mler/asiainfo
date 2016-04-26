package com.bdx.rainbow.service.mapp.impl;

import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.service.mapp.ISK0002Service;
import com.bdx.rainbow.ygcf.entity.Employee;
import com.bdx.rainbow.ygcf.entity.EmployeeExample;
import com.bdx.rainbow.ygcf.mapper.EmployeeMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * sk0002接口
 * Created by fusj on 16/3/21.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class SK0002ServiceImpl implements ISK0002Service {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工列表
     * @param param
     * @return
     */
    @Override
    public List<Employee> selectListByCompanyId(Map param) {
        // 企业编号
        Integer enterpriseId = Integer.parseInt(param.get("enterpriseId").toString());

        // IS_DEL＝0
        EmployeeExample example = new EmployeeExample();
        example.createCriteria().andEnterpriseIdEqualTo(enterpriseId)
                .andIsDelEqualTo("0");

        String pageCount = "0";
        String pageNo = "-1";

        if(StringUtil.isNotEmptyObject(param.get("pageCount"))) {
            pageCount = param.get("pageCount").toString();
        }
        if(StringUtil.isNotEmptyObject(param.get("pageNo"))) {
            pageNo = param.get("pageNo").toString();
        }

        // pageCount=0 && pageNo=-1 表示不分页查询
        if("0".equals(pageCount) && "-1".equals(pageNo)) {
            return employeeMapper.selectByExample(example);
        } else {
            example.setLimitClauseCount(Integer.parseInt(pageCount));
            example.setLimitClauseStart(Integer.parseInt(pageCount) * Integer.parseInt(pageNo));

            return employeeMapper.selectByExample(example);
        }
    }
}
