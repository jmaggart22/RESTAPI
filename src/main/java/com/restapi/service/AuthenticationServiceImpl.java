package com.restapi.service;

import com.restapi.dao.AuthenticationDAO;
import com.restapi.util.Prometheus;

import io.javalin.http.Context;

public class AuthenticationServiceImpl implements AuthenticationService{

	public AuthenticationServiceImpl() {
		super();
	}
	
	public void login(Context ctx) {
		
		Prometheus.counter();	//updates for login attempts
		AuthenticationDAO authDao = new AuthenticationDAO();
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		

        if(authDao.authenticateUser(username, password)) { //cookie
        	
				ctx.cookieStore("username", username);
				ctx.cookieStore("password", password);
				
				ctx.status(201);
				} else {
					ctx.status(403);
				}
}

}
