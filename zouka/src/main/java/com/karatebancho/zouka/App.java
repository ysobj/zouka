package com.karatebancho.zouka;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;

import com.karatebancho.zouka.serlet.ZoukaServlet;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws LifecycleException {
		Tomcat tomcat = new Tomcat();
		Context ctx = tomcat.addContext("/test", "/test");
		tomcat.setBaseDir("/tmp");
		Tomcat.addServlet(ctx, "zouka", new ZoukaServlet());
		ctx.addServletMapping("/zouka", "zouka");
		ctx.setDocBase("/tmp/test");

		Wrapper defaultServlet = Tomcat.addServlet(ctx, "default",
				new DefaultServlet());
		defaultServlet.setLoadOnStartup(1);
		defaultServlet.setOverridable(true);
		ctx.addServletMapping("/", "default");

		tomcat.setPort(4126);
		tomcat.start();
		tomcat.getServer().await();
	}
}
