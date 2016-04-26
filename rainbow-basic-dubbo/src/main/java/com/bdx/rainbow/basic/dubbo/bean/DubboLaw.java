package com.bdx.rainbow.basic.dubbo.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class DubboLaw implements Serializable{
	private Integer lawId;

    private String agencies;

    private Integer bttype;

    private String businesstype;

    private String filenumber;

    private String isdel;

    private String keywords;

    private String overview;

    private Integer parentid;

    private Timestamp publishTime;

    private String publishedtype;

    private String ranges;

    private String rootid;

    private Integer serialno;

    private String title;

    private Integer zIndex;

    private Timestamp createTime;

    private String creater;

    private Timestamp updateTime;

    private String updater;
    
    private String backupString;
    
    private String contenttxt;
    
    private String content;
    
	public String getContenttxt() {
		return contenttxt;
	}

	public void setContenttxt(String contenttxt) {
		this.contenttxt = contenttxt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getLawId() {
		return lawId;
	}

	public void setLawId(Integer lawId) {
		this.lawId = lawId;
	}

	public String getAgencies() {
		return agencies;
	}

	public void setAgencies(String agencies) {
		this.agencies = agencies;
	}

	public Integer getBttype() {
		return bttype;
	}

	public void setBttype(Integer bttype) {
		this.bttype = bttype;
	}

	public String getBusinesstype() {
		return businesstype;
	}

	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}

	public String getFilenumber() {
		return filenumber;
	}

	public void setFilenumber(String filenumber) {
		this.filenumber = filenumber;
	}

	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public Timestamp getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public String getPublishedtype() {
		return publishedtype;
	}

	public void setPublishedtype(String publishedtype) {
		this.publishedtype = publishedtype;
	}

	public String getRanges() {
		return ranges;
	}

	public void setRanges(String ranges) {
		this.ranges = ranges;
	}

	public String getRootid() {
		return rootid;
	}

	public void setRootid(String rootid) {
		this.rootid = rootid;
	}

	public Integer getSerialno() {
		return serialno;
	}

	public void setSerialno(Integer serialno) {
		this.serialno = serialno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getzIndex() {
		return zIndex;
	}

	public void setzIndex(Integer zIndex) {
		this.zIndex = zIndex;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getBackupString() {
		return backupString;
	}

	public void setBackupString(String backupString) {
		this.backupString = backupString;
	}
    
}
