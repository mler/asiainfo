package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;

/*
 * 员工信息详情
 */
public class SK0017Response extends BDXBody {
	
		private String picturePath;//头像
		private String name;//姓名
		private String jobTitle;//职称
		private String post;//职务
		private String documentType;//证件类型
		private String IDNumber;//证件号码
		private String personaPhone;//联系电话
		private String areBlacklist;//是否有健康证
		private String fileUrl;//健康证照片
        private String healthId;//健康证编号
        private String checkResult;//检查结果
        private String issuingDate;//发证日期
        private String validity;//有效期

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

    public String getPicturePath() {
			return picturePath;
		}
		public void setPicturePath(String picturePath) {
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
		
		public String getIDNumber() {
			return IDNumber;
		}
		public void setIDNumber(String iDNumber) {
			IDNumber = iDNumber;
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
		public String getFileUrl() {
			return fileUrl;
		}
		public void setFileUrl(String fileUrl) {
			this.fileUrl = fileUrl;
		}
		public String getDocumentType() {
			return documentType;
		}
		public void setDocumentType(String documentType) {
			this.documentType = documentType;
		}

		
		
		
		
	}

