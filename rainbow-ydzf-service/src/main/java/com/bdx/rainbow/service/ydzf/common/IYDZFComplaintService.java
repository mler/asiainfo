package com.bdx.rainbow.service.ydzf.common;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.entity.ydzf.TYdzfComplaint;
import com.bdx.rainbow.urs.entity.SysDeptTree;
import com.bdx.rainbow.urs.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * Created by core on 16/4/12.
 */
public interface IYDZFComplaintService {
    public List<TYdzfComplaint> queryComplaintList(TYdzfComplaint obj, Integer start, Integer count);
    public int countComplaintList(TYdzfComplaint obj);
    public int insertComplaint(TYdzfComplaint obj);
    public int updateCompalit(TYdzfComplaint obj);
    public TYdzfComplaint queryById(Integer complaintId);
    public List<SysDeptTree> getDepts(SysUser user);
}
