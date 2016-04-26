package com.bdx.rainbow.mapp.action;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.common.SystemException;
import com.bdx.rainbow.common.configuration.Constants;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.ActionException;
import com.bdx.rainbow.mapp.model.BasicMappUser;
import com.bdx.rainbow.mapp.model.req.YZ0006Request;
import com.bdx.rainbow.mapp.model.rsp.YZ0006Response;
import com.bdx.rainbow.service.IPhoneUserService;
import com.bdx.rainbow.urs.dubbo.IDubUserService;


/**
 * 登陆
 * @author zhengwenjuan
 *
 */
@Service("yz0006")
@Action(bizcode="yz0006",version="1.0",usercheck=false, ipcheck=false)
@Scope("prototype")
public class YZ0006Action extends AbstractMappAction<YZ0006Request, YZ0006Response>{
	
	@Autowired
	private IPhoneUserService phoneUserService;
	
	@Autowired
	private IDubUserService dubUserService;
	
	public static final Integer PLATID = 8;
	@Override
	public void doAction(YZ0006Request request, YZ0006Response response) throws Exception {
//		doCheckModel();
		if(StringUtils.isEmpty(request.getUserLoginName())){
//			throw new BusinessException(new DefaultExceptionCode("9999", "用户名不能为空"));
			throw new ActionException("9999", "用户名不能为空");
		}

		if(StringUtils.isEmpty(request.getPwd())){
//			throw new BusinessException(new DefaultExceptionCode("9999", "密码不能为空"));
			throw new ActionException("9999", "密码不能为空");
		}
		Map<String, Object> sysLogon = new HashMap<String, Object>();
		sysLogon.put(Constants.SYS_LOGON_LOGONIP,MappContext.getAttribute(MappContext.MAPPCONTEXT_REQUEST_IP));
		sysLogon.put(Constants.SYS_LOGON_SERVERIP, null);
		sysLogon.put(Constants.SYS_LOGON_IMEI, request.getImei());
		sysLogon.put(Constants.SYS_LOGON_VERSION, request.getVersion());
		sysLogon.put(Constants.SYS_LOGON_BRAND, request.getBrand());
		sysLogon.put(Constants.SYS_LOGON_MODEL, request.getModel());
		sysLogon.put(Constants.SYS_LOGON_OS, request.getOs());
		sysLogon.put(Constants.SYS_LOGON_LOGONDEVICE,request.getDeviceType());
		sysLogon.put(Constants.SYS_LOGON_LOGONSYS, request.getLogonSys());
		
		/**
		IUserInfo user = phoneUserService.mappLogin(request.getUserLoginName(), request.getPwd(), sysLogon);
		
		String userType = user.getUser().getUserType();
		
		MappContext.setAttribute(MappContext.MAPPCONTEXT_USER, user);
		MappContext.setAttribute(MappContext.MAPPCONTEXT_IMEI , request.getImei());
		MappContext.setAttribute(MappContext.MAPPCONTEXT_DEVICE , request.getDeviceType());
		response.setImg("");
		response.setUserType(userType);
		
		String sessionId = (String) MappContext.getAttribute(MappContext.MAPPCONTEXT_SESSIONID);
		response.setSessionId(sessionId == null ? "":sessionId);
		
		
		List<YZ0006Response.Menu> menus = new ArrayList<YZ0006Response.Menu>(); 
		
		if(user != null && user.getuMenus() != null && user.getuMenus().isEmpty() == false)
		{
			YZ0006Response.Menu menuTmp = null;
			for(com.bdx.rainbow.entity.sys.SysMenu menu : user.getuMenus())
			{
				//TODO 判断是否为客户端菜单
				menuTmp = new YZ0006Response.Menu();
//				menuTmp.setCss(menu.get);
				menuTmp.setMenuId(menu.getMenuId());
				menuTmp.setMenuName(menu.getMenuName());
				menuTmp.setMenuUrl(menu.getMenuUrl());
				menuTmp.setTarget(menu.getoTarget());
				menuTmp.setMenuCode(menu.getMenuCode());
				menus.add(menuTmp);
			}
			
			response.setMenus(menus);
			
//			MappContext.setAttribute(MappContext.MAPPCONTEXT_RIGHT, rights);
 
		}	

		TMappUser user = phoneUserService.mappLogin1(request.getUserLoginName(), request.getPwd(), sysLogon);
		*/

		
		try {
			com.bdx.rainbow.urs.entity.IUserInfo userInfo = dubUserService.login(request.getUserLoginName(), request.getPwd(), PLATID,sysLogon);
			String userType = userInfo.getUser().getUserType();
			
			
			MappContext.setAttribute(MappContext.MAPPCONTEXT_IMEI , request.getImei());
			MappContext.setAttribute(MappContext.MAPPCONTEXT_DEVICE , request.getDeviceType());
			response.setImg("");
			response.setUserType(userType);
			
			BasicMappUser sessionDetail = new BasicMappUser();
			sessionDetail.setId(userInfo.getUser().getUserId());
			sessionDetail.setCorpId(userInfo.getUser().getCorpId());
			sessionDetail.setUserType(userInfo.getUser().getUserType());
			sessionDetail.setUserInfo(userInfo);
			MappContext.setAttribute(MappContext.MAPPCONTEXT_USER, sessionDetail);
			
			String sessionId = (String) MappContext.getAttribute(MappContext.MAPPCONTEXT_SESSIONID);
			response.setSessionId(sessionId == null ? "":sessionId);
			
			
		} catch (Exception e) {
			if(e instanceof com.bdx.rainbow.urs.exception.BusinessException) {
	               com.bdx.rainbow.urs.exception.BaseException e1=(com.bdx.rainbow.urs.exception.BaseException)e;
//	               throw new BusinessException(new DefaultExceptionCode( , e1.getErrorMsg()));
	               throw new ActionException("9999", e1.getErrorMsg());
	           }else {
	               throw e;
	           }
		}
		
	}

}
