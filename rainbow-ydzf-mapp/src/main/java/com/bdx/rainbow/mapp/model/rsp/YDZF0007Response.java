package com.bdx.rainbow.mapp.model.rsp;


import java.util.List;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfMonitorTempletContentMB;
import com.bdx.rainbow.mapp.model.bean.TYdzfMonitorTempletMB;


public class YDZF0007Response extends BDXBody {
	
	private TYdzfMonitorTempletMB ydzfMonitorTempletMB;
    private List<TYdzfMonitorTempletContentMB> ydzfMonitorTempletContentMBList;
	public TYdzfMonitorTempletMB getYdzfMonitorTempletMB() {
		return ydzfMonitorTempletMB;
	}
	public List<TYdzfMonitorTempletContentMB> getYdzfMonitorTempletContentMBList() {
		return ydzfMonitorTempletContentMBList;
	}
	public void setYdzfMonitorTempletMB(TYdzfMonitorTempletMB ydzfMonitorTempletMB) {
		this.ydzfMonitorTempletMB = ydzfMonitorTempletMB;
	}
	public void setYdzfMonitorTempletContentMBList(
			List<TYdzfMonitorTempletContentMB> ydzfMonitorTempletContentMBList) {
		this.ydzfMonitorTempletContentMBList = ydzfMonitorTempletContentMBList;
	}
	
	
}
