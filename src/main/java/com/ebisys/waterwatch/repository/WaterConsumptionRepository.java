package com.ebisys.waterwatch.repository;

import com.ebisys.waterwatch.model.WaterConsumption; 

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface WaterConsumptionRepository extends JpaRepository<WaterConsumption , Integer>{
	
	@Query(value = "select * from waterconsumption"
			+ " order by avgMonthlyKL desc"
			+ " limit 10"
			, nativeQuery = true)
	List<WaterConsumption> findTopTenConsumers();
	
	
}
