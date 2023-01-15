package servlets;

import resources.TestResource;
import sax.ReadXMLFileSAX;
import service.ResourceServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourceServlet extends HttpServlet {

    public static final String PATH = "/resources";

    private final ResourceServer resourceServer;

    public ResourceServlet(ResourceServer resourceServer) {
        this.resourceServer = resourceServer;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getParameter("path");

        TestResource testResource = (TestResource) ReadXMLFileSAX.readXML(path);
        resourceServer.setTestResource(testResource);

        resp.setContentType("text/html;charset=utf-8");
//        resp.getWriter().println(accountServer);
    }
}
