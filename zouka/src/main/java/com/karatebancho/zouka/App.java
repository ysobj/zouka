package com.karatebancho.zouka;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.naming.resources.VirtualDirContext;

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

		Context staticCtx = tomcat.addContext("/web", "/web");
		staticCtx.setDocBase("/tmp/web");

		VirtualDirContext html = new VirtualDirContext();
		html.setExtraResourcePaths("/xxx=/tmp/web");
		// staticCtx.addR
		tomcat.start();
		tomcat.getServer().await();
	}
}
