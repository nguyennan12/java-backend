package com.javaweb.repository.custom;

import java.util.List;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.model.BuildingEntity;

public interface BuildingRepositoryCustom {
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder);
}
