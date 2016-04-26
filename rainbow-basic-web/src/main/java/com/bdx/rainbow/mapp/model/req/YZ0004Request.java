package com.bdx.rainbow.mapp.model.req;

import com.bdx.rainbow.mapp.model.BDXBody;

public class YZ0004Request extends BDXBody {
	
	private int pageNumber;
	
	private String moduleCode;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

}



