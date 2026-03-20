package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.RentAreaEntity;
import com.javaweb.utils.ConnectionJDBCUtil;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository{
    
	@Override
	public List<RentAreaEntity> getValueById(Long id) {
		String sql = " SELECT rent_areas.value FROM rent_areas where rent_areas.building_id = " + id;
		List<RentAreaEntity> result = new ArrayList<>();
		  try (
		            Connection conn = ConnectionJDBCUtil.getConnection();
		            Statement stmt = conn.createStatement();
		            ResultSet rs = stmt.executeQuery(sql)) {
		        	
		        	while(rs.next()){
		        		RentAreaEntity rentArea = new RentAreaEntity();
		        		rentArea.setValue(rs.getLong("value"));
		        		result.add(rentArea);
		        	}
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		return result;
	}
	
}
