package com.bdx.rainbow.etl.entity.seed;

import java.util.Map;
import java.util.Set;

/**
 * http的请求种子
 * @author mler
 *
 */
public class HttpSeed implements Seed {

	public final static String RESOLVETYPE_RESULT = "0";
	
	public final static String RESOLVETYPE_SEED_PAGE = "1";
	
	public final static String RESOLVETYPE_SEED_COMMON = "2";
	
	private String charset;
	
	private String url;
	
	private Map<String,String> param;
	
	private String html;
	
	private long createTime;
	
	private Set<String> resolveTypes;
	
	public Set<String> getResolveTypes() {
		return resolveTypes;
	}

	public void setResolveTypes(Set<String> resolveTypes) {
		this.resolveTypes = resolveTypes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public Map<String, String> getParam() {
		return param;
	}

	public void setParam(Map<String, String> param) {
		this.param = param;
	}

	@Override
	public String toKey() {
		// TODO Auto-generated method stub
		return this.toString();
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
	
}
