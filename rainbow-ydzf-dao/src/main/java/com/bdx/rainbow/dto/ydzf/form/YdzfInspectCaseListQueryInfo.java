package com.bdx.rainbow.dto.ydzf.form;

import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;



public class YdzfInspectCaseListQueryInfo {
	
	private TYdzfInspectCase ydzfInspectCase;

	public TYdzfInspectCase getYdzfInspectCase() {
		return ydzfInspectCase;
	}

	public void setYdzfInspectCase(TYdzfInspectCase ydzfInspectCase) {
		this.ydzfInspectCase = ydzfInspectCase;
	}

    //案件创建时间开始查询时间
    private String startTime;
    //案件创建时间结束查询时间
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
