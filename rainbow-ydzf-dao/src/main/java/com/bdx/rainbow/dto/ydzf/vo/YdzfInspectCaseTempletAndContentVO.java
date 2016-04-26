package com.bdx.rainbow.dto.ydzf.vo;

import java.util.List;

import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTemplet;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseTempletContent;

public class YdzfInspectCaseTempletAndContentVO {
	
	private TYdzfInspectCaseTemplet ydzfInspectCaseTemplet;
    private List<TYdzfInspectCaseTempletContent> ydzfInspectCaseTempletContentList;
	public TYdzfInspectCaseTemplet getYdzfInspectCaseTemplet() {
		return ydzfInspectCaseTemplet;
	}
	public List<TYdzfInspectCaseTempletContent> getYdzfInspectCaseTempletContentList() {
		return ydzfInspectCaseTempletContentList;
	}
	public void setYdzfInspectCaseTemplet(TYdzfInspectCaseTemplet ydzfInspectCaseTemplet) {
		this.ydzfInspectCaseTemplet = ydzfInspectCaseTemplet;
	}
	public void setYdzfInspectCaseTempletContentList(
			List<TYdzfInspectCaseTempletContent> ydzfInspectCaseTempletContentList) {
		this.ydzfInspectCaseTempletContentList = ydzfInspectCaseTempletContentList;
	}
	
}
