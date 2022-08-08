package com.restapi;

import io.javalin.Javalin;
import io.javalin.plugin.metrics.MicrometerPlugin;
import com.restapi.controller.RequestMapping;
import com.restapi.util.Prometheus;




public class MainDriver {


	public static void main(String...args) { 

		Prometheus.monitoring();
		
		Javalin app = Javalin.create( config -> {
			config.registerPlugin(new MicrometerPlugin(Prometheus.registry));
				}
				).start(7070);
		
		 Prometheus.monitoringPaths(app, Prometheus.registry);

		 RequestMapping.configureRoutes(app);
	
		
		 
	 

	}
}
