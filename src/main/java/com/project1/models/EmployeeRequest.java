package com.project1.models;



public class EmployeeRequest { //used for requests
	
	private String username;
	private String newRequest;
	private double amount;
	private boolean pending;
	private boolean approved;
	private String localTime;
	
	public EmployeeRequest() {
		super();
	}
	

	public EmployeeRequest(String username, String newRequest, double amount, boolean pending, boolean approved, String localTime) {
		super();
		this.username = username;
		this.newRequest = newRequest;
		this.amount = amount;
		this.pending = pending;
		this.approved = approved;
		this.localTime = localTime;
		
	}
	
	public EmployeeRequest(String username, String newRequest, double amount, String localTime) {
		super();
		this.username = username;
		this.newRequest = newRequest;
		this.amount = amount;
		this.localTime = localTime;
		
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getNewRequest() {
		return newRequest;
	}


	public void setNewRequest(String newRequest) {
		this.newRequest = newRequest;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public boolean isPending() {
		return pending;
	}


	public void setPending(boolean pending) {
		this.pending = pending;
	}


	public boolean isApproved() {
		return approved;
	}


	public void setApproved(boolean approved) {
		this.approved = approved;
	}


	public String getLocalTime() {
		return localTime;
	}


	public void setLocalTime(String localTime) {
		this.localTime = localTime;
	}
	
	
	public String toString() {
		return "Employee [username=" + username + ", request=" + newRequest + ", amount=" + amount + ", pending=" + pending + ", approved=" + approved + ", time=" + localTime+"]";
	}
	

}
