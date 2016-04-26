package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.model.req.SK0021Request;
import com.bdx.rainbow.mapp.model.rsp.SK0021Response;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/*
 * 公告详情
 */

@Service("sk0021")
@Action(bizcode="sk0021", version = "1.0", usercheck=false, ipcheck=false)
public class SK0021Action extends AbstractMappAction<SK0021Request, SK0021Response> {
//	@Autowired
//    private IMessageService messageService;
	@Override
	public void doAction(SK0021Request request, SK0021Response response) throws Exception {
//		Message message=new Message();
//		message=messageService.load(Integer.parseInt(this.request.getId()));
//		this.response.setCreateTime(message.getCreateTime().toString());//创建时间
//		this.response.setMsgContent(message.getMsgContent());//消息内容
//		this.response.setMsgTitle(message.getMsgTitle());//公告标题
//		this.response.setSendFrom(message.getSendFrom());//来源
	}

}
