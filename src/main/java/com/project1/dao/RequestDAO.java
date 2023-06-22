package com.project1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.project1.models.EmployeeRequest;
import com.project1.util.ConnectionFactory;

import io.javalin.http.Context;


public class RequestDAO {

	public RequestDAO() {
		super();	
	}
	
	public void getRequests(Context ctx, String username) throws SQLException{
		
			String sql = ("SELECT * FROM requests WHERE username='"+username+"' and pending=true"); //only pending requests are returned
			Connection connection = ConnectionFactory.connectUser();
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ArrayList<EmployeeRequest> a = new ArrayList<>();
			while(rs.next()) {
						EmployeeRequest e = new EmployeeRequest(rs.getString("username"),
						rs.getString("newRequest"), rs.getDouble("amount"), rs.getBoolean("pending"), rs.getBoolean("approved"), rs.getString("timestamp"));
						a.add(e);
						}
			for (EmployeeRequest x: a) 
				ctx.json(a);
			
			
	}
	
	public void getRequestHistory(Context ctx, String username) throws SQLException{
		
		String sql = ("SELECT * FROM requests WHERE username='"+username+"'"); //employee-level request history
		Connection connection = ConnectionFactory.connectUser();
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<EmployeeRequest> a = new ArrayList<>();
		while(rs.next()) {
					EmployeeRequest e = new EmployeeRequest(rs.getString("username"),
					rs.getString("newRequest"), rs.getDouble("amount"), rs.getBoolean("pending"), rs.getBoolean("approved"), rs.getString("timestamp"));
					a.add(e);
					}
		for (EmployeeRequest x: a) 
			ctx.json(a);
		
		
}
	
	public void getAllRequests(Context ctx) throws SQLException{
	
		String sql = ("SELECT * FROM requests");
		Connection connection = ConnectionFactory.connectUser();
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<EmployeeRequest> a = new ArrayList<>();
		while(rs.next()) {
					EmployeeRequest e = new EmployeeRequest(rs.getString("username"),
					rs.getString("newRequest"), rs.getDouble("amount"), rs.getBoolean("pending"), rs.getBoolean("approved"), rs.getString("timestamp"));
					a.add(e);
					}
		for (EmployeeRequest x: a) 
			ctx.json(a);
		
		
}
	
	public Context postRequest(Context ctx) throws SQLException {
		
		EmployeeRequest e = ctx.bodyAsClass(EmployeeRequest.class);
		String sql = "INSERT INTO requests VALUES (?, ?, ?, ?, ?)";
		Connection connection = ConnectionFactory.connectUser();
		PreparedStatement ps = connection.prepareStatement(sql);
				ps.setString(1, e.getUsername());
				ps.setString(2, e.getNewRequest());
				ps.setDouble(3, e.getAmount());
				ps.setBoolean(4, true);
				ps.setBoolean(5, false);
				ps.executeUpdate();
				updateHistory(ctx);		//updates historical data
				return ctx;
				
		
	}

	private void updateHistory(Context ctx) throws SQLException {
		
		EmployeeRequest e = ctx.bodyAsClass(EmployeeRequest.class);
		String sql = "INSERT INTO history VALUES (?, ?, ?)";
		Connection connection = ConnectionFactory.connectUser();
		PreparedStatement ps = connection.prepareStatement(sql);
				ps.setString(1, e.getUsername());
				ps.setString(2, e.getNewRequest());
				ps.setDouble(3, e.getAmount());
				ps.executeUpdate();
		 
		
	}
	
	public void approveRequest(Context ctx, String username) throws SQLException{
		
		String sql = "UPDATE requests SET approved=true, pending=false WHERE username='"+username+"'";
		Connection connection = ConnectionFactory.connectUser();
		PreparedStatement ps = connection.prepareStatement(sql);
			ps.executeUpdate();
			
		
	}
	
	public void denyRequest(Context ctx, String username) throws SQLException{
		
		String sql = "UPDATE requests SET approved=false, pending=false WHERE username='"+username+"'";
		Connection connection = ConnectionFactory.connectUser();
		PreparedStatement ps = connection.prepareStatement(sql);
			ps.executeUpdate();
			
		
	}
	
	public void history(Context ctx) throws SQLException{
		
		String sql = ("SELECT * FROM history"); //employee-level request history
		Connection connection = ConnectionFactory.connectUser();
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<EmployeeRequest> a = new ArrayList<>();
		while(rs.next()) {
					EmployeeRequest e = new EmployeeRequest(rs.getString("username"),
					rs.getString("request"), rs.getDouble("amount"), rs.getString("timestamp"));
					a.add(e);
					}
		for (EmployeeRequest x: a) 
			ctx.json(a);
	}
	
}
