package com.bdx.rainbow.urs.service.dubbo;

import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.bdx.rainbow.common.exception.BaseException;
import com.bdx.rainbow.entity.urs.SysDeptExt;
import com.bdx.rainbow.entity.urs.SysDeptModel;
import com.bdx.rainbow.urs.common.BeanCopyUtil;
import com.bdx.rainbow.urs.common.Constants;
import com.bdx.rainbow.urs.dubbo.IDubDeptService;
import com.bdx.rainbow.urs.entity.DeptModel;
import com.bdx.rainbow.urs.entity.SysDeptTree;
import com.bdx.rainbow.urs.entity.SysUser;
import com.bdx.rainbow.urs.entity.SysUserInfo;
import com.bdx.rainbow.urs.exception.BusinessException;
import com.bdx.rainbow.urs.exception.DefaultExceptionCode;
import com.bdx.rainbow.urs.exception.SystemException;
import com.bdx.rainbow.urs.service.IDeptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by core on 16/1/21.
 */
@Service
public class DubDeptService implements IDubDeptService ,GenericService {
    @Autowired
    private IDeptService deptService;

    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args)
            throws GenericException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DeptModel getDeptAndExtById(Integer deptId) throws Exception {
        try {
          if (deptId==null){
              throw new BusinessException(new DefaultExceptionCode("8888","参数deptId不能为空！"));
          }
          SysDeptModel model= deptService.getDeptAndExtById(deptId);
            if (model==null){
                throw new BusinessException(new DefaultExceptionCode("8888","没有找到对应参数deptId部门信息！"));
            }
          DeptModel  deptModel=new DeptModel();
          deptModel.setDeptId(model.getSc().getDeptId());
            deptModel.setAdmin(model.getSc().getAdmin());
            deptModel.setAreaid(model.getSc().getAreaid());
            deptModel.setCountyid(model.getSc().getCountyid());
            deptModel.setDeptId(deptId);
            deptModel.setDeptName(model.getSc().getDeptName());
            deptModel.setDeptPid(model.getSc().getDeptPid());
            deptModel.setProvince(model.getSc().getProvince());
            deptModel.setDeptStatus(model.getSc().getDeptStatus());
            deptModel.setDeptType(model.getSc().getDeptType());
          if (model.getScExts()!=null&&model.getScExts().size()>0){
              Map<String,String> deptExts=new HashMap<String,String>();
              for (SysDeptExt ext:model.getScExts()) {
                  deptExts.put(ext.getExtKey(),ext.getExtValue());
              }
              deptModel.setDeptExts(deptExts);
          }
            return deptModel;
        } catch (Exception e) {
            if(e instanceof BaseException) {
                BaseException e1=(BaseException)e;

                throw new BusinessException(new DefaultExceptionCode(e1.getErrorCode(),e1.getErrorMsg()));
            }else if(e instanceof com.bdx.rainbow.urs.exception.BaseException){
                throw e;
            }else {
                throw new Exception("系统异常！");
            }
        }
    }

    @Override
    public List<SysDeptTree> getSysDeptTree(SysUser user) throws Exception {
        Map<String, Object> condition=new HashMap<>();
        if (!StringUtils.equals(user.getAdminUser(), Constants.USER_ADMIN_FLAG)){
            condition.put("ADMIN",user.getAdminUser());
        }else{
            condition.put("ADMIN",user.getUserName());
        }
        condition.put("DEPT_STATUS",Constants.DEPT_STATUS_NORMAL_1);
        List<SysDeptTree> trees=new ArrayList<SysDeptTree>();
        try {
            List<com.bdx.rainbow.entity.urs.SysDeptTree> dest = deptService.getSysDeptTree(condition);
            for (com.bdx.rainbow.entity.urs.SysDeptTree dept:dest){
                SysDeptTree tree=new SysDeptTree();
                BeanCopyUtil.copyProperties(dept,tree);
                trees.add(tree);
            }
        } catch (Exception e) {
            if(e instanceof BaseException) {
                BaseException e1=(BaseException)e;
                throw new BusinessException(new DefaultExceptionCode(e1.getErrorCode(),e1.getErrorMsg()));
            }else if(e instanceof com.bdx.rainbow.urs.exception.BaseException){
                throw e;
            }else {
                e.printStackTrace();
                throw new Exception("系统异常！");
            }
        }
        return trees;
    }

    @Override
    public List<SysDeptTree> getDeptByLogon(SysUserInfo logon) throws Exception{
        if (logon.getUser().getDeptId()==null||logon.getUser().getDeptId()==0){
            throw new BusinessException(new DefaultExceptionCode("8888"," 用户的部门信息为空，请联系系统管理员！"));
        }
        SysDeptModel model= deptService.getDeptAndExtById(logon.getUser().getDeptId());
        Map<String, Object> condition=new HashMap<>();
        SysUser user=logon.getUser();
        if (!StringUtils.equals(user.getAdminUser(), Constants.USER_ADMIN_FLAG)){
            condition.put("ADMIN",user.getAdminUser());
        }else{
            condition.put("ADMIN",user.getUserName());
        }
        condition.put("DEPT_STATUS",Constants.DEPT_STATUS_NORMAL_1);
        List<SysDeptTree> trees=new ArrayList<SysDeptTree>();
        try {
            List<com.bdx.rainbow.entity.urs.SysDeptTree> dest = deptService.getSysDeptTree(condition);
            if (model.getSc().getDeptPid()==0){
                for (com.bdx.rainbow.entity.urs.SysDeptTree dept:dest){
                    if (dept.getDeptPid()==model.getSc().getDeptId()){
                        SysDeptTree tree=new SysDeptTree();
                        BeanCopyUtil.copyProperties(dept,tree);
                        trees.add(tree);
                    }
                }
            }else{
                for (com.bdx.rainbow.entity.urs.SysDeptTree dept:dest){
                    if(dept.getDeptPid()==model.getSc().getDeptPid()){
                        SysDeptTree tree=new SysDeptTree();
                        BeanCopyUtil.copyProperties(dept,tree);
                        trees.add(tree);
                    }
                }

            }

        } catch (Exception e) {
            if(e instanceof BaseException) {
                BaseException e1=(BaseException)e;
                throw new BusinessException(new DefaultExceptionCode(e1.getErrorCode(),e1.getErrorMsg()));
            }else if(e instanceof com.bdx.rainbow.urs.exception.BaseException){
                throw e;
            }else {
                e.printStackTrace();
                throw new Exception("系统异常！");
            }
        }
        return trees;
    }
}
