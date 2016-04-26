package com.bdx.rainbow.crawler.bean;


public class SeedBeanDefinition {

	private Class<?> analyzer;
	
	private Class<?> seedClass;
	
	private String executor;
	
	private int split;

	public Class<?> getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Class<?> analyzer) {
		this.analyzer = analyzer;
	}

	public Class<?> getSeedClass() {
		return seedClass;
	}

	public void setSeedClass(Class<?> seedClass) {
		this.seedClass = seedClass;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public int getSplit() {
		return split;
	}

	public void setSplit(int split) {
		this.split = split;
	}
	
}
