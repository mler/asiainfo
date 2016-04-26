package com.bdx.rainbow.service.jc;

import com.bdx.rainbow.entity.basic.mysql.TBasicPGds;
import com.bdx.rainbow.entity.basic.mysql.TBasicProductorGds;
import com.bdx.rainbow.entity.jc.Pcsdn;
import com.bdx.rainbow.entity.jc.Pgds;
import com.bdx.rainbow.entity.jc.ProductorGds;

public interface IProductService {

	public Pcsdn findPcsdnByBarcode(String barcode) throws Exception;
	
	public Pgds findPgdsByBarcode(String barcode) throws Exception;
	
	public ProductorGds findProductorGdsByPcode(String pcode) throws Exception;
	
	public TBasicPGds findBasicPgdsByBarcode(String barcode) throws Exception;
	
	public TBasicProductorGds findBasicProductorGdsByPcode(String pcode) throws Exception;
	
}
