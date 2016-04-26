package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.ActionException;
import com.bdx.rainbow.mapp.core.exception.ErrorCodeDefine;
import com.bdx.rainbow.mapp.entity.MappSessionDetail;
import com.bdx.rainbow.mapp.model.req.SK0003Request;
import com.bdx.rainbow.mapp.model.rsp.SK0003Response;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.service.mapp.ISK0003Service;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.ygcf.entity.Employee;
import com.bdx.rainbow.ygcf.entity.Health;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 修改/添加员工信息
 */
@Service("sk0003")
@Action(bizcode="sk0003", version="1.0", usercheck=false, ipcheck=false)
public class SK0003Action extends AbstractMappAction<SK0003Request, SK0003Response> {

	@Autowired
	private ISK0003Service sk0003Service;

	@Override
	public void doAction(SK0003Request request, SK0003Response response) throws Exception {

		MappSessionDetail sessionDetail = (MappSessionDetail) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);

		if(null != sessionDetail && null != sessionDetail.getCorpId()) {
			Employee employee = new Employee();
			Health health = new Health();

			if(StringUtil.isNotEmptyObject(request.getId())) {
				employee.setEmployeeId(Integer.parseInt(request.getId()));
			}
			employee.setEnterpriseId(sessionDetail.getCorpId());
			employee.setFullName(request.getName());
			employee.setJobTitle(request.getJobTitle());
			employee.setPost(request.getPost());
			employee.setIdType(request.getDocumentType());
			employee.setIdCode(request.getiDNumber());
			employee.setPersonalPhone(request.getPersonaPhone());

			employee.setIsneedHealth(request.getAreBlacklist());
			employee.setHealthCode(request.getHealthId());

			health.setHealthCode(request.getHealthId());
			health.setHealthResult(request.getCheckResult());

			// 头像，健康证图片，发证日期，有效期
			sk0003Service.insertOrUpdateEmployee(employee, health, request.getPicturePath(), request.getFileUrl(), request.getIssuingDate(), request.getValidity(), sessionDetail.getId());

		} else {
			throw new ActionException(ErrorCodeDefine.NO_USER_INFO, "登陆超时");
//			throw new BusinessException(new DefaultExceptionCode(ErrorCodeDefine.NO_USER_INFO, "登录超时!"));
		}
	}
}
