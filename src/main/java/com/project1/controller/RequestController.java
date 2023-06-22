package com.project1.controller;

import java.sql.SQLException;

import com.project1.dao.AuthenticationDAO;
import com.project1.dao.RequestDAO;
import com.project1.service.AuthenticationService;
import com.project1.service.Prometheus;
import com.project1.service.RequestService;

import io.javalin.http.Context;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class RequestController implements RequestService, AuthenticationService{

	public RequestController() {
		super();
	}
	static PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
	
	//-----------------------------------login
	public void login(Context ctx) {
		Prometheus prom = new Prometheus();
		prom.counter();	//updates prometheus for login attempts
		AuthenticationDAO authDao = new AuthenticationDAO();
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		
			if(authDao.authenticateUser(username, password)) {
				ctx.sessionAttribute("username", username);
				ctx.sessionAttribute("password", password);
				
				ctx.status(201);
				} else {
					ctx.status(403);
				}
	}
	//-----------------------------------admin
	public void getAllRequests(Context ctx){
		
		RequestDAO reqDao = new RequestDAO();
		try {
			reqDao.getAllRequests(ctx);
			ctx.status(200);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void requestApproval(Context ctx) {
		
		String user = ctx.formParam("username");
		RequestDAO req = new RequestDAO();
		try {
			req.approveRequest(ctx, user);
			ctx.status(201);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void requestDenial(Context ctx) {
		
		String user = ctx.formParam("username");
		RequestDAO req = new RequestDAO();
		try {
			req.denyRequest(ctx, user);
			ctx.status(201);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void history(Context ctx) {
		
		RequestDAO req = new RequestDAO();
		try {
			req.history(ctx);
			ctx.status(201);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------------employee
	public void getPending(Context ctx){
		
		String user = ctx.formParam("username");
		String check = ctx.cachedSessionAttribute("username").toString(); 
		
		if(user.equalsIgnoreCase(check)) {

			 RequestDAO req = new RequestDAO();
			 
			 try {
				req.getRequests(ctx, check);
				ctx.status(200);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			 
		}
	}
	
	public void getHistory(Context ctx){
		
		String user = ctx.formParam("username");
		String check = ctx.cachedSessionAttribute("username").toString(); 
		
		if(user.equalsIgnoreCase(check)) {

			 RequestDAO req = new RequestDAO();
			 
			 try {
				req.getRequestHistory(ctx, check);
				ctx.status(200);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			 
		}
	}
	
	public void postRequest(Context ctx){
		
		RequestDAO req = new RequestDAO();
		
		try {
			req.postRequest(ctx);
			ctx.status(201);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
}
