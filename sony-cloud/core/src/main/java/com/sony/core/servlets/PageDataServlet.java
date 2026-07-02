package com.sony.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@Component(service = Servlet.class,
property = {
        "sling.servlet.resourceTypes=sony/components/page",
        "sling.servlet.methods=GET",
        "sling.servlet.extensions=txt"
        })

public class PageDataServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {

        Resource resource = request.getResource();
        ResourceResolver resolver = request.getResourceResolver();

        PageManager pageManager = resolver.adaptTo(PageManager.class);
        Page page = pageManager.getContainingPage(resource);

        response.setContentType("text/plain");

        if (page != null) {

            response.getWriter().println("Page Title: " + page.getTitle());
            response.getWriter().println("Page Path: " + page.getPath());
            response.getWriter().println("------ Properties ------");

            Resource content = page.getContentResource();

            if (content != null) {
                ValueMap props = content.getValueMap();

                for (Map.Entry<String, Object> entry : props.entrySet()) {
                    response.getWriter().println(
                            entry.getKey() + " : " + entry.getValue()
                    );
                }
            }

        } else {
            response.getWriter().println("Page not found");
        }
    }
}
