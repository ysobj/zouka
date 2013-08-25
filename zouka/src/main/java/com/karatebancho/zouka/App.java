package com.karatebancho.zouka;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;

import com.karatebancho.zouka.servlet.ZoukaServlet;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws LifecycleException {

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(4126);

		Context ctx = tomcat.addContext("/test", "/test");
		Tomcat.addServlet(ctx, "zouka", new ZoukaServlet());
		ctx.addServletMapping("/zouka", "zouka");
		ctx.setDocBase("web");

		Wrapper defaultServlet = Tomcat.addServlet(ctx, "default",
				new DefaultServlet());
		defaultServlet.setLoadOnStartup(1);
		defaultServlet.setOverridable(true);
		ctx.addServletMapping("/", "default");

		tomcat.start();
		tomcat.getServer().await();
	}
}
