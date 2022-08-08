package com.restapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import com.restapi.models.CustomerRequest;
import com.restapi.util.ConnectionFactory;

import io.javalin.http.Context;


public class RequestDAO  {


	public RequestDAO() {
		super();	
	}

	
	public void getAccount(Context ctx, String username) throws SQLException{
		
			String sql = ("SELECT * FROM accounts WHERE username= ? "); //returns account balance
			Connection connection = ConnectionFactory.connectUser();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
						CustomerRequest e = new CustomerRequest(rs.getInt("acctnum"), rs.getString("username"),
						 rs.getDouble("balance"), rs.getString("timestamp"));
						ctx.json(e);
						System.out.println(e);
						}
						
	}
	
	public void deposit(Context ctx, String username) throws SQLException {
		
	
			String sql = "SELECT balance FROM accounts WHERE username= ?";
			Connection connection = ConnectionFactory.connectUser();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet acct = ps.executeQuery();
			
			
			String sql1 = "UPDATE accounts SET balance = ? WHERE username= ? ";
			PreparedStatement ps1 = connection.prepareStatement(sql1);
			double balance;	
					while (acct.next())
					{
					balance = acct.getDouble("balance");
					balance += Double.parseDouble(ctx.formParam("deposit"));
					ps1.setDouble(1, balance);
					ps1.setString(2, username);
					ps1.executeUpdate();
					
					} 
							
}
	
	public void withdraw(Context ctx, String username) {
		
		try {
			String SQLquery = "SELECT balance FROM accounts WHERE username= ?";
			Connection connection = ConnectionFactory.connectUser();
			PreparedStatement ps = connection.prepareStatement(SQLquery);
			ps.setString(1, username);
			ResultSet acct = ps.executeQuery();
			
			String sql1 = "UPDATE accounts SET balance= ? WHERE username= ?";
			PreparedStatement ps1 = connection.prepareStatement(sql1);
			double balance;		
					while (acct.next())
					{
					balance = acct.getDouble("balance");
					balance -= Double.parseDouble(ctx.formParam("withdraw"));
					ps1.setDouble(1, balance);
					ps1.setString(2, username);
					ps1.executeUpdate();
					} 
					updateHistory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
}
	
	public void transfer(Context ctx, String username, int acctNum) throws SQLException {
		boolean x = true; //if the account balance is positive after the withdrawal, the transfer will continue
		//First, withdraw from this account
		
			String SQLquery = "SELECT balance FROM accounts WHERE username = ?";
			Connection connection = ConnectionFactory.connectUser();
			PreparedStatement ps = connection.prepareStatement(SQLquery);
			ps.setString(1, username);
			ResultSet acct = ps.executeQuery();
			
			
			double balance;
				
					while (acct.next())
					{
					balance = acct.getDouble("balance");
					if (!(balance - Double.parseDouble(ctx.formParam("amount")) < 0))            //check for negative account balance
					   {
						balance -= Double.parseDouble(ctx.formParam("amount"));
					   } else { 
						   x = false;
						   ctx.status(418);
						   }
					if (x == true) {
					String sql = "UPDATE accounts SET balance = ? WHERE username = ?";
					PreparedStatement ps1 = connection.prepareStatement(sql);
					ps1.setDouble(1, balance);
					ps1.setString(2, username);
					ps1.executeUpdate();
					}
				
		
	if (x == true) {
		//Second, deposit in another account
	
			String sql1 = "SELECT balance FROM accounts WHERE acctnum = ?";
			PreparedStatement ps2 = connection.prepareStatement(sql1);
			ps2.setInt(1, acctNum);
			ResultSet acct2 = ps2.executeQuery();
			
			
			double balance1;	
			String sql2 = "UPDATE accounts SET balance = ? WHERE acctnum = ?";
			PreparedStatement ps3 = connection.prepareStatement(sql2);		
					while (acct2.next())
					{
					balance1 = acct2.getDouble("balance");
					balance1 += Double.parseDouble(ctx.formParam("amount"));
					ps3.setDouble(1, balance1);
					ps3.setInt(2, acctNum);				
					} 
					updateHistory();
				}
	
			}
					
		
	}
	
	
	public void createAcct(Context ctx, String username, String password, double balance) throws SQLException {
		
		
		String sql = "INSERT INTO users VALUES (?, ?)"; //table used for authentication
		Connection connection = ConnectionFactory.connectUser();
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, password);
		ps.executeUpdate();
		
		  Random rand = new Random();  //assigns a random account number
	      int limit = 9999999;
	      int acctNum = rand.nextInt(limit);
	      
	      String sql2 = "INSERT INTO accounts VALUES ( ? )"; //account info
	      String sql3 = "UPDATE accounts SET username= ?, balance= ? WHERE acctnum= ?";
	      PreparedStatement ps2 = connection.prepareStatement(sql2);
	      ps2.setInt(1, acctNum);
	      ps2.executeUpdate();
	      PreparedStatement ps3 = connection.prepareStatement(sql3);
	      ps3.setString(1, username);
	      ps3.setDouble(2, balance);
	      ps3.setInt(3, acctNum);
	      ps3.executeUpdate();
	      updateHistory();
	   

		
		
	}
	
	//below only available to management---------------------------------------------------------------------
	public void getAllAccounts(Context ctx) throws SQLException { //used by managers to view all account info
		
		String sql = "SELECT * FROM accounts"; 
		Connection connection = ConnectionFactory.connectUser();
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<CustomerRequest> a = new ArrayList<>();
		while(rs.next()) {
			 CustomerRequest e = new CustomerRequest(rs.getInt("acctnum"), rs.getString("username"), rs.getDouble("balance"), rs.getString("timestamp"));
			 a.add(e);
			for (CustomerRequest x : a) {
			ctx.json(a);
			System.out.println(a);
			}
		
		}
		
		
	}
	
	public void closeAccount(Context ctx, String username) throws SQLException { //admin priviledge required
		
		
		String sql = "DELETE FROM accounts WHERE username= ?";
		Connection connection = ConnectionFactory.connectUser();
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, username);
		ps.executeUpdate();
		String sql2 = "DELETE FROM users WHERE username = ?";
		PreparedStatement ps1 = connection.prepareStatement(sql2);
		ps1.setString(1, username);
		ps1.executeUpdate();
		
		
	}
	
	public void getHistory(Context ctx) throws SQLException {
		
		String sql = "SELECT * FROM history";
		Connection connection = ConnectionFactory.connectUser();
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		ArrayList<CustomerRequest> c = new ArrayList<>();
		while(rs.next()) {
			CustomerRequest a = new CustomerRequest(rs.getInt("acctnum"), rs.getString("username"), rs.getDouble("balance"), rs.getString("timestamp"));
			c.add(a);
		}
		for (CustomerRequest x : c)
			ctx.json(c);	
	}
	
	public void updateHistory() throws SQLException { //transaction history is defined as a change in the customer's account balance
		
		String sql = "SELECT * FROM accounts";
		Connection connection = ConnectionFactory.connectUser();
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		ArrayList<CustomerRequest> c = new ArrayList<>();
		while(rs.next()) {
			CustomerRequest a = new CustomerRequest(rs.getInt("acctnum"), rs.getString("username"), rs.getDouble("balance"), rs.getString("timestamp"));
			String sql1 = "INSERT INTO history VALUES (?, ?, ?)";
			PreparedStatement ps1 = connection.prepareStatement(sql1);
			ps1.setInt(1, a.getAcctnum());
			ps1.setString(2, a.getUsername());
			ps1.setDouble(3, a.getBalance());
			ps1.executeUpdate();
			c.add(a);
		}
			
	}
	
}
