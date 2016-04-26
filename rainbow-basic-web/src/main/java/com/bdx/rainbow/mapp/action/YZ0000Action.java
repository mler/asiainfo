package com.bdx.rainbow.mapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.bdx.rainbow.entity.basic.mysql.TBasicPGds;
import com.bdx.rainbow.entity.basic.mysql.TBasicSku;
import com.bdx.rainbow.service.basic.IBasicSkuService;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.entity.jc.ProductorGds;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.model.req.YZ0000Request;
import com.bdx.rainbow.mapp.model.rsp.YZ0000Response;
import com.bdx.rainbow.service.jc.IProductService;

/**
 * 根据条码查询商品
 * mler @author mler
 * @since 2015-11-4
 * mapp 
 *
 */
@Service("yz0000")
@Action(bizcode="yz0000",version="1.0",usercheck=false, ipcheck=false)
@Scope("prototype")
public class YZ0000Action extends AbstractMappAction<YZ0000Request, YZ0000Response>{
	
	@Autowired
	private IProductService productService;

//	@Autowired
//	private ISkuService skuService;
	
	@Autowired
	private IBasicSkuService basicSkuService;

	@Override
	public void doAction(YZ0000Request request, YZ0000Response response) throws Exception {
		
		if(StringUtils.isBlank(request.getBarcode()) == false)
		{
			/**
			JcSku jcSku = skuService.findSkuByCode(request.getBarcode());

			if(jcSku != null)
			{
				this.response.setEntities(new ArrayList<YZ0000Response.SkuEntity>());
				YZ0000Response.SkuEntity entity = new YZ0000Response.SkuEntity();

//				entity.setId(jcSku.getSkuId());
				entity.setName(jcSku.getSkuName());
				entity.setCheckInfo(null);
				entity.setCode(jcSku.getSkuBarcode());
				entity.setMainRes(new HashMap<String,String>(0));
				entity.setProductor(jcSku.getpName());
				entity.setLogNo(null);
				entity.setPunishInfo(null);
				entity.setSpec(jcSku.getSkuSpec());
				entity.setBrand(jcSku.getSkuBrand());
				entity.setImgs(new HashSet<String>(1));
				entity.setIco(skuService.findImgPathByFileId(jcSku.getIco()));
				entity.getMainRes().put("无", "");
				entity.setProductArea(jcSku.getpArea());
				entity.setProductAddress(jcSku.getpAddress());
//				if(StringUtil.isBlank(jcSku.getCreateEmpCode()) == false)
//				{
//					SysUser user = userService.getUserById(Integer.valueOf(jcSku.getCreateEmpCode()));
//					entity.setCreator(user==null?null:user.getUserLoginName());
//				}
				entity.setCreator(jcSku.getCreateEmpCode());
				
				this.response.getEntities().add(entity);
				*/
			TBasicSku sku = basicSkuService.getSkuByBarcode(request.getBarcode());

			if(sku != null)
			{
				response.setEntities(new ArrayList<YZ0000Response.SkuEntity>());
				YZ0000Response.SkuEntity entity = new YZ0000Response.SkuEntity();

//				entity.setId(jcSku.getSkuId());
				entity.setName(sku.getSkuName());
				entity.setCheckInfo(null);
				entity.setCode(sku.getSkuBarcode());
				entity.setMainRes(new HashMap<String,String>(0));
				entity.setProductor(sku.getCompanyName());
				entity.setLogNo(null);
				entity.setPunishInfo(null);
				entity.setSpec(sku.getSpec());
				entity.setBrand(sku.getBrand());
				entity.setImgs(new HashSet<String>(1));
				entity.setIco(sku.getIcoId());
				entity.getMainRes().put("无", "");
				entity.setProductArea(sku.getProductionArea());
				entity.setProductAddress(sku.getProductAddress());
//				if(StringUtil.isBlank(jcSku.getCreateEmpCode()) == false)
//				{
//					SysUser user = userService.getUserById(Integer.valueOf(jcSku.getCreateEmpCode()));
//					entity.setCreator(user==null?null:user.getUserLoginName());
//				}
				entity.setCreator(sku.getCreater());
				
				response.getEntities().add(entity);

			} else {
//				Pgds pgds = productService.findPgdsByBarcode(request.getBarcode());
				TBasicPGds pgds = productService.findBasicPgdsByBarcode(request.getBarcode());

				if(pgds != null)
				{
					response.setEntities(new ArrayList<YZ0000Response.SkuEntity>());
					YZ0000Response.SkuEntity entity = new YZ0000Response.SkuEntity();

					entity.setName(pgds.getName());
					entity.setCode(request.getBarcode());
					entity.setMainRes(new HashMap<String,String>(0));
					entity.setProductor(pgds.getProductor());
					entity.setSpec(pgds.getSpec());
					if(StringUtil.isBlank(pgds.getComposition()) == false)
						entity.getMainRes().put(pgds.getComposition(), "");

					if(StringUtils.isBlank(pgds.getProductor())
							&& StringUtils.isBlank(pgds.getProductCode())==false)
					{
						ProductorGds productorGds = productService.findProductorGdsByPcode(pgds.getProductCode());
						if(productorGds != null)
						{
							entity.setProductor(productorGds.getName());
						}
					}

					response.getEntities().add(entity);
				}
			}

//	    	entity.setSalers(new ArrayList<YZ0000Response.Saler>());
//	    	YZ0000Response.Saler saler1 = new YZ0000Response.Saler();
//	    	saler1.setMobile("886391884");
//	    	saler1.setName("好又多超市");
//	    	saler1.setAddress("上塘路54号");
//	    	YZ0000Response.Saler saler2 = new YZ0000Response.Saler();
//	    	saler2.setMobile("88301884");
//	    	saler2.setName("世纪联华超市");
//	    	saler2.setAddress("中山北路594号");
//	    	
//	    	entity.getSalers().add(saler1);
//	    	entity.getSalers().add(saler2);
		}
		
	}


}
