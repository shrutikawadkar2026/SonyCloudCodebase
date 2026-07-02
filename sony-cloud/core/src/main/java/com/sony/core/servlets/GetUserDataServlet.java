package com.sony.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.servlet.Servlet;
import java.io.IOException;
import java.io.PrintWriter;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/getuserdata",
                "sling.servlet.methods=GET"
        })
public class GetUserDataServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Session session = null;

        try {
            String email = request.getParameter("email");

            if (email == null || email.trim().isEmpty()) {
                out.write("{\"status\":\"error\",\"message\":\"Email is required.\"}");
                out.flush();
                return;
            }

            ResourceResolver resolver = request.getResourceResolver();
            session = resolver.adaptTo(Session.class);

            Node parentNode = session.getNode("/content/sony/users");

            NodeIterator nodeIterator = parentNode.getNodes();
            while (nodeIterator.hasNext()) {
                Node child = nodeIterator.nextNode();

                if (child.hasProperty("email")) {
                    String storedEmail = child.getProperty("email").getString();

                    if (email.equalsIgnoreCase(storedEmail)) {
                        String firstName = child.hasProperty("firstName") ? child.getProperty("firstName").getString() : "";
                        String lastName  = child.hasProperty("lastName")  ? child.getProperty("lastName").getString()  : "";
                        String username  = child.hasProperty("username")  ? child.getProperty("username").getString()  : "";
                        String mobile    = child.hasProperty("mobile")    ? child.getProperty("mobile").getString()    : "";
                        String age       = child.hasProperty("age")       ? child.getProperty("age").getString()       : "";

                        out.write("{" +
                                "\"status\":\"success\"," +
                                "\"firstName\":\"" + firstName + "\"," +
                                "\"lastName\":\""  + lastName  + "\"," +
                                "\"email\":\""     + storedEmail + "\"," +
                                "\"username\":\""  + username  + "\"," +
                                "\"mobile\":\""    + mobile    + "\"," +
                                "\"age\":\""       + age       + "\"" +
                                "}");
                        out.flush();
                        return;
                    }
                }
            }

            out.write("{\"status\":\"error\",\"message\":\"User not found.\"}");
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
}