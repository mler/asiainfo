package com.bdx.rainbow.entity.sys;

import java.util.List;

public class SysDeptModel {

	private SysDept sc;
	private List<SysDeptExt> scExts;
	public SysDept getSc() {
		return sc;
	}
	public void setSc(SysDept sc) {
		this.sc = sc;
	}
	public List<SysDeptExt> getScExts() {
		return scExts;
	}
	public void setScExts(List<SysDeptExt> scExts) {
		this.scExts = scExts;
	}

}
