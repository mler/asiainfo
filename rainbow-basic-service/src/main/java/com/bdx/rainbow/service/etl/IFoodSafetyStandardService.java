package com.bdx.rainbow.service.etl;

import java.util.List;

import com.bdx.rainbow.entity.etl.FoodSafetyStandard;

public interface IFoodSafetyStandardService {

	public void insertBatch(List<FoodSafetyStandard> records) throws Exception;
}
