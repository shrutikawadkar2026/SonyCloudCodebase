package com.sony.core.servlets;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(service = Servlet.class,
property = {
        Constants.SERVICE_DESCRIPTION+"=Custom Servlet",
        "sling.servlet.method="+ HttpConstants.METHOD_GET,
        "sling.servlet.paths="+"/bin/practicejob"
})
public class PracticeJobServlet extends SlingAllMethodsServlet {
    @Reference
    private JobManager jobManager;
    private final Logger logger= LoggerFactory.getLogger(getClass());

    @Override
    protected void doGet( SlingHttpServletRequest request,  SlingHttpServletResponse response) throws ServletException, IOException {

        final Map<String, Object>props=new HashMap<>();
        props.put("data","test");
        jobManager.addJob("practice/job",props);
        logger.error("Practice Servlet called .....");

        response.setStatus(SlingHttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print("{\"response message\":\" Servlet Called\"}");
    }
}
