package com.bdx.rainbow.service.jc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.entity.basic.mysql.TBasicPGds;
import com.bdx.rainbow.entity.basic.mysql.TBasicProductorGds;
import com.bdx.rainbow.entity.jc.Pcsdn;
import com.bdx.rainbow.entity.jc.Pgds;
import com.bdx.rainbow.entity.jc.ProductorGds;
import com.bdx.rainbow.mapper.basic.mysql.TBasicPGdsMapper;
import com.bdx.rainbow.mapper.basic.mysql.TBasicProductorGdsMapper;
import com.bdx.rainbow.mapper.jc.PcsdnMapper;
import com.bdx.rainbow.mapper.jc.PgdsMapper;
import com.bdx.rainbow.mapper.jc.ProductorGdsMapper;
import com.bdx.rainbow.service.jc.IProductService;


@Service
@Transactional
public class ProductService implements IProductService {

	@Autowired
	private PcsdnMapper pcsdnMapper;
	
	@Autowired
	private PgdsMapper pgdsMapper;
	
	@Autowired
	private ProductorGdsMapper productorGdsMapper;
	
	@Autowired
	private TBasicPGdsMapper tBasicPGdsMapper;
	
	@Autowired
	private TBasicProductorGdsMapper tBasicProductorGdsMapper;
	
	@Override
	public Pcsdn findPcsdnByBarcode(String barcode) throws Exception {
		return pcsdnMapper.selectByPrimaryKey(barcode);
	}

	@Override
	public Pgds findPgdsByBarcode(String barcode) throws Exception {
		return pgdsMapper.selectByPrimaryKey(barcode);
	}

	@Override
	public ProductorGds findProductorGdsByPcode(String pcode) throws Exception {
		return productorGdsMapper.selectByPrimaryKey(pcode);
	}

	@Override
	public TBasicPGds findBasicPgdsByBarcode(String barcode) throws Exception {
		return tBasicPGdsMapper.selectByPrimaryKey(barcode);
	}

	@Override
	public TBasicProductorGds findBasicProductorGdsByPcode(String pcode)
			throws Exception {
		return tBasicProductorGdsMapper.selectByPrimaryKey(pcode);
	}

}
