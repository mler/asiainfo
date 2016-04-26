package com.bdx.rainbow.mapp.model.rsp;

import com.bdx.rainbow.mapp.model.BDXBody;

public class YZ0005Response extends BDXBody {
	
	private String title;
	
	private String url;
	
	private String content;
	
	private String createTime;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}


