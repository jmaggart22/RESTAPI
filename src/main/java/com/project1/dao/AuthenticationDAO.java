package com.project1.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.project1.util.ConnectionFactory;

public class AuthenticationDAO {
	
	public AuthenticationDAO(){
		super();
	}

	public static boolean f = false;	
	public boolean authenticateUser(String username, String password) {
		try {
		Connection conn = ConnectionFactory.connectUser();
		String SQL = "SELECT * FROM employee WHERE username = ? and upassword = ?";
			try(PreparedStatement ps = conn.prepareStatement(SQL)){
				
				ps.setString(1, username);
				ps.setString(2, password);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					if (!rs.wasNull()) {
						if (rs.getBoolean("ismanager")) {//table contains employees and managers
								f = true; // allows admin access
								return true;
								}
						return true;
					}
					
					
			}
			
			} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
public boolean check() {
	return f;
	}
}
