package com.ebisys.waterwatch.service.implementationFolder;

import java.util.List;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ebisys.waterwatch.model.WaterConsumption;
import com.ebisys.waterwatch.repository.WaterConsumptionRepository;
import com.ebisys.waterwatch.service.WaterConsumptionService;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import com.opencsv.*;

@Service
public class WaterConsumptionServiceImplementation implements WaterConsumptionService{
	
	@Autowired
	private WaterConsumptionRepository waterConsumptionRepository;
	
	public List <WaterConsumption> findAll(){
		save_csv();
		return waterConsumptionRepository.findAll();
	}
	
	public List <WaterConsumption> findTopTenConsumers(){
		return waterConsumptionRepository.findTopTenConsumers();
	}
	
	public void save_csv() {
		//put a * and a / when not using this
				List<WaterConsumption> res = waterConsumptionRepository.findAll();
				
				
				if(res.isEmpty() == true) {
					System.out.println("No Data");
					
					String [] HEADERS = {"Suburb", "AverageMonthlyKL" , "Latitude" , "Longitude"};
					String fileLocation = "C:\\Users\\cjdan\\spring-tool-suite-4-4.19.1.RELEASE-e4.28.0-win32.win32.x86_64.self-extracting(1)\\contents\\sts-4.19.1.RELEASE\\waterwatch\\src\\main\\resources\\waterwatch_data.csv";
					
					
					try {
						Reader in = new FileReader(fileLocation);
						CSVFormat cSVFormat = CSVFormat.DEFAULT.builder()
								.setHeader(HEADERS)
								.setSkipHeaderRecord(false)
								.build();
						Iterable<CSVRecord> records = CSVParser.parse(in, cSVFormat);
						
						/*
						Reader in = new FileReader(fileLocation);
						Iterable<CSVRecord> records = CSVFormat.DEFAULT
								.withHeader(HEADERS)
								.withFirstRecordAsHeader()
								.parse(in);
						//add a new break */
						for (CSVRecord record : records){
							String suburb = record.get("Suburb");
							String averageMonthlyKL = record.get("AverageMonthlyKL");
							String latitude = record.get("Latitude");
							String longitude = record.get("Longitude");
							
							// Convert to proper Datatypes 
							
							Integer avgMonthlyKL =  Integer.valueOf(averageMonthlyKL);
							Double dLatitude = Double.parseDouble(latitude);
							Double dLongitude = Double.parseDouble(longitude);
							Point geom = new GeometryFactory().createPoint(new Coordinate(dLatitude,dLongitude));
							
							
							//Load Data into the WaterConsumption Table
							
							WaterConsumption wc = new WaterConsumption();
							wc.setSuburb(suburb);
							wc.setAvgMonthlyKL(avgMonthlyKL);
							wc.setGeom(geom);
							waterConsumptionRepository.save(wc);

						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else {
					System.out.println("Data Loaded");
					
				}
		//put a * and a / when not using this
			
		/*
		//Start of OPCSV Stuff
	 	if(res.isEmpty() == true) {
		
			String file = "C:\\Users\\cjdan\\spring-tool-suite-4-4.19.1.RELEASE-e4.28.0-win32.win32.x86_64.self-extracting(1)\\contents\\sts-4.19.1.RELEASE\\waterwatch\\src\\main\\resources\\waterwatch_data.csv";
	
			FileReader filereader = new FileReader(file);
	  
	            // create csvReader object passing
			  try {
	            CSVReader csvReader = new CSVReader(filereader);
	            String[] nextRecord;
	  
	            // we are going to read data line by line
	            while ((nextRecord = csvReader.readNext()) != null) {
	                for (String record : nextRecord) { 
	                	
						Point geom = new GeometryFactory().createPoint(new Coordinate(dLatitude,dLongitude));
	
	                	WaterConsumption wc = new WaterConsumption();
						wc.setSuburb(nextRecord[0]);
						wc.setAvgMonthlyKL(nextRecord[1]);
						wc.setGeom(geom);
						waterConsumptionRepository.save(wc);
	
	                }
	            }
			  }
	        catch (Exception e) {
	            e.printStackTrace();
	        }else {
			System.out.println("Data Loaded");
			}
        */
		//End of OPCSV Stuff
		
				
	}

}
