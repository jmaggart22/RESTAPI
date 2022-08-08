package com.restapi.util;

import java.io.File;
import io.javalin.Javalin;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.DiskSpaceMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.system.UptimeMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class Prometheus {
	
	public Prometheus(){
		super();
	}
	
	public static PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
	
	
	static Counter counter = Counter.builder("total_login_attempts")
			.description("The number of login attempts")
			.tag("purpose", "tracking").register(registry);
	
	
	
	public static double counter() {
			counter.increment(1);
			return counter.count();
	}
	
	
	public static void monitoring() {
		
			
		
	 registry.config().commonTags("app","monitored-app");
	 
	 
	 
		
	 new ClassLoaderMetrics().bindTo(registry);
	 new JvmMemoryMetrics().bindTo(registry);
	 new JvmGcMetrics().bindTo(registry);
	 new JvmThreadMetrics().bindTo(registry);
	 new UptimeMetrics().bindTo(registry);
	 new ProcessorMetrics().bindTo(registry);
	 new DiskSpaceMetrics(new File(System.getProperty("user.dir"))).bindTo(registry);
	 
		


	}
	
	public static void monitoringPaths(Javalin app, PrometheusMeterRegistry registry) {

		app.get("/metrics", ctx ->{
			ctx.result(registry.scrape());
		});
		
		
	}
 

}
