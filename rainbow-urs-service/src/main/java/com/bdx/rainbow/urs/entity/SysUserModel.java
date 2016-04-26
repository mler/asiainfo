package com.bdx.rainbow.urs.entity;

import com.bdx.rainbow.entity.urs.SysUser;
import com.bdx.rainbow.entity.urs.SysUserExt;

import java.util.List;


public class SysUserModel {

	private SysUser su;
	
	private List<SysUserExt> Sue;

	public SysUser getSu() {
		return su;
	}

	public void setSu(SysUser su) {
		this.su = su;
	}

	public List<SysUserExt> getSue() {
		return Sue;
	}

	public void setSue(List<SysUserExt> sue) {
		Sue = sue;
	}

	

}
