package com.bdx.rainbow.mapp.model.rsp;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.mapp.model.BDXBody;



public class YZ0006Response extends BDXBody {
	
	private String sessionId;
	
	/**
	 * 头像
	 */
	private String img;
	
	/**
	 * 用户类型
	 */
	private String userType;
	
	/**
	 * 系统参数
	 */
	private Map<String,SysParam> params;
	
	/**
	 * 菜单
	 */
	private List<Menu> menus;
	
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public static class SysParam extends BDXBody {
		private String key;
		private String value;
		private String desc;
		
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
	}

	public Map<String, SysParam> getParams() {
		return params;
	}

	public void setParams(Map<String, SysParam> params) {
		this.params = params;
	}
	
	
	
	public static class Menu extends BDXBody {
		/**
		 * 菜单ID
		 */
		private Integer menuId;
		
		/**
		 * 菜单名称
		 */
		private String menuName;
		
		/**
		 * 菜单编码 可以以此判断该进入什么样的模块
		 */
		private String menuCode;
		
		/**
		 * 菜单样式
		 */
		private String menuPic;
		
		/**
		 * 菜单地址  一般用于嵌套html页面时才会用到
		 */
		private String menuUrl;
		
		/**
		 * 用于判断是否嵌套URL地址
		 */
		private String target;

		public Integer getMenuId() {
			return menuId;
		}

		public void setMenuId(Integer menuId) {
			this.menuId = menuId;
		}

		public String getMenuName() {
			return menuName;
		}

		public void setMenuName(String menuName) {
			this.menuName = menuName;
		}

		public String getMenuCode() {
			return menuCode;
		}

		public void setMenuCode(String menuCode) {
			this.menuCode = menuCode;
		}

		public String getMenuPic() {
			return menuPic;
		}

		public void setMenuPic(String menuPic) {
			this.menuPic = menuPic;
		}

		public String getMenuUrl() {
			return menuUrl;
		}

		public void setMenuUrl(String menuUrl) {
			this.menuUrl = menuUrl;
		}

		public String getTarget() {
			return target;
		}

		public void setTarget(String target) {
			this.target = target;
		}
		
	}

	
	
	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	

}
