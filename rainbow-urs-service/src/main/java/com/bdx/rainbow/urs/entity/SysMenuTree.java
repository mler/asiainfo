package com.bdx.rainbow.urs.entity;

import java.io.Serializable;

import com.bdx.rainbow.common.tree.ITreeable;
import com.bdx.rainbow.entity.urs.SysMenu;

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
