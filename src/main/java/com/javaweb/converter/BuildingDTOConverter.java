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
import com.javaweb.utils.StringUtils;

@Component
public class BuildingDTOConverter {
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private RentAreaRepository rentAreaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public BuildingDTO toBuildingDTO(BuildingEntity item) {
		BuildingDTO building = modelMapper.map(item, BuildingDTO.class);

     
        StringBuilder address = new StringBuilder("");
        if (StringUtils.checkString(item.getStreet())) address.append(item.getStreet());
        if (StringUtils.checkString(item.getWard())) address.append(", ").append(item.getWard());
        
        if (item.getDistrict() != null) {
            address.append(", ").append(item.getDistrict().getName());
        }
        building.setAddress(address.toString());

       
        List<RentAreaEntity> rentAreas = item.getRentAreas();
        if (rentAreas != null && !rentAreas.isEmpty()) {
            String rentAreasString = rentAreas.stream()
                    .map(area -> area.getValue().toString())
                    .collect(Collectors.joining(", "));
            building.setRentAreas(rentAreasString);
        }

        return building;
	}
}
