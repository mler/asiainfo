package com.bdx.rainbow.mapp.model.rsp;

import java.util.List;

import com.bdx.rainbow.mapp.model.BDXBody;

public class YZ0004Response extends BDXBody {
	
	private List<Article> alist;
	
	private Long total;
	
	private Long totalPage;
	
	private String nextPageUrl;

	public static class Article extends BDXBody
	{
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
	
	public List<Article> getAlist() {
		return alist;
	}

	public void setAlist(List<Article> alist) {
		this.alist = alist;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}

	public String getNextPageUrl() {
		return nextPageUrl;
	}

	public void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}
}


