package com.bdx.rainbow.mapp.model.req;

import com.bdx.rainbow.mapp.model.BDXBody;


/**
 * 查询执行状态
 * Created by luli on 16/2/22.
 */
public class YDZF0004Request extends BDXBody {

    /**
     * 查询类型
     */
    private String checkPlanType;//0：日常，1：专项，2：突击，3：抽检

    /**
     * 执行状态
     */
    private String checkPlanStatus;//0：待执行,1：正在执行，9:完成
    /**
     * 
     * 组织机构id
     */
    private int deptId;



	public String getCheckPlanType() {
		return checkPlanType;
	}

	public void setCheckPlanType(String checkPlanType) {
		this.checkPlanType = checkPlanType;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getCheckPlanStatus() {
		return checkPlanStatus;
	}

	public void setCheckPlanStatus(String checkPlanStatus) {
		this.checkPlanStatus = checkPlanStatus;
	}


}
