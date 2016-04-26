package com.bdx.rainbow.mapp.model.req;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.bdx.rainbow.mapp.model.BDXBody;


//文件新增
public class YDZF0032Request extends BDXBody {
	
	
	private String fileName;
	private String extName;
	private  boolean isTemp=false;
	private boolean needValidate=true;
	@NotNull(message="文件内容不能为空")
	private  byte[] fileBytes;

	public String getFileName() {
		return fileName;
	}

	public byte[] getFileBytes() {
		return fileBytes;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

	public boolean isTemp() {
		return isTemp;
	}

	public boolean isNeedValidate() {
		return needValidate;
	}

	public void setTemp(boolean isTemp) {
		this.isTemp = isTemp;
	}

	public void setNeedValidate(boolean needValidate) {
		this.needValidate = needValidate;
	}

	
}
