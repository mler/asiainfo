package com.bdx.rainbow.urs.dubbo;


import com.bdx.rainbow.urs.entity.DeptModel;
import com.bdx.rainbow.urs.entity.SysDeptTree;
import com.bdx.rainbow.urs.entity.SysUser;
import com.bdx.rainbow.urs.entity.SysUserInfo;
import com.bdx.rainbow.urs.exception.BusinessException;
import com.bdx.rainbow.urs.exception.SystemException;

import java.util.List;
import java.util.Map;

/**
 * Created by core on 16/1/21.
 */
public interface IDubDeptService {
    /**
     * 根据部门Id返回部门
     * @param deptId
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    DeptModel getDeptAndExtById(Integer deptId) throws Exception;

    /**
     * 根据当前用户的管理员信息返回所有部门信息
     * @param user
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    List<SysDeptTree> getSysDeptTree(SysUser user)
            throws Exception;

    /**
     * 返回当前用户同级部门或下级部门
     * @param logon 当前登陆用户
     * @return
     */
    List<SysDeptTree> getDeptByLogon(SysUserInfo logon) throws Exception;
}
