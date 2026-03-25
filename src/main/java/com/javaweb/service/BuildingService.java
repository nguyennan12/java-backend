package com.javaweb.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.entity.BuildingEntity;

public interface BuildingService {
	List<BuildingDTO> findAll(Map<String, Object> params, List<String> typeCode);
	
	BuildingDTO create(BuildingDTO buildingDTO);
	
	void delete(Long id);
	
	BuildingDTO update(@RequestBody BuildingDTO buildingDTO, Long id);
}
