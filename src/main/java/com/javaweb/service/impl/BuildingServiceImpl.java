package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.converter.BuildingSearchBuilderConverter;
import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.entity.RentAreaEntity;
import com.javaweb.service.BuildingService;

@Service
@Transactional
public class BuildingServiceImpl implements BuildingService{
	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private BuildingDTOConverter buildingDTOConverter;
	
	@Autowired
	private BuildingSearchBuilderConverter buildingSearchBuilderConverter;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Override
	public List<BuildingDTO> findAll(Map<String, Object> params, List<String> typeCode) {
		BuildingSearchBuilder buildingSearchBuilder = buildingSearchBuilderConverter.toBuildingSearchBuilder(params, typeCode);
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(buildingSearchBuilder);
		List<BuildingDTO> result = new ArrayList<BuildingDTO>();
		for(BuildingEntity item : buildingEntities) {
			BuildingDTO building = buildingDTOConverter.toBuildingDTO(item);
			result.add(building);
		}
		System.out.print(result);
		return result;
	}

	@Override
	public BuildingDTO create(BuildingDTO buildingDTO) {
		
		BuildingEntity buildingEntity = modelMapper.map(buildingDTO, BuildingEntity.class);
		
		if (buildingDTO.getDistrictId() != null) {
	        DistrictEntity district = districtRepository.findById(buildingDTO.getDistrictId())
	                .orElseThrow(() -> new RuntimeException("District not found!"));
	        buildingEntity.setDistrict(district);
	    }
		BuildingEntity savedBuilding = buildingRepository.save(buildingEntity);
		
		return buildingDTOConverter.toBuildingDTO(savedBuilding);
	}

	@Override
	public void delete(Long id) {
		buildingRepository.deleteById(id);
	}

	@Override
	public BuildingDTO update(BuildingDTO buildingDTO, Long id) {
		BuildingEntity buildingExists = buildingRepository.findById(id).get();
		modelMapper.map(buildingDTO, buildingExists);
		BuildingEntity savedBuilding = buildingRepository.save(buildingExists);
		
		return modelMapper.map(savedBuilding, BuildingDTO.class);
	}
	
	

}
