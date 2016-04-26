package com.bdx.rainbow.mapp.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.common.configuration.Constants;
import com.bdx.rainbow.urs.exception.BusinessException;
import com.bdx.rainbow.urs.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.urs.entity.SysUser;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.model.BasicMappUser;
import com.bdx.rainbow.mapp.model.req.YZ0007Request;
import com.bdx.rainbow.mapp.model.rsp.YZ0007Response;
import com.bdx.rainbow.service.IPhoneUserService;
import com.bdx.rainbow.service.IUserService;
import com.bdx.rainbow.urs.dubbo.IDubUserService;

/**
 * 注册
 * @author zhengwenjuan
 *
 */
@Service("yz0007")
@Action(bizcode="yz0007",version="1.0",usercheck=false, ipcheck=false)
@Scope("prototype")
public class YZ0007Action extends AbstractMappAction<YZ0007Request, YZ0007Response> implements ApplicationContextAware {
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IPhoneUserService phoneUserService;
	
	private ApplicationContext context;
	
	@Autowired
	private IDubUserService dubUserService;
	
	@Override
	public void doAction(YZ0007Request request, YZ0007Response response) throws Exception {
		/**
		//直接从session中获取用户信息
		IUserInfo userInfo = (IUserInfo) MappContext.getContext().get(MappContext.MAPPCONTEXT_USER);
		
		if (userInfo == null && StringUtils.equals(request.getOptType(), "1")) {
			throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.NO_USER_INFO.getCode(), Constants.ErrorType.NO_USER_INFO.getMsg()));
		}
		
		if (userInfo == null && StringUtils.equals(request.getOptType(), "0")) {
			SysUser sysUser = new SysUser();
			sysUser.setUserLoginName(request.getUserLoginName());
			sysUser.setUserName(request.getUserName());
			sysUser.setLoginPwd(request.getPwd());
			sysUser.setMobilePhone(request.getMobilePhone());
			sysUser.setUserStatus("1");
			sysUser.setDeptId(1);
			sysUser.setUserType(request.getUserType());
			sysUser.setCreater(Constants.UNKNOW_USER_ID);
			sysUser.setCreateTime(DateUtil.getCurrent());
//			sysUser.setUserStatus(Constants.COMMON_STATUS_VALID);
			boolean isExists = userService.isThisLoginNameExsit(sysUser.getUserLoginName());
			if (isExists) {
				throw new BusinessException(new DefaultExceptionCode("9999","用户已存在"));
			}
			System.out.println("还能中文啊");
			try {
				int userId = userService.createUser(sysUser, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
			
		}
		
		if (StringUtils.equals(request.getOptType(), "1")) {//修改
			
			SysUser sysUser = new SysUser();
			sysUser.setUserId(userInfo.getUserId());
			sysUser.setLoginPwd(request.getPwd());
			sysUser.setUserLoginName(request.getUserLoginName());
			sysUser.setUserName(request.getUserName());
			sysUser.setMobilePhone(request.getMobilePhone());
			sysUser.setUserStatus("1");
			sysUser.setDeptId(1);
			sysUser.setUserType(request.getUserType());
			sysUser.setUpdater(userInfo.getUserId());
			
			userService.updateUser(sysUser);
		}*/
		
		//直接从session中获取用户信息
//			TMappUser userInfo = (TMappUser) MappContext.getContext().get(MappContext.MAPPCONTEXT_USER);
			BasicMappUser userInfo = (BasicMappUser)  MappContext.getContext().get(MappContext.MAPPCONTEXT_USER);
			
			if (userInfo == null && StringUtils.equals(request.getOptType(), "1")) {
				throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.NO_USER_INFO.getCode(), Constants.ErrorType.NO_USER_INFO.getMsg()));
			}
			
			if (userInfo == null && StringUtils.equals(request.getOptType(), "0")) {
//				TMappUser sysUser = new TMappUser();
				SysUser sysUser = new SysUser();
				sysUser.setUserLoginName(request.getUserLoginName());
				sysUser.setUserName(request.getUserName());
				sysUser.setLoginPwd(request.getPwd());
				sysUser.setMobilePhone(request.getMobilePhone());
				sysUser.setUserStatus("1");
				sysUser.setDeptId(1);
				sysUser.setUserType(request.getUserType());
				sysUser.setCreater(Constants.UNKNOW_USER_ID);
				sysUser.setCreateTime(DateUtil.getCurrent());
//					sysUser.setUserStatus(Constants.COMMON_STATUS_VALID);
			/**	boolean isExists = phoneUserService.ifloginNameExists(sysUser.getUserLoginName());
				if (isExists) {
					throw new BusinessException(new DefaultExceptionCode("9999","用户已存在"));
				}
//				System.out.println("还能中文啊");
				try {
					phoneUserService.createMappUser(sysUser);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				*/
				dubUserService.insertUser(sysUser, YZ0006Action.PLATID, "009");
			}
			

	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		context = context;
		
	}
}
