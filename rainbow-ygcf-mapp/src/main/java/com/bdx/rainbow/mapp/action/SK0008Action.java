package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.model.req.SK0008Request;
import com.bdx.rainbow.mapp.model.rsp.SK0008Response;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/*
 * 日常检查审核提交
 */
@Service("sk0008")
@Action(bizcode="sk0008",version = "1.0",usercheck=false, ipcheck=false)
public class SK0008Action extends AbstractMappAction<SK0008Request, SK0008Response> {
//	 @Autowired
//	 private IDailySchemeAuditService dailySchemeAuditService;
//	 private IDailyCheckSchemeService dailyCheckSchemeService;
	@Override
	public void doAction(SK0008Request request, SK0008Response response) throws Exception {
//		DailySchemeAudit dailySchemeAudit=new DailySchemeAudit();
//
//		DailyCheckScheme dailyCheckScheme=new DailyCheckScheme();
//		//根据id找出此DailyCheckScheme对象，然后对其审核结果进行更新
//		dailyCheckScheme=dailyCheckSchemeService.load(Integer.parseInt(this.request.getId()));
//		//将request中传入的审核结果传给dailyCheckScheme
//		dailyCheckScheme.setAuditFlow(Integer.parseInt(this.request.getStatus()));
//		//更新此id对应的DailyCheckScheme
//		dailyCheckSchemeService.update(dailyCheckScheme);
//		//根据id将审核意见入表DailySchemeAudit
//		dailySchemeAudit.setId(Integer.parseInt(this.request.getId()));
//		dailySchemeAudit.setAuditIdea(this.request.getAuditIdea());
//		dailySchemeAuditService.save(dailySchemeAudit);
	}

}
