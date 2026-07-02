package com.sony.core.servlets;

import com.sony.core.services.PracticeReadPropertiesService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service= Servlet.class,
property = {
        "sling.servlet.paths=/bin/jkl",
        "sling.servlet.methods=get"
})
public class PracticeReadPropertyServiceServlet extends SlingSafeMethodsServlet {
    @Reference
    PracticeReadPropertiesService practiceReadPropertiesService;
    @Override
    protected void doGet( SlingHttpServletRequest request,  SlingHttpServletResponse response) throws ServletException, IOException {
        String path=request.getParameter("path");
        String result= practiceReadPropertiesService.fetchingProperties(path);
        response.getWriter().write(result);
    }
}
