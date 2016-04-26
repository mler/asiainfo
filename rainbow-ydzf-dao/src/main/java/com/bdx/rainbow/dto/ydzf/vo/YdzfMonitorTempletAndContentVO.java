package com.bdx.rainbow.dto.ydzf.vo;

import java.util.List;

import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTemplet;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTempletContent;

public class YdzfMonitorTempletAndContentVO {
	
	private TYdzfMonitorTemplet ydzfMonitorTemplet;
    private List<TYdzfMonitorTempletContent> ydzfMonitorTempletContentList;
	public TYdzfMonitorTemplet getYdzfMonitorTemplet() {
		return ydzfMonitorTemplet;
	}
	public List<TYdzfMonitorTempletContent> getYdzfMonitorTempletContentList() {
		return ydzfMonitorTempletContentList;
	}
	public void setYdzfMonitorTemplet(TYdzfMonitorTemplet ydzfMonitorTemplet) {
		this.ydzfMonitorTemplet = ydzfMonitorTemplet;
	}
	public void setYdzfMonitorTempletContentList(
			List<TYdzfMonitorTempletContent> ydzfMonitorTempletContentList) {
		this.ydzfMonitorTempletContentList = ydzfMonitorTempletContentList;
	}
	
}
