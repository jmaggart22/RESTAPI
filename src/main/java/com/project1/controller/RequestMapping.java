package com.project1.controller;
import com.project1.dao.AuthenticationDAO;
import io.javalin.Javalin;

public class RequestMapping {

	private static RequestController req = new RequestController();
	private static AuthenticationDAO authDao = new AuthenticationDAO();
	


	public static void configureRoutes(Javalin app) {
		
			//login	----------------------------------------------------------------------------------------
			app.post("/login", ctx -> {	
				
				
				req.login(ctx);
				
			
				});
			
			
			//-----------------------------------------------------------------------------------------------
			//management endpoints
			app.before("/admin/*", ctx -> {  //redirects in case of unauthorized access
				
				if(!authDao.check()) {
				ctx.redirect("http://localhost:7070/");
				}
				
				});
				
			app.get("/admin/viewrequests", ctx -> {  //returns all requests from all employees
				
					req.getAllRequests(ctx);
					
				});
			
			app.put("/admin/requestapproval", ctx -> { //approve requests here
				
					req.requestApproval(ctx);
			
				});
			
			app.put("/admin/requestdenial", ctx -> { //deny requests here
				
					req.requestDenial(ctx);
			
				});
			
			app.get("/admin/history", ctx -> {
				
					req.history(ctx);
				
				});
			
			
			
			//employee endpoints
			//-------------------------------------------------------------------------------------------
			app.get("/employee/pending", ctx -> {  //pending requests only
				
					req.getPending(ctx);

				});
			
			app.get("/employee/history", ctx -> { //request history
				
					req.getHistory(ctx);
			});
			
			app.post("/employee/requestform", ctx ->{
				
					req.postRequest(ctx);
				
			});
			
			
			
			//--------------------------------------------------------------------------------------------
			//endpoint for logging out
			app.post("/logout", ctx -> {
				
				ctx.consumeSessionAttribute("username");
				ctx.status(200);
			});
			
			
			
			
	}
	
	
}
