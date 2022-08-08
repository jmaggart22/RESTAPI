package com.restapi.controller;

import java.sql.SQLException;

import com.restapi.service.AuthenticationServiceImpl;
import com.restapi.service.RequestServiceImpl;

import io.javalin.http.Context;

public class RequestController {

	RequestServiceImpl req = new RequestServiceImpl();
	AuthenticationServiceImpl auth = new AuthenticationServiceImpl();
	
	public RequestController() {
		super();
	}
	
	
	//-----------------------------------login
	public void login(Context ctx) {
		
		auth.login(ctx);
		
	}

	
	//-----------------------------------------------customer functions
	public void getAcct(Context ctx){                          //gets account info
		
		req.getAcct(ctx);
		
	}
	
	public void custDeposit(Context ctx) {       //deposits
		
		req.custDeposit(ctx);
		
	}
	
	public void custWithdraw(Context ctx) {  //withdrawals
		
		req.custWithdraw(ctx);
		
		
	}
	
	public void custTransfer(Context ctx) throws SQLException {  //transferring between accounts (based on account number)
		
		req.custTransfer(ctx);
		
	}
	
	//--------------------------------------------------------------- for creating new accounts
	public void newAcct(Context ctx) throws SQLException {
		
		req.newAcct(ctx);
	}
	
	//---------------------------------------------------------management
	public void getAllAccts(Context ctx) throws SQLException { //this also security checks the session
		
		req.getAllAccts(ctx);
		
	}
	
	public void closeAcct(Context ctx) throws SQLException { 
		
		req.closeAcct(ctx);
		
	}
	
	public void history(Context ctx) throws SQLException {
		
		req.history(ctx);
		
		
	}

	
}
