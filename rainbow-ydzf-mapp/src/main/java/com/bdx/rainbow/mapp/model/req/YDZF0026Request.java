package com.bdx.rainbow.mapp.model.req;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.bdx.rainbow.mapp.model.BDXBody;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseWitnessesMB;


//听证新增
public class YDZF0026Request extends BDXBody {
	@NotNull(message="参数不能为空")
	@Valid
	private TYdzfInspectCaseWitnessesMB ydzfInspectCaseWitnessesMB;

	public TYdzfInspectCaseWitnessesMB getYdzfInspectCaseWitnessesMB() {
		return ydzfInspectCaseWitnessesMB;
	}

	public void setYdzfInspectCaseWitnessesMB(
			TYdzfInspectCaseWitnessesMB ydzfInspectCaseWitnessesMB) {
		this.ydzfInspectCaseWitnessesMB = ydzfInspectCaseWitnessesMB;
	}





	

	
	
}
