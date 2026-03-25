package com.javaweb.repository.custom.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.custom.BuildingRepositoryCustom;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.StringUtils;

@Repository
//@Primary
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
	
    public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
    	Long staffId = buildingSearchBuilder.getStaffId();
    	if(staffId != null) {
    		sql.append(" INNER JOIN building_assignments ba ON b.id = ba.building_id ");
    	}
    	List<String> typeCode = buildingSearchBuilder.getTypeCode();
    	if(typeCode != null && typeCode.size() != 0) {
    		sql.append(" INNER JOIN building_rent_types brt ON b.id = brt.building_id ");
    		sql.append("INNER JOIN rent_types rt ON rt.id = brt.rent_type_id");
    	}
    }
    
    public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
    	try {
    		Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
    		for(Field item: fields) {
    			item.setAccessible(true);
    			String fieldName = item.getName();
    			if(!fieldName.equals("staffId") && !fieldName.equals("typeCode") && 
    				!fieldName.startsWith("rentArea") && !fieldName.startsWith("rentPrice")) {
    				Object value = item.get(buildingSearchBuilder);
    				if(value != null) {
        				if(item.getType().getName().equals("java.lang.Long")) {
        					where.append(" AND b." + StringUtils.toSnakeCase(fieldName) + " = " + value);
        				}
        				else if(item.getType().getName().equals("java.lang.String")) {
        					where.append(" AND b." + StringUtils.toSnakeCase(fieldName) + " LIKE '%" + value + "%' ");
        				}
        			}
    			}
    		}
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
    	Long staffId = buildingSearchBuilder.getStaffId();
    	if(staffId != null) {
    		where.append(" AND ba.user_id = " + staffId);
    	}
    	
    	Long rentAreaTo = buildingSearchBuilder.getRentAreaTo();
    	Long rentAreaFrom = buildingSearchBuilder.getRentAreaFrom();
    	if(rentAreaTo != null|| rentAreaFrom != null) {
    		where.append(" AND EXISTS (SELECT * FROM rent_areas ra WHERE b.id = ra.building_id");
    		if(rentAreaTo != null) {
    			where.append(" AND ra.value <= " + rentAreaTo);
    		}
    		else {
    			where.append(" AND ra.value >= " + rentAreaFrom);
    		}
    		where.append(" ) ");
    	}
    	
    	Long rentPriceTo = buildingSearchBuilder.getRentPriceTo();
    	Long rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
    	if(rentPriceTo != null || rentPriceFrom != null) {
    		if(rentPriceTo != null) {
    			where.append(" AND b.rent_price <= " + rentPriceTo);
    		}
    		else {
    			where.append(" AND b.rent_price >= " + rentPriceFrom);
    		}
    	}
    	
    	List<String> typeCode = buildingSearchBuilder.getTypeCode();
    	if(typeCode != null && typeCode.size() != 0){
    		where.append(" AND rt.code IN (" + 
    			typeCode.stream()
    				.map(code -> "'" + code + "'")
    				.collect(Collectors.joining(",")) +
    				")");
    	}
    }
	
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		 StringBuilder sql = new  StringBuilder("SELECT b.* FROM building b ");
			 
			 joinTable(buildingSearchBuilder, sql);
			 StringBuilder where = new StringBuilder(" WHERE 1 = 1");
			 queryNormal(buildingSearchBuilder, where);
			 querySpecial(buildingSearchBuilder, where);
			 where.append(" GROUP BY b.id;");
			 sql.append(where);
		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
		return query.getResultList();
	}



}
