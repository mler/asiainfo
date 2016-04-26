package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.DubboLawMB;

//法律法规详情查询
public class YDZF0016Response extends BDXBody {
	private DubboLawMB dubboLawMB;

	public DubboLawMB getDubboLawMB() {
		return dubboLawMB;
	}

	public void setDubboLawMB(DubboLawMB dubboLawMB) {
		this.dubboLawMB = dubboLawMB;
	}

}
