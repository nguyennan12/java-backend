
package com.javaweb.converter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.utils.MapUtil;

@Component
public final class BuildingSearchBuilderConverter {
	public BuildingSearchBuilder toBuildingSearchBuilder(Map<String, Object> params, List<String> typeCode) {
		BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder()
				.setName(MapUtil.getObject(params, "name", String.class))
                .setFloorArea(MapUtil.getObject(params, "floorArea", Long.class))
                .setNumberOfBasement(MapUtil.getObject(params, "numberOfBasement", Integer.class))
                .setWard(MapUtil.getObject(params, "ward", String.class))
                .setStreet(MapUtil.getObject(params, "street", String.class))
                .setDistrictId(MapUtil.getObject(params, "districtId", Long.class))
                .setTypeCode(typeCode)
                .setRentPriceFrom(MapUtil.getObject(params, "rentPriceFrom", Long.class))
                .setRentPriceTo(MapUtil.getObject(params, "rentPriceTo", Long.class))
                .setRentAreaFrom(MapUtil.getObject(params, "rentAreaFrom", Long.class))
                .setRentAreaTo(MapUtil.getObject(params, "rentAreaTo", Long.class))
                .setServiceFee(MapUtil.getObject(params, "serviceFee", String.class))
                .setBrokerageFee(MapUtil.getObject(params, "brokerageFee", String.class))
                .setManagerName(MapUtil.getObject(params, "managerName", String.class))
                .setManagerPhoneNumber(MapUtil.getObject(params, "managerPhoneNumber", String.class))
                .setStaffId(MapUtil.getObject(params, "staffId", Long.class))
                .build();
		return buildingSearchBuilder;
	}

}
