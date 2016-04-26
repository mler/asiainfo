package com.bdx.rainbow.mapp.model.req;

import com.bdx.rainbow.mapp.model.BDXBody;

/**
 * 图片或者二维码搜索商品信息，以二维码优先
 * @author mler
 *
 */
public class YZ0000Request extends BDXBody {
	
	private String barcode;
	
	private int x;
	
	private int y;
	
	private int width;
	
	private int height;
	
	private byte[] img;
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
}
