package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.common.constant.SysParamConstants;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.ActionException;
import com.bdx.rainbow.mapp.entity.MappSessionDetail;
import com.bdx.rainbow.mapp.model.req.SK0001Request;
import com.bdx.rainbow.mapp.model.rsp.SK0001Response;
import java.util.HashMap;
import java.util.Map;

import com.bdx.rainbow.service.mapp.IBasicService;
import com.bdx.rainbow.service.mapp.ISK0001Service;
import com.bdx.rainbow.urs.entity.IUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 手机端登陆接口
 */
@Service("sk0001")
@Action(bizcode="sk0001", version = "1.0", usercheck=false, ipcheck=false)
public class SK0001Action extends AbstractMappAction<SK0001Request, SK0001Response> {

	private static final Logger logger = LoggerFactory.getLogger(SK0001Action.class);

	@Autowired
	private ISK0001Service sk0001Service;

	@Autowired
	private IBasicService basicService;

	@Override
	public void doAction(SK0001Request request, SK0001Response response) throws Exception {
		// 登陆
		Map map = new HashMap();
		map.put("username", request.getUserName());
		map.put("password", request.getPwd());

		IUserInfo userInfo = sk0001Service.checkUserLogin(map);

		if (userInfo != null) {

			MappSessionDetail sessionDetail = new MappSessionDetail();
			sessionDetail.setId(userInfo.getUser().getUserId());
			sessionDetail.setCorpId(userInfo.getUser().getCorpId());
			sessionDetail.setUserType(userInfo.getUser().getUserType());
			sessionDetail.setUserInfo(userInfo);

			response.setSessionId(MappContext.getAttribute(MappContext.MAPPCONTEXT_SESSIONID).toString());
			response.setHeadImage("");

			// 判断是否企业用户
			if(SysParamConstants.USER_TYPE_ENTERPRISE.equals(sessionDetail.getUserType())) {
				Integer corpId = sessionDetail.getCorpId();

				DubboEnterpriseInfo enterpriseInfo = basicService.getEnterpriseById(corpId);
				if(null == enterpriseInfo) {
					throw new ActionException("0008", "企业不存在");
				}

				response.setCompanyName(enterpriseInfo.getEnterpriseName());
			}

			MappContext.setAttribute(MappContext.MAPPCONTEXT_USER, sessionDetail);
			MappContext.setAttribute(MappContext.MAPPCONTEXT_DEVICE, request.getDeviceType());

		} else {
			throw new ActionException("0008", "用户不存在或密码错误");
		}
	}
}
