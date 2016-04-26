package com.bdx.rainbow.urs.entity;

import com.bdx.rainbow.common.tree.ITreeable;
import com.bdx.rainbow.entity.urs.SysDept;

public class SysDeptTree extends SysDept implements ITreeable {

	@Override
	public String getNodeId() {
		return super.getDeptId().toString();
	}

	@Override
	public String getParentNodeId() {
		return super.getDeptPid().toString();
	}

}
