package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;

//法律条文标题组合查询接口(根据id 返回所有titles)
public class YDZF0015Response extends BDXBody {
	//多个ID 查询返回json
	//[{"lawId":1,"title":"3333"},{"lawId":1,"title":"3333"}]
	private String lawTitlesJson;


	public String getLawTitlesJson() {
		return lawTitlesJson;
	}

	public void setLawTitlesJson(String lawTitlesJson) {
		this.lawTitlesJson = lawTitlesJson;
	}
}
