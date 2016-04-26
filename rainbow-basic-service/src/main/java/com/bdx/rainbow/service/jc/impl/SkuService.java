package com.bdx.rainbow.service.jc.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.*;

import com.bdx.rainbow.entity.sys.SysOperLog;
import com.bdx.rainbow.entity.sys.SysUser;
import com.bdx.rainbow.entity.vfs.FileUploadInfo;
import com.bdx.rainbow.mapper.sys.SysOperLogMapper;
import com.bdx.rainbow.mapper.vfs.FileUploadInfoMapper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.entity.jc.JcSku;
import com.bdx.rainbow.entity.jc.JcSkuExample;
import com.bdx.rainbow.entity.jc.JcSkuExample.Criteria;
import com.bdx.rainbow.entity.jc.Pgds;
import com.bdx.rainbow.entity.jc.ProductorGds;
import com.bdx.rainbow.entity.vfs.FileServerConfig;
import com.bdx.rainbow.mapper.jc.JcSkuBatchMapper;
import com.bdx.rainbow.mapper.jc.JcSkuMapper;
import com.bdx.rainbow.service.jc.ISkuService;
import com.bdx.rainbow.service.vfs.IFile;

@Service
@Transactional
public class SkuService implements ISkuService{
	
	private Logger logger = LoggerFactory.getLogger(SkuService.class);

	@Autowired
	private JcSkuMapper jcSkuMapper;
	
	@Autowired
	private JcSkuBatchMapper jcSkuBatchMapper;
	
	@Autowired
	private IFile fileService;
	
	@Autowired
	private VfsSetting vfsSetting;

	@Autowired
	private FileUploadInfoMapper fileUploadInfoMapper;

	@Autowired
	private SysOperLogMapper sysOperLogMapper;
	
	
	public int saveSku(JcSku sku) throws Exception
	{
		// 主键不为空，修改
		/**if(null != sku.getSkuId() && StringUtils.isNotBlank(sku.getSkuId().toString())) {
			JcSkuExample example = new JcSkuExample();
			example.createCriteria().andSkuIdEqualTo(sku.getSkuId());

			return jcSkuMapper.updateByExampleSelective(sku, example);
		} else {**/
		if(StringUtils.isEmpty(sku.getSkuBarcode()))
			throw new Exception("商品二维码不能为空");
		JcSku old = findSkuByCode(sku.getSkuBarcode());
		
		if (old != null) {
			return jcSkuMapper.updateByPrimaryKeySelective(sku);
		}else{
			return jcSkuMapper.insertSelective(sku);
		}
	}
	
	/**
	 * 通过条形码查找商品（有效的商品信息 status != 2）
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public JcSku findSkuByCode(String code) throws Exception
	{
		if(StringUtils.isBlank(code))
			throw new Exception("商品二维码不能为空");
		
		JcSkuExample where = new JcSkuExample();
		Criteria c = where.createCriteria();
		c.andSkuBarcodeEqualTo(code);
		c.andStatusNotEqualTo("2");

		where.setOrderByClause("status desc");
		
		List<JcSku> skuList = jcSkuMapper.selectByExample(where);
		
		if(skuList == null || skuList.isEmpty())
			return null;
		
		return skuList.iterator().next();
		
	}
	
	/**
	 * 场景:客户端上传图片后，将新的图片更新到现有的食品中，如果数据库中无数据插入新的数据
	 * @param code
	 * @param fileIds
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public int addNewSkuByImg(String code,Set<String> fileIds,String userid) throws Exception
	{
		JcSku sku = findSkuByCode(code);
		
		if(sku == null)
		{
			sku = new JcSku();
			sku.setCreateEmpCode(userid);
			sku.setCreateTime(new Timestamp(System.currentTimeMillis()));
			sku.setUpdateEmpCode(userid);
			sku.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			sku.setStatus("0");
			sku.setSkuBarcode(code);
			sku.setImgs(StringUtil.collectionToString(fileIds, ","));
		}
		else
		{
			String imgs = sku.getImgs();
			if(StringUtils.isBlank(imgs) == false)
			{
				String[] img_array = imgs.split(",");
				Set<String> img_set = new HashSet<String>(img_array.length);
				CollectionUtils.addAll(img_set, img_array);
				img_set.addAll(fileIds);
				sku.setImgs(StringUtil.collectionToString(img_set, ","));
			}
			sku.setStatus("0");
		}
		
		return saveSku(sku);
	}
	
	/**
	 * 商品人工审核审核完毕
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public int chechFinish(String code) throws Exception
	{
		JcSku sku = findSkuByCode(code);
		
		if(sku == null)
			throw new Exception("未找到该商品 code = "+code);
		
		sku.setStatus("1");
		
//		JcSkuExample where = new JcSkuExample();
//		Criteria c = where.createCriteria();
//		c.andSkuIdEqualTo(sku.getSkuId());
		
//		return jcSkuMapper.updateByExample(sku, where);
		return jcSkuMapper.updateByPrimaryKeySelective(sku);
	}
	
	/**
	 * 根据二维码从gds网站查出商品和供应商信息，与本地的商品信息比较，做更新，并记录更新内容
	 * @param barcode
	 * @param goods
	 * @param supplier
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public int updateSkuByGds(String barcode,Pgds goods,ProductorGds supplier,String userid) throws Exception
	{

		/**
		 * 将如果正式商品表中无该商品，则新加
		 */
		JcSku sku = findSkuByCode(barcode);
		if(sku == null)
		{
			sku = new JcSku();
			sku.setCreateEmpCode(userid);
			sku.setCreateTime(new Timestamp(System.currentTimeMillis()));
			
		}
		
		/**
		 * 判断是否与原本的实体有更改
		 */
		boolean nochange = true;
		
		StringBuffer changeNote = new StringBuffer("");
		
		if(StringUtils.isNotBlank(barcode) && barcode.equals(sku.getSkuBarcode()) == false)
		{
			sku.setSkuBarcode(barcode);
			changeNote.append(" [skuBarcode:"+sku.getSkuBarcode()+"=>"+barcode+"] ");
			
		}
		if(StringUtils.isNotBlank(goods.getName()) && goods.getName().equals(sku.getSkuName()) == false)
		{
			sku.setSkuName(goods.getName());
			changeNote.append(" [skuName:"+sku.getSkuName()+"=>"+goods.getName()+"] ");
			nochange = false;
		}
		if(StringUtils.isNotBlank(goods.getBrand()) && goods.getBrand().equals(sku.getSkuBrand()) == false)
		{
			sku.setSkuBrand(goods.getBrand());
			changeNote.append(" [skuBrand:"+sku.getSkuBrand()+"=>"+goods.getBrand()+"] ");
			nochange = false;
		}
		if(StringUtils.isNotBlank(goods.getOrgiCountry()) && goods.getOrgiCountry().equals(sku.getpArea()) == false)
		{
			sku.setpArea(goods.getOrgiCountry());
			changeNote.append(" [pArea:"+sku.getpArea()+"=>"+goods.getOrgiCountry()+"] ");
			nochange = false;
		}
		if(supplier != null && StringUtils.isNotBlank(supplier.getAddress()) && supplier.getAddress().equals(sku.getpAddress()) == false)
		{
			sku.setpAddress(supplier==null?"":supplier.getAddress());
			changeNote.append(" [pAddress:"+sku.getpAddress()+"=>"+supplier.getAddress()+"] ");
			nochange = false;
		}
		if(StringUtils.isNotBlank(goods.getProductor()) && goods.getProductor().equals(sku.getpName()) == false)
		{
			sku.setpName(goods.getProductor());
			changeNote.append(" [pName:"+sku.getpName()+"=>"+goods.getProductor()+"] ");
			nochange = false;
		}
		if(StringUtils.isNotBlank(goods.getValidDate()) && goods.getValidDate().equals(sku.getSafeDeadline()) == false)
		{
			sku.setSafeDeadline(goods.getValidDate());
			changeNote.append(" [safeDeadline:"+sku.getSafeDeadline()+"=>"+goods.getValidDate()+"] ");
			nochange = false;
		}
		if(StringUtils.isNotBlank(goods.getSpec()) && goods.getSpec().equals(sku.getSkuSpec()) == false)
		{
			sku.setSkuSpec(goods.getSpec());
			changeNote.append(" [skuSpec:"+sku.getSkuSpec()+"=>"+goods.getSpec()+"] ");
			nochange = false;
		}
		if(supplier != null && StringUtils.isNotBlank(supplier.getpCode()) && supplier.getpCode().equals(sku.getpCode()) == false)
		{
			sku.setpCode(supplier==null?"":supplier.getpCode());
			changeNote.append(" [pCode:"+sku.getpCode()+"=>"+supplier.getpCode()+"] ");
			nochange = false;
		}
		if(StringUtils.isNotBlank(goods.getUnspsc()) && goods.getUnspsc().equals(sku.getGdsType()) == false)
		{
			sku.setGdsType(goods.getUnspsc());
			changeNote.append(" [gdsType:"+sku.getGdsType()+"=>"+goods.getUnspsc()+"] ");
			nochange = false;
		}
		
		if(nochange == false)
		{
			sku.setStatus("0");
			sku.setUpdateEmpCode(userid);
			sku.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			sku.setChangeNote(changeNote.toString());
		}
		
		return saveSku(sku);
	}
	
	/**
	 * 根据二维码从gds网站查出商品和供应商信息，与本地的商品信息比较，做更新，并记录更新内容
	 * @param barcode
	 * @param goods
	 * @param supplier
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public int updateByCFDA(String barcode,Pgds goods,ProductorGds supplier,String userid) throws Exception
	{

		/**
		 * 将如果正式商品表中无该商品，则新加
		 */
		JcSku sku = findSkuByCode(barcode);
		if(sku == null)
		{
			sku = new JcSku();
			sku.setCreateEmpCode(userid);
			sku.setCreateTime(new Timestamp(System.currentTimeMillis()));
			
		}
		
		/**
		 * 判断是否与原本的实体有更改
		 */
		boolean nochange = true;
		
		StringBuffer changeNote = new StringBuffer("");
		
		if(StringUtils.isNotBlank(barcode) && barcode.equals(sku.getSkuBarcode()) == false)
		{
			sku.setSkuBarcode(barcode);
			changeNote.append(" [skuBarcode:"+sku.getSkuBarcode()+"=>"+barcode+"] ");
			
		}
		if(StringUtils.isNotBlank(goods.getName()) && goods.getName().equals(sku.getSkuName()) == false)
		{
			sku.setSkuName(goods.getName());
			changeNote.append(" [skuName:"+sku.getSkuName()+"=>"+goods.getName()+"] ");
			nochange = false;
		}
		if(StringUtils.isNotBlank(goods.getBrand()) && goods.getBrand().equals(sku.getSkuBrand()) == false)
		{
			sku.setSkuBrand(goods.getBrand());
			changeNote.append(" [skuBrand:"+sku.getSkuBrand()+"=>"+goods.getBrand()+"] ");
			nochange = false;
		}
		if(StringUtils.isNotBlank(goods.getOrgiCountry()) && goods.getOrgiCountry().equals(sku.getpArea()) == false)
		{
			sku.setpArea(goods.getOrgiCountry());
			changeNote.append(" [pArea:"+sku.getpArea()+"=>"+goods.getOrgiCountry()+"] ");
			nochange = false;
		}
		if(supplier != null && StringUtils.isNotBlank(supplier.getAddress()) && supplier.getAddress().equals(sku.getpAddress()) == false)
		{
			sku.setpAddress(supplier==null?"":supplier.getAddress());
			changeNote.append(" [pAddress:"+sku.getpAddress()+"=>"+supplier.getAddress()+"] ");
			nochange = false;
		}
		if(StringUtils.isNotBlank(goods.getProductor()) && goods.getProductor().equals(sku.getpName()) == false)
		{
			sku.setpName(goods.getProductor());
			changeNote.append(" [pName:"+sku.getpName()+"=>"+goods.getProductor()+"] ");
			nochange = false;
		}
		if(StringUtils.isNotBlank(goods.getValidDate()) && goods.getValidDate().equals(sku.getSafeDeadline()) == false)
		{
			sku.setSafeDeadline(goods.getValidDate());
			changeNote.append(" [safeDeadline:"+sku.getSafeDeadline()+"=>"+goods.getValidDate()+"] ");
			nochange = false;
		}
		if(StringUtils.isNotBlank(goods.getSpec()) && goods.getSpec().equals(sku.getSkuSpec()) == false)
		{
			sku.setSkuSpec(goods.getSpec());
			changeNote.append(" [skuSpec:"+sku.getSkuSpec()+"=>"+goods.getSpec()+"] ");
			nochange = false;
		}
		if(supplier != null && StringUtils.isNotBlank(supplier.getpCode()) && supplier.getpCode().equals(sku.getpCode()) == false)
		{
			sku.setpCode(supplier==null?"":supplier.getpCode());
			changeNote.append(" [pCode:"+sku.getpCode()+"=>"+supplier.getpCode()+"] ");
			nochange = false;
		}
		if(StringUtils.isNotBlank(goods.getUnspsc()) && goods.getUnspsc().equals(sku.getGdsType()) == false)
		{
			sku.setGdsType(goods.getUnspsc());
			changeNote.append(" [gdsType:"+sku.getGdsType()+"=>"+goods.getUnspsc()+"] ");
			nochange = false;
		}
		
		if(nochange == false)
		{
			sku.setStatus("0");
			sku.setUpdateEmpCode(userid);
			sku.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			sku.setChangeNote(changeNote.toString());
			return saveSku(sku);
		}
		
		return 0;
		
	}
	
	
	/**
	 * 更新sku内容，并且记录更改内容
	 * @param barcode
	 * @param goods
	 * @param supplier
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public JcSku updateSkuNoSave(JcSku newSku,String userid) throws Exception
	{

		/**
		 * 将如果正式商品表中无该商品，则新加
		 */
		JcSku sku = findSkuByCode(newSku.getSkuBarcode());
		
		
		if(sku == null)
		{
			sku = newSku;
			sku.setCreateEmpCode(userid);
			sku.setCreateTime(new Timestamp(System.currentTimeMillis()));
			sku.setStatus("0");
		}
		
		/**
		 * 判断是否与原本的实体有更改
		 */
		boolean nochange = true;
		
		StringBuffer changeNote = new StringBuffer("");
		
		
		if(StringUtils.isNotBlank(newSku.getSkuName()) && newSku.getSkuName().equals(sku.getSkuName()) == false)
		{
			changeNote.append(" [skuName:" + sku.getSkuName() + "=>" + newSku.getSkuName() + "] ");
			sku.setSkuName(newSku.getSkuName());
			nochange = false;
		}
		if(StringUtils.isNotBlank(newSku.getSkuBrand()) && newSku.getSkuBrand().equals(sku.getSkuBrand()) == false)
		{
			changeNote.append(" [skuBrand:" + sku.getSkuBrand() + "=>" + newSku.getSkuBrand() + "] ");
			sku.setSkuBrand(newSku.getSkuBrand());
			nochange = false;
		}
		if(StringUtils.isNotBlank(newSku.getpArea()) && newSku.getpArea().equals(sku.getpArea()) == false)
		{
			changeNote.append(" [pArea:" + sku.getpArea() + "=>" + newSku.getpArea() + "] ");
			sku.setpArea(newSku.getpArea());
			nochange = false;
		}
		if(StringUtils.isNotBlank(newSku.getpAddress()) && newSku.getpAddress().equals(sku.getpAddress()) == false)
		{
			changeNote.append(" [pAddress:" + sku.getpAddress() + "=>" + newSku.getpAddress() + "] ");
			sku.setpAddress(newSku == null ? "" : newSku.getpAddress());
			nochange = false;
		}
		if(StringUtils.isNotBlank(newSku.getpName()) && newSku.getpName().equals(sku.getpName()) == false)
		{
			changeNote.append(" [pName:" + sku.getpName() + "=>" + newSku.getpName() + "] ");
			sku.setpName(newSku.getpName());
			nochange = false;
		}
		if(StringUtils.isNotBlank(newSku.getSafeDeadline()) && newSku.getSafeDeadline().equals(sku.getSafeDeadline()) == false)
		{
			changeNote.append(" [safeDeadline:" + sku.getSafeDeadline() + "=>" + newSku.getSafeDeadline() + "] ");
			sku.setSafeDeadline(newSku.getSafeDeadline());
			nochange = false;
		}
		if(StringUtils.isNotBlank(newSku.getSkuSpec()) && newSku.getSkuSpec().equals(sku.getSkuSpec()) == false)
		{
			changeNote.append(" [skuSpec:" + sku.getSkuSpec() + "=>" + newSku.getSkuSpec() + "] ");
			sku.setSkuSpec(newSku.getSkuSpec());
			nochange = false;
		}
		if(StringUtils.isNotBlank(newSku.getpCode()) && newSku.getpCode().equals(sku.getpCode()) == false)
		{
			changeNote.append(" [pCode:" + sku.getpCode() + "=>" + newSku.getpCode() + "] ");
			sku.setpCode(newSku == null ? "" : newSku.getpCode());
			nochange = false;
		}
		if(StringUtils.isNotBlank(newSku.getGdsType()) && newSku.getGdsType().equals(sku.getGdsType()) == false)
		{
			changeNote.append(" [gdsType:" + sku.getGdsType() + "=>" + newSku.getGdsType() + "] ");
			sku.setGdsType(newSku.getGdsType());
			nochange = false;
		}
		// 不是新增，才会判断是否有
		if(sku != newSku && StringUtils.isNotBlank(newSku.getIco())) {
			changeNote.append(" [ico:" + sku.getIco() + "=>" + newSku.getIco() + "] ");
			sku.setIco(newSku.getIco());
			nochange = false;
		}


		if(nochange == false)
		{
			sku.setStatus("0");
			sku.setUpdateEmpCode(userid);
			sku.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			sku.setChangeNote(changeNote.toString());
		} else {
			sku.setChangeNote("新增");
		}
		
		SysOperLog sysOperLog = new SysOperLog();
		sysOperLog.setOperUser(userid);
		sysOperLog.setOperDate(new Timestamp(new Date().getTime()));
		sysOperLog.setClassName(JcSku.class.getName());
		sysOperLog.setBusinessKey(sku.getSkuBarcode());
		sysOperLog.setLogContent(sku.getChangeNote());
//		Parameters: 21(Long), com.bdx.rainbow.entity.jc.JcSku(String), 6923341232586(String), 板(String), 18657100022(Long), 2016-01-16 19:35:02.858(Timestamp)
		sysOperLogMapper.insert(sysOperLog);

		return sku;
	}
	
	/**
	 * 根据上传的信息，保存图片和药品信息
	 * @param drc
	 * @param imgs
	 * @return
	 * @throws Exception
	 */
	public String saveSkuAndUploadImage(JcSku sku,byte[] ico ,List<byte[]> images,Integer userid,String userLoginName) throws Exception
	{
		if(sku == null || StringUtils.isBlank(sku.getSkuBarcode()))
			return null;
		
		FileServerConfig config = fileService.getServerConfig(vfsSetting.getServerName());
		
		if(StringUtils.isBlank(sku.getSkuBarcode()))
			logger.warn("sku barcode is empty");

		// 上传icon
		String ico_id = (ico == null) ? null : fileService.uploadFile(config, ico, "jpg", userid+"_"+System.currentTimeMillis()+"_"+Math.random(), userid, false, true);
		sku.setIco(ico_id);

		List<String> fileIds = new ArrayList<String>();

		if(null != images) {
			// 上传图片
			for(byte[] img : images)
			{

				String fileId = fileService.uploadFile(config, img, "jpg", userid+"_"+System.currentTimeMillis()+"_"+Math.random(), userid, false, true);
				fileIds.add(fileId);
			}
		}

		sku.setImgs(StringUtil.collectionToString(fileIds, ","));
		
		sku = updateSkuNoSave(sku,StringUtils.isEmpty(userLoginName)?"":userLoginName);

		saveSku(sku);
		
		return sku.getSkuBarcode();
	}

	/**
	 * 根据文件ID获取文件路径
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	@Override
	public String findImgPathByFileId(String fileId) throws Exception {
		FileUploadInfo fileUploadInfo = fileUploadInfoMapper.selectByPrimaryKey(fileId);
		if(null == fileUploadInfo) {
			return "";
		}

		StringBuffer path = new StringBuffer();
		path.append(vfsSetting.getFileReadService());
		path.append(fileUploadInfo.getFilePath()).append(File.separatorChar).append(fileId).append(".").append(fileUploadInfo.getFileExt());

		return path.toString();
	}

	@Override
	public Map<String, Object> getSkuList(JcSku condition, Integer start,
			Integer count) throws Exception {
		JcSkuExample where = convertCondition(condition);
		if (start >= 0 && count > 0) {
			where.setLimitClauseStart(start);
			where.setLimitClauseCount(count);
		}
		where.setOrderByClause("CREATE_TIME desc");
		List<JcSku> list = jcSkuMapper.selectByExample(where);
		int total = jcSkuMapper.countByExample(where);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (list != null && list.size() != 0) {
			for (JcSku item:list) {
				if (StringUtils.isNotEmpty(item.getIco())) {
					String string = findImgPathByFileId(item.getIco());
					item.setIco(string);
//					if (StringUtils.isNotEmpty(item.getImgs())) {
//						String[] imgFields = item.getImgs().split(",");
//						List<String> imgList = new ArrayList<String>(imgFields.length);
//						for (String imgField:imgFields) {
//							String imgPath = findImgPathByFileId(imgField);
//							if (StringUtils.isNotEmpty(imgPath)) {
//								imgList.add(imgPath);
//							}
//							
//						}
//						model.addAttribute("imgs", imgList);
//					}
				}
			}
			
		}
		resultMap.put("list", list);
		resultMap.put("total", total);
		return resultMap;
	}

	private JcSkuExample convertCondition(JcSku condition){
		JcSkuExample where = new JcSkuExample();
		JcSkuExample.Criteria criteria = where.createCriteria();
		
		if (StringUtils.isNotEmpty(condition.getSkuBarcode())) {
			criteria.andSkuBarcodeEqualTo(condition.getSkuBarcode());
		}
		if (StringUtils.isNotEmpty(condition.getStatus())) {
			if (condition.getStatus().startsWith("!")) {
				criteria.andStatusNotEqualTo(condition.getStatus().substring(1));
			}else {
				criteria.andStatusEqualTo(condition.getStatus());
			}
			
		}
		if (StringUtils.isNotEmpty(condition.getSkuName())) {
			criteria.andSkuNameLike("%" + condition.getSkuName() + "%");
		}
		if (StringUtils.isNotEmpty(condition.getCreateEmpCode())) {
			criteria.andCreateEmpCodeEqualTo(condition.getCreateEmpCode());
		}
		return where;
	}

	@Override
	public String justUploadImag(File img, String extName,Integer userId) throws Exception {
		FileServerConfig config = fileService.getServerConfig(vfsSetting.getServerName());
		
		String fileId = null;

		if(null != img) {
			// 上传图片
//			fileId = fileService.uploadFile(config, img, "jpg", userid+"_"+System.currentTimeMillis()+"_"+Math.random(), userid, false, true);
			fileId = fileService.uploadFile(config, img,extName, userId);
		}
		return fileId;
	}
	
	
}
