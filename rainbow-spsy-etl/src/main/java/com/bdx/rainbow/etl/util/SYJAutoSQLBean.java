package com.bdx.rainbow.etl.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SYJAutoSQLBean {
	
	
	private String runUrl;
	private String tableName;
	private String tableComment;
	//column,comment
	private Map<String,String> columnMap=new LinkedHashMap<String,String>();
	
	private String creatTableSql;
	public String getRunUrl() {
		return runUrl;
	}
	public void setRunUrl(String runUrl) {
		this.runUrl = runUrl;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	public Map<String, String> getColumnMap() {
		return columnMap;
	}
	public void setColumnMap(Map<String, String> columnMap) {
		this.columnMap = columnMap;
	}
	@Override
	public String toString() {
		return "SYJAutoSQLBean [runUrl=" + runUrl + ", tableName=" + tableName
				+ ", tableComment=" + tableComment + ", columnMap=" + columnMap
				+ "]";
	}
	public String getCreatTableSql() {
		
		String dorpSql="drop table "+this.getTableName()+" cascade constraints;";
		String	sql="create table "+this.getTableName()+" ( id number(11) primary key,CONTENT_ID NUMBER(11), CREATE_TIME DATE,CREATE_EMP_CODE VARCHAR2(20),UPDATE_TIME DATE, UPDATE_EMP_CODE VARCHAR2(20)";
		String columnKey="";
		String columnComment="";
		for(String key:this.getColumnMap().keySet())
		{
			columnKey=columnKey+" ,"+key+ " VARCHAR2(500) ";
			columnComment=columnComment+" comment on column "+this.getTableName()+"."+key+" is '"+this.getColumnMap().get(key)+"';";
		}
		sql=sql+columnKey+");";
		String commentSql=" comment on table "+this.getTableName()+" is '"+this.getTableComment()+"';"+columnComment;

	
		return dorpSql+sql+commentSql;
	}
	public void setCreatTableSql(String creatTableSql) {
		this.creatTableSql = creatTableSql;
	}
	


}
