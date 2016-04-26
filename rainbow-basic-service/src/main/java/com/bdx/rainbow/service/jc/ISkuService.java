package com.bdx.rainbow.service.jc;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bdx.rainbow.entity.jc.JcSku;
import com.bdx.rainbow.entity.jc.Pgds;
import com.bdx.rainbow.entity.jc.ProductorGds;

public interface ISkuService {

	
	
	public int saveSku(JcSku sku) throws Exception;
	/**
	 * 通过条形码查找商品
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public JcSku findSkuByCode(String code) throws Exception;
	
	/**
	 * 场景:客户端上传图片后，将新的图片更新到现有的食品中，如果数据库中无数据插入新的数据
	 * @param code
	 * @param fileIds
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public int addNewSkuByImg(String code,Set<String> fileIds,String userid) throws Exception;
	
	/**
	 * 商品人工审核审核完毕
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public int chechFinish(String code) throws Exception;
	
	/**
	 * 根据二维码从gds网站查出商品和供应商信息，与本地的商品信息比较，做更新，并记录更新内容
	 * @param barcode
	 * @param goods
	 * @param supplier
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public int updateSkuByGds(String barcode,Pgds goods,ProductorGds supplier,String userid) throws Exception;
	
	/**
	 * 根据二维码从gds网站查出商品和供应商信息，与本地的商品信息比较，做更新，并记录更新内容
	 * @param barcode
	 * @param goods
	 * @param supplier
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public int updateByCFDA(String barcode,Pgds goods,ProductorGds supplier,String userid) throws Exception;
	
	/**
	 * 根据上传的信息，保存图片和药品信息
	 * @param drc
	 * @param imgs
	 * @return
	 * @throws Exception
	 */
	public String saveSkuAndUploadImage(JcSku sku,byte[] ico,List<byte[]> imgs,Integer userid,String userLoginName) throws Exception;


	/**
	 * 根据文件ID获取文件路径
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	public String findImgPathByFileId(String fileId) throws Exception;
	
	/**
	 * 分页获得SKU信息
	 * @param condition
	 * @param start
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSkuList(JcSku condition,Integer start, Integer count)throws Exception;
	
	/**
	 * 只是上传图片
	 * @param img
	 * @param userid
	 * @param userLoginName
	 * @return
	 * @throws Exception
	 */
	public String justUploadImag(File img, String extName,Integer userId)throws Exception;
}
