package com.restapi.models;



public class CustomerRequest { 
	
	private int acctnum;
	private String username;
	private double balance;
	private String timestamp;
	
	public CustomerRequest() {
		super();
	}
	

	public CustomerRequest(int acctnum, String username, double balance, String timestamp) {
		super();
		this.acctnum = acctnum;
		this.username = username;
		this.balance = balance;
		this.timestamp = timestamp;
		
	}


	public int getAcctnum() {
		return acctnum;
	}


	public void setAcctnum(int acctnum) {
		this.acctnum = acctnum;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}


	public String getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}


	@Override
	public String toString() {
		return "CustomerRequest [acctnum=" + acctnum + ", username=" + username + ", balance=" + balance + ", timestamp="
				+ timestamp + "]";
	}
	

	

}
