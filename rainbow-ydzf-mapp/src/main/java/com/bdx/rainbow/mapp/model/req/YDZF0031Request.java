package com.bdx.rainbow.mapp.model.req;

import org.hibernate.validator.constraints.NotBlank;

import com.bdx.rainbow.mapp.model.BDXBody;


//文件查询
public class YDZF0031Request extends BDXBody {
	
	@NotBlank(message="文件id不能为空")
	private String fileId;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	
}
