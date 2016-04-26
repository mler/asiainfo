package com.bdx.rainbow.mapp.model.req;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseMB;


//稽查案件列表查询
public class YDZF0019Request extends BDXBody {
	
	private TYdzfInspectCaseMB ydzfInspectCaseMB;
	private PageInfo pageInfo;

	public TYdzfInspectCaseMB getYdzfInspectCaseMB() {
		return ydzfInspectCaseMB;
	}

	public void setYdzfInspectCaseMB(TYdzfInspectCaseMB ydzfInspectCaseMB) {
		this.ydzfInspectCaseMB = ydzfInspectCaseMB;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}



	
	
}
