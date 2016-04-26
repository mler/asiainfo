package com.bdx.rainbow.service.ydzf.common.impl;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.service.IEnterpriseDubboService;
import com.bdx.rainbow.common.exception.ExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.common.util.SequenceUtil;
import com.bdx.rainbow.entity.ydzf.TYdzfComplaint;
import com.bdx.rainbow.entity.ydzf.TYdzfComplaintExample;
import com.bdx.rainbow.mapper.ydzf.TYdzfComplaintMapper;
import com.bdx.rainbow.service.ydzf.common.IYDZFComplaintService;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.urs.dubbo.IDubDeptService;
import com.bdx.rainbow.urs.entity.SysDeptTree;
import com.bdx.rainbow.urs.entity.SysUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by core on 16/4/12.
 */
@Service
public class YDZFComplaintService  extends BaseService implements IYDZFComplaintService {
    private static final Logger log = LoggerFactory
            .getLogger(YDZFComplaintService.class);
    @Autowired
    private TYdzfComplaintMapper complaintMapper;
    @Autowired
    private IEnterpriseDubboService enterpriseService;
    @Autowired
    private IDubDeptService dubDeptService;
    @Override
    public List<TYdzfComplaint> queryComplaintList(TYdzfComplaint obj, Integer start, Integer count) {
        TYdzfComplaintExample example=convertOutExample(obj);
        if (start!=null){
            example.setLimitClauseStart(start);
            example.setLimitClauseCount(count);
        }
        return complaintMapper.selectByExample(example);
    }

    @Override
    public int countComplaintList(TYdzfComplaint obj) {
        TYdzfComplaintExample example=convertOutExample(obj);
        return complaintMapper.countByExample(example);
    }


    @Override
    public int insertComplaint(TYdzfComplaint obj) {
        obj.setComplaintChannel(1);
        obj.setComplaintUnitType(1);
        obj.setComplaintSkuId(-1);
        obj.setComplaintStatus(0);
        obj.setComplaintInspectStatus(0);
        obj.setIsDel("0");
        obj.setComplaintCreatDate(DateUtil.getCurrent());
        obj.setComplaintCode("TS"+ SequenceUtil.getSequence());
        obj.setCreateDate(DateUtil.getCurrent());
        return complaintMapper.insertSelective(obj);
    }

    @Override
    public int updateCompalit(TYdzfComplaint obj) {
        obj.setUpdateDate(DateUtil.getCurrent());
        return complaintMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public TYdzfComplaint queryById(Integer complaintId) {
        return complaintMapper.selectByPrimaryKey(complaintId);
    }

    @Override
    public List<SysDeptTree> getDepts(SysUser user) {
        List<SysDeptTree> list=new ArrayList<SysDeptTree>();
        try {
            list=dubDeptService.getSysDeptTree(user);
        } catch (Exception e) {
            log.error("调用dubbo部门接口错误", e);
        }
        return list;
    }

    private TYdzfComplaintExample convertOutExample(TYdzfComplaint condition){
        TYdzfComplaintExample example=new TYdzfComplaintExample();

        if (condition!=null){
            TYdzfComplaintExample.Criteria cr=example.createCriteria();
            cr.andIsDelEqualTo("0");
            if(condition.getComplaintStatus()!=null&&condition.getComplaintStatus()!=-1){
                cr.andComplaintStatusEqualTo(condition.getComplaintStatus());
            }
            if(condition.getEnterpriseBusinessType()!=null&& StringUtils.isNotBlank(condition.getEnterpriseBusinessType())&&!"-1".equals(condition.getEnterpriseBusinessType())){
                cr.andEnterpriseBusinessTypeEqualTo(condition.getEnterpriseBusinessType());
            }
        }
        return example;
    }
}
