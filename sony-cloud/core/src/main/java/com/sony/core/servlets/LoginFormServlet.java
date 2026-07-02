package com.sony.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.servlet.Servlet;
import java.io.IOException;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/sony/login",
                "sling.servlet.methods=POST"
        })

public class LoginFormServlet extends SlingAllMethodsServlet{
    @Override
    protected void doPost(SlingHttpServletRequest request,
                          SlingHttpServletResponse response) throws IOException {

        try {

            String username = request.getParameter("username");
            String password = request.getParameter("password");

            ResourceResolver resolver = request.getResourceResolver();
            Session session = resolver.adaptTo(Session.class);

            Node root = session.getRootNode();

            if (!root.hasNode("content")) {
                response.getWriter().write("{\"error\":\"not_found\"}");
                return;
            }

            Node content = root.getNode("content");

            if (!content.hasNode("sony")) {
                response.getWriter().write("{\"error\":\"not_found\"}");
                return;
            }

            Node sony = content.getNode("sony");

            if (!sony.hasNode("users")) {
                response.getWriter().write("{\"error\":\"not_found\"}");
                return;
            }

            Node users = sony.getNode("users");

            NodeIterator it = users.getNodes();

            while (it.hasNext()) {

                Node user = it.nextNode();

                String storedUsername = user.hasProperty("username")
                        ? user.getProperty("username").getString()
                        : "";

                String storedPassword = user.hasProperty("password")
                        ? user.getProperty("password").getString()
                        : "";


                if (storedUsername.equals(username)) {


                    if (storedPassword.equals(password)) {

                        response.setContentType("application/json");
                        response.getWriter().write("{\"status\":\"success\"}");
                        return;

                    } else {

                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\":\"invalid_password\"}");
                        return;
                    }
                }
            }


            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"not_found\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"error\":\"server_error\"}");
        }
    }
}
