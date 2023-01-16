package main;

import models.UserProfile;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import services.AccountService;
import servlets.SignInServlet;
import servlets.SignOutServlet;
import servlets.SignUpServlet;
import servlets.UsersServlet;

import javax.servlet.http.HttpServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();

        accountService.addUser("admin", "nimda");

        SignUpServlet signUpServlet = new SignUpServlet(accountService);
        SignInServlet signInServlet = new SignInServlet(accountService);
        SignOutServlet signOutServlet = new SignOutServlet(accountService);
        UsersServlet usersServlet = new UsersServlet(accountService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        addServletToContext(context, signUpServlet, "/signup");
        addServletToContext(context, signInServlet, "/signin");
        addServletToContext(context, signOutServlet, "/signout");
        addServletToContext(context, usersServlet, "/users");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("frontend");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        Server server = new Server(8080);
        server.setHandler(handlers);
 
        server.start();
        System.out.println("Server started");
        server.join();
    }

    private static void addServletToContext(ServletContextHandler context, HttpServlet servlet, String mapping) {
        context.addServlet(new ServletHolder(servlet), mapping);
    }
}