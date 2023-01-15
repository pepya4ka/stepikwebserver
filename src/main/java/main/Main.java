package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.ResourceServer;
import service.ResourceServerControllerMXBean;
import service.impl.ResourceServerController;
import service.impl.ResourceServerImpl;
import servlets.ResourceServlet;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        ResourceServer resourceServer = new ResourceServerImpl();

        ResourceServerControllerMXBean resourceServerController = new ResourceServerController(resourceServer);
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName resourceServerControllerName = new ObjectName("Admin:type=ResourceServerController");
        mBeanServer.registerMBean(resourceServerController, resourceServerControllerName);


        ResourceServlet resourceServlet = new ResourceServlet(resourceServer);
        ServletHolder servletHolder = new ServletHolder(resourceServlet);
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addServlet(servletHolder, ResourceServlet.PATH);


        Server server = new Server(8080);
        server.setHandler(servletContextHandler);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
