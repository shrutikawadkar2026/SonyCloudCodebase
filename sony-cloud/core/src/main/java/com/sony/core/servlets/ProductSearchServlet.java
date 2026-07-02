package com.sony.core.servlets;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(service= Servlet.class,
        property={
                "sling.servlet.paths=/bin/productSearch",
                "sling.servlet.methods=get"
        }
)
public class ProductSearchServlet extends SlingSafeMethodsServlet {

        @Reference
        private QueryBuilder queryBuilder;

        @Override
        protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                JsonObject jsonResponse = new JsonObject();
                JsonArray items = new JsonArray();
                String keyword = request.getParameter("keyword");

                if (keyword == null || keyword.trim().isEmpty()) {
                        response.setStatus(400);
                        jsonResponse.addProperty("success", false);
                        jsonResponse.addProperty("message", "Invalid or empty keyword");
                        response.getWriter().write(new Gson().toJson(jsonResponse));
                        return;
                }

                try {

                        ResourceResolver resourceResolver = request.getResourceResolver();
                        Session session = resourceResolver.adaptTo(Session.class);

                        Map<String, String> map = new HashMap<>();
                        map.put("path", "/content/dam/products");
                        map.put("type", "dam:Asset");
                        map.put("p.limit", "10");


                        if (keyword != null && !keyword.isEmpty()) {

                                map.put("nodename", "*" + keyword.toLowerCase() + "*");
                                }




                        int propertyCount = 1;
                        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
                                String key = entry.getKey();
                                String[] values = entry.getValue();


                                if (!key.equals("keyword") && values.length > 0 && !values[0].isEmpty()) {
                                        map.put(propertyCount + "_property", "jcr:content/data/master/" + key);
                                        map.put(propertyCount + "_property.value", values[0]);
                                        propertyCount++;
                                }
                        }

                        Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);
                        SearchResult result = query.getResult();
//TODO:convert the below json object into a pojo arraylist
                        for (Hit hit : result.getHits()) {
                                try {
                                        JsonObject item = new JsonObject();
                                        item.addProperty("path", hit.getPath());
                                        item.addProperty("title", hit.getTitle());
                                        item.addProperty("name", hit.getTitle());
                                        Resource priceResource = hit.getResource().getChild("jcr:content/data/master");
                                        if (priceResource != null) {
                                                String price = priceResource.getValueMap().get("price", String.class);
                                                item.addProperty("price", price);
                                        }
                                        items.add(item);
                                } catch (RepositoryException e) {

                                }
                        }

                        jsonResponse.addProperty("success", true);
                        jsonResponse.addProperty("totalResults", result.getTotalMatches());
                        jsonResponse.add("results", items);

                } catch (Exception e) {

                        jsonResponse.addProperty("message", "Error: " + e.getMessage());
                        response.setStatus(500);
                }

                response.getWriter().write(new Gson().toJson(jsonResponse));
        }
}



























