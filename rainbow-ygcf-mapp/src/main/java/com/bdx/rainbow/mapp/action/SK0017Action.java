package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.ActionException;
import com.bdx.rainbow.mapp.core.exception.ErrorCodeDefine;
import com.bdx.rainbow.mapp.entity.MappSessionDetail;
import com.bdx.rainbow.mapp.model.req.SK0017Request;
import com.bdx.rainbow.mapp.model.rsp.SK0017Response;
import com.bdx.rainbow.service.sk.IEmployeeService;
import com.bdx.rainbow.service.vfs.IFile;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.ygcf.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 员工信息详情
 */
@Service("sk0017")
@Action(bizcode="sk0017", version = "1.0", usercheck=false, ipcheck=false)
public class SK0017Action extends AbstractMappAction<SK0017Request, SK0017Response> {

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private VfsSetting vfsSetting;

	@Autowired
	private IFile fileService;

	@Override
	public void doAction(SK0017Request request, SK0017Response response) throws Exception {

		MappSessionDetail sessionDetail = (MappSessionDetail) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);

		if(null != sessionDetail) {

			if(!StringUtil.isNotEmptyObject(request.getId())) {
				throw new ActionException(ErrorCodeDefine.SERVER_ERROR, "员工编号为空");
			}

			Employee employee = employeeService.get(Integer.parseInt(request.getId()));

			if(employee.getIsneedHealth().equals("1")) {
				// 获取健康证图片路径
				if(StringUtil.isNotEmptyObject(employee.getHealthPath())) {
					String filePath = fileService.getHttpUrl(employee.getHealthPath(), fileService.getServerConfig(vfsSetting.getServerName()));

					response.setFileUrl(filePath);
				}
				response.setHealthId(employee.getHealthCode());
				response.setValidity(DateUtil.parse(employee.getValidDate().getTime(), DateUtil.DATE_FORMAT2));
			}

			response.setName(employee.getFullName());
			response.setJobTitle(employee.getJobTitle());
			response.setPost(employee.getPost());
			response.setDocumentType(employee.getIdType());
			response.setIDNumber(employee.getIdCode());
			response.setPersonaPhone(employee.getPersonalPhone());
			response.setAreBlacklist(employee.getIsneedHealth());
		} else {
			throw new ActionException(ErrorCodeDefine.NO_USER_INFO, "登陆超时");
		}
	}
}
