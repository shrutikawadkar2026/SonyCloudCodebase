package com.sony.core.servlets;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.ContentElement;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/sony/getallcf",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET
        }
)
public class SonyAllCFServlet extends SlingSafeMethodsServlet {

    private static final String CF_BASE_PATH = "/content/dam/sony/en";

    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ResourceResolver resolver = request.getResourceResolver();
        Resource parentResource = resolver.getResource(CF_BASE_PATH);

        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"basePath\":\"").append(CF_BASE_PATH).append("\",");
        json.append("\"contentFragments\":[");

        if (parentResource == null) {
            json.append("]}");
            response.getWriter().write(json.toString());
            return;
        }

        Iterator<Resource> children = parentResource.listChildren();
        boolean firstCF = true;

        while (children.hasNext()) {
            Resource child = children.next();
            ContentFragment cf = child.adaptTo(ContentFragment.class);

            if (cf == null) continue;

            if (!firstCF) json.append(",");
            firstCF = false;

            json.append("{");
            json.append("\"title\":\"").append(safe(cf.getTitle())).append("\",");
            json.append("\"description\":\"").append(safe(cf.getDescription())).append("\",");
            json.append("\"path\":\"").append(safe(child.getPath())).append("\",");
            json.append("\"fields\":{");

            Iterator<ContentElement> elements = cf.getElements();
            boolean firstField = true;

            while (elements.hasNext()) {
                ContentElement element = elements.next();
                String fieldName  = element.getName();
                Object fieldValue = element.getValue().getValue();

                if (!firstField) json.append(",");
                firstField = false;

                if (fieldValue instanceof String[]) {
                    json.append("\"").append(fieldName).append("\":[");
                    String[] arr = (String[]) fieldValue;
                    for (int i = 0; i < arr.length; i++) {
                        if (i > 0) json.append(",");
                        json.append("\"").append(safe(arr[i])).append("\"");
                    }
                    json.append("]");
                } else {
                    String val = fieldValue != null ? fieldValue.toString() : "";
                    json.append("\"").append(fieldName).append("\":\"")
                            .append(safe(val)).append("\"");
                }
            }

            json.append("}}"); // close fields + cf object
        }

        json.append("]}"); // close array + root
        response.getWriter().write(json.toString());
    }

    private String safe(String input) {
        if (input == null) return "";
        return input
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}