package com.bdx.rainbow.mapp.model.req;

import java.util.List;

import com.bdx.rainbow.mapp.model.BDXBody;

public class YZ0003Request extends BDXBody {
	
	private String code;
	
	private byte[] ico;
	
	private String name;
	 
	private String spec;
	
	private String brand;
	
	private String pName;
	
	private String pAddress;
	
	private String pArea;
	
	private List<byte[]> imgs;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<byte[]> getImgs() {
		return imgs;
	}

	public void setImgs(List<byte[]> imgs) {
		this.imgs = imgs;
	}

	public byte[] getIco() {
		return ico;
	}

	public void setIco(byte[] ico) {
		this.ico = ico;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getpAddress() {
		return pAddress;
	}

	public void setpAddress(String pAddress) {
		this.pAddress = pAddress;
	}

	public String getpArea() {
		return pArea;
	}

	public void setpArea(String pArea) {
		this.pArea = pArea;
	}
	
}


