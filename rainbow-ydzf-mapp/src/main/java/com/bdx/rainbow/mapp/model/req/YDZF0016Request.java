package com.bdx.rainbow.mapp.model.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;


//法律法规详情查询
public class YDZF0016Request extends BDXBody {
	
	@NotNull(message="法律ID不能为空")
	@Min(message="法律ID非法", value = 1)
	private int lawId;

	public int getLawId() {
		return lawId;
	}

	public void setLawId(int lawId) {
		this.lawId = lawId;
	}


	
	
}
