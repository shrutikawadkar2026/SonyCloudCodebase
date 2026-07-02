package com.sony.core.servlets;

import com.sony.core.services.TVDetailsService;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.resource.ResourceResolver;

import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import org.json.JSONObject;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.IOException;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/tvdetails",
                "sling.servlet.methods=GET"
        }
)
public class TVDetailsServlet extends SlingSafeMethodsServlet {

    @Reference
    private TVDetailsService tvDetailsService;

    @Override
    protected void doGet(
            SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws ServletException, IOException {

        JSONObject jsonObject = new JSONObject();

        try {

            String tvModelNo =
                    request.getParameter("tvModelNo");

            ResourceResolver resolver =
                    request.getResourceResolver();

            jsonObject =
                    tvDetailsService.getTVDetails(
                            tvModelNo,
                            resolver
                    );

        } catch (Exception e) {

            e.printStackTrace();

            jsonObject.put(
                    "error",
                    e.getMessage()
            );
        }

        response.setContentType("application/json");

        response.getWriter()
                .write(jsonObject.toString());
    }
}