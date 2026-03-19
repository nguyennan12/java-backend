package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.entity.DistrictEntity;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository{
	static final String url = "jdbc:mysql://localhost:3306/building?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    static final String user = "root";
    static final String pass = "Nann_1204";
	@Override
	public DistrictEntity findNameById(Long id) {
		String sql = "SELECT d.name FROM districts d WHERE d.id = " + id + ";";
		DistrictEntity district = new DistrictEntity();
	        try (
	            Connection conn = DriverManager.getConnection(url, user, pass);
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery(sql)) {
	        	
	        	while(rs.next()){
	        		district.setName(rs.getString("name"));
	                
	        	}
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return district;
	}
    
}
