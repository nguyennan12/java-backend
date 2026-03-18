package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.enity.BuildingEntity;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
    static final String url = "jdbc:mysql://localhost:3306/building?useSSL=false&serverTimezone=UTC";
    static final String user = "root";
    static final String pass = "Nann_1204";
    
	@Override
	public List<BuildingEntity> findAll(String name) {
		 String sql = "SELECT * FROM buildings WHERE building_name LIKE '%" + name + "%'";
	        List<BuildingEntity> result = new ArrayList<>();
	        try (
	            Connection conn = DriverManager.getConnection(url, user, pass);
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery(sql);) {
	        	
	        	while(rs.next()) {
	        		BuildingEntity building = new BuildingEntity();
	                building.setName(rs.getString("building_name"));
	                building.setStreet(rs.getString("street"));
	                building.setWard(rs.getString("ward"));
	                building.setNumberOfBasement(rs.getInt("number_of_basement"));
	                result.add(building);
	        	}
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	   return result;

	}

}
