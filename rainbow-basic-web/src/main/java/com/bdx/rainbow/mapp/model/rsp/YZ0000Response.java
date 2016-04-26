package com.bdx.rainbow.mapp.model.rsp;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bdx.rainbow.mapp.model.BDXBody;

public class YZ0000Response extends BDXBody {
	
	private List<SkuEntity> entities;
	
	public List<SkuEntity> getEntities() {
		return entities;
	}

	public void setEntities(List<SkuEntity> entities) {
		this.entities = entities;
	}
	
	public static class Saler extends BDXBody {
		
		private String mobile;//电话
		
		private String name;//销售点名称
		
		private String address;//地址

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
		
	}

	public static class SkuEntity extends BDXBody {
		
		private Long id;
		
		private String name;//商品名称
		
		private String productor;//生产企业
		
		private String code;//食药准字号
		
		private String logNo;//批次号
		
		private Map<String,String> mainRes;//原料
		
		private String barcode;//配料
		
		private List<Saler> salers; 
		
		private String checkInfo;//检查信息
		
		private String punishInfo;//处罚信息
		
		private String spec;//规格
		
		private String brand;
		
		private String productAddress;
		
		private String productArea;
		
		private String ico;
		
		private Set<String> imgs;
		
		private String creator;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSpec() {
			return spec;
		}

		public void setSpec(String spec) {
			this.spec = spec;
		}

		public String getProductor() {
			return productor;
		}

		public void setProductor(String productor) {
			this.productor = productor;
		}


		public Set<String> getImgs() {
			return imgs;
		}

		public void setImgs(Set<String> imgs) {
			this.imgs = imgs;
		}

		public String getLogNo() {
			return logNo;
		}

		public void setLogNo(String logNo) {
			this.logNo = logNo;
		}

		public Map<String, String> getMainRes() {
			return mainRes;
		}

		public void setMainRes(Map<String, String> mainRes) {
			this.mainRes = mainRes;
		}

		public String getBarcode() {
			return barcode;
		}

		public void setBarcode(String barcode) {
			this.barcode = barcode;
		}

		public List<Saler> getSalers() {
			return salers;
		}

		public void setSalers(List<Saler> salers) {
			this.salers = salers;
		}

		public String getCheckInfo() {
			return checkInfo;
		}

		public void setCheckInfo(String checkInfo) {
			this.checkInfo = checkInfo;
		}

		public String getPunishInfo() {
			return punishInfo;
		}

		public void setPunishInfo(String punishInfo) {
			this.punishInfo = punishInfo;
		}

		public String getBrand() {
			return brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}

		public String getProductAddress() {
			return productAddress;
		}

		public void setProductAddress(String productAddress) {
			this.productAddress = productAddress;
		}

		public String getProductArea() {
			return productArea;
		}

		public void setProductArea(String productArea) {
			this.productArea = productArea;
		}

		public String getIco() {
			return ico;
		}

		public void setIco(String ico) {
			this.ico = ico;
		}

		public String getCreator() {
			return creator;
		}

		public void setCreator(String creator) {
			this.creator = creator;
		}
		
	}

}
