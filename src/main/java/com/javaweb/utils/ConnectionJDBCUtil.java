package com.javaweb.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionJDBCUtil {
	 static final String url = "jdbc:mysql://localhost:3306/building?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
	 static final String user = "root";
	 static final String pass = "Nann_1204";
	 public static Connection getConnection() {
	 	Connection conn = null;
	 	try {
	 		 conn = DriverManager.getConnection(url, user, pass);
	 	}
	 	catch (Exception e) {
            e.printStackTrace();
        }
	 	return conn;
	 }
}
