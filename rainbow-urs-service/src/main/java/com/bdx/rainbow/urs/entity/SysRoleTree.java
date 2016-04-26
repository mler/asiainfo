package com.bdx.rainbow.urs.entity;

import com.bdx.rainbow.common.tree.ITreeable;
import com.bdx.rainbow.entity.urs.SysRole;

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
