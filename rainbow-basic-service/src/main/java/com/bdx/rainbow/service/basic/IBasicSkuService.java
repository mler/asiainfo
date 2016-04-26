package com.bdx.rainbow.service.basic;

import java.util.List;
import java.util.Map;

import org.hibernate.annotations.ListIndexBase;

import com.bdx.rainbow.basic.dubbo.service.ISkuDubboService;
import com.bdx.rainbow.entity.basic.mysql.TBasicSku;
import com.bdx.rainbow.entity.basic.mysql.TBasicSkuImag;
import com.bdx.rainbow.entity.jc.JcSku;

public interface IBasicSkuService extends ISkuDubboService{
	
	/**
	 * 保存sku对象  只负责上传图片
	 * @param sku
	 * @param detail
	 * @param images
	 * @throws Exception
	 */
	public void saveSkuNoDeleteImage(TBasicSku sku,Object detail,List<byte[]> images,Integer enterpriseId,String userLoginName,Integer userId) throws Exception;
	
	/**
	 * 根据 条形码 获得商品信息
	 * @param barcode
	 * @return
	 * @throws Exception
	 */
	public TBasicSku getSkuByBarcode(String barcode) throws Exception;
	
	/**
	 * 简单创建sku
	 * @param sku
	 * @throws Exception
	 */
	public void simpleSaveSkuByBarcode(TBasicSku sku,String userLoginName)throws Exception;
	
	
	/**
	 * 根据上传的信息，保存图片和药品信息
	 * @param drc
	 * @param imgs
	 * @return
	 * @throws Exception
	 */
	public String saveSkuAndUploadImage(TBasicSku sku,byte[] ico,List<byte[]> imgs,Integer userid,String userLoginName) throws Exception;
	
	
	
	/**
	 * 分页获得SKU信息
	 * @param condition
	 * @param start
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSkuList(TBasicSku condition,Integer start, Integer limit)throws Exception;
	
	/**
	 * 获得SKU图片信息
	 * @param skuId
	 * @param fileType 图片类型
	 * @return
	 * @throws Exception
	 */
	public List<TBasicSkuImag> getSkuImagsBySkuId(Integer skuId,String fileType)throws Exception;
	

}
