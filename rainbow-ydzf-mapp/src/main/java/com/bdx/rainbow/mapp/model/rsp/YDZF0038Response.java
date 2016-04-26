package com.bdx.rainbow.mapp.model.rsp;


import java.util.List;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseTempletContentMB;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseTempletMB;


public class YDZF0038Response extends BDXBody {
	
	private TYdzfInspectCaseTempletMB ydzfInspectCaseTempletMB;
    private List<TYdzfInspectCaseTempletContentMB> ydzfInspectCaseTempletContentMBList;
	public TYdzfInspectCaseTempletMB getYdzfInspectCaseTempletMB() {
		return ydzfInspectCaseTempletMB;
	}
	public List<TYdzfInspectCaseTempletContentMB> getYdzfInspectCaseTempletContentMBList() {
		return ydzfInspectCaseTempletContentMBList;
	}
	public void setYdzfInspectCaseTempletMB(
			TYdzfInspectCaseTempletMB ydzfInspectCaseTempletMB) {
		this.ydzfInspectCaseTempletMB = ydzfInspectCaseTempletMB;
	}
	public void setYdzfInspectCaseTempletContentMBList(
			List<TYdzfInspectCaseTempletContentMB> ydzfInspectCaseTempletContentMBList) {
		this.ydzfInspectCaseTempletContentMBList = ydzfInspectCaseTempletContentMBList;
	}


	
	
}
