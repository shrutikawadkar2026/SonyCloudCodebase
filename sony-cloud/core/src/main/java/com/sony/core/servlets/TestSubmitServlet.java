package com.sony.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.osgi.service.component.annotations.Component;

import org.apache.sling.servlets.annotations.SlingServletPaths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class)
@SlingServletPaths("/bin/testsubmit")
public class TestSubmitServlet extends SlingAllMethodsServlet {

    private static final Logger log = LoggerFactory.getLogger(TestSubmitServlet.class);

    @Override
    protected void doPost(SlingHttpServletRequest request,
                          SlingHttpServletResponse response)
            throws ServletException, IOException {

        log.error("TEST SERVLET CALLED");

        String name = request.getParameter("name");

        log.error("NAME = {}", name);

        response.getWriter().write("SERVLET SUCCESS");
    }
}