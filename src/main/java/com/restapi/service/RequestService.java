package com.restapi.service;

import java.sql.SQLException;

import io.javalin.http.Context;

public interface RequestService {
	
	
	public void getAcct(Context ctx);
	public void custDeposit(Context ctx);
	public void custWithdraw(Context ctx);
	public void custTransfer(Context ctx) throws SQLException;
	public void newAcct(Context ctx) throws SQLException;

	//management
	public void closeAcct(Context ctx) throws SQLException;
	public void getAllAccts(Context ctx) throws SQLException;
	public void history(Context ctx) throws SQLException;
	

}

