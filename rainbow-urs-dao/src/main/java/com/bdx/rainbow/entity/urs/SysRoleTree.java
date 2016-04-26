package com.bdx.rainbow.entity.urs;

import com.bdx.rainbow.common.tree.ITreeable;


public class SysRoleTree extends SysRole implements ITreeable {

	@Override
	public String getNodeId() {
		return super.getRoleId().toString();
	}

	@Override
	public String getParentNodeId() {
		return super.getRolePid().toString();
	}

}
