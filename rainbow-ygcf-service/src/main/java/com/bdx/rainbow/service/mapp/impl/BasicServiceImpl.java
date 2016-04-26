package com.bdx.rainbow.service.mapp.impl;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.service.IEnterpriseDubboService;
import com.bdx.rainbow.service.mapp.IBasicService;
import com.bdx.rainbow.urs.dubbo.IDubDeptService;
import com.bdx.rainbow.urs.dubbo.IDubDicService;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.urs.entity.ParamDetail;
import com.bdx.rainbow.urs.entity.SysDept;
import com.bdx.rainbow.urs.entity.SysDeptTree;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础通用接口
 * Created by fusj on 16/3/21.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class BasicServiceImpl implements IBasicService {

    @Autowired
    private IEnterpriseDubboService enterpriseDubboService;

    @Autowired
    private IDubDicService dubDicService;

    @Autowired
    private IDubDeptService deptService;

    /**
     * 根据ID获取企业信息
     * @param corpId
     * @return
     */
    @Override
    public DubboEnterpriseInfo getEnterpriseById(Integer corpId) throws Exception {
        Map<String, Object> resultMap = enterpriseDubboService.getEnterpriseDetailById(corpId);

        DubboEnterpriseInfo enterpriseInfo = (DubboEnterpriseInfo) resultMap.get("info");

        return enterpriseInfo;
    }

    /**
     * 根据KEY获取基础数据
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> findCacheByKey(String key) throws Exception {
        List<ParamDetail> list = dubDicService.findCacheByKey(key);

        Map<String, String> result = new HashMap<>();

        if(!CollectionUtils.isEmpty(list)) {
            for(ParamDetail paramDetail : list) {
                result.put(paramDetail.getpValue(), paramDetail.getpDesc());
            }
         }

        return result;
    }

    /**
     * 获取当前用户下所有的组织机构
     * @param sysUserInfo
     * @return
     */
    @Override
    public Map<String, String> getSysDeptTree(IUserInfo sysUserInfo) throws Exception {
        List<SysDeptTree> list = deptService.getSysDeptTree(sysUserInfo.getUser());

        Map<String, String> result = new HashMap<>();
        if(!CollectionUtils.isEmpty(list)) {
            for(SysDeptTree sysDeptTree : list) {
                result.put(sysDeptTree.getDeptId() + "", sysDeptTree.getDeptName());
            }
        }

        return result;
    }
}
