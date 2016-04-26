package com.bdx.rainbow.mapp.model.rsp;

import com.bdx.rainbow.mapp.model.BDXBody;

import java.io.Serializable;
import java.util.List;

public class YZ0003Response extends BDXBody implements Serializable {

    private String code;

    private List<String> fileId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<String> getFileId() {
		return fileId;
	}

	public void setFileId(List<String> fileId) {
		this.fileId = fileId;
	}

}
