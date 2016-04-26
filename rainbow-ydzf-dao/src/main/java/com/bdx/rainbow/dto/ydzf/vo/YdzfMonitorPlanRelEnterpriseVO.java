package com.bdx.rainbow.dto.ydzf.vo;

import java.sql.Timestamp;
import java.util.Map;

public class YdzfMonitorPlanRelEnterpriseVO {
	private Integer monitorPlanRelId;
	private Integer manageEnterpriseId;
	private String manageItemStatus;
    private String checkTaskStatus;
    private String checkTaskResult;
    private Timestamp checkTaskCheckDate;
    private Integer checkTaskUserId;
    private String fileIds;
    private Integer monitorTempletId;
    private String monitorTempletValues ;
    private String  monitorTempletTotal;
    private String checkAdvice;
    private String enterpriseAdvice;
    private String checkSignFileIds;
    private String enterpriseSignFileIds;
    
    //other
    private String checkTaskUserName;
	private String manageEnterpriseName;
	private String regiAddress;
	private String legalPerson;
	private String legalPersonPhone;
	private String planRelRemark;
    private String taskRemark;	
    private Timestamp checkPlanCreatCreatDate;
    private String checkEnterpriseType;
    private Map<String,String> fileidHttpUrlMap;
    private Map<String,String> checkSignFileIdsHttpUrlMap;
    private Map<String,String> enterpriseSignFileIdsHttpUrlMap;

	public Integer getMonitorPlanRelId() {
		return monitorPlanRelId;
	}
	public Integer getManageEnterpriseId() {
		return manageEnterpriseId;
	}
	public String getManageItemStatus() {
		return manageItemStatus;
	}
	public String getCheckTaskStatus() {
		return checkTaskStatus;
	}
	public String getCheckTaskResult() {
		return checkTaskResult;
	}
	public Timestamp getCheckTaskCheckDate() {
		return checkTaskCheckDate;
	}
	public Integer getCheckTaskUserId() {
		return checkTaskUserId;
	}
	public String getFileIds() {
		return fileIds;
	}
	public Integer getMonitorTempletId() {
		return monitorTempletId;
	}
	public String getManageEnterpriseName() {
		return manageEnterpriseName;
	}
	public String getPlanRelRemark() {
		return planRelRemark;
	}
	public String getTaskRemark() {
		return taskRemark;
	}
	public Timestamp getCheckPlanCreatCreatDate() {
		return checkPlanCreatCreatDate;
	}
	public void setMonitorPlanRelId(Integer monitorPlanRelId) {
		this.monitorPlanRelId = monitorPlanRelId;
	}
	public void setManageEnterpriseId(Integer manageEnterpriseId) {
		this.manageEnterpriseId = manageEnterpriseId;
	}
	public void setManageItemStatus(String manageItemStatus) {
		this.manageItemStatus = manageItemStatus;
	}
	public void setCheckTaskStatus(String checkTaskStatus) {
		this.checkTaskStatus = checkTaskStatus;
	}
	public void setCheckTaskResult(String checkTaskResult) {
		this.checkTaskResult = checkTaskResult;
	}
	public void setCheckTaskCheckDate(Timestamp checkTaskCheckDate) {
		this.checkTaskCheckDate = checkTaskCheckDate;
	}
	public void setCheckTaskUserId(Integer checkTaskUserId) {
		this.checkTaskUserId = checkTaskUserId;
	}
	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
	public void setMonitorTempletId(Integer monitorTempletId) {
		this.monitorTempletId = monitorTempletId;
	}
	public void setManageEnterpriseName(String manageEnterpriseName) {
		this.manageEnterpriseName = manageEnterpriseName;
	}
	public void setPlanRelRemark(String planRelRemark) {
		this.planRelRemark = planRelRemark;
	}
	public void setTaskRemark(String taskRemark) {
		this.taskRemark = taskRemark;
	}
	public void setCheckPlanCreatCreatDate(Timestamp checkPlanCreatCreatDate) {
		this.checkPlanCreatCreatDate = checkPlanCreatCreatDate;
	}
	public String getMonitorTempletValues() {
		return monitorTempletValues;
	}
	public void setMonitorTempletValues(String monitorTempletValues) {
		this.monitorTempletValues = monitorTempletValues;
	}
	public Map<String, String> getFileidHttpUrlMap() {
		return fileidHttpUrlMap;
	}
	public void setFileidHttpUrlMap(Map<String, String> fileidHttpUrlMap) {
		this.fileidHttpUrlMap = fileidHttpUrlMap;
	}
	public String getRegiAddress() {
		return regiAddress;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public String getLegalPersonPhone() {
		return legalPersonPhone;
	}
	public void setRegiAddress(String regiAddress) {
		this.regiAddress = regiAddress;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public void setLegalPersonPhone(String legalPersonPhone) {
		this.legalPersonPhone = legalPersonPhone;
	}
	public String getMonitorTempletTotal() {
		return monitorTempletTotal;
	}
	public void setMonitorTempletTotal(String monitorTempletTotal) {
		this.monitorTempletTotal = monitorTempletTotal;
	}
	public String getCheckEnterpriseType() {
		return checkEnterpriseType;
	}
	public void setCheckEnterpriseType(String checkEnterpriseType) {
		this.checkEnterpriseType = checkEnterpriseType;
	}
	public String getCheckAdvice() {
		return checkAdvice;
	}
	public String getEnterpriseAdvice() {
		return enterpriseAdvice;
	}
	public String getCheckSignFileIds() {
		return checkSignFileIds;
	}
	public String getEnterpriseSignFileIds() {
		return enterpriseSignFileIds;
	}
	public Map<String, String> getCheckSignFileIdsHttpUrlMap() {
		return checkSignFileIdsHttpUrlMap;
	}
	public Map<String, String> getEnterpriseSignFileIdsHttpUrlMap() {
		return enterpriseSignFileIdsHttpUrlMap;
	}
	public void setCheckAdvice(String checkAdvice) {
		this.checkAdvice = checkAdvice;
	}
	public void setEnterpriseAdvice(String enterpriseAdvice) {
		this.enterpriseAdvice = enterpriseAdvice;
	}
	public void setCheckSignFileIds(String checkSignFileIds) {
		this.checkSignFileIds = checkSignFileIds;
	}
	public void setEnterpriseSignFileIds(String enterpriseSignFileIds) {
		this.enterpriseSignFileIds = enterpriseSignFileIds;
	}
	public void setCheckSignFileIdsHttpUrlMap(
			Map<String, String> checkSignFileIdsHttpUrlMap) {
		this.checkSignFileIdsHttpUrlMap = checkSignFileIdsHttpUrlMap;
	}
	public void setEnterpriseSignFileIdsHttpUrlMap(
			Map<String, String> enterpriseSignFileIdsHttpUrlMap) {
		this.enterpriseSignFileIdsHttpUrlMap = enterpriseSignFileIdsHttpUrlMap;
	}
	public String getCheckTaskUserName() {
		return checkTaskUserName;
	}
	public void setCheckTaskUserName(String checkTaskUserName) {
		this.checkTaskUserName = checkTaskUserName;
	}
	



}
