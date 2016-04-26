package com.bdx.rainbow.urs.dubbo;

import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.urs.entity.SysUser;
import com.bdx.rainbow.urs.entity.SysUserInfo;
import com.bdx.rainbow.urs.exception.BusinessException;
import com.bdx.rainbow.urs.exception.SystemException;

import java.util.List;
import java.util.Map;

/**
 * Created by core on 16/1/20.
 */
public interface IDubUserService {
    /**
     * 用户登陆
     * @param login_name
     * @param pwd
     * @param platId 平台ID
     * @param sysLogon
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    IUserInfo login(String login_name, String pwd,Integer platId,Map<String, Object> sysLogon) throws Exception;
    /**
     * 用户查询, 支持分页
     * obj为null时，则条件为空
     * condition dept_id, user_name(like), user_login_name(like), email, user_type, user_status
     * @param obj
     * @param start
     * @param count
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    List<SysUser> getUsers(SysUser obj, Integer start, Integer count,SysUserInfo logon) throws Exception;

    /**
     * 用户新增
     * @param obj
     * @param type 类型 用于角色判断
     * @return
     */
    Integer insertUser(SysUser obj,Integer platId,String type)throws Exception;

    IUserInfo  getUserInfoById(Integer userId,Integer platId)throws Exception;


}
