package com.bdx.rainbow.etl.util;

public class SYJTableBean {
	
	
	private String runUrl;
	private String tableId;
	private String tableName;
	private String tableClass;
	private String title;
	private String bcId;
	private String detailId;
	private String searchPageUrl;
	private String searchDetailUrl;
	private String 	insertSql;
	private int total;
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBcId() {
		return bcId;
	}
	public void setBcId(String bcId) {
		this.bcId = bcId;
	}
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public String getSearchDetailUrl() {
		return searchDetailUrl;
	}
	public void setSearchDetailUrl(String searchDetailUrl) {
		this.searchDetailUrl = searchDetailUrl;
	}
	public String getSearchPageUrl() {
		return searchPageUrl;
	}
	public void setSearchPageUrl(String searchPageUrl) {
		this.searchPageUrl = searchPageUrl;
	}
	public String getRunUrl() {
		return runUrl;
	}
	public void setRunUrl(String runUrl) {
		this.runUrl = runUrl;
	}
	public String getInsertSql() {
		return "INSERT into SYJ_TABLE_BEAN (id,url,table_name,table_class,title,status,total) values(SEQ_SYJ_TABLE_BEAN.nextval,'"+this.getSearchPageUrl()+"','"+this.getTableName()+"','"+this.getTableClass()+"','"+this.getTitle()+"','0',"+this.getTotal()+");";
	}
	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}
	public String getTableClass() {
		return tableClass;
	}
	public void setTableClass(String tableClass) {
		this.tableClass = tableClass;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	


	


}
