package com.bdx.rainbow.urs.entity;

import com.bdx.rainbow.entity.urs.SysDept;
import com.bdx.rainbow.entity.urs.SysDeptExt;

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
