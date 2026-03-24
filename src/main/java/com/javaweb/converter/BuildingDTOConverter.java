package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.entity.RentAreaEntity;

@Component
public class BuildingDTOConverter {
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private RentAreaRepository rentAreaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public BuildingDTO toBuildingDTO(BuildingEntity item) {
		//set basic data
		BuildingDTO building = modelMapper.map(item, BuildingDTO.class);
		
		//set address
		building.setAddress(item.getStreet() + ", " + item.getWard() + ", " + item.getDistrict().getName());
		
		//set rent area
		List<RentAreaEntity> rentAreaEntity = item.getRentAreas();
		String rentAreasString = rentAreaEntity.stream()
		        .map(area -> area.getValue().toString()) 
		        .collect(Collectors.joining(", ")); 
		building.setRentAreas(rentAreasString);
		
		return building;
	}
}
