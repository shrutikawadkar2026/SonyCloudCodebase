package com.sony.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;
import java.io.IOException;
import java.io.PrintWriter;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/register",
                "sling.servlet.methods=POST"
        })
public class RegistrationServlet extends SlingAllMethodsServlet {

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

            String firstName = request.getParameter("firstName");
            String lastName  = request.getParameter("lastName");
            String email     = request.getParameter("email");
            String password  = request.getParameter("password");
            String mobile    = request.getParameter("mobile");
            String dob       = request.getParameter("dob");
            String age       = request.getParameter("age");
            String username  = request.getParameter("username");

            // VALIDATION
            if (isEmpty(firstName) || isEmpty(email) || isEmpty(password) || isEmpty(username)) {
                out.write("{\"status\":\"error\",\"message\":\"Please fill all required fields.\"}");
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


            javax.jcr.NodeIterator nodeIterator = parentNode.getNodes();
            while (nodeIterator.hasNext()) {
                Node child = nodeIterator.nextNode();
                if (child.hasProperty("email")) {
                    String existingEmail = child.getProperty("email").getString();
                    if (email.equalsIgnoreCase(existingEmail)) {
                        out.write("{\"status\":\"error\",\"message\":\"This email is already registered. Please login.\"}");
                        out.flush();
                        return;
                    }
                }
            }


            String nodeName = username.replaceAll("[^a-zA-Z0-9_]", "_");


            if (parentNode.hasNode(nodeName)) {
                out.write("{\"status\":\"error\",\"message\":\"Please try submitting again.\"}");
                out.flush();
                return;
            }


            Node userNode = parentNode.addNode(nodeName, "nt:unstructured");
            userNode.setProperty("firstName", firstName);
            userNode.setProperty("lastName",  lastName  != null ? lastName  : "");
            userNode.setProperty("email",     email);
            userNode.setProperty("password",  password);
            userNode.setProperty("mobile",    mobile    != null ? mobile    : "");
            userNode.setProperty("dob",       dob       != null ? dob       : "");
            userNode.setProperty("age",       age       != null ? age       : "");
            userNode.setProperty("username",  username);


            session.save();

            out.write("{\"status\":\"success\",\"message\":\"Registration successful!\"}");
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            out.write("{\"status\":\"error\",\"message\":\"Something went wrong. Please try again.\"}");
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