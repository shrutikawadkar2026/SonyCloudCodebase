package com.sony.core.servlets;

import com.sony.core.services.GreetingServices;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class,
         property = {

        "sling.servlet.paths=/bin/greet",
                 "sling.servlet.methods=GET"
         }
)

public class GreetingServlet extends SlingSafeMethodsServlet {
    @Reference
    private GreetingServices greetingServices;

    @Override
    protected void doGet( SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        if (name==null || name.isEmpty()){
            name = "world";


        }
        // now we call the service
        String greetings = greetingServices.getGreetings(name);

        //set the Responce
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(greetings);
    }
}
