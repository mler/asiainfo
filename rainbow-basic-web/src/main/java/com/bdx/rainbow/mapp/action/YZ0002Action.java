package com.bdx.rainbow.mapp.action;

import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.common.SystemException;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.entity.basic.mysql.TBasicSkuItem;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.model.req.YZ0002Request;
import com.bdx.rainbow.mapp.model.rsp.YZ0002Response;
import com.bdx.rainbow.service.basic.IMedicineService;

/**
 * 药品监管码查询
 * mapp demo 2014/11/19 
 *
 */
@Service("yz0002")
@Action(bizcode="yz0002",version="1.0",usercheck=true, ipcheck=false)
@Scope("prototype")
public class YZ0002Action extends AbstractMappAction<YZ0002Request, YZ0002Response>{
	
//	@Autowired
//	private IDrugService drugService;
	
	@Autowired
	private IMedicineService medicineService;
	
	@Override
	public void doAction(YZ0002Request request, YZ0002Response response) throws BusinessException, SystemException,
			Exception {
		/**
		Drc drc = null;
		
		if(StringUtils.isBlank(request.getCode()) == false)
		{
			if(StringUtil.isBlank(request.getCode()))
				throw new Exception("监管码信息不正确");
			
			drc = drugService.selectDrcByCode(request.getCode());
			
			if(drc == null)
			{
				drc = drugService.findDrcFromCFDA(request.getCode());
			}
			
		}
		
		if(drc != null)
		{
			
			PropertyUtils.copyProperties(response, drc);
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				long expiryDate = sdf.parse(drc.getExpiryDate()).getTime();
				response.setIfExpiry((System.currentTimeMillis()>expiryDate));
			}
			catch(Exception e)
			{
				log.debug("有效期转化出错");
			}
		}
		*/
		
		TBasicSkuItem drc = null;
		
		if(StringUtils.isBlank(request.getCode()) == false)
		{
			if(StringUtil.isBlank(request.getCode()))
				throw new Exception("监管码信息不正确");
			
			drc = medicineService.selectDrcByCode(request.getCode());
			
			if(drc == null)
			{
				drc = medicineService.findDrcFromCFDA(request.getCode());
			}
			
		}
		
		if(drc != null)
		{
			PropertyUtils.copyProperties(response, drc);
			response.setLicenseNumber(drc.getApprovalNum());
			response.setLastTime(drc.getLastFlowTime());
			response.setFlow(drc.getLastFlow());
			response.setTitle(drc.getItemName());
			response.setPrepnType(drc.getForm());
			response.setPrepnUnit(drc.getFormUnit());
			response.setProductionBatch(drc.getBatchNo());
			response.setStatus(drc.getFlow());
			response.setJgmCode(drc.getItemBarcode());
			response.setManufacturer(drc.getManufactory());
			response.setExpiryDate(drc.getExpireDate());
			response.setSpecifications(drc.getSpec());
			
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				long expiryDate = sdf.parse(drc.getExpireDate()).getTime();
				response.setIfExpiry((System.currentTimeMillis()>expiryDate));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
			
	}
	

}
