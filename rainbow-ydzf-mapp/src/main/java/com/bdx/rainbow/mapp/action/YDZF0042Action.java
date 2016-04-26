package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseReadyPunishResultVO;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0042Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0042Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseReadyPunishService;
import com.bdx.rainbow.urs.entity.IUserInfo;

/**
 * 拟处罚查询
 * 
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0042")
@Action(bizcode = "ydzf0042", userCheck = true, ipCheck = false)
@Scope("prototype")
public class YDZF0042Action extends
		AbstractBaseActionHandler<YDZF0042Request, YDZF0042Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0042Action.class);
	@Autowired
	private IYDZFInspectCaseReadyPunishService ydzfInspectCaseReadyPunishService;

	@Override
	protected void doAction() throws BusinessException, SystemException,
			Exception {
		// IUserInfo user = (IUserInfo)
		// MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
		YdzfInspectCaseReadyPunishResultVO vo = ydzfInspectCaseReadyPunishService
				.queryInspectCaseReadyPunishInfo(this.request
						.getInspectCaseReadyPunishId());
		TransformMapperBeanUtil.DTO2MB(vo, this.response);
	}

}
