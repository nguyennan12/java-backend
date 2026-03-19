package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.NumberUtils;
import com.javaweb.utils.StringUtils;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
    static final String url = "jdbc:mysql://localhost:3306/building?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    static final String user = "root";
    static final String pass = "Nann_1204";
    
    public static void joinTable(Map<String, Object> params, List<String> typeCode, StringBuilder sql) {
    	String staffId = (String)params.get("staffId");
    	if(StringUtils.checkString(staffId) == true) {
    		sql.append(" INNER JOIN building_assignments ba ON b.id = ba.building_id ");
    	}
    	if(typeCode != null && typeCode.size() != 0) {
    		sql.append(" INNER JOIN building_rent_types brt ON b.id = brt.building_id ");
    		sql.append("INNER JOIN rent_types rt ON rt.id = brt.rent_type_id");
    	}
    	String rentAreaTo = (String)params.get("rentAreaTo");
    	String rentAreaFrom = (String)params.get("rentAreaFrom");
    	if(StringUtils.checkString(rentAreaTo)|| StringUtils.checkString(rentAreaFrom)) {
    		sql.append(" INNER JOIN rent_areas ra ON b.id = ra.building_id ");
    	}
    }
    
    public static void queryNormal(Map<String, Object> params, List<String> typeCode, StringBuilder where) {
    	for(Map.Entry<String, Object> it : params.entrySet()) {
    		if(!it.getKey().equals("staffId") && !it.getKey().equals("typeCode") && 
    				!it.getKey().startsWith("rentArea") && !it.getKey().startsWith("rentPrice")) {
    			String value = it.getValue().toString();
    			if(StringUtils.checkString(value) == true) {
    				if(NumberUtils.isNumber(value) == true) {
    					where.append(" AND b." + it.getKey() + " = " + value);
    				}
    				else {
    					where.append(" AND b." + it.getKey() + " LIKE '%" + value + "%' ");
    				}
    			}
    		}
    	}
    }
    
    public static void querySpecial(Map<String, Object> params, List<String> typeCode, StringBuilder where) {
    	String staffId = (String)params.get("staffId");
    	if(StringUtils.checkString(staffId) == true) {
    		where.append(" AND ba.user_id = " + staffId);
    	}
    	
    	String rentAreaTo = (String)params.get("rentAreaTo");
    	String rentAreaFrom = (String)params.get("rentAreaFrom");
    	if(StringUtils.checkString(rentAreaTo)|| StringUtils.checkString(rentAreaFrom)) {
    		if(StringUtils.checkString(rentAreaTo)) {
    			where.append(" AND ra.value <= " + rentAreaTo);
    		}
    		else {
    			where.append(" AND ra.value >= " + rentAreaFrom);
    		}
    	}
    	
    	String rentPriceTo = (String)params.get("rentPriceTo");
    	String rentPriceFrom = (String)params.get("rentPriceFrom");
    	if(StringUtils.checkString(rentPriceTo) == true || StringUtils.checkString(rentPriceFrom) == true) {
    		if(StringUtils.checkString(rentPriceTo)) {
    			where.append(" AND b.rent_price <= " + rentPriceTo);
    		}
    		else {
    			where.append(" AND b.rent_price >= " + rentPriceFrom);
    		}
    	}
    	
    	if(typeCode != null && typeCode.size() != 0){
    		where.append(" AND rt.code IN (" + 
    			typeCode.stream()
    				.map(code -> "'" + code + "'")
    				.collect(Collectors.joining(",")) +
    				")");
    	}
    }
    
	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode) {
		 StringBuilder sql = new  StringBuilder("SELECT b.building_name, b.ward,"
		 		+ " b.street, b.district_id, b.number_of_basement, b.manager_name,"
		 		+ " b.manager_phone_number,b.rent_price,"
		 		+ " b.floor_area, b.service_fee, b.brokerage_fee FROM buildings b ");
		 
		 joinTable(params, typeCode, sql);
		 StringBuilder where = new StringBuilder(" WHERE 1 = 1");
		 queryNormal(params, typeCode, where);
		 querySpecial(params, typeCode, where);
		 sql.append(where);
		 sql.append(" GROUP BY b.id;");
	        List<BuildingEntity> result = new ArrayList<>();
	        try (
	            Connection conn = DriverManager.getConnection(url, user, pass);
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery(sql.toString());) {
	        	
	        	while(rs.next()){
	        		BuildingEntity building = new BuildingEntity();
	                building.setName(rs.getString("b.building_name"));
	                building.setStreet(rs.getString("b.street"));
	                building.setWard(rs.getString("b.ward"));
	                building.setDistrictId(rs.getLong("b.district_id"));
	                building.setNumberOfBasement(rs.getInt("b.number_of_basement"));
	                building.setFloorArea(rs.getLong("b.floor_area"));
	                building.setRentPrice(rs.getLong("b.rent_price"));
	                building.setServiceFee(rs.getString("b.service_fee"));
	                building.setBrokerageFee(rs.getString("b.brokerage_fee"));
	                building.setManagerName(rs.getString("b.manager_name"));
	                building.setMamagerNumberPhone(rs.getString("b.manager_phone_number"));
	                result.add(building);
	        	}
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	   return result;

	}

}
