package com.bdx.rainbow.mapp.model.rsp;


import com.bdx.rainbow.mapp.model.BDXBody;

import java.util.List;

public class SK0020Response extends BDXBody {
//用户权限对应菜单
	private List<Menu> Menus; 
	
	public List<Menu> getMenus() {
		return Menus;
	}

	public void setMenus(List<Menu> menus) {
		Menus = menus;
	}

	public static class Menu{
    private String id;//系统ID
    private String sysName;//系统名称
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	
	}
}
