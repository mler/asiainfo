package com.bdx.rainbow.mapp.action;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.entity.ydzf.MappAppVersion;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.model.req.YDZF0003Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0003Response;
import com.bdx.rainbow.service.ydzf.common.IMappAppVersionService;

/**
 *  03接口（版本更新）
 * @author fox
 *
 */
@Service("ydzf0003")
@Action(bizcode="ydzf0003",userCheck=false, ipCheck=false)
@Scope("prototype")
public class YDZF0003Action extends AbstractBaseActionHandler<YDZF0003Request,YDZF0003Response> {

	@Autowired
	private IMappAppVersionService mappAppVersionService;
	
	@Override
	protected void doAction() throws Exception {
		if(StringUtils.isEmpty(this.request.getItemKey()))
			throw new Exception("no send the itemKey,please send it");
		MappAppVersion app = mappAppVersionService.getAppVersion(this.request.getItemKey());
		if(app == null){
			throw new Exception("there's no app of " + this.request.getItemKey() + ",check your itemKey");
		}
		this.response.setCompatibleVersion(app.getCompatibleversion());
		this.response.setIntroduction(app.getIntroduction());
		this.response.setLastVersion(app.getLastversion());
		this.response.setUpdateURL(app.getUpdateurl());
	}
}
