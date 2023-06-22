package com.project1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	static final String USERNAME = System.getenv("db_user");
	static final String PASSWORD = System.getenv("db_pass");
	static final String URL = System.getenv("db_url");
	
	public ConnectionFactory() {
		super();
	}
	
	public static Connection connectUser() throws SQLException{
		
				Connection connection;
				connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				return connection;
			
	}
	
	
	
}
