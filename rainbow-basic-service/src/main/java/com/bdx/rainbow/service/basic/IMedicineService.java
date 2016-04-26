package com.bdx.rainbow.service.basic;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.entity.basic.mysql.TBasicSkuItem;
import com.bdx.rainbow.entity.jc.Drc;

public interface IMedicineService {

	/**
	 * 保存药品信息
	 * @param record
	 * @throws Exception
	 */
	public void insertDrugItem(TBasicSkuItem record) throws Exception;
	
	
	/**
	 * 通过药品监管码，从本地数据库中搜索出药品信息
	 * @param jgmCode
	 * @return
	 * @throws Exception
	 */
	public TBasicSkuItem selectDrcByCode(String jgmCode) throws Exception;
	
	/**
	 * 通过药品监管码，从CFDA的接口中搜索出药品信息
	 * @param jgmCode
	 * @return
	 * @throws Exception
	 */
	public TBasicSkuItem findDrcFromCFDA(String jgmCode) throws Exception;
	
	
}
