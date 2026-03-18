package com.javaweb.repository;

import java.util.List;

import com.javaweb.repository.enity.BuildingEntity;

public interface BuildingRepository {
	public List<BuildingEntity> findAll(String name);
}
