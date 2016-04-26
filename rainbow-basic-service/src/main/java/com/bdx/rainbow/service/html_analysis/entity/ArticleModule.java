package com.bdx.rainbow.service.html_analysis.entity;

public class ArticleModule {

	private String code;
	
	private String analyzeType;
	
	private String name;
	
	private String url;
	

	public ArticleModule(String code, String analyzeType, String name, String url) {
		super();
		this.code = code;
		this.name = name;
		this.url = url;
		this.analyzeType = analyzeType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAnalyzeType() {
		return analyzeType;
	}

	public void setAnalyzeType(String analyzeType) {
		this.analyzeType = analyzeType;
	}
	
	
}
