package com.bdx.rainbow.mapp.model.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;


//法律条文标题组合查询接口(根据id 返回所有titles)
public class YDZF0015Request extends BDXBody {
	
	@NotNull(message="法律ID不能为空")
	private List<Integer> lawIdList;

	public List<Integer> getLawIdList() {
		return lawIdList;
	}

	public void setLawIdList(List<Integer> lawIdList) {
		this.lawIdList = lawIdList;
	}




	
	
}
