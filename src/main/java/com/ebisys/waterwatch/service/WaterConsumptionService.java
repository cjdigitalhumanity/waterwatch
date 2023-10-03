package com.ebisys.waterwatch.service;

import com.ebisys.waterwatch.model.WaterConsumption;

import java.util.List;

public interface WaterConsumptionService {
	List<WaterConsumption> findAll();
	List<WaterConsumption> findTopTenConsumers();
	
	
	

}
