package com.bdx.rainbow.mapp.model.req;


import com.bdx.rainbow.mapp.model.BDXBody;

//日常检查列表
public class SK0006Request extends BDXBody {
	private String checkSTime;//检查时间(从)
	private String pageCount;//分页条数
	private String pageNumber;//当前页
	public String getCheckSTime() {
		return checkSTime;
	}
	public void setCheckSTime(String checkSTime) {
		this.checkSTime = checkSTime;
	}
	public String getCheckETime() {
		return checkETime;
	}
	public void setCheckETime(String checkETime) {
		this.checkETime = checkETime;
	}
	private String checkETime;//检查时间(到)

	public String getPageCount() {
		return pageCount;
	}
	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	
}
