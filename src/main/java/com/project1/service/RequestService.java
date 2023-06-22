package com.project1.service;

import io.javalin.http.Context;

public interface RequestService {
	
	public void getAllRequests(Context ctx);
	public void requestApproval(Context ctx);
	public void requestDenial(Context ctx);
	public void getPending(Context ctx);
	public void getHistory(Context ctx);
	public void postRequest(Context ctx);

}

