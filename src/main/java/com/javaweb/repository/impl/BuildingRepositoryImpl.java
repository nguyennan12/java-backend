package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
import com.javaweb.utils.NumberUtils;
import com.javaweb.utils.StringUtils;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
   
    
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
		 StringBuilder sql = new  StringBuilder("SELECT b.id, b.name, b.ward,"
		 		+ " b.street, b.district_id, b.number_of_basement, b.manager_name,"
		 		+ " b.manager_phone_number,b.rent_price,"
		 		+ " b.floor_area, b.service_fee, b.brokerage_fee FROM buildings b ");
		 
		 joinTable(buildingSearchBuilder, sql);
		 StringBuilder where = new StringBuilder(" WHERE 1 = 1");
		 queryNormal(buildingSearchBuilder, where);
		 querySpecial(buildingSearchBuilder, where);
		 sql.append(where);
		 sql.append(" GROUP BY b.id;");
	        List<BuildingEntity> result = new ArrayList<>();
	        try (
	            Connection conn = ConnectionJDBCUtil.getConnection();
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery(sql.toString());) {
	        	
	        	while(rs.next()){
	        		BuildingEntity building = new BuildingEntity();
	        		building.setId(rs.getLong("b.id"));
	                building.setName(rs.getString("b.name"));
	                building.setStreet(rs.getString("b.street"));
	                building.setWard(rs.getString("b.ward"));
	                building.setDistrictId(rs.getLong("b.district_id"));
	                building.setNumberOfBasement(rs.getInt("b.number_of_basement"));
	                building.setFloorArea(rs.getLong("b.floor_area"));
	                building.setRentPrice(rs.getLong("b.rent_price"));
	                building.setServiceFee(rs.getString("b.service_fee"));
	                building.setBrokerageFee(rs.getString("b.brokerage_fee"));
	                building.setManagerName(rs.getString("b.manager_name"));
	                building.setManagerPhoneNumber(rs.getString("b.manager_phone_number"));
	                result.add(building);
	        	}
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	   return result;

	}

}
