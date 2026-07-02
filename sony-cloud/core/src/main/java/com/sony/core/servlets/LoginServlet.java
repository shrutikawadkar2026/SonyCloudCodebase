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
import java.io.PrintWriter;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/userlogin",
                "sling.servlet.methods=POST"
        })
public class LoginServlet extends SlingAllMethodsServlet {

    @Override
    protected void doPost(SlingHttpServletRequest request,
                          SlingHttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Session session = null;

        try {
            ResourceResolver resolver = request.getResourceResolver();

            String email    = request.getParameter("email");
            String password = request.getParameter("password");

            if (isEmpty(email) || isEmpty(password)) {
                out.write("{\"status\":\"error\",\"message\":\"Please enter email and password.\"}");
                out.flush();
                return;
            }


            session = resolver.adaptTo(Session.class);

            if (session == null) {
                out.write("{\"status\":\"error\",\"message\":\"Session error. Please try again.\"}");
                out.flush();
                return;
            }


            Node parentNode = session.getNode("/content/sony/users");


            NodeIterator nodeIterator = parentNode.getNodes();
            while (nodeIterator.hasNext()) {
                Node child = nodeIterator.nextNode();

                if (child.hasProperty("email") && child.hasProperty("password")) {
                    String storedEmail    = child.getProperty("email").getString();
                    String storedPassword = child.getProperty("password").getString();

                    if (email.equalsIgnoreCase(storedEmail) &&
                            password.equals(storedPassword)) {
                        out.write("{\"status\":\"success\",\"message\":\"Login successful!\"}");
                        out.flush();
                        return;
                    }
                }
            }

            out.write("{\"status\":\"error\",\"message\":\"Invalid email or password. Please try again.\"}");
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            out.write(e.getMessage());
            out.flush();
        } finally {
            if (session != null && session.isLive()) {
                session.logout();
            }
        }
    }

    private boolean isEmpty(String val) {
        return val == null || val.trim().isEmpty();
    }
}