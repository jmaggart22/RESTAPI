package com.restapi.controller;

import com.restapi.dao.AuthenticationDAO;
import io.javalin.Javalin;

public class RequestMapping {

			static RequestController req = new RequestController();
			static AuthenticationDAO authDao = new AuthenticationDAO();
	


	public static void configureRoutes(Javalin app) {
		

			//login	-----------------------------------------------------------------------------------------
			app.post("/login", ctx -> {	
						
					req.login(ctx);
							
					});
			
			//testing for authorization
			//-----------------------------------------------------------------------------------------------
			app.before("/customer/*", ctx -> {  
				
					if(!authDao.check()) {
					ctx.status(403);
					}
					
					});
			
			app.before("/admin/*", ctx -> {  
				
					if(!authDao.check()) {
					ctx.status(403);
					}
					
					});
					
			
			//customer endpoints
			//-----------------------------------------------------------------------------------------------
			app.get("/customer/accountinfo", ctx -> {  //account
				
					req.getAcct(ctx);

					});
			
			app.post("/customer/deposit", ctx -> { //make a deposit
				
					req.custDeposit(ctx);
				
					});
			
			app.post("/customer/withdrawal", ctx ->{ //make a withdrawal
				
					req.custWithdraw(ctx);
				
					});
			
			app.post("/customer/transfer", ctx ->{ //transfer to another account
				
					req.custTransfer(ctx);
					
					});
			
			//guest endpoint for new account
			//----------------------------------------------------------------------------------------------
			app.post("/guest/newaccount", ctx ->{ //create a new account
				
					req.newAcct(ctx);
				
					});
			
			//management endpoints
			//----------------------------------------------------------------------------------------------
			
			app.get("/admin/viewaccounts", ctx ->{
				
					req.getAllAccts(ctx);
				
					});
			
			app.post("/admin/closeaccount", ctx -> {
				
					req.closeAcct(ctx);
				
					});
			
			app.get("/admin/history", ctx -> {
				
					req.history(ctx);
					
					});
			
			
			//endpoint for logging out
			//----------------------------------------------------------------------------------------------
			app.post("/logout", ctx -> {
				
					ctx.clearCookieStore();
					ctx.status(200);
					
					});
			
			
			
			
	}
	
	
}
