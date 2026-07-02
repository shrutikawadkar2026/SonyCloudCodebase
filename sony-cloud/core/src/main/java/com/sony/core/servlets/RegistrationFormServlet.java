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
                "sling.servlet.paths=/bin/sony/register",
                "sling.servlet.methods=POST"
        })
public class RegistrationFormServlet extends SlingAllMethodsServlet {

    @Override
    protected void doPost(SlingHttpServletRequest request,
                          SlingHttpServletResponse response) throws IOException {

        try {

            String name = request.getParameter("name");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            ResourceResolver resolver = request.getResourceResolver();
            Session session = resolver.adaptTo(Session.class);


            Node root = session.getRootNode();

            Node content = root.hasNode("content")
                    ? root.getNode("content")
                    : root.addNode("content", "nt:unstructured");


            Node sony = content.hasNode("sony")
                    ? content.getNode("sony")
                    : content.addNode("sony", "nt:unstructured");


            Node users = sony.hasNode("users")
                    ? sony.getNode("users")
                    : sony.addNode("users", "nt:unstructured");



            NodeIterator it = users.getNodes();

            while (it.hasNext()) {
                Node existing = it.nextNode();


                if (existing.hasProperty("username") &&
                        existing.getProperty("username").getString().equals(username)) {

                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\":\"Username already exists\"}");
                    return;
                }


                if (existing.hasProperty("email") &&
                        existing.getProperty("email").getString().equals(email)) {

                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\":\"Email already registered\"}");
                    return;
                }
            }


            Node user = users.addNode(username, "nt:unstructured");

            user.setProperty("name", name);
            user.setProperty("username", username);
            user.setProperty("email", email);
            user.setProperty("password", password);

            session.save();

            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}




