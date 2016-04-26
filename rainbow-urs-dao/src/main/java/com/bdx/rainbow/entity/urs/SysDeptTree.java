package com.bdx.rainbow.entity.urs;

import com.bdx.rainbow.common.tree.ITreeable;
import com.bdx.rainbow.entity.urs.*;

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
