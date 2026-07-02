package com.sony.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;

import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.Iterator;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.resourceTypes=sony/components/content/articlelist",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.extensions=json"
        }
)
public class Articlesservlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        JsonArray jsonArray = new JsonArray();

        ResourceResolver resolver = request.getResourceResolver();

        PageManager pageManager = resolver.adaptTo(PageManager.class);

        Page rootPage = pageManager.getPage("/content/sony/us/en");

        if (rootPage != null) {

            Iterator<Page> pages = rootPage.listChildren();

            while (pages.hasNext()) {

                Page childPage = pages.next();

                JsonObject obj = new JsonObject();

                obj.addProperty("title", childPage.getTitle());
                obj.addProperty("name", childPage.getName());
                obj.addProperty("path", childPage.getPath());
                obj.addProperty("description", childPage.getDescription());

                jsonArray.add(obj);
            }
        }

        response.getWriter().write(jsonArray.toString());
    }
}