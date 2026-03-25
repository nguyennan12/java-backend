package com.javaweb.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {
	@Autowired
	private BuildingService buildingService;
	
    @GetMapping("/api/building/")
    public List<BuildingDTO> getBuilding(@RequestParam Map<String, Object> params,
    									 @RequestParam(name="typeCode", required = false) List<String> typeCode
    		) {
    	List<BuildingDTO> result = buildingService.findAll(params, typeCode);
        return result;
    }
    
    @PostMapping("api/building/")
    public BuildingDTO createBuilding(@RequestBody BuildingDTO buildingDTO) {
    	return buildingService.create(buildingDTO);
    }
    
    @DeleteMapping("api/building/{id}")
    public void deleteBuilding(@PathVariable Long id) {
    	 buildingService.delete(id);
    }
    
    @PutMapping("api/building/{id}")
    public BuildingDTO createBuilding(@RequestBody BuildingDTO buildingDTO, 
    									 @PathVariable Long id) {
    	return buildingService.update(buildingDTO, id);
    }
}