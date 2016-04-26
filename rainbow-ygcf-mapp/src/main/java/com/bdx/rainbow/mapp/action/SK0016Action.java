package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.model.req.SK0016Request;
import com.bdx.rainbow.mapp.model.rsp.SK0016Response;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/*
 * 公告列表
 */
@Service("sk0016")
@Action(bizcode="sk0016", version = "1.0", usercheck=false, ipcheck=false)
public class SK0016Action extends AbstractMappAction<SK0016Request, SK0016Response> {
//    @Autowired
//    private IMessageService messageService;
	@Override
	public void doAction(SK0016Request request, SK0016Response response) throws Exception {
//		Map param=new HashMap();
//		if (StringUtils.isNotBlank(this.request.getPageCount())) {
//            param.put("pageSize", this.request.getPageCount());
//        }
//        if (StringUtils.isNotBlank(this.request.getPageNumber())) {
//            param.put("page",Integer.valueOf(this.request.getPageNumber())+1);
//        }
//        List list=new ArrayList();
//        if("0".equals(this.request.getPageCount())&&"-1".equals(this.request.getPageNumber())){
//            SysUser user=(SysUser) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
//            	list =messageService.getMsgs(2, user.getId());
//        }else{
////                param.put("msgTitle","null");
//            param.put("msgType","1,2,3,4,5,6");
//            list =messageService.getList(param).getRows();
//        }
//
//        List<SK0016Response.Message> messages=new ArrayList<SK0016Response.Message>();
//        if(list!=null&&list.size()>0) {
//            for (int i = 0; i < list.size(); i++) {
//                Message map = (Message) list.get(i);
//                SK0016Response.Message message = new SK0016Response.Message();
//
//                message.setMsgTitle(map.getMsgTitle());//公告标题
//                message.setMsgContent(map.getMsgContent()); //消息内容
//                message.setCreateTime(TimeUtils.getFormatTimeString(map.getCreateTime()));//创建时间
//                message.setId(map.getId().toString());//公告id
//
//                messages.add(message);
//            }
//            this.response.setmessages(messages);
//        }
		
	}

}
