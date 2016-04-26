package com.bdx.rainbow.urs.entity;

import java.io.Serializable;

public class SysDeptTree extends SysDept implements ITreeable ,Serializable {

	@Override
	public String getNodeId() {
		return super.getDeptId().toString();
	}

	@Override
	public String getParentNodeId() {
		return super.getDeptPid().toString();
	}

}
