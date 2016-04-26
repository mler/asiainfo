package com.bdx.rainbow.service.etl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.entity.etl.FoodSafetyStandard;
import com.bdx.rainbow.mapper.etl.FoodSafetyStandardMapper;
import com.bdx.rainbow.service.etl.IFoodSafetyStandardService;

@Service
@Transactional(rollbackFor=Exception.class,value="transactionManager") 
public class FoodSafetyStandardService implements IFoodSafetyStandardService {

	@Autowired
	private FoodSafetyStandardMapper foodSafetyStandardMapper;
	
	@Override
	public void insertBatch(List<FoodSafetyStandard> records) throws Exception {
		this.foodSafetyStandardMapper.insertBatch(records);
	}

}
