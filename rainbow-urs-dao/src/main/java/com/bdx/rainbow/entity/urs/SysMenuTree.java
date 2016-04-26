package com.bdx.rainbow.entity.urs;

import java.io.Serializable;

import com.bdx.rainbow.common.tree.ITreeable;


public class SysMenuTree extends SysMenu implements ITreeable ,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getNodeId() {
		return super.getMenuId().toString();
	}

	@Override
	public String getParentNodeId() {
		return super.getMenuPid().toString();
	}

}
