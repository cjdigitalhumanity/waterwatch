package com.ebisys.waterwatch.controller;

import com.ebisys.waterwatch.model.WaterConsumption;
import com.ebisys.waterwatch.service.WaterConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/waterconsumption")
public class WaterConsumptionController {

	@Autowired
	private WaterConsumptionService waterconsumptionservice;
	
	@GetMapping
	public List<WaterConsumption> findAll() {
		return waterconsumptionservice.findAll();
	}
	
	@GetMapping("/topten")
	public List<WaterConsumption> findTopTenConsumers() {
		return waterconsumptionservice.findTopTenConsumers();
	}
}
