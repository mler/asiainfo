package com.bdx.rainbow.urs.service.dubbo;


import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.bdx.rainbow.common.exception.BaseException;
import com.bdx.rainbow.entity.urs.*;
import com.bdx.rainbow.mapper.urs.SysPowerCommMapper;
import com.bdx.rainbow.mapper.urs.SysRole2UserTypeMapper;
import com.bdx.rainbow.mapper.urs.SysUser2PlatMapper;
import com.bdx.rainbow.mapper.urs.SysUser2RoleMapper;
import com.bdx.rainbow.urs.common.BeanCopyUtil;
import com.bdx.rainbow.urs.common.Constants;
import com.bdx.rainbow.urs.dubbo.IDubUserService;
import com.bdx.rainbow.urs.entity.IUserInfo;

import com.bdx.rainbow.urs.entity.SysUser;
import com.bdx.rainbow.urs.entity.SysUserInfo;
import com.bdx.rainbow.urs.service.IRoleService;
import com.bdx.rainbow.urs.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by core on 16/1/20.
 */
@Service
public class DubUserService implements IDubUserService  ,GenericService {
    @Autowired
    private IUserService userService;
    @Autowired
    private SysPowerCommMapper sysPowerCommMapper;
    @Autowired
    private SysUser2PlatMapper user2PlatMapper;
    @Autowired
    private SysUser2RoleMapper user2RoleMapper;
    @Autowired
    private SysRole2UserTypeMapper role2UserTypeMapper;
    @Autowired
    private IRoleService roleService;

    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args)
            throws GenericException {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public IUserInfo login(String login_name, String pwd,Integer platId, Map<String, Object> sysLogon) throws Exception {
        try{
            if (!StringUtils.isNotBlank(login_name)){
                throw new com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode("8888","用户名不能为空"));
            }
            if(!StringUtils.isNotBlank(pwd)){
                throw new com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode("8888","用户密码不能为空"));
            }
            System.out.println("=====================登陆====================");
            return userService.login(login_name,pwd,platId,sysLogon);
        }catch(Exception e){
           if(e instanceof com.bdx.rainbow.common.exception.BaseException) {
               com.bdx.rainbow.common.exception.BaseException e1=(com.bdx.rainbow.common.exception.BaseException)e;
               throw new com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode( e1.getErrorCode(), e1.getErrorMsg()));
           }else if(e instanceof com.bdx.rainbow.urs.exception.BaseException){
               throw e;
           }else {
               throw new Exception("系统异常！");
           }

        }

    }



    @Override
    public List<SysUser> getUsers(SysUser obj, Integer start, Integer count,SysUserInfo logon) throws Exception {

        List<SysUser> users = new ArrayList<SysUser>();
        com.bdx.rainbow.entity.urs.SysUser con = new com.bdx.rainbow.entity.urs.SysUser();
        if (obj == null) {
            throw new com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode("8888", "查询条件不能为空"));

        }
        if (logon == null) {
            throw new Exception(" 当前用户为空，请传入当前用户信息");
        }
        BeanCopyUtil.copyProperties(obj, con);
        if (!StringUtils.equals(logon.getUser().getAdminUser(), Constants.USER_ADMIN_FLAG)) {
            con.setAdminUser(logon.getUser().getAdminUser());
        } else {
            con.setAdminUser(logon.getUser().getUserName());
        }

        try {
            List<com.bdx.rainbow.entity.urs.SysUser> userList = userService.getUsers(con, start, count);
            for (com.bdx.rainbow.entity.urs.SysUser sysUser : userList) {
                SysUser user = new SysUser();
                sysUser.setLoginPwd(null);
                BeanCopyUtil.copyProperties(sysUser, user);
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            if (e instanceof BaseException) {
                throw new com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode(((BaseException) e).getErrorCode(), ((BaseException) e).getErrorMsg()));
            }else if(e instanceof com.bdx.rainbow.urs.exception.BaseException){
                throw e;
            } else {
                e.printStackTrace();
                throw new Exception("系统异常！");
            }
        }
    }

    @Override
    public Integer insertUser(SysUser obj,Integer platId, String type) throws Exception{
        if (platId == null || StringUtils.isBlank(platId.toString())) {
            throw new com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode("8888"," 当前用户注册平台ID为空，请传入当前用户注册平台ID"));
        }
        Map<String, Object> condition =new HashMap<>();
        condition.put("platId",platId);
        condition.put("userStatus",Constants.USER_STATUS_NORMAL_1);
        condition.put("adminUser",Constants.USER_ADMIN_FLAG);
        List<com.bdx.rainbow.entity.urs.SysUser> users=sysPowerCommMapper.getUsersByPlatId(condition);
        Integer adminId=0;
        if (users!=null&&users.size()>0){
            obj.setAdminUser(users.get(0).getUserName());
            obj.setDeptId(users.get(0).getDeptId());
            adminId=users.get(0).getUserId();
        }else{
            throw new com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode("8888"," 当前用户注册平台无管理员，请联系超级管理员！"));
        }
        com.bdx.rainbow.entity.urs.SysUser newSysUser=new com.bdx.rainbow.entity.urs.SysUser();
        BeanCopyUtil.copyProperties(obj, newSysUser);
        Integer userId=0;
        try {
            userService.insertUser(newSysUser);
            userId=newSysUser.getUserId();
            SysUser2Plat record=new SysUser2Plat();
            record.setPlatId(platId);
            record.setUserId(userId);
            user2PlatMapper.insert(record) ;
            SysUser2Role user2Role=new SysUser2Role();
            user2Role.setUserId(userId);
            if (type==null||StringUtils.isEmpty(type)){
                SysUser2RoleExample example=new SysUser2RoleExample();
                example.createCriteria().andUserIdEqualTo(adminId);
                List<SysUser2Role> roles=user2RoleMapper.selectByExample(example);
                if (roles!=null&&roles.size()>0){
                    user2Role.setRoleId(roles.get(0).getRoleId());
                    user2RoleMapper.insertSelective(user2Role);
                }
            }else{
                SysRole2UserTypeExample typeExample=new SysRole2UserTypeExample();
                typeExample.createCriteria().andUserTypeEqualTo(type);
                List<SysRole2UserType> role2Types=role2UserTypeMapper.selectByExample(typeExample);
                if (role2Types!=null&&role2Types.size()>0){
                    user2Role.setRoleId(role2Types.get(0).getRoleId());
                    user2RoleMapper.insertSelective(user2Role);
                }else{
                    throw new com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode("8888"," 当前用户类型未分配角色，请联系超级管理员！"));
                }
            }
        } catch (Exception e) {
            if (e instanceof BaseException) {
                throw new com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode(((BaseException) e).getErrorCode(), ((BaseException) e).getErrorMsg()));
            }else if(e instanceof com.bdx.rainbow.urs.exception.BaseException){
                throw e;
            } else {
                e.printStackTrace();
                throw new Exception("系统异常！");
            }
        }
        return userId;
    }

    @Override
    public IUserInfo getUserInfoById(Integer userId, Integer platId) throws Exception {

        try{

            return userService.getUserInfoById(userId,platId);
        }catch(Exception e){
            if(e instanceof BaseException) {
                BaseException e1=(BaseException)e;
                throw new com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode( e1.getErrorCode(), e1.getErrorMsg()));
            }else if(e instanceof com.bdx.rainbow.urs.exception.BaseException){
                throw e;
            }else {
                throw new Exception("系统异常！");
            }

        }
    }


}

