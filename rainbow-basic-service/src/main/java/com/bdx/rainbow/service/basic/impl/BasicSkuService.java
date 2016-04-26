package com.bdx.rainbow.service.basic.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.basic.common.SkuConstant;
import com.bdx.rainbow.basic.dubbo.bean.DubboSku;
import com.bdx.rainbow.basic.dubbo.bean.DubboSkuFood;
import com.bdx.rainbow.basic.dubbo.bean.DubboSkuImag;
import com.bdx.rainbow.common.configuration.Constants;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.entity.basic.mysql.TBasicEnterpriseSku;
import com.bdx.rainbow.entity.basic.mysql.TBasicSku;
import com.bdx.rainbow.entity.basic.mysql.TBasicSkuExample;
import com.bdx.rainbow.entity.basic.mysql.TBasicSkuFood;
import com.bdx.rainbow.entity.basic.mysql.TBasicSkuImag;
import com.bdx.rainbow.entity.basic.mysql.TBasicSkuImagExample;
import com.bdx.rainbow.entity.basic.mysql.TBasicSkuMedicine;
import com.bdx.rainbow.entity.jc.JcSku;
import com.bdx.rainbow.entity.jc.JcSkuExample;
import com.bdx.rainbow.entity.vfs.FileServerConfig;
import com.bdx.rainbow.entity.vfs.FileUploadInfo;
import com.bdx.rainbow.entity.vfs.FileUploadInfoExample;
import com.bdx.rainbow.mapper.basic.mysql.SelfSkuMapper;
import com.bdx.rainbow.mapper.basic.mysql.TBasicEnterpriseSkuMapper;
import com.bdx.rainbow.mapper.basic.mysql.TBasicSkuFoodMapper;
import com.bdx.rainbow.mapper.basic.mysql.TBasicSkuImagMapper;
import com.bdx.rainbow.mapper.basic.mysql.TBasicSkuMapper;
import com.bdx.rainbow.mapper.basic.mysql.TBasicSkuMedicineMapper;
import com.bdx.rainbow.mapper.vfs.FileUploadInfoMapper;
import com.bdx.rainbow.service.basic.IBasicSkuService;
import com.bdx.rainbow.service.vfs.IFile;
import com.bdx.rainbow.vfs.dubbo.IFileDubboService;

@Service("basicSkuService")
@Transactional(rollbackFor=Exception.class,value="transactionManager")
public class BasicSkuService implements IBasicSkuService {
	
	private Logger logger = LoggerFactory.getLogger(BasicSkuService.class);
	
	@Autowired
	private TBasicSkuMapper tBasicSkuMapper;
	
//	@Autowired
//	private IFile fileService;
//	
//	@Autowired
//	private VfsSetting vfsSetting;
	
	@Autowired
	private TBasicSkuImagMapper tBasicSkuImagMapper;
	
	@Autowired
	private TBasicSkuMedicineMapper tBasicSkuMedicineMapper;
	
	@Autowired
	private TBasicSkuFoodMapper tBasicSkuFoodMapper;
	
	@Autowired
	private TBasicEnterpriseSkuMapper tBasicEnterpriseSkuMapper;
	
	@Autowired
	private SelfSkuMapper selfSkuMapper;
	
	@Autowired
	private IFileDubboService fileDubboService;
	
	private static final String VFS_SERVER_NAME = "VFS_RAINBOW_FILE_SERVER";
	
	@Override
	public void saveSkuNoDeleteImage(TBasicSku sku, Object detail, List<byte[]> images,Integer enterpriseId,String userLoginName,Integer userId)
			throws Exception {
		if (sku != null) {
			Timestamp now = DateUtil.getCurrent();
			if(sku.getSkuId() == null){//create
				sku.setCreater(userLoginName);
				sku.setCreateTime(now);
				tBasicSkuMapper.insertSelective(sku);//不能 skuId = ...insert
				int skuId = sku.getSkuId();
				
				if (detail != null && sku.getSkuTypeId() != null) {//根据 sku.getSkuTypeId() 来判断
					if (detail instanceof TBasicSkuFood) {
						TBasicSkuFood food = (TBasicSkuFood)detail;
						food.setSkuId(skuId);
						food.setCreateTime(now);
						food.setCreater(userLoginName);
						tBasicSkuFoodMapper.insert(food);
					}
					
					if (detail instanceof TBasicSkuMedicine) {
						TBasicSkuMedicine medicine = (TBasicSkuMedicine)detail;
						medicine.setCreateTime(now);
						medicine.setCreater(userLoginName);
						tBasicSkuMedicineMapper.insert(medicine);
					}
				}
				sku.setSkuId(skuId);
				if (enterpriseId != null) {
					TBasicEnterpriseSku enterpriseSku = new TBasicEnterpriseSku();
					enterpriseSku.setCreater(userLoginName);
					enterpriseSku.setCreateTime(now);
					enterpriseSku.setSkuId(skuId);
					enterpriseSku.setEnterpriseId(enterpriseId);
					tBasicEnterpriseSkuMapper.insertSelective(enterpriseSku);
					
				}
				
			}else {//update
				if (detail != null && sku.getSkuTypeId() != null) {//根据 sku.getSkuTypeId() 来判断
					if (detail instanceof TBasicSkuFood) {
						TBasicSkuFood food = (TBasicSkuFood)detail;
						food.setSkuId(sku.getSkuId());
						food.setUpdateTime(now);
						food.setUpdater(userLoginName);
						tBasicSkuFoodMapper.updateByPrimaryKeySelective(food);
					}
					
					if (detail instanceof TBasicSkuMedicine) {
						TBasicSkuMedicine medicine = (TBasicSkuMedicine)detail;
						medicine.setSkuId(sku.getSkuId());
						medicine.setUpdateTime(now);
						medicine.setUpdater(userLoginName);
						tBasicSkuMedicineMapper.updateByPrimaryKeySelective(medicine);
					}
				}
				sku.setUpdater(userLoginName);
				sku.setUpdateTime(now);
				tBasicSkuMapper.updateByPrimaryKeySelective(sku);
			}
			if (images != null) {
				for (int i = 0; i < images.size(); i++) {
//					String fileId1 = fileService.uploadFile(null, images.get(i), "jpg", userId+"_"+System.currentTimeMillis()+"_"+Math.random(), userId, false, true);
					String fileId = fileDubboService.uploadFile(VFS_SERVER_NAME, images.get(i), "jpg", userId+"_"+System.currentTimeMillis()+"_"+Math.random(), userId, false, true);
//					String url = fileDubboService.getHttpUrl(fileId, "VFS_RAINBOW_FILE_SERVER_DUBBO");
					TBasicSkuImag imag = new TBasicSkuImag();
					imag.setCreater(userLoginName);
					imag.setCreateTime(now);
					imag.setSkuId(sku.getSkuId());
					imag.setVfsId(fileId);
					tBasicSkuImagMapper.insert(imag);
				}
			}
			
		}
	}

	@Override
	public TBasicSku getSkuByBarcode(String barcode) throws Exception {
		TBasicSkuExample example = new TBasicSkuExample();
		example.createCriteria().andSkuBarcodeEqualTo(barcode);
		example.setOrderByClause("CREATE_TIME desc");
		List<TBasicSku> list = tBasicSkuMapper.selectByExample(example);
		if (list != null && list.size() != 0) {
			TBasicSku sku = list.get(0);
			if (StringUtils.isNotEmpty(sku.getIcoId())) {
//				sku.setIcoId(fileService.getHttpUrl(sku.getIcoId()));
				sku.setIcoId(fileDubboService.getHttpUrl(sku.getIcoId(),VFS_SERVER_NAME));
			}
			return sku;
		}
		return null;
	}

	@Override
	public void simpleSaveSkuByBarcode(TBasicSku sku,String userLoginName) throws Exception {
		TBasicSku skuOld = getSkuByBarcode(sku.getSkuBarcode());
		Timestamp now = DateUtil.getCurrent();
		if (skuOld == null) {
			sku.setCreateTime(now);
			sku.setCreater(userLoginName);
			tBasicSkuMapper.insertSelective(sku);
		}else {
			sku.setUpdateTime(now);
			sku.setUpdater(userLoginName);
			sku.setSkuId(skuOld.getSkuId());
			tBasicSkuMapper.updateByPrimaryKeySelective(sku);
		}
	}

	@Override
	public String saveSkuAndUploadImage(TBasicSku sku, byte[] ico,
			List<byte[]> imgs, Integer userId, String userLoginName)
			throws Exception {
		if(sku == null || StringUtils.isBlank(sku.getSkuBarcode()))
			return null;
		
//		FileServerConfig config = fileService.getServerConfig(vfsSetting.getServerName());
		
		if(StringUtils.isBlank(sku.getSkuBarcode()))
			logger.warn("sku barcode is empty");

		// 上传icon
//		String ico_id = (ico == null) ? null : fileService.uploadFile(config, ico, "jpg", userId+"_"+System.currentTimeMillis()+"_"+Math.random(), userId, false, true);
		String ico_id = (ico == null) ? null : fileDubboService.uploadFile("VFS_RAINBOW_FILE_SERVER", ico, "jpg", userId+"_"+System.currentTimeMillis()+"_"+Math.random(), userId, false, true);
		sku.setIcoId(ico_id);

		sku = updateSkuNoSave(sku,StringUtils.isEmpty(userLoginName)?Constants.UNKNOW_USER_ID.toString():userLoginName);

		saveSkuNoDeleteImage(sku, null, imgs,null, userLoginName, userId);
		
		return sku.getSkuBarcode();
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
	private TBasicSku updateSkuNoSave(TBasicSku newSku,String userLoginName) throws Exception
	{

		/**
		 * 将如果正式商品表中无该商品，则新加
		 */
		TBasicSku sku = getSkuByBarcode(newSku.getSkuBarcode());
		
		
		if(sku == null)
		{
			sku = newSku;
			sku.setCreater(userLoginName);
			sku.setCreateTime(new Timestamp(System.currentTimeMillis()));
			sku.setStatus(SkuConstant.STATUS_WAIT_FOR_AUDIT);
		}
		
		/**
		 * 判断是否与原本的实体有更改
		 */
		boolean nochange = true;
		
		StringBuffer changeNote = new StringBuffer("");
		
		
		if(StringUtils.isNotEmpty(newSku.getSkuName()) && newSku.getSkuName().equals(sku.getSkuName()) == false)
		{
			changeNote.append(" [skuName:" + sku.getSkuName() + "=>" + newSku.getSkuName() + "] ");
			sku.setSkuName(newSku.getSkuName());
			nochange = false;
		}
		if(StringUtils.isNotEmpty(newSku.getBrand()) && newSku.getBrand().equals(sku.getBrand()) == false)
		{
			changeNote.append(" [brand:" + sku.getBrand() + "=>" + newSku.getBrand() + "] ");
			sku.setBrand(newSku.getBrand());
			nochange = false;
		}
		if(StringUtils.isNotEmpty(newSku.getProductionArea()) && newSku.getProductionArea().equals(sku.getProductionArea()) == false)
		{
			changeNote.append(" [productionArea:" + sku.getProductionArea() + "=>" + newSku.getProductionArea() + "] ");
			sku.setProductionArea(newSku.getProductionArea());
			nochange = false;
		}
		if(StringUtils.isNotEmpty(newSku.getProductAddress()) && newSku.getProductAddress().equals(sku.getProductAddress()) == false)
		{
			changeNote.append(" [productAddress:" + sku.getProductAddress() + "=>" + newSku.getProductAddress() + "] ");
			sku.setProductAddress(newSku == null ? "" : newSku.getProductAddress());
			nochange = false;
		}
		if(StringUtils.isNotEmpty(newSku.getCompanyName()) && newSku.getCompanyName().equals(sku.getCompanyName()) == false)
		{
			changeNote.append(" [companyName:" + sku.getCompanyName() + "=>" + newSku.getCompanyName() + "] ");
			sku.setCompanyName(newSku.getCompanyName());
			nochange = false;
		}
		if(StringUtils.isNotEmpty(newSku.getLifeTime()) && newSku.getLifeTime().equals(sku.getLifeTime()) == false)
		{
			changeNote.append(" [lifeTime:" + sku.getLifeTime() + "=>" + newSku.getLifeTime() + "] ");
			sku.setLifeTime(newSku.getLifeTime());
			nochange = false;
		}
		if(StringUtils.isNotEmpty(newSku.getSpec()) && newSku.getSpec().equals(sku.getSpec()) == false)
		{
			changeNote.append(" [spec:" + sku.getSpec() + "=>" + newSku.getSpec() + "] ");
			sku.setSpec(newSku.getSpec());
			nochange = false;
		}
		if(StringUtils.isNotEmpty(newSku.getManufactoryCode()) && newSku.getManufactoryCode().equals(sku.getManufactoryCode()) == false)
		{
			changeNote.append(" [manufactoryCode:" + sku.getManufactoryCode() + "=>" + newSku.getManufactoryCode() + "] ");
			sku.setManufactoryCode(newSku == null ? "" : newSku.getManufactoryCode());
			nochange = false;
		}
		// 不是新增，才会判断是否有
		if(sku != newSku && StringUtils.isNotEmpty(newSku.getIcoId())) {
			changeNote.append(" [ico:" + sku.getIcoId() + "=>" + newSku.getIcoId() + "] ");
			sku.setIcoId(newSku.getIcoId());
			nochange = false;
		}


		if(nochange == false)
		{
			sku.setStatus("0");
			sku.setUpdater(userLoginName);
			sku.setUpdateTime(DateUtil.getCurrent());
			sku.setChangeNote(changeNote.toString());
		} else {
			sku.setChangeNote("新增");
		}
		
		return sku;
	}
	

	@Override
	public Map<String, Object> getSkuList(TBasicSku condition, Integer start,
			Integer limit) throws Exception {
		TBasicSkuExample where = convertCondition(condition);
		if (start >= 0 && limit > 0) {
			where.setLimitClauseStart(start);
			where.setLimitClauseCount(limit);
		}
		where.setOrderByClause("CREATE_TIME desc");
		List<TBasicSku> list = tBasicSkuMapper.selectByExample(where);
		int total = tBasicSkuMapper.countByExample(where);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (list != null && list.size() != 0) {
			for (TBasicSku item:list) {
				if (StringUtils.isNotEmpty(item.getIcoId())) {
//					String string = fileService.getHttpUrl(item.getIcoId());
					String string = fileDubboService.getHttpUrl(item.getIcoId(),VFS_SERVER_NAME);
					item.setIcoId(string);
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
	
	private TBasicSkuExample convertCondition(TBasicSku condition){
		TBasicSkuExample where = new TBasicSkuExample();
		TBasicSkuExample.Criteria criteria = where.createCriteria();
		
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
		if (StringUtils.isNotEmpty(condition.getCreater())) {
			criteria.andCreaterEqualTo(condition.getCreater());
		}
		return where;
	}
	
	private Map<String, Object> convertConditionMap(TBasicSku condition,Integer enterpriseId){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		if (enterpriseId != null) {
			paramMap.put("enterpriseId",enterpriseId);
		}
		if (StringUtils.isNotEmpty(condition.getSkuBarcode())) {
			paramMap.put("skuBarcode",condition.getSkuBarcode());
		}
		if (StringUtils.isNotEmpty(condition.getStatus())) {
			if (condition.getStatus().startsWith("!")) {
				paramMap.put("nStatus", condition.getStatus().substring(1));
			}else {
				paramMap.put("status", condition.getStatus());
			}
			
		}
		if (StringUtils.isNotEmpty(condition.getSkuName())) {
			paramMap.put("skuName","%" + condition.getSkuName() + "%");
		}
		if (StringUtils.isNotEmpty(condition.getCreater())) {
			paramMap.put("creater",condition.getCreater());
		}
		return paramMap;
	}

	@Override
	public List<TBasicSkuImag> getSkuImagsBySkuId(Integer skuId,String fileType)
			throws Exception {
		TBasicSkuImagExample example = new TBasicSkuImagExample();
		TBasicSkuImagExample.Criteria criteria = example.createCriteria();
		criteria.andSkuIdEqualTo(skuId);
		if (StringUtils.isNotEmpty(fileType)) {
			criteria.andFileTypeEqualTo(fileType);
		}
		example.setOrderByClause("FILE_INDEX asc");
		List<TBasicSkuImag> imags = tBasicSkuImagMapper.selectByExample(example);
		
		List<String> fileIds = new ArrayList<String>();
		for (TBasicSkuImag imag:imags) {
			if (StringUtils.isNotEmpty(imag.getVfsId())) {
				fileIds.add(imag.getVfsId());
			}
		}
//		Map<String,String> vfsMap = fileService.getHttpUrls(fileIds, null);
		Map<String,String> vfsMap = fileDubboService.getHttpUrls(fileIds,VFS_SERVER_NAME);

		for (TBasicSkuImag imag:imags) {
			if (StringUtils.isNotEmpty(imag.getVfsId())) {
				imag.setFileUrl(vfsMap.get(imag.getVfsId()));
			}
		}
		return imags;
	}

	@Override
	public Map<String, Object> getDubboSkuList(DubboSku condition,Integer enterpriseId,
			Integer start, Integer limit) throws Exception {
		
		TBasicSku sku = new TBasicSku();
		PropertyUtils.copyProperties(sku,condition);
		/**
		TBasicSkuExample where = convertCondition(sku);
		if (start >= 0 && limit > 0) {
			where.setLimitClauseStart(start);
			where.setLimitClauseCount(limit);
		}
		where.setOrderByClause("CREATE_TIME desc");
		List<TBasicSku> list = tBasicSkuMapper.selectByExample(where);
		int total = tBasicSkuMapper.countByExample(where);
		*/
		
		Map<String, Object> paramMap = convertConditionMap(sku, enterpriseId);
		if (enterpriseId != null) {
			paramMap.put("enterpriseId", enterpriseId);
		}
		paramMap.put("orderByClause", "CREATE_TIME desc");
		if (start >= 0 && limit > 0) {
			paramMap.put("limitClauseStart", start);
			paramMap.put("limitClauseCount", limit);
		}
		
		List<TBasicSku> list = selfSkuMapper.getSkus(paramMap);
		int total = selfSkuMapper.countSku(paramMap);
		
		
		List<DubboSku> dubboSkus = new ArrayList<DubboSku>();
		DubboSku dubboSku = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (list != null && list.size() != 0) {
			for (TBasicSku item:list) {
				if (StringUtils.isNotEmpty(item.getIcoId())) {
					String string = fileDubboService.getHttpUrl(item.getIcoId(),VFS_SERVER_NAME);
					item.setIcoId(string);
				}
				dubboSku = new DubboSku();
				PropertyUtils.copyProperties(dubboSku,item);
				dubboSkus.add(dubboSku);
			}
			
		}
		resultMap.put("list", dubboSkus);
		resultMap.put("total", total);
		return resultMap;
	}

	@Override
	public DubboSku getDubboSkuByBarcode(String barcode) throws Exception {
		if (StringUtils.isNotEmpty(barcode)) {
			TBasicSku sku = getSkuByBarcode(barcode);
			if (sku != null) {
				DubboSku dubboSku = new DubboSku();
				PropertyUtils.copyProperties(dubboSku, sku);
				return dubboSku;
			}else {
				return null;
			}
		}
		
		return null;
	}

	@Override
	public List<DubboSkuImag> getDubboSkuImagsBySkuId(Integer skuId,
			String fileType) throws Exception {
		TBasicSkuImagExample example = new TBasicSkuImagExample();
		TBasicSkuImagExample.Criteria criteria = example.createCriteria();
		criteria.andSkuIdEqualTo(skuId);
		if (StringUtils.isNotEmpty(fileType)) {
			criteria.andFileTypeEqualTo(fileType);
		}
		example.setOrderByClause("FILE_INDEX asc");
		List<TBasicSkuImag> imags = tBasicSkuImagMapper.selectByExample(example);
		
		List<String> fileIds = new ArrayList<String>();
		for (TBasicSkuImag imag:imags) {
			if (StringUtils.isNotEmpty(imag.getVfsId())) {
				fileIds.add(imag.getVfsId());
			}
		}
		Map<String,String> vfsMap = fileDubboService.getHttpUrls(fileIds, VFS_SERVER_NAME);

		if (imags != null && imags.size() != 0) {
			List<DubboSkuImag> dubboImags = new ArrayList<DubboSkuImag>();
			DubboSkuImag dubboImag = null;
			for (TBasicSkuImag imag:imags) {
				if (StringUtils.isNotEmpty(imag.getVfsId())) {
					imag.setFileUrl(vfsMap.get(imag.getVfsId()));
				}
				dubboImag = new DubboSkuImag();
				PropertyUtils.copyProperties(dubboImag, imag);
				dubboImags.add(dubboImag);
			}
			return dubboImags;
			
		}
		
		return null;
	}

	@Override
	public Map<String, Object> getDubboSku(String queryCondition,Integer enterpriseId,Integer start, Integer limit) throws Exception {
		/**
		TBasicSkuExample example = new TBasicSkuExample();
		TBasicSkuExample.Criteria criteria1 = example.createCriteria();
		criteria1.andSkuBarcodeLike("%" + queryCondition + "%");
		
		TBasicSkuExample.Criteria criteria2 = example.createCriteria();
		criteria2.andSkuNameLike("%" + queryCondition + "%");
		
		example.setOrderByClause("CREATE_TIME desc");
		if (start >= 0 && limit > 0) {
			example.setLimitClauseStart(start);
			example.setLimitClauseCount(limit);
		}
		int total = tBasicSkuMapper.countByExample(example);
		
		*/
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(queryCondition)) {
			paramMap.put("queryCondition","%" + queryCondition + "%");
		}
		paramMap.put("status", SkuConstant.STATUS_NORMAL);
		paramMap.put("enterpriseId", enterpriseId);
		paramMap.put("orderByClause", "CREATE_TIME desc");
		if (start >= 0 && limit > 0) {
			paramMap.put("limitClauseStart", start);
			paramMap.put("limitClauseCount", limit);
		}
		
		
		List<TBasicSku> list = selfSkuMapper.getSkus(paramMap);
		int total = selfSkuMapper.countSku(paramMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", total);
//		List<TBasicSku> list = tBasicSkuMapper.selectByExample(example);
		List<DubboSku> bList = new ArrayList<DubboSku>();
		if (list != null && list.size() != 0) {
			DubboSku bSku = null;
			for (TBasicSku sku:list) {
				bSku = new DubboSku();
				PropertyUtils.copyProperties(bSku, sku);
				bList.add(bSku);
			}
			resultMap.put("list", bList);
		}
		return resultMap;
	}

	@Override
	public void saveDubboFood(DubboSkuFood food, List<byte[]> images,Integer enterpriseId,String userLoginName,Integer userId)
			throws Exception {
		if (food != null) {
			Timestamp now = DateUtil.getCurrent();
			food.setCreateTime(now);
			if (food.getSkuTypeId() == null) {
				food.setSkuTypeId(1);
			}
			TBasicSku basic = new TBasicSku();
			TBasicSkuFood basicFood = new TBasicSkuFood();
			PropertyUtils.copyProperties(basic, food);
			PropertyUtils.copyProperties(basicFood, food);
			
			saveSkuNoDeleteImage(basic, basicFood, images,enterpriseId, userLoginName, userId);
			
		}
		
	}

	@Override
	public DubboSku getDubboSkuDetailById(Integer skuId) throws Exception {
		TBasicSku basicSku = tBasicSkuMapper.selectByPrimaryKey(skuId);
		DubboSku dubboSku = new DubboSku();
		if (basicSku != null && basicSku.getSkuTypeId() != null && (basicSku.getSkuTypeId().intValue() == 1 || basicSku.getSkuTypeId().intValue() == 11)) {//食品类
			TBasicSkuFood basicFood = tBasicSkuFoodMapper.selectByPrimaryKey(skuId);
			DubboSkuFood dubboFood = new DubboSkuFood();
			PropertyUtils.copyProperties(dubboFood, basicSku);
			PropertyUtils.copyProperties(dubboFood, basicFood);
			return dubboFood;
		}
		
		if (basicSku != null && basicSku.getSkuTypeId() != null && basicSku.getSkuTypeId().intValue() == 2) {//药品类
			TBasicSkuMedicine basicMed = tBasicSkuMedicineMapper.selectByPrimaryKey(skuId);
//			DubboSkuFood dubboFood = new DubboSkuFood();
//			PropertyUtils.copyProperties(dubboFood, basicFood);
//			return dubboFood;
		}
		PropertyUtils.copyProperties(dubboSku, basicSku);
		return dubboSku;
	}

}
