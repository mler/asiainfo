package com.bdx.rainbow.mapp.model.req;


import com.bdx.rainbow.mapp.model.BDXBody;

public class SK0008Request extends BDXBody {
//审核功能
	private String id;//检查编号id
	private String status;//是否通过
	private String auditIdea;//审核意见
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuditIdea() {
		return auditIdea;
	}

	public void setAuditIdea(String auditIdea) {
		this.auditIdea = auditIdea;
	}
}
