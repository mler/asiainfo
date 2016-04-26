package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.ActionException;
import com.bdx.rainbow.mapp.core.exception.ErrorCodeDefine;
import com.bdx.rainbow.mapp.entity.MappSessionDetail;
import com.bdx.rainbow.mapp.model.req.SK0002Request;
import com.bdx.rainbow.mapp.model.rsp.SK0002Response;
import com.bdx.rainbow.service.mapp.ISK0002Service;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.ygcf.entity.Employee;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 获取员工列表
 */
@Service("sk0002")
@Action(bizcode="sk0002", version = "1.0", usercheck=false, ipcheck=false)
public class SK0002Action extends AbstractMappAction<SK0002Request, SK0002Response> {

	@Autowired
	private ISK0002Service sk0002Service;

	@Override
	public void doAction(SK0002Request request, SK0002Response response) throws Exception {
		Map param=new HashMap();

		// 一页显示数量
		if (StringUtil.isNotEmptyObject(request.getPageCount())) {
            param.put("pageCount", request.getPageCount());
        }

		// 页码,传递从0开始
        if (StringUtil.isNotEmptyObject(request.getPageNumber())) {
            param.put("pageNo", Integer.valueOf(request.getPageNumber()));
        }

		// session数据
		MappSessionDetail sessionDetail = (MappSessionDetail) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
		if(null != sessionDetail && null != sessionDetail.getCorpId()) {
			param.put("enterpriseId", sessionDetail.getCorpId());
		} else {
			throw new ActionException(ErrorCodeDefine.NO_USER_INFO, "登陆超时");
		}

		// 如果pageCount＝0 && pageNumber＝-1,表示不分页查询
		List<Employee> list = sk0002Service.selectListByCompanyId(param);

		if(!CollectionUtils.isEmpty(list)) {
			List<SK0002Response.Empl> employees=new ArrayList<>();

			for(Employee employee : list) {
				SK0002Response.Empl empl=new SK0002Response.Empl();

				// 需要VFS读取, 或者在service中进行转换,暂用健康证代替
//				empl.setPicturePath(employee.getHealthPath());
				empl.setName(employee.getFullName());
				empl.setJobTitle(employee.getJobTitle());
				empl.setId(employee.getEmployeeId().toString());

				employees.add(empl);
			}

			response.setEmployees(employees);
		}
	}
}
