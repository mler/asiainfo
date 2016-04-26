package com.bdx.rainbow.dto.ydzf.vo;

import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseReadyPunish;
import java.sql.Timestamp;

public class YdzfInspectCaseReadyPunishResultVO {
	
	private TYdzfInspectCaseReadyPunish ydzfInspectCaseReadyPunish;
	//other
	//{"law_info": [{"id": "1", "title": "标题1"},{"id": "2", "title": "标题2"},]}
	private String lawJson;

	public TYdzfInspectCaseReadyPunish getYdzfInspectCaseReadyPunish() {
		return ydzfInspectCaseReadyPunish;
	}

	public void setYdzfInspectCaseReadyPunish(
			TYdzfInspectCaseReadyPunish ydzfInspectCaseReadyPunish) {
		this.ydzfInspectCaseReadyPunish = ydzfInspectCaseReadyPunish;
	}

	public String getLawJson() {
		return lawJson;
	}

	public void setLawJson(String lawJson) {
		this.lawJson = lawJson;
	}

}
