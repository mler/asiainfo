package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;

import java.util.List;

//公告列表，详情
public class SK0016Response extends BDXBody {
	private List<Message> messages;
	
	public List<Message> getmessages() {
		return messages;
	}
	public void setmessages(List<Message> messages) {
		this.messages = messages;
	}

	public static class Message{
	private String msgTitle;       //公告标题
	private String msgContent;     //消息内容
	private String createTime;      //创建时间
	private String id;            // 公告id
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	}
}
