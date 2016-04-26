package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.model.req.YDZF0001Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0001Response;
import com.bdx.rainbow.service.ydzf.system.IYDZFLoginService;
import com.bdx.rainbow.urs.entity.IUserInfo;
/**
 * 登录
 * @author fox
 *
 */
@Service("ydzf0001")
@Action(bizcode="ydzf0001",userCheck=false, ipCheck=false)
@Scope("prototype")
public class YDZF0001Action extends AbstractBaseActionHandler<YDZF0001Request,YDZF0001Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0001Action.class);
	@Autowired
	private IYDZFLoginService ydzfLoginService;
    @Override
    protected void doAction() throws Exception {
    	String loginName=this.request.getUsercode();
     	String pwd=this.request.getPwd();
     	try {
     	   	IUserInfo user=ydzfLoginService.login(loginName, pwd, null);
     	   if (user!=null){
               MappContext.setAttribute(MappContext.MAPPCONTEXT_USER, user);
               MappContext.setAttribute(MappContext.MAPPCONTEXT_DEVICE, this.request.getDeviceType());
              // HttpSession session = new ThreadLocal<HttpSession>().get();
               this.response.setSessionId(MappContext.getAttribute(MappContext.MAPPCONTEXT_SESSIONID).toString());
               this.response.setUserName(user.getUserName());
               this.response.setUserId(user.getUserId());
               this.response.setLoginName(user.getNickName());
               this.response.setPhoneNum(user.getUser().getMobilePhone());
               this.response.setDeptId(user.getUser().getDeptId());
               this.response.setDeptName(user.getDeptName());
           }
     	   else
     	   {
     		   throw new com.bdx.rainbow.common.exception.BusinessException(new DefaultExceptionCode("0008","用户不存在或密码错误"));
     	   }
		} catch (com.bdx.rainbow.common.exception.BusinessException e) {
			 throw e;
		}
     	catch (Exception e) {
     		log.error("mapper-0001-错误",e);
     		throw new  com.bdx.rainbow.common.exception.SystemException(com.bdx.rainbow.common.exception.ExceptionCode.EX_CORE_UNKNOW);
		}
    }
   
}
