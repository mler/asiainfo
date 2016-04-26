package com.bdx.rainbow.service.jc;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.entity.jc.Drc;

public interface IDrugService {

	/**
	 * 保存药品信息
	 * @param record
	 * @throws Exception
	 */
	public void saveDrc(Drc record) throws Exception;
	
	
	/**
	 * 通过药品监管码，从本地数据库中搜索出药品信息
	 * @param jgmCode
	 * @return
	 * @throws Exception
	 */
	public Drc selectDrcByCode(String jgmCode) throws Exception;
	
	/**
	 * 通过药品监管码，从CFDA的接口中搜索出药品信息
	 * @param jgmCode
	 * @return
	 * @throws Exception
	 */
	public Drc findDrcFromCFDA(String jgmCode) throws Exception;
	
	/**
	 * 根据上传的信息，保存图片和药品信息
	 * @param drc
	 * @param imgs
	 * @return
	 * @throws Exception
	 */
//	public Map<String,List<String>> saveDrcAndUploadImage(Map<Drc,List<byte[]>> drcAndImage,Integer userid) throws Exception;
	
}
