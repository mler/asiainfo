package com.bdx.rainbow.mapp.model.req;

import java.sql.Timestamp;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.DubboLawMB;


//法律法规列表查询
public class YDZF0014Request extends BDXBody {
	
//	@NotNull(message="参数不能为空")
//	@Valid
	private DubboLawMB dubboLawMB;
	private PageInfo pageinfo;
	private Timestamp startTime;
	private Timestamp endTime;

	public DubboLawMB getDubboLawMB() {
		return dubboLawMB;
	}

	public void setDubboLawMB(DubboLawMB dubboLawMB) {
		this.dubboLawMB = dubboLawMB;
	}

	public PageInfo getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(PageInfo pageinfo) {
		this.pageinfo = pageinfo;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}


	
	
}
