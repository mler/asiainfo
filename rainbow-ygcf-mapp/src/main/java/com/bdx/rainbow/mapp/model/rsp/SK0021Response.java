package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;

/*
 * 公告详情
 */
public class SK0021Response extends BDXBody {
	private String msgTitle;    //公告标题
	private String msgContent;  //消息内容
	private String createTime;  //创建时间
	private String sendFrom;   //来源
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
	public String getSendFrom() {
		return sendFrom;
	}
	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}
}
