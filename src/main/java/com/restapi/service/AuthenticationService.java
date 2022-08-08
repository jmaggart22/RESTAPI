package com.restapi.service;

import io.javalin.http.Context;

public interface AuthenticationService {

	public void login(Context ctx);
	
}
