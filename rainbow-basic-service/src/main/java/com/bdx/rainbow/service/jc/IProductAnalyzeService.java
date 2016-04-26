package com.bdx.rainbow.service.jc;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.bdx.rainbow.entity.jc.Pcsdn;

public interface IProductAnalyzeService {

	
//	public Pgds gdsAnalyzeByHtml(String barcode,String html) throws Exception;
	
	public List<Pcsdn> csdnAnalyzeByDoc(File file) throws Exception;
	
//	public ProductorGds gdsAnalyzeByHtml(String html) throws Exception;
	
	public void gdsAnalyze(String barcode,Map<String,String> contentMap,String userid) throws Exception;
	
}
