package com.bdx.rainbow.mapp.model.req;


import com.bdx.rainbow.mapp.model.BDXBody;

/**
 * 添加员工信息
 */
public class SK0003Request extends BDXBody {

	/**
	 * 编码
	 */
    private String id;

	/**
	 * 头像
	 */
	private byte[] picturePath;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 职称
	 */
	private String jobTitle;

	/**
	 * 职务
	 */
	private String post;

	/**
	 * 证件类型
	 */
	private String documentType;

	/**
	 * 证件号码
	 */
	private String iDNumber;

	/**
	 * 联系电话
	 */
	private String personaPhone;

	/**
	 * 是否有健康证
	 */
	private String areBlacklist;

	/**
	 * 健康证照片
	 */
	private byte[] fileUrl;

	/**
	 * 健康证编号
	 */
	private String healthId;

	/**
	 * 检查结果
	 */
	private String checkResult;

	/**
	 * 发证日期
	 */
	private String issuingDate;

	/**
	 * 有效期
	 */
	private String validity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(byte[] picturePath) {
		this.picturePath = picturePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getiDNumber() {
		return iDNumber;
	}

	public void setiDNumber(String iDNumber) {
		this.iDNumber = iDNumber;
	}

	public String getPersonaPhone() {
		return personaPhone;
	}

	public void setPersonaPhone(String personaPhone) {
		this.personaPhone = personaPhone;
	}

	public String getAreBlacklist() {
		return areBlacklist;
	}

	public void setAreBlacklist(String areBlacklist) {
		this.areBlacklist = areBlacklist;
	}

	public byte[] getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(byte[] fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getHealthId() {
		return healthId;
	}

	public void setHealthId(String healthId) {
		this.healthId = healthId;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getIssuingDate() {
		return issuingDate;
	}

	public void setIssuingDate(String issuingDate) {
		this.issuingDate = issuingDate;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}
}
